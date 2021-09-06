// components/my-banner/index.js
import {User} from "../../models/user";
import {promisic} from "../../utils/util";

Component({
    /**
     * 组件的属性列表
     */
    properties: {
        couponCount:Number
    },

    /**
     * 组件的初始数据
     */
    data: {
        showLoginBtn: false,
        couponCount:Number
    },

    /**
     * 生命周期
     * attached - 在组件实例进入页面节点树时执行
     */
    lifetimes: {
        async attached() {
            // wx.getUserInfo()
            if (!await this.hasAuthUserInfo()) {
                this.setData({
                    showLoginBtn: true
                })
            }
        }
    },

    observers:{
        'couponCount':function (couponCount) {
        }
    },

    /**
     * 组件的方法列表
     */
    methods: {
        /**
         * 更新用户信息
         * @param event
         * @returns {Promise<void>}
         */
        async onAuthUserInfo(event) {
            if (event.detail.userInfo) {
                const success = await User.updateUserInfo(event.detail.userInfo)
                this.setData({
                    showLoginBtn:false
                })
            }
        },

        /**
         * 是否能获取到用户信息
         * @returns {Promise<boolean>}
         */
        async hasAuthUserInfo() {
            const setting = await promisic(wx.getSetting)();
            const userInfo = setting.authSetting['scope.userInfo']
            return !!userInfo;
        },

        /**
         * 优惠劵
         * @param event
         */
        onGotoMyCoupon(event) {
            wx.navigateTo({
                url:`/pages/my-coupon/my-coupon`
            })
        },

        /**
         * 关于我们
         * @param event
         */
        aboutUs(event) {
            wx.navigateTo({
                url:`/pages/about/about`
            })
        }
    }
})
