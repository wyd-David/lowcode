<template>
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
            value: 'id',
            label: 'name',
            isLeaf: 'userLeaf'
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
            this.searchAll(node, resolve); // 搜索
          } else {
            this.getRootDept(node, resolve); // 根节点
          }
        } else {
          if (node.data.deptLeaf) {
            this.getUserByDept(node, resolve); // 员工节点
          } else {
            //this.getChildrenDept(node, resolve); // 子部门
            this.getChildrenDpetAndUser(node, resolve); // 子部门与用户
          }
        }
      },
      // 获取部门根节点
      getRootDept(node, resolve) {
        this.$API.getRootDept().then(res => {
          let data = res.data || [];
          return resolve(data);
        });
      },
      // 根据部门编号获取子部门与用户
      getChildrenDpetAndUser(node, resolve) {
      	let deptId = node.data[this.props.value] || '';
      	// 去除搜索时给根节点添加的标识
      	deptId = deptId.replace('root-', '');
      	let loading1 = true;
      	let loading2 = true;
      	let deptList = [];
      	let userList = [];
      	const loadEnd = () => {
      		if(!loading1 && !loading2) {
            let list = [...deptList, ...userList];
      			return resolve([...deptList, ...userList]);
      		}
      	}
      	this.$API.getChildrenDept(deptId).then(res => {
      		loading1 = false;
      		let list = res.data || [];
      		deptList = list;
      		loadEnd();
      	});
      	this.$API.getUserByDept(deptId).then(res => {
      		loading2 = false;
      		let list = res.data || [];
      		userList = list;
      		loadEnd();
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
      // 根据部门编号获取员工
      getUserByDept(node, resolve) {
        let deptId = node.data[this.props.value] || '';
        // 去除搜索时给根节点添加的标识
        deptId = deptId.replace('root-', '');
        this.$API.getUserByDept(deptId).then(res => {
          let data = res.data || [];
          if(data.length) {
            return resolve(data);
          } else {
            let temp = [{
              id: Date.now(),
              name: '无数据',
              userId: Date.now(),
              userName: '无数据',
              disabled: true,
              userLeaf: true
            }];
            return resolve(temp);
          }
        });
      },
      // 根据用户名称、部门名称搜索
      searchAll(node, resolve) {
        let params = {
          name: this.keyword
        }
        this.$API.searchAll(params).then(res => {
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
        if ((!this.multiple && this.leafChecked && !data[this.props.isLeaf]) ||
          (!this.multiple && this.leafChecked && data[this.props.isLeaf]) && data.disabled) {
          this.$refs.tree.setCurrentKey(null);
          this.$emit('node-click', null);
        } else {
          let temp = JSON.parse(JSON.stringify(data));
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
      &.is-disabled {
        display: none;
      }
    }
  }
</style>
