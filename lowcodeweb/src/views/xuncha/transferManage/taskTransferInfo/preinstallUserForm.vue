<template>
  <div>
    <el-dialog
      append-to-body
      @open="init"
      title="个人信息详情"
      :visible.sync="dialogVisible"
      width="70%"
      :close-on-click-modal="false"
      :before-close="close"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>工作信息</span>
        </div>
        <div class="context-row">
          <el-form
            :model="form"
            :rules="rules"
            ref="formRef"
            label-width="130px"
            @submit.native.prevent
          >
            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="搜索:">
                  <el-select
                    v-model="userId"
                    style="width: 100%"
                    filterable
                    remote
                    @change="selectChange"
                    reserve-keyword
                    placeholder="请输入姓名或账号进行搜索"
                    :remote-method="remoteMethod"
                    :loading="loading"
                  >
                    <el-option
                      v-for="(item, i) in userList"
                      :key="item.userId + i"
                      :label="
                        item.name + '-' + item.userId + '-' + item.companyName
                      "
                      :value="item.userId"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="专家领域">
                  <el-input
                    :value="form.fieldList.dictLabel"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="标签" prop="labelObj">
                  <el-select
                    clearable
                    v-model="form.labelObj"
                    value-key="dataCode"
                  >
                    <el-option
                      v-for="item in form.labelList"
                      :value="item"
                      :label="item.dataName"
                      :key="item.dataId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
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
                  <el-input :value="form.postLevel || 0"></el-input>
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
                <el-form-item
                  v-if="form.inspectionTimes == '是'"
                  label="巡察年份:"
                >
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
              <el-col
                :span="12"
                v-if="
                  form.xunchaTalentDeptRecommend &&
                  form.xunchaTalentDeptRecommend.birthday
                "
              >
                <el-form-item label="出生年月:">
                  <el-date-picker
                    :value="form.xunchaTalentDeptRecommend.birthday"
                    type="date"
                    placeholder="请选择"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col
                :span="12"
                v-if="
                  form.xunchaTalentDeptRecommend &&
                  form.xunchaTalentDeptRecommend.joinInWorkDay
                "
              >
                <el-form-item label="参加工作时间:">
                  <el-date-picker
                    :value="form.xunchaTalentDeptRecommend.joinInWorkDay"
                    type="date"
                    placeholder="请选择"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col
                :span="12"
                v-if="
                  form.xunchaTalentDeptRecommend &&
                  form.xunchaTalentDeptRecommend.joinInPartDay
                "
              >
                <el-form-item label="入党时间:">
                  <el-date-picker
                    :value="form.xunchaTalentDeptRecommend.joinInPartDay"
                    type="date"
                    placeholder="请选择"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col
                :span="12"
                v-if="
                  form.xunchaTalentDeptRecommend &&
                  form.xunchaTalentDeptRecommend.frontlineWorkExperience
                "
              >
                <el-form-item label="是否有一线工作经历:">
                  <el-radio-group
                    :value="
                      form.xunchaTalentDeptRecommend.frontlineWorkExperience
                    "
                  >
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
              <el-table-column
                label="所属单位"
                prop="companyName"
              ></el-table-column>
              <el-table-column
                label="部门"
                prop="departmentName"
              ></el-table-column>
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
                  {{ parseTime(scope.row.createdTime, "{y}-{m}-{d}") }}
                </template>
              </el-table-column>
              <el-table-column label="方式" prop="accessType">
              </el-table-column>
            </el-table>
          </div>

          <div class="dialog-title">
            <span class="line"></span><span>巡察经历</span>
          </div>
          <div class="context-row">
            <el-table>
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="巡察项目"></el-table-column>
              <el-table-column label="组别名称"></el-table-column>
              <el-table-column label="组内角色"></el-table-column>
              <el-table-column label="巡察批次"></el-table-column>
              <el-table-column label="巡察时间"></el-table-column>
              <el-table-column label="考核结果"></el-table-column>
            </el-table>
          </div>

          <div class="dialog-title">
            <span class="line"></span><span>表扬表彰</span>
          </div>
          <div class="context-row">
            <el-table :data="form.xunchaCheckedFineVoList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="年份"></el-table-column>
              <el-table-column
                label="所在分组"
                prop="groupName"
              ></el-table-column>
              <el-table-column label="组内角色"></el-table-column>
              <el-table-column
                label="巡察批次"
                prop="batchName"
              ></el-table-column>
              <el-table-column
                label="表扬表彰记录"
                prop="fineTypeName"
              ></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('close')">取 消</el-button>
        <el-button type="primary" @click="add">设 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  getPresetsInfolist,
  getUserDetail,
  addPresetsInfo,
  removePersonnel,
} from "@/api/xuncha/transferManage/transferPersonnelInfo";

import { downloadFile } from "@/utils/uploadOperate";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    piciId: [String, Number],
    storageType: {
      type: [String, Number],
      default: "",
    },
    transferId: [String, Number],
    more: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      fileName: null,
      fileKey: null,
      form: {
        xunchaTalentDeptRecommend: { fileName: null },
        fieldList: {},
        labelList: [],
        labelObj: {},
      },
      userList: [],
      loading: false,
      userId: "",
      rules: {
        labelObj: [
          { required: true, message: "请选择标签", trigger: "change" },
        ],
      },
    };
  },
  methods: {
    add() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          this.$confirm("确定提交吗?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then((e) => {
            addPresetsInfo({
              instanceId: this.piciId,
              transferId: this.transferId,
              personnelName: this.form.userName,
              labelName: this.form.labelObj.dataName,
              labelKey: this.form.labelObj.dataCode,
              fieldKey: this.form.fieldList.dictLabel,
              company: this.form.companyName,
              ouname: this.form.departmentName,
              personnelId: this.form.userId,
            }).then((res) => {
              if (res.code == 200) {
                this.msgSuccess("预设成功");
                this.$emit("success", res.msg);
              }
            });
          });
        }
      });
    },
    selectChange(e) {
      getUserDetail({ userId: e, storageType: 1 }).then((res) => {
        if (!res.data.fieldList) {
          res.data.fieldList = {};
        }
        this.form = res.data;
        this.form.xunchaTalentDeptRecommend =
          res.data.xunchaTalentDeptRecommend || {};
        this.fileName = this.form.xunchaTalentDeptRecommend.fileName;
        this.fileKey = this.form.xunchaTalentDeptRecommend.fileKey;
      });
    },
    remoteMethod(query) {
      if (query) {
        this.loading = true;
        getPresetsInfolist({
          userId: query,
          instanceId: this.piciId,
        }).then((res) => {
          this.userList = res.data;
          this.loading = false;
        });
      } else {
        this.userList = [];
      }
    },
    downloadFile() {
      downloadFile(this.fileKey);
    },
    init() {
        Object.assign(this.$data, this.$options.data())
    },
    close() {
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
