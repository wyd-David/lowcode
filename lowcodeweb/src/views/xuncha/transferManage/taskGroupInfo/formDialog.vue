<template>
  <div>
    <!-- 查看人员抽调_巡察任务抽调信息 对话框 -->

    <div class="dialog-context">
      <div class="dialog-title">
        <span class="line"></span><span>巡察信息</span>
      </div>
      <el-form ref="queryform" :model="form" label-width="130px">
        <div class="context-row">
          <el-row :gutter="20">
            <el-col :span="22">
              <el-form-item label="业务单号" prop="groupNo">
                <el-input disabled v-model="form.groupNo" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="巡察组：" prop="groupName">
                <el-input disabled v-model="form.groupName" />
              </el-form-item>
            </el-col>
            <el-col :span="10" :offset="2">
              <el-form-item label="组长：" prop="groupLeaderName">
                <el-input disabled v-model="form.groupLeaderName" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-input disabled v-model="form.xunchaYear" />
              </el-form-item>
            </el-col>
            <el-col :span="10" :offset="2">
              <el-form-item label="巡察批次：" prop="xunchaPici">
                <el-input disabled v-model="form.xunchaPici" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item
                label="关联巡察任务："
                style="position: relative"
                prop="dates"
              >
                <el-input disabled v-model="form.taskName"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-button
                class="btn"
                size="mini"
                type="primary"
                style="margin-left: 10px; height: 30px"
                @click="isXunChaTaskInfo = true"
                >查看
              </el-button>
            </el-col>
            <el-col :span="10">
              <el-form-item label="计划巡察时段：" prop="dates">
                <el-input disabled v-model="form.xunchaTimeSolt" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="被巡察单位：" prop="companyIds">
                <el-input disabled v-model="form.company" />
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>

      <div class="dialog-title" style="justify-content: space-between">
        <div><span class="line"></span><span>组成员信息</span></div>
      </div>

      <div class="context-row">
        <el-table :data="groupUserList">
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column
            label="组内标签/职责"
            align="center"
            prop="labelValue"
          ></el-table-column>
          <el-table-column label="姓名" align="center" prop="userName">
            <template slot-scope="scope">
                <el-button type="text" @click="()=>{ userId = scope.row.userId;collectFormShow = true}">{{scope.row.userName}}</el-button>
              </template>
          </el-table-column>
          <template
            v-if="
              process.curStepName == '巡察办主管分组' &&
              process.authorizeFlag == 2
            "
          >
            <el-table-column label="替换成员" align="center" prop="newUser">
              <template slot-scope="scope">
                <el-select
                  v-model="scope.row.newUser"
                  placeholder="请选择"
                  clearable
                >
                  <el-option
                    v-for="item in scope.row.replaceList"
                    :key="item.personnelInfoId"
                    :label="item.personnelName"
                    :value="item.personnelId"
                  />
                </el-select>
              </template>
            </el-table-column>
          </template>
          <el-table-column
            label="所在单位"
            align="center"
            prop="companyName"
          ></el-table-column>
          <el-table-column
            label="部门"
            align="center"
            prop="dpName"
          ></el-table-column>
          <el-table-column
            label="专家领域"
            align="center"
            prop="fieldValue"
          ></el-table-column>
        </el-table>
      </div>
      <xun-cha-task-info
        ref="xunChaTaskInfo"
        :taskId="form.taskId"
        :disableForm="true"
        :dialogVisible="isXunChaTaskInfo"
        @close="isXunChaTaskInfo = false"
      />

      <turnback-process
        ref="turnbackRef"
        isBusiness
        :taskId="process.curtaskId"
        :preUserName="preTurnBackUserName"
        @handleData="returnSubmit"
        :dialogVisible="turnbackProcessShow"
        @close="close"
      ></turnback-process>
      <deep-btn
        ref="deepBtnRef"
        :isBusiness="true"
        v-bind="$attrs"
        :preUserName="precompleteUserName"
        @handleData="submitForm"
        @initProcess="getprocess"
        :businessKey="getBusinessKey()"
        @close="handleClose"
      >
        <!-- 发起默认按钮 -->
        <template>
          <!-- <el-button
            v-if="
              this.process.curStepName == '巡察组组长' &&
              this.process.authorizeFlag == 2
            "
            @click="turnbackOper"
            type="primary"
            >退回</el-button
          > -->
        </template>
      </deep-btn>
    </div>

    <collectForm
      :dialogVisible="collectFormShow"
      :id="userId"
      more
      @close="collectFormShow = false"
    ></collectForm>
  </div>
</template>

<script>
import {
  getTransferFieldInfo,
  listTransferFieldInfo,
  disableEnable,
} from "@/api/xuncha/transferManage/transferFieldInfo.js";
import {
  listTransferPersonnelInfo,
  exportTransferPersonnelInfo,
} from "@/api/xuncha/transferManage/transferPersonnelInfo.js";
import {
  getXunChaGroupSHInfo,
  fininshGroupToExamine,
} from "@/api/xuncha/transferManage/taskGroupInfo";
import XunChaTaskInfo from "@/views/xuncha/transferManage/groupPiciInfo/groupDialog/xunChaTaskInfo";
import deepBtn from "@/components/WorkFlow/deepBtn";
import TurnbackProcess from "@/components/WorkFlow/turnbackProcess";
import collectForm from "@/views/talent/collect/collectForm.vue";

export default {
  props: {
    isOpenQuery: {
      type: Boolean,
      default: true,
    },
    // 弹出层标题
    title: {
      type: String,
    },

    groupId: {
      type: String,
      default: "",
    },
    businessKey: {
      type: String,
      default: "",
    },
  },
  components: {
    deepBtn,
    XunChaTaskInfo,
    TurnbackProcess,
    collectForm
  },
  data() {
    return {
      isXunChaTaskInfo: false,
      turnbackProcessShow: false,
      collectFormShow: false,
      userId:"",
      process: {},
      preUserName: null,
      preTurnBackUserName: null,
      precompleteUserName: null,
      // 表单参数
      form: {
        year: null,
        piciName: null,
        dates: null,
        companyIds: null,
      },
      ruleTable: [],
      groupUserList: [],
      resultTable: [
        {
          tab: "123",
          profession: "123",
          name: "123",
          unit: "123",
          department: "党廉办",
        },
      ],
      tabList: [
        {
          id: 1,
          name: "主笔人",
        },
      ],
      informTable: [],
      professionList: [],
      xunchaYears: [],
      xunchaPici: [],
      CompanyAll: [],
      members: [],
    };
  },
  computed: {},

  mounted() {
    this.tabQuery();
  },
  methods: {
    handleOpen(groupId) {
      console.log(groupId);
      
    },
    getBusinessKey() {
      return this.groupId;
    },
    getprocess(process) {
      if (process.taskDefinitionKey) {
        let tempObj = process.allFlowStep[process.allFlowStep.length - 1];
        // console.log(tempObj);
        process.preUserName = tempObj.todoUserLoginId;
        process.preUserId = tempObj.historicTaskInstance.assignee;
        this.process = process;
        this.preTurnBackUserName = process.preUserName;
        if (process.curStepName == "巡察办主管分组") {
          tempObj = process.allFlowStep.find((i) => i.name == "巡察组组长");
          process.preUserName = tempObj.todoUserLoginId;
          process.preUserId = tempObj.historicTaskInstance.assignee;
          this.precompleteUserName = process.preUserName;
        }
      }

      getXunChaGroupSHInfo({ groupId: this.groupId || groupId }).then((res) => {
        this.form = res.data;
        this.groupUserList = res.data.groupUserList;
        console.log(this.process);
        if (
          this.process.curStepName == "巡察办主管分组" &&
          this.process.authorizeFlag == 2
        ) {
          this.groupUserList.forEach(async (item, index) => {
            let res = await this.replaceListQuery(
              item.labelValue,
              item.fieldValue,
              item.userId
            );
            item.replaceId = "";
            item.replaceList = res.rows;
            this.$set(
              this.groupUserList,
              index,
              JSON.parse(JSON.stringify(item))
            );
          });
        }
      });
      // console.log(this.preUserName);
    },
    // 保存
    onSave() {
      let param = this.ruleTable;
      exportTransferPersonnelInfo(param).then((res) => {});
    },
    replaceListQuery(labelName, fieldKey,userId) {
      let params = {
        instanceId: this.form.instanceId,
        labelName,
        fieldKey,
        userId,
        isGroupSh:1
      };
      return listTransferPersonnelInfo(params);
    },

    tabQuery() {
      getTransferFieldInfo().then((res) => {
        this.tabList = res;
      });
    },

    handleProfessionChange(val, rowObj) {
      this.professionNumQuery(val, rowObj);
    },
    // 专家人数查询
    professionNumQuery(param, target) {
      disableEnable(param).then((res) => {
        target.num = res;
      });
    },

    // 专业查询
    professionListQuery(param, target) {
      listTransferFieldInfo(param).then((res) => {
        target.professionList = res;
      });
    },
    deleteRule(index) {
      this.ruleTable.splice(index, 1);
    },
    transferInform() {},
    turnbackOper() {
      this.turnbackProcessShow = true;
      this.$refs.turnbackRef.init(this.process);
    },
    // 退回
    returnSubmit(e) {
      const loading = this.openLoading();
      let params = {
        message: e.nextStep,
        nextUserId: e.nextDealUser,
        taskId: e.taskId,
        content: e.option,
        groupId: this.groupId,
        examineStatus: this.form.examineStatus,
      };
      fininshGroupToExamine(params).then((res) => {
        if (res.code == 200) {
          loading.close();
          this.$message.success("操作成功!");
          setTimeout(() => {
            location.reload();
          }, 500);
        }
      });
    },
    // 确认提交
    submitForm(res) {
      console.log(res, this.form);
      if (res.message == "退回") {
        this.returnSubmit(res);
      } else {
        let flag = false;
        this.groupUserList.forEach(res => {
          if (this.groupUserList.filter(i => i.newUser && i.newUser == res.newUser).length > 1) { 
            flag = true;
          }
        });
        if (flag) { 
          this.msgError("请勿添加相同人员!");
          return;
        }
        let newUserId = this.groupUserList
          .map((i) => {
            if (i.newUser) {
              return i.newUser;
            } else {
              return " ";
            }
          })
          .join(",");
        let userId = this.groupUserList
          .map((i) => {
            return i.userId;
          })
          .join(",");
        let params = {
          newUserId,
          userId,
          taskId: res.taskId,
          content: res.option,
          groupId: this.groupId,
          nextUserId: res.nextDealUser,
          examineStatus: this.form.examineStatus,
        };
        if (this.process.curStepName == "巡察办主管分组") {
          params.nextUserId = this.process.preUserId;
        }
        params.message = "同意";

        fininshGroupToExamine(params).then((res) => {
          if (res.code == 200) {
            this.$message.success("提交成功");
            setTimeout(() => {
              this.$refs.deepBtnRef.applyShow = false;
              location.reload();
            }, 500);
          }
        });
      }
    },
    handleClose(e) {
      this.$emit("close", e);
    },
    close() {
      this.turnbackProcessShow = false;
      this.$emit("close");
    },
  },
};
</script>

<style lang="scss" scoped>
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
      position: absolute;
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
  .btn_box {
    display: flex;
    justify-content: center;
  }
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
</style>