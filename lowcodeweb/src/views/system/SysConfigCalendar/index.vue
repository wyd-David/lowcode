<template>
  <div class="app-container">
    <div class="search-folder mb12" ref="folder" :class="{'folder-open': isOpen}">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
        <el-form-item label="节日日期" prop="festivalTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="queryParams.festivalTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择节日日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="日期类型,0:上班,1:休假" prop="calendarType">
          <el-select v-model="queryParams.calendarType" placeholder="请选择日期类型,0:上班,1:休假" clearable size="small">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="日期描述" prop="remarks">
          <el-input
            v-model="queryParams.remarks"
            placeholder="请输入日期描述"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
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
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['SysConfigCalendar:SysConfigCalendar:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['SysConfigCalendar:SysConfigCalendar:edit']"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['SysConfigCalendar:SysConfigCalendar:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['SysConfigCalendar:SysConfigCalendar:importData']"
          >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['SysConfigCalendar:SysConfigCalendar:importData']"
          >导出
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="SysConfigCalendarList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="日期描述" align="center" prop="id"/>
        <el-table-column label="节日日期" align="center" prop="festivalTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.festivalTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="日期类型,0:上班,1:休假" align="center" prop="calendarType"/>
        <el-table-column label="日期描述" align="center" prop="remarks"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleQueryOption(scope.row)"
            >查看
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['SysConfigCalendar:SysConfigCalendar:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['SysConfigCalendar:SysConfigCalendar:remove']"
            >删除
            </el-button>
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
    <!-- 添加或修改日历配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="节日日期" prop="festivalTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.festivalTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择节日日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="日期类型,0:上班,1:休假" prop="calendarType">
          <el-select v-model="form.calendarType" placeholder="请选择日期类型,0:上班,1:休假">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="日期描述" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入日期描述"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 日历配置导入对话框 -->
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
          <el-checkbox v-model="upload.updateSupport"/>
          是否更新已经存在的用户数据
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
  import { getToken } from '@/utils/auth'
  import {
    listSysConfigCalendar,
    getSysConfigCalendar,
    delSysConfigCalendar,
    addSysConfigCalendar,
    updateSysConfigCalendar,
    exportSysConfigCalendar,
    importTemplate
  } from '@/api/system/SysConfigCalendar/SysConfigCalendar'

  export default {
    name: 'SysConfigCalendar',
    data() {
      return {
        // 搜索栏展示配置
        height: null,
        disabled: false,
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
        // 日历配置表格数据
        SysConfigCalendarList: [],
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
          url: process.env.VUE_APP_BASE_API + '/SysConfigCalendar/SysConfigCalendar/importData'
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          festivalTime: null,
          calendarType: null,
          remarks: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      }
    },
    created() {
      this.getList()
    },
    mounted() {
      this.height = this.$refs.folder.clientHeight
      if (this.height > 32) {
        this.isOpen = false
      }
    },
    methods: {
      /** 查询日历配置列表 */
      getList() {
        this.loading = true
        listSysConfigCalendar(this.queryParams).then(response => {
          this.SysConfigCalendarList = response.rows
          this.total = response.total
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
          id: null,
          festivalTime: null,
          calendarType: null,
          remarks: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false
        this.reset()
        this.open = true
        this.title = '添加日历配置'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false
        this.reset()
        const id = row.id || this.ids
        getSysConfigCalendar(id).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改日历配置'
        })
      },
      /**详情按钮*/
      handleQueryOption(row) {
        this.disabled = true
        this.reset()
        const id = row.id || this.ids
        getSysConfigCalendar(id).then(response => {
          this.form = response.data
          this.open = true
          this.title = '查看日历配置'
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
            if (this.form.id != null) {
              updateSysConfigCalendar(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                }
              })
            } else {
              addSysConfigCalendar(this.form).then(response => {
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
        const ids = row.id || this.ids
        this.$confirm('是否确认删除日历配置编号为"' + ids + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delSysConfigCalendar(ids)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出日历配置数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return exportSysConfigCalendar(queryParams)
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {
        })
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = '日历配置数据导入'
        this.upload.open = true
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg,response.key);
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
      },
      // 搜索栏展开
      onHandle() {
        this.isOpen = !this.isOpen
      }
    }
  }
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

