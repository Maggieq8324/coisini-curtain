import {Http} from "../utils/http";

export class Spu{

    /**
     * 无规格spu判断
     * @param spu
     * @returns {boolean}
     */
    static isNoSpec(spu) {
        if(spu.sku_list.length === 1 && spu.sku_list[0].specs.length === 0){
            return true
        }
        return false
    }

    static getDetail(id) {
        return Http.request({
            url: `/spu/id/${id}/detail`
        });
    }
}
