<template>
  <div id="ai">
    <div class="tabBox">
      <el-tabs v-model="tabIndex" @tab-click="getData">
        <el-tab-pane label="分析酒店发票集中度模型" name="1"></el-tab-pane>
        <el-tab-pane label="重复报账" name="2"></el-tab-pane>
      </el-tabs>

      <el-table stripe header-cell-class-name="tableHeaderClass" :data="tableData">
        <el-table-column label="序号" type="index"> </el-table-column>
        <el-table-column
          label="地点"
          prop="name"
        ></el-table-column>
        <el-table-column width="80" label="数量" prop="value"></el-table-column>
      </el-table>
    </div>
    <div class="footLine"></div>
  </div>
</template>

<script>
import {
  hotelConcentrationRatio,
  repetitionSubmit,
} from "@/api/aiXuncha/aiHome.js";
export default {
  props:{
    xunchaLunciId:{
      type:String,
      default:""
    }
  },
  watch: {
    xunchaLunciId(newVal) {
      this.getData();
    },
  },
  data() {
    return {
      tabIndex: "1",
      tableData: [
      ],
    };
  },
   mounted() {
    // this.getData({ name: 1 });
  },
  methods: {
    getData() {
      if (this.tabIndex == 1) {
        console.log(this.xunchaLunciId);
        hotelConcentrationRatio(this.xunchaLunciId).then((res) => {
          this.tableData = res.data.filter((i) => i.value > 0);
        });
      } else {
        repetitionSubmit(this.xunchaLunciId).then((res) => {
          this.tableData = res.data.filter((i) => i.value > 0);
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
  /deep/ .el-table__empty-block::after{
    background-image: url(~@/assets/images/newNoData.png) !important;
  }
/deep/.tableHeaderClass {
  color: #51feff !important;
  background-color: transparent !important;
}
/deep/.el-table {
  background-color: transparent !important;
}
/deep/.el-table::before {
  height: 0;
}
/deep/.el-table td.el-table__cell {
  border: 0px;
}
/deep/.el-table tr {
  background-color: transparent !important;
}
/deep/.el-table th.el-table__cell.is-leaf {
  border: 0;
}
/deep/.el-table--striped
  .el-table__body
  tr.el-table__row--striped
  td.el-table__cell {
  background: rgba(162, 207, 255, 0.12);
}
/deep/.el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
  background: rgba(162, 207, 255, 0.12);
}
.tabBox {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 8px;
  /deep/.el-table .cell {
    color: #fff;
  }
  /deep/.el-table__header-wrapper .cell {
    color: #51feff;
  }
  /deep/.el-tabs__item {
    padding: 0 10px /* 15/144 */ !important;
    color: #fff;
    box-sizing: border-box;
  }

  /deep/.is-active {
    transition: 0.3s;
    background: linear-gradient(
      0deg,
      rgba(0, 148, 255, 0.4) 0%,
      rgba(0, 149, 255, 0) 100%
    );
  }

  /deep/.el-tabs__active-bar {
    background: #4cdcf3;
  }
}
.select {
  display: flex;
  flex-direction: row-reverse;
}
.footLine {
  width: calc(100% - 30px);
  height: 5px;
  border-width: 0px 6px 1px 6px;
  border-style: solid;
  border-color: #4cdcf3;
  border-bottom-color: #396281;
  margin: 20px 15px 0;
  box-sizing: border-box;
}
</style>