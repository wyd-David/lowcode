<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="驱动类型" prop="driverclass">
            <el-select v-model="queryParams.driverclass" placeholder="请选择驱动类型" clearable size="small">
              <el-option v-for="dict in driverclassOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="引擎ID" prop="instancedisplyname">
            <el-input v-model="queryParams.datasourceid" placeholder="引擎ID" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="引擎名称" prop="instancedisplyname">
            <el-input v-model="queryParams.instancedisplyname" placeholder="请输入实例名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="ip" prop="ip">
            <el-input v-model="queryParams.ip" placeholder="请输入ip地址" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="端口" prop="port">
            <el-input v-model="queryParams.port" placeholder="请输入端口" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="数据库" prop="database">
            <el-input v-model="queryParams.database" placeholder="请输入数据库" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="schema" prop="schema">
            <el-input v-model="queryParams.schema" placeholder="请输入数据库schema" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="版本号" prop="versioncode">
            <el-input v-model="queryParams.versioncode" placeholder="请输入版本号" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="状态" prop="appStatus">
            <el-select v-model="queryParams.appStatus" placeholder="请选择状态" clearable size="small">
              <el-option v-for="dict in appStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
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
            v-hasPermi="['datasource:appInfo:add']">新增</el-button>
        </el-col>
       <!-- <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['datasource:appInfo:edit']">修改</el-button>
        </el-col> -->
      <!--  <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['datasource:appInfo:remove']">删除</el-button>
        </el-col> -->
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-lock" size="mini" :disabled="multiple" @click="disabledBtnClick(1)"
            v-hasPermi="['datasource:appInfo:remove']">禁用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-unlock" size="mini" :disabled="multiple" @click="disabledBtnClick(0)"
            v-hasPermi="['datasource:appInfo:remove']">启用</el-button>
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['datasource:appInfo:importData']">导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['datasource:appInfo:importData']">导出</el-button>
        </el-col> -->
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="appInfoList" @selection-change="handleSelectionChange">
  <!--      <el-table-column type="selection" width="55" align="left" /> -->
        <el-table-column label="引擎ID" align="center" prop="datasourceid" />
        <el-table-column label="引擎名称" align="center" prop="instancedisplyname" />
        <el-table-column label="ip地址" align="center" prop="ip" />
        <el-table-column label="端口" align="center" prop="port" />
        <el-table-column label="数据库" align="center" prop="database" />
        <el-table-column label="schema" align="center" prop="schema" />
         <el-table-column label="用户名" align="center" prop="username" />
        <el-table-column label="驱动类型" align="center" prop="driverclass" :formatter="driverclassFormat" />
        <el-table-column label="版本号" align="center" prop="versioncode" />
        <el-table-column label="备注" align="center" prop="infos" />
         <el-table-column label="是否启用" align="center" prop="isDelete" />
        <el-table-column label="状态" align="center" prop="appStatus" :formatter="appStatusFormat" />
         <el-table-column label="创建人" align="center" prop="createBy" />
         <el-table-column label="创建时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
             <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}  {h}:{m}:{s}') }}</span>
           </template>
         </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <!-- <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
                v-hasPermi="['datasource:appInfo:getmeta']">抓取元数据</el-button> -->
                <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
                    >抓取元数据</el-button>
                <!-- <el-button size="mini" type="text" @click="handleDelete(scope.row)"
                  v-hasPermi="['datasource:appInfo:remove']">校验数据源</el-button> -->
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改动态数据源管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="驱动类型" prop="driverclass"  required="true">
          <el-select v-model="form.driverclass" placeholder="请选择驱动类型">
            <el-option v-for="dict in driverclassOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="中文名称" prop="instancedisplyname"  required="true">
          <el-input v-model="form.instancedisplyname" placeholder="请输入中文名称" />
        </el-form-item>
        <el-form-item label="ip地址" prop="ip" required="true">
          <el-input v-model="form.ip" placeholder="请输入ip地址" />
        </el-form-item>
        <el-form-item label="端口" prop="port"  required="true">
          <el-input v-model="form.port" placeholder="请输入端口" />
        </el-form-item>
        <el-form-item label="数据库" prop="database"  required="true">
          <el-input v-model="form.database" placeholder="请输入数据库" />
        </el-form-item>
        <el-form-item label="schema" prop="schema">
          <el-input v-model="form.schema" placeholder="请输入数据库schema" />
        </el-form-item>
        <el-form-item label="用户名" prop="username"  required="true">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password"  required="true">
          <el-input v-model="form.password" placeholder="请输入密码"  type="password"/>
        </el-form-item>
        <el-form-item label="版本号" prop="versioncode">
          <el-input v-model="form.versioncode" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="备注" prop="infos">
          <el-input v-model="form.infos" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
         <el-button  type="danger" @click="submitForm1">校验</el-button>

        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="isGetMetaQuery" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
       <el-form-item label="驱动类型" prop="driverclass"  >
        <el-input v-model="form.driverclass" placeholder="请输入中文名称" readonly="true"/>
       </el-form-item>
       <el-form-item label="中文名称" prop="instancedisplyname"   >
         <el-input v-model="form.instancedisplyname" placeholder="请输入中文名称" readonly="true"/>
       </el-form-item>
        <el-form-item label="抓取表名称" prop="importStatus">
          <el-input v-model="form.importStatus" type="textarea" placeholder="请输入内容,多个用英文逗号隔开" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="getMeta">提交</el-button>
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 查看动态数据源管理对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="驱动类型:" prop="driverclass">
          {{form.driverclass | meta_datasourceFieldFilter(driverclassOptions)}}
        </el-form-item>
        <el-form-item label="ip地址:" prop="ip">
          {{form.ip}}
        </el-form-item>
        <el-form-item label="端口:" prop="port">
          {{form.port}}
        </el-form-item>
        <el-form-item label="数据库:" prop="database">
          {{form.database}}
        </el-form-item>
        <el-form-item label="数据库" prop="schema">
          {{form.schema}}
        </el-form-item>
        <el-form-item label="实例名称:" prop="instancedisplyname">
          {{form.instancedisplyname}}
        </el-form-item>
        <el-form-item label="用户名:" prop="username">
          {{form.username}}
        </el-form-item>
        <el-form-item label="版本号:" prop="versioncode">
          {{form.versioncode}}
        </el-form-item>
        <el-form-item label="备注:" prop="infos">
          {{form.infos}}
        </el-form-item>
        <el-form-item label="状态:" prop="appStatus">
          {{form.appStatus | datasource_validate_statusFieldFilter(appStatusOptions)}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button  type="danger" @click="validateData">校验</el-button>

        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>



  </div>
</template>

<script>
  import {
    getToken
  } from "@/utils/auth";
  import {
    listAppInfo,
    getAppInfo,
    delAppInfo,
    addAppInfo,
    updateAppInfo,
    exportAppInfo,
    importTemplate,
    disableEnable
  } from "@/api/datasource/appInfo";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "AppInfo",
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
        //是否显示抓取元数据
        isGetMetaQuery: false,
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
        // 动态数据源管理表格数据
        appInfoList: [],
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
          url: process.env.VUE_APP_BASE_API + "/datasource/appInfo/importData",
        },
        // 驱动类型字典
        driverclassOptions: [],
        // 状态字典
        appStatusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          driverclass: null,
          ip: null,
          port: null,
          database: null,
          schema: null,
          instancedisplyname: null,
          username: null,
          password: null,
          versioncode: null,
          infos: null,
          appStatus: null,
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
      meta_datasourceFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
      datasource_validate_statusFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts("meta_datasource").then(response => {
        this.driverclassOptions = response.data || [];
      });
      this.getDicts("datasource_validate_status").then(response => {
        this.appStatusOptions = response.data || [];
      });
    },
    methods: {
      /** 查询动态数据源管理列表 */
      getList() {
        this.loading = true;
        listAppInfo(this.queryParams).then(response => {
          this.appInfoList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // 驱动类型字典翻译
      driverclassFormat(row, column) {
        return this.selectDictLabel(this.driverclassOptions, row.driverclass);
      },
      // 状态字典翻译
      appStatusFormat(row, column) {
        return this.selectDictLabel(this.appStatusOptions, row.appStatus);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      cancel1() {
        this.isGetMetaQuery = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          driverclass: null,
          ip: null,
          port: null,
          database: null,
          schema: null,
          instancedisplyname: null,
          username: null,
          password: null,
          versioncode: null,
          datasourceid: null,
          infos: null,
          isDelete: null,
          appStatus: null,
          createTime: null,
          createBy: null
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
        this.ids = selection.map(item => item.datasourceid)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加动态数据源管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const datasourceid = row.datasourceid || this.ids
        getAppInfo(datasourceid).then(response => {
          this.form = response.data;
          this.isGetMetaQuery = true;
          this.title = "抓取元数据";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const datasourceid = row.datasourceid || this.ids
        getAppInfo(datasourceid).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看动态数据源管理";
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
             this.form.importStatus="1";
            if (this.form.datasourceid != null) {
              updateAppInfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addAppInfo(this.form).then(response => {
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

      //获取元数据
      getMeta(){
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.datasourceid != null) {
              this.open = true;


              updateAppInfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("抓取成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              this.msgSuccess("抓取失败");
              this.open = false;
                this.getList();
            }
          }
        });
      },
      validateData(){

        console.log(this.form)
        addAppInfo(this.form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("校验成功！");
          }
        });
      },
      //校验
      submitForm1() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.datasourceid != null) {
              updateAppInfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addAppInfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("校验成功！");
               /*   this.open = false;
                  this.getList(); */
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const datasourceids = row.datasourceid || this.ids;
        this.$confirm('是否确认抓取数据源管理编号为"' + datasourceids + '"的元数据?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAppInfo(datasourceids);
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
        this.$confirm('是否确认导出所有动态数据源管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAppInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "动态数据源管理数据导入";
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
