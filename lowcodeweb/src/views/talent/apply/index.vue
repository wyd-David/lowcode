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
          <el-form-item label="申请人:" prop="createUserName">
            <el-input
              v-model="queryParams.createUserName"
              placeholder="请输入申请人"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="单位:" prop="companyName">
            <el-input
              v-model="queryParams.companyName"
              placeholder="请输入单位"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="部门:" prop="deptName">
            <el-input
              v-model="queryParams.deptName"
              placeholder="请输入部门"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> -->
          <company-dept-select ref="cds" :companyId.sync="queryParams.companyId" :deptId.sync="queryParams.deptId"></company-dept-select>
          <el-form-item label="当前状态:" prop="processStatus">
            <el-select
              v-model="queryParams.processStatus"
              placeholder="请选择"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in leaveTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
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
      <span class="line"></span><span>离库管理</span>
    </div>
    <div class="list">
      <div class="operation">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['talent:apply:add']"
          >新增</el-button
        >
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </div>
      <!-- <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['talent:apply:add']"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['talent:apply:edit']"
            >修改</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['talent:apply:remove']"
            >删除</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-lock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(1)"
            v-hasPermi="['talent:apply:remove']"
            >禁用</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-unlock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(0)"
            v-hasPermi="['talent:apply:remove']"
            >启用</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['talent:apply:importData']"
            >导入</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['talent:apply:importData']"
            >导出</el-button
          >
        </el-col>
         <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar>
      </el-row> -->

      <el-table
        v-loading="loading"
        :data="applyList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column
          type="index"
          label="序号"
          align="center"
        ></el-table-column>
        <el-table-column min-width="200" show-overflow-tooltip label="业务单号" align="center" prop="orderNumber" />
        <el-table-column label="申请人" min-width="150" align="left" prop="createUserName" />
        <el-table-column label="所在单位" show-overflow-tooltip align="left" min-width="150" prop="companyName" />
        <el-table-column label="部门" show-overflow-tooltip align="left" min-width="150" prop="deptName" />
        <el-table-column
          label="当前状态"
          min-width="150"
          align="left"
          prop="leaveType"
          :formatter="leaveTypeFormat"
        />

        <el-table-column
          label="申请时间"
          align="center"
          min-width="180"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.createdTime }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="150px"
          align="left"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看</el-button
            >
            <el-button
            v-if="scope.row.processStatus == '1'"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['talent:apply:remove']"
              >删除</el-button
            >
            <el-button
              size="mini"
              type="text"
              v-if="scope.row.processStatus == '1'"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['talent:apply:edit']"
              >修改</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <aform :id="id" :disabled="disabled" :dialogVisible="open" @close="close"></aform>
  </div>
</template>

<script>
import CompanyDeptSelect from "../collect/CompanyDeptSelect";
import aform from "./aform";
import { getToken } from "@/utils/auth";
import {
  listApply,
  getApply,
  delApply,
  addApply,
  updateApply,
  exportApply,
  importTemplate,
  disableEnable,
} from "@/api/talent/apply";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
export default {
  name: "Apply",
  components: { ExpandCollapse, aform,CompanyDeptSelect },
  data() {
    return {
      id: "",
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 离库管申请表格数据
      applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/talent/apply/importData",
      },
      // 离库类型(系统自动，人工发起)字典
      leaveTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        leaveType: null,
        createdBy: null,
        comapnyId: "",
        deptId: "",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
    leave_process_statusFieldFilter(val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created() {
    this.getList();
    this.getDicts("leave_process_status").then((response) => {
      this.leaveTypeOptions = response.data || [];
      this.leaveTypeOptions = this.leaveTypeOptions.filter(i => i.dictValue != '1')
    });
  },
  methods: {
    close(e) {
      this.open = false;
      if (typeof(e) == "number") {
        this.getList();
      }
    },
    /** 查询离库管申请列表 */
    getList() {
      this.loading = true;
      listApply(this.queryParams).then((response) => {
        this.applyList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
      });
    },
    // 离库类型(系统自动，人工发起)字典翻译
    leaveTypeFormat(row, column) {
      if (row.processStatus == '1') { 
        return '草稿';
      }
      return this.selectDictLabel(this.leaveTypeOptions, row.processStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        leaveType: null,
        orderNumber: null,
        leaveReason: null,
        processStatus: "0",
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.$refs.cds.reset();
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.id = "";
      this.disabled = false;
      this.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.id = row.id;
      this.open = true;
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.disabled = true;
      this.id = row.id;
      this.open = true;
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateApply(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addApply(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.orderNumber || this.ids;
      this.$confirm(
        '是否确认删除该条数据',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delApply(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 禁用或启用操作*/
    disabledBtnClick(type) {
      var ids = this.ids || [];
      this.$confirm("是否确认" + (type === 0 ? "启用" : "禁用") + "?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return disableEnable({
            ids: ids.toString(),
            disableEnableState: type,
          });
        })
        .then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有离库管申请数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportApply(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "离库管申请数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
}
.search-folder {
  // margin: 0 -16px;
  // padding: 0 16px;
  padding: 15px;
  position: relative;
  height: 60px;
  overflow-y: hidden;
  display: flex;
  background: #fff;
  border-radius: 2px;
  .ivu-icon {
    // position: absolute;
    left: 2px;
    top: 6px;
    color: #3a89da;
    z-index: 10;
    font-size: 18px;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
  &.folder-open {
    height: auto;
  }
  &-utils {
    display: flex;
    height: 32px;
    align-items: center;
    margin-left: 10px;
    p {
      cursor: pointer;
      width: 32px;
      margin-left: 10px;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #3a89da;
    }
  }
}
.utlsBtn {
  /deep/ .el-button--mini {
    padding: 9px 15px;
  }
  .normalBtn {
    /deep/ .el-button--mini {
      padding: 8px;
    }
  }
}
.container {
  padding: 12px;
  background: #fff;
  border-radius: 2px;
  
  .pageBox {
    height: 32px;
    .pagination-container {
      padding: 0 !important;
      margin: 0;
      height: 32px;
      margin-top: 12px;
      /deep/ .number,
      /deep/ .btn-prev,
      /deep/ .btn-next {
        width: 32px;
        height: 32px;
        line-height: 31px;
        background: #fff;
        border-radius: 4px;
        font-size: 14px;
        font-family: HelveticaNeue;
        color: rgba(0, 0, 0, 0.65);
        border: 1px solid rgba(23, 35, 61, 0.15);
        font-weight: normal;
      }
      /deep/ .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
    }
    /deep/ .el-pagination__total {
      line-height: 32px;
    }
  }
}
/deep/ .el-form-item__label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: rgba(23, 35, 61, 0.75);
}
</style>
