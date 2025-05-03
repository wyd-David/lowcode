<template>
  <div>
    <el-dialog
      :title="type == '1' ? '人才推荐审批' : '查看'"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="close"
      :close-on-click-modal="false"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>待办信息</span>
        </div>
        <div class="context-row">
          <el-form disabled label-width="120px">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="业务单号:">
                  <el-input
                    style="width: 100%"
                    :value="form.orderNumber || obj.orderNumber"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="标题:">
                  <el-input
                    style="width: 100%"
                    :value="obj.title"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="下发机构:">
                  <el-input
                    style="width: 100%"
                    :value="form.companyName || obj.companyName"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="收集截止时间:">
                  <el-input
                    style="width: 100%"
                    :value="obj.createdTime || obj.collectionEndDate"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="dialog-title" v-if="type != '3'">
          <span class="line"></span><span>需求名额指标</span>
        </div>
        <div class="context-row" v-if="type != '3'">
          <el-table
            :header-cell-style="{ 'text-align': 'center' }"
            :cell-style="{ 'text-align': 'center' }"
            :data="form.fieldList"
          >
            <el-table-column
              align="center"
              label="序号"
              type="index"
            ></el-table-column>
            <el-table-column align="center" label="单位">
              <template slot-scope="scope">
                {{ form.companyName }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="部门">
              <template slot-scope="scope">
                {{ form.deptName }}
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="fieldTypeName"
              label="专家领域"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="personNumber"
              label="指标人数(人)"
            ></el-table-column>
          </el-table>
          <div style="text-align: right">
            总计:<span style="color: #b1261d"> {{ total }} </span>人
          </div>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>实际推荐人才</span>
        </div>
        <div class="context-row">
          <el-table :data="form.deptRecommendList">
            <el-table-column
              align="center"
              label="序号"
              type="index"
            ></el-table-column>
            <el-table-column align="center" prop="userName" label="姓名">
              <template slot-scope="scope"
                ><span
                  @click="showUser(scope.row)"
                  style="color: red; cursor: pointer"
                  >{{ scope.row.userName }}</span
                ></template
              >
            </el-table-column>
            <el-table-column
              align="center"
              prop="fieldTypeName"
              label="专家领域"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="deptName"
              label="部门"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="postName"
              label="职务"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="postLevel"
              label="职级"
            ></el-table-column>
          </el-table>
        </div>
      </div>

      <all-flow-step
        v-if="obj.id && obj.type != 3"
        :allFlowStep="process.allFlowStep"
      ></all-flow-step>

      <span slot="footer" class="dialog-footer" v-if="type == '1'">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" :disabled="turnClick" @click="turnbackOper"
          >退 回</el-button
        >
        <el-button type="primary" @click="submit">同 意</el-button>
      </span>
    </el-dialog>
    <turnback-process
      title="退回"
      ref="turnbackRef"
      isBusiness
      :taskId="process.curtaskId"
      :preUserName="process.preUserName"
      @handleData="returnSubmit"
      :dialogVisible="turnbackProcessShow"
      @close="turnbackClose"
    ></turnback-process>

    <collect-form
      :dialogVisible="collectOpen"
      :id="userId"
      @close="close(1)"
    ></collect-form>
  </div>
</template>
<script>
import { getDeptById, finishFormTask } from "@/api/talent/dept";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { approve, getRecommendList } from "@/api/talent/recommendSchedule";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import TurnbackProcess from "@/components/WorkFlow/turnbackProcess";
import collectForm from "../collect/collectForm";
export default {
  components: {
    TurnbackProcess,
    collectForm,
    AllFlowStep,
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    obj: {
      type: Object,
      default: () => {},
    },
    type: {
      type: String,
      default: "1",
    },
  },
  data() {
    return {
      turnClick: true,
      collectOpen: false,
      userId: "",
      turnbackProcessShow: false,
      form: {
        deptRecommendList: [],
      },
      list: {},
      process: {},
    };
  },
  computed: {
    total() {
      let count = 0;
      if (this.form.fieldList) {
        this.form.fieldList.forEach((res) => {
          count += res.personNumber;
        });
      }
      return count;
    },
  },
  created() {
    this.init();
  },
  methods: {
    showUser(row) {
      this.userId = row.userId;
      this.collectOpen = true;
    },
    handleData(e) {},
    submit() {
      this.$confirm("确定要提交吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          approve({
            id: this.obj.id,
          }).then((res) => {
            this.$message.success("审批成功");
            this.$emit("close", 200);
          });
        })
        .catch(() => {});
    },
    returnSubmit(e) {
      const loading = this.openLoading();
      let preUserId = this.process.preUserId;
      finishFormTask(this.process.curtaskId, {
        colletionId: this.form.colletionId,
        id: this.form.id,
        deptRecommendList: this.form.deptRecommendList,
        variables: JSON.stringify({
          message: e.nextStep,
          btnMessage: e.btnMessage,
          nextDealUser: preUserId,
          option: e.option,
        }),
      }).then((res) => {
        if (res.code == 200) {
          this.$message.success("提交成功!");
          loading.close();
          this.$emit("close", 200);
        }
      });
      this.turnbackProcessShow = false;
    },
    turnbackOper() {
      this.turnbackProcessShow = true;
      this.$refs.turnbackRef.init(this.process);
    },
    getDetail() {
      //type为3的时候是点击分公司人才推荐页面的人数所展示的
      if (this.obj.type == 3) {
        getRecommendList({
          colletionId: this.obj.id,
          deptId: this.obj.deptId,
          fieldType: this.obj.fieldType,
        }).then((res) => {
          this.loading = false;
          this.form.deptRecommendList = res.data;
        });
        return;
      }
      getDeptById(this.obj.id).then((res) => {
        this.form = res.data;
        fetchWorkFlowInfo(
          this.form.orderNumber,
          null,
          (res) => {
            if (
              res.nextStepAndUser[0] &&
              res.nextStepAndUser[0].nextStepUser.length == 0
            ) {
              let tempObj = res.allFlowStep[res.allFlowStep.length - 2];
              res.preUserName = tempObj.todoUserLoginId;
              res.preUserId = tempObj.historicTaskInstance.assignee;
            }
            this.process = res;
            this.turnClick = false;
          },
          1
        );
      });
    },
    init() {
      this.getDetail();
    },
    turnbackClose() {
      this.turnbackProcessShow = false;
    },
    close(t) {
      if (typeof e == "number") {
        this.collectOpen = false;
        return;
      }
      this.$emit("close");
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-radio-group {
  display: flex;
  height: 36px;
  align-items: center;
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
    position: relative;
    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
    .operation {
      position: absolute;
      right: 0;
      margin: 10px 0px;
      text-align: right;
      padding-right: 30px;
      z-index: 9;
    }
  }

  .context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;
  }
}
</style>