<template>
  <div>
    <div class="dialog-context">
      <div class="dialog-title">
        <span class="line"></span><span>人员基础信息</span>
      </div>
      <div class="context-row">
        <el-form
          :disabled="roleType == 0"
          ref="queryform"
          :rules="rules"
          :model="form"
          label-width="155px"
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名：" v-if="roleType == 0" prop="userName">
                <el-input v-model="form.userName" style="width: 100%" />
              </el-form-item>
              <el-form-item label="姓名：" v-else prop="userName">
                <el-select
                  style="width: 100%"
                  v-model="form.userId"
                  filterable
                  remote
                  value-key="loginid"
                  reserve-keyword
                  clearable
                  placeholder="请输入姓名"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  @change="handleSelect"
                >
                  <el-option
                    v-for="item in userList"
                    :key="item.loginid"
                    :label="
                      item.username + '-' + item.loginid + '-' + item.dpname
                    "
                    :value="item.loginid"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所在单位：" prop="companyName">
                <el-input
                  v-model="form.companyName"
                  disabled
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="部门：" prop="department">
                <el-input v-model="form.orgName" disabled style="width: 100%" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="部门备注：">
                <el-input
                  disabled
                  v-model="form.orgRemarks"
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="职务：" prop="postName">
                <el-input
                  disabled
                  v-model="form.postName"
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="职级：">
                <el-input
                  disabled
                  v-model="form.rankLevel"
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="OA邮箱：" prop="mailbox">
                <el-input
                  v-model="form.mailbox"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号码：" prop="phone">
                <el-input
                  v-model="form.phone"
                  disabled
                  style="width: 100%"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <div
            class="dialog-title"
            style="justify-content: space-between; margin-left: -40px"
          >
            <div><span class="line"></span><span>工作经历</span></div>
          </div>

          <div>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="出生年月：" prop="birthday">
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.birthday"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择日期"
                    :picker-options="pickerOptionsDate"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="专家领域：" prop="majorType">
                  <el-select
                    style="width: 100%"
                    v-model="form.majorType"
                    placeholder="请选择"
                    @change="handleMajor"
                  >
                    <el-option
                    v-for="dict in fieldList"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="参加工作时间：" prop="workingHours">
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.workingHours"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择日期"
                    :picker-options="pickerOptionsDate"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="入党时间：" prop="joiningPartyDay">
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.joiningPartyDay"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择日期"
                    :picker-options="pickerOptionsDate"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="是否有巡察经历：" prop="isXunucha">
                  <el-radio-group v-model="form.isXunucha">
                    <el-radio label="是"></el-radio>
                    <el-radio label="否"></el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col v-show="form.isXunucha != '否'" :span="12" :offset="4">
                <el-form-item
                  label="巡察年份："
                  :prop="form.isXunucha != '否' ? 'xunchaYear' : ''"
                >
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.xunchaYear"
                    type="year"
                    placeholder="请选择巡察年份"
                    :picker-options="pickerOptions"
                    value-format="yyyy"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="是否有一线工作经历:" prop="isFrontline">
                  <el-radio-group v-model="form.isFrontline">
                    <el-radio label="是"></el-radio>
                    <el-radio label="否"></el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="个人简历：" prop="resume">
                  <el-button
                    v-if="!form.resumeAttName"
                    @click="uploadFormShow = true"
                    size="small"
                    type="primary"
                    >上传简历</el-button
                  >
                  <div v-else>
                    <el-button
                      type="text"
                      title="点击下载"
                      :disabled="false"
                      @click="downloadFile"
                      >{{ form.resumeAttName }}</el-button
                    >
                    <el-button
                      type="primary"
                      size="small"
                      @click="uploadFormShow = true"
                      >重新上传</el-button
                    >
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>
    </div>
    <!-- 文件上传 -->
    <el-dialog
      modal-append-to-body
      append-to-body
      title="文件上传"
      :visible.sync="uploadFormShow"
      width="50%"
      @open="initUpload"
      :before-close="handleClose"
    >
      <div>
        <CommonUpload
          v-if="uploadFormShow"
          ref="CommonUpload"
          :single-file="true"
          @file-success="onFileSuccess"
          @file-removed="onFileRemoved"
        ></CommonUpload>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="uploadFormShow = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 申请页面的提交页面 -->
    <submit-process
      processKey="sporadic_ruku_process"
      stepName="地市公司党委书记"
      :dialogVisible="submitProcessShow"
      @close="submitProcessShow = false"
      @complete="(val) => saveFromData(val, 1)"
    >
    </submit-process>
    <deep-btn
      ref="deepBtnRef"
      v-bind="$attrs"
      
      :businessKey="getBusinessKey()"
      @initProcess="initProcess"
      @handleData="onFininshGroupToExamine"
      @close="handleClose"
      isBusiness
    >
      <!-- 发起默认按钮，由 businessKey 控制，为空的话显示下面的默认按钮 -->
      <el-button type="primary" @click="saveBtn">暂存</el-button>
      <el-button type="primary" @click="submitBtn">提交</el-button>
    </deep-btn>
  </div>
</template>

<script>
import {
  getSporadicTalentUserInfo,
  addSporadicTalentUserInfo,
  fininshSporadicToExamine,
  selectViewInfoList,
} from "@/api/xuncha/replaceManage/sporadicTalentUserInfo";
import { getUserInfo } from "@/api/talent/dept";
import deepBtn from "@/components/WorkFlow/deepBtn";
import { listData,  } from "@/api/system/dict/data";
import { queryDictList } from "@/api/xuncha/common/base";
import SubmitProcess from "@/components/WorkFlow/submitProcess";
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
const examineStatusArray = [
  "",
  { spet: "党办主任申请", outLine: "" },
  { spet: "巡察办主管", outLine: "退回地市党委书记" },
  { spet: "结束", outLine: "" },
  { spet: "地市公司党委书记", outLine: "退回党办主任" },
  { spet: "巡察办室经理", outLine: "退回巡察办主管" },
];
export default {
  props: {
    id: {
      type: String,
      default: "",
    },
    isEditAuthority: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    deepBtn,
    CommonUpload,
    SubmitProcess,
  },
  data() {
    let validatePhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error("手机号不能为空！"));
      }
      //使用正则表达式进行验证手机号码
      if (!/^1[3456789]\d{9}$/.test(value)) {
        callback(new Error("手机号不正确！"));
      } else {
        callback();
      }
    };

    return {
      pickerOptionsDate: {
        //控制时间范围
        disabledDate(time) {
          return time.getTime() > Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      // 表单参数
      form: {
        userId: null,
        userName: null,
        companyIds: null,
        orgRemarks: null,
        workingHours: null,
        joiningPartyDay: null,
        xunchaYear: null,
        majorType:null,
        majorName:null,
        phone: "",
        isXunucha: "否",
        isFrontline: "否",
      },
      file: {},
      submitProcessShow: false,
      uploadFormShow: false,
      loading: false,
      // 角色type
      roleType: 0,
      groupRules: [],
      fieldList: [],

      informTable: [],
      professionList: [],
      userList: [],
      // 表单校验
      rules: {
        companyIds: [
          { required: true, message: "被巡察单位不能为空", trigger: "blur" },
        ],
        replaceName: [
          { required: true, message: "替换人员不能为空", trigger: "change" },
        ],
        birthday: [
          { required: true, message: "出生日期不能为空", trigger: "change" },
        ],
        majorType: [
          {
            required: true,
            message: "专家领域不能为空",
            trigger: "change",
          },
        ],
        workingHours: [
          {
            required: true,
            message: "参加工作时间不能为空",
            trigger: "change",
          },
        ],
        joiningPartyDay: [
          { required: true, message: "入党时间不能为空", trigger: "change" },
        ],
        isXunucha: [
          {
            required: true,
            message: "是否有巡察经历不能为空",
            trigger: "change",
          },
        ],
        isFrontline: [
          {
            required: true,
            message: "是否有一线工作经历不能为空",
            trigger: "change",
          },
        ],
        xunchaYear: [
          {
            required: true,
            message: "巡察年份不能为空",
            trigger: "change",
          },
        ],

        phone: [{ validator: validatePhone, trigger: "blur" }],
      },
      pickerOptions: {
        disabledDate(time) {
          const date = new Date();
          const year = date.getFullYear();
          const timeYear = time.getFullYear();
          return year < timeYear;
        },
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.handleOpen();
  },
  methods: {
    getBusinessKey() {
      if (this.isEditAuthority) {
        return "";
      } else {
        return this.id;
      }
    },
    handleMajor(val){
      this.form.majorName = this.fieldList.find(i=>i.dictValue==val).dictLabel
    },
    handleOpen() {
      // 专家类型
      let param = {
        dictType: "field_type",
        pageNum: 1,
        pageSize: 100,
      };
      listData(param).then((res) => {
        this.fieldList = res.rows;
      });
      if (this.id) {
        getSporadicTalentUserInfo(this.id).then((res) => {
          this.form = res.data;
          this.remoteMethod(res.data.userName);
        });
      }
    },
    //搜索用户下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          selectViewInfoList({
            username: query,
          }).then((res) => {
            this.userList = res.rows;
            this.loading = false;
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    handleSelect(value) {
      const user = this.userList.find((i) => i.loginid == value);
      this.form.companyName = user.companyDpname;
      this.form.companyId = user.companyDpid;
      this.form.orgName = user.dpname;
      this.form.orgRemarks = user.remark;
      this.form.postName = user.position;
      this.form.rankLevel = user.userposilevel;
      this.form.mailbox = user.email;
      this.form.phone = user.phone;
      this.form.userName = user.username;
    },
    confirm() {
      if (!this.file) {
        this.$message.warning("请上传文件!");
        return;
      }
      this.form.resumeAttId = this.file.fileId;
      this.form.resumeAttName = this.file.fileName;
      this.uploadFormShow = false;
    },
    initUpload() {
      this.uploadFormShow = true;
    },
    onFileSuccess(file, files, fileList) {
      this.file = file.result.data;
    },
    onFileRemoved(file, files, fileList) {
      this.file = null;
      this.form.resumeAttId = "";
      this.form.resumeAttName = "";
    },
    downloadFile() {
      this.download(this.form.resumeAttName, this.form.resumeAttId);
    },
    // 初始化流程信息
    initProcess(value) {
      console.log("init");
      // 区分角色，是否禁用表单
      if (value && value.curStepName != "党办主任申请") {
        this.roleType = 0;
      } else {
        this.roleType = 1;
      }
      if (this.isEditAuthority) {
        this.roleType = 1;
      }
      this.process = value;
    },
    // 待办工作流
    onFininshGroupToExamine(res) {
      console.log(res);
      // 审核通过，到下一步
      let params = null;

      if (res.message == "同意") {
        let examineStatus = examineStatusArray.findIndex(
          (i) => i.spet == this.process.curStepName
        );
        params = {
          ...this.form,
          message: "同意",
          taskId: res.taskId,
          nextUserId: res.nextDealUser,
          option: res.option,
          examineStatus,
        };
        console.log(params);
      }
      // 退回
      else {
         let examineStatus = examineStatusArray.findIndex(
          (i) => i.spet == this.process.curStepName
        );
        params = {
          ...this.form,
          message: res.nextStep,
          taskId: res.taskId,
          nextUserId: res.nextDealUser,
          option: res.option,
          examineStatus,
        };
      }
      const loading = this.openLoading();
      fininshSporadicToExamine(params).then((res) => {
        loading.close();
        this.msgSuccess(res.msg);
        this.$refs.deepBtnRef.closeAll();
        setTimeout(() => {
          location.reload();
        }, 500);
      });
    },

    // 发出申请
    saveFromData(value, status) {
      const loading = this.openLoading();
      let params = null;
      if (status == 0) {
        params = {
          status,
          ...this.form,
        };
      } else {
        let nextarr = JSON.parse(value.variables);
        let { nickName, companyName, department } =
          this.$store.getters.userInfo;
        params = {
          ...this.form,
          message: "同意",
          examineStatus: "1",
          nextUserId: nextarr.nextDealUser,
          option: nextarr.option,
          title: `${nickName}-${companyName}(${department})发起的零星入库`,
          status,
        };
      }

      addSporadicTalentUserInfo(params).then((res) => {
        loading.close();
        if (res.code == 200) {
          this.msgSuccess(res.msg);
          this.handleClose(200);
          this.submitProcessShow = false;
        }
      });
    },
    saveBtn() {
      this.$confirm("是否确定暂存?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.saveFromData({}, 0);

          })
    },
    // 点击提交按钮
    submitBtn() {
      // 对表单进行校验
      this.$refs.queryform.validate((valid) => {
        console.log(valid);
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.submitProcessShow = true;
          })
        }
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