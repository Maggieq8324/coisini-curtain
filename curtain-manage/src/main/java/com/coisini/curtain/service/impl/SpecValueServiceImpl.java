package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.model.SpecValueDO;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.dto.SpecValueDTO;
import com.coisini.curtain.mapper.SkuMapper;
import com.coisini.curtain.mapper.SkuSpecMapper;
import com.coisini.curtain.mapper.SpecValueMapper;
import com.coisini.curtain.model.SkuDO;
import com.coisini.curtain.model.SkuSpecDO;
import com.coisini.curtain.model.SpecKeyValueDO;
import com.coisini.curtain.service.SpecValueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValueDO> implements SpecValueService {

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Override
    public void create(SpecValueDTO dto) {
        SpecValueDO specValueDO = new SpecValueDO();
        BeanUtils.copyProperties(dto, specValueDO);
        this.save(specValueDO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SpecValueDTO dto, Integer id) {
        SpecValueDO specValue = this.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        BeanUtils.copyProperties(dto, specValue);
        this.updateById(specValue);

        List<Integer> skuIds = skuSpecMapper.getSkuIdsByValueId(id);
        if (skuIds.isEmpty()) {
            return;
        }
        List<SkuDO> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpecDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpecDO::getValueId, id);
            wrapper.lambda().eq(SkuSpecDO::getSkuId, sku.getId());
            List<SkuSpecDO> skuSpecs = skuSpecMapper.selectList(wrapper);
            List<SpecKeyValueDO> specs = new ArrayList<>();
            skuSpecs.forEach(skuSpec -> {
                SpecKeyValueDO specKeyAndValue = specValueMapper.getSpecKeyAndValueById(skuSpec.getKeyId(), skuSpec.getValueId());
                specs.add(specKeyAndValue);
            });
            sku.setSpecs(specs);
            skuMapper.updateById(sku);
        });
    }

    @Override
    public void delete(Integer id) {
        SpecValueDO specValue = this.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public SpecKeyValueDO getSpecKeyAndValueById(Integer keyId, Integer valueId) {
        return this.getBaseMapper().
                getSpecKeyAndValueById(keyId, valueId);
    }

}
