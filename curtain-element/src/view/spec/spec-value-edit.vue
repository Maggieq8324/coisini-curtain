<template>
  <el-dialog :append-to-body="true" :before-close="handleClose" :visible.sync="visible">
    <div style="margin-top:-25px;">
      <div class="dialog-title">
        <span>{{ isCreate ? '创建规格值' : '更新规格值' }}</span>
      </div>
      <el-form :model="form" status-icon ref="specValueForm" label-width="100px" @submit.native.prevent>
        <el-form-item label="规格值名称" prop="value" :rules="rules.Null">
          <el-input size="medium" v-model="form.value" placeholder="请填写规格值名称" :readonly="!hasAuth"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="img">
          <upload-imgs ref="uploadEle" :max-num="maxNum" :value="initData" :disabled="!hasAuth"/>
        </el-form-item>
        <el-form-item label="扩展" prop="extend">
          <el-input size="medium" v-model="form.extend" placeholder="请填写规格值扩展" :readonly="!hasAuth"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer" style="padding-left:5px;">
      <el-button type="primary" @click="submitForm('specValueForm')" v-permission="{ permission: '更新规格值' }"
        >确 定</el-button
      >
      <el-button @click="resetForm('specValueForm')" v-permission="{ permission: '更新规格值' }">重 置</el-button>
    </div>
  </el-dialog>
</template>
<script>
import SpecValue from '@/model/spec-value'
import rules from '@/core/util/rules-1.0'
import Auth from '@/core/util/auth'
import UploadImgs from '@/component/base/upload-image'

export default {
  components: { UploadImgs },
  props: {
    dialogFormVisible: {
      type: Boolean,
      default: false,
    },
    isCreate: {
      type: Boolean,
      default: false,
    },
    specId: {
      type: Number,
      default: 0,
    },
    specValueId: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    visible: {
      get() {
        return this.dialogFormVisible
      },
      set() {},
    },
  },
  data() {
    return {
      hasAuth: Auth.hasAuth('更新规格值'),
      display: true,
      form: {
        spec_id: 0,
        value: '',
        extend: '',
        img: '',
      },
      initData: [],
      maxNum: 1,
      rules: {
        ...rules
      }
    }
  },
  async mounted() {
    if (!this.isCreate) {
      const res = await SpecValue.getSpecValue(this.specValueId)
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
            form.spec_id = this.specId
            res = await SpecValue.addSpecValue(form)
          } else {
            res = await SpecValue.editSpecValue(this.specValueId, form)
          }
          if (res.code < window.MAX_SUCCESS_CODE) {
            this.$message.success(`${res.message}`)
            this.$emit('dialogClose')
          }
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    handleClose(done) {
      done()
      this.$emit('dialogClose')
    },
  },
}
</script>
<style lang="scss" scoped>
.dialog-title {
  color: $parent-title-color;
  font-size: 16px;
  margin-bottom: 20px;
}
</style>
