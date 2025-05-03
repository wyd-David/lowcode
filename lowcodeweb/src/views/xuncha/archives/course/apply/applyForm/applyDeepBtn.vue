<template>
  <div class="operations">
    <!-- 流程轨迹 -->
    <template>
      <all-flow-step
        ref="allFlowStepRef"
        :allFlowStep="process.allFlowStep"
        v-if="businessKey"
      ></all-flow-step>
    </template>
    <div :class="{ 'deep-btn-box': isTodo, 'dialog-btn-box': !isTodo }">
      <el-button
        v-show="!isTodo"
        @click="handleClose"
        style="margin-right: 10px"
        >取 消</el-button
      >
      <!-- 发起默认按钮 -->
      <span v-if="!businessKey">
        <slot></slot>
      </span>

      <!-- 流程的按钮控制 -->
      <span v-if="process.authorizeFlag == '2' && type != '1'">
        <!-- 暂存 -->
        <el-button
          type="primary"
          v-if="hasBtnPerms['saveDraft']"
          @click="saveDraftData('saveDraft')"
        >
          {{ hasBtnPerms["saveDraft"]["name"] }}
        </el-button>

        <!-- 回退 -->
        <el-button
          type="primary"
          v-if="hasBtnPerms['turnback']"
          @click="turnbackOper('turnback')"
        >
          {{ hasBtnPerms["turnback"]["name"] }}
        </el-button>
        <!-- 转办 -->
        <el-button
          type="primary"
          v-if="hasBtnPerms['transfer'] && roleType != '3'"
          @click="transferOper('transfer')"
        >
          {{ hasBtnPerms["transfer"]["name"] }}
        </el-button>
        <!-- 任务共享 -->
        <el-button
          type="primary"
          v-if="hasBtnPerms['participant'] && roleType == '2'"
          @click="participantOper('participant')"
        >
          {{ hasBtnPerms["participant"]["name"] }}
        </el-button>
        <!--  提交 -->
        <el-button
          type="primary"
          v-if="hasBtnPerms['complete'] && roleType != '3'"
          @click="applyOper('complete')"
        >
          {{ hasBtnPerms["complete"]["name"] }}
        </el-button>

        <!-- 共享人完成 -->
        <el-button
          type="primary"
          v-if="hasBtnPerms['participantComplete'] && roleType == '3'"
          @click="onParticipantsComplete()"
        >
          {{ hasBtnPerms["participantComplete"]["name"] }}
        </el-button>
      </span>
    </div>

    <!-- <submit-Process
      ref="completeRef"
      :taskId="process.curtaskId"
      :dialogVisible="applyShow"
      @close="applyShow = false"
    ></submit-Process> -->

    <complete-process
      :title="title"
      :preUserName="process.preUserName"
      ref="completeRef"
      :isPreUser="isPreUser"
      :taskId="process.curtaskId"
      :dialogVisible="applyShow"
      :isBusiness="isBusiness"
      @handleData="handleData"
      @close="applyShow = false"
    ></complete-process>
    <turnback-process
      :title="title"
      ref="turnbackRef"
      :taskId="process.curtaskId"
      :dialogVisible="turnbackProcessShow"
      @close="turnbackProcessShow = false"
    ></turnback-process>

    <transfer-process
      :title="title"
      ref="transferRef"
      :taskId="process.curtaskId"
      :dialogVisible="transferProcessShow"
      @close="transferProcessShow = false"
    ></transfer-process>

    <task-dispense
      :dialogVisible="taskDispenseShow"
      :applyId="applyId"
      v-bind="$attrs"
      :taskId="process.curtaskId"
      @close="taskDispenseShow = false"
    ></task-dispense>
  </div>
</template>

<script>
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import SubmitProcess from "./components/applySubmit.vue";
import TurnbackProcess from "@/components/WorkFlow/turnbackProcess";
import transferProcess from "@/components/WorkFlow/transferProcess";
import CompleteProcess from "@/components/WorkFlow/completeProcess";

import taskDispense from "./components/taskDispense.vue";
import { fetchWorkFlowInfo } from "@/utils/workflow";

export default {
  props: {
    isPreUser: {
      type: Boolean,
      default: false,
    },

    businessKey: {
      type: String,
      default: "",
    },

    todoId: {
      type: String,
      default: "",
    },
    // 所在页面是否为待办
    isTodo: {
      type: Boolean,
      default: false,
    },
    //0-发起 1-流程运行中
    type: {
      type: String,
      default: "1",
    },
    needBeforeComplete: {
      type: Boolean,
      default: false,
    },
    roleType: {
      type: String,
      default: "1",
    },
    applyId: {
      type: String,
      default: "",
    },
  },
  components: {
    AllFlowStep,
    SubmitProcess,
    TurnbackProcess,
    transferProcess,
    taskDispense,
    CompleteProcess,
  },
  watch: {},
  data() {
    return {
      //按钮
      applyShow: false,
      turnbackProcessShow: false,
      transferProcessShow: false,
      taskDispenseShow: false,
      isBusiness: false,
      title:null,
      //按钮权限
      hasBtnPerms: {},
      //流程基本信息
      process: {
        //业务id
        businessKey: "",
        //待办id
        todoId: "",
        //0无权限 1可查看 2可处理
        authorizeFlag: "",
        //流程实例id
        processInstanceId: null,
        //流程定义key
        processDefinitionId: null,
        //任务定义key
        taskDefinitionKey: null,
        //当前节点用户
        curStepUser: "",
        //当前节点名称
        curStepName: "",
        //任务id
        curtaskId: "",
        //下一个节点
        nextStepAndUser: [],
        //退回节点
        backNextStepAndUser: [],
        //流程轨迹
        allFlowStep: [],
        //按钮信息
        hasBtnPerms: [],
      },
    };
  },
  created() {
    // this.init();
  },
  watch: {
    businessKey(val) {
      console.log(val);
      this.init();
    },
  },
  methods: {
    // 初始化流程信息
    init() {
      let that = this;
      if (!this.isTodo) {
        var flag = 1;
      }
      // this.businessKey='cb5044848e714af384a27c13274e8005'
      fetchWorkFlowInfo(
        this.businessKey,
        this.todoId,
        (e) => {
          // if (e.authorizeFlag == 0) {
          //   that.$router.push("/noPermission");
          // }
          that.process = e;
          console.log(e);
          that.hasBtnPerms = that.process.hasBtnPerms;
          this.$emit("setAuthorizeFlag", that.process.authorizeFlag);
          this.$emit("queryDetail", e);
        },
        flag
      );
    },
    setRoletype() {
      // 得到流程数据，再获取申请表单
      // if (this.process.curStepName=='申报人拟稿') {
      // }
      // else if (this.process.curStepName=='归档人收集材料') {
      //   // this.isBusiness=true
      //   this.$emit('queryDetail','归档人收集材料')
      // }else{
      //   this.$emit('queryDetail','')
      // }
    },
    //自定义接口回调
    handleData(res) {
      this.$emit("participantSaveDraftData", res);
    },
    // 共享人完成
    onParticipantsComplete() {
      this.$emit("participantComplete", this.process.curtaskId);
    },
    applyOper(btnText) {
      this.setTitle(btnText);
      if (this.process.curStepName == "申报人拟稿") {
        this.$emit("initiatorComplete");
      } else if (
        this.process.curStepName == "归档人收集材料" ||
        this.process.curStepName == "归档人收集资料"
      ) {
        this.$emit("participantSaveDraftData", () => {
          this.openDialog("applyShow", "completeRef");
        });
      } else {
        this.openDialog("applyShow", "completeRef");
      }
    },
    // onParticipantComplete() {
    //   this.openDialog("applyShow", "completeRef");

    // },
    transferOper(btnText) {
      this.setTitle(btnText);
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    turnbackOper(btnText) {
      this.setTitle(btnText);

      this.openDialog("turnbackProcessShow", "turnbackRef");
    },
    //打开弹窗
    openDialog(show, btnRef) {
      if (
        !this.process.nextStepAndUser &&
        this.process.nextStepAndUser.length == 0
      ) {
        this.$message.error("流程人员异常");
        return;
      }
      this[show] = true;

      console.log(this.$refs[btnRef]);
      this.$refs[btnRef].init(this.process);
    },
    // 任务共享
    participantOper(btnText) {
      this.setTitle(btnText);

      this.taskDispenseShow = true;
    },
    // 暂存
    saveDraftData(btnText) {
      this.setTitle(btnText);
      if (
        this.process.curStepName == "归档人收集材料" ||
        this.process.curStepName == "归档人收集资料"
      ) {
        this.$emit("participantSaveDraftData",btnText);
      } else {
        this.$emit("saveDraftData");
      }
    },
    setTitle(str) {
      this.title = this.hasBtnPerms[str].name;
      console.log(this.hasBtnPerms[str].name);
    },
    handleClose() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped lang="scss">
.operations {
  width: auto;
  box-sizing: border-box;
  position: relative;
  // text-align: center;
}
</style>
