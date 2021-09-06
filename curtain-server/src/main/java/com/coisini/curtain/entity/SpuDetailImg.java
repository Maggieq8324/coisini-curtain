package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * @Description SpuDetailImg模型
 * @author coisini
 * @date Aug 15, 2021
 * @Version 1.0
 */
@Entity
@Getter
@Setter
public class SpuDetailImg extends BaseEntity{

    @Id
    private Long id;
    private String img;
    private Long spuId;
    private Long sort;

}
