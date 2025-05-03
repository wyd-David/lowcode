<template>
  <div style="width: 100%; height: 100%" :id="uuid"></div>
</template>

<script>
import chartMapOptions from "./chart-map-options";
var mapData = require("./guangdong.json");
var chart;
export default {
  name: "cpt-chart-mapGc",
  title: "渐变地图",
  icon: "el-icon-map-location",
  group: "map",
  props: {
    width: Number,
    height: Number,
    echartData: [Object, Array],
    option: {
      type: Object,
      default: () => {
        return chartMapOptions;
      },
    },
    statusName: [Array, String],
  },
  data() {
    return {
      lastData: [],
      uuid: "",
      chartOption: {},
      chart: undefined,
      cptData: [],
    };
  },
  watch: {
    "option.attribute": {
      handler(obj, newObj) {
        this.loadChart(newObj);
      },
      deep: true, //深度监听
    },
    echartData: {
      handler(obj, newObj) {
        this.cptData = newObj;
        this.loadChart(this.chartOption);
      },
      deep: true, //深度监听
    },
    width() {
      chart.resize();
    },
    height() {
      chart.resize();
    },
  },
  created() {
    this.cptData = this.echartData || JSON.parse(chartMapOptions.cptDataForm);
    this.uuid = require("uuid").v1();
  },
  mounted() {
    this.$echarts.registerMap("guandong", mapData);
    chart = this.$echarts.init(document.getElementById(this.uuid));
    this.refreshCptData();
  },
  methods: {
    resize() {
      chart.resize();
    },
    convertData(data) {
      var geoCoordMap = mapData.features;
      var res = [];
      data.forEach((item) => {
        // 获取当前省份的经纬度坐标
        let geoCoord = geoCoordMap.find(
          (res) => res.properties.name == item.name
        ).properties.center;
        if (geoCoord) {
          res.push({
            // name 表示地区名称
            name: item.name,
            // value数据格式为：[113.665412, 34.757975, '200']
            value: geoCoord.concat(item.value),
          });
        }
      });
      return res;
    },
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
      that.chartOption = {
        title: {
          text: attribute.titleText,
          subtext: attribute.subtext,
          left: attribute.titleLeft,
          top: attribute.titleTop,
          textStyle: {
            color: attribute.titleColor,
            fontSize: attribute.titleFontSize,
          },
          subtextStyle: {
            color: attribute.subTitleColor,
            fontSize: attribute.subTitleFontSize,
          },
        },
        tooltip: {
          backgroundColor: "#fff",
          borderColor: "#fd8473",
          borderWidth: 2,
          padding: 10,
          formatter: function (e) {
            //e, t, n
            if (that.statusName) {
              return (
                `<span style='font-size: .1rem;
font-family: PingFangSC-Regular, PingFang SC;
font-weight: 600;
color: #333333;'>${e.name || ""}</span><br/>
                <span style='font-size: .097222rem;
font-family: PingFangSC-Medium, PingFang SC;
font-weight: 400;
color: rgba(23,35,61,0.75);'>${that.statusName[Number(e.value || 0)]}</span><br/>
<span style='font-size: .097222rem;
font-family: PingFangSC-Medium, PingFang SC;
font-weight: 400;
color: rgba(23,35,61,0.75);'>${e.data?e.data.taskTime||'': ''}</span>
`
              );
            }
            return (
              e.seriesName + "<br />" + (e.name || "") + "：" + (e.value || 0)
            );
          },
        },
        visualMap: {
          hoverLink: false,
          min: 0,
          max: 100,
          right: 20,
          bottom: 20,
          showLabel: !0,
          // borderColor: "#fd8473",
          // borderWidth:2,
          textStyle: {
            color: "#8c8c8c",
          },
          pieces: attribute.piecesOptions || [],
          show: !0,
        },
        piecesOptions: attribute.piecesOptions || [],
        geo: {
          map: "guandong",
          roam: attribute.roam, //允许缩放
          //scaleLimit: { min: 1, max: 2 },//允许缩放级别
          zoom: 1.23,
          label: {
            show: true,
            fontSize: attribute.geoLabelSize,
            color: attribute.geoLabelColor,
          },
          itemStyle: {
            borderWidth: 2,
            borderColor: "#fd8473", //边界线颜色
          },
          emphasis: {
            disabled: true,
          },
        },
        series: [
          {
            name: attribute.seriesName,
            type: "map",
            geoIndex: 0,
            data: this.cptData,
          },
          // {
          //   name: "散点", // 自定义名称
          //   type: "effectScatter", // scatter  effectScatter
          //   coordinateSystem: "geo", // 设置坐标系类型
          //   geoIndex:0,
          //   data: this.convertData(
          //     mapData.features.map((res) => {
          //       return {
          //         name: res.properties.name,
          //       };
          //     })
          //   ), // 设置散点位置和数据
          //   symbolSize: function (val) {
          //     // 设置散点大小
          //     return 5;
          //   },
          //   showEffectOn: "render",
          //   rippleEffect: {
          //     brushType: "stroke",
          //     number: 0,
          //   },
          //   itemStyle: {
          //     color: "#E6222E",
          //   },
          //   label: {
          //     // 静态显示时的样式
          //     normal: {
          //       show: false, // 显示地区名称
          //     },
          //   },
          //   zlevel: 3,
          // },
        ],
      };
      chart.setOption(that.chartOption);
      chart.on("mouseover", function (params) {
        that.chartOption.geo.regions = [
          {
            name: params.name,
            itemStyle: {
              shadowColor: "#fd8473",
              shadowBlur: 10,
            },
          },
        ];
        chart.setOption(that.chartOption);
      });

      chart.on("datarangeselected", (e) => {
        const selected = { 0: true, 1: true, 2: true }; //* 因为这边只分了五段，故写死
        if (JSON.stringify(selected) !== JSON.stringify(e.selected)) {
          //* 判断是否为全部选中，全部选中则不用设置。如果不判断则会死循环
          chart.dispatchAction({
            //* 调用action方法将图例重新设置为全部选中
            type: "selectDataRange",
            // 连续型 visualMap 和 离散型 visualMap 不一样
            // 连续型的是一个表示数值范围的数组。
            // 离散型的是一个对象，键值是类目或者分段的索引。值是`true`或`false`
            selected: { 0: true, 1: true, 2: true },
          });
        }
      });
    },
  },
};
</script>

<style scoped></style>