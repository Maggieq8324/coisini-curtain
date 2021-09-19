import moment from 'moment'

// 设置语言为中文
moment.locale('zh-cn')

/**
 * @param {number} hours
 */
export function getDateAfterHours(hours) {
  const now = new Date()
  return new Date(now.setHours(now.getHours() + hours))
}
/**
 * @param {number} days
 */
export function getDateAfterDays(days) {
  const now = new Date()
  return new Date(now.setHours(now.getHours() + days * 24))
}

/**
 * UTC时间转换
 * @returns {string}
 * @param datetime UTC时间
 * @param dateSeprator 日期拼接符
 * @param timeSeprator 时间拼接符
 * @Eexample dateFormat("2021-09-03T22:42:05.659+00:00", "/", ":")
 *          dateFormat("2021-09-03T22:42:05.659+00:00")
 */
export function transTimestamp(datetime, dateSeprator = '/', timeSeprator = ':') {
  if (datetime) {
    const date = new Date(datetime)
    const year = `${date.getUTCFullYear()}`
    let month = `${date.getUTCMonth() + 1}`
    let day = `${date.getUTCDate()}`
    let hour = `${date.getUTCHours()}`
    let minute = `${date.getUTCMinutes()}`
    let second = `${date.getUTCSeconds()}`

    if (month.length === 1) {
      month = `0${month}`
    }
    if (day.length === 1) {
      day = `0${day}`
    }
    if (day.length === 1) {
      day = `0${day}`
    }
    if (hour.length === 1) {
      hour = `0${hour}`
    }
    if (minute.length === 1) {
      minute = `0${minute}`
    }
    if (second.length === 1) {
      second = `0${second}`
    }
    return `${year}${dateSeprator}${month}${dateSeprator}${day} ${hour}${timeSeprator}${minute}${timeSeprator}${second}`
  }
}
