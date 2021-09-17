import { get, put, _delete } from '@/lin/plugin/axios'

class ThirdUser {
  static async getThirdUser(id) {
    const res = await get(`v1/user/${id}`)
    return res
  }

  static async editThirdUser(id, data) {
    const res = await put(`v1/user/${id}`, data)
    return res
  }

  static async deleteThirdUser(id) {
    const res = await _delete(`v1/user/${id}`)
    return res
  }

  static async getThirdUsers(page = 0, count = 10) {
    const res = await get('v1/user/page', { page, count })
    return res
  }

  static async search({ keyword, page, count }) {
    const res = await get('v1/user/search', {
      keyword,
      page,
      count,
    })
    return res
  }
}

export default ThirdUser
