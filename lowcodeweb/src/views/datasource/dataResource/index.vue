<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
         <!-- <el-form-item label="数据代码" prop="resourceCode">
            <el-input v-model="queryParams.resourceCode" placeholder="请输入资源代码" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item> -->
          <el-form-item label="所属引擎" prop="datasourceId">
            <el-input v-model="queryParams.datasourceId" placeholder="请输入数据源ID" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="目录中文名称" prop="resourceName">
            <el-input v-model="queryParams.resourceName" placeholder="请输入数据资源名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="目录名称" prop="resourceTableName">
            <el-input v-model="queryParams.resourceTableName" placeholder="请输入资源表名" clearable size="small"
              @keyup.enter.native="handleQuery" />
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
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="dataResourceList" @selection-change="handleSelectionChange">
      <!--  <el-table-column type="selection" width="55" align="left" /> -->

        <!-- <el-table-column label="资源代码" align="center" prop="resourceCode" /> -->
         <el-table-column label="目录名称" align="center" prop="resourceTableName" />
        <el-table-column label="目录中文名称" align="center" prop="resourceName" />

        <el-table-column label="目录类型" align="center" prop="resourceType" />
         <el-table-column label="目录ID" align="center" prop="resourceId" />
        <el-table-column label="引擎ID" align="center" prop="datasourceId" />
        <el-table-column label="引擎名称" align="center" prop="isSameDatasource" />
       <!-- <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column> -->
        <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建者名称" align="center" prop="createUserName" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
           <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="dataColllect(scope.row)">采集</el-button>
           <!--  <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
              v-hasPermi="['datasource:dataResource:edit']">修改</el-button> -->
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
              v-hasPermi="['datasource:dataResource:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改数据资源对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
       <el-form-item label="作业名称" prop="collectName" required="required" >
             <el-input v-model="form.collectName" placeholder="请输入采集作业名称" />
           </el-form-item>
           <el-form-item label="采集引擎" prop="collectResouceId" required="required"  >
             <el-input v-model="form.collectResouceId" placeholder="请输入采集表Id" readonly="true" />
           </el-form-item>
       <el-form-item label="采集表" prop="collectTableName" required="required" >
         <el-input v-model="form.collectTableName" placeholder="请输入采集表" readonly="true"/>
       </el-form-item>
           <el-form-item label="入库引擎" prop="collectToDatasourceid"  required="required">
             <el-input v-model="form.collectToDatasourceid" placeholder="入库到数据源引擎ID" />
           </el-form-item>
           <el-form-item label="入库表" prop="collectToTableName" required="required">
             <el-input v-model="form.collectToTableName" placeholder="请输入入库表" />
           </el-form-item>

          <!-- <el-form-item label="抽取写入服务" prop="apiCodeOut">
             <el-input v-model="form.apiCodeOut" placeholder="请输入抽取时输出节点的服务编码" />
           </el-form-item> -->
           <el-form-item label="分布式运行节点" prop="runNodeId">
             <el-input v-model="form.runNodeId" placeholder="请输入运行节点"  />
           </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import {
    getToken
  } from "@/utils/auth";
  import {
    listDataResource,
    getDataResource,
    delDataResource,
    addDataResource,
    updateDataResource,
    exportDataResource,
    importTemplate,
    credateReport,
    disableEnable
  } from "@/api/datasource/dataResource";
import {
    addEtlDev,
    updateEtlDev,
    etlRun
  } from "@/api/datasource/etlDev";

  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "DataResource",
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
        // 数据资源表格数据
        dataResourceList: [],
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
          url: process.env.VUE_APP_BASE_API + "/datasource/dataResource/importData",
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          resourceCode: null,
          resourceName: null,
          resourceTableName: null,
          datasourceId: null,
          lastUpdateUserId: null,
          lastUpdateUserName: null,
          lastUpdateTime: null,
          useRequirement: null,
          enableStatus: null,
          lastDataUpdateTime: null,
          publishTime: null,
          createTenantId: null,
          version: null,
          versionDesp: null,
          isIndexCreated: null,
          collectStatus: null,
          productionMode: null,
          dataKind: null,
          structuredType: null,
          orgId: null,
          ownUserId: null,
          dataManagerId: null,
          sameName: null,
          dataLayer: null,
          isDeleted: null,
          analysisStatus: null,
          sort: null,
          clusterId: null,
          databaseId: null,
          appId: null,
          extension: null,
          baasStatus: null,
          syncStatus: null,
          reportAttachmentId: null,
          parentResourceId: null,
          isDomainEntity: null,
          isExtractKg: null,
          isSameDatasource: null,
          isSameType: null,
          isSameStructure: null,
          imageBase64: null,
          color: null,
          ownerName: null,
          createUserId: null,
          createUserName: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          status: [{
            required: true,
            message: "状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除不能为空",
            trigger: "blur"
          }],
          openedStatus: [{
            required: true,
            message: "开放状态：   opened：完全公开，needAuthorized：需要审批不能为空",
            trigger: "blur"
          }],
        }
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询数据资源列表 */
      getList() {
        this.loading = true;
        listDataResource(this.queryParams).then(response => {
          this.dataResourceList = response.rows || [];
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
          resourceId: null,
          resourceCode: null,
          resourceName: null,
          resourceTableName: null,
          shortName: null,
          resourceDesp: null,
          resourceType: null,
          datasourceId: null,
          status: "0",
          openedStatus: "0",
          securityGradeId: null,
          dataCycleId: null,
          lastUpdateUserId: null,
          lastUpdateUserName: null,
          lastUpdateTime: null,
          isTop: null,
          topTime: null,
          businessDomainId: null,
          publishStatus: "0",
          relateViewId: null,
          logRecordControl: null,
          belongSpaceId: null,
          relateViewName: null,
          catalogStandardType: null,
          dataCatalogIdentificationCode: null,
          resourceManageType: null,
          isTagEntity: null,
          isMetricEntity: null,
          storageStats: null,
          unPublishReason: null,
          openToSociety: null,
          sharingType: null,
          useRequirement: null,
          remark: null,
          enableStatus: "0",
          lastDataUpdateTime: null,
          publishTime: null,
          createTenantId: null,
          version: null,
          versionDesp: null,
          isIndexCreated: null,
          collectStatus: "0",
          productionMode: null,
          dataKind: null,
          structuredType: null,
          orgId: null,
          ownUserId: null,
          dataManagerId: null,
          sameName: null,
          dataLayer: null,
          isDeleted: null,
          analysisStatus: "0",
          sort: null,
          clusterId: null,
          databaseId: null,
          appId: null,
          extension: null,
          baasStatus: "0",
          syncStatus: "0",
          reportAttachmentId: null,
          parentResourceId: null,
          isDomainEntity: null,
          isExtractKg: null,
          isSameDatasource: null,
          isSameType: null,
          isSameStructure: null,
          imageBase64: null,
          color: null,
          shape: null,
          extendFrom: null,
          ownerType: null,
          ownerId: null,
          ownerName: null,
          createUserId: null,
          createUserName: null,
          createTime: null
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
        this.ids = selection.map(item => item.resourceId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加数据资源";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const resourceId = row.resourceId || this.ids
        getDataResource(resourceId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改数据资源";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
         const resourceId = row.resourceId || this.ids;
       credateReport(resourceId).then(response => {
         const baseUrl = location.protocol+'//' + location.host;
         window.open(baseUrl+"/#/autoResource?resourceId="+row.resourceId);
          /* this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看数据资源"; */
        });
      },
      /**数据采集按钮*/
      dataColllect(row) {
        this.form.collectResouceId = row.datasourceId;
        this.form.collectTableName = row.resourceTableName;

        this.open = true;
        this.title = "数据采集作业";
      },

      /** 提交按钮 */

      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.collectId != null) {
              updateEtlDev(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("操作成功,请到作业开发中查看");
                  this.open = false;
                 // this.getList();
                }
              });
            } else {
              addEtlDev(this.form).then(response => {
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
        const resourceIds = row.resourceId || this.ids;
        this.$confirm('是否确认删除数据资源编号为"' + resourceIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDataResource(resourceIds);
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
        this.$confirm('是否确认导出所有数据资源数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDataResource(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "数据资源数据导入";
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
