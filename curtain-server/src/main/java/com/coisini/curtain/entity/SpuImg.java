package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * @Description SpuImg 模型
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Entity
@Getter
@Setter
public class SpuImg extends BaseEntity{

    @Id
    private Long id;
    private String img;
    private Long spuId;

}
