<template>
  <div class="user-select">
    <Select
      v-model="options"
      :placeholder="placeholder"
      :multiple="multiple"
      :collapse-tags="multiple && collapseTags"
      @on-show="onShowTransfer"
      @on-remove="onRemoveTag"
    >
    </Select>

    <UserTransfer ref="transferRef" :multiple="multiple" :leaf-checked="leafChecked" @confirm="onTransferCallback"></UserTransfer>
  </div>
</template>

<script>
  import Select from './select.vue';
  import UserTransfer from './user-transfer.vue';
  export default {
    components: {
      Select,
      UserTransfer
    },
    props: {
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
      props: {
        type: Object,
        default: () => {
          return {
            value: 'id',
            label: 'name'
          };
        }
      }
    },
    data() {
      return {
        leafChecked: true,
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
          //this.options = JSON.parse(JSON.stringify(val));
          let ids1 = val.map(item => item[this.props.value]);
          let ids2 = this.options.map(item => item[this.props.value]);
          if (val.length && ids1.join(',') !== ids2.join(',')) {
            this.getSelectedOptions(ids1);
          }
          if (!val.length) {
            this.options = [];
          }
        } else {
          let val = this.value || '';
          let ids = this.options.map(item => item[this.props.value]);
          if (val && val !== ids.join(',')) {
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
          isUser: true
        }
        this.$API.getDeptOrUser(params).then(res => {
          this.options = res.data || [];
        });
      },
      // 弹出人员选择
      onShowTransfer() {
        this.$refs.transferRef.show({
          title: '人员选择',
          selected: this.options
        });
      },
      // 确定人员选择
      onTransferCallback(data) {
        this.options = data;
        this.setSelectVal();
      },
      // 移除人员
      onRemoveTag(val) {
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
