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
          <el-form-item label="业务单号：" prop="applyId">
            <el-input
              v-model="queryParams.applyId"
              placeholder="请输入"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="原巡察员：" prop="personName">
            <el-input
              v-model="queryParams.personName"
              placeholder="请输入原巡察员姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="替换巡察员：" prop="replacePersonName">
            <el-input
              v-model="queryParams.replacePersonName"
              placeholder="请输入替换巡察员姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属分组：" prop="groupId">
            <el-input
              v-model="queryParams.groupId"
              placeholder="请输入所属分组名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="当前状态:" prop="status">
            <el-select
              size="small"
              v-model="queryParams.status"
              placeholder="请选择"
            >
              <el-option
                v-for="item in statusList"
                :label="item"
                :value="item"
                :key="item"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="申请时间：" prop="createdTime">
            <el-date-picker
              clearable
              size="small"
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
      <span class="line"></span><span>人员更替</span>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['replaceManage:taskGroupPersonChangeApply:add']"
            >申请更替
          </el-button>
          <el-button
            type="primary"
            size="mini"
            @click="handleToCollection"
            >申请入库
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
        :data="taskGroupPersonChangeApplyList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="业务单号" prop="replaceNo" />
        <el-table-column label="原巡察员" prop="personName" />
        <el-table-column label="专家领域" prop="expertType" />
        <el-table-column label="所属巡察组" prop="groupName" />
        <el-table-column label="替换巡察员" prop="replacePersonName" />
        <el-table-column label="更替原因" prop="replaceReason" />
        <el-table-column label="当前状态" prop="examineStatus">
          <template slot-scope="scope">
            {{ scope.row.examineStatus | filterStatus(statusList) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看
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
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="1000px"
      :close-on-click-modal="false"
      :before-close="cancel"
      append-to-body="append-to-body"
    >
      <apply-form
        v-if="dialogVisible"
        :ischeck="1"
        :dialogroleType="1"
        :id="replaceNo"
        @close="cancel"
      />
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listTaskGroupPersonChangeApply,
  getTaskGroupPersonChangeApply,
  delTaskGroupPersonChangeApply,
} from "@/api/xuncha/replaceManage/taskGroupPersonChangeApply";

import applyForm from "./applyForm";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";

export default {
  name: "TaskGroupPersonChangeApply",
  components: { ExpandCollapse, applyForm },
  data() {
    return {
      replaceNo: null,
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      dialogVisible: false,
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
      // 人员更替_巡察任务组成员人员更替申请 表格数据
      taskGroupPersonChangeApplyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        personName: null,
        replacePersonName: null,
        groupId: null,
        replaceReason: null,
        examineStatus: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      statusList: [
        "草稿",
        "党办主任审核",
        "分公司党办领导审核",
        "巡察办领导审核",
        "已更替",
      ],
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
    filterStatus(val,list) {
      return list[val];
    },
  },

  created() {
    this.getList();
  },
  methods: {
    /** 查询人员更替_巡察任务组成员人员更替申请 列表 */
    getList() {
      this.loading = true;
      listTaskGroupPersonChangeApply(this.queryParams).then((response) => {
        this.taskGroupPersonChangeApplyList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel(code) {
      this.dialogVisible = false;
      this.reset();
      if (code == 200) {
        this.handleQuery();
      }
    },
    // 表单重置
    reset() {
      this.form = {
        applyId: null,
        personName: null,
        replacePersonName: null,
        groupId: null,
        replaceReason: null,
        examineStatus: "0",
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.applyId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      (this.replaceNo = null), (this.disabled = false);
      this.reset();
      this.dialogVisible = true;
      this.title = "申请更替";
    },
    // 跳转到零星入库
    handleToCollection(){
      this.$router.push({
        path: "/talent/replaceManage/sporadicTalentUserInfo",

      });
      // window.open(routeUrl.href, "_blank");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.reset();
      const applyId = row.applyId || this.ids;
      getTaskGroupPersonChangeApply(applyId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人员更替_巡察任务组成员人员更替申请 ";
      });
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      this.replaceNo = row.replaceNo;
      this.dialogVisible = true;
      this.title = "查看人员更替申请 ";
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const applyIds = row.applyId || this.ids;
      this.$confirm(
        '删除后将不能恢复，确定删除？',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTaskGroupPersonChangeApply(applyIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
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

