/**
 * @Description 订单提交
 * @author coisini
 * @date Sep 2, 2021
 * @Version 1.0
 */
export class OrderPost {

    total_price // 总价
    final_total_price // 最终价
    coupon_id
    sku_info_list = []
    address = {}

    constructor(totalPrice, finalTotalPrice, couponId, skuInfoList, address) {
        this.total_price = totalPrice;
        this.final_total_price = finalTotalPrice;
        this.coupon_id = couponId;
        this.sku_info_list = skuInfoList;
        this._fillAddress(address);
    }

    _fillAddress(address) {
        this.address.user_name = address.userName;
        this.address.national_code = address.nationalCode;
        this.address.postal_code = address.postalCode;
        this.address.city = address.cityName;
        this.address.province = address.provinceName;
        this.address.county = address.countyName;
        this.address.detail = address.detailInfo;
        this.address.mobile = address.telNumber;
    }

}