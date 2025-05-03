<template>
  <div class="app-container">
    <!-- 表单查询模块 -->
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="类别" prop="archiveType">
            <dict v-model="queryParams.archiveType" :label.sync="form.archiveTypeName" width="241.4px" :dictType="$dicCode.processApplyArchive"></dict>
          </el-form-item>
          <el-form-item label="归档范围" prop="archiveScope">
            <el-input v-model="queryParams.archiveScope"
                      placeholder="请输入归档范围"
                      clearable
                      size="small"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="对应巡察阶段" prop="xunchaStage">
            <el-select v-model="queryParams.xunchaStage" placeholder="请选择巡察阶段" clearable size="small">
              <el-option v-for="dict in xunchaStageOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="dict.dictValue"/>
            </el-select>
          </el-form-item>
          <el-form-item label="归档组织：" prop="archiveOrgType">
            <el-select v-model="queryParams.archiveOrgType" placeholder="请选择归档组织" clearable size="small">
              <el-option v-for="dict in archiveOrgTypeOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="dict.dictValue"/>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <page-title></page-title>
    <div class="container">
      <!-- 表单按钮 -->
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary"
            :disabled="selectRowArr.length == 0"
            @click="openForm"
            icon="el-icon-plus"
            size="mini">设置可见范围
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary"
                     icon="el-icon-plus"
                     size="mini"
                     @click="handleAdd"
                     v-hasPermi="['system:content:add']">
            添加资料目录
          </el-button>
        </el-col>
      </el-row>
      <!-- 表单模块 -->
      <el-table v-loading="loading" :data="contentList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column label="序号" width="55" type="index" align="center"/>
        <el-table-column label="类别" prop="archiveType" min-width="160" :formatter="archiveTypeFormat"/>
        <el-table-column label="归档范围" prop="archiveScope" min-width="160"/>
        <el-table-column label="序号" prop="contentNo" width="140"/>
        <el-table-column label="对应巡察阶段" prop="xunchaStage" width="160" :formatter="xunchaStageFormat"/>
        <el-table-column label="归档组织" prop="archiveOrgType" width="160" :formatter="archiveOrgTypeFormat"/>
        <el-table-column label="资料移交时间" prop="archiveDate" width="160"/>
        <el-table-column label="可见范围" align="center" prop="companyNameStr" width="160"/>
        <el-table-column label="是否必传" align="center" prop="requireFlag" width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.requireFlag === '0'">否</span>
            <span v-else>是</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['system:content:edit']">修改</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-hasPermi="['system:content:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页模块 -->
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>
      </div>
    </div>

    <!-- 添加或修改巡察过程资料库_文件清单 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="820px" append-to-body :close-on-click-modal="false">
      <el-form inline ref="form" :model="form" :rules="rules" label-width="130px" :disabled="disabled">
        <el-form-item label="类别：" prop="archiveType">
          <dict v-model="form.archiveType" :label.sync="form.archiveTypeName" width="241.4px" :dictType="$dicCode.processApplyArchive"></dict>
        </el-form-item>
        <el-form-item label="对应巡察阶段：" prop="xunchaStage">
          <el-select v-model="form.xunchaStage" placeholder="请选择巡察阶段" style="width: 240px">
            <el-option v-for="dict in xunchaStageOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="序号：" prop="contentNo">
          <el-input maxlength="100" show-word-limit v-model="form.contentNo" placeholder="请输入序号" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="归档范围：" prop="archiveScope">
          <el-input maxlength="100" show-word-limit v-model="form.archiveScope" placeholder="请输入归档范围" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="归档组织：" prop="archiveOrgType">
          <el-select v-model="form.archiveOrgType" placeholder="请选择归档组织" style="width: 240px">
            <el-option v-for="dict in archiveOrgTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="资料移交时间：" prop="archiveDate">
          <!-- <el-input maxlength="200" show-word-limit v-model="form.archiveDate" placeholder="请输入资料移交时间" style="width: 240px"/> -->
          <el-select style="width: 240px" v-model="form.archiveDate">
            <el-option v-for="item in archiveDates" :key="item.dictValue" :label="item.dictLabel" :value="item.dictLabel"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否必传：" prop="requireFlag">
          <el-select v-model="form.requireFlag" placeholder="是否必传" style="width: 240px">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <!--<el-form-item label="可视范围：" prop="visibleScope">
          <el-select v-model="form.visibleScope" placeholder="可视范围" style="width: 240px">
            <el-option v-for="dict in archivesVisibleScopeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="默认可见范围：" prop="visibleScope">
          <el-select disabled v-model="visibleScope" multiple style="width: 240px">
            <el-option v-for="item in archivesVisibleScopeOptions"
                       :key="item.dictValue"
                       :label="item.dictLabel"
                       :value="item.dictValue"
                       disabled/>
          </el-select>
        </el-form-item>
        <el-form-item label="可见范围：" prop="companyList">
          <el-select v-model="form.companyList" multiple style="width: 240px">
            <el-option
              v-for="item in companyOptions"
              :key="item.companyId"
              :label="item.companyName"
              :value="item.companyId"
            />
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align:right;">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm" v-if="!disabled">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 可见范围 -->
    <visible-dialog
      @close="handleClose"
      :dialogVisible="isOpen"
      :formData="selectRow"
    />
  </div>
</template>

<script>
  import { getToken } from '@/utils/auth'
  import { listContent, getContent, delContent, addContent, updateContent, exportContent, importTemplate, disableEnable } from '@/api/xuncha/archives/xunchaProcessContent'

  import ExpandCollapse from '@/components/ExpandCollapse/index'
  import { formatDate } from '@/utils/index'
  import Dict from '@/components/XunCha/Dict/index'
  import { queryDictList } from '@/api/xuncha/common/base'
  import PageTitle from '@/components/PageTitle'
  import VisibleDialog from "../../report/visibleDialog";
  import {queryCompanyList} from "@/api/xuncha/common/base";

  export default {
    name: 'Content',
    components: { ExpandCollapse, Dict, PageTitle,VisibleDialog },
    data() {
      return {
        isOpen: false,
        selectRow: {},
        selectRowArr:[],
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
        // 巡察过程资料库_文件清单 表格数据
        contentList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: '',
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: { Authorization: 'Bearer ' + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + '/system/content/importData'
        },
        //移交时间字典
        archiveDates: [],
        // 入库资料类型字典
        archiveTypeOptions: [],
        // 巡察阶段字典
        xunchaStageOptions: [],
        // 归档组织字典
        archiveOrgTypeOptions: [],
        // 可视范围字典
        archivesVisibleScopeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          archiveScope: null,
          archiveType: null,
          xunchaStage: null,
          archiveOrgType: null
        },
        // 表单参数
        form: {
          requireFlag: '0',
          visibleScope: '1'
        },
        // 表单校验
        rules: {
          archiveType: [{ required: true, message: '入库资料类型不能为空', trigger: 'change' }],
          archiveScope: [{ required: true, message: '归档范围不能为空', trigger: 'blur' }],
          requireFlag: [{ required: true, message: '是否必传不能为空', trigger: 'blur' }],
          archiveOrgType: [{ required: true, message: '归档组织不能为空', trigger: 'change' }]
        },
        // 默认可见范围
        visibleScope: [],
        // 可见范围下拉框
        companyOptions: [],
      }
    },
    filters: {
      filterDate(val) {
        return formatDate(val)
      },
      sys_archive_typeFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val)
        if (!arr.length) return ''
        return arr[0].dictLabel
      },
      xuncha_stageFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val)
        if (!arr.length) return ''
        return arr[0].dictLabel
      },
      sys_data_sourceFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val)
        if (!arr.length) return ''
        return arr[0].dictLabel
      }
    },
    created() {
      this.getList()
      queryDictList({ dictType: 'process_apply_archive' }).then(res => {
        this.archiveTypeOptions = res.data || []
      })
      this.getDicts('sys_archive_type').then(response => {
        this.xunchaStageOptions = response.data || []
      })
      this.getDicts('process_content_archive').then(response => {
        this.archiveOrgTypeOptions = response.data || []
      })
      this.getDicts('archives_visible_scope').then(response => {
        this.archivesVisibleScopeOptions = response.data || []
      })
      this.getDicts("archives_content_time").then(response => {
        this.archiveDates = response.data || []
      })

      this.getDicts('archives_visible_scope').then(response => {
        this.archivesVisibleScopeOptions = response.data || []
        this.archivesVisibleScopeOptions.forEach(item => {
          // 默认可见范围全选，巡察组、巡察办、地市公司分管领导
          this.visibleScope.push(item.dictValue)
        })
      })
      // 获得可见范围下拉框数据
      queryCompanyList().then((res) => {
        this.companyOptions.push(...res.data);
      });


    },
  methods: {
    openForm() {
      this.selectRow = this.selectRowArr[0];
      // this.$refs.tableRef.multipleSelection
      this.isOpen = true;
    },
      handleClose(e) {
      if (e == 200) {
        this.getList();
      }
      this.isOpen = false;
    },
      /** 查询巡察过程资料库_文件清单 列表 */
      getList() {
        this.loading = true
        let companyId = this.$store.getters.userInfo.companyId;
        let viewAll = false;
        if (this.$store.getters.userInfo.department == "巡察工作办公室(党风廉政办公室)") {
          viewAll = true;
        }
        if (!companyId) {
          viewAll = true;
        }
        listContent({...this.queryParams,companyId:companyId,viewAll:viewAll}).then(response => {
          this.contentList = response.rows || []
          this.total = +response.total
          this.loading = false
        })
      },
      // 入库资料类型字典翻译
      archiveTypeFormat(row, column) {
        for (let i = 0; this.archiveTypeOptions.length > i; i++) {
          if (row.archiveType === this.archiveTypeOptions[i].dataCode) {
            return this.archiveTypeOptions[i].dataName
          }
        }
        return row.archiveType
      },
      // 巡察阶段字典翻译
      xunchaStageFormat(row, column) {
        return this.selectDictLabel(this.xunchaStageOptions, row.xunchaStage)
      },
      // 归档组织字典翻译
      archiveOrgTypeFormat(row, column) {
        return this.selectDictLabel(this.archiveOrgTypeOptions, row.archiveOrgType)
      },
      // 可视范围字典翻译
      archivesVisibleScopeFormat(row, column) {
        return this.selectDictLabel(this.archivesVisibleScopeOptions, row.visibleScope)
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          contentId: null,
          archiveScope: null,
          contentNo: null,
          archiveType: null,
          xunchaStage: null,
          archiveOrgType: null,
          archiveDate: null,
          requireFlag: '0',
          remark: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null
        }
        this.resetForm('form')
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.contentId)
        this.selectRowArr = selection
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false
        this.reset()
        this.open = true
        this.title = '新增'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false
        this.reset()
        const contentId = row.contentId || this.ids
        getContent(contentId).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改'
          this.disabled = false
        })
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const contentId = row.contentId || this.ids
        getContent(contentId).then(response => {
          this.form = response.data
          this.open = true
          this.title = '详情'
          this.disabled = true
        })
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false
          return
        }
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.contentId != null) {
              updateContent(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                }
              })
            } else {
              addContent(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('新增成功')
                  this.open = false
                  this.getList()
                }
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const contentIds = row.contentId || this.ids
        this.$confirm('删除后将无法恢复，是否确认删除?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delContent(contentIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || []
        this.$confirm('是否确认' + (type === 0 ? '启用' : '禁用') + '?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return disableEnable({ ids: ids.toString(), disableEnableState: type })
        }).then(() => {
          this.getList()
          this.msgSuccess((type === 0 ? '启用' : '禁用') + '成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有巡察过程资料库_文件清单 数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return exportContent(queryParams)
        }).then(response => {
          this.download(response.msg)
        }).catch(function() {
        })
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = '巡察过程资料库_文件清单 数据导入'
        this.upload.open = true
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg)
        })
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false
        this.upload.isUploading = false
        this.$refs.upload.clearFiles()
        this.$alert(response.msg, '导入结果', { dangerouslyUseHTMLString: true })
        this.getList()
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit()
      }
    }
  }
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

