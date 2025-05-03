<template>
    <div class="app-container">
        <expand-collapse v-show="showSearch">
            <div slot="left">
                <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
                    <el-form-item label="业务单号：" prop="applyCode">
                        <el-input v-model="queryParams.applyCode" placeholder="请输入业务单号" clearable size="small"
                            @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item label="巡察年份：" prop="year">
                        <el-date-picker clearable size="small" style="width: 205px" v-model="queryParams.year"
                            format="yyyy" value-format="yyyy" type="year" placeholder="请选择巡察年份" @change="yearchangefd">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="巡察批次：" prop="batchId">
                        <el-select v-model="queryParams.batchId" placeholder="请选择巡察批次" clearable size="small">
                            <el-option v-for="dict in batchIdOptions" :key="dict.batchId" :label="dict.batchName"
                                :value="dict.batchId" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="巡察组：" prop="groupNames">
                        <el-input v-model="queryParams.groupNames" placeholder="请输入巡察组" clearable size="small"
                            @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item label="巡察组长" prop="groupLeaderNames">
                        <el-input v-model="queryParams.groupLeaderNames" placeholder="请输入巡察组长" clearable size="small"
                            @keyup.enter.native="handleQuery" />
                    </el-form-item>
                </el-form>
            </div>
            <div slot="right">
                <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
                <el-button size="mini" @click="resetQuery">重置</el-button>
            </div>
        </expand-collapse>

        <div class="container">
            <div class="crad">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>巡察员考核</span>
                    </div>
                </div>
            </div>
            <div class="crad-content">
                <el-row :gutter="10" class="mb12 utlsBtn mar20">
                    <el-col :span="1.5">
                        <el-button type="primary" size="mini" @click="handleAdd"
                            v-hasPermi="['checkedAll:checkInfo:add']">
                            发起考核</el-button>
                    </el-col>
                    <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
                </el-row>
                <el-table v-loading="loading" :data="checkedInfoList" @selection-change="handleSelectionChange">
                    <!-- <el-table-column type="selection" width="55" align="left" /> -->
                    <el-table-column label="序号" align="center" type="index" width="55" />
                    <el-table-column label="业务单号" align="center" prop="applyCode" width="160" />
                    <el-table-column label="巡察年份" align="center" prop="year" width="100" />
                    <el-table-column label="巡察批次" align="center" prop="batchName" width="200" />
                    <el-table-column label="标题" align="center" prop="title" />
                    <el-table-column label="被考核组" align="center" prop="groupNames" />
                    <el-table-column label="组长" align="center" prop="groupLeaderNames" />
                    <el-table-column label="当前状态" align="center" prop="approveStatus">
                        <template slot-scope="scope">
                            {{ scope.row.approveStatus | filterAll(approveStatusOption) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                        width="200">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" @click="handleDetails(scope.row)">查看</el-button>
                            <el-button size="mini" type="text" v-if="scope.row.approveStatus == '2'"
                                @click="handleQueryOption(scope.row)">查看评分进度</el-button>
                            <el-button size="mini" type="text"
                                v-if="scope.row.approveStatus == '0' || scope.row.approveStatus == '3'"
                                @click="handleAdd(scope.row)">修改</el-button>
                            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
                                v-if="scope.row.approveStatus == '0'">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- <div class="pageBox">
                    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                        :limit.sync="queryParams.pageSize" @pagination="getList" />
                </div> -->
            </div>

        </div>
        <!-- 添加或修改巡察考核发起记录对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body :close-on-click-modal="false"
            :fullscreen="isFullscreen">
            <div slot="title" style="position: relative">
                <span>{{ title }}</span>
                <el-button type="text" style="position: absolute; right: 40px" size="small"
                    @click="isFullscreen = !isFullscreen">{{ isFullscreen ? "关闭全屏" : "打开全屏" }}</el-button>
            </div>
            <div class="crad">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>发起考核</span>
                    </div>
                </div>
                <div class="crad-content">
                    <expand-collapse>
                        <div slot="left">
                            <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules"
                                v-loading="loadingAdd">
                                <el-form-item label="巡察年份：" prop="seeyear">
                                    <el-date-picker size="small" style="width: 205px" format="yyyy" value-format="yyyy"
                                        v-model="seeParams.year" type="year" placeholder="请选择巡察年份" @change="yearchange"
                                        :clearable="false">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="巡察批次：" prop="seebatchId">
                                    <el-select v-model="seeParams.batchId" placeholder="请选择巡察批次" size="small"
                                        @change="handleQueryChild">
                                        <el-option v-for="dict in batchIdChildOptions" :key="dict.batchId"
                                            :label="dict.batchName" :value="dict.batchId" :clearable="false" />
                                    </el-select>
                                </el-form-item>
                            </el-form>
                        </div>
                    </expand-collapse>
                </div>
                <div class="crad-content crad-content-top20">
                    <el-row :gutter="10" class="mb12 utlsBtn mar20 btnright">
                        <el-col :span="1.5">
                            <el-button type="primary" size="mini" @click="handleAddChild">
                                发起考核</el-button>
                        </el-col>
                    </el-row>
                    <el-table v-loading="loadingChild" :data="checkedInfoListChild"
                        @selection-change="handleSelectionChangeChild" ref="elTable">
                        <el-table-column type="selection" width="55" align="center" />
                        <el-table-column label="序号" align="center" type="index" width="55" />
                        <el-table-column label="巡察组" align="center" prop="groupName" >
							<template slot-scope="scope">
							    <span class="btmLine" @click="onGrounpDetail(scope.row)">{{
							            scope.row.groupName
							    }}</span>
							</template>
                        </el-table-column>
                        <el-table-column label="组长" align="center" prop="groupLeaderName" />
                        <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" />
                        <el-table-column label="巡察年份" align="center" prop="year" />
                        <el-table-column label="批次" align="center" prop="batchName" />
                    </el-table>
                    <!-- <div class="pageBox">
                        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                            :limit.sync="queryParams.pageSize" @pagination="getList" />
                    </div> -->
                </div>
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>考核信息设置</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
                        <el-form-item label="标题：" prop="title">
                            <el-input v-model="form.title" placeholder="请输入标题" />
                        </el-form-item>
                        <el-table :data="InfoListChild" :span-method="objectSpanMethod" border
                            style="width: 100%; margin-top: 20px">
                            <el-table-column type="index" label="序号" width="55" align="center">
                            </el-table-column>
                            <el-table-column prop="groupName" label="巡察组" align="center" width="100">
                                <template slot-scope="scope">
                                    <div>{{ scope.row.groupName }}</div>
                                    <el-button type="primary" size="mini" @click="handleRemoveChild(scope)"
                                        style="margin-top:10px">
                                        删除</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column prop="examTypeName" label="考核类型" align="center" width="180">
                            </el-table-column>
                            <el-table-column label="* 评分人选择" align="center" width="160">
                                <template slot-scope="scope">
                                    <!--  -->
                                    <el-form-item label="" prop="examUserIds" class="specialForm" :rules='{
                                        required: true,
                                        message: "评分人不能为空",
                                        trigger: "change",
                                        validator: (rule, value, callback) => {
                                            if (showList[scope.row.groupId + "_" + scope.row.examTypeId].examUserIds.length == 0) {
                                                callback(Errors("至少选择一个"))
                                            } else {
                                                callback()
                                            }
                                        }
                                    }'>
                                        <el-select
                                            v-model="showList[scope.row.groupId + '_' + scope.row.examTypeId].examUserIds"
                                            placeholder="请选择" @change="PeoChange()">
                                            <el-option
                                                v-for="item in (ExamLeadListOptions.filter(item => item.groupId == scope.row.groupId).length > 0 && ExamLeadListOptions.filter(item => item.groupId == scope.row.groupId)[0].examTypeList.length > 0 ? ExamLeadListOptions.filter(item => item.groupId == scope.row.groupId)[0].examTypeList.filter(itm => itm.examTypeId == scope.row.examTypeId)[0].fromExamList : [])"
                                                :key="item.userId" :label="item.userName" :value="item.userId">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column label="*评分截止时间" align="center" width="210">
                                <template slot-scope="scope">
                                    <el-form-item label="" prop="examLastTime" class="specialForm" :rules='{
                                        required: true,
                                        message: "截止时间不能为空",
                                        trigger: "change",
                                        validator: (rule, value, callback) => {
                                            if (showList[scope.row.groupId + "_" + scope.row.examTypeId].examLastTime == "" || showList[scope.row.groupId + "_" + scope.row.examTypeId].examLastTime == null) {
                                                callback(Errors("请选择截止时间"))
                                            } else {
                                                callback()
                                            }
                                        }
                                    }'>
                                        <el-date-picker
                                            v-model="showList[scope.row.groupId + '_' + scope.row.examTypeId].examLastTime"
                                            type="datetime" placeholder="请选择截止时间" style="width:190px"
                                            default-time="23:59:59" format="yyyy-MM-dd HH:mm"
                                            value-format="yyyy-MM-dd HH:mm" @change="PeoChange()">
                                        </el-date-picker>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column prop="examRemark" label="备注" align="center"
                                :width="isFullscreen ? '' : '400'">
                                <template slot-scope="scope">
                                    <el-form-item prop="examRemark" label="" class="specialForm">
                                        <el-input placeholder="请输入备注"
                                            v-model="showList[scope.row.groupId + '_' + scope.row.examTypeId].examRemark"
                                            @change="PeoChange()">
                                        </el-input>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column prop="delete" label="操作" align="center" fixed="right" width="100">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="text" @click="handleDeleteChild(scope)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-form>
                </div>

            </div>
            <!-- <deep-btn @close="cancel" @saveDraftData="saveBtn" :businessKey="form.id" v-if="open">
                <el-button type="primary" @click="saveBtn">暂 存</el-button>
                <el-button type="primary" @click="submitForm">提 交</el-button>
            </deep-btn> -->
            <deep-btn @close="cancel" @saveDraftData="saveBtn" :businessKey="form.id" v-if="open"
                :isTodo="isFullscreen">
                <span slot="btns">
                    <el-button type="primary" @click="saveBtn">暂 存</el-button>
                    <el-button type="primary" @click="submitForm">提 交</el-button>
                </span>
            </deep-btn>
        </el-dialog>
        <!-- 查看巡察考核发起记录对话框 -->
        <el-dialog :title="title" :visible.sync="isOpenQuery" width="1000px" append-to-body="append-to-body"
            :before-close="close">
            <el-form ref="queryformDetail" :model="form" label-width="120px" :disabled="true">
                <div class="crad">
                    <div class="crad-content">
                        <el-form-item label="标题：" prop="title">
                            <el-input v-model="form.title" placeholder="请输入标题" readonly />
                        </el-form-item>
                    </div>
                    <div class="crad-header">
                        <div class="crad-title">
                            <span class="line"></span>
                            <span>被考核小组信息</span>
                        </div>
                    </div>
                    <div class="crad-content">
                        <el-table v-loading="loadingChild" :data="form.list"
                            @selection-change="handleSelectionChangeChild">
                            <!-- <el-table-column type="selection" width="55" align="left" /> -->
                            <el-table-column label="序号" align="center" type="index" width="55" />
                            <el-table-column label="巡察组" align="center" prop="groupName" >
								<template slot-scope="scope">
								    <span class="btmLine" @click="onGrounpDetail(scope.row)">{{
								            scope.row.groupName
								    }}</span>
								</template>
                            </el-table-column>
                            <el-table-column label="组长" align="center" prop="groupLeaderName" />
                            <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" />
                            <el-table-column label="巡察年份" align="center" prop="year" />
                            <el-table-column label="批次" align="center" prop="batchName" />
                            <!-- <el-table-column label="待考核类型" align="center" prop="examTypeList" width="250" /> -->
                        </el-table>
                        <!-- <div class="pageBox">
                            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                                :limit.sync="queryParams.pageSize" @pagination="getList" />
                        </div> -->
                    </div>
                    <div class="crad-header">
                        <div class="crad-title">
                            <span class="line"></span>
                            <span>考核信息</span>
                        </div>
                    </div>
                    <div class="crad-content">
                        <el-table :data="InfoListChildDetail" border v-loading="loadingChild"
                            style="width: 100%; margin-top: 20px" :span-method="objectSpanMethodDetail">
                            <!-- :span-method="objectSpanMethod" -->
                            <el-table-column type="index" label="序号" width="55" align="center">
                            </el-table-column>
                            <el-table-column prop="groupName" label="巡察组" align="center">
                                <template slot-scope="scope">
                                    {{ scope.row.groupName }}
                                </template>
                            </el-table-column>
                            <el-table-column prop="examTypeName" label="考核类型" align="center">
                            </el-table-column>
                            <el-table-column label="* 评分人选择" align="center" width="150">
                                <template slot-scope="scope">
                                    {{ showListDetail[scope.row.groupId + '_' + scope.row.examTypeId].examUserNames }}
                                </template>
                            </el-table-column>
                            <el-table-column label="*评分截止时间" align="center" width="210">
                                <template slot-scope="scope">
                                    {{ showListDetail[scope.row.groupId + '_' + scope.row.examTypeId].examLastTime }}
                                </template>
                            </el-table-column>
                            <el-table-column prop="examRemark" label="备注" align="center" width="200">
                                <template slot-scope="scope">
                                    {{ showListDetail[scope.row.groupId + '_' + scope.row.examTypeId].examRemark }}
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <all-flow-step :allFlowStep="process.allFlowStep" v-if="form.approveStatus != '0'"></all-flow-step>
                    <!-- <all-flow-step :allFlowStep="process.allFlowStep" v-if="form.approveStatus != '0'"></all-flow-step>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="isOpenQuery = false" :disabled="false">关 闭</el-button>
                    </div> -->
                </div>
            </el-form>
            <deep-btn @close="cancel" :isTodo="false" type="0" :ischeck="ischeck" v-if="isOpenQuery"></deep-btn>
        </el-dialog>
        <submit-process processKey="exam_process" stepName="巡察办室经理审批" :dialogVisible="submitProcessShow" @close="close"
            @complete="saveFromData" :title="stitle"></submit-process>
        <!-- <SubmitIdx ref="siRef" processKey="exam_process" stepName="巡察办室经理审批" @close="cancel"></SubmitIdx> -->
		<patrol-group ref="PatrolGroup" :groupObject="groupObject" :dialogVisible="isOpenGroup"
		    @close="isOpenGroup = false"></patrol-group>
    </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
    getBatchList,//1
    listCheckedInfo,//1
    saveApplyInfoPer,//1
    GroupListPer,//1
    getExamLeaderList,
    getApplyInfo,//1
    delCheckedInfo,//1
    submitApplyInfoPer,//1
    addCheckedInfo,
    updateCheckedInfo,
    exportCheckedInfo,
    importTemplate,
    disableEnable
} from "@/api/checkedAll/checkedInfo";


import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
import ApplyFooter from "@/components/ApplyFooter";
import deepBtn from "@/components/WorkFlow/deepBtn";
import SubmitIdx from "@/components/SubmitIdx";
import SubmitProcess from '@/components/WorkFlow/submitProcess';
import PatrolGroup from "@/components/PatrolGroup";

import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
export default {
    name: "CheckedInfo",
    components: { ExpandCollapse, deepBtn, ApplyFooter, SubmitIdx, SubmitProcess, AllFlowStep, fetchWorkFlowInfo,PatrolGroup },

    data () {
        return {
            ischeck: 1,
            isFullscreen: false,
            stitle: "提交",
            false: false,
            batchIdOptions: [],
            batchIdChildOptions: [],
            approveStatusOption: [],
            submitProcessShow: false,
            // processID: "123456",
            process: { allFlowStep: [] },
            disabled: false,
            // 遮罩层
            loading: true,
            loadingAdd: false,
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
            idsChild: [],
            singleChild: true,
            multipleChild: true,
            loadingChild: false,
            // 巡察考核发起记录表格数据
            checkedInfoList: [],
            //发起考核组备选人组
            checkedInfoListChild: [],
            //拆分展示提交组
            InfoListChild: [],
            InfoListChildDetail: [],
            infoIndex: null,
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
                year: null,
                batchId: null,
                id: null,
            },
            seeParamsCopy: {},
            // 查询参数
            queryParams: {
                type: "2",
                pageNum: 1,
                pageSize: 10,
                applyCode: null,
                year: null,
                batchId: null,
                groupNames: null,
                groupLeaderNames: null,
                // approveStatus: null,
            },
            // 表单参数
            form: {
                title: null,
            },
            ExamLeadListOptions: [],
            options: [],
            showListDetail: {},
            showList: {},
            // 表单校验
            rules: {
                title: [{ required: true, message: "标题不能为空", trigger: "change" }],
                // examUserIds: [{ required: true, message: "评分人不能为空", trigger: "change",validator:tagslen }],
                // seeyear:[{ required: true, message: "年份不能为空", trigger: "change" }],
                // seebatchId:[{ required: true, message: "批次不能为空", trigger: "change" }],
            },
			groupObject: {
			    taskId: "",
			    piciId: "",
			},
			isOpenGroup: false
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
        examFilter (val) {
            if (!val.length) return "";
            return val.map(item => item.examTypeName).join()
        }
    },
    created () {
        //审核状态
        this.getDicts("approve_status").then(res => {
            this.approveStatusOption = res.data ? res.data : [];
        })
        //批次
        // getBatchList().then(res => {
        //     this.batchIdOptions = res.data ? res.data : [];
        // })

        this.yearchangefd('');
        this.yearchange('');
        this.getList();
    },
    methods: {
        //审核完成后
        handleAF (e) {

        },
        // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
        // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
        // 也可以返回一个键名为rowspan和colspan的对象。
        objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
            // console.log(this.InfoListChild)
            // console.log(row.examTypeId)
            if (columnIndex === 1) {
                let data = this.InfoListChild.filter((item) => item.groupId == row.groupId);
                let idx = 0;
                data.forEach((item, index) => { if (item.examTypeId == row.examTypeId) { idx = index } })
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
        objectSpanMethodDetail ({ row, column, rowIndex, columnIndex }) {
            // console.log(this.InfoListChildDetail)
            // console.log(row.examTypeId)
            if (columnIndex === 1) {
                let data = this.InfoListChildDetail.filter((item) => item.groupId == row.groupId);
                let idx = 0;
                data.forEach((item, index) => { if (item.examTypeId == row.examTypeId) { idx = index } })
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
        yearchangefd (val) {
            //批次
            this.queryParams.batchId = null;
            getBatchList({ year: val }).then(res => {
                this.batchIdOptions = res.data ? res.data : [];
            })
        },
        yearchange (val) {
            console.log(val)
            //批次
            this.seeParams.batchId = null;
            getBatchList({ year: val }).then(res => {
                this.batchIdChildOptions = res.data ? res.data : [];
            })
        },
        Errors (err) {
            let text = new Error(err);
            return text
        },
        //发起审核-搜索
        handleQueryChild () {
            if (this.seeParams.year == null || this.seeParams.year == "") {
                this.$message({
                    message: "请选择巡察年份",
                    type: 'warning'
                });
                return
            }
            if (this.seeParams.batchId == null || this.seeParams.batchId == "") {
                this.$message({
                    message: "请选择巡察批次",
                    type: 'warning'
                });
                return
            }
            this.form.title = this.seeParams.year + "年" + this.batchIdChildOptions.filter((item) => item.batchId === this.seeParams.batchId)[0].batchName + "巡察组成员工作考评";

            // 数据--不知道有没有十几条，要翻页
            GroupListPer(this.seeParams).then(res => {
                this.checkedInfoListChild = res.data
            })
            //清空一下
            this.InfoListChild = []
            this.seeParamsCopy = JSON.parse(JSON.stringify(this.seeParams))
            // this.checkedInfoListChild = [
            //     { id: 1, groupName: "第三巡察组", groupId: 10, groupLeaderNames: "胡歌", toExamCompanyName: "深圳市公司", year: "2021", batchName: "第一批", dai: [{ examTypeId: 1, name: "领导小组评分" }, { examTypeId: 2, name: "巡察办评分" }] },
            //     { id: 2, groupName: "第四巡察组", groupId: 20, groupLeaderNames: "胡切", toExamCompanyName: "深圳市公司", year: "2021", batchName: "第一批", dai: [{ examTypeId: 1, name: "领导小组评分" }, { examTypeId: 2, name: "巡察办评分" }, { examTypeId: 3, name: "被巡察单位评分" }] },
            // ]
            //拼接类型
            // this.checkedInfoListChild.forEach((item) => {
            //     item.examTypeList = item.examTypeList ? item.examTypeList : "" + item.dai.map((itm) => { return itm.name })
            // })
        },
        //发起审核-发起审核
        handleAddChild () {
            console.log(this.idsChild)
            if (this.idsChild.length == 0) {
                this.$message({
                    message: "请至少选择一个巡察组",
                    type: 'warning'
                });
                return
            }
            let form = {
                batchId: this.seeParams.batchId,
                groupIds: this.idsChild.toString(),
                year: this.seeParams.year,
            }
            //返回评分人总列表
            getExamLeaderList(form).then(res => {
                this.ExamLeadListOptions = res.data
            })
            let Array = [];
            this.checkedInfoListChild.forEach((item) => {
                this.idsChild.forEach((itm) => {
                    if (itm == item.groupId) {
                        Array.push(item)
                    }
                })
            })
            //拆分类型
            this.InfoListChild = [];
            Array.forEach((item) => {
                item.examTypeList && item.examTypeList.forEach((itm, idx) => {
                    // 巡察组 考核类型 评分人选择 评分截止时间 备注
                    let child = itm;
                    child.groupId = item.groupId;//巡察组id
                    child.groupName = item.groupName;//巡察组name
                    child.examUserIds = '';//评分人选择单选，之后拼接字符串
                    child.examLastTime = "";
                    child.examRemark = "";
                    child.index = idx;
                    this.InfoListChild.push(child)
                })
            })
            // this.showList
            let data = {};
            for (let i in this.InfoListChild) {
                data[this.InfoListChild[i].groupId + "_" + this.InfoListChild[i].examTypeId] = { examUserIds: '', examLastTime: "", examRemark: "" };
            }
            this.showList = data;
        },
        //评分人选择
        PeoChange (e) {
            // this.showList.forEach((item)=>{
            //     // item.split("_")[0]
            //     // item.split("_")[1]
            //     pagein = this.InfoListChild.filter((item)=>item.groupId==item.split("_")[0]&&item.examTypeId==item.split("_")[1])
            // })
            for (let i in this.showList) {
                // i.split("_")[0]
                // i.split("_")[1]
                this.InfoListChild.map((item) => {
                    if (item.groupId == i.split("_")[0] && item.examTypeId == i.split("_")[1]) {
                        item.examUserIds = this.showList[i].examUserIds
                        item.examLastTime = this.showList[i].examLastTime
                        item.examRemark = this.showList[i].examRemark
                        return item
                    }
                    return item
                })
            }
            console.log(this.InfoListChild, this.showList)
        },
        //删除巡察组-总
        handleRemoveChild (data) {
            console.log(this.InfoListChild, this.showList, data)
            let row = data.row;
            let index = data.$index;
            let that = this;
            this.$confirm('是否删除当前巡察组？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                let newInfo = that.InfoListChild.filter(item => item.groupId != row.groupId);
                that.InfoListChild = newInfo;
                this.$message({
                    message: "删除成功",
                    type: 'success'
                });
            }).catch(() => {

            });
        },
        //删除考核类型-分
        handleDeleteChild (data) {
            console.log(this.InfoListChild, this.showList, data)
            let row = data.row;;
            let index = data.$index;
            let that = this;
            this.$confirm('是否删除当前考核类型？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                this.InfoListChild.splice(index, 1);
                delete that.showList[row.groupId + "_" + row.examTypeId];
                this.$message({
                    message: "删除成功",
                    type: 'success'
                });
            }).catch(() => {

            });
        },
        handleSelectionChangeChild (selection) {
            this.idsChild = selection.map((item) => item.groupId);
            this.singleChild = selection.length !== 1;
            this.multipleChild = !selection.length;
        },
        /** 查询巡察考核发起记录列表 */
        getList () {
            let that = this;
            this.loading = true;
            listCheckedInfo(this.queryParams).then(response => {
                that.checkedInfoList = response.rows || [];
                that.total = Number(response.total);
                that.loading = false;
            });
        },
        // 取消按钮
        cancel () {
            this.open = false;
            this.isOpenQuery = false;
            this.isOpenUser = false;
            this.reset();
            this.resetAdd();
            this.InfoListChild = [];
            this.checkedInfoListChild = [];
            this.showList = {};
        },
        close () {
            this.isOpenQuery = false;
            this.submitProcessShow = false;
        },
        // 表单重置
        reset () {
            this.form = {
                title: null,
                // revision: null,
                // createdBy: null,
                // createdTime: null,
                // updatedBy: null,
                // updatedTime: null,
                // id: null,
                // type: null,
                // approveStatus: "0",
                // applyStatus: "0"
            };
            this.resetForm("form");
        },
        resetAdd () {
            this.seeParams = {
                year: null,
                batchId: null,
                id: null,
            };
            this.seeParamsCopy = {};
            this.resetForm("seeform");
        },
        /** 搜索按钮操作 */
        handleQuery () {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery () {
            this.resetForm("queryForm");
            this.queryParams.type = '2';
            this.handleQuery();
        },
        resetQueryChild () {
            this.resetForm("seeForm");
            this.handleQueryChild();
        },
        // 多选框选中数据
        handleSelectionChange (selection) {
            this.ids = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增修改按钮操作 */
        handleAdd (row) {
            let that = this;
            // this.disabled = false;
            this.reset();
            this.resetAdd();
            const id = row.id || "";
            this.title = "巡察员发起考核";
            that.open = true;
            if (id) {
                that.loadingAdd = true
                that.loadingChild = true
                getApplyInfo({ applyId: id }).then(res => {
                    let data = res.data;
                    that.seeParams = {
                        year: data.year,
                        id: data.id,
                        batchId: data.batchId
                    }
                    getBatchList({ year: data.year }).then(resc => {
                        that.batchIdChildOptions = resc.data ? resc.data : [];
                        that.loadingAdd = false
                    })
                    that.seeParamsCopy = JSON.parse(JSON.stringify(that.seeParams))
                    that.checkedInfoListChild = data.list
                    that.loadingChild = false
                    that.form.title = data.title
                    // that.InfoListChild

                    let Array = data.list;
                    //拆分类型
                    that.InfoListChild = [];
                    Array.forEach((item) => {
                        item.examTypeList && item.examTypeList.forEach((itm, idx) => {
                            // 巡察组 考核类型 评分人选择 评分截止时间 备注
                            let child = itm;
                            child.examUserIds = child.examUserIds.toString();
                            child.groupId = item.groupId ? item.groupId : '';//巡察组id
                            child.groupName = item.groupName ? item.groupName : '';//巡察组name
                            child.index = idx;
                            that.InfoListChild.push(child)
                        })
                    })

                    let datas = {};
                    for (let i in that.InfoListChild) {
                        datas[that.InfoListChild[i].groupId + "_" + that.InfoListChild[i].examTypeId] = that.InfoListChild[i];
                    }
                    console.log(datas);
                    this.showList = datas;

                    let formSJ = {
                        batchId: that.seeParams.batchId,
                        groupIds: res.data.groupIds,
                        year: that.seeParams.year,
                    }
                    //返回评分人总列表
                    getExamLeaderList(formSJ).then(res => {
                        that.ExamLeadListOptions = res.data
                    })

                })

            }

        },
        handleQueryOption (row) {
            const id = row.id || this.ids
            console.log(row.applyCode)
            this.$router.push({ path: "/talent/checkedAll/examScopeSchedulePre/index", query: { applyCode: row.applyCode } })
        },
        /**详情按钮*/
        handleDetails (row) {
            // this.reset();
            const id = row.id || this.ids
            let that = this;
            this.isOpenQuery = true;
            this.title = "巡察考核发起记录详情";
            this.loadingChild = true;
            getApplyInfo({ applyId: id }).then(response => {
                that.form = response.data;
                that.isOpenQuery = true;
                that.loadingChild = false;
                let Array = response.data.list;
                //拆分类型
                let InfoListChild = [];
                Array.forEach((item) => {
                    item.examTypeList && item.examTypeList.forEach((itm, idx) => {
                        // 巡察组 考核类型 评分人选择 评分截止时间 备注
                        let child = itm;
                        child.groupId = item.groupId ? item.groupId : '';//巡察组id
                        child.groupName = item.groupName ? item.groupName : '';//巡察组name
                        child.index = idx;
                        InfoListChild.push(child)
                    })
                })
                this.InfoListChildDetail = InfoListChild;

                let data = {};
                for (let i in InfoListChild) {
                    data[InfoListChild[i].groupId + "_" + InfoListChild[i].examTypeId] = InfoListChild[i];
                }
                console.log(data);
                this.showListDetail = data;
                if (response.data.approveStatus != '0') {
                    fetchWorkFlowInfo(id, null, (res) => {
                        this.process = res;
                    }, this.ischeck);
                }

            });
        },
        /** 提交按钮 */
        submitForm () {
            let that = this;
            if (this.disabled) {
                this.open = false;
                return
            }
            if (this.InfoListChild.length == 0) {
                this.$message({
                    message: "请至少添加一个巡察组",
                    type: 'warning'
                });
                return
            }

            if (this.form.title == "" || this.form.title == null) {
                this.$message({
                    message: "请输入标题",
                    type: 'warning'
                });
                return
            }
            this.$refs["form"].validate(valid => {
                if (valid) {
                    console.log(that.dataProcessing())
                    let data = that.dataProcessing()
                    // that.$refs.siRef.open("/checkedAll/checkInfo/getApplyInfo",data);
                    that.submitProcessShow = true;
                }
            });
        },
        dataProcessing () {
            let that = this;
            let seeParams = JSON.parse(JSON.stringify(that.seeParamsCopy));
            let InfoListChild = JSON.parse(JSON.stringify(that.InfoListChild));
            let checkedInfoListChild = JSON.parse(JSON.stringify(that.checkedInfoListChild));
            let forms = { ...seeParams };
            forms.groupList = []
            checkedInfoListChild.forEach((item) => {
                InfoListChild.forEach((itm) => {

                    let a = '';
                    if (typeof (itm.examUserIds) == 'string') {
                        a = [];
                        a.push(itm.examUserIds)
                    } else {
                        a = itm.examUserIds;
                    }
                    itm.examUserIds = a;
                    if (item.groupId == itm.groupId) {
                        if (!item.newdai) {
                            item.newdai = [];
                        }
                        // itm.examUserIds = itm.examUserIds.toString();
                        item.newdai.push(itm);
                    }
                })
            })
            checkedInfoListChild.forEach((item) => {
                if (item.newdai) {
                    forms.groupList.push({
                        groupId: item.groupId,
                        groupName: item.groupName,
                        examTypeList: item.newdai
                    })
                }
            })
            let newList = [];
            newList = checkedInfoListChild.filter(item => {
                return item.newdai && item.newdai.length > 0
            })
            newList.forEach(item => {
                item.examTypeList = item.newdai
            })
            let formSubmit = {
                title: that.form.title,
                list: newList,
                year: forms.year,
                batchId: forms.batchId,
                id: forms.id,
            }
            return formSubmit;
        },
        //真-提交
        saveFromData (value) {
            console.log(value)
            if (this.form.title == "" || this.form.title == null) {
                this.$message({
                    message: "请输入标题",
                    type: 'warning'
                });
                return
            }
            if (this.InfoListChild.length == 0) {
                this.$message({
                    message: "请至少添加一个巡察组",
                    type: 'warning'
                });
                return
            }
            const loading = this.openLoading();
            let data = this.dataProcessing();
            let nextarr = JSON.parse(value.variables);
            submitApplyInfoPer({
                examInfo: JSON.stringify(data),
                flowInfo: JSON.stringify({
                    nextUserId: nextarr.nextDealUser,
                    option: nextarr.option,
                    stepName: "巡察办室经理审批"
                })
            }).then(res => {
                this.$message({
                    message: res.msg,
                    type: 'success'
                });
                this.submitProcessShow = false
                this.open = false
                this.getList();
                loading.close();
            })
        },
        //暂存
        saveBtn () {
            let that = this;
            if (this.form.title == "" || this.form.title == null) {
                this.$message({
                    message: "请输入标题",
                    type: 'warning'
                });
                return
            }
            if (this.InfoListChild.length == 0) {
                this.$message({
                    message: "请至少添加一个巡察组",
                    type: 'warning'
                });
                return
            }
            console.log("开始暂存")
            const loading = this.openLoading();
            let data = this.dataProcessing();

            saveApplyInfoPer({ examInfo: JSON.stringify(data) }).then(res => {
                console.log(res)
                that.$message.success(res.msg)
                that.open = false
                that.cancel()
                that.getList();
                loading.close();
            })
        },
        /** 删除按钮操作 */
        handleDelete (row) {
            const ids = row.id || this.ids;
            this.$confirm('是否确认删除?', "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            }).then(function () {
                return delCheckedInfo({ id: ids });
            }).then(() => {
                this.getList();
                this.msgSuccess("删除成功");
            }).catch(function () { });
        },
        /** 禁用或启用操作*/
        disabledBtnClick (type) {
            var ids = this.ids || [];
            this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            }).then(function () {
                return disableEnable({ ids: ids.toString(), disableEnableState: type });
            }).then(() => {
                this.getList();
                this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
            }).catch(function () { });
        },
        /** 导出按钮操作 */
        handleExport () {
            const queryParams = this.queryParams;
            this.$confirm('是否确认导出所有巡察考核发起记录数据项?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            }).then(function () {
                return exportCheckedInfo(queryParams);
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
		//打开巡察组详情弹窗
		onGrounpDetail (row) {
		    this.groupObject = {
		        groupId: row.groupId ? row.groupId : ''
		    }
		    this.isOpenGroup = true
		}
    }
};
</script>



<style lang="scss" scoped>
.app-container {
    background: #F5F7F9;
}

/deep/ .el-dialog__body {
    // max-height: 500px;
    overflow: auto;
}

/deep/.is-fullscreen>.el-dialog__body {
    max-height: initial;
    padding-bottom: 40px;
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

.btnright {
    display: flex;
    justify-content: flex-end;
}

.crad-content-top20 {
    padding: 0 40px 20px;
}
</style>