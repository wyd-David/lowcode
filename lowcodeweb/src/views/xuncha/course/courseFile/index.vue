<template>
    <div class="app-main">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <!-- 事实确认单 -->
            <el-tab-pane label="事实确认单" name="voucher">
                <expand-collapse v-if="activeName == 'voucher'">
                    <div slot="left">
                        <el-form :model="queryParams01" ref="queryForm01" :inline="true" v-show="showSearch01"
                            label-width="180px">
                            <el-form-item label="预编号：" prop="spendCode">
                                <el-input v-model="queryParams01.spendCode" style="width: 210px" placeholder="请输入预编号"
                                    clearable @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="事实确认单编号：" prop="voucherCode">
                                <el-input v-model="queryParams01.voucherCode" style="width: 210px"
                                    placeholder="请输入事实确认单编号" clearable @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="问题底稿编号：" prop="matterCode">
                                <el-input v-model="queryParams01.matterCode" style="width: 210px"
                                    placeholder="请输入问题底稿编号" clearable @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="巡察年份：" prop="xunchaYear">
                                <el-date-picker clearable style="width: 210px"
                                    v-model="queryParams01.xunchaYear" type="year" value-format="yyyy"
                                    placeholder="选择巡察年份" @change="xunchaYearChange01">
                                </el-date-picker>
                            </el-form-item>
                            <el-form-item label="巡察批次：" prop="piciId">
                                <el-select v-model="queryParams01.piciId" style="width: 210px" placeholder="请选择巡察批次"
                                    @change="handlePiciChange01" :disabled="queryParams01.piciIdshow01">
                                    <el-option v-for="item in piciIdDataList01" :key="item.piciId"
                                        :label="item.piciName" :value="item.piciId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="巡察组：" prop="groupName">
                                <el-select v-model="queryParams01.groupName" style="width: 210px" placeholder="请选择巡察组"
                                    @change="handlexunchazuChange01" :disabled="queryParams01.groupNameshow01">
                                    <el-option v-for="item in groupNameDataList01" :key="item.taskId"
                                        :label="item.groupName" :value="item.taskId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="当前状态：" prop="status">
                                <el-select v-model="queryParams01.status" style="width: 210px" placeholder="请选择当前状态">
                                    <el-option v-for="item in statusNameDataList" :key="item.value" :label="item.label"
                                        :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="承办人：" prop="agentListName">
                                <el-input v-model="queryParams01.agentListName" style="width: 210px"
                                    placeholder="请输入承办人" clearable size="small" @keyup.enter.native="handleQuery" />
                            </el-form-item>
                        </el-form>
                    </div>
                    <div slot="right">
                        <el-button size="mini" @click="resetQuery">重置</el-button>
                        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
                    </div>
                </expand-collapse>
                <div class="container" v-if="activeName == 'voucher'">
                    <div style="float:right;margin:10px 0">
                        <el-row :gutter="10" class="mb12 utlsBtn">
                            <el-col :span="1.5">
                                <el-button type="default" icon="el-icon-download" size="mini"
                                    :disabled="ids01.length === 0" @click="handleExport01">导出
                                </el-button>
                            </el-col>
                            <el-col :span="1.5">
                                <el-button type="default" icon="el-icon-refresh-right" size="mini"
                                    @click="readWorldFile01" :disabled="deleteShow01">
                                    退回巡察组
                                </el-button>
                            </el-col>
                            <el-col :span="1.5">
                                <el-button type="primary" icon="el-icon-finished" size="mini" @click="finishFlow01"
                                    :disabled="deleteShow01">批量入库
                                </el-button>
                            </el-col>
                        </el-row>
                    </div>

                    <el-table v-loading="loading" :data="courseVoucherList" @selection-change="handleSelectionChange01">
                        <el-table-column type="selection" width="55" align="left" />
                        <el-table-column label="预编号" min-width="180px" align="left" prop="spendCode" />
                        <el-table-column label="事实确认单编号" min-width="160px" align="left" prop="voucherCode" />
                        <el-table-column label="问题底稿编号" min-width="140px" align="left" prop="matterCode" />
                        <el-table-column label="巡察年份" align="center" prop="xunchaYear" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.xunchaYear, '{y}') }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="巡察批次" align="center" prop="piciName" />
                        <el-table-column label="巡察组" align="center" prop="groupName" />
                        <el-table-column label="被巡察单位" min-width="120px" align="center" prop="orgName" />
                        <el-table-column label="承办人" align="center" prop="agentListName" />
                        <el-table-column label="当前状态" align="center" prop="status">
                            <template slot-scope="scope">
                                <span v-if="scope.row.status === '3'">待入库</span>
                                <span v-else-if="scope.row.status === '6'">已入库</span>
                                <span v-else>退回巡察组</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" fixed="right" width="240" align="center" class-name="small-padding fixed-width">
                            <template slot-scope="scope">
                                <el-button size="mini" type="text" @click="handleReturnPatrol01(scope.row)"
                                    v-if="scope.row.status === '3'">退回巡察组
                                </el-button>
                                <el-button size="mini" type="text" @click="handleDataWarehousing01(scope.row)"
                                    v-if="scope.row.status === '3'">资料入库
                                </el-button>
                                <el-button size="mini" type="text" @click="handleQueryOption01(scope.row)">详情查看
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="pageBox">
                        <pagination v-show="total01>0" :total="total01" :page.sync="queryParams01.pageNum"
                            :limit.sync="queryParams01.pageSize" @pagination="getList" />
                    </div>
                </div>
            </el-tab-pane>
            <!-- 问题底稿 -->
            <el-tab-pane label="问题底稿" name="papers">
                <expand-collapse v-if="activeName == 'papers'">
                    <div slot="left">
                        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch"
                            label-width="180px">
                            <el-form-item label="预编号：" prop="spendCode">
                                <el-input v-model="queryParams.spendCode" style="width: 210px" placeholder="请输入预编号"
                                    clearable @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="事实确认单编号：" prop="voucherCode">
                                <el-input v-model="queryParams.voucherCode" style="width: 210px"
                                    placeholder="请输入事实确认单编号" clearable @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="问题底稿编号：" prop="matterCode">
                                <el-input v-model="queryParams.matterCode" style="width: 210px" placeholder="请输入问题底稿编号"
                                    clearable @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="巡察年份：" prop="xunchaYear">
                                <el-date-picker clearable style="width: 210px"
                                    v-model="queryParams.xunchaYear" type="year" value-format="yyyy-MM-dd"
                                    placeholder="选择巡察年份" @change="xunchaYearChange">
                                </el-date-picker>
                            </el-form-item>
                            <el-form-item label="巡察批次：" prop="piciId">
                                <el-select v-model="queryParams.piciId" style="width: 210px" placeholder="请选择巡察批次"
                                    @change="handlePiciChange" :disabled="queryParams.piciIdshow">
                                    <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                                        :value="item.piciId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="巡察组：" prop="groupName">
                                <el-select v-model="queryParams.groupName" style="width: 210px" placeholder="请选择巡察组"
                                    @change="handlexunchazuChange" :disabled="queryParams.groupNameshow">
                                    <el-option v-for="item in groupNameDataList" :key="item.taskId"
                                        :label="item.groupName" :value="item.taskId">
                                    </el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="问题领域：" prop="territoryCode">
                                <el-select v-model="queryParams.territoryCode" style="width: 210px"
                                    placeholder="请选择问题领域">
                                    <el-option v-for="item in fieldDataList"
                                        :key="item.dataCode"
                                        :label="item.dataName"
                                        :value="item.dataCode">
                                </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="当前状态：" prop="status">
                                <el-select v-model="queryParams.status" style="width: 210px" placeholder="请选择当前状态">
                                    <el-option v-for="item in statusNameDataList" :key="item.value" :label="item.label"
                                        :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div slot="right">
                        <el-button size="mini" @click="resetQuery">重置</el-button>
                        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
                    </div>
                </expand-collapse>
                <div class="container" v-if="activeName == 'papers'">
                    <div style="float:right;margin:10px 0">
                        <el-row :gutter="10" class="mb12 utlsBtn">
                            <el-col :span="1.5">
                                <el-button type="default" icon="el-icon-download" size="mini"
                                    :disabled="ids02.length === 0" @click="handleExport">导出
                                </el-button>
                            </el-col>
                            <el-col :span="1.5">
                                <el-button type="default" icon="el-icon-refresh-right" size="mini"
                                    @click="readWorldFile" :disabled="deleteShow">退回巡察组
                                </el-button>
                            </el-col>
                            <el-col :span="1.5">
                                <el-button type="primary" icon="el-icon-finished" size="mini" @click="finishFlow"
                                    :disabled="deleteShow">批量入库
                                </el-button>
                            </el-col>
                        </el-row>
                    </div>
                    <el-table v-loading="loading" :data="papersList" @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="55" align="left" />
                        <el-table-column label="预编号" min-width="180px" align="left" prop="spendCode" />
                        <el-table-column label="问题底稿编号" min-width="140px" align="left" prop="matterCode" />
                        <el-table-column label="事实确认单编号" min-width="160px" align="left" prop="voucherCode" />
                        <el-table-column label="巡察年份" align="center" prop="xunchaYear" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.xunchaYear, '{y}') }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="巡察批次" align="center" prop="piciName" />
                        <el-table-column label="巡察组" align="center" prop="groupName" />
                        <el-table-column label="被巡察单位" min-width="120px" align="center" prop="orgName" />
                        <el-table-column label="问题领域" align="left" prop="domains" />
                        <el-table-column label="当前状态" align="left" prop="status">
                            <template slot-scope="scope">
                                <span v-if="scope.row.status === '3'">待入库</span>
                                <span v-else-if="scope.row.status === '6'">已入库</span>
                                <span v-else>退回巡察组</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width" width="240">
                            <template slot-scope="scope">
                                <el-button size="mini" type="text" @click="handleReturnPatrol(scope.row)"
                                    v-if="scope.row.status === '3' ">退回巡察组
                                </el-button>
                                <el-button size="mini" type="text" @click="handleDataWarehousing(scope.row)"
                                    v-if="scope.row.status === '3'">资料入库
                                </el-button>
                                <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">详情查看
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="pageBox">
                        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                            :limit.sync="queryParams.pageSize" @pagination="getList" />
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 查看事实确认单对话框 -->
        <el-dialog :title="title01" :visible.sync="isOpenQuery01" width="900px" append-to-body="append-to-body">
            <el-form ref="form" :model="form01" :rules="rules01" label-width="200px">
                <div class="dialog-title" style="margin-bottom:10px">
                    <div style="width:50%;float:left">
                        <span class="line"></span><span>基础信息</span>
                    </div>
                </div>
                <el-form-item label="预编号：" prop="spendCode">
                    <el-input v-model="form01.spendCode" placeholder="点击暂存或提交后自动生成" :disabled="true" />
                </el-form-item>
                <el-form-item label="事实确认单编号：" prop="voucherCode">
                    <el-input v-model="form01.voucherCode" placeholder="组长审批通过后自动生成" :disabled="true" />
                </el-form-item>
                <el-form-item label="问题底稿编号：" prop="matterCode">
                    <el-input v-model="form01.matterCode" placeholder="组长审批通过后自动生成" :disabled="true" />
                </el-form-item>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="巡察年份：" prop="xunchaYear">
                            <el-date-picker clearable size="small" style="width: 100%" v-model="form01.xunchaYear"
                                type="year" value-format="yyyy" placeholder="选择巡察年份" :disabled="true">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="巡察批次：" prop="piciId">
                            <el-select v-model="form01.piciId" style="width: 100%;" placeholder="请选择巡察批次"
                                :disabled="true">
                                <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                                    :value="item.piciId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="20">
                        <el-form-item label="巡察组：" prop="groupName">
                            <el-select v-model="form01.groupName" style="width: 100%" placeholder="请选择巡察组"
                                :disabled="true">
                                <el-option v-for="item in groupNameDataList" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" style="text-align:right;margin-top:0.25rem;">
                        <el-button type="primary" size="mini" @click="handleGenerateDetails(form01)">巡察组详情
                        </el-button>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="任务时间：" prop="taskDateFrame">
                            <el-date-picker style="width: 100%" v-model="form01.taskDateFrame" type="daterange"
                                range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :disabled="true">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="被巡察单位：" prop="orgName">
                            <el-select v-model="form01.orgName" style="width: 100%;" placeholder="请选择被巡察单位"
                                :disabled="true">
                                <el-option v-for="item in orgIdDataList" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <div class="dialog-title" style="margin-bottom:10px">
                    <span class="line"></span><span>问题信息</span>
                </div>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="事实领域：" prop="territoryIdList">
                            <el-select v-model="form01.territoryIdList" :multiple="true" style="width: 100%"
                                placeholder="请选择事实领域" :disabled="true">
                                <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                                    :value="item.dataCode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="事实概述：" prop="truthOverview">
                    <el-input type="textarea" :rows="3" placeholder="请输入事实概述" v-model="form01.truthOverview"
                        maxlength="20000" :show-word-limit="true" :disabled="true" />
                </el-form-item>
                <el-form-item label="具体事实：" prop="specificTruth">
                    <el-input type="textarea" :rows="3" placeholder="请输入具体事实" v-model="form01.specificTruth"
                        maxlength="20000" :show-word-limit="true" :disabled="true" />
                </el-form-item>
                <el-form-item label="承办人：" prop="agentIdList">
                    <el-select v-model="form01.agentIdList" :multiple="true" style="width: 100%" placeholder="请选择承办人"
                        :disabled="true">
                        <el-option v-for="item in createdByDataList"
                            :key="item.id"
                            :label="item.userName+'（'+item.userId+' - '+item.labelValue+'）'"
                            :value="item.id"
                            :disabled="item.disabled">
                        </el-option>
                    </el-select>
                </el-form-item>
                <div class="dialog-title" style="margin-bottom:10px">
                    <span class="line"></span><span>附件材料</span>
                </div>
                <el-form-item label="事实确认单附件：" prop="matterCode">
                    <el-button type="text" :title="form01.fileName" class="accessory"
                        style="font-size: 12px; color: #1590ff">
                        {{ form01.fileName }}
                    </el-button>
                    <el-button type="primary" size="mini" @click="handlePreviewFile">预览</el-button>
                    <el-button type="primary" size="mini" @click="onDownload()" v-if="form01.fileId" :disabled="false">
                        下载
                    </el-button>
                </el-form-item>
            </el-form>
            <!-- 流程历史信息 -->
            <all-flow-step ref="allFlowStepRef" :allFlowStep="process01.allFlowStep" v-if="form01.id" />
            <div slot="footer" class="dialog-footer">
                <el-button size="mini" type="default" @click="cancelForm01">关闭</el-button>
                <el-button size="mini" type="primary" @click="handleDataWarehousing01(form01)"
                    v-if="form01.status === '3'">
                    资料入库
                </el-button>
                <el-button size="mini" type="primary" @click="handleReturnPatrol01(form01)"
                    v-if="form01.status === '3' ">退回巡察组
                </el-button>
            </div>
        </el-dialog>

        <!-- 查看巡察过程_问题底稿对话框 -->
        <el-dialog :title="title" :visible.sync="isOpenQuery" width="900px" append-to-body="append-to-body">
            <el-form ref="queryform" :model="form" :rules="rules" label-width="180px">
                <div class="dialog-title" style="margin-bottom:10px">
                    <div style="width:50%;float:left">
                        <span class="line"></span><span>基础信息</span>
                    </div>
                </div>
                <el-form-item label="预编号：" prop="spendCode">
                    <el-input v-model="form.spendCode" placeholder="请输入预编号" :disabled="true" />
                </el-form-item>
                <el-form-item label="事实确认单编号：" prop="voucherCode">
                    <el-input v-model="form.voucherCode" placeholder="请输入事实确认单编号" :disabled="true" />
                </el-form-item>
                <el-form-item label="问题底稿编号：" prop="matterCode">
                    <el-input v-model="form.matterCode" placeholder="请输入问题底稿编号" :disabled="true" />
                </el-form-item>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="巡察年份" prop="xunchaYear">
                            <el-date-picker clearable size="small" style="width: 200px" v-model="form.xunchaYear"
                                type="year" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择巡察年份" :disabled="true">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="巡察批次:" prop="piciId" style="text-align:right">
                            <el-select v-model="form.piciId" style="width: 210px;" placeholder="请选择巡察批次"
                                :disabled="true">
                                <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                                    :value="item.piciId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="19">
                        <el-form-item label="巡察组：" prop="groupName">
                            <el-select v-model="form.groupName" style="width: 100%" placeholder="请选择巡察组"
                                :disabled="true">
                                <el-option v-for="item in groupNameDataList" :key="item.taskId" :label="item.groupName"
                                    :value="item.taskId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5" style="text-align:right;margin-top:0.25rem;">
                        <el-button type="primary" size="mini" @click="handleGenerateDetails(form)">巡察组详情</el-button>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="巡察时间：" prop="checkTime">
                            <el-date-picker :disabled="true" style="width: 100%" v-model="form.checkTime"
                                type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="被巡察单位：" prop="orgName">
                            <el-select v-model="form.orgName" style="width: 100%;" placeholder="请选择被巡察单位"
                                :disabled="true">
                                <el-option v-for="item in orgIdDataList" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row style="text-align:left">
                    <el-col :span="12">
                        <el-form-item label="分管领导：" prop="head">
                            <el-input v-model="form.head" style="width: 100%;" placeholder="请输入分管领导" :disabled="true" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <div class="dialog-title" style="margin-bottom:10px">
                    <span class="line"></span><span>问题信息</span>
                </div>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="问题领域：" prop="territoryCode">
                            <el-select v-model="form.territoryCode" :multiple="true" style="width: 100%;"
                                placeholder="请选择问题领域" :disabled="true">
                                <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                                    :value="item.dataCode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="问题来源：" prop="problemSourceCode">
                            <el-select v-model="form.problemSourceCode" :multiple="true" style="width: 100%;"
                                placeholder="请选择问题来源" :disabled="true">
                                <el-option v-for="item in problemSourceDateList" :key="item.dataCode"
                                    :label="item.dataName" :value="item.dataCode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="其他：" prop="note" v-if="noteShow">
                    <el-input v-model="form.note" type="text" maxlength="200" :show-word-limit="true" placeholder="请输入其他"
                        :disabled="true" />
                </el-form-item>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="问题发生单位层级：" prop="hierarchy">
                            <el-select v-model="form.hierarchy" style="width: 100%;" placeholder="请选择问题发生单位层级"
                                :disabled="true">
                                <el-option v-for="item in hierarchyDateList" :key="item.dataCode" :label="item.dataName"
                                    :value="item.dataCode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类别：" prop="category">
                            <el-input v-model="form.category" style="width: 100%;" placeholder="请输入类别"
                                :disabled="true" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="重点关注问题清单：" prop="priorityListing">
                    <el-input v-model="form.priorityListing" placeholder="请输入重点关注问题清单" :disabled="true" />
                </el-form-item>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="班子问题还是部门问题：" prop="isDepartmentOfProblem">
                            <el-select v-model="form.isDepartmentOfProblem" style="width: 100%;"
                                placeholder="请选择班子问题还是部门问题" :disabled="true">
                                <el-option v-for="item in problemDateList" :key="item.dataCode" :label="item.dataName"
                                    :value="item.dataCode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="被巡察单位类型：" prop="unitUnderInspectionType">
                            <el-select v-model="form.unitUnderInspectionType" style="width: 100%;"
                                placeholder="请选择被巡察单位类型" :disabled="true">
                                <el-option v-for="item in unitUnderDateList" :key="item.dataCode" :label="item.dataName"
                                    :value="item.dataCode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="问题分类：" prop="questionClassification">
                    <el-select v-model="form.questionClassification" style="width: 100%" placeholder="请选择问题分类"
                        :disabled="true">
                        <el-option v-for="item in questionDateList" :key="item.dataCode" :label="item.dataName"
                            :value="item.dataCode">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="问题概述：" prop="issueAnOverview">
                    <el-input type="textarea" :rows="3" placeholder="请输入问题概述" v-model="form.issueAnOverview"
                        maxlength="20000" :show-word-limit="true" :disabled="true" />
                </el-form-item>
                <el-form-item label="查证事实描述：" prop="factDescription">
                    <el-input type="textarea" :rows="3" placeholder="请输入查证事实描述" v-model="form.factDescription"
                        maxlength="20000" :show-word-limit="true" :disabled="true" />
                </el-form-item>
                <el-form-item label="问题剖析：" prop="problemAnalysis">
                    <el-input type="textarea" :rows="3" placeholder="请输入问题剖析" v-model="form.problemAnalysis"
                        maxlength="20000" :show-word-limit="true" :disabled="true" />
                </el-form-item>
                <el-form-item label="支撑材料：" prop="materialSupport">
                    <el-input type="textarea" :rows="3" placeholder="请输入支撑材料" v-model="form.materialSupport"
                        maxlength="20000" :show-word-limit="true" :disabled="true" />
                    <span>
                        <!-- <el-button type="primary"  size="mini" @click="uploadFileBtn01()">
                                <span v-if="!form.coursePapersFilesList">上传文件</span>
                                <span v-else>重新上传</span>
                            </el-button> -->
                        <div v-if="form.coursePapersFilesList" v-for="item in form.coursePapersFilesList"
                            :key="item.fileId">
                            <el-button type="text" :title="item.fileName" class="accessory"
                                style="font-size: 12px; color: #1590ff">
                                {{ item.fileName }}
                            </el-button>
                            <!-- <el-button type="primary" size="mini" @click="onPreview01(item.fileId)" v-if="item.fileId"
                                :disabled="false">预览
                            </el-button> -->
                            <el-button type="primary" size="mini" @click="onDownload01(item.fileId)" v-if="item.fileId"
                                :disabled="false">下载
                            </el-button>
                        </div>
                    </span>
                </el-form-item>
                <el-form-item label="承办人：" prop="userId">
                    <el-select v-model="form.userId" :multiple="true" style="width: 100%" class="angetSelect"
                        placeholder="请选择承办人" :disabled="true">
                        <el-option v-for="item in createdByDataList"
                            :key="item.id"
                            :label="item.userName+'（'+item.userId+' - '+item.labelValue+'）'"
                            :value="item.id"
                            :disabled="item.disabled">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="分管副组长：" prop="deputyLeader">
                    <el-select v-model="form.deputyLeader" style="width: 100%" placeholder="请选择分管副组长" :disabled="true">
                        <el-option v-for="item in createdByDataList"
                            :key="item.id"
                            :label="item.userName+'（'+item.userId+'）'"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="组长：" prop="leader">
                    <el-select v-model="form.leader" style="width: 100%" placeholder="请选择组长" :disabled="true">
                        <el-option v-for="item in createdByDataList"
                            :key="item.id"
                            :label="item.userName+'（'+item.userId+'）'"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <div class="dialog-title" style="margin-bottom:10px">
                    <span class="line"></span><span>附件材料</span>
                </div>
                <el-form-item label="底稿附件：" prop="fileId">
                    <el-button type="text" :title="form.fileName" class="accessory"
                        style="font-size: 12px; color: #1590ff">
                        {{ form.fileName }}
                    </el-button>
                    <el-button type="primary" size="mini" @click="handlePreviewFile">预览</el-button>
                    <el-button type="primary" size="mini" @click="onDownload()" v-if="form.fileId" :disabled="false">下载
                    </el-button>
                </el-form-item>
            </el-form>

            <!-- 流程历史信息 -->
            <all-flow-step ref="allFlowStepRef" :allFlowStep="process.allFlowStep" v-if="form.id" />

            <div slot="footer" class="dialog-footer">
                <el-button size="mini" type="default" @click="cancelForm">关 闭</el-button>
                <el-button size="mini" type="primary" @click="handleReturnPatrol(form)" v-if="form.status === '3' ">
                    退回巡察组
                </el-button>
                <el-button size="mini" type="primary" @click="handleDataWarehousing(form)" v-if="form.status === '3'">
                    资料入库
                </el-button>
            </div>
        </el-dialog>

        <!-- 巡察过程_查看分组 -->
        <el-dialog :title="groupingTitle" :visible.sync="groupingShow" width="900px" :close-on-click-modal="false"
            append-to-body="append-to-body">
            <div class="dialog-context">
                <div class="dialog-title">
                    <span class="line"></span><span>巡察信息</span>
                </div>
                <el-form ref="queryform" :model="formQuery" label-width="130px">
                    <el-row :gutter="20">
                        <el-col :span="11">
                            <el-form-item label="组名：" prop="groupName">
                                <el-input style="width: 100%" v-model="formQuery.groupName" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="11" :offset="1">
                            <el-form-item label="成员数：" prop="teamMembersCount">
                                <el-input style="width: 100%" v-model="formQuery.teamMembersCount" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="11">
                            <el-form-item label="巡察年份：" prop="xunchaYear">
                                <el-input style="width: 100%" v-model="formQuery.xunchaYear" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="11" :offset="1">
                            <el-form-item label="巡察批次：" prop="xunchaPici">
                                <el-input style="width: 100%" v-model="formQuery.xunchaPici" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="11">
                            <el-form-item label="关联巡察任务：" prop="taskName">
                                <el-input style="width: 100%" v-model="formQuery.taskName" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="11" :offset="1">
                            <el-form-item label="计划巡察时段：" prop="xunchaTimeSolt">
                                <el-input style="width: 100%" v-model="formQuery.xunchaTimeSolt" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="23">
                            <el-form-item label="被巡察单位：" prop="xunchaPici">
                                <el-input style="width: 100%" v-model="formQuery.company" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>

                <div class="dialog-title" style="justify-content: space-between">
                    <div><span class="line"></span><span>组成员信息</span></div>
                </div>

                <div class="context-row">
                    <el-table :data="formQuery.xunchaGroupUserVoList">
                        <el-table-column label="序号" type="index"></el-table-column>
                        <el-table-column label="组内标签/职责" prop="labelValue"></el-table-column>
                        <el-table-column label="姓名" prop="userName">
                            <template slot-scope="scope">{{scope.row.userName}}
                            </template>
                        </el-table-column>

                        <el-table-column label="所在单位" prop="companyName"></el-table-column>
                        <el-table-column label="部门" prop="dpName"></el-table-column>
                        <el-table-column label="领域" prop="fieldValue"></el-table-column>
                    </el-table>
                </div>
            </div>
        </el-dialog>

        <!-- 生成审批意见 -->
        <el-dialog :title="returnTitle" :visible.sync="returnShow" width="600px" append-to-body="append-to-body">
            <el-form ref="returnform" :model="returnform" label-width="140px">
                <el-form-item label="审批/处理意见:" prop="matterCode" style="margin-bottom: 0 !important">
                    <el-input type="textarea" :rows="3" placeholder="请输入审批意见" v-model="returnform.handle"
                        maxlength="200" :show-word-limit="true" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="returnShow = false">关 闭</el-button>
                <el-button @click="clickreturn" type="primary">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
    listPapers,
    getPapers,
    delPapers,
    addPapers,
    updatePapers,
    exportPapers,
    importTemplate,
    disableEnable,
    getUndertaker as getUndertaker02,
    exportWord as exportWord02
} from "@/api/course/coursePapers";
import {
    listCourseVoucher,
    getCourseVoucher,
    delCourseVoucher,
    addCourseVoucher,
    updateCourseVoucher,
    exportCourseVoucher,
    getXunchaGroupInfoList,
    getStartFlow,
    getUndertaker,
    sendBackFlow,
    sendFinishFlow,
    exportWord
} from "@/api/course/courseVoucher";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
import { queryPiciList, queryLunciList, queryCompanyList } from "@/api/xuncha/common/base";
import { listXunchaTaskInfo } from "@/api/xuncha/XunchaTaskInfo";
import { selectDictData } from "@/api/xuncha/xunchaDictData";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { downloadFile, previewFile, downloadZipFile, } from "@/utils/uploadOperate";

export default {
    name: "file",
    components: { ExpandCollapse, AllFlowStep },
    data() {
        return {
            noteShow: false,
            // 是否显示查看分组页面
            groupingShow: false,
            // 查看分组页面标题
            groupingTitle: "",
            formQuery: {},
            // 事实确认单_流程信息
            process01: {},
            // 是否显示查看弹出层
            isOpenQuery01: false,
            form01: {},
            title01: '',
            deleteShow01: true,
            orgIdDataList: [],
            fieldDataList: [],
            deleteShow: true,
            //承办人
            createdByDataList: [],
            // 问题来源
            problemSourceDateList: [],
            // 问题发生单位层级
            hierarchyDateList: [],
            // 班子问题
            problemDateList: [],
            // 被巡察单位类型
            unitUnderDateList: [],
            // 问题分类
            questionDateList: [],

            // 表单校验
            rules01: {
                xunchaYear: [
                    { required: true, message: '请选择巡察年份', trigger: 'change' }
                ],
                piciId: [
                    { required: true, message: '请选择巡察批次！', trigger: 'change' }
                ],
                groupName: [
                    { required: true, message: '请选择巡察组！', trigger: 'change' }
                ],
                taskDateFrame: [
                    { required: true, message: '请选择任务时段！', trigger: 'change' }
                ],
                orgName: [
                    { required: true, message: '请选择被巡察单位！', trigger: 'change' }
                ],
                territoryIdList: [
                    { required: true, message: '请选择事实领域！', trigger: 'change' }
                ],
                truthOverview: [
                    { required: true, message: '请填写事实概述！', trigger: 'blur' }
                ],
                specificTruth: [
                    { required: true, message: '请填写具体事实！', trigger: 'blur' }
                ],
                agentIdList: [
                    { required: true, message: '请填写承办人！', trigger: 'change' }
                ]
            },

            // 问题底稿——流程信息
            process: {},
            // 是否显示查看弹出层
            isOpenQuery: false,
            form: {},
            title: '',

            // 表单校验
            rules: {
                isOpen: [
                    { required: true, message: "是否对巡察办可见不能为空", trigger: "blur" }
                ],
                xunchaYear: [
                    { required: true, message: '请选择巡察年份', trigger: 'change' }
                ],
                piciId: [
                    { required: true, message: '请选择巡察批次！', trigger: 'change' }
                ],
                groupName: [
                    { required: true, message: '请选择巡察组！', trigger: 'change' }
                ],
                checkTime: [
                    { required: true, message: '请选择巡察时间', trigger: 'change' }
                ],
                orgName: [
                    { required: true, message: '请选择被巡察单位！', trigger: 'change' }
                ],
                head: [
                    { required: true, message: '选输入分管领导', trigger: 'blur' }
                ],
                territoryCode: [
                    { required: true, message: '请选择问题领域！', trigger: 'change' }
                ],
                problemSourceCode: [
                    { required: true, message: '请选择问题来源！', trigger: 'change' }
                ],
                note: [
                    { required: true, message: '请输入其他', trigger: 'blur' }
                ],
                hierarchy: [
                    { required: true, message: '请选择问题发生单位层级！', trigger: 'change' }
                ],
                category: [
                    { required: true, message: '请输入类别！', trigger: 'blur' }
                ],
                priorityListing: [
                    { required: true, message: '请输入重点关注问题清单!', trigger: 'change' }
                ],
                isDepartmentOfProblem: [
                    { required: true, message: '请选择班子问题还是部门问题！', trigger: 'change' }
                ],
                unitUnderInspectionType: [
                    { required: true, message: '请选择被巡察单位类型！', trigger: 'change' }
                ],
                questionClassification: [
                    { required: true, message: '请选择问题分类！', trigger: 'change' }
                ],
                issueAnOverview: [
                    { required: true, message: '请输入问题概述！', trigger: 'blur' }
                ],
                factDescription: [
                    { required: true, message: '请输入查证事实描述！', trigger: 'blur' }
                ],
                problemAnalysis: [
                    { required: true, message: '请输入问题剖析！', trigger: 'blur' }
                ],
                materialSupport: [
                    { required: true, message: '请输入支撑材料！', trigger: 'blur' }
                ],
                userId: [
                    { required: true, message: '请选择承办人！', trigger: 'change' }
                ],
                deputyLeader: [
                    { required: true, message: '请选择分管副组长！', trigger: 'change' }
                ],
                leader: [
                    { required: true, message: '请选择组长！', trigger: 'change' }
                ],
                fileId: [
                    { required: true, message: '请选择底稿附件！', trigger: 'blur,change' }
                ],
            },

            // 退回巡察组
            returnShow: false,
            returnTitle: '',
            returnform: {},

            activeName: 'voucher',
            // 显示搜索条件
            showSearch01: true,
            showSearch: true,
            loading: true,
            total01: 0,
            // 事实确认单_查询参数
            queryParams01: {
                pageNum: 1,
                pageSize: 10,
                spendCode: null,
                voucherCode: null,
                matterCode: null,
                xunchaYear: null,
                piciId: null,
                taskId: null,
                groupName: null,
                statusName: null,
                createdBy: null,
                piciIdshow01: true,
                groupNameshow01: true,
                leadShow: this.$store.getters.name,
            },
            // 巡查批次
            piciIdDataList01: [],
            //巡查组
            groupNameDataList01: [],
            //当前状态
            statusNameDataList: [
                // { value: '1', label: '草稿' },
                // { value: '2', label: '组长审核' },
                { value: '3', label: '待入库' },
                { value: '4', label: '退回组长' },
                { value: '5', label: '退回组员' },
                { value: '6', label: '审核通过' },
            ],
            courseVoucherList: [],
            ids01: [],
            ids02: [],
            // 问题底稿_查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                spendCode: null,
                voucherCode: null,
                matterCode: null,
                xunchaYear: null,
                piciId: null,
                piciName: null,
                taskId: null,
                groupName: null,
                checkTime: null,
                orgId: null,
                orgName: null,
                head: null,
                hierarchyId: null,
                hierarchy: null,
                category: null,
                fileId: null,
                fileName: null,
                status: null,
                statusName: null,
                isOpen: this.$store.getters.name,
                createdBy: null,
                createdTime: null,
                updatedBy: null,
                updatedTime: null,
                territoryCode: null,
                piciIdshow01: true,
                groupNameshow01: true,
                piciIdshow: true,
                groupNameshow: true,
            },
            // 巡查批次
            piciIdDataList: [],
            //巡查组
            groupNameDataList: [],
            //问题领域
            territoryDataList: [
                { value: '01', label: '问题1' },
                { value: '02', label: '问题2' },
            ],
            papersList: [],
            total: 0,
            handleId: [],
            handleCurrent: "1",  //1.事实确认单 2.问题底稿
            handleTab: "1",    //页面切换 1.事实确认单 2.问题底稿
            handleArray: [],
        }
    },
    created() {
        this.getList();
    },
    methods: {
        uploadFileBtn01() {
            this.uploadFormShow01 = true;
        },
        // 预览文件
        onPreview01(val) {
            previewFile(val);
        },
        // 文件下载
        onDownload01(val, caseId) {
            downloadFile(val);
        },
        fileSuccess01(files) {
            console.log("ffiles111", files);
            this.msgSuccess("上传成功");
            let coursePapersFilesList = [];
            if (files != null) {
                files.map((item, index) => {
                    coursePapersFilesList.push({
                        fileId: item.result.data.fileId,
                        fileName: item.result.data.fileName,
                        filePath: item.result.data.fileId,
                    })
                })
            }
            this.form.coursePapersFilesList = coursePapersFilesList;
        },
        /**事实确认单批量导出 */
        handleExport01() {
            console.log("ids1111", this.ids01);
            exportWord({ ids: this.ids01 }).then(res => {
                // this.download(res.msg);
                downloadFile(res.msg);
            })
        },

        /**问题底稿批量导出 */
        handleExport() {
            console.log("ids222", this.ids02);
            exportWord02({ ids: this.ids02 }).then(res => {
                // this.download(res.msg);
                downloadFile(res.msg);
            })
        },
        /**巡察组详情 */
        handleGenerateDetails(val) {
            if (val.taskId) {
                getXunchaGroupInfoList({ taskId: val.taskId }).then((res) => {
                    if (res.data) {
                        this.formQuery = res.data;
                        this.groupingShow = true;
                        this.groupingTitle = "查看分组";
                    } else {
                        this.$message.error("该巡察组没有进行分组！")
                    }
                })
            } else {
                this.$message.error("请先选择巡察组！")
            }
        },
        // 文件下载
        onDownload(fileId, caseId) {
            downloadFile(this.activeName == "voucher"?this.form01.fileId:this.form.fileId);
        },
        handlePreviewFile() { 
            previewFile(this.activeName == "voucher"?this.form01.fileId:this.form.fileId);
        },
        // 取消查看详情
        cancelForm01() {
            this.isOpenQuery01 = false;
            this.reset();
        },
        // 取消查看详情
        cancelForm() {
            this.isOpenQuery = false;
            this.reset();
        },
        // 退回生成
        clickreturn() {
            if (this.handleTab == "1") {
                const parm = {
                    ids: this.handleId,
                    option: this.returnform.handle,
                    courseType: this.handleCurrent
                }
                sendBackFlow(parm).then(res => {
                    this.$message.success(res.msg);
                    this.returnShow = false;
                    this.returnform = {};
                    this.getList();
                })
            } else {
                const parm = {
                    ids: this.handleId,
                    option: this.returnform.handle,
                    courseType: this.handleCurrent,
                }
                sendFinishFlow(parm).then(res => {
                    console.log("res111111", res)
                    this.$message.success(res.msg);
                    this.returnShow = false;
                    this.returnform = {};
                    this.getList();
                })
            }

        },
        // tab切换
        handleClick(tab, event) {
            this.activeName = tab.name;
            // 清空 ids 数组
            this.ids01 = [];
            this.ids02 = [];
            this.getList();
        },
        // 试试确认单——表格多选
        handleSelectionChange01(selection) {
            console.log("selection", selection);
            this.handleCurrent = "1";
            let handleArray = [];
            let newArray = [];
            let array = [];
            let deleteShow01 = true;
            this.ids01 = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length;
            if (selection.length > 0) {
                selection.map((item, index) => {
                    array.push({
                        id: item.id,
                        status: item.status
                    });
                })
                if (array.length > 0) {
                    array.map((item, index) => {
                        if (item.status == 3) {
                            handleArray.push(item.id);
                        } else {
                            newArray.push(item.id);
                        }
                    })
                } else {
                    deleteShow01 = true;
                }
                if (newArray.length > 0) {
                    deleteShow01 = true;
                } else {
                    deleteShow01 = false;
                }
            } else {
                deleteShow01 = true;
            }
            this.handleArray = handleArray;
            this.deleteShow01 = deleteShow01;
            console.log("deleteShow01", deleteShow01, handleArray);
        },

        // 问题底稿——表格多选
        handleSelectionChange(selection) {

            this.handleCurrent = "2";
            let handleArray = [];
            let newArray = [];
            let array = [];
            let deleteShow = true;
            this.ids02 = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length;
            if (selection.length > 0) {
                selection.map((item, index) => {
                    array.push({
                        id: item.id,
                        status: item.status
                    });
                })
                if (array.length > 0) {
                    array.map((item, index) => {
                        if (item.status == 3) {
                            handleArray.push(item.id);
                        } else {
                            newArray.push(item.id);
                        }
                    })
                } else {
                    deleteShow = true;
                }
                if (newArray.length > 0) {
                    deleteShow = true;
                } else {
                    deleteShow = false;
                }
            } else {
                deleteShow = true;
            }
            this.handleArray = handleArray;
            this.deleteShow = deleteShow;
            console.log("ccccc", deleteShow, handleArray, this.ids02);
        },
        /**事实确认单查询 */
        // 根据巡察年份获取关联
        xunchaYearChange01(date) {
            this.piciIdDataList01 = [];
            queryPiciList({ year: date }).then((res) => {
                if (res.data.length > 0) {
                    this.piciIdDataList01 = res.data;
                } else {
                    this.piciIdDataList01 = [];
                }
                this.queryParams01.piciIdshow01 = false;
            })
        },
        //根据巡察批次获取关联
        handlePiciChange01(val) {
            this.groupNameDataList01 = [];
            listXunchaTaskInfo({ piciId: val.piciId }).then((res) => {
                if (res.rows.length > 0) {
                    this.groupNameDataList01 = res.rows;
                } else {
                    this.groupNameDataList01 = [];
                }
                this.queryParams01.groupNameshow01 = false;
            })
        },
        //根据巡察组获取关联
        handlexunchazuChange01(val) {
            let newData = this.groupNameDataList01;
            let newObject = {};
            if (newData != null) {
                newData.map((item, index) => {
                    if (item.taskId == val) {
                        newObject = item;
                        this.queryParams01.taskId = item.taskId;
                    }
                })
            }
        },
        // 问题底稿查询
        // 根据巡察年份获取关联
        xunchaYearChange(date) {
            this.piciIdDataList = [];
            queryPiciList({ year: date }).then((res) => {
                if (res.data.length > 0) {
                    this.piciIdDataList = res.data;
                } else {
                    this.piciIdDataList = [];
                }
                this.queryParams.piciIdshow = false;
            })
        },
        //根据巡察批次获取关联
        handlePiciChange(val) {
            this.groupNameDataList = [];
            listXunchaTaskInfo({ piciId: val.piciId }).then((res) => {
                if (res.rows.length > 0) {
                    this.groupNameDataList = res.rows;
                } else {
                    this.groupNameDataList = [];
                }
                this.queryParams.groupNameshow = false;
            })
        },
        //根据巡察组获取关联
        handlexunchazuChange(val) {
            let newData = this.groupNameDataList;
            let newObject = {};
            if (newData != null) {
                newData.map((item, index) => {
                    if (item.taskId == val) {
                        newObject = item;
                        this.queryParams.taskId = item.taskId;
                    }
                })
            }
        },
        // 搜索按钮操作
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        // 重置按钮操作
        resetQuery() {
            this.resetForm("queryForm01");
            this.resetForm("queryForm");
            this.handleQuery();
        },
        //查询巡察过程_事实确认单列表
        getList() {
            this.loading = true;
            if (this.activeName == 'voucher') {
                listCourseVoucher(this.queryParams01).then(res => {
                    console.log("res11111", res)
                    res.rows.map((item, index) => {
                        let agentListName = "";
                        if (item.agentList.length > 0) {
                            item.agentList.map((i, index) => {
                                agentListName += i.userName + "，"
                            })
                        } else {
                            agentListName = "";
                        }
                        if (agentListName.length > 0) {
                            agentListName = agentListName.substring(0, agentListName.length - 1)
                        } else {
                            agentListName = ""
                        }
                        item.agentListName = agentListName;
                    })
                    this.courseVoucherList = res.rows || [];
                    this.total01 = Number(res.total);
                    this.loading = false;
                });
            } else {
                listPapers(this.queryParams).then(res => {
                    console.log("resccccc", res);
                    this.papersList = res.rows || [];
                    this.total = Number(res.total);
                    this.loading = false;
                });
            }
            selectDictData({ dictType: 'course_voucher_territory' }).then((res) => {
                this.fieldDataList = res.data;
            })
            selectDictData({ dictType: 'course_papers_source' }).then((res) => {
                this.problemSourceDateList = res.data;
            });
            selectDictData({ dictType: 'course_papers_underInspection' }).then((res) => {
                this.hierarchyDateList = res.data;
            })
            selectDictData({ dictType: 'course_papers_department' }).then((res) => {
                this.problemDateList = res.data;
            });
            selectDictData({ dictType: 'course_papers_hierarchy' }).then((res) => {
                this.unitUnderDateList = res.data;
            });
            selectDictData({ dictType: 'course_papers_classification' }).then((res) => {
                this.questionDateList = res.data;
            })
        },
        // 事实确认单退回巡察组
        handleReturnPatrol01(val) {
            this.handleId = [val.id];
            this.handleCurrent = "1";
            this.handleTab = "1";
            this.returnTitle = "退回巡察组";
            this.returnShow = true;
        },
        // 资料入库
        handleDataWarehousing01(val) {
            this.handleId = [val.id];
            this.handleCurrent = "1";
            this.handleTab = "2",
                this.returnTitle = "资料入库";
            this.returnShow = true;
        },
        // 查看详情
        handleQueryOption01(val) {
            this.title01 = "查看事实确认单详情";
            this.isOpenQuery01 = true;
            const id = val.id;
            getCourseVoucher(id).then(res => {
                if (res.data.xunchaYear != null) {
                    res.data.xunchaYear = res.data.xunchaYear.toString();
                }
                let newArray = [];
                if (res.data.taskDateFrame != null) {
                    newArray = res.data.taskDateFrame.split(",");
                }
                res.data.taskDateFrame = newArray;
                let createdByDataList = [];
                queryPiciList({ year: res.data.xunchaYear.substring(0, 4) }).then((res) => {
                    let piciIdDataList = [];
                    if (res.data.length > 0) {
                        piciIdDataList = res.data;
                    } else {
                        piciIdDataList = [];
                    }
                    this.piciIdDataList = piciIdDataList;
                })
                let orgIdDataList = [];
                orgIdDataList.push({
                    value: res.companyNames,
                    label: res.companyNames,
                })
                this.orgIdDataList = orgIdDataList;
                listXunchaTaskInfo({ piciId: val.piciId }).then((res) => {


                    let groupNameDataList = [];
                    if (res.rows.length > 0) {
                        groupNameDataList = res.rows;
                    } else {
                        groupNameDataList = [];
                    }
                    this.groupNameDataList = groupNameDataList;
                })
                getUndertaker({ taskId: res.data.taskId, id: res.data.id }).then((res) => {
                    createdByDataList.push({
                        userId: res.data.currentBy.userId,
                        userName: res.data.currentBy.userName,
                        disabled: true,
                        id: res.data.currentBy.userId,
                        labelValue:res.data.currentBy.labelValue,
                    })
                    createdByDataList.push(...res.data.undertake);
                    this.createdByDataList = createdByDataList;
                })
                this.form01 = res.data;
                this.isOpenQuery01 = true;
                this.title01 = "查看事实确认单";
            });
            // 获得流程信息
            fetchWorkFlowInfo(id, "", (res) => {
                this.process01 = res;
            }, 1)
        },
        // 问题底稿
        handleReturnPatrol(val) {
            this.handleId = [val.id];
            this.handleCurrent = "2",
                this.handleTab = "1",
                this.returnTitle = "退回巡察组";
            this.returnShow = true;
        },
        // 资料入库
        handleDataWarehousing(val) {
            console.log("val11111", val);
            this.handleId = [val.id];
            this.handleCurrent = "2",
                this.handleTab = "2",
                this.returnTitle = "资料入库";
            this.returnShow = true;
        },
        // 查看详情
        handleQueryOption(val) {
            const id = val.id;
            let createdByDataList = [];
            getPapers(id).then(res => {
                console.log("res33333", res);
                let newArray = [];
                if (res.data.xunchaCoursePapers.checkTime != null) {
                    newArray = res.data.xunchaCoursePapers.checkTime.split(",");
                }
                res.data.xunchaCoursePapers.checkTime = newArray;

                this.form = { ...res.data.xunchaCoursePapers, ...res.data.xunchaCourseText };
                this.form.id = res.data.xunchaCoursePapers.id;
                this.isOpenQuery = true;
                this.title = "查看问题底稿详情";
                queryPiciList({ year: res.data.xunchaCoursePapers.xunchaYear.substring(0, 4) }).then((res) => {
                    let dataList = [];
                    if (res.data.length > 0) {
                        dataList = res.data;
                    } else {
                        dataList = [];
                    }
                    this.piciIdDataList = dataList;
                });
                listXunchaTaskInfo({ piciId: res.data.xunchaCoursePapers.piciId }).then((res) => {
                    let dataList = [];
                    if (res.rows.length > 0) {
                        dataList = res.rows;
                    } else {
                        dataList = [];
                    }
                    this.groupNameDataList = dataList;
                });
                // 获得承办人下拉框数据
                getUndertaker02({ taskId: res.data.xunchaCoursePapers.taskId, id: res.data.xunchaCoursePapers.id }).then((res) => {
                    createdByDataList.push({
                        userId: res.data.currentBy.userId,
                        userName: res.data.currentBy.userName,
                        disabled: true,
                        id: res.data.currentBy.userId,
                        labelValue:res.data.currentBy.labelValue,
                    })
                    createdByDataList.push(...res.data.undertake);
                    this.createdByDataList = createdByDataList;
                });
                if (res.data.coursePapersDomainList != null) {
                    let territoryCode = [];
                    res.data.coursePapersDomainList.map((item, index) => {
                        territoryCode.push(item.territoryCode);
                    })
                    this.form.territoryCode = territoryCode;
                };
                if (res.data.coursePapersSourceList != null) {
                    let problemSourceCode = [];
                    res.data.coursePapersSourceList.map((item, index) => {
                        problemSourceCode.push(item.problemSourceCode);
                    })
                    this.form.problemSourceCode = problemSourceCode;
                };
                if (this.form.problemSourceCode.indexOf("5") != -1) {
                    this.noteShow = true;
                }
                if (res.data.courseOverviewAgentList != null) {
                    let userId = [];
                    res.data.courseOverviewAgentList.map((item, index) => {
                        userId.push(item.id);
                    })
                    this.form.userId = userId;
                }
                if (res.data.coursePapersFilesList != null) {
                    res.data.coursePapersFilesList.map((item, index) => {
                        item.fileId = item.filePath;
                    })
                    this.form.coursePapersFilesList = res.data.coursePapersFilesList;
                }
            });
            // 获得流程信息
            fetchWorkFlowInfo(id, "", (res) => {
                this.process = res;
            }, 1)
        },
        // 批量退回巡察组
        readWorldFile01() {
            this.returnShow = true;
            this.returnTitle = "批量退回巡察组";
            this.handleId = this.handleArray;
            this.handleCurrent = "1";
            this.handleTab = "1";
        },
        // 批量退回巡察组
        readWorldFile() {
            this.returnShow = true;
            this.returnTitle = "批量退回巡察组";
            this.handleId = this.handleArray;
            this.handleCurrent = "2";
            this.handleTab = "1";
        },
        // 事实确认单——批量资料入库
        finishFlow01() {
            this.returnShow = true;
            this.returnTitle = "批量资料入库";
            this.handleId = this.handleArray;
            this.handleCurrent = "1";
            this.handleTab = "2";
        },

        // 问题底稿——批量资料入库
        finishFlow() {
            this.returnShow = true;
            this.returnTitle = "批量资料入库";
            this.handleId = this.handleArray;
            this.handleCurrent = "2";
            this.handleTab = "2";
        }
    }

}
</script>

<style lang="scss" scoped>
/deep/.el-tabs__header{
    margin: 0;
    background: #fff;
}
.app-main {
    padding: 20px;
}
input{
    font-size: 14px;
}

/deep/.el-tabs--top .el-tabs__item.is-top:nth-child(2) {
    padding-left: 20px;
}
// /deep/.el-input.is-disabled .el-input__inner {
//     background-color: #F5F7FA !important;
// }

/deep/.el-range-editor.is-disabled {
      background-color:#fff;
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

/deep/ .el-dialog__header {
    text-align: center;
}

</style>
