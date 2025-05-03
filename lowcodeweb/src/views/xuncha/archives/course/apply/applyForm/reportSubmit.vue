<template>
  <div>
    <div class="dialog-context">
      <div class="base-info">
        <!-- 表单 -->
        <!-- <baseform
          ref="baseformRef"
          :type="type"
          @sumitData="initBaseForm"
        ></baseform> -->
        <div>
          <div class="form-module-title">
            <span class="line"></span><span>申请信息</span>
          </div>
          <div class="context-row" style="margin-bottom: 30px">
            <el-form
              :model="formData"
              :rules="formRules"
              ref="formRef"
              label-width="130px"
              class="searchFormClass"
              :disabled="type == '3' || roleType != '1' ? true : false"
            >
              <el-row :gutter="24" v-if="formData.applyId">
                <el-col :span="12">
                  <el-form-item label="业务单号：">
                    <el-input
                      v-model="formData.applyId"
                      disabled
                      style="width: 100%"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="24">
                <el-col :span="12">
                  <el-form-item
                    label="巡察轮次："
                    prop="xunchaLunciId"
                    required
                  >
                    <lunci
                      ref="lunciRef"
                      v-model="formData.xunchaLunciId"
                      :label.sync="formData.xunchaLunciName"
                      :width="width"
                      isLink
                      clearable
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="巡察年份：" prop="xunchaDate">
                    <el-date-picker
                      style="width: 100%"
                      v-model="formData.xunchaDate"
                      type="year"
                      placeholder="选择年"
                      value-format="yyyy"
                      @change="
                        (val) =>
                          handleSelectChange(
                            val,
                            ['xunchaPiciId', 'xunchaTaskId', 'xunchaGroup'],
                            'piciRef',
                            'year'
                          )
                      "
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="24">
                <el-col :span="12">
                  <el-form-item label="巡察批次：" prop="xunchaPiciId" required>
                    <pici
                      ref="piciRef"
                      isLink
                      v-model="formData.xunchaPiciId"
                      :label.sync="formData.xunchaPiciName"
                      :width="width"
                      @change="
                        (val) =>
                          handleSelectChange(
                            val,
                            ['xunchaTaskId', 'xunchaGroup'],
                            'taskRef',
                            'pici'
                          )
                      "
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="巡察任务：" prop="xunchaTaskId" required>
                    <task
                      isLink
                      ref="taskRef"
                      v-model="formData.xunchaTaskId"
                      :width="width"
                      @change="(val) => handleTaskChange(val)"
                    />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="24">
                <el-col :span="12">
                  <el-form-item label="巡察组：" prop="xunchaGroup">
                    <group
                      v-model="formData.xunchaGroup"
                      :label.sync="formData.groupName"
                      :width="width"
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="被巡察单位：" prop="orgId">
                    <el-select
                      v-model="formData.orgId"
                      placeholder="请选择被巡察单位"
                      style="width: 100%"
                    >
                      <el-option
                        v-for="item in taskUnitList"
                        :key="item.orgId"
                        :label="item.orgName"
                        :value="item.orgId"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="24">
                <el-col :span="12">
                  <el-form-item label="入库资料类型：" prop="archiveType">
                    <!-- <dict
                      @change="handleArchiveType"
                      v-model="formData.archiveType"
                      :label.sync="formData.archiveTypeName"
                      :width="width"
                      :dictType="$dicCode.reportType"
                    ></dict> -->
                    <el-input disabled value="巡察报告"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item
                    label="归档组织："
                    prop="archiveOrgType"
                    required
                  >
                    <el-radio-group
                      @change="handleArchiveOrgType"
                      v-model="formData.archiveOrgType"
                    >
                      <el-radio
                        v-for="item in archiveOrgTypeList"
                        :key="item.label"
                        :label="item.label"
                        >{{ item.name }}
                      </el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="24">
                <el-col :span="24">
                  <el-form-item label="说明：" prop="remark">
                    <el-input
                      v-model="formData.remark"
                      type="textarea"
                      maxlength="200"
                      show-word-limit
                      placeholder="请输入说明"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </div>
        <!-- 归档清单 -->
        <apply-content-report
          v-show="roleType != '4'"
          :roleType="roleType"
          :authorizeFlag="authorizeFlag"
          ref="applycontentRef"
        ></apply-content-report>

        <!-- 归档资料 -->
        <applycontentfile
          v-show="process && process.curStepName != '申报人拟稿'"
          ref="applycontentfileRef"
          :bussinessKey="applyId"
          :roleType="roleType"
          :authorizeFlag="authorizeFlag"
        >
          <template v-slot:operation>
            <el-button type="primary" @click="batchDownload" size="small"
              >批量下载
            </el-button>
          </template>
        </applycontentfile>
        <!-- 共享信息 -->
        <participant v-show="roleType == '2'" ref="participant"></participant>
      </div>

      <submit-Process
        ref="completeRef"
        :nextStepLabel="nextStepLabel"
        :dialogVisible="submitProcessShow"
        @complete="saveApplyFromData"
        @close="submitProcessShow = false"
      ></submit-Process>
      <div style="height: 76px"></div>
      <!-- 流程信息 -->
      <div slot="footer" class="form-module-footer">
        <Deep-btn
          ref="deepbtnRef"
          v-bind="$attrs"
          :form="formData"
          :type="type"
          :roleType="roleType"
          :formQuery="baseForm"
          :applyId="applyId"
          :todoId="todoId"
          :businessKey="businessKey"
          needBeforeComplete
          @close="handleClose"
          @queryDetail="queryDetail"
          @initiatorComplete="initSubmitBtn"
          @participantComplete="handleparticipantComplete"
          @saveDraftData="saveDraftData"
          @setAuthorizeFlag="setAuthorizeFlag"
          @participantSaveDraftData="participantSaveDraftData"
        >
          <el-button type="primary" @click="saveDraftData">暂存</el-button>
          <el-button type="primary" @click="submitBtn">提交</el-button>
        </Deep-btn>
      </div>
    </div>
  </div>
</template>
<script>
import { fininshFormTask } from "@/api/workflow";
import {
  saveProcessApplyData,
  queryTaskUnit,
} from "@/api/xuncha/archives/xunchaProcessApply.js";
import { saveReportApplyData } from "@/api/xuncha/archives/xunchaReportApplyContent";
import {
  getReportApplyDataDetail,
  getContentReportFileAndParticipant,
} from "@/api/xuncha/archives/xunchaProcessApply";
import { addFiles, endTodo } from "@/api/xuncha/archives/xunchaProcessFile";
import { addReportFiles } from "@/api/xuncha/archives/xunchaReportFile";

import Lunci from "@/components/XunCha/LunCi/index";
import Pici from "@/components/XunCha/Pici/index";
import Task from "@/components/XunCha/Task/index";
import Company from "@/components/XunCha/Company/index";
import Dict from "@/components/XunCha/Dict/index";
import Group from "@/components/XunCha/Group/index";
import baseform from "./components/baseform";
import applyContentReport from "./components/reportCompents/contentReport.vue";
import applycontentfile from "./components/reportCompents/contentReportFile.vue";
import participant from "./components/participant";
import contentSelect from "./components/contentSelect";
import auditProcess from "./components/auditProcess";
// import ApplyFooter from "@/components/ApplyFooter";

import DeepBtn from "./applyDeepBtn.vue";
import SubmitProcess from "./components/applySubmit.vue";
import {
  downloadFile,
  previewFile,
  downloadZipFile,
} from "@/utils/uploadOperate";

export default {
  components: {
    auditProcess,
    DeepBtn,
    baseform,
    applyContentReport,
    applycontentfile,
    participant,
    contentSelect,
    SubmitProcess,
    Lunci,
    Pici,
    Task,
    Company,
    Dict,
    Group,
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    ReportApplyContentble: {
      type: Boolean,
      default: false,
    },
    applyId: {
      type: String,
      default: "",
    },
    //1->新建 2->编辑 3->查看
    //流程相关
    //0->发起 1->流程运行中
    type: {
      type: String,
      default: "",
    },
  },
  data() {
    let validateYear = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请选择年份"));
      } else {
        callback();
      }
    };
    return {
      activeName: 1,
      // 流程处理弹窗
      submitProcessShow: false,
      //业务模块
      auditProcessShow: false,
      participantSelectShow: false,
      //0无权限 1可查看 2可处理
      authorizeFlag: "",
      btns: null,
      id: "archive_process_flow_test1",
      businessKey: "",
      // id: "archive_process_flow_001",
      process: {},
      taskId: "",
      todoId: "",
      baseForm: {},
      // 1->申请人 2->负责人 3->共享人 4->审核人
      roleType: "1",
      width: "100%",
      orderShow: true,
      multipleSelection: [], //多选
      nextStepLabel: "巡察组",
      archiveOrgTypeList: [
        { label: "A", name: "巡察组" },
        { label: "B", name: "被巡察单位" },
        { label: "C", name: "其他单位" },
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
        archiveOrgType: "A",
        archiveObjectId: "002",
        archiveObjectName: "归档对象",
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
        applyType: "2",
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
        orgId: [
          { required: true, message: "请选择被巡察单位", trigger: "change" },
        ],
        archiveType: [
          { required: false, message: "请选择入库类型", trigger: "change" },
        ],
        archiveOrgType: [
          { required: true, message: "请选择归档组织", trigger: "change" },
        ],
      },
      batchs: [],
      tableData: [{}], //入库资料信息
      taskInfo: [{}], //流程信息
      taskUnitList: [],
      nextObj: {
        //下一步信息
        remark: "",
      },
      contentListSele: [],
    };
  },

  watch: {
    formData: {
      handler(value) {
        this.handleArchiveOrgType(value.archiveOrgType);
        this.initBaseForm(value, this.nextStepLabel);
      },
      deep: true,
    },
  },
  mounted() {
    // debugger
    let bussinessKey = this.$route.query.bussinessKey;
    if (bussinessKey) {
      this.applyId = bussinessKey;
      this.handleOpen();
    }
  },
  methods: {
    handleSelectChange(val, formKeyArr, ref, condition) {
      // 清空选中值
      formKeyArr.forEach((i) => {
        this.formData[i] = "";
      });
      this.cleanOptions(condition);
      this.$refs[ref].queryList(val);
    },
    cleanOptions(condition) {
      if (condition == "year") {
        this.$refs.piciRef.options = [];
        this.$refs.taskRef.options = [];
      } else if (condition == "lunci") {
        this.$refs.taskRef.options = [];
      } else if (condition == "pici") {
        this.$refs.taskRef.options = [];
      }
    },
    // 巡察任务select
    handleTaskChange(val) {
      let { options } = this.$refs.taskRef;
      this.$set(
        this.formData,
        "xunchaGroup",
        options.find((i) => i.taskId == val).groupName
      );
      this.formData.orgId = "";
      queryTaskUnit({ taskId: val }).then((res) => {
        this.taskUnitList = res.data || [];
      });
    },
    handleArchiveType(val) {
      console.log(val);
    },
    batchDownload() {
      let files = this.$refs.applycontentfileRef.ids;
      downloadZipFile(files);
    },
    // 申请人暂存
    saveDraftData() {
      this.checkFormData(() => {
        this.saveFromData({}, "暂存");
      });
    },
    // 申请人提交
    submitBtn() {
      this.checkFormData(() => {
        this.$confirm("是否确定提交?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          // 打开下一步弹框
          this.submitProcessShow = true;
        });
      });
    },
    saveApplyFromData(obj) {
      let param = {};
      // 下一步处理人
      if (obj.userId) {
        param.todoHandler = obj.userId;
      } else {
        delete param.todoHandler;
      }
      param.base = this.baseForm;
      this.setContentList();
      param.contentList = this.contentListSele;
      param.contentFileList = this.$refs.applycontentfileRef.tableData;
      const loading = this.openLoading();
      saveReportApplyData(param).then((res) => {
        if (res.code == 200) {
          if (this.taskId) {
            let fm = new FormData();
            fm.append("taskId", this.taskId);
            fm.append("userId", this.$store.getters.name);

            fm.append(
              "TaskVariables",
              JSON.stringify({
                nextDealUser: obj.userId,
                message: "提交",
                option: obj.option,
              })
            );
            fininshFormTask(fm).then((res) => {
              loading.close();
              if (res.code == 200) {
                this.$message.success("提交成功");
                setTimeout(() => {
                  this.$emit("close");
                  location.reload();
                }, 500);
              } else {
                this.$message.error("提交失败");
                // this.$emit("close");
              }
            });
          } else {
            this.msgSuccess(res.msg);
            loading.close();
            this.handleClose();
          }
        }
      });
    },
    // 申请人拟稿提交,打开弹框
    initSubmitBtn() {
      this.submitBtn();
    },
    setAuthorizeFlag(val) {
      this.authorizeFlag = val;
    },
    // 主责&共享人暂存
    participantSaveDraftData(call) {
      let params = this.$refs.applycontentfileRef.tableData;
      var test = window.location.href;
      params.forEach((res) => {
        if (call == "saveDraft") {
          res.status = 0;
        } else {
          res.status = 1;
        }
      });
      if (test.indexOf("archive_report_process") != -1) {
        addReportFiles(params, this.applyId).then((res) => {
          if (typeof call == "function") {
            call();
          } else {
            this.msgSuccess(res.msg);
          }
        });
      } else {
        addFiles(params, this.applyId).then((res) => {
          if (typeof call == "function") {
            call();
          } else {
            this.msgSuccess(res.msg);
          }
        });
      }
    },

    // 共享人完成
    handleparticipantComplete(taskId) {
      this.participantSaveDraftData(() => {
        const tableData = this.$refs.participant.tableData;
        const result = tableData.find((item) => {
          return item.userId == this.$store.getters.name;
        });
        const loading = this.openLoading();
        endTodo({
          id: result.id,
          status: 1,
          taskId,
        }).then((res) => {
          loading.close();
          if (res.code == 200) {
            this.msgSuccess(res.msg);
            setTimeout(() => {
              this.$emit("close");
              location.reload();
            }, 500);
          }
        });
      });
    },

    // 打开弹框回调
    handleOpen() {
      if (this.applyId) {
        this.businessKey = this.applyId;
      } else {
        this.dataInit();
      }

      return;
    },
    // 申请详情
    queryDetail(process) {
      this.process = process;
      this.taskId = process.curtaskId;
      this.authorizeFlag = process.authorizeFlag + "";

      getReportApplyDataDetail({ applyId: this.applyId }).then((response) => {
        let { contentList, base } = response.data;
        let { archiveUserId } = base;

        // 表单数据回显
        let formData = this.formData;
        for (const key in formData) {
          formData[key] = base[key];
        }
        queryTaskUnit({ taskId: formData.xunchaTaskId }).then((res) => {
          this.taskUnitList = res.data || [];
        });
        this.handleSelectChange(this.formData.xunchaDate, [], "lunciRef");
        // 获得巡察批次下拉框数据
        this.handleSelectChange(this.formData.xunchaDate, [], "piciRef");
        // 获得巡察任务下拉框数据
        this.handleSelectChange(this.formData.xunchaPiciId, [], "taskRef");

        // 归档清单回显
        this.$refs.applycontentRef.tableData = this.getTreeData(
          contentList.map((i) => {
            i.isSelect = true;
            return i;
          })
        );
        console.log();
        this.setRoleType(archiveUserId);
      });
    },
    getTreeData(data) {
      let temp = [];
      data.forEach((item) => {
        if (item.parentId == 0) {
          temp.push(item);
        } else {
          let parent = data.filter(
            (item2) => item2.contentId == item.parentId
          )[0];
          if (parent) {
            if (!parent.children) {
              parent.children = [];
            }
            parent.children.push(item);
          }
        }
      });
      return temp;
    },
    initBaseForm(value, nextStepLabel) {
      this.baseForm = value;
      this.nextStepLabel = nextStepLabel;
    },
    setRoleType(archiveUserId) {
      let lookType = "oneself";
      let applyer = this.process.allFlowStep.find((i) => {
        return i.taskDefinitionKey == "apply";
      });
      let auditer = this.process.allFlowStep.find((i) => {
        return (
          i.taskDefinitionKey == "shijingli_audit" ||
          i.taskDefinitionKey == "leader_audit"
        );
      });
      // 当前节点处理人
      let curStepUser = this.process.curStepUser.split(",");

      if (
        curStepUser &&
        curStepUser.some((i) => i == this.$store.getters.nickName)
      ) {
        switch (this.process.taskDefinitionKey) {
          case "apply":
            lookType = "all";
            this.roleType = "1";
            break;
          case "collect_file":
            if (archiveUserId == this.$store.getters.name) {
              lookType = "all";
              this.roleType = "2";
            } else {
              this.roleType = "3";
            }
            break;
          case "shijingli_audit":
            lookType = "all";
            this.roleType = "4";
            break;
          case "leader_audit":
            lookType = "all";
            this.roleType = "4";
            break;
        }
      } else {
        // 申请人
        lookType = "oneself";
        this.roleType = "3";
        if (
          applyer &&
          applyer.historicTaskInstance.assignee == this.$store.getters.name
        ) {
          lookType = "all";
          this.roleType = "1";
        }
        // 归档人
        if (archiveUserId == this.$store.getters.name) {
          lookType = "all";
          this.roleType = "2";
        }
        // 审核人
        if (
          auditer &&
          auditer.historicTaskInstance.assignee == this.$store.getters.name
        ) {
          lookType = "all";
          this.roleType = "4";
        }
      }
      getContentReportFileAndParticipant({
        applyId: this.applyId,
        lookType,
      }).then((res) => {
        let { contentFileList, participantList } = res.data;
        // 归档资料回显
        this.$refs.applycontentfileRef.tableData = contentFileList;
        // 共享信息回显
        this.$refs.participant.tableData = participantList;
      });
    },
    setContentList() {
      this.contentListSele = [];
      let tableData = this.$refs.applycontentRef.tableData;
      this.test_parse(tableData);
    },
    test_parse(source) {
      for (var key in source) {
        if (source[key].isSelect) {
          this.contentListSele.push(source[key]);
        }
        console.log(source[key]);
        if (source[key].children && source[key].children.length != 0) {
          console.log(source[key].children);
          this.test_parse(source[key].children);
        }
      }
    },
    // 申请人保存数据
    saveFromData(obj, type, fn) {
      this.$confirm("是否确定暂存?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let param = {};
        // 下一步处理人
        if (obj.userId) {
          param.todoHandler = obj.userId;
        } else {
          delete param.todoHandler;
        }
        param.base = this.baseForm;
        this.setContentList();
        param.contentList = this.contentListSele;
        param.contentFileList = this.$refs.applycontentfileRef.tableData;
        const loading = this.openLoading();
        saveReportApplyData(param).then((res) => {
          if (fn) {
            fn();
          } else {
            if (res.code == 200) {
              loading.close();
              this.msgSuccess("提交成功");
              this.handleClose();
            }
          }
        });
      });
    },
    // 保存taskId
    // saveTaskId() {
    //   this.taskId = this.$refs.deepbtnRef.process.curtaskId;
    // },

    // 关闭弹框
    handleClose() {
      this.$emit("close");
    },

    // 初始化数据
    dataInit() {
      this.authorizeFlag = "2";
      this.roleType = "1";
      this.$refs.applycontentRef.init();
    },
    // 校验表单
    checkFormData(call) {
      this.$refs.formRef.validate((valid) => {
        if (this.$refs.applycontentRef.tableData.length == 0) {
          this.msgError("归档清单不能为空！");
        } else if (valid) {
          call();
        } else {
          this.msgError("请完善表单信息！");
        }
      });
    },
    handleArchiveOrgType(val) {
      this.nextStepLabel = this.archiveOrgTypeList.find(
        (i) => i.label == val
      ).name;
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