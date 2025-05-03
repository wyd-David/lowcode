<template>
  <div>
    <el-dialog
      title="文件上传"
      height="auto"
      modal-append-to-body
      append-to-body
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >

      <div class="dialog-context">
          <div class="context">
            <CommonUpload
                  ref="CommonUpload"
                  :single-file="singleFile"
                  @file-success="onFileSuccess"
                  @file-removed="onFileRemoved"
                ></CommonUpload>
          </div>

        </div>


      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="complete"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";


export default {
  components: { CommonUpload },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    singleFile:{
      type: Boolean,
      default: false,
    }

  },
  data() {
    return {
      multipleSelection: [], //多选
      files:[],
      fileList:[]

    };
  },
  methods: {
    //成功回调
    onFileSuccess(file,files,fileList) {
      // debugger
      this.files=files
    },
    //移除回调
    onFileRemoved(file,files,fileList) {
      this.files=files
    },
    handleClose() {
      this.$refs.CommonUpload.clearFiles();
      this.$emit("close");
    },
    complete() {
      // this.dialogVisible = false
      // this.$refs.CommonUpload.clearFiles();
      if (this.files.length==0) {
        this.msgError('请上传文件')
        return
      }
              console.log(this.files)
      this.$emit("file-success",this.files);
      this.$emit("close");
    },

  },
    // mounted() {
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
  // },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  height: auto;
  overflow: auto;
}
/deep/ .el-dialog__footer {
  text-align: center;
}

</style>
