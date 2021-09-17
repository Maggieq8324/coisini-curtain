<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <div class="title">C端用户列表</div>
        <div class="header-left" v-permission="'搜索用户'">
          <lin-search @query="onQueryChange" ref="searchKeyword" />
        </div>
      </div>
      <transition name="fade">
        <div class="search" v-if="keyword">
          <p class="search-tip">
            搜索“<span class="search-keyword">{{ keyword }}</span
            >”， 找到 <span class="search-num">{{ totalNums }}</span> 条用户信息
          </p>
          <button class="search-back" @click="backInit">返回全部用户</button>
        </div>
      </transition>
      <el-table stripe v-loading="loading" :data="tableData">
        <el-table-column prop="id" label="id" width="100"></el-table-column>
        <el-table-column prop="avatar" label="头像" width="200">
          <template v-if="scope.row.avatar" slot-scope="scope">
            <img class="display_img" :src="scope.row.avatar" :alt="scope.row.avatar" />
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="openid" label="openid" width="300">
          <template slot-scope="scope">{{ scope.row.openid }}</template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="nickname" label="用户名" width="150"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="150"></el-table-column>
        <el-table-column prop="mobile" min-width="200" label="联系方式"></el-table-column>

        <el-table-column fixed="right" width="150" label="操作">
          <template slot-scope="scope">
            <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">详情</el-button>
            <el-button disabled @click.prevent="handleDelete(scope.row)" type="danger" size="mini" plain
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
    <user-detail :userId="userId" @editClose="editClose" v-else></user-detail>
  </div>
</template>

<script>
import LinSearch from '@/component/base/search/lin-search'
import ThirdUser from '@/model/third-user'
import UserDetail from './user-detail'

export default {
  components: { LinSearch, UserDetail },
  data() {
    return {
      tableData: [],
      userId: null,
      showEdit: false,
      totalNums: 0,
      keyword: null,
      searchKeyword: '',
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
    }
  },
  async created() {
    this.loading = true
    this.getThirdUsers()
    this.loading = false
  },
  watch: {
    // 关键字搜索
    searchKeyword(newVal) {
      if (newVal) {
        this.keyword = newVal
        if (this.searchUser) {
          this.keyword = `${newVal}`
        }
      } else {
        this.keyword = ''
        this.$refs.searchKeyword.clear()
      }
      this.searchPage()
    },
  },
  methods: {
    async getThirdUsers() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const users = await ThirdUser.getThirdUsers(page, count)
      this.tableData = users.items
      this.totalNums = users.total
    },
    async searchPage() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const users = await ThirdUser.search({
        keyword: this.searchKeyword,
        page,
        count,
      })
      this.tableData = users.items
      this.totalNums = users.total
    },
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      if (this.searchKeyword.length) {
        this.searchPage()
      } else {
        this.getThirdUsers()
      }
      this.loading = false
    },
    handleEdit(val) {
      this.userId = `${val.id}`
      this.showEdit = true
    },
    editClose() {
      this.showEdit = false
      this.getThirdUsers()
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await ThirdUser.deleteThirdUser(val.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getThirdUsers()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    // 清空检索
    async backInit() {
      this.searchKeyword = ''
      this.keyword = ''
      this.tableData = []
      this.loading = true
      await this.getThirdUsers()
      this.loading = false
    },
    async onQueryChange(query) {
      this.searchKeyword = query.trim()
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    justify-content: flex-start;
    align-items: center;

    .title {
      height: 59px;
      line-height: 59px;
      margin-right: 30px;
      color: $parent-title-color;
      font-size: 16px;
      font-weight: 500;
    }
  }

  .search {
    height: 52px;
    width: 100%;
    background: rgba(57, 99, 188, 0.1);
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 24px;
    margin-bottom: 24px;

    .search-tip {
      margin-left: 40px;
      height: 52px;
      line-height: 52px;
      color: #354058;
      font-size: 14px;

      .search-keyword {
        color: $theme;
      }

      .search-num {
        color: #f4516c;
      }
    }

    .search-back {
      margin: 8px 20px;
      height: 32px;
      background: #f4516c;
      border: none;
      border-radius: 2px;
      color: #fff;
      padding: 0 13px;
      font-size: 14px;
      cursor: pointer;
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
