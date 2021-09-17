package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.GridCategoryDTO;
import com.coisini.curtain.model.GridCategoryDO;

public interface GridCategoryService extends IService<GridCategoryDO> {

    void createGridCategory(GridCategoryDTO dto);

    void updateGridCategory(GridCategoryDTO dto, Integer id);

    void deleteGridCategory(Integer id);

}
