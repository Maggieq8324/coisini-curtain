package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.GridCategory;
import com.coisini.curtain.repository.GridCategoryRepository;
import com.coisini.curtain.service.GridCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description GridCategory 实现类
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
@Service
public class GridCategoryServiceImpl implements GridCategoryService {

    @Autowired
    private GridCategoryRepository gridCategoryRepository;

    @Override
    public List<GridCategory> getGridCategoryList() {
        return gridCategoryRepository.findAll();
    }
}
