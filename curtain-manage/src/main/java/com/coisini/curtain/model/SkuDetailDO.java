package com.coisini.curtain.model;


import lombok.Data;

/**
 * @author TaleLin
 */
@Data
public class SkuDetailDO extends SkuDO {

    private Integer spuId;

    private String spuName;

    private String currency;

}
