<template>
  <div ref="chartHomeRef" class="homePage-left">
    <div class="homePage-item" style="height: 1.625rem; /* 234/144 */">
      <div class="homePage-item-header">AI巡察员</div>
      <div class="homePage-item-context" style="min-height: 1.18rem">
        <div
          class="navlist-item zflink"
          @click="onUrl(2)"
          v-if="isShowWisdomCruising.show"
          v-hasPermi="['home:wisdom:cruising']"
        >
          <div class="navlist-item-left">
            <p class="title">{{ isShowWisdomCruising.name }}</p>
          </div>
          <div class="navlist-item-right">
            <img src="~@/assets/images/navIcon3.png" alt="" />
          </div>
        </div>
        <div
          class="navlist-item ljlink"
          @click="onUrl(3)"
          v-if="isShowAiModel.show"
          v-hasPermi="['home:ai:model']"
        >
          <div class="navlist-item-left">
            <p class="title">{{ isShowAiModel.name }}</p>
          </div>
          <div class="navlist-item-right">
            <img src="~@/assets/images/navIcon.png" alt="" />
          </div>
        </div>
        <div
          class="navlist-item ljlink"
          @click="onUrl(1)"
          v-if="isShowHonestRisk.show"
          v-hasPermi="['home:honest:risk']"
        >
          <div class="navlist-item-left">
            <p class="title">{{ isShowHonestRisk.name }}</p>
          </div>
          <div class="navlist-item-right">
            <img src="~@/assets/images/navIcon.png" alt="" />
          </div>
        </div>
        <div
          class="navlist-item jclink"
          @click="onUrl(1)"
          v-if="isShowHonestBasics.show"
          v-hasPermi="['home:honest:basics']"
        >
          <div class="navlist-item-left">
            <p class="title">{{ isShowHonestBasics.name }}</p>
          </div>
          <div class="navlist-item-right">
            <img src="~@/assets/images/navIcon1.png" alt="" />
          </div>
        </div>
        <!-- <div class="navlist-item addlink">
          <div class="navlist-item-left">
            <p class="title">添加领域</p>
          </div>
          <div class="navlist-item-right">
            <img src="~@/assets/images/xtbz@2x.png" alt="" />
          </div>
        </div> -->
      </div>
    </div>
    <div class="homePage-item" style="height: 1.833333rem; /* 264/144 */">
      <div class="homePage-item-header">巡察案例库</div>
      <div class="homePage-item-context">
        <div id="pie" style="width: 100%; height: 1.25rem; /* 180/144 */"></div>
      </div>
    </div>
    <div class="homePage-item" style="height: 1.7rem; /* 264/144 */">
      <div class="homePage-item-header">
        巡察档案库
        <div class="homePage-item-header-date">
          <el-select
            size="mini"
            v-model="year"
            @change="handleClick"
            style="width: 100px"
          >
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
        style="
          height: 1.25rem;
          display: inline-block;
          width: 100%;
          padding-top: 0.2rem;
        "
      >
        <div>
          <el-row :gutter="24">
            <el-col
              :span="8"
              style="text-align: center; cursor: pointer"
              @click.native="toPage(0)"
            >
              <el-progress
                type="circle"
                :width="80"
                :percentage="processFile"
                :format="format"
              ></el-progress>
              <div style="font-size: 0.083333rem">过程资料库</div>
            </el-col>
            <el-col
              :span="8"
              style="text-align: center; cursor: pointer"
              @click.native="toPage(1)"
            >
              <el-progress
                type="circle"
                :width="80"
                :percentage="reportContent"
                :format="format"
              ></el-progress>
              <div style="font-size: 0.083333rem">报告报表库</div>
            </el-col>
            <el-col
              :span="8"
              style="text-align: center; cursor: pointer"
              @click.native="toPage(2)"
            >
              <el-progress
                type="circle"
                :width="80"
                :percentage="regulation"
                :format="format"
              ></el-progress>
              <div style="font-size: 0.083333rem">巡察制度流程库</div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCaseCountApi,
  getProcessFileCountApi,
  getReportContentCountApi,
  getRegulationCountApi,
} from "@/api/home";

export default {
  data() {
    return {
      year: new Date().getFullYear() + "",
      caseData: [],
      tableData: [],
      activeName: "1",
      yearDate: [],
      chart: null,
      processFile: 0,
      reportContent: 0,
      regulation: 0,
      st: null,
      // 智慧小巡 按钮是否显示
      isShowWisdomCruising: {
        name: "智慧小巡",
        show: false,
      },
      // 作风建设 按钮是否显示
      isShowHonestRisk: {
        name: "作风建设",
        show: false,
      },
      // 党建领域 按钮是否显示
      isShowHonestBasics: {
        name: "党建领域",
        show: false,
      },
      // AI模型 按钮是否显示
      isShowAiModel: {
        name: "AI模型",
        show: false,
      },
    };
  },
  created() {
    this.getYears();
    this.getCaseCount();
    this.handleClick();

    // 判断智慧小巡、作风建设、党建领域三个按钮是否显示
    for (let i = 0; i < this.$store.getters.permission_add_routes.length; i++) {
      // 找到 meta.title  == '首页' 的菜单
      let item = this.$store.getters.permission_add_routes[i];
      if (item.meta && item.meta.title === "首页") {
        // 遍历下面的 threeChildren
        if (item.threeChildren) {
          for (let j = 0; j < item.threeChildren.length; j++) {
            let name =
              item.threeChildren[j].meta && item.threeChildren[j].meta.title;
            let flag = !item.threeChildren[j].hidden;
            if (item.threeChildren[j].path == "/test1") {
              this.isShowWisdomCruising.show = flag;
              this.isShowWisdomCruising.name = name;
            }
            if (item.threeChildren[j].path == "/test2") {
              this.isShowHonestRisk.show = flag;
              this.isShowHonestRisk.name = name;
            }
            if (item.threeChildren[j].path == "/test3") {
              this.isShowHonestBasics.show = flag;
              this.isShowHonestBasics.name = name;
            }
            if (item.threeChildren[j].path == "/test4") {
              this.isShowAiModel.show = flag;
              this.isShowAiModel.name = name;
            }
          }
        }
        break;
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.chartInit();
    });
  },
  methods: {
    toPage(i) {
      this.$router.push({
        path: [
          "/archives/xunchaProcessFile",
          "/archives/xunchaReportFile",
          "/regulation/regulationBase",
        ][i],
      });
    },
    format(e) {
      return e;
    },
    onUrl(t) {
      let href = "";
      if (t == 1) {
        // href = this.$router.resolve({name: "AIModel"}).href;
        this.$alert("即将上线！", "提示", {
          confirmButtonText: "确定",
        });
      } else if (t == 2) {
        // href = "http://yjxt.gmcc.net";
        this.$router.push({
          path: "/robot",
        });
      } else if (t == 3) {
        // href = "http://gdjj.gmcc.net/sys/main.action";
        window.open(this.$router.resolve({ name: "AIModel" }).href, "_blank");
      }
    },
    getCaseCount() {
      getCaseCountApi().then((res) => {
        res.data.forEach((item) => {
          item.value = item.number;
          item.name = item.domainTypeName;
        });
        this.caseData = res.data;
        this.chartInit();
      });
    },
    chartResize() {
      this.chart.resize();
      clearTimeout(this.st);
      this.st = setTimeout(() => {
        this.chart.resize();
      }, 1000);
    },
    chartInit() {
      let that = this;
      //数据汇总算法
      const dataNum = this.caseData.reduce((pre, cur) => {
        return pre * 1 + cur.value * 1;
      }, 0);
      //数据取两位小数
      //数据取两位小数
      let zong = parseFloat(dataNum);
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
          x: "center",
          y: "center",
          left: "38%",
          text: " 总量", //主标题
          subtext: zong, //副标题
          textAlign: "center",
          textStyle: {
            //标题样式
            color: "#333",
          },
        },
        legend: {
          selectedMode:false,
          formatter: function (name) {
            let temp = that.caseData.find(
              (item) => item.domainTypeName === name
            );
            return name + "    " + temp.value;
          },
          orient: "vertical", // 布局方式，默认为水平布局，可选为：'horizontal' ¦ 'vertical'
          // 水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
          x: "right",
          // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
          y: "bottom",
          align: "left",
          top: 0,
          icon: "circle",
          itemHeight: 10,
          itemWidth: 10,
        },
        series: [
          {
            type: "pie",
            center: ["40%", "50%"],
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: "#fff",
              borderWidth: 2,
            },
            label: {
              show: false,
              position: "center",
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
            data: this.caseData,
          },
        ],
      };
      this.chart.setOption(option);

      this.chart.on("click", (params) => {
        //此处的value值为饼状图里 data的name 值
        var value = params.name;
        this.$router.push({
          path: "/archives/xunchaCase",
          query: {
            typeName: value,
          },
        });
      });
    },
    handleClick(e) {
      getProcessFileCountApi({ createdTime: this.year + "-01-01" }).then(
        (res) => {
          this.processFile = res.data;
        }
      );
      getReportContentCountApi({ createdTime: this.year + "-01-01" }).then(
        (res) => {
          this.reportContent = res.data;
        }
      );
      getRegulationCountApi({ createdTime: this.year + "-01-01" }).then(
        (res) => {
          this.regulation = res.data;
        }
      );
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
/deep/ .el-tabs__item {
  padding: 0 0.104167rem !important;
  height: 0.277778rem !important;
  line-height: 0.277778rem !important;
  font-size: 0.097222rem !important;
  font-family: PingFangSC-Medium, PingFang SC;
}

/deep/ .el-tabs__item.is-active {
  color: #1590ff !important;
  font-weight: 500 !important;
}

/deep/ .el-tabs__nav-next {
  line-height: 0.305556rem !important;
}

/deep/ .el-tabs__nav-prev {
  line-height: 0.305556rem !important;
}

/deep/ .el-progress-circle {
  width: 0.555556rem !important;
  height: 0.555556rem !important;
}

/deep/ .el-progress__text {
  font-size: 0.075617rem !important;
}
</style>
