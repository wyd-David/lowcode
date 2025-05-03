<template>
    <div class="app-container">
        <todo-bpm-header v-if="typeId != 1"></todo-bpm-header>
        <div class="container" v-if="!historyOpen">
            <div class="crad">
                <div class="crad-header" v-show="showSearch">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>考评信息</span>
                    </div>
                    <div class="history-btn" v-if="typeId == 1" @click="onHistory">评分历史</div>
                </div>
                <div class="" v-show="showSearch">
                    <expand-collapse>
                        <div slot="left">
                            <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules"
                                :disabled="disable">
                                <el-form-item label="巡察年份：" prop="seeyear">
                                    <el-date-picker size="small" :clearable="false" style="width: 205px" format="yyyy"
                                        value-format="yyyy" v-model="seeParams.year" type="year" placeholder="请选择巡察年份"
                                        @change="yearchange">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="巡察批次：" prop="seebatchId">
                                    <el-select v-model="seeParams.batchId" placeholder="请选择巡察批次" size="small"
                                        :clearable="false" @change="handleQuery">
                                        <el-option v-for="dict in batchIdOptions" :key="dict.batchId"
                                            :label="dict.batchName" :value="dict.batchId" />
                                    </el-select>
                                </el-form-item>
                            </el-form>
                        </div>
                        <!-- <div slot="right" v-if="typeId == 1">
                            <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
                        </div> -->
                    </expand-collapse>
                </div>
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>评分表</span>
                    </div>
                </div>
            </div>
            <div class="crad-content">
                <el-row :gutter="10" class="mb12 utlsBtn mar20">
                    <el-col :span="4">
                        <el-button type="primary" size="mini" @click="handleExport">
                            <!-- v-hasPermi="['checkedAll:leadingRating:export']" -->
                            导出评分表
                        </el-button>
                    </el-col>
                    <el-col :span="20" style="display: flex;justify-content: flex-end;"
                        v-if="typeId != 1 && process.authorizeFlag == '1'">
                        <el-tag type="danger">已处理</el-tag>
                    </el-col>
                    <!-- <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" /> -->
                </el-row>
                <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
                    <el-table v-loading="loading" :data="checkedInfoList" @selection-change="handleSelectionChange"
                        border :span-method="objectSpanMethod" :row-class-name="rowClassName"
                        @cell-mouse-enter="handleMouseEnter" @cell-mouse-leave="handleMouseLeave">
                        <!--:span-method="objectSpanMethod"  -->
                        <el-table-column label="序号" align="center" prop="order" width="55" />
                        <el-table-column label="业务单号" align="center" prop="applyCode" width="160" />
                        <el-table-column label="巡察组" align="center" prop="groupName" width="160">
                            <template slot-scope="scope">
                                <span class="btmLine" @click="onGrounpDetail(scope.row)">{{
                                scope.row.groupName
                                }}</span>
                                <span>（{{scope.row.ifSubmit | filterAll(statusOption)}}）</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="评分截止时间" align="center" prop="examLastTime" width="160" />
                        <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName" width="100" />
                        <!-- <el-table-column label="姓名" align="center" prop="" /> -->
                        <el-table-column label="姓名" align="center" prop="toExamUserName">
                            <template slot-scope="scope">
                                <span class="btmLine" @click="onUserInfo(scope.row)">{{ scope.row.toExamUserName
                                }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="组内职责" align="center" prop="userTypeName" />
                        <el-table-column align="center" v-for="(item, index) in tableData" :key="index"
                            :width="(Number(item.scopeTitle.length) + item.mixScope.toString().length + 3) * 15">
                            <template slot="header" slot-scope="scope">
                                {{ item.scopeTitle }}（{{ item.mixScope }}分）
                            </template>
                            <template slot-scope="scope">
                                <span v-if="scope.row.ifSubmit != 2">
                                    <el-form-item label="" prop="scope" :disabled="true" class="specialForm" :rules='{
                                        required: true,
                                        trigger: "change",
                                        validator: (rule, value, callback) => {
                                            let reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                                            let a = scope.row.examList[index]["examResult"];
                                            if (a.length == 0 || a == null || a == "") {
                                                callback(Errors("评分不能为空"))
                                            } else if (!reg.test(a)) {
                                                callback(Errors("请输入正确格式的分数"));
                                            } else {
                                                if (Number(item.mixScope) < Number(a) || Number(a) < 0) {
                                                    callback(Errors("请输入范围内的分数"));
                                                } else {
                                                    callback()
                                                }
                                            }
                                        }
                                    }'>
                                        <el-input v-model="scope.row.examList[index]['examResult']" placeholder="请输入"
                                            clearable min="0" :max="item.mixScope"
                                            @blur="inpBlur(scope.row.examList, scope.$index)"
                                            :disabled="scope.row.ifSubmit == '1'"
                                            onkeyup="value=value.replace(/[^0-9.]/g,'')" />
                                    </el-form-item>
                                </span>
                                <span v-else>--</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="合计（分）" align="center" prop="Allpoints" width="100" fixed="right">
                        </el-table-column>

                    </el-table>
                    <div class="pageBox">
                        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                            :limit.sync="queryParams.pageSize" @pagination="getList" />
                    </div>
                </el-form>
            </div>
            <div class="crad-content btips">
                <div>评分参考：</div>
                <div>本表满分100分，考核总分权重占比为70%。</div>
            </div>
        </div>
        <div class="container" v-if="!historyOpen">
            <div slot="footer" class="dialog-footer" v-if="typeId == 1 && ifsubmits > 0">
                <el-button type="primary" @click="storageForm">暂 存</el-button>
                <el-button type="primary" @click="submitForm">提 交</el-button>
            </div>
            <deep-btn @close="cancel" :isTodo="true" @saveDraftData="storageForm" :businessKey="seeParams.id"
                :todoId="todoId" v-if="typeId != 1" @initProcess="initProcess" type="1">
                <span slot="btns">
                    <el-button type="primary" @click="storageForm"
                        v-if="typeId != 1 && ifsubmits > 0 && process.authorizeFlag == '2'">暂 存</el-button>
                    <el-button type="primary" @click="transferForm"
                        v-if="typeId != 1 && ifsubmits > 0 && process.authorizeFlag == '2'">转 办</el-button>
                    <el-button type="primary" @click="submitForm"
                        v-if="typeId != 1 && ifsubmits > 0 && process.authorizeFlag == '2'">提 交</el-button>
                </span>
            </deep-btn>
        </div>
        <transfer-process ref="transferRef" :taskId="process.curtaskId" :dialogVisible="transferProcessShow" type="1" isBusiness @handleData="handleAF" 
            @close="transferProcessShow = false"></transfer-process>
        <Rat-History :dialogVisible="historyOpen" :histypeId="histypeId" @close="historyOpen = false"></Rat-History>
        <patrol-group ref="PatrolGroup" :groupObject="groupObject" :dialogVisible="isOpenGroup"
            @close="isOpenGroup = false"></patrol-group>
        <collectForm :dialogVisible="iFormShow.open" :id="iFormShow.id" more @close="handleCollectFormClose">
        </collectForm>
    </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
    getLeaderListToExam,
    getExamContentByExamType,
    saveLeaderListToExam,
    submitLeaderListToExam,
    getBatchList,
    exportLeaderListToExam,
} from "@/api/checkedAll/checkedInfo";


import { transfer } from "@/api/talent/collection";
import transferProcess from "@/components/WorkFlow/transferProcess";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
import ApplyFooter from "@/components/ApplyFooter";
import deepBtn from "@/components/WorkFlow/deepBtn";

import SubmitProcess from '@/components/WorkFlow/submitProcess';
import RatHistory from './RatHistory.vue';
import { fetchWorkFlowInfo } from "@/utils/workflow";
import PatrolGroup from "@/components/PatrolGroup";

import collectForm from "@/views/talent/collect/collectForm.vue";
export default {
    name: "CheckedInfo",
    components: { ExpandCollapse, deepBtn, ApplyFooter, SubmitProcess, transferProcess, RatHistory, PatrolGroup, collectForm },

    data () {
        return {

            groupObject: {
                taskId: "",
                piciId: "",
            },
            iFormShow: { open: false, id: null },
            isOpenGroup: false,
            histypeId: "1",
            historyOpen: false,
            ifsubmits: 0,
            todoId: null,
            typeId: null,
            disable: false,
            transferProcessShow: false,
            batchIdOptions: [],
            approveStatusOption: [],
            submitProcessShow: false,
            process: { allFlowStep: [], curtaskId: "" },
            disabled: false,
            true: true,
            false: false,
            // 遮罩层
            loading: true,
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
            // 巡察考核发起记录表格数据
            checkedInfoList: [],
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
                pageNum: 1,
                pageSize: 10,
                applyCode: null,
                year: null,
                batchId: null,
                toExamGroups: null,
                approveStatus: null,
            },
            // 表单参数
            form: {

            },
            ExamLeadListOptions: [],
            options: [],
            showList: {},
            tableData: [],
            cellIndex: -1,
            statusOption: [],
            // 表单校验
            rules: {
                title: [{ required: true, message: "标题不能为空", trigger: "change" }],
                text: [{ required: true, message: "不能为空", trigger: "change" }],
            }
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
        let that = this;
        this.typeId = this.$route.path.indexOf('_db') < 0 ? 1 : 2;
        this.seeParams.id = this.$route.query.id ? this.$route.query.id : '';
        this.todoId = this.$route.query.todoInfoId ? this.$route.query.todoInfoId : '';
        this.disable = this.typeId == 1 ? false : true;

        this.statusOption = [{ dictValue: '0', dictLabel: '评分中' }, { dictValue: '1', dictLabel: '已完成' }, { dictValue: '2', dictLabel: '已过期' },]
        this.getDicts("approve_status").then(res => {
            this.approveStatusOption = res.data ? res.data : [];
        })
        getExamContentByExamType({ examTypeId: '1' }).then(res => {
            that.tableData = res.data
        })

        this.yearchange('');
        let date = new Date();
        let year = date.getFullYear();
        this.seeParams.year = this.$route.query.year ? this.$route.query.year : ('' + year);
        this.seeParams.batchId = this.$route.query.batchId ? this.$route.query.batchId : '';
        getBatchList({ year: this.seeParams.year }).then(res => {
            this.batchIdOptions = res.data ? res.data : [];
            (this.typeId == 1) && this.batchIdOptions.length > 0 && (this.seeParams.batchId = this.batchIdOptions[0].batchId)
            this.seeParamsCopy = JSON.parse(JSON.stringify(this.seeParams))
            this.getList();
        })

    },
    methods: {

        handleAF (handleData) {
            // dealUser
            let fm = new FormData();
            fm.append("todoId", this.todoId);
            fm.append("dealUser", handleData.nextDealUser);
            fm.append("applyId", this.seeParams.id);
            transfer(fm).then(res => {
                if (res.code == 200) {
                    this.$message.success("转办成功");
                    window.location.reload()
                }
            });
        },
        onUserInfo (row) {
            this.iFormShow.id = row.toExamUserId;
            this.iFormShow.open = true;
            console.log(this.iFormShow);
        },
        handleCollectFormClose () {
            this.iFormShow.open = false
        },
        onGrounpDetail (row) {
            this.groupObject = {
                groupId: row.groupId ? row.groupId : ''
            }
            this.isOpenGroup = true
        },
        initProcess (res) {
            let tempObj = {};
            if (res.allFlowStep.length && res.backNextStepAndUser.length == 1) {
                for (let i = 1; i < res.allFlowStep.length; i++) {
                    let item = res.allFlowStep[i];
                    if (item.varialbes && item.varialbes.message == "转办") {
                    } else {
                        tempObj = item;
                        break;
                    }
                }
                res.preUserName = tempObj.todoUserLoginId;
                res.preUserId = tempObj.historicTaskInstance.assignee;
            }
            this.process = res;
        },
        // 里面包含当前行row、当前列column、当前行号rowIndex、当前列号columnIndex四个属性。
        // 该函数可以返回一个包含两个元素的数组，第一个元素代表rowspan，第二个元素代表colspan。 
        // 也可以返回一个键名为rowspan和colspan的对象。
        objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
            if (columnIndex < 5) {
                // let len = this.checkedInfoList.filter((item) => {
                //     if((item.applyCode == row.applyCode)&&(item.groupId==row.groupId)){
                //         return item
                //     }
                // }).length;
                // if (row.index % len === 0) {
                //     return {
                //         rowspan: len,
                //         colspan: 1
                //     };
                // } else {
                //     return {
                //         rowspan: 0,
                //         colspan: 0
                //     };
                // }
                let data = this.checkedInfoList.filter((item) => {
                    if ((item.applyCode == row.applyCode) && (item.groupId == row.groupId)) {
                        return item
                    }
                });
                let idx = 0;
                data.forEach((item, index) => { if ((item.toExamUserId == row.toExamUserId)) { idx = index } })
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
        yearchange (val) {
            console.log(val)
            this.seeParams.batchId = null;
            //批次
            getBatchList({ year: val }).then(res => {
                this.batchIdOptions = res.data ? res.data : [];
            })
        },
        Errors (err) {
            let text = new Error(err);
            return text
        },
        //评分人选择
        PeoChange (e) {
            for (let i in this.showList) {
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
        close () {
            this.submitProcessShow = false;
        },

        //真-提交
        saveFromData (value) {
            console.log(value)
        },
        /** 查询巡察考核发起记录列表 */
        getList () {
            let that = this;
            this.loading = true;
            let formsData = JSON.parse(JSON.stringify(this.seeParams))
            if (this.todoId) {
                formsData.todoId = this.todoId
            }
            getLeaderListToExam(formsData).then(res => {
                let data = res.data
                let tableData = that.tableData;
                that.ifsubmits = data.filter(item => item.ifSubmit == '0').length
                that.checkedInfoList = data.map((item, index, da) => {
                    item.Allpoints = item.Allpoints ? item.Allpoints : '0';
                    item.index = da.filter(itm => { if (item.groupId == itm.groupId) return item }).findIndex((idd) => idd.toExamUserId === item.toExamUserId)
                    return item
                }, data);

                this.handleData(this.checkedInfoList);
                console.log(that.checkedInfoList, "arr=====")
                this.loading = false;
            })
        },
        inpBlur (val, indx) {
            console.log(val, indx)
            let Allpoints = 0;
            Allpoints = this.checkedInfoList[indx].examList.reduce((r, n) => {
                console.log(Number(n.examResult.replace(/[^0-9.]/ig, "") != "" ? Number(n.examResult.replace(/[^0-9.]/ig, "")) : 0))
                return r + (Number(n.examResult.replace(/[^0-9.]/ig, "") != "" ? Number(n.examResult.replace(/[^0-9.]/ig, "")) : 0))
            }, 0)
            this.checkedInfoList[indx].Allpoints = Allpoints.toFixed(2);
        },
        // 取消按钮
        cancel () {
            this.open = false;
            this.reset();
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
            // this.seeParams = {
            //     year:null
            // };
            this.resetForm("seeform");
        },
        /** 搜索按钮操作 */
        handleQuery () {
            this.queryParams.pageNum = 1;
            this.seeParamsCopy = JSON.parse(JSON.stringify(this.seeParams))
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery () {
            this.resetForm("queryForm");
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
            this.resetAdd();
            this.open = true;
            this.title = "巡察组发起考核";
        },
        /** 修改按钮操作 */
        handleUpdate (row) {
            this.disabled = false;
            this.reset();
            const id = row.id || this.ids
            getCheckedInfo(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = "修改巡察考核发起记录";
            });
        },
        handleQueryOption (row) {
            const id = row.id || this.ids
        },
        /**详情按钮*/
        handleDetails (row) {
            // this.reset();
            const id = row.id || this.ids
            this.isOpenQuery = true;
            this.title = "查看巡察考核发起记录";
            getCheckedInfo(id).then(response => {
                this.form = response.data;
                this.isOpenQuery = true;
                this.title = "查看巡察考核发起记录";
            });
        },
        //暂存按钮
        storageForm () {
            console.log("开始暂存")
            let data = {
                examDetail: JSON.stringify(this.checkedInfoList),
                batchId: this.seeParamsCopy.batchId ? this.seeParamsCopy.batchId : '',
                todoId: this.todoId ? this.todoId : '',
                year: this.seeParamsCopy.year ? this.seeParamsCopy.year : ''
            }
            const loading = this.openLoading();
            saveLeaderListToExam(data).then(res => {
                this.$message({
                    message: res.msg,
                    type: 'success'
                });
                this.getList()
                loading.close()
            })
        },
        //转办
        transferForm () {
            console.log("转办")
            console.log("转办nextStep")
            this.transferProcessShow = true;
            this.$refs["transferRef"].init(this.process);
        },
        /** 提交按钮 */
        submitForm () {
            let that = this;
            if (this.disabled) {
                this.open = false;
                return
            }
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.$confirm('是否确定提交？', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(() => {
                        const loading = this.openLoading();
                        let data = {
                            examDetail: JSON.stringify(this.checkedInfoList),
                            batchId: that.seeParamsCopy.batchId ? that.seeParamsCopy.batchId : '',
                            todoId: that.todoId ? that.todoId : '',
                            year: that.seeParamsCopy.year ? that.seeParamsCopy.year : ''
                        }
                        submitLeaderListToExam(data).then(res => {
                            that.$message.success(res.msg)
                            that.getList()
                            loading.close()
                            that.typeId != "1" && window.location.reload();
                        })
                    }).catch(() => {

                    });

                }
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
        /** 禁用或启用操作*/
        disabledBtnClick (type) {
            var ids = this.ids || [];
            this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function () {
                return disableEnable({ ids: ids.toString(), disableEnableState: type });
            }).then(() => {
                this.getList();
                this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
            }).catch(function () { });
        },
        /** 导出按钮操作 */
        handleExport () {
            const queryParams = this.seeParamsCopy;
            this.$confirm('是否确认导出当前批次数据项?', "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            }).then(function () {
                return exportLeaderListToExam(queryParams);
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
        //历史记录
        onHistory () {
            this.historyOpen = true;
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
            this.checkedInfoList = data;

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

/deep/ .operations .dialog-title {
    display: none !important;
}

/deep/ .operations .context-row {
    display: none !important;
}
</style>