<template>
  <div class="ai_dialog">
    <el-dialog :visible.sync="dialogVisible" width="1100px" :close-on-click-modal="false" :before-close="handleClose"
      custom-class="ai_mainLog" :append-to-body="true" @open="handleOpen">
      <div class="title" slot="title">
        <span>{{row.type == 1 ? '上传文件':'查看详情'}}</span>
      </div>
      <img class="background" src="~@/assets/images/ai/BG.png" alt="" />
      <div class="table_box" v-if="dialogVisible" style="height: 74vh">
        <el-scrollbar style="height: 80%">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="巡察轮次：" prop="xunchaLunciId">
                  <el-select v-model="queryParams.xunchaLunciId" placeholder="请选择巡察轮次" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handleLunciChange">
                    <el-option v-for="dict in lunciOptions" :key="dict.lunciId" :label="dict.lunciName"
                      :value="dict.lunciId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察年份：" prop="year">
                  <el-date-picker :clearable="false" size="small" popper-class="ai_popper"
                    style="width:205px;border:none !important;background:none !important" v-model="queryParams.year"
                    format="yyyy" :popper-append-to-body="false" value-format="yyyy" type="year" placeholder="请选择巡察年份"
                    @change="handleYearChange" :picker-options="pickerOptions">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="巡察批次：" prop="xunchaPiciId">
                  <el-select v-model="queryParams.xunchaPiciId" placeholder="请选择巡察批次" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handlePiciChange">
                    <el-option v-for="dict in piciOptions" :key="dict.piciId" :label="dict.piciName"
                      :value="dict.piciId" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="巡察任务：" prop="taskId">
                  <el-select v-model="queryParams.taskId" placeholder="请选择巡察任务" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handleTaskChange">
                    <el-option v-for="dict in taskOptions" :key="dict.taskId" :label="dict.taskName"
                      :value="dict.taskId" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="被巡察单位：" prop="companyName">
                  <el-select v-model="queryParams.companyName" placeholder="请选择被巡察单位" size="small"
                    style="width:205px;border:none !important;background:none !important" :popper-append-to-body="false"
                    @change="handleCompanyName">
                    <el-option v-for="dict in companyOptions" :key="dict.orgId" :label="dict.orgName"
                      :value="dict.orgName" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="比对资源：">
                  <div class="flex">
                    <span style="color: #fff">{{ row.comparisonName }}</span>
                    <img style="width: 20px" src="~@/assets/images/ai/question-circle.png" alt="" />
                    <el-button class="btn_primary download_btn" icon="el-icon-download" @click="downloadTmplate">
                      文件模板下载
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="dialog-title">
            <span class="line"></span><span>上传结果</span>
          </div>
          <div>
            <el-button class="btn_primary" icon="el-icon-plus" @click="openLoad()"
              v-if="this.queryParams.companyName && row.type == 1 ">文件上传</el-button>
            <el-button class="nomal" @click="openTips('all')" :disabled="single?true:false">批量删除</el-button>
            <el-button class="nomal" @click="oneClick" :disabled="tableData.length>0?false:true">一键删除</el-button>
          </div>
          <div class="flex msg_box" v-show="showMsg">
            <img class="icon" src="~@/assets/images/ai/Icon.png" alt="" />
            <span>{{ '文件解析完成。本次共上传'+ (Number(recordData.errorNumber) + Number(recordData.successNumber))+'条数据，'}}</span>
            <span></span>
            <span>{{ Number(recordData.successNumber)+'条数据上传成功，'}}</span>
            <span>{{ Number(recordData.errorNumber) +'条数据上传失败'}}</span>
            <img class="icon cancel" src="~@/assets/images/ai/cancel-icon.png" alt="" />
          </div>

          <el-scrollbar class="table_scrollbar">
            <el-table stripe header-cell-class-name="tableHeaderClass" :data="tableData" max-height="300"
              @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="left" />
              <el-table-column label="序号" align="center" width="50" type="index"></el-table-column>
              <el-table-column v-for="item in row.tableItems" :label="item.label" :prop="item.prop" :key="item.prop"
                min-width="150" :width="item.width ? item.width : ''" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="item.isDate">{{parseTime(scope.row[item.prop], "{y}-{m}-{d} {h}:{i}:{s}")}}</span>
                  <span v-else>{{ scope.row[item.prop] }}</span>
                </template>
              </el-table-column>
              <el-table-column label="上传时间" align="center" min-width="150" prop="createdTime">
                <template slot-scope="scope">
                  <span>{{parseTime(scope.row.createdTime, "{y}-{m}-{d} {h}:{i}:{s}")}}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" :width="row.dataKey=='meetingLength'?'100':'50'">
                <template slot-scope="scope">
                  <!-- 议题与关键字特有的修改按钮 -->
                  <el-button size="mini" v-if="row.dataKey=='meetingLength'" @click="openSubject(scope.row)"
                    type="text">修改
                  </el-button>
                  <el-button size="mini" @click="openTips(scope.row)" type="text">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-scrollbar>
        </el-scrollbar>
        <div class="pageBox">
          <pagination :total="Number(total)" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />
        </div>
      </div>
      <div v-if="row.type" slot="footer">
        <el-button class="btn_primary" @click="submit">确 定</el-button>
        <el-button class="nomal" @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 上传文件 -->
    <el-dialog :visible.sync="upload.open" width="400px" :append-to-body="true" :modal-append-to-body="false">
      <div class="title" slot="title">
        <span>上传文件</span>
      </div>
      <img style="width: 460px; height: 460px" class="background" src="~@/assets/images/ai/BG.png" alt="" />
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url"
        :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess"
        :auto-upload="false" :data="upload.params" drag v-if="upload.open">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button class="btn_primary" @click="submitFileForm">确 定</el-button>
        <el-button class="nomal" @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 批量删除 -->
    <el-dialog :title="tipTitle" top="20vh" :visible.sync="tipOpen" append-to-body width="20%" :show-close="true"
      :closeOnClickModal="true" class="tipBox">
      <span>{{ tipText }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tipOpen = false" class="btn1">取 消</el-button>
        <el-button type="primary" @click="deleteSelect" class="btn2">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 一键删除 -->
    <el-dialog :title="oneClickTitle" top="20vh" :visible.sync="oneClickShow" append-to-body width="20%"
      :show-close="true" :closeOnClickModal="true" class="tipBox">
      <span>{{ oneClickText }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="oneClickShow = false" class="btn1">取 消</el-button>
        <el-button type="primary" @click="oneClickDelete" class="btn2">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改关键字 -->
    <el-dialog :visible.sync="subjectVisible" width="1100px" :close-on-click-modal="false" custom-class="ai_mainLog"
      :append-to-body="true">
      <div class="title" slot="title">
        <span>修改关键字</span>
      </div>
      <img class="background" src="~@/assets/images/ai/BG.png" alt="" />
      <div class="table_box tanc" style="height: 74vh">
        <el-scrollbar style="height: 90%">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="140px">
            <div class="dialog-title">
              <span class="line"></span><span>议题信息</span>
            </div>
            <el-row>
              <el-col :span="24">
                <el-form-item label="省公司公文：" prop="companyDispatch" class="width98">
                  <el-input v-model="keyArray.companyDispatch" type="textarea" rows="4" readonly resize='none'>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="下发部门：" prop="issueDept" class="width98">
                  <el-input v-model="keyArray.issueDept" type="text" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="下发时间：" prop="issueDate">
                  <el-input v-model="keyArray.issueDate" type="text" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="省公司联系人：" prop="companyLinkman">
                  <el-input v-model="keyArray.companyLinkman" type="text" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="学习要求和内容：" prop="studyContent" class="width98">
                  <el-input v-model="keyArray.studyContent" type="textarea" rows="4" readonly resize='none'></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="dialog-title">
            <span class="line"></span><span>关键字信息</span>
          </div>
          <div>
            <el-button class="nomal margb20" @click="handAdd" :disabled="false">添加关键字</el-button>
          </div>
          <el-row>
            <!-- <el-col :span="12">
                <el-form-item label="关键字1：" prop="">
                  <el-input v-model="keyArray.keywordList" type="text"></el-input>
                </el-form-item>
              </el-col> -->
            <el-form ref="forms" :model="keyArray" :rules="rules" label-width="140px">
              <el-col :span="12" v-for="(item,index) in keyArray.keywordList" :key="index">
                <el-col :span="21">
                  <el-form-item :label="'关键字'+(index+1)+'：'" prop="keywordList">
                    <el-input v-model="keyArray.keywordList[index]" type="text"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="3">
                  <i class="el-icon-error" style=" color: red;font-size: 20px;line-height: 2;" @click="handDelez(index)"
                    v-show="index!=0"></i>
                </el-col>

              </el-col>
            </el-form>
          </el-row>


        </el-scrollbar>
      </div>
      <div slot="footer">
        <el-button class="btn_primary" @click="onsubz">确 定</el-button>
        <el-button class="nomal" @click="subjectVisible=false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { getJSON, fileConfirmSave, getModelData, batchDelete } from "@/api/aiXuncha/aiBaseInfo.js";
import {
  queryPiciList,
  queryLunciList,
  queryCompanyList,
} from "@/api/xuncha/common/base";
import { listXunchaTaskInfo2, queryInfoByTaskId } from "@/api/xuncha/XunchaTaskInfo";
import { getStartEndYear, AiMeetingEdit } from "@/api/xuncha/XunchaLunciManage";
import { queryTaskUnit } from "@/api/xuncha/archives/xunchaProcessApply";

export default {
  props: {
    row: {
      type: Object,
    },
    dialogVisible: {
      type: Boolean,
      default: false,
    },
  },
  data () {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      queryParams: {
        companyName: null,
        xunchaLunciId: null,
        year: null,
        xunchaPiciId: null,
        taskId: null,
        pageSize: 10,
        pageNum: 1,
      },
      total: 0,
      showMsg: false,
      loading: null,
      tipOpen: false,
      subjectVisible: false,
      tipTitle: "标题",
      tipText: "内容",
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url:
          CONFIG.url + process.env.VUE_APP_BASE_API + "/aiBaseInfo/importData",
        params: {},
      },
      lunciOptions: [],
      piciOptions: [],
      companyOptions: [],
      taskOptions: [],
      tableData: [],
      ids: [],
      endYear: "3000",
      startYear: "2000",
      pickerOptions: {
        //控制时间范围
        disabledDate: (time) => {
          return time.getFullYear() > this.endYear || time.getFullYear() < this.startYear
        },
      },
      recordData: {
        recordId: '',
      },
      oneClickShow: false,
      oneClickTitle: '标题',
      oneClickText: '内容',
      //关键字弹窗
      keyArray: {
        option: "《关于组织参加南瓜树考试",
        deptName: "南瓜树",
        time: "2022年3月28日",
        name: "yabushan",
        context: "内容",

      },

      rules: {},
    };
  },
  created () {
    this.selectDataInit();
  },
  watch: {
    // row: {
    //   handler (nval, oval) {
    //     this.selectDataInit();
    //   },
    //   deep: true,
    // },
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map((item) => item[this.row.idLabel]);
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handAdd () {
      if (this.keyArray.keywordList.length <= 9) {
        this.keyArray.keywordList.push("")
      } else {
        this.$message.warning("最多添加10个关键字")
      }
    },
    handDelez (index) {
      this.keyArray.keywordList.splice(index, 1)
    },
    onsubz () {
      let type = true;
      let that = this;
      this.keyArray.keywordList.forEach(item => {
        if (item == "") {
          type = false
          return
        }
      })
      if (type) {
        AiMeetingEdit(this.keyArray).then(res => {
          this.subjectVisible = false;
          this.getList();
          this.$message.success("添加修改成功")
        })
      } else {
        this.$message.warning("关键字不能为空")
      }

    },
    handleOpen () {
      this.queryParams = { ...JSON.parse(JSON.stringify(this.queryParams)), ...JSON.parse(JSON.stringify(this.row.queryParams)) };
      if (this.row.type == "0") {
        this.getList();
      }
      this.selectDataInit();
    },
    // 初次加载页面，下拉框数据4
    selectDataInit () {
      let that = this;
      let queryP = JSON.parse(JSON.stringify(that.queryParams));
      console.log("重新加载------------------1")
      queryLunciList().then((res) => {
        that.lunciOptions = res.data;
      });
      listXunchaTaskInfo2().then((res) => {
        that.taskOptions = res.data;

        that.queryParams.taskId = queryP.taskId || res.data[0].taskId;
        queryTaskUnit({ taskId: that.queryParams.taskId }).then((res) => {
          that.companyOptions = res.data;
          that.queryParams.companyName = queryP.companyName || res.data[0].orgName;
          // 回显任务对应的批次，轮次，年份
          queryInfoByTaskId(that.queryParams.taskId).then(res => {
            that.queryParams.xunchaLunciId = queryP.xunchaLunciId || res.data.lunciId;
            that.queryParams.xunchaPiciId = queryP.xunchaPiciId || res.data.piciId;
            that.queryParams.year = queryP.year || res.data.xunchaYear;

            that.taskOptions = that.taskOptions.filter(item => item.piciId == that.queryParams.xunchaPiciId);
            // 加载【巡察批次】下拉框数据
            queryPiciList({ year: that.queryParams.year }).then((res) => {
              that.piciOptions = res.data;
            });
            // 加载巡察年份范围
            getStartEndYear(that.queryParams.xunchaLunciId).then(res => {
              that.startYear = res.data.startYear;
              that.endYear = res.data.endYear;
            })
          })
          // this.queryGetModelData();
        });
      });
    },
    // 切换巡察轮次
    handleLunciChange (val) {
      this.queryParams.year = null;
      this.queryParams.xunchaPiciId = null;
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      // 加载巡察年份范围
      getStartEndYear(this.queryParams.xunchaLunciId).then(res => {
        this.startYear = res.data.startYear;
        this.endYear = res.data.endYear;
      })
      // 清空表格数据
      this.tableData = []
      this.total = 0
      this.piciOptions = []
      this.taskOptions = []
      this.companyOptions = []
    },
    // 切换巡察年份
    handleYearChange (value) {
      let param = {
        year: value,
      };
      this.queryParams.xunchaPiciId = null;
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      queryPiciList(param).then((res) => {
        this.piciOptions = res.data;
      });
      // 清空表格数据
      this.tableData = []
      this.total = 0
      this.piciOptions = []
      this.taskOptions = []
      this.companyOptions = []
    },
    // 切换巡察批次
    handlePiciChange (value) {
      let param = {
        piciId: value,
      };
      this.queryParams.taskId = null;
      this.queryParams.companyName = null;
      listXunchaTaskInfo2(param).then((res) => {
        this.taskOptions = res.data;
      });
      // 清空表格数据
      this.tableData = []
      this.total = 0
      this.taskOptions = []
      this.companyOptions = []
    },
    // 切换巡察任务
    handleTaskChange () {
      this.queryParams.companyName = null;
      queryTaskUnit({ taskId: this.queryParams.taskId }).then((res) => {
        this.companyOptions = res.data;
      });
      // 清空表格数据
      this.tableData = []
      this.total = 0
      this.companyOptions = []
    },
    // 切换被巡察单位
    handleCompanyName () {
      this.getList()
    },
    // 获得上传结果列表数据
    getList () {
      let params = {}
      if (this.row.type == "0") {
        params = {
          queryTaskId: this.queryParams.taskId,
          queryCompanyName: this.queryParams.companyName,
          pageSize: this.queryParams.pageSize,
          pageNum: this.queryParams.pageNum,
        };
      } else {
        params = {
          pageSize: this.queryParams.pageSize,
          pageNum: this.queryParams.pageNum,
          recordId: this.recordData.recordId,
        };
      }
      this.row.list(params).then((res) => {
        this.tableData = res.rows;
        this.total = res.total;
      });
    },
    // 下载模板
    downloadTmplate () {
      getJSON(this.row.url + "/importTemplate").then((response) => {
        this.download(response.msg);
      });
    },
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
      this.loading = this.openLoading(null, 300000);
    },
    submitFileForm () {
      this.loading = this.openLoading(null, 300000);
      this.$refs.upload.submit();
      this.getList();
    },
    openLoad () {
      this.upload.open = true;
      this.upload.params = {
        companyName: this.queryParams.companyName,
        dataType: this.row.id,
        taskId: this.queryParams.taskId,
      };
    },
    // 删除
    onDel (row) {
      this.$confirm(`是否确定删除该数据项?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let key = this.row.idLabel;
          let params = {};
          params[key] = row[key];
          this.row.option(params).then(() => {
            this.getList();
            this.$message.success("删除成功");
          });
        })
        .catch(function () {
        });
    },
    openSubject (data) {
      console.log(data)
      this.subjectVisible = true
      this.keyArray = data;
    },
    openTips (data) {
      this.tipOpen = true;
      this.tipTitle = "提示";
      this.tipText = "是否确定删除所选数据项";
      if (data != 'all') {
        this.ids = [data.id]
      }
    },
    deleteSelect () {
      let key = this.row.idLabel;
      let params = {};
      params[key] = this.ids.join(",");
      this.row.option(params).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
        this.tipOpen = false;
      });
    },

    // 一键删除
    oneClick () {
      this.oneClickShow = true;
      this.oneClickTitle = "提示";
      this.oneClickText = "是否确定删除此表的所有数据";
    },
    oneClickDelete () {
      let param = {};
      if (this.row.type == "0") {
        param = {
          companyName: this.queryParams.companyName,
          dataType: this.row.id,
          taskId: this.queryParams.taskId,
        }
      } else {
        param = {
          recordId: this.recordData.recordId,
          dataType: this.row.id,
        }
      }
      this.loading = this.openLoading();
      batchDelete(param).then((res) => {
        this.loading.close();
        if (res.code == 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.warning(res.msg)
        }
        this.oneClickShow = false;
        this.getList();
      })
      console.log("param1111", param);
    },


    handleFileSuccess (res) {
      this.recordData = {};
      console.log(res)
      if (res.code == 200) {
        this.$message.success(res.msg);
        this.showMsg = true;
        this.recordData = res.data;
        this.loading.close();
        this.$refs.upload.clearFiles();
        this.upload.open = false;
        this.upload.isUploading = false;
        // this.queryGetModelData();
        this.getList();
      } else {
        this.recordData.recordId = '';
        this.$message.warning(res.msg);
        this.loading.close();
        this.$refs.upload.clearFiles();
        this.upload.open = false;
        this.upload.isUploading = false;
      }
    },
    submit () {
      fileConfirmSave(this.recordData.recordId).then((res) => {
        if (res.code == 200) {
          this.$message.success(res.msg);
        } else {
          this.$message.warning(res.msg);
        }
        this.$emit("close");
        this.showMsg = false;
        this.recordData = {};
        this.tableData = [];
      })
    },
    handleClose () {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.tableData = [];
      this.total = null;
      this.showMsg = false;
      this.recordData = {};
      this.$emit("close");
      this.$refs.upload.clearFiles();
    },
    queryGetModelData () {
      //不知道有什么用
      // getModelData(this.queryParams).then((res) => {
      //   this.dataCount = res[row.dataKey];
      // });
    },

    Errors (err) {
      let text = new Error(err);
      return text
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .el-table__empty-block::after {
  background-image: url(~@/assets/images/newNoData.png) !important;
}

/deep/ .is-scrolling-right::-webkit-scrollbar {
  background: #ee8308;
  border-radius: 2px;
  // width: 4px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 4px;
}

/deep/ .el-checkbox__inner {
  &:hover {
    border-color: #11d9ff;
  }
}

.el-button:focus,
.el-button:hover {
  color: #fff;
}

/deep/ .el-checkbox__input.is-indeterminate .el-checkbox__inner {
  background-color: #11d9ff;
  border-color: #11d9ff;
}

/deep/ .el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #11d9ff;
  border-color: #11d9ff;
}

/deep/ .el-checkbox__input.is-focus .el-checkbox__inner {
  border-color: #11d9ff;
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow-x: scroll;
}

.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0px !important;
}

/deep/ .el-table .el-table__header-wrapper th {
  background: linear-gradient(180deg, #041141 0%, #021d48 50%, #002259 100%);
  border-bottom: 1px solid #007eff !important;
  // opacity: 0.2;
}

/deep/ .el-table {
  background-color: transparent !important;
}

/deep/ .el-table td.el-table__cell {
  border: 0px;
}

/deep/ .el-table tr {
  background-color: transparent !important;
}

/deep/ .el-table th.el-table__cell.is-leaf {
  border: 0;
}

// /deep/ .el-table--striped .el-table__body tr.el-table__row--striped td.el-table__cell {
//   background: #111f48 !important;
// }

// /deep/ .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
//   background: #111f48 !important;
// }

/deep/ .el-dialog__header {
  .el-dialog__headerbtn {
    z-index: 100;

    &:hover .el-dialog__close {
      color: #46a1ff;
    }
  }
}

/deep/ .el-scrollbar__wrap {
  overflow-x: auto;
  height: 100%;
}

/deep/ .el-table {
  background-color: transparent !important;
}

/deep/ .table_scrollbar .el-scrollbar__wrap {
  margin-bottom: -30px !important;

  .el-scrollbar__view {
    white-space: nowrap;
    display: inline-block;
  }
}

/deep/ .is-horizontal {
  height: 8px;
  position: relative;
  z-index: 3;
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow: scroll;
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow: scroll;
}

/deep/ .ai_mainLog .el-dialog__body {
  overflow: hidden;
  min-height: 536px;
}

/deep/ .el-upload:focus .el-upload-dragger {
  border-color: #46a1ff;
}

/deep/ .el-upload-dragger {
  background: transparent !important;

  &:hover {
    border-color: #46a1ff;
  }

  .el-upload__text {
    color: #fff;
  }

  .el-upload__text em {
    color: #46a1ff;
  }
}

/deep/ .el-upload-list__item:hover {
  background: transparent !important;
  color: #46a1ff;

  .el-icon-document {
    color: #46a1ff;
  }
}

/deep/ .el-upload-list {
  min-height: 28px;
}

/deep/ .el-input {
  background: transparent !important;
}

/deep/ .el-input__inner {
  background: transparent !important;
  border-color: #ffffff15 !important;
  color: #fff;
}

.tipBox {
  /deep/ .el-dialog {
    width: 22rem !important;
    min-height: 14rem;
    padding: 1.3rem;
    color: #ffffff;
    background: transparent;
    background-image: url("~@/assets/images/ai/tipbg.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;

    .el-dialog__header {
      // padding: 0;
      box-shadow: none;
      text-align: center;

      .el-dialog__title {
        color: #ffffff;
        font-weight: 100;
      }

      .el-dialog__headerbtn {
        top: 1.3rem;
        right: 2rem;
      }

      .el-dialog__headerbtn .el-dialog__close {
        color: #fff;
      }

      // height: 1rem;
    }

    .el-dialog__body {
      color: #ffffff;
      font-weight: 100;
    }

    .el-dialog__footer {
      padding: 5px 20px !important;
      background: transparent;
      position: absolute;
      box-shadow: none;
      bottom: 2rem;
      right: 1rem;
    }
  }
}

.btn1 {
  background: transparent;
  color: #fff;
  border: 1px solid #ffffff40;
  padding: 0.4rem 0.8rem;
  font-size: 14px;
}

.btn2 {
  background: #007eff;
  color: #fff;
  border: 1px solid #007eff;
  padding: 0.4rem 0.8rem;
  font-size: 14px;
}

.flex {
  display: flex;
  align-items: center;
}

.download_btn {
  font-size: 12px;
  padding: 8px 14px;
  margin-left: 10px;
}

.msg_box {
  padding: 10px;
  // background: linear-gradient(270deg, #348cff 0%, #38a2ff 100%);
  box-shadow: inset 0px 0px 13px 0px rgba(64, 211, 255, 1);
  border-radius: 5px;
  border: 1px solid rgba(64, 213, 255, 0.65);
  position: relative;
  color: #fff;
  margin: 16px 0;

  .icon {
    width: 25px;
  }

  .cancel {
    position: absolute;
    right: 20px;
    cursor: pointer;
  }
}

.background {
  width: 105%;
  height: 110%;
  position: absolute;
  top: -32px;
  left: -32px;
  z-index: -1;
}

.btn_primary {
  background: linear-gradient(131deg, #46a1ff 0%, #007eff 100%);
  color: #fff;
  border: 0;
}

.nomal {
  background: transparent;
  color: #fff;

  &:hover {
    border-color: #7ebcfb;
  }
}

.title {
  color: #fff;
  position: relative;
  z-index: 10;
  font-size: 20px;
  font-weight: 100;
}

/deep/ .el-dialog {
  background: transparent;
}

/deep/ .el-dialog__header {
  position: relative;
}

/deep/ .el-dialog__footer {
  background: transparent;
}

.dialog-title {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 100;
  color: #fff;
  background: transparent;
  position: relative;
  margin-bottom: 16px;

  .line {
    background: #fff;
    height: 22px;
  }
}

/deep/.el-table tbody tr:hover>td {
  background-color: #3F9DFF70 !important;
}

/deep/ .el-table__body {
  width: auto !important;
}

/deep/ .el-table__body-wrapper {
  width: calc(100% - 18px);
}

/deep/::-webkit-scrollbar {
  width: 8px;
  height: 20px;
}

/deep/::-webkit-scrollbar-thumb {
  box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  background-color: rgba(144, 147, 153, 0.3);
  border-radius: 12px;
}

/deep/ .el-progress.el-progress--line {
  .el-progress-bar {
    display: none;
  }

  .el-progress__text {
    display: none;
  }
}

/deep/.el-button.is-disabled,
.el-button.is-disabled:hover {
  background: transparent;
}

.width98 {
  width: 98%;
}

/deep/ .tanc {
  .margb20 {
    margin-bottom: 20px;
  }

  .el-form-item__content {
    width: calc(99% - 140px);

    textarea {
      background: transparent;
      color: #fff;
      border-color: #ffffff15;
    }
  }
}
</style>
