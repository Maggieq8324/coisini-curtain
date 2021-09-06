/**
 * 存储用户sku选择
 */
import {Cell} from "./cell";
import {Joiner} from "../../utils/joiner";

export class SkuPending {

    pending = []
    size // 完整的sku大小

    /**
     * 构造方法
     */
    constructor(size) {
        this.size = size
    }

    /**
     * 初始化默认sku
     * @param sku
     */
    init(sku) {
        for (let i = 0; i < sku.specs.length; i++) {
            const cell = new Cell(sku.specs[i])
            this.insertCell(cell, i)
        }
    }

    /**
     * 当前已选的规格值
     * @returns {*[]}
     */
    getCurrentSpecValues() {
        return this.pending.map(cell => {
            return cell ? cell.spec.value : null
        })
    }

    /**
     * 当前缺失的规格名序号
     * @returns {[]}
     */
    getMissingSpecKeysIndex() {
        const keysIndex = [];
        for (let i = 0; i < this.size; i++) {
            if(!this.pending[i]){
                keysIndex.push(i);
            }
        }
        return keysIndex;
    }

    /**
     * 获取sku-code
     * @returns {string}
     */
    getSkuCode() {
        const joiner = new Joiner('#')
        this.pending.forEach(cell => {
            const cellCode = cell.getCellCode();
            joiner.join(cellCode);
        })
        return joiner.getStr();
    }

    /**
     * 用户是否确认完整的sku
     */
    isIntact() {
        for (let i = 0; i < this.size; i++) {
            if (this._isEmptyPart(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 已选规格是否存在
     * @param index
     * @returns {boolean}
     * @private
     */
    _isEmptyPart(index) {
        return !this.pending[index];
    }

    /**
     * 插入用户选择
     * @param cell
     * @param x 当前行
     */
    insertCell(cell, x) {
        this.pending[x] = cell;
    }

    /**
     * 移除用户选择
     * @param x 当前行
     */
    removeCell(x) {
        this.pending[x] = null;
    }

    /**
     * 查找其它行已选择的cell
     * @param x
     * @returns {*}
     */
    findSelectedCellByX(x) {
        return this.pending[x];
    }

    /**
     * 当前cell是否被选中
     * @param cell
     * @param x
     * @returns {boolean}
     */
    isSelected(cell, x) {
        const pendingCell = this.pending[x];

        if (!pendingCell) {
            return false;
        }

        return cell.id === pendingCell.id;
    }

}