// components/spu-preview-r/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    data: Object
  },

  /**
   * 组件的初始数据
   */
  data: {
    tags: Array
  },

  observers: {
    data: function (data) {
      if (!data) {
        return
      }
      if (!data.tags) {
        return
      }
      const tags = data.tags.split('$')
      this.setData({
        tags
      })
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoadImg(event) {
      const {width, height} = event.detail
      this.setData({
        w:340,
        h:340*height/width
      })
    },
    onItemTap(event){
      const pid = event.currentTarget.dataset.pid
      wx.navigateTo({
        url:`/pages/detail/detail?pid=${pid}`
      })
    }
  }
})
