<template>
  <div class="app-container">
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            >新增
          </el-button>
        </el-col>
      </el-row>

      <el-table
        v-loading="loading"
        :data="flyGroupInfoList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="序号" align="center" type="index" />
        <el-table-column label="年份" prop="xunchaYear" />
        <el-table-column
          label="巡察批次"
          width="180"
          show-overflow-tooltip
          prop="xunchaPici"
        />
        <el-table-column label="组别名称" min-width="120" prop="flyName" />
        <el-table-column label="组长" prop="groupLeaderName" />
        <el-table-column
          label="成员人数"
          align="center"
          prop="groupMemberTotal"
        />
        <el-table-column
          label="成员"
          width="210"
          show-overflow-tooltip
          prop="personnelName"
        />
        <el-table-column label="绑定巡察组" min-width="110" prop="groupName" />
        <el-table-column label="类型" prop="updatedBy">
          <template>飞行分组</template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="180"
          fixed="right"
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
              @click="handleEdit(scope.row)"
              v-if="scope.row.opStatus != 1"
              >修改
            </el-button>

            <el-button
              v-if="scope.row.groupName && scope.row.opStatus != 1"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              >解绑
            </el-button>
            <el-button
              v-if="!scope.row.groupName && scope.row.opStatus != 1"
              size="mini"
              type="text"
              @click="handleBound(scope.row)"
              >绑定
            </el-button>
            <el-button
              v-if="scope.row.opStatus != 1"
              size="mini"
              type="text"
              @click="handleAddMember(scope.row)"
              >添加组员
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
    <bound-dialog
      type="2"
      :boundForm="boundForm"
      :dialogVisible="isBoundDialog"
      @close="closeBound"
    />
    <el-dialog
      :title="title"
      :visible.sync="isOpenQuery"
      width="1000px"
      :close-on-click-modal="false"
      :before-close="cancel"
      append-to-body="append-to-body"
    >
      <fly-group-member
        v-if="isOpenQuery"
        @close="cancel"
        :flyGroupId="flyGroupId"
        :type="operatType"
        ref="flyGroupMemberRef"
      />
      <div slot="footer" v-if="operatType != 2">
        <el-button @click="cancel">取 消</el-button>
        <el-button @click="submitForm" type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import flyGroupMember from "./flyGroupMember.vue";
import boundDialog from "./boundDialog.vue";

import { deleteXunchaCheckFlyGroupRelation } from "@/api/xuncha/groupManage/flyGroupRelation";
import { getXunchaFlyGroupInfoData } from "@/api/xuncha/groupManage/flyPersonInfo.js";
import { getXunchaFlyGroupInfoList } from "@/api/xuncha/groupManage/flyGroupInfo.js";
import { formatDate } from "@/utils/index";

export default {
  name: "FlyGroupInfo",
  props: {
    queryParams: {
      type: Object,
    },
  },
  components: { flyGroupMember, boundDialog },
  data() {
    return {
      flyGroupId: null,
      disabled: false,
      /**
       * 1:添加组员  2:查看
       */
      operatType: 1,
      isBoundDialog: false,
      boundForm: {
        checkGroupId: null,
        flyGroupId: null,
      },
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
      // 分组管理_飞行小组信息 表格数据
      flyGroupInfoList: [],
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
          process.env.VUE_APP_BASE_API + "/groupManage/flyGroupInfo/importData",
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
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询分组管理_飞行小组信息 列表 */
    getList() {
      this.loading = true;
      console.log(this.queryParams);
      getXunchaFlyGroupInfoData(this.queryParams).then((response) => {
        this.flyGroupInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    closeBound(code) {
      if (code == 200) {
        this.getList();
      }
      this.isBoundDialog = false;
    },
    // 取消按钮
    cancel(code) {
      this.flyGroupId = null;
      this.isOpenQuery = false;
      this.reset();
      if (code == 200) {
        this.getList();
      }
    },
    // 表单重置
    reset() {
      this.form = {
        flyGroupId: null,
        instanceId: null,
        flyGroupName: null,
        groupLeader: null,
        groupMemberTotal: null,
        flyGroupStatus: 0,
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

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.flyGroupId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.operatType = 3;
      this.reset();
      this.isOpenQuery = true;
      this.title = "新增";
    },
    /** 添加组员按钮操作 */
    handleAddMember(row) {
      this.operatType = 1;
      this.flyGroupId = row.flyGroupId || this.ids;

      this.isOpenQuery = true;
      this.title = "添加组员";
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.operatType = 2;
      this.flyGroupId = row.flyGroupId || this.ids;

      this.isOpenQuery = true;
      this.title = "详情 ";
    },
    handleEdit(row) {
      this.operatType = 3;
      this.flyGroupId = row.flyGroupId;
      this.isOpenQuery = true;
      this.title = "修改 ";
    },
    submitForm() {
      this.$confirm("是否确定?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$refs.flyGroupMemberRef.submitForm();
      });
    },
    // 绑定按钮
    handleBound(row) {
      this.boundForm.flyGroupId = row.flyGroupId;
      this.isBoundDialog = true;
    },
    /** 解绑按钮操作 */
    handleDelete(row) {
      const flyGroupId = row.flyGroupId || this.ids;
      const checkGroupId = row.groupId;
      this.$confirm(`是否确认与${row.groupName}解绑`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          let params = {
            flyGroupId,
            checkGroupId,
          };
          return deleteXunchaCheckFlyGroupRelation(params);
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
      this.$confirm("是否确认导出所有分组管理_飞行小组信息 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportFlyGroupInfo(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "分组管理_飞行小组信息 数据导入";
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
  padding: 20px 0;
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

