<template>
  <div>
    <div class="tabBox">
      <el-tabs v-model="tabIndex" @tab-click="getData">
        <el-tab-pane label="稽核无出差文件的集中度" name="3"></el-tab-pane>
        <el-tab-pane
          label="未出差报销差旅费问题（与考勤比对）"
          name="1"
        ></el-tab-pane>
        <el-tab-pane
          label="未出差报销差旅费问题（与招待比对）"
          name="2"
        ></el-tab-pane>
      </el-tabs>
    </div>

    <div style="position: relative; height: 220px">
      <div id="pie" style="width: 100%; height: 100%"></div>
    </div>
    <div class="footLine"></div>
  </div>
</template>

<script>
import {
  notEvectionFile,
  travelExpenseAttendance,
  travelExpenseServe,
} from "@/api/aiXuncha/aiHome.js";

export default {
  props: {
    xunchaLunciId: {
      type: String,
    },
  },
  watch: {
    xunchaLunciId(newVal) {
      this.getData();
    },
  },
  data() {
    return {
      tabIndex: "1",
      num: "",
      chartData: [],
    };
  },
  mounted() {
    // this.getData({ name: 1 });
  },
  methods: {
    getData() {
      if (this.tabIndex == 1) {
        travelExpenseAttendance(this.xunchaLunciId).then((res) => {
          this.chartData = res.data.filter((i) => i.value > 0);
          this.onCalculateNum();
            this.chartInit();
        });
      } else if (this.tabIndex == 2) {
        travelExpenseServe(this.xunchaLunciId).then((res) => {
          this.chartData = res.data.filter((i) => i.value > 0);
          this.onCalculateNum();
            this.chartInit();
        });
      } else {
        notEvectionFile(this.xunchaLunciId).then((res) => {
          this.chartData = res.data.filter((i) => i.value > 0);
          this.onCalculateNum();
            this.chartInit();
        });
      }
    },
    onCalculateNum() {
      this.num = null;
      this.chartData.forEach((i) => {
        this.num += i.value;
      });
    },
    chartInit() {
      let that = this;
      this.chart = this.$echarts.init(document.getElementById("pie"));
      let option = {
        color: ["#FF8B4E", "#8BBAFF", "#FFD44F", "#FC9A93", "#BA84FB"],
        tooltip: {
          trigger: "item",
          textStyle: {
            fontSize: "100%",
          },
        },
        //使用标题来显示数据总和
        title: {
          show:this.chartData.length!=0,
          x: "center",
          y: "center",
          left: "24%",
          text: "总量:" + this.num, //主标题
          textAlign: "center",
          textStyle: {
            //标题样式
            fontSize: 16,
            color: "#fff",
          },
        },
        legend: {
          orient: "vertical", // 布局方式，默认为水平布局，可选为：'horizontal' ¦ 'vertical'
          // 水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
          //   x: "right",
          //   // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
          //   y: "bottom",
          align: "left",
          top: "25%",
          right: "20%",
          icon: "circle",
          itemHeight: 10,
          itemWidth: 10,
          textStyle: {
            color: "rgba(255,255,255,0.8)",
            fontSize: "14",
          },
        },
        series: [
          {
            type: "pie",
            center: ["25%", "50%"],
            radius: ["40%", "70%"],
            roseType: true,
            avoidLabelOverlap: false,
            clockwise: false,
            label: {
              show: true,
              position: "inside",
              formatter: function (params) {
                return params.value;
              },
            },
            emphasis: {
              label: {
                show: false,
                fontSize: "30",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: this.chartData,
          },
        ],
      };
      this.chart.setOption(option);
      // 监听resize事件——屏幕大小发生变化时触发
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
  /deep/.el-tabs__item {
    padding: 0 10px /* 15/144 */ !important;
    color: #fff;
    box-sizing: border-box;
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
.footLine {
  width: calc(100% - 30px);
  height: 5px;
  border-width: 0px 6px 1px 6px;
  border-style: solid;
  border-color: #4cdcf3;
  border-bottom-color: #396281;
  margin: 0 15px;
  box-sizing: border-box;
}
</style>