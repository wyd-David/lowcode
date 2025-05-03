<template>
  <div >
    <div class="dialog-title">
      <span class="line"></span><span>流程轨迹</span>
    </div>
    <div class="context-row">
      <el-table
        :data="allFlowStep">
        <el-table-column label="序号" align="center" width="80" type="index"></el-table-column>
        <el-table-column label="环节"  prop="name"></el-table-column>
        <el-table-column label="处理人"  prop="todoUserLoginId"></el-table-column>
        <el-table-column label="处理时间" prop="endTime"></el-table-column>
        <el-table-column label="处理结果" prop="btnMessage">
          <template slot-scope="scope">
              <span>{{scope.row.btnMessage||scope.row.message}}</span>
          </template>
        </el-table-column>
        <el-table-column label="处理意见">
          <template slot-scope="scope">
              <span>{{scope.row.option||scope.row.content}}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>

export default {
  props: {
    allFlowStep: {
      type: Array,
      default: () => { 
        return [];
      },
    }
  },


};
</script>

