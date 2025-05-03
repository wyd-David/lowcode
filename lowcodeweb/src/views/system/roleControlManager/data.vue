<template>
  <div class="app-container">
    <div
      class="search-folder mb12"
      ref="folder"
      :class="{ 'folder-open': isOpen }"
    >
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="120px"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户名"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <!-- <el-form-item label="作用域" prop="scope">
          <el-input
            v-model="queryParams.scope"
            placeholder="请输入作用域"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="用户部门名称" prop="userDeptName">
          <el-input
            v-model="queryParams.userDeptName"
            placeholder="请输入用户部门名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="queryParams.createTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标识 0删除1不删除" prop="delSign">
          <el-input
            v-model="queryParams.delSign"
            placeholder="请输入删除标识 0删除1不删除"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
      </el-form>

      <div class="search-folder-utils">
        <el-button type="primary" size="mini" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="mini" @click="resetQuery">重置</el-button>
        <!-- <p v-if="height > 34" @click="onHandle">
          {{ isOpen ? "收起" : "展开" }}
        </p>
        <span v-if="height > 34">
          <i
            class="el-icon-arrow-up ivu-icon"
            @click="onHandle"
            v-if="isOpen"
          ></i>
          <i class="el-icon-arrow-down ivu-icon" @click="onHandle" v-else></i>
        </span> -->
      </div>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:roleControlManager:add']"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:roleControlManager:edit']"
            >修改</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:roleControlManager:remove']"
            >删除</el-button
          >
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-lock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(1)"
            v-hasPermi="['system:roleControlManager:remove']"
            >禁用</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-unlock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(0)"
            v-hasPermi="['system:roleControlManager:remove']"
            >启用</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['system:roleControlManager:importData']"
            >导入</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:roleControlManager:importData']"
            >导出</el-button
          >
        </el-col> -->
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>

      <el-table
        v-loading="loading"
        :data="roleControlManagerList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="角色名称" align="center" prop="roleControlName" />
        <el-table-column label="ERP号" align="center" prop="erpid" />
        <el-table-column label="用户id" align="center" prop="userId" />
        <el-table-column label="用户登录id" align="center" prop="userLoginId" />
        <el-table-column label="用户名" align="center" prop="userName" />
        <el-table-column label="作用域" align="center" prop="scopeText" />
        <el-table-column
          label="用户部门名称"
          align="center"
          prop="userDeptName"
        />

        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <!-- <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleQueryOption(scope.row)"
              >查看</el-button
            > -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:roleControlManager:edit']"
              >修改</el-button
            >
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:roleControlManager:remove']"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 添加或修改 角色授权控制管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="员工" prop="userText">
          <el-select v-model="form.userText" filterable remote reserve-keyword placeholder="请输入员工名字"
                     :remote-method="remoteMethod" :loading="loading" @change="getUserByLoginId(form.userText)">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="dpname">
          <el-input v-model="form.dpname" :disabled="true" />
        </el-form-item>
        <el-form-item label="岗位" prop="position">
          <el-input v-model="form.position" :disabled="true" />
        </el-form-item>
        <el-form-item label="作用域" prop="deptId">
          <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择作用域" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="员工" prop="useName">
          <el-input
            v-model="form.userName"
            placeholder="请输入员工名称"
            clearable
            size="small"
            @input="getUserByLoginId(form.userName)"
          >
            <el-option
              v-for="item in FitnessWhiteUserList"
              :key="item.loginid"
              :label="item.username"
              :value="item.loginid"
            >
            </el-option>
          </el-input>
        </el-form-item>
        <el-form-item label="用户id" prop="loginid">
          <el-input v-model="form.loginid" :disabled="true" />
        </el-form-item>
       <el-form-item label="归属部门" prop="deptId">
          <!-- <treeselect
            v-model="form.deptId"
            :options="deptOptions"
            :show-count="true"
            placeholder="请选择归属部门"
          /> -->
<!--
          <el-cascader
            v-model="form.deptId"
            placeholder=""
            :options="deptOptions"
            style="width:100%"
            :props="{ value: 'id', label: 'label' }"
            size="small"
            filterable
            clearable
            ></el-cascader>
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="form.position" :disabled="true" />
        </el-form-item>
        <el-form-item label="部门名称" prop="dpname">
          <el-input
            v-model="form.dpname"
            :disabled="true"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog> -->
    <!-- 查看 角色授权控制管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="isOpenQuery"
      width="600px"
      append-to-body="append-to-body"
    >
      <el-form
        ref="queryform"
        :model="form"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="角色控制表id:" prop="roleControlId">
          {{ form.roleControlId }}
        </el-form-item>
        <el-form-item label="用户id:" prop="userId">
          {{ form.userId }}
        </el-form-item>
        <el-form-item label="用户登录id:" prop="userLoginId">
          {{ form.userLoginId }}
        </el-form-item>
        <el-form-item label="用户名:" prop="userName">
          {{ form.userName }}
        </el-form-item>
        <el-form-item label="作用域:" prop="scope">
          {{ form.scope }}
        </el-form-item>
        <el-form-item label="用户部门id:" prop="userDeptId">
          {{ form.userDeptId }}
        </el-form-item>
        <el-form-item label="用户部门名称:" prop="userDeptName">
          {{ form.userDeptName }}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!--  角色授权控制管理导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox
            v-model="upload.updateSupport"
          />是否更新已经存在的用户数据
          <el-link type="info" style="font-size: 12px" @click="importTemplate"
            >下载模板</el-link
          >
        </div>
        <div class="el-upload__tip" style="color: red" slot="tip">
          提示：仅允许导入“xls”或“xlsx”格式文件！
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listRoleControlManager,
  getRoleControlManager,
  delRoleControlManager,
  addRoleControlManager,
  updateRoleControlManager,
  exportRoleControlManager,
  importTemplate,
  disableEnable,
  getFitnessWhiteViewEmpInfo,
} from "@/api/system/roleControlManager";

import { formatDate } from "@/utils/index";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "RoleControlManager",
  components: { Treeselect },
  data() {
    return {
      // 搜索栏展示配置
      height: null,
      disabled: false,
      isOpen: true,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 部门树选项
      deptOptions: undefined,
      options: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //  角色授权控制管理表格数据
      roleControlManagerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url:
          process.env.VUE_APP_BASE_API +
          "/system/roleControlManager/importData",
      },
      formDept:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleControlManagerId: null,
        roleControlId: null,
        userId: null,
        userLoginId: null,
        userName: null,
        scope: null,
        userDeptId: null,
        userDeptName: null,
        createTime: null,
        delSign: null,
      },
      // 表单参数
      form: {
        userText: ''
      },
      //用户信息
      formUser: {
        loginid: null,
      },
      FitnessWhiteUserList:[],
      states: [],
      // 表单校验
      rules: {
        roleControlId: [
          { required: true, message: "角色控制表id不能为空", trigger: "blur" },
        ],
        userId: [
          { required: true, message: "用户id不能为空", trigger: "blur" },
        ],
        userLoginId: [
          { required: true, message: "用户登录id不能为空", trigger: "blur" },
        ],
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
        ],
        userText: [
          { required: true, message: "员工不能为空", trigger: "blur" },
        ],
        deptId: [
          { required: true, message: "作用域不能为空", trigger: "blur" },
        ],
        scope: [{ required: true, message: "作用域不能为空", trigger: "blur" }],
      },
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    this.getTreeselect();
    this.getFitnessWhiteViewEmpInfo();
    const id = this.$route.params.id;
    this.queryParams.roleControlId = id;
    this.getList();
  },
  mounted() {
    this.height = this.$refs.folder.clientHeight;
    if (this.height > 32) {
      this.isOpen = false;
    }
  },
  methods: {
    /** 查询 角色授权控制管理列表 */
    getList() {
      this.loading = true;
      listRoleControlManager(this.queryParams).then((response) => {
        this.roleControlManagerList = response.rows;
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
        roleControlManagerId: null,
        roleControlId: null,
        userId: null,
        userLoginId: null,
        userName: null,
        scope: null,
        userDeptId: null,
        userDeptName: null,
        createTime: null,
        updateTime: null,
        delSign: null,
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
      this.ids = selection.map((item) => item.roleControlManagerId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "添加 角色授权控制管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.disabled = false;
      this.reset();
      const roleControlManagerId = row.roleControlManagerId || this.ids;
      getRoleControlManager(roleControlManagerId).then((response) => {
        this.form = response.data;
        this.form.userText = response.data.userName;
        this.form.dpname = response.data.userDeptName;
        this.form.position = response.data.position;
        this.form.deptId = response.data.scope;
        this.open = true;
        this.title = "修改 角色授权控制管理";
      });
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      const roleControlManagerId = row.roleControlManagerId || this.ids;
      getRoleControlManager(roleControlManagerId).then((response) => {
        this.form = response.data;
        this.isOpenQuery = true;
        this.title = "查看 角色授权控制管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.roleControlManagerId != null) {
            const params={
                roleControlManagerId: this.form.roleControlManagerId,
                roleControlId: this.queryParams.roleControlId,
                userId: this.form.loginid,
                userLoginId: this.form.loginid,
                scope: this.form.deptId, //归属部门Id
                userName:this.form.username,
                userDeptId: this.form.dpid,
                userDeptName: this.form.dpname,
            }
            updateRoleControlManager(params).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            const params={
                roleControlId: this.queryParams.roleControlId,
                userId: this.form.loginid,
                userLoginId: this.form.loginid,
                scope: this.form.deptId, //归属部门Id
                userName:this.form.username,
                userDeptId: this.form.dpid,
                userDeptName: this.form.dpname,
            }
            //params.scope=params.scope[params.scope.length-1]
            // console.log(12211,this.form)
            console.log(1112,params)
            // return ;
            addRoleControlManager(params).then((response) => {
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
      const roleControlManagerIds = row.roleControlManagerId || this.ids;
      this.$confirm(
        '是否确认删除 角色授权控制管理编号为"' +
          roleControlManagerIds +
          '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delRoleControlManager(roleControlManagerIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 禁用或启用操作*/
    disabledBtnClick(type) {
      var ids = this.ids || [];
      this.$confirm("是否确认" + (type === 0 ? "启用" : "禁用") + "?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return disableEnable({
            ids: ids.toString(),
            disableEnableState: type,
          });
        })
        .then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出 角色授权控制管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportRoleControlManager(queryParams);
        })
        .then((response) => {
          this.download(response.msg,response.key);
        })
        .catch(function () {});
    },
        /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = " 角色授权控制管理数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg,response.key);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    //获取用户信息
    getUserByLoginId(userid) {

      this.form.loginid = userid;
      this.FitnessWhiteUserList.forEach((item, index) => {
        if (userid == item.loginid) {
          console.log(item);
          this.form = JSON.parse(JSON.stringify(item));
        }
      })
    },
    //     getFitnessWhiteViewEmpInfo() {
    //   getFitnessWhiteViewEmpInfo().then((response) => {
    //     this.FitnessWhiteUserList = response.rows;
    //   });
    // },
    getFitnessWhiteViewEmpInfo() {
      let pa = {
        username: ""
      }
      getFitnessWhiteViewEmpInfo(pa).then(response => {
        this.FitnessWhiteUserList = response.rows;
        this.FitnessWhiteUserList.forEach((item, index) => {
          this.states.push({
            label: item.userText,
            value: item.loginid
          });

        })
      });
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 搜索栏展开
    onHandle() {
      this.isOpen = !this.isOpen;
    },
    remoteMethod(query) {
      let pa = {
        username: query
      }
      if (query !== '') {
        this.states = [];
        this.loading = true;
        getFitnessWhiteViewEmpInfo(pa).then(response => {
          this.loading = false;
          this.FitnessWhiteUserList = response.rows;
          this.FitnessWhiteUserList.forEach((item, index) => {
            this.states.push({
              label: item.userText,
              value: item.loginid
            });
            this.options = this.states.filter(item => {
              return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
            });
          })
        });
      } else {
        this.options = [];
      }
    },
  },
};
</script>



<style lang="scss" scoped>
.app-container {
  background: #e9f4fd;
}
.search-folder {
  // margin: 0 -16px;
  // padding: 0 16px;
  padding: 15px;
  position: relative;
  height: 60px;
  overflow-y: hidden;
  display: flex;
  background: #fff;
  border-radius: 2px;
  .ivu-icon {
    // position: absolute;
    left: 2px;
    top: 6px;
    color: #3a89da;
    z-index: 10;
    font-size: 18px;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
  &.folder-open {
    height: auto;
  }
  &-utils {
    display: flex;
    height: 32px;
    align-items: center;
    margin-left: 10px;
    p {
      cursor: pointer;
      width: 32px;
      margin-left: 10px;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #3a89da;
    }
  }
}
.utlsBtn {
  /deep/ .el-button--mini {
    padding: 9px 15px;
  }
  .normalBtn {
    /deep/ .el-button--mini {
      padding: 8px;
    }
  }
}
.container {
  padding: 12px;
  background: #fff;
  border-radius: 2px;

  .pageBox {
    height: 32px;
    .pagination-container {
      padding: 0 !important;
      margin: 0;
      height: 32px;
      margin-top: 12px;
      /deep/ .number,
      /deep/ .btn-prev,
      /deep/ .btn-next {
        width: 32px;
        height: 32px;
        line-height: 31px;
        background: #fff;
        border-radius: 4px;
        font-size: 14px;
        font-family: HelveticaNeue;
        color: rgba(0, 0, 0, 0.65);
        border: 1px solid rgba(23, 35, 61, 0.15);
        font-weight: normal;
      }
      /deep/ .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
    }
    /deep/ .el-pagination__total {
      line-height: 32px;
    }
  }
}
/deep/ .el-form-item__label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: rgba(23, 35, 61, 0.75);
}
</style>
