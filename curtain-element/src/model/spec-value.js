import { post, get, put, _delete } from '../lin/plugin/axios'

class SpecValue {
  static async addSpecValue(data) {
    const res = await post('v1/spec-value', data)
    return res
  }

  static async getSpecValue(id) {
    const res = await get(`v1/spec-value/${id}`)
    return res
  }

  static async editSpecValue(id, data) {
    const res = await put(`v1/spec-value/${id}`, data)
    return res
  }

  static async deleteSpecValue(id) {
    const res = await _delete(`v1/spec-value/${id}`)
    return res
  }

  static async getSpecValues(page = 0, count = 10) {
    const res = await get('v1/spec-value/page', {
      page,
      count,
    })
    return res
  }

  static async getBySpecKeyId(keyId) {
    const res = await get(`v1/spec-value/by/spec-key/${keyId}`)
    return res
  }
}
export default SpecValue
