<template>
  <el-dialog
    title="离库"
    :visible.sync="dialogVisible"
    width="700px"
    :close-on-click-modal="false"
    :before-close="handleClose"
    append-to-body="append-to-body"
  >
    <div class="dialog-context">
      <el-form ref="queryform" :model="form" label-width="100px">
        <el-form-item label="出库原因：" align="center" prop="reason">
          <el-input
            v-model="form.reason"
            type="textarea"
            :rows="6"
            style="width: 100%"
            placeholder="请输入"
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
</template>

<script>
import { removeLeader } from "@/api/xuncha/groupLeader/groupLeader";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    row:{
        type:Object,
    }
  },
  data() {
    return {
      loading: false,
      form: {
        reason:null
      },
    };
  },
  methods: {
    handleClose(e) {
      this.form.reason=""
      this.$emit("close",e);
    },
    submitForm() {
        let{id,userId} = this.row
        let params = {
            reason:this.form.reason,
            id,
            userId
        }
        this.$confirm("是否确定离库?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            const loading =  this.openLoading()
            removeLeader(params).then(res=>{
               if (res.code === 200) {
                    loading.close()
                    this.handleClose(200)
                    this.msgSuccess(res.msg)
                }
            })
          })
    },
  },
};
</script>

<style>
</style>