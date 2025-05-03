<template>
  <div>
    <!-- <div slot="footer" class="dialog-footer">
    <slot></slot>
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="storageForm">暂 存</el-button>
      <el-button type="primary" @click="onsubmit">提 交</el-button>
    </div>
    <!-- 审核 -->
    <el-dialog
      title="审核"
      modal-append-to-body
      append-to-body
      :visible.sync="subShow"
      width="600px"
      @open="init"
    >
      <div class="dialog-context">
        <el-form
          v-loading="loading"
          label-width="140px"
          :model="subForm"
          :rules="afRules"
          ref="afRules"
        >
          <el-form-item label="下一环节:">
            <!-- <el-select
              value-key="outLineId"
              style="width: 100%"
              v-model="subForm.nextStepObj"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in nextUsers"
                :key="item.nextUserId + index"
                :label="item.nextStep"
                :value="item"
                >{{ item.nextStep }}</el-option
              >
            </el-select> -->
            <el-input disabled :value="nextStep"></el-input>
          </el-form-item>
          <el-form-item label="处理人:" prop="id">
            <el-select
              style="width: 100%"
              v-model="subForm.id"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in nextUsers"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                >{{ item.name }}</el-option
              >
            </el-select>
          </el-form-item>
          <el-form-item label="处理意见:">
            <el-input
              v-model="subForm.option"
              type="textarea"
              row="3"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="subShow = false">取 消</el-button>
        <el-button type="primary" @click="sub">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import request from "@/utils/request";
export default {
  props: {
    processKey: {
      type: String,
      default: "",
    },
    stepName: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      subShow: false,
      loading: false,
      subForm: {
        id: "",
      },
      nextStep:"",
      url: "",
      obj: {},
      nextUsers: [], //下一步处理人
      afRules: {
        id: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.init();
  },
  methods: {
    open(url, obj) {
      this.subShow = true;
      this.loading = false;
      this.url = url;
      this.obj = obj;
    },
    init() {
      this.subForm = {
        id: "",
      },
      request({
        url: "/activiti/getDealUserByProcessInstanceId",
        method: "get",
        params: {
          processKey: this.processKey,
          userId: this.$store.getters.name,
          stepName: this.stepName,
        },
      }).then((res) => {
        if (res.code == 200) {
          let tempArr = [];
          let ids = res.data.nextUserId.split(",");
          let names = res.data.nextUserName.split(",");
          for (let i = 0; i < ids.length; i++) {
            tempArr.push({
              id: ids[i],
              name: names[i],
            });
          }
          this.nextStep = res.data.nextStep;
          this.nextUsers = tempArr;
          this.subForm.id = tempArr[0]?tempArr[0].id:'';
          this.loading = false;
        }
        console.log(res);
      });
    },
    // 取消
    close(code) {
      this.subShow = false;
      this.$emit("close", code);
    },
    sub() {
      this.$refs["afRules"].validate((valid) => {
        if (valid) {
          let temp = this.obj;
          const loading = this.openLoading();
          temp.variables = JSON.stringify({
            btnMessage:"提交",
            nextDealUser: this.subForm.id,
            option: this.subForm.option,
          });
          request({
            url: this.url,
            method: "post",
            data: this.obj,
          }).then((res) => {
            if(res.code == 200){
              this.$message.success("提交成功");
              loading.close();
              this.close(res.code);
            }
          }).catch((err) => {
            loading.close();
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style lang="scss">
.dialog-context {
}
.dialog-title {
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

.context-row {
  padding: 20px 40px;
  margin-bottom: 15px;
  position: relative;
  .operation {
    position: absolute;
    right: 0;
    top: 10px;
    margin: 10px 0px;
    text-align: right;
    padding-right: 30px;
    z-index: 9;
  }
}

.dialog-footer {
  text-align: center;
}
</style>
