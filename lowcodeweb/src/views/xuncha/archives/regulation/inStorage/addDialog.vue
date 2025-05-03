<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :title="obj.title"
      :visible.sync="dialogVisible"
      width="70%"
      :before-close="close"
    >
      <div class="dialog-content">
        <apply-form
          ref="applyForm"
          :obj="obj"
          :ischeck="1"
          v-if="dialogVisible"
          @close="close"
        ></apply-form>
      </div>

      <span
        slot="footer"
        v-if="obj.operationType == 1 || obj.operationType == 3"
        class="dialog-footer"
      >
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="handleSave">暂 存</el-button>
        <el-button type="primary" @click="handleClick">提 交</el-button>
      </span>
    </el-dialog>

    <submit-Process
      :dialogVisible="submitProcessShow"
      ref="siRef"
      processKey="regulation_apply_flow"
      stepName="室经理审批"
      @close="submitProcessShow = false"
      @complete="submit"
    ></submit-Process>
  </div>
</template>

<script>
import { save } from "@/api/xuncha/archives/inStorage";
import applyForm from "./applyForm";

import submitProcess from "@/components/WorkFlow/submitProcess";
import { fininshFormTask } from "@/api/workflow";

export default {
  components: { applyForm, submitProcess },
  props: {
    obj: [Object],
    dialogVisible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      submitProcessShow: false,
    };
  },
  methods: {
    handleClick() {
       this.$confirm("是否确定提交?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.submitProcessShow = true;
      })
    },
    handleSave() {
       this.$confirm("是否确定暂存?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.submit({ userName: "" }, "draft");
      })
    },
    submit(e, status) {
      console.log(this.$refs.applyForm.process);
      debugger;
      let tempForm = this.$refs.applyForm.form;

      tempForm.firstContentId = tempForm.firstContent.contentId;
      tempForm.firstContentName = tempForm.firstContent.contentName;
      tempForm.secondContentId = tempForm.secondContent.contentId;
      tempForm.secondContentName = tempForm.secondContent.contentName;

      tempForm.thirdContentId = tempForm.thirdContent.contentId;
      tempForm.thirdContentName = tempForm.thirdContent.contentName;
      tempForm.status = status || "checking";
      tempForm.userName = e.userName;
      console.log(tempForm);
      this.$refs.applyForm.$refs.rulesForm.validate((valid) => {
        if (valid) {
          const loading = this.openLoading();
          save(tempForm).then((res) => {
            if (res.code == 200) {
              let process = this.$refs.applyForm.process
              if (process.allFlowStep.length != 0&&status!='draft') {
                let fm = new FormData();
                fm.append("taskId", process.curtaskId);
                fm.append("userId", this.$store.getters.name);

                fm.append(
                  "TaskVariables",
                  JSON.stringify({
                    nextDealUser: e.userName,
                    message: "提交",
                    option: e.option,
                  })
                );
                fininshFormTask(fm).then((res) => {
                  loading.close();
                  if (res.code == 200) {
                    this.$message.success("提交成功");
                    this.close(200);
                  } else {
                    this.$message.error("提交失败");
                    // this.$emit("close");
                  }
                });
              }else{
                loading.close();
                this.msgSuccess(res.msg)
                this.close(200)
              }
              
            }
          });
        }
      });
    },

    close(e) {
      this.submitProcessShow = false;
      this.$emit("close", e);
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.el-dialog__body {
  max-height: 500px;
  overflow-y: auto;
}
/deep/.el-dialog__footer {
  position: relative;
  z-index: 2222;
}
.dialog-footer {
}
</style>
