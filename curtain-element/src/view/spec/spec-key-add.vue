<template>
  <div class="container">
    <div class="title">创建规格名</div>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item label="规格名名称" prop="name">
              <el-input size="medium" v-model="form.name" placeholder="请填写规格名名称"></el-input>
            </el-form-item>
            <el-form-item label="规格名描述" prop="name">
              <el-input size="medium" v-model="form.description" placeholder="请填写规格名描述"></el-input>
            </el-form-item>
            <el-form-item label="单位" prop="unit">
              <el-input size="medium" v-model="form.unit" placeholder="去哪个填写单位，如：英寸"></el-input>
            </el-form-item>
            <el-form-item label="是否标准">
              <el-switch
                v-model="isStandard"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="标准"
                inactive-text="非标准"
              ></el-switch>
            </el-form-item>
            <el-form-item class="submit">
              <el-button
                v-permission="{ permission: ['创建规格名'], type: 'disabled' }"
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
import SpecKey from '@/model/spec-key'

export default {
  data() {
    return {
      isStandard: true,
      form: {
        name: '',
        description: '',
        unit: '',
        standard: 1,
      },
    }
  },
  watch: {
    isStandard(newVal) {
      this.form.standard = newVal ? 1 : 0
    },
  },
  methods: {
    async submitForm(formName) {
      const postData = { ...this.form }
      postData.standard = this.isStandard ? 1 : 0
      const res = await SpecKey.addSpecKey(postData)
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
        this.resetForm(formName)
        this.$confirm('是否跳转到规格名列表页？', '提示', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'info',
        }).then(async () => {
          this.$router.push({ path: '/spec/key/list' })
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
    height: 50px;
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
