<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="120px"
        >
          <el-form-item label="业务单号：" prop="applyOrderNo">
            <el-input
              v-model="queryParams.applyOrderNo"
              placeholder="请输入业务单号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
         <el-form-item label="所在单位：" prop="company">
             <company
              v-model="queryParams.company"
              :label.sync="queryParams.companyName"
              size="small"
              clearable
              width="100%"
            />
          </el-form-item>
          <el-form-item label="部门：" prop="orgName">
            <el-input
              v-model="queryParams.orgName"
              placeholder="请选择部门"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="当前状态：" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="请选择"
              clearable
              size="small"
            >
              <el-option
                v-for="(dict,index) in statusList"
                :key="dict"
                :label="dict"
                :value="index"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="申请时间：" prop="createdTime">
            <el-date-picker
              clearable
              size="small"
              style="width: 200px"
              v-model="queryParams.createdTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择创建时间"
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
      <span class="line"></span><span>零星入库</span>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['replaceManage:sporadicTalentUserInfo:add']"
            >申请入库
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
        :data="sporadicTalentUserInfoList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column label="业务单号"  width="180" prop="applyOrderNo" />
        <el-table-column label="用户姓名"  prop="userName" />
        <el-table-column label="所在单位" width="130" prop="companyName" />
        <el-table-column label="部门名称" show-overflow-tooltip width="140" prop="orgName" />
        <el-table-column
          label="专家领域"
          width="140"
          prop="majorType"
        >
        <template slot-scope="scope">
          <span>{{scope.row.majorType | majorTypeFilter(fieldList)}}</span>
        </template>
        </el-table-column>
        <el-table-column
          label="申请时间"
          align="center"
          prop="createdTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{scope.row.createdTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="当前状态" width="160"  prop="status" >
           <template slot-scope="scope">
          <span>{{scope.row.examineStatus | statusFilter(statusList)}}</span>
           </template>
        </el-table-column>
        <el-table-column label="申请人" width="130" align="center" prop="createdName" />
        <el-table-column
          label="操作"
          align="center"
          fixed="right"
          min-width="140"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
              >查看
            </el-button>
            <el-button
            v-if="scope.row.examineStatus==='0'"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['replaceManage:sporadicTalentUserInfo:edit']"
              >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              v-if="scope.row.status==0"
              @click="handleDelete(scope.row)"
              v-hasPermi="['replaceManage:sporadicTalentUserInfo:remove']"
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
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="1000px"
      :close-on-click-modal="false"
      :before-close="cancel"
      append-to-body="append-to-body"
    >
      <sporadicTalentDialog
      :ischeck="1"
        :isEditAuthority="isEditAuthority"
        @close="cancel"
        :id="applyOrderNo"
        v-if="dialogVisible"
      ></sporadicTalentDialog>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  listSporadicTalentUserInfo,
  getSporadicTalentUserInfo,
  delSporadicTalentUserInfo,
  addSporadicTalentUserInfo,
  updateSporadicTalentUserInfo,
  exportSporadicTalentUserInfo,
  importTemplate,
  disableEnable,
} from "@/api/xuncha/replaceManage/sporadicTalentUserInfo";

import sporadicTalentDialog from "./sporadicTalentDialog.vue";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
import { listData } from "@/api/system/dict/data";
import Company from "@/components/XunCha/Company/index";

export default {
  name: "SporadicTalentUserInfo",
  components: { ExpandCollapse,Company, sporadicTalentDialog },
  data() {
    return {
      applyOrderNo: null,
      disabled: false,
      // 可编辑权限
      isEditAuthority:false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      dialogVisible: false,
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
      // 人员更替_零星入库人员信息表格数据
      sporadicTalentUserInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户导入参数

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyOrderNo: null,
        userId: null,
        userName: null,
        companyId: null,
        companyName: null,
        orgId: null,
        orgName: null,
        orgRemarks: null,
        postName: null,
        rankLevel: null,
        mailbox: null,
        phone: null,
        birthday: null,
        majorType: null,
        workingHours: null,
        joiningPartyDay: null,
        isXunucha: null,
        xunchaYear: null,
        isFrontline: null,
        resumeAttId: null,
        status: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      fieldList:[],
      statusList:["草稿"  ,"地市公司党办主任审核" , "巡察办主管审核" ,"已结束","地市公司党委书记审核","巡察办室经理审核"]
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
    majorTypeFilter(val,fineUpTypeOption){
      let item = fineUpTypeOption.find(i=>val==i.dictValue)
      return item?item.dictLabel:''
    },
    statusFilter(val,fineUpTypeOption){
      return fineUpTypeOption[val]
    }
  },
  created() {
    this.getList();
    let param = {
      dictType:'field_type',
       pageNum: 1,
        pageSize: 100,
    }
    listData(param).then(res=>{
      this.fieldList = res.rows
    })
  },
  methods: {
    /** 查询人员更替_零星入库人员信息列表 */
    getList() {
      this.loading = true;
      listSporadicTalentUserInfo(this.queryParams).then((response) => {
        this.sporadicTalentUserInfoList = response.rows || [];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel(code) {
      this.dialogVisible = false;
      this.applyOrderNo = null;
      this.reset();
      if(code==200){
        this.getList()
      }
    },
    // 表单重置
    reset() {
      this.form = {
        sporadicId: null,
        userId: null,
        userName: null,
        companyId: null,
        companyName: null,
        orgId: null,
        orgName: null,
        orgRemarks: null,
        postName: null,
        rankLevel: null,
        mailbox: null,
        phone: null,
        birthday: null,
        majorType: null,
        workingHours: null,
        joiningPartyDay: null,
        isXunucha: null,
        xunchaYear: null,
        isFrontline: null,
        resumeAttId: null,
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.sporadicId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.dialogVisible = true;
      this.isEditAuthority = true;
      this.title = "新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.dialogVisible = true;
      this.applyOrderNo = row.applyOrderNo;
      this.isEditAuthority = true;
      this.title = "修改";
    },
    /**详情按钮*/
    handleQueryOption(row) {
      // this.reset();
      this.title = "查看";
      this.dialogVisible = true;
      this.isEditAuthority = false;
      this.applyOrderNo = row.applyOrderNo;
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.sporadicId != null) {
            updateSporadicTalentUserInfo(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSporadicTalentUserInfo(this.form).then((response) => {
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
      const sporadicIds = row.sporadicId || this.ids;
      this.$confirm(
        '删除后将不能恢复，确定删除？',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delSporadicTalentUserInfo(sporadicIds);
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
      this.$confirm(
        "是否确认导出所有人员更替_零星入库人员信息数据项?",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return exportSporadicTalentUserInfo(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "人员更替_零星入库人员信息数据导入";
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
/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
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

