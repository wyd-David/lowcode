<template>
  <div>
    <div class="app-main">
      <todo-bpm-header :pageTitle="form.type=='1'?'巡察组考核':'巡察员考核'"></todo-bpm-header>
      <!-- context-row -->
      <div class="">
        <!-- 室经理审批 -->
        <el-form ref="queryformDetail" :model="form" label-width="120px" :disabled="true">
          <div class="crad">
            <div class="crad-content">
              <el-form-item label="标题：" prop="title">
                <el-input v-model="form.title" placeholder="请输入标题" readonly />
              </el-form-item>
            </div>
            <div class="crad-header">
              <div class="crad-title">
                <span class="line"></span>
                <span>被考核小组信息</span>
              </div>
            </div>
            <div class="crad-content">
              <el-table v-loading="loadingChild" :data="form.list">
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="巡察组" align="center" prop="groupName" />
                <el-table-column label="组长" align="center" prop="groupLeaderName" />
                <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" />
                <el-table-column label="巡察年份" align="center" prop="year" />
                <el-table-column label="批次" align="center" prop="batchName" />
              </el-table>
            </div>
            <div class="crad-header">
              <div class="crad-title">
                <span class="line"></span>
                <span>考核信息</span>
              </div>
            </div>
            <div class="crad-content">
              <el-table :data="InfoListChildDetail" border v-loading="loadingChild" style="width: 100%;"
                :span-method="objectSpanMethodDetail" :row-class-name="rowClassName"
                @cell-mouse-enter="handleMouseEnter" @cell-mouse-leave="handleMouseLeave">
                <!-- :span-method="objectSpanMethod" -->
                <el-table-column prop="order" label="序号" width="55" align="center">
                </el-table-column>
                <el-table-column prop="groupName" label="巡察组" align="center">
                  <template slot-scope="scope">
                    {{ scope.row.groupName }}
                  </template>
                </el-table-column>
                <el-table-column prop="examTypeName" label="考核类型" align="center">
                </el-table-column>
                <el-table-column label="* 评分人选择" align="center" width="150">
                  <template slot-scope="scope">
                    {{ showListDetail[scope.row.groupId + '_' + scope.row.examTypeId].examUserNames }}
                  </template>
                </el-table-column>
                <el-table-column label="*评分截止时间" align="center" width="210">
                  <template slot-scope="scope">
                    {{ showListDetail[scope.row.groupId + '_' + scope.row.examTypeId].examLastTime }}
                  </template>
                </el-table-column>
                <el-table-column prop="examRemark" label="备注" align="center" width="200">
                  <template slot-scope="scope">
                    {{ showListDetail[scope.row.groupId + '_' + scope.row.examTypeId].examRemark }}
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <!-- <all-flow-step :allFlowStep="process.allFlowStep"></all-flow-step> -->
          </div>
        </el-form>
        <!-- <apply-footer :todoId="id" type="3" @apply="handleAF"></apply-footer> -->
        <deep-btn ref="dbRef" :isTodo="true" :businessKey="id" :todoId="todoId" @initProcess="initProcess" type="1">
          <span slot="btns">
            <el-button type="primary" @click="transferForm" v-if="process.authorizeFlag == '2'">转 办</el-button>
            <el-button type="primary" @click="returnForm(process.backNextStepAndUser)"
              v-if="process.authorizeFlag == '2'">退回</el-button>
            <el-button type="primary" @click="returnForm(process.nextStepAndUser)" v-if="process.authorizeFlag == '2'">
              同意</el-button>
          </span>
        </deep-btn>
      </div>
    </div>
    <el-dialog :title="title" modal-append-to-body append-to-body :visible.sync="dialogVisibleReturn"
      :before-close="handleClose" width="600px">
      <div class="dialog-context">
        <el-form label-width="140px" :model="subForm" ref="afRules" :rules="rules">
          <el-form-item label="下一环节:" prop="node">
            <el-select style="width: 100%" v-model="subForm.node" placeholder="请选择" @change="changeVal">
              <el-option v-for="item in subForm.nextStep" :key="item.nextStep" :label="item.nextStep"
                :value="item.nextStep">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处理人:" prop="nextUserId" v-if="subForm.nextUserId">
            <el-select style="width: 100%" v-model="subForm.nextUserId" placeholder="请选择">
              <el-option
                v-for="item in (subForm.nextStep && subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0) ? (subForm.nextStep.filter(item => item.nextStep == this.subForm.node)[0].nextStepUser) : []"
                :key="item.nextUserId" :label="item.nextUserName" :value="item.nextUserId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处理意见:">
            <el-input v-model="subForm.option" type="textarea" row="3"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleReturn = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
    <!-- isBusiness @handleData="handleAF" -->
    <transfer-process ref="transferRef" :taskId="process.curtaskId" title="转办"
      :dialogVisible="transferProcessShow" type="1" @close="transferProcessShow = false"></transfer-process>
  </div>
</template>
<script>
import {
  getApplyInfo,
  approveApplyInfo,
} from "@/api/checkedAll/checkedInfo";

import { accidentDeal } from "@/api/talent/collection";
// import { getDicts } from "@/api/system/dict/data";
import ApplyFooter from "@/components/ApplyFooter";
import deepBtn from "@/components/WorkFlow/deepBtn";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import transferProcess from "@/components/WorkFlow/transferProcess";
export default {
  components: { ApplyFooter, deepBtn, AllFlowStep, transferProcess },
  created () {
    this.id = this.$route.query.id ? this.$route.query.id : '';
    // this.groupId = this.$route.query.groupId ? this.$route.query.groupId : '';
    this.todoId = this.$route.query.todoInfoId ? this.$route.query.todoInfoId : '';
    this.getDetail();
  },
  data () {
    return {
      title: "",
      // 表单参数
      form: {
        title: null,
      },
      subForm: {
        message: null,
        nextStep: null,
        option: null,
        nextUserId: null,
      },
      dialogVisibleReturn: false,
      transferProcessShow: false,
      turnbackProcessShow: false,
      taskId: null,
      disabledwindow: false,
      process: { allFlowStep: [], curtaskId: "" },
      loadingChild: false,
      checkedInfoListChild: [],
      activeName: "1",
      id: "",
      groupId: "",
      todoId: "",
      //用于判断是否有权限
      disabled: true,
      applyUser: "",
      btnList: [],
      allFlowStep: [],
      companyList: [],
      nextUsers: [],
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
      formData: {
        title: "",
        collectionType: "",
        joinWorkDate: "",
        joinPartDate: "",
        startInspectTime: "",
        endInspectTime: "",
        datetime: [],
        collectionEndDate: "",
      },
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [], //tab字典
      collectDicts: [], //收集字典
      InfoListChildDetail: [],
      showListDetail: [],
      cellIndex: -1,
      rules: {
        message: [
          {
            required: true,
            message: "请选择审批结果",
            trigger: "change",
          },
        ],
      }
    };
  },
  methods: {
    handleAF (handleData) {
      // dealUser
      let fm = new FormData();
      fm.append("todoId", this.todoId);
      fm.append("dealUser", handleData.nextDealUser);
      accidentDeal(fm).then(res => {
        if (res.code == 200) {
          this.$message.success("转办成功");
          window.location.reload()
        }
      });
    },
    Errors (err) {
      let text = new Error(err);
      return text
    },
    initProcess (res) {
      let tempObj = {};
      if (res.allFlowStep.length && res.backNextStepAndUser.length == 1) {
        for (let i = 1; i < res.allFlowStep.length; i++) {
          let item = res.allFlowStep[i];
          if (item.varialbes && item.varialbes.message == "转办") {
          } else {
            tempObj = item;
            break;
          }
        }
        res.preUserName = tempObj.todoUserLoginId;
        res.preUserId = tempObj.historicTaskInstance.assignee;
      }
      this.process = res;
    },
    objectSpanMethodDetail ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex < 2) {
        let data = this.InfoListChildDetail.filter((item) => item.groupId == row.groupId);
        let idx = 0;
        data.forEach((item, index) => { if (item.examTypeId == row.examTypeId) { idx = index } })
        // console.log(len, rowIndex)
        if (idx % data.length === 0) {
          return {
            rowspan: data.length,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },
    //获取详情
    getDetail () {
      let that = this;
      this.loadingChild = true;
      getApplyInfo({ applyId: this.id }).then(response => {
        that.form = response.data;
        that.isOpenQuery = true;
        that.loadingChild = false;
        let Array = response.data.list;
        //拆分类型
        let InfoListChild = [];
        Array.forEach((item) => {
          item.examTypeList && item.examTypeList.forEach((itm, idx) => {
            // 巡察组 考核类型 评分人选择 评分截止时间 备注
            let child = itm;
            child.groupId = item.groupId ? item.groupId : '';//巡察组id
            child.groupName = item.groupName ? item.groupName : '';//巡察组name
            child.index = idx;
            InfoListChild.push(child)
          })
        })
        this.InfoListChildDetail = InfoListChild;
        this.handleData(this.InfoListChildDetail, 2);
        let data = {};
        for (let i in InfoListChild) {
          data[InfoListChild[i].groupId + "_" + InfoListChild[i].examTypeId] = InfoListChild[i];
        }
        console.log(data);
        this.showListDetail = data;
        // fetchWorkFlowInfo(this.id, null, (res) => {
        //   this.process = res;
        // });
      });
    },
    /** 导入按钮操作 */
    openImportTable () {
      this.upload.title = "入库管理_人才收集管理 数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate () {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    handleClose () {
      this.$emit("close");
    },
    handleTabClick (tab) { },
    add (t) { },
    tableInput (e) {
      let count = 0;
      this.prefecturalTableData.forEach((item) => {
        item.fieldList.forEach((item1) => {
          count += Number(item1.personNumber) || 0;
        });
      });
      e.expertTotals = count;
    },
    // 文件上传中处理
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess (response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getDetail();
    },
    // 提交上传文件
    submitFileForm () {
      this.$refs.upload.submit();
    },
    //打开弹窗
    // openDialog(show, btnRef) {
    //   if (
    //     !this.process.nextStepAndUser &&
    //     this.process.nextStepAndUser.length == 0
    //   ) {
    //     this.$message.error("流程人员异常");
    //     return;
    //   }
    //   this[show] = true;
    //   this.$refs[btnRef].init(this.process);
    // },
    //转办
    transferForm () {
      console.log("转办nextStep")
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    returnForm (data) {
      this.subForm.nextStep = data;
      // this.subForm.message = data.length > 0 ? data[0].outLine : '审核';
      this.subForm.message = data.length > 0 ? data[0].outLine : '';
      this.subForm.node = data.length > 0 ? data[0].nextStep : '';
      // this.subForm.nextUserId = (this.subForm.nextStep && this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0 && this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0) ? (this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node)[0].nextStepUser[0].nextUserId) : ""

      this.dialogVisibleReturn = true;
      this.title = this.subForm.message;
    },
    // handleData () {
    //   console.log("回调")
    // },
    /** 提交接口 */
    submitForm () {
      let that = this;
      if (this.disabledwindow) {
        return
      }
      this.$refs["afRules"].validate(valid => {
        if (valid) {
          this.disabledwindow = true;
          const loading = this.openLoading();
          let form = {
            id: this.id,
            taskId: this.process.curtaskId,
            variables: JSON.stringify({
              option: this.subForm.option,
              message: this.subForm.message,
            })
          }
          approveApplyInfo(form).then(res => {
            that.$message.success(res.msg);
            that.getDetail();
            this.disabledwindow = false;
            this.dialogVisibleReturn = false;
            loading.close();
            window.location.reload()
          })
        }
      })
    },
    // 处理数据, 给每一条数据加order
    handleData (dataList, type) {
      let order = 1;
      let data = dataList;
      for (let i = 0, length = data.length; i < length; i++) {
        if (i === 0) {
          data[i].order = order;
        } else {
          console.log(data[i].groupId, '--', i)
          if (data[i].groupId == data[i - 1].groupId) {
            data[i]['order'] = order
          } else {
            data[i]['order'] = ++order;
          }
        }
      }
      if (type == 1) {
        this.InfoListChild = data;
      } else {
        this.InfoListChildDetail = data;
      }

    },
    // 鼠标进入单元格
    handleMouseEnter (row, column, cell, event) {
      this.cellIndex = row.order;
      console.log("鼠标进入单元格")
    },
    // 给相应的rowIndex添加类名
    rowClassName ({ row, rowIndex }) {
      console.log(this.cellIndex, row.order);
      let r = -1;
      if (this.cellIndex == row.order && this.cellIndex != -1) {
        console.log("正常加入")
        return 'hover-row-art';
      }
    },
    // 鼠标离开
    handleMouseLeave (row, column, cell, event) {
      this.cellIndex = -1;
      console.log("鼠标移除单元格")
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ input::-webkit-outer-spin-button,
/deep/ input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}

/deep/ input[type="number"] {
  -moz-appearance: textfield !important;
}

/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}

/deep/ .el-dialog__footer {
  text-align: center;
}

/deep/ .el-form-item__error {
  width: 280px;
}

.app-main {
  padding: 15px;
  background: #fff;

  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }

  .context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;

    .operation {
      position: absolute;
      right: 0;
      top: 10px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 30px;
      z-index: 9;
    }
  }
}

.app-footer {
  text-align: center;
  background: #fff;
  padding-bottom: 20px;
}
</style>
