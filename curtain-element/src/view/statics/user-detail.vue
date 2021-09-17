<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>用户详情</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        <el-divider></el-divider>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
              <el-form-item label="昵称" prop="nickname">
                <el-input disabled size="medium" v-model="form.nickname"></el-input>
              </el-form-item>

              <el-form-item>
                <el-avatar
                  :size="60"
                  :src="form.wx_profile && form.wx_profile.avatarUrl"
                  @error="errorHandler"
                ></el-avatar>
              </el-form-item>

              <el-form-item label="openid" prop="openid">
                <el-input disabled size="medium" v-model="form.openid"></el-input>
              </el-form-item>

              <el-form-item label="电话" prop="mobile">
                <el-input disabled size="medium" v-model="form.mobile"></el-input>
              </el-form-item>

              <el-form-item label="邮箱" prop="email">
                <el-input disabled size="medium" v-model="form.email"></el-input>
              </el-form-item>

              <el-form-item label="微信档案" prop="wx_profile">
                <div v-if="form.wx_profile">
                  <!--                  <tree-view :data="form.wx_profile" :options="{maxDepth: 3}"></tree-view>-->
                </div>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import ThirdUser from '@/model/third-user'
import StickyTop from '@/component/base/sticky-top/sticky-top'

export default {
  components: { StickyTop },
  props: {
    userId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      form: {},
    }
  },
  async created() {
    this.$nextTick(async () => {
      this.getThirdUser()
    })
  },
  methods: {
    async getThirdUser() {
      const res = await ThirdUser.getThirdUser(this.userId)
      this.form = res
    },
    // eslint-disable-next-line
    async submitForm(formName) {},
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    back() {
      this.$emit('editClose')
    },
    errorHandler() {
      return true
    },
  },
}
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 0;
}

.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
    }
  }

  .wrap {
    padding: 20px;
  }

  .submit {
    float: left;
  }
}

.display_img {
  max-width: 100px;
  max-height: 50px;
  width: auto;
  height: auto;
}

.tree-view-item-key {
  color: red;
}
</style>
