<template>
  <div id="rose"></div>
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
          value: 2700,
        },
        {
          type: '纱窗帘',
          value: 2500,
        },
        {
          type: '无缝帘',
          value: 1898,
        },
        {
          type: '垂直帘',
          value: 2376,
        },
        {
          type: '木竹帘',
          value: 1760,
        },
        {
          type: '墙布',
          value: 1500,
        },
      ],
    }
  },
  mounted() {
    new Rose('rose', {
      title: {
        text: '库存余量',
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
