// components/address/index.js
import {Address} from "../../models/address";
import {AuthAddress} from "../../core/enum";

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    address: Object,
    hasChosen: false, // 已选择地址
    showDialog: false
  },

  /**
   * 组件的初始数据
   */
  data: {
    address: Object
  },

  /**
   * 生命周期函数
   */
  lifetimes: {
    // 页面创建时执行
    attached() {
      const address = Address.getLocal();
      if (address) {
        this.setData({
          address,
          hasChosen: true
        });

        // 事件抛出
        this.triggerEvent('address', {
          address
        })
      }
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    /**
     * 用户地址授权
     */
    async onChooseAddress(event) {
      const authStatus = await this.hasAuthorizedAddress();
      if (authStatus === AuthAddress.DENY) {
        this.setData({
          showDialog: true
        })
        return;
      }
      await this.getUserAddress();
    },
    /**
     * 授权弹窗回调
     * @param event
     */
    onDialogConfirm(event) {
      wx.openSetting({});
    },
    /**
     * 获取用户地址
     */
    async getUserAddress() {
      let res;
      try {
        res = await wx.chooseAddress({})
      } catch (e) {
        console.error(e)
      }

      if (res) {
        this.setData({
          address: res,
          hasChosen: true // 用户已选择地址
        });
        Address.setLocal(res);

        // 事件抛出
        this.triggerEvent('address', {
          address: res
        })
      }
    },
    /**
     * 确认用户是否授权
     * @returns {Promise<void>}
     */
    async hasAuthorizedAddress() {
      // 用户配置总项
      const setting = wx.getSetting({});
      let addressSetting;

      if (setting.authSetting && setting.authSetting['scope.address']) {
        addressSetting = setting.authSetting['scope.address'];
      }

      if (addressSetting === undefined) {
        return AuthAddress.NOT_AUTH; // 用户未地址授权
      }
      if (addressSetting === false) {
        return AuthAddress.DENY; // 拒绝授权
      }
      if (addressSetting === true) {
        return AuthAddress.AUTHORIZED; // 已授权
      }

    }
  }
})
