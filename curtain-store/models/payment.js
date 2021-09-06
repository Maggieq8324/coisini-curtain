/**
 * @Description 支付
 * @author coisini
 * @date Sep 3, 2021
 * @Version 1.0
 */
import {Http} from "../utils/http";

export class Payment {

    /**
     * 获取支付参数
     * @param orderId
     * @returns {Promise<*>}
     */
    static async getPayParams(orderId) {
        const serverParams = await Http.request({
            url:`/payment/pay/order/${orderId}`,
            method:'POST'
        })
        return serverParams;
    }

}