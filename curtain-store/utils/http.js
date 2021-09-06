import {config} from "../config/config";
import {promisic} from "../utils/util";
import {Token} from "../models/token";
import {codes} from "../config/exception-config";
import {HttpException} from "../core/http-exception";

/**
 * Http请求
 */
class Http {
    static async request({
                             url,
                             data,
                             refetch = true, // 授权失败二次重发
                             method = 'GET',
                             throwError = false // 自定义异常抛出
                         }) {
        let res;
        try {
            res = await promisic(wx.request)({
                url: `${config.apiBaseUrl}${url}`,
                data,
                method,
                header: {
                    'content-type': 'application/json',
                    appkey: config.appkey,
                    'authorization': `Bearer ${wx.getStorageSync('token')}`
                }
            })
        } catch (e) {
            /**
             * 该处catch能捕捉无网异常
             * 不能捕捉服务端返回的异常，如参数错误
             */
            if (throwError) {
                throw new HttpException(-1, codes[-1]);
            }

            Http.showError(-1);
            return null;
        }

        const code = res.statusCode.toString();
        if (code.startsWith('2')) {
            return res.data;
        } else {
            if (code === '401') {
                /**
                 * 令牌失效
                 * 二次重发
                 */
                if (refetch) {
                    await Http._refetch({
                        url,
                        data,
                        method
                    });
                }
            } else {
                if (throwError) {
                    throw new HttpException(res.data.code, res.data.message, code);
                }
                if (code === '404') {
                    if (res.data.code !== undefined) {
                        return null;
                    }
                    return res.data
                }

                /**
                 * 提示错误
                 * @type {string | number}
                 */
                const error_code = res.data.code;
                Http.showError(error_code, res.data);
            }
        }


        return res.data;
    }

    /**
     * 重发
     * @param data
     * @returns {Promise<*>}
     * @private
     */
    static async _refetch(data) {
        const token = new Token();
        await token.getTokenFromServer();
        data.refetch = false;
        return await Http.request(data);
    }

    /**
     * 错误提示
     * @param error_code
     * @param serverError
     */
    static showError(error_code, serverError) {
        let tip; // 提示消息

        if (!error_code) {
            // 服务异常
            tip = codes[9999];
        } else {
            if (codes[error_code] === undefined) {
                // 未重定义
                tip = serverError.message;
            } else {
                // 前端重定义
                tip = codes[error_code];
            }
        }

        wx.showToast({
            icon: "none",
            title: tip,
            duration: 2000
        })
    }

}

export {
    Http
}