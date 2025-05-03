<template>
  <div class="homePage-center">
    <div class="homePage-item">
      <div class="homePage-item-header">
        全省巡察情况
        <div class="homePage-item-header-date">
          <el-select v-model="year" size="mini" style="width: 100px">
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
      <div
        class="homePage-item-context"
        style="flex-direction: column;"
      >
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="全省巡察情况" name="1"></el-tab-pane>
          <el-tab-pane label="全省整改情况" name="2"></el-tab-pane>
        </el-tabs>
        <chart-map ref="map1" style="min-height: 317px"></chart-map>
      </div>
    </div>

    <div class="homePage-item">
      <div class="homePage-item-header">
        年度巡察进度
        <div class="homePage-item-header-date">
          <el-select v-model="year" size="mini" style="width: 100px">
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
      <div class="homePage-item-context" style="flex-direction: column">
      <el-carousel height="215px" indicator-position="outside">
        <el-carousel-item>
            <div class="schedule-item">
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size:16px;">第一批</div>
              <el-progress
                style="width: 70%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="100"
              ></el-progress>
              <div class="schedule-item-status completed">已完成</div>
            </div>
            <div class="schedule-item">
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size:16px;">第二批</div>
              <el-progress
                style="width: 70%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="70"
              ></el-progress>
              <div class="schedule-item-status">反馈</div>
            </div>
            <div class="schedule-item">
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size:16px;">第三批</div>
              <el-progress
                style="width: 70%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="30"
              ></el-progress>
              <div class="schedule-item-status">巡前准备</div>
            </div>
        </el-carousel-item>
        <el-carousel-item>
            <div class="schedule-item">
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size:16px;">第一批</div>
              <el-progress
                style="width: 70%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="100"
              ></el-progress>
              <div class="schedule-item-status completed">已完成</div>
            </div>
            <div class="schedule-item">
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size:16px;">第二批</div>
              <el-progress
                style="width: 70%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="70"
              ></el-progress>
              <div class="schedule-item-status">反馈</div>
            </div>
            <div class="schedule-item">
              <img src="~@/assets/images/icon@2x.png" alt="" />
              <div style="font-size:16px;">第三批</div>
              <el-progress
                style="width: 70%"
                :text-inside="true"
                :stroke-width="20"
                :percentage="30"
              ></el-progress>
              <div class="schedule-item-status">巡前准备</div>
            </div>
        </el-carousel-item>
      </el-carousel>
      </div>
    </div>
  </div>
</template>

<script>
import ChartMap from "@/components/echart/chart-map.vue";
export default {
  components: { ChartMap },
  data() {
    return {
      year: "",
      activeName: "1",
      yearDate: [],
    };
  },
  created() {
    this.getYears();
  },
  methods: {
    handleClick(e) {
      console.log(e);
    },
    chartResize() {
      if (this.$refs.map1) { 
        this.$refs.map1.chart.resize();
      }
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

<style lang="scss" scoped>
.schedule-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 30px;
  img {
    width: 20px;
    height: 20px;
  }
  &-status {
    width: 80px;
    font-size: 16px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.85);
  }
  .completed {
    color: #fb0051;
    font-weight: 600;
  }
  /deep/.el-progress-bar__inner {
    background: linear-gradient(
      106deg,
      #f8a16f 0%,
      #fc5489 84%,
      #f72166 100%
    ) !important;
  }
  /deep/.el-progress-bar__outer {
    background: rgba(218, 32, 10, 0.08) !important;
  }
}
/deep/.el-progress-bar__outer {
  border-radius: 1px !important;
}
/deep/.el-progress-bar__inner {
  border-radius: 1px !important;
}
/deep/.el-carousel__arrow{
    display: none !important;
}
</style>
