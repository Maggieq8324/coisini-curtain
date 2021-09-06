// components/my-order-panel/index.js
import {Order} from "../../models/order";

Component({
    /**
     * 组件的属性列表
     */
    properties: {},

    /**
     * 组件的初始数据
     */
    data: {
        unpaidCount: 0,
        paidCount: 0,
        deliveredCount: 0
    },

    lifetimes: {
        // 在组件实例进入页面节点树时执行
        async attached() {

        }
    },

    pageLifetimes: {
        async show() {
            const unpaidCount = await Order.getUnpaidCount(); // 待支付订单数
            const paidCount = await Order.getPaidCount(); // 已支付订单数
            const deliveredCount = await Order.getDeliveredCount(); // 已发货订单数
            this.setData({
                unpaidCount,
                paidCount,
                deliveredCount
            })
        }
    },

    /**
     * 组件的方法列表
     */
    methods: {
        /**
         * 跳转我的订单
         * @param event
         */
        onGotoMyOrder(event) {
            const key = event.currentTarget.dataset.key
            wx.navigateTo({
                url: `/pages/my-order/my-order?key=${key}`
            })
        }
    }
})
