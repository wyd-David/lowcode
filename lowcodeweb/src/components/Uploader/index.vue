<template>
  <el-upload
    ref="upload"
    :class="!!fileList.length&&!(limit>fileList.length)?'upload-hide-trigger':''"
    name="file"
    :disabled="disabled"
    :file-list="fileList"
    :action="uploadUrl"
    :headers="headers"
    :limit="limit"
    :accept="accept"
    :show-file-list="showFileList"
    :data="params"
    :list-type="listType"
    :before-upload="handleBeforeUpload"
    :on-remove="handleRemove"
    :on-success="handleUploadSuccess"
    :on-error="handleUploadError"
  >
    <i class="el-icon-plus"></i>
    <div slot="tip" class="el-upload__tip">
      <slot name="tip"></slot>
    </div>
  </el-upload>
</template>

<script>
  import { getToken } from "@/utils/auth";
  export default {
    props: {
      value: {},
      pathType: {
        type: String,
        default: 'release'
      },
      listType: {
        type: String,
        default: 'picture-card'
      },
      limit: {
        type: Number,
        default: 5
      },
      showFileList: {
        type: Boolean,
        default: true
      },
      accept: {
        type: String
      },
      disabled: {
        type: Boolean,
        default: false
      },
      separator: {
        type: String,
        default: ','
      },
      // 单个文件最大限制20M
      fileSize: {
        type: Number,
        default: 20
      }
    },
    data() {
      return {
        uploadUrl: process.env.VUE_APP_BASE_API + '/system/file/uploadFile', // 上传的图片服务器地址
        headers: {
          'Admin-Token': 'Bearer ' + getToken(),
        },
        params: {
          pathType: this.pathType
        },
        fileList: []
      };
    },
    watch: {
      value: {
        handler(val) {
          this.ininFiles();
        },
        deep: true,
        immediate: true
      }
    },
    created() {
      this.ininFiles();
    },
    methods: {
      ininFiles() {
        let val = this.value;
        let temp = val ? val.split(this.separator) : [];
        this.fileList = temp.length ? temp.map(item => { return { url: item } }) : [];
      },
      // 删除图片
      handleRemove(file, fileList) {
        let temp = JSON.parse(JSON.stringify(this.fileList));
        let index = temp.map(item => item.url).indexOf(file.url);
        temp.splice(index, 1);
        this.$emit('input', temp.map(item => item.url).join(this.separator));
      },
      // 上传文件之前的钩子
      handleBeforeUpload(file) {
        let size = file.size / 1024 / 1024;
        let isPassLimit = size > this.fileSize;
        if(isPassLimit) {
          this.$message({
            type: "error",
            message: `上传文件不能超过${this.fileSize}M`,
          });
          return false;
        }
      },
      // 上传成功回调
      handleUploadSuccess(res) {
        if(res.code === 200) {
          let temp = JSON.parse(JSON.stringify(this.fileList));
          temp.push({ url: res.data.url });
          this.$emit('input', temp.map(item => item.url).join(this.separator));
        } else {
          this.fileList.pop();
          this.$message({
            type: "error",
            message: "上传失败",
          });
        }
      },
      // 上传失败
      handleUploadError() {
        this.$message({
          type: "error",
          message: "上传失败",
        });
      }
    }
  };
</script>
