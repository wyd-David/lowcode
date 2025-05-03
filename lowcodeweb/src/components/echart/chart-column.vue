<template>
  <div :id="uuid" style="width: 100%; height: 100%"></div>
</template>

<script>
import chartColumnOptions from "./chart-column-options";
export default {
  name: "chart-column",
  title: "柱状图",
  icon: "el-icon-s-data",
  group: "chart",
  props: {
    width: Number,
    height: Number,
    echartData: Object,
    option: {
      type: Object,
      default: () => {
        return chartColumnOptions;
      },
    },
  },
  data() {
    return {
      uuid: null,
      chartOption: {},
      chart: undefined,
      cptData: {},
    };
  },
  watch: {
    "option.attribute": {
      handler(obj, newObj) {
        debugger;
        this.loadChart(newObj);
      },
      deep: true, //深度监听
    },
    echartData: {
      handler(obj, newObj) {
        if (newObj) {
          this.cptData = newObj;
        }
        this.loadChart(this.chartOption);
      },
      deep: true, //深度监听
    },
    width() {
      this.chart.resize();
    },
    height() {
      this.chart.resize();
    },
  },
  created() {
    this.cptData = this.echartData || chartColumnOptions.cptDataForm;
    this.uuid = require("uuid").v1();
  },
  mounted() {
    this.chart = this.$echarts.init(document.getElementById(this.uuid));
    this.refreshCptData();
  },
  methods: {
    reLoad(data) {
      this.cptData = data;
      this.refreshCptData();
    },
    refreshCptData() {
      this.loadData();
    },
    loadData() {
      this.loadChart(this.option.attribute);
    },
    loadChart(attribute) {
      const that = this;
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
          selectedMode: attribute.selectedMode,
          itemHeight: 16,
          itemWidth: 24,
          y: "top",
          textStyle: {
            //图例文字的样式
          },
        },
        grid: {
          x: 5,
          y: 40,
          x2: 5,
          y2: 40,
          right: attribute.gridRight,
          containLabel: true,
        },
        xAxis: {
          name: attribute.xName,
          show: attribute.xAxisShow,
          type: "category",
          data: this.cptData.xData.split(","),
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
          name: attribute.yName,
          show: attribute.yAxisShow,
          type: "value",
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
          new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
            { offset: 0, color: "#F8A16F" }, //渐变头部色
            { offset: 1, color: "#FC5689" },
          ]),
          new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
            { offset: 0, color: "#80E2FE" }, //渐变头部色
            { offset: 1, color: "#8A89FF" },
          ]),
          new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
            { offset: 0, color: "#F76B1C" }, //渐变头部色
            { offset: 1, color: "#FAD961" },
          ]),
        ];
        this.cptData.yData.forEach((item, i) => {
          seriesArr.push({
            name: this.cptData.yDataName ? this.cptData.yDataName[i] : "",
            data: item,
            type: attribute.barType || "bar", //pictorialBar || bar
            showBackground: attribute.barBgShow,
            symbol: attribute.barPath,
            barWidth: attribute.barWidth,
            itemStyle: {
              borderRadius: attribute.barBorderRadius,
              normal: {
                color: seriesColor[i],
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
    },
  },
};
</script>

<style scoped></style>