<template>
  <div class="head">
    <!-- <el-breadcrumb separator-class="arrow" v-if="open">
      <el-breadcrumb-item v-for="item in editableTabs" :key="item.modelId">{{ item.title }}</el-breadcrumb-item>
    </el-breadcrumb> -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
// import modelTerrData from "./modelTerrData.json";
export default {
  props: {
    tabsData: {
      type: Object,
      default: {},
    },
  },
  watch: {
    tabsData: {
      handler (nval, oval) {
        this.init();
      },
      deep: true,
    },
  },
  data () {
    return {
      true: true,
      false: false,
      editableTabsValue: '',//选项卡的name值
      open: false,
      editableTabs: [

      ],
    }
  },
  created () {
    this.init();
  },
  computed: {
    ...mapGetters([
      'modelTerrData',
      
    ]),
  },
  methods: {
    init () {
      console.log(this.tabsData, "tab信息")
      if (this.tabsData.id) {
        // let obj = {};
        switch (this.tabsData.type) {
          case "map":
            this.getDetailMap(this.tabsData.id, this.tabsData.type, this.tabsData.arr || [])
            break;
          case "upload":
            this.getDetailMap(this.tabsData.id, this.tabsData.type)
            break;
          case "detail":
            this.getDetail(this.tabsData.id, this.tabsData.type, this.tabsData.arr)
            break;
        }
      }
    },
    //这里处理层级-地图和上传第一层第二
    getDetailMap (id, type, arr) {
      if (id == "999") {
        this.editableTabs = [{ modelId: id, title: "模型数据上传", type }];
        this.open = false;
        return
      }
      if (arr.length == 2) {
        this.getDetail(id, type, arr)
        return
      }
      let obj = this.childrenTreeNode(this.modelTerrData, id)[0];
      this.editableTabs = [{ modelId: obj.modelId, title: obj.modelName, type }];
      this.open = false;
    },
    //这里处理层级-详情和历史分析第三层
    getDetail (id, type, arr) {
      console.log(id, type, arr)
      let array = [];
      arr.forEach(item => {

        let obj = this.childrenTreeNode(this.modelTerrData, item)[0]
        array.push({ modelId: obj.modelId, title: obj.modelName, type })
      })
      this.editableTabs = array;
      this.open = true;
    },

    //查找子节点
    childrenTreeNode (treeList, id) {
      var temp = [];
      var searchFn = function (treeList, id) {
        for (var i = 0; i < treeList.length; i++) {
          var item = treeList[i];
          if (item.modelId === id) {
            temp.push(item);
            // searchFn(treeList, item.modelId)
            break;
          } else {
            if (item.children) {
              searchFn(item.children, id);
            }
          }
        }
      };
      searchFn(treeList, id);
      return temp;
    },
    addTab (targetName) {
      // let newTabName = ++this.tabIndex + '';
      // this.editableTabs.push({
      //   title: 'New Tab',
      //   name: newTabName,
      //   content: 'New Tab content'
      // });
      // this.editableTabsValue = newTabName;
    },
    removeTab (targetName) {
      // let tabs = this.editableTabs;
      // let activeName = this.editableTabsValue;
      // if (activeName === targetName) {
      //   tabs.forEach((tab, index) => {
      //     if (tab.name === targetName) {
      //       let nextTab = tabs[index + 1] || tabs[index - 1];
      //       if (nextTab) {
      //         activeName = nextTab.name;
      //       }
      //     }
      //   });
      // }

      // this.editableTabsValue = activeName;
      // this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    }
  }
}
</script>

<style lang="scss" scoped>
.head {
  width: 25%;
  height: 100%;

  /deep/ .el-breadcrumb__item {
    display: flex;
  }

  /deep/.el-breadcrumb__item:last-child .el-breadcrumb__separator {
    display: none !important;
  }

  /deep/ .arrow {
    width: 1rem;
    height: 1rem;
    display: inline-block;
    background-image: url("~@/assets/images/ai/arrow.png");
    background-size: cover;
    background-repeat: no-repeat;
  }

  /deep/ .el-tabs__item {
    color: #fff;

    &.is-active {
      color: #07A96E;
      font-weight: bold;
    }
  }
}
</style>