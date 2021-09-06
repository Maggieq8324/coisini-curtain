/**
 * 小程序原生封装
 */

/**
 * 提示
 * @param title
 */
const showToast = function (title) {
    wx.showToast({
        icon: "none",
        duration: 2000,
        title
    })
}

export {
    showToast
}