<template>
  <div class="app-container">
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="姓名：" prop="username">
            <el-input
              v-model="queryParams.username"
              placeholder="请输入姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属公司：" prop="company">
            <el-select
              v-model="queryParams.company"
              placeholder="请选择所属公司"
              clearable
              size="small"
            >
              <el-option
                v-for="(item, index) in selectCompanyData"
                :key="item.companyName"
                :label="item.companyName"
                :value="item.companyName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="部门：" prop="dpname">
            <el-input
              v-model="queryParams.dpname"
              placeholder="请输入部门名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <page-title></page-title>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['xuncha:XunchaLiaisonsManage:add']"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['xuncha:XunchaLiaisonsManage:remove']"
            >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['xuncha:XunchaLiaisonsManage:importData']"
            >导出
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <el-table
        v-loading="loading"
        :data="XunchaLiaisonsManageList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55px" align="left" />
        <el-table-column label="序号" type="index" width="55px" />
        <el-table-column label="姓名" width="100px" prop="username" />
        <el-table-column label="OA账号" width="130px" prop="loginId" />
        <el-table-column label="所属公司" width="130px" prop="company" />
        <el-table-column
          label="部门"
          width="140px"
          show-overflow-tooltip
          prop="dpname"
        />
        <el-table-column label="联系电话" prop="phone" />
        <el-table-column label="公司邮箱" prop="email" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="150px"
          fixed="right"
        >
          <template slot-scope="scope">
            <!--<el-button size="mini" type="text" @click="handleQueryOption(scope.row)">详情查看</el-button>-->
            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['xuncha:XunchaLiaisonsManage:remove']"
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
    <!-- 添加或修改联络员维护 对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="联络人：" prop="userId">
          <el-select
            style="width: 100%"
            v-model="value"
            filterable
            remote
            value-key="userId"
            reserve-keyword
            placeholder="请输入姓名关键字"
            :remote-method="remoteMethod"
            :loading="loading"
            @change="handleSelect"
          >
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="
                item.username +
                '-' +
                item.userId +
                '-' +
                item.company +
                '/' +
                item.dpname
              "
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账号：">
          <el-input disabled :value="form.userId" />
        </el-form-item>
        <el-form-item label="部门：">
          <el-input disabled :value="form.dpname"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { getCompanyAll } from "@/api/xuncha/XunchaLunciManage";
import {
  listXunchaLiaisonsManage,
  getXunchaLiaisonsManage,
  delXunchaLiaisonsManage,
  addXunchaLiaisonsManage,
  updateXunchaLiaisonsManage,
  exportXunchaLiaisonsManage,
  importTemplate,
  disableEnable,
  selectLiaisonsData,
} from "@/api/xuncha/XunchaLiaisonsManage";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import PageTitle from "@/components/PageTitle";
export default {
  name: "XunchaLiaisonsManage",
  components: { ExpandCollapse, PageTitle },
  data() {
    return {
      // 所属公司下拉框
      selectCompanyData: [],
      userList: [],
      value: "",
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
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
      // 联络员维护 表格数据
      XunchaLiaisonsManageList: [],
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
          "/xuncha/XunchaLiaisonsManage/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: null,
        company: null,
        dpname: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "联络人不能为空", trigger: "blur" },
        ],
      },
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    this.getList();
    // 获得所属公司下拉框数据
    getCompanyAll().then((res) => {
      this.selectCompanyData = res.data;
      console.log("===================");
      console.log(this.selectCompanyData);
    });
  },
  methods: {
    handleSelect(e) {
      this.form = e;
    },
    //搜索用户下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          selectLiaisonsData(query).then((res) => {
            this.userList = res.data;
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    /** 查询联络员维护 列表 */
    getList() {
      this.loading = true;
      listXunchaLiaisonsManage(this.queryParams).then((response) => {
        this.XunchaLiaisonsManageList = response.rows || [];
        this.total = +response.total;
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
        liaisonsId: null,
        liaisonsCode: null,
        liaisonsAccount: null,
        liaisonsName: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
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
      this.ids = selection.map((item) => item.liaisonsId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "添加联络员维护 ";
      // 将表单元素置空
      this.value = null;
      this.userList = [];
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      this.disabled = true;
      (this.form = row), (this.open = true);
      this.title = "查看联络员维护 ";
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            if (this.form.liaisonsId != null) {
              updateXunchaLiaisonsManage(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addXunchaLiaisonsManage(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const liaisonsIds = row.liaisonsId || this.ids;
      this.$confirm("是否删除当前联络员", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delXunchaLiaisonsManage(liaisonsIds);
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
      this.$confirm("是否确认导出所有联络员数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportXunchaLiaisonsManage(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "联络员维护 数据导入";
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
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
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
