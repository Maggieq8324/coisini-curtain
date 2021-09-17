<template>
  <div class="container">
    <div class="title">
      <span>{{ isCreate ? '创建优惠卷' : '更新优惠卷' }}</span>
      <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
    </div>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item label="标题" prop="title">
              <el-input size="medium" v-model="form.title" placeholder="请填写优惠卷标题"></el-input>
            </el-form-item>

            <el-form-item label="满减额" prop="full_money">
              <el-input-number v-model="form.full_money" :precision="2" :step="1"></el-input-number>
            </el-form-item>

            <el-form-item label="优惠额" prop="minus">
              <el-input-number v-model="form.minus" :precision="2" :step="1"></el-input-number>
            </el-form-item>

            <el-form-item label="折扣" prop="discount">
              <el-input-number v-model="form.discount" :precision="2" :step="0.1" :max="1"></el-input-number>
            </el-form-item>

            <el-form-item label="类型" prop="type">
              <!-- <el-input size="medium" v-model="form.type" placeholder="请填写优惠卷类型"></el-input> -->
              <el-select v-model="form.type" placeholder="请填写类型">
                <el-option v-for="(item, index) in types" :key="item" :label="item" :value="index + 1"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="时间">
              <el-date-picker
                v-model="range"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="描述" prop="description">
              <el-input size="medium" v-model="form.description" placeholder="请填写优惠卷描述"></el-input>
            </el-form-item>

            <el-form-item class="submit">
              <el-button
                v-permission="{ permission: ['创建优惠卷', '更新优惠卷'], type: 'disabled' }"
                type="primary"
                @click="submitForm('form')"
                >保 存</el-button
              >
              <el-button @click="resetForm('form')">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs'
import Coupon from '@/model/coupon'

export default {
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    couponId: {
      type: String,
      default: null,
    },
    activityId: {
      type: String,
      default: null,
    },
    templateData: {
      type: Object,
      default: null,
    },
  },
  data() {
    return {
      form: {
        name: '',
        title: '',
        full_money: null,
        minus: null,
        discount: null,
        type: null,
        activity_id: null,
      },
      types: [],
      range: '',
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            },
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            },
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            },
          },
        ],
      },
    }
  },
  async mounted() {
    this.types = Coupon.types
    if (!this.isCreate) {
      const res = await Coupon.getCoupon(this.couponId)
      this.form = res
      const end = new Date(res.end_time)
      const start = new Date(res.start_time)
      this.range = [start, end]
    } else {
      this.form = this.templateData
    }
  },
  methods: {
    async submitForm(formName) {
      this.form.activity_id = this.activityId
      try {
        const postData = { ...this.form }
        if (postData.discount === 0) {
          postData.discount = null
        }
        if (postData.minus === 0) {
          postData.minus = null
        }
        if (postData.full_money === 0) {
          postData.full_money = null
        }
        postData.start_time = this.dateFormat(this.range[0])
        postData.end_time = this.dateFormat(this.range[1])
        let res
        if (this.isCreate) {
          res = await Coupon.addCoupon(postData)
        } else {
          res = await Coupon.editCoupon(
            this.couponId,
            // eslint-disable-next-line
            postData,
          )
        }
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          this.resetForm(formName)
          this.$confirm('是否返回上一页?', '提示', {
            confirmButtonText: '是',
            cancelButtonText: '否',
            type: 'info',
          }).then(async () => {
            this.$emit('editClose')
          })
        }
      } catch (error) {
        console.error(error)
      }
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    back() {
      this.$emit('editClose')
    },
    dateFormat(val) {
      return dayjs(val).format('YYYY-MM-DD HH:mm:ss')
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
</style>
