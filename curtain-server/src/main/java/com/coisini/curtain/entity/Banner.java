package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @Description Banner 模型
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Banner extends BaseEntity{

    @Id
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;

    /**
     * @OneToMany 指定一对多关系
     *      关系被维护端
     *      FetchType.LAZY：懒加载，加载一个实体时，定义懒加载的属性不会马上从数据库中加载
     *      FetchType.EAGER：急加载，加载一个实体时，定义急加载的属性会立即从数据库中加载
     * @JoinColumn(name = "bannerId") 指定外键
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;

}
