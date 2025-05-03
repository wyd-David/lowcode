<template>
  <div class="ai_dialog">
    <el-dialog :visible.sync="dialogVisible" top="8vh" width="1200px" :close-on-click-modal="false"
      :before-close="handleClose" custom-class="ai_history" :modal-append-to-body="false" append-to-body
      @open="handleOpen">
      <div class="title" slot="title">
        <span>历史分析</span>
      </div>
      <!-- <img class="background" src="~@/assets/images/ai/BG.png" alt="" /> -->
      <div style="height: 60vh">
        <el-scrollbar style="height: 100%">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="巡察轮次：" prop="xunchaLunciId">
                  <el-select v-model="queryParams.xunchaLunciId" placeholder="请选择巡察轮次" size="small"
                    :popper-append-to-body="false" @change="handleLunciChange">
                    <el-option v-for="dict in lunciOptions" :key="dict.lunciId" :label="dict.lunciName"
                      :value="dict.lunciId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察年份：" prop="year">
                  <el-date-picker :clearable="false" popper-class="ai_popper" size="small" style="width: 215px"
                    v-model="queryParams.year" format="yyyy" :popper-append-to-body="false" value-format="yyyy"
                    type="year" placeholder="请选择巡察年份" :picker-options="pickerOptions" @change="handleYearChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察批次：" prop="xunchaPiciId">
                  <el-select v-model="queryParams.xunchaPiciId" placeholder="请选择巡察批次" size="small"
                    :popper-append-to-body="false" @change="handlePiciChange">
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
                    :popper-append-to-body="false" @change="handleTaskChange">
                    <el-option v-for="dict in taskOptions" :key="dict.taskId" :label="dict.taskName"
                      :value="dict.taskId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="被巡察单位：" prop="companyName">
                  <el-select v-model="queryParams.companyName" placeholder="请选择被巡察单位" size="small"
                    :popper-append-to-body="false" @change="getList">
                    <el-option v-for="dict in companyOptions" :key="dict.orgName" :label="dict.orgName"
                      :value="dict.orgName" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="分析模型：" prop="model">
                  <el-select v-model="queryParams.modelId" placeholder="请选择" size="small" :popper-append-to-body="false"
                    @change="handleChangeModel" clearable>
                    <el-option v-for="dict in treeNodeList" :key="dict.modelId" :label="dict.modelName"
                      :value="dict.modelId" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-table stripe header-cell-class-name="tableHeaderClass" :data="tableData">
            <el-table-column label="序号" type="index" width="55" align="center">
            </el-table-column>
            <el-table-column label="分析模型" align="center" prop="modelId">
              <template slot-scope="scope">
                {{ scope.row.modelId | modelFilter(treeNodeList) }}
              </template>
            </el-table-column>
            <el-table-column label="分析人" prop="createdByName" align="center">
            </el-table-column>
            <el-table-column prop="createdTime" label="创建时间" align="center">
              <template slot-scope="scope">
                {{parseTime(scope.row.createdTime, "{y}-{m}-{d} {h}:{i}:{s}")}}
              </template>
            </el-table-column>
            <el-table-column label="分析状态" align="center" prop="analyseStatus">
              <template slot-scope="scope">
                {{ scope.row.analyseStatus | analyseStatusFilter }}
              </template>
            </el-table-column>
            <el-table-column prop="" label="操作" align="center">
              <template slot-scope="scope">
                <el-button size="mini" @click="onOpenDetail(scope.row)" type="text">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </div>
      <div class="pageBox">
        <pagination :total="Number(queryParams.total)" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />
      </div>
      <!-- <div slot="footer">
        <el-button class="nomal" @click="handleClose">关 闭</el-button>
      </div> -->
    </el-dialog>
    <el-dialog :visible.sync="detailDialogVisible" width="1200px" :close-on-click-modal="false"
      :before-close="() => (detailDialogVisible = false)" custom-class="ai_history" :modal-append-to-body="false"
      append-to-body>
      <div class="title" slot="title">
        <span>历史分析</span>
      </div>
      <div style="height: 60vh">
        <el-scrollbar style="height: 100%">
          <analysis-results :example="example" :timeId="timeId" :queryParam="queryParams"/>
        </el-scrollbar>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {
  queryPiciList,
  queryLunciList,
  queryCompanyList,
} from "@/api/xuncha/common/base";
import { listXunchaTaskInfo } from "@/api/xuncha/XunchaTaskInfo";
import { getHistoryAnalyse, getJSON } from "@/api/aiXuncha/aiBaseInfo";
import analysisResults from "./analysisResults.vue";
import { queryTaskUnit } from "@/api/xuncha/archives/xunchaProcessApply";
import { queryInfoByTaskId } from "@/api/xuncha/XunchaTaskInfo";
import { getStartEndYear } from "@/api/xuncha/XunchaLunciManage";
import modelTerrData from "./modelTerrData.json";

export default {
  props: {
    params: {
      type: Object,
    },
    dialogVisible: {
      type: Boolean,
      default: false,
    },
  },
  filters: {
    modelFilter (id, list) {
      return list.find((item) => item.modelId == id).modelName;
    },
    analyseStatusFilter (index) {
      let arr = ["等待中", "分析中", "已完成"];
      return arr[index];
    },
  },
  components: { analysisResults },
  data () {
    return {
      modelTerrData: modelTerrData,
      tabindex: 1,
      queryParams: { modelId: null, pageNum: 1, pageSize: 10, total: 0 },
      detailDialogVisible: false,
      timeId: "",
      lunciOptions: [],
      piciOptions: [],
      companyOptions: [],
      taskOptions: [],
      treeNodeList: [],
      tableData: [],
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
    };
  },
  created () {
    this.selectDataInit();
    this.getTreeNodeList(this.modelTerrData);
  },
  computed: {
    ...mapGetters([
      'modelTerrData',

    ]),
  },
  methods: {


    handleOpen () {
      let obj = {
        modelId: this.params.example.modelId,
        ...this.params.queryParams,
      };
      this.queryParams = obj;

      this.example = this.params.example;
      this.getList();
    },
    handleChangeModel (id) {
      this.example = this.treeNodeList.find((i) => i.modelId == id);
      this.getList();
    },
    onOpenDetail (row) {
      this.timeId = row.timeId;
      this.detailDialogVisible = true;
    },
    getList () {
      getHistoryAnalyse(this.queryParams).then((res) => {
        this.tableData = res.rows;
        this.queryParams.total = res.total;
      });
    },
    getTreeNodeList (items, childrenName = "children") {
      for (let i = 0; i < items.length; i++) {
        const item = items[i];
        // 当前对象存在children
        if (item && item[childrenName]) {
          if (item[childrenName].length === 0) {
            this.treeNodeList.push(item);
          } else {
            // 递归当前children数组
            this.getTreeNodeList(item[childrenName], childrenName);
          }
        }
      }
      return items;
    },
    // 初次加载页面，下拉框数据1
    selectDataInit () {
      queryLunciList().then((res) => {
        this.lunciOptions = res.data;
      });
      listXunchaTaskInfo().then((res) => {
        this.taskOptions = res.rows;
        this.queryParams.taskId = res.rows[0].taskId;
        queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
          this.companyOptions = res.data;
          this.queryParams.companyName = res.data[0].orgName;
          // 回显任务对应的批次，轮次，年份
          queryInfoByTaskId(this.queryParams.taskId).then(res => {
            this.queryParams.xunchaLunciId = res.data.lunciId;
            this.queryParams.xunchaPiciId = res.data.piciId;
            this.queryParams.year = res.data.xunchaYear;
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
        });
      });
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
      this.tableData = []
      this.queryParams.total = 0
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
      this.tableData = []
      this.queryParams.total = 0
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
      listXunchaTaskInfo(param).then((res) => {
        this.taskOptions = res.rows;
      });
      this.tableData = []
      this.queryParams.total = 0
      this.taskOptions = []
      this.companyOptions = []
    },
    // 巡察任务Change
    handleTaskChange () {
      this.queryParams.companyName = null;
      queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
        this.companyOptions = res.data;
      });
      this.tableData = []
      this.queryParams.total = 0
    },
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    submitFileForm () {
      this.$refs.upload.submit();
    },
    openLoad () {
      this.upload.open = true;
      this.upload.params = {
        companyName: this.queryParams.companyName,
        dataType: "2",
        taskId: this.queryParams.taskId,
      };
    },

    handleClick () {
    },
    getModelData () {
    },
    submiit () {
    },
    handleClose () {
      
      this.selectDataInit();
      this.getTreeNodeList(this.modelTerrData);
      this.$emit("close");
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .el-table__empty-block::after {
  background-image: url(~@/assets/images/newNoData.png) !important;
}

/deep/ .el-dialog__header {
  .el-dialog__headerbtn {
    z-index: 100;

    &:hover .el-dialog__close {
      color: #46a1ff;
    }
  }
}

/deep/ .el-scrollbar__wrap {
  overflow-x: hidden;
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow: scroll;
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow: scroll;
}

/deep/ .ai_history .el-dialog__body {
  overflow: hidden;
  // min-height: 536px;
}

/deep/ .el-upload:focus .el-upload-dragger {
  border-color: #46a1ff;
}

/deep/ .el-upload-dragger {
  background: transparent !important;

  &:hover {
    border-color: #46a1ff;
  }

  .el-upload__text {
    color: #fff;
  }

  .el-upload__text em {
    color: #46a1ff;
  }
}

/deep/ .el-upload-list__item:hover {
  background: transparent !important;
  color: #46a1ff;

  .el-icon-document {
    color: #46a1ff;
  }
}

/deep/ .el-upload-list {
  min-height: 28px;
}

/deep/ .el-input {
  background: transparent !important;
}

/deep/ .el-input__inner {
  background: transparent !important;
  border-color: #ffffff15 !important;
  color: #fff;
}

.flex {
  display: flex;
  align-items: center;
}

.download_btn {
  font-size: 12px;
  padding: 8px 14px;
  margin-left: 10px;
}

.btntabs {
  background-image: url("~@/assets/images/ai/btnnormal.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  color: #fff;
  padding: 0.6rem 1rem;
  font-size: 14px;
}

.msg_box {
  padding: 10px;
  // background: linear-gradient(270deg, #348cff 0%, #38a2ff 100%);
  box-shadow: inset 0px 0px 13px 0px rgba(64, 211, 255, 1);
  border-radius: 5px;
  border: 1px solid rgba(64, 213, 255, 0.65);
  position: relative;
  color: #fff;
  margin: 16px 0;

  .icon {
    width: 25px;
  }

  .cancel {
    position: absolute;
    right: 20px;
    cursor: pointer;
  }
}

.background {
  width: 105%;
  position: absolute;
  top: -32px;
  left: -32px;
  z-index: -1;
}

.btn_primary {
  background: linear-gradient(131deg, #46a1ff 0%, #007eff 100%);
  color: #fff;
  border: 0;
}

.nomal {
  background: transparent;
  color: #fff;

  &:hover {
    border-color: #7ebcfb;
  }
}

.title {
  color: #fff;
  position: relative;
  z-index: 10;
  font-size: 20px;
  font-weight: 100;
}

/deep/ .el-dialog {
  background: transparent;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-image: url("~@/assets/images/ai/BG.png");
  padding: 2.2rem;
}

/deep/ .el-dialog__header {
  position: relative;
}

/deep/ .el-dialog__footer {
  background: transparent;
}

.ai_dialog .dialog-title {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 100;
  color: #fff;
  background: transparent;
  position: relative;
  margin-bottom: 16px;

  .line {
    background: #fff;
    height: 22px;
  }
}

/deep/.el-table tbody tr:hover>td { background-color: #3F9DFF70 !important; }
</style>
