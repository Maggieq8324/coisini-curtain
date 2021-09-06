package com.coisini.curtain.service;

import com.coisini.curtain.entity.Spu;
import org.springframework.data.domain.Page;

/**
 * @Description Search 接口
 * @author coisini
 * @date Sep 5,, 2021
 * @Version 1.0
 */
public interface SearchService {

    Page<Spu> search(String q, Integer page, Integer count);

}
