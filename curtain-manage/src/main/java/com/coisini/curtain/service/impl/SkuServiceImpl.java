package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.evt.SkuEvt;
import com.coisini.curtain.mapper.SkuMapper;
import com.coisini.curtain.model.*;
import com.coisini.curtain.service.SkuSpecService;
import com.coisini.curtain.service.SpecValueService;
import com.coisini.curtain.service.SpuService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import com.coisini.curtain.evt.SkuSelector;
import com.coisini.curtain.service.SkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SpecValueService specValueService;

    @Autowired
    private SkuSpecService skuSpecService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(SkuEvt evt) {
        // 1. 检测数据
        QueryWrapper<Sku> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Sku::getTitle, evt.getTitle());
        int count = this.count(wrapper);
        if (count > 0) {
            throw new ForbiddenException(80000);
        }
        Spu spu = spuService.getById(evt.getSpuId());
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        List<SkuSelector> selectors = evt.getSelectors();
        List<SpecKeyValue> specs = this.checkSelectors(selectors);
        if (specs == null) {
            throw new ParameterException(80001);
        }
        // 2. 存储sku基础信息
        Sku sku = new Sku();
        BeanUtils.copyProperties(evt, sku);
        String code = this.generateSkuCode(selectors, evt.getSpuId());
        sku.setCode(code);
        sku.setCategoryId(spu.getCategoryId());
        sku.setRootCategoryId(spu.getRootCategoryId());
        sku.setSpecs(specs);
        this.save(sku);
        // 3. 存储信息到关联表中
        this.insertSpecs(specs, evt.getSpuId(), sku.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SkuEvt evt, Integer id) {
        // 1. 检测数据
        Spu spu = spuService.getById(evt.getSpuId());
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        List<SkuSelector> selectors = evt.getSelectors();
        List<SpecKeyValue> specs = this.checkSelectors(selectors);
        if (specs == null) {
            throw new ParameterException(80001);
        }
        // 2. 存储sku基础信息
        Sku sku = this.getById(id);
        if (sku == null) {
            throw new NotFoundException(80002);
        }
        BeanUtils.copyProperties(evt, sku);
        String code = this.generateSkuCode(selectors, evt.getSpuId());
        sku.setCode(code);
        sku.setCategoryId(spu.getCategoryId());
        sku.setRootCategoryId(spu.getRootCategoryId());
        sku.setSpecs(specs);
        this.updateById(sku);
        // 3.先删除关联信息，再存储信息到关联表中
        skuSpecService.deleteSpecs(sku.getSpuId(), sku.getId());
        this.insertSpecs(specs, evt.getSpuId(), sku.getId());
    }

    @Override
    public void delete(Integer id) {
        // 删除 sku
        Sku sku = this.getById(id);
        if (sku == null) {
            throw new NotFoundException(80002);
        }
        this.getBaseMapper().deleteById(id);
        // 删除 sku_spec
        skuSpecService.deleteSpecs(sku.getSpuId(), sku.getId());
    }

    @Override
    public SkuDetail getDetail(Integer id) {
        return this.getBaseMapper().getDetail(id);
    }

    private List<SpecKeyValue> checkSelectors(List<SkuSelector> selectors) {
        List<SpecKeyValue> specs = new ArrayList<>();
        for (SkuSelector selector : selectors) {
            SpecKeyValue specKeyAndValue = specValueService.
                    getSpecKeyAndValueById(selector.getKeyId(), selector.getValueId());
            if (specKeyAndValue == null) {
                return null;
            }
            specs.add(specKeyAndValue);
        }
        return specs;
    }

    /**
     * 向sku_specs 表中插入数据
     */
    private void insertSpecs(List<SpecKeyValue> specs, Integer spuId, Integer skuId) {
        ArrayList<SkuSpec> skuSpecList = new ArrayList<>();
        specs.forEach(spec -> {
            SkuSpec skuSpec = new SkuSpec();
            skuSpec.setSpuId(spuId);
            skuSpec.setSkuId(skuId);
            skuSpec.setKeyId(spec.getKeyId());
            skuSpec.setValueId(spec.getValueId());
            skuSpecList.add(skuSpec);
        });
        skuSpecService.saveBatch(skuSpecList);
    }

    private String generateSkuCode(List<SkuSelector> selectors, Integer spuId) {
        // 调整：sku的code 调整成$分隔spu和sku，#分隔sku片段
        selectors.sort((o1, o2) -> (int) (o1.getKeyId() - o2.getKeyId()));
        StringBuilder builder = new StringBuilder();
        builder.append(spuId);
        builder.append("$");
        for (int i = 0; i < selectors.size(); i++) {
            SkuSelector skuSelector = selectors.get(i);
            builder.append(skuSelector.getKeyId());
            builder.append("-");
            builder.append(skuSelector.getValueId());
            if (i < selectors.size() - 1) {
                builder.append("#");
            }
        }
        // blob law
        return builder.toString();
    }

}
