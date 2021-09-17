<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <div class="title">优惠卷列表</div>
        <!-- <el-button @click.prevent="addCoupon" type="primary" plain size="medium">添加优惠卷</el-button> -->
      </div>

      <el-table stripe v-loading="loading" :data="tableData">
        <el-table-column prop="id" label="id" width="100"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="title" label="标题" width="250"></el-table-column>
        <el-table-column prop="full_money" label="满减额" width="150"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="minus" label="优惠额" width="150"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="discount" label="折扣" width="150"></el-table-column>
        <el-table-column prop="type" label="类型" width="150">
          <template slot-scope="scope">{{ scope.row.type | typeFormat }}</template>
        </el-table-column>
        <el-table-column prop="start_time" label="开始时间" width="200">
          <template slot-scope="scope">{{ scope.row.start_time | dateFormat }}</template>
        </el-table-column>
        <el-table-column prop="end_time" label="结束时间" width="200">
          <template slot-scope="scope">{{ scope.row.end_time | dateFormat }}</template>
        </el-table-column>
        <el-table-column
          :show-overflow-tooltip="true"
          prop="description"
          min-width="200"
          label="描述"
        ></el-table-column>
        <el-table-column fixed="right" width="150" label="操作">
          <template slot-scope="scope">
            <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
            <el-button
              v-permission="{ auth: ['删除优惠卷'], type: 'disabled' }"
              @click.prevent="handleDelete(scope.row)"
              type="danger"
              size="mini"
              plain
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <el-table stripe v-loading="loading" :data="templates">
        <el-table-column prop="id" label="id" width="100"></el-table-column>
        <el-table-column prop="title" label="名称" width="250"></el-table-column>
        <el-table-column prop="full_money" label="满减额" width="150"></el-table-column>
        <el-table-column prop="minus" label="优惠额" width="150"></el-table-column>
        <el-table-column prop="discount" label="折扣" width="150"></el-table-column>
        <el-table-column prop="type" label="类型" width="150">
          <template slot-scope="scope">{{ scope.row.type | typeFormat }}</template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="300"></el-table-column>
        <el-table-column fixed="right" width="300" label="操作">
          <template slot-scope="scope">
            <el-button
              v-permission="{ permission: ['创建优惠卷'], type: 'disabled' }"
              @click.prevent="addCoupon(scope.row)"
              type="primary"
              plain
              size="mini"
              >从当前模板创建</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <coupon-edit
      v-else
      @editClose="editClose"
      :templateData="templateData"
      :couponId="couponId"
      :isCreate="isCreate"
      :activityId="activityId"
    ></coupon-edit>
  </div>
</template>

<script>
import dayjs from 'dayjs'
import Coupon from '@/model/coupon'
import CouponEdit from './CouponEdit'

export default {
  components: { CouponEdit },
  props: {
    activityId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      couponId: null,
      isCreate: false,
      tableData: [],
      templates: [],
      showEdit: false,
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true,
      templateData: null,
      loading: false,
    }
  },
  async created() {
    this.$nextTick(() => {
      this.loading = true
      this.getCoupons()
      this.getTemplates()
      this.loading = false
    })
  },
  methods: {
    async getCoupons() {
      const coupons = await Coupon.getListByActivityId(this.activityId)
      this.tableData = coupons
    },
    async getTemplates() {
      const templates = await Coupon.getCouponTemplates()
      this.templates = templates
    },
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      this.getCoupons()
      this.loading = false
    },
    handleEdit(val) {
      this.isCreate = false
      this.couponId = `${val.id}`
      this.showEdit = true
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await Coupon.deleteCoupon(val.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getCoupons()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    addCoupon(templateData) {
      this.templateData = templateData
      this.isCreate = true
      this.couponId = null
      this.showEdit = true
    },
    editClose() {
      this.showEdit = false
      this.getCoupons()
    },
  },
  filters: {
    dateFormat(val) {
      return dayjs(val).format('YYYY-MM-DD HH:mm:ss')
    },
    typeFormat(val) {
      switch (val) {
        case 1:
          return '满减券'
        case 2:
          return '折扣券'
        case 3:
          return '无门槛券'
        case 4:
          return '满金额折扣券'
        default:
          return val
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    align-items: center;

    .title {
      height: 59px;
      line-height: 59px;
      color: $parent-title-color;
      font-size: 16px;
      font-weight: 500;
    }
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
