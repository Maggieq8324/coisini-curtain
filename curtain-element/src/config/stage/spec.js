const specRouter = {
  route: null,
  name: null,
  title: '规格管理',
  type: 'folder',
  icon: 'el-icon-paperclip',
  filePath: 'view/spec/',
  order: null,
  inNav: true,
  children: [
    {
      title: '规格名列表',
      type: 'view',
      name: 'specKeyList',
      route: '/spec/key/list',
      filePath: 'view/spec/spec-key-list.vue',
      inNav: true,
      icon: '',
    },
    {
      title: '创建规格名',
      type: 'view',
      name: 'specKeyAdd',
      route: '/spec/key/add',
      filePath: 'view/spec/spec-key-add.vue',
      inNav: true,
      icon: '',
    },
    {
      title: '规格值详情',
      type: 'view',
      name: 'spceKeyValue',
      route: '/spec/key/value/:id',
      filePath: 'view/spec/spec-key-value.vue',
      icon: '',
    },
  ],
}

export default specRouter
