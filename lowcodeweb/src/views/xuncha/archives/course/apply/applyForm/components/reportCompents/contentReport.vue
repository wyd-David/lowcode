<template>
  <div>
    <div class="form-module-title">
      <span class="line"></span><span>归档清单</span>
    </div>

    <div class="module-context-row">
      <template v-if="authorizeFlag == 2">
        <div class="operation" v-if="roleType == '1'">
          <el-button type="primary" size="small" @click="onRestore"
            >还原</el-button
          >
          <el-button type="primary" size="small" @click="addList"
            >添加资料</el-button
          >
          <el-button
            type="primary"
            size="small"
            :disabled="isDisabledDele"
            @click="deleteSelect"
            >批量删除</el-button
          >
        </div>
      </template>

      <el-table
        :data="tableData"
        row-key="contentId"
        :row-class-name="rowClassName"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="left" />
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column
          width="180"
          show-overflow-tooltip
          label="目录名称"
          prop="fullName"
        />
        <el-table-column label="资料移交时间" prop="archiveDate" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.archiveDate }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="资料提供方"
          prop="archiveOrgType"
          width="180"
          show-overflow-tooltip
          :formatter="archiveOrgTypeFormat"
        />
        <el-table-column label="说明" show-overflow-tooltip prop="remark" />
        <el-table-column align="center" label="操作" v-if="authorizeFlag == 2">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="mini"
              v-if="roleType == '1'"
              @click="deleteData(scope.row)"
              >删除</el-button
            >
            <el-button
              type="text"
              size="mini"
              @click="onUpload(scope.row)"
              v-if="roleType != '4' && roleType != '1'"
              >上传附件</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      title="清单列表"
      append-to-body
      :visible.sync="isSourceList"
      width="800px"
    >
      <source-list ref="sourceList" :tableData="sourceList" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="isSourceList = false">取 消</el-button>
        <el-button type="primary" @click="upload">确 认</el-button>
      </div>
    </el-dialog>
    <uploadForm
      v-if="uploadFormShow"
      :dialogVisible="uploadFormShow"
      ref="uploadForm"
      @file-success="fileSuccess"
      @close="uploadFormShow = false"
    />
  </div>
</template>
<script>
import uploadForm from "@/components/CommonUpload/uploadForm";
import sourceList from "./sourceReportList.vue";

export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    //角色类型 1：主管 2-主责人 3-共享人 4-审批人
    roleType: {
      type: String,
      default: "",
    },
    authorizeFlag: {
      type: String,
      default: "1",
    },
  },
  components: { uploadForm, sourceList },
  data() {
    return {
      multipleSelection: [], //多选
      tableData: [],
      // 文件上传
      uploadFormShow: false,
      isDisabledDele: true,
      formRules: {},
      batchs: [],

      isSourceList: false,
      selectedRow: null,
      sourceList: [],
      // 归档组织字典
      archiveOrgTypeOptions: [],
    };
  },
  mounted() {
    this.getDicts("process_content_archive").then((response) => {
      this.archiveOrgTypeOptions = response.data || [];
    });
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
  },
  methods: {
    // 资料提供方字典翻译
    archiveOrgTypeFormat(row, column) {
      return this.selectDictLabel(
        this.archiveOrgTypeOptions,
        row.archiveOrgType
      );
    },
    onUpload(row) {
      this.uploadFormShow = true;
      this.selectedRow = row;
    },
    // 打开清单

    addList() {
      this.isSourceList = true;
    },
    fileSuccess(files) {
      console.log("complete", files);
      this.$bus.$emit("setRow", {
        name: this.selectedRow.fullName,
        contentId: this.selectedRow.contentId,
        files,
      });
    },

    handleClose() {
      this.$emit("close");
    },
    upload() {
      // console.log(this.$refs.sourceList.multipleSelection)
      this.tableData = JSON.parse(
        JSON.stringify(this.$refs.sourceList.renderDynamic)
      );

      this.isSourceList = false;
    },
    deleteData(row) {
      row.isSelect = false;
      // this.$refs.sourceList.$refs.product.toggleRowSelection(row, false);
    },
    init() {
      Object.assign(this.$data, this.$options.data());
    },
    rowClassName({ row }) {
      if (!row.isSelect) {
        return "hiddenRow";
      }
    },
    onRestore() {
      this.tableData = JSON.parse(
        JSON.stringify(this.$refs.sourceList.renderDynamic)
      );
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      console.log(selection);
      this.ids = selection.map((item) => item.contentId);
      this.isDisabledDele = this.ids.length == 0;
      this.multipleSelection = selection;
    },
    // 批量删除
    deleteSelect() {
      this.multipleSelection.forEach((row) => {
        row.isSelect = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .hiddenRow {
  display: none;
}
.dialog-context {
  .form-module-title {
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

  .module-context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;
    .operation {
      position: absolute;
      right: 0;
      left: 0;
      top: -30px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 40px;
    }
  }
}
</style>