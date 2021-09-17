const skuRouter = {
  route: null,
  name: null,
  title: 'SKU管理',
  type: 'folder', // 类型: folder, tab, view
  icon: 'el-icon-goods',
  filePath: 'view/sku/', // 文件路径
  order: null,
  inNav: true,
  children: [
    {
      title: 'SKU列表',
      type: 'view',
      name: 'skuList',
      route: '/sku/list',
      filePath: 'view/sku/sku-list.vue',
      inNav: true,
      icon: '',
    },
  ],
}

export default skuRouter
