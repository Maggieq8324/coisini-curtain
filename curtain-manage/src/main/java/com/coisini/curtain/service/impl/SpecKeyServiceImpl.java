package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.bo.SpecKeyAndItemsBo;
import com.coisini.curtain.evt.SpecKeyEvt;
import com.coisini.curtain.mapper.SkuMapper;
import com.coisini.curtain.mapper.SkuSpecMapper;
import com.coisini.curtain.mapper.SpecKeyMapper;
import com.coisini.curtain.mapper.SpecValueMapper;
import com.coisini.curtain.model.*;
import com.coisini.curtain.service.SpecKeyService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKey> implements SpecKeyService {

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecKeyMapper specKeyMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Override
    public void create(SpecKeyEvt evt) {
        // 不可创建重复的规格名
        QueryWrapper<SpecKey> wrapper = new QueryWrapper();
        wrapper.lambda().eq(SpecKey::getName, evt.getName());
        SpecKey existed = this.getOne(wrapper);
        if (existed != null) {
            throw new ForbiddenException(60000);
        }
        SpecKey specKey = new SpecKey();
        BeanUtils.copyProperties(evt, specKey);
        this.save(specKey);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SpecKeyEvt evt, Integer id) {
        SpecKey specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        BeanUtils.copyProperties(evt, specKey);
        this.updateById(specKey);
        // 更新 sku 的 specs 字段
        List<Integer> skuIds = skuSpecMapper.getSkuIdsByKeyId(id);
        if (skuIds.isEmpty()) {
            return;
        }
        List<Sku> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpec> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpec::getKeyId, id);
            wrapper.lambda().eq(SkuSpec::getSkuId, sku.getId());
            List<SkuSpec> skuSpecs = skuSpecMapper.selectList(wrapper);
            ArrayList<SpecKeyValue> specs = new ArrayList<>();
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
        SpecKey specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public SpecKeyAndItemsBo getKeyAndValuesById(Integer id) {
        SpecKey specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        QueryWrapper<SpecValue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpecValue::getSpecId, specKey.getId());
        List<SpecValue> items = specValueMapper.selectList(wrapper);
        SpecKeyAndItemsBo specKeyAndItems = new SpecKeyAndItemsBo(specKey, items);
        return specKeyAndItems;
    }

    @Override
    public List<SpecKey> getBySpuId(Integer spuId) {
        return this.baseMapper.getBySpuId(spuId);
    }

}
