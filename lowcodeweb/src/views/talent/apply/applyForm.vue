<template>
  <div class="app-main">
    <div v-if="!disabled">
      <div class="dialog-title">
        <span class="line"></span><span>人员基础信息</span>
      </div>
      <div class="context-row">
        <el-form label-width="80px" @submit.native.prevent>
          <el-form-item label="姓名:">
            <el-input
              v-model="queryForm.name"
              placeholder="请输入姓名"
              style="width: 92%"
              @keydown.enter.native="search"
            ></el-input>
            &nbsp;<el-button type="primary" size="small" @click="search"
              >搜索</el-button
            >
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="userList">
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column label="姓名" prop="name"></el-table-column>
          <el-table-column
            label="专业领域"
            prop="fieldTypeName"
          ></el-table-column>
          <el-table-column
            label="所在单位"
            prop="companyName"
          ></el-table-column>
          <el-table-column label="部门" prop="deptName"></el-table-column>
          <el-table-column label="职务" prop="postName"></el-table-column>
          <el-table-column label="职级" prop="postLevel"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                @click="handleApply(scope.row)"
                >申请</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <div class="dialog-title">
      <span class="line"></span><span>申请离库人员</span>
    </div>
    <div class="context-row">
      <el-table :data="applyList">
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column
          prop="name"
          align="center"
          label="离库人姓名"
        ></el-table-column>
        <el-table-column
          prop="companyName"
          show-overflow-tooltip
          label="所在单位"
        ></el-table-column>
        <el-table-column
          prop="deptName"
          show-overflow-tooltip
          label="部门"
        ></el-table-column>
        <el-table-column
          prop="fieldTypeName"
          show-overflow-tooltip
          label="专家类型"
        ></el-table-column>
        <el-table-column
          align="center"
          label="巡察综合评分"
        >
        <template slot-scope="scope">
          <span style="color:red;">{{scope.row.inspectionScore}}</span>
        </template>
        </el-table-column>
        <el-table-column
          prop="rejectInspectionTimes"
          align="center"
          label="拒绝次数"
        ></el-table-column>
        <el-table-column
          prop="createdTime"
          align="center"
          label="入库时间"
        ></el-table-column>
        <el-table-column v-if="!disabled" label="操作">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="handleCancel(scope.$index)"
              >取消</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <br />
      <el-form :disabled="disabled" label-width="80px">
        <el-form-item label="离库原因:">
          <el-input type="textarea" v-model="form.leaveReason" :rows="3"></el-input>
        </el-form-item>
      </el-form>
    </div>

    <all-flow-step v-if="!isTodo && id && form.processStatus != 1" :allFlowStep="process.allFlowStep"></all-flow-step>
  </div>
</template>
<script>
import { getApply } from "@/api/talent/apply";
import { getPersonList } from "@/api/talent/config";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
export default {
  props: {
    id: {
      type: String,
      default: "",
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: "",
    },
    isTodo: {
      type: Boolean,
      default: false,
    },
  },
  components: {AllFlowStep},
  data() {
    return {
      loading: false,
      userList: [], //搜索到的离库人
      leaveReason: "", //离库原因
      queryForm: {},
      form: {},
      applyList: [], //选中的离库人
      process: {}, //流程信息
    };
  },
  created() {
    if (this.id) {
      this.getDetail();
    }
  },
  methods: {
    getDetail() {
      getApply(this.id).then((res) => {
        this.applyList = res.data.detailList;
        this.form = res.data;
        if (!this.isTodo) { 
          fetchWorkFlowInfo(res.data.orderNumber, null, (res) => {
            this.process = res;
          },1);
        }
        this.$emit("initForm", res.data);
      });
    },
    checkSubmit() {
      if (!this.applyList.length) {
        this.$message.warning("请选择离库人!");
        return null;
      }
      let tempObj = {
        leaveApplyRelList: this.applyList,
        leaveReason: this.form.leaveReason,
        id: this.id,
        orderNumber: this.form.orderNumber,
        createdBy:this.form.createdBy
        
      };

      return tempObj;
    },
    handleApply(row) {
      if (this.applyList.find(res => res.userId == row.userId)) {
          this.msgError("该人员已经在申请列表中!");
      } else { 
          this.applyList.push(JSON.parse(JSON.stringify(row)));
      }
    },
    handleCancel(i) {
      this.applyList.splice(i, 1);
    },
    search() {
      this.loading = true;
      getPersonList({...this.queryForm,type:2}).then((res) => {
        this.loading = false;
        this.userList = res.data;
      });
    },
    close() {
      this.$emit("close");
    },
  },
};
</script>
