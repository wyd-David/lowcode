<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="员工ID" prop="empId">
        <el-input
          v-model="queryParams.empId"
          placeholder="请输入员工ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位名称" prop="positionName">
        <el-input
          v-model="queryParams.positionName"
          placeholder="请输入岗位名称"
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
          v-hasPermi="['system:position:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:position:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:position:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:position:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="positionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="从事何专业工作" align="center" prop="jobKind" />
      <el-table-column label="岗位名称" align="center" prop="positionName" />
      <el-table-column label="岗位ID" align="center" prop="positionId" />
      <el-table-column label="专业岗位起始时间" align="center" prop="positionFromDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.positionFromDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="专业岗位截止时间" align="center" prop="positionToDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.positionToDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="岗位部门名称" align="center" prop="deptName" />
      <el-table-column label="岗位职级" align="center" prop="positionLevelName" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:position:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:position:remove']"
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

    <!-- 添加或修改员工从事岗位子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="排序显示" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
        <el-form-item label="起始时间" prop="positionFromDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.positionFromDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择专业岗位起始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="截止时间" prop="positionToDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.positionToDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择专业岗位截止时间">
          </el-date-picker>
        </el-form-item>
        </el-col>
        <el-col :span="12">

        <el-form-item label="岗位部门名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入岗位部门名称" />
        </el-form-item>
        <el-form-item label="岗位职级" prop="positionLevelName">
          <el-input v-model="form.positionLevelName" placeholder="请输入岗位职级" />
        </el-form-item>
        <el-form-item label="岗位名称" prop="positionName">
          <el-input v-model="form.positionName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="岗位ID" prop="positionId">
          <el-input v-model="form.positionId" placeholder="请输入岗位ID" />
        </el-form-item>
         </el-col>
         <el-col :span="24">
        <el-form-item label="从事何专业工作" prop="jobKind">
          <el-input v-model="form.jobKind" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPosition, getPosition, delPosition, addPosition, updatePosition, exportPosition } from "@/api/system/position";

export default {
  name: "Position",
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
      // 员工从事岗位子集表格数据
      positionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        sortIndex: null,
        jobKind: null,
        positionName: null,
        positionId: null,
        positionFromDate: null,
        positionToDate: null,
        deptName: null,
        positionLevelName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
    /** 查询员工从事岗位子集列表 */
    getList() {
      this.loading = true;
      listPosition(this.queryParams).then(response => {
        this.positionList = response.rows;
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
        recId: null,
        empId: null,
        sortIndex: null,
        jobKind: null,
        positionName: null,
        positionId: null,
        positionFromDate: null,
        positionToDate: null,
        deptName: null,
        positionLevelName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.recId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加员工从事岗位子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getPosition(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工从事岗位子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updatePosition(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPosition(this.form).then(response => {
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
      const recIds = row.recId || this.ids;
      this.$confirm('是否确认删除员工从事岗位子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPosition(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工从事岗位子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPosition(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
