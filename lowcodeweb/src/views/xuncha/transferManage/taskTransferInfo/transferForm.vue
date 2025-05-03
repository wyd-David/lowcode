<template>
  <div>
    <!-- 查看人员抽调_巡察任务抽调信息 对话框 -->
    <el-dialog
      :title="title"
      :fullscreen="isFullscreen"
      :visible.sync="isOpenQuery"
      width="900px"
      :close-on-click-modal="false"
      :before-close="handleClose"
      @open="handleOpen"
      append-to-body="append-to-body"
    >
      <div slot="title" style="position: relative">
        <span>{{ title }}</span>
        <el-button
          type="text"
          style="position: absolute; right: 40px"
          size="small"
          @click="isFullscreen = !isFullscreen"
          >{{ isFullscreen ? "关闭全屏" : "打开全屏" }}</el-button
        >
      </div>
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>巡察信息</span>
        </div>
        <el-form
          ref="queryform"
          :model="form"
          label-width="120px"
          :rules="formRules"
        >
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="巡察年份：" :prop="'xunchaYear'">
                <el-input
                  v-if="status"
                  style="width: 250px"
                  v-model="form.xunchaYear"
                  :disabled="true"
                />
                <el-date-picker
                  v-else
                  clearable
                  size="small"
                  style="width: 250px"
                  v-model="form.xunchaYear"
                  type="year"
                  value-format="yyyy"
                  @change="handleYearChange"
                  placeholder="选择巡察年份"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10" :offset="4">
              <el-form-item
                label="巡察批次："
                :prop="status ? 'piciName' : 'xunchaInstance'"
              >
                <el-input
                  v-if="status"
                  style="width: 250px"
                  v-model="form.piciName"
                  :disabled="true"
                />
                <el-select
                  v-else
                  :disabled="status !== ''"
                  style="width: 250px"
                  v-model="form.xunchaInstance"
                  placeholder="请选择巡察批次"
                  @change="handlePiciChange"
                >
                  <el-option
                    v-for="dict in xunchaPici"
                    :key="dict.piciId"
                    :label="dict.piciName"
                    :value="dict.piciId"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="巡察时段：" prop="xunchaTimeSolt">
                <el-input
                  style="width: 250px"
                  v-model="form.xunchaTimeSolt"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="被巡察单位：" prop="companyNames">
            <el-input v-model="form.companyNames" :disabled="true" />
          </el-form-item>

          <el-row :gutter="20">
            <el-form-item
              label-width="230px"
              label="派出单位抽取人数限制：最多抽取"
              prop="maxNumber"
            >
              <el-input
                :disabled="status !== ''"
                style="width: 80px"
                v-model="form.maxNumber"
              />
            </el-form-item>
          </el-row>
        </el-form>
        <div class="dialog-title" style="justify-content: space-between">
          <div style="display: flex; align-items: center">
            <span class="line"></span><span>抽调规则</span>
          </div>
          <template v-if="authorizeFlag == 2">
            <el-button
              :disabled="status != '' && status != 0"
              class="btn"
              size="mini"
              type="primary"
              @click="addRule"
              >继续添加规则
            </el-button>
          </template>
        </div>
        <div class="context-row">
          <el-table :data="xunchaTransferFieldInfoVoList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column align="center" label="标签" prop="labelKey">
              <template slot-scope="scope">
                <span v-if="status">{{
                  scope.row.labelKey | labelNameFilter
                }}</span>
                <el-select
                  v-else
                  v-model="scope.row.labelKey"
                  placeholder="请选择"
                  clearable
                  @change="handleTabChange(scope.row)"
                >
                  <el-option
                    v-for="item in tabList"
                    :key="item.labelId"
                    :label="item.labelName"
                    :value="item.labelId"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column align="center" label="专家领域" prop="fieldKey">
              <template slot-scope="scope">
                <span v-if="status">{{ scope.row.fieldKey }}</span>
                <el-select
                  v-else
                  v-model="scope.row.fieldKey"
                  placeholder="请选择"
                  clearable
                  @change="handleProfessionChange(scope.row)"
                >
                  <el-option
                    v-for="item in scope.row.professionList"
                    :key="item.dataCode"
                    :label="item.dataName"
                    :value="item.dataCode"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
              label="专家在库人数"
              prop="professionNum"
              align="center"
            ></el-table-column>
            <el-table-column
              align="center"
              label="抽取人数"
              prop="transferTotal"
            >
              <template slot-scope="scope">
                <span v-if="status">{{ scope.row.transferTotal }}</span>
                <el-input
                  v-else
                  oninput="value=value.replace(/[^\d]/g,'')"
                  @input="
                    numberChange(
                      scope.row,
                      arguments[0],
                      scope.row.professionNum
                    )
                  "
                  v-model="scope.row.transferTotal"
                  placeholder="请输入人数"
                />
              </template>
            </el-table-column>
            <template v-if="authorizeFlag == 2">
              <el-table-column
                v-if="status == ''"
                header-align="center"
                align="center"
                label="操作"
              >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="deleteRule(scope.$index)"
                    type="text"
                    >删除
                  </el-button>
                </template>
              </el-table-column>
            </template>
          </el-table>
        </div>
        <template v-if="authorizeFlag == 2">
          <div class="btn_box" v-if="status == '' || status == '0'">
            <el-button size="mini" @click="onSave" type="primary"
              >保 存</el-button
            >
            <el-button size="mini" @click="onTransfer" type="primary"
              >开始抽调</el-button
            >
          </div>
        </template>

        <div class="dialog-title">
          <span class="line"></span><span>抽取结果</span>
          <div class="operation">
            <el-button size="small" type="primary" @click="preinstallUser" v-if="title == '新增'">预设人员</el-button>
            <el-button size="small" type="primary" @click="exportFile" v-else>导出</el-button>
          </div>
        </div>
        <div class="context-row">
          <el-table :data="xunchaTransferPersonnelInfoList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column
              label="巡察批次"
              show-overflow-tooltip
              prop="piciName"
            ></el-table-column>
            <el-table-column label="标签" prop="labelName"></el-table-column>
            <el-table-column
              label="专家领域"
              prop="fieldKey"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="姓名"
              align="center"
              prop="personnelName"
            >
              <template slot-scope="scope">
                <el-button type="text" @click="()=>{ personnelId = scope.row.personnelId;collectFormShow = true}">{{scope.row.personnelName}}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="所在单位"
              prop="company"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="部门"
              prop="ouname"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="抽调类型"
            >
              <template slot-scope="scope">
                {{scope.row.type == "0"?'系统抽调':'预设人员'}}
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              v-if="authorizeFlag == 2 && status != '4' && status != '2'"
              label="操作"
            >
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.type == 0"
                  size="mini"
                  @click="onAgain(scope.row, scope.$index)"
                  type="text"
                  >重抽
                </el-button>
                <el-button type="text" size="mini" @click="onRemove(scope.row, scope.$index)" v-else>删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pageBox">
            <pagination
              :total="total"
              :page.sync="paginationForm.pageNum"
              :limit.sync="paginationForm.pageSize"
              @pagination="pageResultQuery"
            />
          </div>
        </div>

        <!-- 抽取记录 -->
        <record-table v-if="authorizeFlag == 1" :form="form" />

        <!-- 通知记录 -->
        <notice-table
          v-if="authorizeFlag == 1"
          :tableData="xunchaTaskTransferNoticRecordList"
        />
      </div>
      <div slot="footer">
        <el-button @click="handleClose">取消</el-button>
        <template v-if="authorizeFlag == 2">
          <el-button @click="onTemporary" type="primary">暂 存</el-button>
          <el-button @click="submitForm(3)" type="primary">确定抽取</el-button>
        </template>
      </div>
    </el-dialog>

    <collectForm
      :dialogVisible="collectFormShow"
      :id="personnelId"
      more
      @close="collectFormShow = false"
    ></collectForm>
    <preinstall-user-form
    :transferId="form.transferId"
    :piciId="form.xunchaInstance"
      :dialogVisible="preinstallUserFormShow"
      more
      @success="preinstallSuccess"
      @close="preinstallUserFormShow = false"
    ></preinstall-user-form>
  </div>
</template>

<script>
import { selectLunciData, getCompanyAll } from "@/api/xuncha/XunchaLunciManage";
import {
  listTransferPersonnelInfo,
  addTransferPersonnelInfo,
  updateTransferPersonnelInfo,
  onAgainTransferPersonnelInfo,
  exportPersonInfo,
  removePersonnel
} from "@/api/xuncha/transferManage/transferPersonnelInfo";
import collectForm from "@/views/talent/collect/collectForm.vue";
import { listXunchaPiciManage } from "@/api/xuncha/XunchaPiciManage";
import { formatDate } from "@/utils/index";
import {
  getTransferFieldInfo,
  getProfessionUserCount,
  getProfessionTabInfo,
} from "@/api/xuncha/transferManage/transferFieldInfo";
import { getTaskTransferInfoDetail } from "@/api/xuncha/transferManage/taskTransferInfo";
import noticeTable from "./noticeTable.vue";
import recordTable from "./recordTable.vue";
import preinstallUserForm from './preinstallUserForm';
import PreinstallUserForm from './preinstallUserForm.vue';
let lableList = [];
getTransferFieldInfo({}).then((res) => {
  if (res.code === 200) {
    lableList = res.rows;
  }
});
export default {
  props: {
    isOpenQuery: {
      type: Boolean,
      default: false,
    },
    // 弹出层标题
    title: {
      type: String,
    },
    transferId: {
      type: String,
      default: "",
    },
    // 1:无法编辑 2:可编辑
    authorizeFlag: {
      type: Number,
      default: 1,
    },
  },
  components: { recordTable, noticeTable,collectForm,preinstallUserForm, PreinstallUserForm },
  data() {
    return {
      preinstallUserFormShow:false,
      isFullscreen: false,
      personnelId: "",
      collectFormShow:false,
      personnelNo:"",
      // 表单参数
      form: {
        xunchaInstance: null,
        xunchaTimeSolt: null,
        companyid: null,
        companyNames: null,
        lunciId: null,
        xunchaYear: null,
        transferId: null,
        piciName: null,
        maxNumber: null,
        updateName: "",
        transferTime: "",
      },
      //
      /**抽调任务状态
       * status 0:保存，1:抽调，2:确定，3:暂存，4:确定并通知
       */
      status: "",
      paginationForm: {
        pageNum: 1,
        pageSize: 50,
      },
      xunchaTransferFieldInfoVoList: [
        {
          labelKey: null,
          fieldKey: null,
          professionNum: null,
          transferTotal: null,
          professionList: [],
          status: null,
        },
      ],
      // 抽取结果
      xunchaTransferPersonnelInfoList: [],
      xunchaTaskTransferNoticRecordList: [],
      // 总条数
      total: 0,
      tabList: [],
      xunchaYears: [],
      xunchaPici: [],
      CompanyAll: [],
      formRules: {
        lunciId: [
          {
            required: true,
            message: "请选择年份",
            trigger: "change",
          },
        ],
        xunchaInstance: [
          {
            required: true,
            message: "请选择批次",
            trigger: "change",
          },
        ],
      },
      isPresets:null,
    };
  },
  computed: {},

  filters: {
    labelNameFilter(key) {
      console.log(lableList);
      return lableList.find((i) => i.labelId == key).labelName;
    },
  },
  watch: {},

  methods: {
    onRemove(e) { 
      console.log(e);

      this.$confirm("确定删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => { 
          removePersonnel({
            personnelInfoId: e.personnelInfoId,
            transferNo:this.form.transferNo
          }).then(res => { 
            if (res.code == 200) {
              this.resultQuery(this.form.transferNo);
              this.msgSuccess("删除成功");
            }
          })
      });
    },
    preinstallSuccess(e) {
      this.form.transferId = e;
      // this.form.transferNo = e;
      this.isPresets = 1;
      this.preinstallUserFormShow = false;
      this.resultQuery(this.form.transferNo);
     },
    //预设人员
    preinstallUser() {
      if (this.form.xunchaInstance) {
        this.preinstallUserFormShow = true;
      } else {
        this.msgError("请选择批次");
      }
    },
    exportFile() { 
      exportPersonInfo({ transferNo: this.form.transferNo }).then(res => { 
        if (res.code == 200) {
          this.download(res.msg);
        }
      })
    },
    handleOpen() {
      Object.assign(this.$data, this.$options.data());
      this.yearsQuery();
      // 判断是否为查询已有抽调
      if (this.transferId) {
        getTaskTransferInfoDetail({ transferId: this.transferId }).then(
          (res) => {
            let form = this.form;
            let data = res.data;
            for (const key in form) {
              form[key] = data[key];
            }
            form.xunchaInstance = data.instanceId;
            this.status = data.status;
            this.form.xunchaTimeSolt = data.xunchaTimeSolt;
            // this.xunchaTransferPersonnelInfoList =
            //   data.xunchaTransferPersonnelInfoList;
            this.resultQuery(data.transferNo);

            this.xunchaTransferFieldInfoVoList =
              data.xunchaTransferFieldInfoVoList;
            this.xunchaTaskTransferNoticRecordList =
              data.xunchaTaskTransferNoticRecordList;
          }
        );
      }
    },
    dateChange(val) {
      console.log(val);
    },
    numberChange(row, val, maxNum) {
      console.log(arguments);
      //转换数字类型
      row.transferTotal = Number(val);
      //重新渲染
      this.$nextTick(() => {
        //比较输入的值和最大值，返回小的
        let num = Math.min(Number(val), maxNum);
        //输入负值的情况下， = 0（可根据实际需求更该）
        if (num < 0) {
          row.transferTotal = 0;
        } else {
          //反之
          row.transferTotal = num;
        }
      });
    },
    addRule() {
      this.xunchaTransferFieldInfoVoList.push({
        labelKey: null,
        fieldKey: null,
        professionNum: null,
        transferTotal: null,
        professionList: [],
        status: null,
      });
    },

    // 保存
    onSave() {
      this.addTransferPersonnelInfoQuery(0, "保存成功");
    },
    // 抽调
    onTransfer() {
      let status = true;
      this.xunchaTransferFieldInfoVoList.forEach((i) => {
        i.status = 1;
        if (!i.labelKey || !i.fieldKey || !i.transferTotal) {
          status = false;
        }
      });
      if (status) {
        this.addTransferPersonnelInfoQuery(1, "抽调成功");
      } else {
        this.msgError("请完善抽调规则");
      }
    },
    // 重抽
    onAgain(row, index) {
      let params = {
        fieldKey: row.fieldKey,
        personnelId: row.personnelId,
        labelKey: row.labelKey,
        company: row.company,
        ouname: row.ouname,
        transferId: row.transferId,
        instanceId: row.instanceId,
      };
      onAgainTransferPersonnelInfo(params).then((res) => {
        // this.msgSuccess(res.msg)
        // this.$set(this.xunchaTransferPersonnelInfoList,index,res)
        // row = res;
        if (res.code === 200) {
          this.resultQuery(this.form.transferNo);
        }
      });
    },

    // 巡察年份change
    handleYearChange(val) {
      this.form.xunchaInstance = null;
      this.form.xunchaTimeSolt = null;
      this.form.companyid = null;
      this.form.companyNames = null;
      let params = {
        year: val,
      };
      // let { lunciName } = this.xunchaYears.find((i) => i.lunciId == val);
      // this.form.xunchaYear = lunciName;
      this.piciQuery(params);
    },
    // 巡察批次change
    handlePiciChange(event) {
      this.xunchaTransferFieldInfoVoList.forEach((item) => {
        item.labelKey = "";
        item.fieldKey = "";
        item.professionNum = "";
      });
      this.xunchaPici.forEach((i) => {
        if (i.piciId == event) {
          this.form.xunchaTimeSolt = `${
            formatDate(i.startTime).split(" ")[0]
          } 至 ${formatDate(i.endTime).split(" ")[0]}`;
          this.form.companyNames = i.companyNames;
          this.form.companyid = i.companyIds;
          this.form.piciName = i.piciName;
        }
      });
      this.tabQuery();
      // this.transferPersonnelCountQuery()
    },
    // 新增人员抽调_抽调批次人员信息
    /**
     * status 0:保存，1:抽调，2:确定，3:暂存，4:确定并通知
     */
    addTransferPersonnelInfoQuery(status, msg, call) {
      this.$refs["queryform"].validate((valid) => {
        if (valid) {
          let params = {
            isPresets:this.isPresets,
            xunchaTransferFieldInfoVoList: this.xunchaTransferFieldInfoVoList,
            status: status,
            ...this.form,
          };
          if (status > 0) {
            params.transferNo = this.form.transferNo;
          }
          addTransferPersonnelInfo(params).then((res) => {
            if (res.code === 200) {
              this.msgSuccess(msg);
              this.status = status;
              if (call) {
                call();
              }
              if (status == 1) {
                this.form.transferId = res.msg;
                this.resultQuery(res.msg);
              }
            }
          });
        } else {
          this.msgError("请完善表单信息!");
        }
      });
    },
    // 标签Query
    tabQuery() {
      getTransferFieldInfo({ instanceId: this.form.xunchaInstance,companyIds:this.form.companyid }).then(
        (res) => {
          this.tabList = [];
          if (res.code === 200) {
            this.tabList = res.rows;
          }
        }
      );
    },

    // 标签change
    handleTabChange(row) {
      row.fieldKey = "";
      row.professionNum = "";
      this.professionListQuery(row);
    },
    // 专业change
    handleProfessionChange(val) {
      val.instanceId = this.form.xunchaInstance;
      val.companyIds = this.form.companyid;
      getProfessionUserCount(val).then((res) => {
        if (res.code === 200) {
          val.professionNum = res.data;
        }
      });
    },

    // 巡察年份query
    yearsQuery() {
      selectLunciData().then((res) => {
        this.xunchaYears = res.data;
      });
    },
    //巡察批次Query
    piciQuery(params) {
      listXunchaPiciManage(params).then((res) => {
        this.xunchaPici = res.rows;
      });
    },
    // 分页
    pageResultQuery(page) {
      this.resultQuery(this.form.transferNo);
    },
    // 抽取结果Query
    resultQuery(transferNo) {
      this.form.transferNo = transferNo;
      let params = { ...this.form, ...this.paginationForm };
      listTransferPersonnelInfo(params).then((res) => {
        this.xunchaTransferPersonnelInfoList = res.rows;
        this.total = Number(res.total);
      });
    },

    // 专业查询query
    professionListQuery(row) {
      let params = {
        labelId: row.labelKey,
        instanceId: this.form.xunchaInstance,
        companyIds:this.form.companyid
      };
      getProfessionTabInfo(params).then((res) => {
        row.professionList = res.rows;
        this.$forceUpdate();
      });
    },
    // 删除规则
    deleteRule(index) {
      this.xunchaTransferFieldInfoVoList.splice(index, 1);
    },
    // 暂存
    onTemporary() {
      // 校验抽调结果
      if (this.xunchaTransferPersonnelInfoList.length === 0) {
        this.msgError("请先完成抽调操作");
        return;
      }
      this.$confirm("是否确定暂存?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.addTransferPersonnelInfoQuery(2, "暂存成功", () => {
          this.handleClose();
        });
      });
    },
    // 确认抽取
    submitForm(status) {
      if (this.xunchaTransferPersonnelInfoList.length === 0) {
        this.msgError("请先完成抽调操作");
        return;
      }
      this.$confirm("是否确定抽取?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.addTransferPersonnelInfoQuery(status, "抽取成功", () => {
          this.handleClose();
        });
      });
    },
    handleClose(e) {
      this.$emit("close", e);
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
/deep/.is-fullscreen > .el-dialog__body {
  max-height: initial;
  padding-bottom: 40px;
}
/deep/.is-fullscreen > .el-dialog__footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 100;
}
.btn_box {
  display: flex;
  justify-content: center;
}

.context-row {
  padding: 20px 40px;
  margin-bottom: 15px;
  position: relative;
}
</style>
