<template>
  <div>
    <el-dialog @open="init" title="巡察组信息详情" :visible.sync="dialogVisible" width="70%" :close-on-click-modal="false"
      :before-close="close">
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>基本信息</span>
        </div>
        <div class="context-row">
          <el-form label-width="130px" disabled>
            <el-row :gutter="20">
              <el-col :span="11">
                <el-form-item label="组名：" prop="groupName">
                  <el-input style="width: 100%" v-model="form.groupName" />
                </el-form-item>
              </el-col>
              <el-col :span="11" :offset="1">
                <el-form-item label="成员数：" prop="teamMembersCount">
                  <el-input
                    style="width: 100%"
                    v-model="form.teamMembersCount"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="11">
                <el-form-item label="巡察年份：" prop="xunchaYear">
                  <el-input style="width: 100%" v-model="form.xunchaYear" />
                </el-form-item>
              </el-col>
              <el-col :span="11" :offset="1">
                <el-form-item label="巡察批次：" prop="xunchaPici">
                  <el-input style="width: 100%" v-model="form.xunchaPici" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="11">
                <el-form-item label="关联巡察任务：" prop="taskName">
                  <el-input style="width: 100%" v-model="form.taskName" />
                </el-form-item>
              </el-col>
              <el-col :span="11" :offset="1">
                <el-form-item label="计划巡察时段：" prop="xunchaTimeSolt">
                  <el-input style="width: 100%" v-model="form.xunchaTimeSolt" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="23">
                <el-form-item label="被巡察单位：" prop="xunchaPici">
                  <el-input style="width: 100%" v-model="form.company" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>成员信息</span>
        </div>
        <div class="context-row" style="padding-left: 20px">
          <el-table v-loading="loading" :data="form.xunchaGroupUserVoList" border>
            <el-table-column label="序号" align="center" type="index" width="55" />
            <el-table-column label="姓名" align="center" prop="userName">
              <template slot-scope="scope">
                <span class="btmLine" @click="onUserInfo(scope.row)">{{ scope.row.userName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="标签" align="center" prop="labelValue" />
            <el-table-column label="所属公司" align="center" prop="companyName" />
            <el-table-column label="所属部门" align="center" prop="dpName" />
          </el-table>
        </div>
      </div>
    </el-dialog>
    <collectForm :dialogVisible="iFormShow.open" :id="iFormShow.id" more @close="handleCollectFormClose"></collectForm>
    <!-- 调用具体实例，不用谢 -->
    <!-- <el-table-column label="巡察组" align="center" prop="groupName">
              <template slot-scope="scope">
                    <span class="btmLine" @click="onGrounpDetail(scope.row)">{{ scope.row.groupName }}</span>
              </template>
          </el-table-column>
        <patrol-group ref="PatrolGroup" :groupObject="groupObject" :dialogVisible="isOpenGroup"
            @close="isOpenGroup = false"></patrol-group>

        import PatrolGroup from "@/components/PatrolGroup";
        data：{isOpenGroup: false},
        //查看组详情
        onGrounpDetail (row) {
            this.groupObject = {
                piciId: row.piciId ? row.piciId : '',
                taskId: row.taskId ? row.taskId : ''
            }
            this.isOpenGroup = true
        },
        -->
  </div>
</template>
<script>
import { getUserInfo } from "@/api/talent/dept";
import { getUserDetail } from "@/api/xuncha/transferManage/transferPersonnelInfo";

import { downloadFile } from "@/utils/uploadOperate";
import { getXunchaGroupInfoList } from "@/api/xuncha/transferManage/taskGroupInfo";
import collectForm from "@/views/talent/collect/collectForm.vue";
export default {

  components: { collectForm },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    groupObject: {
      type: Object,
      default: {}
    },
    more: {
      type: Boolean,
      default: false,
    },
  },
  data () {
    return {
      form: {},
      loading: false,

      iFormShow: { open: false, id: null },
    };
  },
  methods: {
    downloadFile () {
      downloadFile(this.fileKey);
    },
    init () {
      this.form = {};
      this.getDetail();
    },
    getDetail () {
      let obj = this.groupObject
      console.log(obj)
      getXunchaGroupInfoList(obj).then((res) => {
        this.form = res.data;
        // queryLiaisonsUserList().then(response=>{

        // })
      });
    },
    onUserInfo (row) {
      this.iFormShow.id = row.userId;
      this.iFormShow.open = true;
      console.log(this.iFormShow);
    },
    handleCollectFormClose () {
      this.iFormShow.open = false
    },
    close () {
      this.$emit("close");
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-input {
  width: 100%;
}

/deep/.el-radio-group {
  display: flex;
  height: 36px;
  align-items: center;
}

/deep/.el-dialog__body {
  max-height: 500px;
  overflow-y: auto;
}

.dialog-title .operation {
  padding-right: 40px;
}
</style>
