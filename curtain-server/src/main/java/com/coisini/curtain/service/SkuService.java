package com.coisini.curtain.service;

import com.coisini.curtain.entity.Sku;

import java.util.List;

/**
 * @Description Sku 接口
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
public interface SkuService {

    /**
     * 通过id集合获取sku
     * @param ids
     * @return
     */
    List<Sku> getSkuListByIds(List<Long> ids);

}
