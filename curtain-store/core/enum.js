
const CellStatus = {
    FORBIDDEN: 'forbidden',
    SELECTED: 'selected',
    WAITING: 'waiting'
}

const ShoppingWay = {
    CART: 'cart',
    BUY: 'buy'
}

const SpuListType = {
    THEME: 'theme',
    ROOT_CATEGORY: 'root_category',
    SUB_CATEGORY: 'sub_category',
    LATEST: 'latest'
}

// 地址权限
const AuthAddress = {
    DENY: 'deny',
    NOT_AUTH: 'not_auth',
    AUTHORIZED: 'authorized'
}

// 订单异常类型
const OrderExceptionType = {
    BEYOND_STOCK: 'beyond_stock',
    BEYOND_SKU_MAX_COUNT: 'beyond_sku_max_count',
    BEYOND_ITEM_MAX_COUNT: 'beyond_item_max_count',
    SOLD_OUT: 'sold_out',
    NOT_ON_SALE: 'not_on_sale',
    EMPTY: 'empty'
}

// 优惠劵进入方式
const CouponCenterType = {
    ACTIVITY: 'activity',
    SPU_CATEGORY: 'spu_category'
}

// 优惠劵状态
export const CouponStatus = {
    CAN_COLLECT: 0, // 可领取
    AVAILABLE: 1, // 可使用
    USED: 2, // 已使用
    EXPIRED: 3 // 已过期
}

// 优惠劵类型
const CouponType = {
    FULL_MINUS: 1, // 满多少减多少
    FULL_OFF: 2, // 满多少打多少折
    NO_THRESHOLD_MINUS: 3 // 无门槛
}

// 优惠劵操作类型
const CouponOperate = {
    PICK: 'pick', // 已选择
    UNPICK: 'unpick' // 未选择
}

// 订单状态
const OrderStatus = {
    ALL: 0,
    UNPAID: 1,
    PAID: 2,
    DELIVERED: 3,
    FINISHED: 4,
    CANCELED: 5,
}

// Banner类型
const BannerItemType = {
    SPU: 1,
    THEME: 2,
    SPU_LIST: 3
}

export {
    CellStatus,
    ShoppingWay,
    SpuListType,
    AuthAddress,
    OrderExceptionType,
    CouponCenterType,
    CouponType,
    CouponOperate,
    OrderStatus,
    BannerItemType
}