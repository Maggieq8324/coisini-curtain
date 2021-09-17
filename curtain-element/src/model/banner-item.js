import { post, get, put, _delete } from '@/lin/plugin/axios'

class BannerItem {
  static async addBannerItem(data) {
    const res = await post('v1/banner-item', data)
    return res
  }

  static async getBannerItem(id) {
    const res = await get(`v1/banner-item/${id}`)
    return res
  }

  static async editBannerItem(id, data) {
    const res = await put(`v1/banner-item/${id}`, data)
    return res
  }

  static async deleteBannerItem(id) {
    const res = await _delete(`v1/banner-item/${id}`)
    return res
  }

  static async getBannersItem(page = 0, count = 10) {
    const res = await get('v1/banner-item/page', {
      page,
      count,
    })
    return res
  }
}

export default BannerItem
