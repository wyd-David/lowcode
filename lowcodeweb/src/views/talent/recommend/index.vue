<template>
  <div class="app-main">
    <div class="dialog-title">
      <span class="line"></span><span>待办信息</span>
    </div>
    <div
      class="context-row"
      style="margin-bottom: 0; padding-top: 0; padding-bottom: 0"
    >
      <el-form :model="formData" ref="formRef" label-width="120px">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="收集时间:">
              <el-date-picker
                style="width: 100%"
                @change="getDetail"
                v-model="year"
                type="year"
                value-format="yyyy"
                placeholder="选择年"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标题:">
              <!-- <el-input
                style="width: 100%"
                placeholder="请输入标题"
                v-model="formData.title"
              ></el-input> -->
              <el-select
                @change="getDetail"
                clearable
                style="width: 100%"
                v-model="title"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in titleSelect"
                  :label="item.title"
                  :value="item.title"
                  :key="item.title"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="业务单号:">
              <el-input
                disabled
                style="width: 100%"
                v-model="formData.orderNumber"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="收集截止时间:">
              <el-date-picker
                disabled
                style="width: 100%"
                v-model="formData.joinPartDate"
                type="date"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="名额指标:">
              <el-input
                disabled
                style="width: 100%"
                :value="tableTotal"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button
              @click="todoInfo"
              style="margin-left: 20px"
              size="small"
              type="primary"
              >待办详情</el-button
            >
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="dialog-title">
      <span class="line"></span><span>待办下发</span>
    </div>
    <div
      class="context-row"
      style="margin-bottom: 0; padding-top: 0; padding-bottom: 0"
    >
      <el-form label-width="120px">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="业务单号:">
              <el-input
                placeholder="请输入业务单号"
                style="width: 100%"
                v-model="queryForm.orderNumber"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="责任部门:">
              <el-select
              style="width: 100%"
                clearable
                v-model="queryForm.deptId"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in deptSelect"
                  :key="'d' + item.deptId"
                  :label="item.deptName"
                  :value="item.deptId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="当前状态:">
              <el-select
              style="width: 100%"
                clearable
                v-model="queryForm.processStatus"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in typeSelect"
                  :key="'t' + item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
            <el-button @click="reset" size="small">重置</el-button>
            <el-button type="primary" @click="getList" size="small"
              >查询</el-button
            >
        </el-form-item>
      </el-form>
    </div>
    <div class="dialog-title">
      <span class="line"></span
      ><span
        >下发任务列表<span style="color: #ec832f;font-weight: 400;"
          >（温馨提示：请在完成对各部门人才审核后，统一提交）</span
        ></span
      >
    </div>
    <div class="context-row">
      <div class="operation" v-if="formData.id">
        <el-button @click="handleUrge" size="small" style="margin-right: 10px"
          >批量催办</el-button
        >
        <el-button size="small" style="margin-right: 10px" @click="exportExcel"
          >导出</el-button
        >
        <el-button size="small" @click="listDownload">名单下载</el-button>
        <el-button
          type="primary"
          @click="oneKeySubmit"
          size="small"
          style="margin-right: 10px"
          >一键提交</el-button
        >
      </div>
      <el-table
        v-loading="loading"
        :data="tableData"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column align="center" type="selection" width="60">
        </el-table-column>
        <el-table-column label="序号" align="center" type="index"></el-table-column>
        <el-table-column
          align="left"
          min-width="150"
          show-overflow-tooltip
          prop="orderNumber"
          label="业务单号"
        ></el-table-column>
        <el-table-column
          align="left"
          show-overflow-tooltip
          width="150"
          prop="companyName"
          label="单位"
        ></el-table-column>
        <el-table-column
          width="150"
          show-overflow-tooltip
          align="left"
          prop="deptName"
          label="责任部门"
        ></el-table-column>
        <el-table-column
          prop="receiveUserName"
          align="left"
          label="接收人"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="expertTotals"
          label="指标人数"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="receiveTotals"
          label="实际推荐人数"
        ></el-table-column>
        <el-table-column
          align="left"
          width="150"
          show-overflow-tooltip
          prop="processStatus"
          :formatter="processStatusFormat"
          label="当前状态"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="createdTime"
          width="100"
          label="下发时间"
        ></el-table-column>
        <el-table-column align="left" fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              type="text"
              v-if="['20', '50'].indexOf(scope.row.processStatus) != -1 && formData.id"
              @click="apply(scope.row)"
              >审核</el-button
            >
            <el-button
              type="text"
              @click="handleUrge(scope.row)"
              v-if="scope.row.processStatus < 20 && formData.id"
              >催办</el-button
            >
            <el-button type="text" @click="showDetail(scope.row)"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top:20px;"></div>
      <pagination
        :total="total"
        :page.sync="queryForm.pageNum"
        :limit.sync="queryForm.pageSize"
        @pagination="getList"
      />
      <!-- <div :class="hasScrollbar ? 'pageBox' : 'pageBox hasScrollbar'">
        <pagination
          :total="total"
          :page.sync="queryForm.pageNum"
          :limit.sync="queryForm.pageSize"
          @pagination="getList"
        />
      </div> -->
    </div>
    <addForm
      :dialogVisible="addOpen.open"
      :type="addOpen.type"
      :todoInfo="formData"
      :id="formData.id"
      @close="handleClose"
    ></addForm>
    <applyForm
      v-if="applyOpen.open"
      :dialogVisible="applyOpen.open"
      :type="applyOpen.type"
      :obj="applyOpen.obj"
      @close="handleClose"
    ></applyForm>
  </div>
</template>
<script>
import { getDeptList } from "@/api/talent/collection";
import { getByColletionId, urge, downRecommendPeople,downloadZip } from "@/api/talent/dept";
import { downloadFile,downloadZipFile } from "@/utils/uploadOperate";
import {
  checkSubmit,
  getCompanyTodoInfo,
  exportDept,
} from "@/api/talent/recommendSchedule";
import addForm from "./addForm";
import applyForm from "./applyForm";
//测试表单，人才汇总待生成列表页
import { getCompanyAll } from "@/api/xuncha/XunchaLunciManage";
import { getDicts } from "@/api/system/dict/data";

export default {
  components: { addForm, applyForm },
  created() {
    this.handleOpen();
  },
  data() {
    return {
      title: "",
      hasScrollbar: false,
      loading: false,
      addOpen: {
        open: false,
        type: "1",
      },
      total:0,
      type: "1",
      applyOpen: {
        open: false,
        type: "1",
      },
      queryForm: {
        orderNumber: "",
        deptId: "",
        processStatus: "",
        pageNum: 1,
        pageSize: 10,
      },
      collectTodoInfo: {},
      detailObj: {},
      deptSelect: [],
      typeSelect: [],
      year: new Date().getFullYear() + "",
      disabled: true,
      allFlowStep: [],
      companyList: [],
      formData: {
        expertTotals: 0,
      },
      collectDicts: [], //收集字典
      multipleSelection: [], //表格多选
      tableData: [],
      titleSelect: [],
    };
  },
  
  computed: {
    tableTotal() {
      let count = 0;
      this.tableData.forEach((item) => {
        count += Number(item.expertTotals || 0);
      });
      return count + "人";
    },
  },
  mounted() {
    this.getScrollbar();
    window.onresize = () => {
      this.getScrollbar();
    };
  },
  methods: {
    listDownload() {
      let param = {
        colletionId: this.formData.id,
        companyId: this.formData.companyId,
      }
      if (this.multipleSelection.length) { 
        param = {
          colletionId: this.formData.id,
          deptIds:this.multipleSelection.map(item=>item.deptId).join(","),
        }
      }
      downloadZip(param).then(res => { 
        this.download(res.msg);
      })
      downRecommendPeople(param).then(res => { 
        this.download(res.msg);
      })
    },
    getScrollbar(){ 
      this.hasScrollbar =
      document.body.scrollHeight >
      (window.innerHeight || document.documentElement.clientHeight);
    },
    exportExcel() {
      let params = {
        ...this.queryForm,
        colletionId: this.formData.id,
        createdTime: this.year + "-01-01",
      };
      if (this.multipleSelection.length > 0) {
        params = {
          ids: this.multipleSelection.map((res) => res.id).join(","),
        };
      }
      exportDept(params).then((res) => {
        this.download(res.msg);
      });
    },
    oneKeySubmit() {
      if (!this.tableData.length) {
        this.$message.warning("暂无下发任务列表,不能提交");
        return;
      }
      if (Number(this.tableData[0].processStatus) >= 40) {
        this.$message.warning("已经提交过了,不能提交");
        return;
      }
      checkSubmit({ colletionId: this.formData.id }).then((res) => {
        if (res.code == 200) {
          this.addOpen.type = "1";
          this.addOpen.open = true;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    apply(row) {
      this.applyOpen.obj = row;
      this.applyOpen.obj.title = this.formData.title;
      this.applyOpen.type = "1";
      this.applyOpen.open = true;
    },
    //催办
    handleUrge(row) {
      let ids = row.id;

      if (!row.id) {
        ids = [];
        this.multipleSelection.forEach(item => { 
          if (Number(item.processStatus) < 20) {
            ids.push(item.id);
          }
        })
        ids = ids.join(",");
        if (!ids) {
          this.$message.warning("请选择要催办的数据,状态为未推荐的数据");
          return;
        }
      }
      this.$confirm("确定要催办吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        urge(ids).then((res) => {
          if (res.code == 200) {
            this.$message({
              type: "success",
              message: "催办成功!",
            });
            this.getList();
          }
        });
      });
    },
    todoInfo() {
      if (!this.formData.orderNumber) {
        this.$message.warning("该年份暂无待办信息!");
        return;
      }
      this.addOpen.type = "2";
      this.addOpen.open = true;
      // this.$router.push({
      //   path: "/todo/rctjgs_todo_bpm",
      //   query: {
      //     id: this.formData.orderNumber,
      //   },
      // });
    },
    // 状态字典翻译
    processStatusFormat(row, column) {
      return this.selectDictLabel(this.typeSelect, row.processStatus);
    },
    //表格多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //查看
    showDetail(row) {
      this.applyOpen.obj = row;
      this.applyOpen.obj.title = this.formData.title;
      this.applyOpen.type = "2";
      this.applyOpen.open = true;
    },
    handleClick(e) {},
    handleCompanySelect(e, row) {
      row.companyId = e.companyId;
      row.companyName = e.companyName;
    },
    handleOpen() {
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
      });
      getDicts("dept_process_status").then((res) => {
        this.typeSelect = res.data.filter(i => i.dictValue != '1');
      });
      getCompanyAll().then((res) => {
        this.companyList = res.data;
      });
      this.detailInit();
    },
    detailInit() {
      this.getDetail();
    },
    //获取详情
    getDetail() {
      getCompanyTodoInfo({
        createdTime: this.year + "-01-01",
        title: this.title,
      }).then((res) => {
        if (res.code == 200) {
          this.formData = res.data[0] || {};
          this.title = this.formData.title;
          if (!this.titleSelect.length) {
            this.titleSelect = res.data || [];
          }
          if (res.data[0]) {
            getDeptList({ companyId: res.data[0].companyId }).then((res) => {
              this.deptSelect = res.data;
            });
          }
          this.getList();
        }
      });
    },
    reset() {
      this.queryForm = {
        orderNumber: "",
        deptId: "",
        processStatus: "",
      };
      this.getList();
    },
    getList() {
      this.loading = true;
      getByColletionId({
        ...this.queryForm,
        colletionId: this.formData.id,
        createdTime: this.year + "-01-01",
      }).then((res) => {
        if (res.code == 200) {
          this.tableData = res.rows;
          this.total = Number(res.total);
        }
        this.loading = false;
        this.getScrollbar();
      });
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
    handleClose(e) {
      this.applyOpen.open = false;
      this.addOpen.open = false;
      if (e) {
        this.getList();
      }
    },
    handleTabClick(tab) {},
    add(t) {},
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
/deep/ input::-webkit-outer-spin-button,
/deep/ input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}
/deep/ input[type="number"] {
  -moz-appearance: textfield !important;
}
/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}

/deep/ .el-form-item__error {
  width: 280px;
}
.app-main {
  padding: 15px;
  background: #fff;
  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;
    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }

  .context-row {
    padding: 10px 20px;
    margin-bottom: 15px;
    position: relative;
    .operation {
      position: absolute;
      right: 0;
      top: -35px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 30px;
      z-index: 9;
    }
  }
}
.hasScrollbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding-right: 40px;
}
.app-footer {
  text-align: center;
  background: #fff;
  padding-bottom: 20px;
}
</style>
