<template>
  <div>
    <div class="app-main">
      <todo-bpm-header :pageTitle="title"></todo-bpm-header>
      <div class="crad">
        <div class="crad-header">
          <div class="crad-title" style="margin-top: 30px;">
            <span class="line"></span>
            <span>申请人信息</span>
          </div>
        </div>
        <div class="crad-content">
          <el-form ref="UserForm" :model="userInfo" label-width="120px" :disabled="true">
            <el-row>
              <el-col :span="12">
                <el-form-item label="姓名：" prop="title">
                  <el-input v-model="FitnessWhiteUserList.userText" placeholder="请输入发起人" readonly />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="部门：" prop="title">
                  <el-input v-model="FitnessWhiteUserList.companyDpname" placeholder="请输入部门" readonly />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="业务单号：" prop="title">
                  <el-input v-model="ORDER_NUMBER" placeholder="请输入部门" readonly />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="业务发生时间：" prop="title">
                  <el-date-picker v-model="CREATED_TIME" type="datetime" placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" >
                  </el-date-picker>
                </el-form-item>
              </el-col>

            </el-row>
          </el-form>
        </div>
      </div>
      <div class="crad">
        <div class="crad-header">
          <div class="crad-title">
            <span class="line"></span>
            <span>业务信息</span>
          </div>
        </div>
        <div class="crad-content">
          <v-form-render :form-json="formJson" :form-data="formData" :option-data="optionData" ref="vFormRef">
          </v-form-render>
        </div>
      </div>
      <!-- 操作按钮 -->

        <!-- :businessKey="orderNumber" -->
        <deep-btn ref="deepBtnRef" isTodo v-bind="$attrs" @initProcess="initProcess" :todoId="todoId" :businessKey="bussinessKey"
                  @saveDraftData="saveBtn" :needBeforeComplete="true"
                  @beforeComplete="beforeComplete" type="1">
          <!-- 发起默认按钮，由 businessKey 控制，为空的话显示下面的默认按钮 -->
          <!--<span slot="btns" v-if="form.id == null">
                        &lt;!&ndash; <el-button @click="cancel">关闭1 </el-button> &ndash;&gt;
                        <el-button type="primary" @click="handleDraft">暂存1</el-button>
                        <el-button type="primary" @click="submitForm">提交1</el-button>
                    </span>-->
        </deep-btn>


    </div>
    <!-- :isBusiness="isBusiness" :preUserName="preUserName" -->
  <!--  <complete-process ref="completeRef" :title="applyitle" :taskId="process.curtaskId" :dialogVisible="applyShow"
      isBusiness @handleData="handleData" @close="applyShow = false">
    </complete-process> -->

    <!-- <submit-process :dialogVisible="submitProcessShow" processKey="fine_apply_process" stepName="巡察办室经理审核"
    @close="submitProcessShow = false" @complete="handleData"
    title="提交"
    ></submit-process> -->
  <!--  @complete="submitForm('1')" -->

  </div>
</template>
<script>
import { getToken } from "@/utils/auth";
import { getInfo, queryOneAutoSqlInfo, saveFormInfo } from "@/api/form/info";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
import { init } from "events";
import deepBtn from "@/components/WorkFlow/deepBtn";
import {
  getFitnessWhiteViewEmpInfo,
} from "@/api/todoinfo/todoinfo";

export default {
  components: { deepBtn },
  name: "exam",
  data () {
    return {
      /* 注意：formJson是指表单设计器导出的json，此处演示的formJson只是一个空白表单json！！ */
      formJson: {},
      formData: {},
      optionData: {},
      id: null,
      formId: null,
      listId: null,
      FitnessWhiteUserList: {userText: null,companyDpname: null},//申请人信息
      title: null,
      todoId: null,
      flag: "1",
      userInfo: this.$store.getters.userInfo,
      // orderNumber: "",
      // 流程数据信息
      process: { allFlowStep: [], curtaskId: "" },
      // 主键
      bussinessKey: null,
      //下一步弹窗
      applyShow: false,
      applyitle: "下一步",
      submitProcessShow: false,
      ORDER_NUMBER: null,
      CREATED_TIME: null

    }
  },
  mounted () {
    let nTime = new Date();
    let nYear = nTime.getFullYear();
    let nMonth = nTime.getMonth() + 1;
    let nDay = nTime.getDate();
    let nHour = nTime.getHours();
    let nMin = nTime.getMinutes();
    let nSecond = nTime.getSeconds();
    // yyyy-MM-dd HH:mm
    this.userInfo.createTime = nYear + "-" + nMonth + "-" + nDay + " " + nHour + ":" + nMin
    this.init();
  },

  methods: {
    init () {
      let jsons = null;
      let newFormData = {};
      let geturl = window.location.href;
      let query = {}
      if (geturl.indexOf("?") > -1) {
        let type = geturl.split("?")[1].split("&");
        type.forEach(itm => {
          query[itm.split("=")[0]] = itm.split("=")[1]
        })
      }
      if (query.flag && query.flag.length) {
        this.flag = query.flag
      }
      if (query.id && query.id.length) {
        this.formId = query.id
      }
      if(query.listId && query.listId.length){
        this.listId=query.listId;
      }
      // if (query.orderNumber && query.orderNumber.length) {
      //   this.orderNumber = query.orderNumber
      // }
      this.bussinessKey = query.key
      this.todoId=query.todoInfoId;
      let param = {
        formId: query.id,
        key: query.key
      };
      //判断key值是否有插入，插入则还需要获取key的值
      queryOneAutoSqlInfo(param).then(response => {
        if (response.code === 200) {
          newFormData = response.data;
          this.id = newFormData.ID;
          this.ORDER_NUMBER=newFormData.ORDER_NUMBER;
          this.CREATED_TIME=newFormData.CREATED_TIME;
        }

        //获取用户信息
          let pa = {
            username: newFormData.CREATED_BY,
          };
          getFitnessWhiteViewEmpInfo(pa).then((response) => {
            this.FitnessWhiteUserList = response.rows[0];
            console.log(">>>>>>>"+ this.FitnessWhiteUserList)
          });


        //获取表单
        getInfo(this.formId).then(response => {
          jsons = response.data.formJson;
          this.title = response.data.formName;
          this.$refs.vFormRef.setFormJson(jsons)
          this.$nextTick(() => {
            this.$refs.vFormRef.setFormData(newFormData)
          })
        });

      });


    }, // 点击下一步前的操作
      beforeComplete(fn) {
        debugger
        fn();
      },
    submitForm (isSubmit) {
      let that = this;
      if (isSubmit == '0') {//暂存
        this.$confirm("是否确认暂存?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          that.saveForm(isSubmit)
        }).then(() => {

        }).catch(function () { });

      }
      if (isSubmit == '1') {//提交-下一步处理人
        this.submitProcessShow = true;
      }
    },
    // 下一步处理提交
    handleData (handleData) {
    let map =JSON.parse(handleData.variables);
      this.saveForm('1', map)
    },
    saveForm (isSubmit, data) {
      this.$refs.vFormRef.getFormData().then(formData => {
        // Form Validation OK
        formData.id = this.id;
        formData.formId = this.formId;
        formData.isSubmit = isSubmit;
        formData.listId =this.listId;
        if(isSubmit=='1'){
          formData.nextDealUser = data.nextDealUser;
          formData.option=data.option;
          formData.btnMessage=data.btnMessage;
        }
        saveFormInfo(formData).then(response => {
          if (response.code === 200) {
            let newFormData = {};
            this.msgSuccess("保存成功");
            if (this.flag == '1') {
              this.$router.push({
                path: '/lowcodeResult',
                query: {
                  id: response.msg
                }
              })
            } else {
              window.close();
            }
          }
        });
      }).catch(error => {
        // Form Validation failed
        this.$message.error(error)
      })
    },
    // 初始化流程信息
    initProcess (res) {
      let tempObj = {};
      debugger
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
    // 获得业务主键
    getBusinessKey () {
      // 如果是申报、修订、废止 页面，没有发起流程，业务主键为空
      // if (this.caseType === 1 || this.caseType === 2 || this.caseType === 3) {
      //   return "";
      // } else {
      //   return this.caseId;
      // }
    },
    // 点击暂存按钮
    saveBtn (handleData) {
      let map =JSON.parse(handleData.variables);
      console.log(map)
      debugger
    },
  },
}
</script>
<style lang="scss" scoped>
.app-main {
  padding: 15px;
  background: #fff;
}
</style>
