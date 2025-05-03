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
          <el-form-item label="业务单号:" prop="orderNumber">
            <el-input
              v-model="queryParams.orderNumber"
              placeholder="请输入业务单号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="标题:" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入标题"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="年份:" prop="createdTime">
            <el-date-picker
              clearable
              size="small"
              style="width: 205px"
              v-model="queryParams.createdTime"
              type="year"
              value-format="yyyy-MM-dd"
              placeholder="选择年份"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="当前状态:" prop="processStatus">
            <el-select
              v-model="queryParams.processStatus"
              placeholder="请选择"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in processStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="收集类型:" prop="collectionType">
            <el-select
              v-model="queryParams.collectionType"
              placeholder="请选择"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in collectionTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
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
    <div class="list-title">
      <span class="line"></span><span>名额分配申报记录</span>
    </div>
    <div class="list">
      <div class="operation">
        <el-button type="primary" size="mini" @click="handleAdd">
          名额分配申报
        </el-button>
        <!-- <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['talent:collection:remove']"
          >删除</el-button
        > -->
        <!-- <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleExport"
          v-hasPermi="['talent:collection:export']"
          >导出</el-button
        > -->
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </div>

      <el-table
        v-loading="loading"
        :data="collectionList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column
          label="业务单号"
          align="left"
          width="200"
          prop="orderNumber"
        />
        <el-table-column
          label="年份"
          align="center"
          prop="createdTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime, "{y}年") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="标题"
          min-width="150"
          show-overflow-tooltip
          align="left"
          prop="title"
        />
        <el-table-column
          label="收集类型"
          align="left"
          prop="collectionType"
          :formatter="collectionTypeFormat"
        />
        <!-- <el-table-column
          label="参加工作时间"
          align="center"
          prop="joinWorkDate"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.joinWorkDate, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column> -->
        <el-table-column
          show-overflow-tooltip
          label="当前状态"
          align="left"
          prop="processStatus"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.processStatus == '30'">
              {{ processStatusFormat(scope.row) }}
            </el-tag>
            <span v-else-if="scope.row.processStatus == '1'"> 草稿 </span>
            <span v-else>
              {{ processStatusFormat(scope.row) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          label="创建时间"
          align="center"
          width="200px"
          prop="createdTime"
        />

        <el-table-column
          label="操作"
          fixed="right"
          width="150"
          align="left"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看</el-button
            >
            <el-button
              size="mini"
              v-if="scope.row.processStatus == 30"
              type="text"
              @click="handleIssue(scope.row)"
            >
              下发
            </el-button>
            <el-button
              v-if="scope.row.processStatus == '1'"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['talent:collection:remove']"
              >删除</el-button
            >
            <el-button
              v-if="scope.row.processStatus == 1"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['talent:collection:edit']"
              >修改</el-button
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

    <el-dialog
      title="下发"
      append-to-body
      :visible.sync="issuedDialog.show"
      width="1200px"
      :before-close="close"
    >
      <div class="dialog-context">
        <apply-form
          :dialogVisible="issuedDialog.show"
          :key="new Date().getTime()"
          ref="refForm1"
          :id="issuedDialog.id"
          type="2"
          :disabled="disabled"
        ></apply-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="addIssued">确 定</el-button>
      </span>
    </el-dialog>
    <collectionForm
      :id="collectionFormObj.id"
      :type="collectionFormObj.type"
      :disabled="disabled"
      :dialogVisible="collectionFormObj.show"
      @close="close"
    ></collectionForm>
  </div>
</template>

<script>
import {
  listCollection,
  delCollection,
  exportCollection,
  importTemplate,
  disableEnable,
} from "@/api/talent/collection";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import collectionForm from "./collectionForm.vue";
import { formatDate } from "@/utils/index";
import { issued } from "@/api/talent/collection";
import applyForm from "./applyForm";
export default {
  name: "Collection",
  components: { ExpandCollapse, collectionForm, applyForm },
  data() {
    return {
      issuedDialog: {
        show: false,
        id: "",
      },
      collectionFormObj: {
        id: "",
        show: false,
        type: "1",
      },
      disabled: false,
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
      // 入库管理_人才收集管理 表格数据
      collectionList: [],
      // 弹出层标题
      title: "",
      // 收集类型字典
      collectionTypeOptions: [],
      // 流程状态字典
      processStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        title: null,
        collectionType: null,
        joinWorkDate: null,
        processStatus: null,
        createdTime: null,
        collectionType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        collectionType: [
          { required: true, message: "收集类型不能为空", trigger: "blur" },
        ],
        joinWorkDate: [
          { required: true, message: "参加工作时间不能为空", trigger: "blur" },
        ],
        joinPartDate: [
          { required: true, message: "入党时间不能为空", trigger: "blur" },
        ],
        startInspectTime: [
          {
            required: true,
            message: "计划巡察开始时间不能为空",
            trigger: "blur",
          },
        ],
        endInspectTime: [
          {
            required: true,
            message: "计划巡察结束时间不能为空",
            trigger: "blur",
          },
        ],
        collectionEndDate: [
          { required: true, message: "收集结束时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
    collection_typeFieldFilter(val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
    collection_process_statusFieldFilter(val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created() {
    this.getList();
    this.getDicts("collection_type").then((response) => {
      this.collectionTypeOptions = response.data || [];
    });
    this.getDicts("collection_process_status").then((response) => {
      this.processStatusOptions = response.data || [];
      this.processStatusOptions = this.processStatusOptions.filter(
        (i) => i.dictValue != "1"
      );
    });
  },
  methods: {
    addIssued() {
      let flag = false;
      let isUserList = false;
      let prefecturalTableData = this.$refs.refForm1.prefecturalTableData;
      let provinceTableData = this.$refs.refForm1.provinceTableData;
      prefecturalTableData.forEach((res) => {
        if (res.receiveUserId == "") {
          flag = true;
        }
        if (res.userList.length == 0) {
          isUserList = true;
        }
      });
      provinceTableData.forEach((res) => {
        if (res.receiveUserId == "") {
          flag = true;
        }
        if (res.userList.length == 0) {
          isUserList = true;
        }
      });
      if (isUserList) {
        this.$message.error("未选择接收人,请前往联络员维护进行配置");
        return;
      }
      if (flag) {
        this.$message.error("请选择联络员");
        return;
      }
      //弹窗确认
      this.$confirm("确认下发吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        const loading = this.openLoading();
        issued(
          JSON.stringify({
            id: this.issuedDialog.id,
            companyList: prefecturalTableData,
            deptList: provinceTableData,
          })
        ).then((res) => {
          this.$message.success("下发成功");
          this.getList();
          loading.close();
          this.issuedDialog.show = false;
        });
      });
    },
    //下发
    handleIssue(e) {
      this.issuedDialog.id = e.id;
      this.disabled = true;
      this.issuedDialog.show = true;
    },
    handleQueryOption(e) {
      this.collectionFormObj.type = "1";
      this.collectionFormObj.id = e.orderNumber;
      this.disabled = true;
      this.collectionFormObj.show = true;
    },
    handleAdd() {
      this.collectionFormObj.type = "1";
      this.collectionFormObj.id = "";
      this.disabled = false;
      this.collectionFormObj.show = true;
    },
    /** 查询入库管理_人才收集管理 列表 */
    getList() {
      this.loading = true;
      listCollection(this.queryParams).then((response) => {
        this.collectionList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
      });
    },
    // 收集类型字典翻译
    collectionTypeFormat(row, column) {
      return this.selectDictLabel(
        this.collectionTypeOptions,
        row.collectionType + ""
      );
    },
    // 流程状态字典翻译
    processStatusFormat(row, column) {
      return this.selectDictLabel(this.processStatusOptions, row.processStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderNumber: null,
        title: null,
        collectionType: "0",
        joinWorkDate: null,
        joinPartDate: null,
        startInspectTime: null,
        endInspectTime: null,
        collectionEndDate: null,
        remark: null,
        processStatus: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
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
      this.ids = selection.map((item) => item.orderNumber);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleUpdate(e) {
      this.collectionFormObj.id = e.id;
      this.disabled = false;
      this.collectionFormObj.show = true;
    },
    close(type) {
      this.collectionFormObj.show = false;
      this.issuedDialog.show = false;
      if (type == 200) {
        this.getList();
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.orderNumber || this.ids;
      this.$confirm("确定删除吗?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delCollection(ids);
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
      this.$confirm("是否确认导出所有入库管理_人才收集管理 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportCollection(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "入库管理_人才收集管理 数据导入";
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
  margin-top: 15px;

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

/deep/.el-tag {
  background-color: #ecf5ff !important;
  display: inline-block;
  height: 32px;
  padding: 0 10px;
  line-height: 30px;
  font-size: 12px;
  color: #409eff !important;
  border: 1px solid #d9ecff !important;
  border-radius: 4px;
  box-sizing: border-box;
  white-space: nowrap;
}
</style>
