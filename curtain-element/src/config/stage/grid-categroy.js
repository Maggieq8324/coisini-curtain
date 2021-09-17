const gridCategoryRouter = {
  route: null,
  name: null,
  title: '六宫格管理',
  type: 'folder',
  icon: 'el-icon-s-grid',
  filePath: 'view/category/',
  order: null,
  inNav: true,
  children: [
    {
      title: '六宫格列表',
      type: 'view',
      name: 'categoryList',
      route: '/grid-category/list',
      filePath: 'view/grid-category/grid-category-list.vue',
      inNav: true,
      icon: '',
    },
  ],
}

export default gridCategoryRouter
