<template>
  <div>
    <el-dialog
      append-to-body
      :title="title"
      :visible.sync="dialogVisible"
      width="1000px"
      @open="open"
      :before-close="close"
    >
      <div class="dialog-context">
        <apply-form v-if="dialogVisible" :disabled="disabled" ref="refForm" :id="id" :type="type"></apply-form>
      </div>
      <span slot="footer" class="dialog-footer" v-if="!disabled">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="submit(5)">暂 存</el-button>
        <el-button type="primary" @click="submit(null)">提 交</el-button>
      </span>
    </el-dialog>

    <submit-process
    title="提交"
      processKey="leave_apply_process"
      stepName="党办分管领导审核"
      :dialogVisible="submitProcessShow"
      @complete="completeSubmit"
      @close="submitProcessShow = false"
    ></submit-process>
  </div>
</template>
<script>
import { addApply, temporarySaveApply } from "@/api/talent/apply";
import applyForm from "./applyForm.vue";
import submitProcess from "./submitProcess";
export default {
  components: { applyForm, submitProcess },
  props: {
    dialogVisible: {
      type: Boolean,
      default: true,
    },
    id: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "",
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      submitProcessShow: false,
      title:"新增"
    };
  },
  methods: {
    open() { 
      if (this.disabled) { 
        this.title = "查看";
      } else if (!this.disabled && this.id) {
        this.title = "修改";
       } else {
        this.title = "新增";
      }
    },
    completeSubmit(e) {
      const loading = this.openLoading();
      let result = this.submit(1);
      result.variables = e.variables;
      addApply(result).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("提交成功");
          loading.close();
          this.close(1);
        }
      });
    },
    submit(t) {
      let result = this.$refs.refForm.checkSubmit();
      if (!result) {
        return;
      }
      if (t == 1) {
        return result;
      } else if (t == 5) {
        //弹窗确认
        this.$confirm("确定暂存吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          temporarySaveApply(result).then((res) => {
            if (res.code == 200) {
              this.msgSuccess("暂存成功");
              this.close(1);
            }
          });
        });
      } else {
        this.submitProcessShow = true;
      }
    },
    close(e) {
      this.submitProcessShow = false;
      this.$emit("close",e);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
</style>
