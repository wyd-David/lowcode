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
      <el-form-item label="学历" prop="education">
        <el-select v-model="queryParams.education" placeholder="请选择学历" clearable size="small">
          <el-option
            v-for="dict in educationOptions"
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
          v-hasPermi="['system:education:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:education:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:education:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:education:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="educationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="毕业学校名称" width="200px" align="center" prop="graduateSchoolName" />
      <el-table-column label="入学时间" align="center" prop="entranceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entranceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="毕(肄)业时间" align="center" prop="graduateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.graduateDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学历" align="center" prop="education" :formatter="educationFormat" />
      <el-table-column label="学位" align="center" prop="degree" :formatter="degreeFormat" />
      <el-table-column label="学习形式" align="center" prop="studyForm" :formatter="studyFormFormat" />
      <el-table-column label="学位授予时间" align="center" prop="awardDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.awardDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所学专业" align="center" prop="specialty" />
      <el-table-column label="参加工作时学历标识" align="center" prop="joinWorkFlag" :formatter="joinWorkFlagFormat" />
      <el-table-column label="最高学历标识" align="center" prop="highestFlag" :formatter="highestFlagFormat" />
      <el-table-column label="毕业情况" align="center" prop="graduateCondition" :formatter="graduateConditionFormat" />
      <el-table-column label="学位类别" align="center" prop="degreeClass" :formatter="degreeClassFormat" />
      <el-table-column label="学制" align="center" prop="studySystem" />
      <el-table-column label="是否全日制" align="center" prop="isFulltime" :formatter="isFulltimeFormat" />
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
      <el-table-column label="操作" align="center"  width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:education:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:education:remove']"
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

    <!-- 添加或修改员工教育子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
       <el-form-item label="毕业学校名称" prop="graduateSchoolName">
         <el-input v-model="form.graduateSchoolName" placeholder="请输入毕业学校名称" />
       </el-form-item>
       <el-form-item label="所学专业" prop="specialty">
         <el-input v-model="form.specialty" placeholder="请输入所学专业" />
       </el-form-item>

        <el-form-item label="学位授予时间" prop="awardDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.awardDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择学位授予时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否全日制" prop="isFulltime">
          <el-select v-model="form.isFulltime" placeholder="请选择是否全日制">
            <el-option
              v-for="dict in isFulltimeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
       <el-form-item label="学历" prop="education">
         <el-select v-model="form.education" placeholder="请选择学历">
           <el-option
             v-for="dict in educationOptions"
             :key="dict.dictValue"
             :label="dict.dictLabel"
             :value="parseInt(dict.dictValue)"
           ></el-option>
         </el-select>
       </el-form-item>
        <el-form-item label="学位" prop="degree">
          <el-select v-model="form.degree" placeholder="请选择学位">
            <el-option
              v-for="dict in degreeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序显示" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
      </el-col>
      <el-col :span="12">
       <el-form-item label="入学时间" prop="entranceDate">
         <el-date-picker clearable size="small" style="width: 200px"
           v-model="form.entranceDate"
           type="date"
           value-format="yyyy-MM-dd"
           placeholder="选择入学时间">
         </el-date-picker>
       </el-form-item>
       <el-form-item label="毕(肄)业时间" prop="graduateDate">
         <el-date-picker clearable size="small" style="width: 200px"
           v-model="form.graduateDate"
           type="date"
           value-format="yyyy-MM-dd"
           placeholder="选择毕(肄)业时间">
         </el-date-picker>
       </el-form-item>
        <el-form-item label="参加工作时学历标识" prop="joinWorkFlag">
          <el-select v-model="form.joinWorkFlag" placeholder="请选择参加工作时学历标识">
            <el-option
              v-for="dict in joinWorkFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最高学历标识" prop="highestFlag">
          <el-select v-model="form.highestFlag" placeholder="请选择最高学历标识">
            <el-option
              v-for="dict in highestFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="毕业情况" prop="graduateCondition">
          <el-select v-model="form.graduateCondition" placeholder="请选择毕业情况">
            <el-option
              v-for="dict in graduateConditionOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学位类别" prop="degreeClass">
          <el-select v-model="form.degreeClass" placeholder="请选择学位类别">
            <el-option
              v-for="dict in degreeClassOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学习形式" prop="studyForm">
          <el-select v-model="form.studyForm" placeholder="请选择学习形式">
            <el-option
              v-for="dict in studyFormOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学制" prop="studySystem">
          <el-input-number v-model="form.studySystem" placeholder="请输入学制"></el-input-number>
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
import { listEducation, getEducation, delEducation, addEducation, updateEducation, exportEducation } from "@/api/system/education";

export default {
  name: "Education",
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
      // 员工教育子集表格数据
      educationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学历字典
      educationOptions: [],
      // 学习形式字典
      studyFormOptions: [],
      // 学位字典
      degreeOptions: [],
      // 参加工作时学历标识字典
      joinWorkFlagOptions: [],
      // 最高学历标识字典
      highestFlagOptions: [],
      // 毕业情况字典
      graduateConditionOptions: [],
      // 学位类别字典
      degreeClassOptions: [],
      // 是否全日制字典
      isFulltimeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        sortIndex: null,
        education: null,
        entranceDate: null,
        studyForm: null,
        graduateDate: null,
        graduateSchoolName: null,
        degree: null,
        awardDate: null,
        specialty: null,
        joinWorkFlag: null,
        highestFlag: null,
        graduateCondition: null,
        degreeClass: null,
        studySystem: null,
        majorType: null,
        isFulltime: null,
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
    this.getDicts("18").then(response => {
      this.educationOptions = response.data;
    });
    this.getDicts("19").then(response => {
      this.studyFormOptions = response.data;
    });
    this.getDicts("20").then(response => {
      this.degreeOptions = response.data;
    });
    this.getDicts("36").then(response => {
      this.joinWorkFlagOptions = response.data;
    });
    this.getDicts("36").then(response => {
      this.highestFlagOptions = response.data;
    });
    this.getDicts("22").then(response => {
      this.graduateConditionOptions = response.data;
    });
    this.getDicts("23").then(response => {
      this.degreeClassOptions = response.data;
    });
    this.getDicts("36").then(response => {
      this.isFulltimeOptions = response.data;
    });
  },
  methods: {
    /** 查询员工教育子集列表 */
    getList() {
      this.loading = true;
      listEducation(this.queryParams).then(response => {
        this.educationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 学历字典翻译
    educationFormat(row, column) {
      return this.selectDictLabel(this.educationOptions, row.education);
    },
    // 学习形式字典翻译
    studyFormFormat(row, column) {
      return this.selectDictLabel(this.studyFormOptions, row.studyForm);
    },
    // 学位字典翻译
    degreeFormat(row, column) {
      return this.selectDictLabel(this.degreeOptions, row.degree);
    },
    // 参加工作时学历标识字典翻译
    joinWorkFlagFormat(row, column) {
      return this.selectDictLabel(this.joinWorkFlagOptions, row.joinWorkFlag);
    },
    // 最高学历标识字典翻译
    highestFlagFormat(row, column) {
      return this.selectDictLabel(this.highestFlagOptions, row.highestFlag);
    },
    // 毕业情况字典翻译
    graduateConditionFormat(row, column) {
      return this.selectDictLabel(this.graduateConditionOptions, row.graduateCondition);
    },
    // 学位类别字典翻译
    degreeClassFormat(row, column) {
      return this.selectDictLabel(this.degreeClassOptions, row.degreeClass);
    },
    // 是否全日制字典翻译
    isFulltimeFormat(row, column) {
      return this.selectDictLabel(this.isFulltimeOptions, row.isFulltime);
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
        education: null,
        entranceDate: null,
        studyForm: null,
        graduateDate: null,
        graduateSchoolName: null,
        degree: null,
        awardDate: null,
        specialty: null,
        joinWorkFlag: null,
        highestFlag: null,
        graduateCondition: null,
        degreeClass: null,
        studySystem: null,
        majorType: null,
        isFulltime: null,
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
      this.title = "添加员工教育子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getEducation(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工教育子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateEducation(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addEducation(this.form).then(response => {
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
      this.$confirm('是否确认删除员工教育子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delEducation(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工教育子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportEducation(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
