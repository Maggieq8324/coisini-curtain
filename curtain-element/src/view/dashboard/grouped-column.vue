<template>
  <div id="grouped-column"></div>
</template>

<script>
// eslint-disable-next-line import/no-extraneous-dependencies
import { GroupColumn } from '@antv/g2plot'

export default {
  name: 'GroupedColumn',
  data() {
    return {
      data: [
        { month: '1月', area: '东区', value: 1200 },
        { month: '2月', area: '东区', value: 800 },
        { month: '3月', area: '东区', value: 600 },
        { month: '4月', area: '东区', value: 1140 },
        { month: '5月', area: '东区', value: 880 },
        { month: '6月', area: '东区', value: 800 },
        { month: '7月', area: '东区', value: 900 }
      ],
    }
  },
  mounted() {
    const canvas = new GroupColumn('grouped-column', {
      title: {
        text: '月销售信息',
        visible: true,
      },
      data: this.data,
      // groupField: 'area',
      xField: 'month',
      yField: 'value',
      meta: {
        month: {
          alias: '月份',
        },
        value: {
          alias: '数量',
        },
      },
      forceFit: true,
      color: ['#4577FF', '#00C292', '#FEC108'],
      columnSize: 12,
      legend: {
        visible: true,
        position: 'top-right',
      },
    })
    canvas.render()
  },
}
</script>

<style scoped></style>
