<template>
  <div class="container">
    <div class="title">创建Banner</div>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item label="名称" prop="name">
              <el-input size="medium" v-model="form.name" placeholder="请填写名称"></el-input>
            </el-form-item>
            <el-form-item label="标题" prop="title">
              <el-input size="medium" v-model="form.title" placeholder="请填写标题"></el-input>
            </el-form-item>
            <el-form-item label="主图" prop="img">
              <upload-imgs ref="uploadEle" :max-num="maxNum" :value="initData" />
            </el-form-item>
            <el-form-item label="描述" prop="description">
              <el-input size="medium" v-model="form.description" type="textarea" :rows="4" placeholder="请填写描述">
              </el-input>
            </el-form-item>
            <el-form-item class="submit">
              <el-button
                type="primary"
                v-permission="{ permission: '创建Banner', type: 'disabled' }"
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
import Banner from '@/model/banner'
import UploadImgs from '@/component/base/upload-image'

export default {
  components: { UploadImgs },
  data() {
    return {
      form: {
        name: '',
        title: '',
        description: '',
        img: '',
      },
      initData: [],
      maxNum: 1,
    }
  },
  methods: {
    async getValue() {
      const val = await this.$refs.uploadEle.getValue()
      console.log(val)
      if (val && val.length > 0) {
        this.form.img = val[0].display
      }
    },
    async submitForm(formName) {
      await this.getValue()
      const form = { ...this.form }
      const res = await Banner.addBanner(form)
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
        this.resetForm(formName)
        this.$confirm('是否跳转到Banner列表页？', '提示', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'info',
        }).then(async () => {
          this.$router.push({ path: '/banner/list' })
        })
      }
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    text-indent: 40px;
    border-bottom: 1px solid #dae1ec;
  }

  .wrap {
    padding: 20px;
  }

  .submit {
    float: left;
  }
}
</style>
