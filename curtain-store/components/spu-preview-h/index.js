// components/spu-preview-h/index.js
Component({
    /**
     * 组件的属性列表
     */
    externalClasses: ['l-class'],
    properties: {
        spu: Object
    },

    /**
     * 组件的初始数据
     */
    data: {},

    /**
     * 组件的方法列表
     */
    methods: {
        onImgLoad(event) {
            const {width, height} = event.detail
            this.setData({
                w:340,
                h:340*height/width
            })
        },
        onItemTap(event) {
            // const pid = event.currentTarget.dataset.pid
            // this.triggerEvent("itemtap", {
            //     pid
            // }, {
            //     bubbles: true,
            //     composed: true
            // })
            wx.navigateTo({
                url: `/pages/detail/detail?pid=${this.properties.spu.id}`
            })
        },
    }
})
