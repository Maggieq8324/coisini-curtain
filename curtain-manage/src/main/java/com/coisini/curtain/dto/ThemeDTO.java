package com.coisini.curtain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author TaleLin
 */
@Data
public class ThemeDTO {

    @NotBlank
    @Length(min = 1, max = 60)
    private String title;

    @Length(min = 1, max = 255)
    private String description;

    @NotBlank
    @Length(min = 1, max = 30)
    private String name;

    @Length(min = 1, max = 30)
    private String tplName;

    @Length(min = 1, max = 255)
    private String entranceImg;

    @Length(min = 1, max = 30)
    private String extend;

    @Length(min = 1, max = 255)
    private String internalTopImg;


    @Length(min = 1, max = 255)
    private String titleImg;

}
