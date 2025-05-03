<template>
    <div class="app-container" v-if="dialogVisible">
        <div class="container">
            <div class="crad-header" v-show="showSearch">
                <div class="crad-title">
                    <span class="line"></span>
                    <span>历史记录</span>
                </div>
                <div class="back">
                    <img src="@/assets/images/back.png" fit="cover" @click="onBack" />
                </div>
            </div>

            <expand-collapse>
                <div slot="left">
                    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch"
                        label-width="120px">
                        <el-form-item label="业务单号：" prop="applyCode">
                            <el-input v-model="queryParams.applyCode" placeholder="请输入业务单号" clearable size="small"
                                @keyup.enter.native="handleQuery" />
                        </el-form-item>
                        <el-form-item label="巡察年份：" prop="year">
                            <el-date-picker clearable size="small" style="width: 205px" v-model="queryParams.year"
                                format="yyyy" value-format="yyyy" type="year" placeholder="请选择巡察年份"
                                @change="yearchangefd">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="巡察批次：" prop="batchId">
                            <el-select v-model="queryParams.batchId" placeholder="请选择巡察批次" clearable size="small">
                                <el-option v-for="dict in batchIdOptions" :key="dict.batchId" :label="dict.batchName"
                                    :value="dict.batchId" />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="被考核组：" prop="groupName">
                            <el-input v-model="queryParams.groupName" placeholder="请输入被考核组" clearable size="small"
                                @keyup.enter.native="handleQuery" />
                        </el-form-item>
                        <el-form-item label="被巡察单位：" prop="toExamCompanyName">
                            <el-input v-model="queryParams.toExamCompanyName" placeholder="请输入被巡察单位" clearable
                                size="small" @keyup.enter.native="handleQuery" />
                        </el-form-item>
                        <el-form-item label="考核类型：" prop="examTypeId" v-if="typeId == 1">
                            <el-select v-model="queryParams.examTypeId" placeholder="请选择考核类型" clearable size="small">
                                <el-option v-for="dict in examTypeOption" :key="dict.dictValue" :label="dict.dictLabel"
                                    :value="dict.dictValue" />
                            </el-select>
                        </el-form-item>
                        <!-- <el-form-item label="当前状态：" prop="status">
                        <el-select v-model="queryParams.status" placeholder="请选择当前状态" clearable size="small">
                            <el-option v-for="dict in approveStatusOption" :key="dict.dictValue" :label="dict.dictLabel"
                                :value="dict.dictValue" />
                        </el-select>
                    </el-form-item> -->
                    </el-form>
                </div>
                <div slot="right">
                    <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
                    <el-button size="mini" @click="resetQuery">重置</el-button>
                </div>
            </expand-collapse>
        </div>
        <div class="container">
            <div class="crad">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>{{ typeId == 1 ? '领导小组历史评分' : '巡察办历史评分' }}</span>
                    </div>
                    <!-- <el-row :gutter="10" class="mb12 utlsBtn mar20">
                        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
                    </el-row> -->
                </div>
                <div class="crad-content">
                    <el-table v-loading="loading" :data="examScopeScheduleList"
                        @selection-change="handleSelectionChange">
                        <el-table-column label="序号" align="center" type="index" width="55" />
                        <el-table-column label="业务单号" align="center" prop="applyCode" width="160" />
                        <el-table-column label="巡察年份" align="center" prop="year" width="100" />
                        <el-table-column label="批次" align="center" prop="batchName" width="200" />
                        <el-table-column label="被考核组" align="center" prop="groupName" />
                        <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" width="100" />
                        <el-table-column label="考核类型" align="center" prop="examTypeId">
                            <template slot-scope="scope">
                                {{ scope.row.examTypeId | filterAll(examTypeOption) }}
                            </template>
                        </el-table-column>
                        <el-table-column label="评分截止时间" align="center" prop="examLastTime" width="120" />
                        <el-table-column label="评分人" align="center" prop="fromUserName" width="120" />
                        <el-table-column label="当前状态" align="center" prop="status">
                            <template slot-scope="scope">
                                {{ scope.row.status == 0 ? '评分中' : '已完成' }}
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
                        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                            :limit.sync="queryParams.pageSize" @pagination="getList" />
                    </div>
                </div>
            </div>
        </div>
        <!-- 各种类型详情弹窗 -->
        <el-dialog :title="title" :visible.sync="isOpenQueryLeader" width="1200px" append-to-body="append-to-body">
            <el-form ref="queryformDetail" :model="form" label-width="120px" :disabled="true">
                <div class="crad">
                    <div class="crad-header">
                        <div class="crad-title">
                            <span class="line"></span>
                            <span>被考核小组信息</span>
                        </div>
                    </div>
                    <div class="crad-content">
                        <el-row :gutter="10">
                            <el-col :span="12">
                                <el-form-item label="业务单号：" prop="applyCode">
                                    <el-input v-model="form.applyCode" placeholder="" style="width: 70%" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="巡察年份：" prop="year">
                                    <el-input v-model="form.year" placeholder="" style="width: 70%" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="巡察批次：" prop="batchName">
                                    <el-input v-model="form.batchName" placeholder="" style="width: 70%" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="被考核组：" prop="groupName">
                                    <el-input v-model="form.groupName" placeholder="" style="width: 70%" />
                                    <el-button type="primary" :disabled="false" class="detail">查看</el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="被巡察单位：" prop="toExamCompanyName">
                                    <el-input v-model="form.toExamCompanyName" placeholder="" style="width: 70%" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="考核类型：" prop="examTypeName">
                                    <el-input v-model="form.examTypeName" placeholder="" style="width: 70%" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="评分截止时间：" prop="examLastTime">
                                    <el-input v-model="form.examLastTime" placeholder="" style="width: 70%" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="评分人：" prop="fromUserName"
                                    v-if="form.examTypeId != 1 && form.examTypeId != 2">
                                    <el-input v-model="form.fromUserName" placeholder="" style="width: 70%" />
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
                    <!-- 查看巡察考核评分记录-领导小组评分 -->
                    <div class="crad-content" v-if="form.examTypeId == 1">
                        <el-row :gutter="10" class="mb12 utlsBtn mar20">
                            <el-col :span="1.5">
                                <el-button type="primary" size="mini" @click="handleExport(form.examTypeId)">
                                    导出评分表
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-form ref="form1" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
                            <el-table v-loading="loading" :data="form.checkedInfoList"
                                @selection-change="handleSelectionChange" border>
                                <!-- :span-method="objectSpanMethod" -->
                                <el-table-column label="序号" align="center" type="index" width="55" />
                                <el-table-column label="评分人" align="center" prop="fromUserName" width="150" />
                                <el-table-column label="评分状态" align="center" prop="status" width="100">
                                    <template slot-scope="scope">
                                        {{ scope.row.status == 0 ? '评分中' : '已完成' }}
                                    </template>
                                </el-table-column>
                                <el-table-column label="评分时间" align="center" prop="examTime" width="160">
                                    <template slot-scope="scope">
                                        {{ scope.row.examTime ? scope.row.examTime : '--' }}
                                    </template>
                                </el-table-column>
                                <el-table-column label="被评分人姓名" align="center" prop="toUserName" width="100" />
                                <el-table-column label="组内职责" align="center" prop="userTypeName" />
                                <el-table-column align="center" v-for="(item, index) in tableData" :key="index"
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
                                <el-table-column label="合计（分）" align="center" prop="allpoints" width="100"
                                    fixed="right">
                                </el-table-column>
                            </el-table>
                        </el-form>
                    </div>
                    <!-- 查看巡察考核评分记录-巡察办评分 -->
                    <div class="crad-content" v-if="form.examTypeId == 2">
                        <el-row :gutter="10" class="mb12 utlsBtn mar20">
                            <el-col :span="1.5">
                                <el-button type="primary" size="mini" @click="handleExport(form.examTypeId)">
                                    导出评分表
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-form ref="form2" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
                            <el-table v-loading="loading" :data="form.checkedInfoList"
                                @selection-change="handleSelectionChange" border :span-method="objectSpanMethodOffice">
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
                    </div>
                    <!-- 查看巡察考核评分记录-被巡察单位评分 -->
                    <div class="crad-content" v-if="form.examTypeId == 3">
                        <el-row :gutter="10" class="mb12 utlsBtn mar20">
                            <el-col :span="1.5">
                                <el-button type="primary" size="mini" @click="handleExport(form.examTypeId)">
                                    导出评分表
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-form ref="form3" :model="form" label-width="120px" :disabled="disabled">
                            <el-table v-loading="loading" :data="form.examList" border show-summary
                                :summary-method="getSummaries">
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
                    </div>
                    <!-- 查看巡察考核评分记录-巡察员评分 -->
                    <div class="crad-content" v-if="form.examTypeId == 4">
                        <el-row :gutter="10" class="mb12 utlsBtn mar20">
                            <el-col :span="1.5">
                                <el-button type="primary" size="mini" @click="handleExport(form.examTypeId)">
                                    导出评分表
                                </el-button>
                            </el-col>
                            <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
                        </el-row>
                        <el-form ref="form4" :model="form" label-width="120px">
                            <el-table v-loading="loading" :data="proTeamList" border :header-cell-style="headCellstyle"
                                :header-row-style="headsrowStyle">
                                <el-table-column label="序号" align="center" type="index" width="55">
                                    <el-table-column label="" align="center" type="index" width="55">

                                    </el-table-column>
                                </el-table-column>
                                <el-table-column label="组员姓名" align="center">
                                    <el-table-column label="" align="center" prop="toUserName">
                                        <template slot="header" slot-scope="scope">
                                            <p>评分内容与标准</p>
                                        </template>
                                        <template slot-scope="scope">
                                            {{ scope.row.toUserName }}（{{ scope.row.userTypeName }}）
                                        </template>
                                    </el-table-column>
                                </el-table-column>

                                <el-table-column align="center" v-for="(item, index) in tableDataPre" :key="index"
                                    :width="(Number(item.examContant ? item.examContant.length : 0) + item.examMaxScope ? item.examMaxScope.toString().length : 0 + 3) * 15">
                                    <template slot="header" slot-scope="scope">
                                        <!-- {{item}} -->
                                        {{ item.scopeTitle }}（{{ item.mixScope }}%）
                                    </template>
                                    <el-table-column label="" align="center">
                                        <template slot="header" slot-scope="scope">
                                            {{ item.scopeContent }}
                                        </template>
                                        <template slot-scope="scope">
                                            {{
                                                    scope.row.examList[index]['examResult'] ?
                                                        scope.row.examList[index]['examResult'] : '--'
                                            }}
                                        </template>
                                    </el-table-column>
                                </el-table-column>
                                <el-table-column label="考评总分" align="center">
                                    <el-table-column label="" align="center" width="105" prop="numberAll">
                                        <template slot="header" slot-scope="scope">
                                            <p @click="onshow" v-if="!pageshow">展开标准 <i class="el-icon-arrow-down"></i>
                                            </p>
                                            <p @click="onshow" v-else>收起标准 <i class="el-icon-arrow-up"></i></p>
                                        </template>
                                        <template slot-scope="scope">
                                            {{ scope.row.allpoints }}
                                        </template>
                                    </el-table-column>
                                </el-table-column>
                                <el-table-column label="考评等级" align="center">
                                    <el-table-column label="" align="center" width="105" prop="level">

                                    </el-table-column>
                                </el-table-column>
                            </el-table>
                        </el-form>
                    </div>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="isOpenQueryLeader = false">关 闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
    getBatchList,
    getProgressList,
    getExamContentByExamType,
    getLeaderListProgress,
    getPatrolProgressInfo,
    getToexamProgressInfo,
    getGroupMemberProgress,
} from "@/api/checkedAll/checkedInfo";


import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
export default {
    name: "ExamScopeSchedule",
    components: { ExpandCollapse },
    props: {
        dialogVisible: {
            type: Boolean,
            default: true,
        },
        histypeId: {
            type: String,
            default: '1',
        },
    },
    data () {
        return {
            typeId: null,
            disabled: false,
            // 遮罩层
            loading: false,
            // 是否显示查看弹出层-领导小组
            isOpenQueryLeader: false,
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
            // 巡察考核评分记录 表格数据
            examScopeScheduleList: [],
            // 弹出层标题
            title: "详情",
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
                url: process.env.VUE_APP_BASE_API + "/checkedAll/examScopeSchedule/importData",
            },
            // 查询参数
            queryParams: {
                type: null,
                pageNum: 1,
                pageSize: 10,
                applyCode: null,
                year: null,
                batchId: null,
                groupNames: null,
            },
            // 表单参数
            form: {},
            batchIdOptions: [],
            approveStatusOption: [],
            examTypeOption: [],

            // 巡察考核表格数据
            checkedInfoList: [],
            tableData: [],
            tableDataPre: [],
            proTeamList: [],
            pageshow: false,
            heiH: "96px",
            section: [],
            // 表单校验
            rules: {}
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
        this.typeId = this.histypeId ? this.histypeId : '1';
        if (this.$route.query.applyCode) {
            this.queryParams.applyCode = this.$route.query.applyCode;
            this.handleQuery();
        }
        this.queryParams.type = this.typeId;
        this.getDicts("approve_status").then(res => {
            this.approveStatusOption = res.data ? res.data : [];
        })
        this.getDicts("exam_type").then(res => {
            this.examTypeOption = res.data ? res.data : [];
        })
        getExamContentByExamType({ examTypeId: '1' }).then(res => {
            this.tableData = res.data
        })
        getExamContentByExamType({ examTypeId: '4' }).then(res => {
            this.tableDataPre = res.data
        })
        
        this.yearchangefd('');
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
        ]
        this.getList();
    },
    methods: {
        onBack (e) {
            this.$emit("close", e);
        },
        yearchangefd (val) {
            //批次
            this.queryParams.batchId = null;
            getBatchList({ year: val }).then(res => {
                this.batchIdOptions = res.data ? res.data : [];
            })
        },
        /** 查询巡察考核评分记录 列表 */
        getList () {
            this.loading = false;
            let queryParams = JSON.parse(JSON.stringify(this.queryParams))
            queryParams.fromUserId = this.$store.getters.userInfo.userName;
            queryParams.status = '1';
            queryParams.type = '1';
            queryParams.examTypeId = this.histypeId;
            getProgressList(queryParams).then(response => {
                this.examScopeScheduleList = response.rows || [];
                this.total = Number(response.total);
                this.loading = false;
            });
            // this.handleDetails(1);
        },
        handleDetails (examtype) {
            let type = this.examTypeOption.filter(item => item.dictValue == examtype.examTypeId)[0].dictLabel
            this.title = type + "评分详情"
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
            (examtype.examTypeId == 1) && getLeaderListProgress(datas).then(res => {
                let data = res.rows
                that.isOpenQueryLeader = true;
                that.form.checkedInfoList = data;
            });
            (examtype.examTypeId == 2) && getPatrolProgressInfo(datas).then(res => {
                let data = res.rows
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
                let data = res.rows
                // debugger
                that.isOpenQueryLeader = true;
                that.form = { ...that.form, ...data[0] };
            });
            if (examtype.examTypeId == 4) {
                datas.fromId = examtype.fromId
            }
            (examtype.examTypeId == 4) && getGroupMemberProgress(datas).then(res => {
                that.isOpenQueryLeader = true;
                let dataList = this.tableDataPre;
                let data = res.rows ? res.rows : [];
                data.forEach(item => {
                    let a = item.allpoints;
                    let txt = "";
                    for (let i = 0, len = that.section.length; i < len; i++) {
                        if (a >= that.section[i].min && a <= that.section[i].max) {
                            txt = that.section[i].name
                            break
                        }
                    }

                    item.level = txt;
                    // item.numberAll = ""
                    // item.level = ""
                    item.examList = item.examList.map((itss, idss) => {
                        let dataExam = JSON.parse(JSON.stringify(dataList)).filter(itm => itm.id == itss.examId)[0];
                        itss.scopeContent = dataExam.scopeContent;
                        itss.scopeTitle = dataExam.scopeTitle
                        itss.mixScope = dataExam.mixScope
                        return itss
                    })
                })
                console.log(data, "data--")
                that.proTeamList = data;
            });
        },
        // 取消按钮
        cancel () {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset () {
            this.form = {
                createdBy: null,
                createdTime: null,
                updatedBy: null,
                updatedTime: null,
                id: null,
                applyId: null,
                fromId: null,
                scope: null,
                scopeTypeId: null,
                scopeContentId: null,
                toUserId: null
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
            this.queryParams.type = this.typeId;
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange (selection) {
            this.ids = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 导出按钮操作 */
        handleExport () {
            const queryParams = this.queryParams;
            this.$confirm('是否确认导出所有巡察考核评分记录 数据项?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function () {
                return exportExamScopeSchedule(queryParams);
            }).then(response => {
                this.download(response.msg);
            }).catch(function () { });
        },
        /** 导入按钮操作 */
        openImportTable () {
            this.upload.title = "巡察考核评分记录 数据导入";
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
        getSummaries (param) {
            const { columns, data } = param;
            const sums = [];
            let num = this.form.examList ? this.form.examList.filter(item => item.ifHave == "1") : [];
            console.log(num)
            columns.forEach((column, index) => {
                if (index === 3) {
                    sums[index] = '最终得分：' + (num.length > 0 ? '0' : '100');
                    return;
                } else {
                    sums[index] = '';
                    return;
                }
            });
            return sums;
        },
        // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
        // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
        // 也可以返回一个键名为rowspan和colspan的对象。
        headCellstyle ({ row, column, rowIndex, columnIndex }) {
            // debugger
            // console.log("column",rowIndex,columnIndex)
            if (rowIndex == 1 && row[6] && row[7]) {
                row[6].colSpan = 2 //第三列的表头占据3个单元格
                row[7].colSpan = 0
            }
            if (rowIndex == 1 && columnIndex == 7) {
                return 'display: none'
            }
        },
        headsrowStyle ({ row, rowIndex, num }) {
            if (rowIndex == 1) {
                return 'height:' + this.heiH + ';font-weight:normal;'
            } else {
                return 'font-weight:bold;'
            }
        },
        onshow () {
            this.pageshow = !this.pageshow
            if (this.pageshow) {
                this.heiH = "100%"
            } else {
                this.heiH = "100px"
            }
        },
    }
};
</script>



<style lang="scss" scoped>
.app-container {
    padding: 0;
    background: #F5F7F9;

    .crad-header {
        display: flex;
        align-items: center;
        border-radius: 2px;
        height: 48px;
        padding-right: 16px;
        justify-content: space-between;
    }

    .back {
        background: #fff;
        text-align: right;

        img {
            width: 1.2rem;
            height: 1.2rem;
            margin: 0 1rem;
        }
    }
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

.detail {
    margin-left: 1rem;
}

/deep/ .el-table thead.is-group th.el-table__cell {
    height: inherit;
    transition: all 1s ease 0;
    font-weight: inherit;
}

/deep/ .el-table th.el-table__cell>.cell {
    height: inherit;
    transition: all 1s ease 0;
    font-weight: inherit;
}

/deep/ .specialForm .el-form-item__content {
    margin-left: 0 !important;
}
</style>

