<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型代码" prop="typeCode">
        <el-input
          v-model="queryParams.typeCode"
          placeholder="请输入类型代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据源名"  prop="datasourceName">
        <el-input
          v-model="queryParams.datasourceName"
          placeholder="请输入数据源名称"
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
          v-hasPermi="['system:datasource:add']"
        >重新抓取</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:datasource:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:datasource:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:datasource:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="datasourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="数据源ID" align="center" prop="datasourceId" />
      <el-table-column label="类型代码" align="center" prop="typeCode" />
      <el-table-column label="数据源编码" align="center" v-if="colshow" prop="datasourceCode" />
      <el-table-column label="数据源名称" align="center" prop="datasourceName" />
      <el-table-column label="说明" align="center" prop="description" />
      <el-table-column label="创建时间" align="center"  prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
     
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:datasource:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:datasource:remove']"
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

    <!-- 添加或修改数据源对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型代码" prop="typeCode">
          <el-input v-model="form.typeCode" readonly placeholder="请输入类型代码" />
        </el-form-item>
        <el-form-item label="数据源名称"   prop="datasourceName">
          <el-input v-model="form.datasourceName" readonly placeholder="请输入数据源名称" />
        </el-form-item>
        <el-form-item label="说明" prop="description">
          <el-input v-model="form.description" placeholder="请输入说明" />
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
import { listDatasource, getDatasource, delDatasource, addDatasource, updateDatasource, exportDatasource } from "@/api/system/metadatasource";

export default {
  name: "Datasource",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //是否显示列
      colshow:false,
      //是否只读
      readonly: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 数据源表格数据
      datasourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        typeCode: null,
        datasourceCode: null,
        datasourceName: null,
        description: null,
        creatorId: null,
        creatorName: null,
        createTime: null,
        platformId: null,
        manufacturerId: null,
        classificationCode: null,
        sourceCode: null,
        layerId: null,
        tenantId: null,
        status: null,
        version: null,
        orgid: null,
        isRepository: null,
        cancelStatistic: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        datasourceCode: [
          { required: true, message: "数据源编码不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询数据源列表 */
    getList() {
      this.loading = true;
      listDatasource(this.queryParams).then(response => {
        this.datasourceList = response.rows;
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
        datasourceId: null,
        typeCode: null,
        datasourceCode: null,
        datasourceName: null,
        description: null,
        creatorId: null,
        creatorName: null,
        createTime: null,
        platformId: null,
        manufacturerId: null,
        classificationCode: null,
        sourceCode: null,
        layerId: null,
        tenantId: null,
        status: "0",
        version: null,
        orgid: null,
        isRepository: null,
        cancelStatistic: null
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
      this.ids = selection.map(item => item.datasourceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
     this.$confirm('是否确认重新抓取数据池中的所有数据源?', "警告", {
         confirmButtonText: "确定",
         cancelButtonText: "取消",
         type: "warning"
       }).then(function() {
         return addDatasource();
       }).then(() => {
         this.getList();
         this.msgSuccess("抓取成功");
       }).catch(function() {});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const datasourceId = row.datasourceId || this.ids
      getDatasource(datasourceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据源";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.datasourceId != null) {
            updateDatasource(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDatasource(this.form).then(response => {
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
      debugger
      const datasourceIds = row.datasourxceId || this.ids;
      const datasourceNames = row.datasourceName || this.ids;
      this.$confirm('是否确认删除数据源编号为"' + datasourceNames + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDatasource(datasourceIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出数据源数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDatasource(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
