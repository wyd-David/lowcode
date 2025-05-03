<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="120px"
        >
          <el-form-item label="业务单号:">
            <el-input
              style="width: 220px"
              v-model="queryParams.applyId"
              placeholder="请输入业务单号"
            ></el-input>
          </el-form-item>
          <el-form-item label="当前状态:">
            <el-select
              style="width: 220px"
              v-model="queryParams.status"
              placeholder="请选择"
            >
              <el-option
                v-for="item in statusType"
                :label="item.name"
                :value="item.value"
                :key="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="制度名称:">
            <el-input
              style="width: 220px"
              v-model="queryParams.regulationName"
              placeholder="请输入制度名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="文号:">
            <el-input
              style="width: 220px"
              v-model="queryParams.docCode"
              placeholder="请输入文号"
            ></el-input>
          </el-form-item>
          <el-form-item label="制度层级:">
            <el-select
              style="width: 220px"
              v-model="queryParams.applyType"
              placeholder="请选择"
            >
              <el-option
                v-for="item in contentTypes"
                :label="item.name"
                :key="item.value"
                :value="item.value"
              ></el-option>
            </el-select>
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
      <span class="line"></span><span>巡察制度流程文件入库申请</span>
    </div>
    <div class="list">
      <div class="operation">
        <el-button type="primary" size="mini" @click="openForm">
          发起资料入库
        </el-button>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </div>
      <el-table
        :data="tableData"
      >
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column
          prop="applyId"
          align="left"
          label="业务单号"
          width="240"
        ></el-table-column>
        <el-table-column
          prop="applyTypeName"
          label="制度层级"
        ></el-table-column>
        <el-table-column prop="regulationName" label="制度名称">
        </el-table-column>
        <el-table-column label="文号"><template slot-scope="{row}">{{row.docCode||'无'}}</template></el-table-column>
        <el-table-column
          :formatter="statusTypeFormat"
          label="当前状态"
        ></el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" @click="openForm(scope.row, 2)"
              >查看</el-button
            >
            <el-button type="text" @click="delCell(scope.row)" v-if="(scope.row.status=='draft' ) && scope.row.createdBy==$store.getters.name">删除</el-button>
            <el-button type="text" v-if="(scope.row.status=='draft' || scope.row.status=='back') && scope.row.createdBy==$store.getters.name" @click="openForm(scope.row, 1)"
              >修改</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <add-dialog
      :obj="dialogObj"
      :dialogVisible="dialogObj.open"
      @close="handleClose"
    ></add-dialog>
  </div>
</template>

<script>
import ExpandCollapse from "@/components/ExpandCollapse/index";
import addDialog from "./addDialog";

import { list ,deleteById} from "@/api/xuncha/archives/inStorage";
export default {
  components: { ExpandCollapse, addDialog },
  data() {
    return {
      dialogObj: {
        id: "",
        open: false,
        title:null
      },
      // 显示搜索条件
      showSearch: true,
      //总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyId: "",
        status: "",
        regulationName: "",
        orderByColumn: "created_time",
        isAsc: "desc",
        applyType: "",
        docCode:""
      },
      tableData: [],
      //流程状态
      statusType: [
        { name: "草稿", value: "draft" },
        { name: "审批中", value: "checking" },
        { name: "已结束", value: "complete" },
        { name: "退回", value: "back" },
      ],
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
    };
  },
  created() {
    this.getList();
  },
  methods: {
    delCell(row) {
      deleteById({applyId:row.applyId}).then(res=>{
          if(res.code==200){
            this.msgSuccess(res.msg)
            this.getList()
          }
        })
    },
    openForm(row, t) {
      if (t) {
        this.dialogObj.id = row.applyId;
      }
      if (t == 1) {
        this.dialogObj.title = "修改";
        this.dialogObj.operationType = 3;

      } else if (t == 2) {
        this.dialogObj.title = "查看";
        this.dialogObj.operationType = 2;

      } else {
        this.dialogObj.title = "新增";
        this.dialogObj.operationType = 1;

      }
      this.dialogObj.open = true;
    },
    statusTypeFormat(row) {
      return this.statusType.filter((res) => res.value == row.status)[0].name;
    },
    handleQuery() {
      this.getList();
    },
    resetQuery() {
            Object.assign(this.$data.queryParams, this.$options.data().queryParams);
      this.getList();
    },
    getList() {
      list(this.queryParams).then((res) => {
        if (res.code == 200) {
          this.tableData = res.rows;
          this.total = Number(res.total);
        }
      });
    },
    handleClose(code) {
      this.dialogObj.open = false;
      this.dialogObj.id = null;
      if (code==200) {
        this.getList()
      }
    },
  },
};
</script>
<style lang="scss" scoped></style>
