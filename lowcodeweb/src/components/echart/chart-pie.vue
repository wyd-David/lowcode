<template>
  <div :id="uuid" style="width: 100%;height:100%;"></div>
</template>

<script>
import chartPieOptions from './chart-pie-options'
export default {
  name: "chart-pie",
  title: "饼图",
  icon: 'el-icon-pie-chart',
  group: 'chart',
  props:{
    width:Number,
    height:Number,
    echartData: Array,
    option: {
      type: Object,
      default: () => { return chartPieOptions }
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
      handler(obj, newObj) {
        this.loadChart(newObj);
      },
      deep: true,//深度监听
    },
    echartData:{
      handler(obj, newObj) {
        this.cptData = newObj||[];
        this.loadChart(this.chartOption);
      },
      deep: true,//深度监听
    },
    width(){
      this.chart.resize();
    },
    height(){
      this.chart.resize();
    }
  },
  created() {
    this.cptData = this.echartData || JSON.parse(chartPieOptions.cptDataForm);
    this.uuid = require('uuid').v1();
  },
  mounted() {
    this.refreshCptData();
  },
  methods:{
    refreshCptData(){
      this.loadData()
    },
    loadData(){
      this.loadChart(this.option.attribute);
    },
    loadChart(attribute) {
      if (!this.cptData) return;
      this.chart = this.$echarts.init(document.getElementById(this.uuid));
      const that = this;
      that.chartOption = {
        color: attribute.pieColor,
        title: {
          text: attribute.chartTitle,
          subtext: attribute.subtext,
          left: attribute.titleX,
          top: attribute.titleY,
          textStyle: { fontSize: attribute.titleFontSize, color: attribute.titleTextColor },
          // 副标题文本样式设置
          subtextStyle: { fontSize: 12, color: attribute.subtextColor }
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          selectedMode: false,
          show: attribute.legendShow,
          type: attribute.legendType,
          orient: attribute.orient,
          x: attribute.legendX,
          y: attribute.legendY,
          textStyle:{
            color: attribute.legendTextColor,
            fontSize: attribute.legendFontSize
          }
        },
        series: [
          {
            name: attribute.chartTitle,
            type: 'pie',
            roseType: attribute.roseType==='false' ? false:attribute.roseType,
            radius: [attribute.radiusInside+'%',attribute.radiusOutside+'%'],
            label: {
              position: attribute.labelPosition,
              fontSize: attribute.labelFontSize,
              color: attribute.labelColor
            },
            itemStyle: {
              normal : {
                label : {
                  show : attribute.labelLineShow   //隐藏标示文字
                },
                labelLine : {
                  show : attribute.labelLineShow   //隐藏标示线
                }
              },
              borderRadius: attribute.borderRadius
            },
            data: this.cptData || [],
            center:attribute.pieCenter,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(255, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      that.chart.setOption(that.chartOption);
    }
  }
}
</script>

<style scoped>

</style>
