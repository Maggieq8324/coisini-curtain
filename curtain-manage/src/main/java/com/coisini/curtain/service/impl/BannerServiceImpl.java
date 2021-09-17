package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.bo.BannerWithItemsBo;
import com.coisini.curtain.evt.BannerEvt;
import com.coisini.curtain.mapper.BannerItemMapper;
import com.coisini.curtain.mapper.BannerMapper;
import com.coisini.curtain.model.Banner;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.model.BannerItem;
import com.coisini.curtain.service.BannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private BannerItemMapper bannerItemMapper;

    @Override
    public BannerWithItemsBo getWithItems(Integer id) {
        Banner banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }

//        LambdaQueryWrapper<BannerItemDO> wrapper = new QueryWrapper<BannerItemDO>().lambda();
//        wrapper.eq("banner_id", id);
//        LambdaQueryWrapper<BannerItemDO> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(BannerItemDO::getBannerId, id);
//        List<BannerItemDO> bannerItems = bannerItemMapper.selectList(wrapper);

        List<BannerItem> bannerItems =
                new LambdaQueryChainWrapper<>(bannerItemMapper)
                        .eq(BannerItem::getBannerId, id)
                        .list();

        return new BannerWithItemsBo(banner, bannerItems);
    }

    @Override
    public void delete(Integer id) {
        Banner banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void update(BannerEvt evt, Integer id) {
        Banner banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }
        BeanUtils.copyProperties(evt, banner);
        this.updateById(banner);
    }

}
