package com.coisini.curtain.evt;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Description GridCategory Evt
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class GridCategoryEvt {

    @Length(min = 1, max = 100)
    private String title;

    @Length(min = 1, max = 100)
    private String name;

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @NotNull
    @Positive
    private Integer categoryId;

}
