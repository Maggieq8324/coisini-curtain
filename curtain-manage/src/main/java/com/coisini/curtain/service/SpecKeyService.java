package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.bo.SpecKeyAndItemsBo;
import com.coisini.curtain.evt.SpecKeyEvt;
import com.coisini.curtain.model.SpecKey;

import java.util.List;

public interface SpecKeyService extends IService<SpecKey> {

    void create(SpecKeyEvt evt);

    void update(SpecKeyEvt evt, Integer id);

    void delete(Integer id);

    SpecKeyAndItemsBo getKeyAndValuesById(Integer id);

    List<SpecKey> getBySpuId(Integer spuId);

}
