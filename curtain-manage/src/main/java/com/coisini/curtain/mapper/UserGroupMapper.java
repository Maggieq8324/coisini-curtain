package com.coisini.curtain.mapper;

import com.coisini.curtain.model.UserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pedro@TaleLin
 */
@Repository
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    int insertBatch(@Param("relations") List<UserGroup> relations);
}
