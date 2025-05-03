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
          v-hasPermi="['system:socity:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:socity:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:socity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:socity:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="socityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="员工ID" align="center" prop="empId" />
      <el-table-column label="排序显示" align="center" prop="sortIndex" />
      <el-table-column label="成员学历" align="center" prop="memberEducation" :formatter="memberEducationFormat" />
      <el-table-column label="成员姓名" align="center" prop="memberName" />
      <el-table-column label="与本人的关系" align="center" prop="relationWith" :formatter="relationWithFormat" />
      <el-table-column label="成员出生日期" align="center" prop="memberBirthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.memberBirthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="成员所在单位" align="center" prop="memberInOrg" />
      <el-table-column label="成员政治面貌" align="center" prop="memberPolityFace" :formatter="memberPolityFaceFormat" />
      <el-table-column label="成员民族" align="center" prop="memberFolk" :formatter="memberFolkFormat" />
      <el-table-column label="职位职务" align="center" prop="headship" />
      <el-table-column label="成员性别" align="center" prop="memberSex" :formatter="memberSexFormat" />
      <el-table-column label="是否紧急情况第一联系人" align="center" prop="isEmergencyFirstLinkman" :formatter="isEmergencyFirstLinkmanFormat" />
      <el-table-column label="成员联系电话" align="center" prop="memberPhone" />
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
            v-hasPermi="['system:socity:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:socity:remove']"
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

    <!-- 添加或修改员工家庭关系子集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
       <el-col :span="12">
        <el-form-item label="员工ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="成员姓名" prop="memberName">
          <el-input v-model="form.memberName" placeholder="请输入成员姓名" />
        </el-form-item>
        <el-form-item label="所在单位" prop="memberInOrg">
          <el-input v-model="form.memberInOrg" placeholder="请输入成员所在单位" />
        </el-form-item>
        <el-form-item label="联系电话" prop="memberPhone">
          <el-input v-model="form.memberPhone" placeholder="请输入成员联系电话" />
        </el-form-item>
        <el-form-item label="排序显示" prop="sortIndex">
          <el-input-number v-model="form.sortIndex" placeholder="请输入排序显示"></el-input-number>
        </el-form-item>
        <el-form-item label="成员学历" prop="memberEducation">
          <el-select v-model="form.memberEducation" placeholder="请选择成员学历">
            <el-option
              v-for="dict in memberEducationOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="关系" prop="relationWith">
          <el-select v-model="form.relationWith" filterable placeholder="请选择与本人的关系">
            <el-option
              v-for="dict in relationWithOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="memberBirthday">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.memberBirthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择成员出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="政治面貌" prop="memberPolityFace">
          <el-select v-model="form.memberPolityFace" placeholder="请选择成员政治面貌">
            <el-option
              v-for="dict in memberPolityFaceOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="成员民族" prop="memberFolk">
          <el-select v-model="form.memberFolk" placeholder="请选择成员民族">
            <el-option
              v-for="dict in memberFolkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="成员性别" prop="memberSex">
          <el-select v-model="form.memberSex" placeholder="请选择成员性别">
            <el-option
              v-for="dict in memberSexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系人" prop="isEmergencyFirstLinkman">
          <el-select v-model="form.isEmergencyFirstLinkman" placeholder="请选择是否紧急情况第一联系人">
            <el-option
              v-for="dict in isEmergencyFirstLinkmanOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        </el-col>
         <el-col :span="24">
        <el-form-item label="职位职务" prop="headship">
          <el-input v-model="form.headship" placeholder="请输入职位职务" />
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
import { listSocity, getSocity, delSocity, addSocity, updateSocity, exportSocity } from "@/api/system/socity";

export default {
  name: "Socity",
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
      // 员工家庭关系子集表格数据
      socityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 成员学历字典
      memberEducationOptions: [],
      // 与本人的关系字典
      relationWithOptions: [],
      // 成员政治面貌字典
      memberPolityFaceOptions: [],
      // 成员民族字典
      memberFolkOptions: [],
      // 成员性别字典
      memberSexOptions: [],
      // 是否紧急情况第一联系人字典
      isEmergencyFirstLinkmanOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        sortIndex: null,
        memberEducation: null,
        memberName: null,
        relationWith: null,
        memberBirthday: null,
        memberInOrg: null,
        memberPolityFace: null,
        memberFolk: null,
        headship: null,
        memberSex: null,
        isEmergencyFirstLinkman: null,
        memberPhone: null,
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
      this.memberEducationOptions = response.data;
    });
    this.getDicts("58").then(response => {
      this.relationWithOptions = response.data;
    });
    this.getDicts("USER_RELIGION").then(response => {
      this.memberPolityFaceOptions = response.data;
    });
    this.getDicts("USER_NATION").then(response => {
      this.memberFolkOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.memberSexOptions = response.data;
    });
    this.getDicts("36").then(response => {
      this.isEmergencyFirstLinkmanOptions = response.data;
    });
  },
  methods: {
    /** 查询员工家庭关系子集列表 */
    getList() {
      this.loading = true;
      listSocity(this.queryParams).then(response => {
        this.socityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 成员学历字典翻译
    memberEducationFormat(row, column) {
      return this.selectDictLabel(this.memberEducationOptions, row.memberEducation);
    },
    // 与本人的关系字典翻译
    relationWithFormat(row, column) {
      return this.selectDictLabel(this.relationWithOptions, row.relationWith);
    },
    // 成员政治面貌字典翻译
    memberPolityFaceFormat(row, column) {
      return this.selectDictLabel(this.memberPolityFaceOptions, row.memberPolityFace);
    },
    // 成员民族字典翻译
    memberFolkFormat(row, column) {
      return this.selectDictLabel(this.memberFolkOptions, row.memberFolk);
    },
    // 成员性别字典翻译
    memberSexFormat(row, column) {
      return this.selectDictLabel(this.memberSexOptions, row.memberSex);
    },
    // 是否紧急情况第一联系人字典翻译
    isEmergencyFirstLinkmanFormat(row, column) {
      return this.selectDictLabel(this.isEmergencyFirstLinkmanOptions, row.isEmergencyFirstLinkman);
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
        memberEducation: null,
        memberName: null,
        relationWith: null,
        memberBirthday: null,
        memberInOrg: null,
        memberPolityFace: null,
        memberFolk: null,
        headship: null,
        memberSex: null,
        isEmergencyFirstLinkman: null,
        memberPhone: null,
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
      this.title = "添加员工家庭关系子集";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recId = row.recId || this.ids
      getSocity(recId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工家庭关系子集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recId != null) {
            updateSocity(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSocity(this.form).then(response => {
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
      this.$confirm('是否确认删除员工家庭关系子集编号为"' + recIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSocity(recIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出员工家庭关系子集数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSocity(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
