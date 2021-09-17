import { post, get, put, _delete } from '@/lin/plugin/axios'

class Theme {
  static async addTheme(data) {
    const res = await post('v1/theme', data)
    return res
  }

  static async addThemeSpu(data) {
    const res = await post('v1/theme/spu', data)
    return res
  }

  static async getTheme(id) {
    const res = await get(`v1/theme/${id}`)
    return res
  }

  static async editTheme(id, data) {
    const res = await put(`v1/theme/${id}`, data)
    return res
  }

  static async deleteTheme(id) {
    const res = await _delete(`v1/theme/${id}`)
    return res
  }

  static async getThemes(page = 0, count = 10) {
    const res = await get('v1/theme/page', { page, count })
    return res
  }

  static async getSpus(id) {
    const res = await get('v1/theme/spus', { id })
    return res
  }

  static async getSpuList(id) {
    const res = await get('v1/theme/spu/list', { id })
    return res
  }

  static async deleteSpu(id) {
    const res = await _delete(`v1/theme/spu/${id}`)
    return res
  }
}

export default Theme
