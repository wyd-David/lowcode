<template>
    <div class="app-container">
      <!--  <expand-collapse> -->
          <div slot="left" v-if="this.needQeuryBtn === 'Y'">
                <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
									<!-- <el-form-item label="关键字" prop="formName">
									<el-input
											v-model="queryParams.param"
											placeholder="请输入关键字"
											clearable
											size="small"
											@keyup.enter.native="handleQuery"
									/>
								</el-form-item> -->

                <el-form-item label="高级查询" prop="formName">
                	<el-input
                			v-model="queryParams.param"
                			placeholder="请输入关键字"
                			clearable
                			size="small"
                			@keyup.enter.native="handleQuery"
                	/>
                </el-form-item>
                	<el-form-item label="数据状态" prop="status48624">
                	        <el-select v-model="queryParams.status48624" placeholder="请选择状态" clearable :style="{width: '100%'}">
                	          <el-option v-for="(item, index) in select48624Options" :key="index" :label="item.label"
                	            :value="item.value" :disabled="item.disabled"></el-option>
                	        </el-select>
                	      </el-form-item>
                </el-form-item>
              <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
               <!-- <el-button  size="mini" @click="resetQuery">重置</el-button>
 -->
							</el-form>
            </div>
           <!-- <div slot="right">

            </div>
        </expand-collapse> -->
        <div class="container">
            <el-row :gutter="10" class="mb12 utlsBtn">
                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-plus"
                            size="mini"
                            @click="handleAdd"
                            v-if="this.needViewBtn === 'Y' && this.listFlowProcdefId === null"
                    >新增</el-button>
                </el-col>

                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                            @click="startFlow"
                            v-if="this.listFlowProcdefId != null"
                    >发起申请</el-button>
                </el-col>
                <!-- <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                            @click="startFlow"
                    >发起申请</el-button>
                </el-col> -->
               <!--  <el-col :span="1.5">
                    <el-button
                            type="danger"
                            icon="el-icon-delete"
                            size="mini"
                            :disabled="multiple"
                            @click="handleDelete"
                            v-hasPermi="['form:info:remove']"
                    >删除</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-plus"
                            size="mini"
                            @click="formAdd"
                            v-hasPermi="['form:info:add']"
                    >设计表单</el-button>
                </el-col> -->
               <!-- <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-lock"
                            size="mini"
                            :disabled="multiple"
                            @click="disabledBtnClick(1)"
                            v-hasPermi="['form:info:remove']"
                    >禁用</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-unlock"
                            size="mini"
                            :disabled="multiple"
                            @click="disabledBtnClick(0)"
                            v-hasPermi="['form:info:remove']"
                    >启用</el-button>
                </el-col> -->
                 <el-col :span="1.5">
                    <el-button
                            type="default"
                            icon="el-icon-upload"
                            size="mini"
                            @click="openImportTable"
                            v-if="this.needInportBtn === 'Y'"

                    >导入</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="default"
                            icon="el-icon-download"
                            size="mini"
                            @click="handleExport"
                            v-if="this.needExportBtn === 'Y'"

                    >导出</el-button>
                </el-col>
                <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
            </el-row>
             <el-table
                :data="tableData"
                stripe
                resizable
                border

                style="width: 100%;height: 100%;"
              >
                <el-table-column
                  type="index"
                  :index="indexMethod"
                  label="序号"
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

                <el-table-column
                  label="操作"
                  align="center"
                  class-name="small-padding fixed-width"
                  fixed="right"
                >
                  <template slot-scope="scope">
                   <!-- v-if="scope.row.todoDataType == '1'" -->

                    <el-button
                        @click="toDetail(scope.row)"
                        size="mini"
                        type="text"
                        style="font-size: 13px"

                        >详情
                      </el-button>
                     <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleUpdate(scope.row)"
                    >修改
                    </el-button>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="usedd(scope.row)"
                    >启用
                    </el-button>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="disabledd(scope.row)"
                    >禁用
                    </el-button>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="handleDelete(scope.row)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>


              </el-table>
            <div class="pageBox">
                <pagination
                        v-show="total>0"
                        :total="total"
                        :page.sync="queryParams.pageNum"
                        :limit.sync="queryParams.pageSize"
                        @pagination="getList"
                />
            </div>
        </div>


        <!-- 基础单信息导入对话框 -->
        <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
            <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport + '&formId='+upload.formId" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                    将文件拖到此处，或
                    <em>点击上传</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                    <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
                    <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
                </div>
                <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
            </el-upload>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitFileForm">确 定</el-button>
                <el-button @click="upload.open = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { getToken } from "@/utils/auth";
    import { getList ,selectAutoTableData,autoRemove,importTemplate,importData,exportList } from "@/api/list/list";

    import ExpandCollapse from "@/components/ExpandCollapse/index";
    import {formatDate} from '@/utils/index';
    export default {
        name: "Info",
                                                                                                                                                                                                                                                                                    components: { ExpandCollapse },
        data() {
            return {
                // 单表头  是否对该列进行数据比较，靠scope来判断。
                tableHead: [],
                // 数据
                tableData: [],
                needDeleteBtn: null,//删除按钮
                needExportBtn: null,//导出按钮
                needInportBtn: null,//导入按钮
                needQeuryBtn: null,//查询按钮
                needUpdateBtn: null,//修改按钮
                needViewBtn: null,//查看按钮
                listFlowProcdefId: null,//流程ID
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
                // 基础单信息表格数据
                infoList: [],
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
                    formId: null,
                    // 设置上传的请求头部
                    headers: { Authorization: "Bearer " + getToken() },
                    // 上传的地址
                    url:
                      CONFIG.url +
                      process.env.VUE_APP_BASE_API +
                      "/list/list/importData"
                },
                   // 是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否字典
                isSingleUserOptions: [],
                // 数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式字典
                bindDataWayOptions: [],
                listId: null,//列表ID
                formId: null,//表单ID
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    listId: null,
                    param: null,
                    status48624: null
                 },
                // 表单参数
                form: {},
                // 表单校验
                list: {},//列表参数
                 formData: {
                        select48624: undefined,
                      },
                      rules: {
                        select48624: [{
                          required: true,
                          message: '请选择状态',
                          trigger: 'change'
                        }],
                      },
                      select48624Options: [{
                        "label": "启用",
                        "value": '启用'
                      }, {
                        "label": "禁用",
                        "value": '禁用'
                      }],
        };
        },filters: {
            filterDate(val) {
                return formatDate(val);
            },
            sys_yes_noFieldFilter(val, optionArr) {
                  var arr = optionArr.filter(item => item.dictValue === val);
                  if (!arr.length) return '';
                  return arr[0].dictLabel;
            },
            sys_yes_noFieldFilter(val, optionArr) {
                  var arr = optionArr.filter(item => item.dictValue === val);
                  if (!arr.length) return '';
                    return arr[0].dictLabel;
            },
        },
        mounted() {

          // let geturl = window.location.href;
          // let getqyinfo = geturl.split('?')[1]   //qycode=1001&qyname=%E4%BC%81%E4%B8%9A%E5%BF%99
          // let getqys = getqyinfo.split('&')  //['qycode=1001', 'qyname=%E4%BC%81%E4%B8%9A%E5%BF%99%E5%95%8A%E5%95%8A%E5%95%8A']
          // let getlistid =  getqys[0].split('=')[1]
          // let getformId = getqys[1].split('=')[1]


         let geturl = window.location.href;
         let getqyinfo = geturl.split('|')[1]   //qycode=1001&qyname=%E4%BC%81%E4%B8%9A%E5%BF%99
         let getqys = getqyinfo.split('&')  //['qycode=1001', 'qyname=%E4%BC%81%E4%B8%9A%E5%BF%99%E5%95%8A%E5%95%8A%E5%95%8A']
         let getlistid =  getqys[0]
         let getformId = getqys[1]



          this.formId = getformId;
          this.listid = getlistid;
          this.queryParams.listId=getlistid;
          this.upload.formId=getformId;

          let query= 'N';

          //获取表头信息
          getList(getlistid).then(response => {
                    this.form = response.data;
                   // let obj = JSON.parse(this.form.listMemo);
                    this.tableHead=this.form.listTitleVo;
                    this.needDeleteBtn=this.form.needDeleteBtn;
                    this.needExportBtn=this.form.needExportBtn;
                    this.needInportBtn=this.form.needInportBtn;
                    this.needQeuryBtn=this.form.needQeuryBtn;
                    this.needUpdateBtn=this.form.needUpdateBtn;
                    this.needViewBtn=this.form.needViewBtn;
                    this.listFlowProcdefId=this.form.listFlowProcdefId;
                    query=this.form.needQeuryBtn;
                    if(query == 'Y'){
                      this.getList();
                    }else{
                      this.$message("无数据查看权限，请联系管理员！");
                    }


                });


        },
        created() {

            /* this.getDicts("sys_yes_no").then(response => {
              this.isSingleUserOptions = response.data || [];
            });
            this.getDicts("bindDataWay").then(response => {
                this.bindDataWayOptions = response.data || [];
                }); */
            },
        methods: {
           indexMethod(index) {
                return index + 1;
              },
            /** 查询基础单信息列表 */
            getList() {
                this.loading = true;
                selectAutoTableData(this.queryParams).then(response => {
                     console.log(response.data.rows)
                    this.tableData = response.data.rows || [];
                    this.loading = false;
                    this.total = Number(response.data.total);
                });
            },
            // 是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否字典翻译
            isSingleUserFormat(row, column) {
                        return this.selectDictLabel(this.isSingleUserOptions, row.isSingleUser);
            },
            // 数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式字典翻译
            bindDataWayFormat(row, column) {
                        return this.selectDictLabel(this.bindDataWayOptions, row.bindDataWay);
            },
              // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            /** 重置按钮操作 */
            resetQuery() {
                this.resetForm("queryForm");
                this.handleQuery();
            },
            // 多选框选中数据
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.formUuid)
                this.single = selection.length!==1
                this.multiple = !selection.length
            },
            /** 新增按钮操作 */
            handleAdd() {
                let that =this;
                const baseUrl = location.protocol+'//' + location.host+'/#/lowcodeForm?id='+this.formId+'&key=null&flag=2&listId='+this.listid+'&uuid='+Math.ceil(Math.random()*1000);
                var flag = window.open(baseUrl);
                var loop = setInterval(function(){
                    if(flag.closed){
                        clearInterval(loop);
                        that.getList();
                    }
                },3);
            },
            //发起申请
            startFlow() {
                let that =this;
                const baseUrl = location.protocol+'//' + location.host+'/#/lowcodeFormFlow?id='+this.formId+'&key=null&flag=2&listId='+this.listid+'&uuid='+Math.ceil(Math.random()*1000);
                var flag = window.open(baseUrl);
                var loop = setInterval(function(){
                    if(flag.closed){
                        clearInterval(loop);
                        that.getList();
                    }
                },3);
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
              if(this.needUpdateBtn!='Y'){
                this.$message("该列表无修改权限，请联系管理员!");
                return;
              }
              let param={};
              let keyid=row.id;
              let keyID=row.ID
              if(keyid==null || keyid=='undefined' || keyid==''){
                keyid=keyID;
              }
              let that =this;
                const baseUrl = location.protocol+'//' + location.host+'/#/lowcodeForm?id='+this.formId+'&key='+keyid+'&flag=3&listId='+this.listid+'&uuid='+Math.ceil(Math.random()*1000);
                var flag = window.open(baseUrl);
                var loop = setInterval(function(){
                    if(flag.closed){
                        clearInterval(loop);
                        that.getList();
                    }
                },3);
            },
            //查看
            toDetail(row) {
              let keyid=row.id;
              let keyID=row.ID
              if(keyid==null || keyid=='undefined' || keyid==''){
                keyid=keyID;
              }
              if(this.needViewBtn==='Y'){
                const baseUrl = location.protocol+'//' + location.host+'/#/lowcodeForm?id='+this.formId+'&key='+keyid+'&flag=view&listId='+this.listid+'&uuid='+Math.ceil(Math.random()*1000);
                let result = window.open(baseUrl);
              }else{
                this.$message("该列表无查看权限，请联系管理员!");
              }


            },
            /** 删除按钮操作 */
            handleDelete(row) {
               if(this.needViewBtn!='Y'){
                 this.$message("该列表无删除权限，请联系管理员!");
                 return;
               }
               let keyid=row.id;
               let keyID=row.ID
               if(keyid==null || keyid=='undefined' || keyid==''){
                 keyid=keyID;
               }
                let param={};
                const formUuids = keyid ;
                const formId =this.formId;
                param.formId=formId;
                param.listId=this.listid;
                param.id=formUuids;
                param.flag = 'delete';
                this.$confirm('删除后无法找回，是否确认删除该数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return autoRemove(param);
                }).then((response) => {
                    this.getList();
                    this.msgSuccess(response.msg);
                }).catch(function() {});
            },
            /** 启用按钮操作 */
            usedd(row) {
              if(this.needUpdateBtn!='Y'){
                this.$message("该列表无修改权限，请联系管理员!");
                return;
              }
              let keyid=row.id;
              let keyID=row.ID
              if(keyid==null || keyid=='undefined' || keyid==''){
                keyid=keyID;
              }
             let param={};
                const formUuids = keyid ;
                const formId =this.formId;
                param.formId=formId;
                param.id=formUuids;
                param.listId=this.listid;
                param.flag = 'use';
                this.$confirm('是否确认启用该数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return autoRemove(param);
                }).then((response) => {
                    this.getList();
                    this.msgSuccess("启用成功!");
                }).catch(function() {});
            },
            /** 禁用按钮操作 */
            disabledd(row) {
              if(this.needUpdateBtn!='Y'){
                this.$message("该列表无修改权限，请联系管理员!");
                return;
              }
             let param={};
             let keyid=row.id;
             let keyID=row.ID
             if(keyid==null || keyid=='undefined' || keyid==''){
               keyid=keyID;
             }
                const formUuids = keyid ;
                const formId =this.formId;
                param.formId=formId;
                param.id=formUuids;
                param.listId=this.listid;
                param.flag = 'disable';
                this.$confirm('是否确认禁用该数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return autoRemove(param);
                }).then((response) => {
                    this.getList();
                    this.msgSuccess("禁用成功!");
                }).catch(function() {});
            },
            /** 导出按钮操作 */
            handleExport() {
                const queryParams = this.queryParams;
                this.$confirm('是否确认导出所有基础单信息数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return exportList(queryParams);
                }).then(response => {
                    this.download(response.msg);
                }).catch(function() {});
            },
            /** 导入按钮操作 */
            openImportTable() {
                this.upload.title = "基础单信息数据导入";
                this.upload.open = true;
            },
            /** 下载模板操作 */
            importTemplate() {
             let param ={};
              param.formId= this.formId;
                importTemplate(param).then((response) => {
                    this.download(response.msg);
                });
            },
            // 文件上传中处理
            handleFileUploadProgress(event, file, fileList) {
                this.upload.isUploading = true;
            },
            // 文件上传成功处理
            handleFileSuccess(response, file, fileList) {
                this.upload.open = false;
                this.upload.isUploading = false;
                this.$refs.upload.clearFiles();
                this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
                this.getList();
            },
            // 提交上传文件
            submitFileForm() {
              let geturl = window.location.href;
              let getqyinfo = geturl.split('?')[1] ;  //qycode=1001&qyname=%E4%BC%81%E4%B8%9A%E5%BF%99
              let getqys = getqyinfo.split('&') ; //['qycode=1001', 'qyname=%E4%BC%81%E4%B8%9A%E5%BF%99%E5%95%8A%E5%95%8A%E5%95%8A']
              let getlistid =  getqys[0].split('=')[1];
              this.upload.listId=getlistid;
              this.$refs.upload.submit();
            },
        }
    };
</script>



<style lang="scss" scoped>
    .app-container{
        background: #F5F7F9;
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
    .utlsBtn{
        /deep/ .el-button--mini{
            padding: 9px 15px;
        }
        .normalBtn{
            /deep/ .el-button--mini{
                padding: 8px;
            }
        }
    }
    .container{
        padding: 12px;
        background: #fff;
        border-radius: 2px;
        /deep/ .el-table__header-wrapper{
            th{
                background: rgba(45, 140, 240, 0.04) !important;
                font-size: 14px;
                font-family: PingFangSC-Semibold, PingFang SC;
                font-weight: 600;
                color: rgba(23, 35, 61, 0.75);
            }
        }
        .pageBox{
            height: 32px;
            .pagination-container{
                padding: 0 !important;
                margin: 0;
                height: 32px;
                margin-top: 12px;
                /deep/ .number,
                /deep/ .btn-prev,
                /deep/ .btn-next{
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
                /deep/ .el-input__inner{
                    height: 32px;
                    line-height: 32px;
                }
            }
            /deep/ .el-pagination__total{
                line-height: 32px;
            }
        }
    }
    /deep/ .el-form-item__label{
        font-size: 14px;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: rgba(23, 35, 61, 0.75);
    }
</style>
