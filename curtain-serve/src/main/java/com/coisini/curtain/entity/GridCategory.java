package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @Description GridCategory 模型
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class GridCategory extends BaseEntity{

    @Id
    private Long id;
    private String title;
    private String img;
    private String name;
    private Integer categoryId;
    private Integer rootCategoryId;


}
