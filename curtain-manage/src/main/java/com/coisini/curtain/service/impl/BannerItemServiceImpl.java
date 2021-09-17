package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.evt.BannerItemEvt;
import com.coisini.curtain.mapper.BannerItemMapper;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.model.BannerItem;
import com.coisini.curtain.service.BannerItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BannerItemServiceImpl extends ServiceImpl<BannerItemMapper, BannerItem> implements BannerItemService {

    @Override
    public void delete(Integer id) {
        BannerItem bannerItem = this.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void update(BannerItemEvt evt, Integer id) {
        BannerItem bannerItem = this.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        BeanUtils.copyProperties(evt, bannerItem);
        this.updateById(bannerItem);
    }
}
