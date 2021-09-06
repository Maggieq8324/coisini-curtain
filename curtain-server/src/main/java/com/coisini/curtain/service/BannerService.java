package com.coisini.curtain.service;

import com.coisini.curtain.entity.Banner;

/**
 * @Description Banner 接口
 * @author coisini
 * @date
 * @Version 1.0
 */
public interface BannerService {

    /**
     * 通过Name查找
     * @param name
     * @return
     */
    Banner getByName(String name);

}
