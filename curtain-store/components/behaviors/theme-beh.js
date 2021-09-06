const themeBehavior = Behavior({
    behaviors: [],

    properties: {
        theme: Object,
    },

    /**
     * 组件的初始数据
     */
    data: {
        spuList: Array,
        topImg: String,
        randoms: Array
    },

    observers: {
        'theme': function (theme) {
            if (!theme) {
                return
            }
            this.setData({
                spuList: theme.spu_list,
                topImg: theme.internal_top_img,
                descriptions: this.splitDescription(theme.description)
            })
        }
    },

    methods: {
        onGoToSpu(event) {
            const pid = event.currentTarget.dataset.spuId
            wx.navigateTo({
                url: `/pages/detail/detail?pid=${pid}`
            })
        },

        splitDescription(description) {
            if (!description) {
                return []
            }

            return description.split('#');
        }
    }


})

export {
    themeBehavior
}

