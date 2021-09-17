package com.coisini.curtain.model;

import lombok.Data;

import java.util.List;

/**
 * @Description SpuDetail
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class SpuDetail extends Spu {

    private String categoryName;

    private String sketchSpecKeyName;

    private String defaultSkuTitle;

    private List<String> spuImgList;

    private List<String> spuDetailImgList;
}

