<template>
  <div class="app-container">
    <!-- 列表查询条件 -->
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true"  label-width="120px">
          <el-form-item label="巡察轮次：" prop="lunciName">
            <el-input v-model="queryParams.lunciName" placeholder="请输入巡察轮次" clearable size="small"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <page-title></page-title>
    <!-- 列表模块 -->
    <div class="container">
      <!-- 列表的按钮 -->
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary"
                     icon="el-icon-plus"
                     size="mini"
                     @click="handleAdd"
                     v-hasPermi="['xuncha:XunchaLunciManage:add']">
            新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary"
                     icon="el-icon-edit"
                     size="mini"
                     :disabled="single"
                     @click="handleUpdate"
                     v-hasPermi="['xuncha:XunchaLunciManage:edit']">
            修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger"
                     icon="el-icon-delete"
                     size="mini"
                     :disabled="multiple"
                     @click="handleDelete"
                     v-hasPermi="['xuncha:XunchaLunciManage:remove']">
            删除
          </el-button>
        </el-col>
        <!--<el-col :span="1.5">
            <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
                v-hasPermi="['xuncha:XunchaLunciManage:importData']">导出</el-button>
        </el-col>-->
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>
      <!-- 列表数据 -->
      <el-table v-loading="loading" :data="XunchaLunciManageList" @selection-change="handleSelectionChange" :disabled="disabled">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column label="序号" type="index" width="55" align="center"/>
        <el-table-column label="巡察轮次" width="280" prop="lunciName" :show-overflow-tooltip="true"/>
        <el-table-column label="时间跨度" align="center" width="140">
          <template slot-scope="scope">
            <!-- <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span> -->
            <span>{{ parseTime(scope.row.startTime, '{y}年') + ' 至 ' + parseTime(scope.row.endTime, '{y}年') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="说明" prop="remark" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="left" width="220" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleLogs(scope.row)">操作日志</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-hasPermi="['xuncha:XunchaLunciManage:remove']">删除</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['xuncha:XunchaLunciManage:edit']">修改</el-button>
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize" @pagination="getList"/>
      </div>
    </div>
    <!-- 添加或修改巡察轮次管理 对话框 -->
    <lcForm :id="lunciId+''" :open="open" @close="close" :disabled="disabled"></lcForm>
    <logs :id="logs.id" requestMapping="XunchaLunciManage" :dialogVisible="logs.open" @close="logsClose"></logs>
  </div>
</template>

<script>
  import { getToken } from '@/utils/auth'
  import { listXunchaLunciManage, getXunchaLunciManage, delXunchaLunciManage, addXunchaLunciManage, updateXunchaLunciManage, exportXunchaLunciManage, importTemplate, disableEnable } from '@/api/xuncha/XunchaLunciManage'

  import ExpandCollapse from '@/components/ExpandCollapse/index'
  import lcForm from './lcForm'
import { formatDate } from '@/utils/index'

import logs from '../logs/index';
import PageTitle from "@/components/PageTitle"
  export default {
    name: 'XunchaLunciManage',
    components: { ExpandCollapse, lcForm,logs,PageTitle },
    data() {
      return {
        logs: {
          open: false,
          id:''
        },
        lunciId: '',
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
        // 巡察轮次管理 表格数据
        XunchaLunciManageList: [],
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
          url: process.env.VUE_APP_BASE_API + '/xuncha/XunchaLunciManage/importData'
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          lunciName: null,
          startTime: null,
          endTime: null,
          remark: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          lunciName: [{ required: true, message: '巡察轮次不能为空', trigger: 'blur' }],
          startTime: [{ required: true, message: '巡察开始时间不能为空', trigger: 'blur' }],
          endTime: [{ required: true, message: '巡察结束时间不能为空', trigger: 'blur' }]
        }
      }
    },
    filters: {
      filterDate(val) {
        return formatDate(val)
      }
    },
    created() {
      this.getList()
    },
  methods: {
    handleLogs(row) {
      this.logs.open = true;
      this.logs.id = row.lunciId;
     },
    logsClose() {
      this.logs.open = false;
    },
      close() {
        this.getList()
        this.open = false;
      },
      /** 查询巡察轮次管理 列表 */
      getList() {
        this.loading = true
        listXunchaLunciManage(this.queryParams).then(response => {
          this.XunchaLunciManageList = response.rows || []
          this.total = +response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          lunciId: null,
          lunciName: null,
          startTime: null,
          endTime: null,
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
        this.ids = selection.map(item => item.lunciId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false
        this.lunciId = ''
        this.open = true
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false
        this.lunciId = row.lunciId || this.ids
        this.open = true
      },
      /**详情按钮*/
      handleQueryOption(row) {
        this.disabled = true
        this.lunciId = row.lunciId || this.ids
        this.open = true
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false
          return
        }
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.lunciId != null) {
              updateXunchaLunciManage(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                }
              })
            } else {
              addXunchaLunciManage(this.form).then(response => {
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
        const lunciIds = row.lunciId || this.ids
        this.$confirm('删除后将无法恢复，确定删除？', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delXunchaLunciManage(lunciIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有巡察轮次管理 数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return exportXunchaLunciManage(queryParams)
        }).then(response => {
          this.download(response.msg)
        }).catch(function() {
        })
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = '巡察轮次管理 数据导入'
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
