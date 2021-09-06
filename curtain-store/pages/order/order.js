// pages/order/order.js

import {Cart} from "../../models/cart";
import {Sku} from "../../models/sku";
import {OrderItem} from "../../models/order-item";
import {Order} from "../../models/order";
import {Coupon} from "../../models/coupon";
import {CouponBO} from "../../models/coupon-bo";
import {CouponOperate, ShoppingWay} from "../../core/enum";
import {showToast} from "../../utils/ui";
import {OrderPost} from "../../models/order-post";
import {Payment} from "../../models/payment";

const cart = new Cart();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    finalTotalPrice: 0, // 最终价格 = 总价 - 折扣价
    totalPrice: 0, // 总价
    discountMoney: 0, // 折扣价
    submitBtnDisable: false, // 提交按钮禁用状态

    address: null,

    currentCouponId: null, // 选中优惠劵
    order: null,
    isOk: true,

    orderFail: false, // 订单失败
    orderFailMsg: '', // 失败消息

    shoppingWay: ShoppingWay.BUY
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {

    let orderItems;
    let localItemCount;
    const shoppingWay = options.way;
    this.data.shoppingWay = shoppingWay;

    if (shoppingWay === ShoppingWay.BUY) {
      // 立即购买
      const skuId = options.sku_id;
      const count = options.count;
      orderItems = await this.getSingleOrderItems(skuId, count);
      localItemCount = 1
    } else {
      // 购物车
      const skuIds = cart.getCheckedSkuIds(); // 获取勾选的skuId
      orderItems = await this.getCartOrderItems(skuIds);
      localItemCount = skuIds.length;
    }

    const order = new Order(orderItems, localItemCount)
    this.data.order = order;

    try {
      // 订单校验
      order.checkOrderIsOk();
    } catch (e) {
      console.error(e)
      this.setData({
        isOk: false
      })
      return;
    }

    // 获取我的优惠劵
    const coupons = await Coupon.getMySelfWithCategory();
    const couponBOList = this.packageCouponBOList(coupons, order);

    this.setData({
      orderItems,
      couponBOList,
      totalPrice: order.getTotalPrice(),
      finalTotalPrice: order.getTotalPrice()
    })
  },
  /**
   * 订单提交按钮点击
   * @param event
   */
  async onSubmit(event) {
    if (!this.data.address) {
      showToast('请选择收获地址');
      return;
    }

    this.disableSubmitBtn();

    const order = this.data.order;
    const orderPost = new OrderPost(
        this.data.totalPrice,
        this.data.finalTotalPrice,
        this.data.currentCouponId,
        order.getOrderSkuInfoList(),
        this.data.address
    );

    /**
     * 下单
     * @type {*}
     */
    const oid = await this.postOrder(orderPost);
    if (!oid) {
      this.enableSubmitBtn();
      return;
    }

    /**
     * 移除购物车选中商品
     */
    if (this.data.shoppingWay === ShoppingWay.CART) {
      cart.removeCheckedItems();
    }

    wx.lin.showLoading({
      type: "flash",
      fullScreen: true,
      color: "#157658"
    })


    /* ################  测试 */
    // wx.redirectTo({
    //   url: `/pages/my-order/my-order?key=${1}`
    // })
    // return;
    wx.redirectTo({
      url: `/pages/pay-success/pay-success?oid=${oid}`
    });
    return;
    /* ################ 跳过支付*/


    /**
     * 微信支付
     * 参数获取
     * wx.requestPayment(params)
     */
    const payParams = await Payment.getPayParams(oid)

    if (!payParams) {
      return;
    }

    try {
      // 拉起微信支付
      const res = await wx.requestPayment(payParams);
      wx.redirectTo({
        url: `/pages/pay-success/pay-success?oid=${oid}`
      })
    } catch (e) {
      // 1 is payStatus
      wx.redirectTo({
        url: `/pages/my-order/my-order?key=${1}`
      })
    }

  },
  /**
   * 订单提交
   * @param orderPost
   * @returns {Promise<*>}
   */
  async postOrder(orderPost) {
    try {
      const serverOrder = await Order.postOrderToServer(orderPost);
      if (serverOrder) {
        return serverOrder.id;
      }
    } catch (e) {
      this.setData({
        orderFail: true,
        orderFailMsg: e.message
      })
    }
  },
  /**
   * 按钮启用
   */
  enableSubmitBtn() {
    this.setData({
      submitBtnDisable: false
    })
  },
  /**
   * 按钮禁用
   */
  disableSubmitBtn() {
    this.setData({
      submitBtnDisable: true
    })
  },
  /**
   * 购买单品
   * @param skuId
   * @param count
   * @returns {Promise<OrderItem[]>}
   */
  async getSingleOrderItems(skuId, count) {
    const skus = await Sku.getSkusByIds(skuId)
    return [new OrderItem(skus[0], count)];
  },
  /**
   * 获取购物车Item数据
   * @param skuIds
   * @returns {Promise<*>}
   */
  async getCartOrderItems(skuIds) {
    // 同步最新的SKU数据
    const skus = await Sku.getSkusByIds(skuIds);
    return this.packageOrderItems(skus)
  },
  /**
   * 组装Order-Item
   */
  packageOrderItems(skus) {
    return skus.map(sku => {
      const count = cart.getSkuCountBySkuId(sku.id);
      const orderItem = new OrderItem(sku, count);
      return orderItem;
    })
  },

  /**
   * 地址选择
   * @param event
   */
  onChooseAddress(event) {
    const address = event.detail.address
    this.data.address = address
  },

  /**
   * 构造CouponBO
   * @param coupons
   * @param order
   * @returns {Uint8Array | BigInt64Array | *[] | Float64Array | Int8Array | Float32Array | Int32Array | Uint32Array | Uint8ClampedArray | BigUint64Array | Int16Array | Uint16Array}
   */
  packageCouponBOList(coupons, order) {
    return coupons.map(coupon => {
      const couponBO = new CouponBO(coupon);
      couponBO.meetCondition(order)
      return couponBO;
    })
  },

  /**
   * 优惠劵选择回调
   * @param event
   */
  onChooseCoupon(event) {
    const couponObj = event.detail.coupon;
    const couponOperate = event.detail.operate;

    if (couponOperate === CouponOperate.PICK) {
      // 优惠劵选中
      this.data.currentCouponId = couponObj.id;
      const priceObj = CouponBO.getFinalPrice(this.data.order.getTotalPrice(), couponObj);
      this.setData({
        finalTotalPrice: priceObj.finalPrice,
        discountMoney: priceObj.discountMoney
      })
    } else {
      // 优惠劵取消
      this.data.currentCouponId = null;
      this.setData({
        finalTotalPrice: this.data.order.getTotalPrice(),
        discountMoney: 0
      })
    }

  },

})