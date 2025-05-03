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
              <el-form-item label="姓名：" prop="personnelName">
                <el-input
                  v-model="queryParams.personnelName"
                  placeholder="请输入姓名"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="标签：" prop="labelKey">
                <el-select
                  size="small"
                  style="width: 100%"
                  v-model="queryParams.labelKey"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="dict in labels"
                    :key="dict.dataId"
                    :label="dict.dataName"
                    :value="dict.dataCode"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="专家领域：" prop="fieldKey">
                <el-select
                  size="small"
                  style="width: 100%"
                  v-model="queryParams.fieldKey"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="dict in fieldList"
                    :key="dict.dataCode"
                    :label="dict.dictLabel"
                    :value="dict.dictLabel"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="所属单位：" prop="company">
                <company
                  v-model="queryParams.company"
                  :label.sync="queryParams.companyName"
                  size="small"
                  width="100%"
                />
              </el-form-item>
              <el-form-item label="部门：" prop="ouname">
                <el-input
                  v-model="queryParams.ouname"
                  placeholder="请输入"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="抽调批次：" prop="instanceId">
                <pici
                  ref="piciRef"
                  width="100%"
                  size="small"
                  v-model="queryParams.instanceId"
                  :label.sync="queryParams.piciName"
                />
              </el-form-item>
              <el-form-item label="是否参加：" prop="isCanJ">
                <el-radio-group
                  style="width: 205px"
                  size="small"
                  v-model="queryParams.isCanJ"
                >
                  <el-radio label="">全部</el-radio>
                  <el-radio :label="1">是</el-radio>
                  <el-radio :label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="反馈状态：" prop="fkStatus">
                <el-radio-group
                  style="width: 275px"
                  size="small"
                  v-model="queryParams.fkStatus"
                >
                  <el-radio label="">全部</el-radio>
                  <el-radio label="IS NULL ">待反馈</el-radio>
                  <el-radio label="IS NOT NULL">已反馈</el-radio>
                </el-radio-group>
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
                @click="handleUrgenSelect"
                v-hasPermi="['transferManage:transferPersonnelInfo:add']"
                >催办
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="default"
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['transferManage:transferPersonnelInfo:importData']"
                >导出
              </el-button>
            </el-col>
          </el-row>

          <el-table
            v-loading="loading"
            :data="transferPersonnelInfoList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="left" />
            <el-table-column label="姓名" align="center" prop="personnelName" />
            <el-table-column label="标签" align="center" prop="labelName" />
            <el-table-column
              label="专家领域"
              width="120"
              align="center"
              prop="fieldKey"
            />
            <el-table-column label="是否参加" align="center" prop="isCanJ" />
            <el-table-column
              label="所属公司"
              min-width="110"
              align="center"
              prop="company"
            />
            <el-table-column
              label="部门"
              min-width="120"
              align="center"
              prop="ouname"
            />
            <el-table-column
              label="巡察年份"
              align="center"
              prop="xunchaYear"
            />
            <el-table-column
              label="巡察批次"
              width="140"
              show-overflow-tooltip
              prop="piciName"
            />
            <el-table-column
              label="抽调批次"
              width="180"
              show-overflow-tooltip
              prop="transferId"
            />
            <el-table-column
              label="反馈状态"
              align="center"
              prop="feedbackStatus"
              :formatter="feedbackStatusFormat"
            />
            <el-table-column
              label="操作"
              align="center"
              class-name="small-padding fixed-width"
              fixed="right"
              width="110"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="handleQueryOption(scope.row)"
                  >查看
                </el-button>
                <el-button
                  v-if="scope.row.feedbackStatus == ''"
                  size="mini"
                  type="text"
                  @click="handleUrgen(scope.row)"
                  >催办
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
    <inform-dialog
      :personnelInfoId="personnelInfoId"
      :informOpen="informOpen"
      :transferNo="transferNo"
      @close="informClose"
    ></inform-dialog>
    <collectForm
      :dialogVisible="isOpenQuery"
      :id="personnelId"
      more
      @close="cancel"
    ></collectForm>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import informDialog from "./informDialog.vue";
import collectForm from "@/views/talent/collect/collectForm.vue";
import Pici from "@/components/XunCha/Pici/index";
import Company from "@/components/XunCha/Company/index";
import TreeSelect from "@/components/TreeSelect/index";
import {
  listTransferPersonnelInfo,
  exportTransferPersonnelInfo,
  importTemplate,
} from "@/api/xuncha/transferManage/transferPersonnelInfo";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import PageTitle from "@/components/PageTitle";
import { sendTransferList } from "@/api/xuncha/transferManage/transferPersonnelInfo";
import { queryDictList } from "@/api/talent/config";
import { listData } from "@/api/system/dict/data";

export default {
  name: "TransferPersonnelInfo",
  components: {
    TreeSelect,
    ExpandCollapse,
    Pici,
    Company,
    PageTitle,
    collectForm,
    informDialog,
  },
  data() {
    return {
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      informOpen: false,
      personnelId: null,
      transferNo: null,
      personnelInfoId: null,
      // 选中数组
      ids: [],
      labels: [],
      fieldList: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员抽调_抽调批次人员信息 表格数据
      transferPersonnelInfoList: [],
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
          "/transferManage/transferPersonnelInfo/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        labelKey: null,
        fieldKey: null,
        personnelId: null,
        ouname: null,
        fkStatus: "",
        isCanJ: "",
        remarks: null,
        isGroup: null,
        type: null,
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
  },
  created() {
    this.getList();
    //查询标签列表
    queryDictList({
      dictType: "xuncha_label",
    })
      .then((res) => {
        this.labels = res.data;
      })
      .catch((err) => {
        console.log(err);
      });

    let param = {
      dictType: "field_type",
      pageNum: 1,
      pageSize: 100,
    };
    listData(param).then((res) => {
      this.fieldList = res.rows;
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
    /** 查询人员抽调_抽调批次人员信息 列表 */
    getList() {
      this.loading = true;
      listTransferPersonnelInfo(this.queryParams).then((response) => {
        this.transferPersonnelInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态 0：草稿  1：成功字典翻译
    feedbackStatusFormat(row) {
      if (row.feedbackStatus == "") {
        return "未反馈";
      } else {
        return "已反馈";
      }
    },
    // 取消按钮
    cancel() {
      this.isOpenQuery = false;
    },
    informClose() {
      this.informOpen = false;
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
      this.ids = selection.map((item) => item);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 催办按钮操作 */
    handleUrgen(row) {
      let params = {
        personnelInfoId: row.personnelInfoId,
      };
      sendTransferList(params).then((res) => {
        this.msgSuccess(res.msg);
      });
    },
    // 批量催办
    handleUrgenSelect() {
      console.log(this.ids);
      let personnelInfoIdArr = this.ids.filter((i) => i.feedbackStatus == "");
      let params = {
        personnelInfoId: personnelInfoIdArr
          .map((i) => i.personnelInfoId)
          .join(","),
      };
      console.log(params);

      sendTransferList(params).then((res) => {
        this.msgSuccess(res.msg);
      });
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.personnelId = row.personnelId;
      this.isOpenQuery = true;
      this.title = "查看";
    },

    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm(
        "是否确认导出所有人员抽调_抽调批次人员信息 数据项?",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return exportTransferPersonnelInfo(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "人员抽调_抽调批次人员信息 数据导入";
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