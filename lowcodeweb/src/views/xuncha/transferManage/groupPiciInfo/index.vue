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
              <el-form-item label="预分组批次：" prop="groupPiciNo">
                <el-input
                  v-model="queryParams.groupPiciNo"
                  placeholder="请输入预分组批次"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <!--          <el-form-item label="巡察批次" prop="instanceId">-->
              <!--            <el-input-->
              <!--              v-model="queryParams.instanceId"-->
              <!--              placeholder="请输入巡察批次"-->
              <!--              clearable-->
              <!--              size="small"-->
              <!--              @keyup.enter.native="handleQuery"-->
              <!--            />-->
              <!--          </el-form-item>-->
              <el-form-item label="当前状态：" prop="status">
                <el-select
                  v-model="queryParams.status"
                  placeholder="请选择当前状态"
                  clearable
                  size="small"
                >
                  <el-option
                    v-for="dict in statusOptions"
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
              >搜索
            </el-button>
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
                v-hasPermi="['transferManage:groupPiciInfo:add']"
                >巡察预分组
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
            :data="groupPiciInfoList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="left" />
            <el-table-column
              label="预分组批次"
              min-width="130"
              prop="groupPiciNo"
            />
            <el-table-column label="巡察年份" prop="xunchaYear" />
            <el-table-column label="巡察批次" width="140" show-overflow-tooltip prop="piciName" />
            <el-table-column label="预分组数" prop="groupTotal" />
            <el-table-column label="当前状态" align="center" prop="status">
              <template slot-scope="scope">
                <span>{{ scope.row.status | statusFormatFilter }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="创建时间"
              align="center"
              prop="createdTime"
              width="180"
            >
              <template slot-scope="scope">
                <span>{{
                  parseTime(scope.row.createdTime, "{y}-{m}-{d}")
                }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              width="200"
              fixed="right"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="handleAudit(scope.row)"
                  v-if="scope.row.examineStatus == 0 && scope.row.status != 0"
                  >发起审核
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="handleQueryOption(scope.row)"
                  >查看
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="handleUpdate(scope.row)"
                  v-if="scope.row.status == 0"
                  v-hasPermi="['transferManage:groupPiciInfo:edit']"
                  >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="handleDelete(scope.row)"
                  v-if="scope.row.status == 0"
                  v-hasPermi="['transferManage:groupPiciInfo:remove']"
                  >删除
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
    <!-- 添加或修改人员抽调_预分组批次信息 对话框 -->
    <GroupDialog
      :title="title"
      :dialogVisible="isOpenQuery"
      :authorizeFlag="authorizeFlag"
      @close="cancel"
      :groupPiciId="groupPiciId"
      :optionType="optionType"
    />
    <!-- 发起审核对话框 -->
    <SetAudit
      :title="title"
      :dialogVisible="isAudit"
      :groupPiciNo="groupPiciNo"
      @close="auditCancel"
    />
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import PageTitle from "@/components/PageTitle";
import {
  listGroupPiciInfo,
  delGroupPiciInfo,
  exportGroupPiciInfo,
  importTemplate,
  disableEnable,
} from "@/api/xuncha/transferManage/groupPiciInfo";
import TreeSelect from "@/components/TreeSelect/index";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import GroupDialog from "./groupDialog/index.vue";
import SetAudit from "./setAudit.vue";

export default {
  name: "GroupPiciInfo",
  components: { TreeSelect, ExpandCollapse, GroupDialog, SetAudit, PageTitle },
  data() {
    return {
      groupPiciId: "",
      groupPiciNo: "",
      disabled: false,
      authorizeFlag: true,
      optionType: "",
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 发起审核弹出层
      isAudit: false,
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
      // 人员抽调_预分组批次信息 表格数据
      groupPiciInfoList: [],
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
          "/transferManage/groupPiciInfo/importData",
      },
      statusOptions: [
        {
          dictLabel: "草稿",
          dictValue: "0",
        },
        {
          dictLabel: "成功",
          dictValue: "1",
        },
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupPiciNo: null,
        instanceId: null,
        groupTotal: null,
        status: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
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
    statusFormatFilter(val) {
      let arr = ["草稿", "成功"];
      return arr[val];
    },
  },
  created() {
    this.getList();
    // this.getDicts("g_status").then((response) => {
    //   this.statusOptions = response.data || [];
    // });
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
    /** 查询人员抽调_预分组批次信息 列表 */
    getList() {
      this.loading = true;
      listGroupPiciInfo(this.queryParams).then((response) => {
        this.groupPiciInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel(code) {
      this.groupPiciId = "";
      this.optionType = null;
      this.isOpenQuery = false;
      this.reset();
      // if (code == 200) {
        this.getList();
      // }
    },
    auditCancel(code) {
      this.isAudit = false;
      if (code == 200) {
        this.getList();
      }
    },
    // 表单重置
    reset() {
      this.form = {
        groupPiciId: null,
        groupPiciNo: null,
        instanceId: null,
        groupTotal: null,
        status: "0",
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
       this.queryParams.instanceId = null;
      this.queryParams.xunchaYear = null;
      this.queryParams.lunciId = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.groupPiciId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.authorizeFlag = true;

      this.reset();
      this.isOpenQuery = true;
      this.title = "新增";
    },
    /** 发起审核 */
    handleAudit(row) {
      this.groupPiciId = row.groupPiciId || this.ids;
      this.groupPiciNo = row.groupPiciNo || this.ids;
      this.isAudit = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.title = "修改";
      this.optionType = "2";
      this.reset();
      this.authorizeFlag = true;
      this.groupPiciId = row.groupPiciId || this.ids;
      this.isOpenQuery = true;
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // 不可编辑
      this.authorizeFlag = false;
      this.title = "查看";
      this.groupPiciId = row.groupPiciId || this.ids;
      this.isOpenQuery = true;
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const groupPiciIds = row.groupPiciId || this.ids;
      this.$confirm(
        '是否确认删除人员抽调_预分组批次信息 编号为"' +
          groupPiciIds +
          '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delGroupPiciInfo(groupPiciIds);
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
      this.$confirm("是否确认导出所有人员抽调_预分组批次信息 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportGroupPiciInfo(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "人员抽调_预分组批次信息 数据导入";
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

