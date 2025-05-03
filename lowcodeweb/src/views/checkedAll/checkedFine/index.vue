<template>
    <div class="app-container">
        <div class="container">
            <div class="crad" v-show="showSearch">
                <expand-collapse>
                    <div slot="left">
                        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
                            <el-form-item label="申报单号：" prop="applyId">
                                <el-input v-model="queryParams.applyId" placeholder="请输入申报单号" clearable size="small"
                                    @keyup.enter.native="handleQuery" />
                            </el-form-item>
                            <el-form-item label="巡察年份：" prop="year">
                                <el-date-picker size="small" style="width: 205px" format="yyyy" value-format="yyyy"
                                    v-model="queryParams.year" type="year" placeholder="请选择巡察年份" @change="yearChange"
                                    :clearable="false">
                                </el-date-picker>
                            </el-form-item>
                            <el-form-item label="巡察批次：" prop="batchId">
                                <el-select v-model="queryParams.batchId" placeholder="请选择巡察批次" size="small">
                                    <el-option v-for="dict in batchIdOptions" :key="dict.batchId"
                                        :label="dict.batchName" :value="dict.batchId" :clearable="false" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="当前状态：" prop="status">
                                <el-select v-model="queryParams.status" placeholder="请选择当前状态" size="small"
                                    :clearable="false">
                                    <el-option v-for="dict in fineApproveStatusOption" :key="dict.dictValue"
                                        :label="dict.dictLabel" :value="dict.dictValue" />
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
                    <span>优秀/优良巡察员申报管理</span>
                </div>
            </div>
            <div class="crad-content">
                <el-row :gutter="10" class="mb12 utlsBtn mar20">
                    <el-col :span="1.5">
                        <el-button type="primary" size="mini" @click="handleAdd">
                            <!-- v-hasPermi="['checkedAll:leadingRating:export']" -->
                            优秀/优良巡察员申报
                        </el-button>
                    </el-col>
                    <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList" />
                </el-row>
                <el-table v-loading="loading" :data="checkedFineList" @selection-change="handleSelectionChange">
                    <!-- <el-table-column type="selection" width="55" align="left" /> -->
                    <el-table-column type="index" width="55" align="left" label="序号" />
                    <el-table-column label="业务单号" align="center" prop="applyCode" width="200" />
                    <el-table-column label="巡察年份" align="center" prop="year" />
                    <el-table-column label="巡察批次" align="center" prop="batchName" width="180" />
                    <el-table-column label="申报类型" align="center" prop="fineTypeIds">
                        <template slot-scope="scope">
                            {{ scope.row.fineTypeIds | filterMore(fineTypeIdsOptions) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="申报人" align="center" prop="applyUserName" />
                    <el-table-column label="申报时间" align="center" prop="createdTimeStr" width="200" />
                    <el-table-column label="当前状态" align="center" prop="status" width="100">
                        <template slot-scope="scope">
                            {{ scope.row.status | filterAll(fineApproveStatusOption) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                        width="160">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
                            <!-- <el-button size="mini" type="text" @click="submitForm(scope.row)"
                                v-if="scope.row.status == 0">提交</el-button> -->
                            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
                                v-if="scope.row.status == 0 || scope.row.status == 5">修改</el-button>
                            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
                                v-if="scope.row.status == 0">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pageBox">
                    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                        :limit.sync="queryParams.pageSize" @pagination="getList" />
                </div>
            </div>
        </div>
        <!-- 添加或修改优秀员工申报名单 对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body :close-on-click-modal="false">
            <div class="crad">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>巡察基础信息</span>
                    </div>
                </div>
                <div class="crad-content">
                    <expand-collapse>
                        <div slot="left">
                            <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules">
                                <el-form-item label="巡察年份：" prop="seeyear">
                                    <el-date-picker size="small" style="width: 205px" format="yyyy" value-format="yyyy"
                                        v-model="seeParams.year" type="year" placeholder="请选择巡察年份"
                                        @change="yearChangefd" :clearable="false">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="巡察批次：" prop="seebatchId">
                                    <el-select v-model="seeParams.batchId" placeholder="请选择巡察批次" size="small"
                                        @change="batchChange">
                                        <el-option v-for="dict in batchIdChildOptions" :key="dict.batchId"
                                            :label="dict.batchName" :value="dict.batchId" :clearable="false" />
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="申报类型：" prop="fineTypeIds">
                                    <el-checkbox-group v-model="seeParams.fineTypeIds" @change="fineTypeChange">
                                        <el-checkbox :label="item.dictValue" v-for="item in fineTypeOption"
                                            :key="item.dictValue">{{ item.dictLabel }}</el-checkbox>
                                    </el-checkbox-group>
                                </el-form-item>
                            </el-form>
                        </div>
                    </expand-collapse>
                </div>
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>评优名单</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="优秀巡察员" name="1" v-if="seeParamsCopy.fineTypeIds.indexOf('1') > -1">
                            <div class="">
                                <el-row :gutter="10" class="mb12 utlsBtn mar20">
                                    <el-col :span="1.5">
                                        <el-button type="primary" size="mini" @click="handleAddChild(1)">
                                            择优添加</el-button>
                                    </el-col>
                                </el-row>
                                <el-table v-loading="loadingChild" :data="tableList1" ref="elTable" key="table1">
                                    <el-table-column type="index" label="序号" width="55" align="left" />
                                    <el-table-column label="巡察员" align="center" prop="userName" />
                                    <el-table-column label="所属巡察组" align="center" prop="groupName" />
                                    <el-table-column label="组内标签" align="center" prop="userTypeName" />
                                    <el-table-column label="综合评分" align="center" prop="finalResult" />
                                    <el-table-column label="所在单位" align="center" prop="companyName" />
                                    <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName"
                                        width="180" />
                                    <el-table-column label="推荐方式" align="center" prop="upTypeId">
                                        <template slot-scope="scope">
                                            {{ scope.row.upTypeId | filterAll(fineUpTypeOption) }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width"
                                        fixed="right" width="100">
                                        <template slot-scope="scope">
                                            <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看
                                            </el-button>
                                            <el-button size="mini" type="text"
                                                @click="handleDeletChild('1', scope.row, scope.$index)">删除
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="优良巡察员" name="2" v-if="seeParamsCopy.fineTypeIds.indexOf('2') > -1">
                            <div class="">
                                <el-row :gutter="10" class="mb12 utlsBtn mar20">
                                    <el-col :span="1.5">
                                        <el-button type="primary" size="mini" @click="handleAddChild(2)">
                                            择优添加</el-button>
                                    </el-col>
                                </el-row>
                                <el-table v-loading="loadingChild" :data="tableList2" ref="elTable" key="table1">
                                    <el-table-column type="index" label="序号" width="55" align="left" />
                                    <el-table-column label="巡察员" align="center" prop="userName" />
                                    <el-table-column label="所属巡察组" align="center" prop="groupName" />
                                    <el-table-column label="组内标签" align="center" prop="userTypeName" />
                                    <el-table-column label="综合评分" align="center" prop="finalResult" />
                                    <el-table-column label="所在单位" align="center" prop="companyName" />
                                    <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName"
                                        width="180" />
                                    <el-table-column label="推荐方式" align="center" prop="upTypeId">
                                        <template slot-scope="scope">
                                            {{ scope.row.upTypeId | filterAll(fineUpTypeOption) }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width"
                                        fixed="right" width="100">
                                        <template slot-scope="scope">
                                            <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看
                                            </el-button>
                                            <el-button size="mini" type="text"
                                                @click="handleDeletChild('2', scope.row, scope.$index)">删除
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>

                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>证明材料</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-form :model="uploadParams" ref="uploadForm" :inline="true" label-width="140px" :rules="rules">
                        <el-form-item label="*上党委审议材料：" prop="uploadFile">
                            <el-button type="primary" size="mini" @click="uploadFile">上传文件</el-button>
                        </el-form-item>
                    </el-form>
                    <el-row class="boxList">
                        <el-col :span="3" v-for="(item, index) in dialogArray" :key="index">
                            <i icon="document" class="uploader-file-icon"
                                style="font-style: normal; position: relative;">
                                <i class="el-icon-circle-close" @click="onDeletUpload(item.fileId)"></i>
                            </i>
                            <div>{{ item.fileName }}</div>

                        </el-col>
                    </el-row>
                </div>
            </div>
            <deep-btn @close="cancel" @saveDraftData="saveBtn" :businessKey="id">
                <el-button type="primary" @click="saveBtn">暂 存</el-button>
                <el-button type="primary" @click="submitForm">提 交</el-button>
            </deep-btn>
        </el-dialog>
        <!-- 查看优秀员工申报名单 对话框 -->
        <el-dialog :title="title" :visible.sync="watchOpen" width="1100px" append-to-body :close-on-click-modal="false">
            <div class="crad">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>巡察基础信息</span>
                    </div>
                </div>
                <div class="crad-content">
                    <expand-collapse>
                        <div slot="left">
                            <el-form :model="seeParams" ref="watchForm" :inline="true" label-width="120px"
                                :rules="rules" :disabled="true">
                                <el-form-item label="巡察年份：" prop="seeyear">
                                    <el-date-picker size="small" style="width: 205px" format="yyyy" value-format="yyyy"
                                        v-model="seeParams.year" type="year" placeholder="请选择巡察年份"
                                        @change="yearChangefd" :clearable="false">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="巡察批次：" prop="seebatchId">
                                    <el-select v-model="seeParams.batchId" placeholder="请选择巡察批次" size="small"
                                        @change="batchChange">
                                        <el-option v-for="dict in batchIdChildOptions" :key="dict.batchId"
                                            :label="dict.batchName" :value="dict.batchId" :clearable="false" />
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="申报类型：" prop="fineTypeIds">
                                    <el-checkbox-group v-model="seeParams.fineTypeIds" @change="fineTypeChange">
                                        <el-checkbox :label="item.dictValue" v-for="item in fineTypeOption"
                                            :key="item.dictValue">{{ item.dictLabel }}</el-checkbox>
                                    </el-checkbox-group>
                                </el-form-item>
                            </el-form>
                        </div>
                    </expand-collapse>
                </div>
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>评优名单</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="优秀巡察员" name="1" v-if="seeParamsCopy.fineTypeIds.indexOf('1') > -1">
                            <div class="">
                                <el-table v-loading="loadingChild" :data="tableList1" ref="elTable" key="table1">
                                    <el-table-column type="index" label="序号" width="55" align="left" />
                                    <el-table-column label="巡察员" align="center" prop="userName" />
                                    <el-table-column label="所属巡察组" align="center" prop="groupName" />
                                    <el-table-column label="组内标签" align="center" prop="userTypeName" />
                                    <el-table-column label="综合评分" align="center" prop="finalResult" />
                                    <el-table-column label="所在单位" align="center" prop="companyName" />
                                    <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName"
                                        width="180" />
                                    <el-table-column label="推荐方式" align="center" prop="upTypeId">
                                        <template slot-scope="scope">
                                            {{ scope.row.upTypeId | filterAll(fineUpTypeOption) }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width"
                                        fixed="right" width="100">
                                        <template slot-scope="scope">
                                            <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="优良巡察员" name="2" v-if="seeParamsCopy.fineTypeIds.indexOf('2') > -1">
                            <div class="">
                                <el-table v-loading="loadingChild" :data="tableList2" ref="elTable" key="table1">
                                    <el-table-column type="index" label="序号" width="55" align="left" />
                                    <el-table-column label="巡察员" align="center" prop="userName" />
                                    <el-table-column label="所属巡察组" align="center" prop="groupName" />
                                    <el-table-column label="组内标签" align="center" prop="userTypeName" />
                                    <el-table-column label="综合评分" align="center" prop="finalResult" />
                                    <el-table-column label="所在单位" align="center" prop="companyName" />
                                    <el-table-column label="被巡察单位" align="center" prop="toExamCompanyName"
                                        width="180" />
                                    <el-table-column label="推荐方式" align="center" prop="upTypeId">
                                        <template slot-scope="scope">
                                            {{ scope.row.upTypeId | filterAll(fineUpTypeOption) }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width"
                                        fixed="right" width="100">
                                        <template slot-scope="scope">
                                            <el-button size="mini" type="text" @click="onUserInfo(scope.row)">查看
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>

                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>证明材料</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-form :model="uploadParams" ref="uploadForm" :inline="true" label-width="140px" :rules="rules">
                        <el-form-item label="*上党委审议材料：" prop="uploadFile">
                            <!-- <el-button type="primary" size="mini" @click="uploadFile">上传文件</el-button> -->
                            {{ dialogArray.length == 0 ? '尚未保存材料' : '' }}
                        </el-form-item>
                    </el-form>
                    <el-row class="boxList">
                        <el-col :span="3" v-for="(item, index) in dialogArray" :key="index">
                            <i icon="document" class="uploader-file-icon"
                                style="font-style: normal; position: relative;">
                                <!-- <i class="el-icon-circle-close" @click="onDeletUpload(item.fileId)"></i> -->
                            </i>
                            <div>{{ item.fileName }}</div>
                            <div>
                                <el-button type="primary" size="mini"
                                    @click="importTemplate(item.fileId, item.fileName)">下载
                                </el-button>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </div>
            <all-flow-step :allFlowStep="process.allFlowStep" v-if="seeParams.status != '0'"></all-flow-step>
            <deep-btn @close="cancel" :isTodo="false" type="0" :ischeck="ischeck" v-if="watchOpen"></deep-btn>
        </el-dialog>

        <el-dialog title="文件上传" :visible.sync="dialogVisible" width="600px" @open="initUpload"
            :before-close="handleClose">
            <div class="uploadBox">
                <div class="el-upload__tip" style="color:red" slot="tip">提示：导入文件不宜过大！</div>
                <CommonUpload v-if="isShowUpload" ref="CommonUpload" :single-file="true" @file-success="onFileSuccess"
                    @upload-start="uploadStart" :singleFile="false" @file-removed="onFileRemoved"></CommonUpload>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 优秀员工申报名单 导入对话框 -->
        <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
            <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                    将文件拖到此处，或
                    <em>点击上传</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                    <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
                    <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
                </div>
                <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
            </el-upload>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitFileForm">确 定</el-button>
                <el-button @click="upload.open = false">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 择优选择弹出框 -->
        <el-dialog title="择优添加" :visible.sync="finedialog" width="900px" append-to-body>
            <div class="crad">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>人员选择</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-row>
                        <el-col :span="12">
                            <el-form :model="seeParams" ref="seeForm" :inline="true" label-width="120px" :rules="rules">
                                <el-form-item label="姓名：" prop="seeyear">
                                    <el-input v-model="userName" placeholder="请输入姓名" clearable size="small" />
                                </el-form-item>
                            </el-form>
                        </el-col>
                        <el-col :span="12">
                            <el-button type="primary" size="mini" @click="handleQueryFine" style="margin-top: 3px;">搜索
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>人员信息</span>
                    </div>
                </div>
                <div class="crad-content">
                    <el-table v-loading="loadingFine" :data="fineList" @selection-change="handleSelectionFineChange">
                        <el-table-column type="selection" width="55" align="left" />
                        <el-table-column label="姓名" align="center" prop="userName" />
                        <el-table-column label="所属巡察组" align="center" prop="groupName" />
                        <el-table-column label="组内标签" align="center" prop="userTypeName" />
                        <el-table-column label="综合评分" align="center" prop="finalResult" />
                    </el-table>
                </div>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="closeFine">取 消</el-button>
                    <el-button type="primary" @click="tabMerge">确 定</el-button>
                </div>
            </div>
        </el-dialog>
        <submit-process processKey="fine_apply_process" stepName="巡察办室经理审核" :dialogVisible="submitProcessShow"
            @close="submitProcessShow = false" @complete="saveFromData" :title="stitle"></submit-process>

        <collectForm :dialogVisible="iFormShow.open" :id="iFormShow.id" more @close="handleCollectFormClose">
        </collectForm>
    </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
    getBatchList,
    checkedFineList,
    getToApplyFineList,
    getOtherFinePerList,
    delCheckedFine,
    saveFineApply,
    getFileListOfApply,
    submitFineApply,
    queryTaskUnit,
} from "@/api/checkedAll/checkedFine";


import deepBtn from "@/components/WorkFlow/deepBtn";
import CommonUpload from "@/components/CommonUpload/components/chunkUpload";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import { formatDate } from '@/utils/index';
import SubmitProcess from '@/components/WorkFlow/submitProcess';
import { fetchWorkFlowInfo } from "@/utils/workflow";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import collectForm from "@/views/talent/collect/collectForm.vue";
import {
    downloadFile,
} from '@/utils/uploadOperate'
export default {
    name: "CheckedFine",
    components: { ExpandCollapse, deepBtn, CommonUpload, SubmitProcess, AllFlowStep, collectForm },
    data () {
        return {
            stitle: '提交',
            ischeck: 1,
            iFormShow: { open: false, id: null },
            id: null,
            disabled: false,
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
            idsFine: [],
            // 非单个禁用
            singleFine: true,
            // 非多个禁用
            multipleFine: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 优秀员工申报名单 表格数据
            checkedFineList: [],
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
                url: process.env.VUE_APP_BASE_API + "/checkedAll/checkedFine/importData",
            },
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                year: null,
                batchId: null,
                fineTypeIds: [],
                status: null,
            },
            activeName: "1",
            tabKey: "",
            seeParams: {
                year: null,
                batchId: null,
                fineTypeIds: [],
                applyId: null,
            },
            seeParamsCopy: {
                year: null,
                batchId: null,
                fineTypeIds: [],
                applyId: null,
            },
            uploadParams: {},
            // 表单参数
            form: {},

            // 表单校验
            rules: {
            },

            process: { allFlowStep: [] },
            isShowUpload: false,
            dialogVisible: false,
            dialogArray: [],
            files: [],
            batchIdOptions: [],
            batchIdChildOptions: [],
            loadingChild: false,
            fineApproveStatusOption: [],
            fineTypeOption: [],
            fineUpTypeOption: [],
            //临时的数组
            temporary1: false,
            temporary2: false,
            tableList1: [],
            tableList2: [],
            fineType: null,
            finedialog: false,
            userName: null,
            loadingFine: false,
            fineList: [],
            watchOpen: false,
            watchParams: {},
            submitProcessShow: false,
            fineTypeIdsOptions: [{ dictLabel: '优秀巡察员', dictValue: '1' }, { dictLabel: '优良巡察员', dictValue: '2' },]
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
        filterMore (val, optionArr) {
            if (val && optionArr.length) {
                let newarr =
                    Object.prototype.toString.call(val).indexOf("Array") >= 0
                        ? val
                        : val.split(",");
                var arr = [];
                optionArr.forEach((item) => {
                    if (newarr.indexOf(item.dictValue) !== -1) {
                        arr.push(item.dictLabel);
                    }
                });
                if (!arr.length) return "";
                return arr.toString();
            } else {
                return "";
            }
        },
    },
    watch: {
        "seeParamsCopy.fineTypeIds": function (va, val) {
            // console.log(va[0].toString())
            this.activeName = va && va[0] ? va[0].toString() : ''
        }
    },
    created () {
        //状态 ~草稿，室经理审批
        this.getDicts("fine_approve_status").then(res => {
            this.fineApproveStatusOption = res.data ? res.data : [];
        })
        //类型 ~优秀巡察员，优良巡察员
        this.getDicts("fine_type").then(res => {
            this.fineTypeOption = res.data ? res.data : [];
            this.seeParams.fineTypeIds = this.fineTypeOption.map((item) => {
                return item.dictValue
            })
            this.seeParamsCopy.fineTypeIds = this.seeParams.fineTypeIds;
        })
        //组内评优,择优添加
        this.getDicts("fine_up_type").then(res => {
            this.fineUpTypeOption = res.data ? res.data : [];
        })

        //默认选中最后一年最后一批
        let date = new Date();
        let year = date.getFullYear();
        this.seeParams.year = ('' + year);
        getBatchList({year:this.seeParams.year}).then(res => {
            this.batchIdChildOptions = res.data ? res.data : [];
            this.seeParams.batchId = res.data[0].batchId;
            this.seeParamsCopy.batchId = res.data[0].batchId;
        })
        // this.yearChangefd('');
        this.yearChange('');
        this.getList();
    },
    methods: {
        onUserInfo (row) {
            this.iFormShow.id = row.userId;
            this.iFormShow.open = true;
            console.log(this.iFormShow);
        },

        handleCollectFormClose () {
            this.iFormShow.open = false
        },
        /** 查询优秀员工申报名单 列表 */
        getList () {
            // this.loading = true;
            checkedFineList(this.queryParams).then(response => {
                this.checkedFineList = response.rows || [];
                this.total = Number(response.total);
                this.loading = false;
            });
        },
        // 取消按钮
        cancel () {
            this.watchOpen = false;
            this.open = false;
            this.submitProcessShow = false;
            this.reset();
        },
        yearChange (val) {
            //批次
            this.queryParams.batchId = null;
            getBatchList({ year: val }).then(res => {
                this.batchIdOptions = res.data ? res.data : [];
            })
        },
        yearChangefd (val) {
            console.log(this.seeParams.year)
            if (this.seeParams.year != this.seeParamsCopy.year && this.seeParamsCopy.year != null) {
                //里批次
                if (this.temporary1 || this.temporary2) {
                    this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.seeParamsCopy.year = this.seeParams.year;
                        this.seeParams.batchId = null;
                        this.seeParamsCopy.batchId = null;

                        getBatchList({ year: this.seeParams.year }).then(res => {
                            this.batchIdChildOptions = res.data ? res.data : [];
                        })
                    }).catch(() => {
                        this.seeParams.year = this.seeParamsCopy.year;
                        // this.seeParams.batchId = null;
                        // this.seeParamsCopy.batchId = null;

                        // getBatchList({ year: this.seeParams.year }).then(res => {
                        //     this.batchIdChildOptions = res.data ? res.data : [];
                        // })
                    });
                } else {
                    this.seeParamsCopy.year = this.seeParams.year;
                    this.seeParams.batchId = null;
                    this.seeParamsCopy.batchId = null;
                    getBatchList({ year: this.seeParams.year }).then(res => {
                        this.batchIdChildOptions = res.data ? res.data : [];
                    })
                }
            } else {
                this.seeParamsCopy.year = this.seeParams.year;
                this.seeParams.batchId = null;
                this.seeParamsCopy.batchId = null;
                getBatchList({ year: this.seeParams.year }).then(res => {
                    this.batchIdChildOptions = res.data ? res.data : [];
                })
            }

        },
        batchChange () {
            if (this.seeParams.batchId != this.seeParamsCopy.batchId && this.seeParamsCopy.batchId != null) {
                //里批次-temporary1是第一个表格
                if (this.temporary1 || this.temporary2) {
                    this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.seeParamsCopy.batchId = this.seeParams.batchId;
                        this.getFineDetails()
                    }).catch(() => {
                        this.seeParams.batchId = this.seeParamsCopy.batchId;
                    });
                } else {
                    this.seeParamsCopy.batchId = this.seeParams.batchId;
                    this.getFineDetails()
                }
            } else {
                this.seeParamsCopy.batchId = this.seeParams.batchId;
                this.getFineDetails()
            }
        },
        //查询条件变化-多选
        fineTypeChange (val) {
            // console.log(val.length, this.seeParamsCopy.fineTypeIds.length)
            let that = this;
            // let tabkey = null
            if (val.length > this.seeParamsCopy.fineTypeIds.length) {
                //++
                // tabkey = val.filter(item=>item != this.seeParamsCopy.fineTypeIds)
                if (this.seeParams.batchId == null || this.seeParams.batchId == "") {
                    this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
                    this.$message({
                        message: '请选择批次',
                        type: 'warning'
                    });
                    return
                } else {
                    if (this.temporary1 || this.temporary2) {
                        this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            this.getFineDetailsType(() => {
                                that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
                            }, val)
                        }).catch(() => {
                            this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
                        });
                    } else {
                        that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
                        this.getFineDetailsType()
                    }
                }
            } else if (val.length < this.seeParamsCopy.fineTypeIds.length) {
                //--
                // tabkey = this.seeParamsCopy.fineTypeIds.filter(item=>item != val)
                if (this.seeParams.batchId == null || this.seeParams.batchId == "") {
                    this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
                    this.$message({
                        message: '请选择批次',
                        type: 'warning'
                    });
                    return
                } else {
                    if (this.temporary1 || this.temporary2) {
                        this.$confirm('是否确定修改,如若修改可能会将没有保存的数据清空', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            this.getFineDetailsType(() => {
                                that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
                            }, val)
                        }).catch(() => {
                            this.seeParams.fineTypeIds = this.seeParamsCopy.fineTypeIds
                        });
                    } else {
                        that.seeParamsCopy.fineTypeIds = that.seeParams.fineTypeIds
                        this.getFineDetailsType()
                    }
                }
            }

            // this.seeParamsCopy.fineTypeIds = val;
        },
        //择优添加-搜索
        handleQueryFine () {
            if (this.userName == null || this.userName == "") {
                this.$message({
                    message: '请输入姓名',
                    type: 'warning'
                });
                return
            }
            let Array = [...this.tableList1, ...this.tableList2];
            let notFindUserIds = Array.map(item => {
                return item.userId
            })
            let obj = {
                batchId: this.seeParams.batchId,
                fineTypeId: this.fineType,
                userName: this.userName,
                notFindUserIds: notFindUserIds.toString(),
            }
            getOtherFinePerList(obj).then(res => {
                if (res.data.length == 0) {
                    this.$message({
                        message: '暂无此人',
                        type: 'warning'
                    });
                    return
                }
                this.fineList = res.data;
            })
        },
        //tab切换
        handleClick () {

        },
        //优秀优良添加
        handleAddChild (num) {
            this.fineType = num;
            this.finedialog = true;
        },
        //优秀优良添加确定加入按钮
        tabMerge () {
            if (this.idsFine.length == 0) {
                this.$message({
                    message: '请选择人员添加',
                    type: 'warning'
                });
                return;
            }
            let arr = [];
            if (this.fineType == "1") {//优秀
                this.fineList.forEach((item, index) => {
                    this.idsFine.forEach(itm => {
                        if (item.userId == itm) {
                            arr.push(item)
                        }
                    })
                })
                this.temporary1 = true;
                this.tableList1 = [...this.tableList1, ...arr]
            } else if (this.fineType == "2") {//优良
                this.fineList.forEach((item, index) => {
                    this.idsFine.forEach(itm => {
                        if (item.userId == itm) {
                            arr.push(item)
                        }
                    })
                })
                this.temporary2 = true;
                this.tableList2 = [...this.tableList2, ...arr]
            }
            this.closeFine();
        },
        closeFine () {
            this.finedialog = false;
            this.fineList = [];
            this.idsFine = [];
        },
        //删除
        handleDeletChild (Id, row, index) {
            console.log(Id, row, index);
            if (Id == '1') {
                this.tableList1.splice(index, 1)
                this.temporary1 = true
            } else {
                this.tableList2.splice(index, 1)
                this.temporary2 = true
            }
        },

        uploadFile (row) {
            this.dialogVisible = true;
        },
        handleClose () {
            this.dialogVisible = false;
            this.isShowUpload = false;
        },
        //上传前
        uploadStart () {
            const loading = this.openLoading();
        },
        //成功回调
        onFileSuccess (file, files, fileList) {
            this.files = files.map(item => item.result.data ? item.result.data : {});
            const loading = this.openLoading();
            loading.close();
        },
        //移除回调
        onFileRemoved (file, files, fileList) {
            // this.file = null;
            // this.dialogObj.fileKey = "";
            // this.dialogObj.fileName = "";
        },
        onDeletUpload (id) {
            console.log(id)
            this.$confirm('是否确定删除文件', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let index = this.dialogArray.map((item, index) => { item.fileId == id; return index })[0]
                this.dialogArray.splice(index, 1)
            }).catch(() => {

            });
        },
        confirm () {
            if (!this.files.length) {
                this.$message.warning("请上传文件!");
                return;
            }
            this.dialogArray = [...this.dialogArray, ...this.files];
            this.files = [];
            this.dialogVisible = false;
            this.isShowUpload = false;
        },
        //打开文件上传清除之前的记录
        initUpload () {
            this.isShowUpload = true;
        },

        // 表单重置
        reset () {
            this.seeParams = {
                year: null,
                batchId: null,
                fineTypeIds: [],
                applyId: null,
            };
            this.seeParamsCopy = {
                year: null,
                batchId: null,
                fineTypeIds: [],
                applyId: null,
            };
            this.temporary1 = false;
            this.temporary2 = false;
            this.tableList1 = [];
            this.tableList2 = [];
            this.fineType = null;
            this.userName = null;
            this.fineList = [];
            //--
            this.seeParams.fineTypeIds = this.fineTypeOption.map((item) => {
                return item.dictValue
            })
            this.seeParamsCopy.fineTypeIds = this.seeParams.fineTypeIds;
            let date = new Date();
            let year = date.getFullYear();
            this.seeParams.year = ('' + year);
            this.seeParams.batchId = this.batchIdChildOptions.length ? this.batchIdChildOptions[0].batchId : '';
            this.seeParamsCopy.batchId = this.batchIdChildOptions.length ? this.batchIdChildOptions[0].batchId : '';
            this.dialogArray = [];
            this.resetForm("seeForm");
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
            this.ids = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        handleSelectionFineChange (selection) {
            this.idsFine = selection.map(item => item.userId)
            this.singleFine = selection.length !== 1
            this.multipleFine = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd () {
            this.disabled = false;
            this.reset();
            this.open = true;
            this.title = "优秀/优良巡察员申报新增";
            this.getFineDetails()
        },
        //推荐人员列表
        getFineDetails (func, applyid) {
            let forms = JSON.parse(JSON.stringify(this.seeParams))
            let fineTypeIds = JSON.parse(JSON.stringify(forms.fineTypeIds));
            // debugger
            applyid && (forms.applyId = applyid)
            forms.fineTypeIds = fineTypeIds.toString()
            this.loadingChild = true;
            getToApplyFineList(forms).then(res => {
                let array = ["1", "2"];
                let arrUser = [];
                array.forEach(item => {
                    fineTypeIds.forEach(itm => {
                        if (item == itm) {
                            arrUser.push(itm)
                        }
                    })
                })
                if (fineTypeIds.indexOf("1") > -1) {
                    this.tableList1 = res.data['1'] ? res.data['1'] : []
                    // this.temporary1 = false
                }
                if (fineTypeIds.indexOf("2") > -1) {
                    this.tableList2 = res.data['2'] ? res.data['2'] : []
                    // this.temporary2 = false
                }
                this.loadingChild = false;
                func && func();
            })
        },
        //推荐人员列表--类型特供向
        getFineDetailsType (func, values) {
            let forms = JSON.parse(JSON.stringify(this.seeParams))
            let formsC = JSON.parse(JSON.stringify(this.seeParamsCopy))
            let fineTypeIds = JSON.parse(JSON.stringify(forms.fineTypeIds));
            let fineTypeIdsC = JSON.parse(JSON.stringify(formsC.fineTypeIds));
            forms.fineTypeIds = fineTypeIds.toString()
            this.loadingChild = true;
            getToApplyFineList(forms).then(res => {
                if (fineTypeIds.length > fineTypeIdsC.length) {//加
                    let arrs = fineTypeIds.filter((item)=>{
                        return !fineTypeIdsC.includes(item)
                    })
                    if (arrs.indexOf("1") > -1) {
                        this.tableList1 = res.data['1'] ? res.data['1'] : []
                    }
                    if (arrs.indexOf("2") > -1) {
                        this.tableList2 = res.data['2'] ? res.data['2'] : []
                    }
                }
                if (fineTypeIdsC.length > fineTypeIds.length) {//减
                    let arrs = fineTypeIdsC.filter((item)=>{
                        return !fineTypeIds.includes(item)
                    })
                    if (arrs.indexOf("1") > -1) {
                        this.tableList1 = []
                    }
                    if (arrs.indexOf("2") > -1) {
                        this.tableList2 = []
                    }
                }

                this.loadingChild = false;
                func && func();
            })
        },
        //获取附件
        getFileListOfApply (applyId) {
            getFileListOfApply({ applyId: applyId }).then(res => {
                this.dialogArray = res.data
            })
        },
        /** 修改按钮操作 */
        handleUpdate (row) {
            const id = row.id || this.ids
            checkedFineList({ id }).then(res => {
                let data = res.rows[0]
                this.seeParams.applyId = id;
                this.seeParams.year = data.year;
                this.seeParams.batchId = data.batchId;
                this.seeParams.fineTypeIds = data.fineTypeIds ? data.fineTypeIds.split(',') : [];
                this.seeParamsCopy.applyId = id;
                this.seeParamsCopy.year = data.year;
                this.seeParamsCopy.batchId = data.batchId;
                this.seeParamsCopy.fineTypeIds = data.fineTypeIds ? data.fineTypeIds.split(',') : [];
                this.getFineDetails(() => { }, data.id);
                this.getFileListOfApply(id);
                this.open = true;
                this.title = "修改优秀优良巡察员";
            });
        },
        /**详情按钮*/
        handleQueryOption (row) {
            // this.reset();
            const id = row.id || this.ids
            checkedFineList({ id }).then(res => {
                let data = res.rows[0]
                this.seeParams.year = data.year;
                this.seeParams.batchId = data.batchId;
                this.seeParams.fineTypeIds = data.fineTypeIds.split(',');
                this.seeParamsCopy.year = data.year;
                this.seeParamsCopy.batchId = data.batchId;
                this.seeParamsCopy.fineTypeIds = data.fineTypeIds.split(',');
                this.seeParams.status = data.status;
                if (data.status != '0') {
                    fetchWorkFlowInfo(id, null, (res) => {
                        this.process = res;
                    }, this.ischeck);
                }
                this.getFineDetails(() => { }, data.id);
                this.getFileListOfApply(id);
                this.watchOpen = true;
                this.title = "查看优秀优良巡察员";
            });

        },
        /** 提交按钮 */
        submitForm () {
            let that = this;
            if (this.disabled) {
                this.open = false;
                return
            }
            if (this.seeParams.fineTypeIds.length == 0) {
                this.$message({
                    message: '请至少添加一个申报类型',
                    type: 'warning'
                });
                return
            }
            if (this.tableList1.length == 0 && this.seeParamsCopy.fineTypeIds.indexOf('1') > -1) {
                this.$message({
                    message: '请至少添加一位优秀巡察员',
                    type: 'warning'
                });
                return
            }
            if (this.tableList2.length == 0 && this.seeParamsCopy.fineTypeIds.indexOf('2') > -1) {
                this.$message({
                    message: '请至少添加一位优良巡察员',
                    type: 'warning'
                });
                return
            }
            if (this.dialogArray.length == 0) {
                this.$message({
                    message: '请至少上传一个材料',
                    type: 'warning'
                });
                return
            }
            this.submitProcessShow = true;
        },
        //真-提交
        saveFromData (value) {
            console.log(value)
            if (this.seeParams.fineTypeIds == "" || this.seeParams.fineTypeIds == null) {
                this.$message({
                    message: '请选择申报类型',
                    type: 'warning'
                });
                return
            }
            if (this.tableList1.length == 0 && this.tableList2.length == 0) {
                this.$message({
                    message: '请至少添加一位人员',
                    type: 'warning'
                });
                return
            }
            const loading = this.openLoading();
            let nextarr = JSON.parse(value.variables);
            let obj = {
                applyId: this.seeParams.applyId ? this.seeParams.applyId : '',
                batchId: this.seeParams.batchId,
                year: this.seeParams.year,
                '1': this.seeParams.fineTypeIds.indexOf("1") > -1 ? JSON.stringify(this.tableList1) : '',
                '2': this.seeParams.fineTypeIds.indexOf("2") > -1 ? JSON.stringify(this.tableList2) : '',
                fileKeyList: JSON.stringify(this.dialogArray),
                flowInfo: JSON.stringify({
                    nextUserId: nextarr.nextDealUser,
                    option: nextarr.option,
                    stepName: "巡察办室经理审核"
                })
            }
            submitFineApply(obj).then(res => {
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
            // seeParams.fineTypeIds
            if (this.seeParams.fineTypeIds == "" || this.seeParams.fineTypeIds == null) {
                this.$message.warning("请至少选择一种申报类型")
                return
            }
            if (this.tableList1.length == 0 && this.seeParams.fineTypeIds.indexOf("1") > -1) {
                this.$message.warning("请至少添加一位优秀巡察员")
                return
            }
            if (this.tableList2.length == 0 && this.seeParams.fineTypeIds.indexOf("2") > -1) {
                this.$message.warning("请至少添加一位优良巡察员")
                return
            }
            console.log("开始暂存")
            const loading = this.openLoading();
            let obj = {
                applyId: this.seeParams.applyId ? this.seeParams.applyId : '',
                batchId: this.seeParams.batchId,
                year: this.seeParams.year,
                '1': this.seeParams.fineTypeIds.indexOf("1") > -1 ? JSON.stringify(this.tableList1) : '',
                '2': this.seeParams.fineTypeIds.indexOf("2") > -1 ? JSON.stringify(this.tableList2) : '',
                fileKeyList: JSON.stringify(this.dialogArray),
            }
            saveFineApply(obj).then(res => {
                console.log(res)
                that.$message.success(res.msg)
                that.open = false
                that.getList();
                loading.close();
            })
        },
        /** 删除按钮操作 */
        handleDelete (row) {
            const ids = row.id || this.ids;
            this.$confirm('是否确认删除?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function () {
                return delCheckedFine({ id: ids });
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
            const queryParams = this.queryParams;
            this.$confirm('是否确认导出所有优秀员工申报名单 数据项?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function () {
                return exportCheckedFine(queryParams);
            }).then(response => {
                this.download(response.msg);
            }).catch(function () { });
        },
        /** 导入按钮操作 */
        openImportTable () {
            this.upload.title = "优秀员工申报名单 数据导入";
            this.upload.open = true;
        },
        /** 下载模板操作 */
        importTemplate (id, name) {
            downloadFile(id, "name")
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

/deep/ .el-dialog .el-form-item__label {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
}

/deep/ .el-dialog .expandCollapse {
    height: auto !important;
}

/deep/ .el-dialog .box .textBox {
    display: none;
}

.el-upload__tip {
    margin-top: 0;
    margin-bottom: 12px;
}

.boxList {
    display: flex;
    flex-wrap: wrap;
}

.boxList .uploader-file-icon {
    width: 32px;
    height: 32px;
}

.boxList .uploader-file-icon::before {
    font-size: 32px;
}

.boxList .el-icon-circle-close {
    font-size: 18px;
    position: absolute;
    right: -20px;
    top: -14px;
}

.boxList .el-col {
    text-align: center;
    margin: 10px 4px;

    div {
        margin: 4px;
    }
}
</style>