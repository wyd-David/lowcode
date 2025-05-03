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
      <el-form-item label="资格名称" prop="qualificationName">
        <el-input
          v-model="queryParams.qualificationName"
          placeholder="请输入资格名称"
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
          v-hasPermi="['system:qualification:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:qualification:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:qualification:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:qualification:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qualificationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="职业资格等级" width="100px" align="center" prop="qualificationLevel" :formatter="qualificationLevelFormat" />
      <el-table-column label="资格名称" align="center" prop="qualificationName" />
      <el-table-column label="通过日期" align="center" prop="passDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.passDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="资格鉴定单位" align="center" prop="qualificationOrg" />
      <el-table-column label="鉴定证号" align="center" prop="identifyNo" />
      <el-table-column label="国家标准职级" align="center" prop="standardQualificationLevel" :formatter="standardQualificationLevelFormat" />
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
      <el-table-column label="操作"  width="100px"  align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:qualification:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:qualification:remove']"
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

    <!-- 添加或修改员工职业资格认证子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
       <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="资格名称" prop="qualificationName">
          <el-input v-model="form.qualificationName" placeholder="请输入资格名称" />
        </el-form-item>
        <el-form-item label="资格鉴定单位" prop="qualificationOrg">
          <el-input v-model="form.qualificationOrg" placeholder="请输入资格鉴定单位" />
        </el-form-item>
        <el-form-item label="鉴定证号" prop="identifyNo">
          <el-input v-model="form.identifyNo" placeholder="请输入鉴定证号" />
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="排序显示" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
        <el-form-item label="职业资格等级" prop="qualificationLevel">
          <el-select v-model="form.qualificationLevel" placeholder="请选择职业资格等级">
            <el-option
              v-for="dict in qualificationLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="国家标准职级" prop="standardQualificationLevel">
          <el-select v-model="form.standardQualificationLevel" placeholder="请选择国家标准职级">
            <el-option
              v-for="dict in standardQualificationLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通过日期" prop="passDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.passDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择通过日期">
          </el-date-picker>
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
import { listQualification, getQualification, delQualification, addQualification, updateQualification, exportQualification } from "@/api/system/qualification";

export default {
  name: "Qualification",
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
      // 员工职业资格认证子集表格数据
      qualificationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 职业资格等级字典
      qualificationLevelOptions: [],
      // 国家标准职级字典
      standardQualificationLevelOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        sortIndex: null,
        qualificationLevel: null,
        qualificationName: null,
        passDate: null,
        qualificationOrg: null,
        identifyNo: null,
        standardQualificationLevel: null,
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
    this.getDicts("68").then(response => {
      this.qualificationLevelOptions = response.data;
    });
    this.getDicts("10031").then(response => {
      this.standardQualificationLevelOptions = response.data;
    });
  },
  methods: {
    /** 查询员工职业资格认证子集列表 */
    getList() {
      this.loading = true;
      listQualification(this.queryParams).then(response => {
        this.qualificationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 职业资格等级字典翻译
    qualificationLevelFormat(row, column) {
      return this.selectDictLabel(this.qualificationLevelOptions, row.qualificationLevel);
    },
    // 国家标准职级字典翻译
    standardQualificationLevelFormat(row, column) {
      return this.selectDictLabel(this.standardQualificationLevelOptions, row.standardQualificationLevel);
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
        qualificationLevel: null,
        qualificationName: null,
        passDate: null,
        qualificationOrg: null,
        identifyNo: null,
        standardQualificationLevel: null,
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
      this.title = "添加员工职业资格认证子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getQualification(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工职业资格认证子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateQualification(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQualification(this.form).then(response => {
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
      this.$confirm('是否确认删除员工职业资格认证子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delQualification(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工职业资格认证子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportQualification(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
