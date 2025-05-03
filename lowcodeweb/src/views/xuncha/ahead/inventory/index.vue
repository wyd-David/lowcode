<template>
  <div class="app-main">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="清单名称:" prop="inventoryName">
            <el-input v-model="queryParams.inventoryName" placeholder="请输入清单名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="提供部门:" prop="deptId">
            <el-select v-model="queryParams.deptId" placeholder="请选择">
              <el-option
                v-for="item in deptList"
                :label="item.deptName"
                :key="item.deptId"
                :value="item.deptId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="清单类型:" prop="inventoryType">
            <el-select v-model="queryParams.inventoryType" placeholder="请选择清单类型" clearable size="small">
              <el-option v-for="dict in inventoryTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="资料来源" prop="dataSources">
            <el-input v-model="queryParams.dataSources" placeholder="请输入资料来源" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="container" style="margin-top: 15px">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['ahead:inventory:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['ahead:inventory:importData']">导入</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table style="margin-top: 10px" v-loading="loading" :data="inventoryList"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="id" prop="id" />
        <el-table-column label="清单名称" show-overflow-tooltip align="center" prop="inventoryName" />
        <el-table-column label="提供部门" show-overflow-tooltip align="center" prop="deptName" />
        <el-table-column label="清单说明" show-overflow-tooltip align="center" prop="inventoryRemark" />
        <el-table-column label="清单类型" align="center" prop="inventoryType" :formatter="inventoryTypeFormat" />
        <el-table-column label="资料来源" align="center" prop="dataSources" :formatter="dataSourcesFormat" />
        <el-table-column label="创建人姓名" align="center" prop="createdUserName" />
        <el-table-column label="创建时间" align="center" prop="updateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['ahead:inventory:edit']">修改
            </el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-hasPermi="['ahead:inventory:remove']">
              删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改巡前资料_基础清单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false">
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>基础信息</span>
        </div>
        <div class="context-row">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
            <el-form-item label="清单名称" prop="inventoryName">
              <el-input v-model="form.inventoryName" placeholder="请输入清单名称" />
            </el-form-item>
            <el-form-item label="清单类型" prop="inventoryType">
              <el-select style="width: 100%" v-model="form.inventoryType" placeholder="请选择清单类型">
                <el-option v-for="dict in inventoryTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              v-if="form.inventoryType == 1"
              label="提供部门"
              prop="deptName"
            >
              <el-select
                style="width: 100%"
                value-key="deptId"
                v-model="form.deptObj"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item,index) in deptList"
                  :label="item.deptName"
                  :value="item"
                  :key="index"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="清单说明" prop="inventoryRemark">
              <el-input v-model="form.inventoryRemark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-form>
        </div>
        <div v-if="title != '新增基础清单'">
          <div class="dialog-title">
            <span class="line"></span><span>修改记录</span>
          </div>
          <div class="context-row">
            <el-table :data="form.historyList">
              <el-table-column label="序号" type="index" width="55"></el-table-column>
              <el-table-column label="操作人" align="center" prop="createdUserName"></el-table-column>
              <el-table-column label="所在部门" align="center" prop="deptName"></el-table-column>
              <el-table-column label="操作时间" align="center" prop="createdTime"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 巡前资料_基础清单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <!-- <el-checkbox
            v-model="upload.updateSupport"
          />是否更新已经存在的用户数据 -->
        </div>
        <div class="el-upload__tip" style="text-align: center" slot="tip">
          <el-button size="mini" style="color: red; border-color: red" @click="importTemplate">下载模板</el-button><span
            style="margin-left: 10px">提示：仅允许导入“xls”或“xlsx”格式文件！</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listInventory,
  getInventory,
  delInventory,
  addInventory,
  updateInventory,
  exportInventory,
  importTemplate,
} from "@/api/xuncha/ahead/inventory";

import { getDeptList } from "@/api/talent/collection";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
export default {
  name: "Inventory",
  components: { ExpandCollapse },
  data () {
    return {
      disabled: false,
      // 遮罩层
      loading: true,
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
      // 巡前资料_基础清单表格数据
      inventoryList: [],
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
        url: process.env.VUE_APP_BASE_API + "/ahead/inventory/importData",
      },
      // 清单类型(1:主责部门资料 2:被巡察单位资料)字典
      inventoryTypeOptions: [],
      // 资料来源字典
      dataSourcesOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inventoryName: null,
        deptId: null,
        inventoryType: null,
        dataSources: null,
        createdUserName: null,
      },
      // 表单参数
      form: {
        deptObj: {},
      },
      // 表单校验
      rules: {},
      deptList: [],
    };
  },
  filters: {
    filterDate (val) {
      return formatDate(val);
    },
    inventory_typeFieldFilter (val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
    data_sourcesFieldFilter (val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created () {
    this.getList();
    this.getDicts("inventory_type").then((response) => {
      this.inventoryTypeOptions = response.data || [];
    });
    this.getDicts("data_sources").then((response) => {
      this.dataSourcesOptions = response.data || [];
    });
    getDeptList({ companyName: "省公司" }).then((res) => {
      this.deptList = res.data;
    });
  },
  methods: {
    /** 查询巡前资料_基础清单列表 */
    getList () {
      this.loading = true;
      listInventory(this.queryParams).then((response) => {
        this.inventoryList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
      });
    },
    // 清单类型(1:主责部门资料 2:被巡察单位资料)字典翻译
    inventoryTypeFormat (row, column) {
      return this.selectDictLabel(this.inventoryTypeOptions, row.inventoryType);
    },
    // 资料来源字典翻译
    dataSourcesFormat (row, column) {
      return this.selectDictLabel(this.dataSourcesOptions, row.dataSources);
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
        id: null,
        inventoryName: null,
        deptId: null,
        deptName: null,
        inventoryRemark: null,
        inventoryType: null,
        dataSources: null,
        createdBy: null,
        deptObj: {},
        createdUserName: null,
        createdTime: null,
        updateTime: null,
        updateBy: null,
        updateUserName: null,
        delFlag: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "新增基础清单";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.disabled = false;
      this.reset();
      const id = row.id || this.ids;
      getInventory(id).then((response) => {
        if (response.data.inventoryType == 1) {
          response.data.deptObj = {
            deptId: response.data.deptId,
            deptName: response.data.deptName,
          };
        }
        this.form = response.data;
        this.open = true;
        this.title = "修改基础清单";
      });
    },
    /**详情按钮*/
    handleQueryOption (row) {
      // this.reset();
      this.disabled = true;
      const id = row.id || this.ids;
      getInventory(id).then((response) => {
        if (response.data.inventoryType == 1) {
          response.data.deptObj = {
            deptId: response.data.deptId,
            deptName: response.data.deptName,
          };
        }
        this.form = response.data;
        this.open = true;
        this.title = "查看详情";
      });
    },
    /** 提交按钮 */
    submitForm () {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.inventoryType == 1) {
            this.form.deptId = this.form.deptObj.deptId;
            this.form.deptName = this.form.deptObj.deptName;
          } else {
            this.form.deptId = null;
            this.form.deptName = null;
          }
          if (this.form.id != null) {
            updateInventory(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addInventory(this.form).then((response) => {
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
    handleDelete (row) {
      const ids = row.id || this.ids;
      this.$confirm("确定删除吗", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delInventory(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () { });
    },
    /** 导出按钮操作 */
    handleExport () {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有巡前资料_基础清单数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportInventory(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () { });
    },
    /** 导入按钮操作 */
    openImportTable () {
      this.upload.title = "基础清单数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate () {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess (response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm () {
      this.$refs.upload.submit();
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
}

.app-main {
  padding: 15px;
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

  /deep/ .el-table__header-wrapper {
    th {
      background: rgba(45, 140, 240, 0.04) !important;
      font-size: 14px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.75);
    }
  }

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
