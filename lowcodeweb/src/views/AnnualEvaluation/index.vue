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
            <div class="" v-show="showSearch">
              <expand-collapse>
                <div slot="left">
                  <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px" :rules="rules"
                    :disabled="false">
                    <el-form-item label="巡察员：" prop="userName">
                      <el-input v-model="queryParams.userName" placeholder="请输入巡察员" clearable size="small"
                        @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <!-- <el-form-item label="所属巡察组：" prop="applyCode">
                      <el-input v-model="queryParams.applyCode" placeholder="所属巡察组" clearable size="small"
                        @keyup.enter.native="handleQuery" />
                    </el-form-item> -->
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
                <span>年度考评</span>
              </div>
            </div>
          </div>
          <div class="crad-content">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleShow">
                  生成建议离库名单
                </el-button>
                <el-button type="primary" size="mini" @click="handleExport">
                  批量导出
                </el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="yearCheckList" @selection-change="handleSelectionChange"
                key="table1">
                <el-table-column type="selection" width="55" align="left" />
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="巡察员" align="center" prop="userName" />
                <el-table-column label="单位" align="center" prop="companyName" />
                <el-table-column label="部门" align="center" prop="deptName" />
                <el-table-column label="巡察综合评分" align="center" prop="result" width="120">
                  <template slot-scope="scope">
                    <span class="btmLine" @click="onscore(scope.row)">{{
                        scope.row.result
                    }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="拒绝次数" align="center" prop="backTimes" />
                <el-table-column label="推荐去留情况" align="center" prop="ifLeave">
                  <template slot-scope="scope">
                    {{ scope.row.ifLeave == '1' ? '退出' : '留用' }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                  width="100">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="handleDetails(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div class="pageBox">
                <pagination :total="total" v-if="total > 0" :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize" @pagination="getList" />
              </div>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 详情弹窗 -->
    <el-dialog :title="title" :visible.sync="isOpenQueryLeader" width="1000px" append-to-body="append-to-body">
      <el-form ref="queryformDetail" :model="form" label-width="120px">
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>巡察经历</span>
            </div>
          </div>
          <div class="crad-content">
            <el-table v-loading="loading" :data="form.examHisList" border>
              <el-table-column label="序号" align="center" type="index" width="55" />
              <el-table-column label="巡察项目" align="center" prop="taskName" width="120" />
              <el-table-column label="小组名称" align="center" prop="groupName" />
              <el-table-column label="组内标签" align="center" prop="userTypeName" />
              <el-table-column label="巡察批次" align="center" prop="batchName" />
              <el-table-column label="巡察时间" align="center" prop="taskTime" />
              <el-table-column label="考核结果" align="center" prop="result" />
            </el-table>
          </div>
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>拒绝次数</span>
            </div>
          </div>
          <div class="crad-content">
            <el-table v-loading="loading" :data="form.leaveHisList" border>
              <el-table-column label="序号" align="center" type="index" width="55" />
              <el-table-column label="年份" align="center" prop="year" width="120" />
              <el-table-column label="巡察批次" align="center" prop="batchName" />
              <el-table-column label="小组名称" align="center" prop="groupName" />
              <el-table-column label="成员" align="center" prop="userName" />
              <el-table-column label="通知时间" align="center" prop="noticeTime" />
              <el-table-column label="反馈时间" align="center" prop="feedbackTime" />
              <el-table-column label="反馈结果" align="center" prop="feedbackRemark" />
            </el-table>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQueryLeader = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 评分记录弹窗 -->
    <el-dialog :title="title" :visible.sync="isOpenQueryOpen" width="1000px" append-to-body="append-to-body">
      <div class="crad">
        <div class="crad-header">
          <div class="crad-title">
            <span class="line"></span>
            <span>考核记录</span>
          </div>
        </div>
        <div class="crad-content">
          <el-table v-loading="loading" :data="scoreList" border>
            <el-table-column label="序号" align="center" type="index" width="55" />
            <el-table-column label="姓名" align="center" prop="userName" width="120" />
            <el-table-column label="专家类型" align="center" prop="speTypeName" />
            <el-table-column label="巡察年份" align="center" prop="year" />
            <el-table-column label="批次" align="center" prop="batchName" />
            <el-table-column label="巡察组" align="center" prop="groupName" />
            <el-table-column label="组内标签" align="center" prop="userTypeName" />
            <el-table-column label="考核总分" align="center" prop="result" />
            <el-table-column label="考评等级" align="center" prop="levelName" />
          </el-table>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQueryOpen = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 建议离库弹窗 -->
    <el-dialog :title="title" :visible.sync="createOpen" width="1000px" append-to-body="append-to-body">
      <el-form ref="scoreform" :model="scoreform" label-width="120px">
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>建议离库人员名单</span>
            </div>
          </div>
          <div class="crad-content">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleCreate">
                  留库
                </el-button>
                <el-button type="primary" size="mini" @click="handleExportChild">
                  导出
                </el-button>
              </el-col>
            </el-row>
            <el-table v-loading="loading" :data="createList" border @selection-change="handleSelectionChangeChild">
              <el-table-column type="selection" width="55" align="left" />
              <el-table-column label="序号" align="center" type="index" width="55" />
              <el-table-column label="巡察员" align="center" prop="userName" />
              <el-table-column label="单位" align="center" prop="companyName" />
              <el-table-column label="部门" align="center" prop="deptName" />
              <el-table-column label="巡察综合评分" align="center" prop="result" width="120">
                <template slot-scope="scope">
                  <span class="btmLine" @click="onscore(scope.row)">{{
                      scope.row.result
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column label="拒绝次数" align="center" prop="backTimes" />
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                width="100">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" @click="handleDetails(scope.row)">查看</el-button>
                  <el-button size="mini" type="text" @click="handleCreate(scope.row)">留库</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!-- <div class="pageBox">
              <pagination v-show="createtotal > 0" :total="createtotal" :page.sync="createParams.pageNum"
                :limit.sync="createParams.pageSize" @pagination="createLeave" />
            </div> -->
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createOpen = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">申请离库</el-button>
      </div>
    </el-dialog>
    <!-- 发起离库申请 -->
    <el-dialog :title="btitle" :visible.sync="leaveOpen" width="1000px" append-to-body="append-to-body">
      <el-form ref="leaveform" :model="scoreform" label-width="120px">
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>申请离库人员</span>
            </div>
          </div>
          <div class="crad-content">
            <el-table :data="createList">
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column prop="userName" align="center" label="离库人姓名"></el-table-column>
              <el-table-column prop="companyName" show-overflow-tooltip label="所在单位"></el-table-column>
              <el-table-column prop="deptName" show-overflow-tooltip label="部门"></el-table-column>
              <el-table-column prop="speTypeName" show-overflow-tooltip label="专家类型"></el-table-column>
              <el-table-column align="center" label="巡察综合评分">
                <template slot-scope="scope">
                  <span class="btmLine" @click="onscore(scope.row)">{{
                      scope.row.result
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="backTimes" align="center" label="拒绝次数"></el-table-column>
              <el-table-column prop="inDataTime" align="center" label="入库时间"></el-table-column>
              <el-table-column v-if="!disabled" label="操作">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="handleCreate(scope.row)">取消</el-button>
                </template>
              </el-table-column>
            </el-table>
            <br />
            <el-form :disabled="disabled" label-width="80px">
              <el-form-item label="离库原因:">
                <el-input type="textarea" v-model="leaveReason" :rows="3"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer" v-if="!disabled">
        <el-button @click="leaveOpen = false">取消</el-button>
        <el-button type="primary" @click="submit(5)">暂存</el-button>
        <el-button type="primary" @click="submit(null)">提交</el-button>
      </span>
    </el-dialog>
    <submit-process processKey="leave_apply_process" stepName="巡察办部门领导审核" :dialogVisible="submitProcessShow"
      @complete="completeSubmit" @close="submitProcessShow = false" :title="stitle"></submit-process>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import TreeSelect from "@/components/TreeSelect/index";
import {
  yearCheckedList,
  getUserExamDetail,
  getUserExamResult,
  createLeaveList,
  yearCheckedExportList,
  yearCheckedExportLeaveList,
} from "@/api/checkedAll/AnnualEvaluation";

import { addApply, temporarySaveApply } from "@/api/talent/apply";
import { formatDate } from '@/utils/index';
import submitProcess from "@/components/WorkFlow/submitProcess";

// import submitProcess from "@/views/talent/apply/submitProcess";
export default {
  name: "AnnualEvaluation",
  components: { formatDate, ExpandCollapse, TreeSelect, submitProcess },

  data () {
    return {
      stitle: '提交',
      disabled: false,
      true: true,
      false: false,
      isOpenQueryLeader: false,
      isOpenQueryOpen: false,
      createOpen: false,
      leaveOpen: false,
      submitProcessShow: false,
      leaveReason: '',
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
      // 选中数组
      idsChild: [],
      // 非单个禁用
      singleChild: true,
      // 非多个禁用
      multipleChild: true,
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
        userName: "",
      },
      createParams: {
        pageNum: 1,
        pageSize: 10,
      },
      createtotal: 0,
      // 表单参数
      form: {

      },
      scoreform: {

      },
      scoreList: [],
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "change" }],
      },
      // batchIdOptions: [],
      treeIndex: {},
      // tableData1: [],
      // tableDataPre: [],
      yearCheckList: [],
      createList: [],
      btitle: '',
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

    // getExamContentByExamType({ examTypeId: '1' }).then(res => {
    //   this.tableData1 = res.data
    // })

    // getExamContentByExamType({ examTypeId: '4' }).then(res => {
    //   this.tableDataPre = res.data
    // })
    // this.isOpenQueryLeader = true;
    this.getList();
  },
  methods: {
    //树形图
    handleNodeClick (data, node, agms) {
      let { batchId, id, lunciId, year } = data
      this.treeIndex = { batchId, id, lunciId, year };
      // if (data.children.length == 0 && node.parent.data.label) {
      //   this.treeIndex.year = node.parent.data.label ? node.parent.data.label : ""
      // }
      console.log('groupData:', data, this.treeIndex);
      this.getList();
    },
    // yearchangefd (val) {
    //   //批次
    //   this.queryParams.batchId = null;
    //   getBatchList({ year: val }).then(res => {
    //     this.batchIdOptions = res.data ? res.data : [];
    //   })
    // },
    onscore (data) {
      this.reset();

      let obj = {
        userId: data.userId,
        year: data.year,
      }
      getUserExamResult(obj).then(res => {
        this.scoreList = res.data
        this.title = '评分记录'
        this.isOpenQueryOpen = true
      })
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {};
      this.scoreform = {};
      this.scoreList = [];
      this.resetForm("form");
      this.resetForm("scoreform");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      // 查询参数
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: "",
      }
      this.resetForm("F");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleSelectionChangeChild (selection) {
      this.idsChild = selection.map(item => item.userId)
      this.singleChild = selection.length !== 1
      this.multipleChild = !selection.length
    },
    getList () {
      console.log(this.treeIndex)
      // let obj = {...this.queryParams,...this.treeIndex}
      let treeobj = this.treeIndex;
      yearCheckedList({ ...this.queryParams, ...treeobj }).then(response => {
        this.yearCheckList = response.rows || [];
        this.total = Number(response.total);
        this.loading = false;
      });
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
    handleCreate (row) {
      const id = row.userId || this.idsChild.toString();
      // debugger
      let createListNew = this.createList.filter(item => {
        if (id.indexOf(item.userId) < 0) {
          return item
        }
      })

      this.createList = createListNew;
    },
    /**详情按钮*/
    handleDetails (row) {
      let that = this;
      this.disabled = false;
      this.reset();
      let obj = {
        userId: row.userId,
        year: this.treeIndex.year,
      }
      getUserExamDetail(obj).then(res => {
        this.form = res.data
        this.title = "年度考评详情"
        this.isOpenQueryLeader = true;
      })
      // this.getDetail(row.id);
    },
    getDetail (id) {
      // let datas = {
      //   applyId: examtype.applyId,
      //   groupId: examtype.groupId
      // };
      // that.form = {};
      // that.form = examtype;
      // (examtype.examTypeId == 1) && getLeaderListProgress(datas).then(res => {
      //   let data = res.rows
      //   that.isOpenQueryLeader = true;
      //   that.form.checkedInfoList = data;
      // });

      // that.proTeamList = data;
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
    //生成建议离库名单
    handleShow () {
      this.createLeave()
      this.title = "建议离库人员"
      this.createOpen = true
    },
    createLeave () {
      createLeaveList(this.createParams).then(response => {
        this.createList = response.rows || [];
        this.createtotal = Number(response.total);
      })
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
        return yearCheckedExportList(queryParams);
      }).then(response => {
        this.download(response.msg, response.key);
      }).catch(function () { });
    },
    /** 导出按钮操作 */
    handleExportChild () {
      const queryParams = this.queryParams;
      // let queryParams = JSON.parse(JSON.stringify(this.queryParams));
      queryParams.id = this.idsChild ? this.idsChild.toString() : "";
      this.$confirm('是否确认导出当前批次数据项?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(function () {
        return yearCheckedExportLeaveList(queryParams);
      }).then(response => {
        this.download(response.msg, response.key);
      }).catch(function () { });
    },
    //申请离库
    onSubmit () {
      console.log(this.createList)
      if (!this.createList.length) {
        this.$message.warning("请选择离库人!");
        return
      }
      this.btitle = '申请离库人员',
      this.leaveOpen = true
    },
    submit (t) {
      if (!this.createList.length) {
        this.$message.warning("请选择离库人!");
        return null;
      }
      let tempObj = {
        leaveApplyRelList: this.createList,
        leaveReason: this.leaveReason,
      };
      let result = tempObj;
      if (!result) {
        return;
      }
      if (t == 1) {
        return result;
      } else if (t == 5) {
        //弹窗确认
        this.$confirm("确定暂存吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          temporarySaveApply(result).then((res) => {
            if (res.code == 200) {
              this.msgSuccess("暂存成功");
              this.close(1);
            }
          });
        });
      } else {
        this.submitProcessShow = true;
      }
    },
    close (e) {
      this.submitProcessShow = false;
      this.createOpen = false;
      this.leaveOpen = false;
      // this.$emit("close", e);
    },

    completeSubmit (e) {
      let result = this.submit(1);
      result.variables = e.variables;
      addApply(result).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("提交成功");
          this.close(1);
        }
      });
    },
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

.bwten {
  display: flex;
  justify-content: space-between;
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
  margin-left: 0;
  padding-left: 0;

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