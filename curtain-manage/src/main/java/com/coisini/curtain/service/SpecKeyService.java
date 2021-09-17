package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.bo.SpecKeyAndItemsBO;
import com.coisini.curtain.dto.SpecKeyDTO;
import com.coisini.curtain.model.SpecKeyDO;

import java.util.List;

public interface SpecKeyService extends IService<SpecKeyDO> {

    void create(SpecKeyDTO dto);

    void update(SpecKeyDTO dto, Integer id);

    void delete(Integer id);

    SpecKeyAndItemsBO getKeyAndValuesById(Integer id);

    List<SpecKeyDO> getBySpuId(Integer spuId);

}
