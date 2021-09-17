package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.SpecValueEvt;
import com.coisini.curtain.model.SpecKeyValue;
import com.coisini.curtain.model.SpecValue;

public interface SpecValueService extends IService<SpecValue> {

    void create(SpecValueEvt evt);

    void update(SpecValueEvt evt, Integer id);

    void delete(Integer id);

    SpecKeyValue getSpecKeyAndValueById(Integer keyId, Integer valueId);

}
