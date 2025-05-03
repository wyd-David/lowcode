<template>
  <div class="pt_content">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane
        v-for="item in tabData"
        :label="item.meta.title"
        :key="item.meta.title"
        :name="item.meta.title"
      ></el-tab-pane>
    </el-tabs>
    <el-button
      v-if="oaItemsShow"
      @click="oaShow = !oaShow"
      class="pt_content_rbtn"
      size="mini"
      type="primary"
      >OA制度流程库</el-button
    >
    <transition>
      <div v-if="oaItemsShow && oaShow" class="oa_regulation_base">
        <div
          class="oa_regulation_base_item"
          v-for="item in oaItems"
          @click="toUrl(item.url)"
          :key="item.name"
        >
          {{ item.name }}
        </div>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: "页面导航",
    },
    oaItemsShow: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      oaShow: false,
      // text:"页面导航",
      tabData: [],
      activeName: "",
      oaItems: [
        
      ],
    };
  },
  created() {
    if (this.$route.meta.parentId) {
      let routeArr = this.$store.getters.permission_add_routes.filter((res) => {
        return res.meta && res.meta.currId === this.$route.meta.gId;
      });
      if (routeArr.length) {
        let parent = routeArr[0].children.filter(
          (res) => res.meta.currId == this.$route.meta.currId
        );
        let temp = parent[0].threeChildren;
        this.tabData = temp;
        this.activeName = temp[0].meta.title;
        this.$emit("ptClick", this.activeName);
      }
    }
  },
  methods: {
    simulateClick(e) {
      this.activeName = e;
      this.$emit("ptClick", e);
    },
    handleClick(tab, event) {
      this.$emit("ptClick", this.tabData[tab.index].meta.title,this.tabData[tab.index]);
    },
    toUrl(url) {
      window.open(url);
    },
  },
};
</script>
<style lang="scss" scoped>
.pt_content {
  width: 100%;
  background: #ffffff;
  padding: 0px 20px;
  position: relative;
  .pt_title {
    font-size: 20px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    line-height: 22px;
  }
  .pt_content_rbtn {
    position: absolute;
    right: 20px;
    top: 10px;
  }
  .oa_regulation_base {
    position: absolute;
    z-index: 999;
    top: 45px;
    left: 15px;
    right: 0;
    background: #fff;
    display: flex;
    flex-wrap: wrap;
    &_item {
      font-size: 12px;
      border: 1px solid #eae8e8;
      color: rgb(96, 98, 102);
      margin: 1px;
      width: 150px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      display: inline-block;
      &:hover {
        text-decoration: underline #333;
        background: #eae8e8;
        cursor: pointer;
      }
    }
  }
}

//去除灰线条
::v-deep .el-tabs__nav-wrap::after {
  position: static !important;
}
</style>