package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.Banner;
import com.coisini.curtain.repository.BannerRepository;
import com.coisini.curtain.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description Banner 业务层
 * @author coisini
 * @date Aug 11, 2021
 * @Version 1.0
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner getByName(String name) {
        Banner banner = bannerRepository.findONeByName(name);
        return banner;
    }
}
