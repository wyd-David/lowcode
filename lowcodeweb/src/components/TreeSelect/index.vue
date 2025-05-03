<template>
  <div class="treeSelect">
    <el-input
      v-if="isSearch"
      class="searchInput"
      placeholder="输入关键字进行过滤"
      v-model="queryText"
    >
    </el-input>
    <el-tree
      class="filter-tree"
      ref="tree"
      :data="treeData"
      :props="defaultProps"
      :highlight-current="highlightCurrent"
      :default-expand-all="expandall"
      :filter-node-method="filterNode"
      :render-content="renderContent"
      @node-click="nodeClick"
      :default-expanded-keys="expkeys"
      node-key="id"
      :current-node-key="currentKey"
    >
    </el-tree>
  </div>
</template>

<script>
const treeIfon = require("@/assets/images/treeIcon.png");
const treeFileIcon = require("@/assets/images/treefile.png");
import { getBaseLunciTree } from "@/api/xuncha/common/base.js";
export default {
  props: {
    defaultProps: {
      type: Object,
      default: () => {
        return { children: "children", label: "label" };
      },
    },
    isSearch: {
      type: Boolean,
      default: true,
    },

    highlightCurrent: {
      type: Boolean,
      default: true,
    },
    expandall: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      queryText: "",
      treeData: [
        {
          label: "全部",
          id: "1",
          batchId: "",
          lunciId: "",
          year: "",
          children: [],
        },
      ],
      // chekeys:[],//展开数据的
      currentKey: null,
      expkeys: [], //展开节点的
    };
  },
  watch: {
    queryText(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    getBaseLunciTree().then((res) => {
      this.treeData[0].children = res.data;
    });
  },
  methods: {
    init() {
      this.data = [
        {
          label: "全部",
          id: "1",
          children: [
            {
              label: "2022年",
              id: "2",
              children: [
                {
                  label: "第一批巡察",
                  id: "3",
                },
                {
                  label: "第二批巡察",
                  id: "4",
                },
                {
                  label: "第三批巡察",
                  id: "5",
                },
                {
                  label: "第四批巡察",
                  id: "6",
                },
              ],
            },
            {
              label: "2021年",
              id: "12",
              children: [
                {
                  label: "第一批巡察",
                  id: "13",
                },
                {
                  label: "第二批巡察",
                  id: "14",
                },
                {
                  label: "第三批巡察",
                  id: "15",
                },
                {
                  label: "第四批巡察",
                  id: "16",
                },
              ],
            },
            {
              label: "2020年",
              id: "22",
              children: [
                {
                  label: "第一批巡察",
                  id: "23",
                },
                {
                  label: "第二批巡察",
                  id: "24",
                },
                {
                  label: "第三批巡察",
                  id: "25",
                },
                {
                  label: "第四批巡察",
                  id: "26",
                },
              ],
            },
          ],
        },
      ];
      this.expkeys =
        this.data.length && this.data[0].children.length
          ? [this.data[0].id, this.data[0].children[0].id]
          : [];
      let that = this;
      setTimeout(() => {
        that.$refs.tree.setCurrentKey(
          this.data[0].children[0].children[
            this.data[0].children[0].children.length - 1
          ].id
        );
        that.nodeClick(
          this.data[0].children[0].children[
            this.data[0].children[0].children.length - 1
          ]
        );
      }, 100);
    },
    filterNode(value, data) {
      if (!value) return true;
      return data[this.defaultProps.label].indexOf(value) !== -1;
    },
    // 自定义节点
    renderContent(h, { node, data, store }) {
      return h(
        "div",
        {
          attrs: {
            class: "tree-item",
          },
        },
        [
          // 在树形控件自定义函数中增加icon和图片的标签
          h("img", {
            attrs: {
              src: node.childNodes.length ? treeIfon : treeFileIcon,
              class: "treeIcon",
            },
          }),

          h(
            "span",
            { attrs: { class: "nodeStyle", title: node.label } },
            node.label
          ),
        ]
      );
    },
    nodeClick(data, node, agms) {
      this.$emit("node-click", data, node, agms);
    },
  },
};
</script>

<style lang="scss" >
.filter-tree {
  padding: 10px;
}
.searchInput {
  margin-bottom: 16px;
}

.nodeStyle {
  font-size: 14px;
  min-width: 50px;
}

.treeIcon {
  width: 16px;
  height: 16px;
  margin-right: 10px;
  margin-bottom: -2px;
}
</style>