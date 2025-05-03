<template>
  <div>
    <!-- 页面表单 -->
    <div class="dialog-content">
      <div class="context-row" style="margin-bottom: 0; padding-bottom: 0px">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="100px"
          :disabled="formDisabled"
        >
          <el-row :gutter="20" v-if="caseType === 4">
            <el-col :span="12">
              <el-form-item label="业务单号：" prop="applyType">
                <el-input
                  v-model="form.processId"
                  disabled
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="申请类型：" prop="applyType">
                <el-select
                  v-model="form.applyType"
                  placeholder="请选择申请类型"
                  style="width: 100%"
                  disabled
                >
                  <el-option
                    v-for="dict in applyTypeSelectData"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="领域类型：" prop="domainType">
                <el-select
                  v-model="form.domainType"
                  placeholder="请选择领域类型"
                  style="width: 100%"
                >
                  <el-option
                    v-for="dict in domainTypeSelectData"
                    :key="dict.dataId"
                    :label="dict.dataName"
                    :value="dict.dataCode"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item
                label="上传文件："
                prop="fileId"
                style="width: 100%"
              >
                <el-button
                  type="text"
                  :title="form.name"
                  class="accessory"
                  style="font-size: 12px; color: #1590ff"
                >
                  {{ form.name }}
                </el-button>
                <el-button
                  type="primary"
                  v-show="!formDisabled"
                  size="mini"
                  @click="uploadFileBtn()"
                >
                  <span v-if="form.fileId">重新上传</span>
                  <span v-else>上传文件</span>
                </el-button>
                <el-button
                  type="primary"
                  size="mini"
                  @click="onPreview()"
                  v-if="form.fileId"
                  :disabled="false"
                  >预览</el-button
                >
                <el-button
                  type="primary"
                  size="mini"
                  @click="onDownload()"
                  v-if="form.fileId"
                  :disabled="false"
                  >下载</el-button
                >
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="案例数量：" prop="domainNum">
                <el-input
                  v-model="form.domainNum"
                  placeholder="请输入领域案例数量"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <!--<el-row :gutter="20">
          <el-col :span="12">
            <a href="javascript:" @click="onPreview()" style="color: #409eff">
              {{ form.fileName }}
            </a>
          </el-col>
        </el-row>-->
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="文件名称：" prop="fileName">
                <el-input
                  v-model="form.fileName"
                  placeholder="请输入内容"
                  maxlength="1000"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="文号：" prop="controlNo">
                <el-input
                  v-model="form.controlNo"
                  placeholder="请输入内容"
                  maxlength="1000"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="版本：" prop="versions">
            <el-input
              v-model="form.versions"
              placeholder="请输入内容"
              maxlength="1000"
            />
          </el-form-item>
          <el-form-item label="说明：" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请输入内容"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!-- 操作按钮 -->
    <div slot="footer" class="dialog-footer">
      <!--
        @initProcess 获得流程信息数据
        businessKey 业务主键，可用于判断是否使用默认按钮，如果为空使用默认按钮
        @saveDraftData 暂存事件
        @close 关闭窗口事件
        needBeforeComplete 保存前是否具有前置操作
        @beforeComplete 保存前的前置操作逻辑
      -->
      <deep-btn
        ref="deepBtnRef"
        v-bind="$attrs"
        @initProcess="initProcess"
        :businessKey="getBusinessKey()"
        @saveDraftData="saveBtn"
        @close="handleClose"
        :needBeforeComplete="true"
        @beforeComplete="beforeComplete"
      >
        <!-- 发起默认按钮，由 businessKey 控制，为空的话显示下面的默认按钮 -->
        <el-button type="primary" @click="saveBtn">暂存</el-button>
        <el-button type="primary" @click="submitBtn">提交</el-button>
      </deep-btn>
    </div>
    <!-- 文件上传页面组件 -->
    <uploadForm
      :dialogVisible="uploadFormShow"
      @file-success="fileSuccess"
      @close="uploadFormShow = false"
      singleFile
      ref="uploadForm"
    />
    <!-- 申报页面的提交页面 -->
    <submit-process
      title="提交"
      processKey="xuncha_case_flow"
      stepName="巡察室经理 复核文件"
      :dialogVisible="submitProcessShow"
      @close="submitProcessShow = false"
      @complete="saveFromData"
    >
    </submit-process>
  </div>
</template>
<script>
import {
  listXunchaCase,
  getXunchaCase,
} from "@/api/xuncha/archives/xunchaCase";
import {
  listXunchaCaseProcess,
  getXunchaCaseProcess,
  delXunchaCaseProcess,
  addXunchaCaseProcess,
  updateXunchaCaseProcess,
  analysisExcelRow,
} from "@/api/xuncha/archives/xunchaCaseProcess";
import uploadForm from "@/components/CommonUpload/uploadForm";
import { selectDictData } from "@/api/xuncha/xunchaDictData";
import CompleteProcess from "@/components/WorkFlow/completeProcess";
import deepBtn from "@/components/WorkFlow/deepBtn";
import SubmitProcess from "@/components/WorkFlow/submitProcess";
import {
  downloadFile,
  previewFile,
  downloadZipFile,
} from "@/utils/uploadOperate";

export default {
  components: { uploadForm, CompleteProcess, deepBtn, SubmitProcess },
  props: {
    // 1 申报；2 修订；3废止；4暂存(退回巡察办主管)；5审批
    caseType: {
      type: Number,
      default: 1,
    },
    caseId: {
      type: String,
      default: "",
    },
    todoId: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        domainType: [
          { required: true, message: "领域类型不能为空", trigger: "blur" },
        ],
        fileId: [
          { required: true, message: "案例数量不能为空", trigger: "blur" },
        ],
        domainNum: [
          { required: true, message: "文件不能为空", trigger: "change" },
          {
            required: true,
            message: "请输入正整数",
            pattern: /^\d+$/g,
            trigger: "change",
          },
        ],
        fileName: [
          { required: true, message: "文件名称不能为空", trigger: "blur" },
        ],
      },
      // 是否禁用表单
      formDisabled: false,
      // 申请类型下拉框数据
      applyTypeSelectData: [],
      // 领域类型下拉框数据
      domainTypeSelectData: [],
      // 是否显示文件上传弹出框
      uploadFormShow: false,
      // 是否显示选人组件
      participantSelectShow: false,
      // 申报页面的提交页面
      submitProcessShow: false,
      // 流程信息
      processInfo: null,
    };
  },
  created() {
    // 获得申请类型下拉框数据
    this.getDicts("case_apply_type").then((response) => {
      this.applyTypeSelectData = response.data || [];
    });
    // 领域类型下拉框
    selectDictData({ dictType: "domain_type" }).then((res) => {
      this.domainTypeSelectData = res.data || [];
    });
    // 初始化数据
    this.initData();
  },
  methods: {
    // 关闭页面返回 close 事件
    handleClose() {
      // this.form = {};
      this.$refs["form"].clearValidate();
      this.$emit("close");
    },
    // 获得业务主键
    getBusinessKey() {
      // 如果是申报、修订、废止 页面，没有发起流程，业务主键为空
      if (this.caseType === 1 || this.caseType === 2 || this.caseType === 3) {
        return "";
      } else {
        return this.caseId;
      }
    },
    // 点击暂存按钮
    saveBtn() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定暂存?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            // 新增接口
            addXunchaCaseProcess(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("已暂存");
                this.$emit("close");
              }
            });
          });
        }
      });
    },
    // 点击上传文件按钮
    uploadFileBtn() {
      this.uploadFormShow = true;
    },
    // 文件上传成功回调方法
    fileSuccess(files) {
      this.msgSuccess("上传成功");
      this.form.fileId = files[0].result.data.fileId;
      let name = files[0].result.data.fileName;

      this.form.name = name;
      this.form.fileName = name.substring(0, name.lastIndexOf("."));
      // 文件上传后解析文件
      analysisExcelRow(this.form.fileId).then((res) => {
        if (res.data && !this.form.domainNum) {
          this.form.domainNum = res.data;
        }
      });
    },
    // 点击提交按钮
    submitBtn() {
      // 对表单进行校验
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.submitProcessShow = true;
          });
        }
      });
    },
    // 初始化数据
    initData() {
      switch (this.caseType) {
        case 1:
          // 申报页面
          this.formDisabled = false;
          this.form = {
            processId: null, // 案例业务流程ID
            serialNo: null, // 工单流水号
            title: null, // 标题
            caseId: null, // 案例ID
            fileId: null, // 文件ID
            fileName: null, // 文件名称
            applyType: "A", // 申请类型 文件申报、文件修订、文件废止
            domainType: null, // 领域类型 业务字典：domain_type
            domainTypeName: null, // 领域类型名称
            domainNum: null, // 领域案例数量
            controlNo: null, // 文号
            versions: null, // 版本
            status: null, // 流程业务状态
            remark: null, // 说明
          };
          this.form.applyType = "A";
          break;
        case 2:
          // 修订页面
          this.formDisabled = false;
          getXunchaCase(this.caseId).then((res) => {
            this.form = res.data;
            this.form.applyType = "B";
          });
          break;
        case 3:
          // 废止页面
          this.formDisabled = true;
          getXunchaCase(this.caseId).then((res) => {
            this.form = res.data;
            this.form.applyType = "C";
          });
          break;
        case 4:
          // 暂存、审核页面
          let itemCaseId = this.caseId;
          if (!itemCaseId) {
            itemCaseId = this.$route.query.bussinessKey;
          }
          getXunchaCaseProcess(itemCaseId).then((res) => {
            this.form = res.data;
          });
          break;
      }
    },
    // 申报页面的提交页面中点击发送
    saveFromData(value) {
      this.form.username = JSON.parse(value.variables).nextDealUser;
      const loading = this.openLoading();
      // 下一个处理人
      addXunchaCaseProcess(this.form).then((response) => {
        if (response.code === 200) {
          this.msgSuccess("提交成功");
          this.submitProcessShow = false;
          loading.close();
          this.$emit("close");
        }
      });
    },
    // 初始化流程信息
    initProcess(value) {
      // 区分暂存和流程页面，是否禁用表单
      if (value && this.caseType === 4) {
        this.formDisabled = value.taskDefinitionKey !== "user01";
      }
    },
    // 点击下一步前的操作
    beforeComplete(fn) {
      if (!this.formDisabled) {
        // 保存数据，暂存的时候才需要保存
        const loading = this.openLoading();
        this.$refs["form"].validate((valid) => {
          if (valid) {
            // 新增接口
            updateXunchaCaseProcess(this.form).then((response) => {
              if (response.code === 200) {
                this.$emit("close");
                loading.close();
                fn();
              }
            });
          }
        });
      } else {
        fn();
      }
    },

    // 预览文件
    onPreview() {
      previewFile(this.form.fileId);
    },
    // 文件下载
    onDownload(fileId, caseId) {
      downloadFile(this.form.fileId);
    },
  },
  watch: {
    caseId() {
      this.initData();
    },
    caseType() {
      this.initData();
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog-footer {
  text-align: center;
}
.accessory {
  max-width: 100px;
  overflow: hidden;
  white-space: nowrap;
  cursor: default;
  display: inline-block;
  text-overflow: ellipsis;
}
/deep/ .el-dialog__body {
  height: 500px;
  overflow: auto;
}
</style>
