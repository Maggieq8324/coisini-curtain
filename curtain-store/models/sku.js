/**
 * @Description Sku
 * @author coisini
 * @date Aug 30, 2021
 * @Version 1.0
 */

import {Http} from "../utils/http";

export class Sku {
    static async getSkusByIds(ids) {
        /**
         * 模板字符串将数组转换成字符串
         * ids=1,2,3,4
         */
        const res = await Http.request({
            url: `/sku?ids=${ids}`
        })
        return res
    }
}