package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.model.SpecValue;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.evt.SpecValueEvt;
import com.coisini.curtain.mapper.SkuMapper;
import com.coisini.curtain.mapper.SkuSpecMapper;
import com.coisini.curtain.mapper.SpecValueMapper;
import com.coisini.curtain.model.Sku;
import com.coisini.curtain.model.SkuSpec;
import com.coisini.curtain.model.SpecKeyValue;
import com.coisini.curtain.service.SpecValueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValue> implements SpecValueService {

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Override
    public void create(SpecValueEvt evt) {
        SpecValue specValue = new SpecValue();
        BeanUtils.copyProperties(evt, specValue);
        this.save(specValue);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SpecValueEvt evt, Integer id) {
        SpecValue specValue = this.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        BeanUtils.copyProperties(evt, specValue);
        this.updateById(specValue);

        List<Integer> skuIds = skuSpecMapper.getSkuIdsByValueId(id);
        if (skuIds.isEmpty()) {
            return;
        }
        List<Sku> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpec> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpec::getValueId, id);
            wrapper.lambda().eq(SkuSpec::getSkuId, sku.getId());
            List<SkuSpec> skuSpecs = skuSpecMapper.selectList(wrapper);
            List<SpecKeyValue> specs = new ArrayList<>();
            skuSpecs.forEach(skuSpec -> {
                SpecKeyValue specKeyAndValue = specValueMapper.getSpecKeyAndValueById(skuSpec.getKeyId(), skuSpec.getValueId());
                specs.add(specKeyAndValue);
            });
            sku.setSpecs(specs);
            skuMapper.updateById(sku);
        });
    }

    @Override
    public void delete(Integer id) {
        SpecValue specValue = this.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public SpecKeyValue getSpecKeyAndValueById(Integer keyId, Integer valueId) {
        return this.getBaseMapper().
                getSpecKeyAndValueById(keyId, valueId);
    }

}
