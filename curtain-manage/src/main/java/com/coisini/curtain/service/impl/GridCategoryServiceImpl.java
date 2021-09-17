package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.service.GridCategoryService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.evt.GridCategoryEvt;
import com.coisini.curtain.mapper.CategoryMapper;
import com.coisini.curtain.mapper.GridCategoryMapper;
import com.coisini.curtain.model.Category;
import com.coisini.curtain.model.GridCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GridCategoryServiceImpl extends ServiceImpl<GridCategoryMapper, GridCategory> implements GridCategoryService {

    @Value("${curtain.grid-category-maximum-quantity}")
    private int maximumQuality;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void createGridCategory(GridCategoryEvt evt) {
        Integer count = this.getBaseMapper().selectCount(null);
        if (count >= maximumQuality) {
            throw new ForbiddenException(50001);
        }
        Category category = categoryMapper.selectById(evt.getCategoryId());
        if (category == null) {
            throw new NotFoundException(40000);
        }
        GridCategory gridCategory = new GridCategory();
        BeanUtils.copyProperties(evt, gridCategory);
        this.save(gridCategory);
    }

    @Override
    public void updateGridCategory(GridCategoryEvt evt, Integer id) {
        GridCategory gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        Category category = categoryMapper.selectById(evt.getCategoryId());
        if (category == null) {
            throw new NotFoundException(40000);
        }
        setGridCategoryByCondition(evt, gridCategory, category);
        this.updateById(gridCategory);
    }

    @Override
    public void deleteGridCategory(Integer id) {
        GridCategory gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        this.getBaseMapper().deleteById(id);
    }

    private void setGridCategoryByCondition(GridCategoryEvt evt, GridCategory gridCategory, Category category) {
        // 如果存在 title，赋值 title，否则填充 name
        if (evt.getTitle() == null) {
            gridCategory.setTitle(category.getName());
        } else {
            gridCategory.setTitle(evt.getTitle());
        }
        if (evt.getName() != null) {
            gridCategory.setName(evt.getName());
        } else {
            gridCategory.setName(category.getName());
        }
        gridCategory.setImg(evt.getImg());
        // 如果当前绑定的分类无父分类，则绑定到rootCategoryId
        // 否则绑定父分类绑定到rootCategoryId，当前id绑定到categoryId
        if (category.getParentId() == null) {
            gridCategory.setRootCategoryId(category.getId());
        } else {
            gridCategory.setRootCategoryId(category.getParentId());
            gridCategory.setCategoryId(category.getId());
        }
    }
}
