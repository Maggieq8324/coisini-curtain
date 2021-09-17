<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <div class="title">SKU列表</div>
        <el-button style="margin-left:30px;" @click.prevent="addSku" type="primary" plain size="medium"
          >添加SKU</el-button
        >
      </div>

      <el-table stripe v-loading="loading" :data="tableData">
        <el-table-column fixed="left" prop="id" label="id" width="100"></el-table-column>
        <el-table-column prop="img" min-width="200" label="图片">
          <template v-if="scope.row.img" slot-scope="scope">
            <el-image :src="scope.row.img" :preview-src-list="imgSrcList" style="max-height: 50px; max-width: 100px;">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="title" label="标题" width="150"></el-table-column>
        <el-table-column prop="spu_id" label="spu_id" width="100"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="discount_price" label="折扣" width="100"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="price" label="价格（元）" width="150"></el-table-column>
        <el-table-column prop="online" label="是否上架" width="150">
          <template v-if="scope.row.online" slot-scope="scope">{{ scope.row.online | onLine }}</template>
        </el-table-column>

        <el-table-column :show-overflow-tooltip="true" prop="code" label="编码" width="200"></el-table-column>
        <el-table-column prop="stock" label="库存（个）" width="150"></el-table-column>

        <el-table-column width="150" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
            <el-button
              v-permission="{ permission: ['删除SKU'], type: 'disabled' }"
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
    <sku-edit v-else @editClose="editClose" :skuId="skuId" :isCreate="isCreate"></sku-edit>
  </div>
</template>

<script>
import Sku from '@/model/sku'
import SkuEdit from './sku-edit'

export default {
  components: { SkuEdit },
  data() {
    return {
      skuId: null,
      isCreate: false,
      imgSrcList: [], // 用于大图预览
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
    this.getSkus()
    this.loading = false
  },
  methods: {
    async getSkus() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const skus = await Sku.getSkus(page, count)
      this.tableData = skus.items
      this.totalNums = skus.total
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
      this.getSkus()
      this.loading = false
    },
    handleEdit(val) {
      this.isCreate = false
      this.skuId = `${val.id}`
      this.showEdit = true
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await Sku.deleteSku(val.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getSkus()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    addSku() {
      this.isCreate = true
      this.skuId = null
      this.showEdit = true
    },
    editClose() {
      this.showEdit = false
      this.getSkus()
    },
  },
  filters: {
    onLine(val) {
      return val === 1 ? '是' : '否'
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
</style>
