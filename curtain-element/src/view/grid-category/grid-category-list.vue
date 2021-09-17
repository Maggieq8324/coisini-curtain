<template>
  <div class="container">
    <div class="header">
      <div class="title">六宫格列表</div>
      <el-button
        style="margin-left: 30px;"
        @click.prevent="handleAdd"
        type="primary"
        plain
        size="medium"
        v-permission="{ permission: '创建六宫格', type: 'disabled' }"
        >创建六宫格</el-button
      >
    </div>
    <el-table stripe v-loading="loading" :data="tableData">
      <el-table-column prop="id" label="id" width="100"></el-table-column>
      <el-table-column prop="img" label="图片" width="200">
        <template v-if="scope.row.img" slot-scope="scope">
          <el-image :src="scope.row.img" :preview-src-list="imgSrcList" style="max-height: 50px; max-width: 100px;">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" width="200"></el-table-column>
      <el-table-column prop="category_id" label="分类id" width="150"></el-table-column>
      <el-table-column prop="root_category_id" label="父分类id" width="150"></el-table-column>
      <el-table-column prop="index" label="排序" width="150"></el-table-column>
      <el-table-column fixed="right" width="150" label="操作">
        <template slot-scope="scope">
          <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
          <el-button
            @click.prevent="handleDelete(scope.row)"
            type="danger"
            plain
            size="mini"
            v-permission="{ permission: '删除六宫格', type: 'disabled' }"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!--  创建/编辑分类弹窗  -->
    <grid-category-edit
      v-if="dialogFormVisible"
      :isCreate="isCreate"
      :dialogFormVisible="dialogFormVisible"
      :grid-category-id="gridCategoryId"
      @dialogClose="dialogClose"
    ></grid-category-edit>
  </div>
</template>

<script>
import GridCategory from '@/model/grid-category'
import GridCategoryEdit from './grid-category-edit'

export default {
  components: {
    GridCategoryEdit,
  },
  data() {
    return {
      display: true,
      tableData: [],
      imgSrcList: [], // 用于大图预览
      dialogFormVisible: false, // 展示弹窗
      isCreate: false,
      gridCategoryId: 0,
      form: {
        name: '',
        title: 0,
        parent_id: null,
        description: '',
        online: 1,
        index: null,
      },
    }
  },
  async created() {
    this.loading = true
    this.getGridCategories()
    this.loading = false
  },
  methods: {
    async getGridCategories() {
      const gridCategories = await GridCategory.getGridCategories()
      this.tableData = gridCategories
      this.initImgSrcList()
    },
    initImgSrcList() {
      this.tableData.forEach(item => {
        if (!item.img) {
          return
        }
        this.imgSrcList.push(item.img)
      })
    },
    // 获取所拥有的权限并渲染  由子组件提供
    handleAdd() {
      this.dialogFormVisible = true
      this.isCreate = true
    },
    handleEdit(val) {
      this.gridCategoryId = val.id
      this.isCreate = false
      this.dialogFormVisible = true
    },
    handleDelete(val) {
      let res
      this.$confirm('此操作将永久删除该六宫格，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        try {
          this.loading = true
          res = await GridCategory.deleteGridCategory(val.id)
        } catch (e) {
          this.loading = false
        }
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.loading = false
          if (this.totalNums % this.pageCount === 1 && this.currentPage !== 1) {
            this.currentPage--
          }
          this.getSubCategories()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        } else {
          this.loading = false
          this.$message.error(`${res.message}`)
        }
      })
    },
    dialogClose() {
      this.dialogFormVisible = false
      this.loading = true
      this.getGridCategories()
      this.loading = false
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    align-items: center;

    .title {
      height: 59px;
      line-height: 59px;
      color: $parent-title-color;
      font-size: 16px;
      font-weight: 500;
    }
  }
}
</style>
