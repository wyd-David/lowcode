<template>
  <div class="app-container">
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
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
          <el-form-item label="当前状态:" prop="processStatus">
            <el-select
              v-model="queryParams.processStatus"
              placeholder="请选择"
              clearable
              size="small"
            >
              <el-option v-for="item in processStatus" :key="item.dictValue"
              :label="item.dictLabel" :value="item.dictValue"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="业务单号:" prop="orderNumber">
            <el-input
              v-model="queryParams.orderNumber"
              placeholder="请输入业务单号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"></el-input>
          </el-form-item>
          <el-form-item label="申请时间:" prop="createdTime">
            <el-date-picker
              clearable
              size="small"
              style="width: 205px"
              v-model="queryParams.createdTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择申请时间"
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
      <span class="line"></span><span>人员标签审核</span>
    </div>
    <div class="list">
      <div class="operation">
        <el-button size="small" @click="labelSet" type="primary"
          >人员标签设置</el-button
        >
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </div>
      <el-table
        v-loading="loading"
        :data="configList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="业务单号" align="center" prop="orderNumber" />
        <el-table-column label="申请人" align="center" prop="createUserName" />
        <el-table-column
          label="申请时间"
          align="center"
          prop="createdTime"
          width="180"
        >
        </el-table-column>
        <el-table-column
        align="left"
          label="当前状态"
          :formatter="typeFormatter"
        ></el-table-column>
        <el-table-column
          label="操作"
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
              v-hasPermi="['talent:config:remove']"
              >删除</el-button
            >
            <el-button
            v-if="scope.row.processStatus == '1'"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['talent:config:edit']"
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
    <config-form :dialogVisible="open" :type="openType" :id="id" @close="handleClose"></config-form>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listConfig,
  getConfig,
  delConfig,
  addConfig,
  updateConfig,
  exportConfig,
  importTemplate,
  disableEnable,
} from "@/api/talent/config";
import configForm from "./configForm";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import ConfigForm from "./configForm.vue";
import { getDicts } from "@/api/system/dict/data";

export default {
  name: "Config",
  components: { ExpandCollapse, configForm, ConfigForm },
  data() {
    return {
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      id:"",
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
      processStatus: [],
      // 在库管理_人才标签配置管理 表格数据
      configList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //弹窗状态(1,新增,2,修改,3,查看)
      openType: "",
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
        url: process.env.VUE_APP_BASE_API + "/talent/config/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        processStatus:null,
        createUserName: null,
        createdTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    getDicts("label_process_status").then((res) => {
      this.processStatus = res.data;
    });
  },
  methods: {
    typeFormatter(row) {
      let status = this.processStatus.find(
        (item) => item.dictValue === row.processStatus
      )
      if (row.processStatus == '1') { 
        return '草稿';
      }
      return status?status.dictLabel:"";
    },
    //人员标签设置
    labelSet() {
      this.openType = "1";
      this.open = true;
    },
    /** 查询在库管理_人才标签配置管理 列表 */
    getList() {
      this.loading = true;
      listConfig(this.queryParams).then((response) => {
        this.configList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
      });
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
        labelName: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      };
      this.resetForm("queryForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {

      this.resetForm("queryForm");
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
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "添加在库管理_人才标签配置管理 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.openType = "2";
      this.id = row.id || this.ids;
      this.open = true;
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      this.id = row.id || this.ids;
      this.openType = "3";
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
            const loading = this.openLoading();
            updateConfig(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
                loading.close();
              }
            });
          } else {
            const loading = this.openLoading();
            addConfig(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
                loading.close();
              }
            });
          }
        }
      });
    },
    handleClose(e) { 
      this.open = false;
      this.id = "";
      if (typeof e === 'number') { 
        this.getList();
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.orderNumber || this.ids;
      this.$confirm(
        '是否确认删除该条数据?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delConfig(ids);
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
      this.$confirm(
        "是否确认导出所有在库管理_人才标签配置管理 数据项?",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return exportConfig(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "在库管理_人才标签配置管理 数据导入";
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
