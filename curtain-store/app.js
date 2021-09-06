// app.js
// App({
//   onLaunch() {
//     // 展示本地存储能力
//     const logs = wx.getStorageSync('logs') || []
//     logs.unshift(Date.now())
//     wx.setStorageSync('logs', logs)
//
//     // 登录
//     wx.login({
//       success: res => {
//         // 发送 res.code 到后台换取 openId, sessionKey, unionId
//       }
//     })
//   },
//   globalData: {
//     userInfo: null
//   }
// })

import {Cart} from "./models/cart";
import {Token} from "./models/token";

App({
  onLaunch() {
    const cart = new Cart();
    if (!cart.isEmpty()) {
      wx.showTabBarRedDot({
        index: 2
      })
    }

    /**
     * token
     * @type {Token}
     */
    const token = new Token();
    token.verify();
  }
})
