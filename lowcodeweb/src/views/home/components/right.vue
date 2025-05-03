<template>
  <div class="homePage-right">
    <div class="homePage-item" style="height: 1.118056rem">
      <div class="homePage-item-header">人才队伍建设</div>
      <div
        class="homePage-item-context"
        style="padding: 0.160611rem 0.104167rem"
      >
        <div class="team-item">
          <div class="team-item-header">
            人才库<span style="color: rgba(23, 35, 61, 0.45)">(人)</span>
          </div>
          <div class="team-item-context" @click="toUrl(1)">
            {{ teamData["0"] ? teamData["0"]["number"] : 0 }}
          </div>
        </div>
        <div class="team-item">
          <div class="team-item-header">
            支撑人才库<span style="color: rgba(23, 35, 61, 0.45)">(人)</span>
          </div>
          <div class="team-item-context" @click="toUrl(2)">
            {{ teamData["1"] ? teamData["1"]["number"] : 0 }}
          </div>
        </div>
        <div class="team-item">
          <div class="team-item-header">
            组长库<span style="color: rgba(23, 35, 61, 0.45)">(人)</span>
          </div>
          <div class="team-item-context" @click="toUrl(3)">
            {{ teamData["2"] ? teamData["2"]["number"] : 0 }}
          </div>
        </div>
      </div>
    </div>

    <div class="homePage-item" style="height: 1.763889rem">
      <div class="homePage-item-header">
        <el-tabs v-model="activeName1" @tab-click="handleClick">
          <el-tab-pane label="职级分布" name="1"></el-tab-pane>
          <el-tab-pane label="年龄分布" name="2"></el-tab-pane>
          <el-tab-pane label="专家领域分布" name="3"></el-tab-pane>
        </el-tabs>
      </div>
      <div class="homePage-item-context" style="height: 1.3rem">
        <div
          v-if="activeName1 == 1 || activeName1 == 3"
          style="display: flex; flex: 1"
        >
          <chart-column
            :echartData="columnData"
            ref="column1"
            :option="columnOption"
            style="height: 1.2rem"
          ></chart-column>
        </div>
        <div v-if="activeName1 == 2" style="width: 100%; height: 1.3rem">
          <el-carousel
            :interval="10000"
            indicator-position="none"
            style="height: 100%; width: 100%"
          >
            <el-carousel-item>
              <chart-pie
                v-if="isPieShow"
                :echartData="ageData1"
                ref="pie1"
                :option="{
                  attribute: {
                    ...chartPieOptions.attribute,
                    pieColor: ageDataCount(1)
                      ? [
                          '#91cc75',
                          '#fac858',
                          '#ee6666',
                          '#73c0de',
                          '#5470c6',
                          '#ff8c71',
                        ]
                      : ['#e1e5ea'],
                    chartTitle: '人才库' + ageDataCount(1) + '人',
                    pieCenter: ['50%', '40%'],
                    radiusInside: 40,
                    radiusOutside: 70,
                    titleFontSize: 12,
                    titleY: 100,
                    titleX: 20,
                  },
                }"
                style="height: 1.2rem; width: 100%"
              ></chart-pie>
            </el-carousel-item>
            <el-carousel-item>
              <chart-pie
                v-if="isPieShow"
                :echartData="ageData2"
                ref="pie2"
                :option="{
                  ...chartPieOptions,
                  attribute: {
                    ...chartPieOptions.attribute,
                    pieColor: ageDataCount(2)
                      ? [
                          '#91cc75',
                          '#fac858',
                          '#ee6666',
                          '#73c0de',
                          '#5470c6',
                          '#ff8c71',
                        ]
                      : ['#e1e5ea'],
                    chartTitle: '支撑人才库' + ageDataCount(2) + '人',
                    pieCenter: ['50%', '40%'],
                    radiusInside: 40,
                    radiusOutside: 70,
                    titleFontSize: 12,
                    titleY: 100,
                    titleX: 20,
                  },
                }"
                style="height: 1.2rem; width: 100%"
              ></chart-pie>
            </el-carousel-item>
            <el-carousel-item>
              <chart-pie
                v-if="isPieShow"
                :echartData="ageData3"
                ref="pie3"
                :option="{
                  ...chartPieOptions,
                  attribute: {
                    ...chartPieOptions.attribute,
                    pieColor: ageDataCount(3)
                      ? [
                          '#91cc75',
                          '#fac858',
                          '#ee6666',
                          '#73c0de',
                          '#5470c6',
                          '#ff8c71',
                        ]
                      : ['#e1e5ea'],
                    chartTitle: '组长库' + ageDataCount(3) + '人',
                    pieCenter: ['50%', '40%'],
                    radiusInside: 40,
                    radiusOutside: 70,
                    titleFontSize: 12,
                    titleY: 100,
                    titleX: 20,
                  },
                }"
                style="height: 1.2rem; width: 100%"
              ></chart-pie>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
    </div>

    <div class="homePage-item" style="height: 2.277222rem">
      <div class="homePage-item-header">
        <el-tabs v-model="activeName2" @tab-click="getExcellentPeople">
          <el-tab-pane label="优秀巡察组" name="1"></el-tab-pane>
          <el-tab-pane label="优秀巡察员" name="2"></el-tab-pane>
        </el-tabs>
        <!-- <div class="homePage-item-header-more">
          更多 <i class="el-icon-arrow-right"></i>
        </div> -->
      </div>
      <div
        class="homePage-item-context"
        style="height: 1.83rem; overflow-y: scroll"
      >
        <el-table v-if="activeName2 == 1" :data="tableData" style="width: 100%">
          <el-table-column label="排名">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column prop="groupName" label="巡察组"> </el-table-column>
          <el-table-column label="综合得分">
            <template slot-scope="scope">
              <span class="score" v-if="activeName2 == 1">
                {{ scope.row.score || 0 }}
              </span>
            </template>
          </el-table-column>
          <el-table-column min-width="100" label="被巡察单位">
            <template slot-scope="scope">
              <span>{{ scope.row.companyName }}</span>
            </template>
          </el-table-column>
        </el-table>

        <el-table v-if="activeName2 == 2" :data="tableData" style="width: 100%">
          <el-table-column
            prop="userName"
            min-width="80px"
            show-overflow-tooltip
            label="姓名"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.userName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="companyName"
            show-overflow-tooltip
            label="公司"
          >
          </el-table-column>
          <el-table-column show-overflow-tooltip label="领域">
            <template slot-scope="scope">
              <span>{{ scope.row.fieldTypeName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="finalResult" label="得分">
            <template slot-scope="scope">
              <span class="score" v-if="activeName2 == 2">
                {{ scope.row.finalResult || 0 }}
              </span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getPersonNumberCountApi,
  getPersonAgeCountApi,
  getPersonPostLevelCountApi,
  getPersonFieldCountApi,
  getExcellentPeopleApi,
  getExcellentGroupApi,
} from "@/api/home";
import { getDicts } from "@/api/system/dict/data";
import chartPieOptions from "@/components/echart/chart-pie-options";
import ChartPie from "@/components/echart/chart-pie.vue";
import chartColumnOptions from "@/components/echart/chart-column-options";
import ChartColumn from "@/components/echart/chart-column.vue";
import ChartMap from "@/components/echart/chart-map.vue";
let chartPieOption = JSON.parse(JSON.stringify(chartPieOptions));
let chartColumnOption = JSON.parse(JSON.stringify(chartColumnOptions));
export default {
  components: { ChartPie, ChartMap, ChartColumn },
  data() {
    chartPieOption.attribute.legendShow = true;
    chartPieOption.attribute.labelLineShow = false;
    chartPieOption.attribute.titleTextColor = "#1590ff";
    chartPieOption.attribute.titleFontSize = "100%";
    chartPieOption.attribute.legendTextColor = "#333333";
    chartColumnOption.attribute.yName = "人";
    chartColumnOption.attribute.xName = "职级";
    chartColumnOption.attribute.gridRight = "12%";
    chartColumnOption.attribute.barLabelShow = false;
    chartColumnOption.attribute.selectedMode = false;
    chartColumnOption.attribute.dataZoom = {
      show: true, // 为true 滚动条出现
      realtime: true,
      type: "slider", // 有type这个属性，滚动条在最下面，也可以不行，写y：36，这表示距离顶端36px，一般就是在图上面。
      height: 20, // 表示滚动条的高度，也就是粗细
      start: 0, // 表示默认展示20%～80%这一段。
      end: 20,
    };
    return {
      teamData: {},
      ageData1: null,
      ageData2: null,
      ageData3: null,
      levelData: null,
      fieldData: null,
      columnData: null,
      year: "",
      chartPieOptions: chartPieOption,
      tableData: [],
      activeName1: "1",
      activeName2: "1",
      yearDate: [],
      fieldType: [],
      loading: false,
      columnOption: {
        ...chartColumnOption,
        attribute: {
          ...chartColumnOption.attribute,
        },
      },
      isPieShow: false,
    };
  },
  created() {
    this.getYears();
    getDicts("field_type").then((dict) => {
      this.fieldType = dict.data;
      this.getPersonFieldCount();
    });
    this.getPersonNumberCount();
    this.getPersonAgeCount();
    this.getPersonPostLevelCount();
    this.getExcellentPeople();
  },
  methods: {
    toUrl(t) {
      let url = {};
      if (t == 1) {
        url.path = "/talent/info/info";
      } else if (t == 2) {
        url.path = "/talent/info/info";
        url.query = { activeName: "巡察支撑人才库" };
      } else if (t == 3) {
        url.path = "/talent/info/groupLeaderHistory";
      }

      this.$router.push(url);
    },
    ageDataCount(t) {
      let count = 0;
      if (!this["ageData" + t]) {
        return count;
      }
      this["ageData" + t].forEach((res) => {
        count += Number(res.number);
      });
      return count;
    },
    getPersonNumberCount() {
      getPersonNumberCountApi().then((res) => {
        this.teamData = res.data;
      });
    },
    getPersonAgeCount() {
      getPersonAgeCountApi().then((res) => {
        Object.keys(res.data).forEach((key) => {
          res.data[key].forEach((i) => {
            i.value = i.number;
            i.name = "年龄" + i.name;
          });
        });
        this.ageData1 = res.data[1];
        this.ageData2 = res.data[2];
        this.ageData3 = res.data[3];
      });
    },
    //职级分布数据
    getPersonPostLevelCount() {
      getPersonPostLevelCountApi().then((res) => {
        let yTempArr = [];
        let xTempArr = [[], [], []];
        Object.keys(res.data).forEach((i) => {
          res.data[i].forEach((j) => {
            if (yTempArr.indexOf(j.postLevel) == -1) {
              yTempArr.push(j.postLevel);
            }
          });
        });

        yTempArr.forEach((i) => {
          let temp1 = res.data[1].find((j) => j.postLevel == i) || {
            number: 0,
          };
          let temp2 = res.data[2].find((j) => j.postLevel == i) || {
            number: 0,
          };
          let temp3 = res.data[3].find((j) => j.postLevel == i) || {
            number: 0,
          };
          xTempArr[0].push(Number(temp1.number));
          xTempArr[1].push(Number(temp2.number));
          xTempArr[2].push(Number(temp3.number));
        });

        this.levelData = {
          xData: yTempArr.join(","),
          yData: xTempArr,
          yDataName: ["人才库", "支撑人才库", "组长库"],
        };
        this.columnData = this.levelData;
        this.$nextTick(() => {
          this.chartInit();
        });
      });
    },
    //获取专家领域分布数据
    getPersonFieldCount() {
      let yTempArr = [];
      let xTempArr = [[], [], []];
      getPersonFieldCountApi({ collectionType: 1 }).then((res) => {
        //组装数据
        res.data.sort((a, b) => {
          return b.number - a.number;
        });
        res.data.forEach((i) => {
          let obj = this.fieldType.find(
            (item) => item.dictValue == i.fieldType
          );
          i.fieldType = obj ? obj.dictLabel : "无";
          yTempArr.push(obj ? obj.dictLabel : "无");
        });

        let tempArr = [];
        yTempArr.forEach((item) => {
          if (tempArr.indexOf(item) === -1) {
            tempArr.push(item);
          }
        });
        yTempArr = tempArr;

        yTempArr.forEach((i) => {
          let temp1 = res.data.find((item) => item.fieldType == i) || {
            number: 0,
          };
          xTempArr[0].push(Number(temp1.number));
        });

        this.fieldData = {
          xData: yTempArr.join(","),
          yData: xTempArr,
          yDataName: ["人才库"],
        };
      });
    },
    getExcellentPeople() {
      this.tableData = [];
      if (this.activeName2 == "2") {
        getExcellentPeopleApi({
          createdTime: new Date().getFullYear() + "-01-01",
        }).then((res) => {
          this.tableData = res.data;
        });
      } else {
        getExcellentGroupApi({
          createdTime: new Date().getFullYear() + "-01-01",
        }).then((res) => {
          this.tableData = res.data;
        });
      }
    },
    chartInit() {
      this.$nextTick(() => {
        if (this.$refs.column1) {
          this.$refs.column1.reLoad(this.columnData);
        }
        if (this.$refs.pie1) {
          this.$refs.pie1.refreshCptData();
          this.$refs.pie2.refreshCptData();
          this.$refs.pie3.refreshCptData();
        }
      });
    },
    chartResize() {
      this.$nextTick(() => {
        if (this.$refs.column1) {
          this.$refs.column1.chart.resize();
        }

        if (this.$refs.pie1) {
          this.$refs.pie1.chart.resize();
          this.$refs.pie2.chart.resize();
          this.$refs.pie3.chart.resize();
        }
      });
    },
    handleClick(e, event) {
      if (this.activeName1 == 1) {
        this.columnData = this.levelData;
        this.columnOption.attribute.yName = "人";
        this.columnOption.attribute.xName = "职级";
        this.columnOption.attribute.gridRight = "12%";
      }
      if (this.activeName1 == 3) {
        this.columnData = this.fieldData;
        this.columnOption.attribute.xName = "";
        this.columnOption.attribute.gridRight = "";
      }
      this.isPieShow = false;
      //解决,用了走马灯饼图显示异常的问题
      if (this.activeName1 == 2) {
        setTimeout(() => {
          this.$nextTick(() => {
            this.isPieShow = true;
            this.chartInit();
          });
        }, 0);
      }
      this.$nextTick(() => {
        this.chartInit();
      });
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
.score {
  font-size: 14px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #1590ff;
}
.team-item {
  text-align: center;
  &-header {
    font-size: 0.097222rem;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
  }
  &-context {
    font-size: 0.166667rem;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
  }
  &-context:hover {
    cursor: pointer;
    color: rgba(127, 129, 134, 0.85);
  }
}
/deep/.el-table {
  ::-webkit-scrollbar {
    width: 6px;
    height: 6px;
  }

  ::-webkit-scrollbar-track {
    border-radius: 4px;
  }

  ::-webkit-scrollbar-thumb {
    border-radius: 8px;
    background-color: #dddee0;
  }

  ::-webkit-scrollbar-thumb:hover {
    background-color: #c7c9cc;
  }
}

/deep/.el-tabs__item {
  padding: 0 0.104167rem !important;
  height: 0.277778rem !important;
  line-height: 0.277778rem !important;
  font-size: 0.097222rem !important;
  font-family: PingFangSC-Medium, PingFang SC;
}
/deep/.el-tabs__item.is-active {
  color: #1590ff !important;
  font-weight: 500 !important;
}
/deep/.el-tabs__nav-next {
  line-height: 0.305556rem !important;
}
/deep/ .el-tabs__nav-prev {
  line-height: 0.305556rem !important;
}
/deep/.el-table {
  font-size: 0.097222rem;
}
/deep/.el-carousel__container {
  height: 100%;
}
</style>
