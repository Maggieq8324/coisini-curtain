/**
 * 栅栏
 */
import {Cell} from "./cell";

class Fence {

    cells = [] // 单元格
    specs // sku规格
    title // 规格名称
    id // 规格id

    /**
     * 构造函数
     * @param specs
     */
    constructor(specs) {
        this.specs = specs
        this.title = specs[0].key
        this.id = specs[0].key_id
    }

    /**
     * 初始化栅栏单元格属性
     */
    init() {
        this._initCells();
    }

    /**
     * 初始化栅栏单元格属性
     */
    _initCells() {
        this.specs.forEach(s => {
            /**
             * cell去重
             * some 返回一个Boolean，判断是否有元素符合func条件
             * every 返回一个Boolean，判断所有元素是否符合func条件
             * filter 返回一个符合func条件的元素数组
             * map 返回一个新的array，数组元素由每一次调用函数产生结果组成
             * @type {boolean}
             */
            const existed = this.cells.some(c=>{
                return c.id === s.value_id
            })
            if(existed){
                return
            }
            const cell = new Cell(s)
            this.cells.push(cell)
        })
    }

    /**
     * 设置可视规格
     * @param skuList
     */
    setFenceSketch(skuList) {
        this.cells.forEach(c => {
            this._setCellSkuImg(c, skuList);
        })
    }

    /**
     * 设置可视规格图片
     * @param cell
     * @param skuList
     * @private
     */
    _setCellSkuImg(cell, skuList) {
        const specCode = cell.getCellCode();
        const matchedSku = skuList.find(s => s.code.includes(specCode));
        if (matchedSku) {
            cell.skuImg = matchedSku.img;
        }
    }

    /**
     * value标题
     * @param title
     */
    // pushValueTitle(title) {
    //     this.valueTites.push(title)
    // }


}

export {
    Fence
}