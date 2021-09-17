package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.Sku;
import com.coisini.curtain.model.SkuDetail;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@Repository
public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 根据 skuId 获取 sku 详情
     * @param id skuId
     * @return SkuDetailDO
     */
    SkuDetail getDetail(Integer id);

}
