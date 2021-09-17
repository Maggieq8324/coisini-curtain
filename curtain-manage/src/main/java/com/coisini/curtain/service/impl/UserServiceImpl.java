package com.coisini.curtain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.common.LocalUser;
import com.coisini.curtain.common.enumeration.GroupLevelEnum;
import com.coisini.curtain.evt.user.ChangePasswordEvt;
import com.coisini.curtain.mapper.UserGroupMapper;
import com.coisini.curtain.model.Permission;
import com.coisini.curtain.model.UserGroup;
import com.coisini.curtain.service.PermissionService;
import com.coisini.curtain.service.UserIdentityService;
import com.coisini.curtain.service.UserService;
import io.github.talelin.autoconfigure.exception.FailedException;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.evt.user.RegisterEvt;
import com.coisini.curtain.evt.user.UpdateInfoEvt;
import com.coisini.curtain.mapper.UserMapper;
import com.coisini.curtain.model.Group;
import com.coisini.curtain.model.User;
import com.coisini.curtain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 * @author Juzi@TaleLin
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Transactional
    @Override
    public User createUser(RegisterEvt evt) {
        boolean exist = this.checkUserExistByUsername(evt.getUsername());
        if (exist) {
            throw new ForbiddenException("username already exist, please choose a new one", 10071);
        }
        if (StrUtil.isNotBlank(evt.getEmail())) {
            exist = this.checkUserExistByEmail(evt.getEmail());
            if (exist) {
                throw new ForbiddenException("email already exist, please choose a new one", 10076);
            }
        } else {
            // bug 前端如果传入的email为 "" 时，由于数据库中存在""的email，会报duplication错误
            // 所以如果email为blank，必须显示设置为 null
            evt.setEmail(null);
        }
        User user = new User();
        BeanUtil.copyProperties(evt, user);
        this.baseMapper.insert(user);
        if (evt.getGroupIds() != null && !evt.getGroupIds().isEmpty()) {
            checkGroupsValid(evt.getGroupIds());
            checkGroupsExist(evt.getGroupIds());
            List<UserGroup> relations = evt.getGroupIds()
                    .stream()
                    .map(groupId -> new UserGroup(user.getId(), groupId))
                    .collect(Collectors.toList());
            userGroupMapper.insertBatch(relations);
        } else {
            // id为2的分组为游客分组
            Integer guestGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.GUEST);
            UserGroup relation = new UserGroup(user.getId(), guestGroupId);
            userGroupMapper.insert(relation);
        }
        userIdentityService.createUsernamePasswordIdentity(user.getId(), evt.getUsername(), evt.getPassword());
        return user;
    }

    @Transactional
    @Override
    public User updateUserInfo(UpdateInfoEvt evt) {
        User user = LocalUser.getLocalUser();
        if (StrUtil.isNotBlank(evt.getUsername())) {
            boolean exist = this.checkUserExistByUsername(evt.getUsername());
            if (exist) {
                throw new ForbiddenException("username already exist, please choose a new one", 10071);
            }
            user.setUsername(evt.getUsername());
            userIdentityService.changeUsername(user.getId(), evt.getUsername());
        }
        BeanUtil.copyProperties(evt, user);
        this.baseMapper.updateById(user);
        return user;
    }

    @Override
    public User changeUserPassword(ChangePasswordEvt evt) {
        User user = LocalUser.getLocalUser();
        boolean valid = userIdentityService.verifyUsernamePassword(user.getId(), user.getUsername(), evt.getOldPassword());
        if (!valid) {
            throw new ParameterException("password invalid, please enter correct password", 10032);
        }
        valid = userIdentityService.changePassword(user.getId(), evt.getNewPassword());
        if (!valid) {
            throw new FailedException("password change failed", 10011);
        }
        return user;
    }

    @Override
    public List<Group> getUserGroups(Integer userId) {
        return groupService.getUserGroupsByUserId(userId);
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getStructualUserPermissions(Integer userId) {
        List<Permission> permissions = getUserPermissions(userId);
        return permissionService.structuringPermissions(permissions);
    }

    @Override
    public List<Permission> getUserPermissions(Integer userId) {
        // 查找用户搜索分组，查找分组下的所有权限
        List<Integer> groupIds = groupService.getUserGroupIdsByUserId(userId);
        if (groupIds == null || groupIds.size() == 0) {
            return new ArrayList<>();
        }
        return permissionService.getPermissionByGroupIds(groupIds);
    }

    @Override
    public List<Permission> getUserPermissionsByModule(Integer userId, String module) {
        List<Integer> groupIds = groupService.getUserGroupIdsByUserId(userId);
        if (groupIds == null || groupIds.size() == 0) {
            return new ArrayList<>();
        }
        return permissionService.getPermissionByGroupIdsAndModule(groupIds, module);
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public boolean checkUserExistByUsername(String username) {
        int rows = this.baseMapper.selectCountByUsername(username);
        return rows > 0;
    }

    @Override
    public boolean checkUserExistByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getEmail, email);
        int rows = this.baseMapper.selectCount(wrapper);
        return rows > 0;
    }

    @Override
    public boolean checkUserExistById(Integer id) {
        int rows = this.baseMapper.selectCountById(id);
        return rows > 0;
    }

    @Override
    public IPage<User> getUserPageByGroupId(Page<User> pager, Integer groupId) {
        Integer rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        return this.baseMapper.selectPageByGroupId(pager, groupId, rootGroupId);
    }

    @Override
    public Integer getRootUserId() {
        Integer rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        QueryWrapper<UserGroup> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserGroup::getGroupId, rootGroupId);
        UserGroup userGroup = userGroupMapper.selectOne(wrapper);
        return userGroup == null ? 0 : userGroup.getUserId();
    }

    private void checkGroupsExist(List<Integer> ids) {
        for (Integer id : ids) {
            if (!groupService.checkGroupExistById(id)) {
                throw new NotFoundException("group not found，can't create user", 10023);
            }
        }
    }

    private void checkGroupsValid(List<Integer> ids) {
        Integer rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        boolean anyMatch = ids.stream().anyMatch(it -> it.equals(rootGroupId));
        if (anyMatch) {
            throw new ForbiddenException("you can't add user to root group", 10073);
        }
    }
}
