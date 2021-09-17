package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.SpuDO;
import com.coisini.curtain.model.SpuDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-23
 */
public interface SpuMapper extends BaseMapper<SpuDO> {

    /**
     * 获取spu详情
     * @param id spu的id
     * @return SpuDetailDO
     */
    SpuDetailDO getDetail(Integer id);

    /**
     * 获取指定spu的规格id列表
     * @param id spu的id
     * @return spu关联的规格id列表
     */
    List<Integer> getSpecKeys(@Param("id") Integer id);

}
