package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.GridCategoryEvt;
import com.coisini.curtain.model.GridCategory;

public interface GridCategoryService extends IService<GridCategory> {

    void createGridCategory(GridCategoryEvt evt);

    void updateGridCategory(GridCategoryEvt evt, Integer id);

    void deleteGridCategory(Integer id);

}
