import { post, get, put, _delete } from '@/lin/plugin/axios'

class SpecKey {
  static async addSpecKey(data) {
    const res = await post('v1/spec-key', data)
    return res
  }

  static async getSpecKey(id) {
    const res = await get(`v1/spec-key/${id}`)
    return res
  }

  static async getSpecKeyDetail(id) {
    const res = await get(`v1/spec-key/${id}/detail`)
    return res
  }

  static async editSpecKey(id, data) {
    const res = await put(`v1/spec-key/${id}`, data)
    return res
  }

  static async deleteSpecKey(id) {
    const res = await _delete(`v1/spec-key/${id}`)
    return res
  }

  static async getSpecKeys(page = 0, count = 10) {
    const res = await get('v1/spec-key/page', {
      page,
      count,
    })
    return res
  }

  static async getList() {
    const res = await get('v1/spec-key/list')
    return res
  }

  static async getBySpuId(spuId) {
    const res = await get(`v1/spec-key/by/spu/${spuId}`)
    return res
  }
}

export default SpecKey
