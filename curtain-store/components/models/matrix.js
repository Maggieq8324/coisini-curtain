/**
 * 矩阵
 */

class Matrix {

    m

    /**
     * 构造函数
     * @param martix
     */
    constructor(martix) {
        this.m = martix;
    }

    /**
     * 获取行号
     * @returns {*}
     */
    get rowsNum() {
        return this.m.length
    }

    /**
     * 获取列数
     * @returns {*}
     */
    get colsNum() {
        return this.m[0].length;
    }

    /**
     * 获取矩阵元素
     * @param cb 回调
     */
    each(cb) {
        for (let j = 0; j < this.colsNum; j++) { // 列
            for (let i = 0; i < this.rowsNum; i++) { // 行
                const element = this.m[i][j];
                cb(element, i ,j)
            }
        }
    }

    /**
     * 矩阵转置
     */
    transpose() {
        const desArr = [];
        for (let j = 0; j < this.colsNum; j++) {
            desArr[j] = [];
            for (let i = 0; i < this.rowsNum; i++) {
                desArr[j][i] = this.m[i][j];
            }
        }

        return desArr;
    }
}

export {
    Matrix
}