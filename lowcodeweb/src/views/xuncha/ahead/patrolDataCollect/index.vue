<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="业务单号:">
            <el-input
              v-model="queryParams.orderNumber"
              placeholder="请输入业务单号"
            ></el-input>
          </el-form-item>
          <el-form-item label="关联巡察任务:">
            <el-select
              clearable
              style="width: 206.4px"
              v-model="queryParams.taskId"
              placeholder="请选择"
            >
              <el-option
                v-for="item in XunchaTaskInfoList"
                :key="item.taskId"
                :label="item.taskName"
                :value="item.taskId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="资料类型:">
            <el-select
              clearable
              style="width: 206.4px"
              v-model="queryParams.dataType"
              placeholder="请选择"
            >
              <el-option
                v-for="item in inventoryType"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="巡察年份:">
            <el-date-picker
              clearable
              @change="changeYear"
              style="width: 206.4px"
              v-model="queryParams.year"
              type="year"
              value-format="yyyy"
              placeholder="选择年"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="巡察批次:">
            <el-select
              clearable
              style="width: 206.4px"
              v-model="queryParams.batchId"
              placeholder="请选择"
              @change="changePici"
            >
              <el-option
                :label="item.piciName"
                v-for="item in selectPiciData"
                :key="item.piciId"
                :value="item.piciId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="收集进度:">
            <el-input
              v-model="queryParams.orderNumber"
              placeholder="请选择"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="list-title">
      <span class="line"></span><span>巡前资料收集</span>
    </div>
    <div class="list">
      <div class="operation">
        <el-button type="primary" size="mini" @click="handleAdd">
          发起收集
        </el-button>
      </div>

      <el-table v-loading="loading" :data="tableData">
        <el-table-column label="序号" type="index" width="55"></el-table-column>
        <el-table-column label="业务单号" width="180" prop="orderNumber">
          <template slot-scope="scope">
            <span v-if="scope.row.processStatus != 1">{{
              scope.row.orderNumber
            }}</span>
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="收集资料类型"
          min-width="140"
          prop="dataType"
          :formatter="inventoryTypeFormat"
        ></el-table-column>
        <el-table-column
          align="center"
          label="资料说明"
          prop="dataRemark"
        ></el-table-column>
        <el-table-column
          align="center"
          label="巡察年份"
          prop="year"
        ></el-table-column>
        <el-table-column align="center" label="巡察批次"></el-table-column>
        <el-table-column
          label="关联巡察任务"
          prop="taskName"
          show-overflow-tooltip
          min-width="120"
        ></el-table-column>
        <el-table-column align="center" label="分发任务数" min-width="120">
          <template slot-scope="scope">
            <el-button v-if="scope.row.processStatus != 1" type="text">{{
              scope.row.totalTaskNumber
            }}</el-button>
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column label="收集进度" align="center">
          <template slot-scope="scope">
            <img
              v-if="scope.row.processStatus != 1"
              :src="img['img' + scope.row.processStatus]"
              width="20"
              height="20"
            />
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="已完成任务数" min-width="120">
          <template slot-scope="scope">
            <el-button v-if="scope.row.processStatus != 1" type="text">{{
              scope.row.finishTaskNumber
            }}</el-button>
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="未完成任务数" min-width="120">
          <template slot-scope="scope">
            <el-button v-if="scope.row.processStatus != 1" type="text">{{
              scope.row.unFinishTaskNumber
            }}</el-button>
            <span v-else>--</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          min-width="150"
          align="center"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="getDetail(scope.row)"
              >查看</el-button
            >
            <el-button
              type="text"
              v-if="scope.row.processStatus == 1"
              size="small"
              @click="handleRemove(scope.row)"
              >删除</el-button
            >
            <el-button
              type="text"
              v-if="scope.row.processStatus == 1"
              size="small"
              @click="handleUpdate(scope.row)"
              >修改</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>

    <collect-form :obj="collectForm" @handleClose="handleClose"></collect-form>
  </div>
</template>

<script>
import {
  queryPiciByYeay,
  listXunchaTaskInfo,
} from "@/api/xuncha/XunchaTaskInfo";
import { remove } from "@/api/xuncha/ahead/patrolDataCollect";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import collectForm from "./collectForm";
import { getDicts } from "@/api/system/dict/data";
import { list } from "@/api/xuncha/ahead/patrolDataCollect";
import { timingSafeEqual } from "crypto";
export default {
  components: { ExpandCollapse, collectForm },
  data() {
    return {
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: "",
        dataType: "",
        year: "",
        batchId: "",
        taskId: "",
      },
      loading: false,
      tableData: [],
      selectPiciData: [],
      XunchaTaskInfoList: [],
      inventoryType: [],
      total: 0,
      collectForm: {
        dialogVisible: false,
        title: "巡前资料收集",
        id: "",
        disabled: false,
      },
      img: {
        img10: require("@/assets/images/type10.png"),
        img100: require("@/assets/images/type100.png"),
      },
    };
  },
  mounted() {
    getDicts("inventory_type").then((res) => {
      this.inventoryType = res.data || [];
      this.getList();
    });
  },
  methods: {
    getDetail(row) {
      this.collectForm = {
        dialogVisible: true,
        title: "查看巡前资料收集",
        id: row.id,
        disabled: true,
      };
    },
    handleRemove(row) {
      this.$confirm("确定删除吗", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        remove({ id: row.id }).then((res) => {
          if (res.code == 200) {
            this.msgSuccess("删除成功");
            this.getList();
            return;
          }
          this.msgError(res.msg);
        });
      });
    },
    handleUpdate(row) {
      this.collectForm = {
        dialogVisible: true,
        title: "修改巡前资料收集",
        id: row.id,
        disabled: false,
      };
    },
    inventoryTypeFormat(row, column) {
      return this.selectDictLabel(this.inventoryType, row.datatype);
    },
    changeYear() {
      // 获得批次下拉框数据
      queryPiciByYeay(this.queryParams.year).then((res) => {
        this.selectPiciData = res.data;
        this.XunchaTaskInfoList = [];
        this.queryParams.batchId = "";
        this.queryParams.taskId = "";
      });
    },
    changePici() {
      listXunchaTaskInfo({
        year: this.queryParams.year,
        piciId: this.queryParams.batchId,
        pageSize: 999,
      }).then((response) => {
        this.XunchaTaskInfoList = response.rows || [];
        this.queryParams.taskId = "";
        this.loading = false;
      });
    },
    getList() {
      list(this.queryParams).then((res) => {
        if (res.code == 200) {
          this.tableData = res.rows || [];
          this.total = Number(res.total);
        }
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.getList();
    },
    handleAdd() {
      this.collectForm = {
        dialogVisible: true,
        title: "巡前资料收集",
        id: "",
        disabled: false,
      };
    },
    handleClose(e) {
      if (e == 200) {
        this.getList();
      }
      this.collectForm.dialogVisible = false;
    },
  },
};
</script>

<style></style>
