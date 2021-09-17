package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.BannerItemDTO;
import com.coisini.curtain.model.BannerItemDO;

public interface BannerItemService extends IService<BannerItemDO> {

    void update(BannerItemDTO dto, Integer id);

    void delete(Integer id);

}
