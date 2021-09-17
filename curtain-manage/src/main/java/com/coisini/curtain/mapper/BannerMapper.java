/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-14 05:14
 */
package com.coisini.curtain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.BannerDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper extends BaseMapper<BannerDO> {
    List<BannerDO> getAllBanners();

    @Select("SELECT * FROM banner")
    List<BannerDO> getAllBanners1();

    long insertBanner(BannerDO bannerDO);
}
