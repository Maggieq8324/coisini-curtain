/**
 * @Description Address
 * @author coisini
 * @date Sep 1, 2021
 * @Version 1.0
 */
class Address {
    static STORAGE_KEY = 'address'

    /**
     * 获取缓存地址
     * @returns {any}
     */
    static getLocal() {
        const address = wx.getStorageSync(Address.STORAGE_KEY)
        return address ? address : null
    }

    /**
     * 缓存地址
     * @param address
     */
    static setLocal(address) {
        wx.setStorageSync(Address.STORAGE_KEY, address)
    }
}

export {
    Address
}