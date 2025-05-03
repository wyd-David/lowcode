<template>
  <div>
    <!-- 查看人员抽调_巡察任务抽调信息 对话框 -->
    <el-dialog
      :title="title"
      :fullscreen="isFullscreen"
      :visible.sync="dialogVisible"
      width="1300px"
      :close-on-click-modal="false"
      @open="handleOpen"
      :before-close="handleClose"
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
          :disabled="disabled"
          ref="queryform"
          :rules="rules"
          :model="form"
          label-width="120px"
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-input
                  v-if="groupPiciId"
                  style="width: 100%"
                  v-model="form.xunchaYear"
                  :disabled="true"
                />
                <el-date-picker
                  v-else
                  clearable
                  size="small"
                  style="width: 100%"
                  v-model="form.xunchaYear"
                  type="year"
                  value-format="yyyy"
                  @change="handleYearChange"
                  placeholder="选择巡察年份"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="巡察批次："
                :prop="groupPiciId ? 'piciName' : 'xunchaInstance'"
              >
                <el-input
                  v-if="groupPiciId"
                  style="width: 100%"
                  v-model="form.piciName"
                  :disabled="true"
                />
                <el-select
                  v-else
                  style="width: 100%"
                  v-model="form.piciId"
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
            <el-col :span="12">
              <el-form-item label="巡察时段：" prop="xunchaTimeSolt">
                <el-input
                  style="width: 100%"
                  v-model="form.xunchaTimeSolt"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="被巡察单位：" prop="companyNames">
            <el-input disabled v-model="form.companyNames" />
          </el-form-item>
        </el-form>
        <div class="result_box">
          <div>
            <span>已抽调总人数：</span>
            <div
              style="display: inline"
              v-for="item in transferList"
              :key="item.transferList"
            >
              <span>{{ item.fieldKey }}<span v-if="item.personnelCount">：</span></span>
              <span>{{ item.personnelCount }} </span>
            </div>
          </div>
        </div>
        <div class="dialog-title" style="justify-content: space-between">
          <div><span class="line"></span><span>分组规则设置</span></div>
          <template v-if="authorizeFlag">
            <el-button
              class="btn"
              size="mini"
              type="primary"
              :disabled="!form.piciId"
              @click="addgroup"
              >新增巡察组
            </el-button>
          </template>
        </div>

        <div class="context-row">
          <el-table :data="xunchaTaskGroupInfoVoList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="巡察组" min-width="90" prop="groupName">
            </el-table-column>
            <el-table-column
              label="被巡察单位"
              width="110"
              show-overflow-tooltip
              align="center"
              prop="company"
            >
            </el-table-column>
            <el-table-column
              label="组长"
              width="130"
              align="center"
              prop="groupLeader"
            >
              <template slot-scope="scope">
                <el-select
                  v-if="authorizeFlag"
                  style="width: 100%"
                  v-model="scope.row.groupLeader"
                  filterable
                  remote
                  value-key="userId"
                  reserve-keyword
                  placeholder="请输入"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  clearable
                  @change="(val) => leaderChange(val, scope.row.groupLeader)"
                >
                  <el-option
                    v-for="item in groupLeaderList"
                    v-show="item.isShow"
                    :key="item.userId"
                    :label="
                      item.userName +
                      '-' +
                      item.userId +
                      '-' +
                      item.company +
                      '/' +
                      item.dpname
                    "
                    :value="item.userId"
                  >
                  </el-option>
                </el-select>
                <el-button v-else type="text" @click="personnelId = scope.row.groupLeader;collectFormShow=true">{{ scope.row.groupLeaderName }}</el-button>
              </template>
            </el-table-column>

            <el-table-column
              label="副组长"
              width="130"
              align="center"
              prop="deputyTeamLeader"
            >
              <template slot-scope="scope">
                <el-select
                  v-if="authorizeFlag"
                  multiple
                  v-model="scope.row.deputyTeamLeaderArray"
                  filterable
                  remote
                  value-key="userId"
                  reserve-keyword
                  placeholder="请输入"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  clearable
                  @change="
                    (val) => checkMember(val, scope.row, 'deputyTeamLeader')
                  "
                  @focus="(val) => remoteMethod('')"
                >
                  <el-option
                    v-for="item in groupLeaderList"
                    v-show="item.isShow"
                    :key="item.userId"
                    :label="
                      item.userName +
                      '-' +
                      item.userId +
                      '-' +
                      item.company +
                      '/' +
                      item.dpname
                    "
                    :value="item.userId"
                  />
                </el-select>
                <el-button v-else type="text" @click="personnelId = scope.row.deputyTeamLeader;collectFormShow=true">{{ scope.row.deputyTeamLeaderName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="主笔人"
              width="130"
              align="center"
              prop="leadWriter"
            >
              <template slot-scope="scope">
                <el-select
                  v-if="authorizeFlag"
                  multiple
                  filterable
                  remote
                  v-model="scope.row.leadWriterArray"
                  placeholder="请选择"
                  clearable
                  @change="(val) => checkMember(val, scope.row, 'leadWriter')"
                >
                  <el-option
                    v-show="item.isShow"
                    v-for="item in leadWriterList"
                    :key="item.personnelInfoId"
                    :label="
                      item.personnelName +
                      '-' +
                      item.personnelId +
                      '-' +
                      item.company +
                      '/' +
                      item.ouname
                    "
                    :value="item.personnelId"
                  />
                </el-select>
                <el-button v-else type="text" @click="personnelId = scope.row.leadWriter;collectFormShow=true">{{ scope.row.leadWriterName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="联络员"
              width="130"
              align="center"
              prop="liaisonMan"
            >
              <template slot-scope="scope">
                <el-select
                  v-if="authorizeFlag"
                  multiple
                  filterable
                  remote
                  v-model="scope.row.liaisonManArray"
                  placeholder="请选择"
                  clearable
                  @change="(val) => checkMember(val, scope.row, 'liaisonMan')"
                >
                  <el-option
                    v-show="item.isShow"
                    v-for="item in liaisonManList"
                    :key="item.personnelInfoId"
                    :label="
                      item.personnelName +
                      '-' +
                      item.personnelId +
                      '-' +
                      item.company +
                      '/' +
                      item.ouname
                    "
                    :value="item.personnelId"
                  />
                </el-select>
                <el-button v-else type="text" @click="personnelId = scope.row.liaisonMan;collectFormShow=true">{{ scope.row.liaisonManName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="成员总数"
              width="80"
              align="center"
              prop="count"
            >
              <template slot-scope="scope">
                <template v-if="!scope.row.groupId">
                  {{ scope.row | countSum }}
                </template>
                <template v-else>
                  {{ scope.row.groupMemberTotal }}
                </template>
              </template>
            </el-table-column>
            <el-table-column
              min-width="260"
              label="分组规则"
              align="center"
              prop="groupRuleContent"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.groupRuleContent">{{
                  scope.row.groupRuleContent
                }}</span>
                <template
                  v-else-if="!scope.row.groupRuleContent && authorizeFlag"
                >
                  <el-button
                    type="primary"
                    size="mini"
                    @click="onOpenSetDialog(scope.$index, scope.row)"
                    >设置规则</el-button
                  >
                  <el-button
                    @click="onReplicate(scope.row, scope.$index)"
                    type="primary"
                    size="mini"
                    >复用上一组规则</el-button
                  >
                </template>
              </template>
            </el-table-column>

            <el-table-column
              v-if="authorizeFlag"
              fixed="right"
              width="130"
              label="操作"
            >
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.groupRuleContent"
                  size="mini"
                  @click="edit(scope.$index, scope.row)"
                  type="text"
                  >修改规则
                </el-button>
                <el-button
                  size="mini"
                  @click="onDeleteRule(scope.$index)"
                  type="text"
                  >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="btn_box" v-if="authorizeFlag">
          <!-- <el-button size="mini" @click="onSave" type="primary"
            >保 存</el-button
          > -->
          <el-button size="mini" @click="onGroup" type="primary"
            >开始预分组</el-button
          >
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>预分组结果</span>
          <span v-show="tipsShow" class="tips-enough"
            >本批次抽取的人才数量不足，请先抽取人才</span
          >
          <div class="operation">
            <el-button size="small" type="primary" @click="exportFile">导出</el-button>
          </div>
        </div>
        <div class="context-row">
          <el-table :data="groupPersonInfoVoList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column
              label="巡察组"
              align="center"
              prop="groupName"
            ></el-table-column>
            <el-table-column
              label="组长"
              align="center"
              prop="groupLeaderName"
            >
              <template slot-scope="scope">
                <el-button type="text" @click="personnelId = scope.row.groupLeader;collectFormShow=true">{{ scope.row.groupLeaderName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="副组长"
              align="center"
              prop="deputyTeamLeaderName"
            >
              <template slot-scope="scope">
                <el-button type="text" @click="personnelId = scope.row.deputyTeamLeader;collectFormShow=true">{{ scope.row.deputyTeamLeaderName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="主笔人"
              align="center"
              prop="leadWriterName"
            >
              <template slot-scope="scope">
                <el-button type="text" @click="personnelId = scope.row.leadWriter;collectFormShow=true">{{ scope.row.leadWriterName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="联络员"
              align="center"
              prop="liaisonManName"
            >
              <template slot-scope="scope">
                <el-button type="text" @click="personnelId = scope.row.liaisonMan;collectFormShow=true">{{ scope.row.liaisonManName }}</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="成员总数"
              align="center"
              prop="groupMemberTotal"
            >
            </el-table-column>
            <el-table-column
              label="成员"
              align="center"
              prop="memberName"
            ></el-table-column>
            <el-table-column
              label="分组状态"
              align="center"
              prop="examineStatus"
            >
              <template slot-scope="scope">
                <span>
                  {{ scope.row.examineStatus | examineStatusFilters }}</span
                >
              </template>
            </el-table-column>
            <template v-if="authorizeFlag">
              <el-table-column label="操作" fixed="right" width="190">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="onReGroup(scope.row, scope.$index)"
                    type="text"
                    >重新预分组</el-button
                  >
                  <el-button
                    size="mini"
                    @click="onAdjustMember(scope.row)"
                    type="text"
                    >调整成员</el-button
                  >
                  <el-button
                    size="mini"
                    @click="deleteGroup(scope.row)"
                    type="text"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </template>
          </el-table>
        </div>
      </div>
      <div slot="footer" v-if="authorizeFlag" class="dialog-footer">
        <el-button @click="handleClose">关 闭</el-button>
        <el-button @click="onTemporary" type="primary">暂 存</el-button>
        <el-button @click="submitForm" type="primary">确定抽取</el-button>
        <el-button @click="transferAudit" type="primary"
          >确定抽取并发起审核</el-button
        >
      </div>
    </el-dialog>
    <Set-rule
      ref="setRule"
      :groupId="selectGroupId"
      :instanceId="form.piciId"
      @close="handleCloseSet"
      @submit="onSetRuler"
      :dialogVisible="isSetDialog"
    />
    <AdjustMember
      ref="adjustMember"
      :instanceId="form.piciId"
      :groupId="groupId"
      :dialogVisible="isAdjustMember"
      @close="handleCloseAdjust"
    />
    <XunChaTaskInfo
      ref="xunChaTaskInfo"
      :param="form"
      :dialogVisible="isXunChaTaskInfo"
      @close="handleCloseXunChaTask"
    />
    <collectForm
      :dialogVisible="collectFormShow"
      :id="personnelId"
      more
      @close="collectFormShow = false"
    ></collectForm>
  </div>
</template>

<script>
import { selectLunciData } from "@/api/xuncha/XunchaLunciManage";
import { listXunchaPiciManage } from "@/api/xuncha/XunchaPiciManage";
import {
  getTransferPersonnelCount,
  listTransferPersonnelInfo,
} from "@/api/xuncha/transferManage/transferPersonnelInfo";
import { listGroupLeader } from "@/api/xuncha/groupLeader/groupLeader";
import {
  addGroupingTransfer,
  removeTaskGroup,
  reGroupPersionnel,
  getGroupPersonInfoList,
  getGroupInfoDetail,
  exportGroupInfo
} from "@/api/xuncha/transferManage/taskGroupInfo";
import { unListXunchaTaskInfo } from "@/api/xuncha/XunchaTaskInfo";
import { noToChinese } from "@/utils/index";
import { formatDate } from "@/utils/index";
import collectForm from "@/views/talent/collect/collectForm.vue";
import AdjustMember from "./adjustMember.vue";
import SetRule from "./setRule.vue";
import XunChaTaskInfo from "./xunChaTaskInfo.vue";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: true,
    },
    // 弹出层标题
    title: {
      type: String,
    },
    optionType: {
      type: String,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    groupPiciId: {
      type: String,
      default: "",
    },
    // 1:无法编辑 2:可编辑
    authorizeFlag: {
      type: Boolean,
      default: false,
    },
  },
  components: { SetRule, AdjustMember, XunChaTaskInfo,collectForm },
  data() {
    return {
      collectFormShow: false,
      personnelId: "",
      isSetDialog: false,
      isAdjustMember: false,
      isXunChaTaskInfo: false,
      isFullscreen: false,
      tipsShow: false,
      // 选中组Id
      groupId: "",
      selectGroupId: "",
      status: "",
      // 表单参数
      form: {
        lunciId: null,
        xunchaYear: null,
        piciId: null,
        piciName: null,
        xunchaTimeSolt: null,
        companyNames: null,
        status: null,
      },
      xunchaTaskGroupInfoVoList: [],
      xunchaPici: [],
      groupLeaderList: [],
      // 副组长
      deputyTeamGroupLeaderList: [],
      // 抽调结果
      transferList: [],
      // 预分组结果
      groupPersonInfoVoList: [],
      // 查询详情，规则回显
      xunchaYears: [],
      // 主笔人
      leadWriterList: [],
      // 联络员
      liaisonManList: [],

      // 表单校验
      rules: {
        lunciId: [
          {
            required: true,
            message: "巡察年份不能为空",
            trigger: "blur",
          },
        ],
        piciId: [
          { required: true, message: "巡察批次名称不能为空", trigger: "blur" },
        ],
        xunchaTimeSolt: [
          { required: true, message: "计划巡察时段不能为空", trigger: "blur" },
        ],
      },
      // 已选中row下标
      selectedIndex: null,
      loading: false,
    };
  },
  computed: {},

  mounted() {},
  filters: {
    countSum(row) {
      let count =
        row.liaisonManArray.length +
        row.leadWriterArray.length +
        row.deputyTeamLeaderArray.length +
        row.ruleCount;
      if (row.groupLeader) {
        count++;
      }
      console.log(count);
      return count;
    },
    examineStatusFilters(val) {
      let examineStatusArray = ["未发起审核", "待审核", "已结束", "已退回"];
      return examineStatusArray[val];
    },
  },
  methods: {
    exportFile() { 
      exportGroupInfo({ groupPiciNo: this.groupPiciNo }).then(res => { 
        if (res.code == 200) {
          this.download(res.msg);
        }
      })
    },
    //搜索用户下拉框
    remoteMethod(query) {
      console.log(query);
      let params = {
        userName: query,
        status: 0,
        instanceId: this.form.piciId,
        pageSize: 100,
      };
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          listGroupLeader(params).then((res) => {
            this.groupLeaderList = res.rows;
            this.groupLeaderList.forEach((i) => (i.isShow = true));
            this.deputyTeamGroupLeaderList = [...res.rows];
            this.leaderChange();
          });
        }, 200);
      } else {
        listGroupLeader(params).then((res) => {
          this.groupLeaderList = res.rows;
          this.groupLeaderList.forEach((i) => (i.isShow = true));
          this.deputyTeamGroupLeaderList = [...res.rows];
          this.leaderChange();
        });
      }
    },
    leaderChange(val) {
      console.log(val);
      let leaders = this.xunchaTaskGroupInfoVoList.map((i) => i.groupLeader);
      // 隐藏已选项，实现已选人员不可在其他组重复选
      this.xunchaTaskGroupInfoVoList.map((i) => {
        leaders = leaders.concat(i.deputyTeamLeaderArray);
      });
      console.log(leaders);
      this.groupLeaderList.forEach((item, index) => {
        if (leaders.some((ele) => item.userId == ele)) {
          item.isShow = false;
          this.$set(this.groupLeaderList, index, item);
        } else {
          item.isShow = true;
          this.$set(this.groupLeaderList, index, item);
        }
      });
    },
    handleOpen() {
      this.yearsQuery();
      if (this.groupPiciId) {
        let params = {
          piciId: this.groupPiciId,
        };
        // 详情
        getGroupInfoDetail(params).then((res) => {
          let form = this.form;
          let data = res.data;
          for (const key in form) {
            form[key] = data[key];
          }
          form.piciId = data.instanceId;
          this.groupPiciNo = data.groupPiciNo;

          this.groupPersonInfoVoList = data.groupPersonInfoVoList;
          // ryStatus    该组是否抽取人员不足
          this.tipsShow = this.groupPersonInfoVoList.some(
            (i) => i.ryStatus == 0
          );

          this.groupPersonInfoVoListProcess(data.groupRulePersonInfoVoList);
        });
      }
    },
    // 分组规则回显数据处理
    groupPersonInfoVoListProcess(data) {
      this.xunchaTaskGroupInfoVoList = data.map((item) => {
        item.deputyTeamLeaderArray = item.deputyTeamLeader
          ? item.deputyTeamLeader.split(",")
          : [];
        item.leadWriterArray = item.leadWriter
          ? item.leadWriter.split(",")
          : [];
        item.liaisonManArray = item.liaisonMan
          ? item.liaisonMan.split(",")
          : [];
        item.groupRuleInfoList = item.groupRuleDetailList;
        return JSON.parse(JSON.stringify(item));
      });
      this.handlePiciChange(this.form.piciId);
    },
    // 保存按钮事件
    onSave() {
      this.addGroupingTransferQuery(0, (res) => {
        this.groupPiciNo = res.msg;
        this.form.groupPiciNo = res.msg;
      });
    },

    // 预分组
    onGroup() {
      let isemptyLeader = this.xunchaTaskGroupInfoVoList.some((i) => {
        return i.groupLeader == "";
      });
      let isemptyGroupRule = this.xunchaTaskGroupInfoVoList.some((i) => {
        return i.groupRuleInfoList == null || !i.groupRuleInfoList.length;
      });
      if (isemptyLeader) {
        this.msgError("请设置组长");
        return;
      }
      if (isemptyGroupRule) {
        this.msgError("请设置规则");
        return;
      }
      const loading = this.openLoading();
      this.addGroupingTransferQuery(1, (res) => {
        loading.close();
        this.groupPiciNo = res.msg;
        this.form.groupPiciNo = res.msg;

        this.resultQuery(res.msg);
        this.handleOpen();
      });
    },
    // 暂存
    onTemporary() {
      if (this.groupPersonInfoVoList.length == 0) {
        this.msgError("请先抽取预分组");
        return;
      }
      this.$confirm("是否确定暂存?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        const loading = this.openLoading();
        this.addGroupingTransferQuery(2, () => {
          loading.close();
          this.handleClose(200);
        });
      });
    },

    submitForm() {
      if (this.groupPersonInfoVoList.length == 0) {
        this.msgError("请先抽取预分组");
        return;
      }
      this.$confirm("是否确定抽取?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        const loading = this.openLoading();
        this.addGroupingTransferQuery(3, () => {
          loading.close();

          this.handleClose(200);
        });
      });
    },
    transferAudit() {
      if (this.groupPersonInfoVoList.length == 0) {
        this.msgError("请先抽取预分组");
        return;
      }
      this.$confirm("是否确定抽取并发起审核?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        const loading = this.openLoading();
        this.addGroupingTransferQuery(4, () => {
          loading.close();
          this.handleClose(200);
        });
      });
    },
    // 修改规则
    edit(index, row) {
      this.selectedIndex = index;
      this.selectGroupId = row.groupId;
      this.isSetDialog = true;
      // let groupRule = row.groupRuleInfoList || row.groupRuleDetailList
      this.$nextTick(() => {
        this.$refs["setRule"].mySelectResign(row.groupRuleInfoList);
      });
    },
    // 打开设置规则弹框
    onOpenSetDialog(index, row) {
      this.isSetDialog = true;
      this.selectGroupId = null;
      this.selectedIndex = index;
    },
    // 打开调整成员弹框
    onAdjustMember(row) {
      this.groupId = row.groupId;
      this.isAdjustMember = true;
    },
    // 巡察年份change
    handleYearChange(val) {
      this.form.piciId = null;
      this.form.xunchaTimeSolt = null;
      this.form.companyNames = null;

      let param = {
        year: val,
      };
      this.piciQuery(param);
    },
    // 批次change
    handlePiciChange(event) {
      this.xunchaPici.forEach((i) => {
        if (i.piciId == event) {
          this.form.xunchaTimeSolt = `${
            formatDate(i.startTime).split(" ")[0]
          } 至 ${formatDate(i.endTime).split(" ")[0]}`;
          this.form.companyNames = i.companyNames;
          this.form.piciName = i.piciName;
        }
      });
      if (!this.groupPiciId) {
        this.setXunChaGroup(event);
      }
      this.transferPersonnelCountQuery();
      let groupId = "";
      if (this.optionType == "2") {
        groupId = this.xunchaTaskGroupInfoVoList
          .map((i) => i.groupId)
          .join(",");
      }
      this.getGroupLeaderQuery(groupId);
      this.listTransferPersonnelInfoQuery("主笔人", "leadWriterList", groupId);
      this.listTransferPersonnelInfoQuery("联络员", "liaisonManList", groupId);
    },
    /**
     * val  select值
     * row  table列
     * key  字段名
     */
    checkMember(val, row, key) {
      if (key == "deputyTeamLeader") {
        let leaders = this.xunchaTaskGroupInfoVoList.map((i) => i.groupLeader);
        this.xunchaTaskGroupInfoVoList.map((i) => {
          leaders = leaders.concat(i.deputyTeamLeaderArray);
        });
        this.groupLeaderList.forEach((item, index) => {
          if (leaders.some((ele) => item.userId == ele)) {
            item.isShow = false;
            this.$set(this.groupLeaderList, index, item);
          } else {
            item.isShow = true;
            this.$set(this.groupLeaderList, index, item);
          }
        });
      } else {
        let leadWriters = [];
        this.xunchaTaskGroupInfoVoList.map((i) => {
          leadWriters = leadWriters.concat(i[key + "Array"]);
        });
        this[key + "List"].forEach((item, index) => {
          if (leadWriters.some((ele) => item.personnelId == ele)) {
            item.isShow = false;
            this.$set(this[key + "List"], index, item);
          } else {
            item.isShow = true;
            this.$set(this[key + "List"], index, item);
          }
        });
      }

      row[key] = val.join(",");
      console.log(row[key]);
      // val.some(item=>item==)
    },
    // 根据巡察任务生成对应巡察组
    setXunChaGroup(piciId) {
      let params = {
        piciId,
      };
      unListXunchaTaskInfo(params).then((res) => {
        this.xunchaTaskGroupInfoVoList = res.rows.map((item) => {
          return {
            groupName: item.groupName,
            company: item.companyNames,
            groupRuleContent: "",
            groupLeader: "",
            count: 0,
            ruleCount: 0,
            liaisonManArray: [],
            leadWriterArray: [],
            deputyTeamLeaderArray: [],
            groupRuleInfoList: null,
            taskId: item.taskId,
          };
        });
      });
    },
    // 巡察年份query
    yearsQuery() {
      selectLunciData().then((res) => {
        this.xunchaYears = res.data;
      });
    },
    //巡察批次Query
    piciQuery(param) {
      listXunchaPiciManage(param).then((res) => {
        this.xunchaPici = res.rows;
      });
    },
    // 获取抽调中各领域人员数量
    transferPersonnelCountQuery() {
      let param = {
        piciId: this.form.piciId,
      };
      getTransferPersonnelCount(param).then((res) => {
        if (res.code === 200) {
          this.transferList = res.data;
          if (!this.transferList.length) {
            this.transferList = [{ fieldKey: "暂无" }];;
          }
        }
      });
    },
    // 预分组抽调
    addGroupingTransferQuery(status, call) {
      let params = {
        ...this.form,
        status,
        groupPiciNo: this.groupPiciNo,
        // instanceId: this.form.piciId,
        xunchaTaskGroupInfoVoList: this.xunchaTaskGroupInfoVoList,
      };
      addGroupingTransfer(params).then((res) => {
        this.form.status = status;
        this.msgSuccess("操作成功");
        // if (status==1) {
        //   this.resultQuery(res.msg);

        // }
        call(res);
      });
    },
    // 抽取结果Query
    resultQuery(groupPiciNo) {
      getGroupPersonInfoList(groupPiciNo).then((res) => {
        this.groupPersonInfoVoList = res.data;
        this.tipsShow = this.groupPersonInfoVoList.some((i) => i.ryStatus == 0);
        this.xunchaTaskGroupInfoVoList.forEach((i) => {
          this.groupPersonInfoVoList.forEach((e) => {
            if (i.groupLeader == e.groupLeader) {
              i.groupId = e.groupId;
            }
          });
        });
        // this.xunchaTransferPersonnelInfoList = res.rows;
        // this.total = Number(res.total);
      });
    },
    listTransferPersonnelInfoQuery(labelName, list, groupId) {
      let param = {
        instanceId: this.form.piciId,
        labelName,
        feedbackStatus: 1,
        isGroup: 0,
        groupId,
      };

      listTransferPersonnelInfo(param).then((res) => {
        this[list] = res.rows;
        this[list].forEach((i) => (i.isShow = true));
      });
    },

    // 新增巡察组
    addgroup() {
      this.isXunChaTaskInfo = true;
    },
    // 删除巡察组
    onDeleteRule(index) {
      this.xunchaTaskGroupInfoVoList.splice(index, 1);
    },

    // 重新预分组
    onReGroup(row, index) {
      let params = {
        groupId: row.groupId,
      };
      reGroupPersionnel(params).then((res) => {
        this.msgSuccess(res.msg);
        // debugger
        this.$set(this.groupPersonInfoVoList, index, res.data);
      });
    },
    // 删除预分组
    deleteGroup(row) {
      let params = {
        groupId: row.groupId,
        transferId: this.form.piciId,
      };
      removeTaskGroup(params).then((res) => {
        this.msgSuccess(res.msg);
        this.resultQuery(this.groupPiciNo);
      });
    },

    // 关闭设置规则弹框
    handleCloseSet() {
      this.isSetDialog = false;
    },
    handleCloseAdjust(type) {
      if (type) {
        this.resultQuery(this.groupPiciNo);
      }
      this.isAdjustMember = false;
    },
    // 组长人员列表Query
    getGroupLeaderQuery(groupId) {
      listGroupLeader({
        status: 0,
        instanceId: this.form.piciId,
        groupId,
        pageSize: 100,
      }).then((res) => {
        this.groupLeaderList = res.rows;
        this.groupLeaderList.forEach((i) => (i.isShow = true));
        if (this.optionType == "2") {
          this.leaderChange();
        }
      });
    },
    // 生成规则文本
    onSetRuler(arr) {
      // 将已选行生成规则
      let rulerSrt = "";
      let total = 0;
      debugger
      arr.forEach((item) => {
        total += Number(item.transferTotal);
        if (item.labelName == "其他成员") {
          rulerSrt += `${item.labelName}：${item.transferTotal}，`;
        } else {
          rulerSrt += `${item.labelName}：${item.transferTotal}，${item.fieldKey}; `;
        }
      });
      this.xunchaTaskGroupInfoVoList[this.selectedIndex].groupRuleInfoList =
        arr;
      this.xunchaTaskGroupInfoVoList[this.selectedIndex].groupRuleContent =
        rulerSrt;
      this.xunchaTaskGroupInfoVoList[this.selectedIndex].ruleCount =
        Number(total);
      this.xunchaTaskGroupInfoVoList[this.selectedIndex].groupMemberTotal = Number(total);
      this.handleCloseSet();
    },

    onReplicate(row, index) {
      this.selectedIndex = index;
      let result = this.xunchaTaskGroupInfoVoList[this.selectedIndex ? this.selectedIndex - 1 : 0].groupRuleInfoList;
      if (result) {
        this.onSetRuler(
          result
        );
      } else { 
        this.$message.warning("暂无规则");
      }
      // this.xunchaTaskGroupInfoVoList[this.selectedIndex].groupRuleInfoList
    },
    // 弹框初始化
    init() {
      this.$refs.queryform.resetFields();
      this.form.piciId = "";
      this.form.groupPiciNo = "";
      this.groupId = "";
      this.groupPiciNo = "";
      this.transferList = [];
      this.xunchaPici = [];
      this.xunchaTaskGroupInfoVoList = [];
      this.groupPersonInfoVoList = [];
      this.tipsShow = false;
      this.selectGroupId = "";
    },
    handleClose(e) {
      this.init();
      this.$emit("close", e);
    },
    handleCloseXunChaTask(val) {
      if (val == 200) {
        let params = {
          piciId: this.form.piciId,
        };
        unListXunchaTaskInfo(params).then((res) => {
          this.xunchaTaskGroupInfoVoList.push({
            groupName: res.rows[0].groupName,
            company: res.rows[0].companyNames,
            groupRuleContent: "",
            groupLeader: "",
            count: 0,
            ruleCount: 0,
            liaisonManArray: [],
            leadWriterArray: [],
            deputyTeamLeaderArray: [],
            groupRuleInfoList: null,
            taskId: res.rows[0].taskId,
          });
        });
      }
      this.isXunChaTaskInfo = false;
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
.dialog-footer {
  text-align: right;
}
.tips-enough {
  color: #1590ff;
  font-weight: 400;
  font-size: 12px;
  margin-left: 25px;
}
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