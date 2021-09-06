// pages/cart/cart.js

import {Cart} from "../../models/cart";
import {Calculator} from "../../models/calculator";
import {accAddition, accSubtract, accMultiply, accDivision} from "../../utils/number";
import {ShoppingWay} from "../../core/enum";

const cart = new Cart();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    cartItems: [],
    isEmpty: false,
    allChecked: false, // 全选
    totalPrice: 0, // 总价格
    totalSkuCount: 0 // 总数量
  },

  /**
   * 生命周期函数--监听页面加载
   * 小程序第一次加载时执行
   * tabBar切换时不会执行
   */
  onLoad: async function(options) {
    // 获取服务端sku更新购物车数据
    const cartData = await cart.getAllSkuFromServer()
    if(cartData){
      this.setData({
        cartItems: cartData.items
      })
    }
  },
  /**
   * 生命周期函数--监听页面显示
   * tabBar切换时会执行
   */
  onShow: function () {
    /**
     * 刷新购物车数据
     */
    const cart = new Cart();
    const cartItems = cart.getAllCartItemFromLocal().items;

    if (cart.isEmpty()) {
      this.empty();
      return;
    }

    this.setData({
      cartItems: cartItems
    })

    this.notEmpty(); // 非空
    this.isAllChecked(); // 全选
    this.refreshCartData(); // 刷新购物车数据
  },
  /**
   * 刷新购物车数据
   */
  refreshCartData() {
    const checkedItems = cart.getCheckedItems();
    const calculator = new Calculator(checkedItems);
    calculator.calc();
    this.setCalcData(calculator);
  },
  /**
   * 数量选择事件回调
   * @param event
   */
  onCountFloat(event) {
    this.refreshCartData();
  },
  /**
   * 设置计算后的价格
   * @param calculator
   */
  setCalcData(calculator) {
    const totalPrice = calculator.getTotalPrice()
    const totalSkuCount = calculator.getTotalSkuCount()
    this.setData({
      totalPrice,
      totalSkuCount
    })
  },
  /**
   * 是否全选
   */
  isAllChecked() {
    const allChecked = cart.isAllChecked()
    this.setData({
      allChecked
    })
  },
  /**
   * Item 选择事件回调
   * @param event
   */
  onSingleCheck(event) {
    this.isAllChecked()
    this.refreshCartData();
  },
  /**
   * Item删除事件
   * @param event
   */
  onDeleteItem(event) {
    this.isAllChecked()
    this.refreshCartData();
  },
  /**
   * 全选
   * @param event
   */
  onCheckAll(event) {
    const checked = event.detail.checked
    cart.checkAll(checked)
    this.setData({
      cartItems: this.data.cartItems
    })
    this.refreshCartData();
  },
  /**
   * 空数据
   */
  empty() {
    this.setData({
      isEmpty: true,
    })
    /* 角标隐藏 */
    wx.hideTabBarRedDot({
      index: 2
    })
  },
  /**
   * 非空数据
   */
  notEmpty() {
    this.setData({
      isEmpty: false
    })
    /* 角标显示 */
    wx.showTabBarRedDot({
      index: 2
    })
  },
  /**
   * 结算
   * @param event
   */
  onSettle(event) {
    if (this.data.totalSkuCount <= 0) {
      return;
    }

    wx.navigateTo({
      url: `/pages/order/order?way=${ShoppingWay.CART}`
    })
  }


})