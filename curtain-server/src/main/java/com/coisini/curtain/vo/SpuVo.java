package com.coisini.curtain.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description Spu Vo
 * @author coisini
 * @date Aug 15, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class SpuVo {

    private Long id;
    private String title;
    private String subtitle;
    private String price;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private String forThemeImg;

}
