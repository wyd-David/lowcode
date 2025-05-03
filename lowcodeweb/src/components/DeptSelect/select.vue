<template>
  <div class="dept-select-wrapper">
    <el-select
      v-model="selectVal"
      popper-class="dept-select-option"
      :placeholder="placeholder"
      :multiple="multiple"
      :disabled="disabled"
      :collapse-tags="multiple && collapseTags"
      @remove-tag="onRemoveTag"
    >
      <template v-for="(item,i) in options">
        <el-option :key="i" :label="item[props.label]" :value="item[props.value]"></el-option>
      </template>
    </el-select>
    <el-button :disabled="disabled" class="select-btn" type="primary" @click="onShowTransfer">选择</el-button>
  </div>
</template>

<script>
  export default {
    props: {
      disabled: {
        type: Boolean,
        default: false
      },
      value: {
        type: Array,
        default: () => {
          return []
        }
      },
      // 是否多选
      multiple: {
        type: Boolean,
        default: false
      },
      placeholder: {
        default: '请选择'
      },
      // 是否隐藏多选项
      collapseTags: {
        type: Boolean,
        default: false
      },
      props: {
        type: Object,
        default: () => {
          return {
            value: 'dpId',
            label: 'dpName'
          };
        }
      }
    },
    data() {
      return {
        selectVal: this.multiple ? [] : '',
        options: []
      }
    },
    watch: {
      value: {
        handler(newValue, oldValue) {
          this.initData();
        },
        immediate: true,
        deep: true
      }
    },
    created() {
      this.initData();
    },
    methods: {
      // 初始化数据
      initData() {
        this.options = this.value;
        if (this.multiple) {
          let ids = this.options.map(item => item[this.props.value]);
          this.selectVal = ids;
        } else {
          this.selectVal = this.options.length ? this.options[0][this.props.value] : '';
        }
      },
      // 显示弹框
      onShowTransfer() {
        this.$emit('on-show');
      },
      // 移除部门
      onRemoveTag(val) {
        let index = this.options.findIndex(item => item[this.props.value] === val);
        this.options.splice(index, 1);
        let temp = JSON.parse(JSON.stringify(this.options));
        this.$emit('input', temp);
        this.$emit('on-remove', temp);
      }
    }
  }
</script>

<style>
  .dept-select-option {
    display: none !important;
  }
</style>
<style lang="scss" scoped="scoped">
  .dept-select-wrapper {
    position: relative;

    ::v-deep .el-select {
      width: 100%;

      .el-select__tags {
        padding-right: 30px;
      }

      .el-input__inner {
        padding-right: 65px;
      }

      .el-input__suffix {
        display: none;
      }
    }

    .select-btn {
      position: absolute;
      right: 0;
      top: 50%;
      z-index: 10;
      transform: translateY(-50%);
      padding: 10px;
    }
  }
</style>
