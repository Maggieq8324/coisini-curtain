import {Http} from "../utils/http";

/**
 * @Description User
 * @author coisini
 * @date Sep 3, 2021
 * @Version 1.0
 */
export class User{
    static async updateUserInfo(data) {
        return Http.request({
            url:`/user/wx_info`,
            data,
            method:'POST'
        })
    }
}