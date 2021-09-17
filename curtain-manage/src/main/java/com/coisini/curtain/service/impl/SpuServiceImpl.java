package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.mapper.SpuMapper;
import com.coisini.curtain.model.*;
import com.coisini.curtain.service.*;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.dto.SpuDTO;
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
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuDO> implements SpuService {

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
    public SpuDetailDO getDetail(Integer id) {
        return this.getBaseMapper().getDetail(id);
    }

    @Override
    public List<Integer> getSpecKeys(Integer id) {
        return this.getBaseMapper().getSpecKeys(id);
    }

    /**
     * 插入
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(SpuDTO dto) {
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(dto, spuDO);
        CategoryDO categoryDO = categoryService.getCategoryById(dto.getCategoryId());
        spuDO.setRootCategoryId(categoryDO.getParentId());
        this.save(spuDO);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null) {
            spuImgList.add(dto.getImg());
        } else {
            spuImgList = dto.getSpuImgList();
        }

        this.insertSpuImgList(spuImgList, spuDO.getId());

        if (dto.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(), spuDO.getId());
        }

        if (dto.getSpecKeyIdList() != null) {
            this.insertSpuKeyList(dto.getSpecKeyIdList(), spuDO.getId());
        }
    }

    /**
     * 插入Spu图片
     * @param spuImgList
     * @param spuId
     */
    private void insertSpuImgList(List<String> spuImgList, Integer spuId) {
        List<SpuImgDO> spuImgDOList = spuImgList.stream().map(s -> {
            SpuImgDO spuImgDO = new SpuImgDO();
            spuImgDO.setImg(s);
            spuImgDO.setSpuId(spuId);
            return spuImgDO;
        }).collect(Collectors.toList());
        this.spuImgService.saveBatch(spuImgDOList);
    }

    /**
     * 插入Spu 详情图
     * @param spuDetailImgList 详情图
     * @param spuId Spu ID
     */
    private void insertSpuDetailImgList(List<String> spuDetailImgList, Integer spuId) {
        List<SpuDetailImgDO> spuDetailImgDOList = new ArrayList<>();
        for (int i = 0; i < spuDetailImgList.size(); i++) {
            SpuDetailImgDO spuDetailImgDO = new SpuDetailImgDO();
            spuDetailImgDO.setImg(spuDetailImgList.get(i))
                    .setSpuId(spuId)
                    .setSort(i);
            spuDetailImgDOList.add(spuDetailImgDO);
        }
        this.spuDetailImgService.saveBatch(spuDetailImgDOList);
    }

    private void insertSpuKeyList(List<Integer> spuKeyIdList, Integer spuId) {
        List<SpuKeyDO> spuKeyDOList = spuKeyIdList.stream()
                .map(sk -> {
                    SpuKeyDO spuKeyDO = new SpuKeyDO();
                    spuKeyDO.setSpuId(spuId)
                            .setSpecKeyId(sk);
                    return spuKeyDO;
                }).collect(Collectors.toList());
        this.spuKeyService.saveBatch(spuKeyDOList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SpuDTO dto, Integer id) {
        SpuDO spuDO = this.getById(id);
        if (spuDO == null) {
            throw new NotFoundException(70000);
        }
        BeanUtils.copyProperties(dto, spuDO);
        CategoryDO category = categoryService.getCategoryById(dto.getCategoryId());
        if (category.getParentId() != null) {
            spuDO.setRootCategoryId(category.getParentId());
        }
        this.updateById(spuDO);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null) {
            spuImgList.add(dto.getImg());
        } else {
            spuImgList = dto.getSpuImgList();
        }
        spuImgService.hardDeleteImgsBySpuId(spuDO.getId());
        spuDetailImgService.hardDeleteImgsBySpuId(spuDO.getId());
        this.insertSpuImgList(spuImgList, spuDO.getId());
        if (dto.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(), spuDO.getId());
        }
        this.updateSpuKey(spuDO.getId(), dto.getSpecKeyIdList());
    }

    @Override
    public void delete(Integer id) {
        SpuDO exist = this.getById(id);
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
        QueryWrapper<SpuKeyDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpuKeyDO::getSpuId, spuId);
        List<SpuKeyDO> exists = spuKeyService.getBaseMapper().selectList(wrapper);
        List<Integer> existsIds = new ArrayList<>();
        List<SpuKeyDO> newSpuKeyList = new ArrayList<>();
        for (SpuKeyDO exist: exists) {
            existsIds.add(exist.getId());
        }
        for (Integer specKeyId: newSpecKeyIdList) {
            SpuKeyDO spuKeyDO = new SpuKeyDO();
            spuKeyDO.setSpecKeyId(specKeyId);
            spuKeyDO.setSpuId(spuId);
            newSpuKeyList.add(spuKeyDO);
        }
        spuKeyService.removeByIds(existsIds);
        spuKeyService.saveBatch(newSpuKeyList);
    }

}
