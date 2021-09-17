<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>{{ isCreate ? '创建主题' : '更新主题' }}</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        <el-divider></el-divider>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
              <el-form-item label="标题" prop="title">
                <el-input size="medium" v-model="form.title" placeholder="请填写标题"></el-input>
              </el-form-item>
              <el-form-item label="名称" prop="title">
                <el-input size="medium" v-model="form.name" placeholder="请填写名称"></el-input>
              </el-form-item>
              <el-form-item label="主题描述" prop="description">
                <el-input size="medium" v-model="form.description" placeholder="请填写主题描述"></el-input>
              </el-form-item>

              <el-form-item label="模板名" prop="tpl_name">
                <el-select v-model="form.tpl_name" placeholder="请选择模板名">
                  <el-option v-for="item in tpl_options" :key="item" :label="item" :value="item"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="是否上线">
                <el-switch
                  size="medium"
                  v-model="onlined"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-text="上线"
                  inactive-text="下线"
                ></el-switch>
              </el-form-item>

              <el-form-item label="标题图" prop="title_img">
                <upload-imgs :rules="rules" :max-num="maxNum" ref="uploadImgEle" :value="initImgData" />
              </el-form-item>

              <el-form-item label="入口图" prop="entrance_img">
                <upload-imgs :rules="rules" :max-num="maxNum" ref="uploadEntranceEle" :value="initEntranceData" />
              </el-form-item>

              <el-form-item label="外部图" prop="entrance_img">
                <upload-imgs :rules="rules" :max-num="maxNum" ref="uploadInternalEle" :value="initInternalData" />
              </el-form-item>

              <el-form-item class="submit">
                <el-button
                  v-permission="{ permission: ['创建主题', '更新主题'], type: 'disabled' }"
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

      <div v-if="!isCreate" class="title plus">
        <span>主题下的SPU</span>
      </div>
      <div v-if="!isCreate" class="wrap">
        <el-row>
          <el-table stripe v-loading="loading" :data="tableData">
            <el-table-column prop="id" fixed="left" label="id" width="100"></el-table-column>
            <el-table-column prop="img" label="主图" width="200">
              <template v-if="scope.row.img" slot-scope="scope">
                <img class="display_img" :src="scope.row.img" :alt="scope.row.img" />
              </template>
            </el-table-column>
            <el-table-column prop="title" label="标题" width="150"></el-table-column>
            <el-table-column prop="subtitle" label="副标题" min-width="300"></el-table-column>
            <el-table-column fixed="right" width="150" label="操作">
              <template slot-scope="scope">
                <el-button
                  v-permission="{ permission: ['删除主题下的spu'], type: 'disabled' }"
                  @click.prevent="handleDelete(scope.row)"
                  type="danger"
                  size="mini"
                  plain
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <div class="complete">
            <el-autocomplete
              @focus="loadSpuSuggestions"
              popper-class="my-autocomplete"
              class="inline-input"
              v-model="spuState"
              :fetch-suggestions="querySpuSearch"
              placeholder="添加SPU"
              @select="handleSpuSelect"
            >
              <template slot-scope="{ item }">
                <span class="id">{{ item.id }}</span>
                <span class="name">{{ item.title }}</span>
              </template>
            </el-autocomplete>
            <el-button
              v-permission="{ permission: ['添加主题下的spu'], type: 'disabled' }"
              class="add"
              @click.prevent="addThemeSpu"
              type="primary"
              plain
              size="medium"
              >添加</el-button
            >
          </div>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import theme from '@/model/theme'
import UploadImgs from '@/component/base/upload-image'

export default {
  components: {
    UploadImgs,
  },
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    themeId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      form: {
        title: '',
        name: '',
        tpl_name: '',
        description: '',
        online: 1,
        title_img: null,
        entrance_img: null,
        internal_top_img: null,
      },
      rules: {
        minWidth: 10,
        minHeight: 10,
        maxSize: 5,
      },
      initImgData: [],
      initEntranceData: [],
      initInternalData: [],
      maxNum: 1,
      tableData: [],
      loading: false,
      spuState: '',
      spuId: null,
      spuSuggestions: [],
      onlined: true,
      tpl_options: ['diana', 'irelia', 'camille', 'janna', 'spu-list'],
    }
  },
  watch: {
    onlined(val) {
      this.form.online = val ? 1 : 0
    },
  },
  async mounted() {
    if (!this.isCreate) {
      const res = await theme.getTheme(this.themeId)
      this.form = res
      const initImgData = [
        {
          id: res.id,
          display: res.title_img,
        },
      ]
      this.initImgData = initImgData
      const initEntranceData = [
        {
          id: res.id,
          display: res.entrance_img,
        },
      ]
      this.initEntranceData = initEntranceData
      const initInternalData = [
        {
          id: res.id,
          display: res.internal_top_img,
        },
      ]
      this.initInternalData = initInternalData
      await this.getSpus(res.id)
    }
  },
  methods: {
    // eslint-disable-next-line
    async submitForm(formName) {
      await this.getValue()
      try {
        const postData = { ...this.form }
        let res
        if (this.isCreate) {
          res = await theme.addTheme(postData)
        } else {
          res = await theme.editTheme(this.themeId, postData)
        }
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          this.$confirm('是否返回上一页', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(async () => {
            this.$emit('editClose')
          })
        }
      } catch (error) {
        console.error(error)
      }
    },
    async addThemeSpu() {
      const info = { theme_id: this.form.id, spu_id: this.spuId }
      try {
        const res = await theme.addThemeSpu(info)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          this.getSpus(this.form.id)
          this.spuId = null
          this.spuState = ''
        }
      } catch (error) {
        console.error(error)
      }
    },
    async getSpus(id) {
      const spus = await theme.getSpus(id)
      this.tableData = spus
    },
    async loadSpuSuggestions() {
      if (this.spuSuggestions.length > 0) {
        return
      }
      try {
        if (!this.isCreate) {
          this.spuSuggestions = await theme.getSpuList(this.form.id)
          if (this.spuSuggestions.length === 0) {
            this.$message.error('未找到SPU建议，请先添加SPU')
          }
        }
      } catch (error) {
        this.$message.error('获取SPU建议信息失败')
        console.error(error)
      }
    },
    querySpuSearch(queryString, cb) {
      // eslint-disable-next-line
      const suggestions = this.spuSuggestions
      const results = queryString ? suggestions.filter(this.createSpuFilter(queryString)) : suggestions
      cb(results)
    },
    createSpuFilter(queryString) {
      // eslint-disable-next-line
      return suggestion => {
        return suggestion.title.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      }
    },
    handleSpuSelect(item) {
      this.spuState = item.title
      this.spuId = item.id
    },
    async getValue() {
      const val = await this.$refs.uploadImgEle.getValue()
      if (val && val.length > 0) {
        this.form.title_img = val[0].display
      }
      const val1 = await this.$refs.uploadEntranceEle.getValue()
      if (val1 && val1.length > 0) {
        this.form.entrance_img = val1[0].display
      }
      const val2 = await this.$refs.uploadInternalEle.getValue()
      if (val2 && val2.length > 0) {
        this.form.internal_top_img = val2[0].display
      }
    },
    async handleDelete(row) {
      try {
        const res = await theme.deleteSpu(row.tid)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          await this.getSpus(this.form.id)
        }
      } catch (error) {
        console.error(error)
      }
    },
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

.display_img {
  max-width: 100px;
  max-height: 50px;
  width: auto;
  height: auto;
}

.plus {
  display: flex;
  justify-content: space-between;
}

.complete {
  margin-top: 20px;
  display: flex;
  .add {
    margin-left: 10px;
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
