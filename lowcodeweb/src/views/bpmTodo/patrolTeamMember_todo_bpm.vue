<template>
  <div>
    <div class="app-main">
      <todo-bpm-header></todo-bpm-header>
      <div class="context-row">
        <!-- 组员评分页 -->
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
                    <el-input v-model="form.applyCode" placeholder="请输入标题" />
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="标题：" prop="title">
                    <el-input v-model="form.title" placeholder="请输入标题" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="评分类型：" prop="examTypeName">
                    <el-input v-model="form.examTypeName" placeholder="请输入评分" style="width: 280px" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="评分截止时间：" prop="examLastTime">
                    <el-input v-model="form.examLastTime" placeholder="请输入时间" style="width: 280px" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="被考核组：" prop="groupName">
                    <el-input v-model="form.groupName" placeholder="请输入时间" style="width: 280px" />
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
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
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
            <el-form ref="form" :model="form" label-width="120px" :disabled="form.ifSubmit == '1'">
              <el-table v-loading="loading" :data="proTeamList" border :header-cell-style="headCellstyle"
                :header-row-style="headsrowStyle">
                <el-table-column label="序号" align="center" type="index" width="55">
                  <el-table-column label="" align="center" type="index" width="55">

                  </el-table-column>
                </el-table-column>
                <el-table-column label="组员姓名" align="center">
                  <el-table-column label="" align="center" prop="userName">
                    <template slot="header" slot-scope="scope">
                      <p>评分内容与标准</p>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column align="center" v-for="(item, index) in tableData" :key="index"
                  :width="(Number(item.examContant ? item.examContant.length : 0) + item.examMaxScope ? item.examMaxScope.toString().length : 0 + 3) * 15">
                  <template slot="header" slot-scope="scope">
                    <!-- {{item}} -->
                    {{ item.scopeTitle }}（{{ item.mixScope }}%）
                  </template>
                  <el-table-column label="" align="center">
                    <template slot="header" slot-scope="scope">
                      {{ item.scopeContent }}
                    </template>
                    <template slot-scope="scope">
                      <el-form-item label="" prop="scope" class="specialForm" :rules='{
                        required: true,
                        trigger: "change",
                        validator: (rule, value, callback) => {
                          let reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                          let a = scope.row.examList[index]["examResult"];
                          // let examMaxScope = scope.row.examMaxScope.toString();
                          if (a.length == 0 || a == null || a == "") {
                            callback(Errors("评分不能为空"))
                          } else if (!reg.test(a)) {
                            callback(Errors("请输入正确格式的分数"));
                          } else {
                            // if (Number(scope.row.examList[index]["examMaxScope"]) < Number(a) || Number(a) < 0) {
                            if (10 < Number(a) || Number(a) < 0) {
                              callback(Errors("请输入范围内的分数"));
                            } else {
                              callback()
                            }
                          }
                        }
                      }'>
                        <!-- {{scope.row.examList[index]}} -->
                        <el-input v-model="scope.row.examList[index]['examResult']" placeholder="请输入" clearable min="0"
                          :max="item.examMaxScope" onkeyup="value=value.replace(/[^0-9.]/g,'')"
                          @blur="inpBlur(scope.row.examList, scope.$index)" />
                      </el-form-item>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="考评总分" align="center">
                  <el-table-column label="" align="center" width="105" prop="Allpoints">
                    <template slot="header" slot-scope="scope">
                      <p @click="onshow" v-if="!pageshow">展开标准 <i class="el-icon-arrow-down"></i></p>
                      <p @click="onshow" v-else>收起标准 <i class="el-icon-arrow-up"></i></p>
                    </template>
                    <template slot-scope="scope">
                      {{ scope.row.Allpoints  }}
                      <!-- | pointFilt -->
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="考评等级" align="center">
                  <el-table-column label="" align="center" width="105" prop="levelId" v-if="form.ifSubmit == '1'">
                    <template slot-scope="scope">
                      {{ scope.row.levelId | filterAllLevel(scoreLevelption) }}
                    </template>
                  </el-table-column>
                  <el-table-column label="" align="center" width="105" prop="level" v-else>
                    <template slot-scope="scope">
                      {{ scope.row.level }}
                    </template>
                  </el-table-column>
                </el-table-column>
              </el-table>
            </el-form>
          </div>
          <div class="crad-content btips">
            <div>评分参考：</div>
            <div>1.考评等级分为“优秀”、“优良”、“中等”、“及格”、“不及格”五个等级。原则上“优秀”比例不超过20%，“优良”比例不超过30%。</div>
            <div>2.考评等级按照考评合计分评定，参照标准：“优秀”为9-10分，“优良”为8-9分，“中等”为7-8分，“及格”为6-7分，“不及格”为0-6分。</div>
          </div>
          <!-- <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="">暂 存</el-button>
            <el-button type="primary" @click="submitForm">提 交</el-button>
          </div> -->
          <deep-btn ref="dbRef" :isTodo="true" :businessKey="id" :todoId="todoId" @initProcess="initProcess" type="3">
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
        <!-- <SubmitIdx ref="siRef" processKey="talent_collection" stepName="领导审核" @close="cancel"></SubmitIdx> -->
        <transfer-process ref="transferRef" :taskId="process.curtaskId" :dialogVisible="transferProcessShow" type="1" isBusiness @handleData="handleAF" 
          @close="transferProcessShow = false"></transfer-process>
        <patrol-group ref="PatrolGroup" :groupObject="groupObject" :dialogVisible="isOpenGroup"
          @close="isOpenGroup = false"></patrol-group>
      </div>
    </div>
  </div>
</template>
<script>
import {
  getExamOfGroupPre,
  getExamContentByExamType,
  exportExamOfGroupPre,
  saveExamOfGroupPre,
  submitExamOfGroupPre,
  getPersonNumber,
} from "@/api/checkedAll/checkedInfo";
import { getDicts } from "@/api/system/dict/data";
import { transfer } from "@/api/talent/collection";
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
    getExamContentByExamType({ examTypeId: '4' }).then(res => {
      this.tableData = res.data
      this.getDetail();
    })

    this.getDicts("score_level").then(res => {
      this.scoreLevelption = res.data ? res.data : [];
    })
    // this.getDetail()
  },
  data () {
    return {
      groupObject: {
        taskId: "",
        piciId: "",
      },
      isOpenGroup: false,
      heiH: '96px',
      pageshow: false,
      transferProcessShow: false,
      disabledwindow: false,
      process: { allFlowStep: [], curtaskId: "" },
      tableData: [],
      section: [],
      // 表单参数
      form: {
        title: null,
      },
      loading: false,
      total: 0,
      proTeamList: [],
      activeName: "1",
      id: "",
      disabled: true,
      false: false,
      applyUser: "",
      btnList: [],
      allFlowStep: [],
      companyList: [],
      nextUsers: [],
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
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
      scoreLevelption: [],
    };
  },
  filters: {
    // toFixfiler(val){
    //   debugger
    //   return val?val.toFixed(2):'0';
    // }

    filterAllLevel (val, optionArr) {
      if (!val) return "--";
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
    pointFilt (val) {
      if (val) {
        return (val / 10).toFixed(2);
      } else {
        return ""
      }
    }
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

    onGrounpDetail (row) {
      this.groupObject = {
        groupId: row.groupId ? row.groupId : ''
      }
      this.isOpenGroup = true
    },
    // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
    // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
    // 也可以返回一个键名为rowspan和colspan的对象。
    headCellstyle ({ row, column, rowIndex, columnIndex }) {
      // debugger
      // console.log("column",rowIndex,columnIndex)
      if (rowIndex == 1 && row[6] && row[7]) {
        row[6].colSpan = 2 //第三列的表头占据3个单元格
        row[7].colSpan = 0
      }
      if (rowIndex == 1 && columnIndex == 7) {
        return 'display: none'
      }
    },
    headsrowStyle ({ row, rowIndex, num }) {
      if (rowIndex == 1) {
        return 'height:' + this.heiH + ';font-weight:normal;'
      } else {
        return 'font-weight:bold;'
      }
    },
    onshow () {
      this.pageshow = !this.pageshow
      if (this.pageshow) {
        this.heiH = "100%"
      } else {
        this.heiH = "100px"
      }
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
    Errors (err) {
      let text = new Error(err);
      return text
    },
    handleCompanySelect (e, row) {
      row.companyId = e.companyId;
      row.companyName = e.companyName;
    },
    //获取详情
    getDetail () {
      this.loading = true;
      let that = this;
      this.section = [
        {
          id: 1,
          name: "优秀",
          max: 10,
          min: 9,
        },
        {
          id: 2,
          name: "优良",
          max: 9,
          min: 8,
        },
        {
          id: 3,
          name: "中等",
          max: 8,
          min: 7,
        },
        {
          id: 4,
          name: "及格",
          max: 7,
          min: 6,
        },
        {
          id: 5,
          name: "不及格",
          max: 6,
          min: 0,
        },
      ]
      getExamOfGroupPre({ groupId: this.groupId }).then(res => {
        this.form = res.data;
        this.loading = false;
        let dataList = this.tableData;
        let data = res.data.examUserList ? res.data.examUserList : [];
        data.forEach(item => {
          item.Allpoints = item.Allpoints ? item.Allpoints : "";
          // item.level = ""
          let a = item.Allpoints;
          let txt = "";
          for (let i = 0, len = that.section.length; i < len; i++) {
            if (a >= that.section[i].min && a <= that.section[i].max) {
              txt = that.section[i].name
              break
            }
          }
          if (Number(item.Allpoints) == 0) {
            item.level = "--"
          } else {
            item.level = txt
          }
          item.examList = item.examList.map((itss, idss) => {
            let dataExam = JSON.parse(JSON.stringify(dataList)).filter(itm => itm.id == itss.examId)[0];
            itss.scopeContent = dataExam.scopeContent;
            itss.scopeTitle = dataExam.scopeTitle
            itss.mixScope = dataExam.mixScope
            return itss
          })
        })
        console.log(data, "data--")
        that.proTeamList = data;
      })
    },
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
        return exportExamOfGroupPre(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function () { });
    },
    inpBlur (val, indx) {
      console.log(val, indx)
      let Allpoints = 0;
      let proTeamList = JSON.parse(JSON.stringify(this.proTeamList));
      Allpoints = proTeamList[indx].examList.reduce((r, n) => {
        let bl = 0.1;
        if(n.examId==18){
          bl = 0.5;
        }else if(n.examId==19){
          bl = 0.2;
        }
        else if(n.examId==20){
          bl = 0.2;
        }
        else if(n.examId==21){
          bl = 0.1;
        }
        console.log(Number(n.examResult.replace(/[^0-9.]/ig, "") != "" ? Number(n.examResult.replace(/[^0-9.]/ig, "")) : 0))
        return r + ((Number(n.examResult.replace(/[^0-9.]/ig, "") != "" ? Number(n.examResult.replace(/[^0-9.]/ig, "")) : 0)))*bl
      }, 0)
      proTeamList[indx].Allpoints = Allpoints.toFixed(2);
      let a = Allpoints;
      let txt = "";
      for (let i = 0, len = this.section.length; i < len; i++) {
        if (a >= this.section[i].min && a <= this.section[i].max) {
          txt = this.section[i].name
          break
        }
      }

      proTeamList[indx].level = txt;
      this.proTeamList = proTeamList;
      console.log(Allpoints, txt)
    },
    /** 导入按钮操作 */
    openImportTable () {
      this.upload.title = "入库管理_人才收集管理 数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate () {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    addInit () {
      getNextDealUser().then((res) => {
        let tempArr = [];
        if (res.data.nextUserId) {
          let ids = res.data.nextUserId.split(",");
          let names = res.data.nextUserName.split(",");
          for (let i = 0; i < ids.length; i++) {
            tempArr.push({
              id: ids[i],
              name: names[i],
            });
          }
        }
        this.nextUsers = tempArr;
      });
    },
    handleClose () {
      this.$emit("close");
    },
    //暂存按钮
    storageForm () {
      console.log("开始暂存")
      let data = JSON.parse(JSON.stringify(this.form))
      data.todoId = this.todoId
      data.examUserList = this.proTeamList

      const loading = this.openLoading();
      saveExamOfGroupPre(data).then(res => {
        this.$message({
          message: res.msg,
          type: 'success'
        });
        this.getDetail()
        loading.close()
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
      let data = this.form
      data.examUserList = this.proTeamList
      data.todoId = this.todoId

      // queryform
      this.$refs["form"].validate(valid => {
        if (valid) {

          const loading = this.openLoading();
          getPersonNumber(data).then(resp => {
            loading.close();
            let respon = resp.data
            // canExcellentNumber
            // canGoodNumber
            const h = this.$createElement;
            let div = h('div', [
              h('p', [
                h('span', `本次巡察，本小组的优秀巡察员名额指标为 `),
                h('span', { class: 'red' }, respon.canExcellentNumber),
                h('span', `（人）。`),
              ]),
              h('p', [
                h('span', `优良巡察员名额指标为 `),
                h('span', { class: 'red' }, respon.canGoodNumber),
                h('span', `（人）。`),
              ]),
              // h('p',`${respon.canGoodNumber}`),
              h('p', `如果超出名额范围，系统将按照规则自动进行调整。`),])
            this.$confirm(div, '提示', {
              confirmButtonText: '继续提交',
              cancelButtonText: '返回调整',
            }).then(() => {
              this.disabledwindow = true

              const loading = this.openLoading();
              submitExamOfGroupPre(data).then(res => {
                this.$message({
                  message: res.msg,
                  type: 'success'
                });
                this.disabledwindow = false;
                loading.close();
                window.location.reload()
              })
            }).catch(() => {

            });
          })

        }
      })

      // this.$refs["form"].validate(valid => {
      //   if (valid) {

      //   }
      // });
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

// /deep/ .el-form-item__error {
//   width: 280px;
// }

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

.red {
  font-size: 18px;
  font-weight: bold;
  color: #1590ff;
}

/deep/ td.el-table_1_column_4.is-center.is-leaf.el-table__cell {
  text-align: right;
}

/deep/ .el-table thead.is-group th.el-table__cell {
  height: inherit;
  transition: all 1s ease 0;
  font-weight: inherit;
}

/deep/ .el-table th.el-table__cell>.cell {
  height: inherit;
  transition: all 1s ease 0;
  font-weight: inherit;
}

/deep/ .specialForm .el-form-item__content {
  margin-left: 0 !important;
}

/deep/ .operations .dialog-title {
  display: none !important;
}

/deep/ .operations .context-row {
  display: none !important;
}
</style>