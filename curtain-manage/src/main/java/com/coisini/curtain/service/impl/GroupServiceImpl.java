package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.model.UserGroup;
import com.coisini.curtain.service.PermissionService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import com.coisini.curtain.bo.GroupPermissionBo;
import com.coisini.curtain.common.enumeration.GroupLevelEnum;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.mapper.GroupMapper;
import com.coisini.curtain.mapper.UserGroupMapper;
import com.coisini.curtain.model.Group;
import com.coisini.curtain.model.Permission;
import com.coisini.curtain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 * @author Juzi@TaleLin
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public List<Group> getUserGroupsByUserId(Integer userId) {
        return this.baseMapper.selectUserGroups(userId);
    }

    @Override
    public List<Integer> getUserGroupIdsByUserId(Integer userId) {
        return this.baseMapper.selectUserGroupIds(userId);
    }

    @Override
    public IPage<Group> getGroupPage(int page, int count) {
        Page pager = new Page(page, count);
        return this.baseMapper.selectPage(pager, null);
    }

    @Override
    public boolean checkGroupExistById(Integer id) {
        return this.baseMapper.selectCountById(id) > 0;
    }

    @Override
    public GroupPermissionBo getGroupAndPermissions(Integer id) {
        Group group = this.baseMapper.selectById(id);
        List<Permission> permissions = permissionService.getPermissionByGroupId(id);
        return new GroupPermissionBo(group, permissions);
    }

    @Override
    public boolean checkGroupExistByName(String name) {
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Group::getName, name);
        return this.baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean checkIsRootByUserId(Integer userId) {
        QueryWrapper<UserGroup> wrapper = new QueryWrapper<>();
        Integer rootGroupId = this.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        wrapper.lambda().eq(UserGroup::getUserId, userId)
                .eq(UserGroup::getGroupId, rootGroupId);
        UserGroup relation = userGroupMapper.selectOne(wrapper);
        return relation != null;
    }

    @Override
    public boolean deleteUserGroupRelations(Integer userId, List<Integer> deleteIds) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            return true;
        }
        if (checkIsRootByUserId(userId)) {
            throw new ForbiddenException("can't modify the root user's group", 10078);
        }
        QueryWrapper<UserGroup> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(UserGroup::getUserId, userId)
                .in(UserGroup::getGroupId, deleteIds);
        return userGroupMapper.delete(wrapper) > 0;
    }

    @Override
    public boolean addUserGroupRelations(Integer userId, List<Integer> addIds) {
        if (addIds == null || addIds.isEmpty()) {
            return true;
        }
        boolean ok = checkGroupExistByIds(addIds);
        if (!ok) {
            throw new ForbiddenException("cant't add user to non-existent group", 10077);
        }
        List<UserGroup> relations = addIds.stream().map(it -> new UserGroup(userId, it)).collect(Collectors.toList());
        return userGroupMapper.insertBatch(relations) > 0;
    }

    @Override
    public List<Integer> getGroupUserIds(Integer id) {
        QueryWrapper<UserGroup> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserGroup::getGroupId, id);
        List<UserGroup> list = userGroupMapper.selectList(wrapper);
        return list.stream().map(UserGroup::getUserId).collect(Collectors.toList());
    }

    @Override
    public Group getParticularGroupByLevel(GroupLevelEnum level) {
        if (GroupLevelEnum.USER.getValue().equals(level.getValue())) {
            return null;
        } else {
            QueryWrapper<Group> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Group::getLevel, level);
            Group group = this.baseMapper.selectOne(wrapper);
            return group;
        }
    }

    @Override
    public Integer getParticularGroupIdByLevel(GroupLevelEnum level) {
        Group group = this.getParticularGroupByLevel(level);
        return group == null ? 0 : group.getId();
    }

    private boolean checkGroupExistByIds(List<Integer> ids) {
        return ids.stream().allMatch(this::checkGroupExistById);
    }
}
