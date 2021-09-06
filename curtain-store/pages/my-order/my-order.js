// pages/my-order/my-order.js
import {Order} from "../../models/order";
import {OrderStatus} from "../../core/enum";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    activeKey:OrderStatus.ALL,
    items:[],
    loadingType:'loading',
    bottomLoading:true,
    paging:null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    // 订单状态
    const activeKey = options.key
    this.data.activeKey = options.key
    await this.initItems(activeKey)
  },

  async onShow() {
    await this.initItems(this.data.activeKey)
  },

  /**
   * 初始化Order-Item
   * @param activeKey
   * @returns {Promise<void>}
   */
  async initItems(activeKey) {
    wx.lin.hideEmpty();
    this.setData({
      activeKey,
      items:[]
    });

    this.data.paging = this.getPaging(activeKey);
    const data = await this.data.paging.getMoreData();
    if(!data){
      return;
    }
    this.bindItems(data);
  },

  /**
   * 获取分页对象
   * @param activeKey
   * @returns {Paging}
   */
  getPaging(activeKey) {
    activeKey = parseInt(activeKey);
    switch (activeKey) {
      case OrderStatus.ALL: // 全部
        return Order.getPagingByStatus(OrderStatus.ALL);
      case OrderStatus.UNPAID: // 待支付
        return Order.getPagingUnpaid();
      case OrderStatus.PAID: // 已支付
        return Order.getPagingByStatus(OrderStatus.PAID);
      case OrderStatus.DELIVERED : // 已发货
        return Order.getPagingByStatus(OrderStatus.DELIVERED);
      case OrderStatus.FINISHED : // 已完成
        return Order.getPagingByStatus(OrderStatus.FINISHED);
    }
  },

  /**
   * 无订单
   */
  empty() {
    wx.lin.showEmpty({
      text:'暂无相关订单',
    })
    this.setData({
      bottomLoading:false
    })
  },

  /**
   * 数据绑定
   * @param data
   */
  bindItems(data) {
    if(data.empty){
      this.empty();
      return
    }

    if (data.accumulator.length !== 0){
      this.setData({
        items:data.accumulator,
        bottomLoading:true
      });
    }

    if(!data.moreData){
      this.setData({
        loadingType:'end'
      })
    }
  },

  /**
   * Segment 切换
   * @param event
   * @returns {Promise<void>}
   */
  async onSegmentChange(event) {
    const activeKey = event.detail.activeKey
    await this.initItems(activeKey)
  },

  /**
   * 触底事件
   * @returns {Promise<void>}
   */
  async onReachBottom() {
    const data = await this.data.paging.getMoreData();
    console.log("onReachBottom:" + JSON.stringify(data))
    if (data) {
      this.bindItems(data);
    }
  },

  /**
   * 支付成功回调
   * @param event
   */
  onPaySuccess(event) {
    const oid = event.detail.oid
    wx.navigateTo({
      url:`/pages/pay-success/pay-success?oid=${oid}`
    })
    // this.initItems(2)
  }

})