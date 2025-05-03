<template>
  <span>
    <el-form-item label="所在单位:" prop="companyId">
      <el-select
        style="width: 100%"
        v-model="queryForm.companyId"
        filterable
        remote
        size="small"
        reserve-keyword
        placeholder="请输入单位"
        :remote-method="remoteMethod"
        :loading="loading"
        @change="handleChange"
        clearable
      >
        <el-option
          v-for="item in companyList"
          :key="item.companyId"
          :label="item.companyName"
          :value="item.companyId"
        >
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="部门:" prop="deptId">
      <el-select
        style="width: 100%"
        v-model="queryForm.deptId"
        filterable
        remote
        size="small"
        reserve-keyword
        placeholder="请输入部门"
        :remote-method="remoteMethod1"
        :loading="loading1"
        @change="handleDeptChange"
        clearable
        :disabled="disabled"
      >
        <el-option
          v-for="item in deptList"
          :key="item.deptId"
          :label="item.deptName"
          :value="item.deptId"
        >
        </el-option>
      </el-select>
    </el-form-item>
  </span>
</template>
<script>
import {
  getBaseCompanyList,
  getBaseDeptList as getDeptList,
} from "@/api/talent/collect";
export default {
  data() {
    return {
      queryForm: {
        companyId: "",
        deptId: "",
      },
      loading1: false,
      loading: false,
      companyList: [],
      deptList: [],
      disabled: true,
    };
  },
  methods: {
    handleDeptChange() {
      this.$emit("update:deptId", this.queryForm.deptId);
    },
    reset() { 
      this.queryForm = {
        companyId: "",
        deptId: "",
      };
      this.deptList = [];
      this.disabled = true;
      this.$emit("update:companyId", "");
      this.$emit("update:deptId", "");
    },
    //搜索公司下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          getBaseCompanyList({ companyName: query }).then((res) => {
            this.companyList = res.data;
            this.loading = false;
          });
        }, 200);
      } else {
        this.companyList = [];
      }
    },
    //搜索公司下拉框
    remoteMethod1(query) {
      if (query !== "") {
        this.loading1 = true;
        setTimeout(() => {
          getDeptList({
            companyId: this.queryForm.companyId,
            deptName: query,
          }).then((res) => {
            this.deptList = res.data;
            this.loading1 = false;
          });
        }, 200);
      } else {
        this.deptList = [];
      }
    },
    handleChange() {
      this.$emit("update:companyId", this.queryForm.companyId);
      getDeptList({ companyId: this.queryForm.companyId }).then((res) => {
        this.deptList = res.data;
        this.disabled = false;
      });
    },
  },
};
</script>
