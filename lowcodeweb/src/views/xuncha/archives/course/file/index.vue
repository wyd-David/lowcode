<template>
  <div class="app-container">
    <!-- 列表查询条件模块 -->
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="巡察单位：" prop="companyId">
            <company
              v-model="queryParams.companyId"
              :label.sync="queryParams.companyName"
              size="small"
              width="205.4px"
            >
            </company>
          </el-form-item>
          <el-form-item label="资料名称：" prop="fileName">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入资料名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="归档范围：" prop="archiveScope">
            <el-select
              v-model="queryParams.archiveScope"
              placeholder="请选择归档范围"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in archiveScopeOptions"
                @keyup.enter.native="handleQuery"
                :key="dict.contentId"
                :label="dict.archiveScope"
                :value="dict.archiveScope"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="巡察阶段：" prop="xunchaStage">
            <el-select
              v-model="queryParams.xunchaStage"
              placeholder="请选择巡察阶段"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xunchaStageOptions"
                @keyup.enter.native="handleQuery"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="巡察轮次：" prop="lunciId">
            <lun-ci
              v-model="queryParams.lunciId"
              :label.sync="queryParams.lunciName"
              size="small"
              width="205.4px"
              @keyup.enter.native="handleQuery"
            ></lun-ci>
          </el-form-item>
          <el-form-item label="巡察年份：" prop="year">
            <el-date-picker
              clearable
              size="small"
              style="width: 200px"
              v-model="queryParams.year"
              type="year"
              value-format="yyyy"
              placeholder="选择巡察年份"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="巡察组：" prop="groupName">
            <group
              v-model="queryParams.taskId"
              :label.sync="queryParams.groupName"
              width="205.4px"
              @keyup.enter.native="handleQuery"
            >
            </group>
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
      <!-- 列表按钮模块 -->
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            size="mini"
            :disabled="ids.length === 0"
            @click="batchDownload"
          >
            批量下载
          </el-button>
        </el-col>

        <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <!-- 列表模块 -->
      <el-table
        v-loading="loading"
        :data="xunchaProcessFileList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column
          label="被巡察单位"
          width="120"
          align="center"
          prop="orgName"
        />
        <el-table-column
          label="资料名称"
          width="220"
          prop="fileName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="归档范围"
          width="150"
          prop="archiveScope"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="对应巡察阶段"
          width="120"
          align="center"
          prop="xunchaStage"
          :formatter="xunchaStageFormat"
        />
        <el-table-column
          label="归档组织"
          width="120"
          align="center"
          prop="archiveOrgType"
          :formatter="archiveOrgTypeFormat"
        />
        <el-table-column
          label="巡察轮次"
          width="220"
          align="center"
          prop="xunchaLunciName"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="巡察年份" align="center" prop="xunchaDate">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.xunchaDate, "{y}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="提交巡察组"
          width="220"
          align="center"
          prop="xunchaGroup"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="预览量" align="center" prop="previewSum" />
        <el-table-column label="下载量" align="center" prop="downloadSum" />
        <el-table-column label="小巡同步状态" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.indexStatus === '0'">未同步</span>
            <span v-if="scope.row.indexStatus === '1'">已同步</span>
            <span v-if="scope.row.indexStatus === '2'">半同步(仅名称)</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="onPreview(scope.row.fileId, scope.row.id)"
            >
              预览
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="onDownload(scope.row.fileId, scope.row.id)"
            >
              下载
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页模块 -->
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
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listXunchaProcessFile,
  downloadAndPreviewPlusMuch,
} from "@/api/xuncha/archives/xunchaProcessFile";
import { listContent } from "@/api/xuncha/archives/xunchaProcessContent";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import LunCi from "@/components/XunCha/LunCi/index";
import Company from "@/components/XunCha/Company/index";
import Group from "@/components/XunCha/Group/index";
import {
  saveProcessIndexsPoolByBusinessKey,
  saveTimelyProcessIndexsPool,
} from "@/api/xuncha/archives/synchronization";
import {
  downloadFile,
  downloadConvertPdf,
  previewFile,
  downloadZipFile,
} from "@/utils/uploadOperate";
import PageTitle from "@/components/PageTitle";

export default {
  name: "XunchaProcessFile",
  components: { ExpandCollapse, PageTitle, LunCi, Company, Group },
  data() {
    return {
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      fileTypeShow: false,
      isBatch: false,
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
      // 巡察过程资料库_文件信息  表格数据
      xunchaProcessFileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        fileName: null,
        archiveScope: null,
        groupName: null,

        datumName: null,
        xunchaStage: null,
        lunciId: null,
        year: null,
        requireFlag: null,
      },
      downLoadForm: {
        type: "0",
        fileId: "",
        id: "",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        applyId: [
          { required: true, message: "申请id不能为空", trigger: "blur" },
        ],
        contentId: [
          { required: true, message: "清单id不能为空", trigger: "blur" },
        ],
        fileId: [
          { required: true, message: "文件ID不能为空", trigger: "blur" },
        ],
        fileName: [
          { required: true, message: "文件名称不能为空", trigger: "blur" },
        ],
        datumName: [
          { required: true, message: "资料名称不能为空", trigger: "blur" },
        ],
        previewSum: [
          { required: true, message: "预览量不能为空", trigger: "blur" },
        ],
        downloadSum: [
          { required: true, message: "下载量不能为空", trigger: "blur" },
        ],
      },
      // 对应巡察阶段字典数据
      xunchaStageOptions: [],

      // 归档范围字典数据
      archiveScopeOptions: [],
      // 归档组织字典数据
      archiveOrgTypeOptions: [],
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    this.getList();
    this.getDicts("sys_archive_type").then((response) => {
      this.xunchaStageOptions = response.data || [];
    });
    this.getDicts("process_content_archive").then((response) => {
      this.archiveOrgTypeOptions = response.data || [];
    });
    listContent().then((res) => {
      this.archiveScopeOptions = res.rows;
    });
  },
  methods: {
    // 同步按钮
    handleSync(row) {
      if (row.applyId) {
        saveProcessIndexsPoolByBusinessKey({ id: row.applyId }).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: "同步成功",
              type: "success",
            });
          }
        });
        return;
      }

      saveTimelyProcessIndexsPool().then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "同步成功",
            type: "success",
          });
        }
      });
    },
    // 巡察阶段字典翻译
    xunchaStageFormat(row, column) {
      return this.selectDictLabel(this.xunchaStageOptions, row.xunchaStage);
    },
    // 归档组织字典翻译
    archiveOrgTypeFormat(row, column) {
      return this.selectDictLabel(
        this.archiveOrgTypeOptions,
        row.archiveOrgType
      );
    },
    /** 查询巡察过程资料库_文件信息  列表 */
    getList() {
      this.loading = true;
      let companyId = this.$store.getters.userInfo.companyId;
      let viewAll = false;
        if (this.$store.getters.userInfo.department == "巡察工作办公室(党风廉政办公室)") {
          viewAll = true;
        }
        if (!companyId) {
          viewAll = true;
        }
      listXunchaProcessFile({...this.queryParams,orgId:companyId,viewAll:viewAll}).then((response) => {
        this.xunchaProcessFileList = response.rows || [];
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
        id: null,
        applyId: null,
        contentId: null,
        fileId: null,
        fileName: null,
        datumCode: null,
        datumName: null,
        previewSum: null,
        downloadSum: null,
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
      this.queryParams.taskId = null;
      this.queryParams.groupName = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 下载文件
    onDownload(fileId, id) {
      downloadAndPreviewPlusMuch([id], "download");
      downloadFile(fileId);
    },
    // 批量下载
    batchDownload() {
      // 记录下载量
      // downloadPlusMuch(this.ids);
      let files = [];
      this.ids.forEach((id) => {
        this.xunchaProcessFileList.forEach((process) => {
          if (id === process.id) {
            files.push(process.fileId);
          }
        });
      });
      downloadAndPreviewPlusMuch(this.ids, "download");
      downloadZipFile(files);
    },
    // 预览文件
    onPreview(fileId, id) {
      downloadAndPreviewPlusMuch([id], "preview");
      previewFile(fileId);
    },

    batchDownloadconfirm() {
      let files = [];
      this.ids.forEach((id) => {
        this.xunchaProcessFileList.forEach((process) => {
          if (id === process.id) {
            files.push(process.fileId);
          }
        });
      });
      downloadAndPreviewPlusMuch(this.ids, "download");
      if (this.downLoadForm.type == 1) {
        downloadConvertPdf(files);
      } else {
        downloadZipFile(files);
      }
    },
  },
};
</script>


<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
}
.tips {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  color: rgba(23, 35, 61, 0.85);
  background: #fff;
  padding: 15px;
  margin-top: 15px;
  border-radius: 2px 2px 0px 0px;
}
.form-module-title {
  display: flex;
  align-items: center;
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

