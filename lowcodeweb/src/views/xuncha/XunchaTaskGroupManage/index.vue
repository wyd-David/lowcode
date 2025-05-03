<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="120px"
        >
          <el-form-item label="巡察任务ID" prop="taskId">
            <el-select
              v-model="queryParams.taskId"
              placeholder="请选择巡察任务ID"
              clearable
              size="small"
            >
              <el-option label="请选择字典生成" value="" />
            </el-select>
          </el-form-item>
          <el-form-item label="创建人" prop="createdBy">
            <el-input
              v-model="queryParams.createdBy"
              placeholder="请输入创建人"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="创建时间" prop="createdTime">
            <el-date-picker
              clearable
              size="small"
              style="width: 200px"
              v-model="queryParams.createdTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择创建时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="更新人" prop="updatedBy">
            <el-input
              v-model="queryParams.updatedBy"
              placeholder="请输入更新人"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="更新时间" prop="updatedTime">
            <el-date-picker
              clearable
              size="small"
              style="width: 200px"
              v-model="queryParams.updatedTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择更新时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="删除标志 0存在；1删除；" prop="delFlag">
            <el-input
              v-model="queryParams.delFlag"
              placeholder="请输入删除标志 0存在；1删除；"
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
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['xuncha:XunchaTaskGroupManage:add']"
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
            v-hasPermi="['xuncha:XunchaTaskGroupManage:edit']"
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
            v-hasPermi="['xuncha:XunchaTaskGroupManage:remove']"
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
            v-hasPermi="['xuncha:XunchaTaskGroupManage:remove']"
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
            v-hasPermi="['xuncha:XunchaTaskGroupManage:remove']"
            >启用</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['xuncha:XunchaTaskGroupManage:importData']"
            >导入</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['xuncha:XunchaTaskGroupManage:importData']"
            >导出</el-button
          >
        </el-col>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>

      <el-table
        v-loading="loading"
        :data="XunchaTaskGroupManageList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="巡察组ID" align="center" prop="groupId" />
        <el-table-column label="巡察任务ID" align="center" prop="taskId" />
        <el-table-column label="巡察组名称" align="center" prop="groupName" />
        <el-table-column
          label="操作"
          align="center"
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
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['xuncha:XunchaTaskGroupManage:edit']"
              >修改</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['xuncha:XunchaTaskGroupManage:remove']"
              >删除</el-button
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
    <!-- 查看巡察组管理 对话框 -->
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
        <el-form-item label="巡察任务ID:" prop="taskId">
          {{ form.taskId | FieldFilter(taskIdOptions) }}
        </el-form-item>
        <el-form-item label="巡察组名称:" prop="groupName">
          {{ form.groupName }}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 巡察组管理 导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox
            v-model="upload.updateSupport"
          />是否更新已经存在的用户数据
          <el-link type="info" style="font-size: 12px" @click="importTemplate"
            >下载模板</el-link
          >
        </div>
        <div class="el-upload__tip" style="color: red" slot="tip">
          提示：仅允许导入“xls”或“xlsx”格式文件！
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <tgForm :id="groupId" :open="open" @close="open = false"></tgForm>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listXunchaTaskGroupManage,
  getXunchaTaskGroupManage,
  delXunchaTaskGroupManage,
  exportXunchaTaskGroupManage,
  importTemplate,
  disableEnable,
} from "@/api/xuncha/XunchaTaskGroupManage";

import tgForm from "./tgForm";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
export default {
  name: "XunchaTaskGroupManage",
  components: { ExpandCollapse,tgForm },
  data() {
    return {
        groupId: '',
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
      // 巡察组管理 表格数据
      XunchaTaskGroupManageList: [],
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
        url:
          process.env.VUE_APP_BASE_API +
          "/xuncha/XunchaTaskGroupManage/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskId: null,
        groupName: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        groupName: [
          { required: true, message: "巡察组名称不能为空", trigger: "blur" },
        ],
      },
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
    /** 查询巡察组管理 列表 */
    getList() {
      this.loading = true;
      listXunchaTaskGroupManage(this.queryParams).then((response) => {
        this.XunchaTaskGroupManageList = response.rows || [];
        this.total = response.total;
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
        groupId: null,
        taskId: null,
        groupName: null,
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
      this.ids = selection.map((item) => item.groupId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.groupId = row.groupId || this.ids;
        this.open = true;
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      const groupId = row.groupId || this.ids;
      getXunchaTaskGroupManage(groupId).then((response) => {
        this.form = response.data;
        this.isOpenQuery = true;
        this.title = "查看巡察组管理 ";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupIds = row.groupId || this.ids;
      this.$confirm(
        '是否确认删除巡察组管理 编号为"' + groupIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delXunchaTaskGroupManage(groupIds);
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
      this.$confirm("是否确认导出所有巡察组管理 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportXunchaTaskGroupManage(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "巡察组管理 数据导入";
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
  margin-top: 15px;
  
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
