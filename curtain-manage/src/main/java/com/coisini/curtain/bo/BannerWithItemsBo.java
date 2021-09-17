package com.coisini.curtain.bo;

import com.coisini.curtain.model.Banner;
import com.coisini.curtain.model.BannerItem;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * @Description BannerWithItems Business Object
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class BannerWithItemsBo {

    private Integer id;

    private String name;

    private String title;

    private String img;

    private String description;

    List<BannerItem> items;

    public BannerWithItemsBo(Banner banner, List<BannerItem> items) {
        BeanUtils.copyProperties(banner, this);
        this.setItems(items);
    }
}
