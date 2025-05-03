<template>
  <div>
    <div class="app-main">
      <todo-bpm-header :pageTitle="title"></todo-bpm-header>
    <div class="crad">
        <div class="crad-header">
          <div class="crad-title" style="margin-top: 30px;">
            <span class="line"></span>
            <span>个人信息</span>
          </div>
        </div>
        <div class="crad-content">
          <el-form ref="UserForm" :model="userInfo" label-width="120px" :disabled="true">
            <el-row>
              <el-col :span="12">
                <el-form-item label="发起人：" prop="title">
                  <el-input v-model="userInfo.nickName" placeholder="请输入发起人" readonly />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="部门：" prop="title">
                  <el-input v-model="userInfo.dept.deptName" placeholder="请输入部门" readonly />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="发起时间：" prop="title">
                  <el-date-picker v-model="userInfo.createTime" type="datetime" placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" style="width: 100%;">
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
      <div slot="footer" class="dialog-footer">
        <!-- :businessKey="orderNumber" -->
        <deep-btn ref="deepBtnRef" @initProcess="initProcess" :isTodo="true">
          <span slot="btns">
            <!-- process.authorizeFlag == '2' -->
            <el-button type="primary" v-if="flag != 'view'" @click="submitForm('0')">暂存</el-button>
            <el-button type="primary" v-if="flag != 'view'" @click="submitForm('1')">提交</el-button>
          </span>
        </deep-btn>
      </div>
    </div>
    <!-- :isBusiness="isBusiness" :preUserName="preUserName" -->
  <!--  <complete-process ref="completeRef" :title="applyitle" :taskId="process.curtaskId" :dialogVisible="applyShow"
      isBusiness @handleData="handleData" @close="applyShow = false">
    </complete-process> -->

    <submit-process :dialogVisible="submitProcessShow" :processKey="key" :stepName="stepName"
    @close="submitProcessShow = false" @complete="handleData"
    title="提交"
    ></submit-process>
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
import CompleteProcess from "@/components/WorkFlow/completeProcess";

import SubmitProcess from "@/components/WorkFlow/submitProcess";

import {
    getList
  } from "@/api/list/list";

export default {
  components: { deepBtn, CompleteProcess,SubmitProcess },
  name: "vFormAdd",
  data () {
    return {
      /* 注意：formJson是指表单设计器导出的json，此处演示的formJson只是一个空白表单json！！ */
      formJson: {},
      formData: {},
      optionData: {},
      id: null,
      formId: null,
      listId: null,
      title: null,
      key: null,
      stepName: null,
      flag: "1",
      userInfo: this.$store.getters.userInfo,
      // orderNumber: "",
      process: { allFlowStep: [], curtaskId: "" },
      //下一步弹窗
      applyShow: false,
      applyitle: "下一步",
      submitProcessShow: false,
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
      debugger
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

      let param = {
        formId: query.id,
        key: query.key
      };
      //获取列表
      getList(this.listId).then(response => {
        let listFlowProcdefId = response.data.listFlowProcdefId;
        let data = listFlowProcdefId.split(",");
        this.stepName=data[1];
        this.key=data[0];
      });
      //判断key值是否有插入，插入则还需要获取key的值
      queryOneAutoSqlInfo(param).then(response => {
        if (response.code === 200) {
          newFormData = response.data;
          this.id = newFormData.ID;
        }

        //获取表单
        getInfo(this.formId).then(response => {
          jsons = response.data.formJson;
          this.title = response.data.formName;
          this.$refs.vFormRef.setFormJson(jsons)
          
          if(JSON.stringify(newFormData)!="{}"){
            this.$nextTick(() => {
              this.$refs.vFormRef.setFormData(newFormData)
            })
          }
          
        });

      });



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
      //  nextDealUser && (formData.nextDealUser = nextDealUser);
        //alert( JSON.stringify(formData) )
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
    saveBtn () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定暂存?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            // 新增接口
            // addXunchaCaseProcess(this.form).then((response) => {
            //   if (response.code === 200) {
            //     this.msgSuccess("已暂存");
            //     this.$emit("close");
            //   }
            // });
          });
        }
      });
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
