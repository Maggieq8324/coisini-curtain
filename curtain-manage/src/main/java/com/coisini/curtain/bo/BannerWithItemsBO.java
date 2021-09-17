/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-19 04:05
 */
package com.coisini.curtain.bo;

import com.coisini.curtain.model.BannerDO;
import com.coisini.curtain.model.BannerItemDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class BannerWithItemsBO {

    private Integer id;

    private String name;

    private String title;

    private String img;

    private String description;

    List<BannerItemDO> items;

    public BannerWithItemsBO(BannerDO banner, List<BannerItemDO> items) {
        BeanUtils.copyProperties(banner, this);
        this.setItems(items);
    }
}
