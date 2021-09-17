<template>
  <div id="line"></div>
</template>

<script>
// eslint-disable-next-line import/no-extraneous-dependencies
import { Line } from '@antv/g2plot'

export default {
  name: 'Line',
  data() {
    return {
      data: [
        { month: '1月', value: 100, type: '东区' },
        { month: '2月', value: 80, type: '东区' },
        { month: '3月', value: 100, type: '东区' },
        { month: '4月', value: 70, type: '东区' },
        { month: '5月', value: 120, type: '东区' },
        { month: '6月', value: 80, type: '东区' },
        { month: '7月', value: 60, type: '东区' }
      ],
    }
  },
  mounted() {
    new Line('line', {
      title: {
        visible: true,
        text: '销售趋势分析',
      },
      data: this.data,
      forceFit: true,
      // seriesField: 'type',
      xField: 'month',
      yField: 'value',
      smooth: true,
      color: ['#4577FF', '#00C292', '#FEC108'],
      legend: {
        visible: true,
        position: 'top-right',
      },
    }).render()
  },
}
</script>

<style scoped></style>
