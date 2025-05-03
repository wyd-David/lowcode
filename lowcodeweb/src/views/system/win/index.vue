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
      <el-form-item label="奖罚类型" prop="hortationClass">
        <el-select v-model="queryParams.hortationClass" placeholder="请选择奖罚类型" clearable size="small">
          <el-option
            v-for="dict in hortationClassOptions"
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
          v-hasPermi="['system:win:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:win:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:win:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:win:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="winList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序" align="center" prop="sortIndex" />
      <el-table-column label="奖惩批准时间" align="center" prop="confirmDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.confirmDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="奖励类别" width="200px" align="center" prop="hortationType" :formatter="hortationTypeFormat" />
      <el-table-column label="奖励原因" align="center" prop="hortationReason" />
      <el-table-column label="奖励时间" align="center" prop="hortationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hortationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="奖励惩罚批准机关" width="200px" align="center" prop="confirmOrg" />
      <el-table-column label="奖罚类型"  width="200px" align="center" prop="hortationClass" :formatter="hortationClassFormat" />
      <el-table-column label="奖励内容" width="200px" align="center" prop="hortationContent" />
      <el-table-column label="惩罚时间"  align="center" prop="punishDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.punishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="惩罚类别" width="200px" align="center" prop="punishType" :formatter="punishTypeFormat" />
      <el-table-column label="惩罚原因" width="200px" align="center" prop="punishReason" />
      <el-table-column label="惩罚内容" width="200px" align="center" prop="punishContent" />
      <el-table-column label="处罚撤消时间" align="center" prop="cancelPunishDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cancelPunishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
      <el-table-column label="操作" width="100px"  align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:win:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:win:remove']"
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

    <!-- 添加或修改员工奖惩子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="奖惩批准时间" prop="confirmDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.confirmDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择奖惩批准时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="奖励类别" prop="hortationType">
          <el-select v-model="form.hortationType" placeholder="请选择奖励类别">
            <el-option
              v-for="dict in hortationTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="奖励时间" prop="hortationDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.hortationDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择奖励时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="奖罚类型" prop="hortationClass">
          <el-select v-model="form.hortationClass" placeholder="请选择奖罚类型">
            <el-option
              v-for="dict in hortationClassOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="惩罚时间" prop="punishDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.punishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择惩罚时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处罚撤消时间" prop="cancelPunishDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.cancelPunishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择处罚撤消时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="惩罚类别" prop="punishType">
          <el-select v-model="form.punishType" placeholder="请选择惩罚类别">
            <el-option
              v-for="dict in punishTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
        <el-form-item label="奖励惩罚机关" prop="confirmOrg">
          <el-input v-model="form.confirmOrg" placeholder="请输入奖励惩罚批准机关" />
        </el-form-item>
        <el-form-item label="奖励原因" prop="hortationReason">
          <el-input v-model="form.hortationReason" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="奖励内容">
          <el-input v-model="form.hortationContent" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="惩罚原因" prop="punishReason">
          <el-input v-model="form.punishReason" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="惩罚内容" prop="punishContent">
          <el-input v-model="form.punishContent" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
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
import { listWin, getWin, delWin, addWin, updateWin, exportWin } from "@/api/system/win";
import Editor from '@/components/Editor';

export default {
  name: "Win",
  components: { Editor },
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
      // 员工奖惩子集表格数据
      winList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 奖励类别字典
      hortationTypeOptions: [],
      // 奖罚类型字典
      hortationClassOptions: [],
      // 惩罚类别字典
      punishTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        sortIndex: null,
        confirmDate: null,
        hortationType: null,
        hortationReason: null,
        hortationDate: null,
        confirmOrg: null,
        hortationClass: null,
        hortationContent: null,
        punishDate: null,
        punishType: null,
        punishReason: null,
        punishContent: null,
        cancelPunishDate: null,
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
    this.getDicts("41").then(response => {
      this.hortationTypeOptions = response.data;
    });
    this.getDicts("42").then(response => {
      this.hortationClassOptions = response.data;
    });
    this.getDicts("43").then(response => {
      this.punishTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询员工奖惩子集列表 */
    getList() {
      this.loading = true;
      listWin(this.queryParams).then(response => {
        this.winList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 奖励类别字典翻译
    hortationTypeFormat(row, column) {
      return this.selectDictLabel(this.hortationTypeOptions, row.hortationType);
    },
    // 奖罚类型字典翻译
    hortationClassFormat(row, column) {
      return this.selectDictLabel(this.hortationClassOptions, row.hortationClass);
    },
    // 惩罚类别字典翻译
    punishTypeFormat(row, column) {
      return this.selectDictLabel(this.punishTypeOptions, row.punishType);
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
        confirmDate: null,
        hortationType: null,
        hortationReason: null,
        hortationDate: null,
        confirmOrg: null,
        hortationClass: null,
        hortationContent: null,
        punishDate: null,
        punishType: null,
        punishReason: null,
        punishContent: null,
        cancelPunishDate: null,
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
      this.title = "添加员工奖惩子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getWin(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工奖惩子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateWin(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWin(this.form).then(response => {
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
      this.$confirm('是否确认删除员工奖惩子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delWin(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工奖惩子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportWin(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
