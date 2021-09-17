<template>
  <el-dialog :append-to-body="true" :before-close="handleClose" :visible.sync="visible">
    <div style="margin-top:-25px;">
      <div class="dialog-title">
        <span>{{ isCreate ? '创建分类' : '更新分类' }}</span>
      </div>
      <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
        <el-form-item label="名称" prop="name">
          <el-input size="medium" v-model="form.name" placeholder="请填写分类名"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="index">
          <el-input size="medium" v-model="form.index" placeholder="请填写分类排序"></el-input>
        </el-form-item>
        <el-form-item label="显示上线" prop="online">
          <el-switch
            v-model="display"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="上线"
            inactive-text="下线"
          ></el-switch>
        </el-form-item>
        <el-form-item label="图片" prop="img">
          <upload-imgs ref="uploadEle" :max-num="maxNum" :value="initData" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input size="medium" v-model="form.description" type="textarea" :rows="1" placeholder="请填写描述">
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer" style="padding-left:5px;">
      <el-button type="primary" @click="submitForm" v-permission="{ permission: '更新分类', type: 'disabled' }"
        >确 定</el-button
      >
      <el-button @click="resetForm('form')">重 置</el-button>
    </div>
  </el-dialog>
</template>
<script>
import Category from '@/model/category'
import UploadImgs from '@/component/base/upload-image'

export default {
  components: {
    UploadImgs,
  },
  props: {
    dialogFormVisible: {
      type: Boolean,
      default: false,
    },
    isCreate: {
      type: Boolean,
      default: false,
    },
    categoryId: {
      type: Number,
      default: 0,
    },
    isSub: {
      type: Boolean,
      default: false,
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
      display: true,
      initData: [],
      maxNum: 1,
      form: {
        name: '',
        is_root: 0,
        parent_id: null,
        description: '',
        online: 1,
        index: null,
      },
    }
  },
  watch: {
    display(val) {
      this.form.online = val ? 1 : 0
    },
  },
  async mounted() {
    if (!this.isCreate) {
      const res = await Category.getCategory(this.categoryId)
      this.form = res
      this.display = res.online === 1
      this.initData = [{ display: res.img }]
    }
    if (this.isSub) {
      this.form.parent_id = this.$route.params.id
      this.form.is_root = 0
    } else {
      this.form.parent_id = null
      this.form.is_root = 1
    }
  },
  methods: {
    async submitForm() {
      await this.getValue()
      const form = { ...this.form }
      let res
      if (this.isCreate) {
        res = await Category.addCategory(form)
      } else {
        res = await Category.editCategory(this.categoryId, form)
      }
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
        this.$emit('dialogClose')
      }
    },
    async getValue() {
      const val = await this.$refs.uploadEle.getValue()
      if (val && val.length > 0) {
        this.form.img = val[0].display
      }
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
