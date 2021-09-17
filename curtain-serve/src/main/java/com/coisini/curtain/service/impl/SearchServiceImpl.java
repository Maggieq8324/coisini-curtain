package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.Spu;
import com.coisini.curtain.repository.SpuRepository;
import com.coisini.curtain.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Description Search 实现类
 * @author coisini
 * @date Sep 5, 2021
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Page<Spu> search(String q, Integer page, Integer count) {
        Pageable paging = PageRequest.of(page, count);
        String likeQ = "%" +q + "%";
        return spuRepository.findByTitleLikeOrSubtitleLike(likeQ, likeQ, paging);
    }

}
