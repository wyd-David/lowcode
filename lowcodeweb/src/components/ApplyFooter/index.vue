<template>
  <div>
    <div class="dialog-title">
      <span class="line"></span><span>流程信息</span>
    </div>
    <div class="context-row">
      <el-table :data="allFlowStep">
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="步骤" prop="name"></el-table-column>
        <el-table-column
          label="处理人"
          prop="todoUserLoginId"
        ></el-table-column>
        <el-table-column label="处理时间" prop="startTime"></el-table-column>
        <el-table-column label="处理意见" prop="option"></el-table-column>
      </el-table>
    </div>
    <div v-if="type != '1'" slot="footer" class="dialog-footer">
    <slot></slot>
      <el-button v-if="type == '3'" @click="todoShow = true">转 办</el-button>
      <el-button type="primary" @click="applyShow = true">审 核</el-button>
    </div>
    <!-- 转办 -->
    <el-dialog
      title="转办"
      modal-append-to-body
      append-to-body
      :visible.sync="todoShow"
      width="600px"
    >
      <div class="dialog-context">
        <el-form label-width="100px">
          <el-form-item label="转办人:" :model="todoForm" :rules="tfRules" ref="tfRules">
            <el-select
              style="width: 100%"
              v-model="todoForm.nextUserId"
              filterable
              remote
              value-key="userId"
              reserve-keyword
              placeholder="请输入联络人"
              :remote-method="remoteMethod"
              :loading="loading"
              @change="handleSelect"
            >
              <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.userName + '-' + item.userId + '-' +item.companyName+'/'+ item.departmentName"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="todoShow = false">取 消</el-button>
        <el-button type="primary" @click="todo">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 审核 -->
    <el-dialog
      title="审核"
      modal-append-to-body
      append-to-body
      :visible.sync="applyShow"
      width="600px"
    >
      <div class="dialog-context">
        <el-form label-width="140px" :model="applyForm" :rules="afRules" ref="afRules">
          <el-form-item label="下一步处理节点:">
            <el-select
              value-key="outLineId"
              style="width: 100%"
              @change="handleChange"
              v-model="applyForm.nextStepObj"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in nextUsers"
                :key="item.nextUserId + index"
                :label="item.nextStep"
                :value="item"
                >{{ item.nextStep }}</el-option
              >
            </el-select>
          </el-form-item>
          <el-form-item label="下一步处理人:">
            <el-select
              value-key="id"
              style="width: 100%"
              v-model="applyForm.nextUserObj"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in applyForm.nextStepObj.children"
                :key="item.id + index"
                :label="item.name"
                :value="item"
                >{{ item.name }}</el-option
              >
            </el-select>
          </el-form-item>
          <el-form-item label="审核意见:">
            <el-input v-model="applyForm.option"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="applyShow = false">取 消</el-button>
        <el-button type="primary" @click="apply">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getAllFlowStep, getOneTaskInfo,accidentDeal,getUserInfo } from "@/api/talent/collection";
import { selectLiaisonsData } from "@/api/xuncha/XunchaLiaisonsManage";
export default {
  props: {
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
    //1->查看,2->查看,审核,3->查看,审核,转办,
    type: {
      type: String,
      default: "1",
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
      taskId: "", //任务id
      todoInfoId:"",
      todoForm: {},
      applyForm: {
        nextUserObj: {},
        nextStepObj:{}
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
    this.init();
  },
  methods: {
    handleChange(){
      this.applyForm.nextUserObj = {};
    },
    handleSelect(value) {
      this.todoForm.nextUserId = value.userId;
      this.todoForm.nextUserName = value.username;
    },
    init() {
      this.getAllFlow();
      this.todoInfoId = this.$route.query.todoInfoId;
    },
    //转办
    todo() {
      // this.$emit("todo", {...this.todoForm,taskId:this.taskId});
      let fm = new FormData();
      fm.append("todoId", this.todoInfoId);
      fm.append("dealUser","xuxing2");
      accidentDeal(fm).then(res => {
        if(res.code == 200){
          this.$message.success("转办成功");
        }
      });
    },
    //审核
    apply() {
      this.$emit("apply", {...this.applyForm,taskId:this.taskId});
    },
    getNextDealUser() {},
    //搜索用户下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          getUserInfo(query).then((res) => {
            this.loading = false;
            this.userList = res.data;
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    //流程日志
    getAllFlow() {
      let fd = new FormData();
      fd.append("flag", 1);
      fd.append("Id", this.todoId);
      let that = this;
      getAllFlowStep(fd).then((res) => {
        let data = res.data || [];
        that.allFlowStep = data.map((item) => {
          return {
            name: item.historicTaskInstance
              ? item.historicTaskInstance.name
              : "",
            todoUserLoginId: item.todoUserLoginId || "",
            startTime: item.startDateTime ? item.startDateTime : "",
            endTime: item.endDateTime ? item.endDateTime : "",
            message: item.varialbes ? item.varialbes.message : "",
            option: item.varialbes ? item.varialbes.option : "",
            nextStep: item.nextStep || "",
            nextTodoUser: item.nextTodoUser || "",
          };
        });
        that.curStepUser = that.allFlowStep[0].todoUserLoginId || "";
        that.curStepName = that.allFlowStep[0].name || "";
        if (
          res.data[0] &&
          res.data[0].historicTaskInstance &&
          res.data[0].historicTaskInstance.deleteReason !==
            "completed"
        )
          that.getOneTaskInfo(res.data[0].historicTaskInstance.id);
        that.taskId = res.data[0].historicTaskInstance.id;
      });
    },
    //流程按钮
    getOneTaskInfo(taskId) {
      let fd = new FormData();
      fd.append("taskId", taskId);
      getOneTaskInfo(fd).then((res) => {
        if (res.code == 200) {
          for (let i = 0; i < res.data.nextStepAndUser.length; i++) {
            let tempObj = res.data.nextStepAndUser[i];
            if(tempObj.nextUserId != null){
              let ids = tempObj.nextUserId.split(",");
              let names = tempObj.nextUserName.split(",");
              tempObj.children = [];
              for (let j = 0; j < ids.length; j++) {
                tempObj.children.push({
                  id: ids[j],
                  name: names[j],
                });
              }
            }
          }

          this.nextUsers = res.data.nextStepAndUser;
          this.applyForm.nextStepObj = res.data.nextStepAndUser[0];
          this.currentDealUserId = res.data.currentDealUserId;
          this.applyUser =
            res.data.taskInfoBean.excutionVariables.nextDealUser || "";
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
