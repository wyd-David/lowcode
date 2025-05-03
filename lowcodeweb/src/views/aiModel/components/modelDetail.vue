<template>
  <div class="details">
    <el-breadcrumb separator-class="arrow" v-if="open">
      <el-breadcrumb-item v-for="item in editableTabs" :key="item.modelId">{{ item.title }}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row class="title">
      <el-col :span="12" style="text-align: left">{{ example.modelName }}
        <span class="showbt" @click="packupDown">{{showtext}}<i class="el-icon-arrow-down" :class="{fan:heigType}"></i></span>
      </el-col>
      <el-col :span="12" class="btnright">
        <div class="hisbtn" @click="openHistory">
          <img src="@/assets/images/ai/historyicon.png" class="hisicon" />
          历史分析
        </div>
      </el-col>
    </el-row>
    <!-- <el-scrollbar style="height:100%"></el-scrollbar> -->
    <div class="tablebox " :class="{tab1:heigType}">
      <el-scrollbar style="height: 100%">
        <div class="boxtitle">模型说明</div>
        <div class="boxexplain textcolor">
          {{ example.modelMemo }}
        </div>
        <div class="boxtitle">分析资源选择</div>
        <div class="boxexplain textcolor">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="巡察轮次：" prop="xunchaLunciId">
                  <el-select style="width:205px;border:none !important;background:none !important"
                    v-model="queryParams.xunchaLunciId" placeholder="请选择巡察轮次" size="small"
                    :popper-append-to-body="false" @change="handleLunciChange">
                    <el-option v-for="dict in lunciOptions" :key="dict.lunciId" :label="dict.lunciName"
                      :value="dict.lunciId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察年份：" prop="year">
                  <el-date-picker :picker-options="pickerOptions" :clearable="false"
                    style="width:205px;border:none !important;background:none !important" size="small"
                    popper-class="ai_popper" v-model="queryParams.year" format="yyyy" :popper-append-to-body="false"
                    value-format="yyyy" type="year" placeholder="请选择巡察年份" @change="handleYearChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察批次：" prop="xunchaPiciId">
                  <el-select style="width:205px;border:none !important;background:none !important"
                    v-model="queryParams.xunchaPiciId" placeholder="请选择巡察批次" size="small" :popper-append-to-body="false"
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
                  <el-select style="width:205px;border:none !important;background:none !important"
                    v-model="queryParams.taskId" placeholder="请选择巡察任务" size="small" :popper-append-to-body="false"
                    @change="handleTaskChange">
                    <el-option v-for="dict in taskOptions" :key="dict.taskId" :label="dict.taskName"
                      :value="dict.taskId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="被巡察单位：" prop="companyName">
                  <el-select style="width:205px;border:none !important;background:none !important"
                    v-model="queryParams.companyName" placeholder="请选择被巡察单位" size="small" :popper-append-to-body="false"
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
          <el-table stripe header-cell-class-name="tableHeaderClass" :data="tableData" :span-method="addSpanMethod"
            :cell-class-name="fenxi">
            <el-table-column label="序号" prop="id" width="55" align="center">
              <template slot-scope="scope">
                <div class="indexIcon">{{ scope.$index + 1 }}</div>
              </template>
            </el-table-column>
            <el-table-column label="比对资料" align="left" prop="comparisonName">
              <template slot-scope="scope">
                <div class="text">
                  <span>{{ scope.row.comparisonName }}</span>
                  <el-popover placement="right-end" title="" trigger="hover" popper-class="popBox1084"
                    :append-to-body="true" offset="0">
                    <div class="div">
                      <div class="el-dialog__title">温馨提示</div>
                      <div class="el-dialog__body">{{scope.row.desc}}</div>
                    </div>
                    <img style="margin-left: 5px; width: 1rem; height: 1rem" slot="reference"
                      src="~@/assets/images/ai/question-circle.png" alt="" />
                  </el-popover>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="dataNumber" label="数据上传（条）" align="center" width="200">
              <template slot-scope="scope">
                <span v-if="scope.row.dataNumber > 0" style="color: #00adff">
                  {{ scope.row.dataNumber | numberFilt }}
                </span>
                <span v-else>数据未上传 <span style="color: #f5222d"> ！</span></span>
              </template>
            </el-table-column>
            <el-table-column prop="" label="操作" align="center">
              <template slot-scope="scope">
                <el-button size="mini" @click="onDataDetail(scope.row, 0)" type="text" class="btn1"
                  v-if="queryParams.companyName">查看详情
                </el-button>
                <el-button size="mini" @click="onDataDetail(scope.row, 1)" type="text" class="btn2"
                  v-if="queryParams.companyName">上传文件
                </el-button>
              </template>
            </el-table-column>
            <el-table-column prop="" label="" align="center" width="180">
              <template slot-scope="scope">
                <el-button class="btnAnalyse" @click="onAnalyse">开始分析</el-button>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="" label="操作" align="center" fixed="right"></el-table-column> -->
          </el-table>
        </div>
        <!-- <div style="text-align: center">
          <el-button class="btnAnalyse" @click="onAnalyse">开始分析</el-button>
        </div> -->
      </el-scrollbar>
    </div>
    <div class="tablebox  white" :class="{tab2:heigType}">
      <el-scrollbar style="height: 100%">
        <div class="boxtitle">分析结果</div>
        <analysis-results :modelData="tableData" :example="example" :queryParam="setqueryParams" :heigType="heigType"/>
      </el-scrollbar>
    </div>

    <el-dialog :title="tipTitle" top="20vh" :visible.sync="tipOpen" :modal-append-to-body="false" width="20%"
      :show-close="true" :closeOnClickModal="true" class="tipBox">
      <span>{{ tipText }}</span>
      <span v-show="showFooter" slot="footer" class="dialog-footer">
        <el-button @click="tipOpen = false" class="btn1">取 消</el-button>
        <el-button type="primary" :icon="btnDisabled ? 'el-icon-loading' : ''" :disabled="btnDisabled" @click="submit"
          class="btn2">确 定</el-button>
      </span>
    </el-dialog>
    <upload-dialog :row="tableRow" :dialogVisible="openUpload" @close="closeUpload" />
    <history-analyse :params="{ queryParams, example }" :dialogVisible="isOpenHistory" @close="isOpenHistory = false"
    />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import historyAnalyse from "./historyAnalyse.vue";

import { queryPiciList, queryLunciList } from "@/api/xuncha/common/base";
import { queryTaskUnit } from "@/api/xuncha/archives/xunchaProcessApply.js";
import { listXunchaTaskInfo2 } from "@/api/xuncha/XunchaTaskInfo";
import {
  getModelData,
  getJSON,
  startOneAnalyse,
  validateAnalyse,
} from "@/api/aiXuncha/aiBaseInfo.js";
import { getStartEndYear } from "@/api/xuncha/XunchaLunciManage";
import { queryInfoByTaskId } from "@/api/xuncha/XunchaTaskInfo.js";

import analysisResults from "./analysisResults.vue";
import modelData from "./modelData";
import uploadDialog from "./uploadDialog.vue";
import { getValueByName } from "@/api/aiXuncha/bingoConfig";

export default {
  components: {
    historyAnalyse,
    analysisResults,
    uploadDialog,
    modelData,
  },

  props: {
    detailData: {
      type: Object,
      default: {},
    },
    tabsData: {
      type: Object,
      default: {},
    },
  },
  data () {
    return {
      heigType: false,
      showtext: "收起",
      strue: true,
      tipOpen: false,
      isOpenHistory: false,
      tabType: false,
      tipTitle: "标题",
      tipText: "内容",
      showFooter: true,
      dialogType: "onExport",
      btnDisabled: false,
      tabindex: 1,
      tableRow: {},
      openUpload: false,
      batchIdOptions: [],
      lunciOptions: [],
      piciOptions: [],
      companyOptions: [],
      taskOptions: [],
      total: 0,
      queryParams: {
        companyName: null,
        xunchaLunciId: null,
        year: null,
        xunchaPiciId: null,
        taskId: null,
        pageSize: 10,
        pageNum: 1,
      },
      setqueryParams: {},
      tableData: [],
      analysisType: true,
      analysisData: [],
      example: {},
      endYear: "3000",
      startYear: "2000",
      pickerOptions: {
        //控制时间范围
        disabledDate: (time) => {
          return time.getFullYear() > this.endYear || time.getFullYear() < this.startYear
        },
      },

      open: false,
      editableTabs: [],
      totalNumber: 2,
    };
  },
  filters: {
    numberFilt (nums) {
      let num = nums.toString().split("."); // 分隔小数点
      let arr = num[0].split("").reverse(); // 转换成字符数组并且倒序排列
      let res = [];
      for (let i = 0, len = arr.length; i < len; i++) {
        if (i % 3 === 0 && i !== 0) {
          res.push(","); // 添加分隔符
        }
        res.push(arr[i]);
      }
      res.reverse(); // 再次倒序成为正确的顺序
      if (num[1]) {
        // 如果有小数的话添加小数部分
        res = res.join("").concat("." + num[1]);
      } else {
        res = res.join("");
      }
      return res;
    },
  },
  computed: {
    ...mapGetters([
      'modelTerrData',

    ]),
  },
  methods: {
    packupDown(){
      this.heigType = !this.heigType;
      this.showtext = this.showtext.indexOf("展开")>-1?"收起":"展开"
    },
    //这里处理层级-详情和历史分析第三层
    getDetail (id, type, arr) {
      let array = [];
      arr.forEach(item => {
        let obj = this.childrenTreeNode(this.modelTerrData, item)[0]
        array.push({ modelId: obj.modelId, title: obj.modelName, type })
      })
      this.editableTabs = array;
      this.open = true;
    },

    //查找子节点
    childrenTreeNode (treeList, id) {
      var temp = [];
      var searchFn = function (treeList, id) {
        for (var i = 0; i < treeList.length; i++) {
          var item = treeList[i];
          if (item.modelId === id) {
            temp.push(item);
            // searchFn(treeList, item.modelId)
            break;
          } else {
            if (item.children) {
              searchFn(item.children, id);
            }
          }
        }
      };
      searchFn(treeList, id);
      return temp;
    },

    onDataDetail (row, type) {
      this.tableRow = row;
      this.tableRow.queryParams = this.queryParams;
      this.tableRow.type = type;
      this.openUpload = true;
    },
    closeUpload () {
      this.openUpload = false;
      this.queryGetModelData();
    },
    openHistory () {
      this.isOpenHistory = true;
    },
    init () {
      
      let example = this.childrenTreeNode(
        this.modelTerrData,
        this.detailData.id
      );
      if (example.length) {
        this.example = example[0];
      }
      // 筛选出模型所需数据
      this.tableData = modelData.filter((item) => {
        return this.example.modelData.some((ele) => ele == item.dataKey);
      });
      this.totalNumber = this.tableData.length;
      if (this.taskOptions.length == 0) {
        this.selectDataInit();
      } else {
        this.queryGetModelData();
      }

    },
    fenxi ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex == '4') {
        return "teshu"
      }
    },
    // 合并单元格
    addSpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex == '4') {
        if (rowIndex === 0) {
          return {
            rowspan: this.totalNumber,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
    },

    initParams () {
      this.getDetail(this.tabsData.id, this.tabsData.type, this.tabsData.arr)
    },
    // 查找子节点
    childrenTreeNode (treeList, id) {
      var temp = [];
      var searchFn = function (treeList, id) {
        for (var i = 0; i < treeList.length; i++) {
          var item = treeList[i];
          if (item.modelId === id) {
            temp.push(item);
            // searchFn(treeList, item.modelId)
            break;
          } else {
            if (item.children) {
              searchFn(item.children, id);
            }
          }
        }
      };
      searchFn(treeList, id);
      return temp;
    },
    headerStyle ({ row, rowIndex }) {
      return "tabheader";
    },
    selectDataInit () {
      console.log("重新加载------------------2")
      queryLunciList().then((res) => {
        this.lunciOptions = res.data;
      });
      listXunchaTaskInfo2().then((res) => {
        this.taskOptions = res.data;
        this.queryParams.taskId = this.queryParams.taskId || res.data[0].taskId;
        queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
          this.companyOptions = res.data;
          this.queryParams.companyName = this.queryParams.companyName || res.data[0].orgName;
          // 回显任务对应的批次，轮次，年份
          queryInfoByTaskId(this.queryParams.taskId).then(res => {
            this.queryParams.xunchaLunciId = this.queryParams.xunchaLunciId || res.data.lunciId;
            this.queryParams.xunchaPiciId = this.queryParams.xunchaPiciId || res.data.piciId;
            this.queryParams.year = this.queryParams.year || res.data.xunchaYear;
            this.setqueryParams = this.queryParams;
            this.taskOptions = this.taskOptions.filter(item => item.piciId == this.queryParams.xunchaPiciId);
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
    setquery(){
      this.setqueryParams = this.queryParams;
    },
    // 切换巡察轮次
    handleLunciChange (val) {
      // 将请求资料数据上传数置为初始化
      this.setModelDataZero()
      this.queryParams.year = null;
      this.queryParams.xunchaPiciId = null;
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      getStartEndYear(val).then(res => {
        this.startYear = res.data.startYear;
        this.endYear = res.data.endYear;
      })
      this.piciOptions = []
      this.taskOptions = []
      this.companyOptions = []
      this.setquery()
    },
    // 切换巡察年份
    handleYearChange (value) {
      // 将请求资料数据上传数置为初始化
      this.setModelDataZero()
      let param = {
        year: value,
      };
      this.queryParams.xunchaPiciId = null;
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      queryPiciList(param).then((res) => {
        this.piciOptions = res.data;
      });
      this.piciOptions = []
      this.taskOptions = []
      this.companyOptions = []
      this.setquery()
    },
    // 切换巡察批次
    handlePiciChange (value) {
      // 将请求资料数据上传数置为初始化
      this.setModelDataZero()
      let param = {
        piciId: value,
      };
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      listXunchaTaskInfo2(param).then((res) => {
        this.taskOptions = res.data;
      });
      this.taskOptions = []
      this.companyOptions = []
      this.setquery()
    },
    // 切换巡察任务
    handleTaskChange () {
      // 将请求资料数据上传数置为初始化
      this.setModelDataZero()
      this.queryParams.companyName = null;
      queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
        this.companyOptions = res.data;
      });
      this.companyOptions = [];
      this.setquery()
    },
    // 切换被巡察单位，请求资料数据上传数
    queryGetModelData () {
      let that = this;
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

      getValueByName('ai_excel_explain_json').then((res) => {
        let meun = JSON.parse(res.data);
        meun.map((item, index) => {
          this.tableData.map((i, index) => {
            if (item.dataKey == i.dataKey) {
              i.desc = item.desc;
            }
          })
        })
      })
    },
    // 将请求资料数据上传数置为初始化
    setModelDataZero () {
      this.tableData.forEach((i) => {
        i.dataNumber = 0;
      });
    },
    //分析结果
    // analysisFunc(url) {
    //   let that = this;
    //   this.analysisType = true;
    //   // this.queryParams
    //   let params={
    //     queryCompanyName:this.queryParams.companyName,
    //     queryTaskId:this.queryParams.taskId
    //   }
    //   getJSON(url,params).then((res) => {
    //     console.log(res.rows);
    //     that.total = res.total;
    //     that.analysisType = false;
    //     that.analysisData = res.rows;
    //   });
    // },
    submit () {
      // 防止多次提交
      if (this.btnDisabled) {
        return;
      }
      this.btnDisabled = true;
      if (this.dialogType == "onExport") {
        // 防止多次提交
        if (this.btnDisabled) {
          return;
        }
        this.onExport();
      } else {
        this.onAnalyse(200);
      }
    },
    onExport () {
      let exports = this.example.api.export ? this.example.api.export : "";
      // this.queryParams
      this.$confirm("是否确认导出当前数据项?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(function () {
          return getJSON(exports);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {
        });
    },
    ontabs (index) {
      this.tabindex = index;
    },
    onCheckAnalyse (params) {
      return validateAnalyse(params);
    },
    onAnalyse (code) {
      let condition = this.tableData.some(
        (i) => i.dataNumber == "数据未上传！"
      );
      if (condition) {
        this.tipOpen = true;
        this.showFooter = false;
        this.tipTitle = "提示";
        this.tipText = "请先上传数据";
        return;
      }
      let { modelId } = this.example;
      let { companyName, taskId } = this.queryParams;
      let params = {
        modelId,
        companyName,
        taskId,
      };

      this.onCheckAnalyse(params).then((res) => {
        // 可以分析
        if (res.msg == 200 || code == 200) {
          startOneAnalyse(params).then((res) => {
            this.btnDisabled = false;
            if (res.code == 200) {
              this.tipOpen = true;
              this.showFooter = false;
              this.tipTitle = "比对中";
              this.tipText = res.msg;
            }
          });
        }
        // 已有分析任务，询问是否继续分析
        else {
          this.showFooter = true;
          this.tipOpen = true;
          this.tipTitle = "提示";
          this.dialogType = "onAnalyse";
          this.tipText = "已有分析任务，是否继续分析";
        }
      });
    },
  },
  created () {
    this.init();
    this.initParams();
  },
  watch: {
    tabsData: {
      handler (nval, oval) {
        this.initParams();
      },
      deep: true,
    },
    detailData: {
      handler (nval, oval) {
        this.init();
      },
      deep: true,
    },

  },
};
</script>

<style lang="scss" scoped>
/deep/ .el-table__empty-block::after {
  background-image: url(~@/assets/images/newNoData.png) !important;
}

/deep/ .el-table .el-table__row .teshu {
  background: #03123C !important;

  &:hover {
    background: #03123C !important;
  }
}

/deep/ .tableHeaderClass {
  color: #11d9ff !important;
  // background-color: #000 !important;
}

.text {
  display: flex;
  justify-content: flex-start;
  align-items: center;
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

.details {
  width: 100%;
  height: 100%;
  background: #011241;

  .title {
    width: 100%;
    height: 2.6rem;
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
        cursor: pointer;

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

  .white {
    color: #fff;
  }

  .btn1 {
    background: transparent;
    color: #fff;
    border: 1px solid #ffffff40;
    padding: 0.3rem 0.5rem;
    font-size: 12px;
  }

  .btnAnalyse {
    font-size: 14px;
    text-align: center;
    background: linear-gradient(131deg, #46a1ff 0%, #007eff 100%);
    color: #fff !important;
    border: 0;
  }

  .btn2 {
    background: #007eff;
    color: #fff !important;
    border: 1px solid #007eff;
    padding: 0.3rem 0.5rem;
    font-size: 12px;
  }

  .tablebox {
    &.tab1 {
      height: 0%;
      margin: 0;
      padding: 0;
    }

    &.tab2 {
      height: calc(90% - 0rem);
    }

    width: 100%;
    height: calc(50% - 3rem);
    margin: 1rem 0 0;
    padding: 0.8rem 1.5rem 0.8rem 0.5rem;
    box-sizing: border-box;
    background-image: url("~@/assets/images/ai/box.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    overflow: auto;
    transition: all 0.5s;

    .indexIcon {
      width: 1.5rem;
      height: 1.5rem;
      background-image: url("~@/assets/images/ai/indexicon.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      text-align: center;
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

      .xiubox {
        // min-width: 400px;
      }
    }

    .btn1 {
      background: transparent;
      color: #fff;
      border: 1px solid #ffffff40;
      padding: 0.3rem 0.5rem;
      font-size: 12px;
    }

    .btn2 {
      background: #007eff;
      color: #fff;
      border: 1px solid #007eff;
      padding: 0.3rem 0.5rem;
      font-size: 12px;
    }

    .btntabs {
      background-image: url("~@/assets/images/ai/btnnormal.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      color: #fff;
      padding: 0.6rem 1rem;
      font-size: 14px;
    }

    .btntabs.btntabindex {
      background-image: url("~@/assets/images/ai/btnselect.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      color: #fff;
      padding: 0.6rem 1rem;
      font-size: 14px;
    }

    .btnexport {
      background-image: url("~@/assets/images/ai/expbtn.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      color: #fff;
      padding: 0.6rem;
      font-size: 14px;
    }

    .tabBox {
      margin: 0.5rem;
      font-size: 1rem;

      .whit65 {
        color: #ffffff65;
      }

      .label {
        font-size: 0.875rem;
        margin-left: 1.2rem;
      }

      .dan {
        font-size: 0.75rem;
        margin-left: 0.2rem;
      }

      .tabright {
        text-align: right;
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
      border-color: #ffffff15 !important;
    }

    /deep/ .el-popover__reference-wrapper {
      display: flex;
    }

    /deep/ .el-scrollbar__wrap {
      overflow-x: hidden;
    }

    /deep/ .el-popper .el-scrollbar__wrap {
      overflow-x: scroll;
    }

    /deep/ .el-progress-bar {
      width: 80%;

      .el-progress-bar__outer {
        border-radius: 0;
        height: 12px !important;
        background-color: #ffffff12;
      }

      .el-progress-bar__inner {
        border-radius: 0;
        height: 12px !important;
        background: linear-gradient(270deg, #5fe0e0 0%, #007eff 100%);
      }
    }

    /deep/ .el-progress__text {
      color: #ffffff85;
    }

    // /deep/ .el-dialog {
    //   z-index: 2001;
    // }
  }

  .tipBox {
    /deep/ .el-dialog {
      width: 22rem !important;
      min-height: 14rem;
      padding: 1.3rem;
      color: #ffffff;
      background: transparent;
      background-image: url("~@/assets/images/ai/tipbg.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;

      .el-dialog__header {
        // padding: 0;
        box-shadow: none;
        text-align: center;

        .el-dialog__title {
          color: #ffffff;
          font-weight: 100;
        }

        .el-dialog__headerbtn {
          top: 2.3rem;
          right: 2rem;
        }

        .el-dialog__headerbtn .el-dialog__close {
          color: #fff;
        }

        // height: 1rem;
      }

      .el-dialog__body {
        color: #ffffff;
        font-weight: 100;
      }

      .el-dialog__footer {
        padding: 5px 20px !important;
        background: transparent;
        position: absolute;
        box-shadow: none;
        bottom: 2rem;
        right: 1rem;
      }
    }
  }
}

/deep/ .tipBox .el-dialog:not(.is-fullscreen) {
  margin-top: 20vh !important;
}

/deep/ .el-loading-mask {
  background-color: #011439;

  .el-loading-spinner .path {
    stroke: #fff;
  }
}

/deep/ .el-breadcrumb__item {
  display: flex;
}

/deep/.el-breadcrumb__item:last-child .el-breadcrumb__separator {
  display: none !important;
}

/deep/ .arrow {
  width: 1rem;
  height: 1rem;
  display: inline-block;
  background-image: url("~@/assets/images/ai/arrow.png");
  background-size: cover;
  background-repeat: no-repeat;
  margin-bottom: 0.5rem;
  color: #fff;
}

/deep/.el-breadcrumb__inner {
  color: #fff !important;
}

/deep/ .el-breadcrumb .el-breadcrumb__item:nth-child(3) .el-breadcrumb__inner {
  color: #11D9FF !important;
}

/deep/ .el-tabs__item {
  color: #fff;

  &.is-active {
    color: #07A96E;
    font-weight: bold;
  }
}
</style>
<style lang="scss">
.popBox1084 {
  background: transparent;
  border: 0;

  .div {
    transform: translateY(2rem);
    margin-left: -2rem;
    width: 22rem !important;
    min-height: 14rem;
    padding: 2.3rem;
    color: #ffffff;
    background: transparent;
    background-image: url("~@/assets/images/ai/tipbg.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;

    .el-dialog__title {
      line-height: 24px;
      font-size: 18px;
      color: #ffffff;
      font-weight: 100;
      text-align: center;
    }

    .el-dialog__body {
      color: #ffffff;
      font-weight: 100;

      padding: 10px !important;
    }

  }
}
.showbt{
  font-size: 20px;
  color: #11D9FF;
  margin-left: 0.5rem;
}
.el-icon-arrow-down{
  font-size: 14px;
  transition: all 0.5s;
}
.fan{
  transform: rotate(-180deg);
}
</style>
