<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <!-- <el-form-item label="资源ID" prop="resourceId">
									<el-input
											v-model="queryParams.resourceId"
											placeholder="请输入资源ID"
											clearable
											size="small"
											@keyup.enter.native="handleQuery"
									/>
								</el-form-item> -->

          <el-form-item label="名称" prop="resourceName">
            <el-input v-model="queryParams.resourceName" placeholder="请输入名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>            <el-form-item label="数据编码" prop="resourceCode">              <el-input v-model="queryParams.apiCode" placeholder="请输入数据编码" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
           </el-form-item>            <el-form-item label="状态" prop="apiStatus">              <el-select v-model="queryParams.apiStatus" placeholder="请选择" clearable size="small">                <el-option v-for="dict in apiStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"                  :value="dict.dictValue"></el-option>              </el-select>            </el-form-item>
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
            v-hasPermi="['system:apiInfo:add']">新增</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="apiInfoList" @selection-change="handleSelectionChange">
        <!--   <el-table-column type="selection" width="55" align="left" /> -->
        <el-table-column label="序列号" align="center" prop="id" />
        <el-table-column label="名称" align="center" width="200" prop="resourceName" :show-overflow-tooltip='true'/>
        <!-- <el-table-column label="sql表达式" align="center" prop="apiDefination" /> -->
        <el-table-column label="数据编码" width="250" align="center" prop="apiCode" :show-overflow-tooltip='true'/>

        <el-table-column label="状态" align="center"  width="200" prop="apiStatus" :formatter="apiStatusFormat" />
        <el-table-column label="创建者" align="center"  width="100" prop="createBy" :show-overflow-tooltip='true'/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
       <!-- <el-table-column label="更新者" align="center" width="100" prop="updateBy" /> -->
        <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>


        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['system:apiInfo:edit']">修改
            </el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-hasPermi="['system:apiInfo:remove']">
              删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改数据服务API对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
      <!--  <el-form-item label="资源ID" prop="resourceId" >
          <el-input v-model="form.resourceId" placeholder="请输入资源ID"  />
        </el-form-item>
        <el-form-item label="资源编码" prop="resourceCode"  >
          <el-input v-model="form.resourceCode" placeholder="请输入资源编码" />
        </el-form-item> -->
        <el-form-item label="名称" prop="resourceName" >
          <el-input v-model="form.resourceName" placeholder="请输入资源名称" />
        </el-form-item>
        <el-form-item label="数据引擎ID" prop="datasourceId" >
          <el-input v-model="form.datasourceId" placeholder="请输入数据引擎ID"  />
        </el-form-item>
        <el-form-item label="数据编码" prop="apiCode" >
          <el-input v-model="form.apiCode"   placeholder="请输入内容" />
        </el-form-item>

        <el-form-item label="sql表达式" prop="apiDefination">
           <template slot="label">
              <span>sql
                <el-tooltip class="item"
                            effect="dark"
                            content="必传参数表达式: {！ param=#paramValue#},非必传参数表达式: {? param=#paramValue#},用户环境变量参数：@Env(loginId)日期变量参数：
          @{yyyy}    当前年份
          @{MM}  当前月份
          @{dd} 当前日期天
          @{yyyy-MM-dd}    当前年月日
          @{yyyy-MM-dd HH:mm:ss}     当前年月日时分秒"
                            placement="left">
                  <i class="el-icon-question"
                     style="font-size: 16px; vertical-align: middle;"></i>
                </el-tooltip>
              </span>
            </template>
          <el-input v-model="form.apiDefination" rows="7" type="textarea"  placeholder="请输入sql表达式" />
        </el-form-item>
        <el-form-item label="api状态" prop="apiStatus">
          <el-select v-model="form.apiStatus" placeholder="请选择api状态,1停用，0：启用">
            <el-option v-for="dict in apiStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入使用方式" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看数据服务API对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="700px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">

        <el-form-item label="名称:" prop="resourceName">
          {{form.resourceName}}
        </el-form-item>
       <!-- <el-form-item label="数据源ID:" prop="datasourceId">
          {{form.datasourceId}}
        </el-form-item> -->

        <el-form-item label="数编码:" prop="apiCode">
          {{form.apiCode}}
        </el-form-item>
        <el-form-item label="sql表达式:" prop="apiDefination">
          {{form.apiDefination}}
        </el-form-item>
        <el-form-item label="状态:" prop="apiStatus">
          {{form.apiStatus | sys_yes_noFieldFilter(apiStatusOptions)}}
        </el-form-item>

        <el-form-item label="使用方式:" prop="remark">
          {{form.remark}}
        </el-form-item>
       <label>API使用说明:</label>
  <hr/>
        <el-form-item label=" 请求根地址:" >
        http://IP:端口<br/>
          登录接口：/prod-api/datasource/login<br/>
          新增接口：/prod-api/datasource/insert<br/>
          修改接口：/prod-api/datasource/insert<br/>
          删除接口：/prod-api/datasource/delete<br/>
          查询接口：/prod-api/datasource/select<br/>
          分页查询接口：/prod-api/datasource/getTableInfo<br/>
          服务编排接口：/prod-api/datasource/transaction<br/>
          联邦查询接口：/prod-api/datasource/unionSelect<br/>
        </el-form-item>
        <el-form-item label=" 请求参数:" >
          ApiCode: QUERY_TEST_YA_1<label style="color: red;">【该值为数据编码】</label><br/>
          param: 传入API服务对应的键值对信息 例如：{参数名1:value,参数名2:value2}<br/>
          Token: 调用登录接口后可获取<button type="button" style="background-color: azure;"><a target="_blank"  href="https://note.youdao.com/s/UUewGMj8">详细操作文档</a></button>
        </el-form-item>
        <el-form-item label=" Token:" >
          调用所有API服务前需要拥有账号，且进行登录后获取到Token进行传入
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 数据服务API导入对话框 -->
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
    listApiInfo,
    getApiInfo,
    delApiInfo,
    addApiInfo,
    updateApiInfo,
    exportApiInfo,
    importTemplate,
    disableEnable,
    validateSql
  } from "@/api/system/apiInfo";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "ApiInfo",
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
        // 数据服务API表格数据
        apiInfoList: [],
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
          url: process.env.VUE_APP_BASE_API + "/system/apiInfo/importData",
        },
        // api状态,1停用，0：启用字典
        apiStatusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          resourceId: null,
          resourceCode: null,
          resourceName: null,
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
      sys_yes_noFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts("API_SERVICE_STATUS").then(response => {
        this.apiStatusOptions = response.data || [];
      });
    },
    methods: {
      /** 查询数据服务API列表 */
      getList() {
        this.loading = true;
        listApiInfo(this.queryParams).then(response => {
          this.apiInfoList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // api状态,1停用，0：启用字典翻译
      apiStatusFormat(row, column) {
        return this.selectDictLabel(this.apiStatusOptions, row.apiStatus);
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
          resourceId: null,
          resourceCode: null,
          resourceName: null,
          datasourceId: null,
          apiDefination: null,
          apiCode: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          apiStatus: null
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
        this.title = "添加数据服务API";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids
        getApiInfo(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改数据服务API";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const id = row.id || this.ids
        getApiInfo(id).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看数据服务API";
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
            validateSql(this.form).then(response =>{
              if (response.code === 200) {
                if (this.form.id != null) {
                  updateApiInfo(this.form).then(response => {
                    if (response.code === 200) {
                      this.msgSuccess("修改成功");
                      this.open = false;
                      this.getList();
                    }
                  });
                } else {
                  addApiInfo(this.form).then(response => {
                     debugger
                     console.log(response);
                    if (response.code === 200) {
                      this.msgSuccess("新增成功");
                      this.open = false;
                      this.getList();
                    }
                  });
                }
              }
            });





          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除数据服务API编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delApiInfo(ids);
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
        this.$confirm('是否确认导出所有数据服务API数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportApiInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "数据服务API数据导入";
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
