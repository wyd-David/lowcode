<template>
  <div>
    <el-dialog
      title="催办"
      width="600px"
      :visible.sync="dialogVisible"
      append-to-body
      :before-close="handleClose"
      @open="getNoticContentQuery"
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
              :disabled="true"
              style="width: 100%"
              placeholder="请输入内容"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="预计开始通知时间：" prop="noticTime">
            <el-date-picker
              style="width: 250px"
              v-model="form.noticTime"
              type="datetime"
              placeholder="选择日期时间"
              value-format="yyyy-mm-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {
getNoticContent,
sendTaskTransferInfoNotic
} from "@/api/xuncha/transferManage/taskTransferInfo";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    transferNo:{
      type:String,
      default:''
    }
  },
  data() {
    return {
      form: {
        type:"短信通知",
        content:null,
        noticTime:''
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
  mounted(){
    // this.getNoticContentQuery()
  },
  methods: {
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          let params = {
            noticTime:this.form.noticTime,
            transferNo:this.transferNo
          }
          console.log(this.form);
          debugger
          sendTaskTransferInfoNotic(params).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("通知成功");
              this.handleClose();
            }
          });
        }
      });
    },
    getNoticContentQuery(){
      getNoticContent(this.transferNo).then(res=>{
        this.form.content = res.msg
      })
    },
    handleClose() {
      this.$emit("close");
    },
  },
};
</script>

<style>
</style>