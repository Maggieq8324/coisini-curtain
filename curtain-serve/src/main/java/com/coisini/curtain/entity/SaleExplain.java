package com.coisini.curtain.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * @Description Sale Explain 模型
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@Getter
@Setter
@Entity
public class SaleExplain extends BaseEntity{

    @Id
    private int id;
    private Boolean fixed;
    private String text;
    private Integer spuId;
    private Integer index;
    private Integer replaceId;

}
