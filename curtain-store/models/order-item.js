/**
 * @Description Order Item 订单结算页Item
 * @author coisini
 * @date Sep 1, 2021
 * @Version 1.0
 */
import {Cart} from "./cart";
import {accMultiply} from "../utils/number";
import {OrderExceptionType} from "../core/enum";
import {OrderException} from "../core/order-exception";

export class OrderItem {
    count = 0
    singleFinalPrice // sku 单价
    finalPrice // sku总价 = singleFinalPrice * count
    online
    title
    img
    stock
    categoryId
    rootCategoryId
    specs
    skuId

    cart = new Cart();

    constructor(sku, count) {
        this.title = sku.title;
        this.img = sku.img;
        this.skuId = sku.id;
        this.stock = sku.stock;
        this.online = sku.online;
        this.categoryId = sku.category_id;
        this.rootCategoryId = sku.root_catgory_id;
        this.specs = sku.specs;

        this.count = count;

        this.singleFinalPrice = this.ensureSingleFinalPrice(sku);
        this.finalPrice = accMultiply(this.count, this.singleFinalPrice);
    }

    /**
     * Order - Item 校验
     */
    isOk() {
        this._checkStock();
        this._beyondMaxSkuCount();
    }

    /**
     * 最大购买数量校验
     * @private
     */
    _beyondMaxSkuCount() {
        if (this.count > Cart.SKU_MAX_COUNT) {
            throw new OrderException('超过商品最大购买数量', OrderExceptionType.BEYOND_SKU_MAX_COUNT)
        }
    }

    /**
     * 库存校验
     * @private
     */
    _checkStock() {
        if (this.stock === 0) {
            throw new OrderException('当前商品已售罄', OrderExceptionType.SOLD_OUT)
        }
        if (this.count > this.stock) {
            throw new OrderException('购买商品数量超过最大库存', OrderExceptionType.BEYOND_STOCK)
        }
    }

    /**
     * 确认Sku单价
     * @param sku
     * @returns {methods.properties.spu.price|*}
     */
    ensureSingleFinalPrice(sku) {
        if (sku.discount_price) {
            return sku.discount_price; // 折扣价
        }
        return sku.price; // 原价
    }
}