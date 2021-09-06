// pages/search/search.js
import {HistoryKeyword} from "../../models/history-keyword";
import {Tag} from "../../models/tag";
import {Search} from "../../models/search";
import {showToast} from "../../utils/ui";

const history = new HistoryKeyword()
Page({

  data: {
    loadingType:'end'
  },

  onLoad: async function (options) {
    const historyTags = history.get();
    const hotTags = await Tag.getSearchTags();
    this.setData({
      historyTags,
      hotTags
    })
  },
  /**
   * 搜索回调函数
   * @param event
   * @returns {Promise<void>}
   */
  async onSearch(event) {
    this.setData({
      search: true,
      items: []
    })

    // value 输入框值 name 标签值
    const keyword = event.detail.value || event.detail.name;
    if (!keyword) {
      showToast('请输入关键字');
      return;
    }
    // 保存关键字
    history.save(keyword);

    this.setData({
      historyTags: history.get()
    })

    // 根据关键字搜索
    const paging = Search.search(keyword);
    wx.lin.showLoading({
      color: '#157658',
      type: 'flash',
      fullScreen: true
    });
    const data = await paging.getMoreData();
    wx.lin.hideLoading();
    this.bindItems(data);
  },
  /**
   * 取消搜索回调事件
   * @param event
   */
  onCancel(event) {
    this.setData({
      search: false
    })
  },
  /**
   * 绑定搜索结果
   * @param data
   */
  bindItems(data) {
    if (data.accumulator.length !== 0) {
      this.setData({
        items: data.accumulator
      })
    }
  },
  /**
   * 清除历史搜索回调事件
   * @param event
   */
  onDeleteHistory(event) {
    history.clear();
    this.setData({
      historyTags: []
    })
  }

})