/**
 * sku规格字典决策
 */
import {SkuCode} from "./sku-code";
import {CellStatus} from "../../core/enum";
import {SkuPending} from "./sku-pending";
import {Joiner} from "../../utils/joiner";

export class Judger {

    fenceGroup
    pathDict = [] //规格字典
    skuPending // 用户sku选择

    constructor(fenceGroup) {
        this.fenceGroup = fenceGroup;
        this._initPathDict();
        this._initSkuPending();
    }

    /**
     * 用户是否选择完整的sku
     * @returns {boolean}
     */
    isSkuIntact() {
        return this.skuPending.isIntact()
    }

    /**
     * 获取当前已选的规格值
     * @returns {*[]}
     */
    getCurrentValues() {
        return this.skuPending.getCurrentSpecValues()
    }

    /**
     * 当前缺失的规格名
     * @returns {*[]}
     */
    getMissingKeys() {
        const missingKeysIndex = this.skuPending.getMissingSpecKeysIndex();
        return missingKeysIndex.map(i => {
            return this.fenceGroup.fences[i].title
        })
    }

    /**
     * 初始化用户sku选择
     * @private
     */
    _initSkuPending() {
        const specsLength = this.fenceGroup.fences.length // 规格长度
        this.skuPending = new SkuPending(specsLength);
        const defaultSku = this.fenceGroup.getDefaultSku();
        if (!defaultSku) {
            return
        }

        this.skuPending.init(defaultSku);
        this._initSelectedCell()
        this.judge(null, null, null, true)
        // console.log("skupending", this.skuPending)
    }

    /**
     * 设置默认sku规格
     * @private
     */
    _initSelectedCell() {
        this.skuPending.pending.forEach(cell => {
            this.fenceGroup.setCellStatusById(cell.id, CellStatus.SELECTED)
        })
    }

    /**
     * 初始化路径字典
     */
    _initPathDict() {
        this.fenceGroup.spu.sku_list.forEach(s => {
            const skuCode = new SkuCode(s.code);
            this.pathDict = this.pathDict.concat(skuCode.totalSeqments);
        })
        // console.log(this.pathDict)
    }

    /**
     * cell点击决策
     * @param cell
     * @param x
     * @param y
     */
    judge(cell, x, y, isInit = false) {
        if (!isInit) {
            this._changeCurrentCellStatus(cell, x, y)
        }

        this.fenceGroup.eachCell((cell, x, y) =>{
            const path = this._findPotentialPath(cell, x, y)
            const isIn = this._isInDict(path);

            if (!path) {
                return
            }
            
            if (isIn) {
                this.fenceGroup.setCellStatusByXY(x, y, CellStatus.WAITING);
            } else {
                this.fenceGroup.setCellStatusByXY(x, y, CellStatus.FORBIDDEN);
            }
        })
    }

    /**
     * 获取完整的sku
     * @returns {*}
     */
    getDeterminateSku() {
        const code = this.skuPending.getSkuCode();
        return this.fenceGroup.getSku(code);
    }

    /**
     * 字典查找潜在路径
     * @private
     */
    _isInDict(path) {
        return this.pathDict.includes(path);
    }

    /**
     * 寻找潜在路径（待确认的SKU路径）
     * 当前的cell，不需要再判断潜在路径
     * 对于某个cell，他的潜在路径应该是他自己加上其他行的已选cell
     * 对于某个cell，不是需要考虑当前行其它cell是否已选
     * @private
     */
    _findPotentialPath(cell, x, y) {
        const joiner = new Joiner('#')
        for (let i = 0; i < this.fenceGroup.fences.length; i++) {
            const selected = this.skuPending.findSelectedCellByX(i);
            if (x === i) {
                // 当前行
                if (this.skuPending.isSelected(cell, x)) {
                    // 当前cell被选中直接renturn
                    return;
                }

                const cellCode = this._getCellCode(cell.spec)
                joiner.join(cellCode);
            } else {
                // 其它行
                if (selected) {
                    const selectedCellCode = this._getCellCode(selected.spec);
                    joiner.join(selectedCellCode);
                }
            }
        }

        return joiner.getStr();
    }

    /**
     * 获取单元格code
     * @returns {string}
     */
    _getCellCode(spec) {
        return spec.key_id + '-' + spec.value_id;
    }

    /**
     * 修改当前cell状态
     * 正选和反选
     * @param cell
     * @param x
     * @param y
     * @private
     */
    _changeCurrentCellStatus(cell, x, y) {
        if (cell.status === CellStatus.WAITING) {
            this.fenceGroup.setCellStatusByXY(x, y, CellStatus.SELECTED)
            this.skuPending.insertCell(cell, x);
        }
        if (cell.status === CellStatus.SELECTED) {
            this.fenceGroup.setCellStatusByXY(x, y, CellStatus.WAITING)
            this.skuPending.removeCell(x);
        }
    }
}