package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.mapper.SpuMapper;
import com.coisini.curtain.model.*;
import com.coisini.curtain.service.*;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.evt.SpuEvt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description Spu 实现类
 * @author coisini
 * @date Sep 8, 2021
 * @Version 1.0
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuImgService spuImgService;

    @Autowired
    private SpuDetailImgService spuDetailImgService;

    @Autowired
    private SpuKeyService spuKeyService;

    /**
     * 获取明细
     * @param id
     * @return
     */
    @Override
    public SpuDetail getDetail(Integer id) {
        return this.getBaseMapper().getDetail(id);
    }

    @Override
    public List<Integer> getSpecKeys(Integer id) {
        return this.getBaseMapper().getSpecKeys(id);
    }

    /**
     * 插入
     * @param evt
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(SpuEvt evt) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(evt, spu);
        Category category = categoryService.getCategoryById(evt.getCategoryId());
        spu.setRootCategoryId(category.getParentId());
        this.save(spu);

        List<String> spuImgList = new ArrayList<>();
        if (evt.getSpuImgList() == null) {
            spuImgList.add(evt.getImg());
        } else {
            spuImgList = evt.getSpuImgList();
        }

        this.insertSpuImgList(spuImgList, spu.getId());

        if (evt.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(evt.getSpuDetailImgList(), spu.getId());
        }

        if (evt.getSpecKeyIdList() != null) {
            this.insertSpuKeyList(evt.getSpecKeyIdList(), spu.getId());
        }
    }

    /**
     * 插入Spu图片
     * @param spuImgList
     * @param spuId
     */
    private void insertSpuImgList(List<String> spuImgList, Integer spuId) {
        List<SpuImg> spuImgDOList = spuImgList.stream().map(s -> {
            SpuImg spuImg = new SpuImg();
            spuImg.setImg(s);
            spuImg.setSpuId(spuId);
            return spuImg;
        }).collect(Collectors.toList());
        this.spuImgService.saveBatch(spuImgDOList);
    }

    /**
     * 插入Spu 详情图
     * @param spuDetailImgList 详情图
     * @param spuId Spu ID
     */
    private void insertSpuDetailImgList(List<String> spuDetailImgList, Integer spuId) {
        List<SpuDetailImg> spuDetailImgDOList = new ArrayList<>();
        for (int i = 0; i < spuDetailImgList.size(); i++) {
            SpuDetailImg spuDetailImg = new SpuDetailImg();
            spuDetailImg.setImg(spuDetailImgList.get(i))
                    .setSpuId(spuId)
                    .setSort(i);
            spuDetailImgDOList.add(spuDetailImg);
        }
        this.spuDetailImgService.saveBatch(spuDetailImgDOList);
    }

    private void insertSpuKeyList(List<Integer> spuKeyIdList, Integer spuId) {
        List<SpuKey> spuKeyList = spuKeyIdList.stream()
                .map(sk -> {
                    SpuKey spuKey = new SpuKey();
                    spuKey.setSpuId(spuId)
                            .setSpecKeyId(sk);
                    return spuKey;
                }).collect(Collectors.toList());
        this.spuKeyService.saveBatch(spuKeyList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SpuEvt evt, Integer id) {
        Spu spu = this.getById(id);
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        BeanUtils.copyProperties(evt, spu);
        Category category = categoryService.getCategoryById(evt.getCategoryId());
        if (category.getParentId() != null) {
            spu.setRootCategoryId(category.getParentId());
        }
        this.updateById(spu);

        List<String> spuImgList = new ArrayList<>();
        if (evt.getSpuImgList() == null) {
            spuImgList.add(evt.getImg());
        } else {
            spuImgList = evt.getSpuImgList();
        }
        spuImgService.hardDeleteImgsBySpuId(spu.getId());
        spuDetailImgService.hardDeleteImgsBySpuId(spu.getId());
        this.insertSpuImgList(spuImgList, spu.getId());
        if (evt.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(evt.getSpuDetailImgList(), spu.getId());
        }
        this.updateSpuKey(spu.getId(), evt.getSpecKeyIdList());
    }

    @Override
    public void delete(Integer id) {
        Spu exist = this.getById(id);
        if (exist == null) {
            throw new NotFoundException(70000);
        }
        this.getBaseMapper().deleteById(id);
    }

    /**
     * 更新spu_key表
     * @param spuId spu id
     * @param newSpecKeyIdList 前端传递过来的 spu_key id列表
     */
    private void updateSpuKey(Integer spuId, List<Integer> newSpecKeyIdList) {
        QueryWrapper<SpuKey> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpuKey::getSpuId, spuId);
        List<SpuKey> exists = spuKeyService.getBaseMapper().selectList(wrapper);
        List<Integer> existsIds = new ArrayList<>();
        List<SpuKey> newSpuKeyList = new ArrayList<>();
        for (SpuKey exist: exists) {
            existsIds.add(exist.getId());
        }
        for (Integer specKeyId: newSpecKeyIdList) {
            SpuKey spuKey = new SpuKey();
            spuKey.setSpecKeyId(specKeyId);
            spuKey.setSpuId(spuId);
            newSpuKeyList.add(spuKey);
        }
        spuKeyService.removeByIds(existsIds);
        spuKeyService.saveBatch(newSpuKeyList);
    }

}
