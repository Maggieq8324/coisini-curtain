/**
 * sku-code拆解
 */

import {combination} from "../../utils/util";

export class SkuCode {

    code
    spuId
    totalSeqments = [] // 所有sku排列组合

    constructor(code) {
        this.code = code;
        this._splitToSeqments()
    }

    /**
     * sku-code 拆解
     * @private
     */
    _splitToSeqments() {
        const spuAndSpec = this.code.split('$');
        this.spuId = spuAndSpec[0];

        const specCodeArray = spuAndSpec[1].split('#');
        const length = specCodeArray.length;

        for (let i = 1; i <= length; i++) {
            // 排列组合
            const seqments = combination(specCodeArray, i);
            const newSeqments = seqments.map(segs=>{
                return segs.join('#')
            })

           this.totalSeqments = this.totalSeqments.concat(newSeqments);
        }
    }

}