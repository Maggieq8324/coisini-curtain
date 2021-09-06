import {Http} from "../utils/http";

class Category {
    static async getGridCategoryC() {
        return await Http.request({
            url: `/category/grid/all`
        })
    }
}

export {
    Category
}