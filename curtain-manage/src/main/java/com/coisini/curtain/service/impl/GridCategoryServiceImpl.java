package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.service.GridCategoryService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.dto.GridCategoryDTO;
import com.coisini.curtain.mapper.CategoryMapper;
import com.coisini.curtain.mapper.GridCategoryMapper;
import com.coisini.curtain.model.CategoryDO;
import com.coisini.curtain.model.GridCategoryDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GridCategoryServiceImpl extends ServiceImpl<GridCategoryMapper, GridCategoryDO> implements GridCategoryService {

    @Value("${sleeve.grid-category-maximum-quantity}")
    private int maximumQuality;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void createGridCategory(GridCategoryDTO dto) {
        Integer count = this.getBaseMapper().selectCount(null);
        if (count >= maximumQuality) {
            throw new ForbiddenException(50001);
        }
        CategoryDO category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null) {
            throw new NotFoundException(40000);
        }
        GridCategoryDO gridCategory = new GridCategoryDO();
        BeanUtils.copyProperties(dto, gridCategory);
        this.save(gridCategory);
    }

    @Override
    public void updateGridCategory(GridCategoryDTO dto, Integer id) {
        GridCategoryDO gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        CategoryDO category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null) {
            throw new NotFoundException(40000);
        }
        setGridCategoryByCondition(dto, gridCategory, category);
        this.updateById(gridCategory);
    }

    @Override
    public void deleteGridCategory(Integer id) {
        GridCategoryDO gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        this.getBaseMapper().deleteById(id);
    }

    private void setGridCategoryByCondition(GridCategoryDTO dto, GridCategoryDO gridCategory, CategoryDO category) {
        // 如果存在 title，赋值 title，否则填充 name
        if (dto.getTitle() == null) {
            gridCategory.setTitle(category.getName());
        } else {
            gridCategory.setTitle(dto.getTitle());
        }
        if (dto.getName() != null) {
            gridCategory.setName(dto.getName());
        } else {
            gridCategory.setName(category.getName());
        }
        gridCategory.setImg(dto.getImg());
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
