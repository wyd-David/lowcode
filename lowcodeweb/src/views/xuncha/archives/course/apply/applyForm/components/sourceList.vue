<template>
  <div>
    <el-table :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column type="index" label="序号"/>
      <el-table-column prop="archiveScope" align="center" label="归档范围"/>
      <el-table-column prop="contentNo" align="center" label="序号"/>
      <el-table-column prop="archiveType" align="center" label="类别" :formatter="archiveTypeFormat" min-width="100px"/>
      <el-table-column prop="xunchaStage" align="center" label="对应巡察阶段" :formatter="xunchaStageFormat" min-width="100px"/>
      <el-table-column prop="archiveOrgType" align="center" label="归档组织" :formatter="archiveOrgTypeFormat"/>
      <el-table-column prop="requireFlag" align="center" label="必传">
        <template slot-scope="scope">
          <span v-if="scope.row.requireFlag === '0'">非必传</span>
          <span v-else>必传</span>
        </template>
      </el-table-column>
      <el-table-column prop="archiveDate" align="center" label="资料移交时间" min-width="100px"/>
    </el-table>
    <!-- <div class="pageBox">
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div> -->
  </div>
</template>

<script>
  import { getSourceList } from '@/api/xuncha/archives/xunchaProcessContent.js'
  import { queryDictList } from '@/api/xuncha/common/base'

  export default {
    props: {
      tableData: {
        type: Array,
        default: []
      }
    },
    data() {
      return {
        multipleSelection: [],
        // 入库资料类型字典
        archiveTypeOptions: [],
        // 巡察阶段字典
        xunchaStageOptions: [],
        // 归档组织字典
        archiveOrgTypeOptions: []
      }
    },
    mounted() {
      // getSourceList(this.params).then((res) => {
      //   console.log(res);
      //   this.tableData = res.rows
      // });
    },
    created() {
      queryDictList({ dictType: 'process_apply_archive' }).then(res => {
        this.archiveTypeOptions = res.data || []
      })
      this.getDicts('sys_archive_type').then(response => {
        this.xunchaStageOptions = response.data || []
      })
      this.getDicts('process_content_archive').then(response => {
        this.archiveOrgTypeOptions = response.data || []
      })
    },
    methods: {
      // 入库资料类型字典翻译
      archiveTypeFormat(row, column) {
        for (let i = 0; this.archiveTypeOptions.length > i; i++) {
          if (row.archiveType === this.archiveTypeOptions[i].dataCode) {
            return this.archiveTypeOptions[i].dataName
          }
        }
        return row.archiveType
      },
      // 巡察阶段字典翻译
      xunchaStageFormat(row, column) {
        return this.selectDictLabel(this.xunchaStageOptions, row.xunchaStage)
      },
      // 归档组织字典翻译
      archiveOrgTypeFormat(row, column) {
        return this.selectDictLabel(this.archiveOrgTypeOptions, row.archiveOrgType)
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      }
    }
  }
</script>

<style>
</style>
