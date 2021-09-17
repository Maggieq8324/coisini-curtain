package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.SkuEvt;
import com.coisini.curtain.model.Sku;
import com.coisini.curtain.model.SkuDetail;

public interface SkuService extends IService<Sku> {

    void create(SkuEvt evt);

    void update(SkuEvt evt, Integer id);

    void delete(Integer id);

    SkuDetail getDetail(Integer id);

}
