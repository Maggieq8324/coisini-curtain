package com.coisini.curtain.logic;

import com.coisini.curtain.model.SkuOrderModel;
import com.coisini.curtain.core.enumeration.CouponType;
import com.coisini.curtain.core.money.IMoneyDiscount;
import com.coisini.curtain.exception.http.ForbiddenException;
import com.coisini.curtain.exception.http.ParameterException;
import com.coisini.curtain.entity.Category;
import com.coisini.curtain.entity.Coupon;
import com.coisini.curtain.util.CommonUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description Coupon 校验
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
public class CouponChecker {

    private Coupon coupon;

    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    /**
     * 优惠劵是否可用
     */
    public void isOk() {
        /**
         * 是否过期
         */
        Boolean isInTimeLine = CommonUtil.isInTimeLine(new Date(), coupon.getStartTime(), coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    /**
     * 最终价格检测
     * @param orderFinalTotalPrice 前端计算最终价格
     * @param serverTotalPrice 后端计算原始价格
     */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,
                                    BigDecimal serverTotalPrice) {

        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(coupon.getType())) {
            // 满减劵计算
            case FULL_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(coupon.getMinus());
                int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);

                if (compare != 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            // 满减折扣券计算
            case FULL_OFF:
                serverFinalTotalPrice = iMoneyDiscount.discount(serverTotalPrice, coupon.getRate());
                break;
            // 无门槛减除券计算
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(coupon.getMinus());
                break;
            default:
                throw new ParameterException(40009);
        }

        /**
         * 最终计算价格比较
         */
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    /**
     * 是否可使用检测
     * @param skuOrderModelList sku-order 集合
     * @param serverTotalPrice 服务端计算价格
     */
    public void canBeUsed(List<SkuOrderModel> skuOrderModelList, BigDecimal serverTotalPrice) {
        /**
         * 全场劵的计算
         */
        // 订单分类价格
        BigDecimal orderCategoryPrice;

        if(this.coupon.getWholeStore()){
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cidList = coupon.getCategoryList()
                                        .stream()
                                        .map(Category::getId)
                                        .collect(Collectors.toList());

            orderCategoryPrice = this.getSumByCategoryList(skuOrderModelList, cidList);
        }

        this.couponCanBeUsed(orderCategoryPrice);
    }

    /**
     * 优惠劵是否能使用
     * @param orderCategoryPrice 订单分类总价格
     */
    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(coupon.getType())) {
            // 满减折扣券
            case FULL_OFF:
                break;
            // 满减券
            case FULL_MINUS:
                // getFullMoney 优惠劵使用门槛
                int compare = coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            // 无门槛减除券
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    /**
     * 获取订单-分类 sku 价格总和
     * @param skuOrderModelList sku-order 集合
     * @param cidList 分类ID
     * @return 当前订单价格总和
     */
    public BigDecimal getSumByCategoryList(List<SkuOrderModel> skuOrderModelList, List<Long> cidList) {
        return cidList.stream()
                      .map(cid -> this.getSumByCategory(skuOrderModelList, cid))
                      .reduce(BigDecimal::add)
                      .orElse(new BigDecimal("0"));
    }

    /**
     * 获取订单-分类 sku 价格总和
     * @param skuOrderModelList sku-order 集合
     * @param cid 分类ID
     * @return 当前分类价格总和
     */
    private BigDecimal getSumByCategory(List<SkuOrderModel> skuOrderModelList, Long cid) {
        BigDecimal sum = skuOrderModelList.stream()
                                        .filter(sku -> sku.getCategoryId().equals(cid))
                                        .map(SkuOrderModel::getTotalPrice)
                                        .reduce(BigDecimal::add)
                                        .orElse(new BigDecimal("0"));

        return sum;
    }

}
