<template>
  <div class="app-main">
    <todo-bpm-header/>
    <!-- 主体内容 -->
    <div class="dialog-context">
      <div class="context-row">
        <el-form ref="form" :model="form" :rules="rules" label-width="180px" :disabled="disabled">
          <!-- 标签 -->
          <div class="dialog-title" style="margin-bottom:10px">
            <span class="line"></span><span>基础信息</span>
          </div>
          <!-- <div style="width: 100%; height: 40px; line-height: 40px; margin-bottom: 10px">
            <span style="float:right">
              <el-button type="primary" size="mini" @click="handleGenerate">生成确认单</el-button>
            </span>
          </div> -->
          <el-form-item label="预编号：" prop="spendCode">
            <el-input v-model="form.spendCode" placeholder="请输入预编号" disabled/>
          </el-form-item>
          <el-form-item label="事实确认单编号：" prop="voucherCode">
            <el-input v-model="form.voucherCode" placeholder="请输入事实确认单编号" disabled/>
          </el-form-item>
          <el-form-item label="问题底稿编号：" prop="matterCode">
            <el-input v-model="form.matterCode" placeholder="请输入问题底稿编号" disabled/>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-date-picker clearable size="small" style="width: 100%" v-model="form.xunchaYear"
                                type="year" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择巡察年份"
                                @change="xunchaYearChange">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="巡察批次：" prop="piciId">
                <el-select v-model="form.piciId" style="width: 100%;" placeholder="请选择巡察批次"
                           @change="handlePiciChange">
                  <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                             :value="item.piciId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="20">
              <el-form-item label="巡察组：" prop="groupName">
                <el-select v-model="form.groupName" style="width: 100%" placeholder="请选择巡察组"
                           @change="handlexunchazuChange">
                  <el-option v-for="item in groupNameDataList" :key="item.taskId" :label="item.groupName"
                             :value="item.taskId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4" style="margin-top: 0.25rem;text-align:right">
              <el-button type="primary" size="mini" @click="handleGenerateDetails" :disabled="false">巡察组详情</el-button>
            </el-col>
            <el-col :span="12">
              <el-form-item label="任务时间：" prop="taskDateFrame">
                <el-date-picker :disabled="true" style="width: 100%" v-model="form.taskDateFrame"
                                type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="被巡察单位：" prop="orgName">
                <el-select v-model="form.orgName" style="width: 100%;" placeholder="请选择被巡察单位"
                           :disabled="true">
                  <el-option v-for="item in orgIdDataList" :key="item.value" :label="item.label"
                             :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <div class="dialog-title" style="margin-bottom:10px">
            <span class="line"></span><span>问题信息</span>
          </div>

          <el-row style="text-align:left">
            <el-col :span="12">
              <el-form-item label="事实领域：" prop="territoryIdList">
                <el-select v-model="form.territoryIdList" :multiple="true" style="width:100%"
                           placeholder="请选择事实领域">
                  <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                             :value="item.dataCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="事实概述：" prop="truthOverview">
            <el-input type="textarea" :rows="3" placeholder="请输入事实概述" v-model="form.truthOverview"
                      maxlength="20000" :show-word-limit="true" />
            <span
              style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：问题概述是指巡视报告中的面上问题，原则上应与巡视报告相应表述一致，根据实际可按一事一底稿或一个问题一底稿制作</span>
          </el-form-item>
          <el-form-item label="具体事实：" prop="specificTruth">
            <el-input type="textarea" :rows="3" placeholder="请输入具体事实" v-model="form.specificTruth"
                      maxlength="20000" :show-word-limit="true" />
            <span
              style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：巡察了解情况是指巡视组工作人员了解核实问题的过程及事实描述，包括但不限于了解方式、责任主体、时间、地点、主要情节、造成影响等，特别是有关违纪违规问题的定性依据，以及与被巡视党组织主要负责人的沟通情况。</span>
          </el-form-item>
          <el-form-item label="承办人：" prop="agentIdList">
            <el-select v-model="form.agentIdList" class="angetSelect" :multiple="true" style="width: 100%"
                       placeholder="请选择承办人">
              <el-option v-for="item in createdByDataList"
                  :key="item.id"
                  :label="item.userName+'（'+item.userId+' - '+item.labelValue+'）'"
                  :value="item.id" :disabled="item.disabled">
              </el-option>
            </el-select>
            <span style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：承办人至少为两人。</span>
          </el-form-item>

          <!-- 标签 -->
          <div class="dialog-title" style="margin-bottom:10px">
            <span class="line"></span><span>附件材料</span>
          </div>
          <el-form-item label="事实确认单附件：" prop="matterCode">
            <el-button type="text" :title="form.fileName" class="accessory" style="font-size: 12px; color: #1590ff">
              {{ form.fileName }}
            </el-button>
            <el-button type="primary" v-show="!formDisabled" size="mini" @click="uploadFileBtn()">
              <span v-if="form.fileId">重新上传</span>
              <span v-else>上传文件</span>
            </el-button>
            <el-button type="primary"
                       size="mini"
                       @click="onPreview()"
                       v-if="form.fileId"
                       :disabled="false">
              预览
            </el-button>
            <el-button type="primary"
                       size="mini"
                       @click="onDownload()"
                       v-if="form.fileId"
                       :disabled="false">
              下载
            </el-button>
          </el-form-item>
        </el-form>
        <!--
            @initProcess 获得流程信息数据
            businessKey 业务主键，可用于判断是否使用默认按钮，如果为空使用默认按钮
            @saveDraftData 暂存事件
            @close 关闭窗口事件
            needBeforeComplete 保存前是否具有前置操作
            @beforeComplete 保存前的前置操作逻辑
        -->
        <deep-btn ref="deepBtnRef" isTodo v-bind="$attrs" @initProcess="initProcess" :businessKey="bussinessKey"
                  @saveDraftData="saveBtn" :needBeforeComplete="true"
                  @beforeComplete="beforeComplete">
          <!-- 发起默认按钮，由 businessKey 控制，为空的话显示下面的默认按钮 -->
          <!--<span slot="btns" v-if="form.id == null">
                        &lt;!&ndash; <el-button @click="cancel">关闭1 </el-button> &ndash;&gt;
                        <el-button type="primary" @click="handleDraft">暂存1</el-button>
                        <el-button type="primary" @click="submitForm">提交1</el-button>
                    </span>-->
        </deep-btn>
      </div>
    </div>

    <!-- 生成确认单 -->
    <el-dialog title="生成确认单" :visible.sync="generateShow" width="800px" append-to-body="append-to-body">
      <el-form ref="generateform" :rules="generateformRules" :model="generateform" label-width="160px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="巡察年份:" prop="xunchaYear">
              <el-date-picker clearable size="small" style="width: 210px"
                              v-model="generateform.xunchaYear" type="year" value-format="yyyy" placeholder="选择巡察年份"
                              @change="xunchaYearChange02">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次:" prop="piciId">
              <el-select v-model="generateform.piciId" style="width: 210px;float:right"
                         placeholder="请选择巡察批次" @change="handlePiciChange02"
                         :disabled="generateform.piciIdshow02">
                <el-option v-for="item in piciIdDataList02" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="巡察组:" prop="groupName">
          <el-select v-model="generateform.groupName" style="width: 100%" placeholder="请选择巡察组"
                     @change="handlexunchazuChange02">
            <el-option v-for="item in groupNameDataList02" :key="item.taskId" :label="item.groupName"
                       :value="item.taskId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题底稿:" prop="matterCode">
          <el-select v-model="generateform.matterCode" :multiple="true" style="width: 100%"
                     placeholder="请选择问题底稿">
            <el-option v-for="item in matterCodeDataList" :key="item.id" :label="item.spendCode"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="generateShow = false">关 闭</el-button>
        <el-button @click="clickGenerate" type="primary">生成</el-button>
      </div>
    </el-dialog>

    <!-- 巡察组详情 -->
    <el-dialog title="查看分组" :visible.sync="groupingShow" width="900px" :close-on-click-modal="false"
               append-to-body="append-to-body">
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>巡察信息</span>
        </div>
        <el-form ref="queryform" :model="formQuery" label-width="130px">
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="组名：" prop="groupName">
                <el-input style="width: 100%" v-model="formQuery.groupName"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="成员数：" prop="teamMembersCount">
                <el-input style="width: 100%" v-model="formQuery.teamMembersCount"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-input style="width: 100%" v-model="formQuery.xunchaYear"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="巡察批次：" prop="xunchaPici">
                <el-input style="width: 100%" v-model="formQuery.xunchaPici"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="关联巡察任务：" prop="taskName">
                <el-input style="width: 100%" v-model="formQuery.taskName"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="计划巡察时段：" prop="xunchaTimeSolt">
                <el-input style="width: 100%" v-model="formQuery.xunchaTimeSolt"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="23">
              <el-form-item label="被巡察单位：" prop="xunchaPici">
                <el-input style="width: 100%" v-model="formQuery.company"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <div class="dialog-title" style="justify-content: space-between">
          <div><span class="line"></span><span>组成员信息</span></div>
        </div>

        <div class="context-row">
          <el-table :data="formQuery.xunchaGroupUserVoList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="组内标签/职责" prop="labelValue"></el-table-column>
            <el-table-column label="姓名" prop="userName">
              <template slot-scope="scope">{{scope.row.userName}}
              </template>
            </el-table-column>

            <el-table-column label="所在单位" prop="companyName"></el-table-column>
            <el-table-column label="部门" prop="dpName"></el-table-column>
            <el-table-column label="领域" prop="fieldValue"></el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
    <!-- 文件上传组件 -->
    <uploadForm :dialogVisible="uploadFormShow" @file-success="fileSuccess" @close="uploadFormShow = false"
                singleFile ref="uploadForm"/>
  </div>
</template>

<script>
  import {
    listCourseVoucher,
    getCourseVoucher,
    delCourseVoucher,
    addCourseVoucher,
    updateCourseVoucher,
    exportCourseVoucher,
    importTemplate,
    disableEnable,
    getXunchaGroupInfoList,
    getStartFlow,
    getUndertaker,
    finishFormTask,
    getPiciSelectData,
    getGroupSelectData,
    getPapersSelectData,
    getPapersData,
    getGroupLeader
  } from "@/api/course/courseVoucher";
  import {queryPiciList} from "@/api/xuncha/common/base";
  import {listXunchaTaskInfo} from "@/api/xuncha/XunchaTaskInfo";
  import {selectDictData} from "@/api/xuncha/xunchaDictData";
  import deepBtn from "@/components/WorkFlow/deepBtn";
  import {downloadFile, previewFile, downloadZipFile,} from "@/utils/uploadOperate";
  import uploadForm from "@/components/CommonUpload/uploadForm";
  import completeProcess from "@/components/WorkFlow/completeProcess";
  import {fetchWorkFlowInfo} from "@/utils/workflow";
  import AllFlowStep from "@/components/WorkFlow/AllFlowStep";

  export default {
    components: {deepBtn, uploadForm, completeProcess, AllFlowStep},
    data() {
      return {
        // 流程数据信息
        process: null,
        // 主键
        bussinessKey: null,
        // 表单数据
        form: {},
        // 表单校验
        rules: {
          xunchaYear: [
            {required: true, message: '请选择巡察年份', trigger: 'change'}
          ],
          piciId: [
            {required: true, message: '请选择巡察批次！', trigger: 'change'}
          ],
          groupName: [
            {required: true, message: '请选择巡察组！', trigger: 'change'}
          ],
          // taskDateFrame: [
          //   {required: true, message: '请选择任务时段！', trigger: 'change'}
          // ],
          // orgName: [
          //   {required: true, message: '请选择被巡察单位！', trigger: 'change'}
          // ],
          territoryIdList: [
            {required: true, message: '请选择事实领域！', trigger: 'change'}
          ],
          truthOverview: [
            {required: true, message: '请填写事实概述！', trigger: 'blur'}
          ],
          specificTruth: [
            {required: true, message: '请填写具体事实！', trigger: 'blur'}
          ],
          agentIdList: [
            {required: true, message: '请填写承办人！', trigger: 'change'}
          ]
        },
        //
        generateformRules: {
          'generateform.xunchaYear': [
            {required: true, message: '请选择巡察年份', trigger: 'change'}
          ],
          'generateform.piciId': [
            {required: true, message: '请选择巡察批次！', trigger: 'change'}
          ],
          'generateform.groupName': [
            {required: true, message: '请选择巡察组！', trigger: 'change'}
          ],
          'generateform.matterCode': [
            {required: true, message: '请选择问题底稿！', trigger: 'change'}
          ],
        },
        // 是否禁用表单数据
        disabled: false,
        // 是否显示上传文件按钮和重新上传文件按钮
        formDisabled: false,
        // 巡查批次
        piciIdDataList: [],
        //巡查组
        groupNameDataList: [],
        // 被巡察单位
        orgIdDataList: [],
        // 事实领域
        fieldDataList: [],
        // 承办人
        createdByDataList: [],
        // 是否显示问题底稿
        generateShow: false,
        // 生成确认单数据
        generateform: {},
        // 生成确认单 - 巡查批次
        piciIdDataList02: [],
        // 生成确认单 - 巡查组
        groupNameDataList02: [],
        // 生成确认单 - 问题底稿
        matterCodeDataList: [
          {value: '01', label: '底稿1'},
          {value: '02', label: '底稿2'},
        ],
        // 是否显示巡察组详情
        groupingShow: false,
        // 巡察组详情数据
        formQuery: {},
        // 是否显示文件上传组件
        uploadFormShow: false,
      }
    },
    created() {
      this.bussinessKey = this.$route.query.bussinessKey
      // 获得实体数据，回显：巡查批次下拉框数据、巡察组下拉框数据、承办人下拉框数据
      getCourseVoucher(this.bussinessKey).then(res => {
        if (res.data.xunchaYear != null) {
          res.data.xunchaYear = res.data.xunchaYear.toString();
        }
        let newArray = [];
        if (res.data.taskDateFrame != null) {
          newArray = res.data.taskDateFrame.split(",");
        }
        res.data.taskDateFrame = newArray;
        let createdByDataList = [];
        // 回显巡查批次下拉框数据
        getPiciSelectData({year: res.data.xunchaYear.slice(0, 4)}).then((res) => {
          if (res.data.length > 0) {
            this.piciIdDataList = res.data;
          } else {
            this.piciIdDataList = [];
          }
        })
        // 回显巡察组下拉框数据
        getGroupSelectData({piciId: res.data.piciId}).then((res) => {
          if (res.data.length > 0) {
            this.groupNameDataList = res.data;
          } else {
            this.groupNameDataList = [];
          }
        })
        // 回显承办人下拉框数据
        getUndertaker({taskId: res.data.taskId, id: res.data.id}).then((res) => {
          createdByDataList.push({
            userId: res.data.currentBy.userId,
            userName: res.data.currentBy.userName,
            disabled: true,
            id: res.data.currentBy.userId,
            labelValue:res.data.currentBy.labelValue,
          })
          createdByDataList.push(...res.data.undertake);
          this.createdByDataList = createdByDataList;
        })
        this.form = res.data;
        this.open = true;
      });
      // 获得事实领域下拉框数据
      selectDictData({dictType: 'course_voucher_territory'}).then((res) => {
        this.fieldDataList = res.data;
      })
    },
    methods: {
      // 生成确认单按钮
      handleGenerate() {
        this.generateShow = true;
      },
      // 根据巡察年份获取关联
      xunchaYearChange(date) {
        let dataList = [];
        this.form.piciId = null;
        this.form.groupName = null;
        this.form.taskDateFrame = [];
        this.form.orgName = null;
        getPiciSelectData({year: date.slice(0, 4)}).then((res) => {
          if (res.data.length > 0) {
            dataList = res.data;
          } else {
            dataList = [];
          }
          this.piciIdDataList = dataList;
        })
      },
      //根据巡察批次获取关联
      handlePiciChange(val) {
        let dataList = [];
        this.form.groupName = null;
        this.form.taskDateFrame = [];
        this.form.orgName = null;
        getGroupSelectData({piciId: val}).then((res) => {
          if (res.data.length > 0) {
            dataList = res.data;
          } else {
            dataList = [];
          }
          this.groupNameDataList = dataList;
        })
      },
      //根据巡察组获取关联
      handlexunchazuChange(val) {
        let newData = this.groupNameDataList;
        let newObject = {};
        let orgIdDataList = [];
        let createdByDataList = [];
        if (newData != null) {
          newData.map((item, index) => {
            if (item.taskId === val) {
              newObject = item;
              this.form.taskDateFrame = [item.startTime, item.endTime];
              orgIdDataList.push({
                value: item.companyNames,
                label: item.companyNames,
              })
              this.orgIdDataList = orgIdDataList;
              this.form.orgName = item.companyNames;
              this.form.taskId = item.taskId;
              getUndertaker({taskId: this.form.taskId}).then((res) => {
                createdByDataList.push({
                  userId: res.data.currentBy.userId,
                  userName: res.data.currentBy.userName,
                  disabled: true,
                  id: res.data.currentBy.userId,
                  labelValue:res.data.currentBy.labelValue,
                })
                createdByDataList.push(...res.data.undertake);
                this.createdByDataList = createdByDataList;
                this.form.agentIdList = [res.data.currentBy.userId]
              })
            }
          })
        }
      },
      /**巡察组详情 */
      handleGenerateDetails() {
        getXunchaGroupInfoList({taskId: this.form.taskId}).then((res) => {
          if (res.data) {
            this.formQuery = res.data;
            this.groupingShow = true;
            this.groupingTitle = "查看分组";
          } else {
            this.$message.error("该巡察组没有进行分组！")
          }
        })
      },
      // 初始化流程信息
      initProcess(value) {
        // 保存流程信息
        this.process = value
        // 判断当前是否可以编辑，当节点为 user01 可编辑
        this.disabled = this.process.taskDefinitionKey !== 'user01'
      },
      // 点击暂存按钮
      saveBtn() {
        this.$refs["form"].validate((valid) => {

          if (valid) {
            // 判如果承办人小于两个，则不能提交
            if (this.form.agentIdList.length <= 1){
              this.msgError("承办人至少为两人");
              return
            }
            this.$confirm("是否确定暂存?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              // 暂存数据
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.taskDateFrame = this.form.taskDateFrame.toString();
              updateCourseVoucher(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("已暂存");
                  this.open = false;
                  this.getList();
                }
              });
            });
          }
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
      },
      // 点击下一步前的操作
      beforeComplete(fn) {
        // 如果是 user01 节点，那么提交前需要进行保存，并且选人对象为组长/副组长
        if (this.process.taskDefinitionKey === 'user01') {
          this.$refs["form"].validate((valid) => {
            if (valid) {
              // 判如果承办人小于两个，则不能提交
              if (this.form.agentIdList.length <= 1){
                this.msgError("承办人至少为两人");
                return
              }
              // 保存数据
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.taskDateFrame = this.form.taskDateFrame.toString();
              updateCourseVoucher(this.form).then(response => {
                if (response.code === 200) {
                  this.open = false;
                  fn();
                }
              });
              // 【自定义选人】组员发起流程选人为组长/副组长
              getGroupLeader({taskId: this.form.taskId}).then(res => {
                this.process.nextStepAndUser[0].nextStepUser = res.data
              })
            }
          });
        } else {
          fn();
        }
      },
      // 根据巡察年份获取关联
      xunchaYearChange02(date) {
        this.piciIdDataList02 = [];
        this.groupNameDataList02 = [];
        this.matterCodeDataList = []
        this.generateform.piciId = null
        this.generateform.groupName = null
        this.generateform.taskId= null
        this.generateform.matterCode = []
        getPiciSelectData({year: date.slice(0, 4)}).then((res) => {
          if (res.data.length > 0) {
            this.piciIdDataList02 = res.data;
          } else {
            this.piciIdDataList02 = [];
          }
          this.generateform.piciIdshow02 = false;
        })
      },
      //根据巡察批次获取关联
      handlePiciChange02(val) {
        this.groupNameDataList02 = [];
        this.matterCodeDataList = []
        this.generateform.groupName = null
        this.generateform.taskId= null
        this.generateform.matterCode = []
        this.generateform.piciId = val
        // 获得巡察组下拉框数据
        getGroupSelectData({piciId: val}).then((res) => {
          this.groupNameDataList02 = res.data;
        })
      },
      //根据巡察组获取关联
      handlexunchazuChange02(val) {
        this.matterCodeDataList = []
        this.generateform.matterCode = []
        // 获得问题底稿数据
        getPapersSelectData({taskId: val}).then((res) => {
          this.matterCodeDataList = res.data;
        })
      },
      /**生成确认单 */
      clickGenerate() {
        // 表单验证
        this.$refs["generateform"].validate(valid => {
          if (valid) {
            getPapersData({papersIdArr: this.generateform.matterCode}).then(res => {
              this.form.specificTruth += res.data.specificTruth
              this.form.truthOverview += res.data.truthOverview
              this.generateShow = false;
            })
          }
        })
      },
      // 点击上传文件按钮
      uploadFileBtn() {
        this.uploadFormShow = true;
      },
      // 文件上传成功回调方法
      fileSuccess(files) {
        this.msgSuccess("上传成功");
        this.form.fileId = files[0].result.data.fileId;
        let name = files[0].result.data.fileName;
        this.form.name = name;
        this.form.fileName = name.substring(0, name.lastIndexOf("."));
      },
      // 预览文件
      onPreview() {
        previewFile(this.form.fileId);
      },
      // 文件下载
      onDownload() {
        downloadFile(this.form.fileId);
      },
    }
  }
</script>

<style lang="scss">
  .app-main {
    padding: 15px;
    background: #fff;
  }

  .app-main .dialog-context .context-row .operations .dialog-btn-box {
    z-index: 2000 !important;
  }

  .form-module-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;
    margin-bottom: 15px;

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }

    // /deep/.el-input.is-disabled .el-input__inner{
  //     background-color: #F5F7FA !important;
  // }
  
  /deep/.el-range-editor.is-disabled {
      background-color:#fff;
  }

</style>
