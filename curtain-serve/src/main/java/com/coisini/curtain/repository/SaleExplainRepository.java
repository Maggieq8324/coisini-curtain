package com.coisini.curtain.repository;

import com.coisini.curtain.entity.SaleExplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description Sale Explain 仓储
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@Repository
public interface SaleExplainRepository extends JpaRepository<SaleExplain, Long> {

    /**
     * 根据Fixed查找
     * @param fixed
     * @return
     */
    List<SaleExplain> findAllByFixed(Boolean fixed);

}
