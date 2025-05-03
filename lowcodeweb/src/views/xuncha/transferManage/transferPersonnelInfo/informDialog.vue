<template>
  <div>
    <el-dialog
      title="通知"
      width="600px"
      :visible.sync="informOpen"
      append-to-body
      @open="getNoticContentQuery"
      :before-close="handleClose"
    >
      <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="150px">
          <el-form-item label="通知形式：" prop="type">
            <el-input
              :disabled="true"
              style="width: 250px"
              v-model="form.type"
              placeholder="请输入通知形式"
            />
          </el-form-item>
          <el-form-item label="通知内容：" align="center" prop="content">
            <el-input
              v-model="form.content"
              type="textarea"
              :rows="8"
              style="width: 100%"
              placeholder="请输入内容"
              maxlength="200"
              show-word-limit
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getNoticContent,
  sendTaskTransferInfoNotic,
} from "@/api/xuncha/transferManage/taskTransferInfo";
import { sendTransferList } from "@/api/xuncha/transferManage/transferPersonnelInfo";
export default {
  props: {
    informOpen: {
      type: Boolean,
      default: false,
    },
    transferNo: {
      type: String,
      default: "",
    },
    personnelInfoId: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      form: {
        type: "短信通知",
        content: null,
        noticTime: "",
      },
      rules: {
        type: [
          { required: true, message: "通知形式不能为空", trigger: "blur" },
        ],
        content: [{ required: true, message: "内容不能为空", trigger: "blur" }],
      },
    };
  },
  mounted() {
    // this.getNoticContentQuery()
  },
  methods: {
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const resArray = this.form.content.match(/\{.*?\}/g);
          let params = {
            personnelInfoId: this.personnelInfoId,
          };
          console.log(resArray);
          if (resArray && resArray.length != 0) {
            this.$confirm(
              `通知内容中仍有${resArray.join(
                "，"
              )}占位符未修改，是否确定通知？`,
              "警告",
              {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
              }
            )
              .then(function () {
                return sendTransferList(params);
              })
              .then((response) => {
                if (response.code === 200) {
                  this.msgSuccess(response.msg);
                  this.handleClose();
                }
              })
              .catch(function () {});
          } else {
            sendTransferList(params).then((response) => {
              if (response.code === 200) {
                this.msgSuccess(response.msg);
                this.handleClose();
              }
            });
          }
          // sendTaskTransferInfoNotic(params).then((response) => {
          //   if (response.code === 200) {
          //     this.msgSuccess("通知成功");
          //     this.handleClose();
          //   }
          // });
        }
      });
    },
    getNoticContentQuery() {
      let params = {
        transferNo: this.transferNo,
      };
      getNoticContent(params).then((res) => {
        this.form.content = res.msg;
      });
    },

    handleClose() {
      this.$emit("close");
    },
  },
};
</script>

<style>
</style>