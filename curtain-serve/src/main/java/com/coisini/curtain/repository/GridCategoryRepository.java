package com.coisini.curtain.repository;

import com.coisini.curtain.entity.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description GridCategory 仓储
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
@Repository
public interface GridCategoryRepository extends JpaRepository<GridCategory, Long> {

}
