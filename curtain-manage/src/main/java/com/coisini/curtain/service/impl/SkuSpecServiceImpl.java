package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.mapper.SkuSpecMapper;
import com.coisini.curtain.model.SkuSpec;
import com.coisini.curtain.service.SkuSpecService;
import org.springframework.stereotype.Service;

@Service
public class SkuSpecServiceImpl extends ServiceImpl<SkuSpecMapper, SkuSpec> implements SkuSpecService {
    @Override
    public Integer getSpecValueId(Integer keyId, Integer skuId) {
        return this.getBaseMapper().getSpecValueId(keyId, skuId);
    }

    @Override
    public void deleteSpecs(Integer spuId, Integer skuId) {
        this.getBaseMapper().deleteSpecs(spuId, skuId);
    }
}
