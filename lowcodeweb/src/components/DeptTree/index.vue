<!-- <template>
  <div>
    <el-tree
      ref="tree"
      :class="leafChecked?'leaf-select-tree':''"
      :key="timeKey"
      :node-key="props.value"
      :props="props"
      lazy
      :show-checkbox="multiple"
      :highlight-current="!multiple"
      :load="loadNode"
      :check-strictly="true"
      :expand-on-click-node="leafChecked?true:false"
      @node-click="onNodeClick"
    >
      <template slot-scope="{ node, data }">
        <span class="el-icon-document" v-if="node.isLeaf">&nbsp;&nbsp;{{ node.label }}</span>
        <span class="el-icon-folder" v-else>&nbsp;&nbsp;{{ node.label }}</span>
      </template>
    </el-tree>
  </div>
</template>

<script>
  export default {
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
        timeKey: null,
        keyword: ''
      }
    },
    methods: {
      // 搜索
      initTree(val) {
        this.keyword = val || '';
        this.timeKey = Date.now(); // 更新树组件
      },
      // 加载节点
      loadNode(node, resolve) {
        if (node.level === 0) { // 根节点
          if (this.keyword.length) {
            this.searchDept(node, resolve); // 搜索
          } else {
            this.getRootDept(node, resolve); // 根节点
          }
        } else {
          this.getChildrenDept(node, resolve); // 子节点
        }
      },
      // 获取部门根节点
      getRootDept(node, resolve) {
        this.$API.getRootDept().then(res => {
          let data = res.data || [];
          return resolve(data);
        });
      },
      // 根据部门编号获取子部门
      getChildrenDept(node, resolve) {
        let deptId = node.data[this.props.value] || '';
        // 去除搜索时给根节点添加的标识
        deptId = deptId.replace('root-', '');
        this.$API.getChildrenDept(deptId).then(res => {
          let data = res.data || [];
          return resolve(data);
        });
      },
      // 根据部门名称搜索
      searchDept(node, resolve) {
        let params = {
          dpName: this.keyword
        }
        this.$API.searchDept(params).then(res => {
          let data = res.data || [];
          // 搜索时，子节点有可能跟根节点重复，给根节点加个标识区分
          data.forEach(item => {
            item[this.props.value] = 'root-' + item[this.props.value];
          });
          return resolve(data);
        });
      },
      // 单击节点
      onNodeClick(data) {
        let temp = JSON.parse(JSON.stringify(data));
        if (!this.multiple && this.leafChecked && !temp[this.props.isLeaf]) {
          this.$refs.tree.setCurrentKey(null);
          this.$emit('node-click', null);
        } else {
          // 去除搜索时给根节点添加的标识
          temp[this.props.value] = temp[this.props.value].replace('root-', '');
          this.$emit('node-click', temp);
        }
      },
      // 获取选中节点
      getSelected() {
        if (this.multiple) {
          let data = this.$refs.tree.getCheckedNodes();
          // 去除搜索时给根节点添加的标识
          data.forEach(item => {
            item[this.props.value] = item[this.props.value].replace('root-', '');
          });
          return data || [];
        } else {
          let data = this.$refs.tree.getCurrentNode();
          // 去除搜索时给根节点添加的标识
          data[this.props.value] = data[this.props.value].replace('root-', '');
          return data || null;
        }
      },
      // 清空选中
      clearSelected() {
        if (this.multiple) {
          this.$refs.tree.setCheckedKeys([]);
        } else {
          this.$refs.tree.setCurrentKey(null);
        }
      }
    }
  }
</script>

<style lang="scss" scoped="scoped">
  ::v-deep .leaf-select-tree {
    .el-checkbox {
      display: none;
    }

    .is-leaf+.el-checkbox {
      display: inline-block;
    }
  }
</style> -->

<template>
  <div>
    <treeselect
      :disabled="disabled"
      v-model="deptId"
      :options="deptOptions"
      :normalizer="normalizer"
      :show-count="true"
      placeholder="选择部门"
      ref="tree"
      @select="handleSelect"
    />
  </div>
</template>
<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { treeselect } from "@/api/system/dept";
export default {
    props: {
        value: {
            type: String,
            default: ""
        },
        disabled: {
            type: Boolean,
            default: false
        }
    },
  components: {
        Treeselect
  },
  data() {
    return {
      val: 0,
      deptId: null,
      deptOptions: [],
    };
  },
  created() {
    treeselect().then((response) => {
      this.deptOptions = response.data;
    });
  },
    methods: {
    handleSelect(e) { 
        this.$emit("input",e.id);
    },
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.label,
        children: node.children,
      };
    },
  },
};
</script>

