<template>
<div>
  <h2 style="text-align: center;">{{title}}</h2>

  <el-form :model="formData" label-width="100px">
     <el-row v-for="(group, index) in groupedFormItems" :key="index">
       <el-col v-for="(item, idx) in group" :span="12" :key="idx">
         <el-form-item :label="item.label">

           <el-input  v-model="formData[item.prop]" v-if="item.disabled" :disabled="item.disabled"  placeholder="自动生成"></el-input>
           <el-input v-model="formData[item.prop]" v-else="item.disabled" :disabled="item.disabled"  placeholder="请输入"></el-input>
         </el-form-item>
       </el-col>
     </el-row>
   </el-form>
<!--子表单-->

<el-button  style="margin: 1%;width: 100px;height: 30px;" type="primary" @click="addRow">添加行</el-button>
    <el-table
       :data="childformData"
       style="width: 100%"
       max-height="250">
      <el-table-column
        type="index"
        :index="indexMethod"
        label="序号"
        align="center"
        width="60"
      >
      </el-table-column>
      <el-table-column
        v-for="(item, index) in formChildFields"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        :align="item.align"
        :headerAlign="item.headerAlign"
        :sortable="item.sortable"
        show-overflow-tooltip
      >
      <template slot-scope="scope">
            <div v-if="item.formfiledtype == 'input' ">
              <el-input v-model="scope.row[item.prop]"></el-input>
            </div>
            <div v-else-if="item.formfiledtype == 'select' ">

            </div>
            <div v-else-if="item.formfiledtype == 'pk' ">

            </div>
            <div v-else-if="item.formfiledtype == 'formselect' ">
              <el-input v-model="scope.row[item.prop]"  prefix-icon="el-icon-search"  @click.native="getDictData(scope.row,item.prop,item.formdatasourcetype,item.formdatasourceId)"></el-input>
            </div>
          <div v-else></div>


        </template>
      </el-table-column>
       <el-table-column
         fixed="right"
         label="操作"
         width="120">
         <template slot-scope="scope">
           <el-button
             @click.native.prevent="deleteRow(scope.$index, tableData)"
             type="text"
             size="small">
             移除
           </el-button>
         </template>
       </el-table-column>
     </el-table>

<div style="margin-top: 30px;">

   <el-button   style="margin-left: 45%;width: 70px;height: 40px;"  @click="close">关闭</el-button>
     <el-button  style="margin-left: 5%;width: 70px;height: 40px;" type="primary" @click="submitForm">保存</el-button>

  </div>



       <el-dialog :title="upload.title" :visible.sync="upload.open" width="80%" append-to-body>
         <div >
                  <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
                  <el-form-item label="高级查询" prop="formName">
                    <el-input
                        v-model="queryParams.param"
                        placeholder="请输入关键字"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                    />
                  </el-form-item>
                <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
                </el-form>
              </div>
         <div class="container" >
              <el-table
                 :data="tableData"
                 stripe
                 resizable
                 border
                 style="width: 100%;height: 100%;"
                @selection-change="handleSelectionChange"
               >
                 <el-table-column
                   type="selection"
                   label="选择"
                   align="center"
                   width="60"
                 >
                 </el-table-column>
                 <el-table-column
                   v-for="(item, index) in tableHead"
                   :key="index"
                   :prop="item.prop"
                   :label="item.label"
                   :width="item.width"
                   :align="item.align"
                   :headerAlign="item.headerAlign"
                   :sortable="item.sortable"
                   show-overflow-tooltip
                 >
                   <el-table-column
                     v-for="(item, index) in item.propChildren"
                     :key="index"
                     :prop="item.prop"
                     :label="item.label"
                     :width="item.width"
                     :align="item.align"
                     :headerAlign="item.headerAlign"
                     :sortable="item.sortable"
                     show-overflow-tooltip
                   >
                     <template slot-scope="scope">
                       <div v-if="item.scope === true">
                         <div v-if="scope.row[item.prop] == ''">
                           {{ scope.row[item.prop] }}
                         </div>
                         <div v-else-if="scope.row[item.prop] > '0'" style="color: green">
                           {{ scope.row[item.prop] }}%<i class="el-icon-caret-top"></i>
                         </div>
                         <div v-else-if="scope.row[item.prop] < '0'" style="color: red">
                           {{ scope.row[item.prop] }}%<i class="el-icon-caret-bottom"></i>
                         </div>
                       </div>
                       <div v-else-if="scope.row[item.prop] < '0'" style="color: red">
                         {{ scope.row[item.prop] }}
                       </div>
                       <div v-else>{{ scope.row[item.prop] }}</div>
                     </template>
                   </el-table-column>
                   <template slot-scope="scope">
                       <div v-if="item.scope === true">
                         <div v-if="scope.row[item.prop] == ''">
                           {{ scope.row[item.prop] }}
                         </div>
                         <div v-else-if="scope.row[item.prop] < '0'" style="color: red">
                           {{ scope.row[item.prop] }}
                         </div>
                          <div v-else-if="scope.row[item.prop] > '0'">
                           {{ scope.row[item.prop] }}
                         </div>
                       </div>
                     <div v-else>{{ scope.row[item.prop] }}</div>
                     </template>
                 </el-table-column>
               </el-table>

         </div>
         <div class="pageBox">
             <pagination
                     v-show="total>0"
                     :total="total"
                     :page.sync="queryParams.pageNum"
                     :limit.sync="queryParams.pageSize"
                     @pagination="getList"
             />
         </div>

        <div slot="footer" class="dialog-footer">
                   <el-button type="primary" @click="getSelectedRows">确 定</el-button>
                    <el-button @click="upload.open = false">取 消</el-button>
               </div>

       </el-dialog>
     </div>

</div>


</template>
<script>
  import { getToken } from "@/utils/auth";
  import {getInfo,queryOneAutoSqlInfo,saveChildFormInfo,getInfoNimin,saveFormInfoNimin} from "@/api/form/childinfo";
  import { getList ,selectAutoTableData,selectChildAutoTableData,autoRemove,importTemplate,importData,exportList } from "@/api/list/list";

  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from '@/utils/index';

  export default {
    data() {
      return {
        /* 注意：formJson是指表单设计器导出的json，此处演示的formJson只是一个空白表单json！！ */
        formJson: {},
        formData: {},
        childformData: [],
        childFormId: null,
        tableHead: [],
        listId: null,
        // 数据
        tableData: [],
        optionData: {},
        selectData: [],
        selectItem: null,
        selectedProp: null,
        rowInfos:{},
        id: null,
        formId: null,
        title: null,
        flag: 1,
        formFields: [],
        formChildFields: [],
        queryParamsChild: {
            pageNum: 1,
            pageSize: 100000,
            listId: null,
            param: null,
            status48624: null,
         },
        // 查询参数
        queryParams: {
            pageNum: 1,
            pageSize: 10,
            listId: null,
            param: null,
            status48624: null,
         },
         // 总条数
        total: 0,
        upload: {
            // 是否显示弹出层（用户导入）
            open: false,
            // 弹出层标题（用户导入）
            title: ""

        },
        rows: [
                { column1: '数据1-1', column2: '数据1-2', column3: '数据1-3' },
                { column1: '数据2-1', column2: '数据2-2', column3: '数据2-3' },
                { column1: '数据3-1', column2: '数据3-2', column3: '数据3-3' }
              ]


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
      let childFormId =getqys[4].split('=')[1]
      let keyId = getqys[5].split('=')[1]
      this.flag=getFlag;
      let newFormData={};
      let jsons =null;
      let param ={};
      param.formId = getformid;
      param.listId=getlistId;
      this.listId=getlistId;
      this.childFormId=childFormId;
      console.log("getlistId",getlistId);
      this.formId = getformid;
      param.key = getKey;

      //获取表头信息
      getList(getlistId).then(response => {
        this.title = response.data.listName;
        this.form = response.data;
         for (let i = 0; i < this.form.listTitleVo.length; i += 1) {
          if(this.form.listTitleVo[i].dispaly=true){
            this.formFields.push(this.form.listTitleVo[i]);
            console.log(this.form.listTitleVo[i])
          }
        }
        for (let i = 0; i < this.form.listChildTitleVo.length; i += 1) {
             console.log(this.form.listChildTitleVo[i]);
          if(this.form.listChildTitleVo[i].display === true){
            this.formChildFields.push(this.form.listChildTitleVo[i]);
          }
        }
       // this.formFields=this.form.listTitleVo;

       let childParam ={};
      childParam.formId = getformid;
      childParam.listId=getlistId;
      childParam.key = getKey;
       //判断key值是否有插入，插入则还需要获取key的值
       queryOneAutoSqlInfo(childParam).then(response => {

           if (response.code === 200) {
             if(response.msg==="-1"){
             }else{
               newFormData = response.data;
               this.formData=newFormData;
               this.id=newFormData.ID;
               //加载子表数据
               if(this.childFormId!=null){
                 this.queryParamsChild.param =this.formData;
                 this.queryParamsChild.listId=this.listId;
                 this.queryParamsChild.status48624 = this.childFormId;
                 selectChildAutoTableData(this.queryParamsChild).then(response => {
                   console.log(" response.data.rows>>"+ response.data.rows);
                     this.childformData = response.data.rows || [];
                 });
               }


             }

           }

       });





      });


    },
  computed: {
    groupedFormItems() {
      const grouped = [];
      for (let i = 0; i < this.formFields.length; i += 2) {
        grouped.push(this.formFields.slice(i, i + 2));
      }
      return grouped;
    }
  },
    methods: {
      close(){
        window.close();
      },
      /** 搜索按钮操作 */
      handleQuery() {
          this.queryParams.pageNum = 1;
          this.getList();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.selectData=selection;
      },
      getDictData(row,prop,formdatasourcetype,formdatasourceId){
        this.selectItem=row;
        this.selectedProp = prop;
        if(formdatasourcetype=='formid'){
          //加载弹出框选择数据信息
          this.upload.open = true;
          this.queryParams.listId=formdatasourceId;
          //获取表头信息
          getList(formdatasourceId).then(response => {
            console.log(">>>>>>>>>",response.data);
                    this.tableHead=response.data.listTitleVo;
                     this.upload.title = response.data.listName;
                    this.getList();
                });
          }else if( formdatasourcetype=='apicode'){

          }else if( formdatasourcetype=='table'){

          }
      },
      getSelectedRows(row) {
        this.upload.open = false;
            // 获取勾选的行数据
            console.log( this.selectData);
            //定义一个临时变量
            let temMap = {};
            //遍历行数据封装
           this.selectData.forEach((item, index) => {
                   console.log("Index:", index, "Value:", item);
                   // 遍历对象的键值对
                         for (let key in item) {
                           if (item.hasOwnProperty(key)) {
                             let value = item[key];
                             //console.log("Key:", key, "Value:", value);
                             // 在这里你可以执行其他操作，如将键值对存储到一个数组中或者对每个键值对执行一些操作

                              try{
                                if(temMap[key]==undefined){
                                  temMap[key]=value;
                                }else{
                                   temMap[key]=temMap[key]+"、"+value;
                                }
                               // this.selectItem[key]= value;
                              }catch(e){
                                //TODO handle the exception
                              }
                           }
                         }
                 });

                 for (let key in temMap) {
                   if (temMap.hasOwnProperty(key)) {
                     let value = temMap[key];
                     //console.log("Key:", key, "Value:", value);
                     // 在这里你可以执行其他操作，如将键值对存储到一个数组中或者对每个键值对执行一些操作
                      try{
                        this.selectItem[key]= value;
                      }catch(e){
                        //TODO handle the exception
                      }
                   }
                 }

              //this.selectItem[this.selectedProp]="fagege";
             // console.log( "this.selectItem》》"+this.selectItem);

            //将数据回写到原先的行位置

          },
      getList() {
          this.loading = true;
          selectAutoTableData(this.queryParams).then(response => {
              this.tableData = response.data.rows || [];
              this.loading = false;
              this.total = Number(response.data.total);
          });
      },
      indexMethod(index) {
           return index + 1;

         },
       addRow() {
          // 清空转换后的对象
          let rowInfos = {};

         this.childformData.push(rowInfos);

          },
        deleteRow(index,tableData){

            this.childformData.splice(index, 1);
          },
      submitForm() {
        let map =this.formData;
        map.listId=this.listId;
        map.formId=this.formId;
        map.childFormId=this.childFormId;
        map.childmap=this.childformData;
       /* this.childformData.listId=this.listId;


        let map =JSON.stringify(this.formData);
        let childmap =JSON.stringify(this.childformData);
       // map.childmap=childmap;
        map.listId=this.listId;
        map.formId=this.formId;
        map.childFormId=this.childFormId; */
        console.log(JSON.stringify(map));
         saveChildFormInfo(JSON.stringify(map)).then(response => {
              if (response.code === 200) {
                 this.msgSuccess("保存成功");

               //  location.reload()
              }
          });



      }
    }
  }
</script>

<style lang="scss" scoped>
.container {
      display: flex; /* 使用 Flexbox 布局 */
    }

    .box {
      width: 30%; /* 设置每个 div 占据容器的一半宽度 */
      padding: 10px;
      border: 0px solid #ccc;
      box-sizing: border-box; /* 让 padding 不会撑大盒子 */
    }

</style>
