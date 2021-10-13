// pages/about/about.js
import {showToast} from "../../utils/ui";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        iphone: '13330582938',
        mail: 'maggieq8324@foxmail.com'
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    onCopyGit() {
        wx.setClipboardData({
            data: this.data.mail
        })
    },

    onCopyCourse() {
        wx.setClipboardData({
            data: this.data.iphone
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
