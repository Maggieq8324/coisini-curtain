package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过分组ids得到所有分组下的权限
     *
     * @param groupIds 分组ids
     * @return 权限
     */
    List<Permission> selectPermissionsByGroupIds(@Param("groupIds") List<Integer> groupIds);

    /**
     * 通过分组id得到所有分组下的权限
     *
     * @param groupId 分组id
     * @return 权限
     */
    List<Permission> selectPermissionsByGroupId(@Param("groupId") Integer groupId);

    /**
     * 通过分组ids得到所有分组下的权限
     *
     * @param groupIds 分组ids
     * @param module   权限模块
     * @return 权限
     */
    List<Permission> selectPermissionsByGroupIdsAndModule(@Param("groupIds") List<Integer> groupIds, @Param("module") String module);
}
