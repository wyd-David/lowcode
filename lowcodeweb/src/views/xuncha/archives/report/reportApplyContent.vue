<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="申请id" prop="applyId">
            <el-input
              v-model="queryParams.applyId"
              placeholder="请输入申请id"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="清单id" prop="contentId">
            <el-input
              v-model="queryParams.contentId"
              placeholder="请输入清单id"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="全部目录信息" prop="fullContentName">
            <el-input
              v-model="queryParams.fullContentName"
              placeholder="请输入全部目录信息"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="目录名称" prop="contentName">
            <el-input
              v-model="queryParams.contentName"
              placeholder="请输入目录名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="资料提供方" prop="archiveOrgType">
            <el-select v-model="queryParams.archiveOrgType" placeholder="请选择资料提供方" clearable size="small">
              <el-option label="请选择字典生成" value="" />
            </el-select>
          </el-form-item>
          <el-form-item label="资料移交时间" prop="archiveDate">
            <el-input
              v-model="queryParams.archiveDate"
              placeholder="请输入资料移交时间"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="是否必传" prop="requireFlag">
            <el-input
              v-model="queryParams.requireFlag"
              placeholder="请输入是否必传"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="说明" prop="remark">
            <el-input
              v-model="queryParams.remark"
              placeholder="请输入说明"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="创建人" prop="createdBy">
            <el-input
              v-model="queryParams.createdBy"
              placeholder="请输入创建人"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="创建时间" prop="createdTime">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.createdTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择创建时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="更新人" prop="updatedBy">
            <el-input
              v-model="queryParams.updatedBy"
              placeholder="请输入更新人"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="更新时间" prop="updatedTime">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.updatedTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择更新时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="删除标志 0存在；1删除；" prop="delFlag">
            <el-input
              v-model="queryParams.delFlag"
              placeholder="请输入删除标志 0存在；1删除；"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button  size="mini" @click="resetQuery">重置</el-button>
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
            v-hasPermi="['system:content:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:content:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:content:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-lock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(1)"
            v-hasPermi="['system:content:remove']"
          >禁用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-unlock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(0)"
            v-hasPermi="['system:content:remove']"
          >启用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['system:content:importData']"
          >导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:content:importData']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="contentList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="ID" align="center" prop="id" />
        <el-table-column label="申请id" align="center" prop="applyId" />
        <el-table-column label="清单id" align="center" prop="contentId" />
        <el-table-column label="全部目录信息" align="center" prop="fullContentName" />
        <el-table-column label="目录名称" align="center" prop="contentName" />
        <el-table-column label="资料提供方" align="center" prop="archiveOrgType" />
        <el-table-column label="资料移交时间" align="center" prop="archiveDate" />
        <el-table-column label="是否必传" align="center" prop="requireFlag" />
        <el-table-column label="说明" align="center" prop="remark" />
        <el-table-column label="创建人" align="center" prop="createdBy" />
        <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新人" align="center" prop="updatedBy" />
        <el-table-column label="更新时间" align="center" prop="updatedTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updatedTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="删除标志 0存在；1删除；" align="center" prop="delFlag" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
            >查看</el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:content:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:content:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 添加或修改巡察报告资料库_文件申请清单 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="申请id" prop="applyId">
          <el-input v-model="form.applyId" placeholder="请输入申请id" />
        </el-form-item>
        <el-form-item label="清单id" prop="contentId">
          <el-input v-model="form.contentId" placeholder="请输入清单id" />
        </el-form-item>
        <el-form-item label="全部目录信息" prop="fullContentName">
          <el-input v-model="form.fullContentName" placeholder="请输入全部目录信息" />
        </el-form-item>
        <el-form-item label="目录名称" prop="contentName">
          <el-input v-model="form.contentName" placeholder="请输入目录名称" />
        </el-form-item>
        <el-form-item label="资料提供方" prop="archiveOrgType">
          <el-select v-model="form.archiveOrgType" placeholder="请选择资料提供方">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="资料移交时间" prop="archiveDate">
          <el-input v-model="form.archiveDate" placeholder="请输入资料移交时间" />
        </el-form-item>
        <el-form-item label="是否必传" prop="requireFlag">
          <el-input v-model="form.requireFlag" placeholder="请输入是否必传" />
        </el-form-item>
        <el-form-item label="说明" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入说明" />
        </el-form-item>
        <el-form-item label="创建人" prop="createdBy">
          <el-input v-model="form.createdBy" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createdTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.createdTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新人" prop="updatedBy">
          <el-input v-model="form.updatedBy" placeholder="请输入更新人" />
        </el-form-item>
        <el-form-item label="更新时间" prop="updatedTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.updatedTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志 0存在；1删除；" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志 0存在；1删除；" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看巡察报告资料库_文件申请清单 对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="申请id:" prop="applyId">
          {{form.applyId}}
        </el-form-item>
        <el-form-item label="清单id:" prop="contentId">
          {{form.contentId}}
        </el-form-item>
        <el-form-item label="全部目录信息:" prop="fullContentName">
          {{form.fullContentName}}
        </el-form-item>
        <el-form-item label="目录名称:" prop="contentName">
          {{form.contentName}}
        </el-form-item>
        <el-form-item label="资料提供方:" prop="archiveOrgType">
          {{form.archiveOrgType | FieldFilter(archiveOrgTypeOptions)}}
        </el-form-item>
        <el-form-item label="资料移交时间:" prop="archiveDate">
          {{form.archiveDate}}
        </el-form-item>
        <el-form-item label="是否必传:" prop="requireFlag">
          {{form.requireFlag}}
        </el-form-item>
        <el-form-item label="说明:" prop="remark">
          {{form.remark}}
        </el-form-item>
        <el-form-item label="创建人:" prop="createdBy">
          {{form.createdBy}}
        </el-form-item>
        <el-form-item label="创建时间:" prop="createdTime">
          {{form.createdTime | filterDate}}
        </el-form-item>
        <el-form-item label="更新人:" prop="updatedBy">
          {{form.updatedBy}}
        </el-form-item>
        <el-form-item label="更新时间:" prop="updatedTime">
          {{form.updatedTime | filterDate}}
        </el-form-item>
        <el-form-item label="删除标志 0存在；1删除；:" prop="delFlag">
          {{form.delFlag}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 巡察报告资料库_文件申请清单 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getToken } from "@/utils/auth";
  import { listContent, getContent, delContent, addContent, updateContent, exportContent,importTemplate, disableEnable } from "@/api/xuncha/archives/xunchaReportApplyContent";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
  import PageTitle from "@/components/PageTitle"
  export default {
    name: "Content",
    components: { ExpandCollapse,PageTitle },
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
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 巡察报告资料库_文件申请清单 表格数据
        contentList: [],
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
          url: process.env.VUE_APP_BASE_API + "/system/content/importData",
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          applyId: null,
          contentId: null,
          fullContentName: null,
          contentName: null,
          archiveOrgType: null,
          archiveDate: null,
          requireFlag: null,
          remark: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          applyId: [
            { required: true, message: "申请id不能为空", trigger: "blur" }
          ],
          contentId: [
            { required: true, message: "清单id不能为空", trigger: "blur" }
          ],
          fullContentName: [
            { required: true, message: "全部目录信息不能为空", trigger: "blur" }
          ],
          archiveOrgType: [
            { required: true, message: "资料提供方不能为空", trigger: "change" }
          ],
          requireFlag: [
            { required: true, message: "是否必传不能为空", trigger: "blur" }
          ],
          createdBy: [
            { required: true, message: "创建人不能为空", trigger: "blur" }
          ],
          createdTime: [
            { required: true, message: "创建时间不能为空", trigger: "blur" }
          ],
          delFlag: [
            { required: true, message: "删除标志 0存在；1删除；不能为空", trigger: "blur" }
          ]
        }
      };
    },filters: {
      filterDate(val) {
        return formatDate(val);
      },
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询巡察报告资料库_文件申请清单 列表 */
      getList() {
        this.loading = true;
        listContent(this.queryParams).then(response => {
          this.contentList = response.rows || [];
          this.total = response.total;
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
          fullContentName: null,
          contentName: null,
          archiveOrgType: null,
          archiveDate: null,
          requireFlag: null,
          remark: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加巡察报告资料库_文件申请清单 ";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids
        getContent(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改巡察报告资料库_文件申请清单 ";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const id = row.id || this.ids
        getContent(id).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看巡察报告资料库_文件申请清单 ";
        });
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateContent(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addContent(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除巡察报告资料库_文件申请清单 编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delContent(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || [];
        this.$confirm('是否确认'+ (type === 0 ? "启用" : "禁用")+'?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return disableEnable({ids: ids.toString(), disableEnableState: type});
        }).then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有巡察报告资料库_文件申请清单 数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportContent(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "巡察报告资料库_文件申请清单 数据导入";
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
    }
  };
</script>



<style lang="scss" scoped>
  .app-container{
    background: #F5F7F9;
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
  .utlsBtn{
    /deep/ .el-button--mini{
      padding: 9px 15px;
    }
    .normalBtn{
      /deep/ .el-button--mini{
        padding: 8px;
      }
    }
  }
  .container{
    padding: 12px;
    background: #fff;
    border-radius: 2px;
    /deep/ .el-table__header-wrapper{
      th{
        background: rgba(45, 140, 240, 0.04) !important;
        font-size: 14px;
        font-family: PingFangSC-Semibold, PingFang SC;
        font-weight: 600;
        color: rgba(23, 35, 61, 0.75);
      }
    }
    .pageBox{
      height: 32px;
      .pagination-container{
        padding: 0 !important;
        margin: 0;
        height: 32px;
        margin-top: 12px;
        /deep/ .number,
        /deep/ .btn-prev,
        /deep/ .btn-next{
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
        /deep/ .el-input__inner{
          height: 32px;
          line-height: 32px;
        }
      }
      /deep/ .el-pagination__total{
        line-height: 32px;
      }
    }
  }
  /deep/ .el-form-item__label{
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
  }
</style>

