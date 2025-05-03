<template>
  <div class="app-container">
    <todo-bpm-header></todo-bpm-header>
    <apply-form
      :disabled="disabled"
      isTodo
      ref="refForm"
      :id="id"
      :type="type"
      @initForm="initForm"
    ></apply-form>
    <deep-btn
      :businessKey="id"
      isTodo
      type="3"
      @initProcess="initProcess"
      isBusiness
      @handleData="handleAF"
    >
      <span slot="btns" style="margin-right: 10px">
          <el-button
            v-if="['30','1','70'].indexOf(form.processStatus) == -1 && process.authorizeFlag == 2"
            type="primary"
            @click="turnbackOper"
            >退回</el-button
          >
        </span>
    </deep-btn>

    <turnback-process
    title="退回"
        ref="turnbackRef"
        isBusiness
        :taskId="process.curtaskId"
        @handleData="returnSubmit"
        :dialogVisible="turnbackProcessShow"
        @close="handleClose"
      ></turnback-process>
  </div>
</template>

<script>
import applyForm from "@/views/talent/apply/applyForm";
import deepBtn from "@/components/WorkFlow/deepBtn";
import { fininshFormTask } from "@/api/talent/apply";
import turnbackProcess from "@/views/talent/collection/turnbackProcess";
import { getDicts } from "@/api/system/dict/data";
export default {
  components: { applyForm, deepBtn,turnbackProcess },
  data() {
    return {
      turnbackProcessShow:false,
      id: "",
      todoId: "",
      disabled: true,
      type: "",
      process: {},
      form: {},
      processStatusOptions:[]
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.todoId = this.$route.query.todoId;
    this.getDicts("leave_process_status").then((response) => {
      this.processStatusOptions = response.data || [];
    });
  },
  methods: {
    initForm(e) { 
      this.form = e;
      this.disabled =  ['1','30','70'].indexOf(e.processStatus) == -1;
    },
    returnSubmit(e) {
      const loading = this.openLoading();
      let preUserId = this.process.preUserId;
      let form = this.$refs.refForm.form;
      let tempObj = {
        taskId: this.process.curtaskId,
        id: form.id,
        variables: JSON.stringify({
          btnMessage:e.btnMessage,
          message: e.nextStep,
          nextDealUser: preUserId || e.nextDealUser,
          option: e.option,
        }),
      }
      fininshFormTask(tempObj).then((res) => {
        if (res.code == 200) { 
          this.$message.success("退回成功!");
          loading.close();
          location.reload();
        }
      });
      this.turnbackProcessShow = false;
    },
    turnbackOper() {
      this.turnbackProcessShow = true;
      this.$refs.turnbackRef.init(this.process);
    },
    initProcess(res) {
      //匹配流程状态并赋值dictValue
      res.backNextStepAndUser.forEach((item) => {
        item.dictValue = this.processStatusOptions.find(
          (i) => i.dictLabel == item.nextStep
        ).dictValue;
      });
      //退回节点：找到20的流转节点，然后去匹配流程轨迹里为5的节点，找到后将处理人赋值到nextStepUser数组里(这是该流程下的处理人)
      let returnObj1 = res.backNextStepAndUser.find((i) => i.dictValue == "50");
      if (returnObj1) {
        let dictLabel = this.processStatusOptions.find(
          (i) => i.dictValue == "20"
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
      //退回节点：找到30的流转节点，然后去匹配流程轨迹里最开始的节点，找到后将处理人赋值到nextStepUser数组里(这是该流程下的处理人)
      let returnObj2 = res.backNextStepAndUser.find((i) => i.dictValue == "30");
      if (returnObj2) {
        let tempReturnObj = res.allFlowStep[res.allFlowStep.length - 1];

        returnObj2.nextStepUser = [
          {
            nextUserId: tempReturnObj.historicTaskInstance.assignee,
            nextUserName: tempReturnObj.todoUserLoginId,
          },
        ];
      }
      //退回节点：找到70的流转节点，然后去匹配流程轨迹里最开始的节点，找到后将处理人赋值到nextStepUser数组里(这是该流程下的处理人)
      let returnObj3 = res.backNextStepAndUser.find((i) => i.dictValue == "70");
      if (returnObj3) {
        let tempReturnObj = res.allFlowStep[res.allFlowStep.length - 1];

        returnObj3.nextStepUser = [
          {
            nextUserId: tempReturnObj.historicTaskInstance.assignee,
            nextUserName: tempReturnObj.todoUserLoginId,
          },
        ];
      }

      this.process = res;
    },
    handleAF(e) {
      if (!this.form.detailList.length) { 
        this.msgError("申请离库人员不能为空!");
        return;
      }
      const loading = this.openLoading();
      e.id = this.form.id;
      e.leaveApplyRelList = this.form.detailList;
      e.processStatus = this.form.processStatus,
      e.variables = JSON.stringify({
        btnMessage:e.btnMessage,
        message: e.nextStep,
        nextDealUser: e.nextDealUser,
        option: e.option,
      })
      fininshFormTask(e).then((res) => {
        if (res.code == 200) {
          this.$message.success("审批成功");
          loading.close();
          location.reload();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    handleClose() {
      this.turnbackProcessShow = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 15px;
}
.operations {
  text-align: center;
}
</style>
