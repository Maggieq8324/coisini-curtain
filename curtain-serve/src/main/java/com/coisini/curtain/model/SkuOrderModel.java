package com.coisini.curtain.model;

import com.coisini.curtain.model.SkuInfoModel;
import com.coisini.curtain.entity.Sku;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * @Description Sku Order Business Object
 * @author coisini
 * @date Aug 22, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class SkuOrderModel {

    private BigDecimal actualPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderModel(Sku sku, SkuInfoModel skuInfoModel) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoModel.getCount();
        this.categoryId = sku.getCategoryId();
    }

    /**
     * 获取总价 = 真实价格 * 数量
     * @return
     */
    public BigDecimal getTotalPrice() {
        return actualPrice.multiply(new BigDecimal(this.count));
    }


}
