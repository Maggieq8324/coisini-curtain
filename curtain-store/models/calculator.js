/**
 * @Description 购物车价格合计
 * @author coisini
 * @date Aug 30, 2021
 * @Version 1.0
 */
import {accAddition, accMultiply} from "../utils/number";

export class Calculator {
    totalPrice = 0 // 总价
    totalSkuCount = 0 // 总数量
    cartItems = [] // 购物车Item

    constructor(cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * 计算价格
     */
    calc() {
        this.cartItems.forEach(item => {
            this.push(item);
        })
    }

    /**
     * 获取总价
     * @returns {number}
     */
    getTotalPrice () {
        return this.totalPrice;
    }

    /**
     * 获取总数量
     * @returns {number}
     */
    getTotalSkuCount() {
        return this.totalSkuCount;
    }

    push(cartItem) {
        let partTotalPrice = 0
        if (cartItem.sku.discount_price) {
            partTotalPrice = accMultiply(cartItem.count, cartItem.sku.discount_price)
        } else {
            partTotalPrice = accMultiply(cartItem.count, cartItem.sku.price)
        }
        this.totalPrice = accAddition(this.totalPrice, partTotalPrice)
        this.totalSkuCount += cartItem.count
    }
}