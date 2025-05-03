<template>
  <div>
    <!-- 转办 -->
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
          :model="todoForm"
          :rules="rulesForm"
          ref="refForm"
          label-width="100px"
        >
          <el-form-item prop="nextUserId" label="处理人:">
            <el-select
              style="width: 100%"
              v-model="todoForm.nextUserId"
              filterable
              remote
              value-key="userId"
              reserve-keyword
              placeholder="请输入处理人"
              :remote-method="remoteMethod"
              :loading="loading"
            >
              <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="
                  item.username +
                  '-' +
                  item.userId +
                  '-' +
                  item.company +
                  '/' +
                  item.dpname
                "
                :value="item.userId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="处理意见:">
            <el-input
              v-model="todoForm.option"
              type="textarea"
              row="3"
            ></el-input>
          </el-form-item> -->
          <in-common-use v-model="todoForm.option"></in-common-use>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="todo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import inCommonUse from "./inCommonUse";
import { accidentDeal } from "@/api/workflow";
import { selectLiaisonsData } from "@/api/xuncha/XunchaLiaisonsManage";
import { getUserInfo } from "@/api/talent/dept";
export default {
  components: {inCommonUse},
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    //获取下一步处理人接口地址
    url: {
      type: String,
      default: "",
    },
    //实例id
    todoId: {
      type: String,
      default: "",
    },
    taskId: {
      type: String,
      default: "",
    },
    //1,搜索公司里的人,2.搜索部门里的人,默认搜全部
    type: {
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
      loading: false,
      todoShow: false,
      applyShow: false,
      userList: [], //转办人
      nextUsers: [], //下一步处理人
      allFlowStep: [], //审核记录
      todoInfoId: "",
      todoForm: {
        nextUserId: null,
        option: "",
      },
      rulesForm: {
        nextUserId: [
          {
            required: true,
            message: "请选择转办人",
            trigger: "blur",
          },
        ],
      },
      applyForm: {
        nextUserObj: {},
        nextStepObj: {},
      },
      afRules: {
        nextUserObj: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "change",
          },
        ],
        nextStepObj: [
          {
            required: true,
            message: "请选择下一步处理节点",
            trigger: "change",
          },
        ],
      },
      tfRules: {
        nextUserId: [
          {
            required: true,
            message: "请选择转办人",
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
    handleClose() {
      // this.dialogVisible=false;
      this.$emit("close");
    },
    handleChange() {
      this.applyForm.nextUserObj = {};
    },
    handleSelect(value) {
      this.todoForm.nextUserId = value.userId;
      this.todoForm.nextUserName = value.username;
    },

    init(process) {
      if (!process.todoId) {
        this.$message.error("参数异常");
      }
      this.todoId = process.todoId;
    },
    //转办
    todo() {
      this.$refs.refForm.validate((valid) => {
        if (valid) {
          let tempObj = {
            nextDealUser: this.todoForm.nextUserId,
            option: this.todoForm.option,
            message: "转办",
            btnMessage: this.title,
          };
          if (this.isBusiness) {
            this.$emit("handleData", tempObj);
            return;
          }
          let fm = new FormData();
          // fm.append("todoId", this.todoId);
          // fm.append("dealUser", this.todoForm.nextUserId);
          // let param = {
          //   taskId:this.taskId,
          //   dealUser: this.todoForm.nextUserId
          // }
          fm.append("taskId", this.taskId);
          fm.append("TaskVariables", JSON.stringify(tempObj));
          const loading = this.openLoading();
          accidentDeal(fm).then((res) => {
            if (res.code == 200) {
              loading.close();
              this.$message.success("转办成功");
              setTimeout(() => {
                this.$emit("close", 200);
                location.reload();
              }, 500);
            }
          });
        } else {
          return false;
        }
      });
    },
    //审核
    apply() {
      this.$emit("apply", { ...this.applyForm, taskId: this.taskId });
    },
    getNextDealUser() {},
    //搜索用户下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          if (this.type) {
            getUserInfo({
              userName: query,
              type: this.type,
            }).then((res) => {
              this.userList = res.data.map((item) => {
                item.company = item.companyName;
                item.dpname = item.departmentName;
                item.username = item.userName;
                return item;
              });
              this.loading = false;
            });
          } else {
            selectLiaisonsData(query).then((res) => {
              this.userList = res.data;
              this.loading = false;
            });
          }
        }, 200);
      } else {
        this.userList = [];
      }
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
