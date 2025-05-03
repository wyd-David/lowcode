<template>
  <div class="list">
    <slot></slot>
    <el-table
      :data="data"
      :header-cell-style="{ 'text-align': 'center' }"
      :cell-style="{ 'text-align': 'center' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index"></el-table-column>
      <el-table-column
        v-for="item in tableColumnList"
        :key="item.prop"
        :prop="item.prop"
        :label="item.label"
        align="center"
      ></el-table-column>

      <el-table-column label="小巡同步状态" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.indexStatus === '0'">未同步</span>
            <span v-if="scope.row.indexStatus === '1'">已同步</span>
            <span v-if="scope.row.indexStatus === '2'">半同步(仅名称)</span>
          </template>
        </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="onPreview(scope.row.fileId, scope.row.applyId)"
            >预览</el-button
          >
          <el-button
            size="mini"
            type="text"
            @click="onDownload(scope.row.fileId, scope.row.applyId)"
            >下载</el-button
          >
          <el-button
            size="mini"
            type="text"
            @click="handleQueryOption(scope.row)"
            >查看</el-button
          >
          <!-- <el-button size="mini" type="text" @click="handleSync(scope.row)"
              >同步</el-button
            > -->
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import tableColumnList from "@/utils/tableColumnList.js";
import { updateDownloadSum, preview } from "@/api/xuncha/archives/inStorage";
import {
  saveRegulationIndexsPoolByBusinessKey,
} from "@/api/xuncha/archives/synchronization";
import {
  downloadFile,
  previewFile,
  downloadZipFile,
} from "@/utils/uploadOperate";

export default {
  props: {
    data: {
      type: Array,
      default: () => [],
    },
    tabType: {
      type: String,
      default: "",
    },
    queryParams: {
      type: Object,
    },
    total: {
      type: Number,
    },
  },
  data() {
    return {
      tableColumnList: tableColumnList.central,
      multipleSelection: [],
    };
  },
  watch: {
    tabType(newVal) {
      switch (newVal) {
        case "中央制度":
          this.tableColumnList = tableColumnList.central;
          break;
        case "集团公司制度":
          this.tableColumnList = tableColumnList.company;
          break;
        case "省公司制度":
          this.tableColumnList = tableColumnList.provice;
          break;
      }
    },
  },
  mounted() {},
  methods: {
    handleSync(row) {
      saveRegulationIndexsPoolByBusinessKey({ id: row.applyId }).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "同步成功",
            type: "success",
          });
        }
      });
    },
    handleQueryOption(row) {
      this.$emit("getInfo", row);
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.$emit('select',this.multipleSelection)
    },
    // 预览
    onPreview(fileId, applyId) {
      previewFile(fileId);
      preview({applyId})
    },
    // 下载
    onDownload(fileId, applyIds) {
      downloadFile(fileId);
      updateDownloadSum({applyIds})
    },
  },
};
</script>

<style lang="scss">
</style>
