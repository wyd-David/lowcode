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
        label-width="80px"
      >
        <el-form-item label="公告标题" prop="noticeTitle">
          <el-input
            v-model="queryParams.noticeTitle"
            placeholder="请输入公告标题"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="noticeType" label-width="80px">
          <el-select
            v-model="queryParams.noticeType"
            placeholder="公告类型"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="通知开始时间" prop="noticeStartDate">-->
        <!--          <el-date-picker clearable size="small" style="width: 200px" v-model="queryParams.noticeStartDate" type="date"-->
        <!--            value-format="yyyy-MM-dd" placeholder="选择通知开始时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="通知结束时间" prop="noticeEndDate">-->
        <!--          <el-date-picker clearable size="small" style="width: 200px" v-model="queryParams.noticeEndDate" type="date"-->
        <!--            value-format="yyyy-MM-dd" placeholder="选择通知结束时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <el-form-item
          label="通知时间"
          prop="noticeStartDateTime"
          label-width="80px"
        >
          <el-date-picker
            v-model="chooseTime"
            type="daterange"
            style="width: 250px"
            range-separator="至"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            size="small"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>

      <div class="search-folder-utils">
        <el-button type="primary" size="mini" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="mini" @click="resetQuery">重置</el-button>
        <!--        <p v-if="height > 34" @click="onHandle">{{isOpen ? '收起' : '展开'}}</p>-->
        <!--        <span v-if="height > 34">-->
        <!--          <i class="el-icon-arrow-up ivu-icon" @click="onHandle" v-if="isOpen"></i>-->
        <!--          <i class="el-icon-arrow-down ivu-icon" @click="onHandle" v-else></i>-->
        <!--        </span>-->
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
            v-hasPermi="['system:notice:add']"
            >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:notice:edit']"
            >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:notice:remove']"
            >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['system:notice:importData']"
            >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:notice:importData']"
            >导出
          </el-button>
        </el-col>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>

      <el-table
        v-loading="loading"
        :data="noticeList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="noticeId" />
        <el-table-column label="公告标题" align="center">
          <template slot-scope="scope">
            <span
              @click="handleQueryOption(scope.row)"
              style="color: #1890ff"
              >{{ scope.row.noticeTitle }}</span
            >
          </template>
        </el-table-column>
        <el-table-column
          label="公告类型"
          align="center"
          prop="noticeType"
          :formatter="typeFormat"
          width="100"
        />
        <!-- <el-table-column label="公告内容" align="center" prop="noticeContent" /> -->
        <el-table-column
          label="状态"
          align="center"
          prop="status"
          :formatter="statusFormat"
          width="100"
        />
        <!--        <el-table-column label="备注" align="center" prop="remark"/>-->
        <el-table-column
          label="通知开始时间"
          align="center"
          prop="noticeStartDate"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.noticeStartDate, "{y}-{m}-{d}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="通知结束时间"
          align="center"
          prop="noticeEndDate"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.noticeEndDate, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="业务类别"
          align="center"
          prop="noticeBusinessType"
          :formatter="noticeBusinessTypeFormat"
        />
        <!--        <el-table-column label="通知人类型" align="center" prop="noticePeopleType"/>-->
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <!-- <el-button size="mini" type="text" icon="el-icon-view" @click="handleQueryOption(scope.row)">查看</el-button> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:notice:edit']"
              >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:notice:remove']"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total > 0"
          :total="Number(total)"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 添加或修改通知公告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="公告标题:" prop="noticeTitle">
          <el-input v-model="form.noticeTitle" placeholder="请输入公告标题" />
        </el-form-item>
        <el-form-item label="公告类型:" prop="noticeType">
          <el-select v-model="form.noticeType" placeholder="请选择">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公告状态:" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="(dict, index) in statusOptions"
              :key="index"
              :label="dict.dictValue"
              >{{ dict.dictLabel }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item> -->
        <!--        <el-form-item label="通知开始时间" prop="noticeStartDate">-->
        <!--          <el-date-picker clearable size="small" style="width: 200px" v-model="form.noticeStartDate" type="date"-->
        <!--            value-format="yyyy-MM-dd" placeholder="选择通知开始时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="通知结束时间" prop="noticeEndDate">-->
        <!--          <el-date-picker clearable size="small" style="width: 200px" v-model="form.noticeEndDate" type="date"-->
        <!--            value-format="yyyy-MM-dd" placeholder="选择通知结束时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->

        <el-form-item label="通知时间:">
          <el-date-picker
            v-model="noticeStartDateTime"
            type="daterange"
            style="width: 250px"
            range-separator="至"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            size="small"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="通知人" prop="noticePeople">
           <el-input v-model="form.noticePeople" placeholder="请输入通知人，可以是个人，也可以是多个部门" />
         </el-form-item> -->
        <el-form-item label="业务类别:" prop="noticeBusinessType">
          <el-select
            v-model="form.noticeBusinessType"
            placeholder="请选择通知业务类别"
          >
            <el-option
              v-for="dict in noticeBusinessTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="通知人类型" prop="noticePeopleType">
          <el-select v-model="form.noticePeopleType" placeholder="请选择通知人类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="公告内容:" prop="noticeContent">
          <editor v-model="form.noticeContent" :min-height="192" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看通知公告对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="isOpenQuery"
      width="800px"
      append-to-body="append-to-body"
    >
      <el-form
        ref="queryform"
        :model="form"
        label-width="120px"
        :disabled="disabled"
        label-position="left"
      >
        <el-form-item label="公告标题:" prop="noticeTitle">
          {{ form.noticeTitle }}
        </el-form-item>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="公告类型:" prop="noticeType">
              {{ form.noticeType | sys_business_typeFieldFilter(typeOptions) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通知业务类别:" prop="noticeBusinessType">
              {{
                form.noticeBusinessType
                  | sys_business_typeFieldFilter(that.noticeBusinessTypeOptions)
              }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="通知开始时间:" prop="noticeStartDate">
              {{ form.noticeStartDate }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通知结束时间:" prop="noticeEndDate">
              {{ form.noticeEndDate }}
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-form-item label="备注:" prop="remark">
          {{form.remark}}
        </el-form-item>
        <el-form-item label="通知人，可以是个人，也可以是多个部门:" prop="noticePeople">
          {{form.noticePeople}}
        </el-form-item> -->
        <el-form-item label="公告内容:">
          <div v-html="form.noticeContent"></div>
        </el-form-item>
        <!-- <el-form-item label="通知人类型" prop="noticePeopleType">
          {{form.noticePeopleType | FieldFilter(noticePeopleTypeOptions)}}
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 通知公告导入对话框 -->
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
  </div>
</template>

<script>
import { formatDate } from "@/utils/index";
import { getToken } from "@/utils/auth";
import {
  listNotice,
  getNotice,
  delNotice,
  addNotice,
  updateNotice,
  exportNotice,
  importTemplate,
} from "@/api/system/notice";
import Editor from "@/components/Editor";

export default {
  name: "Notice",
  components: {
    Editor,
  },
  data() {
    return {
      that: this,
      isOpenQuery: false,
      // 搜索栏展示配置
      height: null,
      isOpen: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      disabled: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //查询时间段
      chooseTime: [],
      //提交时间段
      chooseTimesub: [],
      // 通知公告表格数据
      noticeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型数据字典
      statusOptions: [],
      // 业务类别数据字典
      businessTypeOptions: [],
      // 公告类型数据字典
      typeOptions: [],
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
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/notice/importData",
      },
      // 通知业务类别字典
      noticeBusinessTypeOptions: [],
      //时间
      noticeStartDateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeTitle: null,
        noticeType: null,
        noticeContent: null,
        status: null,
        noticeStartDate: null,
        noticeEndDate: null,
      },
      // 表单参数
      form: {
        status: null,
        noticeId: null,
        noticeTitle: null,
        noticeType: null,
        noticeContent: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        noticeStartDate: null,
        noticeEndDate: null,
        noticePeople: null,
        noticeBusinessType: null,
        noticePeopleType: null,
        // noticeStartDateTime: null,
      },
      // 表单校验
      rules: {
        noticeTitle: [
          { required: true, message: "公告标题不能为空", trigger: "blur" },
        ],
        noticeType: [
          { required: true, message: "公告类型不能为空", trigger: "change" },
        ],
        // noticeStartDateTime:[
        //   { required: true, message: "通知时间不能为空", trigger: ["change","blur"] },
        // ],
        status: [
          {
            required: true,
            message: "公告状态不能为空",
            trigger: ["change", "blur"],
          },
        ],
        noticeBusinessType: [
          {
            required: true,
            message: "业务类别不能为空",
            trigger: ["change", "blur"],
          },
        ],
        noticeContent: [
          {
            required: true,
            message: "公告内容不能为空",
            trigger: ["change", "blur"],
          },
        ],
      },
    };
  },
  filters: {
    sys_business_typeFieldFilter(val, optionArr) {
      console.log("000", val, optionArr);
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created() {
    this.getList();
    this.getDicts("sys_business_type").then((response) => {
      this.noticeBusinessTypeOptions = response.data;
    });
    this.getDicts("sys_notice_status").then((response) => {
      this.statusOptions = response.data;
      this.form.status =
        this.statusOptions.length && this.statusOptions[0].dictValue + "";
      console.log("form.status", response.data);
    });
    this.getDicts("sys_notice_type").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_business_type").then((response) => {
      this.businessTypeOptions = response.data;
    });
  },
  mounted() {
    this.height = this.$refs.folder.clientHeight;
    if (this.height > 32) {
      this.isOpen = false;
    }
  },
  methods: {
    /** 查询通知公告列表 */
    getList() {
      this.loading = true;
      if (this.chooseTime) {
        this.queryParams.noticeStartDate = formatDate(
          this.chooseTime[0],
          "yyyy-MM-dd" + " 00:00:00"
        );
        this.queryParams.noticeEndDate = formatDate(
          this.chooseTime[1],
          "yyyy-MM-dd" + " 23:59:59"
        );
      }
      listNotice(this.queryParams).then((response) => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 通知业务类别字典翻译
    noticeBusinessTypeFormat(row, column) {
      return this.selectDictLabel(
        this.noticeBusinessTypeOptions,
        row.noticeBusinessType
      );
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 公告状态字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.noticeType);
    },
    // 字典业务类别字典翻译
    businessTypeFormat(row, column) {
      return this.selectDictLabel(this.businessTypeOptions, row.businessType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.noticeStartDateTime = [];
      this.form = {
        noticeId: null,
        noticeTitle: null,
        noticeType: null,
        noticeContent: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        noticeStartDate: null,
        noticeEndDate: null,
        noticePeople: null,
        noticeBusinessType: null,
        noticePeopleType: null,
        noticeStartDateTime: null,
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
      this.chooseTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.noticeId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.chooseTimesub = null;
      this.open = true;
      this.title = "添加通知公告";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids;
      getNotice(noticeId).then((response) => {
        this.form = response.data;
        this.noticeStartDateTime = [
          response.data.noticeStartDate,
          response.data.noticeEndDate,
        ];

        console.log(this.noticeStartDateTime, "日期");
        this.open = true;
        this.title = "修改通知公告";
      });
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      const noticeId = row.noticeId || this.ids;
      getNotice(noticeId).then((response) => {
        this.form = response.data;
        this.isOpenQuery = true;
        this.title = "查看通知公告";
      });
    },
    /** 提交按钮 */
    submitForm() {
      // console.log(this.form,"=======")
      // return
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.noticeId != null) {
            let form = JSON.parse(JSON.stringify(this.form));
            form.noticeStartDate = formatDate(
              this.noticeStartDateTime[0],
              "yyyy-MM-dd"
            );
            form.noticeEndDate = formatDate(
              this.noticeStartDateTime[1],
              "yyyy-MM-dd"
            );
            updateNotice(form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            // if (this.form.noticeTitle == null || this.form.noticeTitle == "") {
            //   this.msgError("填写标题！");
            //   return;
            // }
            // if (this.form.noticeType == null || this.form.noticeType == "") {
            //   this.msgError("填写公告类型！");
            //   return;
            // }
            // console.log(this.form.status,"++?");
            // if (this.form.status == null || this.form.status == "") {
            //   this.msgError("填写公告状态！");
            //   return;
            // }
            // if (this.chooseTimesub == null) {
            //   this.msgError("填写通知时间！");
            //   return;
            // }
            // this.form.noticeStartDate = formatDate(
            //   this.chooseTimesub[0],
            //   "yyyy-MM-dd" + " 00:00:00"
            // );
            // this.form.noticeEndDate = formatDate(
            //   this.chooseTimesub[1],
            //   "yyyy-MM-dd" + " 23:59:59"
            // );
            // if (
            //   this.form.noticeContent == null ||
            //   this.form.noticeContent == ""
            // ) {
            //   this.msgError("填写公告内容！");
            //   return;
            // }
            let form = JSON.parse(JSON.stringify(this.form));
            form.noticeStartDate = formatDate(
              this.noticeStartDateTime[0],
              "yyyy-MM-dd"
            );
            form.noticeEndDate = formatDate(
              this.noticeStartDateTime[1],
              "yyyy-MM-dd"
            );
            addNotice(form).then((response) => {
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
      const noticeIds = row.noticeId || this.ids;
      this.$confirm(
        '是否确认删除通知公告编号为"' + noticeIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delNotice(noticeIds);
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
      this.$confirm("是否确认导出通知公告数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportNotice(queryParams);
        })
        .then((response) => {
          this.download(response.msg, response.key);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "通知公告数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg, response.key);
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
      this.$alert(response.msg, "导入结果", {
        dangerouslyUseHTMLString: true,
      });
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
  text-align: left;
}
</style>