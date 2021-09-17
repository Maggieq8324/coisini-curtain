package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.SpecValueDTO;
import com.coisini.curtain.model.SpecKeyValueDO;
import com.coisini.curtain.model.SpecValueDO;

public interface SpecValueService extends IService<SpecValueDO> {

    void create(SpecValueDTO dto);

    void update(SpecValueDTO dto, Integer id);

    void delete(Integer id);

    SpecKeyValueDO getSpecKeyAndValueById(Integer keyId, Integer valueId);

}
