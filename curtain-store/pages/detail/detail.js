// pages/detail/detail.js
import {Spu} from "../../models/spu";
import {CouponCenterType, ShoppingWay} from "../../core/enum";
import {SaleExplain} from "../../models/sale-explain";
import {getWindowHeightRpx} from "../../utils/system";
import {Cart} from "../../models/cart";
import {CartItem} from "../../models/cart_item";
import {Coupon} from "../../models/coupon";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    showRealm:false, // 规格显示面板
    cartItemCount: 0 // 购物车Item数量
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    const pid = options.pid;
    const spu = await Spu.getDetail(pid);

    /**
     * 获取两张分类优惠劵
     * @type {T[]|BigUint64Array|Uint8ClampedArray|Uint32Array|Blob|Int16Array|Float64Array|SharedArrayBuffer|string|Uint16Array|ArrayBuffer|Int32Array|Float32Array|BigInt64Array|Uint8Array|Int8Array|*[]}
     */
    const coupons = await Coupon.getTop2CouponsByCategory(spu.category_id);

    const explain = await SaleExplain.getFixed();
    const windowHeight = await getWindowHeightRpx();
    const h = windowHeight - 100;

    this.setData({
      spu,
      explain,
      h,
      coupons
    });

    this.updateCartItemCount();
  },
  /**
   * 优惠劵跳转
   * @param event
   */
  onGoToCouponCenter(event) {
    const type = CouponCenterType.SPU_CATEGORY;
    const cid = this.data.spu.category_id;
    wx.navigateTo({
      url: `/pages/coupon/coupon?cid=${cid}&type=${type}`
    })
  },
  /**
   * 详情页导航加入购物车监听事件
   * @param event
   */
  onAddToCart(event) {
    this.setData({
      showRealm: true,
      orderWay:ShoppingWay.CART
    })
  },
  /**
   * 详情页导航立即购买监听事件
   * @param event
   */
  onBuy(event) {
    this.setData({
      showRealm:true,
      orderWay:ShoppingWay.BUY
    })
  },
  /**
   * sku选择 购买事件监听
   * 加入购物车 or 立即购买
   */
  onShopping(event) {
    const chosenSku = event.detail.sku;
    const skuCount = event.detail.skuCount;

    // 购物车
    if (event.detail.orderWay === ShoppingWay.CART) {
      const cart = new Cart();
      const cartItem = new CartItem(chosenSku, skuCount);
      cart.addItem(cartItem);
      this.updateCartItemCount();
    }

    // 立即购买
    if(event.detail.orderWay === ShoppingWay.BUY){
      wx.navigateTo({
        url:`/pages/order/order?sku_id=${chosenSku.id}&count=${skuCount}&way=${ShoppingWay.BUY}`
      })
    }
  },
  /**
   * 修改购物车Item数量
   */
  updateCartItemCount() {
    const cart = new Cart()
    this.setData({
      cartItemCount: cart.getCartItemCount(),
      showRealm: false
    })
  },
  /**
   * 首页监听事件
   * @param event
   */
  onGotoHome(event) {
    wx.switchTab({
      url:'/pages/home/home'
    })
  },
  /**
   * 购物车监听事件
   * @param event
   */
  onGotoCart(event) {
    wx.switchTab({
      url:'/pages/cart/cart'
    })
  },
  /**
   * 已选规格回调监听事件
   * @param event
   */
  onSpecChange(event) {
    this.setData({
      specs: event.detail
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },



  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})