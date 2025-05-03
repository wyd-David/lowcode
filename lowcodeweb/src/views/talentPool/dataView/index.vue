<template>
  <div class="app-main">
    <div class="data-view">
      <div class="column">
        <div class="echart">
          <div class="echart-title">
            <span class="line"></span><span> 输送人才</span>
            <el-date-picker class="echart-date" size="mini" v-model="value1" type="year" placeholder="选择年">
            </el-date-picker>
          </div>
          <chart-column :echartData="cc" ref="cc1"></chart-column>
        </div>
        <div class="echart">
          <div class="echart-title">
            <span class="line"></span><span>参加巡察工作次数</span>
            <el-date-picker class="echart-date" size="mini" v-model="value1" type="year" placeholder="选择年">
            </el-date-picker>
          </div>
          <chart-column :echartData="cc" ref="cc2"></chart-column>
        </div>
      </div>
      <div class="column">
        <div class="echart">
          <div class="echart-title">
            <span class="line"></span><span>评优次数</span>
            <el-date-picker class="echart-date" size="mini" v-model="value1" type="year" placeholder="选择年">
            </el-date-picker>
          </div>
          <chart-column :echartData="cc" ref="cc3"></chart-column>
        </div>
        <div class="echart">
          <div class="echart-title">
            <span class="line"></span><span>拒绝次数</span>
            <el-date-picker class="echart-date" size="mini" v-model="value1" type="year" placeholder="选择年">
            </el-date-picker>
          </div>
          <chart-column :echartData="cc" ref="cc4"></chart-column>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import ChartColumn from "@/components/echart/chart-column.vue";
export default {
  components: { ChartColumn },
  data() {
    return {
      value1: "",
      cc: {
        xData: ["广州公司", "深圳公司", "潮汕公司", "汕头公司", "茂名公司", "珠海公司","惠州公司"].join(","),
        yData: [[20, 20, 20, 15, 20, 18, 28]],
        yDataName:[]
      },
    };
  },
  created() {
    window.onresize = () => {
      if (this.$refs.cc1) { 
        this.$refs.cc1.chart.resize();
      }
      if (this.$refs.cc2) { 
        this.$refs.cc2.chart.resize();
      }
      if (this.$refs.cc3) { 
        this.$refs.cc3.chart.resize();
      }
      if (this.$refs.cc4) { 
        this.$refs.cc4.chart.resize();
      }
    };
  },
};
</script>
<style lang="scss" scoped>
.app-main {
  padding: 15px;
}
.data-view {
  background: #fff;
  height: calc(100vh - 115px);
  &:nth-child(2) {
    margin-top: 15px;
  }
  .column {
    display: flex;
    justify-content: space-between;
    height: 50%;
    width: 100%;
    .echart {
      width: 50%;
      padding: 15px;
      .echart-title {
        display: flex;
        align-items: center;
        font-size: 16px;
        font-family: PingFangSC-Semibold, PingFang SC;
        font-weight: 600;
        color: rgba(23, 35, 61, 0.85);
        background: #fff;
        position: relative;
        .line {
          display: inline-block;
          width: 4px;
          height: 17px;
          background: #1590ff;
          border-radius: 2px;
          margin-right: 10px;
        }
        .echart-date{
          width: 120px;
          position: absolute;
          right: 0;
        }
      }
    }
  }
}
</style>
