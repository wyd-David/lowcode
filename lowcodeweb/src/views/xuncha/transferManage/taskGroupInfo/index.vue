<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="4">
        <div class="tree_tit">巡察任务</div>
        <tree-select
          :isSearch="false"
          @node-click="handleNodeClick"
        ></tree-select>
      </el-col>
      <el-col :span="20">
        <expand-collapse>
          <div slot="left">
            <el-form
              :model="queryParams"
              ref="queryForm"
              :inline="true"
              v-show="showSearch"
              label-width="120px"
            >
              <el-form-item label="业务单号：" prop="groupNo">
                <el-input
                  v-model="queryParams.groupNo"
                  placeholder="请输入业务单号"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="巡察组：" prop="groupName">
                <el-select
                  v-model="queryParams.groupName"
                  placeholder="请选择"
                  clearable
                  size="small"
                >
                  <el-option
                    v-for="item in groupList"
                    :key="item.taskId"
                    :label="item.groupName"
                    :value="item.groupName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="关联巡察任务：" prop="taskId">
                <el-select
                  v-model="queryParams.taskId"
                  placeholder="请选择"
                  clearable
                  size="small"
                >
                  <el-option
                    v-for="item in groupList"
                    :key="item.taskId"
                    :label="item.taskName"
                    :value="item.taskId"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="组长：">
                <el-input
                  v-model="queryParams.groupLeaderName"
                  placeholder="请输入组长"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="被巡察单位：" prop="company">
                <company
                  v-model="queryParams.company"
                  :label.sync="queryParams.companyName"
                  size="small"
                  clearable
                  width="100%"
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
                :disabled="ids.length == 0"
                @click="handleUrgenSelectGroup"
              >催办
              </el-button>
            </el-col>
<!--            <el-col :span="1.5">-->
<!--              <el-button-->
<!--                type="default"-->
<!--                icon="el-icon-download"-->
<!--                size="mini"-->
<!--                @click="handleExport"-->
<!--                v-hasPermi="['transferManage:taskGroupInfo:importData']"-->
<!--                >导出-->
<!--              </el-button>-->
<!--            </el-col>-->
            <right-toolbar
              :showSearch.sync="showSearch"
              class="normalBtn"
              @queryTable="getList"
            ></right-toolbar>
          </el-row>

          <el-table
            v-loading="loading"
            :data="taskGroupInfoList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="left" />
            <el-table-column label="业务单号" width="180" prop="groupNo" />
            <el-table-column
              label="巡察组"
              prop="groupName"

            />
            <el-table-column label="巡察年份" prop="xunchaYear" />
            <el-table-column label="巡察批次" width="100" prop="xunchaPici" />
            <el-table-column
              label="关联巡察任务"
              width="160"
              show-overflow-tooltip
              prop="taskName"
            />
            <el-table-column
              label="被巡察单位"
              width="150"
              show-overflow-tooltip
              prop="company"
            />
            <el-table-column
              label="组长"
              align="center"
              prop="groupLeaderName"
            />
            <el-table-column
              label="审核状态"
              align="center"
              prop="examineStatus"
            >
              <template slot-scope="scope">
                <span>
                  {{ scope.row.examineStatus | examineStatusFilters }}</span
                >
              </template>
            </el-table-column>

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
                  >查看
                </el-button>
                <el-button
                  v-if="scope.row.examineStatus == 3"
                  size="mini"
                  type="text"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['transferManage:taskGroupInfo:edit']"
                  >修改
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
      </el-col>
    </el-row>
    <el-dialog
      :title="title"
      :visible.sync="isOpenQuery"
      width="1100px"
      :close-on-click-modal="false"
      :before-close="cancel"
      @open="handleOpenForm"
    >
      <form-dialog
        ref="formRef"
        :title="title"
        :ischeck="1"
        :groupId="groupNo"
        @close="cancel"
      />
    </el-dialog>
    <urgen-form
      :transferNo="transferNo"
      :dialogVisible="isUrengShow"
      @close="handleUrgenClose"
    ></urgen-form>
  </div>
</template>

<script>
import PageTitle from "@/components/PageTitle";
import { getToken } from "@/utils/auth";
import {
  grouplist,
  delTaskGroupInfo,
  exportTaskGroupInfo,
  importTemplate,
  disableEnable,
  sendGroupList,
} from "@/api/xuncha/transferManage/taskGroupInfo";
import { listXunchaTaskInfo } from "@/api/xuncha/XunchaTaskInfo";
import FormDialog from "./formDialog.vue";
import UrgenForm from "./urgenForm.vue";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import Company from "@/components/XunCha/Company/index";
import TreeSelect from "@/components/TreeSelect/index";

export default {
  name: "TaskGroupInfo",
  components: {
    TreeSelect,
    Company,
    ExpandCollapse,
    PageTitle,
    FormDialog,
    UrgenForm,
  },
  data() {
    return {
      transferNo: null,
      isUrengShow: false,
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
      // 人员抽调_巡察任务预分组信息 表格数据
      taskGroupInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 状态 0：草稿  1：成功字典
      statusOptions: [],
      groupNo: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskId: null,
        groupLeaderName: null,
        groupName: null,
        groupNo: "",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      groupList: [],
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
    examineStatusFilters(val) {
      let examineStatusArray = ["未发起审核", "待审核", "已结束", "已退回"];
      return examineStatusArray[val];
    },
    release_statusFieldFilter(val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created() {
    this.getList();
    this.getDicts("release_status").then((response) => {
      this.statusOptions = response.data || [];
    });
    listXunchaTaskInfo().then((res) => {
      this.groupList = res.rows;
    });
  },
  methods: {
    handleNodeClick(data, node, agms) {
      console.log("groupData:", data);
      // this.treeIndex = data;
      let { batchId, year, lunciId } = data;
      this.queryParams.instanceId = batchId;
      this.queryParams.xunchaYear = year;
      this.queryParams.lunciId = lunciId;

      this.getList();
    },
    /** 查询人员抽调_巡察任务预分组信息 列表 */
    getList() {
      this.loading = true;
      grouplist(this.queryParams).then((response) => {
        this.taskGroupInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态 0：草稿  1：成功字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    handleOpenForm() {
      this.$nextTick(() => {
        this.$refs.formRef.handleOpen();
      });
    },
    // 取消按钮
    cancel() {
      this.isOpenQuery = false;
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.instanceId = null;
      this.queryParams.xunchaYear = null;
      this.queryParams.lunciId = null;
      this.resetForm("queryForm");
      console.log(this.queryParams);
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.groupId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    // 催办按钮
    handleUrgen() {
      this.isUrengShow = true;
    },
    handleUrgenClose() {
      this.isUrengShow = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.groupNo = row.groupNo || this.ids;
      this.isOpenQuery = true;
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.groupNo = row.groupNo || this.ids;

      this.isOpenQuery = true;
      this.title = "查看";
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const groupIds = row.groupId || this.ids;
      this.$confirm(
        '是否确认删除人员抽调_巡察任务预分组信息 编号为"' +
          groupIds +
          '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTaskGroupInfo(groupIds);
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
        "是否确认导出所有人员抽调_巡察任务预分组信息 数据项?",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return exportTaskGroupInfo(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "人员抽调_巡察任务预分组信息 数据导入";
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
    // 批量催办
    handleUrgenSelectGroup() {
      console.log(this.ids);
      let params = {
        groupId: this.ids.join(","),
      };
      console.log(params);
      debugger
      sendGroupList(params).then((res) => {
        this.msgSuccess(res.msg);
      });
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

