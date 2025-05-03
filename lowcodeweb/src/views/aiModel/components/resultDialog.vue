<template>
  <div class="ai_dialog">
    <el-dialog
      :visible.sync="dialogVisible"
      top="8vh"
      width="1200px"
      :close-on-click-modal="false"
      @close="handleClose"
      custom-class="history"
      :modal-append-to-body="false"
      @open="handleOpen"
    >
      <div class="title" slot="title">
        <span>详情</span>
      </div>
      <!-- <img class="background" src="~@/assets/images/ai/BG.png" alt="" /> -->
      <div style="height: 70vh">
        <el-scrollbar style="height: 100%">
          <el-table
            stripe
            header-cell-class-name="tableHeaderClass"
            key="table2"
            :data="analysisData"
            v-loading="analysisType"
          >
            <el-table-column label="序号" type="index" width="90" align="left">
              <template slot-scope="scope">
                <div class="indexIcon">{{ scope.$index + 1 }}</div>
              </template>
            </el-table-column>
            <el-table-column label="姓名" prop="fullName" align="center">
            </el-table-column>
            <el-table-column label="员工ID" prop="userId" align="center">
            </el-table-column>
            <el-table-column label="公司" prop="deptName" align="center">
            </el-table-column>
            <el-table-column label="职级" prop="hierarchy" align="center">
            </el-table-column>
            <el-table-column
              label="出差申请单号"
              prop="tripCode"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="出差开始时间"
              prop="tripBeginDate"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="出差结束时间"
              prop="tripEndDate"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="出发城市"
              prop="departureCity"
              align="center"
            >
            </el-table-column>
            <el-table-column label="到达城市" prop="targetCity" align="center">
            </el-table-column>
            <el-table-column label="说明" prop="remark" align="center">
            </el-table-column>
          </el-table>
          <div class="pageBox">
            <pagination
              :total="Number(total)"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="ontabs(tabindex, 1)"
            />
          </div>
        </el-scrollbar>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getModelData,
  getJSON,
  startOneAnalyse,
  validateAnalyse,
} from "@/api/aiXuncha/aiBaseInfo.js";
export default {
  props: {
    row: {
      type: Object,
    },
    dialogVisible: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      analysisType: false,

      analysisData: [],
      total: null,
    };
  },
  created() {
  },
  methods: {
    handleOpen() {
      this.analysisFunc();
    },
    handleClose() {
      this.$emit("close");
    },
    analysisFunc() {
      this.analysisType = true;
      console.log(this.row);
      getJSON("/ai/aiResultTravelingDocumentsInfo/list", {
        companyName: this.row.companyName,
        timeId: this.row.timeId,
        deptName: this.row.deptName,
        hierarchy: this.row.hierarchy,
        ...this.queryParams,
      }).then((res) => {
        this.total = res.total;
        this.analysisType = false;
        this.analysisData = res.rows;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
  /deep/ .el-table__empty-block::after{
    background-image: url(~@/assets/images/newNoData.png) !important;
  }
/deep/.el-dialog__header {
  .el-dialog__headerbtn {
    z-index: 100;
    &:hover .el-dialog__close {
      color: #46a1ff;
    }
  }
}
/deep/ .el-scrollbar__wrap {
  overflow-x: hidden;
}
/deep/.el-select-dropdown .el-scrollbar__wrap {
  overflow: scroll;
}
/deep/.el-select-dropdown .el-scrollbar__wrap {
  overflow: scroll;
}
/deep/.history .el-dialog__body {
  overflow: hidden;
  min-height: 536px;
}
/deep/.el-upload:focus .el-upload-dragger {
  border-color: #46a1ff;
}
/deep/.el-upload-dragger {
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
.flex {
  display: flex;
  align-items: center;
}
.download_btn {
  font-size: 12px;
  padding: 8px 14px;
  margin-left: 10px;
}
.btntabs {
  background-image: url("~@/assets/images/ai/btnnormal.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  color: #fff;
  padding: 0.6rem 1rem;
  font-size: 14px;
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
/deep/.el-dialog {
  background: transparent;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-image: url("~@/assets/images/ai/BG.png");
  padding: 2.2rem;
}
/deep/.el-dialog__header {
  position: relative;
}
/deep/.el-dialog__footer {
  background: transparent;
}
.ai_dialog .dialog-title {
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
</style>