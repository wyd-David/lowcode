<template>
  <div class="app-container">
    <div class="search-folder mb12" ref="folder" :class="{'folder-open': isOpen}">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
        <el-form-item label="应用名称" prop="applyName">
          <el-input v-model="queryParams.applyName" placeholder="请输入应用名称" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="queryParams.jobName" placeholder="请输入任务名称" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="执行状态" prop="jobStatus">
          <el-select v-model="queryParams.jobStatus" placeholder="请选择执行状态" clearable size="small">
            <el-option v-for="dict in jobStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue" />
          </el-select>
        </el-form-item>
      </el-form>

      <div class="search-folder-utils">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <!--<el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['sysJobLog:sysJobLog:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['sysJobLog:sysJobLog:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['sysJobLog:sysJobLog:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-lock" size="mini" :disabled="multiple" @click="disabledBtnClick(1)"
            v-hasPermi="['sysJobLog:sysJobLog:remove']">禁用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-unlock" size="mini" :disabled="multiple" @click="disabledBtnClick(0)"
            v-hasPermi="['sysJobLog:sysJobLog:remove']">启用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['sysJobLog:sysJobLog:importData']">导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['sysJobLog:sysJobLog:importData']">导出</el-button>
        </el-col> -->
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="sysJobLogList" @selection-change="handleSelectionChange">
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column label="应用名称" align="center" prop="applyName" />
        <el-table-column label="任务名称" align="center" prop="jobName" />
        <el-table-column label="执行状态" align="center" prop="jobStatus" :formatter="jobStatusFormat" />
        <el-table-column label="IP地址" align="center" prop="jobIpAddr" />
        <el-table-column label="数据总数量" align="left" prop="dataNum" />
        <el-table-column label="执行方法" align="center" prop="exeMethod" />
        <el-table-column label="创建人" align="center" prop="createdBy" />
        <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" fixed="right" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <router-link :to="'/log/data/'+scope.row.dataBatchId" class="link-type">
              <el-button size="mini" type="text">相关数据</el-button>
            </router-link>
            <!-- <el-button size="mini" type="text" icon="el-icon-view" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
              v-hasPermi="['sysJobLog:sysJobLog:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
              v-hasPermi="['sysJobLog:sysJobLog:remove']">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改定时任务日志 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="应用名称" prop="applyName">
          <el-input v-model="form.applyName" placeholder="请输入应用名称" />
        </el-form-item>
        <el-form-item label="具体描述" prop="applyMiaos">
          <el-input v-model="form.applyMiaos" placeholder="请输入具体描述" />
        </el-form-item>
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="form.jobName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="日志信息" prop="jobMessage">
          <el-input v-model="form.jobMessage" placeholder="请输入日志信息" />
        </el-form-item>
        <el-form-item label="执行状态" prop="jobStatus">
          <el-select v-model="form.jobStatus" placeholder="请选择执行状态">
            <el-option v-for="dict in jobStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="异常信息" prop="jobExceptionInfo">
          <el-input v-model="form.jobExceptionInfo" placeholder="请输入异常信息" />
        </el-form-item>
        <el-form-item label="IP地址" prop="jobIpAddr">
          <el-input v-model="form.jobIpAddr" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="业务数据总数量" prop="dataNum">
          <el-input v-model="form.dataNum" placeholder="请输入业务数据总数量" />
        </el-form-item>
        <el-form-item label="数据批次ID" prop="dataBatchId">
          <el-input v-model="form.dataBatchId" placeholder="请输入数据批次ID" />
        </el-form-item>
        <el-form-item label="执行方法" prop="exeMethod">
          <el-input v-model="form.exeMethod" placeholder="请输入执行方法" />
        </el-form-item>
        <el-form-item label="任务耗时" prop="jobTimeConsuming">
          <el-input v-model="form.jobTimeConsuming" placeholder="请输入任务耗时" />
        </el-form-item>
        <el-form-item label="创建人" prop="createdBy">
          <el-input v-model="form.createdBy" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createdTime">
          <el-date-picker clearable size="small" style="width: 200px" v-model="form.createdTime" type="date"
            value-format="yyyy-MM-dd" placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新人" prop="updatedBy">
          <el-input v-model="form.updatedBy" placeholder="请输入更新人" />
        </el-form-item>
        <el-form-item label="更新时间" prop="updatedTime">
          <el-date-picker clearable size="small" style="width: 200px" v-model="form.updatedTime" type="date"
            value-format="yyyy-MM-dd" placeholder="选择更新时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看定时任务日志 对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="应用名称:" prop="applyName">
          {{form.applyName}}
        </el-form-item>
        <el-form-item label="具体描述:" prop="applyMiaos">
          {{form.applyMiaos}}
        </el-form-item>
        <el-form-item label="任务名称:" prop="jobName">
          {{form.jobName}}
        </el-form-item>
        <el-form-item label="日志信息:" prop="jobMessage">
          {{form.jobMessage}}
        </el-form-item>
        <el-form-item label="执行状态 0：成功   1：失败:" prop="jobStatus">
          {{form.jobStatus | log_typeFieldFilter(jobStatusOptions)}}
        </el-form-item>
        <el-form-item label="异常信息:" prop="jobExceptionInfo">
          {{form.jobExceptionInfo}}
        </el-form-item>
        <el-form-item label="IP地址:" prop="jobIpAddr">
          {{form.jobIpAddr}}
        </el-form-item>
        <el-form-item label="业务数据数量:" prop="dataNum">
          {{form.dataNum}}
        </el-form-item>
        <el-form-item label="数据批次ID:" prop="dataBatchId">
          {{form.dataBatchId}}
        </el-form-item>
        <el-form-item label="执行方法:" prop="exeMethod">
          {{form.exeMethod}}
        </el-form-item>
        <el-form-item label="任务耗时:" prop="jobTimeConsuming">
          {{form.jobTimeConsuming}}
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 定时任务日志 导入对话框 -->
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
    listSysJobLog,
    getSysJobLog,
    delSysJobLog,
    addSysJobLog,
    updateSysJobLog,
    exportSysJobLog,
    importTemplate,
    disableEnable
  } from "@/api/system/sysJobLog/sysJobLog";


  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "SysJobLog",
    data() {
      return {
        // 搜索栏展示配置
        height: null,
        disabled: false,
        isOpen: true,
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
        // 定时任务日志 表格数据
        sysJobLogList: [],
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
          url: process.env.VUE_APP_BASE_API + "/sysJobLog/sysJobLog/importData",
        },
        // 执行状态 0：成功   1：失败字典
        jobStatusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          applyName: null,
          jobName: null,
          jobStatus: null,
          dataNum: null,
          exeMethod: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
      log_typeFieldFilter(val, optionArr) {
        var arr=optionArr && optionArr.filter(item => item.dictValue === val);
        return arr && arr.length ? arr[0].dictLabel : '';
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts("log_type").then(response => {
        this.jobStatusOptions = response.data || [];
      });
    },
    mounted() {
      this.height = this.$refs.folder.clientHeight;
      if (this.height > 32) {
        this.isOpen = false;
      }
    },
    methods: {
      clicLinke(id) {
        this.$router.push('/log/data/'+id)
      },
      /** 查询定时任务日志 列表 */
      getList() {
        this.loading = true;
        listSysJobLog(this.queryParams).then(response => {
          this.sysJobLogList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // 执行状态 0：成功   1：失败字典翻译
      jobStatusFormat(row, column) {
        return this.selectDictLabel(this.jobStatusOptions, row.jobStatus);
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
          applyName: null,
          applyMiaos: null,
          jobName: null,
          jobMessage: null,
          jobStatus: null,
          jobExceptionInfo: null,
          jobIpAddr: null,
          dataNum: null,
          dataBatchId: null,
          exeMethod: null,
          jobTimeConsuming: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加定时任务日志 ";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids
        getSysJobLog(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改定时任务日志 ";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const id = row.id || this.ids
        getSysJobLog(id).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看定时任务日志 ";
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
              updateSysJobLog(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addSysJobLog(this.form).then(response => {
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
        this.$confirm('是否确认删除定时任务日志 编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSysJobLog(ids);
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
        this.$confirm('是否确认导出定时任务日志 数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSysJobLog(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "定时任务日志 数据导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg,response.key);
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
      // 搜索栏展开
      onHandle() {
        this.isOpen = !this.isOpen;
      }
    }
  };
</script>



<style lang="scss" scoped>
  .app-container {
    background: #E9F4FD;
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
