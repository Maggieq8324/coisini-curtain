package com.coisini.curtain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.CategoryEvt;
import com.coisini.curtain.model.Category;

public interface CategoryService extends IService<Category> {

    void updateCategory(CategoryEvt evt, Integer id);

    void deleteCategory(Integer id);

    Category getCategoryById(Integer id);

    IPage<Category> getCategoriesByPage(Integer count, Integer page, Integer root);

    IPage<Category> getSubCategoriesByPage(Integer count, Integer page, Integer id);

}
