package com.coisini.curtain.evt;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Description SpecValue Evt
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class SpecValueEvt {

    @NotBlank
    @Length(min = 1, max = 255)
    private String value;

    @Length(min = 1, max = 255)
    private String extend;

    @Positive
    @NotNull
    private Integer specId;

}
