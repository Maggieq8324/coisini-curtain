// pages/my/my.js
import {Coupon} from "../../models/coupon";
import {AuthAddress, CouponStatus} from "../../core/enum";
import {promisic} from "../../miniprogram_npm/lin-ui/utils/util";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    couponCount: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    // 可用优惠劵数量
    const coupons = await Coupon.getMyCoupons(CouponStatus.AVAILABLE);
    this.setData({
      couponCount: coupons.length
    })
  },

  /**
   * 我的优惠劵
   * @param event
   */
  onGotoMyCoupon(event) {
    wx.navigateTo({
      url: "/pages/my-coupon/my-coupon"
    })
  },

  /**
   * 我的订单
   * @param event
   */
  onGotoMyOrder(event) {
    wx.navigateTo({
      url: "/pages/my-order/my-order?key=0"
    })
  },

  /**
   * 收货地址
   * @param event
   * @returns {Promise<void>}
   */
  async onMgrAddress(event) {
    const authStatus = await this.hasAuthorizedAddress();
    if (authStatus === AuthAddress.DENY) {
      this.setData({
        showDialog: true
      })
      return;
    }
    await this.openAddress();
  },

  /**
   * 地址是否有权限获取
   * @returns {Promise<string>}
   */
  async hasAuthorizedAddress() {
    const setting = await promisic(wx.getSetting)();
    const addressSetting = setting.authSetting['scope.address'];
    if (addressSetting === undefined) {
      return AuthAddress.NOT_AUTH;
    }
    if (addressSetting === false) {
      return AuthAddress.DENY;
    }
    if (addressSetting === true) {
      return AuthAddress.AUTHORIZED;
    }
  },

  /**
   * 打开地址选择
   * @returns {Promise<void>}
   */
  async openAddress() {
    let res;
    res = await promisic(wx.chooseAddress)();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})