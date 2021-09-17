package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.CouponDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author TaleLin
 */
@Repository
public interface CouponMapper extends BaseMapper<CouponDO> {

    List<Integer> getCouponsByActivityId(@Param("id") Integer id);

}
