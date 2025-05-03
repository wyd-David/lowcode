<template>
  <div id="ai" style="width: 100%">
    <el-menu text-color="#fff" active-text-color="#fff" class="el-menu-vertical-demo" :default-openeds="menuArr"
      @open="handleOpen" @close="handleClose" unique-opened @select="onSelect" :default-active="menuIndex">
      <el-submenu ref="subMenu" index="999" class="modelData" popper-append-to-body>
        <template slot="title">
          <div class="back" :class="selectId == 999 ? 'imgX' : 'img'">
            <!-- <img :src="selectId == 999 ? imgX : img" class="background" alt="" /> -->
            <div class="concent">
              <img style="padding-right: 8px; height: 20px" src="~@/assets/images/ai/list-icon.png" alt="" />
              <span style="z-index: 5">模型数据上传</span>
              <img style="padding-left: 5px; width: 25px" src="~@/assets/images/ai/question-circle.png" alt="" />
            </div>
            <!-- <div class="children_count">{{ item.children.length }}</div> -->
          </div>
        </template>
      </el-submenu>
      <menu-item v-for="item in menuList" :key="item.modelId" :selectId="selectId" :item="item" :colorId="colorId">
      </menu-item>
    </el-menu>
  </div>
</template>

<script>
import menuItem from "./menuItem.vue";

export default {
  data () {
    return {
      isCollapse: true,
      menuList: [],
      selectId: "01",
      colorId: "",
      menuIndex: "01",
      menuArr:['01'],
    };
  },
  components: {
    menuItem,
  },

  provide(){
    return {
      $bus:this
    }
  },

  mounted () {
    this.init();
  },
  methods: {
    selectColor(val){
      this.colorId = val;
    },
    init () {
      // 调取接口，获取菜单json
      this.$store.dispatch("setAiMeun").then(res=>{
        this.menuList = res;
      })
      this.$emit("openMap", "01");
    },
    handleOpen (key, keyPath) {
      this.menuIndex = key
      console.log(key, keyPath)
      this.selectId = keyPath[0]
      if (key == 999) {
        this.colorId = "";
        this.$emit("openUpload", key);
        return;
      }
      if (keyPath.length == 1) {
        this.colorId = "";
        this.$emit("openMap", key, keyPath);
      }
    },
    handleClose (key, keyPath) {
      this.selectId = keyPath[0]
      if (key == 999) {
        this.colorId = "";
        return;
      }
      if (keyPath.length == 1) {
        this.colorId = "";
        this.$emit("openMap", key, keyPath);
      }
    },
    //三级触发
    onSelect (key, keyPath) {
      this.$emit("openDetail", key, keyPath);

    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .el-menu {
  border-right: solid 0px #e6e6e6;
  background-color: transparent;
}

/deep/ .el-menu .el-submenu__title .el-icon-arrow-down {
  display: none;
}

/deep/ .el-menu .el-menu--inline {
  margin-bottom: 5px;
  margin-top: 25px;
}

/deep/ .el-menu .el-menu--inline .el-menu--inline {
  margin-bottom: 5px;
  margin-top: 0px;
}

/deep/ .el-submenu__title {
  position: relative;
}

/deep/.el-menu .el-menu-item:hover {
  outline: 0 !important;
  // color: #2E95FB !important;
  background: #46a1ff !important;
}

/deep/.el-menu .el-submenu:hover {
  outline: 0 !important;
  // color: #2E95FB !important;
  background: transparent !important;
}

/deep/.el-menu .el-submenu__title:hover {
  outline: 0 !important;
  // color: #2E95FB !important;
  background: transparent !important;
}

/deep/.el-menu .el-menu-item:hover {
  outline: 0 !important;
  // color: #2E95FB !important;
  background: rgba(70, 161, 255, 0.2) !important;
}

/deep/.el-menu .el-menu-item {
  transition: border-color 0.3s, background-color 0.3s, color 0.3s;
  height: 68px !important;
  padding: 0 !important;
  min-width: 120px;
  background: #021240;
}

/deep/.el-menu .el-menu-item.is-active {
  background: rgba(70, 161, 255, 0.2) !important;
}

/deep/.el-menu .el-menu-item.is-active::before {
  position: absolute;
  content: "";
  top: 0px;
  bottom: 0px;
  left: 0px;
  width: calc(100% - 60px);
  border-left: 3px solid #46a1ff;
  z-index: 0;
}

/deep/.el-menu--inline .el-submenu {
  margin-bottom: 5px;
}

/deep/.el-submenu {
  margin-bottom: 29px;
}

/deep/.modelData .el-menu--inline {
  margin-top: 0;
}
.imgX{
  background-image: url("~@/assets/images/ai/submenu_x(1).png");

}
.img{
  background-image: url("~@/assets/images/ai/submenu.png");

}
.back {
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
</style>