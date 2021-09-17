<template>
  <div class="container">
    <div class="header">
      <div class="title">{{ superCategoryName }}分类列表</div>
      <el-button
        style="margin-left: 30px;"
        @click.prevent="handleAdd"
        type="primary"
        plain
        size="medium"
        v-permission="{ permission: '创建分类', type: 'disabled' }"
        >创建子分类</el-button
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
      <el-table-column prop="name" label="名称" width="150"></el-table-column>
      <el-table-column prop="index" label="排序" width="150"></el-table-column>
      <el-table-column prop="online" label="状态" width="100">
        <template slot-scope="scope">{{ scope.row.online | onlineFormat }}</template>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="200" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column fixed="right" width="200" label="操作">
        <template slot-scope="scope">
          <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
          <el-button
            @click.prevent="handleDelete(scope.row)"
            type="danger"
            plain
            size="mini"
            v-permission="{ permission: '删除分类', type: 'disabled' }"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        @current-change="handleCurrentChange"
        :background="true"
        :page-size="pageCount"
        :current-page="currentPage"
        v-if="refreshPagination"
        layout="prev, pager, next, jumper"
        :total="totalNums"
      ></el-pagination>
    </div>
    <!--  创建/编辑分类弹窗  -->
    <category-edit
      v-if="dialogFormVisible"
      :isCreate="isCreate"
      :dialogFormVisible="dialogFormVisible"
      :categoryId="subCategoryId"
      :isSub="true"
      @dialogClose="dialogClose"
    ></category-edit>
  </div>
</template>

<script>
import Category from '@/model/category'
import CategoryEdit from './category-edit'

export default {
  components: {
    CategoryEdit,
  },
  data() {
    return {
      display: true,
      tableData: [],
      superCategoryId: 0,
      superCategoryName: 0,
      subCategoryId: 0,
      imgSrcList: [], // 用于大图预览
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true,
      dialogFormVisible: false, // 展示弹窗
      isCreate: false,
      root: 1, // 是否是父分类,
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
  async created() {
    this.loading = true
    this.superCategoryId = this.$route.params.id
    this.superCategoryName = this.$route.query.categoryName
    this.getSubCategories()
    this.loading = false
  },
  filters: {
    onlineFormat(val) {
      return val === 1 ? '显示' : '不显示'
    },
  },
  watch: {
    display(val) {
      this.form.online = val ? 1 : 0
    },
  },
  methods: {
    async getSubCategories() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const categories = await Category.getSubCategories(page, count, this.superCategoryId)
      this.tableData = categories.items
      this.totalNums = categories.total
      this.initImgSrcList()
    },
    // 切换table页
    async handleCurrentChange(val) {
      this.imgSrcList = []
      this.currentPage = val
      this.loading = true
      this.getSubCategories()
      this.loading = false
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
      this.subCategoryId = val.id
      this.isCreate = false
      this.dialogFormVisible = true
    },
    handleDelete(val) {
      let res
      this.$confirm('此操作将永久删除该分类，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        try {
          this.loading = true
          res = await Category.deleteCategory(val.id)
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
      this.getSubCategories()
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

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
