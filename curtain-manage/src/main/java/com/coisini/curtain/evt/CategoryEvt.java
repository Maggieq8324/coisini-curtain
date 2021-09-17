package com.coisini.curtain.evt;

import com.coisini.curtain.common.enumeration.OnlineOrNotEnum;
import io.github.talelin.autoconfigure.validator.Enum;
import com.coisini.curtain.common.enumeration.CategoryRootOrNotEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @Description Category Evt
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class CategoryEvt {
    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @Length(min = 1, max = 255)
    private String description;

    @Enum(allowNull = true, target = CategoryRootOrNotEnum.class)
    private Integer isRoot;

    @Positive
    private Integer parentId;

    @Length(min = 1, max = 255)
    private String img;

    @Positive
    private Integer index;

    @Enum(allowNull = true, target = OnlineOrNotEnum.class)
    private Integer online;

    @Positive
    private Integer level;

}
