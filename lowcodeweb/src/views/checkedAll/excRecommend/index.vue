<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="5">
        <div class="tree_tit">巡察批次</div>
        <tree-select :isSearch="false" :highlightCurrent="true" :expandall="false" @node-click="handleNodeClick">
        </tree-select>
      </el-col>
      <el-col :span="19">
        <div class="container">
          <div class="crad">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="优秀巡察员" name="1"></el-tab-pane>
              <el-tab-pane label="优良巡察员" name="2"></el-tab-pane>
            </el-tabs>
            <div class="" v-show="showSearch">
              <expand-collapse>
                <div slot="left">
                  <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px" :rules="rules"
                    :disabled="false">
                    <el-form-item label="巡察员：" prop="userName">
                      <el-input v-model="queryParams.userName" placeholder="请输入姓名" clearable size="small"
                        @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item label="所属巡察组：" prop="groupId">
                      <el-select v-model="queryParams.groupId" placeholder="请选择巡察组" size="small" :clearable="false">
                        <el-option v-for="dict in groupList" :key="dict.groupId" :label="dict.groupName"
                          :value="dict.groupId" />
                      </el-select>
                    </el-form-item>
                    <el-form-item label="组内标签：" prop="userTypeId">
                      <el-select v-model="queryParams.userTypeId" placeholder="请选择组内标签" size="small" :clearable="false">
                        <el-option v-for="dict in userTypeList" :key="dict.userTypeId" :label="dict.userTypeName"
                          :value="dict.userTypeId" />
                      </el-select>
                    </el-form-item>
                    <el-form-item label="评优方式：" prop="upTypeId">
                      <el-select v-model="queryParams.upTypeId" placeholder="请选择组内标签" size="small" :clearable="false">
                        <el-option v-for="dict in upTypeList" :key="dict.upTypeId" :label="dict.upTypeName"
                          :value="dict.upTypeId" />
                      </el-select>
                    </el-form-item>
                  </el-form>
                </div>
                <div slot="right">
                  <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
                  <el-button size="mini" @click="resetQuery">重置</el-button>
                </div>
              </expand-collapse>
            </div>
            <div class="crad-header">
              <div class="crad-title">
                <span class="line"></span>
                <span>{{ activeName == '1' ? '优秀巡察员' : '优良巡察员' }}</span>
              </div>
            </div>
          </div>
          <div class="crad-content">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <!-- <el-button type="primary" size="mini" @click="handleExport">
                  优秀巡察员申报
                </el-button> -->
                <el-button type="primary" size="mini" @click="handleExport">
                  批量导出
                </el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="checkFineList" @selection-change="handleSelectionChange"
                @sort-change="sortChange">
                <el-table-column type="selection" width="55" align="left" />
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="巡察员" align="center" prop="userName" width="160" />
                <el-table-column label="所属巡察组" align="left" prop="groupName" width="120" sortable="custom" />
                <el-table-column label="组内标签" align="center" prop="userTypeName" width="200" />
                <el-table-column label="综合评分" align="center" prop="finalResult" width="160" sortable="custom" />
                <el-table-column label="所在单位" align="center" prop="companyName" width="100" />
                <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" width="200" />
                <el-table-column label="是否参与申报" align="center" prop="ifSubmit" width="120">
                  <template slot-scope="scope">
                    {{ scope.row.ifSubmit == 1 ? '是' : '否' }}
                  </template>
                </el-table-column>
                <el-table-column label="评优方式" align="center" prop="upTypeName" width="120" />
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                  width="100">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div class="pageBox">
                <pagination :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                  @pagination="getList" />
              </div>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>

    <collectForm :dialogVisible="iFormShow.open" :id="iFormShow.id" more @close="handleCollectFormClose">
    </collectForm>

  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import TreeSelect from "@/components/TreeSelect/index";
import {
  getBatchList, getList, exportApplyFineList
} from "@/api/checkedAll/excRecommend";
import { formatDate } from '@/utils/index';
import collectForm from "@/views/talent/collect/collectForm.vue";
export default {
  name: "excRecommend",
  components: { formatDate, ExpandCollapse, TreeSelect, collectForm },

  data () {
    return {
      iFormShow: { open: false, id: null },
      disabled: false,
      true: true,
      false: false,
      // 遮罩层
      loading: false,
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/checkedAll/checkedInfo/importData",
      },
      seeParams: {
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fineTypeId: '1',
        batchId: '',
        userTypeId: '',
        upTypeId: '',
        groupId: '',
        userName: ''
      },
      // 表单参数
      form: {

      },
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "change" }],
      },
      activeName: "1",
      batchIdOptions: [],
      groupList: [],
      userTypeList: [],
      upTypeList: [
        { upTypeId: '1', upTypeName: '组内评优' }, { upTypeId: '2', upTypeName: '择优添加' }
      ],
      treeIndex: {},
      checkFineList: [],
    };
  },
  filters: {
    filterDate (val) {
      return formatDate(val);
    },
    filterAll (val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created () {
    this.getList();

  },
  methods: {
    //树形图
    handleNodeClick (data, node, agms) {
      console.log('groupData:', data);
      this.treeIndex = data;
      this.getList();
    },
    handleClick (tab, event) {
      this.getList();
    },
    getList () {
      console.log('fineTypeId:', this.activeName);
      console.log('groupData - id :', this.treeIndex.id);

      this.queryParams.fineTypeId = this.activeName;
      this.queryParams.batchId = this.treeIndex.id;


      let that = this;
      this.loading = true;
      getList(this.queryParams).then(response => {
        that.checkFineList = response.rows || [];
        that.total = Number(response.total);
        that.loading = false;
      });
      //接口
      //getBatchList(data);

    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.disabled = false;
      this.reset();
      this.open = true;
      this.title = "巡察组发起考核";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.disabled = false;
      this.reset();
      const id = row.id || this.ids
      // getCheckedInfo(id).then(response => {
      //     this.form = response.data;
      //     this.open = true;
      //     this.title = "修改巡察考核发起记录";
      // });
    },
    handleQueryOption (row) {
      const id = row.id || this.ids
    },

    onUserInfo (row) {
      this.iFormShow.id = row.userId;
      this.iFormShow.open = true;
      console.log(this.iFormShow);
    },
    handleCollectFormClose () {
      this.iFormShow.open = false
    },
    /**详情按钮*/
    handleDetails (row) {
      // this.reset();
      const id = row.id || this.ids
      // this.isOpenQuery = true;
      // this.title = "查看巡察考核发起记录";
      // getCheckedInfo(id).then(response => {
      //     this.form = response.data;
      //     this.isOpenQuery = true;
      //     this.title = "查看巡察考核发起记录";
      // });
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除巡察考核发起记录序号为"' + ids + '"的数据项?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delCheckedInfo(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function () { });
    },
    /** 导出按钮操作 */
    handleExport () {
      const queryParams = this.queryParams;
      // let queryParams = JSON.parse(JSON.stringify(this.queryParams));
      queryParams.id = this.ids ? this.ids.toString() : "";
      this.$confirm('是否确认导出当前批次数据项?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(function () {
        return exportApplyFineList(queryParams);
      }).then(response => {
        this.download(response.msg, response.key);
      }).catch(function () { });
    },
    /** 导入按钮操作 */
    openImportTable () {
      this.upload.title = "巡察考核发起记录数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate () {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess (response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm () {
      this.$refs.upload.submit();
    },
    //排序修改
    sortChange ({ column, prop, order }) {
      console.log(column, prop, order)

      let that = this;
      this.loading = true;
      let forms = JSON.parse(JSON.stringify(this.queryParams));
      forms.fineTypeId = this.activeName;
      forms.batchId = this.treeIndex.id;
      if (order != null || order != undefined) {
        forms.orderByParam = prop + '-' + (order == 'ascending' ? 'asc' : 'desc')
      }
      getList(forms).then(response => {
        that.checkFineList = response.rows || [];
        that.total = Number(response.total);
        that.loading = false;
      });
    }
  }
};
</script>



<style lang="scss" scoped>
.app-container {
  background: #F5F7F9;
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


  /deep/ .el-table__header-wrapper {
    th {
      background: rgba(45, 140, 240, 0.04) !important;
      font-size: 14px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.75);
      height: auto;
    }
  }

  /deep/ .el-table__fixed-header-wrapper {
    th {
      background: rgba(45, 140, 240, 0.04) !important;
      font-size: 14px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.75);
      height: auto;
    }
  }

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

/deep/ .specialForm .el-form-item__content {
  margin-left: 0 !important;
}

.btips {
  color: #e14d3b;
  margin-top: 0;
  padding-top: 0;

  div {
    margin-bottom: 20px;
  }
}

.crad-header {
  display: flex;
  align-items: center;
  border-radius: 2px;
  height: 48px;
  padding-right: 16px;
  justify-content: space-between;
}

.history-btn {
  padding: 4px 8px;
  border: 1px solid #1590ff;
  border-radius: 6px;
  color: #1590ff;
  font-size: 14px;
  cursor: pointer;
}

// .operations{
//     display: none;
// }

.app-container {
  background: #f5f7f9;
}

.tree_tit {
  text-align: center;
  padding: 12px 0 12px;
  background: #fff;
  border: 1px solid #f5f7f9;
}
</style>