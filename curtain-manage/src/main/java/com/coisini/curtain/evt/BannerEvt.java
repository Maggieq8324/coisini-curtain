package com.coisini.curtain.evt;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * @Description Banner Evt
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class BannerEvt {

    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    @Length(min = 2, max = 30)
    private String title;

    @Length(min = 2, max = 256)
    private String img;

    @Length(min = 2, max = 256)
    private String description;

}
