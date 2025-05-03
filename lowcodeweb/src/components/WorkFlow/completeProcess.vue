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
          <el-form-item label="下一环节：">
            <el-input disabled :value="nextStep"></el-input>
          </el-form-item>
          <el-form-item
            v-if="showUser && !preUserName"
            label="处理人："
            prop="userId"
          >
            <el-select
              style="width: 100%"
              v-model="subForm.userId"
              placeholder="请选择"
            >
              <el-option
                v-for="item in tempNextUsers"
                :key="item.nextUserId"
                :label="item.nextUserName"
                :value="item.nextUserId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="preUserName" label="处理人：">
            <el-input disabled :value="preUserName"></el-input>
          </el-form-item>
          <!-- <el-form-item label="处理意见：">
            <el-input
              placeholder="请输入处理意见"
              v-model="subForm.option"
              type="textarea"
              row="3"
            ></el-input>
          </el-form-item> -->
          <in-common-use v-model="subForm.option"></in-common-use>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import inCommonUse from "./inCommonUse";
import { validateAuthorize, fininshFormTask } from "@/api/workflow";
export default {
  components: {inCommonUse},
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    preUserName: [String],
    isBusiness: {
      type: Boolean,
      default: false,
    },
    // 下一步处理人Array
    nextUsers: {
      type: Array,
      default: () => {
        return [];
      },
    },
    title: {
      type: String,
      default: "下一步",
    },
    taskId: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      subShow: false,
      showUser: true,
      businessKey: "",
      subForm: {
        id: "",
        userId: "",
      },
      nextStep: "",
      url: "",
      obj: {},
      afRules: {
        userId: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "change",
          },
        ],
      },
      tempNextUsers:[]
    };
  },
  created() {
    // this.init();
  },
  watch: {
    nextUsers(){
      this.tempNextUsers = this.nextUsers;
    }
  },
  methods: {
    open(url, obj) {
      this.subShow = true;
      this.url = url;
      this.obj = obj;
    },
    init(process) {
      debugger
      this.businessKey = process.businessKey;
      this.taskId = process.curtaskId;
      this.nextStep = process.nextStep || process.nextStepAndUser[0].nextStep;
      let nextUsers = process.nextStepAndUser[0].nextStepUser;
      if (nextUsers && nextUsers.length!=0) { 
        this.subForm.userId = nextUsers[0].nextUserId;
      }
      this.tempNextUsers = nextUsers;
      if (this.nextStep.indexOf("结束") > -1) {
        this.showUser = false;
      }
    },
    // 取消
    handleClose(code) {
      this.$emit("close", code);
    },

    submitForm() {
      this.$refs["afRules"].validate((valid) => {
        if (valid) {
          let param = {
            businessKey: this.businessKey,
          };
          // 权限校验
          // validateAuthorize(param).then((res) => {
          //   let authorizeFlag = res.data.flag;
          //   if (authorizeFlag != 2) {
          //     this.msgError("当前工单已处理，请刷新页面");
          //     return;
          //   } else {
          //     this.sub();
          //   }
          // });
          this.sub();
        }
      });
    },

    sub() {
      let fm = new FormData();
      fm.append("taskId", this.taskId);
      fm.append("userId", this.$store.getters.name);

      fm.append(
        "TaskVariables",
        JSON.stringify({
          nextDealUser: this.subForm.userId,
          message: "ttt重新提交",
          btnMessage: this.title,
          option: this.subForm.option,
        })
      );
      if (this.isBusiness) {
        this.$emit("handleData", {
          taskId: this.taskId,
          nextDealUser: this.subForm.userId,
          nextStep: this.nextStep,
          btnMessage: this.title,
          option: this.subForm.option,
          message: "同意",
        });
        return;
      }
      const loading = this.openLoading();
      fininshFormTask(fm).then((res) => {
        loading.close()
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
