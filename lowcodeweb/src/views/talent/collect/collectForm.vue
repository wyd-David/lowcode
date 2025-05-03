<template>
  <div>
    <el-dialog append-to-body @open="init" title="个人信息详情" :visible.sync="dialogVisible" width="70%" :close-on-click-modal="false"
      :before-close="close">
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>工作信息</span>
        </div>
        <div class="context-row">
          <el-form label-width="130px" disabled>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="姓名:" prop="name">
                  <el-input :value="form.userName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所在单位:" prop="name">
                  <el-input :value="form.companyName"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="部门:">
                  <el-input :value="form.departmentName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="部门备注:">
                  <el-input :value="form.deptRemark"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="职务:">
                  <el-input :value="form.postName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="职级:">
                  <el-input :value="(form.postLevel || 0)"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="是否有巡察经历:">
                  <el-radio-group :value="form.inspectionTimes">
                    <el-radio label="是">是</el-radio>
                    <el-radio label="否">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item v-if="form.inspectionTimes == '是'" label="巡察年份:">
                  <el-input :value="form.inspectionYear"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>工作经历</span>
        </div>
        <div class="context-row" style="padding-left: 20px">
          <el-form disabled label-width="150px">
            <el-row :gutter="24">
              <el-col :span="12" v-if="form.xunchaTalentDeptRecommend && form.xunchaTalentDeptRecommend.birthday">
                <el-form-item label="出生年月:">
                  <el-date-picker :value="form.xunchaTalentDeptRecommend.birthday" type="date" placeholder="请选择">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.xunchaTalentDeptRecommend && form.xunchaTalentDeptRecommend.joinInWorkDay">
                <el-form-item label="参加工作时间:">
                  <el-date-picker :value="form.xunchaTalentDeptRecommend.joinInWorkDay" type="date" placeholder="请选择">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12"  v-if="form.xunchaTalentDeptRecommend && form.xunchaTalentDeptRecommend.joinInPartDay">
                <el-form-item label="入党时间:">
                  <el-date-picker :value="form.xunchaTalentDeptRecommend.joinInPartDay" type="date" placeholder="请选择">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.xunchaTalentDeptRecommend && form.xunchaTalentDeptRecommend.frontlineWorkExperience">
                <el-form-item label="是否有一线工作经历:">
                  <el-radio-group :value="form.xunchaTalentDeptRecommend.frontlineWorkExperience">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="OA邮箱:">
                  <el-input :value="form.email"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="手机号码:" prop="phone">
                  <el-input :value="form.telephone"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>

          <el-form label-width="150px">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="个人简历:">
                  <el-button type="text" @click="downloadFile">
                    {{ fileName }}
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div v-if="more">
          <div class="dialog-title">
            <span class="line"></span><span>职级变更记录</span>
          </div>
          <div class="context-row">
            <el-table :data="form.xunchaUserPostVoList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="年份" prop="year"></el-table-column>
              <el-table-column label="所属单位" prop="companyName"></el-table-column>
              <el-table-column label="部门" prop="departmentName"></el-table-column>
              <el-table-column label="职级" prop="postLevel"></el-table-column>
            </el-table>
          </div>

          <div class="dialog-title">
            <span class="line"></span><span>出入库记录</span>
          </div>
          <div class="context-row">
            <el-table :data="form.personInfoHistoryList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="操作" prop="accessWay"></el-table-column>
              <el-table-column label="出/入库时间" prop="createdTime">
                <template slot-scope="scope">
                  {{
                      parseTime(scope.row.createdTime, "{y}-{m}-{d}")
                  }}
                </template>
              </el-table-column>
              <el-table-column label="方式" prop="accessType">
              </el-table-column>

              <!-- <el-table-column
                label="出库原因"
                prop="reason"
              ></el-table-column> -->
            </el-table>
          </div>

          <div class="dialog-title">
            <span class="line"></span><span>巡察经历</span>
          </div>
          <div class="context-row">
            <el-table :data="form.xunchaCheckedHistoryList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="巡察项目" prop="speTypeName"></el-table-column>
              <el-table-column label="组别名称" prop="groupName"></el-table-column>
              <el-table-column label="组内角色" prop="userTypeName"></el-table-column>
              <el-table-column label="巡察批次" prop="batchName"></el-table-column>
              <el-table-column label="巡察时间" show-overflow-tooltip prop="taskTime"></el-table-column>
              <el-table-column label="考核结果" prop="result"></el-table-column>
            </el-table>
          </div>

          <div class="dialog-title">
            <span class="line"></span><span>表扬表彰</span>
          </div>
          <div class="context-row">
            <el-table :data="form.xunchaCheckedFineVoList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="年份"></el-table-column>
              <el-table-column label="所在分组" prop="groupName"></el-table-column>
              <el-table-column label="组内角色"></el-table-column>
              <el-table-column label="巡察批次" prop="batchName"></el-table-column>
              <el-table-column label="表扬表彰记录" prop="fineTypeName"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </el-dialog>
    <!-- 调用具体实例，不用谢 -->
    <!-- <el-table-column label="组长" align="center" prop="groupLeaderName">
            <template slot-scope="scope">
                <span class="btmLine" @click="onUserDetail(scope.row)">{{scope.row.groupLeaderName}}</span>
            </template>
        </el-table-column>

        <collectForm ref="collectForm" :dialogVisible="isOpenUser" more @close="isOpenUser = false"></collectForm> 

        import collectForm from "@/views/talent/collect/collectForm.vue";
        data：{isOpenUser: false},
        //查看人员详情
        onUserDetail(row){
            this.$refs.collectForm.id = row.groupLeaderId
            this.isOpenUser = true
        },
        -->
  </div>
</template>
<script>
import { getUserInfo } from "@/api/talent/dept";
import { getUserDetail } from "@/api/xuncha/transferManage/transferPersonnelInfo";

import { downloadFile } from "@/utils/uploadOperate";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "",
    },
    storageType: {
      type: [String,Number],
      default: "",
    },
    more: {
      type: Boolean,
      default: false,
    },
  },
  data () {
    return {
      fileName: null,
      fileKey: null,
      form: {
        xunchaTalentDeptRecommend: { fileName: null },
      },
    };
  },
  methods: {
    downloadFile () {
      downloadFile(this.fileKey);
    },
    init () {
      this.form = {xunchaTalentDeptRecommend: { fileName: null }};
      this.getDetail();
    },
    getDetail () {
      getUserDetail({ userId: this.id,storageType:this.storageType }).then((res) => {
        this.form = res.data;
        this.form.xunchaTalentDeptRecommend = res.data.xunchaTalentDeptRecommend || {};
        this.fileName = this.form.xunchaTalentDeptRecommend.fileName;
        this.fileKey = this.form.xunchaTalentDeptRecommend.fileKey;
      });
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
