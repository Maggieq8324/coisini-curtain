package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.BannerItemEvt;
import com.coisini.curtain.model.BannerItem;

public interface BannerItemService extends IService<BannerItem> {

    void update(BannerItemEvt evt, Integer id);

    void delete(Integer id);

}
