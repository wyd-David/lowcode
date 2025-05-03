<template>
  <div class="app-container">
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
          <el-form-item label="任务名称：" prop="taskName">
            <el-input v-model="queryParams.taskName" placeholder="请输入任务名称" clearable size="small"
              @keyup.enter.native="handleQuery" disabled />
          </el-form-item>
          <el-form-item label="巡察年份：" prop="year">
            <el-date-picker clearable size="small" style="width: 200px" v-model="queryParams.year" type="year"
              value-format="yyyy" placeholder="选择巡察年份" @change="changeYear">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="归属批次：" prop="piciId">
            <el-select v-model="queryParams.piciId" value-key="piciId" placeholder="请选择巡察批次">
              <el-option :label="item.piciName" v-for="item in selectPiciData" :key="item.piciId" :value="item.piciId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="巡察组：" prop="groupName">
            <el-input v-model="queryParams.groupName" placeholder="请输入巡察组名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="巡察方式：" prop="checkType">
            <el-select v-model="queryParams.checkType" placeholder="请选择巡察方式" clearable size="small">
              <el-option v-for="dict in checkTypeOptions" :key="dict.dataId" :label="dict.dataName"
                :value="dict.dataCode" />
            </el-select>
          </el-form-item>
          <el-form-item label="被巡察单位：" prop="company">
            <el-select v-model="queryParams.company" placeholder="请选择被巡察单位" clearable size="small">
              <el-option v-for="(item, index) in selectCompanyData" :key="index" :label="item.companyName"
                :value="item.companyName" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索
        </el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <page-title></page-title>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['xuncha:XunchaTaskInfo:add']">
            新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['xuncha:XunchaTaskInfo:edit']">
            修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['xuncha:XunchaTaskInfo:remove']">
            删除
          </el-button>
        </el-col>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>

      <el-table v-loading="loading" :data="XunchaTaskInfoList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="序号" type="index" width="55px" align="center" />
        <el-table-column label="任务名称" prop="taskName" :show-overflow-tooltip="true" />
        <el-table-column label="巡察年份" align="center" prop="checkYear" width="100px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.checkYear, '{y} 年') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="归属批次" prop="piciName" :show-overflow-tooltip="true" />
        <el-table-column label="巡察组" prop="groupName" :show-overflow-tooltip="true" />
        <el-table-column label="巡察方式" align="center" prop="checkTypeName" />
        <el-table-column label="任务时段" align="center" prop="startTime" width="190px">
          <template slot-scope="scope">
            <span>{{
            parseTime(scope.row.startTime, '{y}-{m}-{d}') +
            ' 至 ' +
            parseTime(scope.row.endTime, '{y}-{m}-{d}')
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="被巡察单位" prop="companyNames" :show-overflow-tooltip="true" />
        <el-table-column label="操作" align="left" width="220" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleLogs(scope.row)">操作日志
            </el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
              v-hasPermi="['xuncha:XunchaTaskInfo:remove']">删除
            </el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
              v-hasPermi="['xuncha:XunchaTaskInfo:edit']">修改
            </el-button>
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />
      </div>
    </div>

    <!-- 添加或修改巡察任务管理 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>巡察任务</span>
        </div>
        <div class="context-row">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="任务名称：" prop="taskName">
                  <el-input maxlength="100" show-word-limit style="width: 100%" v-model="form.taskName"
                    placeholder="请输入内容" disabled />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="巡察年份：" prop="checkYear">
                  <el-date-picker clearable size="small" style="width: 100%" v-model="form.checkYear" type="year"
                    value-format="yyyy" @change="handleChangeCheckYear" placeholder="选择巡察年份"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="归属批次：" prop="piciId">
                  <div style="display: flex">
                    <el-select style="width: 90%" v-model="form.piciId" @change="handleChangePiciId" value-key="piciId"
                      placeholder="请选择巡察批次">
                      <el-option :label="item.piciName" v-for="item in pcSelect" :key="item.piciId"
                        :value="item.piciId"></el-option>
                    </el-select>
                    <el-button @click="pcFormShow = true" style="margin-left: 10px" type="primary" size="small">添加批次
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务时段：" prop="dates">
                  <el-date-picker style="width: 100%" clearable size="small" v-model="form.dates" type="daterange"
                    value-format="yyyy-MM-dd" start-placeholder="开始日期" end-placeholder="结束日期" @input="daterangeChange">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="巡察组：" prop="groupName">
                  <el-select style="width: 100%" v-model="form.groupName" placeholder="请选择巡察组">
                    <el-option v-for="item in groupNameList" :label="item" :key="item" :value="item"></el-option>
                  </el-select>

                  <!--<el-input
                    maxlength="100"
                    show-word-limit
                    style="width: 100%"
                    v-model="form.groupName"
                    placeholder="请输入巡察组"
                  ></el-input>-->
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="巡察方式：" prop="checkType">
                  <el-select style="width: 100%" v-model="form.checkType" placeholder="请选择巡察方式">
                    <el-option v-for="dict in checkTypeOptions" :key="dict.dataId" :label="dict.dataName"
                      :value="dict.dataCode"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="被巡察单位：" prop="companyIds">
              <el-select style="width: 100%" multiple v-model="form.companyIds" placeholder="请选择"
                @change="companyChange">
                <el-option v-for="(dict, index) in CompanyAll" :key="dict.companyId + index" :label="dict.companyName"
                  :value="dict.companyId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="说明：" prop="remark">
              <el-input v-model="form.remark" type="textarea" maxlength="500" show-word-limit placeholder="请输入内容" />
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button v-if="!disabled" type="primary" @click="submitForm">确 定
        </el-button>
      </div>
    </el-dialog>

    <pcForm :open="pcFormShow" @close="pcFormClose"></pcForm>
    <tgForm :open="tgFormShow" @close="tgFormClose"></tgForm>
    <logs :id="logs.id" requestMapping="XunchaTaskInfo" :dialogVisible="logs.open" @close="logsClose"></logs>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import {
  listXunchaTaskInfo,
  getXunchaTaskInfo,
  delXunchaTaskInfo,
  addXunchaTaskInfo,
  updateXunchaTaskInfo,
  exportXunchaTaskInfo,
  disableEnable,
  queryPiciByYeay,
  queryGroupByPiciId,
  getGroupNameList
} from '@/api/xuncha/XunchaTaskInfo'
import { getCompanyAll } from '@/api/xuncha/XunchaLunciManage'
import ExpandCollapse from '@/components/ExpandCollapse/index'
import { formatDate } from '@/utils/index'
import pcForm from '../XunchaPiciManage/pcForm'
import tgForm from '../XunchaTaskGroupManage/tgForm'
import { selectDictData } from '@/api/xuncha/xunchaDictData'
import PageTitle from '@/components/PageTitle'
import logs from '../logs/index'

export default {
  name: 'XunchaTaskInfo',
  components: { ExpandCollapse, pcForm, tgForm, logs, PageTitle },
  data () {
    return {
      logs: {
        open: false,
        id: ''
      },
      // 列表查询条件巡察对象下拉框数据
      selectCompanyData: [],
      // 列表查询条件巡察批次下拉框数据
      selectPiciData: [],
      pcSelect: [],
      tgSelect: [],
      tgFormShow: false,
      pcFormShow: false,
      CompanyAll: [],
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
      // 巡察任务管理 表格数据
      XunchaTaskInfoList: [],
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
        url: process.env.VUE_APP_BASE_API + '/xuncha/XunchaTaskInfo/importData'
      },
      // 巡察方式
      checkTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        piciId: null,
        taskName: null,
        groupName: null,
        year: null,
        checkType: null,
        startTime: null,
        endTime: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
        company: ''
      },
      // 表单参数
      form: { companyIds: [], dates: [], groupName: "" },
      // 表单校验
      rules: {
        piciId: [
          { required: true, message: '巡察批次不能为空', trigger: 'change' }
        ],
        /*taskName: [
          { required: true, message: '任务名称不能为空', trigger: 'blur' }
        ],*/
        checkYear: [
          { required: true, message: '巡察年份不能为空', trigger: 'blur' }
        ],
        checkType: [
          { required: true, message: '巡察方式不能为空', trigger: 'change' }
        ],
        dates: [
          { required: true, message: '任务时段不能为空', trigger: 'blur' }
        ],
        groupName: [
          { required: true, message: '巡察组不能为空', trigger: 'blur' }
        ],
        companyIds: [
          { required: true, message: '公司不能为空', trigger: 'blur' }
        ]
      },
      // 巡察组名称列表
      groupNameList: ['第一组', '第二组', '第三组', '第四组', '第五组', '第六组', '第七组', '第八组', '第九组', '第十组']
    }
  },
  filters: {
    filterDate (val) {
      return formatDate(val)
    },
    xuncha_task_info_check_typeFieldFilter (val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val)
      if (!arr.length) return ''
      return arr[0].dictLabel
    }
  },
  created () {
    this.getList()
    // 巡察方式下拉框，使用的是 xuncha_dict_data 数据表
    /*this.getDicts('xuncha_task_info_check_type').then((response) => {
        this.checkTypeOptions = response.data || []
      })*/
    // 巡察方式下拉框
    const query = {
      dictType: 'xuncha_task_type'
    }
    selectDictData(query).then((res) => {
      this.checkTypeOptions = res.data || []
    })

    // this.getTgSelect();
    // 获得列表查询条件巡察对象下拉框数据
    getCompanyAll().then((res) => {
      this.selectCompanyData = res.data
    })
    // 获得批次下拉框数据
    queryPiciByYeay('').then((res) => {
      this.selectPiciData = res.data
    })
  },
  methods: {
    changeYear () {
      // 获得批次下拉框数据
      queryPiciByYeay(this.queryParams.year).then((res) => {
        this.selectPiciData = res.data
      })
    },
    handleLogs (row) {
      this.logs.open = true
      this.logs.id = row.taskId
    },
    logsClose () {
      this.logs.open = false
    },
    companyChange (val) {
      console.log(val)
      this.$forceUpdate()
    },
    handleChangePiciId (e, t) {
      let item = this.pcSelect.find((i) => i.piciId == e)
      // 重新获得被巡察单位下拉框数据
      this.getCompanyList()
      // 清空被巡察单位数据
      if (!t) {
        this.$set(this.form, 'companyIds', [])
      }
      let startDate = new Date(parseInt(item.startTime))
      let endDate = new Date(parseInt(item.endTime))
      let ALLstartTime = `${startDate.getFullYear()}-${startDate.getMonth() + 1
        }-${startDate.getDate()}`
      let AllendTime = `${endDate.getFullYear()}-${endDate.getMonth() + 1
        }-${endDate.getDate()}`
      this.form.startTime = ALLstartTime
      this.form.endTime = AllendTime
      if (!t) {
        this.$set(this.form, 'dates', [ALLstartTime, AllendTime])
        this.form.groupName = ''
      }
      this.getGroupNameListData()
    },
    handleChangeCheckYear (e) {
      // 修改了年份，需要将巡察轮次、巡察任务时段、被巡察单位 清空
      this.form.piciObj = null
      this.form.piciId = null
      this.form.piciName = null
      this.form.companyIds = []
      this.getPiciSelect()
    },
    tgFormClose (e) {
      this.tgFormShow = false
    },
    pcFormClose (e) {
      this.pcFormShow = false
      if (e && e.piciId) {
        this.form.piciId = e.piciId
        this.getPiciSelect()
      }
    },
    getPiciSelect (t) {
      queryPiciByYeay(this.form.checkYear).then((res) => {
        this.pcSelect = res.data
        if (res.data) {
          if (this.title == "新增巡察任务管理") {
            this.form.piciId = res.data[0].piciId;
          }
          this.handleChangePiciId(res.data[0].piciId, t);
        }
      })
    },
    getTgSelect () {
      queryGroupByPiciId('').then((res) => {
        this.tgSelect = res.data
      })
    },
    getCompanyList () {
      getCompanyAll(this.form.piciId).then((response) => {
        this.CompanyAll = response.data || []
      })
    },
    /** 查询巡察任务管理 列表 */
    getList () {
      this.loading = true
      listXunchaTaskInfo(this.queryParams).then((response) => {
        this.XunchaTaskInfoList = response.rows || []
        this.total = +response.total
        this.loading = false
      })
    },
    // 巡察方式
    checkTypeFormat (row, column) {
      return this.selectDictLabel(this.checkTypeOptions, row.checkType)
    },
    // 取消按钮
    cancel () {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset () {
      this.form = {
        taskId: null,
        piciId: null,
        taskName: null,
        groupName: '',
        checkYear: null,
        checkType: null,
        startTime: null,
        endTime: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
        dates: []
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map((item) => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd () {
      // 新增页面巡察批次和被巡察单位默认为空
      this.pcSelect = []
      this.CompanyAll = []
      this.disabled = false
      this.reset()
      this.open = true
      this.title = '新增巡察任务管理 '
      this.groupNameList = ['第一组', '第二组', '第三组', '第四组', '第五组', '第六组', '第七组', '第八组', '第九组', '第十组']
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.disabled = false
      this.reset()
      const taskId = row.taskId || this.ids
      getXunchaTaskInfo(taskId).then((response) => {
        this.form = response.data
        this.form.piciObj = { piciId: response.data.piciId }
        this.form.dates = [response.data.startTime, response.data.endTime]
        // let tempYear = response.data.checkYear.split("-")[0];
        // this.form.checkYear = tempYear;
        this.getPiciSelect(1)
        this.getCompanyList()
        this.open = true
        this.title = '修改巡察任务管理 '
        this.getGroupNameListData()
      })
    },
    /**详情按钮*/
    handleQueryOption (row) {
      this.disabled = true
      const taskId = row.taskId || this.ids
      getXunchaTaskInfo(taskId).then((response) => {
        this.form = response.data
        this.form.piciObj = { piciId: response.data.piciId }
        this.form.dates = [response.data.startTime, response.data.endTime]
        //let tempYear = response.data.checkYear.split("-")[0];
        // this.form.checkYear = tempYear;
        this.getPiciSelect(1)
        this.getCompanyList()
        this.open = true
        this.title = '查看巡察任务管理 '
      })
    },
    /** 提交按钮 */
    submitForm () {
      if (this.disabled) {
        this.open = false
        return
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$confirm('是否确定提交?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            if (this.form.checkYear.length === 4) {
              this.form.checkYear = this.form.checkYear + '-01-01'
            }
            if (this.form.taskId != null) {
              this.form.startTime = this.form.dates[0]
              this.form.endTime = this.form.dates[1]
              updateXunchaTaskInfo(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                }
              })
            } else {
              this.form.startTime = this.form.dates[0]
              this.form.endTime = this.form.dates[1]
              addXunchaTaskInfo(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess('新增成功')
                  this.open = false
                  this.getList()
                }
              })
            }
          })
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const taskIds = row.taskId || this.ids
      this.$confirm('是否删除当前任务', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(function () {
          return delXunchaTaskInfo(taskIds)
        })
        .then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        })
        .catch(function () {
        })
    },
    /** 禁用或启用操作*/
    disabledBtnClick (type) {
      var ids = this.ids || []
      this.$confirm('是否确认' + (type === 0 ? '启用' : '禁用') + '?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(function () {
          return disableEnable({
            ids: ids.toString(),
            disableEnableState: type
          })
        })
        .then(() => {
          this.getList()
          this.msgSuccess((type === 0 ? '启用' : '禁用') + '成功')
        })
        .catch(function () {
        })
    },
    // 更新
    daterangeChange (e) {
      // 对计划巡察时段时间校验
      if (!this.form.startTime || !this.form.endTime) {
        this.$message.error('请选择批次！')
        this.form.dates = []
      } else if (
        this.stringToDate(this.form.startTime) > this.stringToDate(e[0]) ||
        this.stringToDate(this.form.endTime) < this.stringToDate(e[1])
      ) {
        this.$message.error('任务时段不在批次计划时间范围内！')
        this.form.dates = [this.form.startTime, this.form.endTime]
      }
      this.form.groupName = ''
      this.getGroupNameListData()
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },
    // 将日期字符串转为日期
    stringToDate (str) {
      const dateStrs = str.split('-')
      const year = parseInt(dateStrs[0], 10)
      const month = parseInt(dateStrs[1], 10) - 1
      const day = parseInt(dateStrs[2], 10)
      return new Date(year, month, day)
    },
    // 获得巡察组名称列表数据
    getGroupNameListData () {
      getGroupNameList(this.form.taskId, this.form.piciId).then(res => {
        this.groupNameList = res.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
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

/deep/ .el-form-item__error {
  width: 250px;
}

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
</style>
