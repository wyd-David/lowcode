<template>
  <div id="ai" style="height:100%;overflow: auto;">
    <div class="ai-container">
      <img src="@/assets/images/ai/backgrounds.png" alt="" class="backimg" />
      <img src="@/assets/images/ai/pan.png" alt="" class="panimg" v-if="mapOpen" ref="pan" />
      <!-- 头部标题以及时间模块 -->
      <div class="header">
        <!-- <header-tab style="margin-top: 2rem" /> -->
        <div style="width:25%"></div>
        <div class="headtitle">
          <img src="@/assets/images/ai/logo.png" style="width: 2rem; height: 2rem; margin-right: 0.5rem" />
          <div style="font-size: 1.9rem">嵌入式廉洁风险防控预警平台-AI巡察员</div>
        </div>
        <div style="margin-top: 3rem; width: 25%; text-align: right">
          <span style="margin-right:1rem;">{{ userName }}</span>
          <span>{{ date }}</span>
        </div>
      </div>
      <div class="app-container">
        <!-- 左边菜单 menu -->
        <div class="left">
          <el-row class="tac">
            <el-col :span="24">
              <model-terr @openMap="openMap" @openUpload="openUpload" @openDetail="openDetail" />
            </el-col>
          </el-row>
        </div>
        <!-- 右边主体内容 -->
        <div class="boxMore">
          <div class="boxBottom">
            <!-- 图表模块 - 地图模块 -->
            <div class="center" v-show="mapOpen">
              <div class="ai-list-title">
                <div class="flex">
                  <span class="line"></span><span>全省差旅异常情况</span>
                </div>
                <el-select v-model="mapObj.xunchaLunciId" style="width: 250px" :popper-append-to-body="false"
                  @change="onChangeLunci">
                  <el-option v-for="item in options" :key="item.lunciId" :label="item.lunciName" :value="item.lunciId">
                  </el-option>
                </el-select>
              </div>
              <div style="width: 100%; height: 60%" ref="mapModelx">
                <map-model :echartData="mapData" ref="mapRef" :mapObj="mapObj" v-if="mapOpen"></map-model>
              </div>
              <!-- 柱状图 -->
              <centerEchart ref="centerbarRef" :echartData="mapData" v-if="mapOpen" />
            </div>
            <!-- 图表模块 - 右侧模块 -->
            <div class="right" v-show="mapOpen">
              <el-scrollbar style="height: 100%">
                <right-pie-echart ref="pieRef" :xunchaLunciId="mapObj.xunchaLunciId" />
                <right-table ref="tableRef" :xunchaLunciId="mapObj.xunchaLunciId" />
                <right-bar ref="barRef" :xunchaLunciId="mapObj.xunchaLunciId" />
              </el-scrollbar>
            </div>
            <!-- 模型计算列表页 -->
            <div class="detail" v-show="detailOpen">
              <model-detail :tabsData="tabsData" :detailData="detailData" v-if="detailOpen"></model-detail>
            </div>
            <!-- 模型数据上传 -->
            <div class="detail" v-show="uploadOpen">
              <upload v-if="uploadOpen"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ModelTerr from "./components/menu.vue";
import rightPieEchart from "./components/rightPieEchart.vue";
import rightTable from "./components/rightTable.vue";
import rightBar from "./components/rightBar.vue";
import centerEchart from "./components/center-echart.vue";
import upload from "./components/modelDataUpload.vue";
import mapModel from "./components/mapModel.vue";
import modelDetail from "./components/modelDetail.vue";
import selectLunci from "./components/selectLunci.vue";
// import headerTab from "./components/header.vue";
import { queryLunciList } from "@/api/xuncha/common/base";
import { getXunchaCitiesYear, getDateStr } from "@/api/aiXuncha/aiHome.js";

export default {
  components: {
    upload,
    ModelTerr,
    centerEchart,
    selectLunci,
    rightPieEchart,
    rightTable,
    rightBar,
    mapModel,
    modelDetail,
  },
  data () {
    return {
      userName: this.$store.getters.nickName,
      mapObj: {
        xunchaLunciId: null,
        listId: null,
        yearList: [],
      },
      true: true,
      false: false,
      mapOpen: false,
      detailOpen: false,
      uploadOpen: false,
      detailData: {
        id: null,
      },
      tabsData: {},
      date: "",
      options: [],
      mapData: [],
    };
  },
  created () {
    let that = this;
    queryLunciList().then((res) => {
      this.options = res.data;
      this.mapObj.xunchaLunciId = this.options[0].lunciId;
      this.getMapData();
    });
    getDateStr().then(res => {
      this.date = res.msg
    })
  },
  methods: {
    resetMap () {
      let that = this;
      this.$nextTick(() => {
        //重新渲染
        console.log(that.$refs.mapModelx.offsetWidth, "px");
        that.$refs.pan.style.width =
          that.$refs.mapModelx.offsetWidth / 10 + "rem";
        // that.$refs.mapModelx.resetMap()
      });
    },
    onChangeLunci () {
      this.getMapData();
    },
    getMapData () {
      getXunchaCitiesYear(this.mapObj.xunchaLunciId).then((res) => {
        this.mapData = res.data.dataList;
        this.mapObj.yearList = res.data.yearList;
        this.$nextTick(() => {
          this.$refs.mapRef.cartographic();
          this.$refs.centerbarRef.refreshCptData();
        });
      });
    },
    // 重新请求数据渲染
    upData () {
      this.$nextTick(() => {
        this.$refs.pieRef.getData()
        this.$refs.tableRef.getData()
        this.$refs.barRef.getData()
      })
    },
    openMap (id, arr) {
      console.log(id, "父组件");
      console.log("触发一级，先不管")
      if (arr && arr.length == 2) {
        this.createdTab(id, "map", arr);
        return;
      }
      //拿到列表id，再拿到最新轮次id，传给地图
      let obj = {
        lunciId: "2",
        listId: id,
      };
      // this.mapObj = obj;
      this.mapOpen = true;
      if (this.mapObj.xunchaLunciId) {
        this.getMapData();
        this.resetMap();
        this.upData()
      }
      this.detailOpen = false;
      this.uploadOpen = false;
      this.createdTab(id, "map", arr);
      //地图，需要：<select-lunci><map-model><centerEchart><right-pie-echart /> <right-table /> <right-bar />
    },
    openUpload (id) {
      console.log(id, "拿到详情id");
      this.mapOpen = false;
      this.detailOpen = false;
      this.uploadOpen = true;
      this.createdTab(id, "upload");
      //模型上传 ，需要：<upload />
    },
    openDetail (id, arr) {
      console.log(id, "拿到详情id");
      this.createdTab(id, "detail", arr);
      this.detailData = { id };
      this.$nextTick(() => {
        this.mapOpen = false;
        this.detailOpen = true;
        this.uploadOpen = false;
      })
      //详情 ，需要model-detail
    },
    createdTab (data, type, arr) {
      console.log(data, "生成所需要的参数", type);
      this.tabsData = { id: data, type, arr };
    },
  },
  mounted () {
  },
};
</script>

<style lang="scss">
body .ai_popper {
  background-image: url(~@/assets/images/ai/BG.png) !important;
  background-color: rgba(0, 0, 0, 0);
  border: 0px;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

// body .ai_popper{
//   border: none !important;
//   background-image: url(~@/assets/images/ai/BG.png) !important;
// }
body .el-loading-mask {
  background-color: rgba(1, 18, 65, 0.5) !important;
}

body .el-loading-spinner .el-loading-text {
  color: #3b9cff;
}

body .el-loading-spinner .path {
  -webkit-animation: loading-dash 1.5s ease-in-out infinite;
  animation: loading-dash 1.5s ease-in-out infinite;
  stroke-dasharray: 90, 150;
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: #3b9cff;
  stroke-linecap: round;
}
</style>

<style lang="scss" scoped>
body .el-picker-panel,
body .ai_popper {
  border: none !important;
  background-color: #000 !important;
}

/deep/ .right .el-scrollbar__wrap {
  overflow-x: hidden;
}

/deep/ .select .el-scrollbar__wrap {
  overflow: scroll;
}

.ai-container {
  .flex {
    display: flex;
    align-items: center;
  }

  width: 100vw;
  height: 100vh;
  position: relative;
  min-width: 1580px;
  min-height: 768px;
  overflow: hidden;

  .backimg {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 1;
  }

  .panimg {
    position: absolute;
    width: auto;
    height: 40%;
    top: 59%;
    left: 47%;
    transform: translate(-50%, -50%);
    z-index: 2;
  }

  .header {
    width: 99%;
    color: #fff;
    position: absolute;
    font-size: 1rem;
    padding: 0rem 2.5rem 0rem;
    z-index: 3;
    box-sizing: border-box;
    display: flex;

    .headtitle {
      width: 50%;
      display: flex;
      justify-content: center;
      padding-top: 5px;
    }

    /deep/ .el-breadcrumb__inner {
      font-size: 1rem;
      color: #72fdfe;
    }
  }

  .app-container {
    width: 100%;
    height: 100%;
    position: absolute;
    padding: 5rem 2.5rem 2rem;
    z-index: 3;
    display: flex;
    flex-direction: row;

    // justify-content: center;
    .left {
      width: 22%;
      margin-right: 1%;
      background: transparent;
    }

    .boxMore {
      display: flex;
      flex-direction: column;
      width: 76%;

      .boxBottom {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: row;

        .center {
          width: 60%;

          // background: #00000060;
          .ai-list-title {
            color: #fff;
            display: flex;
            align-items: center;
            box-shadow: inset 0px -1px 0px 0px #4cdcf3;
            border-radius: 2px 2px 0px 0px;
            padding-bottom: 10px;
            justify-content: space-between;
            padding-right: 15px;

            .line {
              display: inline-block;
              width: 4px;
              height: 25px;
              background: #fff;
              border-radius: 2px;
              margin-right: 10px;
              margin-left: 20px;
            }
          }
        }

        .right {
          width: 39%;
          margin-left: 1%;
          background-image: url("~@/assets/images/ai/right-layout-back.png");
          background-size: 100% 100%;
          background-repeat: no-repeat;
          padding: 10px 0 20px 0;
        }

        .detail {
          width: 100%;
          margin-left: 1%;
          margin-right: 1%;
        }
      }
    }
  }
}

// /deep/ .el-select-dropdown.el-popper[x-placement^="bottom"] {
//   background: #041141 !important;
//   border-color: #ffffff15;
// }

// /deep/ .el-popper[x-placement^="bottom"] .popper__arrow::after {
//   top: 1px;
//   margin-left: -6px;
//   border-top-width: 0;
//   border-bottom-color: #ffffff15;
// }

// /deep/ .el-select-dropdown__empty {
//   background: #041141 !important;

// }

/deep/ .el-select-dropdown.el-popper[x-placement^="bottom"] {
  background: #041141 !important;
  border-color: #ffffff15;
}

/deep/ .el-popper[x-placement^="bottom"] .popper__arrow::after {
  top: 1px;
  margin-left: -6px;
  border-top-width: 0;
  border-bottom-color: #ffffff15;
}

/deep/ .el-select-dropdown__item:hover {

  background: linear-gradient(180deg, #0f4ca6 0%, rgba(18, 74, 156, 0.07) 100%) !important;
}

/deep/ .el-select-dropdown__item.hover,
.el-select-dropdown__item:hover,
.el-select-dropdown__item:hover {
  // background-color: #F5F7FA;
  background: linear-gradient(180deg, #0f4ca6 0%, rgba(18, 74, 156, 0.07) 100%);
}

/deep/ .el-select-dropdown__item.selected {
  color: #fff;
}

/deep/.el-table tbody tr:hover>td { background-color: #3F9DFF70 !important; }
</style>
