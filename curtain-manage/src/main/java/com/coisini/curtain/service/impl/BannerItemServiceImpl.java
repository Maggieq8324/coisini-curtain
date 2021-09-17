package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.dto.BannerItemDTO;
import com.coisini.curtain.mapper.BannerItemMapper;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.model.BannerItemDO;
import com.coisini.curtain.service.BannerItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BannerItemServiceImpl extends ServiceImpl<BannerItemMapper, BannerItemDO> implements BannerItemService {

    @Override
    public void delete(Integer id) {
        BannerItemDO bannerItem = this.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void update(BannerItemDTO dto, Integer id) {
        BannerItemDO bannerItemDO = this.getById(id);
        if (bannerItemDO == null) {
            throw new NotFoundException(20001);
        }
        BeanUtils.copyProperties(dto, bannerItemDO);
        this.updateById(bannerItemDO);
    }
}
