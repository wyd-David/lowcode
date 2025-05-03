<template>
  <div class="app-container">
    <permission-tabs ref="pt" @ptClick="ptClick"></permission-tabs>
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          v-if="activeName == 'XunchaReportFile'"
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="被巡察单位" prop="orgId">
            <company
              v-model="queryParams.orgId"
              :label.sync="queryParams.orgName"
              size="small"
              width="205.4px"
            />
          </el-form-item>
          <el-form-item label="资料名称" prop="fileName">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入文件名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="归档范围" prop="archiveOrgType">
            <el-select
              v-model="queryParams.archiveOrgType"
              placeholder="请选择归档范围"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in archiveOrgTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="巡察轮次" prop="xunchaLunciId">
            <lunci
              v-model="queryParams.xunchaLunciId"
              :label.sync="queryParams.xunchaLunciName"
              width="205.4px"
            />
          </el-form-item>
          <el-form-item label="巡察年份:" prop="xunchaDate">
            <el-date-picker
              style="width: 205.4px"
              v-model="queryParams.xunchaDate"
              type="year"
              value-format="yyyy"
              placeholder="选择年"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="巡察组" prop="xunchaGroupId">
            <group
              v-model="queryParams.xunchaGroupId"
              :label.sync="queryParams.xunchaGroup"
              width="205.4px"
            />
          </el-form-item>
        </el-form>

        <el-form
          v-else
          :model="queryParams"
          ref="queryForm1"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="报告名称" prop="fileName">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入文件名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="所属目录:">
            <treeselect
              style="width: 205.4px; line-height: 32px; top: 12px"
              v-model="queryParams.reportCatalogue"
              :options="parentSelect"
              :normalizer="normalizer"
              :show-count="true"
              placeholder="请选择报告目录"
              ref="tree"
            />
          </el-form-item> -->
          <el-form-item label="创建人:" prop="createdBy">
            <el-input
              v-model="queryParams.createdBy"
              placeholder="请输入创建人名称"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="创建时间:" prop="createdTime">
            <el-date-picker
              style="width: 205.4px"
              v-model="queryParams.createdTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="修改人:" prop="updatedBy">
            <el-input
              v-model="queryParams.updatedBy"
              placeholder="请输入修改人名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="修改时间:" prop="updatedTime">
            <el-date-picker
              style="width: 205.4px"
              v-model="queryParams.updatedTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择"
            >
            </el-date-picker>
          </el-form-item> -->
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
        <el-col :span="1.5" v-if="activeName == 'XunchaReportFile1'">
          <el-button type="primary" size="mini" @click="addHandle">
            新增
          </el-button>
        </el-col>
        <el-col :span="1.5" v-if="activeName == 'XunchaReportFile1'">
          <el-button
            :disabled="ids.length === 0"
            type="primary"
            size="mini"
            @click="removeHandle"
          >
            删除
          </el-button>
        </el-col>
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

      <el-table
        v-if="activeName == 'XunchaReportFile'"
        v-loading="loading"
        :data="fileList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column
          label="报告名称"
          width="220"
          prop="datumName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="所属目录"
          width="200"
          prop="fullName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="被巡察单位"
          width="120"
          align="center"
          prop="orgName"
        />
        <el-table-column label="巡察年份" align="center" prop="xunchaDate" />
        <el-table-column
          label="巡察批次"
          width="150"
          align="center"
          prop="xunchaPiciName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="巡察组"
          width="150"
          align="center"
          prop="xunchaGroup"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="预览量" align="center" prop="previewSum" />
        <el-table-column label="下载量" align="center" prop="downloadSum" />
        <el-table-column label="操作" align="center" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="onPreview(scope.row.fileId, scope.row.id)"
              >预览</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="onDownload(scope.row.fileId, scope.row.id)"
              >下载</el-button
            >
            <!-- <el-button size="mini" type="text" @click="handleSync(scope.row)">同步</el-button> -->
          </template>
        </el-table-column>
      </el-table>

      <el-table
        v-else
        v-loading="loading"
        :data="fileList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column
          label="报告名称"
          width="220"
          prop="datumName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="所属目录"
          width="200"
          prop="fullName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="创建人"
          width="200"
          prop="createdBy"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          v-if="activeName == 'XunchaReportFile1'"
          label="创建时间"
          width="200"
        >
          <template slot-scope="scope">
            {{ filterDate(scope.row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column
          label="修改人"
          width="200"
          prop="updatedBy"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          v-if="activeName == 'XunchaReportFile1'"
          label="修改时间"
          width="200"
          prop="updatedTime"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            {{ filterDate(scope.row.updatedTime) }}
          </template>
        </el-table-column>
        <el-table-column label="预览量" align="center" prop="previewSum" />
        <el-table-column label="下载量" align="center" prop="downloadSum" />
        <el-table-column label="操作" align="center" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="onPreview(scope.row.fileId, scope.row.id)"
              >预览</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="onDownload(scope.row.fileId, scope.row.id)"
              >下载</el-button
            >
            <el-button size="mini" type="text" @click="editHandle(scope.row)"
              >修改</el-button
            >
            <!-- <el-button size="mini" type="text" @click="handleSync(scope.row)">同步</el-button> -->
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

    <el-dialog
      :title="openObj.title"
      :visible.sync="openObj.open"
      width="1000px"
      @close="addClose"
    >
      <div class="dialog-content">
        <div class="dialog-title">
          <span class="line"></span><span>文件信息</span>
        </div>
        <div class="context-row">
          <el-form
            label-width="100px"
            :model="form"
            ref="formRef"
            :rules="formRole"
          >
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="报告类型:" prop="reportType">
                  <el-radio
                    disabled
                    v-for="item in reportTypeOptions"
                    v-model="form.reportType"
                    :label="item.dataCode"
                    :key="item.dataCode"
                    >{{ item.dataName }}</el-radio
                  >
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="报告目录:" prop="reportCatalogue">
                  <treeselect
                    noOptionsText="暂无数据"
                    :disabled="!!form.id"
                    v-model="form.reportCatalogue"
                    :options="parentSelect"
                    :normalizer="normalizer"
                    :show-count="true"
                    @select="nodeSelectHandle"
                    placeholder="请选择报告目录"
                    ref="tree"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="报告文件:" prop="fileId">
                  <el-button
                    type="text"
                    :title="form.fileName"
                    class="accessory"
                    style="font-size: 12px; color: #1590ff"
                  >
                    {{ form.fileName }}
                  </el-button>
                  <el-button type="primary" size="mini" @click="uploadFile">
                    <span v-if="form.fileId">重新上传</span>
                    <span v-else>上传文件</span>
                  </el-button>
                  <el-button
                    type="primary"
                    size="mini"
                    @click="previewClick"
                    v-if="form.fileId"
                    :disabled="false"
                    >预览</el-button
                  >
                  <el-button
                    type="primary"
                    size="mini"
                    @click="downloadFile"
                    v-if="form.fileId"
                    :disabled="false"
                    >下载</el-button
                  >
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="报告名称:" prop="reportName">
                  <el-input
                    v-model="form.reportName"
                    placeholder="请输入报告名称"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="openObj.open = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="文件上传"
      :visible.sync="dialogVisible"
      width="50%"
      @open="initUpload"
      :before-close="handleClose"
    >
      <div>
        <CommonUpload
          v-if="isShowUpload"
          ref="CommonUpload"
          :single-file="true"
          @file-success="onFileSuccess"
          @file-removed="onFileRemoved"
        ></CommonUpload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm" :disabled="isConfirm"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import permissionTabs from "@/components/PermissionTabs/index";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {
  listFile,
  downloadAndPreviewPlusMuch,
  addReportFile,
  editFileAndContent,
  remove,
} from "@/api/xuncha/archives/xunchaReportFile";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { parseTime } from "@/utils/ruoyi";
import { selectDictData } from "@/api/xuncha/xunchaDictData";
import {
  saveProcessIndexsPoolByBusinessKey,
  saveTimelyProcessIndexsPool,
} from "@/api/xuncha/archives/synchronization";
import {
  downloadFile,
  previewFile,
  downloadZipFile,
} from "@/utils/uploadOperate";
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
import Company from "@/components/XunCha/Company/index";
import Lunci from "@/components/XunCha/LunCi/index";
import Group from "@/components/XunCha/Group/index";
import PageTitle from "@/components/PageTitle";
import { treeXunchaReportContent } from "@/api/xuncha/archives/xunchaReportContent";
import { downloadFile as download } from "@/utils/uploadOperate";
export default {
  name: "File",
  components: {
    CommonUpload,
    Treeselect,
    permissionTabs,
    ExpandCollapse,
    Company,
    Lunci,
    Group,
    PageTitle,
  },
  data() {
    return {
      //上级目录下拉数据
      parentSelect: [],
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
      // 巡察报告资料库_文件信息 表格数据
      fileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orgId: null,
        xunchaLunciId: null,
        xunchaLunciName: null,
        xunchaDate: null,
        xunchaGroupId: null,
        xunchaGroup: null,
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
      },
      // 归档范围字典数据
      archiveOrgTypeOptions: [],
      activeName: "XunchaReportFile",
      openObj: {
        open: false,
        title: "新增报告报表",
      },
      form: {
        id: "",
        reportType: "B",
        reportCatalogue: null,
        fileId: "",
        fileName: "",
        reportName: "",
        parentId: "",
      },
      formRole: {
        reportType: [
          { required: true, message: "请选择报告类型", trigger: "blur" },
        ],
        reportCatalogue: [
          { required: true, message: "请选择报告目录", trigger: "blur" },
        ],
        fileId: [{ required: true, message: "请上传文件", trigger: "blur" }],
        reportName: [
          { required: true, message: "请输入报告名称", trigger: "blur" },
        ],
      },
      reportTypeOptions: [],
      dialogVisible: false,
      isConfirm: false,
      file: {},
      isShowUpload: false,
    };
  },
  created() {
    this.getList();
    // 获得归档范围字典数据
    this.getDicts("xuncha_report_content_type").then((response) => {
      this.archiveOrgTypeOptions = response.data || [];
    });
    selectDictData({ dictType: "report_type" }).then((response) => {
      this.reportTypeOptions = response.data || [];
    });
    this.handleOpen();
  },
  methods: {
    removeHandle() {
      if (!this.ids.length) {
        this.msgError("至少选择一个");
        return;
      }
      this.$confirm("确认删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        remove({ id: this.ids.join(",") }).then((res) => {
          if (res.code == 200) {
            this.msgSuccess("删除成功");
            this.getList();
          }
        });
      });
    },
    // 预览文件
    previewClick() {
      previewFile(this.form.fileId);
    },
    downloadFile() {
      download(this.form.fileId);
    },
    addClose() {
      this.$refs["formRef"].resetFields();
      this.form.id = "";
    },
    confirm() {
      if (!this.file) {
        this.$message.warning("请上传文件!");
        return;
      }
      this.form.fileId = this.file.fileId;
      this.form.fileName = this.file.fileName;
      let fileName = this.file.fileName.split(".");
      //去除文件名后缀
      try {
        this.form.reportName = this.file.fileName.substring(
          0,
          this.file.fileName.length - (fileName[fileName.length - 1].length + 1)
        );
      } catch {
        this.form.reportName = this.file.fileName;
      }
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    //成功回调
    onFileSuccess(file, files, fileList) {
      this.file = file.result.data;
      this.isConfirm = false;
    },
    //移除回调
    onFileRemoved(file, files, fileList) {
      this.file = null;
      this.form.fileId = "";
      this.form.fileName = "";
    },
    handleClose() {
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    //打开文件上传清楚之前的记录
    initUpload() {
      this.isConfirm = true;
      this.isShowUpload = true;
    },
    uploadFile(row) {
      this.file = null;
      this.dialogVisible = true;
    },
    nodeSelectHandle(e) {
      this.form.parentId = e.parentId;
    },
    handleOpen() {
      treeXunchaReportContent({ reportType: "B" }).then((res) => {
        let data = res.data.map((item) => {
          item.isSelect = false;
          return item;
        });
        this.parentSelect = this.getTreeData(data);
      });
    },
    getTreeData(data) {
      let temp = [];
      data.forEach((item) => {
        if (item.parentId == 0) {
          temp.push(item);
        } else {
          let parent = data.filter(
            (item2) => item2.contentId == item.parentId
          )[0];
          if (parent) {
            if (!parent.children) {
              parent.children = [];
            }
            parent.children.push(item);
          }
        }
      });
      return temp;
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.contentId,
        label: node.contentName,
        children: node.children,
      };
    },
    addHandle() {
      this.reset();
      this.openObj.title = "新增报告报表";
      this.openObj.open = true;
    },
    editHandle(row) {
      this.openObj.title = "修改报告报表";
      console.log(row);
      this.form.id = row.id;
      this.form.fileId = row.fileId;
      this.form.fileName = row.fileName;
      this.form.reportName = row.datumName;
      this.form.reportCatalogue = row.contentId;
      this.openObj.open = true;
    },
    submitForm() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          this.$confirm("确认提交吗?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            let param = {
              contentList: [
                {
                  contentId: this.form.reportCatalogue,
                  parentId: this.form.parentId,
                  contentName: this.form.reportName,
                  archiveOrgType: this.form.reportType,
                },
              ],
              contentFileList: [
                {
                  contentId: this.form.reportCatalogue,
                  fileId: this.form.fileId,
                  fileName: this.form.fileName,
                  datumName: this.form.reportName,
                },
              ],
            };

            if (this.form.id) {
              editFileAndContent({
                id: this.form.id,
                datumName: this.form.reportName,
                fileId: this.form.fileId,
                fileName: this.form.fileName,
              }).then((res) => {
                if (res.code == 200) {
                  this.msgSuccess("提交成功");
                  this.openObj.open = false;
                }
                this.getList();
              });
            } else {
              addReportFile(param).then((res) => {
                if (res.code == 200) {
                  this.msgSuccess("提交成功");
                  this.openObj.open = false;
                }
                this.getList();
              });
            }
          });
        }
      });
    },
    filterDate(val) {
      return parseTime(val, "{y}-{m}-{d}");
    },
    ptClick(e, o) {
      if (o) {
        this.activeName = o.name;
      }
      this.resetQuery();
      this.getList();
    },
    // 同步按钮
    handleSync(row) {
      if (row.id) {
        saveProcessIndexsPoolByBusinessKey({ id: row.id }).then((res) => {
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
    /** 查询巡察报告资料库_文件信息 列表 */
    getList() {
      this.loading = true;
      let companyId = this.$store.getters.userInfo.companyId;
      let viewAll = false;
      if (
        this.$store.getters.userInfo.department ==
        "巡察工作办公室(党风廉政办公室)"
      ) {
        viewAll = true;
      }
      if (!companyId) {
        viewAll = true;
      }
      listFile({
        ...this.queryParams,
        companyId: companyId,
        reportType: this.activeName == "XunchaReportFile" ? null : "B",
        viewAll: viewAll,
      }).then((response) => {
        this.fileList = response.rows || [];
        this.total = +response.total;
        this.loading = false;
      });
    },
    // 资料提供方字典翻译
    archiveOrgTypeFormat(row, column) {
      return this.selectDictLabel(
        this.archiveOrgTypeOptions,
        row.archiveOrgType
      );
    },
    // 表单重置
    reset() {
      this.form = {
        id: "",
        reportType: "B",
        reportCatalogue: null,
        fileId: "",
        fileName: "",
        reportName: "",
        parentId: "",
      };
      this.resetForm("formRef");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        orgId: null,
        xunchaLunciId: null,
        xunchaLunciName: null,
        xunchaDate: null,
        xunchaGroupId: null,
        xunchaGroup: null,
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
      this.resetForm("queryForm");
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
    // 预览文件
    onPreview(fileId, id) {
      downloadAndPreviewPlusMuch([id], "preview");
      previewFile(fileId);
    },
    // 批量下载
    batchDownload() {
      // 记录下载量
      downloadAndPreviewPlusMuch(this.ids, "download");
      let files = [];
      this.ids.forEach((id) => {
        this.fileList.forEach((process) => {
          if (id === process.id) {
            files.push(process.fileId);
          }
        });
      });
      downloadZipFile(files);
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
}
.accessory {
  max-width: 100px;
  overflow: hidden;
  white-space: nowrap;
  cursor: default;
  display: inline-block;
  text-overflow: ellipsis;
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