package com.coisini.curtain.vo;

import com.coisini.curtain.entity.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @Description Category Pure Vo
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class CategoryPureVo {

    private Long id;
    private String name;
    private String isRoot;
    private Long parentId;
    private String img;
    private Long sort;

    public CategoryPureVo(Category category) {
        BeanUtils.copyProperties(category, this);
    }

}
