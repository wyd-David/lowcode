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
          v-hasPermi="['system:abroad:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:abroad:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:abroad:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:abroad:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="abroadList" @selection-change="handleSelectionChange">
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
      <el-table-column label="前往国家(地区)" align="center" prop="abroadArea" />
      <el-table-column label="所去单位" align="center" prop="abroadOrg" />
      <el-table-column label="团组名称" width="200px" align="center" prop="groupName" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="出国日期"  align="center" prop="abroadDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.abroadDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="经费来源" align="center" prop="foundSource" :formatter="foundSourceFormat" />
      <el-table-column label="政审编号" align="center" prop="auditingNo" />
      <el-table-column label="出国任务" align="center" prop="abroadTask" />
      <el-table-column label="团组类别" align="center" prop="groupType" :formatter="groupTypeFormat" />
      <el-table-column label="境外停留天数" align="center" prop="stayDays" />
      <el-table-column label="回国鉴定" align="center" prop="repatriateIdentify" />
      <el-table-column label="政审有效期" align="center" prop="auditingValidTo" />
      <el-table-column label="团组性质" align="center" prop="groupProperty" />
      <el-table-column label="回国日期" align="center" prop="repatriateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.repatriateDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="政审记录" align="center" prop="auditingRecord" :formatter="auditingRecordFormat" />
      <el-table-column label="其它国家名称" align="center" prop="otherCountryName" />
      <el-table-column label="备案日期" align="center" prop="recordDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="异常情况" align="center" prop="abnormityCircs" />
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
            v-hasPermi="['system:abroad:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:abroad:remove']"
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

    <!-- 添加或修改员工出国出境子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
       <el-form-item label="境外停留天数" prop="stayDays">
         <el-input-number v-model="form.stayDays" placeholder="请输入境外停留天数"></el-input-number>
       </el-form-item>
       <el-form-item label="回国鉴定" prop="repatriateIdentify">
         <el-input v-model="form.repatriateIdentify" placeholder="请输入回国鉴定" />
       </el-form-item>
       <el-form-item label="政审有效期" prop="auditingValidTo">
         <el-input v-model="form.auditingValidTo" placeholder="请输入政审有效期" />
       </el-form-item>
       <el-form-item label="团组性质" prop="groupProperty">
         <el-input v-model="form.groupProperty" placeholder="请输入团组性质" />
       </el-form-item>
        <el-form-item label="前往国家" prop="abroadArea">
          <el-input v-model="form.abroadArea" placeholder="请输入前往国家(地区) 数据字典" />
        </el-form-item>
        <el-form-item label="团组名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入团组名称" />
        </el-form-item>
     <el-form-item label="其它国家名称" prop="otherCountryName">
       <el-input v-model="form.otherCountryName" placeholder="请输入其它国家名称" />
     </el-form-item>
        <el-form-item label="政审编号" prop="auditingNo">
          <el-input v-model="form.auditingNo" placeholder="请输入政审编号" />
        </el-form-item>
       </el-col>
       <el-col :span="12">

        <el-form-item label="团组类别" prop="groupType">
          <el-select v-model="form.groupType" placeholder="请选择团组类别 数据字典">
            <el-option
              v-for="dict in groupTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
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
        <el-form-item label="排序显示" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
        <el-form-item label="出国日期" prop="abroadDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.abroadDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出国日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="回国日期" prop="repatriateDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.repatriateDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择回国日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="政审记录" prop="auditingRecord">
          <el-select v-model="form.auditingRecord" placeholder="请选择政审记录 数据字典">
            <el-option
              v-for="dict in auditingRecordOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经费来源" prop="foundSource">
          <el-select v-model="form.foundSource" placeholder="请选择经费来源 数据字典">
            <el-option
              v-for="dict in foundSourceOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备案日期" prop="recordDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.recordDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择备案日期">
          </el-date-picker>
        </el-form-item>
       </el-col>
        <el-col :span="24">
        <el-form-item label="异常情况" prop="abnormityCircs">
          <el-input v-model="form.abnormityCircs" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="所去单位" prop="abroadOrg">
          <el-input v-model="form.abroadOrg" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="出国任务" prop="abroadTask">
          <el-input v-model="form.abroadTask" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
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
import { listAbroad, getAbroad, delAbroad, addAbroad, updateAbroad, exportAbroad } from "@/api/system/abroad";

export default {
  name: "Abroad",
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
      // 员工出国出境子集表格数据
      abroadList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 经费来源 数据字典字典
      foundSourceOptions: [],
      // 团组类别 数据字典字典
      groupTypeOptions: [],
      // 政审记录 数据字典字典
      auditingRecordOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        startDate: null,
        endDate: null,
        sortIndex: null,
        abroadDate: null,
        abroadArea: null,
        abroadOrg: null,
        groupName: null,
        foundSource: null,
        auditingNo: null,
        abroadTask: null,
        groupType: null,
        stayDays: null,
        repatriateIdentify: null,
        auditingValidTo: null,
        groupProperty: null,
        repatriateDate: null,
        auditingRecord: null,
        otherCountryName: null,
        recordDate: null,
        abnormityCircs: null,
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
    this.getDicts("53").then(response => {
      this.foundSourceOptions = response.data;
    });
    this.getDicts("54").then(response => {
      this.groupTypeOptions = response.data;
    });
    this.getDicts("55").then(response => {
      this.auditingRecordOptions = response.data;
    });
  },
  methods: {
    /** 查询员工出国出境子集列表 */
    getList() {
      this.loading = true;
      listAbroad(this.queryParams).then(response => {
        this.abroadList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 经费来源 数据字典字典翻译
    foundSourceFormat(row, column) {
      return this.selectDictLabel(this.foundSourceOptions, row.foundSource);
    },
    // 团组类别 数据字典字典翻译
    groupTypeFormat(row, column) {
      return this.selectDictLabel(this.groupTypeOptions, row.groupType);
    },
    // 政审记录 数据字典字典翻译
    auditingRecordFormat(row, column) {
      return this.selectDictLabel(this.auditingRecordOptions, row.auditingRecord);
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
        abroadDate: null,
        abroadArea: null,
        abroadOrg: null,
        groupName: null,
        foundSource: null,
        auditingNo: null,
        abroadTask: null,
        groupType: null,
        stayDays: null,
        repatriateIdentify: null,
        auditingValidTo: null,
        groupProperty: null,
        repatriateDate: null,
        auditingRecord: null,
        otherCountryName: null,
        recordDate: null,
        abnormityCircs: null,
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
      this.title = "添加员工出国出境子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getAbroad(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工出国出境子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateAbroad(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAbroad(this.form).then(response => {
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
      this.$confirm('是否确认删除员工出国出境子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAbroad(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工出国出境子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAbroad(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
