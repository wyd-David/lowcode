<template>
  <div>
    <!-- 添加或修改巡察批次管理 对话框 -->
    <el-dialog
      :title="title"
      :visible="open"
      width="1000px"
      append-to-body
      @open="init"
      @close="close"
      :close-on-click-modal="false"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>巡察批次</span>
        </div>
        <div class="context-row">
          <el-form
            :disabled="disabled"
            ref="form"
            :model="form"
            :rules="rules"
            label-width="150px"
          >
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="归属轮次：" prop="lunciId">
                  <div style="display: flex">
                    <el-select
                      style="width: 100%"
                      v-model="form.lunciObj"
                      @change="handleChange"
                      value-key="lunciId"
                      placeholder="请选择归属轮次"
                      disabled
                    >
                      <el-option
                        v-for="(item, index) in lcSelect"
                        :label="item.lunciName"
                        :value="item"
                        :key="index"
                      />
                    </el-select>
                    <!--<el-button
                      @click="lcShow = true"
                      style="margin-left: 10px"
                      type="primary"
                      size="small"
                    >添加轮次
                    </el-button
                    >-->
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="年份跨度：">
                  <div style="display: flex">
                    <el-input
                      style="width: 49%"
                      readonly
                      :value="form.yearStart"
                    ></el-input>
                    <span style="width: 2%">-</span>
                    <el-input
                      style="width: 49%"
                      readonly
                      :value="form.yearEnd"
                    ></el-input>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="计划巡察时段：" prop="dates">
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.dates"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    @input="daterangeChange"
                    :clearable="false"
                    disabled
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="巡察批次：" prop="piciName">
                  <el-select style="width: 100%" v-model="form.piciName" placeholder="请输入巡察批次" disabled>
                    <el-option v-for="item in piciNameList" :key="item" :label="item" :value="item"></el-option>
                  </el-select>
                  <!--<el-input
                    v-model="form.piciName"
                    placeholder="请输入巡察批次"
                  />-->
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="被巡察单位：" prop="companyIds">
                  <el-select
                    style="width: 100%"
                    multiple
                    v-model="form.companyIds"
                    placeholder="请选择被巡察单位"
                    disabled
                  >
                    <el-option
                      v-for="dict in CompanyAll"
                      :key="dict.companyId"
                      :label="dict.companyName"
                      :value="dict.companyId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="当前阶段：" prop="status">
                  <el-select
                    style="width: 100%"
                    v-model="form.status"
                    placeholder="请选择当前阶段"
                  >
                    <el-option
                      v-for="dict in statusOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="说明：" prop="remark">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
                disabled
              />
            </el-form-item>
          </el-form>
        </div>
        <div v-if="disabled">
          <div class="dialog-title">
            <span class="line"></span><span>巡察进度</span>
          </div>
          <div class="context-row">
            <el-table :data="form.processInfoList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column
                label="巡察阶段"
                prop="statusName"
              ></el-table-column>
              <el-table-column label="完成日期" prop="finishTime">
                <template slot-scope="scope">
                  <span>{{
                    parseTime(scope.row.finishTime, '{y}-{m}-{d} {h}:{m}:{s}')
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="修改人员"
                prop="updateName"
              ></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="submitForm" v-if="!disabled"
        >确 定
        </el-button
        >
      </div>
    </el-dialog>
    <lcForm :open="lcShow" @close="lcClose"></lcForm>
  </div>
</template>
<script>
  import {
    getXunchaPiciManage,
    addXunchaPiciManage,
    updateXunchaPiciManage,
    getPiciNameList
  } from '@/api/xuncha/XunchaPiciManage'
  import { selectLunciData, getCompanyAll } from '@/api/xuncha/XunchaLunciManage'
  import lcForm from '../XunchaLunciManage/lcForm'

  export default {
    props: {
      open: {
        type: Boolean,
        default: false
      },
      id: {
        type: String,
        default: ''
      },
      disabled: {
        type: Boolean,
        default: false
      }
    },
    components: { lcForm },
    data() {
      return {
        CompanyAll: [],
        lcShow: false,
        lcSelect: [],
        title: '新增巡察批次',
        statusOptions: [],
        // 巡察批次名称下拉框数据
        piciNameList: ['第一批', '第二批', '第三批', '第四批', '第五批'],
        // 表单参数
        form: {
          companyIds: [],
          dates: []
        },
        // 表单校验
        rules: {
          lunciId: [
            {
              required: true,
              message: '巡察轮次不能为空',
              trigger: 'change'
            }
          ],
          piciName: [
            { required: true, message: '巡察批次名称不能为空', trigger: 'blur' }
          ],
          dates: [
            { required: true, message: '计划巡察时段不能为空', trigger: 'blur' }
          ],
          companyIds: [
            { required: true, message: '被巡察单位不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '当前阶段不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleChange(e) {
        this.form.yearStart = e.startTime
        this.form.yearEnd = e.endTime
        this.form.lunciId = e.lunciId
        this.$forceUpdate()
      },
      lcClose(e) {
        this.lcShow = false
        this.getLcList()
        if (e && e.lunciId) {
          this.form.lunciObj = e
          this.form.lunciId = e.lunciId
          this.form.yearStart = e.startTime
          this.form.yearEnd = e.endTime
        }
      },
      init() {
        this.piciNameList = ['第一批', '第二批', '第三批', '第四批', '第五批']
        this.getDicts('xuncha_pici_manager_status').then((response) => {
          this.statusOptions = response.data || []
          if (this.id) {
            this.form.status = response.data[0].dictValue
          }
        })
        getCompanyAll().then((response) => {
          this.CompanyAll = response.data || []
        })
        this.getLcList()
        if (this.id) {
          if (this.disabled) {
            this.title = '查看巡察批次'
          } else {
            this.title = '修改巡察批次阶段'
          }
          this.getDetail()
        } else {
          this.reset()
          this.title = '新增巡察批次'
        }
      },
      getLcList() {
        selectLunciData().then((res) => {
          this.lcSelect = res.data
        })
      },
      /** 提交按钮 */
      submitForm() {
        if (this.form.dates) {
          this.form.startTime = this.form.dates[0]
          this.form.endTime = this.form.dates[1]
        }
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$confirm('是否确定提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if (this.id) {
                updateXunchaPiciManage(this.form).then((response) => {
                  if (response.code === 200) {
                    this.msgSuccess('修改成功')
                    this.close()
                  }
                })
              } else {
                addXunchaPiciManage(this.form).then((response) => {
                  if (response.code === 200) {
                    this.msgSuccess('新增成功')
                    this.close(response.data)
                  }
                })
              }
            })
          }
        })
      },
      // 表单重置
      reset() {
        this.form = {
          piciId: null,
          lunciId: null,
          piciName: null,
          startTime: null,
          endTime: null,
          status: null,
          remark: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null
        }
        this.resetForm('form')
      },
      // 获得巡察批次实体数据
      getDetail() {
        getXunchaPiciManage(this.id).then((res) => {
          this.form = res.data
          this.form.lunciObj = {
            lunciId: res.data.lunciId
          }
          this.form.dates = [res.data.startTime, res.data.endTime]
          let tempObj = this.lcSelect.filter(
            (item) => item.lunciId == res.data.lunciId
          )[0]
          this.form.yearStart = tempObj.startTime
          this.form.yearEnd = tempObj.endTime
          // 获得巡察批次名称下拉框数据
          this.getPiciNameListData()
        })
      },
      close(e) {
        this.$emit('close', e)
      },
      // 更新
      daterangeChange(e) {
        // 对计划巡察时段时间校验
        if (!this.form.yearEnd || !this.form.yearStart) {
          this.$message.error('请选择轮次！')
          this.form.dates = []
        } else if (
          this.stringToDate(this.form.yearStart) > this.stringToDate(e[0]) ||
          this.stringToDate(this.form.yearEnd) < this.stringToDate(e[1])
        ) {
          this.$message.error('计划巡察时段不在年份跨度范围内！')
          this.form.dates = [this.form.yearStart, this.form.yearEnd]
        }
        // 查询加载巡查批次名称
        this.form.piciName = null
        this.getPiciNameListData()

        this.$nextTick(() => {
          this.$forceUpdate()
        })
      },
      // 将日期字符串转为日期
      stringToDate(str) {
        const dateStrs = str.split('-')
        const year = parseInt(dateStrs[0], 10)
        const month = parseInt(dateStrs[1], 10) - 1
        const day = parseInt(dateStrs[2], 10)
        return new Date(year, month, day)
      },
      // 获得批次名称下拉框数据
      getPiciNameListData() {
        getPiciNameList(this.form.piciId, this.form.dates[0].slice(0, 4)).then(res => {
          this.piciNameList = res.data
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
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
