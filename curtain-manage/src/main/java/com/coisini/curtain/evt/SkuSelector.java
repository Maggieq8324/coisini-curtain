package com.coisini.curtain.evt;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Description Sku Selector
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class SkuSelector {

    @Positive
    @NotNull
    private Integer keyId;

    @Positive
    @NotNull
    private Integer valueId;

}
