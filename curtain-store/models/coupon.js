import {Http} from "../utils/http";

/**
 * @Description 优惠劵
 * @author coisini
 * @date Sep 2, 2021
 * @Version 1.0
 */
export class Coupon {
    /**
     * 优惠劵领取
     * @param cid
     * @returns {Promise<null>}
     */
    static async collectCoupon(cid) {
        return await Http.request({
            method: 'POST',
            url: `/coupon/collect/${cid}`,
            throwError: true
        })
    }

    /**
     * 我的优惠劵
     * @param status
     * @returns {Promise<null|*>}
     */
    static getMyCoupons(status) {
        return Http.request({
            url: `/coupon/myself/by/status/${status}`
        })
    }

    /**
     * 查询分类劵
     * @param cid
     * @returns {Promise<null>}
     */
    static async getCouponsByCategory(cid) {
        return await Http.request({
            url: `/coupon/by/category/${cid}`,
        })
    }

    /**
     * 我可用的优惠劵（带分类）
     * @returns {Promise<null|*>}
     */
    static async getMySelfWithCategory() {
        return Http.request({
            url: `/coupon/myself/available/with_category`
        })
    }

    /**
     * 获取两张分类优惠劵
     * @param cid
     * @returns {Promise<T[]|BigUint64Array|Uint8ClampedArray|Uint32Array|Blob|Int16Array|T[]|Float64Array|SharedArrayBuffer|string|Uint16Array|ArrayBuffer|Int32Array|Float32Array|BigInt64Array|Uint8Array|Int8Array|T[]|[]>}
     */
    static async getTop2CouponsByCategory(cid) {
        let coupons = await Http.request({
            url: `/coupon/by/category/${cid}`,
        })

        // 全场劵
        if (coupons.length === 0) {
            const otherCoupons = await Coupon.getWholeStoreCoupons();
            return Coupon.getTop2(otherCoupons);
        }

        if (coupons.length >= 2) {
            return coupons.slice(0, 2);
        }

        if (coupons.length === 1) {
            const otherCoupons = await Coupon.getWholeStoreCoupons();
            coupons = coupons.concat(otherCoupons);
            return Coupon.getTop2(coupons);
        }
    }

    /**
     * 获取前两张优惠劵
     * @param coupons
     * @returns {any[]|BigUint64Array|Uint8ClampedArray|Uint32Array|Blob|Int16Array|Float64Array|SharedArrayBuffer|string|Uint16Array|ArrayBuffer|Int32Array|Float32Array|BigInt64Array|Uint8Array|Int8Array|*}
     */
    static getTop2(coupons) {
        if (coupons.length === 0) {
            return [];
        }

        if (coupons.length >= 2) {
            return coupons.slice(0, 2);
        }

        if (coupons.length === 1) {
            return coupons;
        }

        return [];
    }

    /**
     * 获取全场优惠劵
     * @returns {Promise<null|*>}
     */
    static async getWholeStoreCoupons() {
        return Http.request({
            url: `/coupon/whole_store`
        })
    }
}