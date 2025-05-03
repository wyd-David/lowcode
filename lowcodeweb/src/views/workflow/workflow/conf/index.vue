<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="实例ID" prop="procdefId">
        <el-input
          v-model="queryParams.procdefId"
          placeholder="请输入流程实例ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点名称" prop="stepName">
        <el-input
          v-model="queryParams.stepName"
          placeholder="请输入步骤名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="处理类型" prop="dealType">
        <el-select v-model="queryParams.dealType" placeholder="请选择处理类型：1：具体到人，2：直接上级，3：按角色，4：按角色根据组织递归" clearable size="small">
          <el-option
            v-for="dict in dealTypeOptions"
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['workflow:conf:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['workflow:conf:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['workflow:conf:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workflow:conf:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="confList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程实例ID" width="200px" align="center" prop="procdefId" />
      <el-table-column label="步骤名" width="200px" align="center" prop="stepName" />
	  <el-table-column label="步骤id" width="200px" align="center" prop="stepCode" />
      <el-table-column label="处理类型" width="200px" align="center" prop="dealType"  :formatter="dealTypeFormat"/>
      <el-table-column label="岗位信息" align="center" prop="systemRole" :formatter="dealUserIdFormat" />
	  <el-table-column label="角色" width="200px" align="center" prop="dealRoleId" :formatter="dealRoleIdFormat" />
    <el-table-column label="流程角色" width="200px" align="center" prop="flowRole" :formatter="flowRoleFormat" />
      <el-table-column label="处理人账号" width="200px" align="center" prop="dealUserId" />
      <el-table-column label="处理人名称" align="center" prop="dealUserName" />
      <el-table-column label="创建时间" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workflow:conf:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workflow:conf:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pageBox">
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- 添加或修改step对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="处理类型" prop="dealType">
                 <el-select v-model="form.dealType" placeholder="请选择处理类型：1：具体到人，2：直接上级，3：按角色，4：按角色根据组织递归">
                   <el-option
                     v-for="dict in dealTypeOptions"
                     :key="dict.dictValue"
                     :label="dict.dictLabel"
                     :value="dict.dictValue"
                   ></el-option>
                 </el-select>
               </el-form-item>
        <el-form-item label="角色" prop="dealRoleId">
          <el-select v-model="form.dealRoleId"  placeholder="请选择">
            <el-option v-for="item in roleOptions"
                       :key="item.roleId"
                       :label="item.roleName"
                       :value="item.roleId+''"
                       :disabled="item.status == 1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="岗位信息" prop="systemRole">
          <el-select v-model="form.systemRole"  filterable placeholder="请选择岗位">
            <el-option v-for="item in dealUserIdOptions"
             :key="item.postId"
             :label="item.postName"
             :value="item.postId+''"
             ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="流程角色" prop="flowRole">
          <el-select v-model="form.flowRole"  placeholder="请选择">
            <el-option v-for="item in roleControlOptions"
                       :key="item.roleControlId"
                       :label="item.roleName"
                       :value="item.roleControlId+''"
                       :disabled="item.status == 1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处理人账号" prop="dealUserName">
          <el-input v-model="form.dealUserName" type="textarea" maxlength="200" show-word-limit placeholder="请输入处理人账号,多个用逗号隔开" />
        </el-form-item>
        <el-form-item label="处理人账号ID" prop="dealUserId">
          <el-input v-model="form.dealUserId" type="textarea" maxlength="200" show-word-limit placeholder="请输入处理人账号,多个用逗号隔开" />
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
import { listConf, getConf, delConf, addConf, updateConf, exportConf } from "@/api/workflow/conf";
 import {
   listRole,listRoleControl
 } from "@/api/system/role";
import {
  listPost,
} from "@/api/system/post";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Conf",
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
      // step表格数据
      confList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 处理角色ID字典
      dealRoleIdOptions: [],
      // 处理人账号ID字典
      dealUserIdOptions: [],
      roleOptions:[],
      roleControlOptions:[],
      // 处理类型：1：具体到人，2：直接上级，3：按角色，4：按角色根据组织递归字典
      dealTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stepId: null,
        procdefId: null,
        stepName: null,
        stepCode: null,
        dealType: null,
		systemRole: null,
        dealRoleName: null,
        dealRoleId: null,
        dealUserName: null,
        dealUserId: null,
        createDate: null
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
    this.getDicts("EMPLOYEE_CLASS").then(response => {
      this.dealRoleIdOptions = response.data;
    });
    this.getDicts("DEAL_TYPE").then(response => {
        this.dealTypeOptions = response.data;
    });
   /* this.getDicts("sys_user_sex").then(response => {
      this.dealUserIdOptions = response.data;
    }); */

    const params={
      pageSize:200,
      pageNum:1
    }
    listRole(params).then((response) => {
      this.roleOptions = response.rows;
    });
    listPost(params).then((response) => {
      this.dealUserIdOptions = response.rows;
    });
    listRoleControl(params).then((response) => {
      this.roleControlOptions = response.rows;
    });
  },
  methods: {
    /** 查询step列表 */
    getList() {
      this.loading = true;
      listConf(this.queryParams).then(response => {
        this.confList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
   // 处理类型：1：具体到人，2：直接上级，3：按角色，4：按角色根据组织递归字典翻译
    dealTypeFormat(row, column) {
      return this.selectDictLabel(this.dealTypeOptions, row.dealType);
    },
    // 处理角色ID字典翻译
    dealRoleIdFormat(row, column) {
      return this.selectDictLabel2(this.roleOptions, row.dealRoleId);
    },
    // 处理流程角色ID字典翻译
    flowRoleFormat(row, column) {
      return this.selectRoleControlLabel(this.roleControlOptions, row.flowRole);
    },
    // 处理人账号ID字典翻译
    dealUserIdFormat(row, column) {
      return this.selectRoleLabel(this.dealUserIdOptions, row.systemRole);
    },

    clearFrom(){
      this.form = {
        dealType:this.form.dealType,
        dealRoleId: null,
        systemRole: null,
        flowRole: null,
        dealUserName: null,
        dealUserId: null,
        }
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
        stepId: null,
        procdefId: null,
        stepName: null,
        stepCode: null,
        dealType: null,
		systemRole: null,
        dealRoleName: null,
        dealRoleId: null,
        dealUserName: null,
        dealUserId: null,
        createDate: null
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
      this.title = "审批配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const keyId = row.keyId || this.ids;
      getConf(keyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改step";
      });
    },
    /** 提交按钮 */
    submitForm() {
      let _this=this
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.form);
          if (this.form.keyId != null) {
            updateConf(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addConf(this.form).then(response => {
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
      this.$confirm('是否确认删除step编号为"' + keyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delConf(keyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有step数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportConf(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    },
    //回写角色
    selectRoleLabel(datas, value) {
    	var actions = [];
    	Object.keys(datas).some((key) => {
    		if (datas[key].postId == ('' + value)) {
    			actions.push(datas[key].postName);
    			return true;
    		}
    	})
    	return actions.join('');
    },
    selectDictLabel2(datas, value) {
    	var actions = [];
      console.log(value, datas)
    	Object.keys(datas).some((key) => {
    		if (datas[key].roleId == ('' + value)) {
    			actions.push(datas[key].roleName);
    			return true;
    		}
    	})
    	return actions.join('');
    },
   //回写流程角色
   selectRoleControlLabel(datas, value) {
   	var actions = [];
   	Object.keys(datas).some((key) => {
   		if (datas[key].roleControlId == ('' + value)) {
   			actions.push(datas[key].roleName);
   			return true;
   		}
   	})
   	return actions.join('');
   },
  }
};
</script>
