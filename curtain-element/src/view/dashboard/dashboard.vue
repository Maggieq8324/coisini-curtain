<template>
  <div class="container">
    <img alt="无奈" class="img" src="@/assets/image/dashboard.jpg" />
<!--    <el-row :gutter="20">-->
<!--      <el-col :span="16">-->
<!--        <div class="grid-content bg-purple">-->
<!--          <group-column></group-column>-->
<!--        </div>-->
<!--      </el-col>-->
<!--      <el-col :span="8" class="none">-->
<!--        <div class="grid-content bg-purple">-->
<!--          <radar></radar>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--    <el-row :gutter="20">-->
<!--      <el-col :span="16">-->
<!--        <el-row :gutter="20">-->
<!--          <el-col :span="12">-->
<!--            <div class="grid-content bg-purple">-->
<!--              <tiny-area-july></tiny-area-july>-->
<!--            </div>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <div class="grid-content bg-purple">-->
<!--              <tiny-area-june></tiny-area-june>-->
<!--            </div>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <el-row :gutter="20">-->
<!--          <el-col :span="24">-->
<!--            <div class="grid-content bg-purple">-->
<!--              <line-charts></line-charts>-->
<!--            </div>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--      </el-col>-->
<!--      <el-col :span="8" class="none">-->
<!--        <div class="grid-content bg-purple">-->
<!--          <rose></rose>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->
  </div>
</template>

<script>
import GroupColumn from './grouped-column'
import Radar from './radar'
import TinyAreaJuly from './tiny-area-july'
import TinyAreaJune from './tiny-area-june'
import LineCharts from './line-chart'
import Rose from './rose'

export default {
  components: {
    Rose,
    GroupColumn,
    Radar,
    TinyAreaJuly,
    TinyAreaJune,
    LineCharts,
  },
  data() {
    return {
      list1: [
        { type: 1, value: 100 },
        { type: 2, value: 120 },
        { type: 3, value: 230 },
        { type: 4, value: 150 },
        { type: 5, value: 280 },
        { type: 6, value: 190 },
        { type: 7, value: 130 },
        { type: 8, value: 150 },
        { type: 9, value: 160 },
        { type: 10, value: 140 },
        { type: 11, value: 190 },
      ],
      list2: [
        { type: 1, value: 80 },
        { type: 2, value: 120 },
        { type: 3, value: 210 },
        { type: 4, value: 120 },
        { type: 5, value: 130 },
        { type: 6, value: 190 },
        { type: 7, value: 130 },
        { type: 8, value: 150 },
        { type: 9, value: 160 },
        { type: 10, value: 140 },
        { type: 11, value: 190 },
      ],
    }
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 20px;
  text-align: center;
  position: relative;

  img {
    position: absolute;
    margin: auto;
    top: 500px;
    left: 0;
    right: 0;
    bottom: 0;
    width: 300px;
    height: auto;
  }

  .el-row {
    .el-col {
      .grid-content {
        background: #ffffff;
        box-shadow: 0px 2px 14px 0px rgba(243, 243, 243, 1);
        border-radius: 8px;
        min-height: 36px;
        margin-bottom: 20px;
      }
    }
  }
}
@media screen and (max-width: 1200px) {
  .none {
    display: none;
  }
}
</style>
