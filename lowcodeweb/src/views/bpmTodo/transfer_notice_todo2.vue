<template>
  <div class="app-main">
    <div class="dialog-title">
      <span class="line"></span><span>待办信息</span>
    </div>
    <div class="context-row">
      <el-form :disabled="!authorizeFlag" ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="业务单号：" prop="type">
              <el-input
                disabled
                style="width: 350px"
                v-model="form.transferNo"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="通知内容：" prop="content">
          <el-input
            disabled
            v-model="form.content"
            type="textarea"
            :rows="4"
            style="width: 100%"
            placeholder="请输入内容"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="是否参加：" prop="feedbackStatus">
          <el-radio-group style="width: 150px" v-model="form.feedbackStatus">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-row v-if="form.feedbackStatus==0" :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注：" prop="remark">
              <el-input
                v-model="form.remarks"
                type="textarea"
                :rows="4"
                maxlength="200"
                show-word-limit
                style="width: 100%"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div v-if="authorizeFlag" class="deep-btn-box">
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getSendTransferInfo,
  personnelInfoConfirm,
} from "@/api/xuncha/transferManage/transferPersonnelInfo";
export default {
  props: {
    informOpen: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      personnelInfoId: null,
      todoId: null,
      infoId: null,
      authorizeFlag :true,
      form: {
        transferNo: "",
        content: "",
        feedbackStatus: 1,
        remarks:''
      },
      rules: {
        feedbackStatus: [
          { required: true, message: "请选择是否参加", trigger: "change" },
        ],
      },
    };
  },
  mounted() {
    this.personnelInfoId = this.$route.query.id;
    this.todoId = this.$route.query.todoId;
    this.infoId = this.$route.query.id;
    this.transferNo = this.$route.query.transferNo;
    getSendTransferInfo({ personnelInfoId: this.personnelInfoId }).then(
      (res) => {
        const { data } = res;
        this.form.content = data.content;
        this.form.transferNo = data.transferNo;
        this.form.remarks = data.remarks;
        // feedbackStatus有值为已处理,不可编辑
        if (data.feedbackStatus) {
          this.form.feedbackStatus = parseInt(data.feedbackStatus)
          this.authorizeFlag=false
        }
      }
    );
  },
  methods: {
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          let { feedbackStatus,remarks } = this.form;

          let params = {
            feedbackStatus,
            remarks,
            todoId: this.todoId,
            transferNo: this.transferNo,
            infoId:this.infoId,
            personnelInfoId: this.personnelInfoId,
          };
          const loading = this.openLoading();
          personnelInfoConfirm(params).then((res) => {
            if (res.code === 200) {
              loading.close();
              this.msgSuccess(res.msg);
              setTimeout(() => {
                location.reload();
              }, 700);
            }
          });
        }
      });
    },
    handleClose() {},
  },
};
</script>

<style lang="scss" scoped>
.app-main {
  padding: 15px;
}
</style>