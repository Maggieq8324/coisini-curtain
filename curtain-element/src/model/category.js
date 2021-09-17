import { post, get, put, _delete } from '@/lin/plugin/axios'

class Category {
  static async addCategory(data) {
    const res = await post('v1/category', data)
    return res
  }

  static async getCategory(id) {
    const res = await get(`v1/category/${id}`)
    return res
  }

  static async editCategory(id, data) {
    const res = await put(`v1/category/${id}`, data)
    return res
  }

  static async deleteCategory(id) {
    const res = await _delete(`v1/category/${id}`)
    return res
  }

  /**
   * 分页获取分类数据
   * @param page
   * @param count
   * @param root 1: 父类
   * @returns {Promise<*>}
   */
  static async getCategories(page = 0, count = 10, root = 1) {
    const res = await get('v1/category/page', {
      page,
      count,
      root,
    })
    return res
  }

  /**
   * 分页获取子分类数据
   * @param page
   * @param count
   * @param id 父分类id
   * @returns {Promise<*>}
   */
  static async getSubCategories(page = 0, count = 10, id = 1) {
    const res = await get('v1/category/sub-page', {
      page,
      count,
      id,
    })
    return res
  }

  static async getList() {
    const res = await get('v1/category/list')
    return res
  }
}

export default Category
