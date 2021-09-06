/**
 * Sku Util
 */
import {Joiner} from "./joiner";

/**
 * 规格格式化
 * @param specs
 * @returns {string|null}
 */
const parseSpecValue = function (specs) {
    if(!specs){
        return null
    }
    const joiner = new Joiner('; ', 2)
    specs.map(spec=>{
        joiner.join(spec.value)
    })
    return joiner.getStr()
}

const parseSpecValueArray = function (specs) {

}

export {
    parseSpecValue,
    parseSpecValueArray
}