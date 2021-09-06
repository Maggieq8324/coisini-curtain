import {Http} from "../utils/http";

class Tag{
    static getSearchTags() {
        return Http.request({
            url:`/tag/type`
        })
    }
}

export {
    Tag
}