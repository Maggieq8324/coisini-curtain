package com.coisini.curtain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author TaleLin
 */
@Data
public class SpecValueDTO {

    @NotBlank
    @Length(min = 1, max = 255)
    private String value;

    @Length(min = 1, max = 255)
    private String extend;

    @Positive
    @NotNull
    private Integer specId;

}
