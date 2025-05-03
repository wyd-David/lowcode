<template>
  <div>
    <el-dialog
    append-to-body
      :title="title"
      @open="handleOpen"
      :visible.sync="dialogVisible"
      width="1050px"
      :before-close="close"
    >
    <div class="dialog-context">
      <apply-form ref="refForm" v-if="dialogVisible" :id="id" :type="type"></apply-form>
    </div>
      <span slot="footer" class="dialog-footer" v-if="type != '3'">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="submit(1)">暂 存</el-button>
        <el-button type="primary" @click="submit">提 交</el-button>
      </span>
    </el-dialog>

    <submit-process title="提交" processKey="label_process" stepName="巡察办室经理审核" :dialogVisible="submitProcessShow" @complete="completeSubmit" @close="submitClose"></submit-process>
  </div>
</template>
<script>
import {
  savePersonLabel,
  savePersonLabelTemporary,
} from "@/api/talent/config";
import applyForm from "./applyForm.vue";
import submitProcess from "@/components/WorkFlow/submitProcess";
export default {
  components: {applyForm,submitProcess},
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "",
    },
    //弹窗状态(1,新增,2,修改,3,查看)
    type: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      submitProcessShow: false,
      title: "标签设置",
    };
  },
  methods: {
    handleOpen() { 
      if (this.type == 1) { 
        this.title = "标签设置";
      } else if (this.type == 2) {
        this.title = "修改";
      } else if (this.type == 3) {
        this.title = "查看";
      }
    },
    completeSubmit(e) {
      let obj = this.submit(2);
      let form = this.$refs.refForm.form;
      if (obj) {
        obj.variables = e.variables;
        obj.orderNumber = form.orderNumber;
        obj.createdBy = form.createdBy;
        const openLoading = this.openLoading();
        savePersonLabel(obj).then((res) => {
          openLoading.close();
          if (res.code == 200) {
            this.$message.success("提交成功");
            this.close(1);
          }
        });
      }
    },
    submit(t) {
      let tableData = this.$refs.refForm.tableData;
      if (tableData.length == 0) {
        this.$message.error("请选择巡察员");
        return null;
      }
      //判断是否有空标签的人员
      let labelFlag = false;
      //封装数据
      let tempList = [];
      tableData.forEach((res) => {
        if (!res.labels.length) { 
          labelFlag = true;
        }
        res.labels.forEach((item) => {
          tempList.push({
            ...res,
            ...item,
            labelId: item.dataCode,
          });
        });
      });
      if (labelFlag) { 
        this.msgError("请设置标签!");
        return;
      }
      let obj = {
        labelApplyRelList: tempList,
        id: this.id,
      };
      if (t == 1) {
        //弹窗确认
        this.$confirm("确定暂存吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          //全屏加载
          const openLoading = this.openLoading();
          savePersonLabelTemporary(obj).then((res) => {
            openLoading.close();
            if (res.code == 200) {
              this.$message.success("暂存成功");
              this.close(1);
            }
          });
        });
      } else if (t == 2) {
        return obj;
      } else {
        this.submitProcessShow = true;
      }
    },
    handleClose() {
      this.open = false;
      this.singleLabels = [];
    },
    close(e) {
      if (typeof (e) == 'number') { 
        this.submitProcessShow = false;
      }
      this.$emit("close", e);
    },
    submitClose() { 
      this.submitProcessShow = false;
    }
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
</style>
