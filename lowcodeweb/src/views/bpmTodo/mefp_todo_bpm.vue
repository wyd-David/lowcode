<template>
  <div>
    <todo-bpm-header v-if="!tempId"></todo-bpm-header>
    <div class="app-main" v-show="applyShow">
      <!-- 是退回经办人状态，允许修改 -->
      <apply-form
        ref="refForm"
        :id="id || tempId"
        isTodo
        type="1"
        :formData="formData"
        :disabled="
          process.curStepName != '退回经办人' || process.authorizeFlag != 2
        "
      ></apply-form>
      <!-- tempId 是分公司推荐页面用到了这个待办组件 -->
      <deep-btn
        :businessKey="id || tempId"
        type="3"
        :ischeck="tempId ? 1 : 0"
        @initProcess="initProcess"
        :isTodo="!tempId"
        isBusiness
        @handleData="handleAF"
        @close="handleClose"
        @saveFromData="handleSaveFromData"
        @submitData="submitData"
      >
        <span slot="btns" style="margin-right: 10px">
          <el-button
            v-if="
              process.curStepName != '退回经办人' && process.authorizeFlag == 2
            "
            type="primary"
            @click="turnbackOper"
            >退 回</el-button
          >
          <el-button
            v-if="
              process.curStepName != '退回经办人' && process.authorizeFlag == 2
            "
            type="primary"
            @click="transferOper"
            >转 办</el-button
          >
        </span>
      </deep-btn>

      <transfer-process
        title="转办"
        ref="transferRef"
        :taskId="process.curtaskId"
        :dialogVisible="transferProcessShow"
        @close="handleClose"
      ></transfer-process>

      <turnback-process
        ref="turnbackRef"
        isBusiness
        title="退回"
        :taskId="process.curtaskId"
        @handleData="returnSubmit"
        :dialogVisible="turnbackProcessShow"
        @close="handleClose"
      ></turnback-process>
    </div>

    <!-- 商品功能（测试）导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />
          是否更新已经存在的用户数据
          <el-link type="info" style="font-size: 12px" @click="importTemplate"
            >下载模板</el-link
          >
        </div>
        <div class="el-upload__tip" style="color: red" slot="tip">
          提示：仅允许导入“xls”或“xlsx”格式文件！
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import deepBtn from "@/components/WorkFlow/deepBtn";
import {
  getCollection,
  getNextDealUser,
  getAllCompany,
  fininshFormTask,
} from "@/api/talent/collection";
import turnbackProcess from "@/views/talent/collection/turnbackProcess";
import { getDicts } from "@/api/system/dict/data";
import transferProcess from "@/components/WorkFlow/transferProcess";
import applyForm from "@/views/talent/collection/applyForm";
export default {
  props: {
    tempId: {
      type: String,
      default: "",
    },
  },
  components: { deepBtn, turnbackProcess, transferProcess, applyForm },
  created() {
    this.id = this.$route.query.id;
    this.todoId = this.$route.query.todoInfoId;
    this.getDicts("collection_process_status").then((response) => {
      this.processStatusOptions = response.data || [];
    });
    // this.handleOpen();
  },
  data() {
    return {
      applyShow: false,
      transferProcessShow: false,
      turnbackProcessShow: false,
      process: { allFlowStep: [] },
      activeName: "1",
      id: "",
      todoId: "",
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
      upload: {
        title: "",
      },
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [], //tab字典
      collectDicts: [], //收集字典
      processStatusOptions: [], //流程状态字典
    };
  },
  methods: {
    todoSubmit(e) {
      const loading = this.openLoading();
      let form = this.$refs.refForm.formData;
      let tempObj = {
        taskId: this.process.curtaskId,
        id: form.id,
        variables: JSON.stringify({
          btnMessage: e.btnMessage,
          message: e.nextStep,
          nextDealUser: preUserId || e.nextDealUser,
          option: e.option,
        }),
      };
      fininshFormTask(tempObj).then((res) => {
        if (res.code == 200) {
          this.$message.success("提交成功!");
          loading.close();
          this.getList();
        }
      });
      this.turnbackProcessShow = false;
    },
    transferOper() {
      this.transferProcessShow = true;
    },
    turnbackOper() {
      this.turnbackProcessShow = true;
      this.$refs.turnbackRef.init(this.process);
    },
    returnSubmit(e) {
      const loading = this.openLoading();
      let form = this.$refs.refForm.formData;
      let tempObj = {
        taskId: this.process.curtaskId,
        id: form.id,
        variables: JSON.stringify({
          btnMessage: e.btnMessage,
          message: e.nextStep,
          nextDealUser: e.nextDealUser,
          option: e.option,
        }),
      };
      fininshFormTask(tempObj).then((res) => {
        if (res.code == 200) {
          this.$message.success("提交成功!");
          loading.close();
          location.reload();
        }
      });
      this.turnbackProcessShow = false;
    },
    initProcess(res) {
      //防止部分数据延迟显示的问题(tabs->地市和省公司是否有数据显示隐藏)
      setTimeout(() => {
        this.applyShow = true;
      }, 0);
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

      //匹配流程状态并赋值dictValue
      res.backNextStepAndUser.forEach((item) => {
        item.dictValue = this.processStatusOptions.find(
          (i) => i.dictLabel == item.nextStep
        ).dictValue;
      });
      //退回节点：找到15的流转节点，然后去匹配流程轨迹里为5的节点，找到后将处理人赋值到nextStepUser数组里(这是该流程下的处理人)
      let returnObj1 = res.backNextStepAndUser.find((i) => i.dictValue == "15");
      if (returnObj1) {
        let dictLabel = this.processStatusOptions.find(
          (i) => i.dictValue == "5"
        ).dictLabel;
        let tempReturnObj = res.allFlowStep.find(
          (i) => i.historicTaskInstance.name == dictLabel
        );
        // res.preUserName = tempReturnObj.todoUserLoginId;
        // res.preUserId = tempReturnObj.historicTaskInstance.assignee;
        returnObj1.nextStepUser = [
          {
            nextUserId: tempReturnObj.historicTaskInstance.assignee,
            nextUserName: tempReturnObj.todoUserLoginId,
          },
        ];
      }
      //退回节点：找到15的流转节点，然后去匹配流程轨迹里最开始的节点，找到后将处理人赋值到nextStepUser数组里(这是该流程下的处理人)
      let returnObj2 = res.backNextStepAndUser.find((i) => i.dictValue == "20");
      if (returnObj2) {
        let tempReturnObj = res.allFlowStep[res.allFlowStep.length - 1];

        returnObj2.nextStepUser = [
          {
            nextUserId: tempReturnObj.historicTaskInstance.assignee,
            nextUserName: tempReturnObj.todoUserLoginId,
          },
        ];
      }

      this.process = res;
    },
    handleSaveFromData(e) {
      console.log(e);
    },
    submitData(e) {
      console.log(e);
    },
    async handleAF(e) {
      let form = this.$refs.refForm.formData;
      let tempObj = await this.$refs.refForm.checkSubmit(form.processStatus == "20"?1:null).then((res) => {
        return res;
      });
      if (!tempObj) {
        return;
      }
      tempObj.processStatus = form.processStatus;
      tempObj.variables = JSON.stringify({
        btnMessage: e.btnMessage,
        message: e.nextStep,
        nextDealUser: e.nextDealUser,
        option: e.option,
      });
      tempObj.taskId = e.taskId;
      tempObj.createdBy = form.createdBy;
      const loading = this.openLoading();
      fininshFormTask(tempObj).then((res) => {
        if (res.code == 200) {
          this.$message.success("提交成功");
          location.reload();
          loading.close();
        }
      });
    },
    handleOpen() {
      this.detailInit();
    },
    detailInit() {
      // this.getDetail();
    },
    //获取详情
    getDetail() {
      getCollection(this.id || this.tempId).then((res) => {
        if (res.code == 200) {
          this.formData = res.data;
          res.data.companyList.forEach((item) => {
            item.company = {
              companyId: item.companyId,
              companyName: item.companyName,
            };
          });
          this.prefecturalTableData = res.data.companyList;
          this.provinceTableData = res.data.deptList;
        }
        console.log(res);
      });
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "入库管理_人才收集管理 数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    addInit() {
      getNextDealUser().then((res) => {
        let tempArr = [];
        if (res.data.nextUserId) {
          let ids = res.data.nextUserId.split(",");
          let names = res.data.nextUserName.split(",");
          for (let i = 0; i < ids.length; i++) {
            tempArr.push({
              id: ids[i],
              name: names[i],
            });
          }
        }
        this.nextUsers = tempArr;
      });
    },
    handleClose() {
      this.turnbackProcessShow = false;
      this.transferProcessShow = false;
      this.$emit("close");
    },
    handleTabClick(tab) {},
    add(t) {},
    tableInput(e) {
      let count = 0;
      this.prefecturalTableData.forEach((item) => {
        item.fieldList.forEach((item1) => {
          count += Number(item1.personNumber) || 0;
        });
      });
      e.expertTotals = count;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
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
