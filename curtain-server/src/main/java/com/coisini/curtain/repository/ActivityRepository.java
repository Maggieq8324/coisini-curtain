package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Description Activity 仓储
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * 名称查询
     * @param name
     * @return
     */
    Activity findByName(String name);

    /**
     * 通过优惠劵ID查询
     * @param couponId 优惠劵ID
     * @return
     */
    Optional<Activity> findByCouponListId(Long couponId);

}
