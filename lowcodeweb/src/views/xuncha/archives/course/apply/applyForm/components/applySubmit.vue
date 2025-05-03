<template>
  <div>
    <!-- 审核 -->
    <el-dialog
      title="提交"
      modal-append-to-body
      append-to-body
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="600px"
      @open="handleOpen"
    >
      <div class="dialog-context">
        <el-form
          label-width="140px"
          :model="subForm"
          :rules="afRules"
          ref="afRules"
        >
          <el-form-item :label="nextStepLabel" prop="nextStep">
            <!-- 巡察组 -->
            <group
             disabled
              v-if="nextStepLabel == '巡察组'"
              v-model="subForm.nextStep"
              :label.sync="subForm.groupName"
              :width="width"
              @change="handleGroupChange"
            >
            </group>
            <!-- 被巡察单位 -->
            <company
            disabled
              v-else-if="nextStepLabel == '被巡察单位'"
              v-model="subForm.nextStep"
              :label.sync="subForm.companyName"
              :width="width"
              ref="company"
              @change="handleChange"
            >
            </company>
            <!-- 其他单位 -->
            <el-select
              v-else
              style="width: 100%"
              v-model="subForm.nextStep"
              placeholder="请选择"
              @change="handleChange"
            >
              <el-option
                v-for="item in restUnitList"
                :key="item.companyId"
                :label="item.companyName"
                :value="item.companyId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="showUser" label="处理人:" prop="userId">
            <el-select
              style="width: 100%"
              v-model="subForm.userId"
              placeholder="请选择"
              @change="handleChangeUser"
            >
              <el-option
                v-for="item in nextUsers"
                :key="item.liaisonsAccount"
                :label="item.liaisonsName"
                :value="item.liaisonsAccount"
              ></el-option>
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
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="sub">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import Company from "@/components/XunCha/Company/index";
import Group from "@/components/XunCha/Group/index";

import {
  queryCompanyList,
  queryLiaisonsList,
  queryLiaisonsUserList,
} from "@/api/xuncha/common/base";
import { fininshFormTask } from "@/api/workflow";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    processKey: {
      type: String,
      default: "",
    },
    stepName: {
      type: String,
      default: "",
    },

    nextStepLabel: {
      type: String,
      default: "",
    },
    taskId: {
      type: String,
      default: "",
    },
  },
  components: { Company, Group },
  data() {
    return {
      subShow: false,
      showUser: true,
      subForm: {
        id: "",
        userId: "",
        nextStep: "",
      },
      url: "",
      width: "100%",
      restUnitList: [],
      nextUsers: [],
      afRules: {
        nextStep: [
          {
            required: true,
            message: "请选择",
            trigger: "change",
          },
        ],
        userId: [
          {
            required: true,
            message: "请选择处理人",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.restUnitQuery();
  },
  methods: {
    // init(process) {
    //   this.taskId = process.curtaskId;
    //   if (this.nextStep.indexOf("结束") > -1) {
    //     this.showUser = false;
    //   }
    // },
    handleOpen(){
      if(this.nextStepLabel=="被巡察单位"){
        let formData = this.$parent.formData;
        this.subForm.nextStep = formData.orgId
        this.handleChange(this.subForm.nextStep)
      }
      if(this.nextStepLabel=="巡察组"){
        let formData = this.$parent.formData;
        this.subForm.nextStep = formData.xunchaTaskId
        this.handleGroupChange(this.subForm.nextStep)
      }
    },
    // 巡察组
    handleGroupChange(taskId) {
      queryLiaisonsUserList({ taskId }).then((res) => {
        // this.nextUsers = res.data[0].liaisonManList.map((i) => {
        //   return {
        //     liaisonsName: i.username,
        //     liaisonsAccount: i.userId,
        //   };
        // });

        this.nextUsers = [{
          liaisonsName: res.data[0].groupLeader.username,
          liaisonsAccount: res.data[0].groupLeader.userId,
        }];
        this.subForm.userId = res.data[0].groupLeader.userId;
      });
    },
    handleChange(companyId) {
      queryLiaisonsList({ companyId }).then((res) => {
        this.nextUsers = res.data;
      });
    },
    handleChangeUser(v) {
      console.log(this.subForm.userId);
    },
    // 其他单位query
    restUnitQuery() {
      queryCompanyList().then((res) => {
        this.restUnitList = res.data;
      });
    },
    // 取消
    handleClose(code) {
      this.resetForm("afRules")
      this.$emit("close", code);
    },

    sub() {
      this.$refs["afRules"].validate((valid) => {
        if (valid) {
          this.$emit("complete", JSON.parse(JSON.stringify(this.subForm)));
          this.handleClose();
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
