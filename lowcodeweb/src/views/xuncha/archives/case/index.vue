<template>
  <!-- 案例方法库列表页面 -->
  <div class="app-container">
    <!-- 搜索表单 -->
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="文件名称：" prop="fileName">
            <el-input v-model="queryParams.fileName" placeholder="请输入内容" />
          </el-form-item>
          <el-form-item label="领域类型：" prop="domainType">
            <el-select
              v-model="queryParams.domainType"
              placeholder="请选择领域类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in domainTypeSelectData"
                :key="dict.dataId"
                :label="dict.dataName"
                :value="dict.dataCode"
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
    <!-- 操作按钮、列表数据 -->
    <div class="container">
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <!--<el-button type="primary"
                     size="mini"
                     v-hasPermi="['archives:xunchaCase:add']">
            查看申请历史
          </el-button>-->
          <el-button
            type="primary"
            size="mini"
            v-hasPermi="['archives:xunchaCase:add']"
            @click="declareBtn"
          >
            申报
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-hasPermi="['archives:xunchaCase:add']"
            @click="reviseBtn"
            :disabled="!isEdit"
          >
            修订
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-hasPermi="['archives:xunchaCase:add']"
            @click="abolishBtn"
            :disabled="!isEdit"
          >
            废止
          </el-button>
          <el-button type="primary" size="mini" @click="toprocinst">
            查看申请历史
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-hasPermi="['archives:xunchaCase:add']"
            :disabled="ids.length === 0"
            @click="batchDownload"
          >
            批量下载
          </el-button>
        </el-col>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>
      <!-- 列表 -->
      <el-table
        v-loading="loading"
        :data="xunchaCaseList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column
          label="文件名称"
          width="220"
          prop="fileName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="领域类型"
          width="180"
          align="center"
          prop="domainType"
          :formatter="xdomainTypeFormat"
        />
        <el-table-column
          label="版本"
          width="120"
          align="left"
          prop="versions"
        />
        <el-table-column label="发布时间" width="180" align="center">
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.createdTime, "{y}-{m}-{d} {h}:{i}:{s}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.status === '1'">正常</span>
            <span v-if="scope.row.status === '2'">修订中</span>
            <span v-if="scope.row.status === '3'">废止中</span>
          </template>
        </el-table-column>
        <el-table-column label="预览量" align="center" prop="previewNum" />
        <el-table-column label="下载量" align="center" prop="downloadNum" />
        <el-table-column label="小巡同步状态" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.indexStatus === '0'">未同步</span>
            <span v-if="scope.row.indexStatus === '1'">已同步</span>
            <span v-if="scope.row.indexStatus === '2'">半同步(仅名称)</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="onPreview(scope.row.fileId, scope.row.caseId)"
              >预览</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="onDownload(scope.row.fileId, scope.row.caseId)"
              >下载</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row.caseId)"
              >查看</el-button
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
    <!-- 申报、修订、废止页面组件 -->
    <el-dialog
      :title="title"
      :visible="open"
      width="1000px"
      :before-close="handleClose"
    >
      <case-method-form
      v-if="open"
        :caseType="caseType"
        :caseId="caseId"
        @close="handleClose"
      />
    </el-dialog>
    <!-- 详情查看页面 -->
    <el-dialog
      title="查看"
      :visible="isCheck"
      width="1000px"
      :before-close="handleCloseCheck"
    >
      <div style="min-height: 450px">
        <el-tabs v-model="activeName">
          <el-tab-pane label="基础信息" name="0">
            <el-form
              ref="form"
              :model="form"
              :rules="rules"
              label-width="100px"
              disabled
            >
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-form-item label="申请类型" prop="applyType">
                    <el-select
                      v-model="form.applyType"
                      placeholder="请选择申请类型"
                      style="width: 320px"
                      disabled
                    >
                      <el-option
                        v-for="dict in applyTypeSelectData"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6" :offset="6">
                  <el-form-item label="领域类型" prop="domainType">
                    <el-select
                      v-model="form.domainType"
                      placeholder="请选择领域类型"
                      style="width: 320px"
                    >
                      <el-option
                        v-for="dict in domainTypeSelectData"
                        :key="dict.dataId"
                        :label="dict.dataName"
                        :value="dict.dataCode"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-form-item label="案例数量" prop="domainNum">
                    <el-input
                      v-model="form.domainNum"
                      placeholder="请输入领域案例数量"
                      style="width: 320px"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6" :offset="6"></el-col>
              </el-row>
              <el-form-item label="文件名称" prop="fileName">
                <a
                  href="javascript:;"
                  @click="onDownload(form.fileId, form.caseId)"
                  style="color: #1590ff"
                  >{{ form.fileName }}</a
                >
              </el-form-item>
              <el-form-item label="文号" prop="controlNo">
                <el-input v-model="form.controlNo" placeholder="请输入内容" maxlength="500"/>
              </el-form-item>
              <el-form-item label="版本" prop="versions">
                <el-input v-model="form.versions" placeholder="请输入内容" maxlength="500"/>
              </el-form-item>
              <el-form-item label="说明" prop="remark">
                <el-input
                  v-model="form.remark"
                  type="textarea"
                  maxlength="500"
                  show-word-limit
                  placeholder="请输入内容"
                />
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="修订记录" name="1">
            <el-table :data="caseHistoryInfoList">
              <el-table-column
                type="index"
                label="序号"
                width="80"
                align="center"
              ></el-table-column>
              <el-table-column
                label="修订人"
                align="center"
                prop="nickName"
              ></el-table-column>
              <el-table-column label="修订时间" align="center">
                <template slot-scope="scope">
                  <span>{{
                    parseTime(scope.row.updatedTime, "{y}-{m}-{d} {h}:{i}:{s}")
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="修订内容"
                align="center"
                prop="remark"
              ></el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listXunchaCase,
  getXunchaCase,
  delXunchaCase,
  addXunchaCase,
  updateXunchaCase,
  previewAndDownloadPlusOne,
  downloadPlusMuch,
} from "@/api/xuncha/archives/xunchaCase";
import {
  listXunchaCaseProcess,
  getXunchaCaseProcess,
  delXunchaCaseProcess,
  addXunchaCaseProcess,
  updateXunchaCaseProcess,
  getCaseHistoryInfo,
} from "@/api/xuncha/archives/xunchaCaseProcess";
import { selectDictData } from "@/api/xuncha/xunchaDictData";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import uploadForm from "@/components/CommonUpload/uploadForm";
import caseMethodForm from "@/views/xuncha/archives/case/caseMethodForm";
import PageTitle from "@/components/PageTitle";
import {
  downloadFile,
  previewFile,
  downloadZipFile,
} from "@/utils/uploadOperate";

import {
  saveCaseIndexsPoolByBusinessKey,
  saveTimelyCaseIndexsPool,
} from "@/api/xuncha/archives/synchronization";

export default {
  name: "XunchaCase",
  components: { ExpandCollapse, uploadForm, caseMethodForm, PageTitle },
  data() {
    return {
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 是否允许修订和废止
      isEdit: false,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 巡察案例方法库 表格数据
      xunchaCaseList: [],
      // 是否显示详情页面
      isCheck: false,
      activeName: "0",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileName: null,
        domainType: null,
      },
      // 申请类型下拉框，系统字典
      applyTypeSelectData: [],
      // 领域类型下拉框，功能字典
      domainTypeSelectData: [],
      // 是否显示文件上传
      uploadFormShow: false,
      // 文件上传按钮文字
      uploadText: "文件上传",
      // 操作的申请ID，用于子组件传递
      caseId: "",
      // 申报 1、修订 2、废止类型 3
      caseType: 1,
      // 表单参数
      form: {},
      rules: {
        domainType: [
          { required: true, message: "领域类型不能为空", trigger: "blur" },
        ],
        fileId: [
          { required: true, message: "案例数量不能为空", trigger: "blur" },
        ],
        domainNum: [
          { required: true, message: "文件不能为空", trigger: "blur" },
        ],
        fileName: [
          { required: true, message: "文件名称不能为空", trigger: "blur" },
        ],
      },
      // 文件历史信息
      caseHistoryInfoList: [],
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    // 获得申请类型下拉框数据
    this.getDicts("case_apply_type").then((response) => {
      this.applyTypeSelectData = response.data || [];
    });
    // 领域类型下拉框
    selectDictData({ dictType: "domain_type" }).then((res) => {
      this.domainTypeSelectData = res.data || [];
      let typeName = this.$route.query.typeName;
      if (typeName) {
        let result = this.domainTypeSelectData.find(i => i.dataName == typeName);
        if(result) this.queryParams.domainType = result.dataCode;
      }
      this.getList();
    });
  },
  methods: {
    // 领域类型字典翻译
    xdomainTypeFormat(row, column) {
      for (let index in this.domainTypeSelectData) {
        if (this.domainTypeSelectData[index].dataCode === row.domainType) {
          return this.domainTypeSelectData[index].dataName;
        }
      }
      return row.domainType;
    },

    /** 查询巡察案例方法库 列表 */
    getList() {
      this.loading = true;
      listXunchaCase(this.queryParams).then((response) => {
        this.xunchaCaseList = response.rows || [];
        this.total = +response.total;
        this.loading = false;
      });
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
    // 详情查看按钮
    handleQueryOption(caseId) {
      this.isCheck = true;
      getXunchaCase(caseId).then((res) => {
        this.form = res.data;
        this.form.applyType = "B";
      });
      getCaseHistoryInfo(caseId).then((res) => {
        if (res.data.length !== 0) {
          this.caseHistoryInfoList = res.data;
        } else {
          this.caseHistoryInfoList = [];
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.caseId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.reset();
      const caseId = row.caseId || this.ids;
      getXunchaCase(caseId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改巡察案例方法库 ";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const caseIds = row.caseId || this.ids;
      this.$confirm(
        '是否确认删除巡察案例方法库 编号为"' + caseIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delXunchaCase(caseIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 申报按钮操作 */
    declareBtn() {
      this.title = "文件申报";
      this.caseId = "";
      this.caseType = 1;
      this.open = true;
    },
    /** 修订按钮操作 */
    reviseBtn() {
      this.title = "文件修订";
      this.caseId = this.ids[0];
      this.caseType = 2;
      this.open = true;
    },
    /** 废止按钮操作 */
    abolishBtn() {
      this.title = "文件废止";
      this.caseId = this.ids[0];
      this.caseType = 3;
      this.open = true;
    },
    /** 打开新页面至我的工单 */
    toprocinst() {
      let routeUrl = this.$router.resolve({
        path: "/procinst",
        query:{
          businessType:"ALFFK"
        }
      });
      window.open(routeUrl.href, "_blank");
    },
    // 点击上传文件
    uploadFileBtn() {
      this.uploadText = "上传文件";
      this.uploadFormShow = true;
    },
    // 文件上传成功
    fileSuccess(files) {
      this.uploadText = "重新上传";
      this.msgSuccess("上传成功");
      this.form.fileId = files[0].result.data.fileId;
      this.form.fileName = files[0].result.data.fileName;
    },
    // 点击暂存按钮
    saveBtn() {
      // 刷新列表
      this.getList();
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addXunchaCaseProcess(this.form).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }
          });
        }
      });
    },
    // 下载文件
    onDownload(fileId, caseId) {
      previewAndDownloadPlusOne(caseId, "download");
      downloadFile(fileId);
    },
    // 预览文件
    onPreview(fileId, caseId) {
      previewAndDownloadPlusOne(caseId, "preview");
      previewFile(fileId);
    },
    // 关闭申报、修订、废止弹窗
    handleClose() {
      // 刷新列表
      this.getList();
      this.open = false;
    },
    // 关闭查看页面
    handleCloseCheck() {
      this.isCheck = false;
    },
    // 批量下载
    batchDownload() {

      // 记录下载量
      downloadPlusMuch(this.ids);
      let files = [];
      this.ids.forEach((caseId) => {
        this.xunchaCaseList.forEach((xuncaCase) => {
          if (caseId === xuncaCase.caseId) {
            files.push(xuncaCase.fileId);
          }
        });
      });
      downloadZipFile(files,"案例方法文件");
    },
  },
  watch: {
    // 如果状态为 修订中 或 废止中 那么不可编辑
    ids(value) {
      this.isEdit = false;
      if (this.ids.length === 1) {
        this.xunchaCaseList.forEach((item) => {
          if (item.caseId === this.ids[0]) {
            if (item.status === "1") {
              this.isEdit = true;
            }
          }
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog-title {
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

.dialog-context {
  .dialog-title {
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

  .context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;

    .operation {
      position: absolute;
      right: 0;
      left: 0;
      top: -30px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 40px;
    }
  }
}
</style>
