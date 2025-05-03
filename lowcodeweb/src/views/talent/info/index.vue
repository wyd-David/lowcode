<template>
  <div class="app-main">
    <permission-tabs ref="pt" @ptClick="ptClick"></permission-tabs>

    <div class="app-container">
      <expand-collapse v-show="showSearch">
        <div slot="left">
          <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            label-width="120px"
          >
            <el-form-item label="姓名:" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入姓名"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <company-dept-select
              ref="cds"
              :companyId.sync="queryParams.companyId"
              :deptId.sync="queryParams.deptId"
            ></company-dept-select>

            <el-form-item label="职务:" prop="postName">
              <el-input
                v-model="queryParams.postName"
                placeholder="请输入职务"
              ></el-input>
            </el-form-item>

            <el-form-item label="职级:" prop="postLevel">
              <el-input
                v-model="queryParams.postLevel"
                placeholder="请输入职级"
              ></el-input>
            </el-form-item>

            <el-form-item label="标签:" prop="label">
              <el-select
                multiple
                v-model="queryParams.label"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in labels"
                  :key="item.dataId"
                  :label="item.dataName"
                  :value="item.dataName"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="入库方式:" prop="storageType">
              <el-select v-model="queryParams.storageType" placeholder="请选择">
                <el-option label="常规入库" :value="0"></el-option>
                <el-option label="零星入库" :value="1"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="专家领域:" prop="filedTypeName">
              <el-select
                clearable
                size="small"
                v-model="queryParams.fieldType"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in collectDicts"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="OA邮箱:" prop="email">
              <el-input
                v-model="queryParams.email"
                placeholder="请输入OA邮箱"
              ></el-input>
            </el-form-item>

            <el-form-item label="手机号码:" prop="telephone">
              <el-input
                v-model="queryParams.telephone"
                placeholder="请输入手机号码"
              ></el-input>
            </el-form-item>

            <el-form-item label="出生年月:" prop="birthday">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="queryParams.birthday"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item label="入党时间:" prop="joinInPartDay">
              <el-date-picker
                v-model="queryParams.joinInPartDay"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item label="参加工作时间:" prop="joinInWorkDay">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="queryParams.joinInWorkDay"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
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
        <span class="line"></span><span>人才库</span>
      </div>
      <div class="list">
        <div class="operation">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            >导出</el-button
          >
          <right-toolbar
            :showSearch.sync="showSearch"
            class="normalBtn"
            @queryTable="getList"
          ></right-toolbar>
        </div>
        <el-table
          height="50vh"
          v-loading="loading"
          :data="infoList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="left" />
          <el-table-column
            label="序号"
            align="center"
            type="index"
          ></el-table-column>
          <el-table-column label="姓名" align="left" prop="name" />
          <el-table-column
            width="200"
            show-overflow-tooltip
            label="专家领域"
            align="left"
            prop="filedTypeName"
          >
          </el-table-column>
          <el-table-column
            show-overflow-tooltip
            label="所在单位"
            width="150"
            align="left"
            prop="companyName"
          />
          <el-table-column
            label="部门"
            show-overflow-tooltip
            align="left"
            width="150"
            prop="deptName"
          />
          <el-table-column
            label="职务"
            show-overflow-tooltip
            width="200"
            align="left"
            prop="postName"
          />
          <el-table-column label="职级" align="center" prop="postLevel" />
          <el-table-column label="标签" align="left" width="150" prop="label">
            <template slot-scope="scope">
              <div v-if="scope.row.label.split(',').length == 1">
                <div style="padding: 0;justify-content: left;" class="label-box" slot="reference">
                  <span class="label-item-t" style="margin: 0"
                    >{{ scope.row.label.split(",")[0] }}
                  </span>
                </div>
              </div>
              <el-popover
                v-else
                placement="top-start"
                width="300"
                trigger="hover"
              >
                <div class="label-box">
                  <span
                    class="label-item-t"
                    v-for="(item, index) in scope.row.label.split(',')"
                    :key="index"
                    >{{ item }}
                  </span>
                </div>
                <div
                  class="label-box"
                  slot="reference"
                  style="justify-content: left; padding: 0"
                >
                  <span class="label-item-t" style="margin: 0"
                    >{{ scope.row.label.split(",")[0] }}
                  </span>
                  ...
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column
            label="入库方式"
            align="center"
            prop="joinInspectionTimes"
          >
            <template slot-scope="scope">
              {{ ["常规入库", "零星入库"][scope.row.storageType] }}
            </template>
          </el-table-column>
          <el-table-column label="是否有巡察经历" width="150" align="center">
            <template slot-scope="scope">{{
              ["否", "是"][scope.row.inspectionExperience]
            }}</template>
          </el-table-column>
          <el-table-column
            label="巡察年份"
            show-overflow-tooltip
            align="center"
            prop="inspectionYear"
          />
          <el-table-column
            label="出生年月"
            width="150"
            align="center"
            prop="birthday"
          />
          <el-table-column
            width="150"
            label="参加工作时间"
            align="center"
            prop="joinInWorkDay"
          />
          <el-table-column
            width="150"
            label="入党时间"
            align="center"
            prop="joinInPartDay"
          />
          <el-table-column
            width="150"
            label="是否有一线工作经历"
            align="center"
            prop="frontlineWorkExperience"
          >
            <template slot-scope="scope">{{
              ["否", "是"][scope.row.frontlineWorkExperience] || "否"
            }}</template>
          </el-table-column>
          <el-table-column
            label="OA邮箱"
            width="150"
            show-overflow-tooltip
            align="left"
            prop="email"
          />
          <el-table-column
            label="手机号码"
            width="150"
            align="center"
            prop="telephone"
          />
          <el-table-column
            width="150"
            label="入库时间"
            align="center"
            prop="warehousingTime"
          />
          <el-table-column width="150" label="参加巡察次数" align="center">
            <template slot-scope="scope">
              {{ scope.row.joinInspectionTimes || 0 }}
            </template>
          </el-table-column>
          <el-table-column label="拒绝次数" align="center">
            <template slot-scope="scope">
              {{ scope.row.rejectInspectionTimes || 0 }}
            </template>
          </el-table-column>
          <el-table-column width="150" label="表扬表彰记录" align="center">
            <template slot-scope="scope">
              {{ scope.row.praisedTimes || 0 }}
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleQueryOption(scope.row)"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <!-- <el-table
          v-else
          v-loading="loading"
          :data="infoList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="left" />
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column label="姓名" align="center" prop="name" />
          <el-table-column
            label="专家领域"
            align="center"
            prop="filedTypeName"
          />
          <el-table-column label="所在单位" align="center" prop="companyName" />
          <el-table-column label="部门" align="center" prop="deptName" />
          <el-table-column label="职务" align="center" prop="postName" />
          <el-table-column label="职级" align="center" prop="postLevel" />
          <el-table-column
            label="专业"
            align="center"
            prop="inspectionExperience"
          />
          <el-table-column
            label="是否参加2020年巡察"
            align="center"
            prop="joinInspectionTimes"
          />
          <el-table-column label="出生年月" align="center" prop="refuseTimes" />
          <el-table-column
            label="参加工作时间"
            align="center"
            prop="praisedTimes"
          />
          <el-table-column label="出生年月" align="center" prop="status" />
          <el-table-column label="入党时间" align="center" prop="createdBy" />
          <el-table-column label="入党时间" align="center" prop="updatedBy" />
          <el-table-column
            label="是否有一线工作经历"
            align="center"
            prop="updatedBy"
          />
          <el-table-column label="OA邮箱" align="center" prop="updatedBy" />
          <el-table-column label="手机号码" align="center" prop="updatedBy" />
          <el-table-column label="入库时间" align="center" prop="updatedBy" />
          <el-table-column
            label="参加巡察次数"
            align="center"
            prop="updatedBy"
          />
          <el-table-column label="拒绝次数" align="center" prop="updatedBy" />
          <el-table-column
            label="表扬表彰记录"
            align="center"
            prop="updatedBy"
          />
          <el-table-column label="推优情况" align="center" prop="updatedBy" />
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleQueryOption(scope.row)"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table> -->

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
      <!-- 在库管理_人才库 导入对话框 -->
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

      <collectForm
        :dialogVisible="iFormShow.open"
        :id="iFormShow.id"
        :storageType="iFormShow.storageType"
        more
        @close="handleClose"
      ></collectForm>
    </div>
  </div>
</template>

<script>
// import iForm from "./iForm";
import CompanyDeptSelect from "../collect/CompanyDeptSelect";
import collectForm from "../collect/collectForm";
import permissionTabs from "@/components/PermissionTabs/index";
import { getToken } from "@/utils/auth";
import {
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
  exportInfo,
  importTemplate,
  disableEnable,
} from "@/api/talent/info";
import { queryDictList } from "@/api/talent/config";
import { getDicts } from "@/api/system/dict/data";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
export default {
  name: "Info",
  components: {
    ExpandCollapse,
    permissionTabs,
    collectForm,
    CompanyDeptSelect,
  },
  data() {
    return {
      tabShow: "巡察人才库",
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
      // 在库管理_人才库 表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      iFormShow: {
        open: false,
        id: "",
        disabled: true,
      },
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
        url: process.env.VUE_APP_BASE_API + "/xuncha/info/importData",
      },
      labels: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        collectionType: 1,
        name: "",
        companyId: "",
        deptId: "",
        postName: "",
        postLevel: "",
        label: [],
        storageType: "",
        filedTypeName: "",
        email: "",
        telephone: "",
        birthday: "",
        joinInPartDay: "",
        joinInWorkDay: "",
      },
      //用于首页跳转巡察支撑人才库
      initList: false,
      collectDicts: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    //收集字典
    getDicts("field_type").then((res) => {
      this.collectDicts = res.data;
    });
    //查询标签列表
    queryDictList({
      dictType: "xuncha_label",
    })
      .then((res) => {
        this.labels = res.data.filter(
          (item) => ["0", "1", "7"].indexOf(item.dataCode) == -1
        );
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    ptClick(title) {
      this.tabShow = title;
      if (title == "巡察人才库") {
        this.queryParams.collectionType = 1;
      } else {
        this.queryParams.collectionType = 2;
      }
      this.getList();
    },
    /** 查询在库管理_人才库 列表 */
    getList() {
      this.loading = true;
      listInfo({
        ...this.queryParams,
        label: this.queryParams.label.join(","),
      }).then((response) => {
        this.infoList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
        if (!this.initList) {
          let activeName = this.$route.query.activeName || "";
          if (activeName) {
            this.$refs.pt.simulateClick(activeName);
          }
          this.initList = true;
        }
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
        id: null,
        colletionId: null,
        collectionType: null,
        userId: null,
        name: null,
        fieldType: null,
        inspectionExperience: null,
        joinInspectionTimes: null,
        refuseTimes: null,
        praisedTimes: null,
        status: "0",
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
      this.$refs.cds.reset();
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.iFormShow.id = row.id || this.ids;
      this.iFormShow.open = true;
    },
    /**详情按钮*/
    handleQueryOption(row) {
      this.iFormShow.id = row.userId;
      this.iFormShow.storageType = row.storageType;
      this.iFormShow.open = true;
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addInfo(this.form).then((response) => {
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
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除在库管理_人才库 编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delInfo(ids);
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
      let that = this;
      let queryParams = this.queryParams;
      this.$confirm("是否确认导出所有在库管理_人才库 数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportInfo(
            that.ids.length ? { ids: that.ids.join(",") } : queryParams
          );
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function (err) {
          console.log(err);
        });
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "在库管理_人才库 数据导入";
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
    handleClose() {
      this.iFormShow.open = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.el-form {
  /deep/.el-input {
    width: 200px;
  }
}
.app-container {
  background: #f5f7f9;
  padding-top: 10px;
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
</style>