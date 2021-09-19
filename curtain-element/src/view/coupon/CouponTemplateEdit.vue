<template>
  <div class="container">
    <div class="title">
      <span>{{ couponTemplateEditTitle }}</span>
      <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
    </div>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :model="form" status-icon ref="couponTemplateForm" label-width="100px" @submit.native.prevent>
            <el-form-item label="类型" prop="type" :rules="rules.Null">
              <el-select v-model="form.type" placeholder="请填写类型" :disabled="!hasAuth">
                <el-option v-for="(item, index) in types" :key="item" :label="item" :value="index + 1"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="标题" prop="title" :rules="rules.Null">
              <el-input size="medium" v-model="form.title" placeholder="请填写优惠券标题" :disabled="!hasAuth"></el-input>
            </el-form-item>

            <el-form-item label="描述" prop="description" :rules="rules.Null">
              <el-input size="medium" v-model="form.description" placeholder="请填写优惠券描述" :disabled="!hasAuth"></el-input>
            </el-form-item>

            <el-form-item label="满减额" prop="full_money" v-if="form.type === 1 || form.type === 4">
              <el-input-number v-model="form.full_money" :precision="2" :step="1" :disabled="!hasAuth"></el-input-number>
            </el-form-item>

            <el-form-item label="优惠额" prop="minus" v-if="form.type === 1 || form.type === 3">
              <el-input-number v-model="form.minus" :precision="2" :step="1" :disabled="!hasAuth"></el-input-number>
            </el-form-item>

            <el-form-item label="折扣" prop="discount" v-if="form.type === 2 || form.type === 4">
              <el-input-number v-model="form.discount" :precision="2" :step="0.1" :max="1" :disabled="!hasAuth"></el-input-number>
            </el-form-item>

            <el-form-item class="submit">
              <el-button
                v-permission="{ permission: ['创建优惠券模板', '更新优惠券模板'] }"
                type="primary"
                @click="submitForm('couponTemplateForm')"
                >保 存</el-button
              >
              <el-button @click="resetForm('couponTemplateForm')"
                         v-permission="{ permission: ['创建优惠券模板', '更新优惠券模板'] }">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import Coupon from '@/model/coupon'
import Auth from '@/lin/util/auth'
import rules from '@/lin/util/rules-1.0'

export default {
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    couponTemplateId: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    couponTemplateEditTitle() {
      if (!this.hasAuth) {
        return '优惠券模板详情'
      }

      return this.isCreate ? '创建优惠券模板' : '更新优惠券模板'
    }
  },
  data() {
    return {
      form: {
        title: '',
        description: '',
        full_money: null,
        minus: null,
        discount: null,
        type: null,
      },
      types: [],
      hasAuth: Auth.hasAuth(['创建优惠券模板', '更新优惠券模板']),
      rules: {
        ...rules
      }
    }
  },
  async mounted() {
    this.types = Coupon.types
    if (!this.isCreate) {
      const res = await Coupon.getCouponTemplate(this.couponTemplateId)
      this.form = res
    }
  },
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const form = { ...this.form }
          let res
          if (this.isCreate) {
            form.banner_id = this.bannerId
            res = await Coupon.addCouponTemplate(form)
          } else {
            res = await Coupon.editCouponTemplate(this.couponTemplateId, form)
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
        }
      })
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    back() {
      this.$emit('editClose')
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
