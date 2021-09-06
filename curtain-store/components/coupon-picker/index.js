// components/coupon-picker/index.js
import {getSlashYMD} from "../../utils/date";
import {CouponOperate} from "../../core/enum";

Component({
    /**
     * 组件的属性列表
     */
    properties: {
        coupons: Array
    },

    observers: {
        'coupons': function (coupons) {
            if (coupons.length === 0) {
                return
            }
            const couponsView = this.convertToView(coupons)
            // 可使用优惠劵数量
            const satisfactionCount = this.getSatisfactionCount(coupons)
            this.setData({
                _coupons: couponsView,
                satisfactionCount
            })
        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        _coupons: [],
        currentKey: null,
        satisfactionCount: 0
    },

    /**
     * 组件的方法列表
     */
    methods: {
        /**
         * 转换展示对象
         * @param coupons
         * @returns {Uint8Array | BigInt64Array | {startTime, id, endTime, title, satisfaction}[] | Float64Array | Int8Array | Float32Array | Int32Array | Uint32Array | Uint8ClampedArray | BigUint64Array | Int16Array | Uint16Array}
         */
        convertToView(coupons) {
            const couponsView = coupons.map(coupon => {
                return {
                    id: coupon.id,
                    title: coupon.title,
                    startTime: getSlashYMD(coupon.startTime),
                    endTime: getSlashYMD(coupon.endTime),
                    satisfaction: coupon.satisfaction
                }
            })
            couponsView.sort((a, b) => {
                if (a.satisfaction) {
                    return -1
                }
            })
            return couponsView
        },

        /**
         * 获取可使用优惠劵数量
         * @param coupons
         * @returns {*}
         */
        getSatisfactionCount(coupons) {
            return coupons.reduce((pre, coupon) => {
                if (coupon.satisfaction === true) {
                    return pre + 1;
                }
                return pre
            }, 0);
        },

        /**
         * radio 改变事件
         * @param event
         */
        onChange(event) {
            const currentKey = event.detail.currentKey;
            const key = event.detail.key;
            this.setData({
                currentKey
            })
            const currentCoupon = this.findCurrentCoupon(currentKey, key);

            // 事件抛出
            this.triggerEvent('choose', {
                coupon: currentCoupon,
                operate: this.decidePickOrUnPick(currentKey)
            })
        },

        /**
         * 判断优惠劵选中状态
         * @param currentKey
         * @returns {string}
         */
        decidePickOrUnPick(currentKey) {
            if (currentKey === null) {
                return CouponOperate.UNPICK;
            } else {
                return CouponOperate.PICK;
            }
        },

        /**
         *
         * @param currentKey 当前选择的key
         * @param key 操作的key
         * @returns {number | T | bigint}
         */
        findCurrentCoupon(currentKey, key) {
            if (currentKey === null) {
                // 用户取消了选择
                return this.properties.coupons.find(coupon => coupon.id.toString() === key);
            }

            return this.properties.coupons.find(coupon => coupon.id.toString() === currentKey);
        }

    }
})
