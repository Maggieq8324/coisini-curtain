package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.SpuEvt;
import com.coisini.curtain.model.Spu;
import com.coisini.curtain.model.SpuDetail;

import java.util.List;

/**
 * @Description Spu 接口
 * @author coisini
 * @date Sep 8, 2021
 * @Version 1.0
 */
public interface SpuService extends IService<Spu> {

    void create(SpuEvt evt);

    void update(SpuEvt evt, Integer id);

    void delete(Integer id);

    SpuDetail getDetail(Integer id);

    List<Integer> getSpecKeys(Integer id);

}
