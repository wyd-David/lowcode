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
      <el-form-item label="培训途径" prop="trainingMethod">
              <el-select v-model="queryParams.trainingMethod" placeholder="请选择培训途径" clearable size="small">
                <el-option
                  v-for="dict in trainingMethodOptions"
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
          v-hasPermi="['system:training:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:training:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:training:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:training:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="trainingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
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
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="培训途径" align="center" prop="trainingMethod" :formatter="trainingMethodFormat" />
      <el-table-column label="培训项目名称" align="center" prop="projectName" />
      <el-table-column label="主办单位" align="center" prop="doOrg" />
      <el-table-column label="资格证号" align="center" prop="qualificationNo" />
      <el-table-column label="培训内容类别" align="center" prop="contentType" :formatter="contentTypeFormat" />
      <el-table-column label="考试成绩" align="center" prop="examScore" />
      <el-table-column label="课时数" align="center" prop="examHour" />
      <el-table-column label="发证机关" align="center" prop="grantOrg" />
      <el-table-column label="培训项目地点" align="center" prop="projectAddress" />
      <el-table-column label="培训类别" align="center" prop="trainingType" :formatter="trainingTypeFormat" />
      <el-table-column label="培训项目编号" align="center" prop="projectCode" />
      <el-table-column label="培训目的" align="center" prop="projectGoal" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.projectGoal, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目培训费用" align="center" prop="projectCost" />
      <el-table-column label="个人培训费用" align="center" prop="personalCost" />
      <el-table-column label="培训师资来源" align="center" prop="teachSource" />
      <el-table-column label="是否海外师资" align="center" prop="foreignTeachFlag" :formatter="foreignTeachFlagFormat" />
      <el-table-column label="培训合同" align="center" prop="trainingContract" />
      <el-table-column label="培训文号" align="center" prop="trainingWritNo" />
      <el-table-column label="培训项目周期" align="center" prop="cycleDay" />
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
            v-hasPermi="['system:training:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:training:remove']"
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

    <!-- 添加或修改员工培训子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="24">
          <el-form-item label="师资来源" prop="teachSource">
            <el-input v-model="form.teachSource" placeholder="请输入培训师资来源" />
          </el-form-item>
          <el-form-item label="培训名称" prop="projectName">
          <el-input v-model="form.projectName"  placeholder="请输入内容" />
         </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="员工ID" prop="empId">
              <el-input v-model="form.empId" placeholder="请输入员工ID" />
            </el-form-item>
            <el-form-item label="主办单位" prop="doOrg">
              <el-input v-model="form.doOrg" placeholder="请输入主办单位" />
            </el-form-item>
            <el-form-item label="资格证号" prop="qualificationNo">
              <el-input v-model="form.qualificationNo" placeholder="请输入资格证号" />
            </el-form-item>
            <el-form-item label="考试成绩" prop="examScore">
              <el-input v-model="form.examScore" placeholder="请输入考试成绩" />
            </el-form-item>
            <el-form-item label="课时数" prop="examHour">
              <el-input v-model="form.examHour" placeholder="请输入课时数" />
            </el-form-item>
            <el-form-item label="发证机关" prop="grantOrg">
              <el-input v-model="form.grantOrg" placeholder="请输入发证机关" />
            </el-form-item>
            <el-form-item label="培训地点" prop="projectAddress">
              <el-input v-model="form.projectAddress" placeholder="请输入培训项目地点" />
            </el-form-item>
            <el-form-item label="培训编号" prop="projectCode">
              <el-input v-model="form.projectCode" placeholder="请输入培训项目编号" />
            </el-form-item>
            <el-form-item label="培训费用" prop="projectCost">
              <el-input v-model="form.projectCost" placeholder="请输入项目培训费用" />
            </el-form-item>
            <el-form-item label="个人培训费用" prop="personalCost">
              <el-input v-model="form.personalCost" placeholder="请输入个人培训费用" />
            </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开始日期" prop="startDate">
            <el-date-picker clearable size="small" style="width: 200px"
              v-model="form.startDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择开始日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束日期" prop="endDate">
            <el-date-picker clearable size="small" style="width: 200px"
              v-model="form.endDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="排序" prop="sortIndex">
            <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
          </el-form-item>
          <el-form-item label="培训途径" prop="trainingMethod">
            <el-select v-model="form.trainingMethod" placeholder="请选择培训途径">
              <el-option
                v-for="dict in trainingMethodOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="内类别" prop="contentType">
            <el-select v-model="form.contentType" placeholder="请选择培训内容类别">
              <el-option
                v-for="dict in contentTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="培训类别" prop="trainingType">
            <el-select v-model="form.trainingType" placeholder="请选择培训类别">
              <el-option
                v-for="dict in trainingTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="海外师资" prop="foreignTeachFlag">
            <el-select v-model="form.foreignTeachFlag" placeholder="请选择是否海外师资">
              <el-option
                v-for="dict in foreignTeachFlagOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="培训合同" prop="trainingContract">
            <el-input v-model="form.trainingContract" placeholder="请输入培训合同" />
          </el-form-item>
          <el-form-item label="培训文号" prop="trainingWritNo">
            <el-input v-model="form.trainingWritNo" placeholder="请输入培训文号" />
          </el-form-item>
          <el-form-item label="培训周期" prop="cycleDay">
            <el-input v-model="form.cycleDay" placeholder="请输入培训项目周期" />
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
import { listTraining, getTraining, delTraining, addTraining, updateTraining, exportTraining } from "@/api/system/training";

export default {
  name: "Training",
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
      // 员工培训子集表格数据
      trainingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 培训途径字典
      trainingMethodOptions: [],
      // 培训内容类别字典
      contentTypeOptions: [],
      // 培训类别字典
      trainingTypeOptions: [],
      // 是否海外师资字典
      foreignTeachFlagOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        startDate: null,
        endDate: null,
        sortIndex: null,
        trainingMethod: null,
        projectName: null,
        doOrg: null,
        qualificationNo: null,
        contentType: null,
        examScore: null,
        examHour: null,
        grantOrg: null,
        projectAddress: null,
        trainingType: null,
        projectCode: null,
        projectGoal: null,
        projectCost: null,
        personalCost: null,
        teachSource: null,
        foreignTeachFlag: null,
        trainingContract: null,
        trainingWritNo: null,
        cycleDay: null,
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
    this.getDicts("46").then(response => {
      this.trainingMethodOptions = response.data;
    });
    this.getDicts("47").then(response => {
      this.contentTypeOptions = response.data;
    });
    this.getDicts("48").then(response => {
      this.trainingTypeOptions = response.data;
    });
    this.getDicts("36").then(response => {
      this.foreignTeachFlagOptions = response.data;
    });
  },
  methods: {
    /** 查询员工培训子集列表 */
    getList() {
      this.loading = true;
      listTraining(this.queryParams).then(response => {
        this.trainingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 培训途径字典翻译
    trainingMethodFormat(row, column) {
      return this.selectDictLabel(this.trainingMethodOptions, row.trainingMethod);
    },
    // 培训内容类别字典翻译
    contentTypeFormat(row, column) {
      return this.selectDictLabel(this.contentTypeOptions, row.contentType);
    },
    // 培训类别字典翻译
    trainingTypeFormat(row, column) {
      return this.selectDictLabel(this.trainingTypeOptions, row.trainingType);
    },
    // 是否海外师资字典翻译
    foreignTeachFlagFormat(row, column) {
      return this.selectDictLabel(this.foreignTeachFlagOptions, row.foreignTeachFlag);
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
        trainingMethod: null,
        projectName: null,
        doOrg: null,
        qualificationNo: null,
        contentType: null,
        examScore: null,
        examHour: null,
        grantOrg: null,
        projectAddress: null,
        trainingType: null,
        projectCode: null,
        projectGoal: null,
        projectCost: null,
        personalCost: null,
        teachSource: null,
        foreignTeachFlag: null,
        trainingContract: null,
        trainingWritNo: null,
        cycleDay: null,
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
      this.title = "添加员工培训子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getTraining(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工培训子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateTraining(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTraining(this.form).then(response => {
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
      this.$confirm('是否确认删除员工培训子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTraining(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工培训子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTraining(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
