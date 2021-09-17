<template>
  <el-dialog :append-to-body="true" :before-close="handleClose" :visible.sync="visible">
    <div style="margin-top:-25px;">
      <div class="dialog-title">
        <span>{{ isCreate ? '创建六宫格' : '更新六宫格' }}</span>
      </div>
      <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
        <el-form-item label="标题" prop="title">
          <el-input size="medium" v-model="form.title" placeholder="请填写宫格标题"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input size="medium" v-model="form.name" placeholder="请填写宫格名"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category_id">
          <el-autocomplete
            @focus="loadSuggestions"
            popper-class="my-autocomplete"
            class="inline-input"
            v-model="state"
            :fetch-suggestions="querySearch"
            placeholder="请填写所属分类"
            @select="handleSelect"
          >
            <template slot-scope="{ item }">
              <span class="id">{{ item.id }}</span>
              <span class="name">{{ item.name }}</span>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="图片" prop="img">
          <upload-imgs ref="uploadEle" :max-num="maxNum" :value="initData" />
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer" style="padding-left:5px;">
      <el-button type="primary" @click="submitForm" v-permission="{ permission: '更新六宫格', type: 'disabled' }"
        >确 定</el-button
      >
      <el-button @click="resetForm('form')">重 置</el-button>
    </div>
  </el-dialog>
</template>
<script>
import GridCategory from '@/model/grid-category'
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
    gridCategoryId: {
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
      state: '',
      suggestions: [],
      display: true,
      initData: [],
      maxNum: 1,
      form: {
        name: '',
        title: '',
        category_id: null,
        root_category_id: null,
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
      const res = await GridCategory.getGridCategory(this.gridCategoryId)
      this.form = res
      this.initData = [{ display: res.img }]
    }
    this.loadSuggestions()
  },
  methods: {
    async submitForm() {
      await this.getValue()
      const form = { ...this.form }
      let res
      if (this.isCreate) {
        res = await GridCategory.addGridCategory(form)
      } else {
        res = await GridCategory.editGridCategory(this.gridCategoryId, form)
      }
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
        this.$emit('dialogClose')
      }
    },
    querySearch(queryString, cb) {
      // eslint-disable-next-line
      const suggestions = this.suggestions
      const results = queryString ? suggestions.filter(this.createFilter(queryString)) : suggestions
      cb(results)
    },
    createFilter(queryString) {
      // eslint-disable-next-line
      return suggestion => {
        return suggestion.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      }
    },
    handleSelect(item) {
      this.state = item.name
      this.form.category_id = item.id
    },
    async getValue() {
      const val = await this.$refs.uploadEle.getValue()
      if (val && val.length > 0) {
        this.form.img = val[0].display
      }
    },
    async loadSuggestions() {
      try {
        this.suggestions = await Category.getList()
        if (this.suggestions.length === 0) {
          this.$message.error('未找到宫格建议，请先添加宫格')
        }
      } catch (error) {
        this.$message.error('获取宫格建议信息失败')
        console.error(error)
      }
      const inlineId = this.getInlineId()
      // eslint-disable-next-line
      const hit = this.suggestions.filter(val => {
        return val.id === inlineId
      })
      if (hit.length > 0) {
        this.state = hit[0].name
      }
    },
    getInlineId() {
      if (this.form.category_id) {
        return this.form.category_id
      }
      if (this.form.root_category_id) {
        return this.form.root_category_id
      }
      return null
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
