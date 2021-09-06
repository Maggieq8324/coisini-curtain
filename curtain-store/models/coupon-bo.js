/**
 * @Description Coupon Business Object
 * @author coisini
 * @date Sep 2, 2021
 * @Version 1.0
 */
import {CouponType} from "../core/enum";
import {accMultiply, accSubtract} from "../utils/number";

export class CouponBO {

    constructor(coupon) {
        this.type = coupon.type;
        this.fullMoney = coupon.full_money;
        this.rate = coupon.rate;
        this.minus = coupon.minus;
        this.id = coupon.id;
        this.startTime = coupon.start_time;
        this.endTime = coupon.end_time;
        this.wholeStore = coupon.whole_store;
        this.title = coupon.title;
        this.satisfaction = false; // 当前订单是否可用

        /**
         * 提取分类ID
         * @type {*[]}
         */
        this.categoryIds = coupon.categories.map(category => {
            return category.id;
        })
    }

    /**
     * 优惠劵是否可以使用在当前订单中
     * @param order
     */
    meetCondition(order) {
        let categoryTotalPrice;

        if (this.wholeStore) {
            // 全场券无视适用分类
            categoryTotalPrice = order.getTotalPrice();
        } else {
            categoryTotalPrice = order.getTotalPriceByCategoryIdList(this.categoryIds);
        }

        let satisfaction = false;

        /**
         * FULL_MINUS与FULL_OFF都执行_fullTypeCouponIsOK()
         */
        switch (this.type) {
            case CouponType.FULL_MINUS:
            case CouponType.FULL_OFF:
                satisfaction = this._fullTypeCouponIsOK(categoryTotalPrice, this.type);
                break;
            case CouponType.NO_THRESHOLD_MINUS:
                satisfaction = true;
                break;
            default:
                break;
        }

        this.satisfaction = satisfaction;
    }

    /**
     * 获取最终价格
     * @param orderPrice 订单金额
     * @param couponObj 优惠劵
     */
    static getFinalPrice(orderPrice, couponObj) {
        if (couponObj.satisfaction === false) {
            throw new Error('优惠券不满足使用条件');
        }

        let finalPrice = null;

        switch (couponObj.type) {
            case CouponType.FULL_MINUS: // 满减
                return {
                    finalPrice: accSubtract(orderPrice, couponObj.minus),
                    discountMoney: couponObj.minus
                }
            case CouponType.FULL_OFF: // 折扣
                const actualPrice = accMultiply(orderPrice, couponObj.rate)
                finalPrice = CouponBO.roundMoney(actualPrice)
                const discountMoney = accSubtract(orderPrice, finalPrice)
                return {
                    finalPrice,
                    discountMoney
                }
            case CouponType.NO_THRESHOLD_MINUS: // 通用
                finalPrice = accSubtract(orderPrice, couponObj.minus)
                finalPrice = finalPrice < 0 ? 0 : finalPrice
                return {
                    finalPrice,
                    discountMoney: couponObj.minus
                }
        }

    }

    /**
     * 满减劵是否可用
     * @param categoryTotalPrice
     * @returns {boolean}
     * @private
     */
    _fullTypeCouponIsOK(categoryTotalPrice, type) {
        if (categoryTotalPrice >= this.fullMoney) {
            return true;
        }
        return false
    }

    /**
     * 向上取整
     * @param money
     * @returns {number}
     */
    static roundMoney(money) {
        /**
         * 对于小数的约束可能模式有4种：向上/向下取整、四舍五入、银行家模式（toFixed）
         * 前端算法模式必须同服务端保持一致，否则对于浮点数金额的运算将导致订单无法通过验证
         * Math.ceil()  “向上取整”
         * @type {number}
         */
        return Math.ceil(money * 100) / 100;
    }


}