<template>
  <div>
    <div class="dialog-context" style="padding-left: 10px">
    <div class="dialog-title">
      <span class="line"></span><span>申请信息</span>
    </div>
      <el-form
        :model="form"
        ref="rulesForm"
        :rules="rulesForm"
        label-width="170px"
        :disabled="obj.operationType == 2 || disabled"
      >
        <el-row :gutter="24" v-if="form.applyId">
          <el-col :span="12">
            <el-form-item label="业务单号：">
              <el-input v-model="form.applyId" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="制度名称：" prop="regulationName">
              <el-input
                v-model="form.regulationName"
                placeholder="请输入制度名称"
                maxlength="200"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="制度层级：" prop="applyType">
              <el-select
                style="width: 100%"
                value-key="value"
                placeholder="请选择"
                @change="handleSelect"
                v-model="form.applyType"
              >
                <el-option
                  v-for="item in contentTypes"
                  :label="item.name"
                  :key="item.value"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item v-if="form.applyType != 1" label="文件编号：">
              <el-input
                v-model="form.regulationCode"
                placeholder="请输入文件编号"
                maxlength="100"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-if="form.applyType != 1"
              label="文号(历史文号序列)："
            >
              <el-input
                v-model="form.docCode"
                placeholder="请输入文号"
                maxlength="100"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="一级目录：" prop="firstContent">
              <el-select
                value-key="contentId"
                style="width: 100%"
                v-model="form.firstContent"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in firstContentSelect"
                  :label="item.contentName"
                  :key="'a' + item.contentId"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="二级目录：" v-if="form.applyType != 1">
              <el-select
                value-key="contentId"
                v-model="form.secondContent"
                style="width: 100%"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in form.firstContent.children"
                  :label="item.contentName"
                  :key="'b' + item.contentId"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="三级目录:" v-if="form.applyType != 1">
              <el-select
                value-key="contentId"
                v-model="form.thirdContent"
                style="width: 100%"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in form.secondContent.children"
                  :label="item.contentName"
                  :key="'c' + item.contentId"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执行有效性评价：" v-if="form.applyType != 1">
              <el-select
                v-model="form.execEffeEvaluation"
                style="width: 100%"
                placeholder="请选择"
              >
                <el-option label="无效" value="无效"></el-option>
                <el-option label="有效" value="有效"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="集团废立计划：" v-if="form.applyType != 1">
              <el-select
                v-model="form.groupAbolitionPlan"
                style="width: 100%"
                placeholder="请选择"
              >
                <el-option label="保留" value="保留"></el-option>
                <el-option label="不保留" value="不保留"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="制度文件：" prop="fileId">
              <el-button
                v-if="!form.fileName"
                type="primary"
                size="small"
                @click="uploadFile"
                >上传制度文件</el-button
              >
              <div class="flex" v-else>
                <el-button type="primary" size="small" @click="uploadFile"
                  >重新上传</el-button
                >
                <el-button
                  :disabled="false"
                  class="accessory"
                  type="text"
                  size=""
                  :title="form.fileName"
                  @click="downloadFile"
                  >{{ form.fileName }}</el-button
                >
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="对落实上级政策要求：" v-if="form.applyType != 1">
          <el-input
            type="textarea"
            v-model="form.policyRequirement"
            maxlength="200"
            show-word-limit
            placeholder="请输入"
          />
        </el-form-item>
        <el-form-item
          :label="
            form.applyType == 2 ? '省公司对应落地制度：' : '对应集团公司制度'
          "
          v-if="form.applyType != 1"
        >
          <el-select
            v-model="form.relaRegulation"
            filterable
            remote
            value-key="contentId"
            reserve-keyword
            style="width: 100%"
            placeholder="请输入关键词"
            @visible-change="handleVisibleChange"
            :remote-method="remoteMethod"
            v-el-select-loadmore="loadmore"
            :loading="loading"
            clearable
          >
            <el-option
              v-for="item in options"
              :key="item.contentId"
              :label="item.regulationName"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <slot></slot>
      </el-form>
      <all-flow-step
        ref="allFlowStepRef"
        :allFlowStep="process.allFlowStep"
        v-if="obj.operationType=='2' "
      ></all-flow-step>
      <!-- 待办页组件 -->
      <div  v-show="obj.operationType!='2'&&obj.operationType!='1'" >
        <deep-btn
          v-bind="$attrs"
          ref="deepBtnRef"
          :businessKey="getBusinessKey()"
          :needBeforeComplete="needBeforeComplete"
          @initProcess="initProcess"
          @saveDraftData="saveBtn"
          @close="handleFormClose"
          @beforeComplete="beforeComplete"
        >
        </deep-btn>
      </div>
    </div>

    <el-dialog
      modal-append-to-body
      append-to-body
      title="文件上传"
      :visible.sync="dialogVisible"
      width="50%"
      @open="initUpload"
      :before-close="handleClose"
    >
      <div>
        <CommonUpload
          v-if="isShowUpload"
          ref="CommonUpload"
          :single-file="true"
          @file-success="onFileSuccess"
          @file-removed="onFileRemoved"
        ></CommonUpload>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
import { list as catalogueList } from "@/api/xuncha/archives/catalogue";
import { regulationList, getById } from "@/api/xuncha/archives/inStorage";
import deepBtn from "@/components/WorkFlow/deepBtn";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { save } from "@/api/xuncha/archives/inStorage";
import { downloadFile } from "@/utils/uploadOperate";
export default {
  directives: {
    "el-select-loadmore": {
      bind(el, binding) {
        // 获取element-ui定义好的scroll盒子
        const SELECTWRAP_DOM = el.querySelector(
          ".el-select-dropdown .el-select-dropdown__wrap"
        );
        SELECTWRAP_DOM.addEventListener("scroll", function () {
          /*   scrollHeight 获取元素内容高度(只读)
                     scrollTop 获取或者设置元素的偏移值,常用于, 计算滚动条的位置, 当一个元素的容器没有产生垂直方向的滚动条, 那它的scrollTop的值默认为0.
                    clientHeight 读取元素的可见高度(只读)
                      */
          const condition =
            Math.ceil(this.scrollHeight) - Math.ceil(this.scrollTop) <=
            this.clientHeight;
          // 判断滚动到底部  (condition:如果元素滚动到底, 下面等式返回true, 没有则返回false)
          if (condition) {
            // binding.value 为自定义指令绑定的值，因为绑定的是个方法，所以这里是方法调用，触发指令后执行加载数据
            binding.value();
          }
        });
      },
    },
  },
  components: {
    CommonUpload,
    deepBtn,
    AllFlowStep,
  },
  props: {
    obj: [Object],
  },
  data() {
    const validateFile = (rule, value, callback) => {
      if (this.form.fileId) {
        callback();
      } else {
        callback(new Error("制度文件不能为空"));
      }
    };

    return {
      query: "",
      loading: false,
      sPageSize: 10,
      sPageNo: 1,
      options: [],
      needBeforeComplete: false,
      disabled: false,
      process: "",
      form: {
        fileId: "",
        fileName: "",
        relaRegulation: {},
        firstContent: {},
        secondContent: {},
        thirdContent: {},
        applyTypeObj: {},
        applyType: 2, //制度层级
        applyTypeName: "集团", //制度名称
        regulationName: "", //制度名称
        docCode: "", //文号
        execEffeEvaluation: "", //执行有效性评价
        policyRequirement: "", //对落实上级政策要求
        groupAbolitionPlan: "", //对应落地制度
        relaRegulationName: "", //对应落地制度名称
        regulationCode: "", //文件编号
      },
      rulesForm: {
        regulationName: [
          { required: true, message: "制度名称不能为空", trigger: "change" },
        ],
        applyType: [
          { required: true, message: "制度层级不能为空", trigger: "change" },
        ],
        firstContent: [
          { required: true, message: "一级目录不能为空", trigger: "change" },
        ],
        fileId: [{ required: true, validator: validateFile }],
      },
      file: {},
      dialogVisible: false,
      isShowUpload: false,
      firstContentSelect: [], //一级下拉数据
      contentTypes: [
        {
          value: 1,
          name: "中央库",
        },
        {
          value: 2,
          name: "集团",
        },
        {
          value: 3,
          name: "省公司",
        },
      ],
      timer: null,
    };
  },
  created() {
    this.catalogueSelect(1, () => {
      if (this.obj.id) {
        // this.init();
        this.getDetail();
      }
    });
  },
  methods: {
    getDetail() {
      getById({ applyId: this.obj.id }).then((res) => {
        if (res.code == 200) {
          let { data } = res;


          // 加载一级目录下拉框数据
          catalogueList({ pageNo: 1, pageSize: 99999 , contentType: data.applyType}).then((res2) => {
            if (res2.code == 200) {
              this.firstContentSelect = this.getTreeData(res2.rows);
              let firstContent = this.firstContentSelect.find(
                (i) => i.contentId == data.firstContentId
              ) || { children: [] };
              let secondContent = firstContent.children.find(
                (i) => i.contentId == data.secondContentId
              ) || { children: [] };
              let thirdContent = secondContent.children&&secondContent.children.find(
                (i) => i.contentId == data.thirdContentId
              ) || { children: [] };
              data.firstContent = firstContent;
              data.secondContent = secondContent;
              data.thirdContent = thirdContent;
              let relaRegulation = {
                contentId: data.relaRegulationId,
                regulationName: data.relaRegulationName,
              };
              data.relaRegulation = relaRegulation;
              this.options.push(relaRegulation);
              // console.log(data.relaRegulation);
              this.form = data;
              // for (const key in this.form) {
              //     this.form[key] = res.data[key];
              //   }
              // console.log(this.form);
            }
          });


        }
      });
    },
    initProcess(process) {
      this.process = process;
      if (process.curStepName == "申请人申请") {
        this.needBeforeComplete = true;
        this.disabled = false;
      } else if(this.obj.operationType==1||this.obj.operationType==3){
        this.disabled = false;

      }
       else {
        this.disabled = true;
      }
    },
    // 点击暂存按钮
    saveBtn() {
      // this.$parent.handleSave();
      this.submit({ userName: "" },'draft');
    },
    submit(e,status,fn) {
      let tempForm = this.form;
      tempForm.firstContentId = tempForm.firstContent.contentId;
      tempForm.firstContentName = tempForm.firstContent.contentName;
      tempForm.secondContentId = tempForm.secondContent.contentId;
      tempForm.secondContentName = tempForm.secondContent.contentName;
      tempForm.thirdContentId = tempForm.thirdContent.contentId;
      tempForm.thirdContentName = tempForm.thirdContent.contentName;
      tempForm.status = status || "draft";
      tempForm.userName = e.userName;
      console.log(tempForm);
      this.$refs.rulesForm.validate((valid) => {
        if (valid) {
          const loading = this.openLoading();
          save(tempForm).then((res) => {
            loading.close();
            if (res.code == 200&&fn) {
              fn()
            }
          });
        }
      });
    },
    getRegulationList() {
      this.loading = true;
      regulationList({
        regulationName: this.query,
        applyType: this.form.applyType,
        pageNo: this.sPageNo,
        pageSize: this.sPageSize,
      }).then((res) => {
        if (res.code == 200) {
          this.options = res.rows;
          this.loading = false;
        }
      });
    },
    beforeComplete(fn) {
      this.submit({ userName: "" }, "checking",fn());

    },
    //用户下拉分页加载
    loadmore() {
      this.sPageNo++;
      this.getRegulationList();
    },
    remoteMethod(query) {
      if (query !== "") {
        this.query = query;
        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
          this.getRegulationList();
        }, 200);
      } else {
        this.userList = [];
      }
    },
    handleVisibleChange() {
      this.form.relaRegulationId = this.form.relaRegulation.regulationCode;
      this.form.relaRegulationName = this.form.relaRegulation.regulationName;
      this.sPageSize = 10;
      this.sPageNo = 1;
    },
    handleSelect(e) {
      let applyTypeObj = this.contentTypes.find((i) => i.value == e);
      this.form.applyTypeName = applyTypeObj.name;
      // 加载一级目录下拉框数据
      catalogueList({ pageNo: 1, pageSize: 99999 , contentType: this.form.applyType}).then((res) => {
        if (res.code == 200) {
          this.firstContentSelect = this.getTreeData(res.rows);
          this.form.firstContent = ''
          this.form.secondContent = ''
          this.form.thirdContent = ''
        }
      });
    },
    //转换数据
    getTreeData(data) {
      let temp = [];
      data.forEach((item) => {
        if (item.parentId == 0) {
          item.children = [];
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
    catalogueSelect(t, call) {
      catalogueList({ pageNo: 1, pageSize: 99999 , contentType: this.form.applyType}).then((res) => {
        if (res.code == 200) {
          this.firstContentSelect = this.getTreeData(res.rows);
        }
        call();
      });
    },
    confirm() {
      if (!this.file) {
        this.$message.warning("请上传文件!");
        return;
      }
      this.form.fileId = this.file.fileId;
      this.form.fileName = this.file.fileName;
      this.dialogVisible = false;
      this.isShowUpload = false;
      this.$refs.rulesForm.clearValidate("fileId");
    },
    //打开文件上传清楚之前的记录
    initUpload() {
      this.isShowUpload = true;
    },
    //成功回调
    onFileSuccess(file, files, fileList) {
      this.file = file.result.data;
    },
    //移除回调
    onFileRemoved(file, files, fileList) {
      this.file = null;
      this.form.fileId = "";
      this.form.fileName = "";
    },
    uploadFile() {
      this.dialogVisible = true;
    },
    handleClose() {
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    handleFormClose() {
      this.$emit("close");
    },
    downloadFile() {
      downloadFile(this.form.fileId);
      // this.download(this.form.fileName, this.form.fileId);
    },
    // 获得业务主键
    getBusinessKey() {
      // 如果发起流程，业务主键为空

      return this.obj.id;
    },
  },
};
</script>

<style lang="scss" scoped>
.flex{
  display: flex;
  align-items: center;
}
.accessory {
  width: 180px;
  overflow: hidden;
  white-space: nowrap;
  cursor: default;
  display: inline-block;
  text-overflow: ellipsis;
}
</style>
