<template>
  <div class="continer">
    <div class="dialog-title">
      <span class="line"></span><span>人员抽调信息</span>
    </div>
    <div class="context-row">
      <el-form disabled label-width="80px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工单号:">
              <el-input v-model="form.personnelInfoNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察年份:">
              <el-input v-model="form.xunchaYear"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="巡察批次:">
              <el-input v-model="form.piciName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察时段:">
              <el-input v-model="form.xunchaTimeSolt"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="抽调通知:">
              <el-input type="textarea" v-model="form.content"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="dialog-title">
      <span class="line"></span><span>请假信息</span>
    </div>
    <div class="context-row">
      <el-form
        ref="formRef"
        :rules="formRules"
        :model="form"
        :disabled="!authorizeFlag"
        label-width="100px"
      >
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="被抽调人员:">
              <el-input disabled v-model="form.personnelName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属公司:">
              <el-input disabled v-model="form.company"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="部门:">
              <el-input disabled v-model="form.ouname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="抽调时间:">
              <el-input disabled v-model="form.createdTime"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="是否参加:">
              <el-radio v-model="form.feedbackStatus" :label="1">是</el-radio>
              <el-radio v-model="form.feedbackStatus" :label="0">否</el-radio>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row v-if="form.feedbackStatus == 0" :gutter="24">
          <el-col :span="24">
            <el-form-item label="请假事由:" prop="remarks">
              <el-input
                placeholder="请输入请假事由"
                type="textarea"
                v-model="form.remarks"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <deep-btn :businessKey="personnelInfoId" @initProcess="initProcess" isTodo>
      <span slot="btns" style="margin-right: 10px" v-if="process.curtaskId">
        <el-button
          type="primary"
          v-if="
            process.curStepName == '被抽调人员确认' &&
            process.authorizeFlag == 2
          "
          @click="submit"
          >提交</el-button
        >
        <el-button
          v-if="
            process.curStepName != '被抽调人员确认' &&
            process.authorizeFlag == 2
          "
          type="primary"
          @click="handleRefuse"
          >{{ turnBtn }}</el-button
        >
        <el-button
          v-if="
            process.curStepName != '被抽调人员确认' &&
            process.authorizeFlag == 2
          "
          type="primary"
          @click="handleComplete"
          >同意</el-button
        >
      </span>
    </deep-btn>

    <submit-process
      processKey="transfer_personnel_confirm"
      :stepName="stepName"
      :dialogVisible="submitProcessShow"
      @close="submitProcessShow = false"
      @complete="saveFromData"
      :title="turnTitle"
    ></submit-process>

    <complete-process
      :title="completeTitle"
      ref="completeRef"
      :dialogVisible="completeProcessShow"
      isBusiness
      @handleData="handleAF"
      @close="completeProcessShow = false"
    ></complete-process>
  </div>
</template>
<script>
import {
  getSendTransferInfo,
  fininshPersonnelToExamine,
} from "@/api/xuncha/transferManage/transferPersonnelInfo";
import deepBtn from "@/components/WorkFlow/deepBtn";
import submitProcess from "@/components/WorkFlow/submitProcess";
import completeProcess from "@/components/WorkFlow/completeProcess";
export default {
  components: { deepBtn, submitProcess, completeProcess },
  data() {
    return {
      personnelInfoId: null,
      todoId: null,
      infoId: null,
      authorizeFlag: true,
      submitProcessShow: false,
      completeProcessShow: false,
      turnBtn: "拒绝",
      turnTitle: "拒绝",
      completeTitle: "同意",
      stepName: "部门领导审核",
      form: {
        transferNo: "",
        content: "",
        feedbackStatus: 1,
        remarks: "",
      },
      process: {},
      formRules: {
        remarks: [
          { required: true, message: "请输入请假事由", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submit() {
      if (this.form.feedbackStatus == 1) {
        this.completeTitle = "同意";
        this.$refs.completeRef.init(this.process);
        this.completeProcessShow = true;
      } else {
        this.$refs.formRef.validate((valid) => {
          if (valid) {
            this.stepName = "部门领导审核";
            this.submitProcessShow = true;
          }
        });
      }
    },
    initProcess(e) {
      if (e.curStepName == "被抽调人员确认") {
        this.turnBtn = "拒绝";
      } else {
        this.turnBtn = "退回";
      }
      this.process = e;
    },
    saveFromData(e) {
      e = JSON.parse(e.variables);
      const loading = this.openLoading();
      fininshPersonnelToExamine({
        btnMessage: e.btnMessage,
        content: e.option,
        examineStatus: this.form.examineStatus,
        feedbackStatus: 0,
        message: e.btnMessage,
        nextUserId: e.nextDealUser,
        remarks: this.form.remarks,
        personnelInfoNo: this.form.personnelInfoNo,
        taskId: this.process.curtaskId,
        transferNo: this.form.transferNo,
      }).then((res) => {
        if (res.code == 200) {
          this.msgSuccess(res.msg);
          loading.close();
          setTimeout(() => {
            location.reload();
          }, 500);
        }
      });
    },
    handleAF(e) {
      const loading = this.openLoading();
      fininshPersonnelToExamine({
        btnMessage: e.btnMessage,
        content: e.option,
        examineStatus: this.form.examineStatus,
        feedbackStatus: this.form.feedbackStatus,
        message: e.btnMessage,
        nextUserId: e.nextDealUser,
        remarks: this.form.remarks,
        personnelInfoNo: this.form.personnelInfoNo,
        taskId: this.process.curtaskId,
        transferNo: this.form.transferNo,
      }).then((res) => {
        if (res.code == 200) {
          this.msgSuccess(res.msg);
          loading.close();
          setTimeout(() => {
            location.reload();
          }, 500);
        }
      });
    },
    handleRefuse() {
      //省公司同意退回都直接结束，分公司退回结束，同意需要提交到分管公司领导审核节点
      //分管公司领导审核节点同意退回都直接结束
      this.$refs.completeRef.init(this.process);
      this.completeTitle = "退回";
      this.completeProcessShow = true;
    },
    handleComplete() {
      if (
        this.process.curStepName == "部门领导审核" &&
        this.form.company.indexOf("分公司") != -1
      ) {
        this.turnTitle = "同意";
        this.stepName = "分管公司领导审核";
        this.submitProcessShow = true;
      } else {
        this.completeTitle = "同意";
        this.$refs.completeRef.init(this.process);
        this.completeProcessShow = true;
      }
    },
  },
  mounted() {
    this.personnelInfoId = this.$route.query.id;
    this.todoId = this.$route.query.todoId;
    this.infoId = this.$route.query.id;
    this.transferNo = this.$route.query.transferNo;
    getSendTransferInfo({ personnelInfoNo: this.personnelInfoId }).then(
      (res) => {
        this.form = res.data;
        // feedbackStatus有值为已处理,不可编辑
        if (res.data.feedbackStatus) {
          this.form.feedbackStatus = parseInt(res.data.feedbackStatus);
          this.authorizeFlag = false;
        } else {
          this.form.feedbackStatus = 1;
        }
      }
    );
  },
};
</script>
<style lang="scss" scoped>
.continer {
  padding: 15px;
}
</style>