<template>
  <div class="app-container">
    <todo-bpm-header></todo-bpm-header>
    <div class="dialog-context">
      <basics-info :form="form"></basics-info>

      <div class="dialog-title">
        <span class="line"></span><span>资料清单</span>
      </div>
      <div class="context-row">
        <el-table :data="inventoryList">
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="index" width="55"> </el-table-column>
          <el-table-column
            label="提供部门"
            show-overflow-tooltip
            prop="deptName"
          ></el-table-column>
          <el-table-column
            label="需求资料清单"
            prop="inventoryName"
          ></el-table-column>
          <el-table-column label="说明" prop="remark"></el-table-column>
          <el-table-column align="center" label="上传资料数">
            <template slot-scope="scope">
              <span>{{ fileCount(scope.row.id) }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="!disabled" fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="uploadOpen(scope.row)"
                >文件上传</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="dialog-title">
        <span class="line"></span
        ><span
          >上传材料列表
          <span style="font-weight: 400"
            >（说明：选择通用型资料则默认可提供给本批所有巡察组）</span
          ></span
        >
      </div>
      <div class="context-row">
        <el-table :data="fileList">
          <el-table-column
            label="序号"
            type="index"
            width="55"
          ></el-table-column>
          <el-table-column label="上传资料">
            <template slot-scope="scope">
              <el-button type="text">{{ scope.row.fileName }}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="是否为通用材料">
            <template slot-scope="scope">
              <el-radio
                :disabled="disabled"
                v-model="scope.row.commonData"
                label="1"
                >是</el-radio
              >
              <el-radio
                :disabled="disabled"
                v-model="scope.row.commonData"
                label="0"
                >否</el-radio
              >
            </template>
          </el-table-column>
          <el-table-column
            label="归属清单"
            prop="deptInventoryName"
          ></el-table-column>
          <el-table-column label="操作" v-if="!disabled">
            <template slot-scope="scope">
              <el-button @click="delFile(scope.$index)" type="text"
                >删除</el-button
              >
              <el-button @click="uploadOpen(scope.row, true)" type="text"
                >重新上传</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <deep-btn
      :businessKey="id"
      :todoId="todoId"
      isTodo
      isBusiness
      @initProcess="initProcess"
    >
      <span slot="btns" v-if="process.curStepName">
        <span
          style="margin-right: 10px"
          v-if="process.curStepName != '资料审核'"
        >
          <el-button @click="temporary" type="primary">暂存</el-button>
          <el-button @click="submit" type="primary">提交</el-button>
        </span>
        <span style="margin-right: 10px" v-else>
          <el-button type="primary" @click="apply">审核</el-button>
          <el-button type="primary" @click="turnback">退回</el-button>
        </span>
      </span>
    </deep-btn>

    <el-dialog
      title="文件上传"
      :visible.sync="dialogVisible"
      width="50%"
      @open="initUpload"
      :before-close="handleClose"
    >
      <div>
        <CommonUpload
          v-if="isShowUpload"
          ref="CommonUpload"
          :single-file="true"
          @file-success="onFileSuccess"
          @file-removed="onFileRemoved"
        ></CommonUpload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm" :disabled="isConfirm"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <complete-process
      :title="completeTitle"
      ref="completeRef"
      :preUserName="process.preUserName"
      :taskId="process.curtaskId"
      :dialogVisible="applyShow"
      isBusiness
      @handleData="sbumitFromData"
      @close="applyShow = false"
    ></complete-process>

    <turnback-process
      ref="turnbackRef"
      isBusiness
      title="退回"
      :taskId="process.curtaskId"
      @handleData="sbumitFromData"
      :dialogVisible="turnbackProcessShow"
      @close="turnbackProcessShow = false"
    ></turnback-process>
  </div>
</template>

<script>
import basicsInfo from "@/views/xuncha/ahead/patrolDataCollect/basicsInfo";
import deepBtn from "@/components/WorkFlow/deepBtn";
import {
  getTodoInfo,
  fininshFormTask,
  temporarySaveDeptOrFile,
} from "@/api/xuncha/ahead/deptTodo";
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
import turnbackProcess from "@/components/WorkFlow/turnbackProcess";
import completeProcess from "@/components/WorkFlow/completeProcess";
export default {
  components: {
    basicsInfo,
    deepBtn,
    CommonUpload,
    completeProcess,
    turnbackProcess,
  },
  data() {
    return {
      id: "",
      collectionId: "",
      todoId: "",
      form: {},
      inventoryList: [],
      dialogVisible: false,
      isShowUpload: false,
      isConfirm: true,
      applyShow: false,
      completeTitle: "提交",
      turnbackProcessShow: false,
      file: {},
      fileList: [],
      dialogObj: {},
      process: {},
      isReUpload: false,
      disabled: true,
    };
  },
  computed: {
    fileCount() {
      return function (id) {
        return this.fileList.filter((i) => i.deptInventoryId == id).length;
      };
    },
  },
  created() {
    this.id = this.$route.query.id;
    this.todoId = this.$route.query.todoInfoId;
    this.collectionId = this.$route.query.collectionId;
    this.getDetail();
  },
  methods: {
    delFile(i) {
      this.fileList.splice(i, 1);
    },
    apply() {
      this.completeTitle = "审核";
      this.$refs["completeRef"].init(this.filterProcess(1));
      this.applyShow = true;
    },
    turnback() {
      this.$refs["turnbackRef"].init(this.filterProcess(3));
      this.turnbackProcessShow = true;
    },
    submit() {
      this.completeTitle = "提交";
      this.$refs["completeRef"].init(this.filterProcess(1));
      this.applyShow = true;
    },
    //暂存
    temporary() {
      temporarySaveDeptOrFile({
        ...this.form,
        deptFileList: this.fileList,
      }).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("提交成功");
          location.reload();
        } else {
          this.msgError(res.msg);
        }
        loading.close();
      });
    },
    //退回回调
    returnSubmit(e) {
      console.log(e);
    },
    //提交,审核,退回事件回调
    sbumitFromData(e) {
      let process = this.filterProcess(e.btnMessage == "提交" ? 1 : 3);
      const loading = this.openLoading();
      fininshFormTask({
        ...this.form,
        deptFileList: this.fileList,
        taskId: this.process.curtaskId,
        variables: JSON.stringify({
          btnMessage: e.btnMessage,
          message: e.nextStep,
          nextDealUser: e.btnMessage=="审核"?e.nextDealUser:process.preUserId,
          option: e.option,
        }),
      }).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("提交成功");
          location.reload();
        } else {
          this.msgError(res.msg);
        }
        loading.close();
      });
    },
    initProcess(res) {
      if (res.curStepName == "资料收集中") {
        this.disabled = false;
      }
      this.process = res;
    },
    //改造流程数据,1->提交 审核,3->退回
    filterProcess(t) {
      let res = JSON.parse(JSON.stringify(this.process));
      if (t == 1) {
        if (res.allFlowStep.length) {
          debugger
          let tempObj = res.allFlowStep[1];
          res.preUserName = tempObj.todoUserLoginId;
          res.preUserId = tempObj.historicTaskInstance.assignee;
          if (res.nextStepAndUser[1]) {
            res.nextStep = res.nextStepAndUser[1].nextStep;
          }
        }
      } else if (t == 3) {
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
      }
      return res;
    },
    uploadOpen(row, t) {
      this.file = null;
      this.dialogObj = row;
      this.dialogVisible = true;
      this.isReUpload = t;
    },
    //打开文件上传清楚之前的记录
    initUpload() {
      this.isConfirm = true;
      this.isShowUpload = true;
    },
    //成功回调
    onFileSuccess(file, files, fileList) {
      this.file = file.result.data;
      this.isConfirm = false;
    },
    //移除回调
    onFileRemoved(file, files, fileList) {
      this.file = null;
      this.dialogObj.fileId = "";
      this.dialogObj.fileName = "";
    },
    confirm() {
      if (!this.file) {
        this.$message.warning("请上传文件!");
        return;
      }
      this.dialogObj.fileId = this.file.fileId;
      this.dialogObj.fileName = this.file.fileName;
      if (!this.isReUpload) {
        let tempObj = JSON.parse(JSON.stringify(this.dialogObj));
        tempObj.commonData = "1";
        tempObj.deptInventoryId = tempObj.id;
        tempObj.deptInventoryName = tempObj.inventoryName;
        this.fileList.push(tempObj);
      }
      this.dialogObj.fileCount++;
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    handleClose() {
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    getDetail() {
      getTodoInfo({
        orderNumber: this.id,
        collectionId: this.collectionId,
      }).then((res) => {
        if (res.code == 200) {
          this.form = res.data;
          if (this.form.dataType == 1) {
            this.inventoryList = JSON.parse(
              JSON.stringify(this.form.deptInventoryList)
            );
          } else {
            this.inventoryList = JSON.parse(
              JSON.stringify(this.form.companyInventoryList)
            );
          }
          this.fileList = JSON.parse(JSON.stringify(this.form.deptFileList));
        }
      });
    },
  },
};
</script>

<style></style>
