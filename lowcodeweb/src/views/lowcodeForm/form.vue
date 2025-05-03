<template>
  <div>

  <h2 style="text-align: center;">{{title}}</h2>
    <v-form-render :form-json="formJson" :form-data="formData" :option-data="optionData" ref="vFormRef">
    </v-form-render>
   <span slot="btns">

     <el-button v-if="flag != 'view'" style="margin-left: 45%;width: 70px;height: 40px;" type="primary" @click="close">取消</el-button>
     <el-button  v-if="flag == 'view'" style="margin-left: 45%;width: 70px;height: 40px;" type="primary" @click="close">关闭</el-button>
     <el-button v-if="flag != 'view'" style="margin-left: 5%;width: 70px;height: 40px;" type="primary" @click="submitForm">保存</el-button>
   </span>
  </div>
</template>
<script>
  import { getToken } from "@/utils/auth";
  import {getInfo,queryOneAutoSqlInfo,saveFormInfo,getInfoNimin,saveFormInfoNimin} from "@/api/form/info";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from '@/utils/index';

  export default {
    data() {
      return {
        /* 注意：formJson是指表单设计器导出的json，此处演示的formJson只是一个空白表单json！！ */
        formJson: {},
        formData: {},
        optionData: {},
        id: null,
        formId: null,
        title: null,
        flag: 1

      }
    },
    mounted() {
      let geturl = window.location.href;
      let getqyinfo = geturl.split('?')[1]   //qycode=1001&qyname=%E4%BC%81%E4%B8%9A%E5%BF%99
      let getqys = getqyinfo.split('&')  //['qycode=1001', 'qyname=%E4%BC%81%E4%B8%9A%E5%BF%99%E5%95%8A%E5%95%8A%E5%95%8A']
      let getformid =  getqys[0].split('=')[1]
      let getKey = getqys[1].split('=')[1]
      let getFlag = getqys[2].split('=')[1]
      let getlistId =getqys[3].split('=')[1]
      this.flag=getFlag;
      let newFormData={};
      let jsons =null;
      let param ={};
      param.formId = getformid;
      param.listId=getlistId;
      this.listId=getlistId;
      console.log("getlistId",getlistId);
      this.formId = getformid;
      param.key = getKey;
      if(getFlag=="-10"){
        //匿名模式
        //获取表单

        getInfoNimin(getformid).then(response => {

          jsons = response.data.formJson;
          this.title = response.data.formName;
          this.$refs.vFormRef.setFormJson(jsons)
          this.$nextTick(() => {
            this.$refs.vFormRef.setFormData(newFormData)
          })
        });
      }else{
        //判断key值是否有插入，插入则还需要获取key的值
        queryOneAutoSqlInfo(param).then(response => {

            if (response.code === 200) {
              if(response.msg==="-1"){
                //获取表单
                getInfoNimin(getformid).then(response => {

                  jsons = response.data.formJson;
                  this.title = response.data.formName;
                  this.$refs.vFormRef.setFormJson(jsons)
                  this.$nextTick(() => {
                    this.$refs.vFormRef.setFormData(newFormData)
                  })
                });

              }else{
                newFormData = response.data;

                this.id=newFormData.ID;
              }

            }
             //获取表单
            getInfo(getformid).then(response => {
              debugger;
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
      }

    },

    methods: {
      close(){
        window.close();
      },
      submitForm() {

        this.$refs.vFormRef.getFormData().then(formData => {
          // Form Validation OK
          formData.id = this.id;
          formData.formId = this.formId;
          formData.listId = this.listId;
          if(this.flag=="-10"){//匿名保存
            saveFormInfoNimin(JSON.stringify(formData)).then(response => {
                if (response.code === 200) {
                  let newFormData={};
                   this.msgSuccess("保存成功");
                   if(this.flag=='1' || this.flag=='-10'){
                     this.$router.push({
                               path: '/login',
                               query: {
                                 id: response.msg
                               }
                             })
                   }else{
                    window.close();
                   }


                 //  location.reload()
                }
            });
          }else{
            saveFormInfo(JSON.stringify(formData)).then(response => {
                if (response.code === 200) {
                  let newFormData={};
                   this.msgSuccess("保存成功");
                   if(this.flag=='1' || this.flag=='-10'){
                     this.$router.push({
                               path: '/lowcodeResult',
                               query: {
                                 id: response.msg
                               }
                             })
                   }else{
                    window.close();
                   }


                 //  location.reload()
                }
            });
          }

        }).catch(error => {
          // Form Validation failed
          this.$message.error(error)
        })
      }
    }
  }
</script>
