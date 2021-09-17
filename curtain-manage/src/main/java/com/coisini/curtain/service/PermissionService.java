package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 通过分组id得到分组的权限
     *
     * @param groupId 分组id
     * @return 权限
     */
    List<Permission> getPermissionByGroupId(Integer groupId);

    /**
     * 通过分组id得到分组的权限
     *
     * @param groupIds 分组id
     * @return 权限
     */
    List<Permission> getPermissionByGroupIds(List<Integer> groupIds);

    /**
     * 通过分组id得到分组的权限与分组id的映射
     *
     * @param groupIds 分组id
     * @return 权限map
     */
    Map<Long, List<Permission>> getPermissionMapByGroupIds(List<Integer> groupIds);

    /**
     * 将权限结构化
     *
     * @param permissions 权限
     * @return 结构化的权限
     */
    List<Map<String, List<Map<String, String>>>> structuringPermissions(List<Permission> permissions);

    /**
     * 将权限简单地结构化
     *
     * @param permissions 权限
     * @return 结构化的权限
     */
    Map<String, List<String>> structuringPermissionsSimply(List<Permission> permissions);

    /**
     * 通过分组id和权限模块得到分组的权限与分组id的映射
     *
     * @param groupIds 分组id
     * @param module 权限模块
     * @return 权限map
     */
    List<Permission> getPermissionByGroupIdsAndModule(List<Integer> groupIds, String module);
}
