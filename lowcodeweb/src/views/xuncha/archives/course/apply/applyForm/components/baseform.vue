<template>
  <div>
    <div class="form-module-title">
      <span class="line"></span><span>申请信息</span>
    </div>
    <div class="module-context-row">
      <el-form
        :model="formData"
        :rules="formRules"
        ref="formRef"
        label-width="100px"
        class="searchFormClass"
        :disabled="type == '3' ? true : false"
      >
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="巡察轮次" prop="xunchaLunciId" required>
              <lunci
                v-model="formData.xunchaLunciId"
                :label.sync="formData.xunchaLunciName"
                :width="width"
              >
              </lunci>
            </el-form-item>
          </el-col>

          <el-col :span="6" :offset="6">
            <el-form-item label="巡察年份:" prop="xunchaDate">
              <el-date-picker
                style="width: 320px"
                v-model="formData.xunchaDate"
                type="year"
                placeholder="选择年"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="巡察批次" prop="xunchaPiciId" required>
              <pici
                v-model="formData.xunchaPiciId"
                :label.sync="formData.xunchaPiciName"
                :width="width"
              >
              </pici>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="6">
            <el-form-item label="巡察任务" prop="xunchaTaskId" required>
              <task v-model="formData.xunchaTaskId" :width="width"> </task>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="巡察组" prop="xunchaGroup">
              <group
                v-model="formData.xunchaGroup"
                :label.sync="formData.groupName"
                :width="width"
              >
              </group>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="6">
            <el-form-item label="被巡察单位" prop="orgId" required>
              <company
                @change="companyChange"
                v-model="formData.orgId"
                :label.sync="formData.companyName"
                :width="width"
              >
              </company>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="入库资料类型" prop="archiveType" required>
              <dict
                @change.native="handleArchiveType"
                v-model="formData.archiveType"
                :label.sync="formData.archiveTypeName"
                :width="width"
                :dictType="$dicCode.processApplyArchive"
              ></dict>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="20">
            <el-form-item label="归档组织" prop="archiveOrgType" required>
              <el-radio-group
                @change="handleArchiveOrgType"
                v-model="formData.archiveOrgType"
              >
              <el-radio v-for="item in archiveOrgTypeList" :key="item.label" :label="item.label">{{item.name}}</el-radio>
                <!-- <el-radio label="1">巡察组</el-radio>
                <el-radio label="B">被巡察单位</el-radio>
                <el-radio label="C">其他单位</el-radio> -->
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="说明" prop="remark">
              <el-input
                v-model="formData.remark"
                type="textarea"
                maxlength="200"
                show-word-limit
                placeholder="请输入处理人账号,多个用逗号隔开"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="sumitData">确 定</el-button>
      </span> -->
  </div>
</template>
<script>
import Lunci from "@/components/XunCha/LunCi/index";
import Pici from "@/components/XunCha/Pici/index";
import Task from "@/components/XunCha/Task/index";
import Company from "@/components/XunCha/Company/index";
import Dict from "@/components/XunCha/Dict/index";
import Group from "@/components/XunCha/Group/index";

export default {
  components: { Lunci, Pici, Task, Company, Dict, Group },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    //1->新建 2->编辑 3->查看
    type: {
      type: String,
      default: "1",
    },
  },
  name: "baseForm",
  data() {
    let validateYear = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请选择年份"));
      } else {
        callback();
      }
    };
    return {
      width: "320px",
      orderShow: true,
      multipleSelection: [], //多选
      nextStepLabel:'巡察组',
      archiveOrgTypeList:[
        {
          label:"1",
          name:"巡察组"
        },
        {
          label:"B",
          name:"被巡察单位"
        },
        {
          label:"C",
          name:"其他单位"
        },
      ],
      // 表单数据
      formData: {
        applyId: "",
        serialNo: "",
        title: "",
        archiveType: "A",
        archiveTypeName: "",
        orgId: "",
        orgName: "",
        archiveOrgType: "1",
        archiveObjectId: "002",
        archiveObjectName: "归档对象",
        archiveUserId: "",
        archiveUserName: "",
        xunchaTaskId: "",
        xunchaTaskName: "",
        xunchaGroup: "",
        xunchaPiciId: "",
        xunchaPiciName: "",
        xunchaDate: "",
        xunchaLunciId: "",
        xunchaLunciName: "",
        status: "checking",
        remark: "",
      },
      formRules: {
        xunchaLunciId: [
          { required: true, message: "请选择轮次", trigger: "change" },
        ],
        xunchaDate: [
          {
            required: true,
            validator: validateYear,
            trigger: "blur",
          },
        ],
        xunchaPiciId: [
          { required: true, message: "请选择批次", trigger: "change" },
        ],
        xunchaTaskId: [
          { required: true, message: "请选择任务", trigger: "change" },
        ],
        xunchaGroup: [
          { required: true, message: "请选择巡察组", trigger: "change" },
        ],
        companyId: [
          { required: true, message: "请选择被巡察单位", trigger: "change" },
        ],
        archiveType: [
          { required: true, message: "请选择入库类型", trigger: "change" },
        ],
        archiveOrgType: [
          { required: true, message: "请选择归档组织", trigger: "change" },
        ],
      },
      batchs: [],
      tableData: [{}], //入库资料信息
      taskInfo: [{}], //流程信息
      nextObj: {
        //下一步信息
        remark: "",
      },
    };
  },
  mounted() {
    
  },
  methods: {
    sumitData() {
        this.$refs.formRef.validate((valid) => {
          if (valid) {
            this.$emit("sumitData", this.formData);
            return true
          } else {
            this.msgError("请完善表单信息!");
            return false
          }
        });
    },
    handleArchiveOrgType(val) {
      this.nextStepLabel = this.archiveOrgTypeList.find(i=>i.label==val).name
      console.log(this.nextStepLabel);
    },
    companyChange(val) {},
    handleArchiveType(val) {
      console.log(val);
    },
    init() {
      this.$refs.formRef.resetFields();
    },
  },
  watch: {
    formData: {
      handler(value) {
        this.handleArchiveOrgType(value.archiveOrgType)
        this.$emit("sumitData", value,this.nextStepLabel);
      },
      deep: true,
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  height: 500px;
  overflow: auto;
}

.dialog-context {
  .form-module-title {
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

  .module-context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;
    .operation {
      position: absolute;
      right: 0;
      left: 0;
      top: -30px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 40px;
    }
  }
}
</style>
