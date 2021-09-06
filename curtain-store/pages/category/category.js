// pages/category/category.js

import {getSystemSize} from "../../utils/system";
import {px2rpx} from "../../miniprogram_npm/lin-ui/utils/util";
import {Categories} from "../../models/categories";
import {SpuListType} from "../../core/enum";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultRootId: 2
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    await this.initCategoryData()
    await this.setDynamicSegmentHeight()
  },
  /**
   * 请求数据
   * @returns {Promise<void>}
   */
  async initCategoryData() {
    const categories = new Categories()
    this.data.categories = categories

    await categories.getAll()
    const roots = categories.getRoots()
    const defaultRoot = this.getDefaultRoot(roots)
    const currentSubs = categories.getSubs(defaultRoot.id)
    this.setData({
      roots, // 一级分类
      currentSubs, // 二级分类
      currentBannerImg: defaultRoot.img
    })
  },
  getDefaultRoot(roots) {
    let defaultRoot = roots.find(r => r.id === this.data.defaultRootId)
    if (!defaultRoot) {
      defaultRoot = roots[0]
    }
    return defaultRoot
  },

  /**
   *
   * @returns {Promise<void>}
   */
  async setDynamicSegmentHeight() {
    const res = await getSystemSize()
    const windowHeightRpx = px2rpx(res.windowHeight)
    const h = windowHeightRpx - 60 - 20 - 2
    this.setData({
      segHeight: h
    })
  },

  onSegChange(event) {
    const rootId = event.detail.activeKey
    const currentSubs = this.data.categories.getSubs(rootId)
    const currentRoot = this.data.categories.getRoot(rootId)
    this.setData({
      currentSubs,
      currentBannerImg:currentRoot.img
    })
  },

  onJumpToSpuList(event) {
    const cid = event.detail.cid;

    wx.navigateTo({
      url: `/pages/spu-list/spu-list?cid=${cid}&type=${SpuListType.SUB_CATEGORY}`
    })
  },

  onGotoSearch(event) {
    wx.navigateTo({
      url: `/pages/search/search`
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})