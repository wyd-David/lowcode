<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="名称" prop="listName">
            <el-input v-model="queryParams.listName" placeholder="请输入业务列表名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="列表Id" prop="listId">
            <el-input v-model="queryParams.listId" placeholder="业务列表Id" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['list:list:add']">新增
          </el-button>
        </el-col>
      <!--  <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['list:list:edit']">修改</el-button>
        </el-col> -->
        <!--  <el-col :span="1.5">
                    <el-button
                            type="danger"
                            icon="el-icon-delete"
                            size="mini"
                            :disabled="multiple"
                            @click="handleDelete"
                            v-hasPermi="['list:list:remove']"
                    >删除</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-lock"
                            size="mini"
                            :disabled="multiple"
                            @click="disabledBtnClick(1)"
                            v-hasPermi="['list:list:remove']"
                    >禁用</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="primary"
                            icon="el-icon-unlock"
                            size="mini"
                            :disabled="multiple"
                            @click="disabledBtnClick(0)"
                            v-hasPermi="['list:list:remove']"
                    >启用</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="default"
                            icon="el-icon-upload"
                            size="mini"
                            @click="openImportTable"
                            v-hasPermi="['list:list:importData']"
                    >导入</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                            type="default"
                            icon="el-icon-download"
                            size="mini"
                            @click="handleExport"
                            v-hasPermi="['list:list:importData']"
                    >导出</el-button>
                </el-col> -->
        <!-- <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar> -->
      </el-row>

      <el-table v-loading="loading" :data="listList" @selection-change="handleSelectionChange">
      <!--  <el-table-column type="selection1" width="55" align="left" /> -->
        <el-table-column label="列表key" align="center"width="150"  prop="listId" :show-overflow-tooltip='true'/>
        <el-table-column label="名称" align="center" width="150"  prop="listName" :show-overflow-tooltip='true'/>
        <el-table-column label="表单key" align="center" width="150" prop="formUuid" :show-overflow-tooltip='true'/>
          <el-table-column label="流程key" align="center" width="150"  prop="listFlowProcdefId" :show-overflow-tooltip='true'/>
        <!-- <el-table-column label="编码" align="center" prop="listCode" /> -->
        <el-table-column label="查询按钮" align="center" prop="needQeuryBtn" :formatter="needQeuryBtnFormat" />
        <el-table-column label="导出按钮" align="center" prop="needExportBtn" :formatter="needExportBtnFormat" />
        <el-table-column label="导入按钮" align="center" prop="needInportBtn" :formatter="needInportBtnFormat" />
        <el-table-column label="修改按钮" align="center" prop="needUpdateBtn" :formatter="needUpdateBtnFormat" />
        <el-table-column label="删除按钮" align="center" prop="needDeleteBtn" :formatter="needDeleteBtnFormat" />
        <el-table-column label="查看按钮" align="center" prop="needViewBtn" :formatter="needViewBtnFormat" />

        <el-table-column label="创建人" align="center" prop="createdBy" width="180"/>
        <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!--  <el-table-column label="更新人" align="center" prop="updatedBy" />
                        <el-table-column label="更新时间" align="center" prop="updatedTime" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.updatedTime, '{y}-{m}-{d}') }}</span>
                            </template>
                        </el-table-column> -->
        <!-- <el-table-column label="租户号" align="center" prop="tenantId" /> -->
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180"  fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['list:list:edit']">修改
            </el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-hasPermi="['list:list:remove']">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改业务列对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="列表名称" prop="listName" required="">
          <el-input v-model="form.listName" placeholder="请输入业务列表名称" />
        </el-form-item>
        <!-- <el-form-item label="编码" prop="listCode">
          <el-input v-model="form.listCode" placeholder="请输入业务列表编码" />
        </el-form-item> -->
        <el-form-item label="表单配置主键" prop="formUuid" required="">
          <el-input v-model="form.formUuid" placeholder="请输入表单配置菜单中的主键" />
        </el-form-item>


        <!-- <el-form-item label="业务列表状态" prop="listStatus">
                        <el-select v-model="form.listStatus" placeholder="请选择业务列表状态">
                              <el-option
                                    v-for="dict in listStatusOptions"
                                    :key="dict.dictValue"
                                    :label="dict.dictLabel"
                                    :value="dict.dictValue"
                              ></el-option>
                        </el-select>
                </el-form-item> -->

      <!-- <el-form-item label="数据源" prop="dataResourceid">
          <el-input v-model="form.dataResourceid" placeholder="请输入数据源" />
        </el-form-item>-->
        <el-form-item label="报表数据API" prop="dataApi">
          <el-input v-model="form.dataApi" placeholder="请输入数据API编码逗号隔开,格式为:QUERY,ADD,UPDATE,DELETE" />
        </el-form-item>
        <el-form-item label="列表头展示字段" prop="listMemo" required="true">
          <el-input type="textarea" rows="30" v-model="form.listMemo"  placeholder="[{
                key: '1',
                label: '创建日期',
                prop: 'CREATED_BY',
                width: '150',
                headerAlign: 'center',
                align: 'center',
                scope: false,
                sortable: true
              },
              {
                key: '2',
                label: '创建时间',
                prop: 'CREATED_TIME',
                width: '150',
                headerAlign: 'center',
                align: 'center',
                scope: false,
                sortable: true
              },
              {
                key: '3',
                label: '生日时间',
                prop: 'birth_time',
                width: '150',
                headerAlign: 'center',
		align: 'right',
                scope: false,
                sortable: true

              },
              {
                key: '4',
                label: '用户介绍',
                prop: 'user_info',
                width: '150',
                headerAlign: 'center',
                align: 'right',
                scope: false,
                sortable: true
              },
              {
                key: '5',
                label: '用户名字',
                prop: 'user_name',
                width: '150',
                headerAlign: 'left',
                align: 'left',
                scope: false,
                sortable: false
              }
              ]" />
        </el-form-item>

        <el-form-item label="查询按钮" prop="needQeuryBtn" required="true">
          <el-select v-model="form.needQeuryBtn" placeholder="请选择是否需要查询按钮" >
            <el-option v-for="dict in needQeuryBtnOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="导出按钮" prop="needExportBtn" required="true">
          <el-select v-model="form.needExportBtn" placeholder="请选择是否需要导出按钮">
            <el-option v-for="dict in needExportBtnOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="导入按钮" prop="needInportBtn" required="true">
          <el-select v-model="form.needInportBtn" placeholder="请选择是否需要导入按钮">
            <el-option v-for="dict in needInportBtnOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="修改按钮" prop="needUpdateBtn" required="true">
          <el-select v-model="form.needUpdateBtn" placeholder="请选择是否需要修改按钮">
            <el-option v-for="dict in needUpdateBtnOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="删除按钮" prop="needDeleteBtn" required="true">
          <el-select v-model="form.needDeleteBtn" placeholder="请选择是否需要删除按钮">
            <el-option v-for="dict in needDeleteBtnOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="查看按钮" prop="needViewBtn" required="true">
          <el-select v-model="form.needViewBtn" placeholder="请选择是否需要查看按钮">
            <el-option v-for="dict in needViewBtnOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定流程Key" prop="listFlowProcdefId">
          <el-input v-model="form.listFlowProcdefId" placeholder="工作流实例key:bill_process" />
        </el-form-item>
        <el-form-item label="子列表头展示字段" prop="listMemoChild" >
              <el-input type="textarea" rows="30" v-model="form.listMemoChild"  placeholder="[{
                    key: '1',
                    label: '创建日期',
                    prop: 'CREATED_BY',
                    width: '150',
                    headerAlign: 'center',
                    align: 'center',
                    scope: false,
                    sortable: true
                  },
                  {
                    key: '2',
                    label: '创建时间',
                    prop: 'CREATED_TIME',
                    width: '150',
                    headerAlign: 'center',
                    align: 'center',
                    scope: false,
                    sortable: true
                  },
                  {
                    key: '3',
                    label: '生日时间',
                    prop: 'birth_time',
                    width: '150',
                    headerAlign: 'center',
        align: 'right',
                    scope: false,
                    sortable: true

                  },
                  {
                    key: '4',
                    label: '用户介绍',
                    prop: 'user_info',
                    width: '150',
                    headerAlign: 'center',
                    align: 'right',
                    scope: false,
                    sortable: true
                  },
                  {
                    key: '5',
                    label: '用户名字',
                    prop: 'user_name',
                    width: '150',
                    headerAlign: 'left',
                    align: 'left',
                    scope: false,
                    sortable: false
                  }
                  ]" />
            </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看业务列对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="业务列表名称:" prop="listName">
          {{form.listName}}
        </el-form-item>
        <el-form-item label="业务列表编码:" prop="listCode">
          {{form.listCode}}
        </el-form-item>
        <el-form-item label="是否需要查询按钮;1:是，0：否:" prop="needQeuryBtn">
          {{form.needQeuryBtn | sys_yes_noFieldFilter(needQeuryBtnOptions)}}
        </el-form-item>
        <el-form-item label="是否需要导出按钮;1:是，0：否:" prop="needExportBtn">
          {{form.needExportBtn | sys_yes_noFieldFilter(needExportBtnOptions)}}
        </el-form-item>
        <el-form-item label="是否需要导入按钮;1:是，0：否:" prop="needInportBtn">
          {{form.needInportBtn | sys_yes_noFieldFilter(needInportBtnOptions)}}
        </el-form-item>
        <el-form-item label="是否需要修改按钮;1:是，0：否:" prop="needUpdateBtn">
          {{form.needUpdateBtn | sys_yes_noFieldFilter(needUpdateBtnOptions)}}
        </el-form-item>
        <el-form-item label="是否需要删除按钮;1:是，0：否:" prop="needDeleteBtn">
          {{form.needDeleteBtn | sys_yes_noFieldFilter(needDeleteBtnOptions)}}
        </el-form-item>
        <el-form-item label="是否需要查看按钮;1:是，0：否:" prop="needViewBtn">
          {{form.needViewBtn | sys_yes_noFieldFilter(needViewBtnOptions)}}
        </el-form-item>
        <el-form-item label="绑定的流程ID:" prop="listFlowProcdefId">
          {{form.listFlowProcdefId}}
        </el-form-item>
        <el-form-item label="绑定的表单ID:" prop="formUuid">
          {{form.formUuid}}
        </el-form-item>
        <el-form-item label="业务列表状态:" prop="listStatus">
          {{form.listStatus | sys_yes_noFieldFilter(listStatusOptions)}}
        </el-form-item>
        <el-form-item label="表头:" prop="listMemo">
          {{form.listMemo}}
        </el-form-item>
        <el-form-item label="子表头:" prop="listMemoChild">
          {{form.listMemoChild}}
        </el-form-item>

        <el-form-item label="数据源:" prop="dataResourceid">
          {{form.dataResourceid}}
        </el-form-item>
        <el-form-item label="数据列表API:" prop="dataApi">
          {{form.dataApi}}
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
        <el-form-item label="租户号:" prop="tenantId">
          {{form.tenantId}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 业务列导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
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
  import {
    getToken
  } from "@/utils/auth";
  import {
    listList,
    getList,
    delList,
    addList,
    updateList,
    exportList,
    importTemplate,
    disableEnable
  } from "@/api/list/list";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "List",
    components: {
      ExpandCollapse
    },
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
        // 业务列表格数据
        listList: [],
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
          headers: {
            Authorization: "Bearer " + getToken()
          },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/list/list/importData",
        },
        // 是否需要查询按钮;1:是，0：否字典
        needQeuryBtnOptions: [],
        // 是否需要导出按钮;1:是，0：否字典
        needExportBtnOptions: [],
        // 是否需要导入按钮;1:是，0：否字典
        needInportBtnOptions: [],
        // 是否需要修改按钮;1:是，0：否字典
        needUpdateBtnOptions: [],
        // 是否需要删除按钮;1:是，0：否字典
        needDeleteBtnOptions: [],
        // 是否需要查看按钮;1:是，0：否字典
        needViewBtnOptions: [],
        // 业务列表状态字典
        listStatusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          listName: null,
          listCode: null,
          needQeuryBtn: null,
          needExportBtn: null,
          needInportBtn: null,
          needUpdateBtn: null,
          needDeleteBtn: null,
          needViewBtn: null,
          listFlowProcdefId: null,
          formUuid: null,
          listStatus: null,
          listMemo: null,
          listMemoChild: null,
          dataResourceid: null,
          dataApi: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          listId: null,
          updatedTime: null,
          tenantId: null
        },
        //示例数据
        preholder: [{key: '5',label: '用户名字',prop: 'user_name', width: '150',headerAlign: 'left',align: 'left',scope: false,sortable: false }],
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    filters: {
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
      sys_yes_noFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts("sys_yes_no").then(response => {
        this.needQeuryBtnOptions = response.data || [];
        this.needExportBtnOptions = response.data || [];
        this.needInportBtnOptions = response.data || [];
        this.needUpdateBtnOptions = response.data || [];
        this.needDeleteBtnOptions = response.data || [];
        this.needViewBtnOptions = response.data || [];
        this.listStatusOptions = response.data || [];
      });
     /* this.getDicts("sys_yes_no").then(response => {
        this.needExportBtnOptions = response.data || [];
      });
      this.getDicts("sys_yes_no").then(response => {
        this.needInportBtnOptions = response.data || [];
      });
      this.getDicts("sys_yes_no").then(response => {
        this.needUpdateBtnOptions = response.data || [];
      });
      this.getDicts("sys_yes_no").then(response => {
        this.needDeleteBtnOptions = response.data || [];
      });
      this.getDicts("sys_yes_no").then(response => {
        this.needViewBtnOptions = response.data || [];
      });
      this.getDicts("sys_yes_no").then(response => {
        this.listStatusOptions = response.data || [];
      }); */

    },
    methods: {
      /** 查询业务列列表 */
      getList() {
        this.loading = true;
        listList(this.queryParams).then(response => {
          this.listList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // 是否需要查询按钮;1:是，0：否字典翻译
      needQeuryBtnFormat(row, column) {
        return this.selectDictLabel(this.needQeuryBtnOptions, row.needQeuryBtn);
      },
      // 是否需要导出按钮;1:是，0：否字典翻译
      needExportBtnFormat(row, column) {
        return this.selectDictLabel(this.needExportBtnOptions, row.needExportBtn);
      },
      // 是否需要导入按钮;1:是，0：否字典翻译
      needInportBtnFormat(row, column) {
        return this.selectDictLabel(this.needInportBtnOptions, row.needInportBtn);
      },
      // 是否需要修改按钮;1:是，0：否字典翻译
      needUpdateBtnFormat(row, column) {
        return this.selectDictLabel(this.needUpdateBtnOptions, row.needUpdateBtn);
      },
      // 是否需要删除按钮;1:是，0：否字典翻译
      needDeleteBtnFormat(row, column) {
        return this.selectDictLabel(this.needDeleteBtnOptions, row.needDeleteBtn);
      },
      // 是否需要查看按钮;1:是，0：否字典翻译
      needViewBtnFormat(row, column) {
        return this.selectDictLabel(this.needViewBtnOptions, row.needViewBtn);
      },
      // 业务列表状态字典翻译
      listStatusFormat(row, column) {
        return this.selectDictLabel(this.listStatusOptions, row.listStatus);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          listId: null,
          listName: null,
          listCode: null,
          needQeuryBtn: null,
          needExportBtn: null,
          needInportBtn: null,
          needUpdateBtn: null,
          needDeleteBtn: null,
          needViewBtn: null,
          listFlowProcdefId: null,
          formUuid: null,
          listStatus: null,
          listMemo: null,
          dataResourceid: null,
          dataApi: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          tenantId: null
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
        this.ids = selection.map(item => item.listId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加业务列表";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const listId = row.listId || this.ids
        getList(listId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改业务列表";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const listId = row.listId || this.ids
        getList(listId).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看业务列";
        });
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.listId != null) {
              updateList(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addList(this.form).then(response => {
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
        const listIds = row.listId || this.ids;
        this.$confirm('是否确认删除业务列编号为"' + listIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delList(listIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || [];
        this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return disableEnable({
            ids: ids.toString(),
            disableEnableState: type
          });
        }).then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有业务列数据项?', "警告", {
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
        this.upload.title = "业务列数据导入";
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
        this.$alert(response.msg, "导入结果", {
          dangerouslyUseHTMLString: true
        });
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
  .app-container {
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

  .utlsBtn {
    /deep/ .el-button--mini {
      padding: 9px 15px;
    }

    .normalBtn {
      /deep/ .el-button--mini {
        padding: 8px;
      }
    }
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

  /deep/ .el-form-item__label {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
  }
</style>
