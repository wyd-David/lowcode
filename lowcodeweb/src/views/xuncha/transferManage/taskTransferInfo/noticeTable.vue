<template>
    <div>
        <div class="dialog-title">
            <span class="line"></span><span>通知记录</span>
          </div>
          <div class="context-row">
            <el-table :data="tableData">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column
                label="发起人"
                prop="createdName"
              ></el-table-column>
              <el-table-column
                label="发起时间"
                prop="createdTime"
              ></el-table-column>
              <el-table-column
                label="通知内容"
                prop="noticeContent"
              ></el-table-column>
              <el-table-column
                label="预计开始通知时间"
                prop="estimateNoticeTime"
              ></el-table-column>
            </el-table>
          </div>
    </div>
</template>

<script>

export default {
  props:{
tableData:{
  type:Array,
  default:[]
}
  },
  data(){
    return{
      recordList: [],

    }
  }
}
</script>

<style scoped lang="scss">

</style>
