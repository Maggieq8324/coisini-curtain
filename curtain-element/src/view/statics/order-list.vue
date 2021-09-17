<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <div class="title">订单列表</div>
        <div class="header-left" v-permission="'搜索订单'">
          <lin-search @query="onQueryChange" ref="searchKeyword" />
          <lin-date-picker @dateChange="handleDateChange" ref="searchDate" class="date"> </lin-date-picker>
        </div>
      </div>
      <transition name="fade">
        <div class="search" v-if="keyword">
          <p class="search-tip">
            搜索“<span class="search-keyword">{{ keyword }}</span
            >”， 找到 <span class="search-num">{{ totalNums }}</span> 条订单信息
          </p>
          <button class="search-back" @click="backInit">返回全部订单</button>
        </div>
      </transition>
      <el-table stripe v-loading="loading" :data="tableData">
        <el-table-column prop="id" label="id" width="100"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="order_no" label="订单号" width="150"></el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="user_id" label="用户id" width="150"></el-table-column>
        <el-table-column prop="total_count" label="单品数量" width="150"></el-table-column>
        <el-table-column prop="total_price" width="200" label="总价格"></el-table-column>
        <el-table-column prop="status" min-width="100" label="状态">
          <template v-if="scope.row.status" slot-scope="scope">
            <div class="tags">
              <el-tag :type="computeTagType(scope.row.status)">{{ scope.row.status | orderStatus }}</el-tag>
              <el-tag
                style="margin-left:5px;"
                v-if="scope.row.expired && scope.row.status === 1"
                type="danger"
                size="mini"
                >过期</el-tag
              >
            </div>
          </template>
        </el-table-column>

        <el-table-column fixed="right" width="150" label="操作">
          <template slot-scope="scope">
            <el-button @click.prevent="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
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
    <order-detail v-else @editClose="editClose" :orderId="orderId"></order-detail>
  </div>
</template>

<script>
import LinSearch from '@/component/base/search/lin-search'
import LinDatePicker from '@/component/base/date-picker/lin-date-picker'
import Order from '@/model/order'
import OrderDetail from './order-detail'

export default {
  // eslint-disable-next-line
  components: { OrderDetail, LinSearch, LinDatePicker },
  data() {
    return {
      orderId: null,
      showEdit: false,
      tableData: [],
      loading: false,
      keyword: null,
      searchDate: [],
      searchKeyword: '',
      totalNums: 0,
      currentPage: 1,
      pageCount: 10,
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
    }
  },
  async created() {
    this.loading = true
    this.getOrders()
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
        if (this.searchDate.length) {
          this.keyword = `${newVal} ${this.searchDate}`
        }
      } else {
        this.keyword = ''
        if (this.searchDate.length) {
          this.keyword = `${this.searchDate}`
        }
        this.$refs.searchKeyword.clear()
      }
      this.searchPage()
    },
    // 日期搜索
    searchDate(newDate) {
      if (newDate && newDate.length) {
        this.keyword = `${newDate[0]}至${newDate[1]}`
        if (this.searchKeyword) {
          this.keyword = `${this.searchKeyword} ${newDate[0]}至${newDate[1]}`
        }
      } else {
        this.keyword = ''
        if (this.searchUser) {
          this.keyword = `${this.searchUser}`
        }
        if (this.searchKeyword) {
          this.keyword = `${this.searchUser} ${this.searchKeyword}`
        }
        this.$refs.searchDate.clear()
      }
      this.searchPage()
    },
  },
  methods: {
    async getOrders() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const orders = await Order.getOrders(page, count)
      this.tableData = orders.items
      this.totalNums = orders.total
    },
    async searchPage() {
      const page = this.currentPage - 1
      const count = this.pageCount
      const orders = await Order.search({
        keyword: this.searchKeyword,
        page,
        count,
        start: this.searchDate[0],
        end: this.searchDate[1],
      })
      this.tableData = orders.items
      this.totalNums = orders.total
    },
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      if (this.searchKeyword.length || this.searchDate.length) {
        this.searchPage()
      } else {
        this.getOrders()
      }
      this.loading = false
    },
    handleEdit(val) {
      this.orderId = `${val.id}`
      this.showEdit = true
    },
    handleDateChange(date) {
      this.searchDate = date
    },
    editClose() {
      this.showEdit = false
      this.getOrders()
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await Order.deleteOrder(val.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getOrders()
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
      this.searchDate = []
      this.tableData = []
      this.loading = true
      await this.getOrders()
      this.loading = false
    },
    computeTagType(num) {
      switch (num) {
        case 0:
          return ''
        case 1:
          return 'info'
        case 2:
          return 'success'
        case 3:
          return 'success'
        case 4:
          return 'success'
        case 5:
          return 'warning'
        default:
          return ''
      }
    },
    async onQueryChange(query) {
      this.searchKeyword = query.trim()
    },
  },
  filters: {
    orderStatus(num) {
      switch (num) {
        case 0:
          return '全部'
        case 1:
          return '待支付'
        case 2:
          return '已支付'
        case 3:
          return '已发货'
        case 4:
          return '已完成'
        case 5:
          return '已取消'
        default:
          return '全部'
      }
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

    .header-left {
      float: right;
      display: flex;
      justify-content: space-between;
      align-items: center;
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
@keyframes spin {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}

@media screen and (max-width: 1000px) {
  .date {
    display: none;
  }
}
</style>
