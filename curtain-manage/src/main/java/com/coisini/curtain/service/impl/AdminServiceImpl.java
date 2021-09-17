package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.mapper.GroupPermissionMapper;
import com.coisini.curtain.model.GroupPermission;
import com.coisini.curtain.model.Permission;
import com.coisini.curtain.model.UserIdentity;
import com.coisini.curtain.service.AdminService;
import com.coisini.curtain.service.PermissionService;
import com.coisini.curtain.service.UserIdentityService;
import com.coisini.curtain.service.UserService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.bo.GroupPermissionBo;
import com.coisini.curtain.common.enumeration.GroupLevelEnum;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.evt.admin.DispatchPermissionEvt;
import com.coisini.curtain.evt.admin.DispatchPermissionsEvt;
import com.coisini.curtain.evt.admin.NewGroupEvt;
import com.coisini.curtain.evt.admin.RemovePermissionsEvt;
import com.coisini.curtain.evt.admin.ResetPasswordEvt;
import com.coisini.curtain.evt.admin.UpdateGroupEvt;
import com.coisini.curtain.evt.admin.UpdateUserInfoEvt;
import com.coisini.curtain.model.Group;
import com.coisini.curtain.model.User;
import com.coisini.curtain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 * @author Juzi@TaleLin
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private GroupPermissionMapper groupPermissionMapper;

    @Override
    public IPage<User> getUserPageByGroupId(Integer groupId, Integer count, Integer page) {
        Page<User> pager = new Page<>(page, count);
        IPage<User> iPage;
        // 如果group_id为空，则以分页的形式返回所有用户
        if (groupId == null) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            Integer rootUserId = userService.getRootUserId();
            wrapper.lambda().ne(User::getId, rootUserId);
            iPage = userService.page(pager, wrapper);
        } else {
            iPage = userService.getUserPageByGroupId(pager, groupId);
        }
        return iPage;
    }

    @Override
    public boolean changeUserPassword(Integer id, ResetPasswordEvt evt) {
        throwUserNotExistById(id);
        return userIdentityService.changePassword(id, evt.getNewPassword());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean deleteUser(Integer id) {
        throwUserNotExistById(id);
        boolean userRemoved = userService.removeById(id);
        QueryWrapper<UserIdentity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentity::getUserId, id);
        return userRemoved && userIdentityService.remove(wrapper);
    }

    @Override
    public boolean updateUserInfo(Integer id, UpdateUserInfoEvt validator) {
        List<Integer> newGroupIds = validator.getGroupIds();
        if (newGroupIds == null || newGroupIds.isEmpty()) {
            return false;
        }
        Integer rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        boolean anyMatch = newGroupIds.stream().anyMatch(it -> it.equals(rootGroupId));
        if (anyMatch) {
            throw new ForbiddenException("you can't add user to root group", 10073);
        }
        List<Integer> existGroupIds = groupService.getUserGroupIdsByUserId(id);
        // 删除existGroupIds有，而newGroupIds没有的
        List<Integer> deleteIds = existGroupIds.stream().filter(it -> !newGroupIds.contains(it)).collect(Collectors.toList());
        // 添加newGroupIds有，而existGroupIds没有的
        List<Integer> addIds = newGroupIds.stream().filter(it -> !existGroupIds.contains(it)).collect(Collectors.toList());
        return groupService.deleteUserGroupRelations(id, deleteIds) && groupService.addUserGroupRelations(id, addIds);
    }

    @Override
    public IPage<Group> getGroupPage(Integer page, Integer count) {
        IPage<Group> iPage = groupService.getGroupPage(page, count);
        return iPage;
    }

    @Override
    public GroupPermissionBo getGroup(Integer id) {
        throwGroupNotExistById(id);
        return groupService.getGroupAndPermissions(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean createGroup(NewGroupEvt evt) {
        throwGroupNameExist(evt.getName());
        Group group = Group.builder().name(evt.getName()).info(evt.getInfo()).build();
        groupService.save(group);
        if (evt.getPermissionIds() != null && !evt.getPermissionIds().isEmpty()) {
            List<GroupPermission> relations = evt.getPermissionIds().stream()
                    .map(id -> new GroupPermission(group.getId(), id))
                    .collect(Collectors.toList());
            groupPermissionMapper.insertBatch(relations);
        }
        return true;
    }

    @Override
    public boolean updateGroup(Integer id, UpdateGroupEvt evt) {
        // bug 如果只修改info，不修改name，则name已经存在，此时不应该报错
        Group exist = groupService.getById(id);
        if (exist == null) {
            throw new NotFoundException("group not found", 10024);
        }
        if (!exist.getName().equals(evt.getName())) {
            throwGroupNameExist(evt.getName());
        }
        Group group = Group.builder().name(evt.getName()).info(evt.getInfo()).build();
        group.setId(id);
        return groupService.updateById(group);
    }

    @Override
    public boolean deleteGroup(Integer id) {
        Integer rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        Integer guestGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.GUEST);
        if (id.equals(rootGroupId)) {
            throw new ForbiddenException("root group can't delete", 10074);
        }
        if (id.equals(guestGroupId)) {
            throw new ForbiddenException("guest group can't delete", 10075);
        }
        throwGroupNotExistById(id);
        return groupService.removeById(id);
    }

    @Override
    public boolean dispatchPermission(DispatchPermissionEvt evt) {
        GroupPermission groupPermission = new GroupPermission(evt.getGroupId(), evt.getPermissionId());
        return groupPermissionMapper.insert(groupPermission) > 0;
    }

    @Override
    public boolean dispatchPermissions(DispatchPermissionsEvt evt) {
        List<GroupPermission> relations = evt.getPermissionIds().stream()
                .map(id -> new GroupPermission(evt.getGroupId(), id))
                .collect(Collectors.toList());
        return groupPermissionMapper.insertBatch(relations) > 0;
    }

    @Override
    public boolean removePermissions(RemovePermissionsEvt evt) {
        return groupPermissionMapper.deleteBatchByGroupIdAndPermissionId(evt.getGroupId(), evt.getPermissionIds()) > 0;
    }

    @Override
    public List<Group> getAllGroups() {
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        Integer rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        wrapper.lambda().ne(Group::getId, rootGroupId);
        List<Group> groups = groupService.list(wrapper);
        return groups;
    }

    @Override
    public List<Permission> getAllPermissions() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Permission::getMount, true);
        return permissionService.list(wrapper);
    }

    @Override
    public Map<String, List<Permission>> getAllStructualPermissions() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Permission::getMount, true);
        List<Permission> permissions = getAllPermissions();
        Map<String, List<Permission>> res = new HashMap<>();
        permissions.forEach(permission -> {
            if (res.containsKey(permission.getModule())) {
                res.get(permission.getModule()).add(permission);
            } else {
                ArrayList t = new ArrayList();
                t.add(permission);
                res.put(permission.getModule(), t);
            }
        });
        return res;
    }

    private void throwUserNotExistById(Integer id) {
        boolean exist = userService.checkUserExistById(id);
        if (!exist) {
            throw new NotFoundException("user not found", 10021);
        }
    }

    private void throwGroupNotExistById(Integer id) {
        boolean exist = groupService.checkGroupExistById(id);
        if (!exist) {
            throw new NotFoundException("group not found", 10024);
        }
    }

    private void throwGroupNameExist(String name) {
        boolean exist = groupService.checkGroupExistByName(name);
        if (exist) {
            throw new ForbiddenException("group name already exist, please enter a new one", 10072);
        }
    }
}
