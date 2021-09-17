package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.GroupPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Repository
public interface GroupPermissionMapper extends BaseMapper<GroupPermission> {

    int insertBatch(@Param("relations") List<GroupPermission> relations);

    int deleteBatchByGroupIdAndPermissionId(@Param("groupId") Integer groupId, @Param("permissionIds") List<Integer> permissionIds);
}
