<template>
  <div>
    <div class="app-main">
      <todo-bpm-header></todo-bpm-header>
      <div class="context-row">
        <!-- 室经理退回审批 -->
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>巡察基础信息</span>
            </div>
          </div>
          <div class="crad-content">
            <expand-collapse>
              <div slot="left">
                <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules">
                  <el-form-item label="巡察年份：" prop="seeyear">
                    <el-date-picker size="small" style="width: 205px" format="yyyy" value-format="yyyy"
                      v-model="seeParams.year" type="year" placeholder="请选择巡察年份" @change="yearChangefd"
                      :clearable="false">
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="巡察批次：" prop="seebatchId">
                    <el-select v-model="seeParams.batchId" placeholder="请选择巡察批次" size="small" @change="batchChange">
                      <el-option v-for="dict in batchIdChildOptions" :key="dict.batchId" :label="dict.batchName"
                        :value="dict.batchId" :clearable="false" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="申报类型：" prop="fineTypeIds">
                    <el-checkbox-group v-model="seeParams.fineTypeIds" @change="fineTypeChange">
                      <el-checkbox :label="item.dictValue" v-for="item in fineTypeOption" :key="item.dictValue">{{
                          item.dictLabel
                      }}</el-checkbox>
                    </el-checkbox-group>
                  </el-form-item>
                </el-form>
              </div>
            </expand-collapse>
          </div>
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>评优名单</span>
            </div>
          </div>
          <div class="crad-content">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="优秀巡察员" name="1" v-if="seeParamsCopy.fineTypeIds.indexOf('1') > -1">
                <div class="">
                  <el-row :gutter="10" class="mb12 utlsBtn mar20">
                    <el-col :span="1.5">
                      <el-button type="primary" size="mini" @click="handleAddChild(1)">
                        择优添加</el-button>
                    </el-col>
                  </el-row>
                  <el-table v-loading="loadingChild" :data="tableList1" ref="elTable" key="table1">
                    <el-table-column type="index" label="序号" width="55" align="left" />
                    <el-table-column label="巡察员" align="center" prop="userName" />
                    <el-table-column label="所属巡察组" align="center" prop="groupName" />
                    <el-table-column label="组内标签" align="center" prop="userTypeName" />
                    <el-table-column label="综合评分" align="center" prop="finalResult" />
                    <el-table-column label="所在单位" align="center" prop="companyName" />
                    <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" width="180" />
                    <el-table-column label="推荐方式" align="center" prop="upTypeId">
                      <template slot-scope="scope">
                        {{ scope.row.upTypeId | filterAll(fineUpTypeOption) }}
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                      width="100">
                      <template slot-scope="scope">
                        <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看
                        </el-button>
                        <el-button size="mini" type="text" @click="handleDeletChild('1', scope.row, scope.$index)">删除
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>
              <el-tab-pane label="优良巡察员" name="2" v-if="seeParamsCopy.fineTypeIds.indexOf('2') > -1">
                <div class="">
                  <el-row :gutter="10" class="mb12 utlsBtn mar20">
                    <el-col :span="1.5">
                      <el-button type="primary" size="mini" @click="handleAddChild(2)">
                        择优添加</el-button>
                    </el-col>
                  </el-row>
                  <el-table v-loading="loadingChild" :data="tableList2" ref="elTable" key="table1">
                    <el-table-column type="index" label="序号" width="55" align="left" />
                    <el-table-column label="巡察员" align="center" prop="userName" />
                    <el-table-column label="所属巡察组" align="center" prop="groupName" />
                    <el-table-column label="组内标签" align="center" prop="userTypeName" />
                    <el-table-column label="综合评分" align="center" prop="finalResult" />
                    <el-table-column label="所在单位" align="center" prop="companyName" />
                    <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" width="180" />
                    <el-table-column label="推荐方式" align="center" prop="upTypeId">
                      <template slot-scope="scope">
                        {{ scope.row.upTypeId | filterAll(fineUpTypeOption) }}
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                      width="100">
                      <template slot-scope="scope">
                        <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看
                        </el-button>
                        <el-button size="mini" type="text" @click="handleDeletChild('2', scope.row, scope.$index)">删除
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>

          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>证明材料</span>
            </div>
          </div>
          <div class="crad-content">
            <el-form :model="uploadParams" ref="uploadForm" :inline="true" label-width="140px" :rules="rules">
              <el-form-item label="*上党委审议材料：" prop="uploadFile">
                <el-button type="primary" size="mini" @click="uploadFile">上传文件</el-button>
              </el-form-item>
            </el-form>
            <el-row class="boxList">
              <el-col :span="3" v-for="(item, index) in dialogArray" :key="index">
                <i icon="document" class="uploader-file-icon" style="font-style: normal; position: relative;">
                  <i class="el-icon-circle-close" @click="onDeletUpload(item.fileId)"></i>
                </i>
                <div>{{ item.fileName }}</div>

              </el-col>
            </el-row>
          </div>
        </div>
        <deep-btn @close="cancel" ref="dbRef" :businessKey="seeParams.applyId" :todoId="todoId" :isTodo="true"
          @initProcess="initProcess" type="1">
          <span slot="btns">
            <!-- <el-button type="primary" @click="transferForm" v-if="process.authorizeFlag == '2'">转 办</el-button> -->
            <el-button type="primary" @click="returnForm(process.backNextStepAndUser)"
              v-if="process.authorizeFlag == '2' && processType == '1'">退回</el-button>
            <el-button type="primary" @click="returnForm(process.nextStepAndUser, 'sub')"
              v-if="process.authorizeFlag == '2'">
              提交</el-button>
          </span>
        </deep-btn>
      </div>
    </div>
    <!-- 择优选择弹出框 -->
    <el-dialog title="择优添加" :visible.sync="finedialog" width="800px" append-to-body>
      <div class="crad">
        <div class="crad-header">
          <div class="crad-title">
            <span class="line"></span>
            <span>人员选择</span>
          </div>
        </div>
        <div class="crad-content">
          <el-row>
            <el-col :span="18">
              <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules">
                <el-form-item label="姓名：" prop="seeyear">
                  <el-input v-model="userName" placeholder="请输入姓名" clearable size="small"
                    @keyup.enter.native="handleQueryFine" />
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" size="mini" @click="handleQueryFine" style="margin-top: 3px;">搜索
              </el-button>
            </el-col>
          </el-row>
        </div>
        <div class="crad-header">
          <div class="crad-title">
            <span class="line"></span>
            <span>人员信息</span>
          </div>
        </div>
        <div class="crad-content">
          <el-table v-loading="loadingFine" :data="fineList" @selection-change="handleSelectionFineChange">
            <el-table-column type="selection" width="55" align="left" />
            <el-table-column label="姓名" align="center" prop="userName" />
            <el-table-column label="所属巡察组" align="center" prop="groupName" />
            <el-table-column label="组内标签" align="center" prop="userTypeName" />
            <el-table-column label="综合评分" align="center" prop="finalResult" />
          </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeFine">取 消</el-button>
          <el-button type="primary" @click="tabMerge">确 定</el-button>
        </div>
      </div>
    </el-dialog>
    <!-- //文件上传 -->
    <el-dialog title="文件上传" :visible.sync="dialogVisible" width="600px" @open="initUpload" :before-close="handleClose">
      <div class="uploadBox">
        <div class="el-upload__tip" style="color:red" slot="tip">提示：导入文件不宜过大！</div>
        <CommonUpload v-if="isShowUpload" ref="CommonUpload" :single-file="true" @file-success="onFileSuccess"
          @upload-start="uploadStart" :singleFile="false" @file-removed="onFileRemoved"></CommonUpload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 下一步弹窗 -->
    <el-dialog :title="title" modal-append-to-body append-to-body :visible.sync="dialogVisibleReturn"
      :before-close="handleClose" width="600px">
      <div class="dialog-context">
        <el-form label-width="140px" :model="subForm" ref="afRules" :rules="rules">
          <el-form-item label="下一环节:" prop="node">
            <el-select style="width: 100%" v-model="subForm.node" placeholder="请选择" @change="changeVal">
              <el-option v-for="item in subForm.nextStep" :key="item.nextStep" :label="item.nextStep"
                :value="item.nextStep">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处理人:" prop="nextUserId">
            <el-select style="width: 100%" v-model="subForm.nextUserId" placeholder="请选择">
              <el-option
                v-for="item in (subForm.nextStep && subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0) ? (subForm.nextStep.filter(item => item.nextStep == this.subForm.node)[0].nextStepUser) : []"
                :key="item.nextUserId" :label="item.nextUserName" :value="item.nextUserId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处理意见:">
            <el-input v-model="subForm.option" type="textarea" row="3"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleReturn = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
    <transfer-process ref="transferRef" :taskId="process.curtaskId" :dialogVisible="transferProcessShow" type="1"
      @close="transferProcessShow = false"></transfer-process>
    <!-- <submit-process processKey="exam_process" stepName="巡察办部门领导" :dialogVisible="submitProcessShow" @close="close"
      @complete="saveFromData"></submit-process> -->

    <collectForm :dialogVisible="iFormShow.open" :id="iFormShow.id" more @close="handleCollectFormClose">
    </collectForm>
  </div>
</template>
<script>

import { getToken } from "@/utils/auth";
import {
  getBatchList,
  checkedFineList,
  getToApplyFineList,
  getOtherFinePerList,
  delCheckedFine,
  saveFineApply,
  getFileListOfApply,
  submitFineApply,
  approveApplyInfo,
} from "@/api/checkedAll/checkedFine";

// import { getDicts } from "@/api/system/dict/data";
import ApplyFooter from "@/components/ApplyFooter";
import deepBtn from "@/components/WorkFlow/deepBtn";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import transferProcess from "@/components/WorkFlow/transferProcess";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import SubmitProcess from '@/components/WorkFlow/submitProcess';
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
import collectForm from "@/views/talent/collect/collectForm.vue";
import {
  downloadFile,
} from '@/utils/uploadOperate'
export default {
  components: { ApplyFooter, deepBtn, AllFlowStep, transferProcess, ExpandCollapse, ExpandCollapse, SubmitProcess, CommonUpload, collectForm },
  data () {
    return {

      iFormShow: { open: false, id: null },
      id: null,
      todoId: null,
      disabled: false,
      // 遮罩层
      loading: false,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 选中数组
      idsFine: [],
      // 非单个禁用
      singleFine: true,
      // 非多个禁用
      multipleFine: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 优秀员工申报名单 表格数据
      checkedFineList: [],
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
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/checkedAll/checkedFine/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        year: null,
        batchId: null,
        fineTypeIds: [],
        status: null,
      },
      activeName: "1",
      tabKey: "",
      seeParams: {
        year: null,
        batchId: null,
        fineTypeIds: [],
        applyId: null,
      },
      seeParamsCopy: {
        year: null,
        batchId: null,
        fineTypeIds: [],
        applyId: null,
      },
      uploadParams: {},
      // 表单参数
      form: {},

      // 表单校验
      rules: {
        message: [{ required: true, message: "下一步处理节点不能为空", trigger: "change" }],
        nextUserId: [{ required: true, message: "下一步处理人不能为空", trigger: "change" }],
      },

      process: { allFlowStep: [] },
      isShowUpload: false,
      dialogVisible: false,
      dialogArray: [],
      files: [],
      batchIdOptions: [],
      batchIdChildOptions: [],
      loadingChild: false,
      fineApproveStatusOption: [],
      fineTypeOption: [],
      fineUpTypeOption: [],
      //临时的数组
      temporary1: false,
      temporary2: false,
      tableList1: [],
      tableList2: [],
      fineType: null,
      finedialog: false,
      userName: null,
      loadingFine: false,
      fineList: [],
      watchOpen: false,
      watchParams: {},
      submitProcessShow: false,
      fineTypeIdsOptions: [{ dictLabel: '优秀巡察员', dictValue: '1' }, { dictLabel: '优良巡察员', dictValue: '2' },],
      transferProcessShow: false,
      dialogVisibleReturn: false,
      disabledwindow: false,
      processType: null,
      subForm: {
        message: null,
        nextStep: null,
        option: null,
        nextUserId: null,
      },
    };
  },
  filters: {
    filterDate (val) {
      return formatDate(val);
    },

    filterAll (val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
    filterMore (val, optionArr) {
      if (val && optionArr.length) {
        let newarr =
          Object.prototype.toString.call(val).indexOf("Array") >= 0
            ? val
            : val.split(",");
        var arr = [];
        optionArr.forEach((item) => {
          if (newarr.indexOf(item.dictValue) !== -1) {
            arr.push(item.dictLabel);
          }
        });
        if (!arr.length) return "";
        return arr.toString();
      } else {
        return "";
      }
    },
  },
  watch: {
    "seeParamsCopy.fineTypeIds": function (va, val) {
      // console.log(va[0].toString())
      this.activeName = va && va[0] ? va[0].toString() : ''
    }
  },
  created () {

    this.seeParams.applyId = this.$route.query.id ? this.$route.query.id : '';
    this.todoId = this.$route.query.todoInfoId ? this.$route.query.todoInfoId : '';

    //状态 ~草稿，室经理审批
    this.getDicts("fine_approve_status").then(res => {
      this.fineApproveStatusOption = res.data ? res.data : [];
    })
    //类型 ~优秀巡察员，优良巡察员
    this.getDicts("fine_type").then(res => {
      this.fineTypeOption = res.data ? res.data : [];
      this.seeParams.fineTypeIds = this.fineTypeOption.map((item) => {
        return item.dictValue
      })
      this.seeParamsCopy.fineTypeIds = this.seeParams.fineTypeIds;
    })
    //组内评优,择优添加
    this.getDicts("fine_up_type").then(res => {
      this.fineUpTypeOption = res.data ? res.data : [];
    })
    this.yearChangefd('');
    checkedFineList({ id: this.seeParams.applyId }).then(res => {
      let data = res.rows[0]
      this.seeParams.year = data.year;
      this.seeParams.batchId = data.batchId;
      this.seeParams.fineTypeIds = data.fineTypeIds.split(',');
      this.seeParamsCopy.year = data.year;
      this.seeParamsCopy.batchId = data.batchId;
      this.seeParamsCopy.fineTypeIds = data.fineTypeIds.split(',');
      this.seeParams.status = data.status;
      // if (data.status != '0') {
      //   fetchWorkFlowInfo(data.id, null, (res) => {
      //     this.process = res;
      //   });
      // }
      getBatchList({ year: this.seeParams.year }).then(res => {
        this.batchIdChildOptions = res.data ? res.data : [];
      })
      this.getFineDetails(() => { }, data.id);
      this.getFileListOfApply(data.id);
    });
  },
  methods: {

    onUserInfo (row) {
      this.iFormShow.id = row.userId;
      this.iFormShow.open = true;
      console.log(this.iFormShow);
    },

    handleCollectFormClose () {
      this.iFormShow.open = false
    },
    initProcess (res) {
      let tempObj = {};
      if (res.allFlowStep.length && res.backNextStepAndUser.length == 1) {
        for (let i = 1; i < res.allFlowStep.length; i++) {
          let item = res.allFlowStep[i];
          if (item.varialbes && item.varialbes.message == "转办") {
          } else {
            tempObj = item;
            break;
          }
        }
        res.preUserName = tempObj.todoUserLoginId;
        res.preUserId = tempObj.historicTaskInstance.assignee;
      }
      this.process = res;
      if (this.process.allFlowStep[1].message == '退回室经理' && this.process.allFlowStep[1].name == '巡察办部门领导审核') {
        this.processType = '1'
      }
    },
    /** 查询优秀员工申报名单 列表 */
    getList () {
      // this.loading = true;
      checkedFineList(this.queryParams).then(response => {
        this.checkedFineList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
      });
    },
    // 取消按钮
    cancel () {
      this.watchOpen = false;
      this.open = false;
      this.submitProcessShow = false;
      this.reset();
    },
    // yearChange (val) {
    //   //批次
    //   this.queryParams.batchId = null;
    //   getBatchList({ year: val }).then(res => {
    //     this.batchIdOptions = res.data ? res.data : [];
    //   })
    // },
    yearChangefd (val) {
      console.log(this.seeParams.year)
      if (this.seeParams.year != this.seeParamsCopy.year && this.seeParamsCopy.year != null) {
        //里批次
        if (this.temporary1 || this.temporary2) {
          this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.seeParamsCopy.year = this.seeParams.year;
            this.seeParams.batchId = null;
            this.seeParamsCopy.batchId = null;

            getBatchList({ year: this.seeParams.year }).then(res => {
              this.batchIdChildOptions = res.data ? res.data : [];
            })
          }).catch(() => {
            this.seeParams.year = this.seeParamsCopy.year;
            this.seeParams.batchId = null;
            this.seeParamsCopy.batchId = null;

            getBatchList({ year: this.seeParams.year }).then(res => {
              this.batchIdChildOptions = res.data ? res.data : [];
            })
          });
        }
      } else {
        this.seeParamsCopy.year = this.seeParams.year;
        this.seeParams.batchId = null;
        this.seeParamsCopy.batchId = null;
        getBatchList({ year: this.seeParams.year }).then(res => {
          this.batchIdChildOptions = res.data ? res.data : [];
        })
      }

    },
    handleQueryFine () {
      if (this.userName == null || this.userName == "") {
        this.$message({
          message: "请输入姓名",
          type: 'warning'
        });
        return
      }
      let Array = [...this.tableList1, ...this.tableList2];
      let notFindUserIds = Array.map(item => {
        return item.userId
      })
      let obj = {
        batchId: this.seeParams.batchId,
        fineTypeId: this.fineType,
        userName: this.userName,
        notFindUserIds: notFindUserIds.toString(),
      }
      getOtherFinePerList(obj).then(res => {
        if (res.data.length == 0) {
          this.$message({
            message: "暂无此人",
            type: 'warning'
          });
          return
        }
        this.fineList = res.data;
      })
    },
    //tab切换
    handleClick () {

    },
    //优秀优良添加
    handleAddChild (num) {
      this.fineType = num;
      this.finedialog = true;
    },
    //优秀优良添加确定加入按钮
    tabMerge () {
      if (this.idsFine.length == 0) {
        this.$message({
          message: "请选择人员添加",
          type: 'warning'
        });
        return;
      }

      let arr = [];
      if (this.fineType == "1") {//优秀
        this.fineList.forEach((item, index) => {
          this.idsFine.forEach(itm => {
            if (item.companyId == itm) {
              arr.push(item)
            }
          })
        })
        this.tableList1 = [...this.tableList1, ...arr]
      } else if (this.fineType == "2") {//优良
        this.fineList.forEach((item, index) => {
          this.idsFine.forEach(itm => {
            if (item.companyId == itm) {
              arr.push(item)
            }
          })
        })
        this.tableList2 = [...this.tableList2, ...arr]
      }
      this.closeFine();
    },
    closeFine () {
      this.finedialog = false;
      this.fineList = [];
      this.idsFine = [];
    },
    //删除
    handleDeletChild (Id, row, index) {
      console.log(Id, row, index);
      if (Id == '1') {
        this.tableList1.splice(index, 1)
        this.temporary1 = true
      } else {
        this.tableList2.splice(index, 1)
        this.temporary2 = true
      }
    },

    uploadFile (row) {
      this.dialogVisible = true;
    },
    handleClose () {
      this.dialogVisible = false;
      this.isShowUpload = false;
    },

    //上传前
    uploadStart () {
      const loading = this.openLoading();
    },
    //成功回调
    onFileSuccess (file, files, fileList) {
      this.files = files.map(item => item.result.data);

      const loading = this.openLoading();
      loading.close();
      // console.log(file, files, fileList)
    },
    //移除回调
    onFileRemoved (file, files, fileList) {
      // this.file = null;
      // this.dialogObj.fileKey = "";
      // this.dialogObj.fileName = "";
    },
    onDeletUpload (id) {
      console.log(id)
      this.$confirm('是否确定删除文件', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let index = this.dialogArray.map((item, index) => { item.fileId == id; return index })[0]
        this.dialogArray.splice(index, 1)
      }).catch(() => {

      });
    },
    confirm () {
      // debugger
      if (!this.files.length) {
        this.$message.warning("请上传文件!");
        return;
      }
      this.dialogArray = [...this.dialogArray, ...this.files];
      this.files = [];
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    //打开文件上传清除之前的记录
    initUpload () {
      this.isShowUpload = true;
    },

    // 表单重置
    reset () {
      this.seeParams = {
        year: null,
        batchId: null,
        fineTypeIds: [],
        applyId: null,
      };
      this.seeParamsCopy = {
        year: null,
        batchId: null,
        fineTypeIds: [],
        applyId: null,
      };
      this.temporary1 = false;
      this.temporary2 = false;
      this.tableList1 = [];
      this.tableList2 = [];
      this.fineType = null;
      this.userName = null;
      this.fineList = [];
      //--
      this.seeParams.fineTypeIds = this.fineTypeOption.map((item) => {
        return item.dictValue
      })
      this.seeParamsCopy.fineTypeIds = this.seeParams.fineTypeIds;
      let date = new Date();
      let year = date.getFullYear();
      this.seeParams.year = ('' + year);
      this.seeParams.batchId = this.batchIdChildOptions[this.batchIdChildOptions.length - 1].batchId;
      this.seeParamsCopy.batchId = this.batchIdChildOptions[this.batchIdChildOptions.length - 1].batchId;
      this.dialogArray = [];
      this.resetForm("seeForm");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleSelectionFineChange (selection) {
      this.idsFine = selection.map(item => item.companyId)
      this.singleFine = selection.length !== 1
      this.multipleFine = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "优秀/优良巡察员申报新增";
      this.getFineDetails()
    },
    //推荐人员列表
    getFineDetails (func, applyid) {
      let forms = JSON.parse(JSON.stringify(this.seeParams))
      let fineTypeIds = JSON.parse(JSON.stringify(forms.fineTypeIds));
      // debugger
      applyid && (forms.applyId = applyid)
      forms.fineTypeIds = fineTypeIds.toString()
      this.loadingChild = true;
      getToApplyFineList(forms).then(res => {
        let array = ["1", "2"];
        let arrUser = [];
        array.forEach(item => {
          fineTypeIds.forEach(itm => {
            if (item == itm) {
              arrUser.push(itm)
            }
          })
        })
        if (fineTypeIds.indexOf("1") > -1) {
          this.tableList1 = res.data['1'] ? res.data['1'] : []
          // this.temporary1 = false
        }
        if (fineTypeIds.indexOf("2") > -1) {
          this.tableList2 = res.data['2'] ? res.data['2'] : []
          // this.temporary2 = false
        }
        this.loadingChild = false;
        func && func();
      })
    },
    //获取附件
    getFileListOfApply (applyId) {
      getFileListOfApply({ applyId: applyId }).then(res => {
        this.dialogArray = res.data
      })
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      // this.disabled = false;
      // this.reset();
      // const id = row.id || this.ids
      // checkedFineList(id).then(response => {
      //     this.form = response.data;
      //     this.open = true;
      //     this.title = "修改优秀员工申报名单 ";
      // });
      const id = row.id || this.ids
      checkedFineList({ id }).then(res => {
        let data = res.rows[0]
        this.seeParams.applyId = id;
        this.seeParams.year = data.year;
        this.seeParams.batchId = data.batchId;
        this.seeParams.fineTypeIds = data.fineTypeIds.split(',');
        this.seeParamsCopy.applyId = id;
        this.seeParamsCopy.year = data.year;
        this.seeParamsCopy.batchId = data.batchId;
        this.seeParamsCopy.fineTypeIds = data.fineTypeIds.split(',');
        this.getFineDetails(() => { }, data.id);
        this.getFileListOfApply(id);
        this.open = true;
        this.title = "修改优秀优良巡察员";
      });
    },
    /**详情按钮*/
    handleQueryOption (row) {
      // this.reset();
      const id = row.id || this.ids
      checkedFineList({ id }).then(res => {
        let data = res.rows[0]
        this.seeParams.year = data.year;
        this.seeParams.batchId = data.batchId;
        this.seeParams.fineTypeIds = data.fineTypeIds.split(',');
        this.seeParamsCopy.year = data.year;
        this.seeParamsCopy.batchId = data.batchId;
        this.seeParamsCopy.fineTypeIds = data.fineTypeIds.split(',');
        this.seeParams.status = data.status;
        // if (data.status != '0') {
        //   fetchWorkFlowInfo(id, null, (res) => {
        //     this.process = res;
        //   });
        // }
        this.getFineDetails(() => { }, data.id);
        this.getFileListOfApply(id);
        this.watchOpen = true;
        this.title = "查看优秀优良巡察员";
      });

    },
    /** 提交按钮 */
    submitForm () {
      let that = this;
      if (this.disabledwindow) {
        return
      }
      this.disabledwindow = true;
      const loading = this.openLoading();
      // let message = null;
      // this.subForm.nextStep.forEach(item => {
      //   if (item.nextStep == this.subForm.message) {
      //     message = item.outLine
      //   }
      // })
      let form = {
        id: this.seeParams.applyId,
        taskId: this.process.curtaskId,
        applyId: this.seeParams.applyId ? this.seeParams.applyId : '',
        batchId: this.seeParams.batchId,
        year: this.seeParams.year,
        '1': this.seeParams.fineTypeIds.indexOf("1") > -1 ? JSON.stringify(this.tableList1) : '',
        '2': this.seeParams.fineTypeIds.indexOf("2") > -1 ? JSON.stringify(this.tableList2) : '',
        fileKeyList: JSON.stringify(this.dialogArray),
        variables: JSON.stringify({
          option: this.subForm.option,
          message: this.subForm.message,
          nextUserId: this.subForm.nextUserId,
        })
      }
      // debugger
      approveApplyInfo(form).then(res => {
        this.$message({
          message: res.msg,
          type: 'success'
        });
        this.disabledwindow = false;
        this.dialogVisibleReturn = false;
        loading.close();
        window.location.reload()
      })
    },
    //转办
    transferForm () {
      console.log("转办nextStep")
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    returnForm (data, type) {
      debugger
      if (this.process.allFlowStep[1].name == '巡察办部门领导审核' && type == 'sub') {
        this.subForm.nextStep = data.filter(item => item.nextStep == this.process.allFlowStep[1].name);
      } if (this.process.allFlowStep[1].name == '巡察办室经理审核' && type == 'sub') {
        this.subForm.nextStep = data.filter(item => item.nextStep == this.process.allFlowStep[1].name);
      } else {
        this.subForm.nextStep = data;
      }
      this.subForm.message = this.subForm.nextStep.length > 0 ? this.subForm.nextStep[0].outLine : '';
      this.subForm.node = this.subForm.nextStep.length > 0 ? this.subForm.nextStep[0].nextStep : '';
      this.subForm.nextUserId = (this.subForm.nextStep && this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0 && this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0) ? (this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node)[0].nextStepUser[0].nextUserId) : ""
      this.dialogVisibleReturn = true;
      this.title = this.subForm.message;
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delCheckedFine({ id: ids });
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function () { });
    },
    /** 禁用或启用操作*/
    disabledBtnClick (type) {
      var ids = this.ids || [];
      this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return disableEnable({ ids: ids.toString(), disableEnableState: type });
      }).then(() => {
        this.getList();
        this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
      }).catch(function () { });
    },
    /** 导出按钮操作 */
    handleExport () {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有优秀员工申报名单 数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportCheckedFine(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function () { });
    },
    /** 导入按钮操作 */
    openImportTable () {
      this.upload.title = "优秀员工申报名单 数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate (id, name) {
      downloadFile(id, "name")
    },
    // 文件上传中处理
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess (response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm () {
      this.$refs.upload.submit();
    },

    batchChange () {
      if (this.seeParams.batchId != this.seeParamsCopy.batchId && this.seeParamsCopy.batchId != null) {
        //里批次-temporary1是第一个表格
        if (this.temporary1 || this.temporary2) {
          this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.seeParamsCopy.batchId = this.seeParams.batchId;
            this.getFineDetails()
          }).catch(() => {
            this.seeParams.batchId = this.seeParamsCopy.batchId;
          });
        } else {
          this.seeParamsCopy.batchId = this.seeParams.batchId;
          this.getFineDetails()
        }
      } else {
        this.seeParamsCopy.batchId = this.seeParams.batchId;
        this.getFineDetails()
      }
    },
    fineTypeChange (val) {
      console.log(val.length, this.seeParamsCopy.fineTypeIds.length)
      let that = this;
      // let tabkey = null
      if (val.length > this.seeParamsCopy.fineTypeIds.length) {
        //++
        // tabkey = val.filter(item=>item != this.seeParamsCopy.fineTypeIds)
        if (this.seeParams.batchId == null || this.seeParams.batchId == "") {
          this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
          this.$message({
            message: "请选择批次",
            type: 'warning'
          });
          return
        } else {
          if (this.temporary1 || this.temporary2) {
            this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.getFineDetails(() => {
                that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
              })
            }).catch(() => {
              this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
            });
          } else {
            that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
            this.getFineDetails()
          }
        }
      } else if (val.length < this.seeParamsCopy.fineTypeIds.length) {
        //--
        // tabkey = this.seeParamsCopy.fineTypeIds.filter(item=>item != val)
        if (this.seeParams.batchId == null || this.seeParams.batchId == "") {
          this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
          this.$message({
            message: "请选择批次",
            type: 'warning'
          });
          return
        } else {
          if (this.temporary1 || this.temporary2) {
            this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.getFineDetails(() => {
                that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
              })
            }).catch(() => {
              this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
            });
          } else {
            that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
            this.getFineDetails()
          }
        }
      }

      // this.seeParamsCopy.fineTypeIds = val;
    },
    //暂存
    saveBtn () {
      let that = this;
      // seeParams.fineTypeIds
      // if (this.seeParams.fineTypeIds == "" || this.seeParams.fineTypeIds == null) {
      //     this.$message("请选择申报类型")
      //     return
      // }
      // if (this.tableList1.length == 0 && this.tableList2.length == 0) {
      //     this.$message("请至少添加一位人员")
      //     return
      // }
      console.log("开始暂存")
      const loading = this.openLoading();
      let obj = {
        applyId: this.seeParams.applyId ? this.seeParams.applyId : '',
        batchId: this.seeParams.batchId,
        year: this.seeParams.year,
        '1': this.seeParams.fineTypeIds.indexOf("1") > -1 ? JSON.stringify(this.tableList1) : '',
        '2': this.seeParams.fineTypeIds.indexOf("2") > -1 ? JSON.stringify(this.tableList2) : '',
        fileKeyList: JSON.stringify(this.dialogArray),
      }
      saveFineApply(obj).then(res => {
        console.log(res)
        that.$message.success(res.msg)
        that.open = false
        that.getList();
        loading.close();
      })
    },
    changeVal () {
      this.subForm.nextUserId = (this.subForm.nextStep && this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0) ? (this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node)[0].nextStepUser)[0].nextUserId : ''
      this.subForm.message = (this.subForm.nextStep && this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node).length > 0) ? (this.subForm.nextStep.filter(item => item.nextStep == this.subForm.node)[0].outLine) : '';

    },
  }
};
</script>
<style lang="scss" scoped>
/deep/ input::-webkit-outer-spin-button,
/deep/ input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}

/deep/ input[type="number"] {
  -moz-appearance: textfield !important;
}

/deep/ .el-dialog__body {
  // max-height: 500px;
  overflow: auto;
}

/deep/ .el-dialog__footer {
  text-align: center;
}

// /deep/ .el-form-item__error {
//   width: 280px;
// }

.app-main {
  padding: 15px;
  background: #fff;

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
      top: 10px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 30px;
      z-index: 9;
    }
  }
}

.app-footer {
  text-align: center;
  background: #fff;
  padding-bottom: 20px;
}

/deep/ .specialForm .el-form-item__content {
  margin-left: 0 !important;
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
      height: auto;
    }
  }

  /deep/ .el-table__fixed-header-wrapper {
    th {
      background: rgba(45, 140, 240, 0.04) !important;
      font-size: 14px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.75);
      height: auto;
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

.boxList {
  display: flex;
  flex-wrap: wrap;
}

.boxList .uploader-file-icon {
  width: 32px;
  height: 32px;
}

.boxList .uploader-file-icon::before {
  font-size: 32px;
}

.boxList .el-icon-circle-close {
  font-size: 18px;
  position: absolute;
  right: -20px;
  top: -14px;
}

.boxList .el-col {
  text-align: center;
  margin: 10px 4px;

  div {
    margin: 4px;
  }
}
</style>