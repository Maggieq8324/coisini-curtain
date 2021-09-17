package com.coisini.curtain.service;

import com.coisini.curtain.entity.Spu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description Spu 接口
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
public interface SpuService {

    /**
     * 获取spu明细
     * @param id
     * @return
     */
    Spu getDetail(Long id);

    /**
     * 获取Spu分页数据
     * @param pageNum
     * @param size
     * @return
     */
    Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size);

    /**
     * 通过IDs查询
     * @param ids
     * @return
     */
    List<Spu> getSpuListInIds(List<Long> ids);

    /**
     * 通过分类查询spu
     * @param cid
     * @param isRoot
     * @param pageNum
     * @param count
     * @return
     */
    Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer count);

}
