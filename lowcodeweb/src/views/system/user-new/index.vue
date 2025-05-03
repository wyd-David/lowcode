<template>
  <div class="app-container">
    <div class="gf-flex">
      <!--部门数据-->
      <div class="gf-border-r page-user-left">
        <div>
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="dept-tree-container">
          <el-tree
            highlight-current
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :default-expanded-keys="expandId"
            :filter-node-method="filterNode"
            node-key="id"
            ref="tree"
            @node-click="handleNodeClick"
          >
            <template slot-scope="{ node, data }">
              <el-tooltip effect="dark" :content="node.label" placement="right">
                <span class="gf-ellipsis">{{node.label}}</span>
              </el-tooltip>
            </template>
          </el-tree>
        </div>
      </div>
      <!--用户数据-->
      <div class="page-user-right">
        <PmpSearchBar @on-emit-query="handleQuery" @on-emit-reset="resetQuery">
          <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
            <el-form-item label="用户中文名" prop="userName">
              <el-input
                v-model="queryParams.userName"
                placeholder="请输入用户中文名"
                clearable
                size="small"
                style="width: 180px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="主部门" prop="mainDepartment">
              <el-select
                v-model="queryParams.mainDepartment"
                placeholder="全部"
                clearable
                size="small"
                style="width: 180px"
              >
                <el-option
                  v-for="dict in mainDepartmentOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-select
                v-model="queryParams.sex"
                placeholder="全部"
                clearable
                size="small"
                style="width: 180px"
              >
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="userStatus">
              <el-select
                v-model="queryParams.userStatus"
                placeholder="全部"
                clearable
                size="small"
                style="width: 180px"
              >
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="数据来源" prop="dataSource">
              <el-select
                v-model="queryParams.dataSource"
                placeholder="全部"
                clearable
                size="small"
                style="width: 180px"
              >
                <el-option
                  v-for="dict in dataSourceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="员工类型" prop="staffType">
              <el-select
                v-model="queryParams.staffType"
                placeholder="全部"
                clearable
                size="small"
                style="width: 180px"
              >
                <el-option
                  v-for="dict in staffTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <!-- <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item> -->
          </el-form>
        </PmpSearchBar>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              :disabled="modify"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-circle-check"
              size="mini"
              :disabled="single"
              @click="handleAuthRole"
              v-hasPermi="['system:user:assign']"
            >分配角色</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete(null)"
              v-hasPermi="['system:user:remove']"
            >删除</el-button>
          </el-col>
          <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar> -->
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="用户中文名" align="center" key="userName" prop="userName" min-width="120" v-if="columns[0].visible" show-overflow-tooltip />
          <el-table-column label="用户英文名" align="center" key="charName" prop="charName" min-width="120" v-if="columns[1].visible" />
          <el-table-column label="登录账号" align="center" key="loginId" prop="loginId" min-width="120" v-if="columns[2].visible" show-overflow-tooltip />
          <el-table-column label="ERP编号" align="center" key="erpId" prop="erpId" min-width="150" v-if="columns[3].visible" />
          <el-table-column label="办公电话" align="center" key="phone" prop="phone" min-width="160" v-if="columns[4].visible" />
          <el-table-column label="分机号码" align="center" key="directPhone" prop="directPhone" min-width="160" v-if="columns[5].visible" />
          <el-table-column label="手机号" align="center" key="mainMobile" prop="mainMobile" min-width="200" v-if="columns[6].visible" />
          <el-table-column label="副手机号" align="center" key="assistantMobile" prop="assistantMobile" min-width="160" v-if="columns[7].visible" />
          <el-table-column label="邮箱" align="center" key="mailbox" prop="mailbox" min-width="200" v-if="columns[8].visible" />
          <el-table-column label="性别" align="center" key="sex" prop="sex" min-width="100" v-if="columns[9].visible" />
          <el-table-column label="职务岗位" align="center" key="post" prop="post" min-width="200" v-if="columns[10].visible" />
          <el-table-column label="状态" align="center" key="userStatus" prop="userStatus" min-width="200" v-if="columns[11].visible" :formatter="formatterUserStatus"/>
          <el-table-column label="部门" align="center" key="dpName" prop="dpName" min-width="200" v-if="columns[12].visible" show-overflow-tooltip />
          <el-table-column label="汇报列表" align="center" key="reportList" prop="reportList" min-width="400" v-if="columns[13].visible" show-overflow-tooltip />
          <el-table-column label="员工类型" align="center" key="staffType" prop="staffType" min-width="200" v-if="columns[14].visible" :formatter="formatterStaffType" />
          <el-table-column label="岗位职责" align="center" key="idioGraph" prop="idioGraph" min-width="200" show-overflow-tooltip v-if="columns[15].visible" />
          <el-table-column label="工作地点" align="center" key="workingPlace" prop="workingPlace" min-width="200" show-overflow-tooltip v-if="columns[16].visible" />
          <el-table-column label="数据来源" align="center" key="dataSource" prop="dataSource" min-width="100" v-if="columns[17].visible" :formatter="formatterDataSource" />
          <el-table-column label="同步时间" align="center" key="syncTime" prop="syncTime" min-width="160" v-if="columns[18].visible" />
          <el-table-column label="创建时间" align="center" key="userCreateDate" prop="userCreateDate" min-width="160" v-if="columns[19].visible" />
          <el-table-column label="修改时间" align="center" key="userModifyDate" prop="userModifyDate" min-width="160" v-if="columns[20].visible" />
          <el-table-column label="注销时间" align="center" key="userDeleteDate" prop="userDeleteDate" min-width="160" v-if="columns[21].visible" />
          <el-table-column
            label="操作"
            align="left"
            width="200"
            fixed="right"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
                v-if="scope.row.dataSource ==2"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-circle-check"
                @click="handleAuthRole(scope.row)"
                v-hasPermi="['system:user:edit']"
              >分配角色</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
                v-if="scope.row.dataSource ==2"
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
      </div>
    </div>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body
      :close-on-click-modal="false" :close-on-press-escape="false">
      <div class="avatar-wrapper">
        <div class="avatar-label">上传头像</div>
        <Uploader
          v-model="form.avatar"
          :accept="'image/*'"
          :limit="1"
          pathType="avatar"
          class="avatar-upload"
        ></Uploader>
      </div>
      <el-form ref="form" :model="form" :rules="ruleForm" label-width="100px">
        <el-row class="user-row">
          <el-col :span="12">
            <el-form-item label="姓名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入姓名" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="12">
            <el-form-item label="ERP编号" prop="erpId">
              <el-input v-model="form.erpId" placeholder="请输入ERP编号" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择性别" style="width: 300px">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="12">
            <el-form-item label="归属部门" prop="dpId">
              <DeptSelect v-model="form.dpId" style="width: 302px;" @change="selectDept"></DeptSelect>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="24">
            <el-form-item label="所在分组" prop="dpFullName">
              <el-input type="textarea" maxlength="200" show-word-limit v-model="form.dpFullName" placeholder="选择归属部门后自动显示所在分组" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="12">
            <el-form-item label="办公电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入办公电话" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分机号码" prop="directPhone">
              <el-input v-model="form.directPhone" placeholder="请输入分机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主手机号" prop="mainMobile">
              <el-input v-model="form.mainMobile" placeholder="请输入主手机号" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="副手机号" prop="assistantMobile">
              <el-input v-model="form.assistantMobile" placeholder="请输入副手机号" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="24">
            <el-form-item label="其他手机号" prop="moreMobile">
              <el-input type="textarea" maxlength="200" show-word-limit v-model="form.moreMobile" placeholder="多个手机号时请用英文逗号隔开" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="12">
            <el-form-item label="员工类型" prop="staffType">
              <el-select v-model="form.staffType" placeholder="请选择员工类型" style="width: 300px">
                <el-option
                  v-for="dict in staffTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职务岗位" prop="post">
              <el-input v-model="form.post" placeholder="请输入职务岗位" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接领导" prop="superiorLeader">
              <UserSelect v-model="form.superiorLeader" style="width: 302px;"></UserSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="岗位职责" prop="idioGraph">
              <el-input v-model="form.idioGraph" placeholder="请输入岗位职责" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="user-row">
          <el-col :span="24">
            <el-form-item label="办公地址" prop="workingPlace">
              <el-input type="textarea" maxlength="200" show-word-limit v-model="form.workingPlace" placeholder="请输入办公地址" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import DeptSelect from '@/components/DeptSelect/index.vue';
import UserSelect from '@/components/UserSelect/index.vue';
import { listUser, getUser, delUser, addUser, updateUser } from "@/api/system/user";
import { getDeptByDpId } from "@/api/system/dept";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Uploader from "@/components/Uploader";

export default {
  name: "User",
  components: { Treeselect, DeptSelect, UserSelect, Uploader },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选择数据
      selections: [],
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //修改
      modify:true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 部门树默认展示id
      expandId: [],
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions:  [],
      // 主部门字典
      mainDepartmentOptions: [],
      // 数据来源字典
      dataSourceOptions: [],
      // 员工类型字典
      staffTypeOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {
        userId: undefined,
        avatar: undefined,
        userName: undefined,
        erpId: undefined,
        sex: undefined,
        dpId: undefined,
        dpFullName: undefined,
        phone: undefined,
        directPhone: undefined,
        mainMobile: undefined,
        assistantMobile: undefined,
        moreMobile: undefined,
        staffType: undefined,
        post: undefined,
        superiorLeader: undefined,
      },
      ruleForm: {
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        dpId: [
          { required: true, message: '请选择归属部门', trigger: 'change' }
        ],
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dpId: '',
        userName: undefined,
        mainDepartment: undefined,
        sex: undefined,
        userStatus: undefined,
        dataSource: undefined,
        staffType: undefined,
      },
      // 列信息
      columns: [
        { key: 0, label: `用户中文名`, visible: true },
        { key: 1, label: `用户英文名`, visible: true },
        { key: 2, label: `登录账号`, visible: true },
        { key: 3, label: `ERP 编号`, visible: true },
        { key: 4, label: `办公电话`, visible: true },
        { key: 5, label: `分机号码`, visible: true },
        { key: 6, label: `手机号`, visible: true },
        { key: 7, label: `副手机号`, visible: true },
        { key: 8, label: `邮箱`, visible: true },
        { key: 9, label: `性别`, visible: true },
        { key: 10, label: `职务岗位`, visible: true },
        { key: 11, label: `状态`, visible: true },
        { key: 12, label: `部门`, visible: true },
        { key: 13, label: `汇报列表`, visible: true },
        { key: 14, label: `员工类型`, visible: true },
        { key: 15, label: `岗位职责`, visible: true },
        { key: 16, label: `工作地点`, visible: true },
        { key: 17, label: `数据来源`, visible: true },
        { key: 18, label: `同步时间`, visible: true },
        { key: 19, label: `创建时间`, visible: true },
        { key: 20, label: `修改时间`, visible: true },
        { key: 21, label: `注销时间`, visible: true },
      ],
    };
  },
  computed: {
		headers() {
			return {
				'Admin-Token': getToken()
			}
		}
	},
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then((response) => {
      this.sexOptions = response.data;
    });
    this.getConfigKey("sys.user.initPassword").then((response) => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    getDicts(e){
      getData(e).then(res => {
        console.log("1233--------",res);
      });
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
        this.deptOptions.forEach(d => {
          this.expandId.push(d.id)
        })
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // formatterUserStatus(row, column) {
    //   return this.$root.getDictLabel('sys_user_status', row.userStatus+'');
    // },
    // formatterStaffType(row, column) {
    //   return this.$root.getDictLabel('sys_user_staffType', row.staffType+'');
    // },
    // formatterDataSource(row, column) {
    //   return this.$root.getDictLabel('sys_user_dataSource', row.dataSource+'');
    // },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.dpId = data.id;
      this.getList();
    },
    // 选择部门后，显示所在分组
    selectDept(item) {
      getDeptByDpId(item.dpId).then(response => {
        this.form.dpFullName = response.data.dpFullName
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
        userId: undefined,
        avatar: undefined,
        userName: undefined,
        erpId: undefined,
        sex: undefined,
        dpId: undefined,
        dpFullName: undefined,
        phone: undefined,
        directPhone: undefined,
        mainMobile: undefined,
        assistantMobile: undefined,
        moreMobile: undefined,
        staffType: undefined,
        post: undefined,
        superiorLeader: undefined,
        //roleIds: []
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
      this.dateRange = [];
      this.queryParams.dpId = '';
      this.queryParams.status = '1';
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.single = selection.length != 1;
      this.modify = selection.length != 1
      this.multiple = !selection.length;
      this.selections = selection;
      this.ids = selection.map(item => {
        if(item.dataSource == 1){
         this.multiple = true
         this.modify = true
        }
       return item.userId
        });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户";
      });
    },
    /** 分配角色操作 */
    handleAuthRole: function(row) {
      const userId = row.userId || this.ids;
      this.$router.push("/auth/role/" + userId);
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let userDels = []
      if (row) {
        let userDel = {
          userId: row.userId,
          dpId: row.dpId
        }
        userDels.push(userDel)
      } else {
        this.selections.forEach(s => {
          let userDel = {
            userId: s.userId,
            dpId: s.dpId
          }
          userDels.push(userDel)
        })
      }
      this.$confirm('确定要删除吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUser(userDels);
        }).then(() => {
           this.getList();
           this.msgSuccess("删除成功");
        }).catch(() => {});
    },
  }
};
</script>

<style lang="scss" scoped>
  .page-user-right{
    width: 80%;
  }
  .gf-flex{
    display: flex;
  }
  .page-user-left {
    width: 20%;
    padding-right: 12px;
    margin-right: 12px;
    .dept-tree-container {
      max-height: calc(100vh - 188px);
      overflow-y: auto;
    }
  }
.user-row {
  .el-col-12, .el-col-24 {
     padding: 0 40px;
  }
}
::v-deep .el-dialog__body {
  padding: 30px 20px 10px;
}
.dialog-footer {
  text-align: center;
  margin-bottom: 20px;
}
//上传头像
.avatar-wrapper {
  position: relative;
  .avatar-label {
    position: absolute;
    right: 354px;
    top: 68px;
  }
  .avatar-upload {
    position: absolute;
    top: 8px;
    right: 192px;
    z-index: 9999;
  }
  .avatar-img {
    width: 146px;
    height: 146px;
    display: block;
  }
}
</style>
