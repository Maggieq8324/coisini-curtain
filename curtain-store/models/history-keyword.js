/**
 * 搜索缓存
 */
export class HistoryKeyword {
    static MAX_ITEM_COUNT = 20
    static KEY = 'keywords'

    keywords = []

    /**
     * 构造函数
     * 单例模式
     * @returns {HistoryKeyword}
     */
    constructor() {
        if(typeof HistoryKeyword.instance === 'object'){
            return HistoryKeyword.instance
        }
        HistoryKeyword.instance = this
        this.keywords = this._getLocalKeywords()
        return this
    }

    /**
     * 缓存中写入数据
     * 去重
     * @param keyword
     */
    save(keyword) {
        const items = this.keywords.filter(k => {
            return k === keyword
        })
        if (items.length !== 0) {
            return
        }
        if (this.keywords.length >= HistoryKeyword.MAX_ITEM_COUNT) {
            this.keywords.pop()
        }
        this.keywords.unshift(keyword)
        this._refreshLocal()
    }

    /**
     * 获取临时缓存
     * @returns {[]}
     */
    get() {
        return this.keywords
    }

    /**
     * 清除缓存
     */
    clear() {
        this.keywords = []
        this._refreshLocal()
    }

    /**
     * 刷新缓存
     * @private
     */
    _refreshLocal() {
        wx.setStorageSync(HistoryKeyword.KEY, this.keywords)
    }

    /**
     * 获取程序缓存
     * @returns {*[]|any}
     * @private
     */
    _getLocalKeywords() {
        const keywords = wx.getStorageSync(HistoryKeyword.KEY)
        if (!keywords) {
            wx.setStorageSync(HistoryKeyword.KEY, [])
            return []
        }
        return keywords
    }

}