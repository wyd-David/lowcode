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
      <el-form-item label="合同号" prop="contractNo">
        <el-input
          v-model="queryParams.contractNo"
          placeholder="请输入合同号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同类型" prop="contractType">
        <el-select v-model="queryParams.contractType" placeholder="请选择合同类型" clearable size="small">
          <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>

      <el-form-item label="合同性质" prop="contractNature">
        <el-select v-model="queryParams.contractNature" placeholder="请选择合同性质" clearable size="small">
          <el-option v-for="dict in contractNatureOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="合同状态" prop="contractStatus">
        <el-select v-model="queryParams.contractStatus" placeholder="请选择合同状态 数据字典" clearable size="small">
         <el-option v-for="dict in contractStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="开始日期" prop="startDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束日期" prop="endDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.endDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束日期">
        </el-date-picker>
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
          v-hasPermi="['system:contract:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:contract:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:contract:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:contract:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" v-if="show" align="center" prop="recId" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="期限类型" align="center" prop="deadlineTypeName" width="100" />
      <el-table-column label="合同号" align="center" prop="contractNo" />
      <el-table-column label="合同类型" align="center" prop="contractTypeName" />
      <el-table-column label="合同工资" align="center" prop="contractPay" />
      <el-table-column label="签订合同日期" align="center" prop="contractSignedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.contractSignedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同状态" align="center" prop="contractStatusName" />
      <el-table-column label="合同性质" align="center" prop="contractNatureName" />

      <el-table-column label="开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="终止日期" align="center" prop="terminateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.terminateDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同到期是否续签" align="center" prop="continueFlagName"/>
      <el-table-column label="合同终止情況" align="center" prop="terminateGist" />
      <el-table-column label="试用期期限" align="center" prop="probationershipTerm" />
      <el-table-column label="合同备注" align="center" prop="memo" />
      <el-table-column label="合同工作岗位" align="center" prop="contractPost" />
      <el-table-column label="合同甲方公司地点" align="center" prop="asideOfficeAddr" />

      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:contract:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:contract:remove']"
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

    <!-- 添加或修改员工合同子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
       <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="期限类型" prop="deadlineType">
          <el-select v-model="form.deadlineType" placeholder="请选择合同期限类型">
            <el-option v-for="dict in deadlineTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
          </el-select>
        </el-form-item>
        <el-form-item label="终止日期" prop="terminateDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.terminateDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择合同终止">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="合同类型" prop="contractType">
          <el-select v-model="form.contractType" placeholder="请选择合同类型">
            <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
          </el-select>
        </el-form-item>
        <el-form-item label="到期续签" prop="continueFlag">
         <el-select v-model="form.continueFlag" placeholder="请输入合同到期是否续签">
           <el-option v-for="dict in sys_yes_noOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
         </el-select>

        </el-form-item>
        <el-form-item label="试用期限" prop="probationershipTerm">
         <el-input v-model="form.probationershipTerm" placeholder="请输入试用期期限" />
        </el-form-item>
       </el-col>
       <el-col :span="12">
        <el-form-item label="合同号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="请输入合同号" />
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.endDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束日期">
          </el-date-picker>
        </el-form-item>
      <el-form-item label="排序号" prop="sortIndex">
        <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
      </el-form-item>
        <el-form-item label="合同工资" prop="contractPay">
          <el-input v-model="form.contractPay" placeholder="请输入合同工资" />
        </el-form-item>
        <el-form-item label="签订日期" prop="contractSignedDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.contractSignedDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择签订合同日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同状态" prop="contractStatus">
          <el-select v-model="form.contractStatus" placeholder="请选择合同状态 数据字典"  size="small">
           <el-option v-for="dict in contractStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
          </el-select>
        </el-form-item>
        <el-form-item label="合同性质" prop="contractNature">
          <el-select v-model="form.contractNature" placeholder="请输入合同性质">
            <el-option v-for="dict in contractNatureOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
          </el-select>
        </el-form-item>
       </el-col>
       <el-col :span="24">
         <el-form-item label="合同备注" prop="memo">
           <el-input v-model="form.memo" placeholder="请输入合同备注" />
         </el-form-item>
         <el-form-item label="工作岗位" prop="contractPost">
           <el-input v-model="form.contractPost" placeholder="请输入合同工作岗位" />
         </el-form-item>
         <el-form-item label="工作地点" prop="asideOfficeAddr">
           <el-input v-model="form.asideOfficeAddr" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
         </el-form-item>
         <el-form-item label="终止原因" prop="terminateGist">
           <el-input v-model="form.terminateGist" placeholder="请输入合同终止" />
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
import { listContract, getContract, delContract, addContract, updateContract, exportContract } from "@/api/system/contract";

export default {
  name: "Contract",
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
      // 员工合同子集表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      show: false,
      contractTypeOptions: [],
      contractNatureOptions: [],
      deadlineTypeOptions: [],
      sys_yes_noOptions: [],
      contractStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        startDate: null,
        endDate: null,
        sortIndex: null,
        deadlineType: null,
        terminateDate: null,
        terminateCircs: null,
        contractNo: null,
        contractType: null,

        continueFlag: null,
        terminateGist: null,
        probationershipTerm: null,
        memo: null,
        contractPost: null,
        asideOfficeAddr: null,
        contractPay: null,
        contractSignedDate: null,
        contractStatus: null,
        contractNature: null,
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
    this.getDicts("35").then((response) => {
      this.contractTypeOptions = response.data;
    });
    this.getDicts("10036").then((response) => {
      this.contractNatureOptions = response.data;
    });
    this.getDicts("33").then((response) => {
      this.deadlineTypeOptions = response.data;
    });
    this.getDicts("36").then((response) => {
      this.sys_yes_noOptions = response.data;
    });
    this.getDicts("10035").then((response) => {
      this.contractStatusOptions = response.data;
    });


  },
  methods: {
    /** 查询员工合同子集列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows;
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
        startDate: null,
        endDate: null,
        sortIndex: null,
        deadlineType: null,
        terminateDate: null,
        terminateCircs: null,
        contractNo: null,
        contractType: null,
        continueFlag: null,
        terminateGist: null,
        probationershipTerm: null,
        memo: null,
        contractPost: null,
        asideOfficeAddr: null,
        contractPay: null,
        contractSignedDate: null,
        contractStatus: null,
        contractNature: null,
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
      this.title = "添加员工合同子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getContract(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工合同子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateContract(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addContract(this.form).then(response => {
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
      this.$confirm('是否确认删除员工合同子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delContract(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工合同子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportContract(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
