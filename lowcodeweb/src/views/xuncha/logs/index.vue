<template>
  <div>
    <el-dialog
      @open="handleOpen"
      title="操作日志"
      :visible.sync="dialogVisible"
      width="1000px"
      :before-close="handleClose"
    >
      <div>
        <div class="dialog-title">
          <span class="line"></span><span>操作日志</span>
        </div>
        <div class="context-row">
          <el-table v-loading="loading" :data="tableData">
            <el-table-column
              label="序号"
              type="index"
              align="center"
            ></el-table-column>
            <el-table-column label="操作人" prop="username" align="center"></el-table-column>
            <el-table-column label="操作人账号" prop="operName" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                {{['其它','新增','修改','删除'][scope.row.businessType]}}
              </template>
            </el-table-column>
            <el-table-column label="操作内容" prop="operParam" show-overflow-tooltip align="left"></el-table-column>
            <el-table-column label="操作时间" prop="operTime" align="center">
              <template slot-scope="scope">
                  <span>{{
                    parseTime(scope.row.operTime, "{y}-{m}-{d} {h}:{i}:{s}")
                  }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <pagination v-show="total > 0" :total="total" :page.sync="pageNum"
                    :limit.sync="pageSize" @pagination="getList"/>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {getLogs} from "@/api/xuncha/logs";

  export default {
    props: {
      dialogVisible: {
        type: Boolean,
        default: false,
      },
      id: {
        type: String,
        default: "",
      },
      requestMapping: {
        type: String,
        default: "",
      },
    },
    data() {
      return {
        tableData: [],
        loading: false,
        // 总条数
        total: 0,
        pageNum: 1,
        pageSize: 10,

      };
    },
    methods: {
      getList() {
        this.loading = true;
        getLogs(this.requestMapping, {id: this.id}).then((res) => {
          this.tableData = res.rows || [];
          this.total = +res.total
          this.loading = false;
        });
      },
      handleClose() {
        this.$emit("close");
      },
      handleOpen() {
        this.getList();
      },
    },
  };
</script>

<style lang="scss">
  .el-tooltip__popper{ max-width:50% }
</style>
