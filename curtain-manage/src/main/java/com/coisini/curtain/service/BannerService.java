package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.bo.BannerWithItemsBO;
import com.coisini.curtain.dto.BannerDTO;
import com.coisini.curtain.model.BannerDO;

public interface BannerService extends IService<BannerDO> {

    BannerWithItemsBO getWithItems(Integer id);

    void delete(Integer id);

    void update(BannerDTO dto, Integer id);

}
