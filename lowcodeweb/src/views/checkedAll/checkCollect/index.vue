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
              <el-tab-pane label="巡察组" name="1"></el-tab-pane>
              <el-tab-pane label="巡察员" name="2"></el-tab-pane>
            </el-tabs>
            <div class="" v-show="showSearch">
              <expand-collapse>
                <div slot="left">
                  <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules"
                    :disabled="false">
                    <el-form-item label="巡察组：" prop="groupName">
                      <el-input v-model="queryParams.groupName" placeholder="请输入巡察组" clearable size="small"
                        @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item label="巡察员：" prop="userName" v-if="activeName == '2'">
                      <el-input v-model="queryParams.userName" placeholder="请输入组长" clearable size="small"
                        @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item label="组长：" prop="applyCode" v-if="activeName == '1'">
                      <el-input v-model="queryParams.applyCode" placeholder="请输入组长" clearable size="small"
                        @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item label="巡察年份：" prop="year">
                      <el-date-picker size="small" :clearable="false" style="width: 205px" format="yyyy"
                        value-format="yyyy" v-model="queryParams.year" type="year" placeholder="请选择巡察年份"
                        @change="yearchangefd">
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item label="巡察批次：" prop="batchId">
                      <el-select v-model="queryParams.batchId" placeholder="请选择巡察批次" size="small" :clearable="false">
                        <el-option v-for="dict in batchIdOptions" :key="dict.batchId" :label="dict.batchName"
                          :value="dict.batchId" />
                      </el-select>
                    </el-form-item>
                    <el-form-item label="组内标签：" prop="userTypeId" v-if="activeName == '2'">
                      <el-select v-model="queryParams.userTypeId" placeholder="请选择组内标签" size="small" :clearable="false">
                        <el-option v-for="dict in batchIdOptions" :key="dict.batchId" :label="dict.batchName"
                          :value="dict.batchId" />
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
                <span>考核汇总</span>
              </div>
            </div>
          </div>
          <div class="crad-content" v-if="activeName == '1'">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleExport('1')">
                  批量导出
                </el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="groupCollectList" @selection-change="handleSelectionChange"
                key="table1" :span-method="objectSpanMethod" @sort-change="sortChange" :row-class-name="rowClassName"
                @cell-mouse-enter="handleMouseEnter" @cell-mouse-leave="handleMouseLeave">
                <!-- :span-method="objectSpanMethod" sortable 排序-->
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序号" align="center" prop="order" width="55" />
                <el-table-column label="巡察组" align="center" prop="groupName" width="120" sortable="custom">
                  <template slot-scope="scope">
                    <span class="btmLine" @click="onGrounpDetail(scope.row)">{{
                        scope.row.groupName
                    }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" width="120" />
                <el-table-column label="巡察年份" align="center" prop="year" width="100" />
                <el-table-column label="批次" align="center" prop="batchName" width="180" />
                <el-table-column label="考评结果" align="center">
                  <el-table-column label="综合评分" align="center" prop="allResult" sortable="custom" width="120">
                  </el-table-column>
                  <el-table-column label="领导小组评分（70%）" align="center" prop="groupName">
                    <el-table-column label="总分" align="center" prop="groupAllResult">
                      <template slot-scope="scope">
                        <span class="btmLine" @click="handleDetails(scope.row, '1')">{{
                            scope.row.groupAllResult
                        }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="姓名" align="center" prop="groupLeaderName"></el-table-column>
                    <el-table-column label="得分" align="center" prop="groupLeaderResult"></el-table-column>
                  </el-table-column>
                  <el-table-column label="巡察办评分（20%）" align="center" prop="portalResult" width="150">
                    <template slot-scope="scope">
                      <span class="btmLine" @click="handleDetails(scope.row, '2')">{{
                          scope.row.portalResult
                      }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="被巡察单位评分（10%）" align="center" prop="toExamOfficeResult" width="190">
                    <template slot-scope="scope">
                      <span class="btmLine" @click="handleDetails(scope.row, '3')">{{
                          scope.row.toExamOfficeResult
                      }}</span>
                    </template>
                  </el-table-column>
                </el-table-column>
              </el-table>
              <div class="pageBox">
                <pagination :total="total" v-if="total > 0" :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize" @pagination="getList" />
              </div>
            </el-form>
          </div>
          <div class="crad-content" v-if="activeName == '2'">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleExport('2')">
                  批量导出
                </el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
            </el-row>
            <el-form ref="form2" :model="form2" :rules="rules" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="groupCollectList" @selection-change="handleSelectionChange"
                @sort-change="sortChange" key="table2">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="巡察员" align="center" prop="userName" />
                <el-table-column label="综合评分" align="center" prop="result" sortable="custom" width="100" />
                <el-table-column label="所属巡察组" align="center" prop="groupName" width="160" sortable="custom">
                  <template slot-scope="scope">
                    <span class="btmLine" @click="onGrounpDetail(scope.row)">{{
                        scope.row.groupName
                    }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="组内标签" align="center" prop="userTypeName" width="160" />
                <el-table-column label="巡察年份" align="center" prop="year" />
                <el-table-column label="批次" align="center" prop="batchName" width="160" />
                <el-table-column label="所属单位" align="center" prop="companyName" width="160" />
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                  width="100">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="handleDetails(scope.row, '4')">查看</el-button>
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
    <!-- 各种类型详情弹窗 -->
    <el-dialog :title="title" :visible.sync="isOpenQueryLeader" width="1200px" append-to-body="append-to-body">
      <el-form ref="queryformDetail" :model="form" label-width="120px" :disabled="true">
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>巡察组信息</span>
            </div>
          </div>
          <div class="crad-content">
            <el-row :gutter="10">
              <el-col :span="12" v-if="form.examTypeId == '1'">
                <el-form-item label="被考核小组：" prop="groupName">
                  <el-input v-model="form.groupName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12"
                v-if="form.examTypeId == '1' || form.examTypeId == '2' || form.examTypeId == '3' || form.examTypeId == '4'">
                <el-form-item label="巡察年份：" prop="year">
                  <el-input v-model="form.year" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12"
                v-if="form.examTypeId == '1' || form.examTypeId == '2' || form.examTypeId == '3' || form.examTypeId == '4'">
                <el-form-item label="巡察批次：" prop="batchName">
                  <el-input v-model="form.batchName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '2' || form.examTypeId == '3'">
                <el-form-item label="评分组织：" prop="batchName">
                  <el-input v-model="form.batchName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '2' || form.examTypeId == '3'">
                <el-form-item label="被评分对象：" prop="toExamCompanyName">
                  <el-input v-model="form.toExamCompanyName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '2'">
                <el-form-item label="被考核小组：" prop="groupName">
                  <el-input v-model="form.groupName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '3'">
                <el-form-item label="评分人：" prop="fromUserName">
                  <el-input v-model="form.fromUserName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '3'">
                <el-form-item label="评分时间：" prop="examTime">
                  <el-input v-model="form.examTime" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '3'">
                <el-form-item label="被考核小组：" prop="groupName">
                  <el-input v-model="form.groupName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '4'">
                <el-form-item label="被考核成员：" prop="userName">
                  <el-input v-model="form.userName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '4'">
                <el-form-item label="所属巡察组：" prop="groupName">
                  <el-input v-model="form.groupName" placeholder="" style="width: 70%" />
                  <el-button type="primary" :disabled="false" class="detail" @click="onGrounpDetail(form)">查看
                  </el-button>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '4'">
                <el-form-item label="组内标签：" prop="groupName">
                  <el-input v-model="form.groupName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '4'">
                <el-form-item label="评分人：" prop="fromUserName">
                  <el-input v-model="form.fromUserName" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.examTypeId == '4'">
                <el-form-item label="评分时间：" prop="examTime">
                  <el-input v-model="form.examTime" placeholder="" style="width: 70%" />
                </el-form-item>
              </el-col>
              <!-- 被巡察单位评分 -->
            </el-row>
          </div>
          <div class="crad-header">
            <div class="crad-title">
              <span class="line"></span>
              <span>评分进度</span>
            </div>
          </div>
          <!-- 查看巡察考核评分记录-领导小组 -->
          <div class="crad-content" v-if="form.examTypeId == '1'">
            <el-row :gutter="10" class="mb12 utlsBtn mar20 bwten">
              <el-col :span="2">
                <el-button type="primary" size="mini" @click="handleExportDetail(form)" :disabled="false">
                  导出评分表
                </el-button>
              </el-col>
              <el-col :span="18"></el-col>
              <el-col :span="4" style="text-align:right">最后得分：{{ form.groupAllResult }}分</el-col>
            </el-row>
            <el-form ref="form1" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="form.checkedInfoList" @selection-change="handleSelectionChange"
                border>
                <!-- :span-method="objectSpanMethod" -->
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="评分人" align="center" prop="fromUserName" width="120" />
                <el-table-column label="得分" align="center" prop="allpoints" width="120" />
                <el-table-column label="姓名" align="center" prop="toUserName" width="180">
                  <template slot-scope="scope">
                    {{ scope.row.toUserName }}（{{ scope.row.userTypeName }}）
                  </template>
                </el-table-column>
                <el-table-column label="评分" align="center" prop="allpoints" width="100" />
                <el-table-column align="center" v-for="(item, index) in tableData1" :key="index"
                  :width="(Number(item.scopeTitle.length) + item.mixScope.toString().length + 3) * 15">
                  <template slot="header" slot-scope="scope">
                    {{ item.scopeTitle }}（{{ item.mixScope }}分）
                  </template>
                  <template slot-scope="scope">
                    {{
                        scope.row.examList[index]['examResult'] ?
                          scope.row.examList[index]['examResult'] : '--'
                    }}
                  </template>
                </el-table-column>
                <el-table-column label="评分时间" align="center" prop="examTime" width="160">
                </el-table-column>
              </el-table>
            </el-form>
            <div class="crad-content btips">
              <div>评分参考：</div>
              <div>本表满分100分，考核总分权重占比为70%。</div>
            </div>
          </div>
          <!-- 查看巡察考核评分记录-巡察办评分 -->
          <div class="crad-content" v-if="form.examTypeId == '2'">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleExportDetail(form)" :disabled="false">
                  导出评分表
                </el-button>
              </el-col>
            </el-row>
            <el-form ref="form2" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="form.checkedInfoList" @selection-change="handleSelectionChange"
                border :span-method="objectSpanMethodOffice" :row-class-name="rowClassName"
                @cell-mouse-enter="handleMouseEnter" @cell-mouse-leave="handleMouseLeave">
                <!-- :span-method="objectSpanMethodOffice" -->
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="评分人" align="center" prop="fromUserName" width="150" />
                <el-table-column label="评分状态" align="center" prop="status" width="150">
                  <template slot-scope="scope">
                    {{ scope.row.status == 0 ? '评分中' : '已完成' }}
                  </template>
                </el-table-column>
                <el-table-column label="评分时间" align="center" prop="" width="150">
                  <template slot-scope="scope">
                    {{ scope.row.examTime ? scope.row.examTime : '--' }}
                  </template>
                </el-table-column>
                <el-table-column label="评分内容" align="center" prop="examContant" width="300" />
                <el-table-column label="考评总分" align="center" prop="examMaxScope" width="100" />
                <el-table-column label="*评分" align="center" prop="examResult" width="150">
                  <template slot-scope="scope">
                    {{ scope.row.examResult ? scope.row.examResult : '--' }}
                  </template>
                </el-table-column>
                <el-table-column label="总分" align="center" prop="allpoints" fixed="right" />
              </el-table>
            </el-form>
            <div class="crad-content btips">
              <div>评分参考：</div>
              <div>1.本表满分100分，考核总分权重占比为20%。</div>
              <div>2.此表由巡察工作办公室根据巡察组的基础管理情况，在巡察工作结束一周内填写。</div>
            </div>
          </div>
          <!-- 查看巡察考核评分记录-被巡察单位评分 -->
          <div class="crad-content" v-if="form.examTypeId == '3'">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleExportDetail(form)" :disabled="false">
                  导出评分表
                </el-button>
              </el-col>
            </el-row>
            <el-form ref="form3" :model="form" label-width="120px" :disabled="disabled">
              <el-table v-loading="loading" :data="form.examList" border show-summary :summary-method="getSummaries">
                <el-table-column label="序号" align="center" type="index" width="55" />
                <el-table-column label="评分内容" align="center" prop="examContant" />
                <el-table-column label="*有/无" align="center" prop="ifHave" width="200">
                  <template slot-scope="scope">
                    {{ scope.row.ifHave == 0 ? '无' : '有' }}
                  </template>
                </el-table-column>
                <el-table-column label="违规具体情形" align="center" prop="examRemark">
                  <template slot-scope="scope">
                    {{ scope.row.examRemark ? scope.row.examRemark : '--' }}
                  </template>
                </el-table-column>
              </el-table>
            </el-form>
            <div class="crad-content btips">
              <div>评分参考：</div>
              <div>1.本表满分100分，出现任一种违规违纪情形，合计零分，考核总分权重占比为10%。</div>
              <div>2.此表请被巡察单位在巡察组工作结束一周内，以纸质版（盖章，填表人签字）报广东公司党委巡察工作办公室。</div>
            </div>
          </div>
          <!-- 查看巡察考核评分记录-巡察员评分 -->
          <div class="crad-content" v-if="form.examTypeId == '4'">
            <el-row :gutter="10" class="mb12 utlsBtn mar20">
              <el-col :span="1.5">
                <el-button type="primary" size="mini" @click="handleExportDetail(form)" :disabled="false">
                  导出评分表
                </el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
            </el-row>
            <el-form ref="form4" :model="form" label-width="120px">
              <el-table v-loading="loading" :data="proTeamList" border>
                <!-- :header-cell-style="headCellstyle"
                :header-row-style="headsrowStyle" -->
                <el-table-column label="序号" align="center" type="index" width="55">
                </el-table-column>
                <el-table-column align="center" v-for="(item, index) in tableDataPre" :key="index"
                  :width="((Number(item.scopeTitle ? item.scopeTitle.length : 0) + (item.mixScope ? item.mixScope.toString().length : 0) + 3)) * 20">
                  <template slot="header" slot-scope="scope">
                    {{ item.scopeTitle }}（{{ item.mixScope }}%）
                  </template>
                  <template slot-scope="scope">
                    {{ scope.row.examList[index]['examResult'] ? scope.row.examList[index]['examResult'] : '--' }}
                  </template>
                </el-table-column>
                <el-table-column label="考评总分" align="center" prop="allpoints">
                  <template slot-scope="scope">
                    {{ scope.row.allpoints }}
                    <!-- | pointFilt -->
                  </template>
                </el-table-column>
                <!-- <el-table-column label="考评等级" align="center" width="105" prop="level"></el-table-column> -->
                <!-- <el-table-column label="考评等级" align="center"> -->
                  <el-table-column label="考评等级" align="center" width="105" prop="levelId">
                    <template slot-scope="scope">
                      {{ scope.row.leavelId | filterAllLevel(scoreLevelption) }}
                    </template>
                  </el-table-column>
                <!-- </el-table-column> -->
              </el-table>
            </el-form>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQueryLeader = false">关 闭</el-button>
      </div>
    </el-dialog>
    <patrol-group ref="PatrolGroup" :groupObject="groupObject" :dialogVisible="isOpenGroup"
      @close="isOpenGroup = false"></patrol-group>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import TreeSelect from "@/components/TreeSelect/index";
import {
  getBatchList,
  getExamContentByExamType,
  getList,
  exportGroupList,
  exportPerList,
  exportGroupDetail,
  exportPatrolDetail,
  exportToExamOfficeDetail,
  exportPerDetail
} from "@/api/checkedAll/checkCollect";
import {
  getLeaderListProgress,
  getPatrolProgressInfo,
  getToexamProgressInfo,
  getGroupMemberProgress,
  getPerDetail,
  getGroupDetail,
  getPatrolDetail,
} from "@/api/checkedAll/checkedInfo";
import { formatDate } from '@/utils/index';
import PatrolGroup from "@/components/PatrolGroup";
export default {
  name: "excRecommend",
  components: { formatDate, ExpandCollapse, TreeSelect, PatrolGroup },

  data () {
    return {
      stitle: "提交",
      groupObject: {
        taskId: "",
        piciId: "",
      },
      ischeck: 1,
      isOpenGroup: false,
      disabled: false,
      true: true,
      false: false,
      isOpenQueryLeader: false,
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
        batchId: ""
      },
      // 表单参数
      form: {

      },
      form2: {

      },
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "change" }],
      },
      activeName: "1",
      batchIdOptions: [],
      treeIndex: {},
      tableData1: [],
      tableDataPre: [],
      groupCollectList: [],
      examTypeOption: [],
      proTeamList: [],
      section: [],
      cellIndex: -1,
      scoreLevelption: [],
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
    pointFilt (val) {
      if (val) {
        return (val / 10).toFixed(2);
      } else {
        return ""
      }
    },

    filterAllLevel (val, optionArr) {
      if (!val.length) return "--";
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  created () {

    this.section = [
      {
        id: 1,
        name: "优秀",
        max: 100,
        min: 90,
      },
      {
        id: 2,
        name: "优良",
        max: 90,
        min: 80,
      },
      {
        id: 3,
        name: "中等",
        max: 80,
        min: 70,
      },
      {
        id: 4,
        name: "及格",
        max: 70,
        min: 60,
      },
      {
        id: 5,
        name: "不及格",
        max: 60,
        min: 0,
      },
    ];

    this.getDicts("score_level").then(res => {
      this.scoreLevelption = res.data ? res.data : [];
    })
    getExamContentByExamType({ examTypeId: '1' }).then(res => {
      this.tableData1 = res.data
    })

    getExamContentByExamType({ examTypeId: '4' }).then(res => {
      this.tableDataPre = res.data
    })

    this.getDicts("exam_type").then(res => {
      this.examTypeOption = res.data ? res.data : [];
    })
    // this.isOpenQueryLeader = true;

    this.yearchangefd('');
    this.getList();
  },
  methods: {

    onGrounpDetail (row) {
      this.groupObject = {
        groupId: row.groupId ? row.groupId : ''
      }
      this.isOpenGroup = true
    },

    // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
    // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
    // 也可以返回一个键名为rowspan和colspan的对象。
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      // console.log(this.groupCollectList)
      // console.log(row.groupLeaderId)
      if ((columnIndex < 8 && columnIndex > 0) || columnIndex == 10 || columnIndex == 11) {
        let data = this.groupCollectList.filter((item) => item.groupId == row.groupId);
        let idx = 0;
        data.forEach((item, index) => { if (item.groupLeaderId == row.groupLeaderId) { idx = index } })
        // console.log(len, rowIndex)
        if (idx % data.length === 0) {
          return {
            rowspan: data.length,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },

    // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
    // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
    // 也可以返回一个键名为rowspan和colspan的对象。
    objectSpanMethodOffice ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex < 5 || columnIndex == 7) {
        let data = this.form.checkedInfoList.filter((item) => {
          if ((item.fromUserId == row.fromUserId) && (item.groupId == row.groupId)) {
            return item
          }
        });
        let idx = 0;
        data.forEach((item, index) => { if ((item.examId == row.examId)) { idx = index } })
        if (idx % data.length === 0) {
          return {
            rowspan: data.length,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },
    yearchangefd (val) {
      //批次
      this.queryParams.batchId = null;
      getBatchList({ year: val }).then(res => {
        this.batchIdOptions = res.data ? res.data : [];
      })
    },
    //树形图
    handleNodeClick (data, node, agms) {
      console.log('groupData:', data);
      if (data.children.length) {
        this.loading = true;
        this.queryParams.batchId = "";
        getList({ ...this.queryParams, batchId: data.piciId }).then(response => {
          this.groupCollectList = response.rows || [];

          this.handleData(this.groupCollectList);
          this.total = Number(response.total);
          this.loading = false;
        });
        this.treeIndex = {};
        return;
      }

      this.treeIndex = data;
      this.getList();
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
    handleClick (tab, event) {
      this.getList();
    },
    handleQuery () {
      this.queryParams.pageNum = 1;
      console.log(this.activeName);

      this.getList();
    },
    getList () {
      console.log('type:', this.activeName);
      console.log('groupData - id :', this.treeIndex.id);

      this.queryParams.type = this.activeName;
      if (this.treeIndex.id) {
        this.queryParams.batchId = this.treeIndex.id;
      }
      if (this.activeName == '1') {
        this.queryParams.getMethod = 'getGroupList';
      } else {
        this.queryParams.getMethod = 'getPerList';
      }

      let that = this;
      this.loading = true;
      getList(this.queryParams).then(response => {
        that.groupCollectList = response.rows || [];

        this.handleData(that.groupCollectList);
        that.total = Number(response.total);
        that.loading = false;
      });
      //接口
      //getBatchList(data);

    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.queryParams.batchId = "";
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
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
    /**详情按钮*/
    handleDetails (examtype, eid) {
      if (eid) {
        examtype.examTypeId = eid;
      }
      let type = this.examTypeOption.filter(item => item.dictValue == examtype.examTypeId)[0].dictLabel
      this.title = type + "详情"
      this.getDetail(examtype);
    },
    getDetail (examtype) {
      let that = this;
      let datas = {
        applyId: examtype.applyId,
        groupId: examtype.groupId
      };
      that.form = {};
      that.form = examtype;
      console.log(that.form);
      (examtype.examTypeId == 1) && getGroupDetail(datas).then(res => {
        let data = res.rows ? res.rows : res.data;
        that.isOpenQueryLeader = true;
        that.form.checkedInfoList = data;
      });
      (examtype.examTypeId == 2) && getPatrolDetail(datas).then(res => {
        let data = res.rows ? res.rows : res.data;
        let InfoList = [];
        data.forEach((item, index, data) => {
          item.examList && item.examList.forEach((itm, idx, da) => {
            // 巡察组 考核类型 评分人选择 评分截止时间 备注
            let child = { ...itm, ...item };
            item.allpoints = item.allpoints ? item.allpoints : '0';
            child.index = idx;
            delete child.examList
            InfoList.push(child)
          }, data)
        }, data)
        that.form.checkedInfoList = InfoList;
        that.isOpenQueryLeader = true;
      });
      (examtype.examTypeId == 3) && getToexamProgressInfo(datas).then(res => {
        let data = res.rows ? res.rows : res.data;
        // debugger
        that.isOpenQueryLeader = true;
        that.form = { ...that.form, ...data[0] };
      });
      if (examtype.examTypeId == 4) {
        datas.fromId = examtype.fromId;
        datas.toExamUserId = examtype.userId;
      }
      // (examtype.examTypeId == 4) && getGroupMemberProgress(datas).then(res => {
      (examtype.examTypeId == 4) && getPerDetail(datas).then(res => {
        that.isOpenQueryLeader = true;
        let dataList = this.tableDataPre;
        let data = res.rows ? res.rows : res.data;
        // data.forEach(item => {
        let a = data.allpoints;
        let txt = "";
        for (let i = 0, len = that.section.length; i < len; i++) {
          if (a >= that.section[i].min && a <= that.section[i].max) {
            txt = that.section[i].name
            break
          }
        }

        data.level = txt;
        that.form.fromUserName = data.fromUserName;
        that.form.examTime = data.examTime;
        // data.numberAll = ""
        // data.level = ""
        data.examList = data.examList.map((itss, idss) => {
          let dataExam = JSON.parse(JSON.stringify(dataList)).filter(itm => itm.id == itss.examId)[0];
          itss.scopeContent = dataExam.scopeContent;
          itss.scopeTitle = dataExam.scopeTitle
          itss.mixScope = dataExam.mixScope
          return itss
        })
        // })
        console.log(data, "data--")
        that.proTeamList = [data];
      });
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
    handleExport (type) {
      console.log(type, '12321321');
      const queryParams = this.seeParamsCopy;
      console.log(queryParams, '123');
      this.$confirm('是否确认导出当前批次数据项?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(function () {
        if (type == '1') {//巡察组汇总列表导出
          return exportGroupList(queryParams);
        } else if (type == '2') {//巡察员汇总列表导出
          return exportPerList(queryParams);
        }

      }).then(response => {
        this.download(response.msg);
      }).catch(function () { });

    },
    handleExportDetail (form) {
      let typeId = form.examTypeId;
      let datas = {
        batchId: form.batchId,
        groupId: form.groupId,
        year: form.year
      };
      this.$confirm('是否确认导出当前数据项?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(function () {
        if (typeId == '1') {
          return exportGroupDetail(datas);
        } else if (typeId == '2') {
          return exportPatrolDetail(datas);
        } else if (typeId == '3') {
          return exportToExamOfficeDetail(datas);
        } else if (typeId == '4') {
          return exportPerDetail(datas);
        }
      }).then(response => {
        this.download(response.msg);
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
    getSummaries (param) {
      const { columns, data } = param;
      const sums = [];
      let num = this.form.examList ? this.form.examList.filter(item => item.ifHave == "1") : [];
      console.log(num)
      columns.forEach((column, index) => {
        if (index === 3) {
			if(this.form.status == '2'){
				sums[index] = '最终得分：' + this.form.allpoints;
				return;
			}else {
				sums[index] = '最终得分：' + (num.length > 0 ? '0' : '100');
				return;
			}
        } else {
          sums[index] = '';
          return;
        }
      });

      return sums;
    },
    //排序修改
    sortChange ({ column, prop, order }) {
      console.log(column, prop, order)
      this.queryParams.type = this.activeName;
      if (this.treeIndex.id) {
        this.queryParams.batchId = this.treeIndex.id;
      }
      if (this.activeName == '1') {
        this.queryParams.getMethod = 'getGroupList';
      } else {
        this.queryParams.getMethod = 'getPerList';
      }
      let forms = JSON.parse(JSON.stringify(this.queryParams));
      if (order != null || order != undefined) {
        forms.orderByParam = prop + '-' + (order == 'ascending' ? 'asc' : 'desc')
      }
      let that = this;
      this.loading = true;
      getList(forms).then(response => {
        that.groupCollectList = response.rows || [];
        that.total = Number(response.total);
        that.loading = false;
      });
    },

    // 处理数据, 给每一条数据加order
    handleData (dataList) {
      let order = 1;
      let data = dataList;
      for (let i = 0, length = data.length; i < length; i++) {
        if (i === 0) {
          data[i].order = order;
        } else {
          console.log(data[i].groupId, '--', i)
          if (data[i].groupId == data[i - 1].groupId) {
            data[i]['order'] = order
          } else {
            data[i]['order'] = ++order;
          }
        }
      }
      this.groupCollectList = data;

    },
    // 鼠标进入单元格
    handleMouseEnter (row, column, cell, event) {
      this.cellIndex = row.order;
      console.log("鼠标进入单元格")
    },
    // 给相应的rowIndex添加类名
    rowClassName ({ row, rowIndex }) {
      console.log(this.cellIndex, row.order);
      let r = -1;
      if (this.cellIndex == row.order && this.cellIndex != -1) {
        console.log("正常加入")
        return 'hover-row-art';
      }
    },
    // 鼠标离开
    handleMouseLeave (row, column, cell, event) {
      this.cellIndex = -1;
      console.log("鼠标移除单元格")
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

.detail {
  margin-left: 1rem;
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
