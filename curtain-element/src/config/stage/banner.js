const bannerRouter = {
  route: null,
  name: null,
  title: 'Banner管理',
  type: 'folder',
  icon: 'el-icon-postcard',
  filePath: 'view/banner/',
  order: null,
  inNav: true,
  children: [
    {
      title: 'banner列表',
      type: 'view',
      name: 'bannerList',
      route: '/banner/list',
      filePath: 'view/banner/banner-list.vue',
      inNav: true,
      icon: '',
    },
    {
      title: '创建banner',
      type: 'view',
      name: 'bannerAdd',
      route: '/banner/add',
      filePath: 'view/banner/banner-add.vue',
      inNav: true,
      icon: '',
    },
  ],
}

export default bannerRouter
