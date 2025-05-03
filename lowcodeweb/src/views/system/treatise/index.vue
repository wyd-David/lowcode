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
      <el-form-item label="著作名称" prop="treatiseName">
        <el-input
          v-model="queryParams.treatiseName"
          placeholder="请输入论文著作名称"
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
          v-hasPermi="['system:treatise:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:treatise:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:treatise:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:treatise:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="treatiseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="是否合译" align="center" prop="translations" :formatter="translationsFormat" />
      <el-table-column label="论文著作名称" align="center" prop="treatiseName" />
      <el-table-column label="发表时间" align="center" prop="publishDate" />
      <el-table-column label="出版刊登者社或刊物名称" align="center" prop="publishOrgName" />
      <el-table-column label="论文著作标识" align="center" prop="thesisIdentifier" :formatter="thesisIdentifierFormat" />
      <el-table-column label="刊物级别" align="center" prop="publicationLevel" :formatter="publicationLevelFormat" />
      <el-table-column label="获奖及交流情况" align="center" prop="intercourseCircs" />
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
      <el-table-column label="操作" align="center" width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:treatise:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:treatise:remove']"
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

    <!-- 添加或修改员工论文著作子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
       <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="论文著作名称" prop="treatiseName">
          <el-input v-model="form.treatiseName" placeholder="请输入论文著作名称" />
        </el-form-item>
        <el-form-item label="刊物名称" prop="publishOrgName">
          <el-input v-model="form.publishOrgName" placeholder="请输入出版刊登者社或刊物名称" />
        </el-form-item>
        <el-form-item label="排序显示" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
       </el-col>
       <el-col :span="12">
         <el-form-item label="是否合译" prop="translations">
           <el-select v-model="form.translations" placeholder="请选择是否合译">
             <el-option
               v-for="dict in translationsOptions"
               :key="dict.dictValue"
               :label="dict.dictLabel"
               :value="parseInt(dict.dictValue)"
             ></el-option>
           </el-select>
         </el-form-item>
        <el-form-item label="发表时间" prop="publishDate">
          <el-input v-model="form.publishDate" placeholder="请输入发表时间" />
        </el-form-item>
        <el-form-item label="论文著作标识" prop="thesisIdentifier">
          <el-select v-model="form.thesisIdentifier" placeholder="请选择论文著作标识">
            <el-option
              v-for="dict in thesisIdentifierOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="刊物级别" prop="publicationLevel">
          <el-select v-model="form.publicationLevel" placeholder="请选择刊物级别">
            <el-option
              v-for="dict in publicationLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
       </el-col>
       <el-col :span="24">
        <el-form-item label="获奖交流情况" prop="intercourseCircs">
          <el-input v-model="form.intercourseCircs" type="textarea" maxlength="200" show-word-limit placeholder="请输入获奖及交流情况" />
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
import { listTreatise, getTreatise, delTreatise, addTreatise, updateTreatise, exportTreatise } from "@/api/system/treatise";

export default {
  name: "Treatise",
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
      // 员工论文著作子集表格数据
      treatiseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 合字典
      translationsOptions: [],
      // 论文著作标识字典
      thesisIdentifierOptions: [],
      // 刊物级别字典
      publicationLevelOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        sortIndex: null,
        translations: null,
        treatiseName: null,
        publishDate: null,
        publishOrgName: null,
        thesisIdentifier: null,
        publicationLevel: null,
        intercourseCircs: null,
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
    this.getDicts("36").then(response => {
      this.translationsOptions = response.data;
    });
    this.getDicts("63").then(response => {
      this.thesisIdentifierOptions = response.data;
    });
    this.getDicts("64").then(response => {
      this.publicationLevelOptions = response.data;
    });
  },
  methods: {
    /** 查询员工论文著作子集列表 */
    getList() {
      this.loading = true;
      listTreatise(this.queryParams).then(response => {
        this.treatiseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 合字典翻译
    translationsFormat(row, column) {
      return this.selectDictLabel(this.translationsOptions, row.translations);
    },
    // 论文著作标识字典翻译
    thesisIdentifierFormat(row, column) {
      return this.selectDictLabel(this.thesisIdentifierOptions, row.thesisIdentifier);
    },
    // 刊物级别字典翻译
    publicationLevelFormat(row, column) {
      return this.selectDictLabel(this.publicationLevelOptions, row.publicationLevel);
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
        translations: null,
        treatiseName: null,
        publishDate: null,
        publishOrgName: null,
        thesisIdentifier: null,
        publicationLevel: null,
        intercourseCircs: null,
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
      this.title = "添加员工论文著作子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getTreatise(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工论文著作子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateTreatise(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTreatise(this.form).then(response => {
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
      this.$confirm('是否确认删除员工论文著作子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTreatise(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工论文著作子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTreatise(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
