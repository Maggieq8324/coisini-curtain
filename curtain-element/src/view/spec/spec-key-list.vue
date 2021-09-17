<template>
  <div class="container">
    <div class="header">
      <div class="title">规格名列表</div>
    </div>
    <el-table stripe v-loading="loading" :data="tableData">
      <el-table-column prop="id" label="id" width="100"></el-table-column>
      <el-table-column prop="name" label="名称" width="150"></el-table-column>
      <el-table-column prop="description" label="描述" min-width="200" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="unit" label="单位" width="150"></el-table-column>
      <el-table-column prop="standard" label="标准" width="150"></el-table-column>
      <el-table-column fixed="right" width="150" label="操作">
        <template slot-scope="scope">
          <el-button @click.prevent="handleDetail(scope.row)" type="primary" plain size="mini">查看</el-button>
          <el-button
            @click.prevent="handleDelete(scope.row)"
            type="danger"
            plain
            size="mini"
            v-permission="{ permission: '删除规格名', type: 'disabled' }"
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
</template>

<script>
import SpecKey from '@/model/spec-key'

export default {
  components: {},
  data() {
    return {
      tableData: [],
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true,
    }
  },
  async created() {
    this.loading = true
    this.getSpecKeys()
    this.loading = false
  },
  methods: {
    async getSpecKeys() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const specKeys = await SpecKey.getSpecKeys(page, count)
      this.tableData = specKeys.items
      this.totalNums = specKeys.total
    },

    // 切换table页
    async handleCurrentChange(val) {
      this.imgSrcList = []
      this.currentPage = val
      this.loading = true
      this.getSpecKeys()
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
    handleDetail(val) {
      this.$router.push({ path: `/spec/key/value/${val.id}` })
    },
    handleDelete(val) {
      let res
      this.$confirm('此操作将永久删除该规格名，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        try {
          this.loading = true
          res = await SpecKey.deleteSpecKey(val.id)
        } catch (e) {
          this.loading = false
        }
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.loading = false
          if (this.totalNums % this.pageCount === 1 && this.currentPage !== 1) {
            this.currentPage--
          }
          this.getSpecKeys()
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
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    justify-content: space-between;
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
