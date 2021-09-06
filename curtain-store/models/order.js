/**
 * @Description Order
 * @author coisini
 * @date Sep 1, 2021
 * @Version 1.0
 */
import {OrderException} from "../core/order-exception";
import {OrderExceptionType, OrderStatus} from "../core/enum";
import {accAddition} from "../utils/number";
import {Http} from "../utils/http";
import {Paging} from "../utils/paging";

export class Order {
    orderItems // 服务器获取的Item
    localItemCount // 本地缓存Item数量

    constructor(orderItems, localItemCount) {
        this.orderItems = orderItems;
        this.localItemCount = localItemCount;
    }

    /**
     * 订单子项校验
     */
    checkOrderIsOk() {
        this.orderItems.forEach(item => {
            item.isOk();
        })
    }

    /**
     * 内部订单校验
     * @private
     */
    _orderIsOk() {
        this._emptyOrder();
        this._containNotOnSaleItem();
    }

    /**
     * 订单提交
     * @param orderPost
     * @returns {Promise<null>}
     */
    static async postOrderToServer(orderPost) {
        return await Http.request({
            url: '/order',
            method: 'POST',
            data: orderPost,
            throwError: true
        })
    }

    /**
     * 获取订单sku信息
     * @returns {Uint8Array | BigInt64Array | {count, id}[] | Float64Array | Int8Array | Float32Array | Int32Array | Uint32Array | Uint8ClampedArray | BigUint64Array | Int16Array | Uint16Array}
     */
    getOrderSkuInfoList() {
        return this.orderItems.map(item => {
            return {
                id: item.skuId,
                count: item.count
            }
        })
    }

    /**
     * 获取总价
     * @returns {*}
     */
    getTotalPrice() {
        return this.orderItems.reduce((pre, item) => {
            const price = accAddition(pre, item.finalPrice);
            return price;
        }, 0);
    }

    /**
     * 获取所有分类总价
     * @param categoryIdList
     * @returns {number|*}
     */
    getTotalPriceByCategoryIdList(categoryIdList) {
        if (categoryIdList.length === 0) {
            return 0;
        }
        // 衣服、鞋子、书籍
        const price = categoryIdList.reduce((pre, cur) => {
            const eachPrice = this.getTotalPriceEachCategory(cur);
            return accAddition(pre, eachPrice);
        }, 0);
        return price;
    }

    /**
     * 单分类总价
     * @param categoryId
     * @returns {*}
     */
    getTotalPriceEachCategory(categoryId) {
        const price = this.orderItems.reduce((pre, orderItem) => {
            const itemCategoryId = this._isItemInCategories(orderItem, categoryId)
            if (itemCategoryId) {
                return accAddition(pre, orderItem.finalPrice);
            }
            return pre;
        }, 0)
        return price;
    }

    /**
     * Item 是否属于当前分类
     * @param orderItem
     * @param categoryId
     * @returns {boolean}
     * @private
     */
    _isItemInCategories(orderItem, categoryId) {
        if (orderItem.categoryId === categoryId) {
            return true;
        }
        if (orderItem.rootCategoryId === categoryId) {
            return true;
        }
        return false;
    }


    /**
     * 空订单校验
     * @private
     */
    _emptyOrder() {
        if (this.orderItems.length === 0) {
            throw new OrderException('订单中没有任何商品', OrderExceptionType.EMPTY);
        }
    }

    /**
     * 是否包含下架的商品
     * @private
     */
    _containNotOnSaleItem() {
        if (this.orderItems.length !== this.localItemCount) {
            throw new OrderException('服务器返回订单商品数量与实际不相符，可能是有商品已下架', OrderExceptionType.NOT_ON_SALE);
        }
    }

    /**
     * 已支付数量
     * @returns {Promise<PaymentItem|number>}
     */
    static async getPaidCount() {
        const orderPage = await Http.request({
            url: `/order/by/status/${OrderStatus.PAID}`,
            data:{
                start:0,
                count:1
            }
        })
        return orderPage.total
    }

    /**
     * 待支付数量
     * @returns {Promise<PaymentItem|number>}
     */
    static async getUnpaidCount() {
        const orderPage = await Http.request({
            url: `/order/status/unpaid`,
            data:{
                start:0,
                count:1
            }
        })
        return orderPage.total
    }

    /**
     * 已发货数量
     * @returns {Promise<PaymentItem|number>}
     */
    static async getDeliveredCount() {
        const orderPage = await Http.request({
            url: `/order/by/status/${OrderStatus.DELIVERED}`,
            data: {
                start:0,
                count:1
            }
        })
        return orderPage.total
    }

    /**
     * 获取已取消分页订单
     * @returns {Paging}
     */
    static getPagingCanceled() {
        return new Paging({
            url:`/order/status/canceled`
        })
    }

    /**
     * 获取订单明细
     * @param oid
     * @returns {Promise<null|*>}
     */
    static async getDetail(oid) {
        return Http.request({
            url: `/order/detail/${oid}`
        })
    }

    /**
     * 根据订单状态查询分析订单
     * @param status
     * @returns {Paging}
     */
    static getPagingByStatus(status) {
        return new Paging({
            url:`/order/by/status/${status}`
        })
        // return Http.request({
        // })
    }

    /**
     * 获取待支付分页订单
     * @returns {Paging}
     */
    static getPagingUnpaid() {
        return new Paging({
            url:`/order/status/unpaid`
        })
    }


}