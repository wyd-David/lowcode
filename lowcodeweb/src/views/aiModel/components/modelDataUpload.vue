<template>
  <div class="details">
    <el-row class="title">
      <el-col :span="12">模型数据上传</el-col>
    </el-row>
    <div class="tablebox tab1">
      <el-scrollbar style="height: 100%" class="scrollbar">
        <div class="boxtitle">分析资源选择</div>
        <div class="boxexplain textcolor">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="巡察轮次：" prop="xunchaLunciId">
                  <el-select v-model="queryParams.xunchaLunciId" placeholder="请选择巡察轮次" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handleLunciChange" @input="change">
                    <el-option v-for="dict in lunciOptions" :key="dict.lunciId" :label="dict.lunciName"
                      :value="dict.lunciId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察年份：" prop="year">
                  <el-date-picker :clearable="false" size="small" popper-class="ai_popper"
                    style="width: 205px;border:none !important;background:transparent !important"
                    v-model="queryParams.year" format="yyyy" :popper-append-to-body="false" value-format="yyyy"
                    type="year" placeholder="请选择巡察年份" @change="handleYearChange" :picker-options="pickerOptions"
                    @input="change">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察批次：" prop="xunchaPiciId">
                  <el-select v-model="queryParams.xunchaPiciId" placeholder="请选择巡察批次" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handlePiciChange">
                    <el-option v-for="dict in piciOptions" :key="dict.piciId" :label="dict.piciName"
                      :value="dict.piciId" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="巡察任务：" prop="taskId">
                  <el-select v-model="queryParams.taskId" placeholder="请选择巡察任务" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handleTaskChange">
                    <el-option v-for="dict in taskOptions" :key="dict.taskId" :label="dict.taskName"
                      :value="dict.taskId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="被巡察单位：" prop="companyName">
                  <el-select v-model="queryParams.companyName" placeholder="请选择被巡察单位" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="queryGetModelData">
                    <el-option v-for="dict in companyOptions" :key="dict.orgId" :label="dict.orgName"
                      :value="dict.orgName" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="boxexplain textcolor">
          <el-table stripe header-cell-class-name="tableHeaderClass" :data="tableData">
            <el-table-column label="资料名称" prop="comparisonName"></el-table-column>
            <el-table-column prop="dataNumber" label="数据上传（条）"></el-table-column>
            <el-table-column prop="status" label="状态" width="200">
              <template slot-scope="scope">
                <div v-if="scope.row.dataNumber == '数据未上传！'" class="flex">
                  <svg t="1661133602182" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="7021" width="200" height="200">
                    <path
                      d="M512 64C264.8 64 64 264.8 64 512s200.8 448 448 448 448-200.8 448-448S759.2 64 512 64z m191.2 604L488.8 544H480V256h64v246.4l191.2 110.4-32 55.2z"
                      p-id="7022" fill="#cdcdcd"></path>
                  </svg>
                  <span>待上传</span>
                </div>
                <div v-else class="flex">
                  <i class="icon el-icon-success"></i>
                  <span>上传成功</span>
                </div>
              </template>
            </el-table-column>

            <el-table-column prop="" label="操作" align="center">
              <template slot-scope="scope">
                <el-button @click="onUpoad(scope.row, 0)" size="mini" type="text" v-if="queryParams.companyName">查看详情
                </el-button>
                <el-button @click="onUpoad(scope.row, 1)" size="mini" type="text" v-if="queryParams.companyName">上传文件
                </el-button>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="" label="操作" align="center" fixed="right"></el-table-column> -->
          </el-table>
        </div>
      </el-scrollbar>
    </div>
    <upload-dialog :row="row" :dialogVisible="openUpload" @close="close"/>
  </div>
</template>

<script>
import uploadDialog from "./uploadDialog.vue";
import { queryPiciList, queryLunciList, queryCompanyList } from "@/api/xuncha/common/base";
import { listXunchaTaskInfo2 } from "@/api/xuncha/XunchaTaskInfo";
import { getModelData } from "@/api/aiXuncha/aiBaseInfo";
import { queryTaskUnit } from "@/api/xuncha/archives/xunchaProcessApply";
import { queryInfoByTaskId } from "@/api/xuncha/XunchaTaskInfo";
import { getStartEndYear } from "@/api/xuncha/XunchaLunciManage";

import tableData from "./modelData";

export default {
  components: { uploadDialog },
  data () {
    return {
      openUpload: false,
      row: {},
      lunciOptions: [],
      piciOptions: [],
      companyOptions: [],
      taskOptions: [],
      queryParams: {
        companyName: null,
        taskId: null,
      },
      tableData,
      example: {},
      endYear: "3000",
      startYear: "2000",
      pickerOptions: {
        //控制时间范围
        disabledDate: (time) => {
          return time.getFullYear() > this.endYear || time.getFullYear() < this.startYear
        },
      },
    };
  },
  methods: {
    onUpoad (row, type) {
      this.row = row;
      this.row.queryParams = this.queryParams;
      this.row.type = type;
      this.openUpload = true;
    },
    close () {
      this.openUpload = false;
      this.queryGetModelData();
    },
    headerStyle ({ row, rowIndex }) {
      return "tabheader";
    },
    // 初次加载页面，下拉框数据2
    selectDataInit () {
      let that = this;
      queryLunciList().then((res) => {
        this.lunciOptions = res.data;
      });
      listXunchaTaskInfo2().then((res) => {
        this.taskOptions = res.data;
        this.queryParams.taskId = res.data[0].taskId;
        queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
          //被巡察单位-中文
          this.companyOptions = res.data;
          this.queryParams.companyName = res.data[0].orgName;
          // 回显任务对应的批次，轮次，年份
          queryInfoByTaskId(this.queryParams.taskId).then(res => {
            this.queryParams.xunchaLunciId = res.data.lunciId;
            this.queryParams.xunchaPiciId = res.data.piciId;
            this.queryParams.year = res.data.xunchaYear;
            this.taskOptions = this.taskOptions.filter(item=>item.piciId==res.data.piciId);
            // 加载【巡察批次】下拉框数据
            queryPiciList({ year: this.queryParams.year }).then((res) => {
              this.piciOptions = res.data;
            });
            // 加载巡察年份范围
            getStartEndYear(this.queryParams.xunchaLunciId).then(res => {
              this.startYear = res.data.startYear;
              this.endYear = res.data.endYear;
            })
          })
          this.queryGetModelData();
        });
      });
    },
    // selectDataInit () {
    //   queryLunciList().then((res) => {
    //     this.lunciOptions = res.data;
    //   });
    //   listXunchaTaskInfo2().then((res) => {
    //     this.taskOptions = res.rows;
    //     this.queryParams.taskId = res.rows[0].taskId;
    //     queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
    //       this.companyOptions = res.data;
    //       this.queryParams.companyName = res.data[0].orgName;
    //       // 回显任务对应的批次，轮次，年份
    //       queryInfoByTaskId(this.queryParams.taskId).then(res => {
    //         this.queryParams.xunchaLunciId = res.data.lunciId;
    //         this.queryParams.xunchaPiciId = res.data.piciId;
    //         this.queryParams.year = res.data.xunchaYear;
    //         // 加载【巡察批次】下拉框数据
    //         queryPiciList({ year: this.queryParams.year }).then((res) => {
    //           this.piciOptions = res.data;
    //         });
    //         // 加载巡察年份范围
    //         getStartEndYear(this.queryParams.xunchaLunciId).then(res => {
    //           this.startYear = res.data.startYear;
    //           this.endYear = res.data.endYear;
    //         })
    //       })
    //       this.queryGetModelData();
    //     });
    //   });
    // },
    // 进行强制渲染
    change (val) {
      this.$forceUpdate()
    },
    // 切换巡察轮次
    handleLunciChange (val) {
      this.queryParams.year = null;
      this.queryParams.xunchaPiciId = null;
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      // 加载巡察年份范围
      getStartEndYear(this.queryParams.xunchaLunciId).then(res => {
        this.startYear = res.data.startYear;
        this.endYear = res.data.endYear;
      })
      this.setModelDataZero()
      this.piciOptions = []
      this.taskOptions = []
      this.companyOptions = []
    },
    // 巡察年份change
    handleYearChange (value) {
      let param = {
        year: value,
      };
      this.queryParams.xunchaPiciId = null;
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      queryPiciList(param).then((res) => {
        this.piciOptions = res.data;
      });
      this.setModelDataZero()
      this.piciOptions = []
      this.taskOptions = []
      this.companyOptions = []
    },
    // 巡察批次change
    handlePiciChange (value) {
      let param = {
        piciId: value,
      };
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      listXunchaTaskInfo2(param).then((res) => {
        this.taskOptions = res.data;
      });
      this.setModelDataZero()
      this.taskOptions = []
      this.companyOptions = []
    },
    // 巡察任务Change
    handleTaskChange () {
      this.queryParams.companyName = null;
      queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
        this.companyOptions = res.data;
      });
      this.setModelDataZero()
      this.companyOptions = []
    },
    // 请求资料数据上传数
    queryGetModelData () {
      getModelData(this.queryParams).then((res) => {
        for (let key in res.data)
          this.tableData.forEach((i) => {
            if (i.dataKey == key) {
              i.dataNumber = res.data[key];
              if (res.data[key] == 0) {
                i.dataNumber = "数据未上传！";
              }
            }
          });
      });
    },
    // 将请求资料数据上传数置为初始化
    setModelDataZero () {
      this.tableData.forEach((i) => {
        i.dataNumber = '数据未上传！';
      });
    },
  },
  created () {
    this.selectDataInit();
  },
};
</script>


<style lang="scss" scoped>
/deep/ .el-table__empty-block::after {
  background-image: url(~@/assets/images/newNoData.png) !important;
}

/deep/ .tableHeaderClass {
  color: #11d9ff !important;
  // background-color: #000 !important;
}

/deep/ .scrollbar {
  margin-bottom: 5px;

  .el-scrollbar__wrap {
    overflow-x: hidden;
  }
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow-x: scroll;
}

.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0px !important;
}

/deep/ .el-table .el-table__header-wrapper th {
  background: linear-gradient(180deg, #041141 0%, #021d48 50%, #002259 100%);
  border-bottom: 1px solid #007eff !important;
  // opacity: 0.2;
}

/deep/ .el-table {
  background-color: transparent !important;
}

/deep/ .el-table td.el-table__cell {
  border: 0px;
}

/deep/ .el-table tr {
  background-color: transparent !important;
}

/deep/ .el-table th.el-table__cell.is-leaf {
  border: 0;
}

/deep/ .el-table--striped .el-table__body tr.el-table__row--striped td.el-table__cell {
  background: #111f48;
}

/deep/ .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
  background: #111f48;
}

.tabBox {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 8px;

  /deep/ .el-table .cell {
    color: #fff;
  }

  /deep/ .el-tabs__item {
    padding: 0 10px
      /* 15/144 */
      !important;
    color: #fff;
    box-sizing: border-box;
  }

  /deep/ .is-active {
    transition: 0.3s;
    background: linear-gradient(0deg,
        rgba(0, 148, 255, 0.4) 0%,
        rgba(0, 149, 255, 0) 100%);
  }

  /deep/ .el-tabs__active-bar {
    background: #4cdcf3;
  }
}

.flex {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.details {
  width: 100%;
  height: 100%;
  background: #011241;

  .icon {
    width: 20px;
    height: 20px;
    font-size: 20px;
    color: #00a870;
    margin-right: 4px;
  }

  .title {
    width: 100%;
    height: 3rem;
    background-image: url("~@/assets/images/ai/title.png");
    background-size: 100% 100%;
    font-size: 1.25rem;
    color: #fff;
    display: flex;
    align-items: center;
    padding-left: 3%;
    padding-right: 2%;

    .btnright {
      display: flex;
      justify-content: flex-end;
      color: #fff;

      .hisbtn {
        width: 6.5rem;
        height: 2rem;
        background-image: url("~@/assets/images/ai/historyframe.png");
        background-size: 100% 100%;
        background-repeat: no-repeat;
        font-size: 14px;
        display: flex;
        justify-content: center;
        align-items: center;

        .hisicon {
          width: 14px;
          height: 14px;
          margin-right: 6px;
        }
      }
    }
  }

  .textcolor {
    color: #ffffff65;
  }

  .tablebox {
    &.tab1 {
      height: calc(98% - 2.5rem);
      overflow: hidden;
    }

    // &.tab2 {
    //   height: calc(40% - 2.5rem);
    // }

    width: 100%;
    height: calc(50% - 2.5rem);
    margin: 1rem 0 0;
    padding: 1.5rem 1.5rem 1.5rem 0.5rem;
    box-sizing: border-box;
    background-image: url("~@/assets/images/ai/box.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    overflow: auto;

    .indexIcon {
      width: 1.5rem;
      height: 1.5rem;
      background-image: url("~@/assets/images/ai/indexicon.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
    }

    .boxtitle {
      width: auto;
      height: 2rem;
      background-image: url("~@/assets/images/ai/stitle.png");
      background-size: 100% 100%;
      font-size: 1.25rem;
      color: #fff;
      display: flex;
      align-items: flex-start;
      padding-left: 3%;
      padding-right: 2%;
    }

    .boxexplain {
      font-size: 14px;
      margin: 0.5rem;

      .el-form-item {
        margin-bottom: 12px;
      }
    }

    /deep/ .el-table tr {
      background-color: transparent;
      color: #fff;
    }

    // /deep/ .el-table tr.hover-row {
    //   background: linear-gradient(180deg, #0f4ca6 0%, rgba(18, 74, 156, 0.07) 100%);

    //   .el-table__cell {
    //     background: linear-gradient(180deg, #0f4ca6 0%, rgba(18, 74, 156, 0.07) 100%);
    //   }
    // }

    /deep/ .el-input {
      background: transparent !important;
    }

    /deep/ .el-input__inner {
      background: transparent !important;
      border-color: #ffffff15;
    }
  }
}
</style>
