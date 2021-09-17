package com.coisini.curtain.vo;

import com.coisini.curtain.entity.Category;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description Categories Vo
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class CategoriesAllVo {

    private List<CategoryPureVo> roots;
    private List<CategoryPureVo> subs;

    /**
     * 构造函数
     * 2-17-2-4 Java的Stream与Method Reference应用
     * @param map
     */
    public CategoriesAllVo(Map<String, List<Category>> map) {

        this.roots = map.get("roots")
                        .stream()
                        .map(CategoryPureVo::new)
                        .collect(Collectors.toList());

        this.subs = map.get("subs")
                        .stream()
                        .map(CategoryPureVo::new)
                        .collect(Collectors.toList());
    }
}
