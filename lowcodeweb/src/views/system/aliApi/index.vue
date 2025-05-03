<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="HOST" prop="hostInfo">
        <el-input
          v-model="queryParams.hostInfo"
          placeholder="HOST"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:aliApi:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:aliApi:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:aliApi:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:aliApi:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="aliApiList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="NG_KEY" align="center" prop="keyId" />
      <el-table-column label="HOST" align="center" prop="hostInfo" />
	 <el-table-column label="服务提供商" align="center" prop="serverWay" :formatter="serverWayFormat" />
	  <el-table-column label="备注" align="center" prop="memoInfo" :show-overflow-tooltip="true" />
	 <el-table-column label="剩余请求次数" align="center" prop="levelNum" />
	 <el-table-column label="总请求次数" align="center" prop="reqNum" />
      <el-table-column label="请求方式" align="center" prop="methodInfo" :formatter="methodInfoFormat" />
      <el-table-column label="PATH" align="center" prop="pathInfo" />
      <el-table-column label="appcode" align="center" prop="appCode" />
      <el-table-column label="CONTENT_TYPE" align="center" prop="contentType" :formatter="contentTypeFormat" />
      <el-table-column label="状态" align="center" prop="apiStatus" :formatter="apiStatusFormat" />
      <el-table-column label="更新人" align="center" prop="updatedBy" />
     
      <el-table-column label="是否被授权" align="center" prop="isGrant" :formatter="isGrantFormat" />
      <el-table-column label="使用方式" align="center" prop="userWay" :show-overflow-tooltip="true" />
      <el-table-column label="拥有者" align="center" prop="ownerName" />
	  <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
	    <template slot-scope="scope">
	      <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
	    </template>
	  </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:aliApi:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:aliApi:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改聚合数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="HOST信息" prop="hostInfo">
          <el-input v-model="form.hostInfo" placeholder="请输入HOST信息" />
        </el-form-item>
        <el-form-item label="请求方式" prop="methodInfo">
          <el-select v-model="form.methodInfo" placeholder="请选择请求方式,METHOD_INFO">
            <el-option
              v-for="dict in methodInfoOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="PATH" prop="pathInfo">
          <el-input v-model="form.pathInfo" placeholder="请输入PATH_INFO" />
        </el-form-item>
        <el-form-item label="appcode" prop="appCode">
          <el-input v-model="form.appCode" placeholder="请输入appcode" />
        </el-form-item>
        <el-form-item label="contentType" prop="contentType">
          <el-select v-model="form.contentType" placeholder="请选择CONTENT_TYPE">
            <el-option
              v-for="dict in contentTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="apiStatus">
          <el-select v-model="form.apiStatus" placeholder="请选择状态，Y：启用，N：停用">
            <el-option
              v-for="dict in apiStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="body" prop="returnBody">
          <el-select v-model="form.returnBody" placeholder="请选择是否只返回body,Y:是，N:不是">
            <el-option
              v-for="dict in returnBodyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="服务商" prop="serverWay">
          <el-select v-model="form.serverWay" placeholder="请选择服务提供商">
            <el-option
              v-for="dict in serverWayOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
		<el-form-item label="剩余次数" prop="levelNum">
		  <el-input v-model="form.levelNum" placeholder="请输入剩余请求次数" />
		</el-form-item>
        <el-form-item label="备注" prop="memoInfo">
          <el-input v-model="form.memoInfo" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="使用方式" prop="userWay">
          <el-input v-model="form.userWay" placeholder="请输入使用方式" />
        </el-form-item>
        
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAliApi, getAliApi, delAliApi, addAliApi, updateAliApi, exportAliApi } from "@/api/system/aliApi";

export default {
  name: "AliApi",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 聚合数据表格数据
      aliApiList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 请求方式,METHOD_INFO字典
      methodInfoOptions: [],
      // CONTENT_TYPE字典
      contentTypeOptions: [],
      // 状态，Y：启用，N：停用字典
      apiStatusOptions: [],
      // 是否只返回body,Y:是，N:不是字典
      returnBodyOptions: [],
      // 服务提供商字典
      serverWayOptions: [],
      // 是否被授权，Y:是，N：自己的字典
      isGrantOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hostInfo: null,
        methodInfo: null,
        pathInfo: null,
        appCode: null,
        contentType: null,
        createdBy: null,
        createdTime: null,
        apiStatus: null,
        updatedTime: null,
        updatedBy: null,
        returnBody: null,
        levelNum: null,
        serverWay: null,
        reqNum: null,
        memoInfo: null,
        isGrant: null,
        userWay: null,
        ownerName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("METHOD_INFO").then(response => {
      this.methodInfoOptions = response.data;
    });
    this.getDicts("CONTENT_TYPE").then(response => {
      this.contentTypeOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.apiStatusOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.returnBodyOptions = response.data;
    });
    this.getDicts("SERVER_WAY").then(response => {
      this.serverWayOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.isGrantOptions = response.data;
    });
  },
  methods: {
    /** 查询聚合数据列表 */
    getList() {
      this.loading = true;
      listAliApi(this.queryParams).then(response => {
        this.aliApiList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 请求方式,METHOD_INFO字典翻译
    methodInfoFormat(row, column) {
      return this.selectDictLabel(this.methodInfoOptions, row.methodInfo);
    },
    // CONTENT_TYPE字典翻译
    contentTypeFormat(row, column) {
      return this.selectDictLabel(this.contentTypeOptions, row.contentType);
    },
    // 状态，Y：启用，N：停用字典翻译
    apiStatusFormat(row, column) {
      return this.selectDictLabel(this.apiStatusOptions, row.apiStatus);
    },
    // 是否只返回body,Y:是，N:不是字典翻译
    returnBodyFormat(row, column) {
      return this.selectDictLabel(this.returnBodyOptions, row.returnBody);
    },
    // 服务提供商字典翻译
    serverWayFormat(row, column) {
      return this.selectDictLabel(this.serverWayOptions, row.serverWay);
    },
    // 是否被授权，Y:是，N：自己的字典翻译
    isGrantFormat(row, column) {
      return this.selectDictLabel(this.isGrantOptions, row.isGrant);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        keyId: null,
        hostInfo: null,
        methodInfo: null,
        pathInfo: null,
        appCode: null,
        contentType: null,
        createdBy: null,
        createdTime: null,
        apiStatus: null,
        updatedTime: null,
        updatedBy: null,
        returnBody: null,
        levelNum: null,
        serverWay: null,
        reqNum: null,
        memoInfo: null,
        isGrant: null,
        userWay: null,
        ownerName: null
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
      this.ids = selection.map(item => item.keyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加聚合数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const keyId = row.keyId || this.ids
      getAliApi(keyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改聚合数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.keyId != null) {
            updateAliApi(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAliApi(this.form).then(response => {
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
      const keyIds = row.keyId || this.ids;
      this.$confirm('是否确认删除聚合数据编号为"' + keyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAliApi(keyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出聚合数据数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAliApi(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
