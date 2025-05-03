<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="180px">
          <el-form-item label="预编号：" prop="spendCode">
            <el-input v-model="queryParams.spendCode" style="width: 210px" placeholder="请输入预编号" clearable
                         @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="事实确认单编号：" prop="voucherCode">
            <el-input v-model="queryParams.voucherCode" style="width: 210px" placeholder="请输入事实确认单编号"
                      clearable   @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="问题底稿编号：" prop="matterCode">
            <el-input v-model="queryParams.matterCode" style="width: 210px" placeholder="请输入问题底稿编号"
                      clearable  @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="巡察年份：" prop="xunchaYear">
            <el-date-picker clearable style="width: 210px" v-model="queryParams.xunchaYear"
                            type="year" value-format="yyyy-mm-dd HH:mm:ss" placeholder="选择巡察年份"
                            @change="xunchaYearChange01">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="巡察批次：" prop="piciId">
            <el-select v-model="queryParams.piciId" style="width: 210px" placeholder="请选择巡察批次"
                       @change="handlePiciChange01">
              <el-option v-for="item in piciIdDataList01" :key="item.piciId" :label="item.piciName"
                         :value="item.piciId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="巡察组：" prop="groupName">
            <el-select v-model="queryParams.groupName" style="width: 210px" placeholder="请选择巡察组"
                       @change="handlexunchazuChange01">
              <el-option v-for="item in groupNameDataList01" :key="item.taskId" :label="item.groupName"
                         :value="item.taskId">
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
          <el-form-item label="承办人：" prop="agentUsername">
            <el-input v-model="queryParams.agentUsername" style="width: 210px" placeholder="请输入承办人" clearable
                 @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
      </div>
    </expand-collapse>
    <!-- 列表模块 -->
    <div class="container">
      <div style="float:right;margin:10px 0">
        <el-row :gutter="10" class="mb12 utlsBtn">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="default" icon="el-icon-upload2" size="mini" @click="openImportTable">导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="default" icon="el-icon-document" size="mini" @click="handleTemplate">模板下载</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
                       :disabled="ids.length === 0">导出
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDelete"
                       :disabled="ids.length === 0 || deleteShow">删除
            </el-button>
          </el-col>
        </el-row>
      </div>

      <el-table v-loading="loading" :data="courseVoucherList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column label="预编号" min-width="180px" align="left" prop="spendCode"/>
        <el-table-column label="事实确认单编号" min-width="160px" align="left" prop="voucherCode"/>
        <el-table-column label="问题底稿编号" min-width="140px" align="left" prop="matterCode">
          <template slot-scope="scope">
            <el-popover
              placement="top-start"
              title=""
              width="300"
              trigger="hover"
              :content="scope.row.matterCode">
              <span style="cursor: pointer;" slot="reference">{{scope.row.matterCode.length>10?scope.row.matterCode.substring(0,6)+'...':scope.row.matterCode}}</span>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="巡察年份" align="center" prop="xunchaYear" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.xunchaYear, '{y}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="巡察批次" align="center" prop="piciName"/>
        <el-table-column label="巡察组" align="center" prop="groupName"/>
        <el-table-column label="被巡察单位" min-width="120px" align="center" prop="orgName"/>
        <el-table-column label="承办人" align="center" prop="agentListName"/>
        <el-table-column label="当前状态" align="center" prop="statusName"/>
        <el-table-column label="操作" fixed="right" width="240" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
                       v-if="(scope.row.status === '1' || scope.row.status === '5') && $store.getters.name === scope.row.createdBy">
              修改
            </el-button>
            <el-button size="mini" type="text" @click="handleGenerateDraft(scope.row)">生成底稿</el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
                       v-if="scope.row.status === '1' && $store.getters.name === scope.row.createdBy">
              删除
            </el-button>
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">详情查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize" @pagination="getList"/>
      </div>
    </div>
    <!-- 添加或修改事实确认单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="180px" :disabled="disabled">
        <div class="dialog-title" style="margin-bottom:10px">
          <div style="width:50%;float:left">
              <span class="line"></span><span>基础信息</span>
          </div>
          <div style="width:50%;float:right;text-align:right">
              <el-button type="primary" size="mini" @click="handleGenerate">生成确认单</el-button>
          </div>
        </div>
        <el-form-item label="预编号：" prop="spendCode">
          <el-input v-model="form.spendCode"  placeholder="点击暂存或提交后自动生成" disabled/>
        </el-form-item>
        <el-form-item label="事实确认单编号：" prop="voucherCode">
          <el-input v-model="form.voucherCode" placeholder="组长审批通过后自动生成" disabled/>
        </el-form-item>
        <el-form-item label="问题底稿编号：" prop="matterCode">
          <el-input v-model="form.matterCode" placeholder="组长审批通过后自动生成" disabled/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="巡察年份：" prop="xunchaYear">
              <el-date-picker clearable size="small" :disabled="!!form.matterCode" style="width: 100%" v-model="form.xunchaYear"
                              type="year" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择巡察年份"
                              @change="xunchaYearChange">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次：" prop="piciId">
              <el-select v-model="form.piciId" style="width: 100%;" :disabled="!!form.matterCode" placeholder="请选择巡察批次"
                         @change="handlePiciChange">
                <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="巡察组：" prop="groupName">
              <el-select v-model="form.groupName" style="width: 100%" :disabled="!!form.matterCode" placeholder="请选择巡察组"
                         @change="handlexunchazuChange">
                <el-option v-for="item in groupNameDataList" :key="item.taskId" :label="item.groupName"
                           :value="item.taskId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align:right;margin-top:0.25rem;">
            <el-button type="primary" size="mini" @click="handleGenerateDetails">巡察组详情</el-button>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务时间：" prop="taskDateFrame">
              <el-date-picker :disabled="true" style="width:100%" v-model="form.taskDateFrame"
                              type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被巡察单位：" prop="orgName" >
              <el-select v-model="form.orgName" style="width:100%" placeholder="请选择被巡察单位"
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
        <el-row style="text-align:left">
          <el-col :span="12">
            <el-form-item label="事实领域：" prop="territoryIdList">
              <el-select v-model="form.territoryIdList" :multiple="true" style="width: 100%"
                         placeholder="请选择事实领域">
                <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="事实概述：" prop="truthOverview">
          <el-input type="textarea" :rows="3" placeholder="请输入事实概述" v-model="form.truthOverview"
                    maxlength="20000" :show-word-limit="true"  />
          <span
            style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：问题概述是指巡视报告中的面上问题，原则上应与巡视报告相应表述一致，根据实际可按一事一底稿或一个问题一底稿制作</span>
        </el-form-item>
        <el-form-item label="具体事实：" prop="specificTruth">
          <el-input type="textarea" :rows="3" placeholder="请输入具体事实" v-model="form.specificTruth"
                    maxlength="20000" :show-word-limit="true"  />
          <span
            style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：巡察了解情况是指巡视组工作人员了解核实问题的过程及事实描述，包括但不限于了解方式、责任主体、时间、地点、主要情节、造成影响等，特别是有关违纪违规问题的定性依据，以及与被巡视党组织主要负责人的沟通情况。</span>
        </el-form-item>
        <el-form-item label="承办人：" prop="agentIdList">
          <el-select v-model="form.agentIdList" class="angetSelect" :multiple="true" style="width: 100%"
                     placeholder="请选择承办人">
            <el-option v-for="item in createdByDataList" :key="item.id" :label="item.userName+'（'+item.userId+' - '+item.labelValue+'）'"
                       :value="item.id" :disabled="item.disabled">
            </el-option>
          </el-select>
          <span style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：承办人至少为两人。</span>
        </el-form-item>
        <div class="dialog-title" style="margin-bottom:10px">
            <span class="line"></span><span>附件材料</span>
          </div>
        <el-form-item label="事实确认单附件：" prop="fileId">
          <el-button type="text" :title="form.fileName" class="accessory" style="font-size: 12px; color: #1590ff">
            {{ form.fileName }}
          </el-button>
          <el-button type="primary" v-show="!formDisabled" size="mini" @click="uploadFileBtn()">
            <span v-if="form.fileId">重新上传</span>
            <span v-else>上传文件</span>
          </el-button>
          <el-button type="primary" size="mini" @click="onPreview()" v-if="form.fileId" :disabled="false">预览
          </el-button>
          <el-button type="primary" size="mini" @click="onDownload()" v-if="form.fileId" :disabled="false">下载
          </el-button>
        </el-form-item>
      </el-form>
      <!-- 流程历史信息 -->
      <all-flow-step ref="allFlowStepRef" :allFlowStep="process.allFlowStep" v-if="form.id"/>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关闭</el-button>
        <el-button type="primary" @click="handleDraft">暂存</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </div>
    </el-dialog>
    <!-- 生成确认单 -->
    <el-dialog :title="generateTitle" :visible.sync="generateShow" width="800px" append-to-body="append-to-body">
      <el-form ref="generateform" :rules="generateformRules" :model="generateform" label-width="160px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="巡察年份:" prop="xunchaYear">
              <el-date-picker clearable size="small" style="width: 210px"
                              v-model="generateform.xunchaYear" type="year" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择巡察年份"
                              @change="xunchaYearChange02">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次:" prop="piciId">
              <el-select v-model="generateform.piciId" style="width: 210px;float:right"
                         placeholder="请选择巡察批次" @change="handlePiciChange02"
                         :disabled="generateform.piciIdshow02">
                <el-option v-for="item in piciIdDataList02" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="巡察组:" prop="groupName">
          <el-select v-model="generateform.groupName" style="width: 100%" placeholder="请选择巡察组"
                     @change="handlexunchazuChange02">
            <el-option v-for="item in groupNameDataList02" :key="item.taskId" :label="item.groupName"
                       :value="item.taskId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题底稿:" prop="matterCode">
          <el-select v-model="generateform.matterCode" :multiple="true" style="width: 100%"
                     placeholder="请选择问题底稿">
            <el-option v-for="item in matterCodeDataList" :key="item.id" :label="item.spendCode"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="generateShow = false">关 闭</el-button>
        <el-button @click="clickGenerate" type="primary">生成</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改事实确认单上传附件对话框 -->
    <el-dialog :title="addUpload.title" :visible.sync="addUpload.open" width="400px" append-to-body>
      <el-upload ref="addUpload" :limit="1" accept=".xlsx, .xls" :headers="addUpload.headers"
                 :action="addUpload.url + '?updateSupport=' + addUpload.updateSupport" :disabled="addUpload.isUploading"
                 :on-progress="handleAddUploadProgress" :on-success="handleAddSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAddForm">确 定</el-button>
        <el-button @click="addUpload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看事实确认单对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="900px" append-to-body="append-to-body">
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <div class="dialog-title" style="margin-bottom:10px">
          <div style="width:50%;float:left">
              <span class="line"></span><span>基础信息</span>
          </div>
        </div>
        <el-form-item label="预编号：" prop="spendCode">
          <el-input v-model="form.spendCode" placeholder="点击暂存或提交后自动生成" :disabled="true"/>
        </el-form-item>
        <el-form-item label="事实确认单编号：" prop="voucherCode">
          <el-input v-model="form.voucherCode" placeholder="组长审批通过后自动生成" :disabled="true"/>
        </el-form-item>
        <el-form-item label="问题底稿编号：" prop="matterCode">
          <el-input v-model="form.matterCode" placeholder="组长审批通过后自动生成" :disabled="true"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="巡察年份：" prop="xunchaYear">
              <el-date-picker clearable size="small" style="width:100%" v-model="form.xunchaYear"
                              type="year" value-format="yyyy" placeholder="选择巡察年份" :disabled="true">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次：" prop="piciId">
              <el-select v-model="form.piciId" style="width: 100%" placeholder="请选择巡察批次"
                         :disabled="true" @change="handlePiciChange">
                <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="巡察组：" prop="groupName">
              <el-select v-model="form.groupName" style="width: 100%" placeholder="请选择巡察组"
                         :disabled="true">
                <el-option v-for="item in groupNameDataList" :key="item.value" :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align:right;margin-top:0.25rem;">
            <el-button type="primary" size="mini" @click="handleGenerateDetails">巡察组详情
            </el-button>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务时间：" prop="taskDateFrame">
              <el-date-picker style="width: 100%" v-model="form.taskDateFrame" type="daterange"
                              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :disabled="true">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被巡察单位：" prop="orgName" >
              <el-select v-model="form.orgName" style="width: 100%;" placeholder="请选择被巡察单位"
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
        <el-form-item label="事实领域：" prop="territoryIdList">
          <el-select v-model="form.territoryIdList" :multiple="true" style="width: 210px"
                     placeholder="请选择事实领域" :disabled="true">
            <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                       :value="item.dataCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="事实概述：" prop="truthOverview">
          <el-input type="textarea" :rows="3" placeholder="请输入事实概述" v-model="form.truthOverview"
                    maxlength="20000" :show-word-limit="true"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="具体事实：" prop="specificTruth">
          <el-input type="textarea" :rows="3" placeholder="请输入具体事实" v-model="form.specificTruth"
                    maxlength="20000" :show-word-limit="true"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="承办人：" prop="agentIdList">
          <el-select v-model="form.agentIdList" :multiple="true" style="width: 100%" placeholder="请选择承办人"
                     :disabled="true" @change="handleCreatedBy">
            <el-option v-for="item in createdByDataList" :key="item.id"  :label="item.userName+'（'+item.userId+' - '+item.labelValue+'）'"
                       :value="item.id" :disabled="item.disabled">
            </el-option>
          </el-select>
        </el-form-item>
        <div class="dialog-title" style="margin-bottom:10px">
            <span class="line"></span><span>附件材料</span>
          </div>
        <el-form-item label="事实确认单附件：" prop="matterCode">
          <el-button type="text" :title="form.fileName" class="accessory" style="font-size: 12px; color: #1590ff">
            {{ form.fileName }}
          </el-button>
          <el-button type="primary" size="mini" @click="onPreview()" v-if="form.fileId" :disabled="false">预览
                    </el-button>
          <el-button type="primary" size="mini" @click="onDownload()" v-if="form.fileId" :disabled="false">下载
          </el-button>
        </el-form-item>
      </el-form>
      <!-- 流程历史信息 -->
      <all-flow-step ref="allFlowStepRef" :allFlowStep="process.allFlowStep" v-if="form.id"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelForm">关闭</el-button>
        <el-button type="primary" @click="handleGenerateDraft(form)">生成底稿</el-button>
      </div>
    </el-dialog>
    <!-- 巡察过程_事实确认单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".doc, .docx" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入word格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
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
                <el-input style="width: 100%" v-model="formQuery.groupName"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="成员数：" prop="teamMembersCount">
                <el-input style="width: 100%" v-model="formQuery.teamMembersCount"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="巡察年份：" prop="xunchaYear">
                <el-input style="width: 100%" v-model="formQuery.xunchaYear"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="巡察批次：" prop="xunchaPici">
                <el-input style="width: 100%" v-model="formQuery.xunchaPici"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="关联巡察任务：" prop="taskName">
                <el-input style="width: 100%" v-model="formQuery.taskName"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="计划巡察时段：" prop="xunchaTimeSolt">
                <el-input style="width: 100%" v-model="formQuery.xunchaTimeSolt"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="23">
              <el-form-item label="被巡察单位：" prop="xunchaPici">
                <el-input style="width: 100%" v-model="formQuery.company"/>
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
    <!-- 文件上传组件 -->
    <uploadForm :dialogVisible="uploadFormShow" @file-success="fileSuccess" @close="uploadFormShow = false"
                singleFile ref="uploadForm"/>
    <!-- 导入 -->
    <uploadForm :dialogVisible="uploadOpen" @file-success="fileSuccess02" @close="uploadOpen = false"
                ref="uploadForm02"/>
    <!--
    创建页面提交选人组件，说明：
    1、title 弹框页面的标题
    2、processKey 流程名称（唯一）
    3、stepName 下一步处理人节点名称
    4、dialogVisible 布尔值，显示与隐藏
    5、@close 关闭选人页面回调事件
    6、@complete 点击确定之后回调，会返回一个 value 值
  -->
    <submit-process title="提交" ref="submitRef" :isNextUser="false" processKey="xuncha_course_voucher" stepName="组长审核"
                    :dialogVisible="submitProcessShow"
                    @close="submitProcessShow = false" @complete="saveFromData"/>

  </div>
</template>

<script>
  import {getToken} from "@/utils/auth";
  import {
    listCourseVoucher,
    getCourseVoucher,
    delCourseVoucher,
    addCourseVoucher,
    updateCourseVoucher,
    exportCourseVoucher,
    importTemplate,
    disableEnable,
    getXunchaGroupInfoList,
    getStartFlow,
    getUndertaker,
    finishFormTask,
    getGroupSelectData,
    getPapersSelectData,
    getPapersData,
    getGroupLeader,
    getReadWorldFile,
    exportWord,
    getDownloadWordTemplate,
    getPiciSelectData
  } from "@/api/course/courseVoucher";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from '@/utils/index';
  import {queryPiciList, queryLunciList, queryCompanyList} from "@/api/xuncha/common/base";
  import {listXunchaTaskInfo} from "@/api/xuncha/XunchaTaskInfo";
  import {selectDictData} from "@/api/xuncha/xunchaDictData";
  import SubmitProcess from "@/components/WorkFlow/submitProcess";
  import deepBtn from "@/components/WorkFlow/deepBtn";
  import {downloadFile, previewFile, downloadZipFile,} from "@/utils/uploadOperate";
  import uploadForm from "@/components/CommonUpload/uploadForm";
  import completeProcess from "@/components/WorkFlow/completeProcess";
  import {fetchWorkFlowInfo} from "@/utils/workflow";
  import AllFlowStep from "@/components/WorkFlow/AllFlowStep";

  export default {
    name: "CourseVoucher",
    components: {ExpandCollapse, SubmitProcess, deepBtn, uploadForm, completeProcess, AllFlowStep, getPiciSelectData},
    data() {
      return {
        // 是否显示上传文件按钮和重新上传文件按钮
        formDisabled: false,
        // 是否显示上传文件组件
        uploadFormShow: false,
        uploadOpen: false,
        // 是否显示创建页面提交选人组件
        submitProcessShow: false,
        // 是否显示修改页面提交选人组件
        applyShow: false,
        // 是否显示查看分组页面
        groupingShow: false,
        // 查看分组页面标题
        groupingTitle: "",
        formQuery: {},
        deleteShow: true,
        disabled: false,
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
        // 巡察过程_事实确认单表格数据
        courseVoucherList: [],
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
          headers: {Authorization: "Bearer " + getToken()},
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/course/courseVoucher/importData",
        },
        // 查询参数
        queryParams: {
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
          agentUsername: "",
          status: ''
        },
        // 巡查批次
        piciIdDataList01: [],
        //巡查组
        groupNameDataList01: [],
        //当前状态
        statusNameDataList: [
          {value: '1', label: '草稿'},
          {value: '2', label: '组长审核'},
          {value: '3', label: '巡察办审核'},
          {value: '4', label: '退回组长'},
          {value: '5', label: '退回组员'},
          {value: '6', label: '审核通过'},
        ],
        // 表单参数
        form: {
          taskId: '',
          xunchaYear: null,
          taskDateFrame: [],
          fileId: null
        },
        // 表单校验
        rules: {
          xunchaYear: [
            {required: true, message: '请选择巡察年份', trigger: 'change'}
          ],
          piciId: [
            {required: true, message: '请选择巡察批次！', trigger: 'change'}
          ],
          groupName: [
            {required: true, message: '请选择巡察组！', trigger: 'change'}
          ],
          // taskDateFrame: [
          //   {required: true, message: '请选择任务时段！', trigger: 'change'}
          // ],
          // orgName: [
          //   {required: true, message: '请选择被巡察单位！', trigger: 'change'}
          // ],
          territoryIdList: [
            {required: true, message: '请选择事实领域！', trigger: 'change'}
          ],
          truthOverview: [
            {required: true, message: '请填写事实概述！', trigger: 'blur'}
          ],
          specificTruth: [
            {required: true, message: '请填写具体事实！', trigger: 'blur'}
          ],
          agentIdList: [
            {required: true, message: '请填写承办人！', trigger: 'change'}
          ],
          fileId: [
            {required: true, message: '请上传事实确认单附件！', trigger: 'change'}
          ]
        },
        //
        generateformRules: {
          xunchaYear: [
            {required: true, message: '请选择巡察年份', trigger: 'change'}
          ],
          piciId: [
            {required: true, message: '请选择巡察批次！', trigger: 'change'}
          ],
          groupName: [
            {required: true, message: '请选择巡察组！', trigger: 'change'}
          ],
          matterCode: [
            {required: true, message: '请选择问题底稿！', trigger: 'change'}
          ],
        },
        // 巡查批次
        piciIdDataList: [],
        //巡查组
        groupNameDataList: [],
        // 被巡察单位
        orgIdDataList: [],
        // 事实领域
        fieldDataList: [],
        // 承办人
        createdByDataList: [],
        //新增事实确认单上传附件
        addUpload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: {Authorization: "Bearer " + getToken()},
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/course/courseVoucher/importData",
        },
        //生成确认单
        generateShow: false,
        generateTitle: '',
        generateform: {
          piciIdshow02: true,
          groupNameshow02: true,
        },
        // 巡查批次
        piciIdDataList02: [],
        //巡查组
        groupNameDataList02: [],
        // 问题底稿
        matterCodeDataList: [
          {value: '01', label: '底稿1'},
          {value: '02', label: '底稿2'},
        ],
        // 流程信息
        process: {}
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
    },
    activated(){
        let aaa = this.$route.query.openShow;
        if(aaa != null){
                if(aaa.showTab == "2"){
                this.open = aaa.openShow;
                this.title = aaa.title ;
                this.reset();
                let bbb = JSON.parse(localStorage.getItem("valaa"));
                console.log("bbbbbb",bbb);
                let array = [];
                if(bbb.coursePapersDomainList != null){
                  bbb.coursePapersDomainList.map((item,index)=>{
                    array.push(item.territoryCode);
                  })
                }
                this.form.territoryIdList = array ;
                if (bbb.xunchaYear != null) {
                   this.form.xunchaYear = bbb.xunchaYear.toString();
                 }
                 let newArray = [];
                 if (bbb.checkTime != null) {
                   newArray = bbb.checkTime.split(",");
                 }
                 this.form.taskDateFrame = newArray;
                 // 获得巡察批次下拉框数据
                 queryPiciList({year: bbb.xunchaYear.slice(0, 4)}).then((res) => {
                   if (res.data.length > 0) {
                     this.piciIdDataList = res.data;
                   } else {
                     this.piciIdDataList = [];
                   }
                 })
                 this.form.piciId = bbb.piciId ;
                 // 获得巡察组下拉框数据
                 listXunchaTaskInfo({piciId: bbb.piciId}).then((res) => {
                   if (res.rows.length > 0) {
                     this.groupNameDataList = res.rows;
                   } else {
                     this.groupNameDataList = [];
                   }
                 })
                 this.form.groupName = bbb.groupName;
                 let arrList = [];
                 if(bbb.orgName !=null){
                  arrList.push({
                    label:bbb.orgName,
                    value:bbb.orgName,
                  })
                 }
                 this.form.orgName = bbb.orgName;
                // // 获得承办人下拉框数据
                // getUndertaker({taskId: bbb.taskId, id: bbb.id}).then((res) => {
                //    createdByDataList.push({
                //      userId: res.data.currentBy.userId,
                //      userName: res.data.currentBy.userName,
                //      disabled: true,
                //      id: res.data.currentBy.userId,
                //    })
                //     createdByDataList.push(...res.data.undertake);
                //     this.createdByDataList = createdByDataList;
                //     this.form.agentIdList = bbb.agentIdList ;
                // })
                // this.form = bbb ;
                this.form.truthOverview = bbb.issueAnOverview;
                this.form.specificTruth = bbb.factDescription;
                this.form.matterCode = bbb.matterCode;
                localStorage.removeItem("valaa");
            }
        }
    },
    created() {
      this.getList();
    },
    methods: {
        // 模板下载
        handleTemplate(){
          getDownloadWordTemplate().then(res => {
              this.download(res.msg);
            // downloadFile(res.msg);
          })
        },

        /** 导入按钮操作 */
        openImportTable() {
            // this.upload.title = "巡察过程_问题底稿数据导入";
            this.uploadOpen = true;
        },
        fileSuccess02(files) {
            this.msgSuccess("上传成功");
            let fileId = files[0].result.data.fileId;
            let name = files[0].result.data.fileName;
            let fileName = name.substring(0, name.lastIndexOf("."));
            getReadWorldFile({id:fileId}).then((res)=>{
                this.open = true;
                this.title = "新增事实确认单";
                if (res.data.xunchaYear != null) {
                   res.data.xunchaYear = res.data.xunchaYear.toString();
                 }
                 let newArray = [];
                 if (res.data.taskDateFrame != null) {
                   newArray = res.data.taskDateFrame.split(",");
                 }
                 res.data.taskDateFrame = newArray;
                 let createdByDataList = [];
                 // 获得巡察批次下拉框数据
                 queryPiciList({year: res.data.xunchaYear.slice(0, 4)}).then((res) => {
                   if (res.data.length > 0) {
                     this.piciIdDataList = res.data;
                   } else {
                     this.piciIdDataList = [];
                   }
                 })

                 // 获得巡察组下拉框数据
                 listXunchaTaskInfo({piciId: res.data.piciId}).then((res) => {
                   if (res.rows.length > 0) {
                     this.groupNameDataList = res.rows;
                   } else {
                     this.groupNameDataList = [];
                   }
                 })

                 // 获得承办人下拉框数据
                 getUndertaker({taskId: res.data.taskId, id: res.data.id}).then((res) => {
                   createdByDataList.push({
                     userId: res.data.currentBy.userId,
                     userName: res.data.currentBy.userName,
                     disabled: true,
                     id: res.data.currentBy.userId,
                     labelValue:res.data.currentBy.labelValue,
                   })
                    createdByDataList.push(...res.data.undertake);
                    this.createdByDataList = createdByDataList;
                    this.form.agentIdList = [res.data.currentBy.userId]
                })
                  this.form = res.data;
                // data.checkTime = [];
                // data.deputyHead = '';
                // data.groupName = '';
                // data.leader = '';
                // data.matterCode = '';
                // data.orgName = '';
                // data.userName ='';
                // // data.
                // this.form = data;
            })
        },
      // 点击上传文件按钮
      uploadFileBtn() {
        this.uploadFormShow = true;
      },
      // 文件上传成功回调方法
      fileSuccess(files) {
        this.msgSuccess("上传成功");
        this.form.fileId = files[0].result.data.fileId;
        let name = files[0].result.data.fileName;
        this.form.name = name;
        this.form.fileName = name.substring(0, name.lastIndexOf("."));
        // // 文件上传后解析文件
        // analysisExcelRow(this.form.fileId).then((res) => {
        //     if (res.data && !this.form.domainNum) {
        //         this.form.domainNum = res.data;
        //     }
        // });
      },
      // 预览文件
      onPreview() {
        previewFile(this.form.fileId);
      },
      // 文件下载
      onDownload(fileId, caseId) {
        downloadFile(this.form.fileId);
      },
      // 根据巡察年份获取批次信息
      xunchaYearChange01(date) {
        console.log("date1111", date);
        this.piciIdDataList01 = [];
        this.queryParams.xunchaYear = date.substring(0, 5) + "01" + date.substring(7, date.length);
        queryPiciList({year: date}).then((res) => {
          if (res.data.length > 0) {
            this.piciIdDataList01 = res.data;
          } else {
            this.piciIdDataList01 = [];
          }
          this.queryParams.piciIdshow01 = false;
        })
      },
      // 根据批次获得巡察任务信息
      handlePiciChange01(val) {
        this.groupNameDataList01 = [];
        listXunchaTaskInfo({piciId: val}).then((res) => {
          if (res.rows.length > 0) {
            this.groupNameDataList01 = res.rows;
          } else {
            this.groupNameDataList01 = [];
          }
          this.queryParams.groupNameshow01 = false;
        })
      },
      // 根据巡察组获取关联
      handlexunchazuChange01(val) {
        let newData = this.groupNameDataList01;
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
      // 根据巡察年份获取关联
      xunchaYearChange02(date) {
        this.piciIdDataList02 = [];
        this.groupNameDataList02 = [];
        this.matterCodeDataList = []
        this.generateform.piciId = null
        this.generateform.groupName = null
        this.generateform.taskId = null
        this.generateform.matterCode = []
        getPiciSelectData({year: date.slice(0, 4)}).then((res) => {
          if (res.data.length > 0) {
            this.piciIdDataList02 = res.data;
          } else {
            this.piciIdDataList02 = [];
          }
          this.generateform.piciIdshow02 = false;
        })
      },
      //根据巡察批次获取关联
      handlePiciChange02(val) {
        this.groupNameDataList02 = [];
        this.matterCodeDataList = []
        this.generateform.groupName = null
        this.generateform.taskId = null
        this.generateform.matterCode = []
        // 获得巡察组下拉框数据
        getGroupSelectData({piciId: val}).then((res) => {
          this.groupNameDataList02 = res.data;
        })
      },
      //根据巡察组获取关联
      handlexunchazuChange02(val) {
        this.matterCodeDataList = []
        this.generateform.matterCode = []
        // 获得问题底稿数据
        getPapersSelectData({taskId: val}).then((res) => {
          this.matterCodeDataList = res.data;
        })
      },
      /** 新增确认单 */
      // 根据巡察年份获取关联
      xunchaYearChange(date) {
        let dataList = [];
        this.form.piciId = null;
        this.form.groupName = null;
        this.form.taskDateFrame = [];
        this.form.orgName = null;
        getPiciSelectData({year: date.slice(0, 4)}).then((res) => {
          if (res.data.length > 0) {
            dataList = res.data;
          } else {
            dataList = [];
          }
          this.piciIdDataList = dataList;
        })
      },
      //根据巡察批次获取关联
      handlePiciChange(val,fun) {
        let dataList = [];
        if (!fun) {
          this.form.groupName = null;
        }
        this.form.taskDateFrame = [];
        this.form.orgName = null;
        getGroupSelectData({piciId: val}).then((res) => {
          if (res.data.length > 0) {
            dataList = res.data;
          } else {
            dataList = [];
          }
          this.groupNameDataList = dataList;
          if (fun) {
            fun();
          }
        })
      },
      //根据巡察组获取关联
      handlexunchazuChange(val) {
        this.form.taskDateFrame = [];
        this.form.orgName = null;
        let newData = this.groupNameDataList;
        let newObject = {};
        let orgIdDataList = [];
        let createdByDataList = [];
        if (newData != null) {
          newData.map((item, index) => {
            if (item.taskId == val) {
              newObject = item;
              this.form.taskDateFrame = [item.startTime, item.endTime];
              orgIdDataList.push({
                value: item.companyNames,
                label: item.companyNames,
              })
              this.orgIdDataList = orgIdDataList;
              this.form.orgName = item.companyNames;
              this.form.taskId = item.taskId;
              getUndertaker({taskId: this.form.taskId}).then((res) => {
                console.log("reseeeee",res);
                createdByDataList.push({
                  userId: res.data.currentBy.userId,
                  userName: res.data.currentBy.userName,
                  disabled: true,
                  id: res.data.currentBy.userId,
                  labelValue:res.data.currentBy.labelValue,
                })
                createdByDataList.push(...res.data.undertake);
                this.createdByDataList = createdByDataList;
                this.form.agentIdList = [res.data.currentBy.userId]
              })
            }
          })
        }
      },
      //选择承办人
      handleCreatedBy(val) {
        console.log("val11111", val);
      },
      /** 查询巡察过程_事实确认单列表 */
      getList() {
        this.loading = true;
        listCourseVoucher(this.queryParams).then(res => {
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
          this.total = Number(res.total);
          this.loading = false;
        });
        selectDictData({dictType: 'course_voucher_territory'}).then((res) => {
          this.fieldDataList = res.data;
        })

      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
        this.specificTruth = "";
        this.truthOverview = "";
      },
      // 取消查看详情
      cancelForm() {
        this.isOpenQuery = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          spendCode: null,
          voucherCode: null,
          matterCode: null,
          xunchaYear: null,
          piciId: null,
          piciName: null,
          taskId: null,
          groupName: null,
          taskDateFrame: null,
          orgId: null,
          orgName: null,
          truthOverview: null,
          specificTruth: null,
          fileId: null,
          fileName: null,
          status: "0",
          statusName: null,
          leadShow: null,
          createdBy: [],
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null,
          territoryIdList: [],
          agentIdList: [],
        };
        this.resetForm("form");
      },
      // 搜索按钮操作
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      // 重置按钮操作
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.taskId = null
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length;

        this.deleteShow = false
        // 如果选中的 ids 中含有状态不等于 1 那么就不能删除
        if (this.ids) {
          this.ids.forEach(id => {
            this.courseVoucherList.forEach(voucher => {
              if (voucher.id === id) {
                if (voucher.status !== '1') {
                  this.deleteShow = true
                }
              }
            })
          })
        } else {
          this.deleteShow = false
        }
      },
      // 新增按钮操作
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.form.id = null;
        this.title = "新增事实确认单";
        this.specificTruth = "";
        this.truthOverview = "";
      },
      // 修改按钮操作
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids
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
          // 获得巡察批次下拉框数据
          if (res.data.xunchaYear) {
            getPiciSelectData({year: res.data.xunchaYear.slice(0, 4)}).then((res) => {
              if (res.data.length > 0) {
                this.piciIdDataList = res.data;
              } else {
                this.piciIdDataList = [];
              }
            })
          }
          // 获得巡察组下拉框数据
          getGroupSelectData({piciId: res.data.piciId}).then((res) => {
            if (res.data.length > 0) {
              this.groupNameDataList = res.data;
            } else {
              this.groupNameDataList = [];
            }
          })
          // 获得承办人下拉框数据
          getUndertaker({taskId: res.data.taskId, id: res.data.id}).then((res) => {
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
          this.form = res.data;
          this.open = true;
          this.title = "修改事实确认单";
        });
        // debugger
        // 获得流程信息
        fetchWorkFlowInfo(id, "", (res) => {
          this.process = res;
        }, 1)
      },
      /**详情按钮*/
      handleQueryOption(row) {
        this.reset();
        const id = row.id || this.ids;
        let piciIdDataList = [];
        getCourseVoucher(id).then(res => {
          console.log("resaaaa", res);
          if (res.data.xunchaYear != null) {
            res.data.xunchaYear = res.data.xunchaYear.toString();
          }
          let newArray = [];
          if (res.data.taskDateFrame != null) {
            newArray = res.data.taskDateFrame.split(",");
          }
          piciIdDataList.push({
            piciId: res.data.piciId,
            piciName: res.data.piciName,
          })
          this.piciIdDataList = piciIdDataList;
          res.data.taskDateFrame = newArray;
          let createdByDataList = [];
          getUndertaker({taskId: res.data.taskId, id: res.data.id}).then((res) => {
            console.log("res2222", res);
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
          this.form = res.data;
          this.isOpenQuery = true;
          this.title = "查看事实确认单";
        });
        // 获得流程信息
        fetchWorkFlowInfo(id, "", (res) => {
          this.process = res;
        }, 1)
      },
      /**生成底稿 */
      handleGenerateDraft(row) {
        console.log(row);
            this.isOpenQuery = false ;
            let val  = {} ;
            val.openShow = true ;
            val.showTab = "1";
            val.title = '新增问题底稿';
            let aaa = JSON.stringify(row) ;
            localStorage.setItem("valbb",aaa);
            this.$router.push({path:'/papers',query:{openShow:val}})
      },
      /**生成确认单弹窗 */
      handleGenerate() {
        this.generateShow = true;
        this.generateTitle = "选择问题底稿"
        // 页面数据置空
        this.generateform = {
          xunchaYear: null,
          piciId: null,
          groupName: null,
          matterCode: []
        }
        this.piciIdDataList02 = []
        this.groupNameDataList02 = []
        this.matterCodeDataList = []
      },
      /**生成确认单 */
      clickGenerate() {
        // 表单验证
        this.$refs["generateform"].validate(valid => {
          if (valid) {
            getPapersData({papersIdArr: this.generateform.matterCode}).then(res => {
              console.log("res1111", res);
              if (res.data) {
                this.specificTruth += res.data.specificTruth;
                this.truthOverview += res.data.truthOverview;
                this.form.specificTruth = this.specificTruth;
                this.form.truthOverview = this.truthOverview;
                this.form.matterCode = res.data.matterCode;
                if (this.form.matterCode) { 
                  this.form.xunchaYear = this.generateform.xunchaYear;
                  this.form.piciId = this.generateform.piciId;
                  this.form.groupName = this.generateform.groupName;
                  this.piciIdDataList = this.piciIdDataList02;
                  this.handlePiciChange(this.form.piciId, () => {
                    console.log(this.form.groupName);
                    this.handlexunchazuChange(this.form.groupName);
                  });
                }
                this.generateShow = false;
              }
            })
          }
        })
      },
      /**巡察组详情 */
      handleGenerateDetails() {
        if (this.form.taskId) {
          getXunchaGroupInfoList({taskId: this.form.taskId}).then((res) => {
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
      /**暂存 */
      handleDraft() {
        // 表单验证
        // this.$refs["form"].validate(valid => {
          // if (valid) {
            // 判如果承办人小于两个，则不能提交
            // if (this.form.agentIdList.length <= 1) {
            //   this.msgError("承办人至少为两人");
            //   return
            // }
            if (this.form.id) {
              // 修改页面的暂存是修改数据
              if(this.form.xunchaYear != null){
                  this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
                  this.form.taskDateFrame = this.form.taskDateFrame.toString();
              }
              const loading = this.openLoading();
              updateCourseVoucher(this.form).then((res) => {
                loading.close();
                this.open = false;
                this.getList();
                this.specificTruth = "";
                this.truthOverview = "";
              })
            } else {
              // 创建页面的暂存是创建数据
              if(this.form.xunchaYear != null){
                  this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
                  this.form.taskDateFrame = this.form.taskDateFrame.toString();
              }
              const loading = this.openLoading();
              getStartFlow(this.form).then((res) => {
                loading.close();
                this.open = false;
                this.getList();
                this.specificTruth = "";
                this.truthOverview = "";
              })
            }
          // }
        // })
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            // 判如果承办人小于两个，则不能提交
            if (this.form.agentIdList.length <= 1) {
              this.msgError("承办人至少为两人");
              return
            }
            // 【自定义选人】组员发起流程选人为组长/副组长
            getGroupLeader({taskId: this.form.taskId}).then(res => {
              const nextUser = []
              res.data.forEach(item => {
                nextUser.push({
                  id: item.nextUserId,
                  name: item.nextUserName
                })
              })
              this.$refs.submitRef.init(nextUser);
              this.submitProcessShow = true
            })
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除此数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delCourseVoucher(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        exportWord({ids: this.ids}).then(res => {
          // this.download(res.msg);
          downloadFile(res.msg);
        })
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg);
        });
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        console.log("aaaaaaaa",response);
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
      // 上传文件附件
      submitUpload() {
        this.addUpload.title = "上传附件";
        this.addUpload.open = true;
      },
      // 文件上传中处理
      handleAddUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleAddSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "上传结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },
      // 提交上传文件
      submitAddForm() {
        this.$refs.upload.submit();
      },
      // 创建页面/修改页面 - 提交页面 - 点击发送
      saveFromData(val) {
        console.log("val1111", val);
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id) {
              // 修改页面，推动流程，先暂存数据
              this.open = true;
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.taskDateFrame = this.form.taskDateFrame.toString();
              updateCourseVoucher(this.form).then(response => {
                if (response.code === 200) {
                  // 获得流程信息 taskId
                  fetchWorkFlowInfo(this.form.id, "", (res) => {
                    // 任务ID
                    let data = {
                      taskId: res.curtaskId,
                      variables: val.variables
                    }
                    // 调用推动流程接口
                    finishFormTask(data).then(res => {
                      this.msgSuccess("已提交");
                      this.open = false;
                      this.submitProcessShow = false;
                      this.getList();
                      this.specificTruth = "";
                      this.truthOverview = "";
                    })
                  }, 1)
                }
              });
            } else {
              // 新增页面，发起流程
              this.form.nextHandleUsername = JSON.parse(val.variables).nextDealUser;
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.taskDateFrame = this.form.taskDateFrame.toString();
              const loading = this.openLoading();
              getStartFlow(this.form).then((res) => {
                loading.close();
                this.submitProcessShow = false;
                this.open = false;
                this.getList();
                this.specificTruth = "";
                this.truthOverview = "";
              })
            }
          }
        })
      },
      // 初始化流程信息
      initProcess(value) {
        console.log('流程信息')
        console.log(value)
        // 区分暂存和流程页面，是否禁用表单
        console.log(value.taskDefinitionKey)
      },
      // 获得业务主键
      getBusinessKey() {
        // 返回业务主键
        if (this.form.id) {
          return this.form.id;
        } else {
          return false;
        }
      },
      // 点击暂存按钮
      saveBtn() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            this.$confirm("是否确定暂存?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              // 暂存数据
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.taskDateFrame = this.form.taskDateFrame.toString();
              updateCourseVoucher(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("已暂存");
                  this.open = false;
                  this.getList();
                }
              });
            });
          }
        });
      },
      // 关闭页面返回 close 事件
      handleClose() {
        // this.form = {};
        this.$refs["form"].clearValidate();
        this.$emit("close");
      },
      // 点击下一步前的操作
      beforeComplete(fn) {
        fn();
        if (!this.formDisabled) {
          // 保存数据，暂存的时候才需要保存
          const loading = this.openLoading();
          this.$refs["form"].validate((valid) => {
            if (valid) {
              // 暂存数据
              updateCourseVoucher(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("已暂存");
                  this.open = false;
                  this.getList();
                  fn();
                }
              });
            }
          });
        } else {
          fn();
        }
      },
    }
  };
</script>


<style lang="scss" scoped>

  .app-container {
    background: #F5F7F9;
  }

  // /deep/.el-input.is-disabled .el-input__inner{
  //     background-color: #F5F7FA !important;
  // }

  /deep/.el-range-editor.is-disabled {
      background-color:#fff;
  }

  /deep/ .angetSelect > .el-select__tags > span > span:nth-child(1) > .el-tag__close.el-icon-close {
    display: none !important;
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

