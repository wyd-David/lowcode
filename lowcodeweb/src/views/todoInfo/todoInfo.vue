<template>
  <div class="app-container">
    <div class="bgWBox">
      <div
        class="search-folder mb12"
        style="padding: 0 15px; display: flex; justify-content: space-between"
        :class="{ 'folder-open': isOpen }"
      >
        <div ref="folder" :class="{ clearFormBottom: !isShowOpen }">
          <!-- label-width="110px" -->
          <el-form
            :model="queryParams"
            style="flex: 1"
            ref="queryForm"
            :inline="true"
            label-width="110px"
            v-show="showSearch"
          >
            <el-form-item label="标题" prop="todoTitle">
              <el-input
                v-model="queryParams.todoTitle"
                placeholder="请输入待办待阅标题"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="业务id" prop="businessKey">
              <el-input
                v-model="queryParams.businessKey"
                placeholder="请输入业务id"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="发起人/申请人" prop="todoApplicant">
              <el-input
                v-model="queryParams.todoApplicant"
                placeholder="请输入发起人/申请人"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </el-form>
        </div>

        <div class="search-folder-utils">
          <el-button type="primary" size="mini" @click="handleQuery"
            >搜索</el-button
          >
          <el-button size="mini" @click="resetQuery">重置</el-button>
          <p v-if="isShowOpen" @click="onHandle">
            {{ isOpen ? "收起" : "展开" }}
          </p>
          <span v-if="isShowOpen">
            <i
              class="el-icon-arrow-up ivu-icon"
              @click="onHandle"
              v-if="isOpen"
            ></i>
            <i class="el-icon-arrow-down ivu-icon" @click="onHandle" v-else></i>
          </span>
        </div>
      </div>
    </div>

    <div class="container">
      <!-- <el-row :gutter="10" class="mb12 utlsBtn">
          <el-col
            :span="1.5"
            v-if="todoinfoList[0] && todoinfoList[0].todoDataType == '3'"
          >
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="openApprove"
              >批量审批
            </el-button>
          </el-col>
        </el-row> -->

      <el-table
        v-loading="tableLoading"
        :data="todoinfoList"
        @selection-change="handleSelectionChange"
      >
        <!-- <el-table-column v-if="todoinfoList[0] && todoinfoList[0].todoDataType == '3'"></el-table-column> -->
        <!-- <el-table-column
            v-if="todoinfoList[0] && todoinfoList[0].todoDataType == '3'"
            type="selection"
            align="center"
            width="55"
          >
          </el-table-column> -->
        <!-- <el-table-column type="index" width="55" align="center"/> -->
        <el-table-column
          header-align="left"
          align="left"
          label="标题"
          prop="todoTitle"
        />
        <el-table-column label="业务id" prop="businessKey" />
        <!-- <el-table-column header-align="left" align="left" label="待办内容" prop="todoText"/> -->
        <el-table-column
          label="发起人/申请人"
          align="left"
          prop="todoApplicantName"
          width="120px"
        />
        <!-- <el-table-column label="待办URL" align="center" prop="todoUrl"/> -->
        <el-table-column
          label="当前处理人"
          align="left"
          prop="todoHandlerName"
          width="120px"
        />
        <!-- <el-table-column label="处理人名称" align="center" prop="todoHandlerName" width="120px"/> -->

        <!--        <el-table-column label="模块名称" align="center"/>-->
        <!-- <el-table-column label="所属工作流程名称" align="center" prop="todoTpName"/> -->
        <el-table-column label="当前处理环节" align="left" prop="todoTpLink" />
        <el-table-column
          label="接收时间"
          align="left"
          prop="createTime"
          width="150px"
        />
        <el-table-column
          label="处理时间"
          v-if="
            queryParams.todoDataType != '1' && queryParams.todoDataType != '3'
          "
          align="center"
          prop="modifyTime"
          width="150px"
        />
        <el-table-column
          label="处理状态"
          align="center"
          prop="todoType"
          :formatter="todoTypeFormat"
        />
        <el-table-column
          label="待办类型"
          align="center"
          prop="todoDataType"
          :formatter="todoDataTypeFormat"
        />
        <!-- <el-table-column label="待办待阅状态" align="center" prop="todoDataType" :formatter="todoDataTypeFormat"/> -->
        <!-- <el-table-column label="推送状态" align="center" prop="pushStatus" :formatter="pushStatusFormat"/> -->
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.todoDataType == '1'"
              size="mini"
              type="text"
              @click="againPush(scope.row)"
              style="font-size: 13px"
              >重推
              <!-- v-hasPermi="['todoinfo:todoinfo:remove']" -->
            </el-button>

            <el-button
              v-if="scope.row.todoDataType == '1'"
              size="mini"
              type="text"
              @click="accidentDeal(scope.row)"
              style="font-size: 13px"
            >
              转办
              <!-- v-hasPermi="['todoinfo:todoinfo:remove']" -->
            </el-button>

            <el-button
              v-if="scope.row.todoDataType == '1'"
              size="mini"
              type="text"
              @click="recycleProcess(scope.row)"
              style="font-size: 13px"
              >回收
              <!-- v-hasPermi="['todoinfo:todoinfo:remove']" -->
            </el-button>

            <!-- <el-button
                @click="toDetail(scope.row)"
                size="mini"
                type="text"
                v-hasPermi="['todoinfo:todoinfo:remove']"
                style="font-size: 13px"
                v-if="scope.row.todoDataType == 2"
                >详情
              </el-button> -->
            <!-- <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['todoinfo:todoinfo:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['todoinfo:todoinfo:remove']"
            >删除
            </el-button> -->
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

    <!-- 添加或修改待办待阅对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="业务主键" prop="businessKey">
          <el-select v-model="form.businessKey" placeholder="请选择业务主键">
            <el-option
              v-for="dict in businessKeyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="代办待阅标题" prop="todoTitle">
          <el-input v-model="form.todoTitle" placeholder="请输入代办待阅标题" />
        </el-form-item>
        <el-form-item label="应用ID" prop="applyId">
          <el-input v-model="form.applyId" placeholder="请输入应用ID" />
        </el-form-item>
        <el-form-item label="待办内容" prop="todoText">
          <el-input v-model="form.todoText" placeholder="请输入待办内容" />
        </el-form-item>
        <el-form-item label="发起人/申请人" prop="todoApplicant">
          <el-input
            v-model="form.todoApplicant"
            placeholder="请输入发起人/申请人"
          />
        </el-form-item>
        <el-form-item label="待办URL" prop="todoUrl">
          <el-input v-model="form.todoUrl" placeholder="请输入待办URL" />
        </el-form-item>
        <el-form-item label="处理人账号" prop="todoHandler">
          <el-input v-model="form.todoHandler" placeholder="请输入处理人账号" />
        </el-form-item>
        <el-form-item label="处理人名称" prop="todoHandlerName">
          <el-input
            v-model="form.todoHandlerName"
            placeholder="请输入处理人名称"
          />
        </el-form-item>
        <el-form-item label="所属工作流程名称" prop="todoTpName">
          <el-input
            v-model="form.todoTpName"
            placeholder="请输入所属工作流程名称"
          />
        </el-form-item>
        <el-form-item label="当前处理环节" prop="todoTpLink">
          <el-input
            v-model="form.todoTpLink"
            placeholder="请输入当前处理环节"
          />
        </el-form-item>
        <el-form-item label="待办完成时间" prop="modifyTime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.modifyTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择待办完成时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处理状态" prop="todoType">
          <el-select v-model="form.todoType" placeholder="请选择处理状态">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="待办待阅状态" prop="todoDataType">
          <el-select
            v-model="form.todoDataType"
            placeholder="请选择待办待阅状态"
          >
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="推送状态">
          <el-radio-group v-model="form.pushStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 待办待阅导入对话框 -->
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
          <el-checkbox v-model="upload.updateSupport" />
          是否更新已经存在的用户数据
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

    <el-dialog title="批量审批" :visible.sync="isApprove" width="30%">
      <span class="el-form-item__label">您的意见: </span
      ><el-input v-model="optionText" placeholder="请输入意见"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isApprove = false">取 消</el-button>
        <el-button type="primary" @click="approves">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 添加或修改 角色授权控制管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="accopen"
      width="600px"
      append-to-body
    >
      <el-form
        ref="dform"
        :model="dform"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="员工" prop="userText">
          <el-select
            v-model="dform.userText"
            filterable
            remote
            reserve-keyword
            placeholder="请输入员工名字"
            :remote-method="remoteMethod"
            :loading="loading"
            @change="getUserByLoginId(dform.userText)"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="dpname">
          <el-input v-model="dform.dpname" :disabled="true" />
        </el-form-item>
        <el-form-item label="岗位" prop="position">
          <el-input v-model="dform.position" :disabled="true" />
        </el-form-item>
        <el-form-item label="备注" prop="option">
          <el-input type="textarea" v-model="dform.option" placeholder="请输入备注" rows="2" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="accsubmitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listTodoinfo,
  getTodoinfo,
  delTodoinfo,
  addTodoinfo,
  updateTodoinfo,
  exportTodoinfo,
  importTemplate,
  againPush,
  getFitnessWhiteViewEmpInfo,
  accidentDeals,
  recyclePro,
} from "@/api/todoinfo/todoinfo";

export default {
  name: "Todoinfo",
  data() {
    return {
      isShowOpen: false,
      optionText: "",
      disabled: false,
      isApprove: false, //审批模态框
      // 搜索栏展示配置
      height: null,
      isOpen: true,
      // 遮罩层
      loading: true,
      tableLoading: false,
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
      // 待办待阅表格数据
      todoinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //意外处理弹出层
      accopen: false,
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
        url: process.env.VUE_APP_BASE_API + "/todoinfo/importData",
      },
      // 业务主键字典
      businessKeyOptions: [],
      // 处理状态字典
      todoTypeOptions: [],
      // 待办待阅已阅已办状态字典
      todoDataTypeOptions: [],
      // 待办待阅已阅已办状态字典
      pushStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        businessKey: null,
        todoTitle: null,
        todoText: null,
        todoApplicant: null,
        todoUrl: null,
        todoHandlerName: null,
        todoType: null,
        todoDataType: null,
      },
      // 表单参数
      form: {},
      dform: {
        option:""
      },
      row: {},
      FitnessWhiteUserList: [],
      states: [],
      loginid: "",
      options: [],
      // 表单校验
      rules: {
        businessKey: [
          { required: true, message: "业务主键不能为空", trigger: "change" },
        ],
        todoTitle: [
          { required: true, message: "代办待阅标题不能为空", trigger: "blur" },
        ],
        applyId: [
          { required: true, message: "应用ID不能为空", trigger: "blur" },
        ],
        todoText: [
          { required: true, message: "待办内容不能为空", trigger: "blur" },
        ],
        createTime: [
          { required: true, message: "待办接受时间不能为空", trigger: "blur" },
        ],
        modifyTime: [
          { required: true, message: "待办完成时间不能为空", trigger: "blur" },
        ],
        todoType: [
          {
            required: true,
            message: "1：已处理，0：未处理不能为空",
            trigger: "change",
          },
        ],
        userText: [
          { required: true, message: "员工不能为空", trigger: "change" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getFitnessWhiteViewEmpInfo();
    // this.queryParams.todoHandler = this.$store.getters.name;
    // this.queryParams.todoDataType = '1';
    this.getDicts("sys_business_type").then((response) => {
      this.businessKeyOptions = response.data;
    });
    this.getDicts("sys_todo_type").then((response) => {
      this.todoTypeOptions = response.data;
    });
    this.getDicts("sys_todo_data_type").then((response) => {
      this.todoDataTypeOptions = response.data;
    });
    this.getDicts("sys_todo_push_status").then((response) => {
      this.pushStatusOptions = response.data;
    });
  },
  mounted() {
    this.height = this.$refs.folder.clientHeight;
    if (this.height > 45) {
      this.isOpen = false;
      this.isShowOpen = true;
    } else {
      this.isShowOpen = false;
    }
  },
  methods: {
    openApprove() {
      if (this.ids.length) {
        this.isApprove = true;
      } else {
        this.$message.warning("请选择一条!");
      }
    },
    approves() {
      //批量审批按钮
      this.isApprove = false;
    },
    // 跳详情页面按钮
    toDetail(e) {
      let winObj = window.open(e.pcUrl + "&type=1" + "&source=1");
      let _this = this;
      var loop = setInterval(function () {
        if (winObj.closed) {
          _this.getList();
          clearInterval(loop);
        }
      }, 1000);
    },
    //获取用户信息
    getFitnessWhiteViewEmpInfo() {
      let pa = {
        username: "",
      };
      getFitnessWhiteViewEmpInfo(pa).then((response) => {
        this.FitnessWhiteUserList = response.rows;
        this.FitnessWhiteUserList.forEach((item, index) => {
          this.states.push({
            label: item.userText,
            value: item.loginid,
          });
        });
      });
    },
    getUserByLoginId(userid) {
      this.dform.loginid = userid;
      this.FitnessWhiteUserList.forEach((item, index) => {
        if (userid == item.loginid) {
          console.log(item);
          this.dform = { ...this.dform, ...JSON.parse(JSON.stringify(item)) };
        }
      });
    },
    /** 查询待办待阅列表 */
    getList() {
      this.tableLoading = true;
      listTodoinfo(this.queryParams).then((response) => {
        this.todoinfoList = response.rows;
        this.total = Number(response.total);
        this.tableLoading = false;
      });
    },
    // 办理按钮触发
    againPush(row) {
      let { todoId } = row;
      let that = this;
      let fd = new FormData();
      fd.append("todoId", todoId);
      this.$confirm("是否确定重新推送?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        againPush(fd).then((response) => {
          that.$message.success(response.msg);
          that.getList();
        });
      });
    },
    //意外处理按钮触发
    accidentDeal(row) {
      this.dform.todoId = row.todoId;
      this.row = row;
      this.dform.option = "";
      this.accopen = true;
      this.title = "转办";
      this.disabled = false;
    },
    //回收按钮触发
    recycleProcess(row) {
      let { todoId } = row;
      let that = this;
      let fd = new FormData();
      fd.append("todoId", todoId);
      this.$confirm("是否确定回收?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          recyclePro(fd).then((response) => {
            that.$message.success(response.msg);
            that.getList();
          });
        })
        .then(() => {
          console.log("success");
        });
    },
    // 业务主键字典翻译
    businessKeyFormat(row, column) {
      return this.selectDictLabel(this.businessKeyOptions, row.businessKey);
    },
    // 处理状态字典翻译
    todoTypeFormat(row, column) {
      return this.selectDictLabel(this.todoTypeOptions, row.todoType);
    },
    // 待办待阅已阅已办状态字典翻译
    todoDataTypeFormat(row, column) {
      return this.selectDictLabel(this.todoDataTypeOptions, row.todoDataType);
    },
    // 推送状态字典翻译
    pushStatusFormat(row, column) {
      return this.selectDictLabel(this.pushStatusOptions, row.pushStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.accopen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        todoId: null,
        businessKey: null,
        todoTitle: null,
        applyId: null,
        todoText: null,
        todoApplicant: null,
        todoUrl: null,
        todoHandler: null,
        todoHandlerName: null,
        todoTpName: null,
        todoTpLink: null,
        createTime: null,
        modifyTime: null,
        todoType: null,
        todoDataType: null,
        pushStatus: "0",
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
      this.ids = selection.map((item) => item.todoId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加待办待阅";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const todoId = row.todoId || this.ids;
      getTodoinfo(todoId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改待办待阅";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.todoId != null) {
            updateTodoinfo(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTodoinfo(this.form).then((response) => {
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
    /** 意外处理提交按钮 */
    accsubmitForm() {
      let that = this;
      this.$refs["dform"].validate((valid) => {
        if (valid) {
          if (this.dform.loginid != null) {
            let fm = new FormData();
            fm.append("taskId", this.row.taskId);
            fm.append(
              "TaskVariables",
              JSON.stringify({
                nextDealUser: this.dform.loginid,
                option: this.dform.option,
                message: "转办",
                btnMessage: "转办",
              })
            );
            accidentDeals(fm).then((res) => {
              that.$message.success(res.msg);
              that.accopen = false;
              that.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const todoIds = row.todoId || this.ids;
      this.$confirm(
        '是否确认删除待办待阅编号为"' + todoIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTodoinfo(todoIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有待办待阅数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportTodoinfo(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "待办待阅数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
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
        username: query,
      };
      if (query !== "") {
        this.states = [];
        this.loading = true;
        getFitnessWhiteViewEmpInfo(pa).then((response) => {
          this.loading = false;
          this.FitnessWhiteUserList = response.rows;
          this.FitnessWhiteUserList.forEach((item, index) => {
            this.states.push({
              label: item.userText,
              value: item.loginid,
            });
            this.options = this.states.filter((item) => {
              return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
            });
          });
        });
      } else {
        this.options = [];
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.bgWBox {
  background: #fff;
  padding: 10px 15px;
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
    color: #1590ff;
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
      color: #1590ff;
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
  margin-top: 15px;
  /deep/ .el-table__header-wrapper {
    th {
      font-size: 14px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.75);
    }
  }

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
