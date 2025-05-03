<template>
  <div class="app-container">
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            :disabled="ids.length==0"
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
        :data="flyGroupInfoList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="序号" align="center" type="index" />
        <el-table-column label="年份" align="center" prop="xunchaYear" />
        <el-table-column label="巡察批次" min-width="100" show-overflow-tooltip  prop="xunchaPici" />
        <el-table-column label="组别名称" min-width="120" show-overflow-tooltip prop="groupName" />
        <el-table-column label="巡察任务" width="150" show-overflow-tooltip prop="taskName" />
        <el-table-column label="组长" align="center" prop="groupLeader" />
        <el-table-column
          label="副组长"
          prop="deputyTeamLeader"
        />
        <el-table-column
          label="成员人数"
          align="center"
          prop="groupTotal"
        />
        <el-table-column label="主笔人" show-overflow-tooltip prop="leadWriter" />
        <el-table-column label="联络人"  show-overflow-tooltip prop="liaisonMan" />
        <el-table-column label="成员"  show-overflow-tooltip prop="teamMembers" />
        <el-table-column label="被巡察单位" min-width="160" show-overflow-tooltip prop="companyName" />
        <el-table-column label="绑定飞行组" width="130" prop="flyName">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleflyGroup(scope.row)"
              >{{scope.row.flyName}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="类型" align="center" prop="updatedBy">
          <template>巡察分组</template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          fixed="right"
          width="150"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看
            </el-button>

            <el-button
              v-if="!scope.row.flyName && scope.row.opStatus!=1"
              size="mini"
              type="text"
              @click="handleBound(scope.row)"
              v-hasPermi="['groupManage:flyGroupInfo:remove']"
              >绑定
            </el-button>
            <el-button
              v-if="scope.row.flyName && scope.row.opStatus!=1"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['groupManage:flyGroupInfo:remove']"
              >解绑
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
    <!-- 查看飞行小组 -->
    <el-dialog
      title="详情"
      :visible.sync="isOpenFlyGroup"
      width="1000px"
      :close-on-click-modal="false"
      :before-close="()=>isOpenFlyGroup=false"
      append-to-body="append-to-body"
    >
      <fly-group-member
        v-if="isOpenFlyGroup"
        :flyGroupId="flyGroupId"
        :type="2"
        ref="flyGroupMemberRef"
      />

    </el-dialog>
    <!-- 查看 -->
    <group-info
      :groupId="groupId"
      :dialogVisible="isOpenQuery"
      :disabled="disabled"
      @close="cancel"
    />
    <!-- 绑定 -->
    <bound-dialog
      :dialogVisible="isBoundDialog"
      :boundForm="boundForm"
      type="1"
      @close="closeBound"
    />

  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import groupInfo from "./groupInfo.vue";
import boundDialog from "./boundDialog.vue";
import flyGroupMember from "./flyGroupMember.vue";

import {
  exportXunCGroupInfo,
} from "@/api/xuncha/groupManage/flyGroupInfo";

import {
  deleteXunchaCheckFlyGroupRelation,
} from "@/api/xuncha/groupManage/flyGroupRelation";
import {
  getXunchaTaskGroupInfoData,
} from "@/api/xuncha/transferManage/taskGroupInfo";

import { formatDate } from "@/utils/index";

export default {
  name: "FlyGroupInfo",
  props: {
    queryParams: {
      type: Object,
    },
  },
  components: {flyGroupMember, groupInfo ,boundDialog},
  data() {
    return {
      groupId: "",
      disabled: false,
      isBoundDialog: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      isOpenFlyGroup: false,
      flyGroupId: null,
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
      boundForm: {
        checkGroupId: null,
        flyGroupId: null,
      },
      flyGroupList: [],
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
      getXunchaTaskGroupInfoData(this.queryParams).then((response) => {
        this.flyGroupInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.isOpenQuery = false;
      this.reset();
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

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /**详情按钮*/
    handleQueryOption(row) {
      this.isOpenQuery = true;
      this.groupId = row.groupId;
      // // this.reset();
    },
    handleflyGroup(row){
      this.isOpenFlyGroup = true
      this.flyGroupId = row.flyId
    },
    /** 解绑按钮操作 */
    handleDelete(row) {
      const { flyName, groupId,flyId } = row;
      this.$confirm('是否确认与"' + flyName + '"解绑？', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          let params = {
            checkGroupId:groupId,
            flyGroupId:flyId,
          };
          return deleteXunchaCheckFlyGroupRelation(params);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("解绑成功");
        })
        .catch(function () {});
    },
    // 绑定按钮
    handleBound(row) {
      this.boundForm.checkGroupId = row.groupId;
      this.isBoundDialog = true;
    },

    closeBound(code) {
      if (code==200) {
        this.getList()
      }
      this.isBoundDialog = false;
    },
    handleSelectionChange(selection) {
      console.log(selection);
      this.ids = selection.map((item) => item.groupId);
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = {groupId:this.ids.join(",")};
      this.$confirm("是否确认导出所有巡察小组信息 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportXunCGroupInfo(queryParams);
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
  padding: 20px 0;
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

