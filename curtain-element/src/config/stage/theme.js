const themeRouter = {
  route: null,
  name: null,
  title: '主题管理',
  type: 'folder', // 类型: folder, tab, view
  icon: 'el-icon-s-marketing',
  filePath: 'view/theme/', // 文件路径
  order: null,
  inNav: true,
  children: [
    {
      title: '主题列表',
      type: 'view',
      name: 'themeList',
      route: '/theme/list',
      filePath: 'view/theme/theme-list.vue',
      inNav: true,
      icon: '',
    },
  ],
}

export default themeRouter
