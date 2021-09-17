<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>订单详情</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        <el-divider></el-divider>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
              <el-form-item label="订单号" prop="order_no">
                <el-input disabled size="medium" v-model="form.order_no"></el-input>
              </el-form-item>

              <el-form-item label="图片" prop="snap_img">
                <el-image style="height:100px;width:100px;" :src="form.snap_img"></el-image>
              </el-form-item>

              <el-form-item label="标题" prop="snap_title">
                <el-input disabled size="medium" v-model="form.snap_title"></el-input>
              </el-form-item>

              <el-form-item label="预付编号" prop="prepay_id">
                <el-input disabled size="medium" v-model="form.prepay_id"></el-input>
              </el-form-item>

              <el-form-item label="总价格" prop="total_price">
                <el-input-number disabled v-model="form.total_price" :precision="2" :step="0.1"></el-input-number>
              </el-form-item>

              <el-form-item label="交易价格" prop="final_total_price">
                <el-input-number disabled v-model="form.final_total_price" :precision="2" :step="0.1"></el-input-number>
              </el-form-item>

              <el-form-item label="总数量" prop="total_count">
                <el-input-number disabled v-model="form.total_count" :step="1" step-strictly></el-input-number>
              </el-form-item>

              <el-form-item label="状态" prop="status">
                <div class="tags">
                  <el-tag :type="computeTagType(form.status)">{{ form.status | orderStatus }}</el-tag>
                  <el-tag style="margin-left:5px;" v-if="form.expired && form.status === 1" type="danger" size="mini"
                    >过期</el-tag
                  >
                </div>
              </el-form-item>

              <el-form-item label="地址" prop="snap_address">
                <div v-if="form.snap_address" class="address">
                  <div class="header">
                    <span class="name">{{ form.snap_address.user_name }}</span>
                    <span class="mobile">{{ form.snap_address.mobile }}</span>
                  </div>
                  <div class="detail">
                    {{ form.snap_address.province }} {{ form.snap_address.city }}{{ form.snap_address.county }}
                    **************
                    <!-- {{ form.snap_address.detail }} -->
                  </div>
                </div>
              </el-form-item>

              <el-form-item label="详情" prop="snap_items">
                <el-table v-if="form.snap_items" stripe :data="form.snap_items">
                  <el-table-column fixed="left" prop="id" label="id" width="100"></el-table-column>
                  <el-table-column prop="img" label="图片" width="100">
                    <template v-if="scope.row.img" slot-scope="scope">
                      <img class="display_img" :src="scope.row.img" :alt="scope.row.img" />
                    </template>
                  </el-table-column>
                  <el-table-column prop="title" label="标题" width="100"></el-table-column>
                  <el-table-column prop="single_price" label="单价" width="100"></el-table-column>
                  <el-table-column prop="final_price" label="交易价" min-width="100"></el-table-column>
                  <el-table-column fixed="right" prop="count" label="数量" width="100"></el-table-column>
                </el-table>
              </el-form-item>

              <el-form-item class="submit">
                <el-button
                  :disabled="actionDisabled"
                  v-permission="{ permission: ['更新订单状态'], type: 'disabled' }"
                  type="primary"
                  @click="changeOrderStatus"
                  >{{ actionName }}</el-button
                >
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import order from '@/model/order'
import StickyTop from '@/component/base/sticky-top/sticky-top'

export default {
  components: { StickyTop },
  props: {
    orderId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      form: {},
    }
  },
  computed: {
    actionName() {
      if (this.form.status) {
        switch (this.form.status) {
          case 2:
            return '发货'
          case 3:
            return '完成'
          default:
            return '不可操作'
        }
      } else {
        return '不可操作'
      }
    },
    actionDisabled() {
      if (
        // eslint-disable-next-line
        this.form.status &&
        (this.form.status === 2 || this.form.status === 3)
      ) {
        return false
      }
      return true
    },
  },
  async created() {
    this.$nextTick(async () => {
      if (!this.isCreate) {
        this.getOrder()
      }
    })
  },
  methods: {
    async changeOrderStatus() {
      const res = await order.changeOrderStatus(this.orderId, this.form.status + 1)
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
      }
      this.getOrder()
    },
    async getOrder() {
      const res = await order.getOrder(this.orderId)
      this.form = res
    },
    // eslint-disable-next-line
    async submitForm(formName) {},
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    back() {
      this.$emit('editClose')
    },
    computeTagType(num) {
      switch (num) {
        case 0:
          return ''
        case 1:
          return 'info'
        case 2:
          return 'success'
        case 3:
          return 'success'
        case 4:
          return 'success'
        case 5:
          return 'warning'
        default:
          return ''
      }
    },
  },
  filters: {
    orderStatus(num) {
      switch (num) {
        case 0:
          return '全部'
        case 1:
          return '待支付'
        case 2:
          return '已支付'
        case 3:
          return '已发货'
        case 4:
          return '已完成'
        case 5:
          return '已取消'
        default:
          return '全部'
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 0;
}

.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
    }
  }

  .wrap {
    padding: 20px;
  }

  .submit {
    float: left;
  }
}

.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;
    display: inline-flex;
    align-content: space-around;
    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .id {
      margin-right: 15px;
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
.white-color {
  background-color: #fff;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}

.display_img {
  max-width: 100px;
  max-height: 50px;
  width: auto;
  height: auto;
}

.address {
  .header {
    display: flex;
    align-content: center;
    .name {
      font-weight: 600;
      margin-right: 40px;
    }
    .mobile {
      font-weight: 600;
    }
  }
}
</style>
