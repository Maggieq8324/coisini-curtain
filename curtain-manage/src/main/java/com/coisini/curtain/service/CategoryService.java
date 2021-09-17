package com.coisini.curtain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.CategoryDTO;
import com.coisini.curtain.model.CategoryDO;

public interface CategoryService extends IService<CategoryDO> {

    void updateCategory(CategoryDTO dto, Integer id);

    void deleteCategory(Integer id);

    CategoryDO getCategoryById(Integer id);

    IPage<CategoryDO> getCategoriesByPage(Integer count, Integer page, Integer root);

    IPage<CategoryDO> getSubCategoriesByPage(Integer count, Integer page, Integer id);

}
