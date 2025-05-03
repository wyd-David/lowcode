<template>
  <el-dialog
    title="文件下载"
    :visible="fileTypeShow"
    width="800px"
    :before-close="() => (fileTypeShow = false)"
  >
    <div class="form-module-title">
      <span class="line"></span><span>文件下载</span>
    </div>
    <div class="context-row" style="margin-bottom: 30px">
      <el-form :model="downLoadForm" ref="formRef" label-width="165px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="请选择文件下载格式：" prop="type" required>
              <el-radio-group style="width: 550px" v-model="downLoadForm.type">
                <el-radio :label="'0'">按照原文件格式下载</el-radio>
                <el-radio :label="'1'">转换为PDF</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="tips">
          <span style="color: #ec832f">
            温馨提示：Excel格式的文件转换为PDF时，可能会影响文件的美观和可读性，请谨慎转化
          </span>
        </div>
      </el-form>
    </div>
    <div slot="footer">
      <el-button @click="fileTypeShow = false">取消</el-button>
      <el-button type="primary" @click="confirmDownload">下载</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      fileTypeShow: false,
      downLoadForm: {
        type: "0",
        fileId: "",
        id: "",
      },
    };
  },
};
</script>

<style>
</style>