package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.bo.BannerWithItemsBo;
import com.coisini.curtain.evt.BannerEvt;
import com.coisini.curtain.model.Banner;

public interface BannerService extends IService<Banner> {

    BannerWithItemsBo getWithItems(Integer id);

    void delete(Integer id);

    void update(BannerEvt evt, Integer id);

}
