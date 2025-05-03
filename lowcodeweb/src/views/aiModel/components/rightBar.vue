<template>
  <div>
    <div class="tabBox">
      <el-tabs v-model="tabIndex" @tab-click="getData">
        <el-tab-pane label="出差半天报销全天伙食补贴" name="1"></el-tab-pane>
        <el-tab-pane label="出差期间申请用车" name="3"></el-tab-pane>
        <el-tab-pane
          label="既派车又报销差旅补贴/出差期间申请用车"
          name="2"
        ></el-tab-pane>
      </el-tabs>
    </div>
    <div class="headerBox">

    </div>
    <div id="bar" class="bar_box" style="width: 100%; height: 300px"></div>
  </div>
</template>

<script>
import {
  foodUnreasonable,
  tripUnreasonable,
  evectionUseCar,
} from "@/api/aiXuncha/aiHome.js";
export default {
  props: {
    xunchaLunciId: {
      type: String,
      default: "",
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
      tableData: [],
    };
  },
  mounted() {
    // this.getData({ name: 1 });
  },
  methods: {},
  methods: {
    getData() {
      if (this.tabIndex == 1) {
        foodUnreasonable(this.xunchaLunciId).then((res) => {
          this.tableData = res.data.filter((i) => i.value > 0);
          //  = data.map(i=>{
          //   return {

          //   }
          // })
          this.chartInit();
        });
      } else if (this.tabIndex == 3) {
        evectionUseCar(this.xunchaLunciId).then((res) => {
          this.tableData = res.data.filter((i) => i.value > 0);
          this.chartInit();
        });
      } else {
        tripUnreasonable(this.xunchaLunciId).then((res) => {
          this.tableData = res.data.filter((i) => i.value > 0);
          this.chartInit();
        });
      }
    },
    chartInit() {
      let that = this;
      this.chart = this.$echarts.init(document.getElementById("bar"));
      let option = {
        color: ["#FF8B4E"],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
          textStyle: {
            fontSize: "100%",
          },
        },

        grid: {
          left: "15%",
        },
        xAxis: {
          type: "value",

          axisLabel: {
            color: "#fff",
          },
        },
        yAxis: {
          type: "category",

          axisLabel: {
            color: "#fff",
          },
        },
        dataset: {
          dimensions: ["name", "异常数据"],
          source: this.tableData.map(i=>{
            return{name:i.name,"异常数据":i.value}
          }),
        },
        series: [
          {
            type: "bar",
            barCategoryGap: "50%",
            itemStyle: {
              normal: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 1, 1, [
                  { offset: 0, color: "#0092FF" }, //渐变头部色
                  { offset: 1, color: "#38D8FF" },
                ]),
                barBorderRadius: 14,
              },
            },
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
.bar_box {
  padding: 0 20px;
  box-sizing: border-box;
}
.headerBox {
  padding-left: 20px;
  color: #fff;
  display: flex;
  align-items: center;
  .title {
    display: flex;
    align-items: center;
    padding-left: 10px;
  }

  .title > span {
    font-size: 30px;
    font-weight: 600;
    padding: 0 8px;
  }
  .percentage {
    margin-left: 10px;
    font-size: 20px;
    color: #ff7fa1;
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