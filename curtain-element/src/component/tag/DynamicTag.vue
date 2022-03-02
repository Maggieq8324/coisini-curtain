<template>
  <div>
    <el-tag :key="tag" v-for="tag in dynamicTags" :closable="disabled" :disable-transitions="false" @close="handleClose(tag)">{{
      tag
    }}</el-tag>

    <div v-if="disabled">
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
      ></el-input>

      <el-button v-else class="button-new-tag" size="small" @click="showInput" :disabled="!disabled">添加标签</el-button>
    </div>
  </div>
</template>

<script>
export default {
  model: {
    prop: 'dynamicTags',
    event: 'change',
  },
  props: {
    dynamicTags: {
      type: Array,
      default: () => [],
    },
    disabled: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      inputVisible: false,
      inputValue: '',
    }
  },
  mounted() {
    console.log('dynamicTags', this.dynamicTags)
  },
  methods: {
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    },

    showInput() {
      this.inputVisible = true
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm() {
      // eslint-disable-next-line
      const inputValue = this.inputValue
      if (inputValue) {
        this.dynamicTags.push(inputValue)
      }
      this.$emit('change', this.dynamicTags)
      this.inputVisible = false
      this.inputValue = ''
    },
  },
}
</script>

<style>
.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  padding-top: 0;
  padding-bottom: 0;
  text-align: center;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
