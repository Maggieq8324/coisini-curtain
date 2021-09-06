import {randomArray} from "../../utils/random";
import {themeBehavior} from "../behaviors/theme-beh";

Component({

    behaviors:[themeBehavior],
    /**
     * 组件的属性列表
     */
    properties: {
    },

    /**
     * 组件的初始数据
     */
    data: {
        randoms: Array
    },

    observers: {
        'theme': function (theme) {
            if (!theme) {
                return
            }
            const randoms = this.getRandoms(theme.spu_list.length)
            this.setData({
                randoms: randoms
            })
        }
    },

    /**
     * 组件的方法列表
     */
    methods: {
        getRandoms(size) {
            return randomArray(size)
        },

        onLoadImg(event) {
            const {height, width} = event.detail
            this.setData({
                h: height,
                w: width,
            })
        }
    }
})
