<template>
  <div>
    <el-dialog
      title="通知"
      width="800px"
      :visible.sync="dialogVisible"
      append-to-body
      @open="handleOpen"
      :before-close="handleClose"
    >
      <div>
        <el-form ref="form" :model="form" label-width="150px">
          <el-form-item label="预分组批次" prop="groupPiciNo">
            <el-input
              style="width: 250px"
              disabled
              v-model="groupPiciNo"
              placeholder="请输入预分组批次"
            />
          </el-form-item>
          <el-table :data="personInfoVoList">
            <el-table-column label="巡察组" align="center" prop="groupName">
            </el-table-column>
            <el-table-column
              label="被巡察单位"
              align="center"
              prop="company"
            >
            </el-table-column>
            <el-table-column
              label="处理人"
              align="center"
              prop="groupLeaderName"
            >
            </el-table-column>

            <el-table-column label="说明" align="center" prop="content">
              <template slot-scope="scope">
                <el-input v-model="scope.row.content" />
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm">发起审核</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getTaskGroupInfoList,
  startGroupToExamine,
} from "@/api/xuncha/transferManage/taskGroupInfo";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    // 弹出层标题
    title: {
      type: String,
      default: "发起审核",
    },
    groupPiciNo: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      form: {},
      members: [],
      personInfoVoList: [

      ],
    };
  },

  methods: {
    handleOpen() {
      let params = {
        groupPiciNo: this.groupPiciNo,
      };
      getTaskGroupInfoList(params).then((res) => {
        this.personInfoVoList = res.data;
      });
    },
    submitForm() {
      let params ={
        personInfoVoList:this.personInfoVoList
      }
      debugger
      const loading = this.openLoading();
      startGroupToExamine(params).then(res=>{
        this.msgSuccess(res.msg);
        loading.close()
        this.handleClose(200)
      })
    },
    handleClose(e) {
      this.$emit("close",e);
    },
  },
};
</script>

<style>
</style>
