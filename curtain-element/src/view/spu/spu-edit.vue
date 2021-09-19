<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>{{getSpuEditTitle()}}</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        <el-divider></el-divider>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="spuForm" label-width="100px" @submit.native.prevent>
              <el-form-item label="标题" prop="title" :rules="rules.Null">
                <el-input size="medium" v-model="form.title" placeholder="请填写标题" :disabled="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="副标题" prop="subtitle" :rules="rules.Null">
                <el-input size="medium" v-model="form.subtitle" placeholder="请填写副标题" :disabled="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="价格" prop="price" :rules="rules.NumPrice">
                <el-input size="medium" v-model="form.price" placeholder="请填写价格" :disabled="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="折扣" prop="discount_price">
                <el-input size="medium" v-model="form.discount_price" placeholder="请填写折扣" :disabled="!hasAuth"></el-input>
              </el-form-item>
              <el-form-item label="分类" prop="category_id" :rules="rules.Null">
                <el-autocomplete
                  @focus="loadCategorySuggestions"
                  popper-class="my-autocomplete"
                  class="inline-input"
                  v-model="categoryState"
                  :fetch-suggestions="queryCategorySearch"
                  placeholder="请填写所属分类"
                  @select="handleCategorySelect"
                  :disabled="!hasAuth"
                >
                  <template slot-scope="{ item }">
                    <span class="id">{{ item.id }}</span>
                    <span class="name">{{ item.name }}</span>
                  </template>
                </el-autocomplete>
              </el-form-item>
              <el-form-item label="默认sku" prop="default_sku_id">
                <el-autocomplete
                  @focus="loadSkuSuggestions"
                  popper-class="my-autocomplete"
                  class="inline-input"
                  v-model="skuState"
                  :fetch-suggestions="querySkuSearch"
                  placeholder="请填写默认sku"
                  @select="handleSkuSelect"
                  :disabled="!hasAuth"
                >
                  <template slot-scope="{ item }">
                    <span class="id">{{ item.id }}</span>
                    <span class="name">{{ item.title }}</span>
                  </template>
                </el-autocomplete>
              </el-form-item>

              <el-form-item label="是否上架">
                <el-switch
                  size="medium"
                  v-model="saled"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-text="上架"
                  inactive-text="下架"
                  :disabled="!hasAuth"
                ></el-switch>
              </el-form-item>

              <el-form-item label="主图" prop="img">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="主图可以选择不上传，默认选择轮播图的第一张为主图"
                  placement="top-start"
                >
                  <upload-imgs multiple :max-num="mainMaxNun" ref="uploadEle" :value="initData" :disabled="!hasAuth"/>
                </el-tooltip>
              </el-form-item>

              <el-form-item label="主题图" prop="for_theme_img">
                <upload-imgs :max-num="mainMaxNun" ref="uploadThmeEle" :value="initThemeData" :disabled="!hasAuth"/>
              </el-form-item>

              <el-form-item label="轮播图" prop="spu_img_list">
                <upload-imgs multiple sortable :max-num="maxNum" ref="uploadBannerEle" :value="initBannerData" :disabled="!hasAuth"/>
              </el-form-item>

              <el-form-item label="详情图" prop="spu_detail_img_list">
                <upload-imgs multiple sortable :max-num="maxNum" ref="uploadDetailEle" :value="initDetailData" :disabled="!hasAuth"/>
              </el-form-item>

              <el-form-item label="标签" prop="tags" :rules="rules.Null">
                <dynamic-tag v-model="form.tags" :disabled="hasAuth"></dynamic-tag>
              </el-form-item>

              <el-form-item label="选择规格" prop="specs">
                <el-cascader placeholder="选择规格（可多选）" v-model="specs" :props="cascaderProps" :disabled="!hasAuth"></el-cascader>
              </el-form-item>

              <el-form-item label="可视规格" prop="sketch_spec_id">
                <el-autocomplete
                  @focus="loadSpecKeySuggestions"
                  popper-class="my-autocomplete"
                  class="inline-input"
                  v-model="specKeyState"
                  :fetch-suggestions="querySpecKeySearch"
                  placeholder="请填写可视规格id"
                  @select="handleSpecKeySelect"
                  :disabled="!hasAuth"
                >
                  <template slot-scope="{ item }">
                    <span class="id">{{ item.id }}</span>
                    <span class="name">{{ item.name }}</span>
                  </template>
                </el-autocomplete>
              </el-form-item>

              <el-form-item label="描述" prop="description">
                <el-input size="medium" v-model="form.description" placeholder="请填写描述" :disabled="!hasAuth"></el-input>
              </el-form-item>

              <el-form-item class="submit">
                <el-button
                  v-permission="{ permission: ['创建SPU', '更新SPU'] }"
                  type="primary"
                  @click="submitForm('spuForm')"
                  >保 存</el-button
                >
                <el-button @click="resetForm('spuForm')"
                           v-permission="{ permission: ['创建SPU', '更新SPU'] }">重 置</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import Spu from '@/model/spu'
import Category from '@/model/category'
import Sku from '@/model/sku'
import SpecKey from '@/model/spec-key'
import UploadImgs from '@/component/base/upload-image'
import DynamicTag from '@/component/tag/DynamicTag.vue'
import Auth from '@/core/util/auth'
import rules from '@/core/util/rules-1.0'

export default {
  components: {
    UploadImgs,
    DynamicTag,
  },
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    spuId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      hasAuth: Auth.hasAuth(['创建SPU', '更新SPU']),
      form: {
        title: '',
        subtitle: '',
        discount_price: null,
        category_id: null,
        online: 1,
        price: null,
        sketch_spec_id: null,
        default_sku_id: null,
        img: null,
        description: null,
        spu_img_list: null,
        spu_detail_img_list: null,
        tags: []
      },
      rules: {
        minWidth: 100,
        minHeight: 100,
        maxSize: 5,
        ...rules
      },
      initData: [],
      initThemeData: [],
      initBannerData: [],
      initDetailData: [],
      maxNum: 5,
      mainMaxNun: 1,
      saled: true,
      categoryState: '',
      specKeyState: '',
      skuState: '',
      categorySuggestions: [],
      specKeySuggestions: [],
      skuSuggestions: [],
      specs: [],
      cascaderProps: {
        lazy: true,
        multiple: true,
        async lazyLoad(node, resolve) {
          const { level } = node
          try {
            const suggestions = await SpecKey.getList()
            const nodes = suggestions.map(item => ({
              value: item.id,
              label: `${item.id} - ${item.name}`,
              leaf: level >= 0,
            }))
            resolve(nodes)
          } catch (error) {
            this.$message.error('获取规格信息失败，请检查网络')
          }
        },
      },
    }
  },
  watch: {
    saled(val) {
      this.form.online = val ? 1 : 0
    },
  },
  async mounted() {
    if (!this.isCreate) {
      const res = await Spu.getDetail(this.spuId)
      console.log(JSON.stringify(res))
      this.form = {
        id: res.id,
        title: res.title,
        subtitle: res.subtitle,
        category_id: res.category_id,
        root_category_id: res.root_category_id,
        online: res.online,
        price: res.price,
        sketch_spec_id: res.sketch_spec_id,
        default_sku_id: res.default_sku_id,
        img: res.img,
        discount_price: res.discount_price,
        description: res.description,
        tags: res.tags.split('$') ? res.tags.split('$') : [],
        is_test: res.is_test,
        spu_theme_img: res.spu_theme_img,
        for_theme_img: res.for_theme_img,
        category_name: res.category_name,
        sketch_spec_key_name: res.sketch_spec_key_name,
        default_sku_title: res.default_sku_title,
        spu_img_list: res.spu_img_list,
        spu_detail_img_list: res.spu_detail_img_list
      }
      const initData = [
        {
          id: res.id,
          display: res.img,
        },
      ]
      this.saled = res.online === 1
      this.initData = initData
      this.initThemeData = [
        {
          id: res.id,
          display: res.for_theme_img,
        },
      ]
      // eslint-disable-next-line
      this.initBannerData = res.spu_img_list ? res.spu_img_list.map((it, index) => ({ id: index, display: it })) : []
      // eslint-disable-next-line
      this.initDetailData = res.spu_detail_img_list
        ? res.spu_detail_img_list.map((it, index) => ({ id: index, display: it }))
        : []
      this.specKeyState = res.sketch_spec_key_name
      this.categoryState = res.category_name
      this.skuState = res.default_sku_title
      const hitSpecKeys = await Spu.getSpecKeys(this.spuId)
      this.specs = hitSpecKeys.map(item => [item])
      // this.form.tags = res.tags.split('$') ? res.tags.split('$') : []
      // console.log(this.form.tags)
      // console.log('tags', typeof (this.form.tags))
    }
  },
  methods: {
    getSpuEditTitle() {
      if (!this.hasAuth) {
        return 'SPU详情'
      }

      return this.isCreate ? '创建SPU' : '更新SPU'
    },
    // eslint-disable-next-line
    async submitForm(formName) {
      console.log(this.form)
      await this.getValue()
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const postData = { ...this.form }
          const tags = this.form.tags.join('$')
          postData.spec_key_id_list = this.specs.map(spec => spec[0])
          postData.tags = tags
          let res
          if (this.isCreate) {
            res = await Spu.addSpu(postData)
          } else {
            res = await Spu.editSpu(this.spuId, postData)
          }
          if (res.code < window.MAX_SUCCESS_CODE) {
            this.$message.success(`${res.message}`)
            // this.resetForm(formName)
            this.$confirm('是否返回上一页', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
            }).then(async () => {
              this.$emit('editClose')
            })
          }
        }
      })
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    queryCategorySearch(queryString, cb) {
      // eslint-disable-next-line
      const suggestions = this.categorySuggestions
      const results = queryString ? suggestions.filter(this.createCategoryFilter(queryString)) : suggestions
      cb(results)
    },
    querySpecKeySearch(queryString, cb) {
      // eslint-disable-next-line
      const suggestions = this.specKeySuggestions
      const results = queryString ? suggestions.filter(this.createCategoryFilter(queryString)) : suggestions
      cb(results)
    },
    querySkuSearch(queryString, cb) {
      // eslint-disable-next-line
      const suggestions = this.skuSuggestions
      const results = queryString ? suggestions.filter(this.createSkuFilter(queryString)) : suggestions
      cb(results)
    },
    createCategoryFilter(queryString) {
      // eslint-disable-next-line
      return suggestion => {
        return suggestion.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      }
    },
    createSkuFilter(queryString) {
      // eslint-disable-next-line
      return suggestion => {
        return suggestion.title.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      }
    },
    handleCategorySelect(item) {
      this.categoryState = item.name
      this.form.category_id = item.id
    },
    handleSpecKeySelect(item) {
      this.specKeyState = item.name
      this.form.sketch_spec_id = item.id
    },
    handleSkuSelect(item) {
      this.skuState = item.title
      this.form.default_sku_id = item.id
    },
    async getValue() {
      const val = await this.$refs.uploadEle.getValue()
      if (val && val.length > 0) {
        this.form.img = val[0].display
      }
      const val3 = await this.$refs.uploadThmeEle.getValue()
      if (val3 && val3.length > 0) {
        this.form.for_theme_img = val3[0].display
      }
      const val1 = await this.$refs.uploadBannerEle.getValue()
      if (val1 && val1.length > 0) {
        this.form.spu_img_list = val1.map(it => it.display)
      }
      const val2 = await this.$refs.uploadDetailEle.getValue()
      if (val2 && val2.length > 0) {
        this.form.spu_detail_img_list = val2.map(it => it.display)
      }
    },
    back() {
      this.$emit('editClose')
    },
    async loadCategorySuggestions() {
      if (this.categorySuggestions.length > 0) {
        return
      }
      try {
        this.categorySuggestions = await Category.getList()
        if (this.categorySuggestions.length === 0) {
          this.$message.error('暂无分类，请先添加')
        }
      } catch (error) {
        this.$message.error('获取分类建议信息失败')
        console.error(error)
      }
    },
    async loadSpecKeySuggestions() {
      if (this.specKeySuggestions.length > 0) {
        return
      }
      try {
        if (!this.isCreate) {
          // 添加当前 spu_id
          this.specKeySuggestions = await SpecKey.getBySpuId(this.spuId)
        } else {
          this.specKeySuggestions = this.specs
        }
        if (this.specKeySuggestions.length === 0) {
          this.$message.error('暂无规格名，请先添加')
        }
      } catch (error) {
        this.$message.error('获取规格键建议信息失败')
        console.error(error)
      }
    },
    async loadSkuSuggestions() {
      if (this.skuSuggestions.length > 0) {
        return
      }
      // 如果创建spu，则没有sku，不用建议
      if (!this.isCreate) {
        this.skuSuggestions = await Sku.getBySpuId(this.spuId)
        if (this.skuSuggestions.length === 0) {
          this.$message.error('暂无SKU，请先添加')
        }
      }
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

.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;
    display: inline-flex;
    align-content: space-around;
    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .id {
      margin-right: 15px;
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>
