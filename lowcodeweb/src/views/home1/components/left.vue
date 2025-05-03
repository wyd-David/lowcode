<template>
    <div ref="chartHomeRef" class="homePage-left">
      <div class="homePage-item">
        <div class="homePage-item-header">AI巡察员</div>
        <div class="homePage-item-context">
          <div class="navlist-item zflink">
            <div class="navlist-item-left">
              <p class="title">智慧巡察</p>
            </div>
            <div class="navlist-item-right">
              <img src="~@/assets/images/navIcon3.png" alt="" />
            </div>
          </div>
          <div class="navlist-item ljlink">
            <div class="navlist-item-left">
              <p class="title">廉洁风险</p>
            </div>
            <div class="navlist-item-right">
              <img src="~@/assets/images/navIcon.png" alt="" />
            </div>
          </div>
          <div class="navlist-item addlink">
            <div class="navlist-item-left">
              <p class="title">廉洁风险</p>
            </div>
            <div class="navlist-item-right">
              <img src="~@/assets/images/xtbz@2x.png" alt="" />
            </div>
          </div>
        </div>
      </div>
      <div class="homePage-item">
        <div class="homePage-item-header">巡察案例库</div>
        <div class="homePage-item-context" style="min-height: 215px;">
          <chart-pie
          ref="pie1"
            :option="{
              ...chartPieOptions,
              attribute: {
                ...chartPieOptions.attribute,
                titleFontSize: 12,
                titleY: 100,
              },
            }"
            style="height: 120px"
          ></chart-pie>
        </div>
      </div>
      <div class="homePage-item">
        <div class="homePage-item-header">
          巡察组
          <div class="homePage-item-header-date">
            <el-select size="mini" v-model="year" style="width: 100px">
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
        <div class="homePage-item-context" style="flex-direction: column;">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="过程资料库" name="1"></el-tab-pane>
            <el-tab-pane label="报告报表库" name="2"></el-tab-pane>
            <el-tab-pane label="巡察制度" name="3"></el-tab-pane>
          </el-tabs>
          <el-row :gutter="24">
            <el-col :span="8" style="text-align: center">
              <el-progress
                type="circle"
                :width="80"
                :percentage="25"
              ></el-progress>
              <p style="font-size: 12px">第一巡察组</p>
            </el-col>
            <el-col :span="8" style="text-align: center">
              <el-progress
                type="circle"
                :width="80"
                :percentage="45"
              ></el-progress>
              <p style="font-size: 12px">第二巡察组</p>
            </el-col>
            <el-col :span="8" style="text-align: center">
              <el-progress
                type="circle"
                :width="80"
                :percentage="75"
              ></el-progress>
              <p style="font-size: 12px">第三巡察组</p>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
</template>

<script>
import chartPieOptions from "@/components/echart/chart-pie-options";
import ChartPie from "@/components/echart/chart-pie.vue";
import ChartMap from "@/components/echart/chart-map.vue";
let chartPieOption = JSON.parse(JSON.stringify(chartPieOptions));
export default {
  components: { ChartPie, ChartMap },
  data() {
    chartPieOption.attribute.legendShow = false;
    chartPieOption.attribute.labelLineShow = false;
    chartPieOption.attribute.chartTitle = "巡察制度库：128";
    chartPieOption.attribute.titleTextColor = "#1590ff";
    chartPieOption.attribute.titleFontSize = 13;
    chartPieOption.attribute.titleX = 30;
    chartPieOption.attribute.titleY = 150;

    return {
      year: "",
      chartPieOptions: chartPieOption,
      tableData: [],
      activeName: "1",
      yearDate: [],
    };
  },
  created() {
    this.getYears();
  },
  methods: {
    chartResize() { 
      if (this.$refs.pie1) { 
        this.$refs.pie1.resize();
      }
    },
    handleClick(e) {
      console.log(e);
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

<style lang="scss">
</style>
