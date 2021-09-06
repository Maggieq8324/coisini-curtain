// pages/theme/theme.js
import {Theme} from "../../models/theme";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        _noResource: false,
        _theme: null,
        _tplName: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function (options) {
        const tname = options.tname;
        wx.lin.showLoading({
            color: '#157658',
            type: 'flash',
            fullScreen: true
        })

        // 加载主题 - SPU
        const theme = await Theme.getThemeSpuByName(tname);
        this.setData({
            _theme: theme,
            _tplName: theme.tpl_name
        })

        wx.lin.hideLoading();
    },

    initCategoryData(theme) {

    },

    onShareAppMessage() {

    }

})