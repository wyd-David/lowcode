<template>
    <div class="app-container">
        <expand-collapse>
            <div slot="left">
                <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
									<el-form-item label="表单名称" prop="formName">
									<el-input
											v-model="queryParams.formName"
											placeholder="请输入名称"
											clearable
											size="small"
											@keyup.enter.native="handleQuery"
									/>
								</el-form-item>

								<el-form-item label="API" prop="apiInfo">
									<el-input
											v-model="queryParams.apiInfo"
											placeholder="请输入ApiCode"
											clearable
											size="small"
											@keyup.enter.native="handleQuery"
									/>
								</el-form-item>
							</el-form>
            </div>
            <div slot="right">
                <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
                <el-button  size="mini" @click="resetQuery">重置</el-button>
            </div>
        </expand-collapse>
        <div class="container">
            <el-row :gutter="10" class="mb12 utlsBtn">
                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-plus"
                            size="mini"
                            @click="handleAdd"
                            v-hasPermi="['form:info:add']"
                    >新增</el-button>
                </el-col>
                <!-- <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                            :disabled="single"
                            @click="handleUpdate"
                            v-hasPermi="['form:info:edit']"
                    >修改</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="danger"
                            icon="el-icon-delete"
                            size="mini"
                            :disabled="multiple"
                            @click="handleDelete"
                            v-hasPermi="['form:info:remove']"
                    >删除</el-button>
                </el-col> -->
              <!--  <el-col :span="1.5">
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
                <!-- <el-col :span="1.5">
                    <el-button
                            type="default"
                            icon="el-icon-upload"
                            size="mini"
                            @click="openImportTable"
                            v-hasPermi="['form:info:importData']"
                    >导入</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="default"
                            icon="el-icon-download"
                            size="mini"
                            @click="handleExport"
                            v-hasPermi="['form:info:importData']"
                    >导出</el-button>
                </el-col> -->
                <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
                <!-- <el-table-column type="selection" width="55" align="left" /> -->
                 <el-table-column label="KEY" align="center" width="250" prop="formUuid" :show-overflow-tooltip='true' />
                <el-table-column label="表单名称" align="center" width="200"  prop="formName" :show-overflow-tooltip='true'/>
                 <el-table-column label="实体表名称" align="center" width="200"  prop="formEntityName" :show-overflow-tooltip='true'/>
                <el-table-column label="绑定方式" align="center" width="200" prop="bindDataWay" :formatter="bindDataWayFormat" />

                  <el-table-column label="数据量" width="100" align="center" prop="formStatus" />
               <!-- <el-table-column label="表单json" align="center" prop="formJson" /> -->
                <!-- <el-table-column label="是否一个用户只能存储一条" align="center" prop="isSingleUser" :formatter="isSingleUserFormat" /> -->

                <el-table-column label="创建人" align="center" prop="createdBy"  width="100"/>
                <el-table-column label="创建时间" align="center" prop="createdTime" width="100">
                    <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
                    </template>
                </el-table-column>
               <!-- <el-table-column label="更新人" align="center" prop="updatedBy" />
                <el-table-column label="更新时间" align="center" prop="updatedTime" width="180">
                    <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.updatedTime, '{y}-{m}-{d}') }}</span>
                    </template>
                </el-table-column> -->


                <el-table-column label="操作" align="center" class-name="small-padding fixed-width"   fixed="right">
                    <template slot-scope="scope">
                      <!--  <el-button
                                size="mini"
                                type="text"
                                @click="handleQueryOption(scope.row)"
                        >查看</el-button> -->
                        <el-button
                                size="mini"
                                type="text"
                                @click="handleUpdate(scope.row)"
                                v-hasPermi="['form:info:edit']"
                        >修改</el-button>
                        <el-button
                                  size="mini"
                                  type="text"
                                  @click="copyFormLink(scope.row)"
                          >复制链接</el-button>
                        <!--  <el-button
                                    size="mini"
                                    type="text"
                                    @click="designReport(scope.row)"
                            >设计报表</el-button> -->
                          <!--  <el-button
                                      size="mini"
                                      type="text"
                                      @click="designView(scope.row)"
                              >查看大屏</el-button> -->
                        <el-button
                                size="mini"
                                type="text"
                                @click="handleDelete(scope.row)"
                                v-hasPermi="['form:info:remove']"
                        >删除</el-button>
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
        <!-- 添加或修改基础单信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
            <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
              <el-form-item label="表单名称" prop="formName" required="true">
                  <el-input v-model="form.formName" placeholder="请输入中文表单名称" />
              </el-form-item>
            <el-form-item label="实体表名" prop="formEntityName"  >
              <el-input v-model="form.formEntityName" placeholder="请输入英文数据库表名称:TEST_SSS" />
            </el-form-item>

            <!-- <el-form-item label="是否单次上传" prop="isSingleUser">
                <el-select v-model="form.isSingleUser" placeholder="是否一个用户只能上传一次">
                  <el-option v-for="dict in isSingleUserOptions"
                          :key="dict.dictValue"
                          :label="dict.dictLabel"
                          :value="dict.dictValue">
                  </el-option>
                </el-select>
            </el-form-item> -->
            <el-form-item label="绑定方式" prop="bindDataWay">
                <el-select v-model="form.bindDataWay" placeholder="请选择数据绑定方式">
                  <el-option
                      v-for="dict in bindDataWayOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                  ></el-option>
                </el-select>
            </el-form-item>
          <el-form-item label="大屏ID" prop="apiInfo" >
                <el-input v-model="form.apiInfo" placeholder="请输入大屏ID" />
            </el-form-item>
            <el-form-item label="表单(复制表单设计器中的json)" prop="formJson" required="true">
              <el-input v-model="form.formJson" rows="15" type="textarea" placeholder='{
  "widgetList": [
    {
      "type": "input",
      "icon": "text-field",
      "formItemFlag": true,
      "options": {
        "name": "input99197",
        "label": "姓名",
        "labelAlign": "",
        "type": "text",
        "defaultValue": "",
        "placeholder": "",
        "columnWidth": "200px",
        "size": "",
        "labelWidth": null,
        "labelHidden": false,
        "readonly": false,
        "disabled": false,
        "hidden": false,
        "clearable": true,
        "showPassword": false,
        "required": false,
        "requiredHint": "",
        "validation": "",
        "validationHint": "",
        "customClass": [],
        "labelIconClass": null,
        "labelIconPosition": "rear",
        "labelTooltip": null,
        "minLength": null,
        "maxLength": null,
        "showWordLimit": false,
        "prefixIcon": "",
        "suffixIcon": "",
        "appendButton": false,
        "appendButtonDisabled": false,
        "buttonIcon": "el-icon-search",
        "onCreated": "",
        "onMounted": "",
        "onInput": "",
        "onChange": "",
        "onFocus": "",
        "onBlur": "",
        "onValidate": ""
      },
      "id": "input99197"
    },
    {
      "type": "textarea",
      "icon": "textarea-field",
      "formItemFlag": true,
      "options": {
        "name": "textarea100585",
        "label": "简介",
        "labelAlign": "",
        "rows": 3,
        "defaultValue": "",
        "placeholder": "",
        "columnWidth": "200px",
        "size": "",
        "labelWidth": null,
        "labelHidden": false,
        "readonly": false,
        "disabled": false,
        "hidden": false,
        "required": false,
        "requiredHint": "",
        "validation": "",
        "validationHint": "",
        "customClass": "",
        "labelIconClass": null,
        "labelIconPosition": "rear",
        "labelTooltip": null,
        "minLength": null,
        "maxLength": null,
        "showWordLimit": false,
        "onCreated": "",
        "onMounted": "",
        "onInput": "",
        "onChange": "",
        "onFocus": "",
        "onBlur": "",
        "onValidate": ""
      },
      "id": "textarea100585"
    }
  ],
  "formConfig": {
    "modelName": "formData",
    "refName": "vForm",
    "rulesName": "rules",
    "labelWidth": 80,
    "labelPosition": "left",
    "size": "",
    "labelAlign": "label-left-align",
    "cssCode": "",
    "customClass": "",
    "functions": "",
    "layoutType": "PC",
    "onFormCreated": "",
    "onFormMounted": "",
    "onFormDataChange": "",
    "jsonVersion": 3,
    "onFormValidate": ""
  }
}' aria-readonly="true"/>
            </el-form-item>





          </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 查看基础单信息对话框 -->
        <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
            <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
              <el-form-item label="表单名称:" prop="formName">{{form.formName}}</el-form-item>
              <el-form-item label="表单json字符串:" prop="formJson">{{form.formJson}}</el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="表单对应实体库表名称:" prop="formEntityName">
                                    {{form.formEntityName}}
                                </el-form-item>
                                                                                                                                                                                                                                                                                                                                                                                                                                                    <el-form-item label="创建人:" prop="createdBy">
                                    {{form.createdBy}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="创建时间:" prop="createdTime">
                                    {{form.createdTime | filterDate}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="更新人:" prop="updatedBy">
                                    {{form.updatedBy}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="更新时间:" prop="updatedTime">
                                    {{form.updatedTime | filterDate}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="租户ID:" prop="tenantId">
                                    {{form.tenantId}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否:" prop="isSingleUser">
                                    {{form.isSingleUser | sys_yes_noFieldFilter(isSingleUserOptions)}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式:" prop="bindDataWay">
                                    {{form.bindDataWay | sys_yes_noFieldFilter(bindDataWayOptions)}}
                                </el-form-item>
                                                                                                                                                                                                                                                        <el-form-item label="API接口ID;API接口ID:" prop="apiInfo">
                                    {{form.apiInfo}}
                                </el-form-item>
                                                                                                    </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="isOpenQuery = false">关 闭</el-button>
            </div>
        </el-dialog>
        <!-- 基础单信息导入对话框 -->
        <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
            <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
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
    import { listInfo, getInfo, delInfo, addInfo, updateInfo, exportInfo,importTemplate, disableEnable } from "@/api/form/info";


    import ExpandCollapse from "@/components/ExpandCollapse/index";
    import {formatDate} from '@/utils/index';
    export default {
        name: "Info",
                                                                                                                                                                                                                                                                                    components: { ExpandCollapse },
        data() {
            return {
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
                    // 设置上传的请求头部
                    headers: { Authorization: "Bearer " + getToken() },
                    // 上传的地址
                    url: process.env.VUE_APP_BASE_API + "/form/info/importData",
                },
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            // 是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否字典
                            isSingleUserOptions: [],
                                                                                                                        // 数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式字典
                            bindDataWayOptions: [],
                                                                                                                                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    formName: null,
                    formJson: null,
                    formEntityName: null,
                    formStatus: null,
                    createdBy: null,
                    createdTime: null,
                    updatedBy: null,
                    updatedTime: null,
                    tenantId: null,
                    isSingleUser: null,
                    bindDataWay: null,
                                                                apiInfo: null
                                    },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
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
        created() {
            this.getList();
                                                                                                                                                                                                                                                                                                                                        this.getDicts("sys_yes_no").then(response => {
                        this.isSingleUserOptions = response.data || [];
                    });
                    this.getDicts("bindDataWay").then(response => {
                        this.bindDataWayOptions = response.data || [];
                    });
                                                                },
        methods: {
            /** 查询基础单信息列表 */
            getList() {
                this.loading = true;
                listInfo(this.queryParams).then(response => {
                    this.infoList = response.rows || [];
                   // this.total = response.total;

                    this.loading = false;
                     this.total = Number(response.total);
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
                this.form = {
                                                            formUuid: null,
                                                                                formName: null,
                                                                                formJson: null,
                                                                                formEntityName: null,
                                                                                formStatus: "0",
                                                                                createdBy: null,
                                                                                createdTime: null,
                                                                                updatedBy: null,
                                                                                updatedTime: null,
                                                                                tenantId: null,
                                                                                isSingleUser: null,
                                                                                bindDataWay: null,
                                                                                apiInfo: null
                                                };
                this.resetForm("form");
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
                this.disabled = false;
                this.reset();
                this.open = true;
                this.title = "添加基础单信息";
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.disabled = false;
                this.reset();
                const formUuid = row.formUuid || this.ids
                getInfo(formUuid).then(response => {
                    this.form = response.data;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    this.open = true;
                    this.title = "修改基础单信息";
                });
            },
            /**打开设计表单*/
            formAdd(row){
               const baseUrl = location.protocol+'//' + location.host+'/#/lowcodeFormDesign';
              window.open(baseUrl, '_blank');

            },
            /**详情按钮*/
            handleQueryOption(row) {
                // this.reset();
                const formUuid = row.formUuid || this.ids
                getInfo(formUuid).then(response => {
                    this.form = response.data;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    this.isOpenQuery = true;
                    this.title = "查看基础单信息";
                });
            },
            /**复制表单链接*/
            copyFormLink(row) {
                // this.reset();
                let flag= 1;
                if(row.bindDataWay=="NM_TABLE"){
                  flag="-10";//匿名
                }
                const formUuid = row.formUuid || this.ids
                const baseUrl = location.protocol+'//' + location.host+'/#/lowcodeForm?id='+formUuid+'&key=null&flag='+flag+'&listId=null';
                this.$copyText(baseUrl).then(
                          res => {
                             this.$message({message: '复制成功', type:'success'})//提示
                          },
                          err => {
                             this.$message({message: '复制失败', type:'success'})//提示
                          }
                        );

            },
            designReport(row){
             // const baseUrl = location.protocol+'//' + location.host;
              window.open("http://www.nangs.vip:8091/jmreport/index/"+row.formUuid);

            },
            designView(row){//设计大屏
           /* console.log(this.$store.getters.userInfo.userName);
            var username=this.$store.getters.userInfo.userName; */
            //apiInfo
            if(row.apiInfo==null || row.apiInfo=='undefined'){
               this.msgSuccess("请先配置大屏ID！");
               return;
            }
            window.open("http://www.nangs.vip:3389/#/view?id="+row.apiInfo);
             //window.open("http://192.168.8.186:3389/#/login?Admin-Token="+getToken()+"&username="+username);
             // window.open("http://www.nangs.vip:3389/login/"+row.formUuid);

            },
            /** 提交按钮 */
            submitForm() {
                if (this.disabled) {
                    this.open = false;
                    return
                }
                this.$refs["form"].validate(valid => {
                    if (valid) {
                       if (this.form.formUuid != null) {
                            updateInfo(this.form).then(response => {
                                if (response.code === 200) {
                                    this.msgSuccess("修改成功");
                                    this.open = false;
                                    this.getList();
                                }
                            });
                        } else {
                            addInfo(this.form).then(response => {
                                if (response.code === 200) {
                                    this.msgSuccess("新增成功");
                                    this.open = false;
                                    this.getList();
                                }
                            });
                        }
                    }
                });
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const formUuids = row.formUuid || this.ids;
                this.$confirm('是否确认删除基础单信息编号为"' + formUuids + '"的数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return delInfo(formUuids);
                }).then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                }).catch(function() {});
            },
            /** 禁用或启用操作*/
            disabledBtnClick(type) {
                var ids = this.ids || [];
                this.$confirm('是否确认'+ (type === 0 ? "启用" : "禁用")+'?', "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return disableEnable({ids: ids.toString(), disableEnableState: type});
                }).then(() => {
                    this.getList();
                this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
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
                    return exportInfo(queryParams);
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
                importTemplate().then((response) => {
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
