import { post, get, put, _delete } from '@/lin/plugin/axios'

class Activity {
  static async addActivity(data) {
    const res = await post('v1/activity', data)
    return res
  }

  static async getActivity(id) {
    const res = await get(`v1/activity/${id}`)
    return res
  }

  static async getActivityDetail(id) {
    const res = await get(`v1/activity/${id}/detail`)
    return res
  }

  static async editActivity(id, data) {
    const res = await put(`v1/activity/${id}`, data)
    return res
  }

  static async deleteActivity(id) {
    const res = await _delete(`v1/activity/${id}`)
    return res
  }

  static async getActivities(page = 0, count = 10) {
    const res = await get('v1/activity/page', { page, count })
    return res
  }
}

export default Activity
