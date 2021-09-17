<template>
  <div id="radar"></div>
</template>

<script>
// eslint-disable-next-line import/no-extraneous-dependencies
import { Rose } from '@antv/g2plot'

export default {
  name: 'Rose',
  data() {
    return {
      data: [
        {
          type: '布窗帘',
          value: 56,
        },
        {
          type: '纱窗帘',
          value: 56,
        },
        {
          type: '无缝帘',
          value: 56,
        },
        {
          type: '垂直帘',
          value: 56,
        },
        {
          type: '木竹帘',
          value: 56,
        },
        {
          type: '墙布',
          value: 14,
        },
      ],
    }
  },
  mounted() {
    new Rose('radar', {
      title: {
        text: '9月分类销售数',
        visible: true,
      },
      data: this.data,
      radiusField: 'value',
      categoryField: 'type',
      colorField: 'type',
    }).render()
  },
}
</script>

<style scoped></style>
