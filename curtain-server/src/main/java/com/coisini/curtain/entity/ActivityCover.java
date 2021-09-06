package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.List;

/**
 * @Description ActivityCover 模型
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
//@Entity
@Getter
@Setter
@Where(clause = "delete_time is null and online = 1")
public class ActivityCover extends BaseEntity {
    @Id
    private Long id;
    private String coverImg;
    private String internalTopImg;
    private String name;
    private String title;
    private String description;
    private Boolean online;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="activityCoverId")
    private  List<Activity> activityList;
}

