package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.common.enumeration.CategoryRootOrNotEnum;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.evt.CategoryEvt;
import com.coisini.curtain.mapper.CategoryMapper;
import com.coisini.curtain.model.Category;
import com.coisini.curtain.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public void updateCategory(CategoryEvt evt, Integer id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        BeanUtils.copyProperties(evt, category);
        this.updateById(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        if (category.getIsRoot() == CategoryRootOrNotEnum.ROOT.getValue()) {
            // 查找当前父分类下有无子分类，如有则不能删除
            QueryWrapper<Category> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Category::getParentId, id);
            wrapper.lambda().eq(Category::getIsRoot, CategoryRootOrNotEnum.NOT_ROOT.getValue());
            wrapper.last("limit 1");
            Category subCategory = this.baseMapper.selectOne(wrapper);
            if (subCategory != null) {
                throw new ForbiddenException(40001);
            }
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public Category getCategoryById(Integer id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        return category;
    }

    @Override
    public IPage<Category> getCategoriesByPage(Integer count, Integer page, Integer root) {
        Page<Category> pager = new Page<>(page, count);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getIsRoot, root);
        return this.getBaseMapper().selectPage(pager, wrapper);
    }

    @Override
    public IPage<Category> getSubCategoriesByPage(Integer count, Integer page, Integer id) {
        Page<Category> pager = new Page<>(page, count);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getIsRoot, CategoryRootOrNotEnum.NOT_ROOT.getValue());
        wrapper.lambda().eq(Category::getParentId, id);
        return this.getBaseMapper().selectPage(pager, wrapper);
    }

}
