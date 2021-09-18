import store from '@/store'

/**
 * 是否拥有权限
 * @param permission
 * @param user
 * @param permissions
 * @returns {boolean|*}
 */
function hasAuth(permission, user = store.state.user || {}, permissions = store.state.permissions) {
  if (user.admin) {
    return true
  }

  if (typeof permission === 'string') {
    return permissions.includes(permission)
  }
  if (permission instanceof Array) {
    return permission.some(auth => permissions.indexOf(auth) >= 0)
  }
  return false
}

export default { hasAuth }
