<template>
  <div class="app-container">
    <permission-tabs oaItemsShow @ptClick="ptClick"></permission-tabs>
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
          <template v-if="tabType == '中央制度'">
            <el-form-item label="文件名称:">
              <el-input v-model="queryParams.fileName" placeholder="请输入文件名称" size="small"></el-input>
            </el-form-item>
          </template>
          <template>
            <group-company-search v-if="tabType != '中央制度'" :queryParams="queryParams"/>
          </template>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="list-title">
      <span class="line"></span><span>巡察制度库表</span>
    </div>

    <regulation-table
      ref="tableRef"
      :tabType="tabType"
      :data="tableData"
      @getInfo="openInfoForm"
      @select="tableChange"
    >
      <div class="operation">
        <!-- <el-button v-hasPermi="['regulation:prem:save']" :disabled="selectRowArr.length !== 1" type="primary" size="mini" @click="openForm">
          设置可见范围
        </el-button> -->
        <el-button type="primary" :disabled="selectRowArr.length === 0" size="mini" @click="batchDownload">
          批量下载
        </el-button>


        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </div>
    </regulation-table>
    <pagination
      v-show="total > 0"
      :total="Number(total)"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 可见范围 -->
    <visible-dialog
      @close="handleClose"
      :dialogVisible="isOpen"
      :formData="selectRow"
    />

    <el-dialog
      :close-on-click-modal="false"
      title="提示"
      :visible.sync="dialogObj.open"
      width="70%"
      :before-close="infoClose"
    >
      <div class="dialog-content">
        <apply-form
          ref="applyForm"
          :obj="dialogObj"
          v-if="dialogObj.open"
          @close="infoClose"
        >
          <el-row :gutter="24">
            <el-col :span="24">
              <el-form-item label="可见范围：">
                <el-input
                  v-model="getInfoRow.orgName"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </apply-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import PermissionTabs from "@/components/PermissionTabs/index";
  import RegulationTable from "./regulationTable.vue";
  import GroupCompanySearch from "./groupCompanySearch.vue";
  import VisibleDialog from "./visibleDialog.vue";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {applyFileList} from "@/api/xuncha/archives/inStorage";
  import ApplyForm from "../inStorage/applyForm.vue";
  import {saveTimelyRegulationIndexsPool,} from "@/api/xuncha/archives/synchronization";
  import {downloadZipFile,} from "@/utils/uploadOperate";
  import {updateDownloadSum} from "@/api/xuncha/archives/inStorage";

  export default {
    data() {
      return {
        tabType: "",
        typeNum: 1,
        tableData: [],
        isOpen: false,
        dialogInfoVisible: false,
        dialogObj: {
          id: "",
          open: false,
          operationType: 2,
        },

        selectRow: {
          regulationName: null,
          docCode: null,
          fileName: null,
          orgId: [],
        },
        selectRowArr: [],
        getInfoRow: {orgName: ''},
        // 显示搜索条件
        showSearch: true,
        total: 0,
        queryParams: {
          firstContent: {
            children: [],
          },
          secondContent: {
            children: [],
          },
          thirdContent: {
            children: [],
          },
          fileName: null,
          pageNum: 1,
          pageSize: 10,
          firstContentName: null,
          regulationName: null,
          docCode: null,
          execEffeEvaluation: null,
          groupAbolitionPlan: null
        },
      };
    },
    components: {
      PermissionTabs,
      RegulationTable,
      ExpandCollapse,
      GroupCompanySearch,
      VisibleDialog,
      ApplyForm,
    },
    mounted() {
      this.getList();
    },
    methods: {
      // 批量下载
      batchDownload() {
        // 记录下载量
        let files = this.selectRowArr.map(i => i.fileId)
        let applyIds = this.selectRowArr.map(i => i.applyId).join(',')
        updateDownloadSum({applyIds})
        downloadZipFile(files);
      },
      // 多选
      tableChange(val) {
        this.selectRowArr = val
      },
      handleSync() {
        saveTimelyRegulationIndexsPool().then((res) => {
          if (res.code == 200) {
            this.$message({
              message: "同步成功",
              type: "success",
            });
          }
        });
      },
      ptClick(title) {
        this.tabType = title;
        switch (title) {
          case "中央制度":
            this.typeNum = 1;
            this.getList(1);
            break;
          case "集团公司制度":
            this.typeNum = 2;
            this.getList(2);
            break;
          case "省公司制度":
            this.typeNum = 3;

            this.getList(3);
            break;
        }
      },
      getList() {
        let params = {
          // orderByColumn: "created_time",
          // isAsc: "desc",
          applyType: this.typeNum,
        };
        params.firstContentId = this.queryParams.firstContent.contentId;
        params.secondContentId = this.queryParams.secondContent.contentId;
        params.thirdContentId = this.queryParams.thirdContent.contentId;
        params.regulationName = this.queryParams.regulationName;
        params.docCode = this.queryParams.docCode;
        params.execEffeEvaluation = this.queryParams.execEffeEvaluation;
        params.groupAbolitionPlan = this.queryParams.groupAbolitionPlan;
        params.pageNum = this.queryParams.pageNum;
        params.pageSize = this.queryParams.pageSize;
        params.fileName = this.queryParams.fileName;
        if (this.typeNum == 1) {
          params.firstContentName = this.queryParams.firstContentName;

        }
        let companyId = this.$store.getters.userInfo.companyId
        let viewAll = false;
        if (this.$store.getters.userInfo.department == "巡察工作办公室(党风廉政办公室)") {
          viewAll = true;
        }
        if (!companyId) {
          viewAll = true;
        }
        params.companyId = companyId;
        params.viewAll = viewAll;
        applyFileList(params).then((res) => {
          if (res.code == 200) {
            res.rows.forEach((item) => {
              let orgNameArr = ["巡察组", "巡察办", "地市公司分管党办领导"];
              let orgId = []
              item.premList.forEach((ele) => {
                if (ele.orgName) {
                  orgNameArr.push(ele.orgName);
                  orgId.push(ele.orgId);
                }
              });
              item.orgName = orgNameArr.join(",");
              item.orgId = orgId
            });
            this.tableData = res.rows;
            this.total = Number(res.total);
          }
        });
      },
      handleClose(e) {
        if (e == 200) {
          this.getList(this.typeNum);
        }
        this.isOpen = false;
      },
      openInfoForm(row) {
        this.getInfoRow = row;
        this.dialogObj.id = row.applyId;
        this.dialogObj.open = true;
      },
      openForm() {
        this.selectRow = this.selectRowArr[0];
        // this.$refs.tableRef.multipleSelection
        this.isOpen = true;
      },
      infoClose() {
        this.dialogObj.open = false;
      },
      handleQuery() {
        this.getList(this.typeNum);
      },
      resetQuery() {
        Object.assign(this.$data.queryParams, this.$options.data().queryParams);
        this.getList();
      },
    },
  };
</script>

<style>
</style>
