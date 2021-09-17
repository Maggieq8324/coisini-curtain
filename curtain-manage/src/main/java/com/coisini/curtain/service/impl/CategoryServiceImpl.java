package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.common.enumeration.CategoryRootOrNotEnum;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.dto.CategoryDTO;
import com.coisini.curtain.mapper.CategoryMapper;
import com.coisini.curtain.model.CategoryDO;
import com.coisini.curtain.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    @Override
    public void updateCategory(CategoryDTO dto, Integer id) {
        CategoryDO category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        BeanUtils.copyProperties(dto, category);
        this.updateById(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        CategoryDO category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        if (category.getIsRoot() == CategoryRootOrNotEnum.ROOT.getValue()) {
            // 查找当前父分类下有无子分类，如有则不能删除
            QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(CategoryDO::getParentId, id);
            wrapper.lambda().eq(CategoryDO::getIsRoot, CategoryRootOrNotEnum.NOT_ROOT.getValue());
            wrapper.last("limit 1");
            CategoryDO subCategory = this.baseMapper.selectOne(wrapper);
            if (subCategory != null) {
                throw new ForbiddenException(40001);
            }
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public CategoryDO getCategoryById(Integer id) {
        CategoryDO categoryDO = this.getById(id);
        if (categoryDO == null) {
            throw new NotFoundException(40000);
        }
        return categoryDO;
    }

    @Override
    public IPage<CategoryDO> getCategoriesByPage(Integer count, Integer page, Integer root) {
        Page<CategoryDO> pager = new Page<>(page, count);
        QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CategoryDO::getIsRoot, root);
        return this.getBaseMapper().selectPage(pager, wrapper);
    }

    @Override
    public IPage<CategoryDO> getSubCategoriesByPage(Integer count, Integer page, Integer id) {
        Page<CategoryDO> pager = new Page<>(page, count);
        QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CategoryDO::getIsRoot, CategoryRootOrNotEnum.NOT_ROOT.getValue());
        wrapper.lambda().eq(CategoryDO::getParentId, id);
        return this.getBaseMapper().selectPage(pager, wrapper);
    }

}
