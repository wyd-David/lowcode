<template>
  <div style="height: 35%">
    <div :id="uuid" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import selectLunci from "./selectLunci.vue";
import chartColumnOptions from "./chart-column-options";
import { getXunchaCitiesYear } from "@/api/aiXuncha/aiHome.js";
export default {
  name: "chart-column",
  title: "柱状图",
  icon: "el-icon-s-data",
  group: "chart",
  props: {
    width: Number,
    height: Number,
    echartData: Array,
    option: {
      type: Object,
      default: () => {
        return chartColumnOptions;
      },
    },
  },
  components: { selectLunci },
  data() {
    return {
      uuid: null,
      chartOption: {},
      chart: undefined,
      cptData: {},
      tabIndex: "1",
    };
  },
  watch: {
    
    width() {
      this.chart.resize();
    },
    height() {
      this.chart.resize();
    },
  },
  created() {
    // this.cptData = this.echartData || chartColumnOptions.cptDataForm;

    this.uuid = require("uuid").v1();
  },
  mounted() {
    this.chart = this.$echarts.init(document.getElementById(this.uuid));
    // this.refreshCptData();
  },
  methods: {
    refreshCptData() {
      this.loadData();
    },

    loadData() {
      this.loadChart(this.option.attribute);
    },
    loadChart(attribute) {
      const that = this;
      console.log(this.echartData);
      let cptDataForm = {
        xData: this.echartData.map((i) => i.name),
        yData: [this.echartData.map((i) => i.value)],
        yDataName:['异常数据']
      };
      this.cptData = cptDataForm;

      this.chart.clear();
      let columnColor = attribute.barColor;
      if (attribute.gradualColor) {
        columnColor = new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: attribute.barColor1 },
          { offset: 0.5, color: attribute.barColor2 },
          { offset: 1, color: attribute.barColor3 },
        ]);
      }
      that.chartOption = {
        color: columnColor,
        title: {
          text: attribute.chartTitle,
          textStyle: {
            color: attribute.titleTextColor,
            fontSize: attribute.titleFontSize,
          },
          left: attribute.titleLeft,
          top: attribute.titleTop,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        legend: {
          show: attribute.legendShow,
          itemHeight: 16,
          itemWidth: 24,
          y: "top",
          textStyle: {
            //图例文字的样式
          },
        },
        grid: {
          x: 10,
          y: 40,
          x2: 10,
          y2: 40,
          containLabel: true,
        },
        xAxis: {
          show: attribute.xAxisShow,
          type: "category",
          data: this.cptData.xData,
          axisLabel: {
            color: attribute.xLabelColor,
            rotate: attribute.xFontRotate, //倾斜角度-180~180
          },
          axisLine: {
            show: attribute.xLineShow,
            lineStyle: {
              color: attribute.xLineColor,
            },
          },
          axisTick: {
            //x轴刻度线
            show: attribute.xTickShow,
          },
        },
        yAxis: {
          name: attribute.yAxisName,
          show: attribute.yAxisShow,
          type: "value",
          nameTextStyle: {
            padding: [0, 0, 0, 40], // 上右下左与原位置距离
          },
          axisLabel: {
            color: attribute.yLabelColor,
          },
          axisLine: {
            show: attribute.yLineShow,
            lineStyle: {
              color: attribute.yLineColor,
            },
          },
          axisTick: {
            //y轴刻度线
            show: attribute.yTickShow,
          },
          splitLine: {
            lineStyle: {
              // 使用深浅的间隔色
              color: "rgba(255, 255, 255, .3)",
            },
            //网格线
            show: attribute.yGridLineShow,
          },
        },
        dataZoom: attribute.dataZoom,
        series: [],
      };
      // 处理多个数据
      if (this.cptData.yData) {
        let seriesArr = [];
        let seriesColor = [
          //   new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
          //     { offset: 0, color: "#F8A16F" }, //渐变头部色
          //     { offset: 1, color: "#FC5689" },
          //   ]),
          new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
            { offset: 0, color: "#80E2FE" }, //渐变头部色
            { offset: 1, color: "#8A89FF" },
          ]),
          new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
            { offset: 0, color: "#FAD961" }, //渐变头部色
            { offset: 1, color: "#F76B1C" },
          ]),
        ];
        this.cptData.yData.forEach((item, i) => {
          console.log(this.cptData);
          seriesArr.push({
            name: this.cptData.yDataName ? this.cptData.yDataName[i] : "",
            data: item,
            type: attribute.barType || "bar", //pictorialBar || bar
            showBackground: attribute.barBgShow,
            symbol: attribute.barPath,
            barWidth: attribute.barWidth,
            itemStyle: {
              //   borderRadius:
              normal: {
                color: seriesColor[i],
                barBorderRadius: attribute.barBorderRadius,
              },
            },
            label: {
              show: attribute.barLabelShow, //开启显示
              position: "top", //在上方显示
              color: attribute.barLabelColor,
              fontSize: attribute.barLabelSize,
            },
          });
        });
        this.chartOption.series = seriesArr;
      }

      that.chart.setOption(that.chartOption);
      window.addEventListener("resize", () => {
        this.chart.resize();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
  /deep/ .el-table__empty-block::after{
    background-image: url(~@/assets/images/newNoData.png) !important;
  }
.tabBox {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 8px;
  position: relative;
  /deep/.el-tabs__item {
    padding: 0 10px /* 15/144 */ !important;
    color: #fff;
    box-sizing: border-box;
  }
  .select {
    position: absolute;
    top: -5px;
    right: 20px;
  }
  /deep/.is-active {
    transition: 0.3s;
    background: linear-gradient(
      0deg,
      rgba(0, 148, 255, 0.4) 0%,
      rgba(0, 149, 255, 0) 100%
    );
  }
  /deep/.el-tabs__active-bar {
    background: #4cdcf3;
  }
}
</style>
