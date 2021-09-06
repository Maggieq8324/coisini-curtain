package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Description Activity 模型
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Entity
@Setter
@Getter
@Where(clause = "delete_time is null and online = 1")
public class Activity extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String name;
    private String description;
    //    private Long activityCoverId;
    private Date startTime;
    private Date endTime;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private String remark;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "activity_category",
//            joinColumns = @JoinColumn(name = "activity_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id"))
//    private List<Category> categoryList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="activityId")
    private List<Coupon> couponList;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "activity_coupon",
//            joinColumns = @JoinColumn(name = "activity_id"),
//            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
//    private List<Coupon> couponList;
}
