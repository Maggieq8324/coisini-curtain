// components/empty-scrren/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    text: {
      type: String,
      value: '暂无相关商品'
    },
    show: {
      type: Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  lifetimes: {
    attached() {
      this._init()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    _init() {
      wx.lin = wx.lin || {};
      wx.lin.showEmptyScreen = (options) => {
        const {
          text = this.properties.text
        } = {...options};
        this.setData({
          text,
          show: true
        });
      };
      wx.lin.hideEmpty = () => {
        this.setData({
          show: false
        });
      };
    },
  }
})
