// components/hot-list/index.js
import {Banner} from "../../models/banner";

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    banner: Object,
  },

  /**
   * 监听器
   */
  observers:{
    'banner':function (banner) {
      if(!banner){
        return
      }
      if(banner.items.length === 0){
        return
      }
      const left = banner.items.find(i=>i.name==='left')
      const rightTop = banner.items.find(i=>i.name==='right-top')
      const rightBottom = banner.items.find(i=>i.name==='right-bottom')
      this.setData({
        left,
        rightTop,
        rightBottom
      })
    }
  } ,


  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    onGotToTheme(event) {
      const tName = event.currentTarget.dataset.tname
      console.log(tName)
      wx.navigateTo({
        url: `/pages/theme/theme?tname=${tName}`
      })
    },

    onGotoDetail(event) {
      const keyword = event.currentTarget.dataset.keyword
      const type = event.currentTarget.dataset.type
      Banner.gotoTarget(type, keyword)
    }
  }
})
