<template>
  <div class="app-main">
    <div class="dialog-title">
      <span class="line"></span>
      <span>待办信息</span>
    </div>
    <div
      class="context-row"
      style="margin-bottom: 0; padding-top: 0; padding-bottom: 0"
    >
      <el-form :model="formData" ref="formRef" label-width="120px">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="收集时间:">
              <el-date-picker
                style="width: 100%"
                @change="getDetail"
                v-model="year"
                :clearable="false"
                type="year"
                value-format="yyyy"
                placeholder="选择年"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标题:">
              <!-- <el-input disabled style="width: 100%" v-model="formData.title"></el-input> -->
              <el-select
                @change="getDetail"
                clearable
                style="width: 100%"
                v-model="title"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in titleSelect"
                  :label="item.title"
                  :value="item.title"
                  :key="item.title"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="业务单号:">
              <el-input
                disabled
                style="width: 100%"
                v-model="formData.orderNumber"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="收集截止时间:">
              <el-date-picker
                disabled
                style="width: 100%"
                v-model="formData.collectionEndDate"
                type="date"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="名额指标:">
              <el-input
                disabled
                style="width: 100%"
                :value="tableTotal"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button
              style="margin-left: 20px"
              size="small"
              type="primary"
              @click="todoInfo = true"
              >待办详情</el-button
            >
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="dialog-title">
      <span class="line"></span>
      <span>待办下发</span>
    </div>
    <div
      class="context-row"
      style="margin-bottom: 0; padding-top: 0; padding-bottom: 0"
    >
      <el-form label-width="120px">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="业务单号:">
              <el-input
                style="width: 100%"
                placeholder="请输入业务单号"
                v-model="queryForm.orderNumber"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="单位:">
              <el-select
                style="width: 100%"
                v-model="queryForm.deptId"
                clearable
                v-if="activeName == '省公司'"
              >
                <el-option
                  v-for="item in deptQuery.deptSelect"
                  :key="item.deptId"
                  :label="item.deptName"
                  :value="item.deptId"
                ></el-option>
              </el-select>
              <el-select
                style="width: 100%"
                clearable
                v-model="queryForm.companyId"
                v-else
              >
                <el-option
                  v-for="item in companyQuery.companySelect"
                  :key="item.companyId"
                  :label="item.companyName"
                  :value="item.companyId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="当前状态:">
              <el-select
                style="width: 100%"
                clearable
                v-model="queryForm.processStatus"
                v-if="activeName == '省公司'"
              >
                <el-option
                  v-for="item in deptQuery.types"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                ></el-option>
              </el-select>

              <el-select
                style="width: 100%"
                clearable
                v-model="queryForm.processStatus"
                v-else
              >
                <el-option
                  v-for="item in companyQuery.types"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button size="small" @click="reset">重置</el-button>
          <el-button type="primary" @click="getList" size="small"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <div class="dialog-title">
      <span class="line"></span>
      <span>
        人才收集
        <span style="color: #ec832f; font-weight: 400">
          （温馨提示：
          <span style="color: #b1261d">红色</span>数字表示实际荐人数，
          <span style="color: #333">黑色</span
          >数字表示推荐指标人数；请在完成对各分公司人才
          <span style="color: #b1261d">审核</span>后统一入库）
        </span>
      </span>
    </div>
    <div class="context-row">
      <div class="operation">
        <el-button @click="urgeClick" size="small" style="margin-right: 10px"
          >批量催办</el-button
        >
        <el-button
          @click="approveSchedule"
          v-if="activeName == '地市公司'"
          size="small"
          style="margin-right: 10px"
          >批量通过</el-button
        >
        <el-button size="small" :disabled="multiple" @click="handleExport"
          >导出</el-button
        >
        <el-button
          type="primary"
          @click="confirmInterview"
          size="small"
          style="margin-right: 10px"
          >确认入库</el-button
        >
      </div>
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane
          :label="item.dictLabel"
          :name="item.dictLabel"
          v-for="item in tabDicts"
          :key="item.dictLabel"
        ></el-tab-pane>
      </el-tabs>
      <el-table
        v-loading="loading"
        @selection-change="handleSelectionChange"
        :data="tableData"
        ref="multipleTable"
        v-if="activeName == '地市公司'"
      >
        <el-table-column align="center" type="selection"></el-table-column>
        <el-table-column
          width="55"
          label="序号"
          align="center"
          type="index"
        ></el-table-column>
        <el-table-column
          label="业务单号"
          align="left"
          width="180"
          show-overflow-tooltip
        >
          <template slot-scope="scope">{{ scope.row.orderNumber }}</template>
        </el-table-column>
        <el-table-column
          label="分公司"
          width="100"
          show-overflow-tooltip
          align="left"
        >
          <template slot-scope="scope">{{ scope.row.companyName }}</template>
        </el-table-column>
        <el-table-column width="120px" align="center" label="名额指标">
          <template slot-scope="scope">
            <span
              style="color: #b1261d; cursor: pointer"
              @click="showMember(scope.row)"
              >{{ scope.row.expertTotals }}</span
            >
          </template>
        </el-table-column>
        <el-table-column width="120px" align="center" label="实际推荐人数">
          <template slot-scope="scope">
            <span
              style="color: #b1261d; cursor: pointer"
              @click="showMember(scope.row)"
              >{{ filterTotal(scope.row) }}</span
            >
          </template>
        </el-table-column>
        <el-table-column
          width="90"
          align="center"
          :label="item.dictLabel"
          v-for="item in prefecturalDicts"
          :key="item.dictValue"
        >
          <template slot-scope="scope">
            <span
              style="cursor: pointer"
              @click="showMember(scope.row, item.dictValue)"
              v-html="filterNumber(item, scope.row)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          align="left"
          width="120"
          label="当前状态"
          :formatter="processStatusFormat"
        ></el-table-column>
        <el-table-column align="center" label="下发时间" width="120">
          <template slot-scope="scope">
            {{ filterDate(scope.row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="left" fixed="right">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.processStatus == '20'"
              type="text"
              @click="showDetail(scope.row, 1)"
              >审核</el-button
            >
            <el-button
              type="text"
              @click="urgeClick(scope.row)"
              v-if="scope.row.processStatus < 20"
              >催办</el-button
            >
            <el-button type="text" @click="showDetail(scope.row)"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <el-table
        @selection-change="handleSelectionChange"
        v-loading="loading"
        ref="multipleTable"
        :data="tableData"
        v-else
      >
        <el-table-column align="center" type="selection"></el-table-column>
        <el-table-column
          align="center"
          label="序号"
          width="55"
          type="index"
        ></el-table-column>
        <el-table-column label="业务单号" align="left" width="180">
          <template slot-scope="scope">{{ scope.row.orderNumber }}</template>
        </el-table-column>
        <el-table-column
          label="单位"
          width="100"
          show-overflow-tooltip
          align="left"
        >
          <template slot-scope="scope">{{ scope.row.deptName }}</template>
        </el-table-column>
        <el-table-column width="100px" align="center" label="名额指标">
          <template slot-scope="scope">
            <span
              style="color: #b1261d; cursor: pointer"
              @click="showMember(scope.row, null, 2)"
              >{{ scope.row.expertTotals }}</span
            >
          </template>
        </el-table-column>

        <el-table-column width="120px" align="center" label="实际推荐人数">
          <template slot-scope="scope">
            <span
              style="color: #b1261d; cursor: pointer"
              @click="showMember(scope.row, null, 2)"
              >{{ filterTotal(scope.row) }}</span
            >
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          :label="item.dictLabel"
          v-for="item in provinceDicts"
          :key="item.dictValue"
        >
          <template slot-scope="scope">
            <span
              style="cursor: pointer"
              @click="showMember(scope.row, item.dictValue, 2)"
              v-html="filterNumber(item, scope.row)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          width="150"
          align="left"
          label="当前状态"
          :formatter="processStatusFormat"
        ></el-table-column>
        <el-table-column align="center" label="下发时间" width="150">
          <template slot-scope="scope">
            {{ filterDate(scope.row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="left" fixed="right">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.processStatus == '20'"
              type="text"
              @click="apply(scope.row, 1)"
              >审核</el-button
            >
            <el-button @click="urgeClick(scope.row)" v-if="scope.row.processStatus < 20" type="text"
              >催办</el-button
            >
            <el-button type="text" @click="apply(scope.row, 2)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px"></div>
      <pagination
        :total="total"
        :page.sync="queryForm.pageNum"
        :limit.sync="queryForm.pageSize"
        @pagination="getList"
      />
      <!-- <div :class="hasScrollbar ? 'pageBox' : 'pageBox hasScrollbar'">
        
      </div> -->
    </div>
    <rs-form :obj="rsFormObj" @close="handleClose"></rs-form>
    <rs-form-apply :obj="applyOpen" @close="handleClose"></rs-form-apply>

    <applyForm
      v-if="applyOpen1.open"
      :dialogVisible="applyOpen1.open"
      :type="applyOpen1.type + ''"
      :obj="applyOpen1.obj"
      @close="handleClose"
    ></applyForm>

    <el-dialog
      title="待办详情"
      :visible.sync="todoInfo"
      width="80%"
      :before-close="handleClose"
      :key="todoInfo"
    >
      <div>
        <mefp-todo-bpm @close="handleClose" :tempId="formData.orderNumber"></mefp-todo-bpm>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  approveSchedule,
  checkWareHousing,
  getRecommendProcessRate,
  exportCompany,
  exportDept,
} from "@/api/talent/recommendSchedule";
import { listCollection, getAllCompany } from "@/api/talent/collection";
import { getDeptList, urge as deptUrge } from "@/api/talent/dept";
import { urge } from "@/api/talent/company";
import rsForm from "./rsForm";
import rsFormApply from "./rsFormApply";
import applyForm from "../recommend/applyForm";
import { parseTime } from "@/utils/ruoyi";
//测试表单，人才汇总待生成列表页
import collectForm from "../collect/collectForm";
import { getDicts } from "@/api/system/dict/data";

import mefpTodoBpm from "@/views/bpmTodo/mefp_todo_bpm";
export default {
  components: { rsForm, rsFormApply, collectForm, applyForm, mefpTodoBpm },
  created() {
    this.handleOpen();
  },
  data() {
    return {
      title: "",
      titleSelect: [],
      multiple: false,
      todoInfo: false,
      loading: false,
      rsFormObj: {
        open: false,
      },
      applyOpen: {
        open: false,
        id: "",
      },
      applyOpen1: {
        open: false,
        type: "1",
      },
      activeName: "1",
      active: 0,
      disabled: true,
      tableData: [],
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
      year: new Date().getFullYear() + "",
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
      queryForm: {
        companyType: "",
        companyId: "",
        deptId: "",
        orderNumber: "",
        colletionId: "",
        processStatus: "",
        pageNum: 1,
        pageSize: 10,
      },
      total: 0,
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [{ dictLabel: "地市公司" }, { dictLabel: "省公司" }], //tab字典
      collectDicts: [], //收集字典
      multipleSelection: [], //表格多选
      ids: [], //表格多选id
      deptQuery: {
        types: [],
        deptSelect: [],
      },
      companyQuery: {
        types: [],
        companySelect: [],
      },
      hasScrollbar: false,
    };
  },
  computed: {
    tableTotal() {
      let count = 0;
      this.tableData.forEach((item) => {
        count += Number(item.expertTotals || 0);
      });
      return count + "人";
    },
  },
  mounted() {
    this.getScrollbar();
    window.onresize = () => {
      this.getScrollbar();
    };
  },
  methods: {
    getScrollbar() {
      this.hasScrollbar =
        document.body.scrollHeight >
        (window.innerHeight || document.documentElement.clientHeight);
    },
    renderHeader(h, { column }) {
      return h(
        "div",
        {
          style: {
            display: "flex",
          },
        },
        [
          h(
            "el-tooltip",
            {
              props: {
                effect: "dark",
                content: column.label,
                placement: "top",
              },
            },
            [
              h(
                "span",
                {
                  style: {
                    display: "inline-block",
                    width: "80px",
                    textOverflow: "ellipsis",
                    overflow: "hidden",
                    whiteSpace: "nowrap",
                  },
                },
                column.label
              ),
            ]
          ),
        ]
      );
    },
    reset() {
      this.queryForm.companyId = "";
      this.queryForm.deptId = "";
      this.queryForm.orderNumber = "";
      this.queryForm.processStatus = "";
      this.getList();
    },
    showMember(row, fieldType, t) {
      let applyOpen = this.applyOpen;
      if (t == 2) {
        applyOpen = this.applyOpen1;
        applyOpen.deptId = row.deptId;
        applyOpen.id = row.colletionId;
        applyOpen.companyName = row.companyName;
        applyOpen.title = this.formData.title;
      } else {
        applyOpen.companyId = row.companyId;
        applyOpen.id = this.formData.id;
        applyOpen.todoTitle = this.formData.title;
        applyOpen.title = "查看";
      }
      applyOpen.orderNumber = row.orderNumber;
      applyOpen.type = 3;
      applyOpen.companyType = row.companyType;
      applyOpen.fieldType = fieldType;

      applyOpen.todoId = this.formData.orderNumber;
      applyOpen.collectionEndDate = this.formData.collectionEndDate;
      if (t == 2) {
        applyOpen.obj = applyOpen;
      }
      applyOpen.open = true;
    },
    //检查是否可以入库
    confirmInterview() {
      if (!this.ids.length) {
        this.$message.warning("请选择需要入库的数据!");
        return;
      }
      checkWareHousing({
        ids: this.ids.join(","),
        companyType: this.activeName == "地市公司" ? 1 : 2,
      }).then((res) => {
        if (res.code == 200) {
          this.rsFormObj.id = this.formData.id;
          this.rsFormObj.collectionType = this.formData.collectionType;
          this.rsFormObj.ids = this.ids.join(",");
          
          if (this.activeName == "地市公司") {
            this.rsFormObj.companyIds = this.multipleSelection
              .map((i) => i.companyId)
              .join(",");
          } else { 
            this.rsFormObj.deptIds = this.multipleSelection
            .map((i) => i.deptId)
            .join(",");
          }
          this.rsFormObj.companyType = this.activeName == "地市公司" ? 1 : 2;

          this.rsFormObj.title = this.formData.title;
          this.rsFormObj.orderNumber = this.formData.orderNumber;
          this.rsFormObj.startInspectTime = this.formData.startInspectTime;
          this.rsFormObj.endInspectTime = this.formData.endInspectTime;
          this.rsFormObj.collectionEndDate = this.formData.collectionEndDate;
          this.rsFormObj.open = true;
        }
      });
    },
    //部门的表单
    apply(row, t) {
      this.applyOpen1.obj = row;
      this.applyOpen1.companyType = "2";
      this.applyOpen1.obj.title = this.formData.title;
      this.applyOpen1.type = t;
      this.applyOpen1.open = true;
    },
    //审核
    approveSchedule() {
      let ids = this.ids.join(",");
      if (!ids) {
        this.$message.warning("请选择需要审核的数据");
        return;
      }
      let flag = false;
      this.multipleSelection.forEach(res => { 
        if(res.processStatus != "20"){
          flag = true;
        }
      })
      if (flag) { 
        this.msgError("批量通过,只能选择分公司已推荐的数据!");
        return;
      }
      approveSchedule({
        ids: ids,
      }).then((res) => {
        loading.close();
        this.$message.success("审批成功");
        this.$emit("close", 200);
      });
    },
    //催办
    urgeClick(row) {
      let ids = this.ids.join(",");
      if (row.id) {
        ids = row.id;
      }
      if (!ids) {
        this.$message.warning("请选择需要催办的数据");
        return;
      }

      //弹窗确认
      this.$confirm("确定催办吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        if (this.activeName == "地市公司") {
          urge(ids).then((res) => {
            if (res.code == 200) {
              this.$message.success("催办成功");
            }
          });
        } else {
          deptUrge(ids).then((res) => {
            if (res.code == 200) {
              this.$message.success("催办成功");
            }
          });
        }
      });
    },
    filterTotal(row) {
      let count = 0;
      row.fieldList.forEach((i) => {
        count += Number(i.recommendNumber || 0);
      });
      return count;
    },
    filterNumber(item, row) {
      let temp = row.fieldList.filter(
        (res) => res.fieldType == item.dictValue
      )[0];
      if (temp) {
        return `<span style='color:#b1261d;'>${temp.recommendNumber}</span> / <span style='color:rgb(51, 51, 51);'>${temp.personNumber}</span>`;
      }
      return "<span style='color:#b1261d;'>0</span> / <span style='color:rgb(51, 51, 51);'>0</span>";
    },
    filterDate(e) {
      return parseTime(e, "{y}-{m}-{d}");
    },
    //状态字典翻译
    processStatusFormat(row, column) {
      if (this.activeName == "地市公司")
        return this.selectDictLabel(this.companyQuery.types, row.processStatus);
      return this.selectDictLabel(this.deptQuery.types, row.processStatus);
    },
    getList() {
      this.loading = true;
      getRecommendProcessRate({
        ...this.queryForm,
        createdTime: this.year + "-01-01",
      }).then((res) => {
        this.tableData = res.rows;
        this.total = Number(res.total);
        this.loading = false;
        this.getScrollbar();
      });
    },
    //表格多选
    handleSelectionChange(e) {
      this.multipleSelection = e;
      this.ids = e.map((res) => res.id);
    },
    //查看
    showDetail(row, t) {
      this.applyOpen.id = row.id;
      this.applyOpen.orderNumber = row.orderNumber;
      this.applyOpen.type = t;
      this.applyOpen.title = t ? "审核" : "查看";
      this.applyOpen.companyType = row.companyType;
      this.applyOpen.open = true;

      this.applyOpen.todoTitle = this.formData.title;
      this.applyOpen.todoId = this.formData.orderNumber;
      this.applyOpen.collectionEndDate = this.formData.collectionEndDate;
    },
    handleClick(e) {},
    handleCompanySelect(e, row) {
      row.companyId = e.companyId;
      row.companyName = e.companyName;
    },
    handleOpen() {
      //表头字典
      getDicts("field_type").then((res) => {
        //地市公司
        this.prefecturalDicts = res.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) == -1
        );
        //省公司
        this.provinceDicts = res.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) != -1
        );
      });
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
      });
      //tab字典
      getDicts("company_type").then((res) => {
        this.tabDicts = res.data;
        this.activeName = res.data[0].dictLabel;
        this.queryForm.companyType = res.data[0].dictValue;
      });
      getAllCompany().then((res) => {
        this.companyQuery.companySelect = res.data;
      });
      getDeptList({
        companyName: "省公司",
      }).then((res) => {
        this.deptQuery.deptSelect = res.data;
      });
      getDicts("company_process_status").then((res) => {
        this.companyQuery.types = res.data.filter(i => i.dictValue != '1');
      });
      getDicts("dept_process_status").then((res) => {
        this.deptQuery.types = res.data.filter(i => i.dictValue != '1');
      });
      this.detailInit();
    },
    detailInit() {
      this.getDetail();
    },
    //获取详情
    getDetail() {
      listCollection({
        createdTime: this.year + "-01-01",
        title: this.title,
      }).then((res) => {
        if (res.code == 200) {
          this.formData = res.rows[0] || {};
          this.title = this.formData.title;
          if (!this.titleSelect.length) {
            this.titleSelect = res.rows || [];
          }
          if (res.rows[0]) {
            this.queryForm.colletionId = res.rows[0].id;
            res.rows[0].companyList.forEach((item) => {
              item.company = {
                companyId: item.companyId,
                companyName: item.companyName,
              };
            });
            res.rows[0].deptList.forEach((item) => {
              item.dept = {
                deptId: item.deptId,
                deptName: item.deptName,
              };
            });
          }
          this.getList();
          // this.prefecturalTableData = res.rows[0].companyList;
          // this.provinceTableData = res.rows[0].deptList;
        }
      });
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "入库管理_人才收集管理 数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    handleClose(e) {
      this.todoInfo = false;
      this.rsFormObj.open = false;
      this.applyOpen.open = false;
      this.applyOpen1.open = false;
      if (e) {
        this.getList();
      }
    },
    handleTabClick(tab) {
      this.$refs.multipleTable.clearSelection();
      this.multipleSelection = [];
      this.ids = [];
      this.queryForm.companyType = this.getTabValueByName();
      this.queryForm.companyId = "";
      this.queryForm.deptId = "";
      this.getList();
    },
    getTabValueByName() {
      let name = this.activeName;
      let tab = this.tabDicts.filter((item) => item.dictLabel == name);
      return tab[0].dictValue;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出各公司指标数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let temp = {
            ...this.queryForm,
            createdTime: this.year + "-01-01",
          };
          if (this.ids.length) {
            temp = {
              ids: this.ids.join(","),
            };
          }
          if (this.activeName == "省公司") {
            return exportDept(temp);
          } else {
            return exportCompany(temp);
          }
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function (err) {
          console.log(err);
        });
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

/deep/ .el-form-item__error {
  width: 280px;
}

.hasScrollbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding-right: 40px;
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
    padding: 10px 20px;
    margin-bottom: 15px;
    position: relative;
    .operation {
      position: absolute;
      right: 0;
      top: 00px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 10px;
      z-index: 9;
    }
  }
}
.app-footer {
  text-align: center;
  background: #fff;
  padding-bottom: 20px;
}
</style>
