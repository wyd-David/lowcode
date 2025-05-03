<template>
  <div class="app-main">
    <todo-bpm-header></todo-bpm-header>
    <div class="dialog-title">
      <span class="line"></span><span>待办信息</span>
    </div>
    <div class="context-row">
      <el-form disabled label-width="100px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="业务单号:">
              <el-input :value="form.orderNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人:">
              <el-input :value="form.createUserName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="申请时间:">
              <el-input :value="form.createdTime"></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
                    <el-form-item label="申请人:">
                        <el-input :value="form.createdBy"></el-input>
                    </el-form-item>
                </el-col> -->
        </el-row>
      </el-form>
    </div>
    <div v-if="form.processStatus != '99'" class="dialog-title">
      <span class="line"></span><span>标签设置信息</span>
    </div>
    <div v-if="form.processStatus != '99'" class="context-row">
      <el-table :data="tableData">
        <el-table-column
          label="序号"
          type="index"
          align="center"
        ></el-table-column>
        <el-table-column
          label="姓名"
          prop="name"
          align="center"
        ></el-table-column>
        <el-table-column
          label="标签"
          prop="labelName"
          align="center"
        >
        <template slot-scope="scope">
          <div class="label-box">
              <span
                class="label-item-t"
                v-for="(item, index) in scope.row.labels"
                :key="index"
                >{{ item.dataName }}
                </span>
            </div>
        </template>
        </el-table-column>
        <el-table-column
          label="所在单位"
          prop="companyName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="部门"
          prop="deptName"
          align="center"
        ></el-table-column>
        <el-table-column
          show-overflow-tooltip
          width="200px"
          label="职务"
          prop="postName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="职级"
          prop="postLevel"
          align="center"
        ></el-table-column>
      </el-table>
    </div>

    <apply-form
      v-if="form.processStatus == '99'"
      ref="refForm"
      :id="id"
      :type="2"
      isTodo
    ></apply-form>

    <deep-btn
      :businessKey="id"
      type="3"
      isTodo
      @initProcess="initProcess"
      isBusiness
      @handleData="handleAF"
      @close="handleClose"
    >
      <span slot="btns" style="margin-right: 10px">
        <el-button
          v-if="process.authorizeFlag == 2 && form.processStatus != 99"
          type="primary"
          @click="turnbackOper"
          >退 回</el-button
        >
      </span>
    </deep-btn>

    <turnback-process
      ref="turnbackRef"
      isBusiness
      title="退回"
      :taskId="process.curtaskId"
      :preUserName="process.preUserName"
      @handleData="returnSubmit"
      :dialogVisible="turnbackProcessShow"
      @close="handleClose"
    ></turnback-process>
  </div>
</template>
<script>
import applyForm from "@/views/talent/config/applyForm";
import deepBtn from "@/components/WorkFlow/deepBtn";
import turnbackProcess from "@/components/WorkFlow/turnbackProcess";
import { getConfig, queryDictList, fininshFormTask } from "@/api/talent/config";
export default {
  components: { deepBtn, turnbackProcess, applyForm },
  data() {
    return {
      id: "",
      todoId: "",
      tableData: [],
      turnbackProcessShow: false,
      comoleteProcess: false,
      process: {},
      labels: [],
      form: {},
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.todoId = this.$route.query.todoInfoId;
    //查询标签列表
    queryDictList({
      dictType: "xuncha_label",
    })
      .then((res) => {
        this.labels = res.data;
        this.getDetail();
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    getDetail() {
      this.loading = true;
      getConfig(this.id).then((res) => {
        if (res.code == 200) {
          this.form = res.data;
          let resultArr = res.data.labelResultList;
          let tempArr = JSON.parse(JSON.stringify(res.data.labelResultList));
          let obj = {};
          //去重
          tempArr = tempArr.reduce(function (item, next) {
            obj[next.userId]
              ? ""
              : (obj[next.userId] = true && item.push(next));
            return item;
          }, []);
          tempArr.forEach((item) => {
            item.labelIds = "";
            resultArr.forEach((j) => {
              if (item.userId == j.userId) {
                item.labelIds += j.labelId + ",";
              }
            });
            item.labelIds = item.labelIds.substring(
              0,
              item.labelIds.length - 1
            );
            item.labels = this.parseLabel(item.labelIds);
            item.labelName = item.labels.map((k) => k.dataName).join(" , ");
            item.name = item.userName;
          });
          this.tableData = tempArr;
          this.loading = false;
        }
      });
    },
    //解析以逗号隔开的标签
    parseLabel(label) {
      let tempArr = [];
      if (label) {
        label.split(",").map((item) => {
          tempArr.push({
            dataCode: item,
            dataName: this.labels.find((res) => res.dataCode === item).dataName,
          });
        });
        return tempArr;
      }
      return tempArr;
    },
    //审核回调
    handleAF(e) {
      let tableData = null;
      let tempList = null;
      if (this.$refs.refForm) {
        tableData = this.$refs.refForm.tableData;
        tempList = [];
        //封装数据
        tableData.forEach((res) => {
          res.labels.forEach((item) => {
            tempList.push({
              ...res,
              ...item,
              labelId: item.dataCode,
            });
          });
        });
      }
      const openLoading = this.openLoading();
      let obj = {
        taskId: this.process.curtaskId,
        id: this.form.id,
        processStatus: this.form.processStatus,
        labelApplyRelList: tempList,
        variables: JSON.stringify({
          btnMessage:e.btnMessage,
          message: e.nextStep,
          nextDealUser: e.nextDealUser,
          option: e.option,
        }),
      };
      fininshFormTask(obj).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("审批成功");
          openLoading.close();
          location.reload();
        }
      });
    },
    //退回回调
    returnSubmit(e) {
      const openLoading = this.openLoading();
      let obj = {
        taskId: this.process.curtaskId,
        id: this.form.id,
        variables: JSON.stringify({
          btnMessage:e.btnMessage,
          message: e.nextStep,
          nextDealUser: this.process.preUserId || e.nextDealUser,
          option: e.option,
        }),
      };
      fininshFormTask(obj).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("退回成功");
          openLoading.close();
          location.reload();
        }
      });
    },
    initProcess(res) {
      let tempObj = {};
      if (res.allFlowStep.length && res.backNextStepAndUser.length == 1) {
        for (let i = 1;i<res.allFlowStep.length;i++) {
          let item = res.allFlowStep[i];
          if (item.varialbes && item.varialbes.message == "转办") {
          } else { 
            tempObj = item;
            break;
          }
        }
        res.preUserName = tempObj.todoUserLoginId;
        res.preUserId = tempObj.historicTaskInstance.assignee;
      }
      this.process = res;
    },
    turnbackOper() {
      this.turnbackProcessShow = true;
      this.$refs.turnbackRef.init(this.process);
    },
    handleClose() {
      this.comoleteProcessShow = false;
      this.turnbackProcessShow = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.app-main {
  padding: 15px;
}
</style>
