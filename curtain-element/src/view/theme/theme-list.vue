<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <div class="title">主题列表</div>
        <el-button style="margin-left:30px;" @click.prevent="addTheme" type="primary" plain size="medium"
          >添加主题</el-button
        >
      </div>

      <el-table stripe v-loading="loading" :data="tableData">
        <el-table-column prop="id" label="id" width="100"></el-table-column>
        <el-table-column prop="title_img" label="标题图" width="200">
          <template v-if="scope.row.title_img" slot-scope="scope">
            <el-image
              :src="scope.row.title_img"
              :preview-src-list="titleImgSrcList"
              style="max-height: 50px; max-width: 100px;"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" width="150"></el-table-column>
        <el-table-column prop="name" label="名称" width="150"></el-table-column>
        <el-table-column prop="tpl_name" label="模版名" width="150"></el-table-column>
        <el-table-column prop="description" label="描述" width="200"></el-table-column>
        <el-table-column prop="entrance_img" label="入口图" width="200">
          <template v-if="scope.row.entrance_img" slot-scope="scope">
            <el-image
              :src="scope.row.entrance_img"
              :preview-src-list="entranceImgSrcList"
              style="max-height: 50px; max-width: 100px;"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="internal_top_img" min-width="200" label="外链图">
          <template v-if="scope.row.internal_top_img" slot-scope="scope">
            <el-image
              :src="scope.row.internal_top_img"
              :preview-src-list="internalTopImgSrcList"
              style="max-height: 50px; max-width: 100px;"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column fixed="right" width="150" label="操作">
          <template slot-scope="scope">
            <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">查看</el-button>
            <el-button
              v-permission="{ permission: ['删除主题'], type: 'disabled' }"
              @click.prevent="handleDelete(scope.row)"
              type="danger"
              size="mini"
              plain
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
    </div>
    <theme-edit v-else @editClose="editClose" :themeId="themeId" :isCreate="isCreate"></theme-edit>
  </div>
</template>

<script>
import Theme from '@/model/theme'
import ThemeEdit from './theme-edit'

export default {
  components: { ThemeEdit },
  data() {
    return {
      themeId: null,
      isCreate: false,
      titleImgSrcList: [],
      entranceImgSrcList: [],
      internalTopImgSrcList: [],
      tableData: [],
      showEdit: false,
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
    }
  },
  async created() {
    this.loading = true
    this.getThemes()
    this.loading = false
  },
  methods: {
    async getThemes() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const themes = await Theme.getThemes(page, count)
      this.tableData = themes.items
      this.totalNums = themes.total
      this.initImgSrcList()
    },
    initImgSrcList() {
      this.tableData.forEach(item => {
        if (!item.title_img) {
          return
        }
        this.titleImgSrcList.push(item.title_img)
      })
      this.tableData.forEach(item => {
        if (!item.entrance_img) {
          return
        }
        this.entranceImgSrcList.push(item.entrance_img)
      })
      this.tableData.forEach(item => {
        if (!item.internal_top_img) {
          return
        }
        this.internalTopImgSrcList.push(item.internal_top_img)
      })
    },
    async handleCurrentChange(val) {
      this.titleImgSrcList = []
      this.entranceImgSrcList = []
      this.internalTopImgSrcList = []
      this.currentPage = val
      this.loading = true
      this.getThemes()
      this.loading = false
    },
    handleEdit(val) {
      this.isCreate = false
      this.themeId = `${val.id}`
      this.showEdit = true
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await Theme.deleteTheme(val.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getThemes()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    addTheme() {
      this.isCreate = true
      this.themeId = null
      this.showEdit = true
    },
    editClose() {
      this.showEdit = false
      this.getThemes()
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    // justify-content: space-between;
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

.display_img {
  max-width: 100px;
  max-height: 50px;
  width: auto;
  height: auto;
}
</style>
