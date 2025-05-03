<template>
  <div id="echartGD" ref="echartGD"></div>
</template>
<script>

// import * as echarts from "echarts";
import GDjson from "./GD.json";
const colorList = ["#58C7FF","#C4D64E",'#80FFA5', '#00DDFF', '#37A2FF',]
export default {
  name: "mapModel",
  title: "地图",
  components: {},
  props: {
    mapObj: {
      type: Object,
      default: {},
    },
    echartData:Array
  },
  data () {
    return {
      delidata: [
        {
          gte: 2022,
          lte: 2022,
          label: "2022",
          color: "#58C7FF"
        },
        {
          gte: 2021,
          lte: 2021,
          label: "2021",
          color: "#C4D64E"
        },
        
      ],
      mapRes: [],
    };
  },
  created () {
    this.init();
  },
  methods: {
    init () {
      //用mapObj当参数，拿到数据
      // get(){
      console.log("进入地图")
      // let {xunchaLunciId} = this.mapObj
      // getXunchaCitiesYear(xunchaLunciId).then(res=>{
      //   this.mapRes = res.data.map(i=>{
      //     return {
      //       name:i.name,
      //       value:i.year,
      //       data:i.value
      //     }
      //   })
      //   this.$nextTick(() => {
      //     this.cartographic();
      //   });
      // })
      // this.mapRes = [
      //   { name: '韶关', value: 22 },
      //   { name: '深圳', value: 6 },
      //   { name: '东莞', value: 7 },
      //   { name: '清远', value: 11 },
      //   { name: '肇庆', value: 12 },
      //   { name: '茂名', value: 1 },
      // ] || [];
      // }
    },
    cartographic () {
      var chartDom = this.$refs.echartGD;
      var myChart = this.$echarts.init(chartDom);
      var option;
      this.$echarts.registerMap("GDLine", GDjson);
      this.$echarts.registerMap("GDLine2", GDjson);
      this.$echarts.registerMap("GDLine3", GDjson);
      this.$echarts.registerMap("GD", GDjson);
      let aspectScale = 1; //地图长宽高
      let zoom = 1.1; //地图缩放级别

      console.log(GDjson)
      let GDMap = GDjson.features.map(item => {
        return { name: item.properties.name, value: 0 }
      })
      //接口返回的城市
      let data = this.echartData.map(i=>{
          return {
            name:i.name,
            value:i.year,
            data:i.value
          }
        }) || [];
      let delidata = this.mapObj.yearList.map((i,index)=>{
        return {
          gte: parseInt(i),
          lte: parseInt(i),
          label: i+"年",
          color: colorList[index]
        }
      })
      console.log('delidata',delidata);
      let datas = data.filter((item) => {
        let its = item
        for (let i = 0, len = delidata.length; i < len; i++) {
          if (item.value ==delidata[i].gte) {
            item.itemStyle = { emphasis: { areaColor: delidata[i].color, fontSize: 12 } }
            its = item
            return its
          }
          // return item
        }
      })
      console.log(datas)
      option = {
        tooltip: {
          trigger: 'item',
          // textStyle: {
          //   width: 160,
          //   height: 120,
          // },
          // padding: 5,
          backgroundColor: "#000",
          extraCssText: "width:10.6rem;height：100%;",
          formatter: (params) => {
            let htm = `<div class="tips" style="width:10rem;height:6rem;background-image:url(${require('@/assets/images/ai/tip.png')});background-size: 100% 100%;background-repeat:no-repeat;">
                        <div class="tipbox" style="font-size:16px;padding:0.5rem; ">
                          <div style="font-size:16px;padding:0.5rem; ">${params.name}</div>
                          <div style="font-size:14px;padding:0.5rem; "><span style="display: inline-block;background: rgb(221, 112, 60);width: 0.5rem; height: 0.5rem; border-radius: 50%; margin-right: 0.5rem;"></span>异常数据：${params.data ? params.data.data : '0'}</div>
                        </div>
                      </div>`;
            return htm;
          }
        },
        //title
        textStyle: {
          fontSize: "16",
        },
        //展示用的
        visualMap: {
          type: "piecewise",
          show: delidata.length!=0,
          bottom: 10,
          right: 10,
          min: 0,
          max: 100,
          showLabel: !0,
          textStyle: { color: "#fff" },
          pieces: delidata,
        },
        // geo函数定义了 地图省份文字的样式，series中定义了每个系列的特效
        series: [
          {
            name: '广东轮廓',
            type: 'map',
            map: 'GDLine',
            silent: true,
            roam: false, //开启鼠标缩放和漫游
            //调整以下3个配置与页面地图重合
            aspectScale: aspectScale,//地图长宽高
            // center:[104.2,35.9],//设置可见中心坐标，用来放大缩小
            zoom: zoom, //地图缩放级别
            itemStyle: {
              normal: {
                // areaColor: "#003175",//地图背景色
                areaColor: {
                  type: 'radial',
                  x: 0.5,
                  y: 0.5,
                  r: 3,
                  colorStops: [{
                    offset: 0, color: '#003175'
                  }, {
                    offset: 1, color: 'rgba(46,197,251,0.7)'
                  }],
                  global: false
                },
                borderWidth: 3,
                textStyle: {
                  color: "#fff"
                },
                borderColor: "#56C0EE" //地图边框颜色
              },
            },
          },
          {
            name: '广东轮廓2',
            type: 'map',
            map: 'GDLine2',
            silent: true,
            roam: false, //开启鼠标缩放和漫游
            //调整以下3个配置与页面地图重合
            aspectScale: aspectScale,//地图长宽高
            center: [113.420637, 22.905178],//设置可见中心坐标，用来放大缩小
            zoom: zoom * 1.01, //地图缩放级别
            itemStyle: {
              normal: {
                option: 0.2,
                shadowColor: "#02275C",
                areaColor: {
                  type: 'radial',
                  x: 0.5,
                  y: 0.5,
                  r: 3,
                  colorStops: [{
                    offset: 0, color: '#00317570'
                  }, {
                    offset: 1, color: 'rgba(46,197,251,0.4)'
                  }],
                  global: false
                },
                borderWidth: 1,
                textStyle: {
                  color: "#fff"
                },
                borderColor: "#56C0EE40" //地图边框颜色
              },
            },
          },
          {
            name: '广东轮廓3',
            type: 'map',
            map: 'GDLine3',
            silent: true,
            roam: false, //开启鼠标缩放和漫游
            //调整以下3个配置与页面地图重合
            aspectScale: aspectScale,//地图长宽高
            center: [113.420637, 22.955178],//设置可见中心坐标，用来放大缩小
            zoom: zoom * 1.01, //地图缩放级别
            itemStyle: {
              normal: {
                option: 0.2,
                shadowColor: "#02275C",
                areaColor: {
                  type: 'radial',
                  x: 0.5,
                  y: 0.5,
                  r: 3,
                  colorStops: [{
                    offset: 0, color: '#00317540'
                  }, {
                    offset: 1, color: 'rgba(46,197,251,0.1)'
                  }],
                  global: false
                },
                borderWidth: 1,
                textStyle: {
                  color: "#fff"
                },
                borderColor: "#56C0EE10" //地图边框颜色
              },
            },
          },
          {
            name: '广东',
            type: 'map',
            map: 'GD',
            roam: false, //开启鼠标缩放和漫游
            //调整以下3个配置与页面地图重合
            aspectScale: aspectScale,//地图长宽高
            zoom: zoom, //地图缩放级别
            label: {
              normal: {
                //静态的时候展示样式
                show: true, //是否显示地图省份得名称
                textStyle: {
                  color: "#fff",
                  fontSize: 12,
                },
              },
              emphasis: {
                //动态展示的样式
                color: "#fff",
              },
            },
            itemStyle: {
              normal: {
                areaColor: {
                  type: "radial",
                  x: 0.5,
                  y: 0.5,
                  r: 3,
                  colorStops: [{
                    offset: 0, color: '#003171'
                  }, {
                    offset: 1, color: 'rgba(46,197,251,0.7)'
                  }],
                  global: false
                },
                borderWidth: 1,
                textStyle: {
                  color: "#fff",
                },
                borderColor: "#56C0EE", //地图边框颜色
              },
              emphasis: {
                color: "#fff",
                borderWidth: 1,
                borderColor: '#56C0EE',
                shadowColor: "#56C0EE",
                shadowBlur: 40,
                // opacity: 1,
                areaColor: {
                  type: 'radial',
                  x: 0.5,
                  y: 0.5,
                  r: 2,
                  colorStops: [{
                    offset: 0, color: '#003175'
                  }, {
                    offset: 1, color: 'rgba(46,197,251,0.7)'
                  }],
                  global: false
                },
              }
            },
            //编辑区间
            data: datas,
          },
        ],
      };
      myChart.setOption(option);
      // this.resetMap();
      let that = this;
      window.addEventListener("resize", 
        // debugger
        that.resetMap
      );
    },
    resetMap () {
      console.log("地图重新绘画")
      let that = this;
      this.$nextTick(function(){
        var chartDom = this.$refs.echartGD;
        var myChart = this.$echarts.init(chartDom);
        // 监听resize事件——屏幕大小发生变化时触发
        console.log(this.$refs.echartGD.offsetHeight)
        // that.$refs.echartGD.style.width = that.$refs.echartGD.offsetHeight + "px"
        myChart.resize();
      })

    }
  },
  beforeDestroy(){
    console.log("beforeDestroy");
    window.removeEventListener("resize",this.resetMap)
  },
  mounted () {
    // this.resetMap();
  },
  watch: {
    mapObj: {
      handler (nval, oval) {
        // console.log(nval, oval)
        this.init();
      },
      deep: true,
    }
  },
};
</script>

<style lang="scss" scoped>
  /deep/ .el-table__empty-block::after{
    background-image: url(~@/assets/images/newNoData.png) !important;
  }
.ai-container {
  width: 100vw;
  height: 100vh;
  position: relative;
}

#echartGD {
  width: 100%;
  height: 100%;
  margin: 0 auto;
}

.backimg {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.app-container {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 3.8em 2rem 1rem;
  z-index: 2;
  display: flex;
  flex-direction: row;

  // justify-content: center;
}
</style>
