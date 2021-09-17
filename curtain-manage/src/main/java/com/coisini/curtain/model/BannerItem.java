package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description BannerItem
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@TableName("banner_item")
@Getter
@Setter
public class BannerItem extends BaseModel {


    private String name;

    private String img;

    private String keyword;

    private Integer type;

    private Integer bannerId;

}
