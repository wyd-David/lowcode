<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="流程名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实例ID" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入流程实例ID"
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
          v-hasPermi="['workflow:models:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdates"
          v-hasPermi="['workflow:models:edit']"
        >生成审批配置节点</el-button>
      </el-col>
    <!--  <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['workflow:models:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workflow:models:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modelsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="序列号" align="center" prop="rev" />-->
      <el-table-column label="流程名称" width="200px" align="center" prop="name" />
      <el-table-column label="流程实例ID" align="center" prop="pId" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="lastUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="版本" align="center" prop="version" />
      <el-table-column label="部署ID" align="center" prop="deploymentId" />
      <el-table-column label="EDITOR" align="center" prop="editorSourceValueId" />
      <el-table-column label="EDITOR_SORCE" align="center" prop="editorSourceExtraValueId" />
      <el-table-column label="操作" width="100px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workflow:models:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workflow:models:remove']"
          >发布</el-button>
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

    <!-- 添加或修改工作流模型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部署序列号" prop="rev">
          <el-input v-model="form.rev" placeholder="请输入序列号" />
        </el-form-item>
        <el-form-item label="流程名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="流程KEY" prop="key">
          <el-input v-model="form.key" placeholder="请输入流程KEY" />
        </el-form-item>
        <el-form-item label="备注" prop="category">
          <el-input v-model="form.category" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="修改时间" prop="lastUpdateTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.lastUpdateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择修改时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="版本" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本" />
        </el-form-item>
        <el-form-item label="元信息" prop="metaInfo">
          <el-input v-model="form.metaInfo" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="部署ID" prop="deploymentId">
          <el-input v-model="form.deploymentId" placeholder="请输入部署ID" />
        </el-form-item>
        <el-form-item label="EDITOR" prop="editorSourceValueId">
          <el-input v-model="form.editorSourceValueId" placeholder="请输入EDITOR" />
        </el-form-item>
        <el-form-item label="EDITOR_SORCE" prop="editorSourceExtraValueId">
          <el-input v-model="form.editorSourceExtraValueId" placeholder="请输入EDITOR_SORCE" />
        </el-form-item>
        <el-form-item label="租户ID" prop="tenantId">
          <el-input v-model="form.tenantId" placeholder="请输入租户ID" />
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
import { listModels, getModels, addModels, updateModels, exportModels } from "@/api/workflow/models";

export default {
  name: "Models",
  data() {
    return {
      ipPort: CONFIG.workflow_url,
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
      // 工作流模型表格数据
      modelsList: [],
      // 弹出层标题
      title: "",

      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rev: null,
        name: null,
        key: null,
        category: null,
        createTime: null,
        lastUpdateTime: null,
        version: null,
        metaInfo: null,
        deploymentId: null,
        editorSourceValueId: null,
        editorSourceExtraValueId: null,
        tenantId: null
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
    /** 查询工作流模型列表 */
    getList() {
      this.loading = true;
      listModels(this.queryParams).then(response => {
        this.modelsList = response.rows;
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
        id: null,
        rev: null,
        name: null,
        key: null,
        category: null,
        createTime: null,
        lastUpdateTime: null,
        version: null,
        metaInfo: null,
        deploymentId: null,
        editorSourceValueId: null,
        editorSourceExtraValueId: null,
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
      this.ids = selection.map(item => item.deploymentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      window.open(this.ipPort+"/create?name=activiti&key=yabushan_key");
      /* this.reset();
      this.open = true;
      this.title = "添加工作流模型"; */
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      window.open(this.ipPort+"/modeler?modelId="+row.id);
      /* this.reset();
      const id = row.id || this.ids
      getModels(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工作流模型";
      }); */
    },
    handleUpdates(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认生成工作流模型编号为"' + ids + '"的节点数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return getModels(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("生成成功，请在“节点配置中配置审批人”！");
        }).catch(function() {});





    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateModels(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addModels(this.form).then(response => {
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
    /** 发布按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认发布工作流模型编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          window.open(this.ipPort+"/models/"+ ids+"/deployment");
        }).then(() => {
          this.getList();
          this.msgSuccess("发布成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有工作流模型数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportModels(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
