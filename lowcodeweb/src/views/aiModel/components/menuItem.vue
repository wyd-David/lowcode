<template>
  <div>
    <template v-if="hasOneShowingChild(item.children, item)">
      <el-menu-item :index="item.modelId">
        <template slot="title">
          <div class="meun-item-center">
            <div class="meun-three-text">{{ item.modelName }}</div>
            <div class="meun-three-text modelMemo">
              {{ item.modelMemo }}
            </div>
          </div>
        </template>
      </el-menu-item>
    </template>
    <el-submenu v-else ref="subMenu" :index="item.modelId" popper-append-to-body>
      <template slot="title">
        <div @click="onClick(item)" v-if="item.parentModelId" class="meun-three-back">
          <span :style="`color:${ item.modelId == colorId ? '#11D9FF' : '#fff' }`">{{ item.modelName + '
          ('+item.children.length+')' }}</span>
          <i class="arrow el-submenu__icon-arrow el-input__icon el-icon-arrow-up"></i>
        </div>
        <!-- parentModelId为null,第一层 -->
        <div v-else class="back" :class="selectId == item.modelId ? 'imgX' : 'img'">
          <!-- <img :src="selectId == item.modelId ? imgX : img"  class="background" alt=""  /> -->
          <div class="concent">
            <img style="padding-right: 8px; height: 20px" src="~@/assets/images/ai/list-icon.png" alt="" />
            <span style="z-index: 5">{{ item.modelName }}</span>
            <img style="padding-left: 5px; width: 25px" src="~@/assets/images/ai/question-circle.png" alt="" />
          </div>
          <!-- <div class="children_count">{{ item.children.length }}</div> -->
        </div>
      </template>
      <!-- 第二层外层容器样式判断 -->
      <div :class="{ meun_threelist: item.setThreelist }">
        <sidebar-item v-for="child in item.children" :key="child.modelId" :item="child" class="nest-menu"
          :selectId="selectId" :colorId="colorId" />
      </div>
    </el-submenu>
  </div>
</template>

<script>
export default {
  name: "SidebarItem",
  props: {
    // route object
    item: {
      type: Object,
      required: true,
    },
    selectId: {
      type: String,
      default: "",
    },
    isNest: {
      type: Boolean,
      default: false,
    },
    basePath: {
      type: String,
      default: "",
    },
    colorId: {
      type: String,
      default: "",
    }
  },
  data () {
    return {
      id: null,
      imgX: null,
      img: null,
    };
  },

  inject: ['$bus'],

  mounted () {
    this.imgX = require("../../../assets/images/ai/submenu_x.png");
    this.img = require("../../../assets/images/ai/submenu.png");
  },
  methods: {
    onClick (item) {
      // this.id = item.modelId;
      // this.$bus.selectColor(item.modelId)
    },
    hasOneShowingChild (children = [], parent) {
      if (!children) {
        children = [];
      }
      if (parent.modelId == "999") {
        return false;
      }
      if (children.length == 0) {
        return true;
      } else {
        return false;
      }
    },
    resolvePath (routePath, routeQuery) {
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(this.basePath)) {
        return this.basePath;
      }
      if (routeQuery) {
        let query = JSON.parse(routeQuery);
        return { path: path.resolve(this.basePath, routePath), query: query };
      }
      return path.resolve(this.basePath, routePath);
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .el-submenu.is-opened>.el-submenu__title .el-submenu__icon-arrow {
  color: #39b9dd !important;
}
/deep/ .el-submenu.is-opened {
    margin-bottom: 0;
}
.imgX {
  background-image: url("~@/assets/images/ai/submenu_x(1).png");
}

.img {
  background-image: url("~@/assets/images/ai/submenu.png");
}

.back {
  // background: skyblue;
  // background-image: url("~@/assets/images/ai/submenu.png");

  background-size: 100% 100%;
  background-repeat: no-repeat;
  width: 100%;
  height: 70px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;

  .children_count {
    position: absolute;
    right: 43px;
    top: 9px;
    font-weight: 100;
  }

  .concent {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 5px;
    position: relative;
    z-index: 5;
    // margin-left: 85px;
  }

  .background {
    position: absolute;
    right: 0;
    width: 100%;
  }
}

.meun-three-back {
  background-image: url("~@/assets/images/ai/menu-item.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  // display: flex;
  // align-items: center;
  padding-left: 25px;
  font-size: 16px;
  justify-content: center;
  color: #fff;

  .arrow {
    font-weight: 600;
    position: absolute;
    top: 18%;
    right: 38px;
  }

  &>span {
    color: #fff;
  }

}


.meun_threelist {
  background: transparent;
  box-shadow: inset 0px 1px 3px 0px rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(42, 185, 255, 0.5);
  border-radius: 0 0 6px 6px;
  color: #fff;
  width: calc(100% - 61px);
  margin-left: 40px;
}

.meun-three-text {
  line-height: 22px !important;
  font-size: 13px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.modelMemo {
  // color: rgba(255, 255, 255, 0.65);
  color: #ffffff65;
}

.meun-item-center {
  padding: 0 0px 0 20px;
  padding-top: 9px;
  height: 100%;
}

.box {
  width: 100%;
  background-color: #040d32;
}

.border_item {
  position: relative;
  height: 46px;
  // margin: 300px auto;
  background: rgba(1, 19, 67, 0.8);
  border: 2px solid #00a1ff;
  border-radius: 8px;
  color: #fff;
  text-align: left;
  padding: 0 10px;
}

.border_item::before {
  position: absolute;
  content: "";
  top: -2px;
  bottom: -2px;
  left: 30px;
  width: calc(100% - 60px);
  border-top: 2px solid #016886;
  border-bottom: 2px solid #016886;
  z-index: 0;
}

.border_item::after {
  position: absolute;
  content: "";
  top: 20px;
  right: -2px;
  left: -2px;
  height: calc(100% - 40px);
  border-right: 2px solid #016886;
  border-left: 2px solid #016886;
  z-index: 0;
}
</style>