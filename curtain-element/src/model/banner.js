import { post, get, put, _delete } from '@/lin/plugin/axios'

class Banner {
  static async addBanner(data) {
    const res = await post('v1/banner', data)
    return res
  }

  static async getBanner(id) {
    const res = await get(`v1/banner/${id}`)
    return res
  }

  static async editBanner(id, data) {
    const res = await put(`v1/banner/${id}`, data)
    return res
  }

  static async deleteBanner(id) {
    const res = await _delete(`v1/banner/${id}`)
    return res
  }

  static async getBanners(page = 0, count = 10) {
    const res = await get('v1/banner/page', {
      page,
      count,
    })
    return res
  }
}

export default Banner
