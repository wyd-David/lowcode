<template>
  <div>
    <el-dialog
      :title="obj.title"
      :visible.sync="obj.open"
      :close-on-click-modal="false"
      width="80%"
      @open="handleOpen"
      :before-close="close"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>待办信息</span>
        </div>
        <div class="context-row">
          <el-form label-width="120px">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="名称:">
                  <el-input disabled :value="obj.todoTitle"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="关联收集任务:">
                  <el-input disabled :value="obj.todoId"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="收集年份:">
                  <el-input
                    disabled
                    style="width: 100%"
                    :value="obj.collectionEndDate"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <!-- <el-button style="margin-left: 20px" type="primary" size="small"
                  >待办详情</el-button
                > -->
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="dialog-title" v-if="obj.type != 3">
          <span class="line"></span><span>需求名额指标</span>
        </div>
        <div class="context-row" v-if="obj.type != 3">
          <el-table
            v-loading="loading"
            :data="prefecturalTableData"
            v-if="obj.companyType == '1'"
          >
            <el-table-column
              label="序号"
              align="center"
              width="55"
              type="index"
            ></el-table-column>
            <el-table-column label="单位" width="150" align="center">
              <template slot-scope="scope">
                {{ scope.row.companyName }}
              </template>
            </el-table-column>
            <el-table-column
              width="200px"
              align="center"
              label="推荐专家总人数"
            >
              <template slot-scope="scope">
                <span>{{ scope.row.expertTotals }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="150"
              :label="item.dictLabel"
              v-for="item in prefecturalDicts"
              :key="item.dictValue"
            >
              <template slot-scope="scope">
                {{ filterNumber(item, scope.row) }}
              </template>
            </el-table-column>
            <el-table-column
              width="200px"
              align="center"
              label="推荐专家总人数"
            >
              <template slot-scope="scope">
                <span>{{ scope.row.expertTotals }}</span>
              </template>
            </el-table-column>
          </el-table>

          <el-table :data="provinceTableData" v-else>
            <el-table-column
              label="序号"
              width="55px"
              type="index"
            ></el-table-column>
            <el-table-column label="单位" width="150px" align="center">
              <template slot-scope="scope">
                {{ scope.row.companyName }}
              </template>
            </el-table-column>
            <el-table-column
              width="200px"
              align="center"
              label="推荐专家总人数"
            >
              <template slot-scope="scope"></template>
            </el-table-column>
            <el-table-column
              align="center"
              :label="item.dictLabel"
              v-for="item in provinceDicts"
              :key="item.dictValue"
            >
              <template slot-scope="scope">
                {{ filterNumber(item, scope.row) }}
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>实际推荐人才</span>
        </div>
        <div class="context-row">
          <el-table v-loading="loading" :data="form.deptRecommendList">
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
        <all-flow-step
          v-if="obj.type != 3"
          :allFlowStep="process.allFlowStep"
        ></all-flow-step>
      </div>
      <span slot="footer" class="dialog-footer" v-if="obj.type == '1'">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="turnbackOper">退 回</el-button>
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
      @close="close"
    ></turnback-process>
    <collect-form
      :dialogVisible="collectOpen"
      :id="userId"
      @close="close(1)"
    ></collect-form>
  </div>
</template>
<script>
import {
  getRecommendProcessDetail,
  approveSchedule,
  getRecommendList,
} from "@/api/talent/recommendSchedule";
import { finishFormTask } from "@/api/talent/company";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import TurnbackProcess from "@/components/WorkFlow/turnbackProcess";
import { getDicts } from "@/api/system/dict/data";
import collectForm from "../collect/collectForm";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
export default {
  components: { collectForm, TurnbackProcess, AllFlowStep },
  props: {
    obj: {
      type: Object,
      default: () => {
        return {
          open: false,
          type: "1",
          title: "查看",
          id: "",
          companyType: "",
        };
      },
    },
  },
  data() {
    return {
      loading: false,
      turnbackProcessShow: false,
      process: {},
      collectOpen: false,
      userId: "",
      form: {
        deptRecommendList: [],
      },
      tableData: [],
      activeName: "1",
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [], //tab字典
      collectDicts: [], //收集字典
    };
  },
  methods: {
    turnbackOper() {
      this.turnbackProcessShow = true;
      this.$refs.turnbackRef.init(this.process);
    },
    submit() {
      this.$confirm("确定要提交吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const loading = this.openLoading();
          approveSchedule({
            ids: this.obj.id,
          }).then((res) => {
            loading.close();
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
        companyId:this.form.companyId,
        variables: JSON.stringify({
          btnMessage: e.btnMessage,
          message: e.nextStep,
          nextDealUser: preUserId,
          option: e.option,
        }),
      }).then((res) => {
        if (res.code == 200) {
          loading.close();
          this.$message.success("提交成功!");
          this.$emit("close", 200);
        }
      });
      this.turnbackProcessShow = false;
    },
    showUser(row) {
      this.userId = row.userId;
      this.collectOpen = true;
    },
    filterNumber(item, row) {
      let temp = row.fieldList.filter(
        (res) => res.fieldType == item.dictValue
      )[0];
      if (temp) {
        return temp.personNumber;
      }
      return "0";
    },
    handleOpen() {
      //初始化
      this.form = {};
      this.prefecturalTableData = [];
      this.provinceTableData = [];

      //表头字典
      getDicts("field_type").then((res) => {
        //地市公司
        this.prefecturalDicts = res.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) == -1
        );
        //省公司
        this.provinceDicts = res.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) != -1
        );
      });
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
      });
      //tab字典
      getDicts("company_type").then((res) => {
        this.tabDicts = res.data;
        this.activeName = res.data[0].dictLabel;
      });
      this.getDetail();
    },
    getDetail() {
      this.loading = true;
      if (this.obj.type == 3) {
        getRecommendList({
          colletionId: this.obj.id,
          companyId: this.obj.companyId,
          fieldType: this.obj.fieldType,
        }).then((res) => {
          this.loading = false;
          this.form.deptRecommendList = res.data;
        });
        return;
      }
      getRecommendProcessDetail({
        orderNumber: this.obj.orderNumber,
        companyType: this.obj.companyType,
      }).then((res) => {
        this.loading = false;
        if (res.code == 200) {
          if (!res.data) {
            res.data = { deptRecommendList: [] };
          } else { 
            if (res.data.companyType == "1") {
              this.prefecturalTableData.push(res.data);
            } else {
              this.provinceTableData.push(res.data);
            }
          }
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
            },
            1
          );
        }
      });
    },
    close(t) {
      if (t == 1) {
        this.collectOpen = false;
        return;
      }
      this.turnbackProcessShow = false;
      this.$emit("close");
    },
  },
};
</script>
<style lang="scss" scoped></style>
