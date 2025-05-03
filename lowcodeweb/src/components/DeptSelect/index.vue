<template>
  <div class="dept-select">
    <Select
      v-model="options"
      :placeholder="placeholder"
      :multiple="multiple"
      :disabled="disabled"
      :collapse-tags="multiple && collapseTags"
      @on-show="onShowTransfer"
      @on-remove="onRemoveTag"
    >
    </Select>

    <DeptTransfer ref="transferRef" :multiple="multiple" :leaf-checked="leafChecked" @confirm="onTransferCallback"></DeptTransfer>
  </div>
</template>

<script>
  import Select from './select.vue';
  import DeptTransfer from './dept-transfer.vue';
  export default {
    components: {
      Select,
      DeptTransfer
    },
    props: {
      disabled: {
        type: Boolean,
        default: false
      },
      value: {},
      // 是否多选
      multiple: {
        type: Boolean,
        default: false
      },
      valueType: {
        type: String,
        default: 'Array' // Array/String
      },
      placeholder: {
        default: '请选择'
      },
      // 是否隐藏多选项
      collapseTags: {
        type: Boolean,
        default: false
      },
      // 是否只选择叶子节点
      leafChecked: {
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
        if (this.multiple && this.valueType === 'Array') {
          let val = this.value || [];
          this.options = JSON.parse(JSON.stringify(val));
        } else {
          let val = this.value || '';
          let ids = this.options.map(item => item[this.props.value]);
          if (val && val.length && val !== ids.join(',')) {
            this.getSelectedOptions(val);
          }
          if (!val) {
            this.options = [];
          }
        }
      },
      // 获取选中
      getSelectedOptions(ids) {
        let params = {
          ids: ids,
          isUser: false
        }
        this.$API.getDeptOrUser(params).then(res => {
          this.options = res.data || [];
        });
      },
      // 弹出部门选择
      onShowTransfer() {
        this.$refs.transferRef.show({
          title: '部门选择',
          selected: this.options
        });
      },
      // 确定部门选择
      onTransferCallback(data) {
        this.options = data;
        this.setSelectVal();
      },
      // 移除部门
      onRemoveTag() {
        this.setSelectVal();
      },
      // 设置选中
      setSelectVal() {
        if (this.multiple) {
          let temp = JSON.parse(JSON.stringify(this.options));
          if (this.valueType === 'Array') {
            this.$emit('input', temp);
            this.$emit('change', temp);
          } else {
            let ids = temp.map(item => item[this.props.value]);
            let names = temp.map(item => item[this.props.label]);
            this.$emit('input', ids.join(','));
            this.$emit('change', temp, names.join(','));
          }
        } else {
          let temp = JSON.parse(JSON.stringify(this.options));
          this.$emit('change', temp.length ? temp[0] : null);
          this.$emit('input', temp.length ? temp[0][this.props.value] : '');
        }
      }
    }
  }
</script>
