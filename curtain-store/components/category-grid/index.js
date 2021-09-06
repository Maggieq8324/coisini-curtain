// components/category-grid/index.js
import {SpuListType} from "../../core/enum";

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    grid: {
      type: Array
    }
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
    /**
     * 跳转分类
     * @param event
     */
    onGoToCategory(event) {
      const cid = event.currentTarget.dataset.cid;
      const rid = event.currentTarget.dataset.rid;
      const pCid = cid ? cid : rid;
      const isRoot = !cid;
      const type = isRoot ? SpuListType.ROOT_CATEGORY : SpuListType.SUB_CATEGORY;
      wx.navigateTo({
        url: `/pages/spu-list/spu-list?cid=${pCid}&type=${type}`
      })
    }
  }
})