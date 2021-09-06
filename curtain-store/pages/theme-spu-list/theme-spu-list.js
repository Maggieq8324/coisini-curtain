// pages/spu-list/spu-list.js
import {Theme} from "../../models/theme";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        empty: false,
        paging: null,
        loading: true,
        loadingType: 'loading',
        topImg: String,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function (options) {
        const tName = options.tname;
        await this.initThemeData(tName);
    },

    /**
     *
     * @param tName
     * @returns {Promise<void>}
     */
    async initThemeData(tName) {
        const data = await Theme.getThemeSpuByName(tName);

        if (data && data.spu_list.length !== 0) {
            wx.lin.renderWaterFlow(data.spu_list);
            this.setData({
                loadingType: 'end',
                topImg: data.internal_top_img,
                descriptions: this.splitDescription(data.description)
            })
        } else {
            this.empty();
        }
    },

    empty() {
        wx.lin.showEmptyScreen({
            text: '该分类暂时还没有商品'
        })
    },

    /**
     * 描述
     * @param description
     * @returns {*[]|*|string[]}
     */
    splitDescription(description) {
        if (!description) {
            return [];
        }

        return description.split('#');
    },

    onLoadImg(event) {
        const {height, width} = event.detail;
        this.setData({
            h: height,
            w: width,
        })
    }
})