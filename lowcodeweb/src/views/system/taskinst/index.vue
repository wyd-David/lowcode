<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程ID" prop="procDefId">
        <el-input
          v-model="queryParams.procDefId"
          placeholder="请输入流程部署ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务ID" prop="taskDefKey">
        <el-input
          v-model="queryParams.taskDefKey"
          placeholder="请输入任务节点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程实例" prop="procInstId">
        <el-input
          v-model="queryParams.procInstId"
          placeholder="请输入流程实例ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="流程名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理人" prop="assignee">
        <el-input
          v-model="queryParams.assignee"
          placeholder="请输入处理人"
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
          v-hasPermi="['system:taskinst:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:taskinst:edit']"
        >修改</el-button>
      </el-col>
     <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:taskinst:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:taskinst:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskinstList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务ID" align="center" prop="id" />
      <el-table-column label="流程部署ID" width="200px" align="center" prop="procDefId" />
      <el-table-column label="流程实例" align="center" prop="procInstId" />
      <el-table-column label="执行器" align="center" prop="executionId" />
      <el-table-column label="流程名称" align="center" prop="name" />
      <el-table-column label="处理人" align="center" prop="assignee" />
      <el-table-column label="完成状态" width="100px" align="center" prop="deleteReason" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="签收时间" align="center" prop="claimTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.claimTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:taskinst:edit']"
          >修改</el-button>
        <!--  <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:taskinst:remove']"
          >删除</el-button> -->
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

    <!-- 添加或修改流程列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流程部署ID" prop="procDefId">
          <el-input v-model="form.procDefId" placeholder="请输入流程部署ID" />
        </el-form-item>
        <el-form-item label="任务节点" prop="taskDefKey">
          <el-input v-model="form.taskDefKey" placeholder="请输入任务节点" />
        </el-form-item>
        <el-form-item label="流程实例" prop="procInstId">
          <el-input v-model="form.procInstId" placeholder="请输入流程实例" />
        </el-form-item>
        <el-form-item label="执行器" prop="executionId">
          <el-input v-model="form.executionId" placeholder="请输入执行器" />
        </el-form-item>
        <el-form-item label="流程名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="处理人" prop="assignee">
          <el-input v-model="form.assignee" placeholder="请输入处理人" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="签收时间" prop="claimTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.claimTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择签收时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="耗时" prop="duration">
          <el-input v-model="form.duration" placeholder="请输入耗时" />
        </el-form-item>
        <el-form-item label="完成状态" prop="deleteReason">
          <el-input v-model="form.deleteReason" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
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
import { listTaskinst, getTaskinst, delTaskinst, addTaskinst, updateTaskinst, exportTaskinst } from "@/api/system/taskinst";

export default {
  name: "Taskinst",
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
      // 流程列表表格数据
      taskinstList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        procDefId: null,
        taskDefKey: null,
        procInstId: null,
        executionId: null,
        name: null,
        parentTaskId: null,
        description: null,
        owner: null,
        assignee: null,
        startTime: null,
        claimTime: null,
        endTime: null,
        duration: null,
        deleteReason: null,
        priority: null,
        dueDate: null,
        formKey: null,
        category: null,
        tenantId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程列表列表 */
    getList() {
      this.loading = true;
      listTaskinst(this.queryParams).then(response => {
        this.taskinstList = response.rows;
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
        procDefId: null,
        taskDefKey: null,
        procInstId: null,
        executionId: null,
        name: null,
        parentTaskId: null,
        description: null,
        owner: null,
        assignee: null,
        startTime: null,
        claimTime: null,
        endTime: null,
        duration: null,
        deleteReason: null,
        priority: null,
        dueDate: null,
        formKey: null,
        category: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTaskinst(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaskinst(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTaskinst(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除流程列表编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTaskinst(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出流程列表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTaskinst(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
