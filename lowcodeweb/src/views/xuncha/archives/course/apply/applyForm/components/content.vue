<template>
  <div>
    <div class="form-module-title">
      <span class="line"></span><span>归档清单</span>
    </div>

    <div class="module-context-row">
        <div class="operation" v-if="roleType == '1'">
          <el-button type="primary" size="small" @click="onRestore"
            >还原</el-button
          >
          <el-button type="primary" size="small" @click="addList"
            >添加资料</el-button
          >
          <el-button type="primary" size="small" :disabled="isDisabledDele" @click="deleteSelect"
            >批量删除</el-button
          >
        </div>

      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" width="55" />
        <el-table-column prop="archiveScope" width="150" show-overflow-tooltip label="归档范围">
          <template slot-scope="scope">
            <div :class="{'archiveScope_require':scope.row.requireFlag==1}">{{ scope.row.archiveScope }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="contentNo" label="序号" />
        <el-table-column
          prop="archiveType"
          
          label="类别"
          width="130"
          show-overflow-tooltip
          :formatter="archiveTypeFormat"
        />
        <el-table-column
          prop="xunchaStage"
          align="center"
          label="对应巡察阶段"
          width="120"
          :formatter="xunchaStageFormat"
        />
        <el-table-column
          prop="archiveOrgType"
          align="center"
          label="归档组织"
          :formatter="archiveOrgTypeFormat"
        />
        <el-table-column prop="requireFlag" align="center" label="必传">
          <template slot-scope="scope">
            <span v-if="scope.row.requireFlag === '0'">非必传</span>
            <span v-else>必传</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="archiveDate"
          align="center"
          label="资料移交时间"
          width="130"
          show-overflow-tooltip
        />
        <template v-if="authorizeFlag == '2'">
          <el-table-column align="center" label="操作" v-if="roleType != '4'">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="mini"
                v-if="roleType == '1'"
                @click="deleteData(scope)"
                >删除</el-button
              >
              <el-button
                type="text"
                size="mini"
                v-if="roleType == '2' || roleType == '3'"
                @click="onUpload(scope.row)"
                >上传附件</el-button
              >
            </template>
          </el-table-column>
        </template>
      </el-table>
    </div>
    <el-dialog
      title="清单列表"
      append-to-body
      :visible.sync="isSourceList"
      width="800px"
      @open="handleOpen"
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
import sourceList from "./sourceList.vue";

import { getSourceList } from "@/api/xuncha/archives/xunchaProcessContent.js";
import { queryDictList } from "@/api/xuncha/common/base";

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
      default: "",
    },
  },
  components: { uploadForm, sourceList },
  data() {
    return {
      multipleSelection: [], //多选
      tableData: [],
      // 文件上传
      uploadFormShow: false,
      formRules: {},
      batchs: [],
      // tableData: [{}], //入库资料信息
      taskInfo: [{}], //流程信息
      nextObj: {
        //下一步信息
        remark: "",
      },
      isDisabledDele:true,
      isSourceList: false,
      selectedRow: null,
      sourceListParams: {},
      sourceList: [],
      // 入库资料类型字典
      archiveTypeOptions: [],
      // 巡察阶段字典
      xunchaStageOptions: [],
      // 归档组织字典
      archiveOrgTypeOptions: [],
    };
  },
  mounted() {
    this.onRestore()
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
  },
  created() {
    queryDictList({ dictType: "process_apply_archive" }).then((res) => {
      this.archiveTypeOptions = res.data || [];
    });
    this.getDicts("sys_archive_type").then((response) => {
      this.xunchaStageOptions = response.data || [];
    });
    this.getDicts("process_content_archive").then((response) => {
      this.archiveOrgTypeOptions = response.data || [];
    });
  },
  methods: {
    // 入库资料类型字典翻译
    archiveTypeFormat(row, column) {
      for (let i = 0; this.archiveTypeOptions.length > i; i++) {
        if (row.archiveType === this.archiveTypeOptions[i].dataCode) {
          return this.archiveTypeOptions[i].dataName;
        }
      }
      return row.archiveType;
    },
    // 巡察阶段字典翻译
    xunchaStageFormat(row, column) {
      return this.selectDictLabel(this.xunchaStageOptions, row.xunchaStage);
    },
    // 归档组织字典翻译
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
    handleOpen() {
      let parent = this.$parent;
      let formData = parent._data.formData;
      this.sourceListParams = {
        archive_org_type: formData.archiveOrgType,
        archive_type: formData.archiveType,
      };
      getSourceList(this.sourceListParams).then((res) => {
        this.sourceList = res.rows;
      });
    },
    addList() {
      this.isSourceList = true;
    },
    fileSuccess(files) {
      console.log("complete", files);
      this.$bus.$emit("setRow", {
        name: this.selectedRow.archiveScope,
        contentId: this.selectedRow.contentId,
        files,
      });
    },
    handleClose() {
      this.$emit("close");
    },
    upload() {
      // console.log(this.$refs.sourceList.multipleSelection)
      this.tableData = this.$refs.sourceList.multipleSelection;
      this.isSourceList = false;
    },
    deleteData(scope) {
      this.tableData.splice(scope.$index, 1);
      this.$emit('setStatus')
    },
    init() {
      Object.assign(this.$data, this.$options.data());
    },
    // 还原
    onRestore() {
      let formData = this.$parent.formData;
      console.log(formData);
      // console.log();
      // return
      this.sourceListParams = {
        archive_org_type: formData.archiveOrgType,
        archive_type: formData.archiveType,
      };
      getSourceList(this.sourceListParams).then((res) => {
        this.tableData = res.rows;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.contentId);
      this.isDisabledDele = this.ids.length==0
      this.multipleSelection = selection;
    },
    deleteSelect(){
      this.tableData = this.tableData.filter(item=>!this.ids.includes(item.contentId))
    }
  },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  height: 500px;
  overflow: auto;
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
  .archiveScope_require:before{
    content: '*';
    color: #ff4949;
    margin-right: 4px;
    margin-left: -8px;
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
