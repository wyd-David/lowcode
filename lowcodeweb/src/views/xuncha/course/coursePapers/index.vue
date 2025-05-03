<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="180px">
          <el-form-item label="预编号：" prop="spendCode">
            <el-input v-model="queryParams.spendCode" style="width: 210px" placeholder="请输入预编号" clearable size="small"
                      type="text" @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="事实确认单编号：" prop="voucherCode">
            <el-input v-model="queryParams.voucherCode" style="width: 210px" placeholder="请输入事实确认单编号" clearable
                      size="small"
                      type="text" @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="问题底稿编号：" prop="matterCode">
            <el-input v-model="queryParams.matterCode" style="width: 210px" placeholder="请输入问题底稿编号" clearable
                      size="small"
                      type="text" @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="巡察年份：" prop="xunchaYear">
            <el-date-picker clearable size="small" style="width: 210px" v-model="queryParams.xunchaYear"
                            type="year" value-format="yyyy-mm-dd HH:mm:ss" placeholder="选择巡察年份"
                            @change="xunchaYearChange01">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="巡察批次：" prop="piciId">
            <el-select v-model="queryParams.piciId" style="width: 210px" placeholder="请选择巡察批次"
                       @change="handlePiciChange01">
              <el-option v-for="item in piciIdDataList01"
                         :key="item.piciId"
                         :label="item.piciName"
                         :value="item.piciId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="巡察组：" prop="groupName">
            <el-select v-model="queryParams.groupName" style="width: 210px" placeholder="请选择巡察组"
                       @change="handlexunchazuChange01">
              <el-option v-for="item in groupNameDataList01"
                         :key="item.taskId"
                         :label="item.groupName"
                         :value="item.taskId">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="问题领域：" prop="territoryCode">
            <el-select v-model="queryParams.territoryCode" style="width: 210px" placeholder="请选择问题领域">
              <el-option v-for="item in fieldDataList"
                         :key="item.dataCode"
                         :label="item.dataName"
                         :value="item.dataCode">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="当前状态：" prop="status">
            <el-select v-model="queryParams.status" style="width: 210px" placeholder="请选择当前状态">
              <el-option v-for="item in statusNameDataList"
                         :key="item.value"
                         :label="item.label"
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
    <div class="container">
      <div style="float:right;margin:10px 0">
        <el-row :gutter="10" class="mb12 utlsBtn">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="default" icon="el-icon-upload2" size="mini" @click="openImportTable">导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="default" icon="el-icon-document" size="mini" @click="handleTemplate">模板下载</el-button>
          </el-col>
          <!-- v-hasPermi="['system:papers:importData']" -->
          <el-col :span="1.5">
            <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            >导出
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDelete"
                       :disabled="deleteShow">删除
            </el-button>
          </el-col>
        </el-row>
      </div>
      <el-table v-loading="loading" :data="papersList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column label="预编号" min-width="180px" align="left" prop="spendCode"/>
        <el-table-column label="问题底稿编号" min-width="160px" align="left" prop="matterCode"/>
        <el-table-column label="事实确认单编号" min-width="140px" align="left" prop="voucherCode">
          <template slot-scope="scope">
            <el-popover
              placement="top-start"
              title=""
              width="300"
              trigger="hover"
              :content="scope.row.voucherCode">
              <span style="cursor: pointer;" slot="reference">{{scope.row.voucherCode.length>10?scope.row.voucherCode.substring(0,6)+'...':scope.row.voucherCode}}</span>
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
        <el-table-column label="问题领域" align="center" prop="domains"/>
        <el-table-column label="当前状态" align="center" prop="statusName"/>
        <el-table-column label="操作" fixed="right" width="260" align="center" class-name="small-padding fixed-width" >
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleConfirmation(scope.row)">生成事实确认单</el-button>
            <!-- <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-if="scope.row.status != '2'"
                            v-hasPermi="['system:papers:edit']">修改
                        </el-button> -->
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
                       v-if="(scope.row.status === '1' || scope.row.status === '5') && $store.getters.name === scope.row.createdBy">
              修改
            </el-button>
            <!-- <el-button size="mini" type="text" @click="handleDelete(scope.row)" v-if="scope.row.status != '2'"
                            v-hasPermi="['system:papers:remove']">删除
                        </el-button> -->
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
    <!-- 添加或修改巡察过程_问题底稿对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="180px" :disabled="disabled">
        <div class="dialog-title" style="margin-bottom:10px">
          <div style="width:50%;float:left">
            <span class="line"></span><span>基础信息</span>
          </div>
          <div style="width:50%;float:right;text-align:right">
            <el-button type="primary" size="mini" @click="handleManuscript">生成底稿</el-button>
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
              <el-date-picker clearable size="small" :disabled="!!form.voucherCode" style="width: 100%" v-model="form.xunchaYear"
                              type="year" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择巡察年份"
                              @change="xunchaYearChange">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次：" prop="piciId">
              <el-select v-model="form.piciId" style="width: 100%" :disabled="!!form.voucherCode" placeholder="请选择巡察批次"
                         @change="handlePiciChange">
                <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="19">
            <el-form-item label="巡察组：" prop="groupName">
              <el-select v-model="form.groupName" style="width: 100%" :disabled="!!form.voucherCode" placeholder="请选择巡察组"
                         @change="handlexunchazuChange">
                <el-option v-for="item in groupNameDataList"
                           :key="item.taskId"
                           :label="item.groupName"
                           :value="item.taskId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="text-align:right;margin-top:0.25rem;">
            <el-button type="primary" size="mini" @click="handleGenerateDetails">巡察组详情</el-button>
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
              <el-select v-model="form.orgName" style="width: 100%" placeholder="请选择被巡察单位"
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
              <el-input v-model="form.head" type="text" maxlength="200" style="width: 100%;" placeholder="请输入分管领导"/>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="dialog-title" style="margin-bottom:10px">
          <span class="line"></span><span>问题信息</span>
        </div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题领域：" prop="territoryCode">
              <el-select v-model="form.territoryCode" :multiple="true" style="width: 100%" placeholder="请选择问题领域"
                         @change="selectTerritoryCode">
                <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="问题来源：" prop="problemSourceCode">
              <el-select v-model="form.problemSourceCode" :multiple="true" style="width: 100%;" placeholder="请选择问题来源"
                         @change="selectProblemSourceCode">
                <el-option v-for="item in problemSourceDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="其他：" prop="note" v-if="noteShow">
          <el-input v-model="form.note" type="text" maxlength="200" :show-word-limit="true" placeholder="请输入其他"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题发生单位层级：" prop="hierarchyObj">
              <el-select v-model="form.hierarchyObj" style="width: 100%;" value-key="dataCode" placeholder="请选择问题发生单位层级">
                <el-option v-for="item in hierarchyDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类别：" prop="category">
              <el-input v-model="form.category" type="text" maxlength="200" :show-word-limit="true" style="width: 100%;"
                        placeholder="请输入类别"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="重点关注问题清单：" prop="priorityListing">
          <el-input v-model="form.priorityListing" type="text" maxlength="200" :show-word-limit="true"
                    placeholder="请输入重点关注问题清单"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="班子问题还是部门问题：" prop="isDepartmentOfProblem">
              <el-select v-model="form.isDepartmentOfProblem" style="width: 100%;" placeholder="请选择班子问题还是部门问题">
                <el-option v-for="item in problemDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被巡察单位类型：" prop="unitUnderInspectionTypeObj">
              <el-select v-model="form.unitUnderInspectionTypeObj" value-key="dataCode" style="width: 100%;" placeholder="请选择被巡察单位类型">
                <el-option v-for="item in unitUnderDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="问题分类：" prop="questionClassificationObj">
          <el-select v-model="form.questionClassificationObj" value-key="dataCode" style="width: 100%" placeholder="请选择问题分类">
            <el-option v-for="item in questionDateList" :key="item.dataCode" :label="item.dataName"
                       :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题概述：" prop="issueAnOverview">
          <el-input type="textarea" :rows="3" placeholder="请输入问题概述" v-model="form.issueAnOverview"
                    maxlength="20000" :show-word-limit="true"/>
          <span
            style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：问题概述是指巡察组巡察工作期间发现问题的简要客观描述，不超过20000字。</span>
        </el-form-item>
        <el-form-item label="查证事实描述：" prop="factDescription">
          <el-input type="textarea" :rows="3" placeholder="请输入查证事实描述" v-model="form.factDescription"
                    maxlength="20000" :show-word-limit="true"/>
          <span
            style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：查证事实描述是指巡察组了解核实问题的方式、过程及主要事实描述， 包括但不限于巡察组工作人员了解核实问题的时间及方式，问题涉及的主体、时间、地点、违规违纪的主要情节等，不超过20000字。</span>
        </el-form-item>
        <el-form-item label="问题剖析：" prop="problemAnalysis">
          <el-input type="textarea" :rows="3" placeholder="请输入问题剖析" v-model="form.problemAnalysis"
                    maxlength="20000" :show-word-limit="true"/>
          <span
            style="font-size:14px;color:#ffc707;line-height:0 !important">
                        温馨提示：
                        <p>1.从政治角度看（思想、责任、作风、体制机制、执行）；</p>
                        <p>2.从三个点看（风险点：制度缺失；问题点：有制度执行缺失；出血点：有制度执行缺失且存在廉洁风险）</p>
                    </span>
        </el-form-item>
        <el-form-item label="支撑材料：" prop="materialSupport">
          <el-input type="textarea" :rows="3" placeholder="请输入支撑材料" v-model="form.materialSupport"
                    maxlength="20000" :show-word-limit="true"/>
          <span
            style="font-size:14px;color:#ffc707;line-height:0 !important">
                        温馨提示：
                        <p>1.········报告第**-**页（具体材料附后</p>
                        <p>2.········谈话记录第**-**页（具体材料附后）</p>
                        <p>3.········</p>
                        <el-button type="primary" size="mini" @click="uploadFileBtn01()">
                            <span v-if="!form.coursePapersFilesList">上传文件</span>
                            <span v-else>重新上传</span>
                        </el-button>
                        <div v-if="form.coursePapersFilesList" v-for="item in form.coursePapersFilesList"
                             :key="item.fileId">
                            <el-button type="text" :title="item.fileName" class="accessory"
                                       style="font-size: 12px; color: #1590ff">
                                {{ item.fileName }}
                            </el-button>
                            <el-button type="primary" size="mini" @click="onPreview01(item.fileId)" v-if="item.fileId"
                                       :disabled="false">预览
                            </el-button>
                            <el-button type="primary" size="mini" @click="onDownload01(item.fileId)" v-if="item.fileId"
                                       :disabled="false">下载
                            </el-button>
                        </div>
                    </span>
        </el-form-item>
        <el-form-item label="承办人：" prop="userId">
          <el-select v-model="form.userId" :multiple="true" class="angetSelect" style="width: 100%" placeholder="请选择承办人"
                     @change="handleUserId">
            <el-option v-for="item in createdByDataList"
                       :key="item.id"
                       :label="item.userName+'（'+item.userId+' - '+item.labelValue+'）'"
                       :value="item.id"
            >
            </el-option>
          </el-select>
          <span style="font-size:14px;color:#ffc707;line-height:0 !important">温馨提示：承办人至少为两人。</span>
        </el-form-item>
        <el-form-item label="分管副组长：" prop="deputyLeader">
          <el-select v-model="form.deputyLeader" style="width: 100%" placeholder="请选择分管副组长">
            <el-option v-for="item in createdByDataList"
                       :key="item.id"
                       :label="item.userName+'（'+item.userId+'）'"
                       :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组长：" prop="leader">
          <el-select v-model="form.leader" style="width: 100%" placeholder="请选择组长">
            <el-option v-for="item in createdByDataList"
                       :key="item.id"
                       :label="item.userName+'（'+item.userId+'）'"
                       :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="dialog-title" style="margin-bottom:10px">
          <span class="line"></span><span>附件材料</span>
        </div>
        <el-form-item label="底稿附件：" prop="fileId">
          <el-button type="text" :title="form.fileName" class="accessory" style="font-size: 12px; color: #1590ff">
            {{ form.fileName }}
          </el-button>
          <el-button type="primary" size="mini" @click="uploadFileBtn()">
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

    <!-- 查看巡察过程_问题底稿对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="900px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="180px" :disabled="disabled">
        <div class="dialog-title" style="margin-bottom:10px">
          <div style="width:50%;float:left">
            <span class="line"></span><span>基础信息</span>
          </div>
        </div>
        <el-form-item label="预编号：" prop="spendCode">
          <el-input v-model="form.spendCode" placeholder="请输入预编号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="事实确认单编号：" prop="voucherCode">
          <el-input v-model="form.voucherCode" placeholder="请输入事实确认单编号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="问题底稿编号：" prop="matterCode">
          <el-input v-model="form.matterCode" placeholder="请输入问题底稿编号" :disabled="true"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="巡察年份" prop="xunchaYear">
              <el-date-picker clearable size="small" style="width: 100%" v-model="form.xunchaYear" type="year"
                              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择巡察年份" :disabled="true">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次:" prop="piciId">
              <el-select v-model="form.piciId" style="width: 100%;" placeholder="请选择巡察批次"
                         :disabled="true" @change="handlePiciChange">
                <el-option v-for="item in piciIdDataList" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="19">
            <el-form-item label="巡察组：" prop="groupName">
              <el-select v-model="form.groupName" style="width: 100%" placeholder="请选择巡察组"
                         @change="handlexunchazuChange" :disabled="true">
                <el-option v-for="item in groupNameDataList"
                           :key="item.taskId"
                           :label="item.groupName"
                           :value="item.taskId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="text-align:right;margin-top:0.25rem;">
            <el-button type="primary" size="mini" @click="handleGenerateDetails">巡察组详情</el-button>
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
              <el-input v-model="form.head" type="text" maxlength="200" :show-word-limit="true" style="width: 100%;"
                        placeholder="请输入分管领导"/>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="dialog-title" style="margin-bottom:10px">
          <span class="line"></span><span>问题信息</span>
        </div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题领域：" prop="territoryCode">
              <el-select v-model="form.territoryCode" :multiple="true" style="width: 100%;" placeholder="请选择问题领域"
                         :disabled="true">
                <el-option v-for="item in fieldDataList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="问题来源：" prop="problemSourceCode">
              <el-select v-model="form.problemSourceCode" :multiple="true" style="width: 100%;" placeholder="请选择问题来源"
                         :disabled="true">
                <el-option v-for="item in problemSourceDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="其他：" prop="note" v-if="noteShow">
          <el-input v-model="form.note" type="text" maxlength="200" :show-word-limit="true" placeholder="请输入其他"
                    :disabled="true"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题发生单位层级：" prop="hierarchyObj">
              <el-select v-model="form.hierarchyObj" style="width: 100%" value-key="dataCode" placeholder="请选择问题发生单位层级" :disabled="true">
                <el-option v-for="item in hierarchyDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类别：" prop="category">
              <el-input v-model="form.category" style="width: 100%;" placeholder="请输入类别" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="重点关注问题清单：" prop="priorityListing">
          <el-input v-model="form.priorityListing" placeholder="请输入重点关注问题清单" :disabled="true"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="班子问题还是部门问题：" prop="isDepartmentOfProblem">
              <el-select v-model="form.isDepartmentOfProblem" style="width: 100%;" placeholder="请选择班子问题还是部门问题"
                         :disabled="true">
                <el-option v-for="item in problemDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item.dataCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被巡察单位类型：" prop="unitUnderInspectionTypeObj">
              <el-select v-model="form.unitUnderInspectionTypeObj" value-key="dataCode" style="width: 100%" placeholder="请选择被巡察单位类型"
                         :disabled="true">
                <el-option v-for="item in unitUnderDateList" :key="item.dataCode" :label="item.dataName"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="问题分类：" prop="questionClassificationObj">
          <el-select v-model="form.questionClassificationObj" value-key="dataCode" style="width: 100%" placeholder="请选择问题分类" :disabled="true">
            <el-option v-for="item in questionDateList" :key="item.dataCode" :label="item.dataName"
                       :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题概述：" prop="issueAnOverview">
          <el-input type="textarea" :rows="3" placeholder="请输入问题概述" v-model="form.issueAnOverview" maxlength="20000"
                    :show-word-limit="true" :disabled="true"/>
        </el-form-item>
        <el-form-item label="查证事实描述：" prop="factDescription">
          <el-input type="textarea" :rows="3" placeholder="请输入查证事实描述" v-model="form.factDescription" maxlength="20000"
                    :show-word-limit="true" :disabled="true"/>
        </el-form-item>
        <el-form-item label="问题剖析：" prop="problemAnalysis">
          <el-input type="textarea" :rows="3" placeholder="请输入问题剖析" v-model="form.problemAnalysis" maxlength="20000"
                    :show-word-limit="true" :disabled="true"/>
        </el-form-item>
        <el-form-item label="支撑材料：" prop="materialSupport">
          <el-input type="textarea" :rows="3" placeholder="请输入支撑材料" v-model="form.materialSupport" maxlength="20000"
                    :show-word-limit="true" :disabled="true"/>
          <span>
                        <!-- <el-button type="primary" size="mini" @click="uploadFileBtn01()">
                                <span v-if="!form.coursePapersFilesList" disabled>上传文件</span>
                                <span v-else disabled>重新上传</span>
                            </el-button> -->
                            <div v-if="form.coursePapersFilesList" v-for="item in form.coursePapersFilesList"
                                 :key="item.fileId">
                                <el-button type="text" :title="item.fileName" class="accessory"
                                           style="font-size: 12px; color: #1590ff">
                                    {{ item.fileName }}
                                </el-button>
                                <el-button type="primary" size="mini" @click="onPreview01(item.fileId)"
                                           v-if="item.fileId" :disabled="false">预览
                                </el-button>
                                <el-button type="primary" size="mini" @click="onDownload01(item.fileId)"
                                           v-if="item.fileId" :disabled="false">下载
                                </el-button>
                        </div>
                    </span>
        </el-form-item>
        <el-form-item label="承办人：" prop="userId">
          <el-select v-model="form.userId" :multiple="true" style="width: 100%" class="angetSelect" placeholder="请选择承办人"
                     @change="handleUserId" :disabled="true">
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
                       :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组长：" prop="leader">
          <el-select v-model="form.leader" style="width: 100%" placeholder="请选择组长" :disabled="true">
            <el-option v-for="item in createdByDataList"
                       :key="item.id"
                       :label="item.userName+'（'+item.userId+'）'"
                       :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="dialog-title" style="margin-bottom:10px">
          <span class="line"></span><span>附件材料</span>
        </div>
        <el-form-item label="底稿附件：" prop="fileId">
          <el-button type="text" :title="form.fileName" class="accessory" style="font-size: 12px; color: #1590ff">
            {{ form.fileName }}
          </el-button>
          <!-- <el-button type="primary" size="mini" @click="uploadFileBtn()">
            <span v-if="form.fileId" disabled>重新上传</span>
            <span v-else disabled>上传文件</span>
          </el-button> -->
          <el-button type="primary" size="mini" @click="onPreview()" v-if="form.fileId" :disabled="false">预览
          </el-button>
          <el-button type="primary" size="mini" @click="onDownload()" v-if="form.fileId" :disabled="false">下载
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 流程历史信息 -->
      <all-flow-step ref="allFlowStepRef" :allFlowStep="process.allFlowStep" v-if="form.id"/>

      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
        <el-button type="primary" @click="handleConfirmation(form)">生成确认单</el-button>
      </div>
    </el-dialog>

    <!-- 选择事实确认单 -->
    <el-dialog :title="generateTitle" :visible.sync="generateShow" width="800px" append-to-body="append-to-body">
      <el-form ref="generateform" :rules="rulesForm" :model="generateform" label-width="160px">
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
              <el-select clearable v-model="generateform.piciId" style="width: 210px;float:right"
                         placeholder="请选择巡察批次" @change="handlePiciChange02">
                <el-option v-for="item in piciIdDataList02" :key="item.piciId" :label="item.piciName"
                           :value="item.piciId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="巡察组:" prop="groupName">
          <el-select clearable v-model="generateform.groupName" style="width: 100%" placeholder="请选择巡察组"
                     @change="handlexunchazuChange02">
            <el-option v-for="item in groupNameDataList02" :key="item.taskId" :label="item.groupName"
                       :value="item.taskId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="事实确认单:" prop="matterCode">
          <el-select clear v-model="generateform.matterCode" :multiple="true" style="width: 100%"
                     placeholder="请选择事实确认单">
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
    <uploadForm :dialogVisible="uploadFormShow01" @file-success="fileSuccess01" @close="uploadFormShow01 = false"
                ref="uploadForm01"/>

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
  import {getGroupLeader, finishFormTask, getPiciSelectData, getGroupSelectData} from "@/api/course/courseVoucher";
  import {
    listPapers,
    getPapers,
    delPapers,
    addPapers,
    updatePapers,
    exportPapers,
    importTemplate,
    disableEnable,
    getStartFlow,
    getPapersSource,
    getUndertaker,
    getImportWord,
    exportWord,
    getPapersSelectData,
    getPapersData,
    getDownloadWordTemplate
} from "@/api/course/coursePapers";
  
 import {
    getXunchaGroupInfoList,
} from "@/api/course/courseVoucher";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from '@/utils/index';
  import {queryPiciList, queryLunciList, queryCompanyList} from "@/api/xuncha/common/base";
  import {listXunchaTaskInfo} from "@/api/xuncha/XunchaTaskInfo";
  import {selectDictData} from "@/api/xuncha/xunchaDictData";
  import deepBtn from "@/components/WorkFlow/deepBtn";
  import SubmitProcess from "@/components/WorkFlow/submitProcess";
  import {downloadFile, previewFile, downloadZipFile,} from "@/utils/uploadOperate";
  import uploadForm from "@/components/CommonUpload/uploadForm";
  import completeProcess from "@/components/WorkFlow/completeProcess";
  import {fetchWorkFlowInfo} from "@/utils/workflow";
  import AllFlowStep from "@/components/WorkFlow/AllFlowStep";

  export default {
    name: "Papers",
    components: {ExpandCollapse, SubmitProcess, deepBtn, uploadForm, completeProcess, AllFlowStep},
    data() {
      return {
        noteShow: false,
        // 是否显示上传文件按钮和重新上传文件按钮
        formDisabled: false,
        formDisabled01: false,
        // 是否显示上传文件组件
        uploadFormShow: false,
        uploadFormShow01: false,
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
        // 巡察过程_问题底稿表格数据
        papersList: [],
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
          url: process.env.VUE_APP_BASE_API + "/course/coursePapers/importData",
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
          priorityListing: null,
          isDepartmentOfProblem: null,
          unitUnderInspectionTypeId: null,
          unitUnderInspectionType: null,
          questionClassification: null,
          fileId: null,
          fileName: null,
          status: null,
          statusName: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          territoryCode: null,
          piciIdshow01: true,
          groupNameshow01: true,
          isOpen: null,
        },
        // 巡查批次
        piciIdDataList01: [],
        //巡查组
        groupNameDataList01: [],
        // 表单参数
        form: {
          xunchaCoursePapers: {
            xunchaYear: null,
            checkTime: [],
            taskId: '',
          },
          xunchaCourseText: {
            fileId: null,
            fileName: null,
            name: null,
          },
          coursePapersDomainList: [],
          coursePapersSourceList: [],
          courseOverviewAgentList: [],
          todoHandler: "",
          message: "",
        },
        // 表单校验
        rules: {
          isOpen: [
            {required: true, message: "是否对巡察办可见不能为空", trigger: "blur"}
          ],
          xunchaYear: [
            {required: true, message: '请选择巡察年份', trigger: 'change'}
          ],
          piciId: [
            {required: true, message: '请选择巡察批次！', trigger: 'change'}
          ],
          orgName: [
            {required: true, message: '请选择巡察组！', trigger: 'change'}
          ],
          // checkTime: [
          //     { required: true, message: '请选择巡察时间', trigger: 'change' }
          // ],
          // orgName: [
          //     { required: true, message: '请选择被巡察单位！', trigger: 'change' }
          // ],
          head: [
            {required: true, message: '选输入分管领导', trigger: 'blur'}
          ],
          territoryCode: [
            {required: true, message: '请选择问题领域！', trigger: 'change'}
          ],
          problemSourceCode: [
            {required: true, message: '请选择问题来源！', trigger: 'change'}
          ],
          note: [
            {required: true, message: '请输入其他', trigger: 'blur'}
          ],
          hierarchyObj: [
            {required: true, message: '请选择问题发生单位层级！', trigger: 'change'}
          ],
          category: [
            {required: true, message: '请输入类别！', trigger: 'blur'}
          ],
          priorityListing: [
            {required: true, message: '请输入重点关注问题清单!', trigger: 'change'}
          ],
          isDepartmentOfProblem: [
            {required: true, message: '请选择班子问题还是部门问题！', trigger: 'change'}
          ],
          unitUnderInspectionTypeObj: [
            {required: true, message: '请选择被巡察单位类型！', trigger: 'change'}
          ],
          questionClassificationObj: [
            {required: true, message: '请选择问题分类！', trigger: 'change'}
          ],
          issueAnOverview: [
            {required: true, message: '请输入问题概述！', trigger: 'blur'}
          ],
          factDescription: [
            {required: true, message: '请输入查证事实描述！', trigger: 'blur'}
          ],
          problemAnalysis: [
            {required: true, message: '请输入问题剖析！', trigger: 'blur'}
          ],
          materialSupport: [
            {required: true, message: '请输入支撑材料！', trigger: 'blur'}
          ],
          userId: [
            {required: true, message: '请选择承办人！', trigger: 'change'}
          ],
          deputyLeader: [
            {required: true, message: '请选择分管副组长！', trigger: 'change'}
          ],
          leader: [
            {required: true, message: '请选择组长！', trigger: 'change'}
          ],
          fileId: [
            {required: true, message: '请选择底稿附件！', trigger: 'blur,change'}
          ],
        },

        //生成确认单表单校验
        rulesForm: {
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
            {required: true, message: '请选择事实确认单！', trigger: 'change'}
          ],
        },
        // 巡查批次
        piciIdDataList: [],
        //巡查组
        groupNameDataList: [],

        //当前状态
        statusNameDataList: [
          {value: '1', label: '草稿'},
          {value: '2', label: '组长审核'},
          {value: '3', label: '巡察办审核'},
          {value: '4', label: '退回组长'},
          {value: '5', label: '退回组员'},
          {value: '6', label: '审核通过'},
        ],
        // 被巡察单位
        orgIdDataList: [],

        // //问题领域or事实领域
        fieldDataList: [],
        // 问题底稿
        matterCodeDataList: [],

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

        //选择事实确认单
        generateShow: false,
        generateTitle: '',
        generateform: {
          matterCode: [],
          piciIdshow02: true,
          groupNameshow02: true,
        },
        // 巡查批次
        piciIdDataList02: [],
        //巡查组
        groupNameDataList02: [],

        // 流程信息
        process: {},
        uploadCurrent: "1",
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
    },
    activated() {
      let aaa = this.$route.query.openShow;
      if (aaa != null) {
        if (aaa.showTab == "1") {
          this.open = Boolean(aaa.openShow);
          this.title = aaa.title;
          this.reset();
          let bbb = JSON.parse(localStorage.getItem("valbb"));
          let array = [];
          bbb.territoryList.map((item, index) => {
            array.push(item.territoryCode);
          })
          this.form.territoryCode = array;
          if (bbb.xunchaYear != null) {
             this.form.xunchaYear = bbb.xunchaYear.toString();
           }
           let newArray = [];
           if (bbb.taskDateFrame != null) {
             newArray = bbb.taskDateFrame.split(",");
           }
           this.form.checkTime = newArray;
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
          // 获得承办人下拉框数据
          //  getUndertaker({taskId: bbb.taskId, id: bbb.id}).then((res) => {
          //     console.log("resaaaaa",res);
          //     let createdByDataList = [];
          //     createdByDataList.push({
          //       userId: res.data.currentBy.userId,
          //       userName: res.data.currentBy.userName,
          //       disabled: true,
          //       id: res.data.currentBy.userId,
          //     })
          //     createdByDataList.push(...res.data.undertake);
          //     this.createdByDataList = createdByDataList;
          // this.form.userId = [res.data.currentBy.userId]
          // })
          this.form.issueAnOverview = bbb.truthOverview;
          this.form.factDescription = bbb.specificTruth;
          this.form.voucherCode = bbb.voucherCode;
          localStorage.removeItem("valbb");
        }
      }

    },
    created() {
      this.getDataList();
      this.getList();
    },
    methods: {

      // 模板下载
      handleTemplate() {
        getDownloadWordTemplate().then(res => {
          this.download(res.msg);
          // downloadFile(res.msg);
        })
      },

      //材料支撑上传
      // 点击上传文件按钮
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

      //底稿附件上传
      // 点击上传文件按钮
      uploadFileBtn() {
        this.uploadFormShow = true;
      },
      // 预览文件
      onPreview() {
        previewFile(this.form.fileId);
      },
      // 文件下载
      onDownload(fileId, caseId) {
        downloadFile(this.form.fileId);
      },
      // 文件上传成功回调方法
      fileSuccess(files) {
        console.log("files", files);
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

      // 生成底稿
      handleManuscript() {
        this.generateShow = true;
        this.generateTitle = "选择事实确认单"
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

      // 根据巡察年份获取关联
      xunchaYearChange(date) {
        let dataList = [];
        this.form.piciId = null;
        this.form.groupName = null;
        this.form.checkTime = [];
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
        let newData = this.groupNameDataList;
        let newObject = {};
        let orgIdDataList = [];
        let createdByDataList = [];
        if (newData != null) {
          newData.map((item, index) => {
            if (item.taskId == val) {
              newObject = item;
              this.form.checkTime = [item.startTime, item.endTime];
              orgIdDataList.push({
                value: item.companyNames,
                label: item.companyNames,
              })
              this.orgIdDataList = orgIdDataList;
              this.form.orgName = item.companyNames;
              this.form.taskId = item.taskId;
              getUndertaker({taskId: this.form.taskId}).then((res) => {
                console.log("resccccc", res);
                createdByDataList.push({
                  userId: res.data.currentBy.userId,
                  userName: res.data.currentBy.userName,
                  disabled: true,
                  id: res.data.currentBy.userId,
                  labelValue: res.data.currentBy.labelValue,
                })
                createdByDataList.push(...res.data.undertake);
                this.createdByDataList = createdByDataList;
                this.form.userId = [res.data.currentBy.userId]
              })
            }
          })
        }
      },

      // 选择问题领域
      selectTerritoryCode(val) {
        this.$forceUpdate();
        let dataList = [];
        dataList = this.fieldDataList;
        let coursePapersDomainList = [];
        if (dataList != null) {
          dataList.map((item, index) => {
            val.map((i, index) => {
              if (item.dataCode == i) {
                coursePapersDomainList.push({
                  territoryCode: item.dataCode,
                  territoryName: item.dataName,
                })
              }

            })
          })
        }
        this.form.coursePapersDomainList = coursePapersDomainList;
      },
      // 选择问题来源
      selectProblemSourceCode(val) {
        this.$forceUpdate();
        let dataList = [];
        dataList = this.problemSourceDateList;
        let coursePapersSourceList = [];
        if (dataList != null) {
          dataList.map((item, index) => {
            val.map((i, index) => {
              if (item.dataCode == i) {
                coursePapersSourceList.push({
                  problemSourceCode: item.dataCode,
                  problemSourceName: item.dataName,
                })
              }

            })
          })
        }
        if (val.indexOf("5") == -1) {
          this.noteShow = false;
        } else {
          this.noteShow = true;
        }
        this.form.coursePapersSourceList = coursePapersSourceList;
      },

      //选择承办人
      handleUserId(val) {
        this.$forceUpdate();
        console.log("1111", val, this.createdByDataList);
        let dataList = [];
        dataList = this.createdByDataList;
        let courseOverviewAgentList = [];
        if (dataList != null) {
          dataList.map((item, index) => {
            val.map((i, index) => {
              if (item.id == i) {
                courseOverviewAgentList.push({
                  id: item.id,
                  userId: item.userId,
                  userName: item.userName,
                })
              }

            })
          })
        }
        this.form.userId = val;
        console.log('aaaaaa', this.form.userId);
        this.form.courseOverviewAgentList = courseOverviewAgentList;
      },

      /**查询 */
      // 根据巡察年份获取批次信息
      xunchaYearChange01(date) {
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

      /** 选择事实确认单 */
      // 根据巡察年份获取关联
      xunchaYearChange02(date) {
        this.piciIdDataList02 = [];
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
        getGroupSelectData({piciId: val}).then((res) => {
          if (res.data.length > 0) {
            this.groupNameDataList02 = res.data;
          } else {
            this.groupNameDataList02 = [];
          }
          this.generateform.groupNameshow02 = false;
        })
      },
      //根据巡察组获取关联
      handlexunchazuChange02(val) {
        console.log("val111", val);
        // let newData = this.groupNameDataList02;
        // let newObject = {};
        // if (newData != null) {
        //     newData.map((item, index) => {
        //         if (item.taskId == val) {
        //             newObject = item;
        //         }
        //     })
        // }
        // 获得问题底稿数据
        getPapersSelectData({taskId: val}).then((res) => {
          console.log("resggggg", res);
          this.matterCodeDataList = res.data;
        })
      },

      /**生成选择事实确认单 */
      clickGenerate() {
        // 表单验证
        this.$refs["generateform"].validate(valid => {
          if (valid) {
            getPapersData({papersIdArr: this.generateform.matterCode}).then(res => {
              console.log("res1111", res);
              if (res.data) {
                this.issueAnOverview += res.data.specificTruth;
                this.factDescription += res.data.truthOverview;
                this.form.issueAnOverview = this.issueAnOverview;
                this.form.factDescription = this.factDescription;
                this.form.voucherCode = res.data.voucherCode;
                if (this.form.voucherCode) {
                  this.form.xunchaYear = this.generateform.xunchaYear;
                  this.form.piciId = this.generateform.piciId;
                  this.form.groupName = this.generateform.groupName;
                  this.piciIdDataList = this.piciIdDataList02;
                  this.handlePiciChange(this.form.piciId, () => { 
                    this.handlexunchazuChange(this.form.groupName);
                  });
                 }
                this.generateShow = false;
              }
            })
          }
        })
      },

      getDataList() {
        selectDictData({dictType: 'course_voucher_territory'}).then((res) => {
          this.fieldDataList = res.data;
        });
        selectDictData({dictType: 'course_papers_source'}).then((res) => {
          this.problemSourceDateList = res.data;
        });
        selectDictData({dictType: 'course_papers_underInspection'}).then((res) => {
          this.hierarchyDateList = res.data;
        })
        selectDictData({dictType: 'course_papers_department'}).then((res) => {
          this.problemDateList = res.data;
        });
        selectDictData({dictType: 'course_papers_hierarchy'}).then((res) => {
          this.unitUnderDateList = res.data;
        });
        selectDictData({dictType: 'course_papers_classification'}).then((res) => {
          this.questionDateList = res.data;
        })
      },
      /** 查询巡察过程_问题底稿列表 */
      getList() {
        this.loading = true;
        listPapers(this.queryParams).then(res => {
          console.log("res11111", res);
          this.papersList = res.rows || [];
          this.total = Number(res.total);
          this.loading = false;
        });

      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
        this.issueAnOverview = "";
        this.factDescription = "";
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
          checkTime: [],
          orgId: null,
          orgName: null,
          head: null,
          hierarchyId: null,
          hierarchy: null,
          hierarchyObj: {},
          category: null,
          priorityListing: null,
          isDepartmentOfProblem: null,
          unitUnderInspectionTypeId: null,
          unitUnderInspectionType: null,
          unitUnderInspectionTypeObj: {},
          questionClassificationId:null,
          questionClassification: null,
          questionClassificationObj:{},
          fileId: null,
          fileName: null,
          status: "0",
          statusName: null,
          isOpen: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null,
          note: null,
          deputyLeader: null,
          leader: null,
          territoryCode: [],
          problemSourceCode: [],
          userId: [],
          issueAnOverview: "",
          factDescription: "",
          problemAnalysis: "",
          materialSupport: "",
          deputyLeader: null,
          leader: null,
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        let array = [];
        let newArray = [];
        let deleteShow = true;
        this.ids = selection.map(item => item.id)
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
              if (item.status != "1") {
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
        this.deleteShow = deleteShow;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "新增底稿";
        this.issueAnOverview = "";
        this.factDescription = "";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids;
        getPapers(id).then(res => {
          console.log("resrrrrr", res);
          let newArray = [];
          if (res.data.xunchaCoursePapers.checkTime != null) {
            newArray = res.data.xunchaCoursePapers.checkTime.split(",");
          }
          res.data.xunchaCoursePapers.checkTime = newArray;
          res.data.xunchaCoursePapers.hierarchyObj = {
            dataCode: res.data.xunchaCoursePapers.hierarchyId,
            dataName:res.data.xunchaCoursePapers.hierarchy 
          }
          res.data.xunchaCoursePapers.unitUnderInspectionTypeObj = {
            dataCode: res.data.xunchaCoursePapers.unitUnderInspectionTypeId,
            dataName:res.data.xunchaCoursePapers.unitUnderInspectionType 
          }
          res.data.xunchaCoursePapers.questionClassificationObj = {
            dataCode: res.data.xunchaCoursePapers.questionClassificationId,
            dataName:res.data.xunchaCoursePapers.questionClassification 
          }
          this.form = {...res.data.xunchaCoursePapers, ...res.data.xunchaCourseText};
          this.form.id = id;
          this.open = true;
          this.title = "修改底稿";
          getPiciSelectData({year: res.data.xunchaCoursePapers.xunchaYear.substring(0, 4)}).then((res) => {
            let dataList = [];
            if (res.data.length > 0) {
              dataList = res.data;
            } else {
              dataList = [];
            }
            this.piciIdDataList = dataList;
          });
          getGroupSelectData({piciId: res.data.xunchaCoursePapers.piciId}).then((res) => {
            let dataList = [];
            if (res.data.length > 0) {
              dataList = res.data;
            } else {
              dataList = [];
            }
            this.groupNameDataList = dataList;
          });
          // 获得承办人下拉框数据
          let createdByDataList = [];
          getUndertaker({
            taskId: res.data.xunchaCoursePapers.taskId,
            id: res.data.xunchaCoursePapers.id
          }).then((res) => {
            createdByDataList.push({
              userId: res.data.currentBy.userId,
              userName: res.data.currentBy.userName,
              disabled: true,
              id: res.data.currentBy.userId,
              labelValue: res.data.currentBy.labelValue,
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
          }
          ;
          if (res.data.coursePapersSourceList != null) {
            let problemSourceCode = [];
            res.data.coursePapersSourceList.map((item, index) => {
              problemSourceCode.push(item.problemSourceCode);
            })
            this.form.problemSourceCode = problemSourceCode;
          }
          ;
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
          console.log("formaaaaa", this.form);
        });
        fetchWorkFlowInfo(id, "", (res) => {
          this.process = res;
        }, 1)
      },

      /**生成确认单 */
      handleConfirmation(row) {
        this.isOpenQuery = false;
        let val = {};
        val.openShow = true;
        val.showTab = "2";
        val.title = '新增事实确认单';
        let aaa = JSON.stringify(row);
        localStorage.setItem("valaa", aaa);
        this.$router.push({path: '/courseVoucher', query: {openShow: val}})
      },

      /**详情按钮*/
      handleQueryOption(row) {
        this.reset();
        const id = row.id || this.ids;
        let createdByDataList = [];
        getPapers(id).then(res => {
          console.log("res33333", res);
          let newArray = [];
          if (res.data.xunchaCoursePapers.checkTime != null) {
            newArray = res.data.xunchaCoursePapers.checkTime.split(",");
          }
          res.data.xunchaCoursePapers.checkTime = newArray;
          res.data.xunchaCoursePapers.hierarchyObj = {
            dataCode: res.data.xunchaCoursePapers.hierarchyId,
            dataName:res.data.xunchaCoursePapers.hierarchy 
          }
          res.data.xunchaCoursePapers.unitUnderInspectionTypeObj = {
            dataCode: res.data.xunchaCoursePapers.unitUnderInspectionTypeId,
            dataName:res.data.xunchaCoursePapers.unitUnderInspectionType 
          }
          res.data.xunchaCoursePapers.questionClassificationObj = {
            dataCode: res.data.xunchaCoursePapers.questionClassificationId,
            dataName:res.data.xunchaCoursePapers.questionClassification 
          }
          this.form = {...res.data.xunchaCoursePapers, ...res.data.xunchaCourseText};
          this.isOpenQuery = true;
          this.title = "查看问题底稿";
          queryPiciList({year: res.data.xunchaCoursePapers.xunchaYear.substring(0, 4)}).then((res) => {
            let dataList = [];
            if (res.data.length > 0) {
              dataList = res.data;
            } else {
              dataList = [];
            }
            this.piciIdDataList = dataList;
          });
          listXunchaTaskInfo({piciId: res.data.xunchaCoursePapers.piciId}).then((res) => {
            let dataList = [];
            if (res.rows.length > 0) {
              dataList = res.rows;
            } else {
              dataList = [];
            }
            this.groupNameDataList = dataList;
          });
          // 获得承办人下拉框数据
          getUndertaker({
            taskId: res.data.xunchaCoursePapers.taskId,
            id: res.data.xunchaCoursePapers.id
          }).then((res) => {
            createdByDataList.push({
              userId: res.data.currentBy.userId,
              userName: res.data.currentBy.userName,
              disabled: true,
              id: res.data.currentBy.userId,
              labelValue: res.data.currentBy.labelValue,
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
          }
          ;
          if (res.data.coursePapersSourceList != null) {
            let problemSourceCode = [];
            res.data.coursePapersSourceList.map((item, index) => {
              problemSourceCode.push(item.problemSourceCode);
            })
            this.form.problemSourceCode = problemSourceCode;
          }
          ;
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
          console.log("ccccccccccc", this.fieldDataList, this.form.territoryCode);
        });
        fetchWorkFlowInfo(id, "", (res) => {
          this.process = res;
        }, 1)
      },

      /** 上传 */
      submitUpload() {

      },
      /**下载 */
      submitdownload() {
      },

      /**暂存 */
      handleDraft() {
        // 表单验证
        // this.$refs["form"].validate(valid => {
        //     if (valid) {
        //         // 判如果承办人小于两个，则不能提交
        //         if (this.form.userId.length <= 1) {
        //           this.msgError("承办人至少为两人");
        //           return
        //         }
        if (this.form.id) {
          // 修改页面的暂存是修改数据
          this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
          this.form.checkTime = this.form.checkTime.toString();
          if (this.piciIdDataList != null) {
            this.piciIdDataList.map((item, index) => {
              if (item.piciId == this.form.piciId) {
                this.form.piciName = item.piciName;
              }
            })
          }
          if (this.groupNameDataList != null) {
            this.groupNameDataList.map((item, index) => {
              if (item.taskId == this.form.groupName) {
                this.form.groupName = item.groupName;
              }
            })
          }
          this.form.xunchaCoursePapers = {
            spendCode: this.form.spendCode,
            voucherCode: this.form.voucherCode,
            matterCode: this.form.matterCode,
            xunchaYear: this.form.xunchaYear,
            piciId: this.form.piciId,
            piciName: this.form.piciName,
            taskId: this.form.taskId,
            groupName: this.form.groupName,
            checkTime: this.form.checkTime,
            orgName: this.form.orgName,
            head: this.form.head,
            hierarchyId: this.form.hierarchyObj.dataCode,
            hierarchy: this.form.hierarchyObj.dataName,
            category: this.form.category,
            priorityListing: this.form.priorityListing,
            isDepartmentOfProblem: this.form.isDepartmentOfProblem,
            unitUnderInspectionTypeId: this.form.unitUnderInspectionTypeObj.dataCode,
            unitUnderInspectionType: this.form.unitUnderInspectionTypeObj.dataName,
            questionClassificationId: this.form.questionClassificationObj.dataCode,
            questionClassification: this.form.questionClassificationObj.dataName,
            fileId: this.form.fileId,
            fileName: this.form.fileName,
            id: this.form.id,
            deputyLeader: this.form.deputyLeader,
            leader: this.form.leader,
            note: this.form.note,
          };
          let coursePapersDomainList = [];
          if (this.fieldDataList != null) {
            this.fieldDataList.map((item, index) => {
              this.form.territoryCode.map((i, index) => {
                if (item.dataCode == i) {
                  coursePapersDomainList.push({
                    territoryCode: item.dataCode,
                    territoryName: item.dataName,
                  })
                }
              })
            })
          }
          let coursePapersSourceList = [];
          if (this.problemSourceDateList != null) {
            this.problemSourceDateList.map((item, index) => {
              this.form.problemSourceCode.map((i, index) => {
                if (item.dataCode == i) {
                  coursePapersSourceList.push({
                    problemSourceCode: item.dataCode,
                    problemSourceName: item.dataName,
                  })
                }
              })
            })
          }
          let courseOverviewAgentList = [];
          if (this.createdByDataList != null) {
            this.createdByDataList.map((item, index) => {
              this.form.userId.map((i, index) => {
                if (item.id == i) {
                  courseOverviewAgentList.push({
                    userId: item.userId,
                    userName: item.userName,
                    id: item.id,
                  })
                }
              })
            })
          }
          this.form.coursePapersDomainList = coursePapersDomainList;
          this.form.coursePapersSourceList = coursePapersSourceList;
          this.form.courseOverviewAgentList = courseOverviewAgentList;
          this.form.xunchaCourseText = {
            issueAnOverview: this.form.issueAnOverview,
            factDescription: this.form.factDescription,
            problemAnalysis: this.form.problemAnalysis,
            materialSupport: this.form.materialSupport,
          };
          this.form.coursePapersFilesList = this.form.coursePapersFilesList;
          let dataList = JSON.stringify(this.form);
          const loading = this.openLoading();
          console.log("dataList1111", dataList);
          updatePapers(dataList).then((res) => {
            console.log("res1111", res);
            loading.close();
            this.open = false;
            this.getList();
          })
        } else {
          // 创建页面的暂存是创建数据
          this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
          this.form.checkTime = this.form.checkTime.toString();
          if (this.piciIdDataList != null) {
            this.piciIdDataList.map((item, index) => {
              if (item.piciId == this.form.piciId) {
                this.form.piciName = item.piciName;
              }
            })
          }
          if (this.groupNameDataList != null) {
            this.groupNameDataList.map((item, index) => {
              if (item.taskId == this.form.groupName) {
                this.form.groupName = item.groupName;
              }
            })
          }
          this.form.xunchaCoursePapers = {
            spendCode: this.form.spendCode,
            voucherCode: this.form.voucherCode,
            matterCode: this.form.matterCode,
            xunchaYear: this.form.xunchaYear,
            piciId: this.form.piciId,
            piciName: this.form.piciName,
            taskId: this.form.taskId,
            groupName: this.form.groupName,
            checkTime: this.form.checkTime,
            orgName: this.form.orgName,
            head: this.form.head,
            hierarchyId: this.form.hierarchyObj.dataCode,
            hierarchy: this.form.hierarchyObj.dataName,
            category: this.form.category,
            priorityListing: this.form.priorityListing,
            isDepartmentOfProblem: this.form.isDepartmentOfProblem,
            unitUnderInspectionTypeId: this.form.unitUnderInspectionTypeObj.dataCode,
            unitUnderInspectionType: this.form.unitUnderInspectionTypeObj.dataName,
            questionClassificationId: this.form.questionClassificationObj.dataCode,
            questionClassification: this.form.questionClassificationObj.dataName,
            fileId: this.form.fileId,
            fileName: this.form.fileName,
            deputyLeader: this.form.deputyLeader,
            leader: this.form.leader,
            note: this.form.note,
          };
          this.form.xunchaCourseText = {
            issueAnOverview: this.form.issueAnOverview,
            factDescription: this.form.factDescription,
            problemAnalysis: this.form.problemAnalysis,
            materialSupport: this.form.materialSupport,
          };
          this.form.coursePapersFilesList = this.form.coursePapersFilesList;
          let dataList = JSON.stringify(this.form);
          const loading = this.openLoading();
          getStartFlow(dataList).then((res) => {
            loading.close();
            this.open = false;
            this.getList();
          })
        }
        //     }
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
            if (this.form.userId.length <= 1) {
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
        // this.$refs["form"].validate(valid => {
        //     if (valid) {
        //         if (this.form.id != null) {
        //             updatePapers(this.form).then(response => {
        //                 if (response.code === 200) {
        //                     this.msgSuccess("修改成功");
        //                     this.open = false;
        //                     this.getList();
        //                 }
        //             });
        //         } else {
        //             addPapers(this.form).then(response => {
        //                 if (response.code === 200) {
        //                     this.msgSuccess("新增成功");
        //                     this.open = false;
        //                     this.getList();
        //                 }
        //             });
        //         }
        //     }
        // });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除此数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delPapers(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || [];
        this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return disableEnable({ids: ids.toString(), disableEnableState: type});
        }).then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
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
      /** 导入按钮操作 */
      openImportTable() {
        // this.upload.title = "巡察过程_问题底稿数据导入";
        this.uploadOpen = true;
      },
      fileSuccess02(files) {
        this.msgSuccess("上传成功");
        console.log("ffiles2222", files);
        let fileId = files[0].result.data.fileId;
        let name = files[0].result.data.fileName;
        let fileName = name.substring(0, name.lastIndexOf("."));
        console.log("aaaa", fileId, fileName);
        getImportWord({id: fileId}).then((res) => {
          res.data = JSON.parse(res.msg);
          console.log("dddddd", res.data);
          this.open = true;
          this.title = "新增底稿";

          if (res.data.xunchaYear != null) {
                   res.data.xunchaYear = res.data.xunchaYear.toString();
                 }
                 let newArray = [];
                 if (res.data.taskDateFrame != null) {
                   newArray = res.data.taskDateFrame.split(",");
                 }
                if (res.data.checkTime != null) {
                  res.data.checkTime = res.data.checkTime.split(",");
                 }
                res.data.leader = "";
                res.data.matterCode = "";
                 res.data.taskDateFrame = newArray;
                  res.data.hierarchyObj = {};
                  res.data.unitUnderInspectionTypeObj = {};
                  res.data.questionClassificationObj = {};
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
                   this.form.userId = [res.data.currentBy.userId];
                })
                  this.form = res.data;
          // let newArray = [];
          // if (res.data.xunchaCoursePapers.checkTime != null) {
          //   newArray = res.data.xunchaCoursePapers.checkTime.split(",");
          // }
          // res.data.xunchaCoursePapers.checkTime = newArray;
          // this.form = {...res.data.xunchaCoursePapers, ...res.data.xunchaCourseText};
          // queryPiciList({year:res.data.xunchaCoursePapers.xunchaYear.substring(0,4)}).then((res)=>{
          //     let dataList=[];
          //     if(res.data.length>0){
          //         dataList = res.data;
          //     }else{
          //         dataList = [] ;
          //     }
          //     this.piciIdDataList = dataList ;
          // });
          // listXunchaTaskInfo({piciId:res.data.xunchaCoursePapers.piciId}).then((res)=>{
          //     let dataList=[];
          //     if(res.rows.length>0){
          //         dataList = res.rows;
          //     }else{
          //         dataList = [];
          //     }
          //     this.groupNameDataList = dataList;
          // });
          // // 获得承办人下拉框数据
          // let createdByDataList = [];
          // getUndertaker({ taskId: res.data.xunchaCoursePapers.taskId,id: res.data.xunchaCoursePapers.id }).then((res) => {
          //     console.log("resccccc",res);
          //     createdByDataList.push({
          //         userId: res.data.currentBy.userId,
          //         userName: res.data.currentBy.userName,
          //         disabled: true,
          //         id: res.data.currentBy.userId,
          //     })
          //     createdByDataList.push(...res.data.undertake);
          //     this.createdByDataList = createdByDataList;
          //     console.log("createdByDataList",this.createdByDataList);
          // });
          // if(this.fieldDataList != null){
          //     let territoryCode = [];
          //     this.fieldDataList.map((item,index)=>{
          //         if(item.territoryName == data.territoryName){
          //             territoryCode.push(item.territoryCode);
          //         }
          //     })
          //     this.form.territoryCode = territoryCode;
          // };

          // if(this.problemSourceDateList != null){
          //     let  problemSourceCode = [];
          //     this.problemSourceDateList.map((item,index)=>{
          //         if(item.problemSourceName == data.problemSourceName){
          //             problemSourceCode.push(item.problemSourceCode);
          //         }
          //     })
          //     this.form.problemSourceCode = problemSourceCode;
          // };
          // if(res.data.courseOverviewAgentList != null){
          //     let userId = [];
          //     res.data.courseOverviewAgentList.map((item,index)=>{
          //         userId.push(item.id);
          //     })
          //     this.form.userId = userId ;
          // }
          // console.log("form1111",this.form,this.form.userId,this.createdByDataList);
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
        console.log("sssss", event, file, fileList);
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        console.log("response", response, file, fileList, this.upload.url);
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

      // 创建页面/修改页面 - 提交页面 - 点击发送
      saveFromData(val) {
        console.log("val1111", JSON.parse(val.variables));
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id) {
              // 修改页面，推动流程，先暂存数据
              this.open = true;
              this.form.todoHandler = JSON.parse(val.variables).nextDealUser;
              this.form.message = JSON.parse(val.variables).option;
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.checkTime = this.form.checkTime.toString();
              if (this.piciIdDataList != null) {
                this.piciIdDataList.map((item, index) => {
                  if (item.piciId == this.form.piciId) {
                    this.form.piciName = item.piciName;
                  }
                })
              }
              if (this.groupNameDataList != null) {
                this.groupNameDataList.map((item, index) => {
                  if (item.taskId == this.form.groupName) {
                    this.form.groupName = item.groupName;
                  }
                })
              }
              this.form.xunchaCoursePapers = {
                spendCode: this.form.spendCode,
                voucherCode: this.form.voucherCode,
                matterCode: this.form.matterCode,
                xunchaYear: this.form.xunchaYear,
                piciId: this.form.piciId,
                piciName: this.form.piciName,
                taskId: this.form.taskId,
                groupName: this.form.groupName,
                checkTime: this.form.checkTime,
                orgName: this.form.orgName,
                head: this.form.head,
                hierarchyId:this.form.hierarchyObj.dataCode,
                hierarchy: this.form.hierarchyObj.dataName,
                category: this.form.category,
                priorityListing: this.form.priorityListing,
                isDepartmentOfProblem: this.form.isDepartmentOfProblem,
                unitUnderInspectionTypeId:this.form.unitUnderInspectionTypeObj.dataCode,
                unitUnderInspectionType: this.form.unitUnderInspectionTypeObj.dataName,
                questionClassificationId: this.form.questionClassificationObj.dataCode,
                questionClassification: this.form.questionClassificationObj.dataName,
                fileId: this.form.fileId,
                fileName: this.form.fileName,
                id: this.form.id,
                deputyLeader: this.form.deputyLeader,
                leader: this.form.leader,
                note: this.form.note,
              };
              this.form.xunchaCourseText = {
                issueAnOverview: this.form.issueAnOverview,
                factDescription: this.form.factDescription,
                problemAnalysis: this.form.problemAnalysis,
                materialSupport: this.form.materialSupport,
              };
              let coursePapersDomainList = [];
              if (this.fieldDataList != null) {
                this.fieldDataList.map((item, index) => {
                  this.form.territoryCode.map((i, index) => {
                    if (item.dataCode == i) {
                      coursePapersDomainList.push({
                        territoryCode: item.dataCode,
                        territoryName: item.dataName,
                      })
                    }
                  })
                })
              }
              let coursePapersSourceList = [];
              if (this.problemSourceDateList != null) {
                this.problemSourceDateList.map((item, index) => {
                  this.form.problemSourceCode.map((i, index) => {
                    if (item.dataCode == i) {
                      coursePapersSourceList.push({
                        problemSourceCode: item.dataCode,
                        problemSourceName: item.dataName,
                      })
                    }
                  })
                })
              }
              let courseOverviewAgentList = [];
              if (this.createdByDataList != null) {
                this.createdByDataList.map((item, index) => {
                  this.form.userId.map((i, index) => {
                    if (item.id == i) {
                      courseOverviewAgentList.push({
                        userId: item.userId,
                        userName: item.userName,
                        id: item.id,
                      })
                    }
                  })
                })
              }
              this.form.coursePapersFilesList = this.form.coursePapersFilesList;
              this.form.coursePapersDomainList = coursePapersDomainList;
              this.form.coursePapersSourceList = coursePapersSourceList;
              this.form.courseOverviewAgentList = courseOverviewAgentList;
              let dataList = JSON.stringify(this.form);
              console.log("dataList", dataList);
              const loading = this.openLoading();
              updatePapers(dataList).then(res => {
                console.log("resaaaa", res);
                if (res.code === 200) {
                  // 获得流程信息 taskId
                  fetchWorkFlowInfo(this.form.id, "", (res) => {
                    console.log("resfffff", res);
                    // 任务ID
                    let data = {
                      taskId: res.curtaskId,
                      variables: val.variables
                    }
                    // 调用推动流程接口
                    finishFormTask(data).then(res => {
                      console.log('调用推动流程接口')
                      console.log(res)
                      this.msgSuccess("已提交");
                      this.open = false;
                      this.submitProcessShow = false;
                      this.getList();
                    })
                  }, 1)
                  loading.close();
                  this.submitProcessShow = false;
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              // 新增页面，发起流程
              this.form.todoHandler = JSON.parse(val.variables).nextDealUser;
              this.form.message = JSON.parse(val.variables).option;
              this.form.xunchaYear = this.form.xunchaYear.substring(0, 5) + "01" + this.form.xunchaYear.substring(7, this.form.xunchaYear.length)
              this.form.checkTime = this.form.checkTime.toString();
              if (this.piciIdDataList != null) {
                this.piciIdDataList.map((item, index) => {
                  if (item.piciId == this.form.piciId) {
                    this.form.piciName = item.piciName;
                  }
                })
              }
              if (this.groupNameDataList != null) {
                this.groupNameDataList.map((item, index) => {
                  if (item.taskId == this.form.groupName) {
                    this.form.groupName = item.groupName;
                  }
                })
              }
              this.form.xunchaCoursePapers = {
                spendCode: this.form.spendCode,
                voucherCode: this.form.voucherCode,
                matterCode: this.form.matterCode,
                xunchaYear: this.form.xunchaYear,
                piciId: this.form.piciId,
                piciName: this.form.piciName,
                taskId: this.form.taskId,
                groupName: this.form.groupName,
                checkTime: this.form.checkTime,
                orgName: this.form.orgName,
                head: this.form.head,
                hierarchyId:this.form.hierarchyObj.dataCode,
                hierarchy: this.form.hierarchyObj.dataName,
                category: this.form.category,
                priorityListing: this.form.priorityListing,
                isDepartmentOfProblem: this.form.isDepartmentOfProblem,
                unitUnderInspectionTypeId:this.form.unitUnderInspectionTypeObj.dataCode,
                unitUnderInspectionType: this.form.unitUnderInspectionTypeObj.dataName,
                questionClassificationId: this.form.questionClassificationObj.dataCode,
                questionClassification: this.form.questionClassificationObj.dataName,
                fileId: this.form.fileId,
                fileName: this.form.fileName,
                deputyLeader: this.form.deputyLeader,
                leader: this.form.leader,
                note: this.form.note,
              };
              this.form.xunchaCourseText = {
                issueAnOverview: this.form.issueAnOverview,
                factDescription: this.form.factDescription,
                problemAnalysis: this.form.problemAnalysis,
                materialSupport: this.form.materialSupport,
              };
              this.form.coursePapersFilesList = this.form.coursePapersFilesList;
              let dataList = JSON.stringify(this.form);
              console.log("dataList", dataList);
              const loading = this.openLoading();
              getStartFlow(dataList).then((res) => {
                loading.close();
                this.submitProcessShow = false;
                this.open = false;
                this.getList();
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
        console.log('121213213213')
        console.log(this.form.id)
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

//   /deep/ .el-input.is-disabled .el-input__inner {
//     background-color: #F5F7FA !important;
//   }
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

