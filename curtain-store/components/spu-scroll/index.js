// components/spu-scroll/index.js
import {Theme} from "../../models/theme";

Component({
  /**
   * 组件的属性列表
   */
  externalClasses:['l-class'],
  properties: {
    theme: Object,
    spuList: Array
  },
  /**
   * 组件的初始数据
   */
  data: {

  },
  /**
   * 组件的方法列表
   */
  methods: {
    onMore(event) {
      wx.navigateTo({
        url:`/pages/theme-spu-list/theme-spu-list?tname=${Theme.locationE}`
      })
    },
    onTap(event) {
      const spuId = event.currentTarget.dataset.spuId;
      wx.navigateTo({
        url:`/pages/detail/detail?pid=${spuId}`
      })
    },
  }
})