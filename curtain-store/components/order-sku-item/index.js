// components/order-sku-item/index.js
import {parseSpecValue, parseSpecValueArray} from "../../utils/sku";

Component({
  /**
   * 组件的属性列表
   */
  externalClasses:['l-class'],
  properties: {
    orderItem:Object
  },

  /**
   * 组件的初始数据
   */
  data: {
    specValuesText:null
  },

  observers:{
    'orderItem':function (orderItem) {
      const specValues = orderItem.spec_values;
      this.setData({
        specValuesText:specValues?parseSpecValueArray(specValues):parseSpecValue(orderItem.specs)
      })
    }
  },

  attached(){
    // console.log(this.properties.orderItem)
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})

