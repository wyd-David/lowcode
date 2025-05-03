<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="数据源" prop="datasourceId">
          <el-select v-model="queryParams.datasourceId" @change="currentSel" placeholder="数据源" clearable size="small">
            <el-option
              v-for="dict in datasourceIds"
              :key="dict.DATASOURCE_ID"
              :label="dict.DATASOURCE_NAME"
              :value="dict.DATASOURCE_ID"
            />
          </el-select>
      </el-form-item>

      <el-form-item label="资源代码" prop="resourceCode">
        <el-input
          v-model="queryParams.resourceCode"
          placeholder="请输入资源代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资源名称"  prop="resourceName">
        <el-input
          v-model="queryParams.resourceName"
          placeholder="请输入资源名称"
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
          v-hasPermi="['system:resource:add']"
        >重新抓取</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:resource:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:resource:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-share"
          size="mini"
          :disabled="multiple"
          @click="handleBuild"
          v-hasPermi="['system:resource:add']"
        >代码构建</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:resource:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="resourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="数据资源ID" align="center" prop="resourceId" />
      <el-table-column label="资源代码" align="center" prop="resourceCode" />
      <el-table-column label="资源名称" align="center" prop="resourceName" />
      <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:resource:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-share"
            @click="handleBuild(scope.row)"
            v-hasPermi="['system:resource:edit']"
          >构建</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:resource:remove']"
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

    <!-- 添加或修改数据资源对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="资源代码" prop="resourceCode">
          <el-input v-model="form.resourceCode" placeholder="请输入资源代码" />
        </el-form-item>
        <el-form-item label="数据资源名称" prop="resourceName">
          <el-input v-model="form.resourceName" placeholder="请输入数据资源名称" />
        </el-form-item>
        <el-form-item label="数据资源描述" prop="resourceDesp">
          <el-input v-model="form.resourceDesp" placeholder="请输入数据资源描述" />
        </el-form-item>
        <el-form-item label="资源类型" prop="resourceType">
          <el-select v-model="form.resourceType" placeholder="请选择资源类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据源ID" prop="datasourceId">
          <el-input v-model="form.datasourceId" placeholder="请输入数据源ID" />
        </el-form-item>
        <el-form-item label="状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开放状态：   opened：完全公开，needAuthorized：需要审批">
          <el-radio-group v-model="form.openedStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="数据安全分级" prop="securityGradeId">
          <el-input v-model="form.securityGradeId" placeholder="请输入数据安全分级" />
        </el-form-item>
        <el-form-item label="数据更新周期编码" prop="dataCycleId">
          <el-input v-model="form.dataCycleId" placeholder="请输入数据更新周期编码" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.publishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后更新时间" prop="lastUpdateTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.lastUpdateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择最后更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建者身份标识" prop="createUserId">
          <el-input v-model="form.createUserId" placeholder="请输入创建者身份标识" />
        </el-form-item>
        <el-form-item label="创建者名称" prop="createUserName">
          <el-input v-model="form.createUserName" placeholder="请输入创建者名称" />
        </el-form-item>
        <el-form-item label="创建者租户id" prop="createTenantId">
          <el-input v-model="form.createTenantId" placeholder="请输入创建者租户id" />
        </el-form-item>
        <el-form-item label="版本描述，默认1.0" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本描述，默认1.0" />
        </el-form-item>
        <el-form-item label="版本描述" prop="versionDesp">
          <el-input v-model="form.versionDesp" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否已导入平台索引库，默认否。1：是；0：否；" prop="isIndexCreated">
          <el-input v-model="form.isIndexCreated" placeholder="请输入是否已导入平台索引库，默认否。1：是；0：否；" />
        </el-form-item>
        <el-form-item label="采集状态">
          <el-radio-group v-model="form.collectStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集" prop="productionMode">
          <el-input v-model="form.productionMode" placeholder="请输入数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集" />
        </el-form-item>
        <el-form-item label="数据种类" prop="dataKind">
          <el-input v-model="form.dataKind" placeholder="请输入数据种类" />
        </el-form-item>
        <el-form-item label="数据结构类型" prop="structuredType">
          <el-select v-model="form.structuredType" placeholder="请选择数据结构类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="所属组织" prop="orgId">
          <el-input v-model="form.orgId" placeholder="请输入所属组织" />
        </el-form-item>
        <el-form-item label="所属人员" prop="ownUserId">
          <el-input v-model="form.ownUserId" placeholder="请输入所属人员" />
        </el-form-item>
        <el-form-item label="数据管理者" prop="dataManagerId">
          <el-input v-model="form.dataManagerId" placeholder="请输入数据管理者" />
        </el-form-item>
        <el-form-item label="同义词名称，多个用英文逗号间隔" prop="sameName">
          <el-input v-model="form.sameName" placeholder="请输入同义词名称，多个用英文逗号间隔" />
        </el-form-item>
        <el-form-item label="所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典" prop="dataLayer">
          <el-input v-model="form.dataLayer" placeholder="请输入所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典" />
        </el-form-item>
        <el-form-item label="是否删除" prop="isDeleted">
          <el-input v-model="form.isDeleted" placeholder="请输入是否删除" />
        </el-form-item>
        <el-form-item label="分析状态 未分析not_analyzed,分析中analyzing,已分析analyzed,分析失败failed">
          <el-radio-group v-model="form.analysisStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序字段" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序字段" />
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
import { listResource, getResource, delResource, addResource, updateResource, exportResource ,handleBuild} from "@/api/system/resource";
export default {
  name: "Resource",
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
      //数据源类型
      datasourceIds: [],
      // 数据资源表格数据
      resourceList: [],
      //查询的条件
      queryDatasourceId: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        resourceCode: null,
        resourceName: null,
        resourceDesp: null,
        resourceType: null,
        datasourceId: null,
        status: null,
        openedStatus: null,
        securityGradeId: null,
        dataCycleId: null,
        publishTime: null,
        lastUpdateTime: null,
        createUserId: null,
        createUserName: null,
        createTenantId: null,
        version: null,
        versionDesp: null,
        isIndexCreated: null,
        collectStatus: null,
        productionMode: null,
        dataKind: null,
        structuredType: null,
        orgId: null,
        ownUserId: null,
        dataManagerId: null,
        sameName: null,
        dataLayer: null,
        isDeleted: null,
        analysisStatus: null,
        sort: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        status: [
          { required: true, message: "状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除不能为空", trigger: "blur" }
        ],
        openedStatus: [
          { required: true, message: "开放状态：   opened：完全公开，needAuthorized：需要审批不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCustomizeData("datasourceId").then(response => {
      this.datasourceIds = response.data;
    });
  },
  methods: {
    /** 查询数据资源列表 */
    getList() {
      this.loading = true;
      listResource(this.queryParams).then(response => {
        this.resourceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取选择的数据源
    currentSel(selVal) {
     this.$data.queryDatasourceId=selVal;
    	},
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        resourceId: null,
        resourceCode: null,
        resourceName: null,
        resourceDesp: null,
        resourceType: null,
        datasourceId: null,
        status: "0",
        openedStatus: "0",
        securityGradeId: null,
        dataCycleId: null,
        createTime: null,
        publishTime: null,
        lastUpdateTime: null,
        createUserId: null,
        createUserName: null,
        createTenantId: null,
        version: null,
        versionDesp: null,
        isIndexCreated: null,
        collectStatus: "0",
        productionMode: null,
        dataKind: null,
        structuredType: null,
        orgId: null,
        ownUserId: null,
        dataManagerId: null,
        sameName: null,
        dataLayer: null,
        isDeleted: null,
        analysisStatus: "0",
        sort: null
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
      this.ids = selection.map(item => item.resourceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      const d=this.$data.queryDatasourceId;
      console.log(d);
      if(d==null){
        this.msgInfo("请选择数据源");
      }else{
        this.$confirm('是否确认重新抓取数据资源的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return addResource(d);
          }).then(() => {
            this.getList();
            this.msgSuccess("抓取成功");
          }).catch(function() {});
      }

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const resourceId = row.resourceId || this.ids
      getResource(resourceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据资源";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.resourceId != null) {
            updateResource(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addResource(this.form).then(response => {
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
      const resourceIds = row.resourceId || this.ids;
      const resourceNames = row.resourceId || this.ids;
      this.$confirm('是否确认删除数据资源编号为"' + resourceNames + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delResource(resourceIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 代码构建按钮操作 */
    handleBuild(row) {
      const resourceIds = row.resourceId || this.ids;
      const resourceNames = row.resourceName || this.ids;
      this.$confirm('是否确认构建数据资源编号为"' + resourceNames + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return handleBuild(resourceIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("构建成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出数据资源数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportResource(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
