// components/cell/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    cell: Object,
    y: Number,
    x: Number,
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    /**
     * 监听单元格
     * @param event
     */
    onTap(event) {
      // 通过事件向父组件传递
      this.triggerEvent('celltap', {
        cell: this.properties.cell,
        x: this.properties.x,
        y: this.properties.y
      },{
        bubbles: true, // 开启冒泡
        composed: true // 跨越组件边界向realm组件传递
      })
    }
  }
})
