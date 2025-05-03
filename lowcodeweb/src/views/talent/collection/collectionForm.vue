<template>
  <div>
    <el-dialog
      :fullscreen="isFullscreen"
      :visible.sync="dialogVisible"
      width="1200px"
      append-to-body
      :close-on-click-modal="false"
      @open="handleOpen"
      :before-close="handleClose"
      destroy-on-close
    >
      <div slot="title" style="position: relative">
        <span>{{ title }}</span>
        <el-button
          type="text"
          style="position: absolute; right: 40px"
          size="small"
          @click="isFullscreen = !isFullscreen"
          >{{ isFullscreen ? "关闭全屏" : "打开全屏" }}</el-button
        >
      </div>
      <div class="dialog-context">
        <apply-form
          :key="applyKey"
          :dialogVisible="dialogVisible"
          ref="refForm"
          :id="id"
          :type="type"
          :disabled="disabled"
        ></apply-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button v-if="!disabled" type="primary" @click="add(1)"
          >暂 存</el-button
        >
        <el-button v-if="!disabled" type="primary" @click="add(5)"
          >提 交</el-button
        >
      </span>
    </el-dialog>

    <SubmitIdx
      ref="siRef"
      processKey="talent_collection"
      stepName="巡察办室经理审核"
      @close="close"
    ></SubmitIdx>
  </div>
</template>
<script>
import { addCollection, issued, checkTitle } from "@/api/talent/collection";
import applyForm from "./applyForm";
import SubmitIdx from "@/components/SubmitIdx";
export default {
  components: { applyForm, SubmitIdx },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "0",
    },
    //1提交,2下发
    type: {
      type: String,
      default: "1",
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  watch: {
    dialogVisible(val) {
      this.applyKey = new Date().getTime();
    },
  },
  data() {
    return {
      isFullscreen: false,
      title: "发起人才收集",
      applyKey: new Date().getTime(),
    };
  },
  methods: {
    handleOpen() {
      if (!this.id) {
        this.title = "发起人才收集";
      } else if (this.id && !this.disabled) {
        this.title = "修改人才收集";
      } else {
        this.title = "查看";
      }
    },
    handleClose() {
      this.$emit("close");
    },
    async add(t) {
      let tempObj = await this.$refs.refForm.checkSubmit(t).then((res) => {
        return res;
      });
      if (!tempObj) {
        return;
      }
      if (t == 5) {
        this.$refs.refForm.$refs["formRef"].validate((valid) => {
          if (valid) {
            if (!tempObj.companyList.length && !tempObj.deptList.length) {
              this.$message.error("请至少添加一条名额收集信息");
              return;
            }
            this.$confirm("确定要提交吗?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              this.$refs.siRef.open("/talent/collection", tempObj);
            });
          } else {
            return false;
          }
        });
      } else if (t == 1) {
        this.$confirm("确定要暂存吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          const loading = this.openLoading();
          addCollection(tempObj)
            .then((res) => {
              loading.close();
              if (res.code == 200) {
                this.$message.success("暂存成功");
                this.$emit("close", 200);
              } else {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {
              loading.close();
            });
        });
      }
    },
    //下一步公用组件关闭回调
    close(e) {
      this.$emit("close", e);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
/deep/.is-fullscreen > .el-dialog__body {
  max-height: initial;
  padding-bottom: 40px;
}
/deep/.is-fullscreen > .el-dialog__footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 100;
}
</style>
