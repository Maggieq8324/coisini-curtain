package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.Spu;
import com.coisini.curtain.repository.SpuRepository;
import com.coisini.curtain.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description spu 实现类
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Spu getDetail(Long id) {
        return spuRepository.findOneById(id);
    }

    @Override
    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
//        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").ascending());
        return spuRepository.findAll(page);
    }

    @Override
    public List<Spu> getSpuListInIds(List<Long> ids) {
        return spuRepository.findByIdIn(ids);
    }

    @Override
    public Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer count) {
        Pageable page = PageRequest.of(pageNum, count);

        if (isRoot) {
            return spuRepository.findByRootCategoryIdOrderByCreateTime(cid, page);
        } else {
            return spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid, page);
        }
    }
}
