<template>
  <!-- 数据权限-->
  <div>
    <div class="form-module-title">
      <span class="line"></span><span>归档资料</span>
    </div>

    <div class="module-context-row">
      <div class="operation">
        <el-button type="primary" :disabled="ids.length === 0" @click="batchDownload" size="small"
          >批量下载</el-button
        >
      </div>
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column  type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column prop="fullName"  label="目录名称"></el-table-column>

        <el-table-column
          prop="fileName"
          
          label="附件"
        ></el-table-column>
        <el-table-column
          prop="createdBy"
          align="center"
          label="提供人"
        ></el-table-column>
        <el-table-column prop="createdTime" align="center" label="提供时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createdTime) }}</span>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          v-if="authorizeFlag == '2' && roleType != '4'"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" @click="deleteData(scope)">删除</el-button>
            <el-button @click="reUpload(scope)" type="text">重传</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
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
import { parseTime } from "@/utils/ruoyi.js";
import {
  downloadFile,
  downloadZipFile,
} from "@/utils/uploadOperate";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    bussinessKey: {
      type: String,
      default: "",
    },
    authorizeFlag: {
      type: String,
      default: "1",
    },
    //角色类型 1：主管 2-主责人 3-共享人 4-审批人
    roleType: {
      type: String,
      default: "1",
    },
  },
  components: { uploadForm },

  data() {
    return {
      uploadFormShow: false,
      selectedRow: "",
      selectedRowIndex:null,
      multipleSelection: [], //多选
      ids:[],
      formData: {
        year: "",
      },
      formRules: {},
      batchs: [],
      tableData: [],
      taskInfo: [{}], //流程信息
      nextObj: {
        //下一步信息
        remark: "",
      },
    };
  },
  mounted() {
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
    this.$bus.$on("setRow", (obj) => {
      console.log("归档资料", obj);
      this.upload(obj);
    });
  },
  methods: {
    // 批量下载
    batchDownload() {
      let files = this.ids;
      if (files.length == 0) {
        this.msgError("请选择资料进行下载");
      } else {
        downloadZipFile(files);
      }
    },
    complete() {
      console.log("complete", arguments);
    },

    handleClose() {
      this.$emit("close", scope.$index);
    },

    reUpload(scope) {
      this.uploadFormShow = true;
      console.log(scope);
      this.selectedRow = JSON.parse(JSON.stringify(scope.row));
      this.selectedRowIndex = scope.$index;

    },

    fileSuccess(files) {
      //防止上传多个文件覆盖
      let tempIndex = 1;
      files.forEach((i) => {
        this.tableData.splice(this.selectedRowIndex,tempIndex,{
          applyId: this.bussinessKey,
          fullName: this.selectedRow.fullName,
          fileId: i.result.data.fileId,
          fileName: i.name,
          datumCode: "",
          datumName: i.name,
          previewSum: "",
          downloadSum: 0,
          createdBy: this.$store.getters.nickName,
          createdTime: parseTime(new Date()),
          contentId: this.selectedRow.contentId,
        });
        tempIndex = 0;
        this.selectedRowIndex++;
      });
    },

    upload(obj) {
      console.log(obj);
      let { name, files, contentId } = obj;
      files.forEach((i) => {
        this.tableData.push({
          applyId: this.bussinessKey,
          fullName: name,
          fileId: i.result.data.fileId,
          fileName: i.name,
          datumCode: "",
          datumName: i.name,
          previewSum: "",
          downloadSum: 0,
          createdBy: this.$store.getters.nickName,
          createdTime: parseTime(new Date()),
          contentId: contentId,
        });
      });
    },
    deleteData(scope) {
      this.tableData.splice(scope.$index, 1);
    },
    downloads() {},
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.fileId);
      this.multipleSelection = selection;
    },
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
