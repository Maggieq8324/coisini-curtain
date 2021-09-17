<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <div class="title">SPU列表</div>
        <el-button style="margin-left:30px;" @click.prevent="addSpu" type="primary" plain size="medium"
          >添加SPU</el-button
        >
      </div>
      <el-table stripe v-loading="loading" :data="tableData">
        <el-table-column fixed="left" prop="id" label="id" width="100"></el-table-column>
        <el-table-column prop="img" width="100" label="图片">
          <template v-if="scope.row.img" slot-scope="scope">
            <el-image :src="scope.row.img" :preview-src-list="imgSrcList" style="max-width: 200px;"> </el-image>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="title" label="标题" width="200"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="subtitle" label="副标题" width="250"></el-table-column>
        <el-table-column prop="category_id" label="分类id" width="100"></el-table-column>
        <el-table-column prop="price" label="价格(元)" width="150"></el-table-column>
        <el-table-column prop="online" label="是否上架" width="150">
          <template v-if="scope.row.online" slot-scope="scope">{{ scope.row.online | onLine }}</template>
        </el-table-column>
        <el-table-column width="150" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
            <el-button
              v-permission="{ permission: ['删除SPU'], type: 'disabled' }"
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
    <spu-edit v-else @editClose="editClose" :spuId="spuId" :isCreate="isCreate"></spu-edit>
  </div>
</template>

<script>
import Spu from '@/model/spu'
import SpuEdit from './spu-edit'

export default {
  components: { SpuEdit },
  data() {
    return {
      spuId: null,
      isCreate: false,
      imgSrcList: [], // 用于大图预览
      tableData: [],
      showEdit: false,
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true,
    }
  },
  async created() {
    this.loading = true
    this.getSpus()
    this.loading = false
  },
  methods: {
    async getSpus() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const spus = await Spu.getSpus(page, count)
      this.tableData = spus.items
      this.totalNums = spus.total
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
    async handleCurrentChange(val) {
      this.imgSrcList = []
      this.currentPage = val
      this.loading = true
      this.getSpus()
      this.loading = false
    },
    handleEdit(val) {
      this.isCreate = false
      this.spuId = `${val.id}`
      this.showEdit = true
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await Spu.deleteSpu(val.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getSpus()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    addSpu() {
      this.isCreate = true
      this.spuId = null
      this.showEdit = true
    },
    editClose() {
      this.showEdit = false
      this.getSpus()
    },
    // eslint-disable-next-line
    cellStyleCb({ row, column, rowIndex, columnIndex }) {
      return 'long-text-hidden'
    },
  },
  filters: {
    onLine(val) {
      if (val === 1) {
        return '是'
      }
      return '否'
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

.long-text-hidden {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
