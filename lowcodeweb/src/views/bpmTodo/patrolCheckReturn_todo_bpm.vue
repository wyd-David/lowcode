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
              <span>发起考核</span>
            </div>
          </div>
          <div class="crad-content">
            <expand-collapse>
              <div slot="left">
                <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules"
                  v-loading="loadingAdd" :disabled="form.approveStatus != '3'">
                  <el-form-item label="巡察年份：" prop="seeyear">
                    <el-date-picker size="small" style="width: 205px" format="yyyy" value-format="yyyy"
                      v-model="seeParams.year" type="year" placeholder="请选择巡察年份" @change="yearchange"
                      :clearable="false">
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="巡察批次：" prop="seebatchId">
                    <el-select v-model="seeParams.batchId" placeholder="请选择巡察批次" size="small"
                      @change="handleQueryChild">
                      <el-option v-for="dict in batchIdChildOptions" :key="dict.batchId" :label="dict.batchName"
                        :value="dict.batchId" :clearable="false" />
                    </el-select>
                  </el-form-item>
                </el-form>
              </div>
              <!-- <div slot="right">
                <el-button type="primary" size="mini" @click="handleQueryChild" :disabled="form.approveStatus != '3'">搜索
                </el-button>
              </div> -->
            </expand-collapse>
          </div>
          <div class="crad-content crad-content-top20">
            <el-row :gutter="10" class="mb12 utlsBtn mar20 btnright">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleAddChild">
                  发起考核</el-button>
              </el-col>
            </el-row>
            <el-table v-loading="loadingChild" :data="checkedInfoListChild"
              @selection-change="handleSelectionChangeChild" ref="elTable">
              <el-table-column type="selection" width="55" align="left" />
              <el-table-column label="序号" align="center" type="index" width="55" />
              <el-table-column label="巡察组" align="center" prop="groupName" />
              <el-table-column label="组长" align="center" prop="groupLeaderName" />
              <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" />
              <el-table-column label="巡察年份" align="center" prop="year" />
              <el-table-column label="批次" align="center" prop="batchName" />
              <el-table-column label="待考核类型" align="center" prop="examTypeList" width="250">
                <template slot-scope="scope">
                  {{ scope.row.examTypeList | examFilter }}
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>考核信息设置</span>
            </div>
          </div>
          <div class="crad-content">
            <el-form ref="form" :model="form" :rules="rulesd" label-width="120px" :disabled="form.approveStatus != '3'">
              <el-form-item label="标题：" prop="title">
                <el-input v-model="form.title" placeholder="请输入标题" />
              </el-form-item>
              <el-table :data="InfoListChild" border :span-method="objectSpanMethod" :row-class-name="rowClassName"
                @cell-mouse-enter="handleMouseEnter" @cell-mouse-leave="handleMouseLeave"
                style="width: 100%; margin-top: 20px">
                <!--  :span-method="objectSpanMethod" -->
                <el-table-column type="index" label="序号" width="55" align="center">
                </el-table-column>
                <el-table-column prop="groupName" label="巡察组" align="center" width="200">
                  <template slot-scope="scope">
                    <div>{{ scope.row.groupName }}</div>
                    <el-button type="primary" size="mini" @click="handleRemoveChild(scope)" style="margin-top:10px">
                      删除</el-button>
                  </template>
                </el-table-column>
                <el-table-column prop="examTypeName" label="考核类型" align="center" width="180">
                </el-table-column>
                <el-table-column label="* 评分人选择" align="center" width="160">
                  <template slot-scope="scope">
                    <el-form-item label="" prop="examUserIds" class="specialForm" :rules='{
                      required: true,
                      message: "评分人不能为空",
                      trigger: "change",
                      validator: (rule, value, callback) => {
                        if (showList[scope.row.groupId + "_" + scope.row.examTypeId].examUserIds.length == 0) {
                          callback(Errors("至少选择一个"))
                        } else {
                          callback()
                        }
                      }
                    }'>
                      <el-select v-model="showList[scope.row.groupId + '_' + scope.row.examTypeId].examUserIds" multiple
                        placeholder="请选择" @change="PeoChange()">
                        <el-option
                          v-for="item in (ExamLeadListOptions.filter(item => item.groupId == scope.row.groupId).length > 0 && ExamLeadListOptions.filter(item => item.groupId == scope.row.groupId)[0].examTypeList.length > 0 ? ExamLeadListOptions.filter(item => item.groupId == scope.row.groupId)[0].examTypeList.filter(itm => itm.examTypeId == scope.row.examTypeId)[0].fromExamList : [])"
                          :key="item.userId" :label="item.userName" :value="item.userId">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column label="*评分截止时间" align="center" width="210">
                  <template slot-scope="scope">
                    <el-form-item label="" prop="examLastTime" class="specialForm" :rules='{
                      required: true,
                      message: "截止时间不能为空",
                      trigger: "change",
                      validator: (rule, value, callback) => {
                        if (showList[scope.row.groupId + "_" + scope.row.examTypeId].examLastTime == "" || showList[scope.row.groupId + "_" + scope.row.examTypeId].examLastTime == null) {
                          callback(Errors("请选择截止时间"))
                        } else {
                          callback()
                        }
                      }
                    }'>
                      <el-date-picker v-model="showList[scope.row.groupId + '_' + scope.row.examTypeId].examLastTime"
                        type="datetime" placeholder="请选择截止时间" style="width:190px" format="yyyy-MM-dd HH:mm"
                        default-time="23:59:59" value-format="yyyy-MM-dd HH:mm" @change="PeoChange()">
                      </el-date-picker>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column prop="examRemark" label="备注" align="center">
                  <template slot-scope="scope">
                    <el-form-item prop="examRemark" label="" class="specialForm">
                      <el-input placeholder="请输入备注"
                        v-model="showList[scope.row.groupId + '_' + scope.row.examTypeId].examRemark"
                        @change="PeoChange()">
                      </el-input>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column prop="delete" label="操作" align="center" fixed="right" width="100">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="handleDeleteChild(scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form>
          </div>

        </div>
        <deep-btn @close="cancel" :isTodo="true" @saveDraftData="saveBtn" :businessKey="seeParams.id"
          @initProcess="initProcess">
          <span slot="btns">
            <el-button type="primary" @click="saveBtn" v-if="form.approveStatus == '3' && process.authorizeFlag == '2'">
              暂 存
            </el-button>
            <el-button type="primary" @click="submitForm"
              v-if="form.approveStatus == '3' && process.authorizeFlag == '2'">提 交</el-button>
          </span>
        </deep-btn>
      </div>
    </div>
    <transfer-process ref="transferRef" :taskId="process.curtaskId" :dialogVisible="transferProcessShow" type="1"
      @close="transferProcessShow = false"></transfer-process>
    <submit-process processKey="exam_process" stepName="巡察办室经理审批" :dialogVisible="submitProcessShow" @close="close"
      @complete="saveFromData" :title="stitle"></submit-process>
  </div>
</template>
<script>
import {
  getApplyInfo,
  approveApplyInfo,
  getBatchList,
  GroupList,
  getExamLeaderList,
  submitApplyInfo,
  saveApplyInfo,

  saveApplyInfoPer,//1
  GroupListPer,//1
  submitApplyInfoPer,//1
} from "@/api/checkedAll/checkedInfo";
// import { getDicts } from "@/api/system/dict/data";
import ApplyFooter from "@/components/ApplyFooter";
import deepBtn from "@/components/WorkFlow/deepBtn";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import transferProcess from "@/components/WorkFlow/transferProcess";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import SubmitProcess from '@/components/WorkFlow/submitProcess';
export default {
  components: { ApplyFooter, deepBtn, AllFlowStep, fetchWorkFlowInfo, transferProcess, ExpandCollapse, ExpandCollapse, SubmitProcess },
  created () {
    this.typeId = this.$route.path.indexOf('Pre') < 0 ? 1 : 2;//是否为组员1组2员
    this.seeParams.id = this.$route.query.id ? this.$route.query.id : '';
    this.todoId = this.$route.query.todoInfoId ? this.$route.query.todoInfoId : '';

    // this.disable = this.typeId == 1 ? false : true;
    let date = new Date();
    let year = date.getFullYear();
    this.seeParams.year = this.$route.query.year ? this.$route.query.year : ('' + year);
    this.seeParams.batchId = this.$route.query.batchId ? this.$route.query.batchId : '';
    this.seeParams.type = this.typeId == 1 ? "1" : "2"

    getBatchList({ year: this.seeParams.year }).then(res => {
      this.batchIdOptions = res.data ? res.data : [];
      // this.batchIdOptions.length > 0 && (this.seeParams.batchId = this.batchIdOptions[0].batchId)
      this.seeParamsCopy = JSON.parse(JSON.stringify(this.seeParams))
      this.getDetail();
    })
    this.yearchange('');
  },
  data () {
    return {
      stitle: '提交',
      typeId: null,
      // 表单参数
      form: {
        title: null,
      },
      subForm: {
        message: null,
        nextStep: null,
        option: null,
      },
      dialogVisibleReturn: false,
      transferProcessShow: false,
      turnbackProcessShow: false,
      taskId: null,
      disabledwindow: false,
      process: { allFlowStep: [], curtaskId: "" },
      loadingChild: false,
      checkedInfoListChild: [],
      InfoListChild: [],
      activeName: "1",
      id: "",
      groupId: "",
      todoId: "",
      false: false,
      disabled: false,
      applyUser: "",
      btnList: [],
      allFlowStep: [],
      companyList: [],
      nextUsers: [],
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
      formData: {
        title: "",
        collectionType: "",
        joinWorkDate: "",
        joinPartDate: "",
        startInspectTime: "",
        endInspectTime: "",
        datetime: [],
        collectionEndDate: "",
      },
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [], //tab字典
      collectDicts: [], //收集字典
      InfoListChildDetail: [],
      showListDetail: [],
      showList: [],
      seeParams: {
        year: null,
        batchId: null,
        id: null,
        type: null,
      },
      batchIdChildOptions: [],
      seeParamsCopy: {},
      idsChild: [],
      loadingAdd: false,
      singleChild: true,
      multipleChild: true,
      ExamLeadListOptions: [],
      submitProcessShow: false,
      cellIndex: -1,
      rules: {
        message: [
          {
            required: true,
            message: "请选择审批结果",
            trigger: "change",
          },
        ],
      },
      rulesd: {
        title: [{ required: true, message: "标题不能为空", trigger: "change" }],
      }
    };
  },
  filters: {
    examFilter (val) {
      if (!val.length) return "";
      return val.map(item => item.examTypeName).join()
    }
  },
  methods: {
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
    },
    // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
    // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
    // 也可以返回一个键名为rowspan和colspan的对象。
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      // console.log(this.InfoListChild)
      // console.log(row.examTypeId)
      if (columnIndex === 1) {
        let data = this.InfoListChild.filter((item) => item.groupId == row.groupId);
        let idx = 0;
        data.forEach((item, index) => { if (item.examTypeId == row.examTypeId) { idx = index } })
        // console.log(len, rowIndex)
        if (idx % data.length === 0) {
          return {
            rowspan: data.length,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },
    objectSpanMethodDetail ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 1) {
        let data = this.InfoListChildDetail.filter((item) => item.groupId == row.groupId);
        let idx = 0;
        data.forEach((item, index) => { if (item.examTypeId == row.examTypeId) { idx = index } })
        // console.log(len, rowIndex)
        if (idx % data.length === 0) {
          return {
            rowspan: data.length,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },

    Errors (err) {
      let text = new Error(err);
      return text
    },
    //评分人选择
    PeoChange (e) {
      for (let i in this.showList) {
        // i.split("_")[0]
        // i.split("_")[1]
        this.InfoListChild.map((item) => {
          if (item.groupId == i.split("_")[0] && item.examTypeId == i.split("_")[1]) {
            item.examUserIds = this.showList[i].examUserIds
            item.examLastTime = this.showList[i].examLastTime
            item.examRemark = this.showList[i].examRemark
            return item
          }
          return item
        })
      }
      console.log(this.InfoListChild, this.showList)
    },
    //删除巡察组-总
    handleRemoveChild (data) {
      console.log(this.InfoListChild, this.showList, data)
      let row = data.row;
      let index = data.$index;
      let that = this;
      this.$confirm('是否删除当前巡察组？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(() => {
        let newInfo = that.InfoListChild.filter(item => item.groupId != row.groupId);
        that.InfoListChild = newInfo;
        this.$message({
          message: "删除成功",
          type: 'success'
        });
      }).catch(() => {

      });
    },
    //删除考核类型-分
    handleDeleteChild (data) {
      console.log(this.InfoListChild, this.showList, data)
      let row = data.row;;
      let index = data.$index;
      let that = this;
      this.$confirm('是否删除当前考核类型？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(() => {
        this.InfoListChild.splice(index, 1);
        delete that.showList[row.groupId + "_" + row.examTypeId];
        this.$message({
          message: "删除成功",
          type: 'success'
        });
      }).catch(() => {

      });
    },
    handleSelectionChangeChild (selection) {
      this.idsChild = selection.map((item) => item.groupId);
      this.singleChild = selection.length !== 1;
      this.multipleChild = !selection.length;
    },
    //发起审核-发起审核
    handleAddChild (dataone) {
      // console.log(this.idsChild)
      if (this.idsChild.length == 0 && dataone != 1) {
        this.$message({
          message: "至少选择一个巡察组",
          type: 'warning'
        });
        return
      }
      let form = {
        batchId: this.seeParams.batchId,
        groupIds: this.idsChild.toString(),
        year: this.seeParams.year,
        id: this.seeParams.id,
      }
      //返回评分人总列表
      getExamLeaderList(form).then(res => {
        this.ExamLeadListOptions = res.data
      })
      let Array = [];
      if (dataone == 1) {
        Array = this.checkedInfoListChild
      } else {
        this.checkedInfoListChild.forEach((item) => {
          this.idsChild.forEach((itm) => {
            if (itm == item.groupId) {
              Array.push(item)
            }
          })
        })
      }
      //拆分类型
      this.InfoListChild = [];
      Array.forEach((item) => {
        item.examTypeList && item.examTypeList.forEach((itm, idx) => {
          // 巡察组 考核类型 评分人选择 评分截止时间 备注
          // let child = itm;
          // child.groupId = item.groupId;//巡察组id
          // child.groupName = item.groupName;//巡察组name
          // child.examUserIds = [];//评分人选择多选，之后拼接字符串
          // child.examLastTime = "";
          // child.examRemark = "";
          // child.index = idx;
          let child = itm;
          child.groupId = item.groupId ? item.groupId : '';//巡察组id
          child.groupName = item.groupName ? item.groupName : '';//巡察组name
          child.examUserIds = child.examUserIds ? child.examUserIds : [];//评分人选择多选，之后拼接字符串
          child.examLastTime = child.examLastTime ? child.examLastTime : '';
          child.examRemark = child.examRemark ? child.examRemark : '';
          child.index = idx;
          this.InfoListChild.push(child)
        })
      })
      // this.showList
      let data = {};
      // if(dataone != 1){
      //   for (let i in this.InfoListChild) {
      //     data[this.InfoListChild[i].groupId + "_" + this.InfoListChild[i].examTypeId] = { examUserIds: [], examLastTime: "", examRemark: "" };
      //   }
      // }else{
      for (let i in this.InfoListChild) {
        data[this.InfoListChild[i].groupId + "_" + this.InfoListChild[i].examTypeId] = { examUserIds: this.InfoListChild[i].examUserIds ? this.InfoListChild[i].examUserIds : [], examLastTime: this.InfoListChild[i].examLastTime ? this.InfoListChild[i].examLastTime : "", examRemark: this.InfoListChild[i].examRemark ? this.InfoListChild[i].examRemark : "" };
      }
      // }

      this.showList = data;
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
      this.resetAdd();
      this.InfoListChild = [];
      this.checkedInfoListChild = [];
    },
    close () {
      this.isOpenQuery = false;
      this.submitProcessShow = false;
    },
    /** 提交按钮 */
    submitForm () {
      let that = this;
      if (this.disabled) {
        this.open = false;
        return
      }
      if (this.InfoListChild.length == 0) {
        this.$message({
          message: "至少选择一个巡察组",
          type: 'warning'
        });
        return
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(that.dataProcessing())
          let data = that.dataProcessing()
          // that.$refs.siRef.open("/checkedAll/checkInfo/getApplyInfo",data);
          that.submitProcessShow = true;
        }
      });
    },
    dataProcessing () {
      let that = this;
      let seeParams = JSON.parse(JSON.stringify(that.seeParamsCopy));
      let InfoListChild = JSON.parse(JSON.stringify(that.InfoListChild));
      let checkedInfoListChild = JSON.parse(JSON.stringify(that.checkedInfoListChild));
      let forms = { ...seeParams };
      forms.groupList = []
      checkedInfoListChild.forEach((item) => {
        InfoListChild.forEach((itm) => {
          if (item.groupId == itm.groupId) {
            if (!item.newdai) {
              item.newdai = [];
            }
            // itm.examUserIds = itm.examUserIds.toString();
            item.newdai.push(itm);
          }
        })
      })
      checkedInfoListChild.forEach((item) => {
        if (item.newdai) {
          forms.groupList.push({
            groupId: item.groupId,
            groupName: item.groupName,
            examTypeList: item.newdai
          })
        }
      })
      let newList = [];
      newList = checkedInfoListChild.filter(item => {
        return item.newdai && item.newdai.length > 0
      })
      newList.forEach(item => {
        item.examTypeList = item.newdai
      })
      let formSubmit = {
        title: that.form.title,
        list: newList,
        year: forms.year,
        batchId: forms.batchId,
        id: forms.id,
      }
      return formSubmit;
    },
    //真-提交
    saveFromData (value) {
      console.log(value)
      if (this.form.title == "" || this.form.title == null) {
        this.$message({
          message: "请输入标题",
          type: 'warning'
        });
        return
      }
      if (this.InfoListChild.length == 0) {
        this.$message({
          message: "至少选择一个巡察组",
          type: 'warning'
        });
        return
      }
      let data = this.dataProcessing();
      let nextarr = JSON.parse(value.variables);
      const loading = this.openLoading();
      if (this.typeId == 1) {
        submitApplyInfo({
          examInfo: JSON.stringify(data),
          flowInfo: JSON.stringify({
            nextUserId: nextarr.nextDealUser,
            option: nextarr.option,
            stepName: "巡察办室经理审批"
          })
        }).then(res => {
          this.$message({
            message: res.msg,
            type: 'success'
          });
          this.submitProcessShow = false
          this.open = false
          this.getDetail();
          loading.close();
          window.location.reload();
        })
      } else {
        submitApplyInfoPer({
          examInfo: JSON.stringify(data),
          flowInfo: JSON.stringify({
            nextUserId: nextarr.nextDealUser,
            option: nextarr.option,
            stepName: "巡察办室经理审批"
          })
        }).then(res => {
          this.$message({
            message: res.msg,
            type: 'success'
          });
          this.submitProcessShow = false
          this.open = false
          this.getDetail();
          loading.close();
          window.location.reload();
        })
      }

    },
    //暂存
    saveBtn () {
      let that = this;

      if (this.form.title == "" || this.form.title == null) {
        this.$message({
          message: "请输入标题",
          type: 'warning'
        });
        return
      }
      if (this.InfoListChild.length == 0) {
        this.$message({
          message: "请至少添加一个巡察组",
          type: 'warning'
        });
        return
      }
      console.log("开始暂存")
      if (this.disabledwindow) {
        return
      }
      this.disabledwindow = true
      let data = this.dataProcessing();
      if (this.typeId == 1) {
        saveApplyInfo({ examInfo: JSON.stringify(data) }).then(res => {
          this.disabledwindow = false
          console.log(res)
          that.$message.success(res.msg)
          that.getDetail();
        })
      } else {
        saveApplyInfoPer({ examInfo: JSON.stringify(data) }).then(res => {
          this.disabledwindow = false
          console.log(res)
          that.$message.success(res.msg)
          that.getDetail();
        })
      }
    },
    //获取详情
    getDetail () {
      let that = this;
      this.loadingChild = true;
      this.loadingAdd = true
      getBatchList({ year: this.seeParams.year }).then(resc => {
        that.batchIdChildOptions = resc.data ? resc.data : [];
        that.loadingAdd = false
      })
      getApplyInfo({ applyId: this.seeParams.id }).then(res => {
        let data = res.data;
        that.seeParams.batchId = data.batchId
        // that.seeParams = {
        //   year: data.year,
        //   id: data.id,
        //   batchId: data.batchId
        // }
        // getBatchList({ year: data.year }).then(resc => {
        //   that.batchIdChildOptions = resc.data ? resc.data : [];
        //   that.loadingAdd = false
        // })
        that.seeParamsCopy = JSON.parse(JSON.stringify(that.seeParams))

        that.checkedInfoListChild = data.list
        that.loadingChild = false
        that.form = data
        that.handleAddChild(1)

        // // that.InfoListChild

        // let Array = data.list;
        // //拆分类型
        // let InfoListChild = [];
        // Array.forEach((item) => {
        //   item.examTypeList && item.examTypeList.forEach((itm, idx) => {
        //     // 巡察组 考核类型 评分人选择 评分截止时间 备注
        //     let child = itm;
        //     child.groupId = item.groupId ? item.groupId : '';//巡察组id
        //     child.groupName = item.groupName ? item.groupName : '';//巡察组name
        //     child.examUserIds = child.examUserIds?child.examUserIds:[];//评分人选择多选，之后拼接字符串
        //     child.examLastTime = child.examLastTime?child.examLastTime:'';
        //     child.examRemark = child.examRemark?child.examRemark:'';
        //     child.index = idx;
        //     InfoListChild.push(child)
        //   })
        // })

        // let datas = {};
        // for (let i in InfoListChild) {
        //   datas[InfoListChild[i].groupId + "_" + InfoListChild[i].examTypeId] = InfoListChild[i];
        // }
        // console.log(datas);
        // that.showList = datas;
        // that.InfoListChild = JSON.parse(JSON.stringify(InfoListChild))
        // let formSJ = {
        //   batchId: that.seeParams.batchId,
        //   groupIds: res.data.groupIds,
        //   year: that.seeParams.year,
        // }
        // //返回评分人总列表
        // getExamLeaderList(formSJ).then(res => {
        //   that.ExamLeadListOptions = res.data
        // })

      })

    },
    yearchange (val) {
      console.log(val)
      //批次
      this.seeParams.batchId = null;
      getBatchList({ year: val }).then(res => {
        this.batchIdChildOptions = res.data ? res.data : [];
      })
    },

    handleClose () {
      this.$emit("close");
    },
    //发起审核-搜索
    handleQueryChild () {
      if (this.form.approveStatus != '3') {
        return
      }
      if (this.seeParams.year == null || this.seeParams.year == "") {
        this.$message({
          message: "请选择巡察年份",
          type: 'warning'
        });
        return
      }
      if (this.seeParams.batchId == null || this.seeParams.batchId == "") {
        this.$message({
          message: "请选择巡察批次",
          type: 'warning'
        });
        return
      }
      // 数据--不知道有没有十几条，要翻页
      if (this.typeId == 1) {
        GroupList(this.seeParams).then(res => {
          this.checkedInfoListChild = res.data
        })
      } else {
        GroupListPer(this.seeParams).then(res => {
          this.checkedInfoListChild = res.data
        })
      }

      //清空一下
      this.InfoListChild = []
      this.seeParamsCopy = JSON.parse(JSON.stringify(this.seeParams))
    },
    //打开弹窗
    // openDialog(show, btnRef) {
    //   if (
    //     !this.process.nextStepAndUser &&
    //     this.process.nextStepAndUser.length == 0
    //   ) {
    //     this.$message.error("流程人员异常");
    //     return;
    //   }
    //   this[show] = true;
    //   this.$refs[btnRef].init(this.process);
    // },
    //转办
    transferForm () {
      console.log("转办nextStep")
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    returnForm (data) {
      this.subForm.nextStep = data;
      this.subForm.message = data.length > 0 ? data[0].outLine : '';
      this.dialogVisibleReturn = true;
    },


    // 处理数据, 给每一条数据加order
    handleData (dataList, type) {
      let order = 1;
      let data = dataList;
      for (let i = 0, length = data.length; i < length; i++) {
        if (i === 0) {
          data[i].order = order;
        } else {
          console.log(data[i].groupId, '--', i)
          if (data[i].groupId == data[i - 1].groupId) {
            data[i]['order'] = order
          } else {
            data[i]['order'] = ++order;
          }
        }
      }
      if (type == 1) {
        this.InfoListChild = data;
      } else {
        this.InfoListChildDetail = data;
      }

    },
    // 鼠标进入单元格
    handleMouseEnter (row, column, cell, event) {
      this.cellIndex = row.order;
      console.log("鼠标进入单元格")
    },
    // 给相应的rowIndex添加类名
    rowClassName ({ row, rowIndex }) {
      console.log(this.cellIndex, row.order);
      let r = -1;
      if (this.cellIndex == row.order && this.cellIndex != -1) {
        console.log("正常加入")
        return 'hover-row-art';
      }
    },
    // 鼠标离开
    handleMouseLeave (row, column, cell, event) {
      this.cellIndex = -1;
      console.log("鼠标移除单元格")
    },
    /** 提交接口 */
    // submitForm () {
    //   let that = this;
    //   if (this.disabledwindow) {
    //     return
    //   }
    //   this.disabledwindow = true
    //   let form = {
    //     id: this.id,
    //     taskId: this.process.curtaskId,
    //     variables: JSON.stringify({
    //       option: this.subForm.option,
    //       message: this.subForm.message,
    //     })
    //   }
    //   approveApplyInfo(form).then(res => {
    //     that.$message(res.msg);
    //     that.getDetail();
    //     this.disabledwindow = false;
    //     this.dialogVisibleReturn = false;
    //   })
    //   // this.$refs["form"].validate(valid => {
    //   //   if (valid) {

    //   //   }
    //   // });
    // },
  },
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
  max-height: 500px;
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

.btnright {
  display: flex;
  justify-content: flex-end;
}

.crad-content-top20 {
  padding: 0 40px 20px;
}
</style>