<template>
  <div>
    <!-- <div slot="footer" class="dialog-footer">
    <slot></slot>
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="storageForm">暂 存</el-button>
      <el-button type="primary" @click="subShow = true">提 交</el-button>
    </div> -->
    <!-- 审核 -->
    <el-dialog
      :title="title"
      modal-append-to-body
      append-to-body
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="600px"
    >
      <div class="dialog-context">
        <el-form
          label-width="140px"
          :model="subForm"
          :rules="afRules"
          ref="afRules"
        >
          <el-form-item label="下一环节：" prop="message">
            <el-select
              style="width: 100%"
              v-model="subForm.message"
              placeholder="请选择"
              @change="handleSelect"
            >
              <el-option
                v-for="item in nextStep"
                :key="item.outLine"
                :label="item.nextStep"
                :value="item.outLine"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="!preUserName" label="处理人：" prop="userId">
            <el-select
            disabled
              style="width: 100%"
              v-model="subForm.userId"
              placeholder="请选择"
            >
              <el-option
                v-for="item in nextUsers"
                :key="item.nextUserId"
                :label="item.nextUserName"
                :value="item.nextUserId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-else label="处理人：">
            <el-input disabled :value="preUserName"></el-input>
          </el-form-item>
          <el-form-item label="处理意见：" prop="option">
            <el-input
              v-model="subForm.option"
              type="textarea"
              row="3"
              maxlength="200"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="sub">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import request from "@/utils/request";
import { fininshFormTask } from "@/api/workflow";

export default {
  props: {
    preUserName: [String],
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    processKey: {
      type: String,
      default: "",
    },
    stepName: {
      type: String,
      default: "",
    },
    taskId: {
      type: String,
      default: "",
    },
    isBusiness: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "下一步",
    },
  },
  data() {
    return {
      subShow: false,
      subForm: {
        id: "",
        userId: "",
      },
      nextStep: [],
      url: "",
      obj: {},
      nextUsers: [], //下一步处理人
      afRules: {
        userId: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "change",
          },
        ],
        message: [
          {
            required: true,
            message: "请选择退回节点",
            trigger: "change",
          },
        ],
        option: [
          {
            required: true,
            message: "处理意见不能为空",
            trigger: "change",
          },
        ],
        
      },
    };
  },
  created() {
    // this.init();
  },
  methods: {
    handleSelect(value) {
      this.nextStep.forEach((item) => {
        if (item.outLine == value) {
          this.nextUsers = item.nextStepUser;
        }
      });
      this.subForm.userId = this.nextUsers[0].nextUserId;
    },
    open(url, obj) {
      this.subShow = true;
      this.url = url;
      this.obj = obj;
    },
    init(process) {
      this.taskId = process.curtaskId;
      this.nextStep = process.backNextStepAndUser;
      this.subForm.message = process.backNextStepAndUser[0].outLine;
        
      if (this.nextStep && this.nextStep.length !== 0) {
        this.nextUsers = this.nextStep[0].nextStepUser;
        this.subForm.userId = this.nextUsers[0].nextUserId;
      }
      // this.nextUsers = process.backNextStepAndUser[0].nextStepUser
    },
    // 取消
    handleClose(code) {
      this.$emit("close", code);
    },

    sub() {
      this.$refs["afRules"].validate((valid) => {
        if (valid) {
          const loading = this.openLoading();
          let fm = new FormData();
          fm.append("taskId", this.taskId);
          fm.append("userId", this.$store.getters.name);
          fm.append(
            "TaskVariables",
            JSON.stringify({
              nextDealUser: this.subForm.userId,
              message: this.subForm.message,
              btnMessage: this.title,
              option: this.subForm.option,
            })
          );
          if (this.isBusiness) {
            this.$emit("handleData", {
              taskId: this.taskId,
              nextDealUser: this.subForm.userId,
              nextStep: this.subForm.message,
              option: this.subForm.option,
              message: "退回",
              btnMessage: this.title,
            });
            return;
          }

          fininshFormTask(fm).then((res) => {
            loading.close();
            if (res.code == 200) {
              this.$message.success("提交成功");
              setTimeout(() => {
                this.$emit("close");
                location.reload();
              }, 500);
            } else {
              this.$message.error("提交失败");
              // this.$emit("close");
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="scss">
.dialog-context {
}

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

.dialog-footer {
  text-align: center;
}
</style>
