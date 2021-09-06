import {Http} from "./http";

/**
 * 分页对象
 */
class Paging {
    //不关心细节
    //嗨， 我需要下一页的数据了，你能给我吗
    // 状态
    // 实例化
    // new Paging

    start
    count
    req
    locker = false
    url
    moreData = true
    accumulator = []


    constructor(req, count = 10, start = 0) {
        this.start = start
        this.count = count
        this.req = req
        this.url = req.url
    }

    async getMoreData() {
        if(!this.moreData){
            return
        }
        if(!this._getLocker()){
            return
        }
        const data =await this._actualGetData()
        this._releaseLocker()
        return data
    }

    /**
     * 获取数据
     * @returns {Promise<{moreData: boolean, accumulator: [], items: [], empty: boolean}|{moreData: boolean, accumulator: [], items: ([]|DataTransferItemList), empty: boolean}|null>}
     * @private
     */
    async _actualGetData() {
        const req = this._getCurrentReq()
        let paging = await Http.request(req)

        if(!paging){
            return null
        }
        if(paging.total === 0){
            return {
                empty:true,
                items:[], // 当前数据
                moreData:false, // 是否还有数据
                accumulator:[] // 累加器
            }
        }

        this.moreData = Paging._moreData(paging.total_page, paging.page)
        if(this.moreData){
            this.start += this.count
        }
        this._accumulate(paging.data)
        return {
            empty:false,
            items: paging.data,
            moreData:this.moreData,
            accumulator:this.accumulator
        }
    }

    /**
     * 累加器
     * @param items
     * @private
     */
    _accumulate(items){
        this.accumulator = this.accumulator.concat(items)
    }

    /**
     * 是否还有数据
     * @param totalPage
     * @param pageNum
     * @returns {boolean}
     * @private
     */
    static _moreData(totalPage, pageNum) {
        return pageNum < totalPage - 1
    }

    _getCurrentReq() {
        let url = this.url
        const params = `start=${this.start}&count=${this.count}`
        if(url.includes('?')){
            url += '&' + params
            // contains
        }else{
            url += '?' + params
        }
        this.req.url  = url
        return this.req
    }

    /**
     * 获取锁
     * @returns {boolean}
     * @private
     */
    _getLocker() {
        if (this.locker) {
            return false
        }
        this.locker = true
        return true
    }

    /**
     * 释放锁
     * @private
     */
    _releaseLocker() {
        this.locker = false
    }

}

export {
    Paging
}
