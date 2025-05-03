<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="实例ID" prop="instanceId">
            <el-input v-model="queryParams.instanceId" placeholder="请输入实例ID" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="实例编码" prop="instanceCode">
            <el-input v-model="queryParams.instanceCode" placeholder="请输入实例编码" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="分配单元编码" prop="quotaUnitCode">
            <el-input v-model="queryParams.quotaUnitCode" placeholder="请输入分配单元编码" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="实例名" prop="instanceName">
            <el-input v-model="queryParams.instanceName" placeholder="请输入实例名" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="实例显示名" prop="instanceDisplayName">
            <el-input v-model="queryParams.instanceDisplayName" placeholder="请输入实例显示名" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="引擎类型" prop="appClassCode">
            <el-select v-model="queryParams.appClassCode" placeholder="请选择引擎类型" clearable size="small">
              <el-option v-for="dict in appClassCodeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="引擎版本号" prop="versionCode">
            <el-input v-model="queryParams.versionCode" placeholder="请输入引擎版本号" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="引擎版本号" prop="createTime">
            <el-date-picker clearable size="small" style="width: 200px" v-model="queryParams.createTime" type="date"
              value-format="yyyy-MM-dd" placeholder="选择引擎版本号">
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['datasource:appInstance:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['datasource:appInstance:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['datasource:appInstance:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-lock" size="mini" :disabled="multiple" @click="disabledBtnClick(1)"
            v-hasPermi="['datasource:appInstance:remove']">禁用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-unlock" size="mini" :disabled="multiple" @click="disabledBtnClick(0)"
            v-hasPermi="['datasource:appInstance:remove']">启用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['datasource:appInstance:importData']">导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['datasource:appInstance:importData']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="appInstanceList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="单元实例ID" align="center" prop="instanceId" />
        <el-table-column label="实例编码" align="center" prop="instanceCode" />
        <el-table-column label="分配单元编码" align="center" prop="quotaUnitCode" />
        <el-table-column label="实例名" align="center" prop="instanceName" />
        <el-table-column label="实例显示名" align="center" prop="instanceDisplayName" />
        <el-table-column label="实例描述" align="center" prop="instanceDesc" />
        <el-table-column label="引擎类型" align="center" prop="appClassCode" :formatter="appClassCodeFormat" />
        <el-table-column label="引擎版本号" align="center" prop="versionCode" />
        <el-table-column label="创建人" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建人" align="center" prop="lastUpdateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
              v-hasPermi="['datasource:appInstance:edit']">修改</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
              v-hasPermi="['datasource:appInstance:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改分配实例对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="分配单元编码" prop="quotaUnitCode">
          <el-input v-model="form.quotaUnitCode" placeholder="请输入分配单元编码" />
        </el-form-item>
        <el-form-item label="实例名" prop="instanceName">
          <el-input v-model="form.instanceName" placeholder="请输入实例名" />
        </el-form-item>
        <el-form-item label="实例显示名" prop="instanceDisplayName">
          <el-input v-model="form.instanceDisplayName" placeholder="请输入实例显示名" />
        </el-form-item>
        <el-form-item label="实例描述" prop="instanceDesc">
          <el-input v-model="form.instanceDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="引擎类型" prop="appClassCode">
          <el-select v-model="form.appClassCode" placeholder="请选择引擎类型">
            <el-option v-for="dict in appClassCodeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="引擎版本号" prop="versionCode">
          <el-input v-model="form.versionCode" placeholder="请输入引擎版本号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看分配实例对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="分配单元编码:" prop="quotaUnitCode">
          {{form.quotaUnitCode}}
        </el-form-item>
        <el-form-item label="实例名:" prop="instanceName">
          {{form.instanceName}}
        </el-form-item>
        <el-form-item label="实例显示名:" prop="instanceDisplayName">
          {{form.instanceDisplayName}}
        </el-form-item>
        <el-form-item label="实例描述:" prop="instanceDesc">
          {{form.instanceDesc}}
        </el-form-item>
        <el-form-item label="引擎类型:" prop="appClassCode">
          {{form.appClassCode | meta_datasourceFieldFilter(appClassCodeOptions)}}
        </el-form-item>
        <el-form-item label="引擎版本号:" prop="versionCode">
          {{form.versionCode}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 分配实例导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
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
  import {
    getToken
  } from "@/utils/auth";
  import {
    listAppInstance,
    getAppInstance,
    delAppInstance,
    addAppInstance,
    updateAppInstance,
    exportAppInstance,
    importTemplate,
    disableEnable
  } from "@/api/datasource/appInstance";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "AppInstance",
    components: {
      ExpandCollapse
    },
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
        // 分配实例表格数据
        appInstanceList: [],
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
          headers: {
            Authorization: "Bearer " + getToken()
          },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/datasource/appInstance/importData",
        },
        // 引擎类型字典
        appClassCodeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          instanceId: null,
          instanceCode: null,
          quotaUnitCode: null,
          instanceName: null,
          instanceDisplayName: null,
          instanceDesc: null,
          appClassCode: null,
          versionCode: null,
          createTime: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {


              quotaUnitCode: [{
                required: true,
                message: "分配单元编码不能为空",
                trigger: "blur"
              }],
              instanceName: [{
                required: true,
                message: "实例名不能为空",
                trigger: "blur"
              }],
            }
          };
        },
        filters: {
          filterDate(val) {
            return formatDate(val);
          },
          meta_datasourceFieldFilter(val, optionArr) {
            var arr = optionArr.filter(item => item.dictValue === val);
            if (!arr.length) return '';
            return arr[0].dictLabel;
          },
        },
        created() {
          this.getList();
          this.getDicts("meta_datasource").then(response => {
            this.appClassCodeOptions = response.data || [];
          });
        },
        methods: {
          /** 查询分配实例列表 */
          getList() {
            this.loading = true;
            listAppInstance(this.queryParams).then(response => {
              this.appInstanceList = response.rows || [];
              this.total = response.total;
              this.loading = false;
            });
          },
          // 引擎类型字典翻译
          appClassCodeFormat(row, column) {
            return this.selectDictLabel(this.appClassCodeOptions, row.appClassCode);
          },
          // 取消按钮
          cancel() {
            this.open = false;
            this.reset();
          },
          // 表单重置
          reset() {
            this.form = {
              instanceId: null,
              parentId: null,
              path: null,
              instanceCode: null,
              appId: null,
              accountId: null,
              quotaUnitCode: null,
              instanceName: null,
              instanceDisplayName: null,
              instanceDesc: null,
              appClassCode: null,
              versionCode: null,
              instanceType: null,
              storageType: null,
              createUserId: null,
              createUserName: null,
              createTime: null,
              lastUpdateUserId: null,
              lastUpdateUserName: null,
              lastUpdateTime: null,
              belongSpaceId: null,
              ownerType: null,
              ownerId: null,
              ownerName: null,
              securityGradeId: null,
              sortId: null,
              isDeleted: null,
              storageStats: null,
              isExchangeArea: null
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
            this.ids = selection.map(item => item.instanceId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
          },
          /** 新增按钮操作 */
          handleAdd() {
            this.disabled = false;
            this.reset();
            this.open = true;
            this.title = "添加分配实例";
          },
          /** 修改按钮操作 */
          handleUpdate(row) {
            this.disabled = false;
            this.reset();
            const instanceId = row.instanceId || this.ids
            getAppInstance(instanceId).then(response => {
              this.form = response.data;
              this.open = true;
              this.title = "修改分配实例";
            });
          },
          /**详情按钮*/
          handleQueryOption(row) {
            // this.reset();
            const instanceId = row.instanceId || this.ids
            getAppInstance(instanceId).then(response => {
              this.form = response.data;
              this.isOpenQuery = true;
              this.title = "查看分配实例";
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
                if (this.form.instanceId != null) {
                  updateAppInstance(this.form).then(response => {
                    if (response.code === 200) {
                      this.msgSuccess("修改成功");
                      this.open = false;
                      this.getList();
                    }
                  });
                } else {
                  addAppInstance(this.form).then(response => {
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
            const instanceIds = row.instanceId || this.ids;
            this.$confirm('是否确认删除分配实例编号为"' + instanceIds + '"的数据项?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return delAppInstance(instanceIds);
            }).then(() => {
              this.getList();
              this.msgSuccess("删除成功");
            }).catch(function() {});
          },
          /** 禁用或启用操作*/
          disabledBtnClick(type) {
            var ids = this.ids || [];
            this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return disableEnable({
                ids: ids.toString(),
                disableEnableState: type
              });
            }).then(() => {
              this.getList();
              this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
            }).catch(function() {});
          },
          /** 导出按钮操作 */
          handleExport() {
            const queryParams = this.queryParams;
            this.$confirm('是否确认导出所有分配实例数据项?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return exportAppInstance(queryParams);
            }).then(response => {
              this.download(response.msg);
            }).catch(function() {});
          },
          /** 导入按钮操作 */
          openImportTable() {
            this.upload.title = "分配实例数据导入";
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
            this.$alert(response.msg, "导入结果", {
              dangerouslyUseHTMLString: true
            });
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
  .app-container {
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

    /deep/ .el-table__header-wrapper {
      th {
        background: rgba(45, 140, 240, 0.04) !important;
        font-size: 14px;
        font-family: PingFangSC-Semibold, PingFang SC;
        font-weight: 600;
        color: rgba(23, 35, 61, 0.75);
      }
    }

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
