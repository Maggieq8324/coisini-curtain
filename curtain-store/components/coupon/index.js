// components/coupon/index.js
// import {Coupon} from "../../models/coupon";
import {showToast} from "../../utils/ui";
import {CouponData} from "../../models/coupon-data";
import {CouponStatus} from "../../core/enum";
import {Coupon} from "../../models/coupon";

Component({
    /**
     * 组件的属性列表
     */
    properties: {
        coupon: Object,
        // userCollected: Boolean,
        status: {
            type: Number,
            value: CouponStatus.CAN_COLLECT // 克林去
        }
    },

    data: {
        _coupon: Object,
        _status: CouponStatus.CAN_COLLECT,
        userCollected: false
    },

    observers: {
        'coupon': function (coupon) {
            if (!coupon) {
                return;
            }
            this.setData({
                // 内置属性转换
                _coupon: new CouponData(coupon),
            })
        }
    },

    methods: {
        /**
         * 优惠劵领取
         * @param event
         * @returns {Promise<void>}
         */
        async onGetCoupon(event) {
            // 已领取，跳转分类页面
            if (this.data.userCollected) {
                wx.switchTab({
                    url: `/pages/category/category`
                })
                return;
            }
            if (this.data._status === CouponStatus.AVAILABLE) {
                showToast('您已领取了该优惠券,在"我的优惠券"中可查看');
                return;
            }

            // 固定写法，参数传递
            const couponId = event.currentTarget.dataset.id;
            let msg;

            try {
                // 领取优惠劵
                msg = await Coupon.collectCoupon(couponId);
            } catch (e) {
                if (e.errorCode === 40006) {
                    this.setUserCollected();
                    showToast('您已领取了该优惠券,在"我的优惠券"中可查看');
                }
                return;
            }

            if (msg.code === 0) {
                this.setUserCollected();
                showToast('领取成功，在"我的优惠券"中查看');
            }
        },

        /**
         * 二次点击
         * 设置状态已领取
         */
        setUserCollected() {
            this.setData({
                _status: CouponStatus.AVAILABLE,
                userCollected: true
            })
        }
    }

})
