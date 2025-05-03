<template>
  <div class="app-container">
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="120px"
        >
          <el-form-item label="业务单号：" prop="businessKey">
            <el-input
              v-model="queryParams.businessKey"
              placeholder="请输入单号"
              style="width: 200px"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="工单类型：" prop="businessType">
            <el-select
              v-model="queryParams.businessType"
              placeholder="请选择类型"
              style="width: 200px"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in businessTypes"
                :key="dict.dictKey"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="名称：" prop="businessName">
            <el-input
              v-model="queryParams.businessName"
              placeholder="请输入名称"
              style="width: 200px"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="发起时段：" prop="status">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 200px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="状态：" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="请选择状态"
              clearable
              size="small"
            >
              <el-option
                v-for="item in statusValues"
                :label="item.name"
                :key="item.value"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right" >
        <el-button type="primary" size="mini" @click="handleQuery"
          >搜索</el-button
        >
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="list-title">
      <span class="line"></span><span>我的发起</span>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>
      <div class="list">
        <el-table v-loading="loading" :data="procinstList">
          <el-table-column
            label="业务单号"
            width="180"
            :show-overflow-tooltip="true"
            prop="businessKey"
          />
          <el-table-column
            label="工单类型"
            min-width="130px"
            prop="businessTypeName"
          />
          <el-table-column label="名称" min-width="140px" :show-overflow-tooltip="true" prop="businessName" />
          <el-table-column label="发起时间" prop="createdTime" width="180">
            <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.createdTime, "{y}-{m}-{d} {h}:{m}:{s}")
              }}</span>
            </template>
          </el-table-column>
          <el-table-column label="当前状态" align="center" prop="status" />
          <el-table-column
            label="当前处理人"
            align="center"
            max-width="120px"
            prop="todoHandlerName"
          />
          <el-table-column
            label="最后更新时间"
            align="center"
            prop="lastUpdateTime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.lastUpdateTime, "{y}-{m}-{d} {h}:{m}:{s}")
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            fixed="right"
            align="left"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-if="scope.row.delete==true">删除</el-button>
              <el-button size="mini" type="text" @click="handleClick(scope.row)"
                >查看</el-button
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
      <!-- <div class="pageBox">
        
      </div> -->
    </div>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { list,deleteDraftOrder } from "@/api/system/procinst/procinst.js";

import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from "@/utils/index";
export default {
  name: "Procinst",
  components: { ExpandCollapse },
  data() {
    return {
      disabled: false,
      // 遮罩层
      loading: true,
      // 是否显示查看弹出层
      isOpenQuery: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 我的工单表格数据
      procinstList: [],
      // 是否显示弹出层
      open: false,
      businessTypes: [],
      detailObj: {},
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        // businessTypeObj: {},
        pageNum: 1,
        pageSize: 10,
        businessKey: null,
        businessType: null,
        businessName: null,
        status: null,
        createdStartTime: null,
        createdEndTime: null,
        procinstId:null
      },
      // 表单校验
      rules: {
        businessKey: [
          { required: true, message: "单号不能为空", trigger: "blur" },
        ],
      },
      statusValues: [
        {
          value: 0,
          name: "未处理",
        },
        {
          value: 1,
          name: "已处理",
        },
      ],
    };
  },
  filters: {
    filterDate(val) {
      return formatDate(val);
    },
  },
  created() {
    this.getDicts("apply_id").then((response) => {
      this.businessTypes = response.data || [];
      if (this.$route.query) {
        let {query} = this.$route
        for(let key in query)
        this.queryParams[key] = query[key]
      }
      this.getList();
    });
  },
  methods: {
    handleDelete(row) { 
      //弹窗确认
      this.$confirm("确认删除该工单吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteDraftOrder({procinstId:row.procinstId,businessKey:row.businessKey,businessType:row.businessType}).then((response) => {
            this.$message({
              message: "删除成功",
              type: "success",
            });
            this.getList();
          });
        })
    },
    /** 查询我的工单列表 */
    getList() {
      this.loading = true;
      // this.queryParams.businessType =
      //  this.queryParams.businessTypeObj.dictLabel;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          console.log(response.rows)
          this.procinstList = response.rows || [];
          this.total = Number(response.total);
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        procinstId: null,
        businessKey: null,
        businessType: null,
        businessName: null,
        status: "0",
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      // this.queryParams.businessTypeObj = {};
      this.statusValues = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.procinstId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleClick(row) {
      this.detailObj = row;
      window.open(row.todoUrl);
    },
  },
};
</script>



<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
}
.search-folder {
  // margin: 0 -16px;
  // padding: 0 16px;
  padding: 15px;
  position: relative;
  height: 60px;
  overflow-y: hidden;
  display: flex;
  background: #fff;
  border-radius: 2px;
  .ivu-icon {
    // position: absolute;
    left: 2px;
    top: 6px;
    color: #3a89da;
    z-index: 10;
    font-size: 18px;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
  &.folder-open {
    height: auto;
  }
  &-utils {
    display: flex;
    height: 32px;
    align-items: center;
    margin-left: 10px;
    p {
      cursor: pointer;
      width: 32px;
      margin-left: 10px;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #3a89da;
    }
  }
}
.utlsBtn {
  /deep/ .el-button--mini {
    padding: 9px 15px;
  }
  .normalBtn {
    /deep/ .el-button--mini {
      padding: 8px;
    }
  }
}
.container {
  padding: 12px;
  background: #fff;
  border-radius: 2px;
  
  .pageBox {
    height: 32px;
    .pagination-container {
      padding: 0 !important;
      margin: 0;
      height: 32px;
      margin-top: 12px;
      /deep/ .number,
      /deep/ .btn-prev,
      /deep/ .btn-next {
        width: 32px;
        height: 32px;
        line-height: 31px;
        background: #fff;
        border-radius: 4px;
        font-size: 14px;
        font-family: HelveticaNeue;
        color: rgba(0, 0, 0, 0.65);
        border: 1px solid rgba(23, 35, 61, 0.15);
        font-weight: normal;
      }
      /deep/ .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
    }
    /deep/ .el-pagination__total {
      line-height: 32px;
    }
  }
}
/deep/ .el-form-item__label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: rgba(23, 35, 61, 0.75);
}
</style>

