// pages/home/home.js

import {config} from "../../config/config";
import {Theme} from "../../models/theme";
import {Banner} from "../../models/banner";
import {Activity} from "../../models/activity";
import {Category} from "../../models/category";
import {SpuPaging} from "../../models/spu-paging";
import {CouponCenterType} from "../../core/enum";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    themeA: null,
    themeE: null,
    bannerB: [],
    grid: [],
    activityD: null,
    spuPaging: null,
    loadingType: 'loading'
  },
  /**
   * 生命周期函数--监听页面加载
   */
  async onLoad (options) {
    await this.initAllData()
    await this.initBottomSpuList()
  },
  async initBottomSpuList() {
    const paging = SpuPaging.getLatestPaging()
    this.data.spuPaging = paging
    const data = await paging.getMoreData()
    if (!data) {
      return
    }

    wx.lin.renderWaterFlow(data.items)
  },
  /**
   * 初始化数据
   */
  async initAllData() {
    // const themeA = await Theme.getHomeLocationA();

    const theme = new Theme();
    await theme.getThemes();

    const themeA = theme.getHomeLocationA();

    const themeE = theme.getHomeLocationE();

    const themeF = theme.getHomeLocationF();

    const themeH = theme.getHomeLocationH();

    let themeESpu = [];
    if (themeE.online) {
      const data = await Theme.getHomeLocationESpu();
      themeESpu = data.spu_list.slice(0, 8);
    }

    const bannerB = await Banner.getHomeLocationB();

    const grid = await Category.getGridCategoryC()

    const activityD = await Activity.getHomeLocationD();

    const bannerG = await Banner.getHomeLocationG();

    this.setData({
      themeA,
      bannerB,
      themeE,
      themeF,
      themeH,
      themeESpu,
      grid,
      activityD,
      bannerG
    })
  },
  /**
   * 跳转优惠劵
   * @param event
   */
  onGoToCoupons(event) {
    // data-aname="a-2" 固定写法，携带数据
    const name = event.currentTarget.dataset.aname;
    wx.navigateTo({
      url: `/pages/coupon/coupon?name=${name}&type=${CouponCenterType.ACTIVITY}`
    })
  },
  /**
   * 跳转主题
   * @param event
   */
  onGoToTheme(event) {
    const tName = event.currentTarget.dataset.tname;
    wx.navigateTo({
      url: `/pages/theme/theme?tname=${tName}`
    })
  },
  /**
   * 跳转banner
   * @param event
   */
  onGoToBanner(event) {
    const keyword = event.currentTarget.dataset.keyword
    const type = event.currentTarget.dataset.type
    Banner.gotoTarget(type, keyword)
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: async function () {
    const data = await this.data.spuPaging.getMoreData();
    if(!data){
      return
    }
    wx.lin.renderWaterFlow(data.items)

    if(!data.moreData){
      this.setData({
        loadingType: 'end'
      })
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})