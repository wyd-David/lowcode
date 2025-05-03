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
          <el-form-item label="字典名称：" prop="dictName">
            <el-input
              v-model="queryParams.dictName"
              placeholder="请输入字典名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="字典类型：" prop="dictType">
            <el-input
              v-model="queryParams.dictType"
              placeholder="请输入字典类型"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
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
    <page-title></page-title>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['xuncha:xunchaDictType:add']"
            >新增
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <el-table
        v-loading="loading"
        :data="xunchaDictTypeList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column label="字典名称" align="center" prop="dictName" />
        <el-table-column label="字典类型" align="center" prop="dictType" />
        <el-table-column label="描述" align="center" prop="remark" />
        <el-table-column label="创建人" align="center" prop="createdBy" />
        <el-table-column
          label="创建时间"
          align="center"
          prop="createdTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.createdTime, "{y}-{m}-{d} {h}:{m}:{s}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleLogs(scope.row)"
              >操作日志</el-button
            >
            <el-button size="mini" type="text" @click="openDictData(scope.row)"
              >查看字典项</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看</el-button
            >
            <!--<el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['xuncha:xunchaDictType:edit']">修改</el-button>-->
            <!--<el-button size="mini" type="text" @click="handleDelete(scope.row)" v-hasPermi="['xuncha:xunchaDictType:remove']">删除</el-button>-->
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
    <!-- 添加或修改业务字典管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="600px"
      append-to-body
      :close-on-click-modal="false"
    >
      <!-- 表单内容 -->
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="字典名称：" prop="dictName">
          <el-input v-model="form.dictName" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典类型：" prop="dictType">
          <el-input v-model="form.dictType" placeholder="请输入字典类型" />
        </el-form-item>
        <el-form-item label="描述：" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm" v-if="!disabled"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <!-- 查看业务字典管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="isOpenQuery"
      width="600px"
      append-to-body="append-to-body"
    >
      <el-form
        ref="queryform"
        :model="form"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="字典类型：" prop="dictType">
          {{ form.dictType }}
        </el-form-item>
        <el-form-item label="字典名称：" prop="dictName">
          {{ form.dictName }}
        </el-form-item>
        <el-form-item label="描述：" prop="remark">
          {{ form.remark }}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 业务字典数据列表页面对话框 -->
    <el-dialog
      title="字典项"
      :visible.sync="isDictDataList"
      width="1000px"
      append-to-body="append-to-body"
    >
      <el-row style="margin-bottom: 10px">
        <el-col :span="1.5">
          <el-button type="primary" @click="createDictData()" size="small"
            >添加</el-button
          >
        </el-col>
      </el-row>

      <el-table :data="dictDataList">
        <el-table-column
          label="序号"
          type="index"
          align="center"
          width="55"
        ></el-table-column>
        <el-table-column
          label="字典项名称"
          prop="dataName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="字典项编号"
          prop="dataCode"
          align="center"
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="deleteDictData(scope.row.dataId)"
              >删除</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="updateDictData(scope.row.dataId)"
              >修改</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!--<div slot="footer" class="dialog-footer">
        <el-button @click="isDictDataList = false">关 闭</el-button>
      </div>-->
    </el-dialog>
    <!-- 业务字典数据创建页面对话框 -->
    <el-dialog
      :title="dictTitle"
      :visible.sync="isDictDataCreate"
      width="600px"
      append-to-body="append-to-body"
    >
      <el-form
        ref="dictDataform"
        :model="dictDataform"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="字典项编号：" prop="dataCode">
          <el-input
            v-model="dictDataform.dataCode"
            placeholder="请输入字典项编号"
          />
        </el-form-item>
        <el-form-item label="字典项名称：" prop="dataName">
          <el-input
            v-model="dictDataform.dataName"
            placeholder="请输入字典项名称"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isDictDataCreate = false">取 消</el-button>
        <el-button type="primary" @click="submitCreateDictData"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <logs
      :id="logs.id"
      :requestMapping="logs.requestMapping"
      :dialogVisible="logs.open"
      @close="logsClose"
    ></logs>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listXunchaDictType,
  getXunchaDictType,
  delXunchaDictType,
  addXunchaDictType,
  updateXunchaDictType,
  exportXunchaDictType,
  importTemplate,
  disableEnable,
} from "@/api/xuncha/xunchaDictType";
import {
  addXunchaDictData,
  delXunchaDictData,
  listXunchaDictData,
  getXunchaDictData,
  updateXunchaDictData,
} from "@/api/xuncha/xunchaDictData";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import PageTitle from "@/components/PageTitle";
import logs from "../logs/index";
export default {
  name: "XunchaDictType",
  components: { ExpandCollapse, logs, PageTitle },
  data() {
    return {
      logs: {
        open: false,
        id: "",
        requestMapping: "xunchaDictType",
      },
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
      // 业务字典管理表格数据
      xunchaDictTypeList: [],
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
        url: process.env.VUE_APP_BASE_API + "/xuncha/xunchaDictType/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictType: null,
        dictName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dictType: [
          { required: true, message: "字典类型不能为空", trigger: "blur" },
        ],
        dictName: [
          { required: true, message: "字典名称不能为空", trigger: "blur" },
        ],
        dataCode: [
          { required: true, message: "字典项编码不能为空", trigger: "blur" },
        ],
        dataName: [
          { required: true, message: "字典项名称不能为空", trigger: "blur" },
        ],
      },
      // 业务数据字典列表页面弹出框
      isDictDataList: false,
      // 业务字典数据集合
      dictDataList: [],
      // 业务数据字典创建页面弹出框
      isDictDataCreate: false,
      // 标识是数据字典项的创建页面还是修改页面 create 或 update
      isDictDataCreateType: "",
      // 业务数据字典创建页面表单数据
      dictDataform: {
        dataCode: null,
        dataName: null,
      },
      // 打开的字典项数据的字典类型ID
      openDictId: "",
      dictTitle: "",
      dictId: ""
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    this.getList();
  },
  methods: {
    handleLogs(row, type) {
      this.logs.requestMapping = type || "xunchaDictType";
      this.logs.open = true;
      this.logs.id = row.piciId;
    },
    logsClose() {
      this.logs.open = false;
    },
    /** 查询业务字典管理列表 */
    getList() {
      this.loading = true;
      listXunchaDictType(this.queryParams).then((response) => {
        this.xunchaDictTypeList = response.rows || [];
        this.total = +response.total;
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
        dictId: null,
        dictType: null,
        dictName: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.dictId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "添加业务字典";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.reset();
      const dictId = row.dictId || this.ids;
      getXunchaDictType(dictId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改业务字典管理";
      });
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.disabled = true;
      this.reset();
      const dictId = row.dictId || this.ids;
      getXunchaDictType(dictId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "查看业务字典";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            if (this.form.dictId != null) {
              updateXunchaDictType(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addXunchaDictType(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const dictIds = row.dictId || this.ids;
      this.$confirm(
        '是否确认删除业务字典管理编号为"' + dictIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delXunchaDictType(dictIds);
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
      this.$confirm("是否确认导出所有业务字典管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportXunchaDictType(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "业务字典管理数据导入";
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
    // 打开业务字典弹出框
    openDictData(row) {
      this.openDictId = row.dictId;
      // 根据 dictId 获取业务字典数据列表
      this.isDictDataList = true;
      this.loading = true;
      const param = { pageNum: 1, pageSize: 9999, dictId: row.dictId };
      this.dictId = row.dictId
      listXunchaDictData(param).then((response) => {
        this.dictDataList = response.rows;
        if (row.dictType == "xuncha_label" || row.flag) {
          this.dictDataList = this.dictDataList.filter(
            (i) => i.dataName != "副组长" && i.dataName != "组长"
          );
        }
        this.loading = false;
      });
    },
    // 打开创建字典项页面
    createDictData() {
      this.dictTitle = "创建字典项";
      this.isDictDataCreateType = "create";
      this.isDictDataCreate = true;
      this.dictDataform = {
        dataCode: null,
        dataName: null,
        dictId: this.openDictId,
      };
    },
    // 打开修改字典项页面
    updateDictData(dataId) {
      this.dictTitle = "修改字典项";
      this.isDictDataCreateType = "update";
      this.isDictDataCreate = true;
      // 获得实体数据
      this.loading = true;
      getXunchaDictData(dataId).then((response) => {
        this.dictDataform = response.data;
        this.loading = false;
      });
      this.dictDataform = {
        dataCode: null,
        dataName: null,
        dictId: this.openDictId,
      };
    },
    // 点击创建字典项按钮
    submitCreateDictData() {
      this.$refs["dictDataform"].validate((valid) => {
        if (valid) {
          this.loading = true;
          // 增加数据
          if (this.isDictDataCreateType === "create") {
            addXunchaDictData(this.dictDataform).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.openDictData({dictId: this.dictId,flag:true});
                this.isDictDataCreate = false;
              }
            });
          }
          // 修改数据
          if (this.isDictDataCreateType === "update") {
            updateXunchaDictData(this.dictDataform).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.openDictData({dictId: this.dictId,flag:true});
                this.isDictDataCreate = false;
              }
            });
          }
        }
      });
    },
    // 删除字典项数据
    deleteDictData(dataId) {
      this.$confirm("确定删除该字典项？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          const dataIds = [dataId];
          return delXunchaDictData(dataIds);
        })
        .then(() => {
          this.openDictData({dictId: this.dictId,flag:true});
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog-footer {
}

.dialog-title {
  align-items: center;
  width: 100%;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: rgba(23, 35, 61, 0.85);
  background: #fff;

  .line {
    display: inline-block;
    width: 4px;
    height: 17px;
    background: #1590ff;
    border-radius: 2px;
    margin-right: 10px;
  }
}

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
