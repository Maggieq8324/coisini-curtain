package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.Optional;

/**
 * @Description Order 仓储
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 查询未过期的订单
     * expiredTime > now
     * @param now
     * @param status
     * @param uid
     * @param pageable
     * @return
     */
    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now, Integer status, Long uid, Pageable pageable);

    /**
     * 根据用户ID查询订单
     * @param uid
     * @param pageable
     * @return
     */
    Page<Order> findByUserId(Long uid, Pageable pageable);

    /**
     * 根据用户ID 状态查询订单
     * @param uid
     * @param status
     * @param pageable
     * @return
     */
    Page<Order> findByUserIdAndStatus(Long uid, Integer status, Pageable pageable);

    /**
     * 根据用户ID,订单ID查询订单
     * @param uid 用户ID
     * @param oid 订单ID
     * @return
     */
    Optional<Order> findFirstByUserIdAndId(Long uid, Long oid);

    /**
     * 根据订单编号查找
     * @param orderNo 订单编号
     * @return
     */
    Optional<Order> findFirstByOrderNo(String orderNo);

    /**
     * 根据订单编号修改订单状态
     * @param orderNo 订单编号
     * @param status 订单状态
     * @return
     */
    @Modifying
    @Query("update Order o set o.status=:status where o.orderNo=:orderNo")
    int updateStatusByOrderNo(String orderNo, Integer status);

    /**
     * 订单取消
     * @param oid 订单ID
     * @return
     */
    @Modifying
    @Query("update Order o set o.status=5 where o.status = 1 and o.id=:oid")
    int cancelOrder(Long oid);

}
