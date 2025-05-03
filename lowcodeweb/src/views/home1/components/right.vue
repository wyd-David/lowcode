<template>
  <div class="homePage-right">
    <div class="homePage-item">
      <div class="homePage-item-header">人才队伍建设</div>
      <div class="homePage-item-context" style="line-height: 30px">
        <div class="team-item">
          <div class="team-item-header">
            人才库<span style="color: rgba(23, 35, 61, 0.45)">(人)</span>
          </div>
          <div class="team-item-context">
            {{ teamData["0"] ? teamData["0"]["number"] : 0 }}
          </div>
        </div>
        <div class="team-item">
          <div class="team-item-header">
            支撑人才库<span style="color: rgba(23, 35, 61, 0.45)">(人)</span>
          </div>
          <div class="team-item-context">
            {{ teamData["1"] ? teamData["1"]["number"] : 0 }}
          </div>
        </div>
        <div class="team-item">
          <div class="team-item-header">
            组长库<span style="color: rgba(23, 35, 61, 0.45)">(人)</span>
          </div>
          <div class="team-item-context">
            {{ teamData["2"] ? teamData["2"]["number"] : 0 }}
          </div>
        </div>
      </div>
    </div>

    <div class="homePage-item">
      <div class="homePage-item-header">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="职级分布" name="1"></el-tab-pane>
          <el-tab-pane label="年龄分布" name="2"></el-tab-pane>
          <el-tab-pane label="专家领域分布" name="3"></el-tab-pane>
        </el-tabs>
      </div>
      <div class="homePage-item-context" style="min-height: 245px">
        <div
          v-if="activeName == 1 || activeName == 3"
          style="display: flex; flex: 1"
        >
          <chart-column
            :echartData="columnData"
            ref="column1"
            :option="{
              ...chartColumnOptions,
              attribute: {
                ...chartColumnOptions.attribute,
              },
            }"
            style="height: 100%"
          ></chart-column>
        </div>
        <div v-if="activeName == 2" style="display: flex; flex: 1">
          <chart-pie
            :echartData="ageData1"
            ref="pie1"
            :option="{
              attribute: {
                ...chartPieOptions.attribute,
                chartTitle: '人才库',
                titleFontSize: 12,
                titleY: 100,
                titleX: 25,
              },
            }"
            style="height: 120px"
          ></chart-pie>
          <chart-pie
            :echartData="ageData2"
            ref="pie2"
            :option="{
              ...chartPieOptions,
              attribute: {
                ...chartPieOptions.attribute,
                chartTitle: '支撑人才库',
                titleFontSize: 12,
                titleY: 100,
                titleX: 15,
              },
            }"
            style="height: 120px"
          ></chart-pie>
          <chart-pie
            :echartData="ageData3"
            ref="pie3"
            :option="{
              ...chartPieOptions,
              attribute: {
                ...chartPieOptions.attribute,
                chartTitle: '组长库',
                titleFontSize: 12,
                titleY: 100,
              },
            }"
            style="height: 120px"
          ></chart-pie>
        </div>
      </div>
    </div>

    <div class="homePage-item">
      <div class="homePage-item-header">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="优秀巡察组" name="1"></el-tab-pane>
          <el-tab-pane label="优秀巡察员" name="2"></el-tab-pane>
        </el-tabs>
        <div class="homePage-item-header-more">
          更多 <i class="el-icon-arrow-right"></i>
        </div>
      </div>
      <div class="homePage-item-context">
        <el-table
          v-if="activeName == 1"
          :data="tableData"
          style="width: 100%"
          height="240px"
        >
          <el-table-column prop="text" label="排名"> </el-table-column>
          <el-table-column prop="text" label="巡察组"> </el-table-column>
          <el-table-column prop="text" label="综合得分"> </el-table-column>
          <el-table-column min-width="100" prop="text" label="被巡察单位">
          </el-table-column>
        </el-table>

        <el-table
          v-if="activeName == 2"
          :data="tableData"
          style="width: 100%"
          height="240px"
        >
          <el-table-column prop="text" label="姓名"> </el-table-column>
          <el-table-column prop="text" label="公司"> </el-table-column>
          <el-table-column prop="text" label="领域"> </el-table-column>
          <el-table-column prop="text" label="得分"> </el-table-column>
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
    chartPieOption.attribute.legendShow = false;
    chartPieOption.attribute.labelLineShow = false;
    chartPieOption.attribute.titleTextColor = "#1590ff";
    chartPieOption.attribute.titleFontSize = 13;
    chartPieOption.attribute.titleX = 30;
    chartPieOption.attribute.titleY = 150;
    chartColumnOption.attribute.barLabelShow = false;
    chartColumnOption.attribute.dataZoom = {
      show: true, // 为true 滚动条出现
      realtime: true,
      type: "slider", // 有type这个属性，滚动条在最下面，也可以不行，写y：36，这表示距离顶端36px，一般就是在图上面。
      height: 20, // 表示滚动条的高度，也就是粗细
      start: 0, // 表示默认展示20%～80%这一段。
      end: 50,
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
      chartColumnOptions: chartColumnOption,
      tableData: [],
      activeName: "1",
      yearDate: [],
      fieldType: [],
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
  },
  methods: {
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
          yDataName: ["人才库", "支撑人才库", "巡察人才库"],
        };
        this.columnData = this.levelData;
        this.chartInit();
      });
    },
    getPersonFieldCount() {
      getPersonFieldCountApi({ collectionType: 1 }).then((res) => {
        let yTempArr = [];
        let xTempArr = [[]];
        res.data.forEach((i) => {
          let obj = this.fieldType.find(
            (item) => item.dictValue == i.fieldType
          );
          yTempArr.push(obj.dictLabel);
          xTempArr[0].push(i.number);
        });
        this.fieldData = {
          xData: yTempArr.join(","),
          yData: xTempArr,
          yDataName: ["人才库"],
        };
      });
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
      if (this.activeName == 1) {
        this.columnData = this.levelData;
      }
      if (this.activeName == 3) {
        this.columnData = this.fieldData;
      }
      this.chartInit();
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
.team-item {
  text-align: center;
  &-header {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
  }
  &-context {
    font-size: 24px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
  }
}
/deep/.el-table {
  /*css主要部分的样式*/
  /*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 6px; /*对垂直流动条有效*/
    height: 6px; /*对水平流动条有效*/
  }

  /*定义滚动条的轨道颜色、内阴影及圆角*/
  ::-webkit-scrollbar-track {
    border-radius: 4px;
    /* -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3); */
    /* background-color: rosybrown; */
  }

  /*定义滑块颜色、内阴影及圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 8px;
    background-color: #dddee0;
    /* -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3); */
  }

  /*定义滑块悬停变化颜色、内阴影及圆角*/
  ::-webkit-scrollbar-thumb:hover {
    background-color: #c7c9cc;
  }

  /*定义两端按钮的样式*/
  ::-webkit-scrollbar-button {
    /* background-color: cyan; */
  }

  /*定义右下角汇合处的样式*/
  ::-webkit-scrollbar-corner {
    /* background: khaki; */
  }
}
</style>
