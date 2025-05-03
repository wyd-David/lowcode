<template>
  <div :id="uuid" style="width: 100%;height:100%;"></div>
</template>

<script>
import chartLineOptions from './chart-line-options'
export default {
  name: "chart-line",
  title: "折线图",
  icon: 'el-icon-data-line',
  group: 'chart',
  props:{
    width:Number,
    height:Number,
    echartData: Object,
    option: {
      type: Object,
      default: () => { return chartLineOptions }
    }
  },
  data(){
    return {
      uuid:'',
      chartOption:{},
      chart:undefined,
      cptData:[]
    }
  },
  watch:{
    'option.attribute':{
      handler(obj,newObj) {
        this.loadChart(newObj);
      },
      deep: true,//深度监听
      immediate:false
    },
    width(){
      this.chart.resize();
    },
    height(){
      this.chart.resize();
    }
  },
  created() {
    this.cptData = this.echartData || chartLineOptions.cptDataForm;
    this.uuid = require('uuid').v1();
  },
  mounted() {
    this.chart = this.$echarts.init(document.getElementById(this.uuid));
    this.refreshCptData();
  },
  methods:{
    refreshCptData(){
      this.loadData()
    },
    loadData(){
      this.loadChart(this.option.attribute);
    },
    loadChart(attribute){
      const that = this;
      that.chartOption = {
        legend: {
          selectedMode: false,
        },
        color: attribute.lineColor,
        title: {
          text: attribute.title,
          subtext: attribute.subtext,
          left: attribute.titleLeft,
          top: attribute.titleTop,
          textStyle:{
            fontSize: attribute.titleFontSize,
            color: attribute.titleTextColor
          },
          subtextStyle: { fontSize: 12, color: attribute.subtextColor }
        },
        grid: {
          x:10,y:30,x2:10,y2:10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: that.cptData.xData.split(','),
          axisLabel:{
            show: attribute.xLabelShow,
            color: attribute.xLabelColor
          },
          axisLine:{
            show: attribute.xLineShow,
            lineStyle: {
              color: attribute.xLineColor
            }
          },
          axisTick:{//x轴刻度线
            show: attribute.xTickShow
          },
        },
        yAxis: {
          type: 'value',
          axisLabel:{
            show: attribute.yLabelShow,
            color: attribute.yLabelColor
          },
          axisLine:{
            show: attribute.yLineShow,
            lineStyle:{
              color: attribute.yLineColor
            }
          },
          axisTick:{//y轴刻度线
            show: attribute.yTickShow
          },
          splitLine: {//网格线
            show: attribute.yGridLineShow
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        series: []
      }
      
      // 处理多个数据
      if (this.cptData.yData) {
        let seriesArr = [];
        this.cptData.yData.forEach(item => {
          seriesArr.push({
            data: item,
            type: 'line',
            smooth: attribute.smooth,
          })
        });
        this.chartOption.series = seriesArr;
      }
      that.chart.setOption(that.chartOption);
    }
  }
}
</script>

<style scoped>

</style>
