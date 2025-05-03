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
      <el-form-item label="移动电话" prop="mobilePhone">
        <el-input
          v-model="queryParams.mobilePhone"
          placeholder="请输入移动电话"
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
          v-hasPermi="['system:abode:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:abode:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:abode:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:abode:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="abodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="本移动电话"  width="100px" align="center" prop="mobilePhone" />
      <el-table-column label="家庭联系电话" width="100px" align="center" prop="familyPhone" />
      <el-table-column label="办公联系电话"  width="100px"  align="center" prop="workPhone" />
      <el-table-column label="EMAIL邮箱"  width="100px"  align="center" prop="email" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="国家"  width="100px"  align="center" prop="country" />
      <el-table-column label="省"  width="100px"  align="center" prop="province" />
      <el-table-column label="市"  width="100px"  align="center" prop="city" />
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
      <el-table-column label="邮编"  width="100px"  align="center" prop="zipcode" />
      <el-table-column label="户口本地址"  width="100px"  align="center" prop="residenceAddress" />
      <el-table-column label="家庭地址"  width="100px"  align="center" prop="faminlyAddress" />
      <el-table-column label="操作" align="center" width="100px"  class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:abode:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:abode:remove']"
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

    <!-- 添加或修改员工家庭住址子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
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
          <el-form-item label="排序显示" prop="sortIndex">
              <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
          </el-form-item>

          <el-form-item label="移动电话" prop="mobilePhone">
            <el-input v-model="form.mobilePhone" placeholder="请输入本人移动电话" />
          </el-form-item>
          <el-form-item label="家庭电话" prop="familyPhone">
            <el-input v-model="form.familyPhone" placeholder="请输入家庭联系电话" />
          </el-form-item>
          <el-form-item label="办公电话" prop="workPhone">
            <el-input v-model="form.workPhone" placeholder="请输入办公联系电话" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入EMAIL邮箱" />
          </el-form-item>
          <el-form-item label="结束日期" prop="endDate">
            <el-date-picker clearable size="small" style="width: 200px"
              v-model="form.endDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="邮编" prop="zipcode">
            <el-input v-model="form.zipcode" placeholder="请输入邮编" />
          </el-form-item>
          <el-form-item label="国家" prop="country">
            <el-input v-model="form.country" placeholder="请输入国家" />
          </el-form-item>
          <el-form-item label="省" prop="province">
            <el-input v-model="form.province" placeholder="请输入省" />
          </el-form-item>
          <el-form-item label="市" prop="city">
            <el-input v-model="form.city" placeholder="请输入市" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="户口地址" prop="residenceAddress">
            <el-input v-model="form.residenceAddress" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
          </el-form-item>
          <el-form-item label="家庭地址" prop="faminlyAddress">
            <el-input v-model="form.faminlyAddress" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
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
import { listAbode, getAbode, delAbode, addAbode, updateAbode, exportAbode } from "@/api/system/abode";

export default {
  name: "Abode",
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
      // 员工家庭住址子集表格数据
      abodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        startDate: null,
        endDate: null,
        sortIndex: null,
        residenceAddress: null,
        zipcode: null,
        mobilePhone: null,
        familyPhone: null,
        workPhone: null,
        email: null,
        faminlyAddress: null,
        country: null,
        province: null,
        city: null,
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
  },
  methods: {
    /** 查询员工家庭住址子集列表 */
    getList() {
      this.loading = true;
      listAbode(this.queryParams).then(response => {
        this.abodeList = response.rows;
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
        residenceAddress: null,
        zipcode: null,
        mobilePhone: null,
        familyPhone: null,
        workPhone: null,
        email: null,
        faminlyAddress: null,
        country: null,
        province: null,
        city: null,
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
      this.title = "添加员工家庭住址子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getAbode(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工家庭住址子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateAbode(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAbode(this.form).then(response => {
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
      this.$confirm('是否确认删除员工家庭住址子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAbode(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工家庭住址子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAbode(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
