<!--
 * @Author: your name
 * @Date: 2021-11-27 15:31:06
 * @LastEditTime: 2021-11-27 16:28:43
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \server_ui_pc\src\views\system\systemMessage\index.vue
-->
<template>
  <div class="content">
    <div style="background-color: #fff;padding:20px;">
      <el-form inline>
        <el-form-item label="标题">
          <el-input
            size="mini"
            v-model="value"
            style="200px"
            placeholder="请输入标题查询"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="mini"
            @click="getSysNewsPushLog"
            >搜索</el-button
          >
          <el-button size="mini" @click="value = ''"
            >重置</el-button
          >
          <el-button
            type="primary"
            size="mini"
            @click="updateMessageStatus"
            >一键已读</el-button
          >
        </el-form-item>
      </el-form>
      <el-table stripe :data="formList">
        <el-table-column label="标题">
          <template slot-scope="scope">
            <span style="color:#4095df;cursor:pointer" @click="showContent(scope.row)">
            【{{ scope.row.applyName }}】   {{ scope.row.templateTitle }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            {{ ["未读", "已读"][scope.row.readType - 1] }}
          </template>
        </el-table-column>
        <el-table-column label="发布时间">
          <template slot-scope="scope">
            {{ scope.row.createTime }}
          </template>
        </el-table-column>
        <el-table-column label="阅读时间">
          <template slot-scope="scope">
            {{ scope.row.updateTime }}
          </template>
        </el-table-column>
      </el-table>
      <div class="block page">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNo"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
    <el-dialog
      title="消息内容"
      :visible.sync="dialogVisible"
      width="30%">
      <div v-html="curTextContent"></div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listSysNewsPushLog,updateSysNewsPushLog2,updateSysNewsPushLogBySendOutBy } from "@/api/system/SysNewsPushLog/SysNewsPushLog";
export default {
  data() {
    return {
      dialogVisible: false,
      curTextContent: '',
      value: "",
      total: "",
      formList: "",
      pageNo:1,
      pageSize:10
    };
  },
  created() {
    this.getSysNewsPushLog();
  },
  methods: {
    updateMessageStatus() {
      const loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      updateSysNewsPushLogBySendOutBy().then(res => {
        this.getSysNewsPushLog();
        loading.close();
        this.$EventBus.$emit("updateMessage");
      })
    },
    showContent(data) {
      this.curTextContent = data.sendOutText || '';
      updateSysNewsPushLog2({
        id: data.id,
        readType: 2
      }).then(res => {
        this.getSysNewsPushLog();
      })
      this.dialogVisible = true;
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getSysNewsPushLog();
    },
    handleCurrentChange(val) {
      this.pageNo = val;
      this.getSysNewsPushLog();
    },
    getSysNewsPushLog() {
      listSysNewsPushLog({templateTitle:this.value, pageNum: this.pageNo, pageSize: this.pageSize }).then((res) => {
        this.total = res.total;
        this.formList = res.rows;
      });
    },
  },
};
</script>

<style lang="scss" scope>
.content {
  padding: 20px;
  .page{
    margin-top: 10px;
    text-align: end;
  }
}
</style>
