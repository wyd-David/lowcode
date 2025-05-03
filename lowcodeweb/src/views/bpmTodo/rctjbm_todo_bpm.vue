<template>
  <div class="app-main">
    <todo-bpm-header></todo-bpm-header>
    <div class="dialog-context">
      <div class="dialog-title">
        <span class="line"></span><span>待办信息</span>
      </div>
      <div class="context-row">
        <el-form :model="form" disabled label-width="120px">
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="业务单号:">
                <el-input v-model="form.orderNumber" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="标题:">
                <el-input v-model="form.title" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="收集类型:">
                <el-radio-group v-model="form.collectionType">
                  <el-radio
                    :label="item.dictValue"
                    v-for="item in collectDicts"
                    :key="item.dictValue"
                    >{{ item.dictLabel }}</el-radio
                  >
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="下发机构:">
                <el-input v-model="form.companyName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="参加工作时间:">
                <el-input :value="form.joinWorkDate"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="入党时间:">
                <el-date-picker
                  v-model="form.joinPartDate"
                  type="month"
                  placeholder="选择日期"
                  value-format="yyyy-MM"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="计划巡察时段:">
                <div style="display: flex">
                  <el-input
                    :value="form.startInspectTime"
                    style="width: 49%"
                  ></el-input>
                  <span style="margin: 0px 2px">-</span>
                  <el-input
                    style="width: 49%"
                    :value="form.endInspectTime"
                  ></el-input>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收集截止时间:">
                <el-input :value="form.collectionEndDate"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="说明:">
            <el-input
              type="textarea"
              v-model="form.remark"
              :rows="3"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="dialog-title">
        <span class="line"></span><span>需求名额指标</span>
      </div>
      <div class="context-row">
        <el-table
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          :data="form.fieldList"
        >
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column label="单位">
            <template>
              <span>{{ form.companyName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="部门">
            <template>
              <span>{{ form.deptName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="fieldTypeName"
            label="专家领域"
          ></el-table-column>
          <el-table-column
            prop="personNumber"
            label="指标人数(人)"
          ></el-table-column>
        </el-table>
        <div style="text-align: right">
          总计:<span style="color: #b1261d"> {{ total }} </span>人
        </div>
      </div>
      <div class="dialog-title">
        <span class="line"></span><span>推荐人才</span>
      </div>
      <div class="context-row">
        <div class="operation">
          <el-button
            size="small"
            v-if="
              process.curStepName != '部门已推荐' &&
              process.authorizeFlag == '2'
            "
            type="primary"
            @click="addItem"
            >添加</el-button
          >
          <el-button size="small" type="primary" @click="downloadFile"
            >下载人才推荐单</el-button
          >
        </div>
        <el-table :data="talentData">
          <el-table-column
            label="序号"
            align="center"
            type="index"
          ></el-table-column>
          <el-table-column
            :render-header="renderHeader"
            width="150"
            align="left"
            label="姓名"
          >
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.user"
                filterable
                remote
                value-key="userId"
                reserve-keyword
                placeholder="请输入关键词"
                @visible-change="handleVisibleChange"
                @change="handleSelectUser(scope.row)"
                :remote-method="remoteMethod"
                v-el-select-loadmore="loadmore"
                :loading="loading"
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
              >
                <el-option
                  v-for="item in options"
                  :key="item.userId"
                  :label="
                    item.userName +
                    '-' +
                    item.userId +
                    '-' +
                    item.departmentName
                  "
                  :value="item"
                >
                </el-option>
              </el-select>
              <span v-else>
                {{ scope.row.userName }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            :render-header="renderHeader"
            width="150"
            align="left"
            label="专家类型"
          >
            <template slot-scope="scope">
              <el-select
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
                v-model="scope.row.field"
                value-key="fieldType"
                @change="handleSelectField(scope.row)"
              >
                <el-option
                  v-for="item in form.fieldList"
                  :key="item.fieldType"
                  :label="item.fieldTypeName"
                  :value="item"
                ></el-option>
              </el-select>
              <span v-else>
                {{ scope.row.fieldTypeName }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            :render-header="renderHeader"
            min-width="200"
            align="center"
            label="人才推荐附件"
          >
            <template slot-scope="scope">
              <div
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
              >
                <el-tooltip
                  v-if="scope.row.fileKey"
                  class="item"
                  effect="dark"
                  :content="scope.row.fileName"
                  placement="top-start"
                >
                  <el-button type="text" @click="handlePreviewFile(scope.row)">
                    <span
                      style="
                        display: inline-block;
                        width: 200px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                      "
                    >
                      {{ scope.row.fileName }}
                    </span>
                  </el-button>
                </el-tooltip>

                <el-button
                  v-else
                  type="text"
                  size="small"
                  @click="uploadFile(scope.row)"
                  >上传文件</el-button
                >
                <el-button
                  @click="uploadFile(scope.row)"
                  type="text"
                  style="margin-left: 10px"
                  v-if="scope.row.fileName"
                >
                  替换
                </el-button>
              </div>
              <div style="color:red;cursor: pointer;" @click="handlePreviewFile(scope.row)" v-else>
                {{ scope.row.fileName }}
              </div>
            </template>
          </el-table-column>
          <el-table-column width="150" align="left" label="所在单位">
            <template slot-scope="scope">
              <span>{{ scope.row.companyName }}</span>
            </template>
          </el-table-column>
          <el-table-column width="150" align="left" label="部门">
            <template slot-scope="scope">
              <span style="text-align: left; display: flex">{{
                scope.row.deptName
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            width="150"
            align="center"
            show-overflow-tooltip
            label="部门备注"
          >
            <template slot-scope="scope">
              <el-input
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
                placeholder="请输入"
                v-model="scope.row.deptRemark"
              ></el-input>
              <span v-else>
                {{ scope.row.deptRemark }}
              </span>
            </template>
          </el-table-column>
          <!-- <el-table-column width="150" label="专业">
            <template slot-scope="scope">
              <el-input
                v-if="process.authorizeFlag == '2'"
                placeholder="请输入"
                v-model="scope.row.specialty"
              ></el-input>
              <span v-else>
                {{ scope.row.specialty }}
              </span>
            </template>
          </el-table-column> -->
          <el-table-column width="150" align="center" label="出生年月">
            <template slot-scope="scope">
              <span>{{ scope.row.birthday }}</span>
            </template>
          </el-table-column>
          <el-table-column width="150" align="center" label="参加工作时间">
            <template slot-scope="scope">
              <span>{{ scope.row.joinInWorkDay }}</span>
            </template>
          </el-table-column>
          <el-table-column
            :render-header="renderHeader"
            width="180"
            align="center"
            label="入党时间"
          >
            <template slot-scope="scope">
              <el-date-picker
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
                style="width: 100%"
                :picker-options="pickerOptions"
                v-model="scope.row.joinInPartDay"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
              <span v-else>
                {{ scope.row.joinInPartDay }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            :render-header="renderHeader"
            width="150"
            align="center"
            label="是否有一线工作经历"
          >
            <template slot-scope="scope">
              <el-select
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
                v-model="scope.row.frontlineWorkExperience"
              >
                <el-option label="是" :value="1"></el-option>
                <el-option label="否" :value="0"></el-option>
              </el-select>
              <span v-else>
                {{ scope.row.frontlineWorkExperience == 1 ? "是" : "否" }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :render-header="renderHeader" width="150" align="center" label="是否参加过巡察">
            <template slot-scope="scope">
              <el-select
                v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
                v-model="scope.row.inspectionExperience"
              >
                <el-option label="是" value="1"></el-option>
                <el-option label="否" value="0"></el-option>
              </el-select>
              <span v-else>
                {{ scope.row.inspectionExperience == 1 ? "是" : "否" }}
              </span>
            </template>
          </el-table-column>

          <el-table-column width="220" align="center" label="巡察年份">
            <template slot-scope="scope">
              <el-select
              v-if="
                  process.curStepName != '部门已推荐' &&
                  process.authorizeFlag == '2'
                "
                multiple
                v-model="scope.row.inspectionYear"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in yearData"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <span v-else>
                {{ scope.row.inspectionYear.length?scope.row.inspectionYear.join(","):'' }}
              </span>
            </template>
          </el-table-column>

          <el-table-column
            width="150"
            align="center"
            show-overflow-tooltip
            label="OA邮箱"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.email }}</span>
            </template>
          </el-table-column>
          <el-table-column width="150" align="center" label="手机号码">
            <template slot-scope="scope">
              <span>{{ scope.row.telephone }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="
              process.curStepName != '部门已推荐' &&
              process.authorizeFlag == '2'
            "
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="delItem(scope.$index)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- <all-flow-step :allFlowStep="process.allFlowStep"></all-flow-step> -->
      <deep-btn
        ref="dbRef"
        :businessKey="id"
        :todoId="todoId"
        isTodo
        isBusiness
        @initProcess="initProcess"
        type="3"
      >
        <span
          slot="btns"
          v-if="
            process.curStepName != '部门已推荐' && process.authorizeFlag == '2'
          "
        >
          <el-button type="primary" @click="transferOper">转办</el-button>
          <el-button type="primary" @click="submitTemporary">暂存</el-button>
          <el-button type="primary" @click="applyOper">提交</el-button>
        </span>
        <span slot="btns" v-if="form.processStatus == '20' && process.authorizeFlag == '2'">
          <el-button type="primary" @click="applyOper1">审核</el-button>
        </span>
        <span slot="btns" v-if="form.companyName == '省公司' && form.processStatus == '30' && process.authorizeFlag == '2'">
          <el-button
                type="primary"
                @click="confirmInterview"
                style="margin-right: 10px"
                >确认入库</el-button
              >
        </span>
      </deep-btn>
      <transfer-process
        ref="transferRef"
        title="转办"
        :taskId="process.curtaskId"
        :dialogVisible="transferProcessShow"
        @close="close"
      ></transfer-process>

      <applyForm
      v-if="applyOpen.open"
      :dialogVisible="applyOpen.open"
      :type="applyOpen.type"
      :obj="applyOpen.obj"
      @close="applyClose"
    ></applyForm>

      <complete-process
      title="提交"
        ref="completeRef"
        :preUserName="process.preUserName"
        :taskId="process.curtaskId"
        :dialogVisible="applyShow"
        isBusiness
        @handleData="submit"
        @close="applyShow = false"
      ></complete-process>
      <!-- <div class="dialog-footer">
        <el-button>取 消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </div> -->

      <el-dialog
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
          <el-button type="primary" @click="confirm" :disabled="isConfirm">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <rs-form :obj="rsFormObj" @close="applyClose"></rs-form>
  </div>
</template>
<script>
import {
  getDeptById,
  getTodoInfo,
  getCanRecommendList,
  finishFormTask,
  temporarySave,
  downRecommendPeople
} from "@/api/talent/dept";

import { downloadZIP, previewFile,downloadZipFile } from "@/utils/uploadOperate";

import { downloadFile,checkWareHousing } from "@/api/talent/recommendSchedule";
import { parseTime } from "@/utils/ruoyi";
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
import { getDicts } from "@/api/system/dict/data";
import deepBtn from "@/components/WorkFlow/deepBtn";
import transferProcess from "@/components/WorkFlow/transferProcess";
import completeProcess from "@/components/WorkFlow/completeProcess";
import applyForm from "@/views/talent/recommend/applyForm";
import rsForm from "@/views/talent/recommendSchedule/rsForm";
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
    transferProcess,
    completeProcess,
    applyForm,
    rsForm
  },
  created() {
    this.id = this.$route.query.id;
    this.todoId = this.$route.query.todoInfoId;
    this.init();
  },
  computed: {
    total() {
      let count = 0;
      if (this.form.fieldList) {
        this.form.fieldList.forEach((res) => {
          count += res.personNumber;
        });
      }
      return count;
    },
  },
  data() {
    return {
      applyOpen: {
        open: false,
        type: "1",
      },
      rsFormObj: {
        open: false,
      },
      yearData: [],
      applyShow: false,
      transferProcessShow: false,
      //用于判断是否有权限暂存
      isShowUpload: false,
      process: { allFlowStep: [] },
      pickerOptions: {
        //控制时间范围
        disabledDate(time) {
          return time.getTime() > Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      dialogVisible: false,
      dialogObj: {},
      loading: false,
      id: "",
      isConfirm:false,
      todoId: "",
      userList: [],
      options: [],
      form: {},
      file: null,
      talentData: [],
      collectDicts: [], //收集字典
      sPageSize: 10,
      sPageNo: 1,
    };
  },
  methods: {
    //检查是否可以入库
    confirmInterview() {
      checkWareHousing({
        ids: this.form.id,
        companyType:  2 ,
      }).then((res) => {
        if (res.code == 200) {
          this.rsFormObj.id = this.form.colletionId;
          this.rsFormObj.collectionType = this.form.collectionType;
          this.rsFormObj.ids = this.form.id;
          
          this.rsFormObj.companyIds = this.form.companyId;
          this.rsFormObj.companyType =  2;

          this.rsFormObj.title = this.form.title;
          this.rsFormObj.orderNumber = this.form.orderNumber;
          this.rsFormObj.startInspectTime = this.form.startInspectTime;
          this.rsFormObj.endInspectTime = this.form.endInspectTime;
          this.rsFormObj.collectionEndDate = this.form.collectionEndDate;
          this.rsFormObj.open = true;
        }
      });
    },
    applyOper1() {
      this.applyOpen.obj = this.form;
      this.applyOpen.obj.title = this.form.title;
      this.applyOpen.type = "1";
      this.applyOpen.open = true;
     },
    //遍历年份数组
    getYears() {
      let arr = [];
      let lastYear = Number(String(new Date().getFullYear()).substring(2, 4));
      for (let i = 0; i <= lastYear-9; i++) {
        arr.push({
          label: `${i + 2009}年`,
          value: `${i + 2009}`,
        });
      }
      this.yearData = arr.reverse();
    },
    renderHeader(h, { column }) {
      console.log(column);
      //自定义表头加上必填符号
      return h(
        "div",
        {
          style: column,
        },
        [
          h("span", { style: { color: "red" } }, "*"),
          h("span", {}, column.label),
        ]
      );
    },
    initProcess(res) {
      if (res.allFlowStep.length) {
        let tempObj = res.allFlowStep[1];
        res.preUserName = tempObj.todoUserLoginId;
        res.preUserId = tempObj.historicTaskInstance.assignee;
      }
      this.process = res;
    },
    close(e) {
      this.transferProcessShow = false;
      if (e) {
        location.reload();
      }
    },
    check() { 
      if (this.talentData.length == 0) {
        this.$message.error("请推荐人才");
        return;
      }
      let errorArr = [];
      this.talentData.forEach((res) => {
        if (!res.userId) {
          errorArr.push("请选择推荐人");
        }
        if (!res.fileKey) {
          errorArr.push("请上传推荐人附件");
        }
        if (!res.joinInPartDay) {
          errorArr.push("请选择推荐人入党时间");
        }
        if (!res.fieldType) {
          errorArr.push("请选择专家类型");
        }
        if(this.talentData.filter(i => i.userId == res.userId).length > 1){
          errorArr.push("推荐人不能重复");
        }
        if (
          res.frontlineWorkExperience !== 0 &&
          res.frontlineWorkExperience !== 1
        ) {
          errorArr.push("请选择推荐人是否有一线工作经历");
        }

        if (
          res.inspectionExperience !== "0" &&
          res.inspectionExperience !== "1"
        ) {
          errorArr.push("请选择推荐人是否有巡察经历");
        }
      });
      if (errorArr.length) {
        let tempErrorArr = [];
        errorArr.forEach((item) => {
          if (tempErrorArr.indexOf(item) === -1) {
            tempErrorArr.push(item);
          }
        });
        this.$message.error(tempErrorArr.join("、"));
        return false;
      }
      return true;
    },
    applyOper() {
      let result = this.check();
      if (!result) { 
        return;
      }
      this.applyShow = true;
      this.$refs["completeRef"].init(this.process);
    },
    transferOper() {
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    submitTemporary() {
      if (this.talentData.length == 0) {
        this.$message.error("请推荐人才");
        return;
      }
      let errorArr = [];
      let tempArr = JSON.parse(JSON.stringify(this.talentData));
      tempArr.forEach((res) => {
        if (!res.userId) {
          errorArr.push("请选择推荐人");
        }
        if(tempArr.filter(i => i.userId == res.userId).length > 1){
          errorArr.push("推荐人不能重复");
        }
        if (res.inspectionYear) {
          res.inspectionYear = res.inspectionYear.join(",");
        } else {
          res.inspectionYear = "";
        }
      });
      if (errorArr.length) {
        let tempErrorArr = [];
        errorArr.forEach((item) => {
          if (tempErrorArr.indexOf(item) === -1) {
            tempErrorArr.push(item);
          }
        });
        this.$message.error(tempErrorArr.join("、"));
        return false;
      }
      this.$confirm("确认提交暂存吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        temporarySave({
          colletionId: this.form.colletionId,
          id: this.form.id,
          deptRecommendList: tempArr,
        }).then((res) => {
          if (res.code == 200) {
            this.$message.success("提交成功");
          } else {
            this.$message.error(res.message);
          }
        });
      });
    },
    //下载
    downloadFile(row) {
      if (this.process.curStepName == '部门已推荐') {
        downRecommendPeople({
          colletionId: this.form.colletionId,
          companyId: this.form.companyId,
        }).then(res => { 
          this.download(res.msg);
        })
        downloadZIP(this.talentData.map(res => res.fileKey),3);
      } else { 
        downloadFile().then((res) => {
          this.$message.success("下载成功");
        });
      }
    },
    //预览
    handlePreviewFile(row) {
      previewFile(row.fileKey).then((res) => {
        this.$message.success("预览成功");
      });
    },
    handleVisibleChange() {
      this.sPageSize = 10;
      this.sPageNo = 1;
    },
    //打开文件上传清楚之前的记录
    initUpload() {
      this.isConfirm = true;
      this.isShowUpload = true;
    },
    //用户下拉分页加载
    loadmore() {
      this.options = this.userList.slice(0, this.sPageSize * ++this.sPageNo);
    },
    filterDate(val) {
      return parseTime(val, "{y}-{m}-{d}");
    },
    submit(e) {
      //获取上一步处理人id
      let preUserId = this.process.preUserId;
      const loading = this.openLoading();
      let tempArr = JSON.parse(JSON.stringify(this.talentData));
      tempArr.forEach((res) => {
        if (res.inspectionYear) {
          res.inspectionYear = res.inspectionYear.join(",");
        } else {
          res.inspectionYear = "";
        }
      });
      finishFormTask(this.$refs.dbRef.process.curtaskId, {
        colletionId: this.form.colletionId,
        id: this.form.id,
        deptRecommendList: tempArr,
        variables: JSON.stringify({
          btnMessage: "提交",
          message: e.nextStep,
          nextDealUser: preUserId,
          option: e.option,
        }),
      }).then((res) => {
        if (res.code == 200) {
          this.$message.success("提交成功!");
          loading.close();
          location.reload();
        }
      });
    },
    uploadFile(row) {
      this.file = null;
      this.dialogObj = row;
      this.dialogVisible = true;
    },
    confirm() {
      if (!this.file) {
        this.$message.warning("请上传文件!");
        return;
      }
      this.dialogObj.fileKey = this.file.fileId;
      this.dialogObj.fileName = this.file.fileName;
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    //成功回调
    onFileSuccess(file, files, fileList) {
      this.file = file.result.data;
      this.isConfirm = false;
    },
    //移除回调
    onFileRemoved(file, files, fileList) {
      this.file = null;
      this.dialogObj.fileKey = "";
      this.dialogObj.fileName = "";
    },
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          getCanRecommendList({
            userName: query,
            companyId: this.form.companyId,
            deptId: this.form.deptId,
          }).then((res) => {
            if (res.code == 200) {
              this.userList = res.data;
              this.options = res.data.slice(0, this.sPageSize);
              this.loading = false;
            }
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    handleSelectUser(row) {
      row.userId = row.user.userId;
      row.userName = row.user.userName;

      row.companyId = row.user.companyId;
      row.companyName = row.user.companyName;
      row.deptId = row.user.departmentId;
      row.deptName = row.user.departmentName;
      row.email = row.user.email;
      row.employee = row.user.employee;
      row.telephone = row.user.telephone;
      row.birthday = this.filterDate(row.user.userBirthday);
      row.joinInWorkDay = this.filterDate(row.user.userJoinInWorkDate);
      row.userReligion = row.user.userReligion;
      row.userReligionName = row.user.userReligionName;
      row.inspectionYear = row.user.userInspectionYear;
    },
    handleSelectField(row) {
      row.fieldType = row.field.fieldType;
      row.fieldTypeName = row.field.fieldTypeName;
    },
    init() {
      this.getYears();
      this.getDetail();
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
      });
    },
    //获取详情
    getDetail() {
      getDeptById(this.id).then((res) => {
        if (res.code == 200) {
          if (res.data.deptRecommendList.length) {
            res.data.deptRecommendList.forEach((item) => {
              item.inspectionYear = item.inspectionYear.split(",");
              item.user = {
                userId: item.userId,
                userName: item.userName,
              };
              item.field = {
                fieldType: item.fieldType,
                fieldTypeName: item.fieldTypeName,
              };
              item.birthday = this.filterDate(item.birthday);
              item.joinInPartDay = this.filterDate(item.joinInPartDay);
              item.joinInWorkDay = this.filterDate(item.joinInWorkDay);
              this.options.push(item.user);
            });
            this.talentData = res.data.deptRecommendList;
          }
          getTodoInfo({
            id: res.data.colletionId,
            companyId: res.data.companyId,
          }).then((item) => {
            if (item.code == 200) {
              this.form = res.data;
              res.data.fieldList = res.data.fieldList.filter(e => e.personNumber>0);
              this.form = {
                ...res.data,
                companyId: res.data.companyId,
                companyName:res.data.companyName,
                title: item.data.title,
                companyName: item.data.companyName,
                collectionType: item.data.collectionType,
                endInspectTime: item.data.endInspectTime,
                startInspectTime: item.data.startInspectTime,
                joinPartDate: item.data.joinPartDate,
                joinWorkDate: item.data.joinWorkDate,
                collectionEndDate: item.data.collectionEndDate,
                remark: item.data.remark,
              };
            }
          });
        }
      });
    },
    addItem() {
      this.talentData.push({
        deptRemark: "",
        frontlineWorkExperience: "",
        joinInWorkDay: "",
        user: {},
        fileKey: "",
        fileName: "",
        inspectionYear: "",
        inspectionExperience: "",
      });
    },
    delItem(i) {
      this.talentData.splice(i, 1);
    },
    handleClose() {
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    applyClose(e) { 
      this.applyOpen.open = false;
      this.rsFormObj.open = false;
      if (typeof (e) == "number") { 
        location.reload();
      }
    }
  },
};
</script>
<style lang="scss" scoped>
.dialog-context {
  padding: 15px;
}
.dialog-footer {
  text-align: center;
}
/deep/.el-table__empty-block {
  width: auto !important;
}
.el-form {
  /deep/.el-input {
    width: 100%;
  }
  /deep/.el-select {
    width: 100%;
  }
}
/deep/.el-radio-group {
  display: flex;
  height: 36px;
  align-items: center;
}
/deep/.el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
.dialog-context .context-row .operation {
  position: absolute;
  right: 0;
  top: -30px;
  margin: 10px 0px;
  text-align: right;
  padding-right: 40px;
  z-index: 9;
}
</style>
