// components/cart-item/index.js
import {Cart} from "../../models/cart";
import {parseSpecValue} from "../../utils/sku";

const cart = new Cart()

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    cartItem: Object
  },

  /**
   * 组件的初始数据
   */
  data: {
    specStr: String, // 规格描述
    discount: Boolean, // 打折
    soldOut: Boolean, // 售罄
    online: Boolean, // 下架
    stock: Cart.SKU_MAX_COUNT,
    skuCount: 1
  },

  /**
   * 组件数据监听
   */
  observers: {
    cartItem: function (cartItem) {
      if (!cartItem) {
        return
      }
      const specStr = parseSpecValue(cartItem.sku.specs)
      const discount = cartItem.sku.discount_price ? true : false
      const soldOut = Cart.isSoldOut(cartItem)
      const online = Cart.isOnline(cartItem)
      this.setData({
        specStr,
        discount,
        soldOut,
        online,
        stock: cartItem.sku.stock,
        skuCount: cartItem.count
      })
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    /**
     * 删除
     * @param event
     */
    onDelete(event) {
      const skuId = this.properties.cartItem.skuId

      cart.removeItem(skuId)
      this.setData({
        cartItem: null
      })

      // 抛出事件
      this.triggerEvent('itemdelete', {
        skuId
      })
    },
    /**
     * Item选择
     * @param event
     */
    checkedItem(event) {
      const checked = event.detail.checked
      cart.checkItem(this.properties.cartItem.skuId)
      this.properties.cartItem.checked = checked
      this.triggerEvent('itemcheck', {})
    },
    /**
     * 数量选择事件
     * @param event
     */
    onSelectCount(event) {
      let newCount = event.detail.count;
      cart.replaceItemCount(this.properties.cartItem.skuId, newCount);
      this.triggerEvent("countfloat");
    }
  }
})
