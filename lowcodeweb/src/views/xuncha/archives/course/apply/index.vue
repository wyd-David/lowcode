<template>
  <div class="app-container">
    <!-- 列表上部 -->
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true"  label-width="120px">
          <el-form-item label="业务单号：" prop="serialNo">
            <el-input v-model="queryParams.serialNo" placeholder="请输入业务单号" clearable size="small" @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="当前状态：" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择流程业务状态" clearable size="small">
              <el-option label="全部" value=""/>
              <el-option v-for="item in statusList" :key="item.key" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="巡察年份：" prop="xunchaDate">
            <el-date-picker clearable size="small" style="width: 200px" v-model="queryParams.xunchaDate" type="year" value-format="yyyy" placeholder="选择巡察年份"/>
          </el-form-item>
          <el-form-item label="巡察轮次：" prop="xunchaLunciId">
            <lun-ci v-model="queryParams.xunchaLunciId" :label.sync="queryParams.xunchaLunciName" size="small" width="205.4px"/>
          </el-form-item>
          <el-form-item label="被巡察单位：" prop="orgId">
            <company v-model="queryParams.orgId" :label.sync="queryParams.orgName" size="small" width="205.4px"/>
          </el-form-item>
          <el-form-item label="归档组织：" prop="archiveOrgType">
            <el-select v-model="queryParams.archiveOrgType" placeholder="请选择归档组织" clearable size="small">
              <el-option label="全部" value=""/>
              <el-option v-for="item in archiveOrgTypeList" :key="item.label" :label="item.name" :value="item.label"/>
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

    <!-- 列表 -->
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleApply" v-hasPermi="['archives:xunchaProcessApply:add']">
            发起资料入库
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"/>
      </el-row>

      <el-table v-loading="loading" :data="xunchaProcessApplyList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column type="index" label="序号" width="55"  align="center"/>
        <el-table-column label="业务单号" width="200" :show-overflow-tooltip="true" prop="serialNo" />
        <el-table-column label="巡察年份" width="120" prop="xunchaDate">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.xunchaDate, '{y}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="巡察轮次" min-width="160" prop="xunchaLunciName"  :show-overflow-tooltip="true"/>
        <el-table-column label="巡察批次" min-width="160" prop="xunchaPiciName"  :show-overflow-tooltip="true"/>
        <el-table-column label="被巡察单位" min-width="120" prop="orgName" :show-overflow-tooltip="true"/>
        <el-table-column label="归档组织" min-width="120" prop="archiveOrgType"  :show-overflow-tooltip="true" :formatter="archiveOrgTypeFormat"/>
        <el-table-column label="当前状态" width="160" align="center" prop="status" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" width="80" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>
      </div>
    </div>
    <!-- 发起流程弹出框 -->
    <el-dialog :title="title" :visible.sync="isApplyForm" width="1100px" @open="handleOpen" :before-close="onCloseApplyForm">
      <apply-form @close="onCloseApplyForm"
                  ref="applyForm"
                  v-if="isApplyForm"
                  :roleType="operateType"
                  :type="applyFormType"
                  :applyId="applyId"/>
    </el-dialog>
  </div>
</template>

<script>
  import { getToken } from '@/utils/auth'
  import { listXunchaProcessApply, delXunchaProcessApply } from '@/api/xuncha/archives/xunchaProcessApply'
  import ExpandCollapse from '@/components/ExpandCollapse/index'
  import { formatDate } from '@/utils/index'
  import LunCi from '@/components/XunCha/LunCi/index'
  import Company from '@/components/XunCha/Company/index'
  import { selectDictData } from '@/api/xuncha/xunchaDictData'
  import ApplyForm from './applyForm/index.vue'
  import PageTitle from "@/components/PageTitle"

  export default {
    name: 'XunchaProcessApply',
    components: { ApplyForm, ExpandCollapse, LunCi, Company,PageTitle },
    data() {
      return {
        //操作类型  1 主管编辑  2 主责人操作  3 共享人操作  4 审批人操作
        operateType: '1',
        disabled: false,
        isApplyForm: false,
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
        // 巡察过程资料库_文件申请 表格数据
        xunchaProcessApplyList: [],
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
          url:
            process.env.VUE_APP_BASE_API +
            '/archives/xunchaProcessApply/importData'
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          serialNo: null,
          orgId: null,
          archiveOrgType: '',
          xunchaDate: null,
          xunchaLunciId: null,
          status: ''
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          serialNo: [
            { required: true, message: '业务单号不能为空', trigger: 'blur' }
          ],
          title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
          archiveType: [
            {
              required: true,
              message: '入库资料类型:数据字典不能为空',
              trigger: 'change'
            }
          ],
          archiveTypeName: [
            {
              required: true,
              message: '入库资料类型名称不能为空',
              trigger: 'blur'
            }
          ],
          orgId: [
            {
              required: true,
              message: '被巡察单位ID不能为空',
              trigger: 'change'
            }
          ],
          orgName: [
            {
              required: true,
              message: '被巡察单位名称不能为空',
              trigger: 'blur'
            }
          ],
          archiveOrgType: [
            {
              required: true,
              message: '归档组织类型不能为空',
              trigger: 'change'
            }
          ],
          archiveUserId: [
            { required: true, message: '归档人ID不能为空', trigger: 'blur' }
          ],
          archiveUserName: [
            { required: true, message: '归档人名称不能为空', trigger: 'blur' }
          ],
          xunchaTaskId: [
            { required: true, message: '巡察任务不能为空', trigger: 'blur' }
          ],
          xunchaTaskName: [
            { required: true, message: '巡察任务名称不能为空', trigger: 'blur' }
          ],
          xunchaGroup: [
            { required: true, message: '巡察组不能为空', trigger: 'blur' }
          ],
          xunchaPiciId: [
            { required: true, message: '巡察批次ID不能为空', trigger: 'blur' }
          ],
          xunchaPiciName: [
            { required: true, message: '巡察批次名称不能为空', trigger: 'blur' }
          ],
          xunchaDate: [
            { required: true, message: '巡察年份不能为空', trigger: 'blur' }
          ],
          xunchaLunciId: [
            { required: true, message: '巡察轮次ID不能为空', trigger: 'change' }
          ],
          xunchaLunciName: [
            { required: true, message: '巡察轮次名称不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '流程业务状态不能为空', trigger: 'blur' }
          ]
        },
        // 当前状态下拉框数据
        statusList: [
          { value: '草稿', label: '草稿' },
          { value: '归档中', label: '归档中' },
          { value: '巡察办室经理审核', label: '巡察办室经理审核' },
          { value: '巡察办部门领导审核', label: '巡察办部门领导审核' },
          { value: '已结束', label: '已结束' }
        ],
        // 归档对象下拉数据
        archiveObjectList: [],
        applyId: null,
        applyFormType: '',
        // 归档组织列表数据
        archiveOrgTypeList: [
          { label: 'A', name: '巡察组' },
          { label: 'B', name: '被巡察单位' },
          { label: 'C', name: '其他单位' }
        ]
      }
    },
    filters: {
      filterDate(val) {
        return formatDate(val)
      }
    },
    created() {
      this.getList()
      // 获得归档数据下拉数据
      selectDictData({ dictType: 'process_apply_archive' }).then((res) => {
        this.archiveObjectList = res.data || []
        console.log(this.archiveObjectList)
      })
    },
    methods: {
      /** 查询巡察过程资料库_文件申请 列表 */
      getList() {
        this.loading = true
        listXunchaProcessApply(this.queryParams).then((response) => {
          this.xunchaProcessApplyList = response.rows || []
          this.total = +response.total
          this.loading = false
        })
      },
      // 归档组织翻译
      archiveOrgTypeFormat(row, column) {
        for (let index in this.archiveOrgTypeList) {
          let item = this.archiveOrgTypeList[index]
          if (item.label === row.archiveOrgType){
            return item.name
          }
        }
        return row.archiveOrgType
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },

      // 表单重置
      reset() {
        this.form = {
          applyId: null,
          serialNo: null,
          title: null,
          archiveType: null,
          archiveTypeName: null,
          orgId: null,
          orgName: null,
          archiveOrgType: null,
          archiveObjectId: null,
          archiveObjectName: null,
          archiveUserId: null,
          archiveUserName: null,
          xunchaTaskId: null,
          xunchaTaskName: null,
          xunchaGroup: null,
          xunchaPiciId: null,
          xunchaPiciName: null,
          xunchaDate: null,
          xunchaLunciId: null,
          xunchaLunciName: null,
          status: '0',
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
        this.ids = selection.map((item) => item.applyId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.title="修改"
        this.applyFormType = '2'
        this.applyId = row.applyId
        this.isApplyForm = true
      },
      /**详情按钮*/
      handleQueryOption(row) {
        this.title="查看"
        // this.reset();
        this.applyFormType = '3'
        this.applyId = row.applyId
        this.isApplyForm = true
      },
      handleOpen() {
        this.$nextTick(() => {
          this.$refs.applyForm.handleOpen()
        })
      },
      handleApply() {
        this.title="新增"
        this.applyFormType = '1'
        this.applyId = null
        this.isApplyForm = true
      },
      onCloseApplyForm() {
        this.isApplyForm = false
        this.getList()
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const applyIds = row.applyId || this.ids
        this.$confirm(
          '是否确认删除文件申请 编号为"' + applyIds + '"的数据项?',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        )
          .then(function() {
            return delXunchaProcessApply(applyIds)
          })
          .then(() => {
            this.getList()
            this.msgSuccess('删除成功')
          })
          .catch(function() {
          })
      },
      // 获得当前状态
      getStatus(value) {
        for (let index in this.statusList) {
          if (this.statusList[+index].value === value) {
            return this.statusList[+index].label
          }
        }
        return value
      }
    }
  }
</script>


<style lang="scss" scoped>
  .dialog-context {
    .dialog-title {
      display: flex;
      align-items: center;
      font-size: 16px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.85);
      background: #fff;

      .line {
        display: inline-block;
        width: 4px;
        height: 17px;
        background: #1590ff;
        border-radius: 2px;
        margin-right: 10px;
      }
    }

    .context-row {
      padding: 20px 40px;
      margin-bottom: 15px;
      position: relative;

      .operation {
        position: absolute;
        right: 0;
        left: 0;
        top: -30px;
        margin: 10px 0px;
        text-align: right;
        padding-right: 40px;
      }
    }
  }

  .app-container {
    background: #f5f7f9;
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

