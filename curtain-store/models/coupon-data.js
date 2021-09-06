import {getSlashYMD} from "../utils/date";

/**
 * @Description 优惠劵内置属性转换
 * @author coisini
 * @date Sep 2, 2021
 * @Version 1.0
 */
export class CouponData {
    startTime
    endTime
    status

    constructor(coupon, status) {
        Object.assign(this, coupon);
        this.startTime = getSlashYMD(coupon.start_time);
        this.endTime = getSlashYMD(coupon.end_time);
    }
}