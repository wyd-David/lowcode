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
        <el-form-item label="组织名称" prop="ouName">
          <el-input
            v-model="queryParams.ouName"
            placeholder="请输入组织名称"
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
            v-hasPermi="['system:organizationinfo:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:organizationinfo:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:organizationinfo:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:organizationinfo:export']"
          >导出</el-button>
        </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="organizationinfoList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />

        <el-table-column label="组织名称"  width="100px"  align="center" prop="ouName" />
        <el-table-column label="组织全名称"  width="200px"  align="center" prop="ouFullName" />
        <el-table-column label="组织状态" align="center" prop="orgState" :formatter="orgStateFormat" />
        <el-table-column label="组织层级" align="center" prop="ouLevel" />
        <el-table-column label="组织排序" align="center" prop="ouOrder" />
        <el-table-column label="组织类型" align="center" prop="orgTypeId" :formatter="orgTypeIdFormat" />
        <el-table-column label="组织编码"  width="100px"  align="center" prop="orgCode" />
        <el-table-column label="主负责人" align="center" prop="orgMainLeader" />
        <el-table-column label="辅负责人" align="center" prop="orgOtherLeader" />
        <el-table-column label="上级负责人" width="100px" align="center" prop="orgSupervisor" />
        <el-table-column label="上级组织编码" width="100px" align="center" prop="orgParentCode" />
        <el-table-column label="所属(租户)" width="100px" align="center" prop="regionKey" :formatter="regionKeyFormat" />
        <el-table-column label="所属公司" width="100px" align="center" prop="companyName" />
        <el-table-column label="分公司" width="100px" align="center" prop="branchName" />
        <el-table-column label="部门" align="center" prop="departmentName" />
        <el-table-column label="科室" align="center" prop="officeName" />
        <el-table-column label="团队" align="center" prop="teamName" />
        <el-table-column label="小组" align="center" prop="groupName" />
        <el-table-column label="组织ID" width="280px" align="center" prop="ouguid" />
        <el-table-column label="父组织ID" width="300px" align="center" prop="parentOuguid" />
        <el-table-column label="开始生效时间" width="100px" align="center" prop="orgStartTime" />
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
        <el-table-column label="操作" align="center"  width="100px"  class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:organizationinfo:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:organizationinfo:remove']"
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


    <!-- 添加或修改租户组织对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="归属部门" prop="parentOuguid">
          <treeselect v-model="form.parentOuguid" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
        </el-form-item>
      <el-col :span="12">
        <el-form-item label="组织名称" prop="ouName">
          <el-input v-model="form.ouName" placeholder="请输入组织名称" />
        </el-form-item>
        <el-form-item label="组织状态" prop="orgState">
          <el-select v-model="form.orgState" placeholder="请选择组织状态">
            <el-option
              v-for="dict in orgStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组织类型" prop="orgTypeId">
          <el-select v-model="form.orgTypeId" placeholder="请选择组织类型">
            <el-option
              v-for="dict in orgTypeIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组织排序" prop="ouOrder">
          <el-input-number v-model="form.ouOrder" style="width: 100%;" placeholder="请输入组织排序"></el-input-number>
        </el-form-item>
       </el-col>
       <el-col :span="12">
        <el-form-item label="生效时间" prop="orgStartTime">
          <el-input v-model="form.orgStartTime" placeholder="请输入开始生效时间" />
        </el-form-item>
        <el-form-item label="组织编码" prop="orgCode">
          <el-input v-model="form.orgCode" placeholder="请输入组织编码" />
        </el-form-item>
        <el-form-item label="负责人" prop="orgMainLeader">
          <el-input v-model="form.orgMainLeader" placeholder="请输入组织主负责人" />
        </el-form-item>
        <el-form-item label="辅负责人" prop="orgOtherLeader">
          <el-input v-model="form.orgOtherLeader" placeholder="请输入组织辅负责人" />
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
import { listOrganizationinfo, getOrganizationinfo, delOrganizationinfo, addOrganizationinfo, updateOrganizationinfo, exportOrganizationinfo,treeselectOrg } from "@/api/system/organizationinfo";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Organizationinfo",
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
      // 租户组织表格数据
      organizationinfoList: [],
      // 部门树选项
      deptOptions: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 组织状态字典
      orgStateOptions: [],
      // 组织类型字典
      orgTypeIdOptions: [],
      // 所属地区(租户)ID字典
      regionKeyOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentOuguid: null,
        orgState: null,
        ouName: null,
        ouFullName: null,
        ouLevel: null,
        ouOrder: null,
        orgTypeId: null,
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
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        orgStartTime: null,
        orgCode: null,
        orgMainLeader: null,
        orgOtherLeader: null,
        orgSupervisor: null,
        orgParentCode: null
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();

    this.getDicts("ORG_STATE").then(response => {
      this.orgStateOptions = response.data;
    });
    this.getDicts("ORG_TYPE_ID").then(response => {
      this.orgTypeIdOptions = response.data;
    });
    this.getDicts("REGION_KEY").then(response => {
      this.regionKeyOptions = response.data;
    });
  },
  methods: {
    /** 查询租户组织列表 */
    getList() {
      this.loading = true;
      listOrganizationinfo(this.queryParams).then(response => {
        this.organizationinfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.getTreeselect();
    },
    // 组织状态字典翻译
    orgStateFormat(row, column) {
      return this.selectDictLabel(this.orgStateOptions, row.orgState);
    },
    // 组织类型字典翻译
    orgTypeIdFormat(row, column) {
      return this.selectDictLabel(this.orgTypeIdOptions, row.orgTypeId);
    },
    // 所属地区(租户)ID字典翻译
    regionKeyFormat(row, column) {
      return this.selectDictLabel(this.regionKeyOptions, row.regionKey);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        ouguid: null,
        parentOuguid: null,
        orgState: null,
        ouName: null,
        ouFullName: null,
        ouLevel: null,
        ouOrder: null,
        orgTypeId: null,
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
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        orgStartTime: null,
        orgCode: null,
        orgMainLeader: null,
        orgOtherLeader: null,
        orgSupervisor: null,
        orgParentCode: null
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
      this.ids = selection.map(item => item.ouguid)
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
      this.queryParams.ouguid = data.id;
      this.getList();
    },


    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加租户组织";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ouguid = row.ouguid || this.ids
      getOrganizationinfo(ouguid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改租户组织";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.ouguid != null) {
            updateOrganizationinfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOrganizationinfo(this.form).then(response => {
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
      const ouguids = row.ouguid || this.ids;
      this.$confirm('是否确认删除租户组织编号为"' + ouguids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOrganizationinfo(ouguids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出租户组织数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOrganizationinfo(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
