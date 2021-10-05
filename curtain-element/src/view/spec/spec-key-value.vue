<template>
  <div>
    <div class="container">
      <el-row :gutter="20" class="wrap">
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-divider></el-divider>
          <div class="title">
            <span>{{ hasAuth ? '修改规格名' : '规格名详情'}}</span>
          </div>
          <el-form :model="form" status-icon ref="specKeyForm" label-width="100px" @submit.native.prevent>
            <el-form-item label="规格名名称" prop="name" :rules="rules.Null">
              <el-input size="medium" v-model="form.name" placeholder="请填写规格名名称" :readonly="!hasAuth"></el-input>
            </el-form-item>
            <el-form-item label="规格名描述" prop="description" :rules="rules.Null">
              <el-input size="medium" v-model="form.description" placeholder="请填写规格名描述" :readonly="!hasAuth"></el-input>
            </el-form-item>
            <el-form-item label="单位" prop="unit">
              <el-input size="medium" v-model="form.unit" placeholder="请填写单位，如：英寸" :readonly="!hasAuth"></el-input>
            </el-form-item>
            <el-form-item label="是否标准">
              <el-switch
                size="medium"
                v-model="isStandard"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="标准"
                inactive-text="非标准"
                :disabled="!hasAuth"
              ></el-switch>
            </el-form-item>
            <el-form-item class="submit">
              <el-button
                v-permission="{ permission: ['更新规格名'] }"
                type="primary"
                @click="submitForm('specKeyForm')"
                >保 存</el-button
              >
              <el-button @click="resetForm('specKeyForm')"
                         v-permission="{ permission: ['更新规格名'] }">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-divider></el-divider>
          <div class="title plus">
            <span>规格值列表</span>
            <el-button style="margin-left:30px;"
                       @click.prevent="handleAdd"
                       type="primary"
                       plain
                       size="medium"
                       v-permission="{ permission: ['创建规格值'] }"
              >添加规格值</el-button
            >
          </div>
          <el-divider></el-divider>
          <el-table stripe v-loading="loading" :data="tableData">
            <el-table-column prop="id" label="id" width="150"></el-table-column>
            <el-table-column prop="value" label="规格值名称" width="150"></el-table-column>
            <el-table-column prop="img" label="图片" width="200">
              <template v-if="scope.row.img" slot-scope="scope">
                <el-image :src="scope.row.img" :preview-src-list="imgSrcList" style="max-height: 50px; max-width: 100px;">
                </el-image>
              </template>
            </el-table-column>
            <el-table-column :show-overflow-tooltip="true" prop="extend" min-width="200" label="扩展"></el-table-column>
            <el-table-column :width="columnWidth" fixed="right" label="操作" align="center">
              <template slot-scope="scope">
                <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">{{hasAuth ? '编辑' : '详情'}}</el-button>
                <el-button
                  @click.prevent="handleDelete(scope.row)"
                  v-permission="{ permission: ['删除规格值'] }"
                  type="danger"
                  size="mini"
                  plain
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
    <!--  创建/编辑规格值弹窗  -->
    <spec-value-edit
      v-if="dialogFormVisible"
      :isCreate="isCreate"
      :dialog-form-visible="dialogFormVisible"
      :spec-value-id="specValueId"
      :spec-id="id"
      @dialogClose="dialogClose"
    ></spec-value-edit>
  </div>
</template>

<script>
import SpecKey from '@/model/spec-key'
import SpecValue from '@/model/spec-value'
import Auth from '@/core/util/auth'
import rules from '@/core/util/rules-1.0'
import SpecValueEdit from './spec-value-edit'

export default {
  components: {
    SpecValueEdit,
  },
  computed: {
    columnWidth() {
      return Auth.hasAuth(['删除规格值']) ? 150 : 90
    }
  },
  data() {
    return {
      hasAuth: Auth.hasAuth('更新规格值'),
      tableData: [],
      specValueId: 0,
      dialogFormVisible: false,
      isCreate: false,
      id: 0,
      loading: false,
      isStandard: false,
      form: {
        name: '',
        description: '',
        unit: '',
        standard: 0,
      },
      imgSrcList: [], // 用于大图预览
      rules: {
        ...rules
      }
    }
  },
  watch: {
    isStandard(newVal) {
      this.form.standard = newVal ? 1 : 0
    },
    // eslint-disable-next-line
    '$route.params': function() {
      this.getDetail()
    },
  },
  async mounted() {
    await this.getDetail()
  },
  methods: {
    initImgSrcList() {
      this.tableData.forEach(item => {
        if (!item.img) {
          return
        }
        this.imgSrcList.push(item.img)
      })
    },
    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const form = { ...this.form }
          const res = await SpecKey.editSpecKey(this.id, form)
          if (res.code < window.MAX_SUCCESS_CODE) {
            this.$message.success(`${res.message}`)
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await SpecValue.deleteSpecValue(row.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          await this.getDetail()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    async getDetail() {
      this.loading = true
      const { id } = this.$route.params
      this.id = parseInt(id, 0)
      const res = await SpecKey.getSpecKeyDetail(id)
      // eslint-disable-next-line
      this.isStandard = res.standard === 1 ? true : false
      this.form = {
        name: res.name,
        description: res.description,
        unit: res.unit,
        standard: res.standard,
      }
      this.tableData = res.items
      this.initImgSrcList()
      this.loading = false
    },
    handleAdd() {
      this.isCreate = true
      this.specValueId = 0
      this.dialogFormVisible = true
    },
    handleEdit(row) {
      this.isCreate = false
      this.specValueId = row.id
      this.dialogFormVisible = true
    },
    dialogClose() {
      this.dialogFormVisible = false
      this.loading = true
      this.getDetail()
      this.loading = false
    },
  },
}
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 0;
}

.pagination {
  display: flex;
  justify-content: flex-start;
  margin: 20px;
}

.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    // text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
    }
  }

  .wrap {
    padding: 20px;
    display: flex;
    justify-content: space-between;
  }

  .submit {
    float: left;
  }
}

.plus {
  display: flex;
  align-items: center;
}
</style>
