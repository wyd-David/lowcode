<template>
  <div>
    <div class="app-main">
      <todo-bpm-header></todo-bpm-header>
      <div class="context-row">
        <!-- 被巡察单位评分 -->
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>待办信息</span>
            </div>
          </div>
          <div class="crad-content">
            <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
              <el-row :gutter="10">
                <el-col :span="24">
                  <el-form-item label="业务单号：" prop="applyCode">
                    <el-input v-model="form.applyCode" placeholder="" />
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="标题：" prop="title">
                    <el-input v-model="form.title" placeholder="" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="评分类型：" prop="examTypeName">
                    <el-input v-model="form.examTypeName" placeholder="" style="width: 280px" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="评分截止时间：" prop="examLastTime">
                    <el-input v-model="form.examLastTime" placeholder="" style="width: 280px" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="被考核组：" prop="groupName">
                    <el-input v-model="form.groupName" placeholder="" style="width: 280px" />
                    <el-button type="primary" :disabled="false" class="detail" @click="onGrounpDetail(form)">查看
                    </el-button>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="备注：" prop="examRemark">
                    <el-input v-model="form.examRemark" placeholder="请输入备注" type="textarea" :rows="4" />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>评分表</span>
            </div>
          </div>
          <div class="crad-content">
            <el-row :gutter="10" class="mb12 utlsBtn">
              <el-col :span="4">
                <el-button type="primary" size="mini" @click="handleExport">
                  <!-- v-hasPermi="['checkedAll:leadingRating:export']" -->
                  导出评分表
                </el-button>
              </el-col>
              <el-col :span="20" style="display: flex;justify-content: flex-end;" v-if="process.authorizeFlag == '1'">
                <el-tag type="danger">已处理</el-tag>
              </el-col>
            </el-row>
          </div>
          <div class="crad-content">
            <el-form ref="queryform" :model="form" label-width="120px" :disabled="form.ifSubmit == '1'">
              <el-table v-loading="loading" :data="form.examList" border show-summary :summary-method="getSummaries">
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="评分内容" align="center" prop="examContant" />
                <el-table-column label="*有/无" align="center" prop="ifHave" width="200">
                  <template slot-scope="scope">
                    <el-radio-group v-model="scope.row.ifHave">
                      <el-radio :label="'0'">无</el-radio>
                      <el-radio :label="'1'">有</el-radio>
                    </el-radio-group>
                  </template>
                </el-table-column>
                <el-table-column label="违规具体情形（有违规必填）" align="center" prop="examRemark">
                  <template slot-scope="scope">
                    <el-form-item label="" prop="examRemark" class="specialForm" :rules='{
                      required: scope.row.ifHave == "1" ? true : false,
                      message: "违规具体情形不能为空",
                      trigger: "change",
                      validator: (rule, value, callback) => {
                        if (scope.row.examRemark.length <= 0 && scope.row.ifHave == "1") {
                          callback(Errors("违规具体情形不能为空"))
                        } else {
                          callback()
                        }
                      }
                    }'>
                      <el-input v-model="scope.row.examRemark" placeholder="请输入" type="textarea" :rows="3"
                        maxlength="500" show-word-limit />
                    </el-form-item>

                  </template>
                </el-table-column>
              </el-table>
            </el-form>
          </div>
          <div class="crad-content btips">
            <div>评分参考：</div>
            <div>1.本表满分100分，出现任一种违规违纪情形，合计零分，考核总分权重占比为10%。</div>
            <div>2.此表请被巡察单位在巡察组工作结束一周内，以纸质版（盖章，填表人签字）报广东公司党委巡察工作办公室。</div>
          </div>
          <deep-btn ref="dbRef" :isTodo="true" :businessKey="id" :todoId="todoId" @initProcess="initProcess">
            <span slot="btns">
              <el-button type="primary" @click="storageForm"
                v-if="form.ifSubmit != '1' && process.authorizeFlag == '2'">暂
                存</el-button>
              <el-button type="primary" @click="transferForm"
                v-if="form.ifSubmit != '1' && process.authorizeFlag == '2'">
                转 办</el-button>
              <el-button type="primary" @click="submitForm" v-if="form.ifSubmit != '1' && process.authorizeFlag == '2'">
                提 交</el-button>
            </span>
          </deep-btn>
        </div>
        <transfer-process ref="transferRef" :taskId="process.curtaskId" :dialogVisible="transferProcessShow" type="1"
          isBusiness @handleData="handleAF" @close="transferProcessShow = false"></transfer-process>
        <!-- <SubmitIdx ref="siRef" processKey="talent_collection" stepName="领导审核" @close="cancel"></SubmitIdx> -->

        <patrol-group ref="PatrolGroup" :groupObject="groupObject" :dialogVisible="isOpenGroup"
          @close="isOpenGroup = false"></patrol-group>
      </div>
    </div>
  </div>
</template>
<script>
import {
  getExamContentByExamType,
  exportToExamOfficeToExam,
  getToExamOfficeToExam,
  saveToExamOfficeToExam,
  submitToExamOfficeToExam,
} from "@/api/checkedAll/checkedInfo";
import { transfer } from "@/api/talent/collection";
import { getDicts } from "@/api/system/dict/data";
import ApplyFooter from "@/components/ApplyFooter";
import SubmitIdx from "@/components/SubmitIdx";
import deepBtn from "@/components/WorkFlow/deepBtn";
import transferProcess from "@/components/WorkFlow/transferProcess";
import PatrolGroup from "@/components/PatrolGroup";
import { get } from "http";
export default {
  components: { ApplyFooter, SubmitIdx, deepBtn, transferProcess, PatrolGroup },
  created () {
    this.id = this.$route.query.id ? this.$route.query.id : '';
    this.groupId = this.$route.query.groupId ? this.$route.query.groupId : '';
    this.todoId = this.$route.query.todoInfoId ? this.$route.query.todoInfoId : '';

    // getExamContentByExamType({examTypeId:'3'}).then(res=>{
    //     this.tableData = res.data
    //     this.getDetail();
    // })
    this.getDetail();

  },
  data () {
    return {

      groupObject: {
        taskId: "",
        piciId: "",
      },
      isOpenGroup: false,
      id: "",
      groupId: "",
      todoId: "",
      //用于判断是否有权限
      process: { allFlowStep: [], curtaskId: "" },
      transferProcessShow: false,
      // 表单参数
      form: {

      },
      loading: false,
      total: 0,
      CompanyInfoList: [],
      activeName: "1",
      id: "",
      disabled: true,
      disabledwindow: false,
      false: false,
      applyUser: "",
      btnList: [],
      allFlowStep: [],
      companyList: [],
      nextUsers: [],
      tableData: [],
      formData: {
        title: "",
        collectionType: "",
        joinWorkDate: "",
        joinPartDate: "",
        startInspectTime: "",
        endInspectTime: "",
        datetime: [],
        collectionEndDate: "",
      },
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [], //tab字典
      collectDicts: [], //收集字典
    };
  },
  methods: {
    handleAF (handleData) {
      // dealUser
      let fm = new FormData();
      fm.append("todoId", this.todoId);
      fm.append("dealUser", handleData.nextDealUser);
      fm.append("applyId", this.id);
      transfer(fm).then(res => {
        if (res.code == 200) {
          this.$message.success("转办成功");
          window.location.reload()
        }
      });
    },
    Errors (err) {
      let text = new Error(err);
      return text
    },
    onGrounpDetail (row) {
      this.groupObject = {
        groupId: row.groupId ? row.groupId : ''
      }
      this.isOpenGroup = true
    },
    initProcess (res) {
      let tempObj = {};
      if (res.allFlowStep.length && res.backNextStepAndUser.length == 1) {
        for (let i = 1; i < res.allFlowStep.length; i++) {
          let item = res.allFlowStep[i];
          if (item.varialbes && item.varialbes.message == "转办") {
          } else {
            tempObj = item;
            break;
          }
        }
        res.preUserName = tempObj.todoUserLoginId;
        res.preUserId = tempObj.historicTaskInstance.assignee;
      }
      this.process = res;
    },
    //获取详情
    getDetail () {
      this.loading = true;
      getToExamOfficeToExam({ groupId: this.groupId }).then(res => {
        this.form = res.data;
        this.loading = false;
      })
    },
    /** 导入按钮操作 */
    handleClose () {
      this.$emit("close");
    },
    //暂存按钮
    storageForm () {
      console.log("开始暂存")
      let data = this.form;
      const loading = this.openLoading();
      saveToExamOfficeToExam(data).then(res => {
        this.$message({
          message: res.msg,
          type: 'success'
        });
        this.getDetail()
        loading.close();
      })
    },
    //转办
    transferForm () {
      console.log("转办")
      this.transferProcessShow = true;
      this.$refs["transferRef"].init(this.process);
    },
    /** 提交按钮 */
    submitForm () {
      let that = this;
      if (this.disabledwindow) {
        return
      }

      this.$refs["queryform"].validate(valid => {
        if (valid) {
          this.$confirm('是否确定提交？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
          }).then(() => {
            this.disabledwindow = true
            let data = that.form
            data.todoId = that.todoId
            const loading = that.openLoading();
            debugger
            submitToExamOfficeToExam(data).then(res => {
              that.$message.success(res.msg);
              // that.getDetail();
              that.disabledwindow = false;
              loading.close();
              window.location.reload()
            })
          }).catch(() => {

          });
        }
      });
    },
    /** 提交按钮 */
    // submitForm () {
    //   let that = this;
    //   if (this.disabled) {
    //     this.open = false;
    //     return
    //   }
    //   that.$refs.siRef.open("/checkedAll/checkInfo/getApplyInfo", checkedInfoList);
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       console.log(valid)
    //       let checkedInfoList = JSON.parse(JSON.stringify(that.checkedInfoList));
    //       that.originalList.forEach(item => {
    //         checkedInfoList.forEach(itm => {
    //           if (item.applyCode == itm.applyCode) {
    //             !item.fractionList && (item.fractionList = [])
    //             item.fractionList.push(itm)
    //           }
    //         })
    //       })
    //       that.$refs.siRef.open("/checkedAll/checkInfo/getApplyInfo", checkedInfoList);
    //     }
    //   });
    // },
    /** 导出按钮操作 */
    handleExport () {
      const queryParams = {
        todoId: this.todoId ? this.todoId : '',
        groupId: this.groupId ? this.groupId : '',
        year: this.form.year ? this.form.year : '',
      };
      this.$confirm('是否确认导出数据项?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(function () {
        return exportToExamOfficeToExam(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function () { });
    },
    getSummaries (param) {
      const { columns, data } = param;
      const sums = [];
      let num = this.form.examList ? this.form.examList.filter(item => item.ifHave == "1") : [];
      console.log(num)
      columns.forEach((column, index) => {
        if (index === 3) {
          sums[index] = '最终得分：' + (num.length > 0 ? '0' : '100');
          return;
        } else {
          sums[index] = '';
          return;
        }
      });

      return sums;
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    handleTabClick (tab) { },
    add (t) { },
    tableInput (e) {
      let count = 0;
      this.prefecturalTableData.forEach((item) => {
        item.fieldList.forEach((item1) => {
          count += Number(item1.personNumber) || 0;
        });
      });
      e.expertTotals = count;
    },
    // 文件上传中处理
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess (response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getDetail();
    },
    // 提交上传文件
    submitFileForm () {
      this.$refs.upload.submit();
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ input::-webkit-outer-spin-button,
/deep/ input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}

/deep/ input[type="number"] {
  -moz-appearance: textfield !important;
}

/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}

/deep/ .el-dialog__footer {
  text-align: center;
}

/deep/ .el-form-item__error {
  width: 280px;
}

.app-main {
  padding: 15px;
  background: #fff;

  .dialog-title {
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
      top: 10px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 30px;
      z-index: 9;
    }
  }
}

.app-footer {
  text-align: center;
  background: #fff;
  padding-bottom: 20px;
}

.detail {
  margin-left: 1rem;
}

.btips {
  color: #e14d3b;
  margin-top: 1rem;
  padding-top: 0;

  div {
    margin-bottom: 20px;
  }
}

/deep/ td.el-table_1_column_4.is-center.is-leaf.el-table__cell {
  text-align: right;
}

// .operations{
//     display: none;
// }
/deep/ .operations .dialog-title {
  display: none !important;
}

/deep/ .operations .context-row {
  display: none !important;
}

/deep/ .specialForm .el-form-item__content {
  margin-left: auto !important;
}

/deep/ .el-form-item__error {
  width: auto;
}
</style>