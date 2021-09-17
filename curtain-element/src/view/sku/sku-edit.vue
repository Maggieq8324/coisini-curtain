<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>{{ isCreate ? '创建SKU' : '更新SKU' }}</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        <el-divider></el-divider>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
              <el-form-item label="标题" prop="title">
                <el-input size="medium" v-model="form.title" placeholder="请填写标题"></el-input>
              </el-form-item>

              <el-form-item label="价格" prop="price">
                <el-input-number v-model="form.price" :precision="2" :step="0.1"></el-input-number>
              </el-form-item>

              <!-- 折扣价 打折，或者折扣价 -->
              <el-form-item label="折扣价" prop="discount_price">
                <el-radio-group v-model="radio">
                  <el-radio :label="1">无折扣</el-radio>
                  <el-radio :label="2">折扣价</el-radio>
                  <el-radio :label="3">打折</el-radio>
                </el-radio-group>
                <el-input
                  :disabled="radio === 1"
                  style="margin:5px 0;"
                  size="medium"
                  v-model="discount_price_kernel"
                ></el-input>
                <el-input disabled size="medium" v-model="form.discount_price"></el-input>
              </el-form-item>

              <el-form-item v-if="!isCreate" label="编码" prop="code">
                <el-input disabled size="medium" v-model="form.code" placeholder="请填写编码"></el-input>
              </el-form-item>

              <el-form-item label="库存" prop="stock">
                <el-input-number v-model="form.stock" :step="1" step-strictly></el-input-number>
              </el-form-item>

              <el-form-item label="SPU" prop="spu_id">
                <el-autocomplete
                  @focus="loadSpuSuggestions"
                  popper-class="my-autocomplete"
                  class="inline-input"
                  v-model="spuState"
                  :fetch-suggestions="querySpuSearch"
                  placeholder="请填写所属SPU"
                  @select="handleSpuSelect"
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
                ></el-switch>
              </el-form-item>

              <el-form-item label="图片" prop="img">
                <upload-imgs :max-num="maxNum" ref="uploadEle" :value="initData" />
              </el-form-item>

              <el-form-item
                v-for="(specKey, index) in specKeys"
                :key="index"
                :label="`选择${specKey.name}`"
                :prop="specKey.name"
              >
                <el-cascader
                  :placeholder="`选择${specKey.name}`"
                  v-model="specs[index]"
                  :props="makeProps(specKey.id)"
                ></el-cascader>
              </el-form-item>

              <el-form-item class="submit">
                <el-button
                  v-permission="{ permission: ['创建SKU', '更新SKU'], type: 'disabled' }"
                  type="primary"
                  @click="submitForm('form')"
                  >保 存</el-button
                >
                <el-button @click="resetForm('form')">重 置</el-button>
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
import Sku from '@/model/sku'
import SpecKey from '@/model/spec-key'
import SpecValue from '@/model/spec-value'
import UploadImgs from '@/component/base/upload-image'

export default {
  components: {
    UploadImgs,
  },
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    skuId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      form: {
        title: '',
        online: 1,
        price: 0,
        discount_price: null,
        currency: null,
        code: null,
        stock: null,
        img: null,
      },
      rules: {
        minWidth: 10,
        minHeight: 10,
        maxSize: 5,
      },
      radio: 1,
      discount_price_kernel: null,
      initData: [],
      maxNum: 1,
      saled: true,
      spuState: '',
      spuList: [],
      specs: [],
      specKeys: [],
    }
  },
  watch: {
    saled(val) {
      this.form.online = val ? 1 : 0
    },
    discount_price_kernel(val) {
      switch (this.radio) {
        case 1:
          this.form.discount_price = null
          break
        case 2:
          this.form.discount_price = val
          break
        case 3:
          // eslint-disable-next-line
          const rate = parseFloat(val)
          this.form.discount_price = this.form.price * rate
          break
        default:
          this.form.discount_price = null
      }
    },
  },
  async created() {
    this.$nextTick(async () => {
      if (!this.isCreate) {
        const res = await Sku.getSkuDetail(this.skuId)
        this.form = res
        const initData = [
          {
            id: res.id,
            display: res.img,
          },
        ]
        this.saled = res.online === 1
        this.initData = initData
        this.spuState = res.spu_name
        await this.loadKeyAndValues(res.spu_id)
      }
    })
  },
  methods: {
    async loadKeyAndValues(spuId) {
      const specKeys = await SpecKey.getBySpuId(spuId)
      this.specKeys = specKeys
      // eslint-disable-next-line no-unused-vars
      for (const specKeyy of specKeys) {
        // eslint-disable-next-line
        const ttmp = await Sku.getSpecValueId(this.skuId, specKeyy.id)
        this.specs.push([ttmp.value_id])
      }
    },
    // eslint-disable-next-line
    async submitForm(formName) {
      await this.getValue()
      try {
        const postData = { ...this.form }
        const selectors = []
        for (let i = 0; i < this.specs.length; i++) {
          const spec = this.specs[i]
          const specKeyTmp = this.specKeys[i]
          const tmp = {
            key_id: specKeyTmp.id,
            value_id: spec[0],
          }
          selectors.push(tmp)
        }
        postData.selectors = selectors
        let res
        if (this.isCreate) {
          res = await Sku.addSku(postData)
        } else {
          res = await Sku.editSku(this.skuId, postData)
        }
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          // this.resetForm(formName)
          this.$confirm('是否返回上一页?', '提示', {
            confirmButtonText: '是',
            cancelButtonText: '否',
            type: 'info',
          }).then(async () => {
            this.$emit('editClose')
          })
        }
      } catch (error) {
        console.error(error)
      }
    },
    makeProps(key_id) {
      return {
        lazy: true,
        async lazyLoad(node, resolve) {
          const { level } = node
          const suggestions = await SpecValue.getBySpecKeyId(key_id)
          const nodes = suggestions.map(item => ({
            value: item.id,
            label: `${item.id} - ${item.value}`,
            leaf: level >= 0,
          }))
          resolve(nodes)
        },
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    querySpuSearch(queryString, cb) {
      // eslint-disable-next-line
      const suggestions = this.spuList
      const results = queryString ? suggestions.filter(this.createSpuFilter(queryString)) : suggestions
      cb(results)
    },
    createSpuFilter(queryString) {
      // eslint-disable-next-line
      return suggestion => {
        return suggestion.title.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      }
    },
    handleSpuSelect(item) {
      this.spuState = item.title
      this.form.spu_id = item.id
      this.loadKeyAndValues(item.id)
    },
    async getValue() {
      const val = await this.$refs.uploadEle.getValue()
      if (val && val.length > 0) {
        this.form.img = val[0].display
      }
    },
    back() {
      this.$emit('editClose')
    },
    async loadSpuSuggestions() {
      if (this.spuList.length > 0) {
        return
      }
      try {
        this.spuList = await Spu.getList()
        if (this.spuList.length === 0) {
          this.$message.error('未找到SPU建议，请先添加SPU')
        }
      } catch (error) {
        this.$message.error('获取SPU建议信息失败')
        console.error(error)
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
.white-color {
  background-color: #fff;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>
