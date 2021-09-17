package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.SkuDTO;
import com.coisini.curtain.model.SkuDO;
import com.coisini.curtain.model.SkuDetailDO;

public interface SkuService extends IService<SkuDO> {

    void create(SkuDTO dto);

    void update(SkuDTO dto, Integer id);

    void delete(Integer id);

    SkuDetailDO getDetail(Integer id);

}
