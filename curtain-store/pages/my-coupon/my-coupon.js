// pages/my-coupon/my-coupon.js
import {Coupon} from "../../models/coupon";
import {CouponStatus} from "../../core/enum";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        activeKey:CouponStatus.AVAILABLE
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function (options) {
        await this.change(CouponStatus.AVAILABLE);
    },

    /**
     * 优惠劵状态切换展示
     * @param status
     * @returns {Promise<void>}
     */
    async change(status) {
        const coupons = await Coupon.getMyCoupons(status)
        this.setData({
            coupons,
            status
        });
    },

    /**
     * 组件切换
     * @param event
     * @returns {Promise<void>}
     */
    async onSegmentChange(event) {
        await this.change(event.detail.activeKey)
    }

})