<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form inline label-width="120px">
          <el-form-item label="收集年份:">
            <el-date-picker
              size="small"
              value-format="yyyy-MM-dd"
              v-model="queryForm.createdTime"
              type="year"
              placeholder="选择年"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="姓名:">
            <el-input
              size="small"
              placeholder="请输入姓名"
              v-model="queryForm.name"
            ></el-input>
          </el-form-item>
          <company-dept-select
            ref="cds"
            :companyId.sync="queryForm.companyId"
            :deptId.sync="queryForm.deptId"
          ></company-dept-select>
          <el-form-item label="专家类型:">
            <el-select
              size="small"
              v-model="queryForm.fieldType"
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
          <el-form-item label="职务:">
            <el-input
              size="small"
              placeholder="请输入职务"
              v-model="queryForm.postName"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="getList">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>

    <div class="list-title">
      <span class="line"></span><span>人才推荐</span>
    </div>
    <div class="list">
      <div class="operation">
        <el-button
          type="default"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          >导出</el-button
        >
      </div>
      <el-table
        ref="multipleTable"
        :data="tableData"
        style="width: 100%"
        tooltip-effect="dark"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column prop="userName" label="姓名"></el-table-column>
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
          prop="postName"
          show-overflow-tooltip
          label="职务"
        ></el-table-column>
        <el-table-column
          prop="filedTypeName"
          show-overflow-tooltip
          label="专家领域"
        ></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="showUser(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <collect-form
      :dialogVisible="collectOpen"
      :id="userId"
      @close="close"
    ></collect-form>
  </div>
</template>
<script>
import CompanyDeptSelect from "./CompanyDeptSelect";
import collectForm from "./collectForm";
import {
  getRecommendList,
  getBaseCompanyList,
  getBaseDeptList as getDeptList,
  exportCollect,
} from "@/api/talent/collect";
import { getDicts } from "@/api/system/dict/data";
import ExpandCollapse from "@/components/ExpandCollapse/index";
export default {
  components: { ExpandCollapse, collectForm, CompanyDeptSelect },
  created() {
    this.init();
  },
  data() {
    return {
      collectOpen: false,
      userId: "",
      queryForm: {
        fieldType: "",
        userName: "",
        createdTime: new Date().getFullYear() + "-01-01",
        postName: "",
        companyId: "",
        deptId: "",
      },
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      tableData: [],
      collectDicts: [],
      loading: false,
      loading1: false,
    };
  },
  methods: {
    handleExport() {
      const queryParams = this.queryForm;
      queryParams.userName = queryParams.name;
      this.$confirm("是否确认导出所有入库管理_人才汇总 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportCollect(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    showUser(row) {
      this.userId = row.userId;
      this.collectOpen = true;
    },
    init() {
      this.getList();
      //收集字典
      getDicts("field_type").then((res) => {
        this.collectDicts = res.data;
      });
    },
    resetQuery() {
      this.queryForm = {
        fieldType: "",
        userName: "",
        companyId: "",
        deptId: "",
        createdTime: new Date().getFullYear() + "-01-01",
        postName: "",
      };
      this.$refs.cds.reset();
      this.deptList = [];
      this.getList();
    },
    getList() {
      getRecommendList(this.queryForm).then((res) => {
        this.tableData = res.rows;
      });
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    close() {
      this.collectOpen = false;
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-input__inner {
  width: 220px !important;
}
</style>