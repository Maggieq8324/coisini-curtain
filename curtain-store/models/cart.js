/**
 * 购物车
 */
import {Sku} from "./sku";

export class Cart{
    static SKU_MIN_COUNT = 1
    static SKU_MAX_COUNT = 77 // 最大单品数量
    static CART_ITEM_MAX_COUNT = 77 // 最大购物车数量
    static STORAGE_KEY = 'cart'

    // 代理模式, 间接操作缓存
    _cartData = null

    /**
     * 单例模式
     * @returns {Cart}
     */
    constructor() {
        if (typeof Cart.instance === 'object') {
            return Cart.instance
        }
        Cart.instance = this
        return this
    }

    /**
     * 获取缓存Item
     * @returns
     */
    getAllCartItemFromLocal() {
        return this._getCartData();
    }

    /**
     * 服务端获取Sku实时数据
     */
    async getAllSkuFromServer() {
        const cartData = this._getCartData();
        if (cartData.items.length === 0) {
            return [];
        }

        const skuIds = this.getSkuIds();
        const serverData = await Sku.getSkusByIds(skuIds);
        this._refreshByServerData(serverData); // 服务端数据刷新
        this._refreshStorage(); // 刷新缓存
        return this._getCartData(); // 获取购物车数据
    }

    /**
     * 获取已选择的SkuId
     * @returns {[]|*[]}
     */
    getCheckedSkuIds() {
        const cartData = this._getCartData();
        if (cartData.items.length === 0) {
            return [];
        }
        const skuIds = [];
        cartData.items.forEach(item => {
            if (item.checked) {
                skuIds.push(item.sku.id);
            }
        })
        return skuIds;
    }

    /**
     * 移除选中的Item
     */
    removeCheckedItems() {
        const cartData = this._getCartData();
        for (let i = 0; i < cartData.items.length; i++) {
            if (cartData.items[i].checked) {
                cartData.items.splice(i, 1);
            }
        }
        this._refreshStorage();
    }

    /**
     * 通过skuId获取Sku的数量
     * @param skuId
     * @returns {*}
     */
    getSkuCountBySkuId(skuId) {
        const cartData = this._getCartData()
        const item = cartData.items.find(item => item.skuId === skuId)
        if (!item) {
            console.error('在订单里寻找CartItem时不应当出现找不到的情况')
        }
        return item.count
    }

    /**
     * 通过服务端数据刷新
     * @param serverData
     * @private
     */
    _refreshByServerData(serverData) {
        const cartData = this._getCartData();
        cartData.items.forEach(item => {
            this._setLatestCartItem(item, serverData);
        })
    }

    /**
     * 更新Item数据
     * @param item 缓存cart-item数据
     * @param serverData 服务端新数据
     * @private
     */
    _setLatestCartItem(item, serverData) {
        let removed = true;
        for (let sku of serverData) {
            if (sku.id === item.skuId) {
                removed = false;
                item.sku = sku;
                break;
            }
        }

        // 下架
        if (removed) {
            item.sku.online = false;
        }
    }


    /**
     * 获取购物车中的SkuId
     * @returns {(methods.properties.cartItem.skuId|null)[]|*[]}
     */
    getSkuIds() {
        const cartData = this._getCartData()
        if (cartData.items.length === 0) {
            return [];
        }
        return cartData.items.map(item => item.skuId)
    }

    /**
     * 更新Item数量
     * @param skuId
     * @param newCount
     */
    replaceItemCount(skuId, newCount) {
        const oldItem = this.findEqualItem(skuId);
        if (!oldItem) {
            console.error('异常情况，更新CartItem中的数量不应当找不到相应数据');
            return;
        }
        if (newCount < 1) {
            console.error('异常情况，CartItem的Count不可能小于1');
            return;
        }

        // 更新数量，刷新缓存
        oldItem.count = newCount;
        if (oldItem.count >= Cart.SKU_MAX_COUNT) {
            oldItem.count = Cart.SKU_MAX_COUNT;
        }
        this._refreshStorage();
    }

    /**
     * 获取全部勾选的Item
     */
    getCheckedItems() {
        const cartItems = this._getCartData().items;
        const checkedCartItems = [];
        cartItems.forEach(item => {
            if (item.checked) {
                checkedCartItems.push(item);
            }
        })

        return checkedCartItems;
    }

    /**
     * 获取购物车Item数量
     * @returns {number}
     */
    getCartItemCount() {
        return this._getCartData().items.length;
    }

    /**
     * Item 选中事件
     * @param skuId
     */
    checkItem(skuId) {
        const oldItem = this.findEqualItem(skuId);
        oldItem.checked = !oldItem.checked;
        this._refreshStorage();
    }

    /**
     * 是否全选
     * @returns {boolean}
     */
    isAllChecked() {
        let allChecked = true;
        const cartItems = this._getCartData().items;
        for (let item of cartItems) {
            if (!item.checked) {
                allChecked = false;
                break;
            }
        }

        return allChecked;
    }

    /**
     * 全选
     * @param checked
     */
    checkAll(checked) {
        const cartData = this._getCartData()
        cartData.items.forEach(item => {
            item.checked = checked
        })
        this._refreshStorage()
    }

    /**
     * 是否售罄
     * @param item
     * @returns {boolean}
     */
    static isSoldOut(item) {
        return item.sku.stock === 0;
    }

    /**
     * 是否下架
     * @param item
     * @returns {BooleanConstructor | Event}
     */
    static isOnline(item) {
        return item.sku.online;
    }

    /**
     * 是否为空
     * @returns {boolean}
     */
    isEmpty() {
        const cartData = this._getCartData()
        return cartData.items.length === 0;
    }

    /**
     * 添加Item
     * @param newItem
     */
    addItem(newItem) {
        if (this.beyondMaxCartItemCount()) {
            throw new Error('超过购物车最大数量')
        }
        this._pushItem(newItem);
        this._refreshStorage(); // 刷新缓存
    }

    /**
     * 删除Item
     * @param skuId
     */
    removeItem(skuId) {
        const oldItemIndex = this._findEqualItemIndex(skuId);
        const cartData = this._getCartData();
        cartData.items.splice(oldItemIndex, 1);
        this._refreshStorage();
    }

    /**
     * 查找Item
     * @param skuId
     * @returns {number}
     * @private
     */
    _findEqualItemIndex(skuId) {
        const cartData = this._getCartData();
        return cartData.items.findIndex(item => {
            return item.skuId === skuId;
        })
    }

    /**
     * 刷新缓存
     * @private
     */
    _refreshStorage() {
        wx.setStorageSync(Cart.STORAGE_KEY, this._cartData);
    }

    /**
     * 推入商品
     * @param newItem
     * @private
     */
    _pushItem(newItem) {
        const cartData = this._getCartData();

        const oldItem = this.findEqualItem(newItem.skuId);
        if (!oldItem) {
            cartData.items.unshift(newItem);
        } else {
            this._combineItems(oldItem, newItem);
        }
    }

    /**
     * Item查找相同的Sku
     * @param skuId
     */
    findEqualItem(skuId) {
        let oldItem = null
        const items = this._getCartData().items
        for (let i = 0; i < items.length; i++) {
            if (this._isEqualItem(items[i], skuId)) {
                oldItem = items[i];
                break;
            }
        }
        return oldItem;
    }

    /**
     * 判断是否是同一个Item
     * @param oldItem
     * @param skuId
     * @returns {boolean}
     * @private
     */
    _isEqualItem(oldItem, skuId) {
        return oldItem.skuId === skuId;
    }

    /**
     * 同一Item相结合
     * @param oldItem
     * @param newItem
     * @private
     */
    _combineItems(oldItem, newItem) {
        this._plusCount(oldItem, newItem.count);
    }

    /**
     * 同一Item数量相加
     * @param item
     * @param count
     * @private
     */
    _plusCount(item, count) {
        item.count += count;
        if (item.count >= Cart.SKU_MAX_COUNT) {
            item.count = Cart.SKU_MAX_COUNT;
        }
    }

    /**
     * 获取购物车数据
     * 代理模式，如果_cartData存在直接返回
     * 不存在则缓存获取或设置缓存
     * @private
     */
    _getCartData() {
        if (this._cartData !== null) {
            return this._cartData;
        }

        let cartData = wx.getStorageSync(Cart.STORAGE_KEY);
        if (!cartData) {
            cartData = this._initCartDataStorage();
        }

        this._cartData = cartData;
        return cartData;
    }

    /**
     * 初始化购物车缓存
     * @returns {{items: []}}
     * @private
     */
    _initCartDataStorage() {
        const cartData = {
            items: []
        }
        wx.setStorageSync(Cart.STORAGE_KEY, cartData)
        return cartData
    }

    /**
     * 最大购物车数量判断
     * @returns {boolean}
     */
    beyondMaxCartItemCount() {
        const cartData = this._getCartData()
        return cartData.items.length >= Cart.CART_ITEM_MAX_COUNT;
    }
}