import { post, get, _delete, put } from '@/lin/plugin/axios'

class Spu {
  static async addSpu(data) {
    const res = await post('v1/spu', data)
    return res
  }

  static async getSpu(id) {
    const res = await get(`v1/spu/${id}`)
    return res
  }

  static async getDetail(id) {
    const res = await get(`v1/spu/${id}/detail`)
    return res
  }

  static async editSpu(id, data) {
    const res = await put(`v1/spu/${id}`, data)
    return res
  }

  static async deleteSpu(id) {
    const res = await _delete(`v1/spu/${id}`)
    return res
  }

  static async getSpus(page = 0, count = 10) {
    const res = await get('v1/spu/page', { page, count })
    return res
  }

  static async getList() {
    const res = await get('v1/spu/list')
    return res
  }

  static async getSpecKeys(id) {
    const res = await get('v1/spu/key', { id })
    return res
  }

  static async addSpecKey(data) {
    const res = await post('v1/spu/key', data)
    return res
  }
}

export default Spu
