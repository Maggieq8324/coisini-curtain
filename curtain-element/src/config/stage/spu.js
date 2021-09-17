const spuRouter = {
  route: null,
  name: null,
  title: 'SPU管理',
  type: 'folder', // 类型: folder, tab, view
  icon: 'el-icon-s-goods',
  filePath: 'view/spu/', // 文件路径
  order: null,
  inNav: true,
  children: [
    {
      title: 'SPU列表',
      type: 'view',
      name: 'spuList',
      route: '/spu/list',
      filePath: 'view/spu/spu-list.vue',
      inNav: true,
      icon: '',
    },
  ],
}

export default spuRouter
