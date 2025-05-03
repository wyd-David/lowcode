<template>
  <div>
    <el-dialog
      title="调整成员"
      :visible.sync="dialogVisible"
      width="800px"
      @open="handleOpen"
      :close-on-click-modal="false"
      :before-close="handleClose"
      append-to-body="append-to-body"
    >
      <el-table :data="tableData">
        <el-table-column
          label="序号"
          align="center"
          type="index"
        ></el-table-column>
        <el-table-column
          label="标签"
          align="center"
          prop="labelValue"
        ></el-table-column>
        <el-table-column
          label="成员姓名"
          align="center"
          prop="userName"
        ></el-table-column>
        <el-table-column label="替换成员" align="center" prop="replaceId">
          <template slot-scope="scope">
            <el-select
              v-model="scope.row.replaceId"
              placeholder="请选择"
              clearable
              @change="hanc"
            >
              <el-option
                v-for="(item,i) in scope.row.replaceList"
                :key="i+item.personnelId"
                :label="item.personnelName + '(' + item.personnelId + ')'"
                :value="item.personnelId"
              />
            </el-select>
          </template>
        </el-table-column>

        <el-table-column label="专家类型" align="center" prop="fieldValue">
        </el-table-column>
        <el-table-column
          label="所在单位"
          align="center"
          prop="companyName"
        ></el-table-column>
        <el-table-column
          label="部门"
          align="center"
          prop="dpName"
        ></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button @click="submitForm" type="primary">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTransferPersonnelInfo } from "@/api/xuncha/transferManage/transferPersonnelInfo";
import {
  getGroupUserList,
  groupOnAgain,
} from "@/api/xuncha/transferManage/taskGroupPersonInfo";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    groupId: {
      type: String,
      default: " ",
    },
    instanceId: {
      type: String,
      default: " ",
    },
  },
  data() {
    return {
      tableData: [],
    };
  },
  methods: {
    hanc(val){
      console.log(val);
    },
    handleOpen() {
      getGroupUserList({ groupId: this.groupId }).then((res) => {
        this.tableData = res.data;
        this.tableData.forEach(async (item, index) => {
          let res = await this.replaceListQuery(
            item.labelValue,
            item.fieldValue
          );
          item.replaceId=""
          item.replaceList = res.rows;
          this.$set(this.tableData, index, JSON.parse(JSON.stringify(item)));
        });
      });
    },
    replaceListQuery(labelName, fieldKey) {
      let params = {
        instanceId:this.instanceId,
        feedbackStatus:1,
        isGroup:0,
        labelName,
        fieldKey,
      };
      return listTransferPersonnelInfo(params);
    },
    // 提交
    submitForm() {
      let userId = this.tableData
        .map((i) => {
          return i.userId;
        })
        .join(",");
      let newUserId = this.tableData
        .map((i) => {
          if (i.replaceId) {
            return i.replaceId;
          }else{
            return ' '
          }
        })
        .join(",");
      let params = {
        instanceId: this.instanceId,
        groupId:this.groupId,
        userId,
        newUserId
      };
      groupOnAgain(params).then((res) => {
        this.msgSuccess(res.msg)
        this.handleClose(111)
      });
    },
    handleClose(typeBoo) {
      console.log(typeBoo);
      this.$emit("close",typeBoo);
    },
  },
};
</script>

<style>
</style>