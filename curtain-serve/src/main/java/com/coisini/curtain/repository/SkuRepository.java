package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description Sku 仓储
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

    /**
     * 通过id集合查询
     * @param ids
     * @return
     */
    List<Sku> findAllByIdIn(List<Long> ids);

    /**
     * 库存扣减
     * @param sid Sku ID
     * @param quantity 扣减量
     * @return
     */
    @Modifying
    @Query("update Sku s " +
            "set s.stock = s.stock - :quantity " +
            "where s.id = :sid " +
            "and s.stock >= :quantity")
    int reduceStock(Long sid, Long quantity);

    /**
     * 库存返还
     * @param sid Sku ID
     * @param quantity 数量
     * @return
     */
    @Modifying
    @Query("update Sku s set s.stock=s.stock+(:quantity) where s.id = :sid")
    int recoverStock(@Param("sid") Long sid,
                     @Param("quantity") Long quantity);

}
