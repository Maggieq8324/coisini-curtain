import { post, get, put } from '@/lin/plugin/axios'

class Order {
  static async addOrder(data) {
    const res = await post('v1/order', data)
    return res
  }

  static async getOrder(id) {
    const res = await get(`v1/order/${id}/detail`)
    return res
  }

  static async editOrder(id, data) {
    const res = await put(`v1/order/${id}`, data)
    return res
  }

  static async changeOrderStatus(id, status) {
    const res = await put(`v1/order/status?id=${id}&status=${status}`)
    return res
  }

  static async getOrders(page = 0, count = 10) {
    const res = await get('v1/order/page', { page, count })
    return res
  }

  static async search({ keyword, page, count, start, end }) {
    const res = await get('v1/order/search', {
      keyword,
      page,
      count,
      start,
      end,
    })
    return res
  }
}

export default Order
