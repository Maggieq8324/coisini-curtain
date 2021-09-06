import {Http} from "../utils/http";

class Activity {
    static locationD = 'a-2'
    static async getHomeLocationD() {
        return await Http.request({
            url: `/activity/name/${Activity.locationD}`
        })
    }

    /**
     * 获取活动主题优惠劵
     * @param activityName
     * @returns {Promise<*>}
     */
    static async getActivityWithCoupon(activityName) {
        return Http.request({
            url: `/activity/name/${activityName}/with_coupon`
        })
    }
}

export {
    Activity
}