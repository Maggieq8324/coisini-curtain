import {Matrix} from "./matrix";
import {Fence} from "./fence";

/**
 * 栅栏分组
 */
class FenceGroup {
    spu
    skuList = []
    fences = []

    /**
     * 构造器
     * @param spu
     */
    constructor(spu) {
        console.log('fences group constructor')
        this.spu = spu
        this.skuList = spu.sku_list
    }

    /**
     * 通过sku-code获取sku
     * @param skuCode
     * @returns {*}
     */
    getSku(skuCode) {
        const fullSkuCode = this.spu.id + '$' + skuCode;
        const sku = this.spu.sku_list.find(s => s.code === fullSkuCode);
        return sku ? sku : null
    }

    /**
     * 通过cellId设置cell状态
     * @param cellId
     * @param status
     */
    setCellStatusById(cellId, status) {
        this.eachCell((cell) => {
            if (cell.id === cellId) {
                cell.status = status
            }
        })
    }

    /**
     * 通过x y设置cell状态
     * @param x
     * @param y
     * @param status
     */
    setCellStatusByXY(x, y, status) {
        this.fences[x].cells[y].status = status
    }

    /**
     * 获取默认sku
     */
    getDefaultSku() {
        const defaultSkuId = this.spu.default_sku_id;

        if (!defaultSkuId) {
            return;
        }

        return this.skuList.find(s => s.id === defaultSkuId);
    }

    /**
     * 循环获取矩阵元素
     * 初始化栅栏数据
     */
    initFences1(){
        const matrix = this._createMatrix(this.skuList);
        const fences = [];
        let currentJ = -1;
        matrix.each((element, i , j) => {
            if (currentJ !== j) { //开启新列，创建一个新的Fence
                currentJ = j;
                // createFence
                fences[currentJ] = this._createFence(element);
            }

            fences[currentJ].pushValueTitle(element.value);
        })

    }

    /**
     * 矩阵转置
     * 初始化栅栏数据
     */
    initFences() {
        const matrix = this._createMatrix(this.skuList);
        console.log("matrix", matrix)
        const fences = [];
        const at = matrix.transpose();
        console.log('at', at)
        at.forEach(r => {
            const fence = new Fence(r);
            fence.init();

            // 可视化规格设置
            if (this._hasSketchFence() && this._isSketchFence(fence.id)) {
                fence.setFenceSketch(this.skuList);
            }
            fences.push(fence)
        })

        this.fences = fences;

        console.log("fences-group fences", fences)
    }

    /**
     * 是否包含可视规格
     * @returns {boolean}
     * @private
     */
    _hasSketchFence() {
        return this.spu.sketch_spec_id ? true : false
    }

    /**
     * 可视规格
     * @private
     */
    _isSketchFence(fenceId) {
        return this.spu.sketch_spec_id === fenceId ? true : false
    }

    /**
     * cell遍历回调函数
     * @param cb
     */
    eachCell(cb) {
        for (let i = 0; i < this.fences.length; i++) {
            for (let j = 0; j < this.fences[i].cells.length; j++) {
                const cell = this.fences[i].cells[j];
                cb(cell, i, j);
            }
        }
    }

    /**
     * 创建栅栏
     * @private
     */
    _createFence(element) {
        const fence = new Fence();
        return fence;
    }

    /**
     * 创建矩阵
     * @param skuList
     * @private
     */
    _createMatrix(skuList){
        console.log('_createMatrix')
        const m = []
        skuList.forEach(sku => {
            m.push(sku.specs)
        });

        return new Matrix(m);
    }

}

export {
    FenceGroup
}
