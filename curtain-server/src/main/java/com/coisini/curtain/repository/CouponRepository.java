package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

/**
 * @Description Coupon 仓储
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * 根据二级分类ID查询优惠劵
     * @param cid
     * @param now
     * @return
     */
    @Query("select c from Coupon c " +
            "join c.categoryList ca " +
            "join Activity a on a.id = c.activityId " +
            "where ca.id = :cid " +
            "and a.startTime < :now " +
            "and a.endTime > :now ")
    List<Coupon> findByCategory(Long cid, Date now);

    /**
     * 获取全场优惠劵
     * @param isWholeStore
     * @param now
     * @return
     */
    @Query("select c from Coupon c " +
            "join Activity a on c.activityId = a.id " +
            "where c.wholeStore = :isWholeStore " +
            "and a.startTime < :now " +
            "and a.endTime > :now " )
    List<Coupon> findByWholeStore(Boolean isWholeStore, Date now);

    /**
     * 获取可使用的优惠劵
     * @param uid 用户ID
     * @param now
     * @return
     */
    @Query("select c from Coupon c " +
            "join UserCoupon uc " +
            "on c.id = uc.couponId " +
            "join User u " +
            "on u.id = uc.userId " +
            "where uc.status = 1 " +
            "and u.id = :uid " +
            "and c.startTime < :now " +
            "and c.endTime > :now " +
            "and uc.orderId is null")
    List<Coupon> findMyAvailable(Long uid, Date now);

    /**
     * 获取已使用的优惠劵
     * @param uid
     * @param now
     * @return
     */
    @Query("select c From Coupon c " +
            "join UserCoupon uc " +
            "on c.id = uc.couponId " +
            "join User u " +
            "on u.id = uc.userId " +
            "where u.id = :uid " +
            "and uc.status = 2 " +
            "and uc.orderId is not null " +
            "and c.startTime < :now " +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(@Param("uid") Long uid, @Param("now") Date now);

    /**
     * 获取已过期的优惠劵
     * @param uid
     * @param now
     * @return
     */
    @Query("select c From Coupon c " +
            "join UserCoupon uc " +
            "on c.id = uc.couponId " +
            "join User u " +
            "on u.id = uc.userId " +
            "where u.id = :uid " +
            "and uc.orderId is null " +
            "and uc.status <> 2 " +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(Long uid, Date now);

}
