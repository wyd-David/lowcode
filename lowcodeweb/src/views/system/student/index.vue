<template>
  <div class="app-container">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
        </div>
        <div class="head-container">
          <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" default-expand-all @node-click="handleNodeClick" />
        </div>
      </el-col>

     <el-col :span="20" :xs="24">

       <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
         <el-form-item label="学生姓名" prop="stName">
           <el-input
             v-model="queryParams.stName"
             placeholder="请输入学生姓名"
             clearable
             size="small"
             @keyup.enter.native="handleQuery"
           />
         </el-form-item>
         <el-form-item label="学生手机" prop="stMobile">
           <el-input
             v-model="queryParams.stMobile"
             placeholder="请输入学生手机"
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
             v-hasPermi="['system:student:add']"
           >新增</el-button>
         </el-col>
         <el-col :span="1.5">
           <el-button
             type="success"
             icon="el-icon-edit"
             size="mini"
             :disabled="single"
             @click="handleUpdate"
             v-hasPermi="['system:student:edit']"
           >修改</el-button>
         </el-col>
         <el-col :span="1.5">
           <el-button
             type="danger"
             icon="el-icon-delete"
             size="mini"
             :disabled="multiple"
             @click="handleDelete"
             v-hasPermi="['system:student:remove']"
           >删除</el-button>
         </el-col>
         <el-col :span="1.5">
           <el-button
             type="warning"
             icon="el-icon-download"
             size="mini"
             @click="handleExport"
             v-hasPermi="['system:student:export']"
           >导出</el-button>
         </el-col>
       <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
       </el-row>

       <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
         <el-table-column type="selection" width="55" align="center" />
         <!-- <el-table-column label="主键" align="center" prop="stId" /> -->
         <el-table-column label="学生姓名" align="center" prop="stName" />
         <el-table-column label="学生手机" align="center" prop="stMobile" />
         <el-table-column label="所在班级" align="center" prop="orgName" />
         <el-table-column label="课程总数" align="center" prop="stAllNum" />
         <el-table-column label="剩余课程数" align="center" prop="stClassNum" />
         <el-table-column label="备注" align="center" prop="stMemo" />
         <el-table-column label="创建时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
             <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
           </template>
         </el-table-column>
         <el-table-column label="创建人" align="center" prop="createBy" />
         <el-table-column label="更新人" align="center" prop="updateBy" />
         <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
           <template slot-scope="scope">
             <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
           </template>
         </el-table-column>
         <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
           <template slot-scope="scope">
             <el-button
               size="mini"
               type="text"
               icon="el-icon-edit"
               @click="handleUpdate(scope.row)"
               v-hasPermi="['system:student:edit']"
             >修改</el-button>
             <el-button
               size="mini"
               type="text"
               icon="el-icon-delete"
               @click="handleDelete(scope.row)"
               v-hasPermi="['system:student:remove']"
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
     </el-col>


    <!-- 添加或修改学生对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="归属班级" prop="stOrgId">
          <treeselect v-model="form.stOrgId" :options="deptOptions" :show-count="true" placeholder="请选择班级" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="stName">
          <el-input v-model="form.stName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="stMobile">
          <el-input v-model="form.stMobile" placeholder="请输入手机号" :maxlength="11" minlength="11" show-word-limit clearable
            prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
        </el-form-item>


        <el-form-item label="初始课程数" prop="stAllNum">
           <el-input-number v-model="form.stAllNum" placeholder="初始课程数"></el-input-number>
        </el-form-item>
        <el-form-item label="剩余课程数" prop="stClassNum">
          <el-input-number v-model="form.stClassNum" placeholder="请输入剩余课程数"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" prop="stMemo">
          <el-input v-model="form.stMemo" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
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
import { listStudent, getStudent, delStudent, addStudent, updateStudent, exportStudent } from "@/api/system/student";
import { treeselectOrg } from "@/api/system/org";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Student",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 部门树选项
      deptOptions: undefined,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 学生表格数据
      studentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stName: null,
        stMobile: null,
        stOrgId: null,
        stClassNum: null,
        stAllNum: null,
        stMemo: null,
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mobile: [{
                  required: true,
                  message: '请输入手机号',
                  trigger: 'blur'
                }, {
                  pattern: /^1(3|4|5|6|7|8|9)\d{9}$/,
                  message: '手机号格式错误',
                  trigger: 'blur'
                }],
                field101: [{
                  required: true,
                  message: '计数器',
                  trigger: 'blur'
                }],

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学生列表 */
    getList() {
      this.loading = true;
      listStudent(this.queryParams).then(response => {
        this.studentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.getTreeselect();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        stId: null,
        stName: null,
        stMobile: null,
        stOrgId: null,
        stClassNum: null,
        stAllNum: null,
        stMemo: null,
        createTime: null,
        createBy: null,
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
      this.ids = selection.map(item => item.stId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselectOrg().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.stOrgId = data.id;
      this.getList();
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学生";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const stId = row.stId || this.ids
      getStudent(stId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.stId != null) {
            updateStudent(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addStudent(this.form).then(response => {
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
      const stIds = row.stId || this.ids;
      this.$confirm('是否确认删除学生编号为"' + stIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delStudent(stIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出学生数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportStudent(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
