<template>
  <div>
    <!-- 任务共享 -->
    <el-dialog
      title="选择共享"
      modal-append-to-body
      append-to-body
      :visible.sync="dialogVisible"
      width="900px"
      :before-close="handleClose"
    >
      <div class="dialog-context">
        <el-form label-width="100px">
          <!-- <el-form-item label="转办人:" :model="todoForm" :rules="tfRules" ref="tfRules"> -->
          <el-row :gutter="20">
            <el-col :span="20">
              <el-form-item label="姓名：" prop="xunchaLunciId">
                <el-select
                  style="width: 100%"
                  v-model="todoForm.nextUserId"
                  filterable
                  remote
                  value-key="userId"
                  reserve-keyword
                  placeholder="请输入"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  clearable
                  @change="handleSelect"
                >
                  <el-option
                    v-for="item in userList"
                    :key="item.userId"
                    :label="
                      item.username + '-' + item.userId + '-' +item.company+'/'+ item.department
                    "
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-button type="primary" size="small" @click="upload"
                >添加</el-button
              >
            </el-col>
          </el-row>

          <!-- </el-form-item> -->
        </el-form>
        <div class="module-context-row">
          <el-table
            :height="300"
            :max-height="600"
            :data="tableData"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="index" label="序号"></el-table-column>

            <el-table-column
              prop="userName"
              align="center"
              label="姓名"
            ></el-table-column>
            <el-table-column
              prop="companyName"
              align="center"
              label="所在单位"
            ></el-table-column>
            <el-table-column
              prop="deptName"
              align="center"
              label="部门"
            ></el-table-column>

            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="deleteData(scope)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button @click="handleSubmit" type="primary">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { addShare } from "@/api/xuncha/archives/xunchaReportApplyContent.js";
import { queryUumUserList } from "@/api/xuncha/common/base";

import { saveParticipants } from "@/api/xuncha/archives/xunchaProcessApply";
export default {
  components: {},
  props: {
    dialogVisible: {
      type: Boolean,
      default: true,
    },
    form: {
      type: Object,
    },
    applyId: {
      type: String,
      default: "",
    },
    taskId: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      todoShow: false,
      applyShow: false,
      todoInfoId: "",
      todoForm: {},
      applyForm: {
        nextUserObj: {},
        nextStepObj: {},
      },
      tableData: [],
      userList: [],
      selectedPerson: {},
      afRules: {
        nextUserObj: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "change",
          },
        ],
        nextStepObj: [
          {
            required: true,
            message: "请选择下一步处理节点",
            trigger: "change",
          },
        ],
      },
      tfRules: {
        nextUserId: [
          {
            required: true,
            message: "请选择转办人",
            trigger: "change",
          },
        ],
      },
    };
  },
  mounted() {},
  methods: {
    handleClose() {
      // this.dialogVisible=false;
      Object.assign(this.$data, this.$options.data());
      this.$emit("close");
    },

    upload() {
      if (!this.selectedPerson.userId) {
        this.msgError("请选择任务共享人");
        return;
      }
      let isSelected = this.tableData.find(
        (i) => i.userId == this.selectedPerson.userId
      );
      if (isSelected) {
        this.msgError("已在共享名单中");
      } else {
        let { userId, username, departmentId, department, company, companyId } =
          this.selectedPerson;

        this.tableData.push({
          applyId: this.applyId,
          userId,
          userAccount: userId,
          userName: username,
          deptName: department,
          deptId: departmentId,
          companyName: company,
          companyId,
          remark: "",
        });
      }
    },
    //搜索用户下拉框
    remoteMethod(query) {
  
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          let params = {
            username: query,
          };
          if (this.form.archiveOrgType == "A") {
            params.taskId = this.form.xunchaTaskId;
          } else if (this.form.archiveOrgType == "B") {
            params.companyId = this.form.orgId;
          }
          queryUumUserList(params).then((res) => {
            this.userList = res.data;
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    deleteData(scope) {
      this.tableData.splice(scope.$index, 1);
    },
    handleSubmit() {
      // this.$bus.$emit('addParticipant',this.tableData)
      if (this.tableData.length == 0) {
        this.msgError("请先添加共享成员");
        return;
      }
      let params = {
        xunchaProcessParticipantList: this.tableData,
        taskId: this.taskId,
      };
      var test = window.location.href;
      if (test.indexOf("archive_report_process") != -1) {
        addShare(params).then((res) => {
          if (res.code == 200) {
            this.msgSuccess(res.msg);
            this.$bus.$emit("addParticipant", this.tableData);
            this.handleClose();
          }
        });
      } else {
        saveParticipants(params).then((res) => {
          if (res.code == 200) {
            this.msgSuccess(res.msg);
            this.$bus.$emit("addParticipant", this.tableData);
            this.handleClose();
          }
        });
      }
    },

    handleSelect(value) {
      console.log(value);
      this.selectedPerson = value;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.roleId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      this.multipleSelection = val;
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