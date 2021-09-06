package com.coisini.curtain.vo;

import com.coisini.curtain.entity.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description Coupon Pure Vo
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponPureVo {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVo(Object[] objects){
        Coupon coupon = (Coupon) objects[0];
        BeanUtils.copyProperties(coupon, this);
    }

    public CouponPureVo(Coupon coupon){
        BeanUtils.copyProperties(coupon, this);
    }

    public static List<CouponPureVo> getList(List<Coupon> couponList) {
        return couponList.stream()
                .map(CouponPureVo::new)
                .collect(Collectors.toList());
    }
}
