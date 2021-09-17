package com.coisini.curtain.logic;

import com.coisini.curtain.model.SkuOrderModel;
import com.coisini.curtain.model.OrderModel;
import com.coisini.curtain.model.SkuInfoModel;
import com.coisini.curtain.exception.http.ParameterException;
import com.coisini.curtain.entity.OrderSku;
import com.coisini.curtain.entity.Sku;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description Order 校验
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
public class OrderChecker {

    private OrderModel orderModel;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;

    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();

    /**
     *
     * @param orderModel 前端提交订单数据
     * @param serverSkuList 后端读取skuList
     * @param couponChecker 优惠劵校验
     * @param maxSkuLimit 最大购买数量
     */
    public OrderChecker(OrderModel orderModel, List<Sku> serverSkuList,
                        CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderModel = orderModel;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    /**
     * 获取订单主图片
     * @return
     */
    public String getLeaderImg() {
        return serverSkuList.get(0).getImg();
    }

    /**
     * 获取订单主标题
     * @return
     */
    public String getLeaderTitle() {
        return serverSkuList.get(0).getTitle();
    }

    /**
     * 获取订单总价
     * @return
     */
    public Integer getTotalCount() {
        return orderModel.getSkuInfoList().stream()
                                        .map(SkuInfoModel::getCount)
                                        .reduce(Integer::sum)
                                        .orElse(0);
    }

    /**
     * 订单校验
     */
    public void isOk() {

        // 服务端总价格
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderModel> skuOrderModelList = new ArrayList<>();

        /**
         * 下架sku判断
         */
        this.skuNotOnSale(orderModel.getSkuInfoList().size(), this.serverSkuList.size());

        for (int i = 0; i < this.serverSkuList.size(); i++) {
            // 后端Sku
            Sku sku = serverSkuList.get(i);
            // 前端Sku
            SkuInfoModel skuInfoModel = orderModel.getSkuInfoList().get(i);

            // Sku是否售罄
            this.containsSoldOutSku(sku);
            // Sku是否超卖
            this.beyondSkuStock(sku, skuInfoModel);
            // 最大购买数量校验
            this.beyondMaxSkuLimit(skuInfoModel);

            serverTotalPrice = serverTotalPrice.add(calculateSkuOrderPrice(sku, skuInfoModel));
            skuOrderModelList.add(new SkuOrderModel(sku, skuInfoModel));
            this.orderSkuList.add(new OrderSku(sku, skuInfoModel));
        }

        // 总价格校验
        this.totalPriceIsOk(orderModel.getTotalPrice(), serverTotalPrice);

        /**
         * 优惠劵校验
         */
        if (Objects.nonNull(couponChecker)) {
            // 优惠劵是否可用
            couponChecker.isOk();
            // 是否可使用检测
            couponChecker.canBeUsed(skuOrderModelList, serverTotalPrice);
            // 最终价格检测
            couponChecker.finalTotalPriceIsOk(orderModel.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    /**
     * 总价格校验
     * @param orderTotalPrice 前端总原价
     * @param serverTotalPrice 服务端计算总原价
     */
    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    /**
     * Sku总价格计算
     * @param sku
     * @param skuInfoModel
     * @return
     */
    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoModel skuInfoModel) {
        if (skuInfoModel.getCount() <= 0) {
            throw new ParameterException(50007);
        }

        return sku.getActualPrice().multiply(new BigDecimal(skuInfoModel.getCount()));
    }

    /**
     * Sku销售检验
     * 前后端sku数量校验
     * @param count1 前端Sku数量
     * @param count2 后端Sku数量
     */
    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    /**
     * Sku是否售罄
     * @param sku
     */
    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    /**
     * Sku是否超卖
     * @param sku
     * @param skuInfoModel
     */
    private void beyondSkuStock(Sku sku, SkuInfoModel skuInfoModel) {
        /**
         * 库存量 < 购买数量
         */
        if (sku.getStock() < skuInfoModel.getCount()) {
            throw new ParameterException(50003);
        }
    }

    /**
     * 最大购买数量校验
     * @param skuInfoModel
     */
    private void beyondMaxSkuLimit(SkuInfoModel skuInfoModel) {
        if (skuInfoModel.getCount() > maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }

}
