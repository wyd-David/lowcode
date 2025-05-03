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
            >
            </el-input>
          </el-form-item>
          <el-form-item label="预计开始通知时间：" prop="noticTime">
            <el-date-picker
              :picker-options="pickerOptions"
              style="width: 250px"
              v-model="form.noticTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
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
  },
  data() {
    return {
      pickerOptions: {
        //控制时间范围
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      form: {
        type: "短信通知",
        content: null,
        noticTime: "",
      },
      rules: {
        type: [
          { required: true, message: "通知形式不能为空", trigger: "blur" },
        ],
        noticTime: [
          {
            required: true,
            message: "预计开始通知时间不能为空",
            trigger: "blur",
          },
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
            noticTime: this.form.noticTime,
            transferNo: this.transferNo,
            noticContent: this.form.content,
          };
          if (resArray && resArray.length != 0) {
            // resArray.array.forEach(element => {
            //   element.
            // });
            this.$confirm(
              `通知内容中仍有日期，姓名
              未填写，是否确定通知？`,
              "提示",
              {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "info",
              }
            )
              .then(function () {
                return sendTaskTransferInfoNotic(params);
              })
              .then((response) => {
                if (response.code === 200) {
                  this.msgSuccess(response.msg);
                  this.handleClose();
                }
              })
              .catch(function () {});
          } else {
            this.$confirm("是否确定通知?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              sendTaskTransferInfoNotic(params).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess(response.msg);
                  this.handleClose();
                }
              });
            });
          }
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
      this.resetForm("form");
      this.$emit("close");
    },
  },
};
</script>

<style>
</style>