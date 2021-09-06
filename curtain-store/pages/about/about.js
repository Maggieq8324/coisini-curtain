// pages/about/about.js
import {showToast} from "../../utils/ui";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        urlCourse: 'https://www.cnblogs.com/maggieq8324',
        urlGit: 'https://gitee.com/maggieq8324/coisini-curtain.git'
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    onCopyGit() {
        wx.setClipboardData({
            data: this.data.urlGit
        })
    },

    onCopyCourse() {
        wx.setClipboardData({
            data: this.data.urlCourse
        })
    },

    save(event) {
        wx.previewImage({
            urls: ["http://i2.sleeve.7yue.pro/wx.jpg"]
        })
        // wx.saveImageToPhotosAlbum()
    },

    onShareAppMessage: function () {

    }
})