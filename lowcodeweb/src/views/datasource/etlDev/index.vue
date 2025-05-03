<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="作业名称" prop="collectName">
            <el-input v-model="queryParams.collectName" placeholder="请输入作业名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="采集引擎Id" prop="collectResouceId">
            <el-input v-model="queryParams.collectResouceId" placeholder="请输入被采集引擎" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="采集表" prop="collectTableName">
            <el-input v-model="queryParams.collectTableName" placeholder="请输入被采集表" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="入库引擎" prop="collectToDatasourceid">
            <el-input v-model="queryParams.collectToDatasourceid" placeholder="请输入入库到的数据源ID" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="入库表" prop="collectToTableName">
            <el-input v-model="queryParams.collectToTableName" placeholder="请输入入库到的表名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="作业类型" prop="collectType">
            <el-select v-model="queryParams.collectType" placeholder="请选择作业类型" clearable size="small">
              <el-option v-for="dict in collectTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否增量" prop="collectAdd">
            <el-select v-model="queryParams.collectAdd" placeholder="请选择" clearable size="small">
              <el-option v-for="dict in collectAddOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="最后运行时间" prop="lastUpdateTime">
            <el-date-picker clearable size="small" style="width: 200px" v-model="queryParams.lastUpdateTime" type="date"
              value-format="yyyy-MM-dd" placeholder="选择最后运行时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="运行状态" prop="lastStatus">
            <el-select v-model="queryParams.lastStatus" placeholder="请选择"
              clearable size="small">
              <el-option v-for="dict in lastStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="读取服务" prop="apiCodeIn">
            <el-input v-model="queryParams.apiCodeIn" placeholder="请输入读取节点的api_code" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <!--<el-form-item label="写入服务" prop="apiCodeOut">
            <el-input v-model="queryParams.apiCodeOut" placeholder="请输入写入节点的api_code" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item> -->
          <el-form-item label="最后执行结果" prop="collectResult">
            <el-input v-model="queryParams.collectResult" placeholder="请输入最后执行结果" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
		    <el-form-item label="运行节点" prop="runNodeId">
            <el-input v-model="queryParams.runNodeId" placeholder="请输入运行节点" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>

    </expand-collapse>
    </el-form>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['datasource:etlDev:add']">新增</el-button>
        </el-col>
       <!-- <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['datasource:etlDev:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['datasource:etlDev:remove']">删除</el-button>
        </el-col> -->
      <!--  <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-lock" size="mini" :disabled="multiple" @click="disabledBtnClick(1)"
            v-hasPermi="['datasource:etlDev:remove']">停用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-unlock" size="mini" :disabled="multiple" @click="disabledBtnClick(0)"
            v-hasPermi="['datasource:etlDev:remove']">启用</el-button>
        </el-col> -->
       <!-- <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['datasource:etlDev:importData']">导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['datasource:etlDev:importData']">导出</el-button>
        </el-col> -->
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="etlDevList" @selection-change="handleSelectionChange">
       <!-- <el-table-column type="selection" width="55" align="left" /> -->
        <el-table-column label="主键ID" align="center" prop="collectId" width="170"/>
        <el-table-column label="作业名称" align="center" prop="collectName" />
        <el-table-column label="采集引擎" align="center" prop="collectResouceId" />
        <el-table-column label="采集表" align="center" prop="collectTableName" />
        <el-table-column label="入库引擎" align="center" prop="collectToDatasourceid" />
        <el-table-column label="入库表" align="center" prop="collectToTableName" />
        <el-table-column label="作业类型" align="center" prop="collectType" :formatter="collectTypeFormat" />
        <el-table-column label="增量" align="center" prop="collectAdd" :formatter="collectAddFormat" />
        <el-table-column label="增量字段" align="center" prop="fieldName" />
        <el-table-column label="增量值" align="center" prop="fieldValue" />
        <el-table-column label="字段类型" align="center" prop="fieldType" :formatter="fieldTypeFormat" />

        <el-table-column label="执行状态" align="center" prop="lastStatus"
          :formatter="lastStatusFormat" />
        <el-table-column label="读取服务" align="center" prop="apiCodeIn" />
     <!--   <el-table-column label="写入服务" align="center" prop="apiCodeOut" /> -->
        <el-table-column label="最后运行时间" align="center" prop="lastUpdateTime" width="180">
        </el-table-column>
       <!-- <el-table-column label="最后执行结果" align="center" prop="collectResult" /> -->
        <el-table-column label="运行节点" align="center" prop="runNodeId" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
			  <el-button size="mini" type="text" @click="etlRun(scope.row)" v-hasPermi="['datasource:etlDev:edit']">
			    立即运行</el-button>
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['datasource:etlDev:edit']">
              修改</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
              v-hasPermi="['datasource:etlDev:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改作业开发对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px"  append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="作业名称" prop="collectName" required="required" >
          <el-input v-model="form.collectName" placeholder="请输入采集作业名称"  />
        </el-form-item>
        <el-form-item label="采集引擎" prop="collectResouceId" required="required" >
          <el-input v-model="form.collectResouceId" placeholder="请输入采集表Id" />
        </el-form-item>
		<el-form-item label="采集表" prop="collectTableName" required="required" >
		  <el-input v-model="form.collectTableName" placeholder="请输入采集表" />
		</el-form-item>
        <el-form-item label="入库引擎" prop="collectToDatasourceid"  required="required">
          <el-input v-model="form.collectToDatasourceid" placeholder="入库到数据源引擎ID" />
        </el-form-item>
        <el-form-item label="入库表" prop="collectToTableName" required="required">
          <el-input v-model="form.collectToTableName" placeholder="请输入入库表" />
        </el-form-item>
        <el-form-item label="作业类型" prop="collectType">
          <el-select v-model="form.collectType" placeholder="请选择作业类型">
            <el-option v-for="dict in collectTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="抽取读取服务" prop="apiCodeIn">
          <el-input v-model="form.apiCodeIn" placeholder="请输入抽取时读取节点的服务编码" />
        </el-form-item>
        <el-form-item label="是否增量" prop="collectAdd" required="required">
          <el-select v-model="form.collectAdd" placeholder="请选择">
            <el-option v-for="dict in collectAddOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="增量字段" prop="fieldName">
          <el-input v-model="form.fieldName" placeholder="请输入增量字段" />
        </el-form-item>
        <el-form-item label="增量值" prop="fieldValue">
          <el-input v-model="form.fieldValue" placeholder="请输入增量字段" />
        </el-form-item>
        <el-form-item label="字段类型" prop="fieldType">
          <el-select v-model="form.fieldType" placeholder="请选择字段类型">
            <el-option v-for="dict in fieldTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="写入前执行语句" prop="beforeSql">
           <template slot="label">
              <span>抽取前执行语句
                <el-tooltip class="item"
                            effect="dark"
                            content="日期变量参数：
          @{yyyy}    当前年份
          @{MM}  当前月份
          @{dd} 当前日期天
          @{yyyy-MM-dd}    当前年月日
          @{yyyy-MM-dd HH:mm:ss}     当前年月日时分秒"
                            placement="left">
                  <i class="el-icon-question"
                     style="font-size: 16px; vertical-align: middle;"></i>
                </el-tooltip>
              </span>
            </template>
          <el-input v-model="form.beforeSql" rows="7" type="textarea"  placeholder="请输入sql表达式" />
        </el-form-item>

        <el-form-item label="" prop="afterSql">
           <template slot="label">
              <span>抽取后执行sql
                <el-tooltip class="item"
                            effect="dark"
                            content="日期变量参数：
          @{yyyy}    当前年份
          @{MM}  当前月份
          @{dd} 当前日期天
          @{yyyy-MM-dd}    当前年月日
          @{yyyy-MM-dd HH:mm:ss}     当前年月日时分秒"
                            placement="left">
                  <i class="el-icon-question"
                     style="font-size: 16px; vertical-align: middle;"></i>
                </el-tooltip>
              </span>
            </template>
          <el-input v-model="form.afterSql" rows="7" type="textarea"  placeholder="请输入sql表达式" />
        </el-form-item>


       <!-- <el-form-item label="抽取写入服务" prop="apiCodeOut">
          <el-input v-model="form.apiCodeOut" placeholder="请输入抽取时输出节点的服务编码" />
        </el-form-item> -->
        <el-form-item label="分布式运行节点" prop="runNodeId">
          <el-input v-model="form.runNodeId" placeholder="请输入运行节点"  />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看作业开发对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="作业名称:" prop="collectName">
          {{form.collectName}}
        </el-form-item>
        <el-form-item label="采集表Id:" prop="collectResouceId">
          {{form.collectResouceId}}
        </el-form-item>
        <el-form-item label="采集表:" prop="collectTableName">
          {{form.collectTableName}}
        </el-form-item>
        <el-form-item label="入库引擎:" prop="collectToDatasourceid">
          {{form.collectToDatasourceid}}
        </el-form-item>
        <el-form-item label="入库表:" prop="collectToTableName">
          {{form.collectToTableName}}
        </el-form-item>
        <el-form-item label="作业类型" prop="collectType">
          {{form.collectType | ETL_TYPEFieldFilter(collectTypeOptions)}}
        </el-form-item>
        <el-form-item label="是否增量" prop="collectAdd">
          {{form.collectAdd | FieldFilter(collectAddOptions)}}
        </el-form-item>
        <el-form-item label="增量字段:" prop="fieldName">
          {{form.fieldName}}
        </el-form-item>
        <el-form-item label="增量值:" prop="fieldValue">
          {{form.fieldValue}}
        </el-form-item>
        <el-form-item label="字段类型:" prop="fieldType">
          {{form.fieldType | FILED_TYPEFieldFilter(fieldTypeOptions)}}
        </el-form-item>
        <el-form-item label="最后运行时间:" prop="lastUpdateTime">
          {{form.lastUpdateTime | filterDate}}
        </el-form-item>
        <el-form-item label="运行状态:">
          {{form.lastStatus | etl_run_statusFieldFilter(lastStatusOptions)}}
        </el-form-item>
        <el-form-item label="读取节点的api_code:" prop="apiCodeIn">
          {{form.apiCodeIn}}
        </el-form-item>
        <el-form-item label="写入节点的api_code:" prop="apiCodeOut">
          {{form.apiCodeOut}}
        </el-form-item>
        <el-form-item label="最后执行结果:" prop="collectResult">
          {{form.collectResult}}
        </el-form-item>
        <el-form-item label="分布式运行节点:" prop="runNodeId">
          {{form.runNodeId}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 作业开发导入对话框 -->
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
    listEtlDev,
    getEtlDev,
    delEtlDev,
    addEtlDev,
    updateEtlDev,
    exportEtlDev,
    importTemplate,
    disableEnable,
    etlRun
  } from "@/api/datasource/etlDev";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "EtlDev",
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
        // 作业开发表格数据
        etlDevList: [],
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
          url: process.env.VUE_APP_BASE_API + "/datasource/etlDev/importData",
        },
        // 作业类型，1:采集，2：抽取字典
        collectTypeOptions: [],
        // 是否增量,1:是,2:否字典
        collectAddOptions: [],
        // 字段类型字典
        fieldTypeOptions: [],
        // 最后运行状态0:初始，1:运行中,2:成功,3:失败，4:等待运行,5:依赖条件未发生字典
        lastStatusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          collectName: null,
          collectResouceId: null,
          collectTableName: null,
          collectToDatasourceid: null,
          collectToTableName: null,
          collectType: null,
          collectAdd: null,
          fieldName: null,
          fieldValue: null,
          fieldType: null,
          lastUpdateTime: null,
          lastStatus: null,
          apiCodeIn: null,
          apiCodeOut: null,
          collectResult: null,
          runNodeId: null
        },
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
      ETL_TYPEFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
      FieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
      FILED_TYPEFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
      etl_run_statusFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts("ETL_TYPE").then(response => {
        this.collectTypeOptions = response.data || [];
      });
      this.getDicts("36").then(response => {
        this.collectAddOptions = response.data || [];
      });
      this.getDicts("FILED_TYPE").then(response => {
        this.fieldTypeOptions = response.data || [];
      });
      this.getDicts("etl_run_status").then(response => {
        this.lastStatusOptions = response.data || [];
      });
    },
    methods: {
      /** 查询作业开发列表 */
      getList() {
        this.loading = true;
        listEtlDev(this.queryParams).then(response => {
          this.etlDevList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // 作业类型，1:采集，2：抽取字典翻译
      collectTypeFormat(row, column) {
        return this.selectDictLabel(this.collectTypeOptions, row.collectType);
      },
      // 是否增量,1:是,2:否字典翻译
      collectAddFormat(row, column) {
        return this.selectDictLabel(this.collectAddOptions, row.collectAdd);
      },
      // 字段类型字典翻译
      fieldTypeFormat(row, column) {
        return this.selectDictLabel(this.fieldTypeOptions, row.fieldType);
      },
      // 最后运行状态0:初始，1:运行中,2:成功,3:失败，4:等待运行,5:依赖条件未发生字典翻译
      lastStatusFormat(row, column) {
        return this.selectDictLabel(this.lastStatusOptions, row.lastStatus);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          collectId: null,
          collectName: null,
          collectResouceId: null,
          collectTableName: null,
          collectToDatasourceid: null,
          collectToTableName: null,
          createBy: null,
          createTime: null,
          collectType: null,
          collectAdd: null,
          fieldName: null,
          fieldValue: null,
          fieldType: null,
          lastUpdateTime: null,
          lastStatus: "0",
          apiCodeIn: null,
          apiCodeOut: null,
          collectResult: null,
          runNodeId: null
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
        this.ids = selection.map(item => item.collectId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加作业";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const collectId = row.collectId || this.ids
        getEtlDev(collectId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改作业";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const collectId = row.collectId || this.ids
        getEtlDev(collectId).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看作业";
        });
      },
	  /**立即运行*/
	  etlRun(row) {
      const collectId = row.collectId;
      this.$confirm('是否确认立即运行作业编号为"' + row.collectName + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return etlRun(collectId);
      }).then(() => {
        this.getList();
        this.msgSuccess("运行成功");
      }).catch(function() {});
	  /*  const collectId = row.collectId
	    etlRun(collectId).then(response => {
	        this.msgSuccess(response);
	    }); */
	  },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.collectId != null) {
              updateEtlDev(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addEtlDev(this.form).then(response => {
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
        const collectIds = row.collectId || this.ids;
        this.$confirm('是否确认删除作业编号为"' + collectIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delEtlDev(collectIds);
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
        this.$confirm('是否确认导出所有作业数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportEtlDev(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "作业数据导入";
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
