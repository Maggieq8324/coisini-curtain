const activityRouter = {
  route: null,
  name: null,
  title: '活动管理',
  type: 'folder', // 类型: folder, tab, view
  icon: 'el-icon-notebook-1',
  filePath: 'view/activity/', // 文件路径
  order: null,
  inNav: true,
  children: [
    {
      title: '活动列表',
      type: 'view',
      name: 'activityList',
      route: '/activity/list',
      filePath: 'view/activity/activity-list.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
    },
    {
      title: '优惠券模板列表',
      type: 'view',
      name: 'couponTemplateList',
      route: '/coupon-template/list',
      filePath: 'view/coupon/coupon-template-list.vue',
      inNav: true,
      icon: 'iconfont icon-tushuguanli',
    },
  ],
}

export default activityRouter
