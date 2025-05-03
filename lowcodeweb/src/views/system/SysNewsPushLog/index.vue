<template>
  <div class="app-container">
    <div class="search-folder mb12" ref="folder" :class="{'folder-open': isOpen}">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
        <el-form-item label="发送内容" prop="sendOutText">
          <el-input v-model="queryParams.sendOutText" placeholder="请输入发送内容" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="发送人名称" prop="sendOutByName">
          <el-input v-model="queryParams.sendOutByName" placeholder="请输入发送人名称" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="发送手机号" prop="sendOutPhone">
          <el-input v-model="queryParams.sendOutPhone" placeholder="请输入发送手机号" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="对应跳转详情的url" prop="sendOutUrl">
          <el-input v-model="queryParams.sendOutUrl" placeholder="请输入对应跳转详情的url" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="流水号" prop="sendOutNumber">
          <el-input v-model="queryParams.sendOutNumber" placeholder="请输入流水号" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="发送状态" prop="sendOutState">
          <el-select v-model="queryParams.sendOutState" placeholder="请选择发送状态" clearable size="small">
            <el-option v-for="dict in sendOutStateOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue" />
          </el-select>
        </el-form-item>
      </el-form>

      <div class="search-folder-utils">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
        <p v-if="height > 34" @click="onHandle">{{isOpen ? '收起' : '展开'}}</p>
        <span v-if="height > 34">
          <i class="el-icon-arrow-up ivu-icon" @click="onHandle" v-if="isOpen"></i>
          <i class="el-icon-arrow-down ivu-icon" @click="onHandle" v-else></i>
        </span>
      </div>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['SysNewsPushLog:SysNewsPushLog:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['SysNewsPushLog:SysNewsPushLog:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['SysNewsPushLog:SysNewsPushLog:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['SysNewsPushLog:SysNewsPushLog:importData']">导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['SysNewsPushLog:SysNewsPushLog:importData']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="SysNewsPushLogList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="发送状态" align="center" prop="id" />
        <el-table-column label="模板ID" align="center" prop="templateId" />
        <el-table-column label="发送内容" align="center" prop="sendOutText" />
        <el-table-column label="发送人账号" align="center" prop="sendOutBy" />
        <el-table-column label="发送人名称" align="center" prop="sendOutByName" />
        <el-table-column label="发送手机号" align="center" prop="sendOutPhone" />
        <el-table-column label="对应跳转详情的url" align="center" prop="sendOutUrl" />
        <el-table-column label="流水号" align="center" prop="sendOutNumber" />
        <el-table-column label="发送状态" align="center" prop="sendOutState" :formatter="sendOutStateFormat" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
              v-hasPermi="['SysNewsPushLog:SysNewsPushLog:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
              v-hasPermi="['SysNewsPushLog:SysNewsPushLog:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="Number(total)" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改消息推送记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="模板ID" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入模板ID" />
        </el-form-item>
        
        <el-form-item label="发送人账号" prop="sendOutBy">
          <el-input v-model="form.sendOutBy" placeholder="请输入发送人账号" />
        </el-form-item>
        <el-form-item label="发送人名称" prop="sendOutByName">
          <el-input v-model="form.sendOutByName" placeholder="请输入发送人名称" />
        </el-form-item>
        <el-form-item label="发送手机号" prop="sendOutPhone">
          <el-input v-model="form.sendOutPhone" placeholder="请输入发送手机号" />
        </el-form-item>
        <el-form-item label="对应跳转详情的url" prop="sendOutUrl">
          <el-input v-model="form.sendOutUrl" placeholder="请输入对应跳转详情的url" />
        </el-form-item>
        <el-form-item label="流水号" prop="sendOutNumber">
          <el-input v-model="form.sendOutNumber" placeholder="请输入流水号" />
        </el-form-item>
        <el-form-item label="发送状态" prop="sendOutState">
          <el-select v-model="form.sendOutState" placeholder="请选择发送状态">
            <el-option v-for="dict in sendOutStateOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发送内容" prop="sendOutText">
          <el-input v-model="form.sendOutText" placeholder="请输入发送内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 消息推送记录导入对话框 -->
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
    listSysNewsPushLog,
    getSysNewsPushLog,
    delSysNewsPushLog,
    addSysNewsPushLog,
    updateSysNewsPushLog,
    exportSysNewsPushLog,
    importTemplate
  } from "@/api/system/SysNewsPushLog/SysNewsPushLog";

  export default {
    name: "SysNewsPushLog",
    data() {
      return {
        // 搜索栏展示配置
        height: null,
        isOpen: true,
        // 遮罩层
        loading: true,
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
        // 消息推送记录表格数据
        SysNewsPushLogList: [],
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
          url: process.env.VUE_APP_BASE_API + "/SysNewsPushLog/SysNewsPushLog/importData",
        },
        // 发送状态字典
        sendOutStateOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          sendOutText: null,
          sendOutByName: null,
          sendOutPhone: null,
          sendOutUrl: null,
          sendOutNumber: null,
          sendOutState: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getDicts("sys_todo_push_status").then(response => {
        this.sendOutStateOptions = response.data;
      });
    },
    mounted() {
      this.height = this.$refs.folder.clientHeight;
      if (this.height > 32) {
        this.isOpen = false;
      }
    },
    methods: {
      /** 查询消息推送记录列表 */
      getList() {
        this.loading = true;
        listSysNewsPushLog(this.queryParams).then(response => {
          this.SysNewsPushLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 发送状态字典翻译
      sendOutStateFormat(row, column) {
        return this.selectDictLabel(this.sendOutStateOptions, row.sendOutState);
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
          templateId: null,
          sendOutText: null,
          sendOutBy: null,
          sendOutByName: null,
          sendOutPhone: null,
          sendOutUrl: null,
          sendOutNumber: null,
          sendOutState: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null
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
        this.reset();
        this.open = true;
        this.title = "添加消息推送记录";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getSysNewsPushLog(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改消息推送记录";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSysNewsPushLog(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addSysNewsPushLog(this.form).then(response => {
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
        this.$confirm('是否确认删除消息推送记录编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSysNewsPushLog(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出消息推送记录数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSysNewsPushLog(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "消息推送记录数据导入";
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
