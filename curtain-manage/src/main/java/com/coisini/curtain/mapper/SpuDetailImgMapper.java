package com.coisini.curtain.mapper;

import com.coisini.curtain.model.SpuDetailImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-27
 */
public interface SpuDetailImgMapper extends BaseMapper<SpuDetailImg> {

    /**
     * 物理删除spu详情图
     * @param spuId Integer
     */
    void hardDeleteImgsBySpuId(@Param("spuId") Integer spuId);

}
