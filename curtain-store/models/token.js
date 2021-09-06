/**
 * @Description Token
 * @author coisini
 * @date Sep 1, 2021
 * @Version 1.0
 */

import {config} from "../config/config";
import {promisic} from "../utils/util";

export class Token {
    /**
     * 静默登录 code-> 登录 -> token -> storage
     * 1. token不存在 2.token 过期时间
     */

    constructor() {
        this.tokenUrl = config.apiBaseUrl + "/token";
        this.verifyUrl = config.apiBaseUrl + "/token/verify";
    }

    /**
     * token是否可用
     */
    async verify() {
        const token = wx.getStorageSync('token');

        if (!token) {
            await this.getTokenFromServer();
        } else {
            await this._verifyFromServer(token);
        }
    }

    /**
     * 获取服务端token
     * @returns {Promise<void>}
     */
    async getTokenFromServer() {
        // code
        const r = await wx.login({});
        const code = r.code

        const res = await promisic(wx.request)({
            url: this.tokenUrl,
            method: 'POST',
            data: {
                account: code,
                type: 0
            },
        });

        wx.setStorageSync('token', res.data.token);
        return res.data.token;
    }

    /**
     * 校验token
     * @param token
     * @returns {Promise<void>}
     * @private
     */
    async _verifyFromServer(token) {
        const res = await promisic(wx.request)({
            url: this.verifyUrl,
            method: 'POST',
            data: {
                token
            }
        });

        const valid = res.data.is_valid;
        if (!valid) {
            return this.getTokenFromServer();
        }
    }

}