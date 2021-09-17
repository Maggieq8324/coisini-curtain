package com.coisini.curtain.controller.v1;

import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.entity.Category;
import com.coisini.curtain.entity.GridCategory;
import com.coisini.curtain.service.CategoryService;
import com.coisini.curtain.service.GridCategoryService;
import com.coisini.curtain.vo.CategoriesAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * @Description category 控制器
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    /**
     * 获取全部分类
     * @return
     */
    @GetMapping("/all")
    public CategoriesAllVo getAll() {
        Map<String, List<Category>> categories = categoryService.getAll();
        return new CategoriesAllVo(categories);
    }

    /**
     * 获取宫格分类
     * @return
     */
    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList() {
        List<GridCategory> gridCategories = gridCategoryService.getGridCategoryList();

        if (gridCategories.isEmpty()) {
            throw new NotFoundException(30009);
        }

        return gridCategories;
    }

}
