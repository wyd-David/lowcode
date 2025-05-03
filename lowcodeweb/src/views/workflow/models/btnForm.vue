<template>
  <div>
    <el-dialog
      title="节点按钮信息"
      modal-append-to-body
      append-to-body
      :visible.sync="dialogVisible"
      width="1000px"
      :before-close="handleClose"
    >
      <div>
        <div class="operation">
          <el-button type="primary" size="small" @click="addData"
            >添加按钮</el-button
          >
          <el-button type="primary" size="small" @click="addDefaultData"
            >默认按钮</el-button
          >

          <el-button type="primary" size="small" @click="saveBnts"
            >保存</el-button
          >
          <el-button type="primary" size="small" @click="handleClose"
            >关闭</el-button
          >
        </div>
        <el-table v-loading="loading" :data="tableData">
          <el-table-column type="selection" width="55" align="center" />
          <!--<el-table-column label="序列号" align="center" prop="rev" />-->
          <el-table-column
            prop="btnCode"
            align="center"
            label="编码(唯一编码，用于前端控制)"
          >
            <template slot-scope="scope">
              <el-input v-model="scope.row.btnCode"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="btnName" align="center" label="按钮显示名称">
            <template slot-scope="scope">
              <el-input v-model="scope.row.btnName"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="remark" align="center" label="描述">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="100px"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="deleteData(scope)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getProcessBnts, saveProcessBnts } from "@/api/workflow/step";

export default {
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
  data() {
    return {
      defaultBtns: [
        {
          btnCode: "saveDraft",
          btnName: "暂存",
          remark: "暂存",
          procdefId: "32142",
          stepCode: "32142",
        },
        {
          btnCode: "complete",
          btnName: "审批",
          remark: "审批流程",
          procdefId: "32142",
          stepCode: "32142",
        },
        {
          btnCode: "turnback",
          btnName: "退回",
          remark: "退回流程",
          procdefId: "32142",
          stepCode: "32142",
        },
        {
          btnCode: "transfer",
          btnName: "转办",
          remark: "转办流程",
          procdefId: "32142",
          stepCode: "32142",
        },
      ],
      tableData: [],
      procdefId: "",
      stepCode: "",
      stepName: "",
      // 是否显示表单
      btnFormShow: false,
      // 遮罩层
      loading: false,
      modelsList: [{}],
      activeName: "1",
      // 流程处理弹窗
      applyShow: false,
      turnbackProcessShow: false,
      transferProcessShow: false,
      //业务模块
      participantSelectShow: false,
      auditProcessShow: false,
      participantSelectShow: false,
    };
  },
  created() {
    // this.getList()
  },
  methods: {
    getList(processDefId, e) {
      this.stepCode = e.stepCode;
      this.stepName = e.stepName;
      this.procdefId = processDefId;
      let data = {
        procdefId: this.procdefId,
        stepCode: this.stepCode,
      };
      let that = this;
      getProcessBnts(null, data).then((e) => {
        that.tableData = e.data;
      });
    },
    saveBnts() {
      let that = this;
      if (!this.tableData) {
        this.tableData = [];
      }
      that.tableData.forEach((item) => {
        if (item) {
          item["procdefId"] = that.procdefId;
          item["stepCode"] = that.stepCode;
          item["stepName"] = that.stepName;
        }
      });
      let tempArr = [
        {
          procdefId: that.procdefId,
          stepCode: that.stepCode,
        },
      ];
      saveProcessBnts(
        null,
        that.tableData.length ? that.tableData : tempArr
      ).then((e) => {
        that.tableData = e.data;
        this.$message.success("提交成功");
        this.$emit("close");
      });
    },
    handleClose() {
      this.$emit("close");
    },
    initData() {
      this.tableData = JSON.parse(JSON.stringify(this.defaultBtns));
    },
    deleteData(scope) {
      if (!this.tableData) {
        this.tableData = [];
      }
      this.tableData.splice(scope.$index, 1);
    },
    addDefaultData() {
      this.tableData = JSON.parse(JSON.stringify(this.defaultBtns));
    },
    addData() {
      if (!this.tableData) {
        this.tableData = [];
      }
      this.tableData.push({
        code: "",
        name: "暂存",
        remark: "",
      });
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  height: 500px;
  overflow: auto;
}

/deep/ .form-module-footer {
  text-align: center;
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

  .context-row {
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
