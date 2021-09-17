package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.bo.BannerWithItemsBO;
import com.coisini.curtain.dto.BannerDTO;
import com.coisini.curtain.mapper.BannerItemMapper;
import com.coisini.curtain.mapper.BannerMapper;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.model.BannerDO;
import com.coisini.curtain.model.BannerItemDO;
import com.coisini.curtain.service.BannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerDO> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private BannerItemMapper bannerItemMapper;

    @Override
    public BannerWithItemsBO getWithItems(Integer id) {
        BannerDO banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }

//        LambdaQueryWrapper<BannerItemDO> wrapper = new QueryWrapper<BannerItemDO>().lambda();
//        wrapper.eq("banner_id", id);
//        LambdaQueryWrapper<BannerItemDO> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(BannerItemDO::getBannerId, id);
//        List<BannerItemDO> bannerItems = bannerItemMapper.selectList(wrapper);

        List<BannerItemDO> bannerItems =
                new LambdaQueryChainWrapper<>(bannerItemMapper)
                        .eq(BannerItemDO::getBannerId, id)
                        .list();

        return new BannerWithItemsBO(banner, bannerItems);
    }

    @Override
    public void delete(Integer id) {
        BannerDO banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void update(BannerDTO dto, Integer id) {
        BannerDO bannerDO = this.getById(id);
        if (bannerDO == null) {
            throw new NotFoundException(20000);
        }
        BeanUtils.copyProperties(dto, bannerDO);
        this.updateById(bannerDO);
    }

}
