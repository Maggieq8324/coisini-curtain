package com.coisini.curtain.mapper;

import com.coisini.curtain.model.SkuSpecDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-06-03
 */
@Repository
public interface SkuSpecMapper extends BaseMapper<SkuSpecDO> {

    /**
     * 根据规格名id获取sku的id列表
     * @param keyId 规格名id
     * @return sku的id列表
     */
    List<Integer> getSkuIdsByKeyId(Integer keyId);

    /**
     * 根据规格值id获取sku的id列表
     * @param valueId 规格值id
     * @return sku的id列表
     */
    List<Integer> getSkuIdsByValueId(Integer valueId);

    /**
     * 根据skuId和spuId删除sku_spec记录
     * @param spuId Integer
     * @param skuId Integer
     */
    void deleteSpecs(Integer spuId, Integer skuId);

    /**
     * 获取已选的specId
     * @param keyId   规格键 id
     * @param skuId   sku id
     * @return 规格值id
     */
    Integer getSpecValueId(Integer keyId, Integer skuId);

}
