<template>
  <div class="homePage-center">
    <div class="homePage-item" style="height: 3.45694rem; /* 505/144 */">
      <div class="homePage-item-header" style="background-color: #f5f7f9">
        <!-- <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="全省巡察情况" name="1"></el-tab-pane>
          <el-tab-pane label="全省整改情况" name="2"></el-tab-pane>
        </el-tabs> -->
        全省巡察情况
        <div class="homePage-item-header-date">
          <el-select
            v-model="lunciId"
            @change="getCityData"
            size="mini"
            style="width: 100px"
          >
            <el-option
              v-for="item in lunciData"
              :key="item.lunciId"
              :label="item.lunciName"
              :value="item.lunciId"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div
        class="homePage-item-context"
        style="
          flex-direction: column;
          padding-top: 0;
          background-color: #f5f7f9;
        "
      >
        <chart-map
          :statusName="['未开展巡察', '正在开展巡察', '已完成巡察']"
          ref="map1"
          :option="mapOption"
          style="height: 2.929722rem; /* 419/144 */"
        ></chart-map>
      </div>
    </div>

    <div class="homePage-item">
      <div class="homePage-item-header">
        年度巡察进度
        <div class="homePage-item-header-date">
          <el-select
            @change="getYearProgressRate"
            v-model="year"
            size="mini"
            style="width: 100px"
          >
            <el-option
              v-for="item in yearData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div
        class="homePage-item-context"
        style="flex-direction: column; height: 1.256111rem; /* 214/144 */"
      >
        <el-carousel :interval="10000" indicator-position="outside">
          <el-carousel-item v-for="(item, index) in progressData" :key="index">
            <div
              class="schedule-item"
              v-for="(citem, cindex) in item"
              :key="cindex"
            >
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size: 0.097222rem; /* 14/144 */">
                {{ citem.piciName }}
              </div>
              <el-progress
                style="width: 50%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="handleStatusToPer(citem.status)"
              ></el-progress>
              <div
                :class="
                  citem.status == 7
                    ? 'schedule-item-status completed'
                    : 'schedule-item-status'
                "
              >
                {{ selectDictLabel(statusOptions, citem.status) }}
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getYearProgressRateApi,
  getAllProvinceApi,
  getLunciDataApi,
  getProvinceConditionApi,
} from "@/api/home";
import ChartMap from "@/components/echart/chart-map.vue";
export default {
  components: { ChartMap },
  data() {
    return {
      year: "2022",
      activeName: "1",
      lunciId: "",
      yearDate: [],
      progressData: [],
      lunciData: [],
      cityData: [],
      statusToPer: [0, 0, 20, 30, 50, 60, 80, 100],
      st: null,
      statusOptions: [],
      mapOption: {
        attribute: {
          roam: false,
          titleText: "",
          titleLeft: "center",
          titleTop: 10,
          subtext: "",
          titleFontSize: 20,
          titleColor: "#ddd",
          subTitleColor: "#aaa",
          subTitleFontSize: 13,
          geoLabelColor: "#333",
          geoLabelSize: 12,
          piecesOptions: [
            {
              gte: 0,
              lt: 0,
              label: "未开展",
              color: "#ffffff",
            },
          ],
        },
      },
    };
  },
  created() {
    this.getDicts("xuncha_pici_manager_status").then((response) => {
      this.statusOptions = response.data || [];
    });
    getLunciDataApi().then((res) => {
      this.lunciData = res.data;
      if (res.data[0]) {
        this.lunciId = res.data[0].lunciId;
      }
      this.getCityData();
    });
    this.getYears();
    this.getYearProgressRate();
  },
  methods: {
    handleStatusToPer(e) {
      return this.statusToPer[e];
    },
    getYearProgressRate() {
      getYearProgressRateApi({
        createdTime: this.year + "-01-01",
      }).then((res) => {
        res.data.forEach((item, i) => {
          item.i = i;
        });
        //一维数组转二维数组
        let len = res.data.length;
        let n = 3;
        let lineNum = len % n === 0 ? len / n : Math.floor(len / n + 1);
        let tempArr = [];
        for (let i = 0; i < lineNum; i++) {
          let temp = res.data.slice(i * n, i * n + n);
          tempArr.push(JSON.parse(JSON.stringify(temp)));
        }
        this.progressData = tempArr;
      });
    },
    getCityData() {
      getAllProvinceApi().then((r) => {
        getProvinceConditionApi({
          lunciId: this.lunciId,
        }).then((e) => {
          r.data.forEach((i) => {
            let obj = e.data.find((j) => j.companyId == i.companyId);
            i.name = i.companyName;
            i.value = obj ? obj.status : "0";
            i.taskTime = obj ?obj.taskTime:'';
          });

          // if (r.data.find((res) => res.value == 0)) {
          //   this.mapOption.attribute.piecesOptions.push({
          //     gte: 0,
          //     lt: 0,
          //     label: "未开展",
          //     color: "#ffffff",
          //   });
          // }
          if (r.data.find((res) => res.value == 1)) {
            this.mapOption.attribute.piecesOptions.push({
              gte: 1,
              lt: 1,
              label: "正在开展",
              color: "#ed9b9d",
            });
          }
          if (r.data.find((res) => res.value == 2)) {
            this.mapOption.attribute.piecesOptions.push({
              gte: 2,
              lte: 2,
              label: "已完成",
              color: "#fadbd9",
            });
          }

          this.cityData = r.data;
          this.$refs.map1 && this.$refs.map1.reLoad(r.data);
        });
      });
    },
    handleClick(e) {
      console.log(e);
    },
    chartResize() {
      if (this.$refs.map1) {
        this.$refs.map1.resize();
        clearTimeout(this.st);
        this.st = setTimeout(() => {
          this.$refs.map1.resize();
        }, 1000);
      }
    },
    //遍历年份数组
    getYears() {
      let arr = [];
      let lastYear = Number(String(new Date().getFullYear()).substring(2, 4));
      for (let i = 0; i <= lastYear - 9; i++) {
        arr.push({
          label: `${i + 2009}年`,
          value: `${i + 2009}`,
        });
      }
      this.yearData = arr.reverse();
    },
  },
};
</script>

<style lang="scss" scoped>
.schedule-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.138889rem /* 20/144 */;
  white-space: nowrap;
  img {
    width: 0.138889rem /* 20/144 */;
    height: 0.138889rem /* 20/144 */;
  }
  &-status {
    width: 0.555556rem /* 80/144 */;
    font-size: 0.097222rem /* 14/144 */;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.85);
  }
  .completed {
    color: #fb0051;
    font-weight: 600;
  }
  /deep/.el-progress-bar__inner {
    background: linear-gradient(
      106deg,
      #f8a16f 0%,
      #fc5489 84%,
      #f72166 100%
    ) !important;
  }
  /deep/.el-progress-bar__outer {
    background: rgba(218, 32, 10, 0.08) !important;
  }
}
/deep/.el-progress-bar__outer {
  border-radius: 1px !important;
}
/deep/.el-progress-bar__inner {
  border-radius: 1px !important;
}
/deep/.el-carousel__arrow {
  display: none !important;
}
/deep/.el-progress-bar__outer {
  height: 0.138889rem /* 20/144 */ !important;
}
/deep/.el-tabs__item {
  padding: 0 0.104167rem /* 15/144 */ !important;
  height: 0.277778rem /* 40/144 */ !important;
  line-height: 0.277778rem /* 40/144 */ !important;
  font-size: 0.097222rem /* 14/144 */ !important;
  font-family: PingFangSC-Medium, PingFang SC;
}
/deep/.el-tabs__item.is-active {
  color: #1590ff !important;
  font-weight: 500 !important;
}
/deep/.el-tabs__nav-next {
  line-height: 0.305556rem /* 44/144 */ !important;
}
/deep/ .el-tabs__nav-prev {
  line-height: 0.305556rem /* 44/144 */ !important;
}
/deep/.el-carousel__container {
  height: 0.92rem /* 180/144 */ !important;
}
/deep/.el-carousel__indicator--horizontal {
  padding: 0.083333rem /* 12/144 */ 0.027778rem /* 4/144 */;
}
</style>