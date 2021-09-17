package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.SkuSpecDO;

public interface SkuSpecService extends IService<SkuSpecDO> {

    Integer getSpecValueId(Integer keyId, Integer skuId);

    void deleteSpecs(Integer spuId, Integer skuId);

}
