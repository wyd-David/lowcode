<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="类型" prop="datasrouceType">
        <el-select v-model="queryParams.datasrouceType" placeholder="请选择数据库类型" clearable size="small">
          <el-option
            v-for="dict in datasrouceTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库名" prop="datasourceName">
        <el-input
          v-model="queryParams.datasourceName"
          placeholder="请输入数据库名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

     <!-- <el-form-item label="状态" prop="datasourceStatus">
        <el-select v-model="queryParams.datasourceStatus" placeholder="请选择数据库状态" clearable size="small">
          <el-option
            v-for="dict in datasourceStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item> -->
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
          v-hasPermi="['system:metadatasourcecustomer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:metadatasourcecustomer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:metadatasourcecustomer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:metadatasourcecustomer:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="metadatasourcecustomerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称"  width="100px" align="center" prop="customerName" />
     <!-- <el-table-column label="主键" align="center" prop="customerId" /> -->
      <el-table-column label="key" align="center" width="200px" prop="datasourceId" />
      <el-table-column label="类型" align="center" prop="datasrouceType" :formatter="datasrouceTypeFormat" />
      <el-table-column label="库名称" align="center" prop="datasourceName" />
      <el-table-column label="IP地址" align="center" prop="datasourceIp" />
      <el-table-column label="端口" align="center" prop="datasourcePort" />
      <el-table-column label="账号" align="center" prop="datasourceAccount" />
      <el-table-column label="密码" align="center" prop="datasourcePassword" />
      <el-table-column label="连接串" align="center" prop="datasourceUrl" />
      <el-table-column label="备注说明" align="center" prop="datasourceMemo" />
       <el-table-column label="所属用户" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="creteTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.creteTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="datasourceStatus" :formatter="datasourceStatusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:metadatasourcecustomer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:metadatasourcecustomer:remove']"
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

    <!-- 添加或修改用户注册数据源信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item
          v-hasPermi="['system:metadatasourcecustomer:export']"
         label="key" prop="datasourceId">
          <el-input v-model="form.datasourceId" placeholder="请输入数据源key" />
        </el-form-item>
        <el-form-item label="类型" prop="datasrouceType">
          <el-select v-model="form.datasrouceType" placeholder="请选择数据库类型">
            <el-option
              v-for="dict in datasrouceTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库名称" prop="datasourceName">
          <el-input v-model="form.datasourceName" placeholder="请输入数据库名称" />
        </el-form-item>
        <el-form-item label="IP地址" prop="datasourceIp">
          <el-input v-model="form.datasourceIp" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="端口" prop="datasourcePort">
          <el-input v-model="form.datasourcePort" placeholder="请输入数据源端口" />
        </el-form-item>
        <el-form-item label="账号" prop="datasourceAccount">
          <el-input v-model="form.datasourceAccount" placeholder="请输入数据库账号" />
        </el-form-item>
        <el-form-item label="密码" prop="datasourcePassword">
          <el-input v-model="form.datasourcePassword" placeholder="请输入数据库密码" />
        </el-form-item>
        <el-form-item label="连接串" prop="datasourceUrl">
          <el-input v-model="form.datasourceUrl" placeholder="请输入数据库连接串" />
        </el-form-item>
        <el-form-item label="备注说明" prop="datasourceMemo">
          <el-input v-model="form.datasourceMemo" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>

        <el-form-item
        v-hasPermi="['system:metadatasourcecustomer:export']"
        label="状态" prop="datasourceStatus">
          <el-select v-model="form.datasourceStatus" placeholder="请选择数据库状态">
            <el-option
              v-for="dict in datasourceStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import { listMetadatasourcecustomer, getMetadatasourcecustomer, delMetadatasourcecustomer, addMetadatasourcecustomer, updateMetadatasourcecustomer, exportMetadatasourcecustomer } from "@/api/system/metadatasourcecustomer";

export default {
  name: "Metadatasourcecustomer",
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
      // 用户注册数据源信息表格数据
      metadatasourcecustomerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 数据库类型字典
      datasrouceTypeOptions: [],
      // 数据库状态字典
      datasourceStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerName: null,
        datasourceId: null,
        datasrouceType: null,
        datasourceName: null,
        datasourceIp: null,
        datasourcePort: null,
        datasourceAccount: null,
        datasourcePassword: null,
        datasourceUrl: null,
        datasourceMemo: null,
        createId: null,
        createBy: null,
        creteTime: null,
        datasourceStatus: null
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
    this.getDicts("DATASROUCE_TYPE").then(response => {
      this.datasrouceTypeOptions = response.data;
    });
    this.getDicts("DATASOURCE_STATUS").then(response => {
      this.datasourceStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询用户注册数据源信息列表 */
    getList() {
      this.loading = true;
      listMetadatasourcecustomer(this.queryParams).then(response => {
        this.metadatasourcecustomerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 数据库类型字典翻译
    datasrouceTypeFormat(row, column) {
      return this.selectDictLabel(this.datasrouceTypeOptions, row.datasrouceType);
    },
    // 数据库状态字典翻译
    datasourceStatusFormat(row, column) {
      return this.selectDictLabel(this.datasourceStatusOptions, row.datasourceStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        customerName: null,
        customerId: null,
        datasourceId: null,
        datasrouceType: null,
        datasourceName: null,
        datasourceIp: null,
        datasourcePort: null,
        datasourceAccount: null,
        datasourcePassword: null,
        datasourceUrl: null,
        datasourceMemo: null,
        createId: null,
        createBy: null,
        creteTime: null,
        datasourceStatus: null
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
      this.ids = selection.map(item => item.customerId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户注册数据源信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const customerId = row.customerId || this.ids
      getMetadatasourcecustomer(customerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户注册数据源信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.customerId != null) {
            updateMetadatasourcecustomer(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addMetadatasourcecustomer(this.form).then(response => {
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
      const customerIds = row.customerId || this.ids;
      this.$confirm('是否确认删除用户注册数据源信息编号为"' + customerIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delMetadatasourcecustomer(customerIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出用户注册数据源信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportMetadatasourcecustomer(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
