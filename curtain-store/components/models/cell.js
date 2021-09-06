/**
 * 栅栏单元格
 */
import {CellStatus} from "../../core/enum";

export class Cell {

    title // 标题
    id
    status = CellStatus.WAITING
    spec
    skuImg

    constructor(spec) {
        this.title = spec.value;
        this.id = spec.value_id;
        this.spec = spec
    }

    /**
     * 获取单元格code
     * @returns {string}
     */
    getCellCode() {
        return this.spec.key_id + '-' + this.spec.value_id;
    }

}