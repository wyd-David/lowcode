<template>
  <div class="app-container">
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          @submit.native.prevent
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="姓名:">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入姓名"
            ></el-input>
          </el-form-item>
          <company-dept-select
            ref="cds"
            :companyId.sync="queryParams.companyId"
            :deptId.sync="queryParams.deptId"
          ></company-dept-select>
          <el-form-item label="职务:">
            <el-input
              v-model="queryParams.postName"
              placeholder="请输入职务"
            ></el-input>
          </el-form-item>
          <el-form-item label="职级:">
            <el-input
              v-model="queryParams.postLevel"
              placeholder="请输入职级"
            ></el-input>
          </el-form-item>
          <el-form-item label="专家领域:">
            <el-select
              clearable
              size="small"
              v-model="queryParams.fieldType"
              placeholder="请选择"
            >
              <el-option
                v-for="item in collectDicts"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="退出时段:">
            <el-date-picker
              v-model="date"
              type="daterange"
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="list-title">
      <span class="line"></span><span>离库人员</span
      ><el-checkbox
        true-label="1"
        false-label="0"
        @change="getList"
        v-model="queryParams.status"
        style="margin-left: 20px"
        >查看历史离库人才</el-checkbox
      >
    </div>
    <div class="list">
      <el-table v-loading="loading" :data="tableData">
        <el-table-column
          label="序号"
          align="center"
          type="index"
        ></el-table-column>
        <el-table-column label="姓名" prop="userName"></el-table-column>
        <el-table-column
          label="所在单位"
          show-overflow-tooltip
          prop="companyName"
        ></el-table-column>
        <el-table-column
          label="部门"
          show-overflow-tooltip
          prop="deptName"
        ></el-table-column>
        <el-table-column
          label="职务"
          show-overflow-tooltip
          prop="postName"
        ></el-table-column>
        <el-table-column
          label="职级"
          align="center"
          prop="postLevel"
        ></el-table-column>
        <el-table-column
          label="专家领域"
          prop="filedTypeName"
        ></el-table-column>
        <el-table-column
          label="出生年月"
          align="center"
          prop="birthDay"
        ></el-table-column>
        <el-table-column
          label="入党时间"
          align="center"
          prop="joinInPartDay"
        ></el-table-column>
        <el-table-column
          label="入库时间"
          align="center"
          prop="inTime"
        ></el-table-column>
        <el-table-column
          label="离库时间"
          align="center"
          prop="outTime"
        ></el-table-column>
        <el-table-column label="在库时长" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.onLineDay }}天</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleDetail(scope.row)"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pageBox">
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <collectForm
      :dialogVisible="iFormShow.open"
      :id="iFormShow.id"
      more
      @close="handleCollectFormClose"
    ></collectForm>
  </div>
</template>
<script>
import CompanyDeptSelect from "../collect/CompanyDeptSelect";
import { getLeavePersonList } from "@/api/talent/apply";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { getDicts } from "@/api/system/dict/data";
import collectForm from "@/views/talent/collect/collectForm.vue";
export default {
  components: {
    ExpandCollapse,
    collectForm,
    CompanyDeptSelect,
  },
  data() {
    return {
      iFormShow: {
        open: false,
        id: "",
      },
      date: [],
      showSearch: true,
      tableData: [],
      queryParams: {
        status: 0,
        date: [],
        userName: "",
        companyId: "",
        deptId: "",
        postName: "",
        postLevel: "",
        outBeginTime: "",
        outEndTime: "",
        fieldType: "",
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      collectDicts: [],
      loading: false,
    };
  },
  created() {
    //收集字典
    getDicts("field_type").then((res) => {
      this.collectDicts = res.data;
      this.getList();
    });
  },
  methods: {
    handleCollectFormClose() {
      this.iFormShow.open = false;
    },
    handleDetail(row) {
      this.iFormShow.open = true;
      this.iFormShow.id = row.userId;
    },
    handleQuery() {
      this.getList();
    },
    getList() {
      this.loading = true;
      this.queryParams.outBeginTime = this.date[0] || null;
      this.queryParams.outEndTime = this.date[1] || null;
      getLeavePersonList(this.queryParams).then((res) => {
        this.loading = false;
        this.tableData = res.data || [];
        this.total = res.total;
      });
    },
    resetQuery() {
      this.queryParams = {
        status: 0,
        userName: "",
        companyId: "",
        deptId: "",
        postName: "",
        postLevel: "",
        outBeginTime: "",
        outEndTime: "",
        fieldType: "",
      };
      this.$refs.cds.reset();
      this.getList();
    },
  },
};
</script>

<style lang="scss" scoped></style>