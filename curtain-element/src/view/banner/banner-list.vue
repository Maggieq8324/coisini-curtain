<template>
  <div class="container" v-if="!showDetail">
    <div class="header">
      <div class="title">Banner列表</div>
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
      <el-table-column prop="description" label="描述" min-width="200" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column fixed="right" width="150" label="操作">
        <template slot-scope="scope">
          <el-button @click.prevent="handleDetail(scope.row)" type="primary" plain size="mini">查看</el-button>
          <el-button
            @click.prevent="handleDelete(scope.row)"
            type="danger"
            plain
            size="mini"
            v-permission="{ permission: '删除Banner', type: 'disabled' }"
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
  <!-- 详情页 -->
  <banner-with-items v-else @detailClose="detailClose" :bannerId="detailBannerId"></banner-with-items>
</template>

<script>
import Banner from '@/model/banner'
import BannerWithItems from './banner-with-items'

export default {
  components: {
    BannerWithItems,
  },
  data() {
    return {
      showDetail: false,
      tableData: [],
      imgSrcList: [], // 用于大图预览
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true,
      detailBannerId: 0,
    }
  },
  async created() {
    this.loading = true
    this.getBanners()
    this.loading = false
  },
  methods: {
    async getBanners() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const banners = await Banner.getBanners(page, count)
      this.tableData = banners.items
      this.totalNums = banners.total
      this.initImgSrcList()
    },
    // 切换table页
    async handleCurrentChange(val) {
      this.imgSrcList = []
      this.currentPage = val
      this.loading = true
      this.getBanners()
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
      this.showDetail = true
      this.detailBannerId = val.id
      // this.$router.push({ path: `/banner/${val.id}` } )
    },
    handleDelete(val) {
      let res
      this.$confirm('此操作将永久删除该banner，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        try {
          this.loading = true
          res = await Banner.deleteBanner(val.id)
        } catch (e) {
          this.loading = false
          console.log(e)
        }
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.loading = false
          if (this.totalNums % this.pageCount === 1 && this.currentPage !== 1) {
            this.currentPage--
          }
          this.getBanners()
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
    rowClick() {},
    detailClose() {
      this.showDetail = false
      this.getBanners()
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
