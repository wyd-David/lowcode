<template>
  <!-- 自定义确认弹窗样式 -->
  <el-dialog
    width="800px"
    :title="content.title"
    :visible.sync="content.show"
    v-if="isShow"
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
    <div slot="footer" class="dialog-footer">
      <el-button @click="close"> 取 消 </el-button>
      <el-button type="primary" @click="ok"> 确 定 </el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 弹窗内容
      isShow: true,
      content: {
        title: "",
        message: "",
        data: "",
        show: false,
      },
      downLoadForm: {
        type: "0",
        fileId: "",
        id: "",
      },
    };
  },
  methods: {
    close() {},
    ok() {
    },
  },
};
</script>

<style lang="scss" scoped>
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
</style>
