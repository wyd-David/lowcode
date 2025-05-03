<template>
  <div>
    <!-- 查看人员抽调_巡察任务抽调信息 对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="900px"
      :close-on-click-modal="false"
      :before-close="handleClose"
      @open="handleOpen"
      append-to-body="append-to-body"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>巡察信息</span>
        </div>
        <el-form
          :disabled="disabled"
          ref="queryform"
          :model="formQuery"
          label-width="130px"
        >
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="组名：" prop="groupName">
                <el-input style="width: 100%" v-model="formQuery.groupName" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="成员数：" prop="teamMembersCount">
                <el-input
                  style="width: 100%"
                  v-model="formQuery.teamMembersCount"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-input style="width: 100%" v-model="formQuery.xunchaYear" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="巡察批次：" prop="xunchaPici">
                <el-input style="width: 100%" v-model="formQuery.xunchaPici" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="关联巡察任务：" prop="taskName">
                <el-input style="width: 100%" v-model="formQuery.taskName" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="计划巡察时段：" prop="xunchaTimeSolt">
                <el-input style="width: 100%" v-model="formQuery.xunchaTimeSolt" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="23">
              <el-form-item label="被巡察单位：" prop="xunchaPici">
                <el-input style="width: 100%" v-model="formQuery.company" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <div class="dialog-title" style="justify-content: space-between">
          <div><span class="line"></span><span>组成员信息</span></div>
        </div>

        <div class="context-row">
          <el-table :data="formQuery.xunchaGroupUserVoList">
            <el-table-column label="序号" type="index"> </el-table-column>
            <el-table-column
              label="组内标签/职责"
              prop="labelValue"
            ></el-table-column>
            <el-table-column label="姓名" prop="userName">
              <template slot-scope="scope">
                <el-button style="font-size:14px" size="mini" @click="onUserInfo(scope.row)" type="text">{{
                  scope.row.userName
                }}</el-button>
              </template>
            </el-table-column>

            <el-table-column label="所在单位" prop="companyName"></el-table-column>
            <el-table-column label="部门" prop="dpName"></el-table-column>
            <el-table-column label="领域" prop="fieldValue"></el-table-column>
          </el-table>
        </div>
      </div>
      <!-- <div slot="footer" v-if="!disabled" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button @click="submitForm" type="primary">确 定</el-button>
      </div> -->
    </el-dialog>

    <collectForm
        :dialogVisible="iFormShow.open"
        :id="iFormShow.id"
        more
        @close="handleCollectFormClose"
      ></collectForm>
  </div>
</template>

<script>
import { getFitnessWhiteViewEmpInfo } from "@/api/system/roleControlManager.js";
import {
  getXunchaTaskGroupInfoData,
  getXunchaGroupInfoList,
} from "@/api/xuncha/transferManage/taskGroupInfo.js";

import collectForm from "@/views/talent/collect/collectForm.vue";

export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: true,
    },
    // 弹出层标题
    title: {
      type: String,
      default: "查看分组",
    },
    groupId: {
      type: String,
    },
    disabled: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      // 表单参数

      formQuery: {
        groupName: null,
        teamMembersCount: null,
        xunchaPici: null,
      },
      groupRules: [],
      membersList: [],
      iFormShow:{open:false,id:null},
      resultTable: [
        {
          tab: "123",
          profession: "123",
          name: "123",
          unit: "123",
          department: "党廉办",
        },
      ],
      tabList: [
        {
          id: 1,
          name: "主笔人",
        },
      ],
      informTable: [],
      professionList: [],
      // 表单校验
      CompanyAll: [],
    };
  },
  components: {collectForm},
  created() {
    this.getFitnessWhiteViewEmpInfoQuery();
  },

  methods: {
    handleOpen() {
      const params = {
        groupId: this.groupId,
      };
      getXunchaGroupInfoList(params).then((res) => {
        // let formQuery = this.formQuery;
        // let data = res.data;
        // for (const key in formQuery) {
        //   formQuery[key] = data[key];
        // }
        this.formQuery = res.data;
      });
    },
    onUserInfo(row){
      this.iFormShow.id = row.userId ;
      this.iFormShow.open = true;
      console.log(this.iFormShow);

    },
    handleCollectFormClose(){
      this.iFormShow.open=false
    },
    handleMemberChange(event, row) {
      console.log(event, row);
      this.membersList.forEach((i) => {
        if (i.username == event) {
          row.position = i.position;
          row.companyDpname = i.companyDpname;
          row.fieldValue = i.fieldValue;
        }
      });
    },
    submitForm() {
      this.$refs["queryform"].validate((valid) => {
        this.handleClose();
      });
    },
    // 员工信息
    getFitnessWhiteViewEmpInfoQuery() {
      getFitnessWhiteViewEmpInfo().then((res) => {
        this.membersList = res.rows;
      });
    },
    handleClose(e) {
      this.$emit("close", e);
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog-context {
  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;

    .btn {
      float: right;
    }

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }
  .result_box {
    margin-left: 28px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .btn_box {
    display: flex;
    justify-content: center;
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
