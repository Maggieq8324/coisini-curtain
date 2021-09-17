import { post, get, put, _delete } from '@/lin/plugin/axios'

class ActivityCover {
  async addActivityCover(data) {
    const res = await post('v1/activity-cover', data)
    return res
  }

  async getActivityCover(id) {
    const res = await get(`v1/activity-cover/${id}`)
    return res
  }

  async editActivityCover(id, data) {
    const res = await put(`v1/activity-cover/${id}`, data)
    return res
  }

  async deleteActivityCover(id) {
    const res = await _delete(`v1/activity-cover/${id}`)
    return res
  }

  async getActivityCovers(page = 0, count = 10) {
    const res = await get('v1/activity-cover/page', { page, count })
    return res
  }
}

export default ActivityCover
