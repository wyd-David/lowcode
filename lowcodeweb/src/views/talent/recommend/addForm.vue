<template>
  <div>
    <el-dialog
      title="提交人才推荐"
      :visible.sync="dialogVisible"
      width="80%"
      @open="init"
      @click="getDetail"
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
                    v-model="todoInfo.orderNumber"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="标题:">
                  <el-input
                    style="width: 100%"
                    v-model="todoInfo.title"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="收集类型:">
                  <el-radio-group v-model="todoInfo.collectionType">
                    <el-radio
                      :label="item.dictValue"
                      v-for="item in collectDicts"
                      :key="item.dictValue"
                      >{{ item.dictLabel }}</el-radio
                    >
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="下发机构:">
                  <el-input :value="todoInfo.companyName"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="参加工作时间:">
                  <el-date-picker
                    style="width: 100%"
                    v-model="todoInfo.joinWorkDate"
                    type="month"
                    placeholder="选择日期"
                    value-format="yyyy-MM"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="入党时间:">
                  <el-date-picker
                    style="width: 100%"
                    v-model="todoInfo.joinPartDate"
                    type="month"
                    placeholder="选择日期"
                    value-format="yyyy-MM"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="计划巡察时段:">
                  <div style="width: 100%; display: flex">
                    <el-input
                      style="width: 49%"
                      :value="todoInfo.startInspectTime"
                    ></el-input>
                    <span style="margin: 0px 2px">-</span>
                    <el-input
                      style="width: 49%"
                      :value="todoInfo.endInspectTime"
                    ></el-input>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="收集截止时间:">
                  <el-date-picker
                    style="width: 100%"
                    v-model="todoInfo.collectionEndDate"
                    type="datetime"
                    placeholder="选择日期时间"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="说明:">
              <el-input
                type="textarea"
                v-model="todoInfo.remark"
                :rows="3"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>需求名额指标</span>
        </div>
        <div class="context-row">
          <el-table
            :header-cell-style="{ 'text-align': 'center' }"
            :cell-style="{ 'text-align': 'center' }"
            :data="fieldList"
          >
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column prop="companyName" label="单位"></el-table-column>
            <el-table-column prop="deptName" label="部门"></el-table-column>
            <el-table-column
              prop="fieldTypeName"
              label="专家领域"
            ></el-table-column>
            <el-table-column
              prop="personNumber"
              label="指标人数(人)"
            ></el-table-column>
          </el-table>
          <div style="text-align: right">
            总计:<span style="color: #b1261d"> {{ total }} </span>人
          </div>
        </div>
        <div class="dialog-title" v-if="type == '1'">
          <span class="line"></span><span>推荐人才</span>
        </div>
        <div class="context-row" v-if="type == '1'">
          <el-table
            :header-cell-style="{ 'text-align': 'center' }"
            :cell-style="{ 'text-align': 'center' }"
            :data="deptRecommendList"
          >
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column prop="userName" label="姓名"></el-table-column>
            <el-table-column
              prop="fieldTypeName"
              label="专家领域"
            ></el-table-column>
            <el-table-column prop="deptName" label="部门"></el-table-column>
            <el-table-column label="职务" prop="postName"></el-table-column>
            <el-table-column label="职级" prop="postLevel"></el-table-column>
          </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer" v-if="type == '1'">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" :disabled="submitClick" @click="applyOper"
          >提 交</el-button
        >
      </span>
    </el-dialog>

    <complete-process
    title="提交"
      ref="completeRef"
      :preUserName="process.preUserName"
      :taskId="process.curtaskId"
      :dialogVisible="applyShow"
      isBusiness
      @handleData="submit"
      @close="applyShow = false"
    ></complete-process>
  </div>
</template>
<script>
import {
  getCompleteDeptInfo,
  oneClickSubmit,
} from "@/api/talent/recommendSchedule";
import completeProcess from "@/components/WorkFlow/completeProcess";
import { getDicts } from "@/api/system/dict/data";
import { fetchWorkFlowInfo } from "@/utils/workflow";
export default {
  components: {
    completeProcess,
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "",
    },
    //1.一键提交，2.待办详情
    type: {
      type: String,
      default: "1",
    },
    todoInfo: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      submitClick: false,
      applyShow: false,
      form: {},
      collectDicts: [],
      process: {},
      fieldList: [],
      deptRecommendList: [],
      taskId: "",
      memberList: "",
    };
  },
  computed: {
    total() {
      let count = 0;
      if (this.fieldList) {
        this.fieldList.forEach((res) => {
          count += res.personNumber;
        });
      }
      return count;
    },
  },
  methods: {
    applyOper() {
      this.applyShow = true;
      this.$refs["completeRef"].init(this.process);
    },
    init() {
      this.deptRecommendList = [];
      this.fieldList = [];
      this.memberList = "";
      this.form = {};
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
      });
      this.getDetail();
    },
    getDetail() {
      this.submitClick = true;
      fetchWorkFlowInfo(this.todoInfo.orderNumber, "", (res) => {
        if (res.allFlowStep.length) {
          let tempObj = res.allFlowStep[res.allFlowStep.length - 1];
          res.preUserName = tempObj.todoUserLoginId;
          res.preUserId = tempObj.historicTaskInstance.assignee;
        }
        this.taskId = res.curtaskId;
        this.process = res;
        this.submitClick = false;
      },1);
      getCompleteDeptInfo({ colletionId: this.id }).then((res) => {
        res.data.forEach((item) => {
          if (item.memberList.length) {
            this.memberList += "," + item.memberList;
          }
          item.fieldList.forEach((i) => {
            i.companyName = item.companyName;
            i.deptName = item.deptName;
          });
          this.fieldList = this.fieldList.concat(item.fieldList);
          this.deptRecommendList = this.deptRecommendList.concat(
            item.deptRecommendList
          );
        });
      });
    },
    submit(e) {
      const loading = this.openLoading();
      let preUserId = this.process.preUserId;
      oneClickSubmit(this.taskId, {
        colletionId: this.id,
        companyId:this.deptRecommendList[0].companyId,
        variables: JSON.stringify({
          btnMessage: e.btnMessage,
          message: e.nextStep,
          nextDealUser: preUserId,
          option: e.option,
        }),
      }).then((res) => {
        if (res.code == 200) {
          this.$message.success("提交成功");
          this.applyShow = false;
          this.$emit("close", 200);
        } else {
          this.$message.error(res.message);
        }
        loading.close();
      });
    },
    close() {
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
</style>
