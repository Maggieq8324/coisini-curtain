import Utils from '@/core/util/util'
import adminConfig from './admin'
import staticsConfig from './statics'
import bannerConfig from './banner'
import categoryConfig from './category'
import gridCategoryConfig from './grid-categroy'
import specConfig from './spec'
import spuConfig from './spu'
import skuConfig from './sku'
import themeConfig from './theme'
import activityConfig from './activity'

// eslint-disable-next-line import/no-mutable-exports
let homeRouter = [
  {
    route: '/dashboard',
    name: 'DashBoard',
    title: '首页',
    type: 'view',
    icon: 'iconfont icon-weibiaoti--',
    filePath: 'view/dashboard/dashboard.vue',
    order: 0,
    inNav: true,
  },
  {
    title: '日志管理',
    type: 'view',
    name: Symbol('log'),
    route: '/log',
    filePath: 'view/log/log.vue',
    inNav: true,
    icon: 'iconfont icon-rizhiguanli',
    order: 1,
    permission: ['查询所有日志'],
  },
  {
    title: '个人中心',
    type: 'view',
    name: Symbol('center'),
    route: '/center',
    filePath: 'view/center/center.vue',
    inNav: false,
    icon: 'iconfont icon-rizhiguanli',
  },
  {
    title: '404',
    type: 'view',
    name: Symbol('404'),
    route: '/404',
    filePath: 'view/error-page/404.vue',
    inNav: false,
    icon: 'iconfont icon-rizhiguanli',
  },
  staticsConfig,
  bannerConfig,
  categoryConfig,
  gridCategoryConfig,
  specConfig,
  spuConfig,
  skuConfig,
  themeConfig,
  activityConfig,
  adminConfig,
]

// 处理顺序
homeRouter = Utils.sortByOrder(homeRouter)

// 使用 Symbol 处理 name 字段, 保证唯一性
const deepReduceName = target => {
  if (Array.isArray(target)) {
    target.forEach(item => {
      if (typeof item !== 'object') {
        return
      }
      deepReduceName(item)
    })
    return
  }
  if (typeof target === 'object') {
    if (typeof target.name !== 'symbol') {
      // eslint-disable-next-line no-param-reassign
      target.name = target.name || Utils.getRandomStr()
      // eslint-disable-next-line no-param-reassign
      target.name = Symbol(target.name)
    }

    if (Array.isArray(target.children)) {
      target.children.forEach(item => {
        if (typeof item !== 'object') {
          return
        }
        deepReduceName(item)
      })
    }
  }
}

deepReduceName(homeRouter)

export default homeRouter
