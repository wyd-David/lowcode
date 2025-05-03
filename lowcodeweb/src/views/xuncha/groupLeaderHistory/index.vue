<template>
  <div class="app-container">
    <permission-tabs @ptClick="ptClick" title="组长库"></permission-tabs>

    <expand-collapse>
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="120px"
        >
          <el-form-item label="姓名：" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属单位：" prop="company">
            <company
              v-model="queryParams.company"
              :label.sync="queryParams.companyName"
              size="small"
              clearable
              width="100%"
            />
          </el-form-item>
          <el-form-item label="部门：" prop="dpname">
            <el-input
              v-model="queryParams.dpname"
              placeholder="请输入部门"
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
        <p v-if="height > 34 && isShowOpen" @click="onHandle">
          {{ isOpen ? "收起" : "展开" }}
        </p>
        <span v-if="height > 34 && isShowOpen">
          <i
            class="el-icon-arrow-up ivu-icon"
            @click="onHandle"
            v-if="isOpen"
          ></i>
          <i class="el-icon-arrow-down ivu-icon" @click="onHandle" v-else></i>
        </span>
      </div>
    </expand-collapse>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            v-if="queryParams.status == 0"
            @click="handleAdd"
            >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="ids.length==0"
            v-if="queryParams.status == 0"
            @click="isOpenQuery = true"
            >设置标签
          </el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            v-show="queryParams.status == 0"
            @click="openImportTable"
            >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            >导出
          </el-button>
        </el-col>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>

      <el-table
        v-loading="loading"
        :data="groupLeaderHistoryList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="姓名" align="center" prop="userName" />
        <el-table-column label="所属单位" align="center" prop="company" />
        <el-table-column
          label="部门"
          min-width="180"
          align="center"
          prop="dpname"
        />
        <el-table-column label="专业" align="center" prop="fieldKey" />
        <el-table-column label="标签" align="center" prop="labelValue" />
        <el-table-column label="职级" align="center" prop="postLevel" />
        <el-table-column
          label="入库时间"
          align="center"
          prop="createdTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="出库时间"
          align="center"
          prop="createdTime"
          width="180"
          v-if="queryParams.status == 1"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          width="160"
          fixed="right"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看
            </el-button>

            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-if="queryParams.status == 0"
              v-hasPermi="['xuncha:groupLeaderHistory:remove']"
              >离库
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleDeleteLabel(scope.row)"
              v-if="scope.row.labelValue && queryParams.status == 0"
              >删除标签
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total > 0"
          :total="Number(total)"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 组长库 导入对话框 -->
      <el-dialog
        :title="upload.title"
        :visible.sync="upload.open"
        width="400px"
        append-to-body
      >
        <el-upload
          v-if="upload.open"
          ref="upload"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url + '?updateSupport=' + upload.updateSupport"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-error="handleFileError"
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
    <el-dialog
      title="设置标签"
      :visible.sync="isOpenQuery"
      width="600px"
      :close-on-click-modal="false"
      :before-close="handleClose"
      append-to-body="append-to-body"
    >
      <el-form ref="labelForm" :model="form" label-width="120px">
        <el-form-item label="标签：">
          <el-select
            style="width: 250px"
            v-model="form.labelObj"
            placeholder="请选择"
          >
            <el-option
              v-for="dict in options"
              :key="dict.dataCode"
              :label="dict.dataName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button @click="submitForm" type="primary">确 定</el-button>
      </div>
    </el-dialog>
    <add-dialog :title="title" :dialogVisible="open" :id="id" @close="cancel" />
    <remove-dialog
      :dialogVisible="openRemove"
      :row="selectedRow"
      @close="closeRemove"
    />
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listGroupLeader,
  importTemplate,
  updateGroupLeaderLabel,
  updateGroupLeader,
  exportGroupLeader
} from "@/api/xuncha/groupLeader/groupLeader";
import Company from "@/components/XunCha/Company/index";

import PermissionTabs from "@/components/PermissionTabs/index";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import AddDialog from "./addDialog.vue";
import RemoveDialog from "./removeDialog.vue";
import { queryDictList } from "@/api/xuncha/common/base";

import { formatDate } from "@/utils/index";

export default {
  name: "GroupLeaderHistory",
  data() {
    return {
      id: null,
      // 搜索栏展示配置
      height: null,
      disabled: false,
      isShowOpen: false,
      isOpen: true,
      openRemove: false,
      selectedRow: null,
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
      // 在库管理_组长管理_历史记录表格数据
      groupLeaderHistoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "导入",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url:
          process.env.VUE_APP_BASE_API +
          "/xuncha/groupLeader/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        reason: null,
        dpname: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        status: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      options: [],
    };
  },

  components: {
    Company,
    PermissionTabs,
    ExpandCollapse,
    AddDialog,
    RemoveDialog,
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    queryDictList({ dictType: "group_leader_label" }).then((res) => {
      console.log(res.data);
      this.options = res.data;
    });
    this.getList();
  },
  mounted() {},
  methods: {
    /** 查询在库管理_组长管理_历史记录列表 */
    getList() {
      this.loading = true;
      listGroupLeader(this.queryParams).then((response) => {
        this.groupLeaderHistoryList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.id = null;
      this.handleQuery();
    },
    closeRemove(code) {
      this.openRemove = false;
      if (code == 200) {
        this.handleQuery();
      }
    },
    submitForm() {
      let { labelObj } = this.form;
      let params = {
        labelKey: labelObj.dataCode,
        labelValue: labelObj.dataName,
        id: this.ids.toString(),
      };
      updateGroupLeader(params).then((res) => {
        this.msgSuccess(res.msg);
        this.handleClose();
      });
    },
    handleClose() {
      this.isOpenQuery = false;
      this.form = {};
      this.getList();
    },
    // 表单重置
    ptClick(tit) {
      console.log(tit);
      if (tit == "已离库组长") {
        this.queryParams.status = 1;
      } else {
        this.queryParams.status = 0;
      }
      this.getList();
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
      this.open = true;
      this.title = "新增组长";
    },

    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      this.id = row.id;
      this.open = true;
      this.title = "查看组长详情";
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      this.selectedRow = row;
      this.openRemove = true;
    },
    handleDeleteLabel(row) {
      this.$confirm("是否确认删除该组长标签?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        updateGroupLeaderLabel({ id: row.id }).then((res) => {
          this.msgSuccess(res.msg);
          this.getList();
        });
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      const queryParams = {
        status:this.queryParams.status,
        id:this.ids.join(",")
      };
      this.$confirm(
        "是否确认导出所有在库管理_组长管理_历史记录数据项?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return exportGroupLeader(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "在库管理_组长管理_历史记录数据导入";
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
    handleFileError(){
      this.upload.isUploading = false;

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
    // 搜索栏展开
    onHandle() {
      this.isOpen = !this.isOpen;
    },
  },
};
</script>


<style lang="scss" scoped>
.app-container {
  background: #e9f4fd;
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
  margin-top: 25px;

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

