<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>{{ getBannerItemTitle }}</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        <el-divider></el-divider>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="bannerItemForm" label-width="100px" @submit.native.prevent>
              <el-form-item label="名称" prop="name">
                <el-input size="medium" v-model="form.name" placeholder="请填写名称" :readonly="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="关键字" prop="keyword" :rules="rules.Null">
                <el-input size="medium" v-model="form.keyword" placeholder="请填写关键字" :readonly="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="类型" prop="type" :rules="rules.InterNum">
                <el-input size="medium" v-model="form.type" placeholder="请填写类型" :readonly="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="图片" prop="img">
                <upload-imgs :max-num="maxNum" ref="uploadEle" :value="initData" :disabled="!hasAuth"/>
              </el-form-item>
              <el-form-item class="submit">
                <el-button
                  v-permission="{ permission: ['创建Banner item', '更新Banner item'] }"
                  type="primary"
                  @click="submitForm('bannerItemForm')"
                  >保 存</el-button
                >
                <el-button @click="resetForm('bannerItemForm')"
                           v-permission="{ permission: ['创建Banner item', '更新Banner item'] }">重 置</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import BannerItem from '@/model/banner-item'
import UploadImgs from '@/component/base/upload-image'
import StickyTop from '@/component/base/sticky-top/sticky-top'
import Auth from '@/core/util/auth'
import rules from '@/core/util/rules-1.0'

export default {
  components: {
    UploadImgs,
    StickyTop,
  },
  props: {
    editID: {
      type: Number,
    },
    isCreate: {
      type: Boolean,
      default: false,
    },
    bannerId: {
      type: Number,
      default: null,
    }
  },
  computed: {
    getBannerItemTitle() {
      if (!this.hasAuth) {
        return 'BannerItem详情'
      }

      return this.isCreate ? '添加BannerItem' : '修改BannerItem'
    }
  },
  data() {
    return {
      maxNum: 1,
      form: {
        img: '',
        keyword: '',
        type: null,
      },
      rules: {
        ...rules,
        minWidth: 10,
        minHeight: 10,
        maxSize: 5,
      },
      initData: [],
      hasAuth: Auth.hasAuth(['创建Banner item', '更新Banner item']),
    }
  },
  async mounted() {
    if (!this.isCreate) {
      const res = await BannerItem.getBannerItem(this.editID)
      this.initData = [
        {
          id: res.id,
          display: res.img,
        },
      ]
      this.form = res
    }
  },
  methods: {
    async getValue() {
      const val = await this.$refs.uploadEle.getValue()
      if (val && val.length > 0) {
        this.form.img = val[0].display
      }
    },
    async submitForm(formName) {
      await this.getValue()
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const form = { ...this.form }
          let res
          if (this.isCreate) {
            form.banner_id = this.bannerId
            res = await BannerItem.addBannerItem(form)
          } else {
            res = await BannerItem.editBannerItem(this.editID, form)
          }
          if (res.code < window.MAX_SUCCESS_CODE) {
            this.$message.success(`${res.message}`)
            this.resetForm('form')
            this.$emit('editClose')
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
</style>
