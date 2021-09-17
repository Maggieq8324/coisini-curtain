/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-17 10:31
 */
package com.coisini.curtain.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BannerDTO {

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
