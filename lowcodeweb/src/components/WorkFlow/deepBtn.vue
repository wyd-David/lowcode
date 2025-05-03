<template>
  <div class="operations">
    <!-- 流程轨迹 -->
    <all-flow-step ref="allFlowStepRef" style="padding-bottom: 50px" :allFlowStep="process.allFlowStep"
      v-if="businessKey"></all-flow-step>
    <div :class="isTodo ? 'deep-btn-box' : 'dialog-btn-box'">
      <!-- 待办页隐藏取消按钮 -->
      <el-button v-show="!isTodo" @click="handleClose" style="margin-right: 10px">取 消</el-button>
      <!-- 发起默认按钮 -->
      <span v-if="$slots.default && !businessKey">
        <slot></slot>
      </span>
      <!-- 自定义按钮 -->
      <span v-if="$slots.btns">
        <slot name="btns"></slot>
      </span>
      <!-- 流程的按钮控制 -->
      <span v-if="process.authorizeFlag == '2'">
        <!-- <slot name="running"></slot>-->
        <!-- 暂存 -->
        <el-button type="primary" v-if="hasBtnPerms['saveDraft']" @click="saveDraftData('saveDraft')">
          {{ hasBtnPerms["saveDraft"]["name"] }}
        </el-button>
        <!-- 回退 -->
        <el-button type="primary" v-if="hasBtnPerms['turnback']" @click="turnbackOper('turnback')">
          {{ hasBtnPerms["turnback"]["name"] }}
        </el-button>
        <!-- 转办 -->
        <el-button type="primary" v-if="hasBtnPerms['transfer']" @click="transferOper('transfer')">
          {{ hasBtnPerms["transfer"]["name"] }}
        </el-button>
        <!-- 审批 -->
        <el-button type="primary" v-if="hasBtnPerms['complete']" @click="applyOper('complete')">
          {{ hasBtnPerms["complete"]["name"] }}
        </el-button>
      </span>
    </div>

    <!-- <submit-Process
       ref="siRef"
       processKey="archive_process_flow"
       stepName="归档人收集材料"
       @close="close"
       @complete="submitData"
     ></submit-Process> -->
    <complete-process
      ref="completeRef"
      :title="title"
      :taskId="process.curtaskId"
      :dialogVisible="applyShow"
      :isBusiness="isBusiness"
      :preUserName="preUserName"
      @handleData="handleData"
      @close="applyShow = false"
    ></complete-process>
    <turnback-process
      ref="turnbackRef"
      :title="title"
      :isBusiness="isBusiness"
      :taskId="process.curtaskId"
      :dialogVisible="turnbackProcessShow"
      @handleData="handleData"
      @close="turnbackProcessShow = false"
    ></turnback-process>

    <transfer-process
      ref="transferRef"
      :title="title"
      :taskId="process.curtaskId"
      :dialogVisible="transferProcessShow"
      @close="transferProcessShow = false"
    ></transfer-process>
  </div>
</template>

<script>
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import SubmitProcess from "@/components/WorkFlow/submitProcess";
import CompleteProcess from "@/components/WorkFlow/completeProcess";
import TurnbackProcess from "@/components/WorkFlow/turnbackProcess";
import transferProcess from "@/components/WorkFlow/transferProcess";

import { startFormTask, fininshFormTask } from "@/api/workflow";
import { fetchWorkFlowInfo } from "@/utils/workflow";

export default {
  props: {
    businessKey: {
      type: String,
      default: "",
    },
    needBeforeComplete: {
      type: Boolean,
      default: false,
    },
    preUserName: {
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
    //1是不调用权限接口，0是调用权限接口
    ischeck: {
      type: Number,
      default: 0,
    },
    //0-发起 1-流程运行中
    type: {
      type: String,
      default: "1",
    },
    //是否自定义接口回调
    isBusiness: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    AllFlowStep,
    SubmitProcess,
    CompleteProcess,
    TurnbackProcess,
    transferProcess,
  },

  watch: {
    businessKey (val) {
      console.log('businessKey++', val)
      this.init();
    },
    ischeck (val) {
      console.log('ischeck--', val)
    },
  },
  data () {
    return {
      title: null,
      //按钮
      applyShow: false,
      turnbackProcessShow: false,
      transferProcessShow: false,
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
  created () {
    console.log('creat');
    this.init();
  },
  methods: {
    //自定义接口回调
    handleData (res) {
      this.$emit("handleData", res);
    },
    // 初始化流程信息
    init () {
      let that = this
      fetchWorkFlowInfo(this.businessKey, this.todoId, (e) => {
        that.process = e;
        that.hasBtnPerms = that.process.hasBtnPerms;
        console.log(that.process);
        that.$emit("initProcess", that.process);
      }, this.ischeck);
    },
    applyOper (btnText) {
      this.setTitle(btnText)
      if (this.needBeforeComplete) {
        this.$emit("beforeComplete", () => {
          this.openDialog("applyShow", "completeRef");
        });
      } else {
        this.openDialog("applyShow", "completeRef");
      }
    },
    transferOper (btnText) {
      this.setTitle(btnText)
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    turnbackOper (btnText) {
      this.setTitle(btnText)
      this.openDialog("turnbackProcessShow", "turnbackRef");
    },
    // 记录按钮文本
    setTitle (str) {
      this.title = this.hasBtnPerms[str].name
      console.log(this.hasBtnPerms[str].name);
    },
    //打开弹窗
    openDialog (show, btnRef) {
      if (
        !this.process.nextStepAndUser &&
        this.process.nextStepAndUser.length == 0
      ) {
        this.$message.error("流程人员异常");
        return;
      }
      this[show] = true;
      this.$refs[btnRef].init(this.process);
      // 传递的数据
    },
    saveDraftData () {
      this.$emit("saveDraftData");
    },
    handleClose () {
      this.$emit("close");
    },
    closeAll () {
      this.applyShow = false;
      this.turnbackProcessShow = false;
      this.transferProcessShow = false;
    },
  },
};
</script>

<style scoped lang="scss">
.operations {
  width: auto;
  box-sizing: border-box;
  position: relative;
}
</style>
