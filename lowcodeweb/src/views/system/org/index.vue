<template>
  <div class="app-container">
   <!--部门数据-->
   <el-col :span="4" :xs="24">
     <div class="head-container">
     </div>
     <div class="head-container">
       <el-tree :data="deptOptions"  :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" default-expand-all @node-click="handleNodeClick" />
     </div>
   </el-col>
   <el-col :span="20" :xs="24">
     <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
       <el-form-item label="组织名称" prop="orgName">
         <el-input
           v-model="queryParams.orgName"
           placeholder="请输入组织名称"
           clearable
           size="small"
           @keyup.enter.native="handleQuery"
         />
       </el-form-item>
       <el-form-item label="已结课" prop="ortStatus">
         <el-select v-model="queryParams.ortStatus" placeholder="请选择是否已结课" clearable size="small">
           <el-option
             v-for="dict in ortStatusOptions"
             :key="dict.dictValue"
             :label="dict.dictLabel"
             :value="dict.dictValue"
           />
         </el-select>
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
           v-hasPermi="['system:org:add']"
         >新增</el-button>
       </el-col>
       <el-col :span="1.5">
         <el-button
           type="success"
           icon="el-icon-edit"
           size="mini"
           :disabled="single"
           @click="handleUpdate"
           v-hasPermi="['system:org:edit']"
         >修改</el-button>
       </el-col>
       <el-col :span="1.5">
         <el-button
           type="danger"
           icon="el-icon-delete"
           size="mini"
           :disabled="multiple"
           @click="handleDelete"
           v-hasPermi="['system:org:remove']"
         >删除</el-button>
       </el-col>
       <el-col :span="1.5">
         <el-button
           type="warning"
           icon="el-icon-download"
           size="mini"
           @click="handleExport"
           v-hasPermi="['system:org:export']"
         >导出</el-button>
       </el-col>
     <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
     </el-row>
     
     <el-table v-loading="loading" :data="orgList" @selection-change="handleSelectionChange">
       <el-table-column type="selection" width="55" align="center" />
     <!--  <el-table-column label="组织ID" align="center" prop="orgId" /> -->
       <el-table-column label="组织名称" align="center" prop="orgName" />
      <!-- <el-table-column label="父组织id" align="center" prop="parentId" /> -->
       <el-table-column label="是否已结课" align="center" prop="ortStatus" :formatter="ortStatusFormat" />
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
             v-hasPermi="['system:org:edit']"
           >修改</el-button>
           <el-button
             size="mini"
             type="text"
             icon="el-icon-delete"
             @click="handleDelete(scope.row)"
             v-hasPermi="['system:org:remove']"
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
  
    <!-- 添加或修改班级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="归属班级" prop="parentId">
          <treeselect v-model="form.parentId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
        </el-form-item>
        <el-form-item label="组织名称" prop="orgName">
          <el-input v-model="form.orgName" placeholder="请输入组织名称" />
        </el-form-item>
        <el-form-item label="是否结课" prop="ortStatus">
          <el-select v-model="form.ortStatus" placeholder="请选择是否已结课">
            <el-option
              v-for="dict in ortStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import { listOrg, getOrg, delOrg, addOrg, updateOrg, exportOrg,treeselectOrg } from "@/api/system/org";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Org",
  components: { Treeselect },
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
      // 部门树选项
      deptOptions: undefined,
      // 总条数
      total: 0,
      // 班级表格数据
      orgList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否已结课字典
      ortStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orgName: null,
        orgId: null,
        parentId: null,
        ortStatus: null,
        createTime: null,
        createBy: null,
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
    this.getDicts("36").then(response => {
      this.ortStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询班级列表 */
    getList() {
      this.loading = true;
      listOrg(this.queryParams).then(response => {
        this.orgList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.getTreeselect();
    },
    // 是否已结课字典翻译
    ortStatusFormat(row, column) {
      return this.selectDictLabel(this.ortStatusOptions, row.ortStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orgId: null,
        orgName: null,
        parentId: null,
        ortStatus: null,
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
      this.ids = selection.map(item => item.orgId)
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
      this.queryParams.parentId = data.id;
      this.getList();
    },
    
    
    
    
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加班级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orgId = row.orgId || this.ids
      getOrg(orgId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改班级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orgId != null) {
            updateOrg(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOrg(this.form).then(response => {
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
      const orgIds = row.orgId || this.ids;
      this.$confirm('是否确认删除班级编号为"' + orgIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOrg(orgIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出班级数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOrg(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
