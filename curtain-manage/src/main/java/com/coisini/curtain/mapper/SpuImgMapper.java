package com.coisini.curtain.mapper;

import com.coisini.curtain.model.SpuImg;
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
public interface SpuImgMapper extends BaseMapper<SpuImg> {

    /**
     * 物理删除spu轮播图
     * @param spuId Integer
     */
    void hardDeleteImgsBySpuId(@Param("spuId") Integer spuId);

}
