// pages/spu-list/spu-list.js
import {SpuPaging} from "../../models/spu-paging";
import {SpuListType} from "../../core/enum";
import {Theme} from "../../models/theme";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    empty: false,
    paging: null,
    loading: true,
    loadingType: 'loading'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    const cid = options.cid;
    const type = options.type;
    const tName = options.tname;
    switch (type) {
      case SpuListType.ROOT_CATEGORY:
        await this.initCategoryData(cid, true);
        break;
      case SpuListType.SUB_CATEGORY:
        await this.initCategoryData(cid, false);
        break;
      case SpuListType.LATEST:
        await this.initLatestData();
        break;
      case SpuListType.THEME:
        await this.initThemeData(tName);
        break;
    }
  },

  async initThemeData(tName) {
    const data = await Theme.getThemeSpuByName(tName)
    if(data && data.spu_list.length !== 0){
      wx.lin.renderWaterFlow(data.spu_list)
      this.setData({
        loadingType:'end'
      })
    }
    else{
      this.empty()
    }
  },

  async initLatestData() {
    const paging = await SpuPaging.getLatestPaging()
    const data = await paging.getMoreData()
    this.data.paging = paging
    this.initData(data)
  },

  async initCategoryData(cid, isRoot) {
    const paging = SpuPaging.getByCategoryPaging(cid, isRoot)
    const data = await paging.getMoreData()
    this.data.paging = paging
    this.initData(data)
  },

  initData(data) {
    if(!data){
      return
    }
    if (data.empty) {
      this.empty()
      this.setData({
        loading: false
      })
    }
    // if (data.diff){
    wx.lin.renderWaterFlow(data.items)
    // }
    if(!data.moreData){
      this.setData({
        loadingType: 'end'
      })
    }
  },

  empty() {
    wx.lin.showEmptyScreen({
      text: '该分类暂时还没有商品'
    })
  },

  async onReachBottom() {
    let data = await this.data.paging.getMoreData()
    this.initData(data)
  }

})