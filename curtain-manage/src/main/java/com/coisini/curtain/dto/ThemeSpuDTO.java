package com.coisini.curtain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author TaleLin
 */
@Data
public class ThemeSpuDTO {

    @Positive
    @NotNull
    private Integer themeId;

    @Positive
    @NotNull
    private Integer spuId;

}
