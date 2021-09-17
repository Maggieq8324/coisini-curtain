package com.coisini.curtain.repository;

import com.coisini.curtain.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Description UserCoupon 仓储
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    /**
     * 用户ID和优惠劵ID查找
     * @param uid
     * @param couponId
     * @return
     */
    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    /**
     * 用户ID和优惠劵ID和使用状态查找
     *   需添加订单ID是否为空查询
     * @param uid
     * @param couponId
     * @param status
     * @return
     */
    Optional<UserCoupon> findFirstByUserIdAndCouponIdAndStatus(Long uid, Long couponId, int status);

    /**
     * 优惠劵核销
     * @param couponId 优惠劵ID
     * @param oid 订单ID
     * @param uid 用户ID
     * @return
     */
    @Modifying
    @Query("update UserCoupon uc " +
            "set uc.status = 2, uc.orderId = :oid " +
            "where uc.userId = :uid " +
            "and uc.couponId = :couponId " +
            "and uc.status = 1 " +
            "and uc.orderId is null")
    int writeOff(Long couponId, Long oid, Long uid);

    /**
     * 优惠劵归还
     * @param couponId 优惠劵ID
     * @param uid 用户ID
     * @return
     */
    @Modifying
    @Query("update UserCoupon c " +
            "set c.status=1, c.orderId = null " +
            "where c.couponId=:couponId " +
            "and c.userId = :uid " +
            "and c.orderId is not null " +
            "and c.status = 2")
    int returnBack(Long couponId, Long uid);

}
