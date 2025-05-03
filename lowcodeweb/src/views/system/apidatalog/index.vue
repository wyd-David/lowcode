<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="apiCode" prop="apiCode">
        <el-input
          v-model="queryParams.apiCode"
          placeholder="请输入apiCode"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="请求参数" prop="paramInfo">
        <el-input
          v-model="queryParams.paramInfo"
          placeholder="请输入请求参数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="resultCode">
        <el-input
          v-model="queryParams.resultCode"
          placeholder="请输入请求状态，成功，失败"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
    <el-form-item label="请求用户" prop="requestUser">
        <el-input
          v-model="queryParams.requestUser"
          placeholder="请输入请求用户"
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
   <!--   <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:apidatalog:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:apidatalog:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:apidatalog:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:apidatalog:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="apidatalogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="logId" />
      <el-table-column label="apiCode" align="center" prop="apiCode" />
      <el-table-column label="请求参数" align="center" prop="paramInfo" :show-overflow-tooltip="true" />
      <el-table-column label="返回结果" width="200px" align="center" prop="resultInfo"  :show-overflow-tooltip="true" />
      <el-table-column label="请求状态" align="center" prop="resultCode" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
      </el-table-column>
      <el-table-column label="请求用户" align="center" prop="requestUser" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:apidatalog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:apidatalog:remove']"
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

    <!-- 添加或修改api日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="apiCode" prop="apiCode">
          <el-input v-model="form.apiCode" placeholder="请输入apiCode" />
        </el-form-item>
        <el-form-item label="请求参数" prop="paramInfo">
          <el-input v-model="form.paramInfo" type="textarea" maxlength="200" show-word-limit placeholder="请输入请求参数" />
        </el-form-item>
        <el-form-item label="返回结果" prop="resultInfo">
          <el-input v-model="form.resultInfo" type="textarea" maxlength="200" show-word-limit placeholder="请输入返回结果" />
        </el-form-item>
        <el-form-item label="请求状态，成功，失败" prop="resultCode">
          <el-input v-model="form.resultCode" placeholder="请输入请求状态，成功，失败" />
        </el-form-item>
        <el-form-item label="请求用户" prop="requestUser">
          <el-input v-model="form.requestUser" placeholder="请输入请求用户" />
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
import { listApidatalog, getApidatalog, delApidatalog, addApidatalog, updateApidatalog, exportApidatalog } from "@/api/system/apidatalog";

export default {
  name: "Apidatalog",
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
      // api日志表格数据
      apidatalogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        apiCode: null,
        paramInfo: null,
        resultCode: null,
        createTime: null,
        requestUser: null
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
  },
  methods: {
    /** 查询api日志列表 */
    getList() {
      this.loading = true;
      listApidatalog(this.queryParams).then(response => {
        this.apidatalogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        logId: null,
        apiCode: null,
        paramInfo: null,
        resultInfo: null,
        resultCode: null,
        createTime: null,
        requestUser: null
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
      this.ids = selection.map(item => item.logId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加api日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids
      getApidatalog(logId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改api日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateApidatalog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addApidatalog(this.form).then(response => {
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
      const logIds = row.logId || this.ids;
      this.$confirm('是否确认删除api日志编号为"' + logIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delApidatalog(logIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出api日志数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportApidatalog(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
