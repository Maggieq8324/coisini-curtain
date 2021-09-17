package com.coisini.curtain.service;

import com.coisini.curtain.entity.GridCategory;
import java.util.List;

/**
 * @Description GridCategory 接口
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
public interface GridCategoryService {

    /**
     * 获取宫格分类
     * @return
     */
    List<GridCategory> getGridCategoryList();

}
