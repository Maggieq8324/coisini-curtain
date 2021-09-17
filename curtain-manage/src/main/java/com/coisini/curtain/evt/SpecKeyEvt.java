package com.coisini.curtain.evt;

import io.github.talelin.autoconfigure.validator.Enum;
import com.coisini.curtain.common.enumeration.StandardOrNotEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * @Description SpecKey Evt
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class SpecKeyEvt {

    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @Length(min = 1, max = 255)
    private String description;

    @Length(min = 1, max = 30)
    private String unit;

    @Enum(target = StandardOrNotEnum.class, allowNull = true)
    private Integer standard;

}
