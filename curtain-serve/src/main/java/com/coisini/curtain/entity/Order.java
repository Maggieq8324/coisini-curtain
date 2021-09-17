package com.coisini.curtain.entity;

import com.coisini.curtain.core.enumeration.OrderStatus;
import com.coisini.curtain.model.OrderAddressModel;
import com.coisini.curtain.util.CommonUtil;
import com.coisini.curtain.util.GenericAndJson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Description Order 模型
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    /**
     * 过期时间
     */
    private Date expiredTime;
    /**
     * 下单时间
     */
    private Date placedTime;
    private String snapItems;
    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    @JsonIgnore
    public OrderStatus getStatusEnum() {
        return OrderStatus.toType(this.status);
    }

    /**
     * 订单是否应该被取消
     * @return
     */
    public Boolean needCancel() {
        // 状态是否是待支付
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return true;
        }

        // 是否过期
        return CommonUtil.isOutOfDate(this.getExpiredTime());
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems() {
        List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {
                });
        return list;
    }

    public OrderAddressModel getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressModel o = GenericAndJson.jsonToObject(this.snapAddress,
                new TypeReference<OrderAddressModel>() {
                });
        return o;
    }

    public void setSnapAddress(OrderAddressModel address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }

}

