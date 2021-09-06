import {promisic} from "./util";
import {px2rpx} from "../miniprogram_npm/lin-ui/utils/util";

/**
 * 获取窗口参数
 * @returns {Promise<{screenWidth: number, windowHeight: number, screenHeight: number, windowWidth: number}>}
 */
const getSystemSize = async function () {
    const res = await promisic(wx.getSystemInfo)()
    return {
        windowHeight: res.windowHeight,
        windowWidth:res.windowWidth,
        screenWidth: res.screenWidth,
        screenHeight: res.screenHeight,
    }
}

/**
 * 动态计算窗口高度
 */
const getWindowHeightRpx = async function() {
    const res = await getSystemSize()
    return px2rpx(res.windowHeight)
}

export {
    getSystemSize,
    getWindowHeightRpx
}