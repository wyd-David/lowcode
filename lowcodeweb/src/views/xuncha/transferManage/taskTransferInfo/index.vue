<template>
  <div class="app-container taskTransferInfo">
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
              <el-form-item label="抽调编号：" prop="transferNo">
                <el-input
                  v-model="queryParams.transferNo"
                  placeholder="请输入抽调编号"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="状态：" prop="status">
                <el-select
                  v-model="queryParams.status"
                  placeholder="请选择状态"
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
              >搜索</el-button
            >
            <el-button size="mini" @click="resetQuery">重置</el-button>
          </div>
        </expand-collapse>
        <div class="list-title">
          <span class="line"></span><span>人员抽调</span>
          <span style="font-weight: 400; color: #ec832f">
            （温馨提示： 可对已抽取完成的批次发起参与巡察确认通知）
          </span>
        </div>
        <div class="container">
          <el-row :gutter="10" class="mb12 utlsBtn">
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['transferManage:taskTransferInfo:add']"
                >抽调
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
            :data="taskTransferInfoList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="left" />
            <el-table-column
              label="抽调编号"
              width="180"
              
              prop="transferNo"
            />
            <el-table-column label="巡察年份" prop="xunchaYear" />
            <el-table-column
              label="巡察批次"
              align="center"
              prop="xunchaInstance"
              width="140"
            />
            <el-table-column
              label="抽调总人数"
              align="center"
              min-width="110"
              prop="transferTotal"
            />
            <el-table-column
              label="同意参加总人数"
              width="140"
              align="center"
              prop="agreeTotal"
            />
            <el-table-column
              label="拒绝参加总人数"
              width="140"
              align="center"
              prop="refuseTotal"
            />
            <el-table-column
              label="通知状态"
              align="center"
              :formatter="noticStatusFormat"
            />
            <el-table-column
              label="状态"
              align="center"
              prop="status"
              :formatter="statusFormat"
            >
              <template slot-scope="scope">
                {{ scope.row.status | statusFormatFilter }}
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="left"
              width="180"
              class-name="small-padding fixed-width"
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
                  v-if="scope.row.status != 0"
                  @click="handleNotice(scope.row)"
                  v-hasPermi="['transferManage:taskTransferInfo:edit']"
                  >通知
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="handleUpdate(scope.row)"
                  v-if="scope.row.status == 0"
                  v-hasPermi="['transferManage:taskTransferInfo:edit']"
                >
                  修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  v-if="scope.row.status == 0"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['transferManage:taskTransferInfo:remove']"
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
    <transferForm
      :isOpenQuery="isOpenQuery"
      :transferId="transferId"
      :authorizeFlag="authorizeFlag"
      @close="cancel"
      @onOpenInform="informOpen = true"
      :title="title"
    ></transferForm>
    <informDialog
      :informOpen="informOpen"
      :transferNo="transferNosStr"
      @close="informCancel"
    ></informDialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listTaskTransferInfo,
  getTaskTransferInfoDetail,
  delTaskTransferInfo,
} from "@/api/xuncha/transferManage/taskTransferInfo";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import TreeSelect from "@/components/TreeSelect/index";

import { formatDate } from "@/utils/index";
import informDialog from "./informDialog.vue";
import transferForm from "./transferForm.vue";
export default {
  name: "TaskTransferInfo",
  components: { ExpandCollapse, TreeSelect, transferForm, informDialog },
  data() {
    return {
      // 是否禁用编辑
      disabled: false,
      formType: 1,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 通知弹框
      informOpen: false,
      // 选中数组
      ids: [],
      // 选中抽调编号字符串
      transferNosStr: "",
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员抽调_巡察任务抽调信息 表格数据
      taskTransferInfoList: [],
      // 弹出层标题
      title: "",
      // 1->不可编辑  2->可编辑
      authorizeFlag: 1,
      transferId: "",
      // 状态 0：草稿  1：成功字典
      statusOptions: [
        {
          dictValue: 0,
          dictLabel: "草稿",
        },
        {
          dictValue: 1,
          dictLabel: "成功",
        },
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        instanceId: null,
        transferNo: null,
        transferTotal: null,
        agreeTotal: null,
        refuseTotal: null,
        noticStatus: null,
        noticTime: null,
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
      // 被巡察单位
      CompanyAll: [],
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
    release_statusFieldFilter(val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created() {
    this.getList();
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
    /** 查询人员抽调_巡察任务抽调信息 列表 */
    getList() {
      this.loading = true;
      listTaskTransferInfo(this.queryParams).then((response) => {
        this.taskTransferInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态 0：草稿  1：成功字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 状态 0：草稿  1：成功字典翻译
    noticStatusFormat(row, column) {
      if (row.noticStatus === "0") {
        return "未通知";
      } else {
        return "已通知";
      }
    },
    // 取消按钮
    cancel() {
      this.isOpenQuery = false;
      this.getList();
    },
    informCancel() {
      (this.transferNosStr = null), (this.informOpen = false);
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        transferId: null,
        instanceId: null,
        transferNo: null,
        transferTotal: null,
        agreeTotal: null,
        refuseTotal: null,
        noticStatus: "0",
        noticTime: null,
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
      this.queryParams.instanceId=null;
      this.queryParams.xunchaYear=null;
      this.queryParams.lunciId=null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.transferId);
      this.transferNosStr = selection.map((item) => item.transferNo).join(",");
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.transferId = "";
      this.authorizeFlag = 2;
      this.reset();
      this.isOpenQuery = true;
      this.title = "新增";
    },
    /** 通知 */
    handleNotice(row) {
      this.informOpen = true;
      this.transferNosStr = row.transferNo;
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.reset();
      this.authorizeFlag = 2;

      this.transferId = row.transferId || this.ids;
      this.isOpenQuery = true;
      this.title = "修改";
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.transferId = row.transferId || this.ids;
      this.authorizeFlag = 1;
      this.isOpenQuery = true;
      this.disabled = true;
      this.title = "查看 ";
      // });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const transferIds = row.transferId || this.ids;
      const transferNo = row.transferNo;
      console.log(row);
      this.$confirm(
        '是否确认删除人员抽调_巡察任务抽调信息 编号为"' +
          transferNo +
          '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTaskTransferInfo(transferIds);
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
.taskTransferInfo {
  // min-width: 1080px;
}
.left-box {
  width: 210px;
  float: left;
}
.dialog-context {
  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;

    .btn {
      float: right;
    }

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }

  .context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;
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
</style>

