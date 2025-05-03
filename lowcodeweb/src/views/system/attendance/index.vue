<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="老师姓名" prop="mangoUserIdName">
        <el-input
          v-model="queryParams.mangoUserIdName"
          placeholder="请输入老师姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级名称" prop="orgName">
        <el-input
          v-model="queryParams.orgName"
          placeholder="请输入班级名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="stId">
        <el-input
          v-model="queryParams.stId"
          placeholder="请输入学生姓名"
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
          v-hasPermi="['system:attendance:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:attendance:edit']"
        >修改</el-button>
      </el-col>
     <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:attendance:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:attendance:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attendanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="剩余课程节数" align="center" prop="atdId" /> -->
      <el-table-column label="班级名称" align="center" prop="orgName" />
      <el-table-column label="学生姓名" align="center" prop="stIdName" />
        <el-table-column label="微信端考勤老师" align="center" prop="mangoUserIdName" />
      <el-table-column label="PC端考勤老师" align="center" prop="secUserIdName" />
      <el-table-column label="微信端老师ID" align="center" prop="mangoUserId" />
      <!-- <el-table-column label="学生姓名" align="center" prop="stId" /> -->
      <el-table-column label="PC端老师ID" align="center" prop="secUserId" />
      <el-table-column label="使用课程节数" align="center" prop="useClassNum" />
      <el-table-column label="剩余课程节数" align="center" prop="leftClassNum" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
       
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:attendance:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:attendance:remove']"
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

    <!-- 添加或修改考勤记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="老师微信端ID" prop="mangoUserId">
          <el-input v-model="form.mangoUserId" placeholder="请输入老师微信端ID" />
        </el-form-item>
        <el-form-item label="学生ID" prop="stId">
          <el-input v-model="form.stId" placeholder="请输入学生ID" />
        </el-form-item>
        <el-form-item label="后台用户ID" prop="secUserId">
          <el-input v-model="form.secUserId" placeholder="请输入后台用户ID" />
        </el-form-item>
        <el-form-item label="使用课程节数" prop="useClassNum">
          <el-input v-model="form.useClassNum" placeholder="请输入使用课程节数" />
        </el-form-item>
        <el-form-item label="剩余课程节数" prop="leftClassNum">
          <el-input v-model="form.leftClassNum" placeholder="请输入剩余课程节数" />
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
import { listAttendance, getAttendance, delAttendance, addAttendance, updateAttendance, exportAttendance } from "@/api/system/attendance";

export default {
  name: "Attendance",
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
      // 考勤记录表格数据
      attendanceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mangoUserId: null,
        orgName: null,
        stId: null,
        secUserId: null,
        createTime: null,
        mangoUserName: null,
        useClassNum: null,
        leftClassNum: null
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
    /** 查询考勤记录列表 */
    getList() {
      this.loading = true;
      listAttendance(this.queryParams).then(response => {
        this.attendanceList = response.rows;
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
        atdId: null,
        mangoUserId: null,
        stId: null,
        secUserId: null,
        createTime: null,
        useClassNum: null,
        leftClassNum: null
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
      this.ids = selection.map(item => item.atdId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考勤记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const atdId = row.atdId || this.ids
      getAttendance(atdId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考勤记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.atdId != null) {
            updateAttendance(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAttendance(this.form).then(response => {
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
      const atdIds = row.atdId || this.ids;
      this.$confirm('是否确认删除考勤记录编号为"' + atdIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAttendance(atdIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出考勤记录数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAttendance(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
