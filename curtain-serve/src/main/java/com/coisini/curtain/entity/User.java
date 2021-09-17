package com.coisini.curtain.entity;

import com.coisini.curtain.util.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.Map;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Description User 模型
 * @author coisini
 * @date Aug 18, 2021
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Where(clause = "delete_time is null")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String openid;

    private String nickname;

    private String email;

    private String mobile;

    private String password;

    private Long unifyUid;

//    private String group;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "UserCoupon",
//            joinColumns = @JoinColumn(name = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "couponId"))
//    private List<Coupon> couponList;

    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "user", fetch = FetchType.LAZY)

//    @OneToMany
//    @JoinColumn(name="userId")
//    private List<Order> orders = new ArrayList<>();

}

