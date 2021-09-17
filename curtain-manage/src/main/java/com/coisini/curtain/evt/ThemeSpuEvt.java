package com.coisini.curtain.evt;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Description ThemeSpu Evt
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class ThemeSpuEvt {

    @Positive
    @NotNull
    private Integer themeId;

    @Positive
    @NotNull
    private Integer spuId;

}
