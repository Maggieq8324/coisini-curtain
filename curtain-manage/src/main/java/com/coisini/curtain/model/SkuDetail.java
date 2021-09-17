package com.coisini.curtain.model;

import lombok.Data;

/**
 * @Description SkuDetail
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class SkuDetail extends Sku {

    private Integer spuId;

    private String spuName;

    private String currency;

}
