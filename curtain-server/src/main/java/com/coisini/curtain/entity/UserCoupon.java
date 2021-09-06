package com.coisini.curtain.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Description UserCoupon 模型
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long userId;
    private Long couponId;
    private Long orderId;
    private Integer status;
    private Date createTime;
}

