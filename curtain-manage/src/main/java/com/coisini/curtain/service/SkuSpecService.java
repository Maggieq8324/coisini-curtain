package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.SkuSpec;

public interface SkuSpecService extends IService<SkuSpec> {

    Integer getSpecValueId(Integer keyId, Integer skuId);

    void deleteSpecs(Integer spuId, Integer skuId);

}
