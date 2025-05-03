<template>
  <el-dialog class="custom-center-dialog" :title="title" :visible.sync="visible" :width="multiple?'860px':'560px'" :close-on-click-modal="false" append-to-body>
    <div class="transfer-wrapper" :style="{height:multiple?'400px':'350px'}">
      <div class="transfer-left-wrapper">
        <el-input placeholder="请输入部门名称查找" v-model="keyword" @keyup.enter.native="onSearch">
          <el-button slot="append" icon="el-icon-search" @click="onSearch"></el-button>
        </el-input>
        <div style="margin-top: 16px;" v-if="!multiple">
          已选择部门：{{selected?selected[props.label]:''}}
        </div>
        <div class="transfer-left-content">
          <el-scrollbar>
            <DeptTree ref="tree" :key="timeKey" :props="props" :multiple="multiple" :leaf-checked="leafChecked" @node-click="onNodeClick"></DeptTree>
          </el-scrollbar>
        </div>
      </div>

      <template v-if="multiple">
        <div class="transfer-center-wrapper">
          <el-button type="primary" icon="el-icon-arrow-right" circle @click="onTransfer"></el-button>
        </div>

        <div class="transfer-right-wrapper">
          <div class="selected-title">
            可多选，已选择&nbsp;<span>{{selected.length}}</span>&nbsp;个部门
            <el-button type="warning" @click="onClear">清空</el-button>
          </div>
          <div class="transfer-right-content">
            <el-scrollbar>
              <ul class="selected-list">
                <template v-for="(item,i) in selected">
                  <li :key="i">
                    <div class="selected-label">{{item[props.label]}}</div>
                    <i class="el-icon-top" :class="i===0?'is-disabled':''" @click="onToBefore(item, i)"></i>
                    <i class="el-icon-bottom" :class="i===selected.length-1?'is-disabled':''" @click="onToAfter(item, i)"></i>
                    <i class="el-icon-close" @click="onRemove(item, i)"></i>
                  </li>
                </template>
              </ul>
            </el-scrollbar>
          </div>
        </div>
      </template>
    </div>

    <div slot="footer">
      <el-button @click="onCancel">取 消</el-button>
      <el-button :disabled="isDisabled" type="primary" @click="onConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import DeptTree from '@/components/DeptTree';
  export default {
    components: {
      DeptTree
    },
    props: {
      multiple: {
        type: Boolean,
        default: false
      },
      leafChecked: {
        type: Boolean,
        default: false
      },
      props: {
        type: Object,
        default: () => {
          return {
            value: 'dpId',
            label: 'dpName',
            isLeaf: 'deptLeaf'
          }
        }
      }
    },
    data() {
      return {
        title: '',
        visible: false,
        timeKey: null,
        keyword: '',
        selected: this.multiple ? [] : null
      }
    },
    computed: {
      isDisabled() {
        let disabled = this.multiple ? !this.selected.length : !this.selected;
        return disabled;
      }
    },
    watch: {
      visible() {
        if (!this.visible) {
          this.title = '';
          this.keyword = '';
          this.selected = this.multiple ? [] : null;
        }
      }
    },
    methods: {
      // 显示弹框
      show(params) {
        let temp = JSON.parse(JSON.stringify(params.selected));
        this.title = params.title;
        this.visible = true;
        this.timeKey = Date.now();
        this.selected = this.multiple ? (temp || []) : (temp.length ? temp[0] : null);
      },
      // 搜索
      onSearch() {
        this.$refs.tree.initTree(this.keyword);
      },
      // 添加到右边
      onTransfer() {
        if (this.multiple) {
          let valKey = this.props.value;
          let data = this.$refs.tree.getSelected();
          data.forEach(item => {
            let match = this.selected.filter(opt => opt[valKey] === item[valKey]);
            if (!match.length) this.selected.push(item);
          });
          this.$refs.tree.clearSelected(); // 清空左边选中
        }
      },
      // 移除
      onRemove(item, index) {
        this.selected.splice(index, 1);
      },
      // 清空
      onClear() {
        this.selected = [];
      },
      // 单击节点
      onNodeClick(data) {
        if (!this.multiple) {
          this.selected = data;
        }
      },
      // 前置1位
      onToBefore(item, index) {
        if (index > 0) {
          let options = JSON.parse(JSON.stringify(this.selected));
          options[index] = options[index - 1];
          options[index - 1] = item;
          this.selected = options;
        }
      },
      // 后移1位
      onToAfter(item, index) {
        if (index < this.selected.length - 1) {
          let options = JSON.parse(JSON.stringify(this.selected));
          options[index] = options[index + 1];
          options[index + 1] = item;
          this.selected = options;
        }
      },
      // 取消
      onCancel() {
        this.visible = false;
      },
      // 确定
      onConfirm() {
        this.visible = false;
        if (this.multiple) {
          this.$emit('confirm', JSON.parse(JSON.stringify(this.selected)));
        } else {
          let list = this.selected ? [this.selected] : [];
          this.$emit('confirm', JSON.parse(JSON.stringify(list)));
        }
      }
    }
  }
</script>
<style lang="scss" scoped="scoped">
  .custom-center-dialog {
    ::v-deep .el-dialog__body {
      padding: 20px;
    }
  }
  .transfer-wrapper {
    display: flex;

    .transfer-left-wrapper {
      flex: 1;
      width: 0;
      display: flex;
      flex-direction: column;

      .transfer-left-content {
        margin-top: 16px;
        flex: 1;
        overflow: hidden;
        border: 1px solid #ebeef5;
        border-radius: 4px;

        ::v-deep .el-scrollbar {
          height: 100%;

          .el-scrollbar__wrap {
            overflow-x: hidden;
            padding: 16px;
          }
        }
      }
    }

    .transfer-center-wrapper {
      align-self: center;
      padding: 16px;
    }

    .transfer-right-wrapper {
      flex: 1;
      width: 0;
      display: flex;
      flex-direction: column;

      .selected-title {
        height: 36px;
        text-align: right;
        color: #999;
        display: flex;
        align-items: center;
        justify-content: flex-end;

        .el-button {
          margin-left: 16px;
        }

        span {
          color: red;
        }
      }

      .transfer-right-content {
        margin-top: 16px;
        flex: 1;
        overflow: hidden;
        border: 1px solid #ebeef5;
        border-radius: 4px;

        ::v-deep .el-scrollbar {
          height: 100%;

          .el-scrollbar__wrap {
            overflow-x: hidden;
            padding: 16px;
          }
        }

        .selected-list {
          list-style: none;
          margin: 0;
          padding: 0;

          li {
            display: flex;
            align-items: center;
            height: 30px;
            line-height: 30px;
            padding: 0 8px;
            border: 1px solid #ebeef5;

            &:not(:first-child) {
              margin-top: 8px;
            }

            .selected-label {
              flex: 1;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }

            .el-icon-top {
              position: relative;
              top: 1px;
              margin-left: 8px;
              cursor: pointer;

              &:hover {
                color: #1890ff;
              }

              &.is-disabled {
                cursor: not-allowed;
                color: #999 !important;
              }
            }

            .el-icon-bottom {
              margin-left: 8px;
              cursor: pointer;

              &:hover {
                color: #1890ff;
              }

              &.is-disabled {
                cursor: not-allowed;
                color: #999 !important;
              }
            }

            .el-icon-close {
              margin-left: 8px;
              cursor: pointer;

              &:hover {
                color: #1890ff;
              }
            }
          }
        }
      }
    }
  }
</style>
