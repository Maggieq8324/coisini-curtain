import {Http} from "../utils/http";

/**
 * 分类
 */
export class Categories{
    roots = [] // 一级分类
    subs = []

    async getAll() {
        const data = await Http.request({
            url:`/category/all`
        })

        this.roots = data.roots
        this.subs = data.subs
    }

    /**
     * 获取所有一级分类
     * @returns {[]}
     */
    getRoots() {
        return this.roots
    }

    /**
     * 获取一级分类
     * @param rootId
     * @returns {*}
     */
    getRoot(rootId) {
        return this.roots.find(r => String(r.id) === String(rootId));
    }

    /**
     * 获取二级分类
     * @param parentId
     * @returns {*[]}
     */
    getSubs(parentId) {
        return this.subs.filter(sub => String(sub.parent_id) === String(parentId));
    }

}