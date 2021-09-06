package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description Category 仓储
 * @author coisini
 * @date Aug 16, 2021
 * @Version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据根节点查询分类
     * @param isRoot 父节点
     * @return  List<Category>
     */
    List<Category> findAllByIsRootOrderBySortAsc(Boolean isRoot);

}
