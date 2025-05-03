<template>
  <div>
    <!-- 查看人员抽调_巡察任务抽调信息 对话框 -->

    <div class="dialog-context">
      <div class="dialog-title">
        <span class="line"></span><span>原巡察员信息</span>
      </div>
      <div class="context-row">
        <el-form
          :disabled="roleType == 0"
          ref="queryform"
          :rules="rules"
          :model="form"
          label-width="100px"
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名：" prop="personId">
                <el-input v-if="roleType == 0" v-model="form.personNameAnd">
                </el-input>
                <el-select
                  v-else
                  style="width: 100%"
                  v-model="form.personId"
                  filterable
                  remote
                  value-key="personnelId"
                  reserve-keyword
                  placeholder="请输入"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  clearable
                  @change="handleSelect"
                >
                  <el-option
                    v-for="item in userList"
                    :key="item.personnelId"
                    :label="
                      item.personnelName +
                      '-' +
                      item.personnelId +
                      '-' +
                      item.company +
                      '/' +
                      item.department
                    "
                    :value="item.personnelId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-input
                  v-model="form.xunchaYear"
                  style="width: 100%"
                  disabled
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="巡察批次:">
                <el-input
                  v-model="form.piciName"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所在巡察组：" prop="groupName">
                <el-input
                  v-model="form.groupName"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="专家领域：">
                <el-input
                  v-model="form.expertType"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="组内标签：">
                <el-input
                  v-model="form.labelValue"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <div
            class="dialog-title"
            style="
              justify-content: space-between;
              margin-bottom: 20px;
              margin-left: -40px;
            "
          >
            <div><span class="line"></span><span>申请替换为</span></div>
          </div>

          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="姓名：" prop="replacePersonId">
                <el-select
                  :disabled="!form.personId"
                  style="width: 100%"
                  v-model="form.replacePersonId"
                  filterable
                  remote
                  value-key="userId"
                  reserve-keyword
                  :placeholder="form.personId ? '请输入' : '请先选择原巡察员'"
                  :remote-method="remoteReplaceMethod"
                  :loading="loading"
                  clearable
                  @change="handleSelectReplace"
                >
                  <el-option
                    v-for="item in replaceUserList"
                    :key="item.userId"
                    :label="
                      item.name +
                      '-' +
                      item.userId +
                      '-' +
                      item.companyName +
                      '/' +
                      item.deptName
                    "
                    :value="item.userId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="专家领域：" prop="reType">
                <el-input disabled v-model="form.replaceExpertType" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="组内标签：">
                <el-input
                  v-model="form.replaceLabelValue"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="更替原因：" prop="replaceReason">
                <el-input
                  v-model="form.replaceReason"
                  type="textarea"
                  :rows="4"
                  style="width: 100%"
                  placeholder="请输入内容"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="tips">
          <span style="color: #ec832f">
            温馨提示： 如果申请替换的人员不在人才库中，请先申请【零星入库】
          </span>
        </div>
      </div>
    </div>
    <deep-btn
      v-bind="$attrs"
      ref="deepBtnRef"
      isBusiness
      :preUserName="process.preUserName"
      :businessKey="id"
      @initProcess="initProcess"
      @handleData="onFininshGroupToExamine"
      @close="handleClose"
    >
      <!-- 发起默认按钮，由 businessKey 控制，为空的话显示下面的默认按钮 -->
      <el-button type="primary" @click="submitBtn">提 交</el-button>
    </deep-btn>
    <!-- 申报页面的提交页面 -->
    <submit-process
      title="提交"
      processKey="task_group_person_replace"
      stepName="地市公司分管党办领导"
      :dialogVisible="submitProcessShow"
      @close="submitProcessShow = false"
      @complete="saveFromData"
    >
    </submit-process>
  </div>
</template>

<script>
import {
  fininshGroupToExamine,
  getTaskGroupPersonChangeApply,
  addTaskGroupPersonChangeApply,
} from "@/api/xuncha/replaceManage/taskGroupPersonChangeApply";
import { getGroupPersonInfoReplaceList } from "@/api/xuncha/transferManage/taskGroupPersonInfo";
import { getInLibraryUserlist } from "@/api/talent/info.js";
import deepBtn from "@/components/WorkFlow/deepBtn";
import SubmitProcess from "@/components/WorkFlow/submitProcess";
const examineStatusArray = [
  "",
  { spet: "地市公司党办主任", outLine: "退回党办主任" },
  { spet: "地市公司分管党办领导", outLine: "退回分管党办领导" },
  { spet: "巡察办领导", outLine: "" },
  { spet: "结束", outLine: "" },
];
export default {
  props: {
    id: {
      type: String,
      default: "",
    },
    dialogroleType: {
      type: Number,
      default: 1,
    },

    disabled: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    deepBtn,
    SubmitProcess,
  },
  data() {
    return {
      // 表单参数
      form: {
        applyId: null,
        lunciId: null,
        piciName: null,
        xunchaYear: null,
        groupName: null,
        expertType: null,
        labelValue: null,
        labelKey: null,
        personName: null,
        personId: "",
        replacePersonName: null,
        replaceExpertType: null,
        replaceLabelValue: null,
      },
      roleType: 0,
      process: {},
      loading: false,
      // 申请提交页面
      submitProcessShow: false,
      groupRules: [],
      userList: [],
      replaceUserList: [],

      // 表单校验
      rules: {
        personId: [
          { required: true, message: "人员不能为空", trigger: "change" },
        ],
        replacePersonId: [
          { required: true, message: "替换人员不能为空", trigger: "change" },
        ],
        replaceReason: [
          { required: true, message: "更替原因不能为空", trigger: "blur" },
        ],
      },
      CompanyAll: [],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.handleOpen();
  },
  methods: {
    handleOpen() {
      console.log(this.id);
      this.roleType = this.dialogroleType;
      if (this.id) {
        getTaskGroupPersonChangeApply(this.id).then((res) => {
          this.remoteMethod(res.data.personName);
          this.remoteReplaceMethod(res.data.replacePersonName);
          this.form = res.data;
          this.form.personNameAnd = `${res.data.personName}-${res.data.personId}`;
        });
      }
    },
    // 初始化流程信息
    initProcess(res) {
      // 区分暂存和流程页面，是否禁用表单
      if (res && res.curStepName != "地市公司党办主任") {
        this.roleType = 0;
      } else {
        this.roleType = 1;
      }
      this.process = res;

      let tempObj = {};
      if (res.allFlowStep.length) {
        for (let i = 1; i < res.allFlowStep.length; i++) {
          let item = res.allFlowStep[i];
          if (item.varialbes && item.varialbes.message == "转办") {
          } else {
            tempObj = item;
            break;
          }
        }
        if (res.taskDefinitionKey == "apply") {
          res.preUserName = tempObj.todoUserLoginId;
          res.preUserId = tempObj.historicTaskInstance.assignee;
        }
      }
      // if (value && this.caseType === 4) {
      //   this.formDisabled = value.taskDefinitionKey !== "user01";
      // }
    },
    remoteReplaceMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          let params = {
            name: query,
            instanceId: this.form.piciId,
          };
          getInLibraryUserlist(params).then((res) => {
            this.replaceUserList = res.data;
          });
        }, 200);
      } else {
        this.replaceUserList = [];
      }
    },
    // 替换人员change
    handleSelectReplace(value) {
      const reUser = this.replaceUserList.find((i) => i.userId == value);
      this.form.replacePersonName = reUser.name;
      this.form.replaceLabelValue = reUser.label.split(",")[0];

      console.log(reUser.labelKey.split(","));
      this.form.replaceLabelKey = reUser.labelKey.split(",")[0];
      this.form.replaceExpertType = reUser.filedTypeName;
    },
    handleSelect(value) {
      console.log(value);
      const user = this.userList.find((i) => i.personnelId == value);
      this.form.personName = user.personnelName;
      this.form.piciName = user.piciName;
      this.form.groupName = user.groupName;
      this.form.groupId = user.groupId;
      this.form.expertType = user.fieldKey;
      this.form.labelValue = user.labelValue;
      this.form.labelKey = user.labelKey;
      this.form.piciId = user.piciId;
      this.form.xunchaYear = user.xunchaYear;

      // this.selectedPerson = value;
    },
    //搜索用户下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          getGroupPersonInfoReplaceList({ personnelName: query }).then(
            (res) => {
              this.userList = res.data;
            }
          );
        }, 200);
      } else {
        this.userList = [];
      }
    },
    // 申请提交页面中点击发送
    saveFromData(value) {
      let { nickName, companyName } = this.$store.getters.userInfo;
      let params = {
        examineStatus: 1,
        nextUserId: value.userName,
        option: JSON.parse(value.variables).option,
        title: `${nickName}-${companyName}发起的人员更替申请`,
        ...this.form,
      };
      const loading = this.openLoading();
      addTaskGroupPersonChangeApply(params).then((res) => {
        loading.close();
        this.handleClose(200);
        this.msgSuccess(res.msg);
        this.submitProcessShow = false;
      });
    },
    // 待办工作流
    onFininshGroupToExamine(res) {
      console.log(res);
      // 审核通过，到下一步
      let params = null;

      if (res.message == "同意") {
        let examineStatus = examineStatusArray.findIndex(
          (i) => i.spet == this.process.curStepName
        );
        params = {
          ...this.form,
          message: "同意",
          taskId: res.taskId,
          nextUserId: res.nextDealUser,
          option: res.option,
          examineStatus,
        };
        if (this.process.taskDefinitionKey == "apply") {
          params.nextUserId = this.process.preUserId;
        }
        console.log(params);
      }
      // 退回
      else {
        let examineStatus = examineStatusArray.findIndex(
          (i) => i.outLine == res.nextStep
        );
        params = {
          ...this.form,
          message: res.nextStep,
          taskId: res.taskId,
          nextUserId: res.nextDealUser,
          option: res.option,
          examineStatus,
        };
      }
      debugger;
      const loading = this.openLoading();
      fininshGroupToExamine(params).then((res) => {
        loading.close();
        this.msgSuccess(res.msg);
        this.$refs.deepBtnRef.closeAll();
        setTimeout(() => {
          location.reload();
        }, 500);
      });
    },
    // 点击提交按钮
    submitBtn() {
      // 对表单进行校验
      this.$refs["queryform"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.submitProcessShow = true;
          });
        }
      });
    },
    handleClose(e) {
      this.$emit("close", e);
    },
  },
};
</script>

<style lang="scss" scoped>
.tips {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  color: rgba(23, 35, 61, 0.85);
  background: #fff;
  padding: 15px;
  margin-top: 15px;
  border-radius: 2px 2px 0px 0px;
}
.dialog-context {
  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;

    .btn {
      float: right;
    }

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }
  .result_box {
    margin-left: 28px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .btn_box {
    display: flex;
    justify-content: center;
  }
  .context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;

    .operation {
      position: absolute;
      right: 0;
      left: 0;
      top: -30px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 40px;
    }
  }
}
</style>