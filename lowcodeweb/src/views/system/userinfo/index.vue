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
        <el-form-item label="用户帐号" prop="loginId">
          <el-input
            v-model="queryParams.loginId"
            placeholder="请输入用户帐号"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户全名" prop="fullName">
          <el-input
            v-model="queryParams.fullName"
            placeholder="请输入用户全名"
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
            v-hasPermi="['system:userinfo:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:userinfo:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:userinfo:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:userinfo:export']"
          >导出</el-button>
        </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="userinfoList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="用户帐号"  width="100px" align="center" prop="loginId" />
        <el-table-column label="用户全名" width="100px" align="center" prop="fullName" />
        <el-table-column label="账号状态" align="center" prop="accountStatus" :formatter="accountStatusFormat" />
        <el-table-column label="开始生效时间" width="100px" align="center" prop="empStartTime" />
        <el-table-column label="所属租户" width="100px" align="center" prop="regionKey" :formatter="regionKeyFormat" />
        <el-table-column label="所属公司" width="100px" align="center" prop="companyName" />
        <el-table-column label="所属分公司" width="100px" align="center" prop="branchName" />
        <el-table-column label="所属部门" width="100px" align="center" prop="departmentName" />
        <el-table-column label="所属科室" width="100px" align="center" prop="officeName" />
        <el-table-column label="所属团队" width="100px" align="center" prop="teamName" />
        <el-table-column label="所属小组" width="100px" align="center" prop="groupName" />
        <el-table-column label="编制组织" width="100px" align="center" prop="orgIdName" />
        <el-table-column label="工作组织" width="100px" align="center" prop="workOrgName" />
        <el-table-column label="人员类型" width="100px" align="center" prop="jobType" :formatter="jobTypeFormat" />
        <el-table-column label="移动电话" width="120px" align="center" prop="telPhone" />
        <el-table-column label="办公邮件" width="200px" align="center" prop="workEmail" />
        <el-table-column label="生日" align="center" prop="userBirthday" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.userBirthday, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="身份证号" width="200px" align="center" prop="idCard" />
        <el-table-column label="性别" align="center" prop="sysUserSex" :formatter="sysUserSexFormat" />
        <el-table-column label="职位" align="center" prop="postTitle" />
        <el-table-column label="流程职务职级" width="120px" align="center" prop="employeeClass" :formatter="employeeClassFormat" />
        <el-table-column label="用户职位职级"  align="center" prop="userPostLevel" :formatter="userPostLevelFormat" />
        <el-table-column label="部门内排序号" align="center" prop="orderId" />
        <el-table-column label="加入公司日期" align="center" prop="userJoinDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.userJoinDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="民族" align="center" prop="userNation" :formatter="userNationFormat" />
        <el-table-column label="政治面貌" align="center" prop="userReligion" :formatter="userReligionFormat" />
        <el-table-column label="离开公司日期" align="center" prop="userQuitDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.userQuitDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="职务描述"  align="center" prop="dutyDesc" />
        <el-table-column label="联系地址" align="center" prop="addressInfo" />
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
        <el-table-column label="操作" align="center"  width="100px" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:userinfo:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:userinfo:remove']"
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

    <!-- 添加或修改租户用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="所属组织" prop="orgId">
          <treeselect v-model="form.orgId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
        </el-form-item>
        <el-form-item label="工作组织" prop="workOrgId">
          <treeselect v-model="form.workOrgId" :options="deptOptions" :show-count="true" placeholder="请选择工作部门" />
        </el-form-item>
       <el-col :span="12">
        <el-form-item label="用户全名" prop="fullName">
          <el-input v-model="form.fullName" placeholder="请输入用户全名" />
        </el-form-item>
        <el-form-item label="移动电话" prop="telPhone">
          <el-input v-model="form.telPhone" placeholder="请输入移动电话" />
        </el-form-item>
        <el-form-item label="办公邮件" prop="workEmail">
          <el-input v-model="form.workEmail" placeholder="请输入办公邮件" />
        </el-form-item>
        <el-form-item label="生日" prop="userBirthday">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.userBirthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sysUserSex">
          <el-select v-model="form.sysUserSex" placeholder="请选择性别">
            <el-option
              v-for="dict in sysUserSexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="人员类型" prop="jobType">
          <el-select v-model="form.jobType" placeholder="请选择人员类型">
            <el-option
              v-for="dict in jobTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职务职级" prop="employeeClass">
          <el-select v-model="form.employeeClass" placeholder="请选择流程职务职级">
            <el-option
              v-for="dict in employeeClassOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位职级" prop="userPostLevel">
          <el-select v-model="form.userPostLevel" placeholder="请选择用户职位职级">
            <el-option
              v-for="dict in userPostLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位" prop="postTitle">
          <el-input v-model="form.postTitle" placeholder="请输入职位" />
        </el-form-item>
       </el-col>
       <el-col :span="12">
         <el-form-item label="职务描述" prop="dutyDesc">
           <el-input v-model="form.dutyDesc" placeholder="请输入职务描述" />
         </el-form-item>
         <el-form-item label="身份证" prop="idCard">
           <el-input v-model="form.idCard" placeholder="请输入身份证号" />
         </el-form-item>
         <el-form-item label="生效时间" prop="empStartTime">
           <el-input v-model="form.empStartTime" placeholder="请输入人员开始生效时间" />
         </el-form-item>

        <el-form-item label="加入日期" prop="userJoinDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.userJoinDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择加入公司日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="民族" prop="userNation">
          <el-select v-model="form.userNation" placeholder="请选择民族">
            <el-option
              v-for="dict in userNationOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="政治面貌" prop="userReligion">
          <el-select v-model="form.userReligion" placeholder="请选择政治面貌">
            <el-option
              v-for="dict in userReligionOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="离职日期" prop="userQuitDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.userQuitDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择离开公司日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="账号状态" prop="accountStatus">
          <el-select v-model="form.accountStatus" placeholder="请选择账号状态 1:启用 0:停用 2限权">
            <el-option
              v-for="dict in accountStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="orderId">
          <el-input-number v-model="form.orderId" style="width: 80%;" placeholder="请输入组织排序"></el-input-number>
        </el-form-item>
       </el-col>
       <el-col :span="24">
        <el-form-item label="联系地址" prop="addressInfo">
          <el-input v-model="form.addressInfo" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
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
import { listUserinfo, getUserinfo, delUserinfo, addUserinfo, updateUserinfo, exportUserinfo } from "@/api/system/userinfo";
import Treeselect from "@riophae/vue-treeselect";
import { treeselectOrg } from "@/api/system/organizationinfo";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Userinfo",
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
      // 总条数
      total: 0,
      // 租户用户表格数据
      userinfoList: [],
      // 部门树选项
      deptOptions: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 所属地区(租户)ID字典
      regionKeyOptions: [],
      // 人员类型字典
      jobTypeOptions: [],
      // 性别字典
      sysUserSexOptions: [],
      // 流程职务职级字典
      employeeClassOptions: [],
      // 用户职位职级字典
      userPostLevelOptions: [],
      // 民族字典
      userNationOptions: [],
      // 政治面貌字典
      userReligionOptions: [],
      // 账号状态 1:启用 0:停用 2限权字典
      accountStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        loginId: null,
        fullName: null,
        regionKey: null,
        regionName: null,
        companyName: null,
        companyId: null,
        branchName: null,
        branchId: null,
        departmentName: null,
        departmentId: null,
        officeName: null,
        officeId: null,
        teamName: null,
        teamId: null,
        orgId: null,
        orgIdName: null,
        groupName: null,
        groupId: null,
        workOrgId: null,
        workOrgName: null,
        jobType: null,
        telPhone: null,
        workEmail: null,
        addressInfo: null,
        userBirthday: null,
        sysUserSex: null,
        postTitle: null,
        employeeClass: null,
        userPostLevel: null,
        orderId: null,
        userJoinDate: null,
        userNation: null,
        userReligion: null,
        userQuitDate: null,
        dutyDesc: null,
        accountStatus: null,
        idCard: null,
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        empStartTime: null
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
    this.getDicts("REGION_KEY").then(response => {
      this.regionKeyOptions = response.data;
    });
    this.getDicts("JOB_TYPE").then(response => {
      this.jobTypeOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sysUserSexOptions = response.data;
    });
    this.getDicts("EMPLOYEE_CLASS").then(response => {
      this.employeeClassOptions = response.data;
    });
    this.getDicts("USER_POST_LEVEL").then(response => {
      this.userPostLevelOptions = response.data;
    });
    this.getDicts("USER_NATION").then(response => {
      this.userNationOptions = response.data;
    });
    this.getDicts("USER_RELIGION").then(response => {
      this.userReligionOptions = response.data;
    });
    this.getDicts("ACCOUNT_STATUS").then(response => {
      this.accountStatusOptions = response.data;
    });
    this.getTreeselect();
  },
  methods: {
    /** 查询租户用户列表 */
    getList() {
      this.loading = true;
      listUserinfo(this.queryParams).then(response => {
        this.userinfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.queryParams.orgId = data.id;
      this.getList();
    },
    // 所属地区(租户)ID字典翻译
    regionKeyFormat(row, column) {
      return this.selectDictLabel(this.regionKeyOptions, row.regionKey);
    },
    // 人员类型字典翻译
    jobTypeFormat(row, column) {
      return this.selectDictLabel(this.jobTypeOptions, row.jobType);
    },
    // 性别字典翻译
    sysUserSexFormat(row, column) {
      return this.selectDictLabel(this.sysUserSexOptions, row.sysUserSex);
    },
    // 流程职务职级字典翻译
    employeeClassFormat(row, column) {
      return this.selectDictLabel(this.employeeClassOptions, row.employeeClass);
    },
    // 用户职位职级字典翻译
    userPostLevelFormat(row, column) {
      return this.selectDictLabel(this.userPostLevelOptions, row.userPostLevel);
    },
    // 民族字典翻译
    userNationFormat(row, column) {
      return this.selectDictLabel(this.userNationOptions, row.userNation);
    },
    // 政治面貌字典翻译
    userReligionFormat(row, column) {
      return this.selectDictLabel(this.userReligionOptions, row.userReligion);
    },
    // 账号状态 1:启用 0:停用 2限权字典翻译
    accountStatusFormat(row, column) {
      return this.selectDictLabel(this.accountStatusOptions, row.accountStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        employeeId: null,
        loginId: null,
        fullName: null,
        regionKey: null,
        regionName: null,
        companyName: null,
        companyId: null,
        branchName: null,
        branchId: null,
        departmentName: null,
        departmentId: null,
        officeName: null,
        officeId: null,
        teamName: null,
        teamId: null,
        groupName: null,
        groupId: null,
        workOrgId: null,
        workOrgName: null,
        jobType: null,
        telPhone: null,
        workEmail: null,
        addressInfo: null,
        userBirthday: null,
        sysUserSex: null,
        postTitle: null,
        employeeClass: null,
        userPostLevel: null,
        orderId: null,
        userJoinDate: null,
        userNation: null,
        userReligion: null,
        userQuitDate: null,
        dutyDesc: null,
        accountStatus: null,
        idCard: null,
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        empStartTime: null
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
      this.ids = selection.map(item => item.employeeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加租户用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const employeeId = row.employeeId || this.ids
      getUserinfo(employeeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改租户用户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.employeeId != null) {
            updateUserinfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUserinfo(this.form).then(response => {
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
      const employeeIds = row.employeeId || this.ids;
      this.$confirm('是否确认删除租户用户编号为"' + employeeIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUserinfo(employeeIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出租户用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUserinfo(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
