const categoryRouter = {
  route: null,
  name: null,
  title: '分类管理',
  type: 'folder',
  icon: 'el-icon-guide',
  filePath: 'view/category/',
  order: null,
  inNav: true,
  children: [
    {
      title: '分类列表',
      type: 'view',
      name: 'categoryList',
      route: '/category/list',
      filePath: 'view/category/category-list.vue',
      inNav: true,
      icon: '',
    },
    {
      title: '子分类列表',
      type: 'tab',
      name: 'subCategoryList',
      route: '/sub-category/:id/list',
      filePath: 'view/category/sub-category-list.vue',
      inNav: false,
      icon: '',
    },
  ],
}

export default categoryRouter
