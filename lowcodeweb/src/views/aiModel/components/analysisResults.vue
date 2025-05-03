<template>
  <div>
    <el-row class="tabBox">
      <el-col :span="tabType?5:3">
        <el-button size="mini" type="text" class="btntabs " :class="{ 'btntabindex': tabindex == 1 }" @click="ontabs(1)"
          v-if="tabType">结果汇总
        </el-button>
        <el-button size="mini" type="text" class="btntabs" :class="{ 'btntabindex': tabindex == 2 }" @click="ontabs(2)"
          v-if="example.modelId != 400">
          结果详情
        </el-button>
      </el-col>
      <el-col :span="example.modelId == 200?(tabType?10:12):(tabType?16:18)" style="display:flex;margin-right: 0.5rem;">
        <div style="display:flex;flex-grow:0;min-width: 10rem;">
          <span class=" whit65" style="font-size: 0.875rem;">被巡察单位：</span>
          <span style="font-size:14px;color:#fff;">{{ queryParams.companyName }}</span>
        </div>
        <div style="display:flex;flex-grow:1;">
          <el-col :span="24/newModelData.length" v-for="item in newModelData" :key="timeId?timeId:''+'_'+ item.id"
            style="position:relative">
            <span v-if="newModelData.length <= 2" class="label whit65">
              {{item.comparisonName.match(regx)?(item.comparisonName.match(regx)[0]).substring(1,item.comparisonName.match(regx)[0].length-1):item.comparisonName
              }}：
            </span>
            <span v-else
              style="float:left;width:calc(60% - 1.2rem);white-space: nowrap; text-overflow: ellipsis;overflow: hidden;"
              class="showName label whit65">
              {{item.comparisonName.match(regx)?(item.comparisonName.match(regx)[0]).substring(1,item.comparisonName.match(regx)[0].length-1):item.comparisonName}}：
              <span style="position:absolute;top:-20px;left:1.2rem;background:#041037;color:#fff">
                {{item.comparisonName.match(regx)?(item.comparisonName.match(regx)[0]).substring(1,item.comparisonName.match(regx)[0].length-1):item.comparisonName}}
              </span>
            </span>
            <span style="font-size:14px;color:#fff;">
              {{ item.dataNumber == '数据未上传！' ? 0 : item.dataNumber }}</span>
          </el-col>
        </div>
        <div style="display:flex;flex-grow:0;min-width: 6rem;" v-if="example.modelId != 200">
          <span class="showName label whit65">
            {{example.modelId == 100?'无出差文件总数：':''}}
            {{example.modelId == 110?'比中数量：':''}}
            {{example.modelId == 120?'比中数量：':''}}
            {{example.modelId == 200?'':''}}
            {{example.modelId == 310?'比中数量：':''}}
            {{example.modelId == 330?'比中数量：':''}}
            {{example.modelId == 320?'比中数量：':''}}
            {{example.modelId == 340?'重复报账数量：':''}}
            {{example.modelId == 400?'被巡察单位学习记录：':''}}
          </span>
          <span style="color:#fff;">{{ Number(taxindexTotal)}}</span>
        </div>
      </el-col>

      <el-col :span="example.modelId == 200?9:3" class="tabright">
        <el-button size="mini" type="text" class="btnexport" @click="busOpen" v-if="example.modelId == 200">
          <div class="text">
            <img style="margin-right: 5px; width: 1rem;height: 1rem;" src="~@/assets/images/ai/export.png"
              alt="" />协议酒店维护
          </div>
        </el-button>
        <el-button size="mini" type="text" class="btnexport" @click="onxfOpen" v-if="example.modelId == 200">
          <div class="text">
            <img style="margin-right: 5px; width: 1rem;height: 1rem;" src="~@/assets/images/ai/export.png"
              alt="" />销方风险标记
          </div>
        </el-button>
        <el-button size="mini" type="text" class="btnexport" @click="ontips">
          <div class="text">
            <img style="margin-right: 5px; width: 1rem;height: 1rem;" src="~@/assets/images/ai/export.png"
              alt="" />导出比对结果
          </div>
        </el-button>
      </el-col>
    </el-row>
    <!-- 稽核无出差文件的集中度1 -->
    <div class="boxexplain textcolor" v-if="tabindex == 1 && example.modelId == 100">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table1" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" align="left" prop="companyName" width="200"></el-table-column>
          <el-table-column label="部门" align="left" prop="deptName"></el-table-column>
          <el-table-column label="员工层级" align="center" prop="hierarchy"></el-table-column>
          <el-table-column prop="filesNumber" label="无出差文件申请数（条）" align="center" width="200">
            <template slot-scope="scope">
              <span v-if="scope.row.quantity > 0" class="hand" @click="handleDetail('quantity', scope.row)">
                {{ scope.row.quantity | numberFilt }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="percent" label="占比" align="center" width="200">
            <template slot-scope="scope">
              <div class="xiubox">
                <el-progress :percentage="scope.row.proportion ? Number(scope.row.proportion) : 0" :color="customColor">
                </el-progress>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 稽核无出差文件的集中度2 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 100">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table2" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="所属部门" prop="deptName" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center">
          </el-table-column>
          <el-table-column label="工单编号" prop="tripCode" align="center">
          </el-table-column>
          <el-table-column label="出发城市" prop="departureCity" align="center">
          </el-table-column>
          <el-table-column label="到达城市" prop="targetCity" align="center">
          </el-table-column>
          <el-table-column label="出差开始时间" prop="tripBeginDate" align="center">
          </el-table-column>
          <el-table-column label="出差结束时间" prop="tripEndDate" align="center">
          </el-table-column>
          <el-table-column label="说明" prop="remark" align="center">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 未出差报销差旅费问题模型（与考勤比对） -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 110">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table3" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="companyName" align="center">
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="员工编号" prop="employee" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center">
          </el-table-column>
          <el-table-column label="所属部门" prop="deptName" align="center">
          </el-table-column>
          <el-table-column label="出差申请单号" prop="tripCode" align="center">
          </el-table-column>
          <el-table-column label="出差开始时间" prop="tripBeginDate" align="center">
          </el-table-column>
          <el-table-column label="出差结束时间" prop="tripEndDate" align="center">
          </el-table-column>
          <el-table-column label="异常时间" prop="exceptionDate" align="center">
          </el-table-column>
          <el-table-column label="出差事由" prop="tripReason" align="center" width="200">
          </el-table-column>
          <el-table-column label="核对说明" prop="exceptionExplain" align="center" width="200">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 未出差报销差旅费问题模型（与招待比对） -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 120">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table4" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="companyName" align="center">
          </el-table-column>
          <el-table-column label="经办人" prop="responsibleBy" align="center">
          </el-table-column>
          <el-table-column label="经办人ID" prop="responsibleId" align="center">
          </el-table-column>
          <el-table-column label="所属部门" prop="deptName" align="center">
          </el-table-column>
          <el-table-column label="出差单号" prop="tripCode" align="center">
          </el-table-column>
          <el-table-column label="出差开始时间" prop="tripBeginDate" align="center">
          </el-table-column>
          <el-table-column label="出差结束时间" prop="tripEndDate" align="center">
          </el-table-column>
          <el-table-column label="发票日期" prop="invoiceDate" align="center">
          </el-table-column>
          <el-table-column label="出差城市" prop="targetCity" align="center">
          </el-table-column>
          <el-table-column label="招待地市" prop="dsName" align="center">
          </el-table-column>
          <el-table-column label="招待业务申请单编号" prop="serveCode" align="center">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 分析差旅酒店所开发票集中度模型1 -->
    <div class="boxexplain textcolor" v-if="tabindex == 1 && example.modelId == 200">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table5" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="companyName" align="center">
          </el-table-column>
          <el-table-column label="城市类型" prop="cityType" align="center">
          </el-table-column>
          <el-table-column label="发票代码" prop="invoiceCode" align="center">
          </el-table-column>
          <el-table-column label="销方名称" prop="marketName" align="center">
          </el-table-column>
          <el-table-column label="数量" prop="invoiceCount" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.invoiceCount > 0" class="hand" @click="handleDetail('invoiceCount', scope.row)">
                {{ scope.row.invoiceCount | numberFilt }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="percent" label="占比" align="center" width="200">
            <template slot-scope="scope">
              <div class="xiubox">
                <el-progress :percentage="scope.row.proportion ? Number(Number(scope.row.proportion).toFixed(4)) : 0"
                  :color="customColor">
                </el-progress>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="销方税号" prop="marketTfn" align="center">
          </el-table-column>
          <el-table-column label="销方地址电话" prop="marketAddress" align="center">
          </el-table-column>
          <el-table-column label="销方开户银行账号" prop="marketBankAccount" align="center" width="160">
          </el-table-column>
          <!-- <el-table-column label="酒店风险性" prop="levelName" align="center">
          </el-table-column> -->
          <el-table-column label="酒店名称" prop="hotelName" align="center">
          </el-table-column>
          <el-table-column label="是否协议酒店" prop="isXyjd" align="center" width="130">
          </el-table-column>
          <el-table-column label="风险等级" prop="levelName" align="center">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 分析差旅酒店所开发票集中度模型2 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 200">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table6" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="所在部门" prop="deptName" align="center">
          </el-table-column>
          <el-table-column label="单据编号" prop="billCode" align="center">
          </el-table-column>
          <el-table-column label="城市类型" prop="cityType" align="center">
          </el-table-column>
          <el-table-column label="发票代码" prop="invoiceCode" align="center">
          </el-table-column>
          <el-table-column label="发票号码" prop="invoiceNumber" align="center">
          </el-table-column>
          <el-table-column label="销方名称" prop="marketName" align="center">
          </el-table-column>
          <el-table-column label="发票日期" prop="invoiceDate" align="center">
          </el-table-column>
          <el-table-column label="销方税号" prop="marketTfn" align="center">
          </el-table-column>
          <el-table-column label="销方地址电话" prop="marketAddress" align="center">
          </el-table-column>
          <el-table-column label="销方开户银行账号" prop="marketBankAccount" align="center" width="160">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 既派车又报销差旅补贴1 -->
    <div class="boxexplain textcolor" v-if="tabindex == 1 && example.modelId == 310">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table7" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="company" align="center">
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="员工编号" prop="employee" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center" width="120">
          </el-table-column>
          <el-table-column label="所属部门" prop="department" align="center">
          </el-table-column>
          <el-table-column label="出差开始时间" prop="tripBeginDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="出差结束时间" prop="tripEndDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="用车时长" prop="useCarDuration" align="center" width="140">
          </el-table-column>
          <el-table-column label="出差交通天数" prop="tripTrafficDays" align="center" width="140">
          </el-table-column>
          <el-table-column label="交通包干标准" prop="trafficStandard" align="center">
          </el-table-column>
          <el-table-column label="可报销天数" prop="canClaimDays" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.canClaimDays > 0" class="hand" @click="handleDetail('canClaimDays', scope.row)">
                {{ scope.row.canClaimDays | numberFilt }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="可报销上限" prop="canClaimLimit" align="center">
          </el-table-column>
          <el-table-column label="交通包干补贴" prop="trafficSubsidy" align="center">
          </el-table-column>
          <el-table-column label="报销单号" prop="claimCode" align="center">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 既派车又报销差旅补贴2 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 310">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table8" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>

          <el-table-column label="用车人姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="用车人ID" prop="userId" align="center" width="120">
          </el-table-column>
          <el-table-column label="上车地市" prop="startCity" align="center">
          </el-table-column>
          <el-table-column label="上车地点" prop="startAddress" align="center" width="140">
          </el-table-column>
          <el-table-column label="下车地市" prop="endCity" align="center" width="140">
          </el-table-column>
          <el-table-column label="下车地点" prop="endAddress" align="center" width="140">
          </el-table-column>
          <el-table-column label="实际开始时间" prop="startDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="实际结束时间" prop="endDate" align="center">
          </el-table-column>
          <el-table-column label="用车时长" prop="useCarDuration" align="center">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>

    <!-- 出差行程中申请用车行为分析 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 330">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table9" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="company" align="center">
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="员工编号" prop="employee" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center" width="120">
          </el-table-column>
          <el-table-column label="所属部门" prop="department" align="center">
          </el-table-column>
          <el-table-column label="差旅工单编号" prop="tripCode" align="center">
          </el-table-column>
          <el-table-column label="出发城市" prop="departureCity" align="center">
          </el-table-column>
          <el-table-column label="到达城市" prop="targetCity" align="center">
          </el-table-column>
          <el-table-column label="出差开始日期" prop="tripStartDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="出差结束日期" prop="tripEndDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="上车地市" prop="startCity" align="center" width="140">
          </el-table-column>
          <el-table-column label="上车地点" prop="startAddress" align="center" width="140">
          </el-table-column>
          <el-table-column label="下车地市" prop="endCity" align="center" width="140">
          </el-table-column>
          <el-table-column label="下车地点" prop="endAddress" align="center" width="140">
          </el-table-column>
          <el-table-column label="实际开始日期" prop="carStartDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="实际结束日期" prop="carEndDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="用车时长" prop="duration" align="center" width="140">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 出差半天却报销全天伙食补贴问题1 -->
    <div class="boxexplain textcolor" v-if="tabindex == 1 && example.modelId == 320">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table10" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="company" align="center">
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="员工编号" prop="employee" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center" width="120">
          </el-table-column>
          <el-table-column label="所属部门" prop="department" align="center">
          </el-table-column>
          <el-table-column label="出差开始时间" prop="submitStartDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="出差结束时间" prop="submitEndDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="报销天数" prop="submitDays" align="center" width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.submitDays > 0" class="hand" @click="handleDetail('submitDays', scope.row)">
                {{ scope.row.submitDays | numberFilt }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="实际可报销天数" prop="actualSubmitDays" align="center">
          </el-table-column>
          <el-table-column label="伙食标准" prop="mealsStandard" align="center">
          </el-table-column>
          <el-table-column label="报销补贴上限" prop="mealsStandardMax" align="center" width="140">
          </el-table-column>
          <el-table-column label="伙食包干补贴" prop="mealsSubsidy" align="center" width="140">
          </el-table-column>
          <el-table-column label="报销单号" prop="submitCode" align="center" width="140">
          </el-table-column>
          <el-table-column label="用车次数" prop="useCarNumber" align="center" width="140">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <!-- 出差半天却报销全天伙食补贴问题2 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 320">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table11" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="用车人姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="用车人ID" prop="userId" align="center" width="120">
          </el-table-column>
          <el-table-column label="上车地市" prop="startCity" align="center" width="140">
          </el-table-column>
          <el-table-column label="上车地点" prop="startAddress" align="center" width="140">
          </el-table-column>
          <el-table-column label="下车地市" prop="endCity" align="center" width="140">
          </el-table-column>
          <el-table-column label="下车地点" prop="endAddress" align="center" width="140">
          </el-table-column>
          <el-table-column label="实际开始日期" prop="startDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="实际结束日期" prop="endDate" align="center" width="140">
          </el-table-column>
          <el-table-column label="用车时长" prop="useCarDuration" align="center" width="140">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>

    <!-- 重复报账问题分析1 -->
    <div class="boxexplain textcolor" v-if="tabindex == 1 && example.modelId == 340">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table12" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="所属公司" prop="company" align="center">
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="员工编号" prop="employee" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center">
          </el-table-column>
          <el-table-column label="所属部门" prop="department" align="center">
          </el-table-column>
          <el-table-column label="重复次数" prop="frequency" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.frequency > 0" class="hand" @click="handleDetail('frequency', scope.row)">
                {{ scope.row.frequency | numberFilt }}
              </span>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>

    <!-- 重复报账问题分析2 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 340">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table12" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="员工姓名" prop="fullName" align="center">
          </el-table-column>
          <el-table-column label="员工编号" prop="employee" align="center">
          </el-table-column>
          <el-table-column label="员工ID" prop="userId" align="center">
          </el-table-column>
          <el-table-column label="所属部门" prop="department" align="center">
          </el-table-column>
          <el-table-column label="出差申请单号" prop="appluCode" align="center">
          </el-table-column>
          <el-table-column label="出行方式" prop="tripType" align="center">
          </el-table-column>
          <el-table-column label="出差开始时间" prop="startDate" align="center">
          </el-table-column>
          <el-table-column label="出差结束时间" prop="endDate" align="center">
          </el-table-column>
          <el-table-column label="出发地" prop="startAddress" align="center">
          </el-table-column>
          <el-table-column label="目的地" prop="endAddress" align="center">
          </el-table-column>
          <el-table-column label="出差事由" prop="remark" align="center">
          </el-table-column>

        </el-table>
      </el-scrollbar>
    </div>
    <!-- 应学未学模块1 -->
    <div class="boxexplain textcolor" v-if="tabindex == 1 && example.modelId == 400">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table100" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="省公司发文" prop="companyDispatch" align="center" width="120">
          </el-table-column>
          <el-table-column label="学习要求和内容" prop="studyContent" align="center" width="200">
          </el-table-column>
          <el-table-column label="下发部门" prop="issueDept" align="center">
          </el-table-column>
          <el-table-column label="下发时间" prop="issueDate" align="center">
          </el-table-column>
          <el-table-column label="关键字1" prop="keyword1" align="center">
          </el-table-column>
          <el-table-column label="关键字2" prop="keyword2" align="center">
          </el-table-column>
          <el-table-column label="关键字3" prop="keyword3" align="center">
          </el-table-column>
          <el-table-column label="关键字4" prop="keyword4" align="center">
          </el-table-column>
          <el-table-column label="关键字5" prop="keyword5" align="center">
          </el-table-column>
          <el-table-column label="应学支部数" prop="studyCount" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.studyCount > 0" class="hand" @click="handleDetail('studyCount', scope.row)">
                {{ scope.row.studyCount | numberFilt }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="未学支部数" prop="noStudyCount" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.noStudyCount > 0" class="hand" @click="handleDetail('noStudyCount', scope.row)">
                {{ scope.row.noStudyCount | numberFilt }}
              </span>
            </template>
          </el-table-column>

        </el-table>
      </el-scrollbar>
    </div>
    <!-- 应学未学模块2-隐藏 -->
    <div class="boxexplain textcolor" v-if="tabindex == 2 && example.modelId == 400">
      <el-scrollbar class="table_scrollbar">
        <el-table stripe header-cell-class-name="tableHeaderClass" key="table101" :data="analysisData"
          :max-height="tabMaxHeight" v-loading="analysisType">
          <el-table-column label="序号" type="index" width="90" align="left">
            <template slot-scope="scope">
              <div class="indexIcon">{{ scope.$index + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="支部名称" prop="branchName" align="center" width="160">
          </el-table-column>
          <el-table-column label="省公司发文" prop="companyDispatch" align="center" width="100">
          </el-table-column>
          <el-table-column label="学习要求和内容" prop="studyContent" align="center" width="200">
          </el-table-column>
          <el-table-column label="会议名称" prop="meetingName" align="center">
          </el-table-column>
          <el-table-column label="会议类型" prop="meetingType" align="center">
          </el-table-column>
          <el-table-column label="会议时间" prop="meetingDate" align="center" width="100">
          </el-table-column>
          <el-table-column label="会议时长" prop="meetingDuration" align="center">
          </el-table-column>
          <el-table-column label="应到人数" prop="shouldPopulation" align="center">
          </el-table-column>
          <el-table-column label="实到人数" prop="practicalPopulation" align="center">
          </el-table-column>
          <el-table-column label="会议议题" prop="meetingAgenda" align="center" width="200">
            <template slot-scope="scope">
              <div v-html="scope.row.meetingAgenda"></div>
            </template>
          </el-table-column>
          <el-table-column label="比中关键字" prop="suitableKeyword" align="center" width="100">
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>

    <div class="pageBox">
      <pagination :total="Number(total)" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="ontabs(tabindex, 1)" />
    </div>
    <!-- ------------弹窗模块----------- -->
    <!-- 通用提示弹窗 -->
    <el-dialog :title="tipTitle" top="20vh" :visible.sync="tipOpen" append-to-body width="20%" :show-close="true"
      :closeOnClickModal="true" class="tipBox">
      <span>{{ tipText }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tipOpen = false" class="btn1">取 消</el-button>
        <el-button type="primary" @click="onExport" class="btn2">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 导出特殊详情单个提示弹窗 -->
    <el-dialog :title="tipTitle" top="20vh" :visible.sync="tipOpenDeta" append-to-body width="20%" :show-close="true"
      :closeOnClickModal="true" class="tipBox">
      <span>{{ tipText }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tipOpenDeta = false" class="btn1">取 消</el-button>
        <el-button type="primary" @click="onExportDeta" class="btn2">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 开发票详情弹窗 -->
    <el-dialog :visible.sync="dialogDetail" width="1100px" :close-on-click-modal="true" custom-class="ai_mainLog"
      :append-to-body="true">
      <div class="popupback">
        <div class="title" slot="title" style="height:40px;line-height:40px">
          <span>详情</span>
          <span style="float:right;padding-right:10px;color:#868b93;cursor: pointer;" @click="handleClose"><i
              class="el-icon-close"></i></span>
        </div>
        <div :span="24" class="tabright">
          <el-button size="mini" type="text" class="btnexport" @click="ontipsDeta" v-if="example.modelId == 400">
            <div class="text">
              <img style="margin-right: 5px; width: 1rem;height: 1rem;" src="~@/assets/images/ai/export.png"
                alt="" />导出比对结果
            </div>
          </el-button>
        </div>
        <div class="table_box" style="height: calc(100% - 140px)">
          <!-- {{tabindex == 1 && example.modelId == 400}} -->
          <el-scrollbar style="height: 100%" class="table_scrollbar" v-if="tabindex == 1 && example.modelId == 100">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table13" :data="detailData"
              v-loading="analysisDeType" :max-height="400">
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>
              <el-table-column label="员工姓名" prop="fullName" align="center">
              </el-table-column>
              <el-table-column label="所属部门" prop="deptName" align="center">
              </el-table-column>
              <el-table-column label="员工ID" prop="userId" align="center">
              </el-table-column>
              <el-table-column label="工单编号" prop="tripCode" align="center">
              </el-table-column>
              <el-table-column label="出发城市" prop="departureCity" align="center">
              </el-table-column>
              <el-table-column label="到达城市" prop="targetCity" align="center">
              </el-table-column>
              <el-table-column label="出差开始时间" prop="tripBeginDate" align="center">
              </el-table-column>
              <el-table-column label="出差结束时间" prop="tripEndDate" align="center">
              </el-table-column>
              <el-table-column label="说明" prop="remark" align="center">
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <el-scrollbar style="height: 100%" class="table_scrollbar" v-if="tabindex == 1 && example.modelId == 200">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table14" :data="detailData"
              v-loading="analysisDeType" :max-height="400">
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>
              <el-table-column label="员工姓名" prop="fullName" align="center">
              </el-table-column>
              <el-table-column label="所在部门" prop="deptName" align="center">
              </el-table-column>
              <el-table-column label="单据编号" prop="billCode" align="center">
              </el-table-column>
              <el-table-column label="城市类型" prop="cityType" align="center">
              </el-table-column>
              <el-table-column label="发票代码" prop="invoiceCode" align="center">
              </el-table-column>
              <el-table-column label="发票号码" prop="invoiceNumber" align="center">
              </el-table-column>
              <el-table-column label="销方名称" prop="marketName" align="center">
              </el-table-column>
              <el-table-column label="发票日期" prop="invoiceDate" align="center">
              </el-table-column>
              <el-table-column label="销方税号" prop="marketTfn" align="center">
              </el-table-column>
              <el-table-column label="销方地址电话" prop="marketAddress" align="center">
              </el-table-column>
              <el-table-column label="销方开户银行账号" prop="marketBankAccount" align="center">
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <el-scrollbar style="height: 100%" class="table_scrollbar" v-if="tabindex == 1 && example.modelId == 310">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table15" :data="detailData"
              v-loading="analysisType" :max-height="400">
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>

              <el-table-column label="用车人姓名" prop="fullName" align="center">
              </el-table-column>
              <el-table-column label="用车人ID" prop="userId" align="center" width="120">
              </el-table-column>
              <el-table-column label="上车地市" prop="startCity" align="center">
              </el-table-column>
              <el-table-column label="上车地点" prop="startAddress" align="center" width="140">
              </el-table-column>
              <el-table-column label="下车地市" prop="endCity" align="center" width="140">
              </el-table-column>
              <el-table-column label="下车地点" prop="endAddress" align="center" width="140">
              </el-table-column>
              <el-table-column label="实际开始时间" prop="startDate" align="center" width="140">
              </el-table-column>
              <el-table-column label="实际结束时间" prop="endDate" align="center">
              </el-table-column>
              <el-table-column label="用车时长" prop="useCarDuration" align="center">
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <el-scrollbar style="height: 100%" class="table_scrollbar" v-if="tabindex == 1 && example.modelId == 320">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table16" :data="detailData"
              v-loading="analysisType" :max-height="400">
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>
              <el-table-column label="用车人姓名" prop="fullName" align="center" width="120">
              </el-table-column>
              <el-table-column label="用车人ID" prop="userId" align="center" width="120">
              </el-table-column>
              <el-table-column label="上车地市" prop="startCity" align="center" width="140">
              </el-table-column>
              <el-table-column label="上车地点" prop="startAddress" align="center" width="140">
              </el-table-column>
              <el-table-column label="下车地市" prop="endCity" align="center" width="140">
              </el-table-column>
              <el-table-column label="下车地点" prop="endAddress" align="center" width="140">
              </el-table-column>
              <el-table-column label="实际开始日期" prop="startDate" align="center" width="140">
              </el-table-column>
              <el-table-column label="实际结束日期" prop="endDate" align="center" width="140">
              </el-table-column>
              <el-table-column label="用车时长" prop="useCarDuration" align="center" width="140">
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <el-scrollbar style="height: 100%" class="table_scrollbar" v-if="tabindex == 1 && example.modelId == 340">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table17" :data="detailData"
              v-loading="analysisDeType" :max-height="400">
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>
              <el-table-column label="员工姓名" prop="fullName" align="center">
              </el-table-column>
              <el-table-column label="员工编号" prop="employee" align="center">
              </el-table-column>
              <el-table-column label="员工ID" prop="userId" align="center">
              </el-table-column>
              <el-table-column label="所属部门" prop="department" align="center">
              </el-table-column>
              <el-table-column label="出差申请单号" prop="appluCode" align="center">
              </el-table-column>
              <el-table-column label="出行方式" prop="tripType" align="center">
              </el-table-column>
              <el-table-column label="出差开始时间" prop="startDate" align="center">
              </el-table-column>
              <el-table-column label="出差结束时间" prop="endDate" align="center">
              </el-table-column>
              <el-table-column label="出发地" prop="startAddress" align="center">
              </el-table-column>
              <el-table-column label="目的地" prop="endAddress" align="center">
              </el-table-column>
              <el-table-column label="出差事由" prop="remark" align="center">
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <el-scrollbar style="height: 100%" class="table_scrollbar" v-if="tabindex == 1 && example.modelId == 400">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table102" :data="detailData"
              v-loading="analysisDeType" :max-height="400">
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>
              <el-table-column label="支部名称" prop="branchName" align="center" width="160">
              </el-table-column>
              <el-table-column label="省公司发文" prop="companyDispatch" align="center" width="160">
              </el-table-column>
              <el-table-column label="学习要求和内容" prop="studyContent" align="center" width="200">
              </el-table-column>
              <el-table-column label="会议名称" prop="meetingName" align="center">
              </el-table-column>
              <el-table-column label="会议类型" prop="meetingType" align="center">
              </el-table-column>
              <el-table-column label="会议时间" prop="meetingDate" align="center" width="100">
              </el-table-column>
              <el-table-column label="会议时长" prop="meetingDuration" align="center">
              </el-table-column>
              <el-table-column label="应到人数" prop="shouldPopulation" align="center">
              </el-table-column>
              <el-table-column label="实到人数" prop="practicalPopulation" align="center">
              </el-table-column>
              <el-table-column label="会议议题" prop="meetingAgenda" align="center" width="200">
                <template slot-scope="scope">
                  <div v-html="scope.row.meetingAgenda"></div>
                </template>
              </el-table-column>
              <el-table-column label="比中关键字" prop="suitableKeyword" align="center" width="100">
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <!-- 页尾 -->
        </div>
        <div class="pageBox" style="height:40px;line-height:40px">
          <pagination :total="Number(queryParamsDeta.total)" :page.sync="queryParamsDeta.pageNum"
            :limit.sync="queryParamsDeta.pageSize" @pagination="handleDetail" />
        </div>
      </div>
    </el-dialog>

    <!-- 销方风险标记详情弹窗 -->
    <el-dialog :visible.sync="SignOpen" width="1200px" :close-on-click-modal="false" custom-class="ai_mainLog"
      :append-to-body="true">
      <div class="popupback">
        <div class="title" slot="title" style="height:40px;line-height:40px">
          <span>销方风险标记</span>
          <span style="float:right;padding-right:10px;color:#868b93;cursor: pointer;" @click="handleClose"><i
              class="el-icon-close"></i></span>
        </div>

        <div class="table_box" style="height: calc(100% - 100px)">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px" style="height:80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="所属地市：" prop="companyName">
                  <el-select v-model="SignParams.hotelCity" placeholder="请选择所属地市" clearable size="small"
                    :popper-append-to-body="false" @change="onSelects">
                    <el-option v-for="dict in hotelCityOption" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictLabel" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="销方名称：" prop="marketName">
                  <el-input v-model="SignParams.marketName" placeholder="请输入销方名称" clearable size="small"
                    @change="onSelects" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="风险：" prop="year">
                  <el-select v-model="SignParams.riskLevel" placeholder="请选择风险" clearable size="small"
                    :popper-append-to-body="false" @change="onSelects">
                    <el-option v-for="dict in levelNameOptions" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictValue" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-row class="tabright" style="height:40px;line-height:40px">
            <el-button size="mini" type="text" class="btnexport" @click="handleAdd('add')">
              <div class="text">新增
              </div>
            </el-button>
            <el-button size="mini" type="text" class="btnexport" @click="handlePl">
              <div class="text">批量标记
              </div>
            </el-button>
          </el-row>
          <el-scrollbar style="height:calc(100% - 140px)" class="table_scrollbar">
            <el-table stripe header-cell-class-name="tableHeaderClass" key="table13" :data="SignData"
              v-loading="analysisDeType" @selection-change="handleSelectionChange" ref="multipleTable">
              <el-table-column type="selection" width="55" align="left" />
              <el-table-column label="序号" type="index" width="90" align="left">
                <template slot-scope="scope">
                  <div class="indexIcon">{{ scope.$index + 1 }}</div>
                </template>
              </el-table-column>
              <el-table-column label="所属地市" prop="hotelCity" align="center">
                <template slot-scope="scope">
                  {{ scope.row.hotelCity }}
                </template>
              </el-table-column>
              <el-table-column label="销方名称" prop="marketName" align="center">
              </el-table-column>
              <el-table-column label="风险等级" prop="riskLevel" align="center">
                <template slot-scope="scope">
                  {{ scope.row.riskLevel | filterAll(levelNameOptions) }}
                </template>
              </el-table-column>
              <el-table-column label="更新时间" prop="updatedTime" align="center">
              </el-table-column>
              <el-table-column label="更新人" prop="updatedByName" align="center">
              </el-table-column>
              <el-table-column label="操作" prop="" align="center">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" class="" @click="handleAdd(scope.row)">修改</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-scrollbar>
          <!-- 页尾 -->
        </div>
        <div class="pageBox" style="height:40px;line-height:40px">
          <pagination :total="Number(Signtotal)" :page.sync="SignParams.pageNum" :limit.sync="SignParams.pageSize"
            @pagination="onSelects('fan')" />
        </div>
      </div>
    </el-dialog>

    <!-- 新增修改弹窗 -->
    <el-dialog :visible.sync="addOpen" width="600px" :close-on-click-modal="false" custom-class="ai_mainLog addbox"
      :append-to-body="true" v-if="addOpen">
      <div class="popupback" style="height:22rem">
        <div class="title" slot="title" style="height:40px;line-height:40px">
          <span>{{ stitle }}</span>
          <span style="float:right;padding-right:10px;color:#868b93;cursor: pointer;" @click="addOpen = false"><i
              class="el-icon-close"></i></span>
        </div>
        <div class="table_box">
          <el-form :model="addParams" ref="addForm" :inline="true" label-width="120px" :rules="addrule">
            <el-row>
              <el-col :span="24">
                <el-form-item label="所属地市：" prop="">
                  <el-select v-model="addParams.hotelCity" placeholder="请选择所属地市" clearable size="small"
                    :popper-append-to-body="false">
                    <el-option v-for="dict in hotelCityOption" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictLabel" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="销方名称：" prop="marketName">
                  <el-input v-model="addParams.marketName" placeholder="请输入销方名称" clearable size="small"
                    maxlength="200" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="风险：" prop="riskLevel">
                  <el-select v-model="addParams.riskLevel" placeholder="请选择风险" clearable size="small"
                    :popper-append-to-body="false">
                    <el-option v-for="dict in levelNameOptions" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictValue" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button class="btn_primary" @click="submitFileForm">确 定</el-button>
            <el-button class="nomal" @click="addOpen = false">取 消</el-button>
          </div>
        </div>
      </div>
    </el-dialog>


    <!-- 批量标记弹窗 -->
    <el-dialog :visible.sync="plOpen" width="600px" :close-on-click-modal="false" custom-class="ai_mainLog addbox"
      :append-to-body="true" v-if="plOpen">
      <div class="popupback">
        <div class="title" slot="title" style="height:40px;line-height:40px">
          <span>批量标记</span>
          <span style="float:right;padding-right:10px;color:#868b93;cursor: pointer;" @click="plOpen = false"><i
              class="el-icon-close"></i></span>
        </div>
        <div class="table_box" style="height: calc(100% - 40px);">
          <el-form :model="addParams1" ref="plForm" :inline="true" label-width="120px" :rules="addrule1">
            <el-row>
              <el-col :span="24">
                <el-form-item label="所属地市：" prop="city">
                  <el-select v-model="addParams1.city" placeholder="请选择所属地市" clearable size="small"
                    :popper-append-to-body="false" :disabled="true">
                    <el-option v-for="dict in hotelCityOption" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictLabel" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="风险：" prop="riskLevel">
                  <el-select v-model="addParams1.riskLevel" placeholder="请选择风险" clearable size="small"
                    :popper-append-to-body="false">
                    <el-option v-for="dict in levelNameOptions" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictValue" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button class="btn_primary" @click="plForm">确 定</el-button>
            <el-button class="nomal" @click="plOpen = false">取 消</el-button>
          </div>
        </div>
      </div>
    </el-dialog>

    <!-- 协议酒店维护 -->
    <el-dialog :visible.sync="busOpent" width="1300px" :close-on-click-modal="false" custom-class="ai_mainLog"
      :append-to-body="true" v-if="busOpent">
      <div class="title" slot="title">
        <span>协议酒店维护</span>
        <span style="float:right;padding-right:10px;color:#868b93;cursor: pointer;" @click="busOpent = false"><i
            class="el-icon-close"></i></span>
      </div>
      <img class="background" src="~@/assets/images/ai/BG.png" alt="" />
      <div class="table_box tanc" style="height: 74vh">
        <!-- <el-scrollbar style="height: 90%"> -->
        <el-form :model="busParams" ref="busForm" :inline="true" label-width="140px" style="height:100px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="所属地市：" prop="companyName">
                <el-select v-model="busParams.hotelCity" placeholder="请选择所属地市" clearable size="small"
                  :popper-append-to-body="false" @change="onSelectsDeta">
                  <el-option v-for="dict in hotelCityOption" :key="dict.dictValue" :label="dict.dictLabel"
                    :value="dict.dictLabel" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="酒店名称：" prop="hotelName">
                <el-input v-model="busParams.hotelName" placeholder="请输入酒店名称" clearable size="small"
                  @change="onSelectsDeta" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="销方名称：" prop="marketName">
                <el-input v-model="busParams.marketName" placeholder="请输入销方名称" clearable size="small"
                  @change="onSelectsDeta" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="销方税号：" prop="marketTfn">
                <el-input v-model="busParams.marketTfn" placeholder="请输入销方税号" clearable size="small"
                  @change="onSelectsDeta" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="销方地址电话：" prop="marketAddress">
                <el-input v-model="busParams.marketAddress" placeholder="请输入销方地址电话" clearable size="small"
                  @change="onSelectsDeta" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="销方开户银行账号：" prop="marketBankAccount">
                <el-input v-model="busParams.marketBankAccount" placeholder="请输入销方开户银行账号" clearable size="small"
                  @change="onSelectsDeta" />
              </el-form-item>
            </el-col>

          </el-row>
        </el-form>
        <el-row class="tabright" style="">
          <el-button class="btn_primary download_btn" icon="el-icon-download" @click="downloadTmplate">
            文件模板下载
          </el-button>
          <el-button size="mini" type="text" class="btnexport" @click="busAdd">
            <div class="text">新增
            </div>
          </el-button>
          <el-button size="mini" type="text" class="btnexport" @click="importData">
            <div class="text">导入
            </div>
          </el-button>
          <el-button size="mini" type="text" class="btnexport" @click="busDelete">
            <div class="text">批量删除
            </div>
          </el-button>
          <el-button size="mini" type="text" class="btnexport" @click="busExport">
            <div class="text">导出
            </div>
          </el-button>
        </el-row>
        <div class="flex msg_box" v-show="showMsg">
          <img class="icon" src="~@/assets/images/ai/Icon.png" alt="" />
          <span>{{ '文件解析完成。本次共上传'+ (Number(recordData.errorNumber) + Number(recordData.successNumber))+'条数据，'}}</span>
          <span></span>
          <span>{{ Number(recordData.successNumber)+'条数据上传成功，'}}</span>
          <span>{{ Number(recordData.errorNumber) +'条数据上传失败'}}</span>
          <img class="icon cancel" src="~@/assets/images/ai/cancel-icon.png" alt="" @click="showMsg = false" />
        </div>
        <el-scrollbar style="height:calc(100% - 290px)" class="table_scrollbar">
          <el-table stripe header-cell-class-name="tableHeaderClass" key="table13" :data="busData"
            v-loading="analysisDeType" @selection-change="handleSelectionBus" ref="multipleTable">
            <el-table-column type="selection" width="55" align="left" />
            <el-table-column label="序号" type="index" width="90" align="left">
              <template slot-scope="scope">
                <div class="indexIcon">{{ scope.$index + 1 }}</div>
              </template>
            </el-table-column>
            <el-table-column label="所属地市" prop="hotelCity" align="center" width="100">
              <template slot-scope="scope">
                {{ scope.row.hotelCity }}
              </template>
            </el-table-column>
            <el-table-column label="酒店名称" prop="hotelName" align="center">
            </el-table-column>
            <el-table-column label="销方名称" prop="marketName" align="center">
            </el-table-column>
            <el-table-column label="销方税号" prop="marketTfn" align="center">
            </el-table-column>
            <el-table-column label="销方地址电话" prop="marketAddress" align="center">
            </el-table-column>
            <el-table-column label="销方开户银行账号" prop="marketBankAccount" align="center" width="160">
            </el-table-column>
            <el-table-column label="操作" prop="" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="text" class="" @click="busAdd(scope.row)">修改</el-button>
                <el-button size="mini" type="text" class="" @click="busDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
        <div class="pageBox" style="height:40px;line-height:40px">
          <pagination :total="Number(bustotal)" :page.sync="busParams.pageNum" :limit.sync="busParams.pageSize"
            @pagination="onSelects('fan')" />
        </div>
        <!-- </el-scrollbar> -->
      </div>
    </el-dialog>
    <!-- 新增协议酒店弹窗 -->
    <el-dialog :visible.sync="addBusOpen" width="1000px" :close-on-click-modal="false"
      custom-class="ai_mainLog addbox busbox" :append-to-body="true" v-if="addBusOpen">
      <div class="popupback" style="height:26rem">
        <div class="title" slot="title" style="height:40px;line-height:40px">
          <span>{{ stitle }}</span>
          <span style="float:right;padding-right:10px;color:#868b93;cursor: pointer;" @click="addBusOpen = false"><i
              class="el-icon-close"></i></span>
        </div>
        <div class="table_box">
          <el-form :model="busAddParams" ref="busAddForm" :inline="true" label-width="140px" :rules="busAddrule">
            <el-row>
              <el-col :span="12">
                <el-form-item label="所属地市：" prop="hotelCity">
                  <el-select v-model="busAddParams.hotelCity" placeholder="请选择所属地市" clearable size="small"
                    :popper-append-to-body="false">
                    <el-option v-for="dict in hotelCityOption" :key="dict.dictValue" :label="dict.dictLabel"
                      :value="dict.dictLabel" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="酒店名称：" prop="hotelName">
                  <el-autocomplete v-model="busAddParams.hotelName" :fetch-suggestions="querySearchAsync"
                    placeholder="请输入酒店名称" :popper-append-to-body="false" @select="handleSelect"></el-autocomplete>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="销方名称：" prop="">
                  <el-input v-model="busAddParams.marketName" placeholder="请输入销方名称" clearable size="small" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="销方税号：" prop="marketTfn">
                  <el-input v-model="busAddParams.marketTfn" placeholder="请输入销方税号" clearable size="small" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="销方地址电话：" prop="marketAddress">
                  <el-input v-model="busAddParams.marketAddress" placeholder="请输入销方地址电话" clearable size="small"
                    style="width:100%;" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="销方开户银行账号：" prop="marketBankAccount">
                  <el-input v-model="busAddParams.marketBankAccount" placeholder="请输入销方开户银行账号" clearable size="small"
                    style="width:100%;" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button class="btn_primary" @click="submitbusForm">确 定</el-button>
            <el-button class="nomal" @click="addBusOpen = false">取 消</el-button>
          </div>
        </div>
      </div>
    </el-dialog>
    <!-- 协议酒店提示弹窗 -->
    <el-dialog :title="tipTitle" top="20vh" :visible.sync="tipOpenBus" append-to-body width="20%" :show-close="true"
      :closeOnClickModal="true" class="tipBox">
      <span>{{ tipText }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tipOpenBus = false" class="btn1">取 消</el-button>
        <el-button type="primary" @click="onBusDele" class="btn2">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 协议酒店上传 -->
    <!-- 上传文件 -->
    <el-dialog :visible.sync="upload.open" width="400px" :append-to-body="true" :modal-append-to-body="false">
      <div class="title" slot="title">
        <span>上传文件</span>
      </div>
      <img style="width: 460px; height: 460px" class="background" src="~@/assets/images/ai/BG.png" alt="" />
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url"
        :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess"
        :auto-upload="false" :data="upload.params" drag v-if="upload.open">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button class="btn_primary" @click="submitUploadForm">确 定</el-button>
        <el-button class="nomal" @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 协议酒店导出弹窗 -->
    <el-dialog :title="tipTitle" top="20vh" :visible.sync="tipOpenBusiness" append-to-body width="20%"
      :show-close="true" :closeOnClickModal="true" class="tipBox">
      <span>{{ tipText }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tipOpenBusiness = false" class="btn1">取 消</el-button>
        <el-button type="primary" @click="onBusiness" class="btn2">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>

import { getToken } from "@/utils/auth";
import {
  getJSON,
  aiResultHotelFapiaoRiskInfo,
  RiskInfoAdd,
  RiskInfoEdit,
  analysisDataList,
  getModelData,
  aiAgreementHotelInfo,
  aiAgreementHotelAdd,
  getAiInvoiceInfoList,
  aiAgreementHotelaEdit,
  aiAgreementHotelaDele,
  aiAgreementHotelaExport,
} from "@/api/aiXuncha/aiBaseInfo.js";
import modelDataArr from "./modelData";
export default {
  props: {
    queryParam: {
      type: Object,
      default: () => {
      },
    },
    timeId: {
      type: String,
      default: '',
    },
    example: {
      type: Object,
      default: () => {
      },
    },
    modelData: {
      type: Array,
    },
    heigType: {
      type: Boolean,
    }
  },
  watch: {
    queryParam: {
      handler (nval, oval) {
        this.init();
      },
      deep: true,
    },
    example: {
      handler (nval, oval) {
        console.log("监听example")
        this.init();
      },
      deep: true,
    },
    // 历史分析结果列表查询
    timeId: {
      handler (nval, oval) {
        this.initHistory();
      },
      immediate: true
    },
    // 历史分析结果列表查询
    heigType: {
      handler (nval, oval) {
        this.heightComp()
      },
      deep: true,
    },
  },
  data () {
    return {
      tabMaxHeight: 200,
      // modelData: "",
      regx: /(（.+?）)/g,
      true: true,
      false: false,
      dialogDetail: false,
      tipOpen: false,
      tipOpenDeta: false,
      tipOpenBus: false,
      tabindex: 1,
      tabType: false,
      analysisData: [],
      analysisType: false,
      total: 0,
      taxindexTotal: 0,
      tipTitle: "标题",
      tipText: "内容",
      customColor: "linear-gradient(180deg, #041141 0%, #021D48 50%, #002259 100%)",
      queryParams: {
        companyName: null,
        xunchaLunciId: null,
        year: null,
        xunchaPiciId: null,
        taskId: null,
        pageSize: 10,
        pageNum: 1,
      },
      //详情弹窗
      detailData: [],
      analysisDeType: false,
      queryParamsDeta: {
        total: 0,
        pageNum: 1,
        pageSize: 10
      },
      //消方风险标记
      SignOpen: false,
      SignParams: {
        hotelCity: null,
        marketName: null,
        riskLevel: null,
        pageNum: 1,
        pageSize: 10
      },
      addParams: {
        hotelCity: null,
        marketName: null,
        riskLevel: null,
      },
      addParams1: {
        city: null,
        riskLevel: null,
      },
      hotelCityOption: [],
      levelNameOptions: [{ dictValue: "1", dictLabel: '1级' }, { dictValue: "2", dictLabel: '2级' }, {
        dictValue: "3",
        dictLabel: '3级'
      }],
      SignData: [],
      Signtotal: 0,
      addOpen: false,
      stitle: "新增风险标记",
      ids: [],
      invoiceRiskId: "",
      plOpen: false,
      addrule: {
        hotelCity: [
          {
            required: true,
            message: "请选择所属地市",
            trigger: "change",
          },
        ],
        marketName: [
          {
            required: true,
            message: "请输入销方名称",
            trigger: "change",
          },
        ],
        riskLevel: [
          {
            required: true,
            message: "请输入销方名称",
            trigger: "change",
          },
        ],
      },
      addrule1: {
        city: [
          {
            required: true,
            message: "请选择所属地市1",
            trigger: "change",
          },
        ],

        riskLevel: [
          {
            required: true,
            message: "请输入销方名称",
            trigger: "change",
          },
        ],
      },
      busAddrule: {
        hotelCity: [
          {
            required: true,
            message: "请选择所属地市",
            trigger: "change",
          },
        ],
        hotelName: [
          {
            required: true,
            message: "请填写酒店名称",
            trigger: "change",
          },
        ],
      },
      newModelData: [],
      busOpent: false,
      busParams: {
        hotelCity: null,
        hotelName: null,
        marketName: null,
        marketTfn: null,
        marketAddress: null,
        marketBankAccount: null,
        pageNum: 1,
        pageSize: 10
      },
      busData: [],
      bustotal: 0,
      addBusOpen: false,
      busAddParams: {
        hotelCity: null,
        hotelName: null,
        marketName: null,
        marketTfn: null,
        marketAddress: null,
        marketBankAccount: null,
      },
      // 选中数组
      idsBus: [],
      // 非单个禁用
      singleBus: true,
      // 非多个禁用
      multipleBus: true,

      showMsg: false,

      recordData: {
        recordId: '',
      },
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
        //   // 上传的地址
        url:
          CONFIG.url + process.env.VUE_APP_BASE_API + "/ai/aiAgreementHotelInfo/importData",
        params: {},
      },
      tipOpenBusiness: false,
    };
  },

  filters: {
    numberFilt (nums) {
      if (nums) {
        let num = nums.toString().split(".");  // 分隔小数点
        let arr = num[0].split("").reverse();  // 转换成字符数组并且倒序排列
        let res = [];
        for (let i = 0, len = arr.length; i < len; i++) {
          if (i % 3 === 0 && i !== 0) {
            res.push(",");   // 添加分隔符
          }
          res.push(arr[i]);
        }
        res.reverse(); // 再次倒序成为正确的顺序
        if (num[1]) {  // 如果有小数的话添加小数部分
          res = res.join("").concat("." + num[1]);
        } else {
          res = res.join("");
        }
        return res;
      } else {
        return "";
      }

    },

    filterAll (val, optionArr) {
      var arr = optionArr.filter((item) => item.dictValue === val);
      if (!arr.length) return "";
      return arr[0].dictLabel;
    },
  },
  mounted () {

  },
  created () {
    this.getDicts("ss_city").then(res => {
      this.hotelCityOption = res.data ? res.data : []
    })
    this.init();
  },
  methods: {
    onSelectsDeta (data) {
      if (data != 'fan') {
        this.busParams.pageNum = 1
        this.busParams.pageSize = 10
      }
      aiAgreementHotelInfo(this.busParams).then(res => {
        this.busData = res.rows;
        this.bustotal = res.total;
      })
    },
    busOpen () {
      this.busOpent = true;
      aiAgreementHotelInfo(this.busParams).then(res => {
        this.busData = res.rows;
        this.bustotal = res.total;
      })
    },
    busAdd (row) {
      console.log(row)
      if (row.id) {
        this.stitle = "修改协议酒店"
        this.addBusOpen = true
        this.busAddParams = {
          id: row.id,
          hotelCity: row.hotelCity,
          hotelName: row.hotelName,
          marketName: row.marketName,
          marketTfn: row.marketTfn,
          marketAddress: row.marketAddress,
          marketBankAccount: row.marketBankAccount,
        }
        return
      }
      console.log("新增协议酒店")
      this.stitle = "新增协议酒店"
      this.addBusOpen = true
    },
    busDelete (row) {
      if (row.id == undefined && this.multipleBus) {
        this.$message.warning("请选择删除项")
        return
      }
      let that = this;
      this.idsBus = row.id || this.idsBus.toString();
      this.tipOpenBus = true
      this.tipText = "是否确认删除数据项?"

    },
    onBusDele () {
      let ids = this.idsBus;
      console.log(ids)
      aiAgreementHotelaDele({ id: ids }).then(res => {
        this.$message.success(res.msg);
        this.onSelectsDeta();
        this.tipOpenBus = false;
      })
    },
    importData () {
      this.upload.open = true
    },
    busExport () {
      let that = this;
      this.idsBus = this.idsBus.toString();
      this.tipOpenBusiness = true
      this.tipText = "是否确认导出数据项?"
    },
    onBusiness () {
      aiAgreementHotelaExport({ id: this.idsBus }).then(res => {
        this.tipOpenBusiness = false;
        this.download(res.msg);
      })
    },
    handleSelectionBus (selection) {
      this.idsBus = selection.map(item => item.id)
      this.singleBus = selection.length !== 1
      this.multipleBus = !selection.length
    },
    querySearchAsync (queryString, cb) {
      getAiInvoiceInfoList({ hotelName: queryString ? queryString : '' }).then(res => {
        let results = [];
        results = res.data.map(item => {
          item.value = item.hotelName
          return item
        })
        // cb(res.data);
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          console.log(results)
          cb(results);
        }, 2000 * Math.random());
      })
    },
    handleSelect (item) {
      console.log(item)
      this.busAddParams = {
        hotelCity: item.hotelCity,
        hotelName: item.hotelName,
        marketName: item.marketName,
        marketTfn: item.marketTfn,
        marketAddress: item.marketAddress,
        marketBankAccount: item.marketBankAccount,
      }
    },
    submitbusForm () {
      let that = this;
      this.$refs["busAddForm"].validate((valid) => {
        if (valid) {
          if (this.busAddParams.id) {
            aiAgreementHotelaEdit(that.busAddParams).then(res => {
              that.addBusOpen = false;
              that.$message.success(res.msg);
              that.onSelectsDeta();
              that.busAddParams = {
                hotelCity: null,
                hotelName: null,
                marketName: null,
                marketTfn: null,
                marketAddress: null,
                marketBankAccount: null,
              };
            })
          } else {
            aiAgreementHotelAdd(that.busAddParams).then(res => {
              that.addBusOpen = false;
              that.$message.success(res.msg);
              that.onSelectsDeta();
              that.busAddParams = {
                hotelCity: null,
                hotelName: null,
                marketName: null,
                marketTfn: null,
                marketAddress: null,
                marketBankAccount: null,
              };
            })
          }
        }
      })
    },
    downloadTmplate () {
      getJSON("ai/aiAgreementHotelInfo/importTemplate").then((response) => {
        this.download(response.msg);
      });
    },
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
      this.loading = this.openLoading(null, 300000);
    },
    handleFileSuccess (res) {
      this.recordData = {};
      console.log(res)
      if (res.code == 200) {
        this.$message.success(res.msg);
        this.showMsg = true;
        this.recordData = res.data;
        this.loading.close();
        this.$refs.upload.clearFiles();
        this.upload.open = false;
        this.upload.isUploading = false;
        this.onSelectsDeta();
        //   // this.queryGetModelData();
        // this.getList();
      } else {
        this.$message.warning(res.msg);
        this.loading.close();
        this.$refs.upload.clearFiles();
        this.upload.open = false;
        this.upload.isUploading = false;
      }
    },
    submitUploadForm () {
      this.loading = this.openLoading(null, 300000);
      this.$refs.upload.submit();
      // this.onSelectsDeta();
    },
    handlePl () {
      if (this.ids.length == 0) {
        this.$message.warning("请选择销方")
        return
      }
      this.plOpen = true
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.addParams1.city = "";
      let type = true;
      selection.forEach((item) => {
        if (this.addParams1.city) {
          if (this.addParams1.city != item.hotelCity) {
            type = false
            return;
          }
        } else {
          this.addParams1.city = item.hotelCity
        }
      })
      if (type) {
        this.ids = selection.map(item => item.invoiceRiskId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      } else {
        this.$message.warning("请选择相同地市的销方")
        this.$refs.multipleTable.clearSelection();
      }
    },
    plForm () {
      let that = this;
      this.$refs["plForm"].validate((valid) => {
        if (valid) {
          let addParams1 = JSON.parse(JSON.stringify(this.addParams1))
          addParams1.invoiceRiskId = that.ids.toString();
          RiskInfoEdit(addParams1).then(res => {
            that.$message.success(res.msg);
            that.plOpen = false;
            that.addParams1 = {
              city: null,
              riskLevel: null,
            }
            that.invoiceRiskId = "";
            that.ids = [];
            that.onSelects();
          })
        }
      })
    },
    submitFileForm () {
      let that = this;
      this.$refs["addForm"].validate((valid) => {
        if (valid) {
          if (!that.invoiceRiskId) {
            RiskInfoAdd(this.addParams).then(res => {
              that.$message.success(res.msg);
              that.addOpen = false;
              that.addParams = {
                hotelCity: null,
                marketName: null,
                riskLevel: null,
              }
              that.onSelects();
            })
          } else {

            let addParams = JSON.parse(JSON.stringify(this.addParams))
            addParams.invoiceRiskId = that.invoiceRiskId;
            RiskInfoEdit(addParams).then(res => {
              that.$message.success(res.msg);
              that.addOpen = false;
              that.addParams = {
                hotelCity: null,
                marketName: null,
                riskLevel: null,
              }
              that.invoiceRiskId = "";
              that.onSelects();
            })
          }

        }
      })
    },
    handleAdd (data) {
      this.stitle = "新增风险标记";
      this.addParams = {
        hotelCity: null,
        marketName: null,
        riskLevel: null,
      }
      if (data != 'add') {
        this.stitle = "修改风险标记"
        this.invoiceRiskId = data.invoiceRiskId ? data.invoiceRiskId : '';
        this.addParams = {
          hotelCity: data.hotelCity,
          marketName: data.marketName,
          riskLevel: data.riskLevel,
        }
      }

      this.addOpen = true;
    },
    onSelects (data) {
      if (data != 'fan') {
        this.SignParams.pageNum = 1
        this.SignParams.pageSize = 10
      }
      aiResultHotelFapiaoRiskInfo(this.SignParams).then(res => {
        this.SignData = res.rows;
        this.Signtotal = res.total;
      })
    },
    onxfOpen () {
      this.SignOpen = true;
      aiResultHotelFapiaoRiskInfo(this.SignParams).then(res => {
        this.SignData = res.rows;
        this.Signtotal = res.total;
      })
    },
    handleDetail (key, value) {
      this.dialogDetail = true;
      let that = this;
      this.analysisDeType = true;
      // this.queryParamsDeta
      let queryParam = JSON.parse(JSON.stringify(this.queryParam));
      delete queryParam.pageSize
      delete queryParam.pageNum
      this.queryParamsDeta = { ...this.queryParamsDeta, }
      if (key === 'quantity') {
        // 稽核无出差文件的集中度
        this.queryParamsDeta.timeId = value.timeId
        this.queryParamsDeta.companyName = value.companyName
        this.queryParamsDeta.deptName = value.deptName
        this.queryParamsDeta.hierarchy = value.hierarchy
      } else if (key === 'invoiceCount') {
        // 分析差旅酒店所开发票集中度模型
        this.queryParamsDeta.timeId = value.timeId
        this.queryParamsDeta.invoiceCode = value.invoiceCode
        this.queryParamsDeta.marketTfn = value.marketTfn
      } else if (key === 'canClaimDays') {
        // 既派车又报销差旅补贴
        this.queryParamsDeta.timeId = value.timeId
        this.queryParamsDeta.company = value.company
        this.queryParamsDeta.fullName = value.fullName
        this.queryParamsDeta.startDate = value.tripBeginDate
        this.queryParamsDeta.endDate = value.tripEndDate
      } else if (key === 'submitDays') {
        // 出差半天却报销全天伙食补贴问题
        this.queryParamsDeta.timeId = value.timeId
        this.queryParamsDeta.fullName = value.fullName
      } else if (key === 'frequency') {
        // 重复报账问题分析
        this.queryParamsDeta.timeId = value.timeId
        this.queryParamsDeta.fullName = value.fullName
        this.queryParamsDeta.company = value.company
      } else if (key === 'studyCount') {
        // 议题及关键字
        this.queryParamsDeta.resultId = value.id
        this.queryParamsDeta.isStudy = ""
      } else if (key === 'noStudyCount') {
        // 被巡察单位学习记录
        this.queryParamsDeta.resultId = value.id
        this.queryParamsDeta.isStudy = "0"
      } else {
        if (key && value) {
          this.queryParamsDeta[key] = value;
        }
      }
      // { pageSize: this.queryParamsDeta.pageSize, pageNum: this.queryParamsDeta.pageNum }
      getJSON(this.example.api.listInfo, this.queryParamsDeta).then(res => {
        that.queryParamsDeta.total = res.total;
        that.analysisDeType = false;
        that.detailData = res.rows;
      })
    },
    handleClose () {
      this.dialogDetail = false;
      this.SignOpen = false;
      this.detailData = [];
      this.queryParamsDeta = {
        total: 0,
        pageNum: 1,
        pageSize: 10
      };
    },
    heightComp () {
      if (this.timeId) {
        this.tabMaxHeight = 400;
      } else {
        if(this.heigType){
          this.tabMaxHeight = 580;
        }else{
          this.tabMaxHeight = 200;
        }
      }
    },
    init () {
      console.log("执行init",this.timeId)
      // this.modelData = this.modelData || modelDataArr;
      this.queryParams = { ...this.queryParams, ...this.queryParam }
      this.modelData = modelDataArr.filter((item) => {
        return this.example.modelData.some((ele) => ele == item.dataKey);
      });
      // 如果比对完成,且所有数据都下拉选项都有,才执行分析结果
      if (this.example.api.listCollon) {
        this.analysisFunc(this.example.api.listCollon);
        this.tabType = true;
        this.tabindex = 1;
      } else {
        this.analysisFunc(this.example.api.listInfo);
        this.tabType = false;
        this.tabindex = 2;
      }
    },
    initHistory () {
      if (!this.timeId) {
        return
      }
      this.heightComp()
      // 如果比对完成,且所有数据都下拉选项都有,才执行分析结果
      if (this.example.api.listCollon) {
        this.historyAnalysisFunc(this.example.api.listCollon);
        this.tabType = true;
        this.tabindex = 1;
      } else {
        this.historyAnalysisFunc(this.example.api.listInfo);
        this.tabType = false;
        this.tabindex = 2;
      }
    },
    //分析结果
    analysisFunc (url) {
      let that = this;
      this.analysisType = true;
      // this.queryParams
      // { pageSize: this.queryParams.pageSize, pageNum: this.queryParams.pageNum }
      let params = {
        queryCompanyName: this.queryParams.companyName,
        queryTaskId: this.queryParams.taskId,
        pageSize: this.queryParams.pageSize,
        pageNum: this.queryParams.pageNum,
      }
      getJSON(url, params).then(res => {
        that.total = res.total;
        that.analysisType = false;
        that.analysisData = res.rows;
        that.taxindexTotal = res.total;
      })
      let newModelData = [];
      this.modelData.map((item, index) => {
        newModelData.push({
          comparisonName: item.comparisonName,
          dataKey: item.dataKey,
          dataNumber: item.dataNumber,
        });
      })
      console.log("00-------））-", this.modelData, newModelData)
      this.getLastModelData(this.modelData);
      // this.getTotalTab(this.example.api.listInfo);
    },


    // 查询数据
    getLastModelData (newModelData) {
      let that = this;
      let param = {
        companyName: this.queryParams.companyName,
        modelId: this.example.modelId,
        taskId: this.queryParams.taskId,
      }
      console.log("这里改分析结果数量")
      let func = this.timeId?getModelData(param):analysisDataList(param)
      func.then((res) => {
        let dataList = [];
        dataList = newModelData;
        dataList.map((item, index) => {
          if (item.dataKey == 'attendanceLength') {
            item.dataNumber = res.data.attendanceLength;
          } else if (item.dataKey == "businessTripLength") {
            item.dataNumber = res.data.businessTripLength;
          } else if (item.dataKey == 'businessTripDetailLength') {
            item.dataNumber = res.data.businessTripDetailLength;
          } else if (item.dataKey == 'settlementLength') {
            item.dataNumber = res.data.settlementLength;
          } else if (item.dataKey == 'invoiceLength') {
            item.dataNumber = res.data.invoiceLength;
          } else if (item.dataKey == 'mealsLength') {
            item.dataNumber = res.data.mealsLength;
          } else if (item.dataKey == 'trafficLength') {
            item.dataNumber = res.data.trafficLength;
          } else if (item.dataKey == 'serveLength') {
            item.dataNumber = res.data.serveLength;
          } else if (item.dataKey == 'networkingLength') {
            item.dataNumber = res.data.networkingLength;
          } else if (item.dataKey == 'meetingLength') {
            item.dataNumber = res.data.meetingLength;
          } else if (item.dataKey == 'meetingStudyLength') {
            item.dataNumber = res.data.meetingStudyLength;
          }
        })
        that.newModelData = dataList;
      })
    },

    // 查询total
    getTotalTab (url) {
      let params = {
        queryCompanyName: this.queryParams.companyName,
        queryTaskId: this.queryParams.taskId,
        pageSize: this.queryParams.pageSize,
        pageNum: this.queryParams.pageNum,
      }
      getJSON(url, params).then(res => {
        this.taxindexTotal = res.total;
      })
    },

    //历史分析结果
    historyAnalysisFunc (url) {
      let that = this;
      this.analysisType = true;
      // this.queryParams
      getJSON(url, {
        timeId: this.timeId,
        pageSize: this.queryParams.pageSize,
        pageNum: this.queryParams.pageNum
      }).then(res => {
        that.total = res.total;
        that.analysisType = false;
        that.analysisData = res.rows;
      })
    },
    ontabs (index, type) {
      this.tabindex = index
      if (!type) {
        this.queryParams.pageSize = 10;
        this.queryParams.pageNum = 1;
      }
      if (index == 1) {
        this.timeId ? this.historyAnalysisFunc(this.example.api.listCollon) :
          this.analysisFunc(this.example.api.listCollon);
      } else if (index == 2) {
        this.timeId ? this.historyAnalysisFunc(this.example.api.listInfo) :
          this.analysisFunc(this.example.api.listInfo);
      }
    },

    ontips () {
      this.tipTitle = "提示"
      this.tipText = "是否确认导出当前数据项?"
      this.tipOpen = true;
    },
    ontipsDeta () {
      this.tipTitle = "提示"
      this.tipText = "是否确认导出当前数据项?"
      this.tipOpenDeta = true;
    },
    ontipsBus () {
      this.tipTitle = "提示"
      this.tipText = "是否确认导出当前数据项?"
      this.tipOpenBusiness = true;
    },
    onSign () {
      this.tipTitle = "提示"
      this.tipText = "是否确认导出当前数据项?"
      this.tipOpen = true;
    },
    onExportDeta () {
      // 导出详情
      let exports = this.example.api.infoExport;
      let params = {
        resultId: this.queryParamsDeta.resultId,
        isStudy: this.queryParamsDeta.isStudy,
      }
      getJSON(exports, params).then(response => {
        this.tipOpenDeta = false;
        this.download(response.msg);
      });
    },
    onExport () {
      // 导出结果汇总&结果详情
      let exports = this.tabindex == 1 ? this.example.api.export : this.example.api.infoExport;
      // this.queryParams

      let params = {
        queryCompanyName: this.queryParams.companyName,
        queryTaskId: this.queryParams.taskId,
        pageSize: this.queryParams.pageSize,
        pageNum: this.queryParams.pageNum,
      }
      if (this.timeId) {
        params.timeId = this.timeId
      }
      getJSON(exports, params).then(response => {
        this.tipOpen = false;
        this.download(response.msg);
      });
    },
  },
};
</script>


<style lang="scss" scoped>
/deep/ .el-table__empty-block::after {
  background-image: url(~@/assets/images/newNoData.png) !important;
}

.hand {
  color: #00ADFF;
  text-decoration: underline;
  cursor: pointer;
  font-size: 18px;
}

.text {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  color: #fff !important;
}

.btntabs {
  background-image: url("~@/assets/images/ai/btnnormal.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  color: #fff;
  padding: 0.6rem 1rem;
  font-size: 14px;
  margin-right: 10px;
}

.el-button+.el-button {
  // margin-left: 0px;
}

.boxexplain {
  font-size: 14px;
  margin: 0.5rem;
}

.btntabs.btntabindex {
  background-image: url("~@/assets/images/ai/btnselect.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  color: #fff !important;
  padding: 0.6rem 1rem;
  font-size: 14px;
}

.btnexport {
  background-image: url("~@/assets/images/ai/expbtn.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  color: #fff;
  padding: 0.6rem 1rem;
  font-size: 14px;
  max-width: 8rem;
  // margin: 0 0.3rem;
}

.tabBox {
  margin: 0.5rem;
  font-size: 1rem;
  display: flex;
  align-items: center;

  .whit65 {
    color: #ffffff65
  }

  .label {
    font-size: 0.875rem;
    margin-left: 0.5rem;
  }

  .dan {
    font-size: 0.75rem;
    margin-left: 0.2rem;
  }

}

.tipBox {
  /deep/ .el-dialog {
    width: 22rem !important;
    min-height: 14rem;
    padding: 1.3rem;
    color: #ffffff;
    background: transparent;
    background-image: url("~@/assets/images/ai/tipbg.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;

    .el-dialog__header {
      // padding: 0;
      box-shadow: none;
      text-align: center;

      .el-dialog__title {
        color: #ffffff;
        font-weight: 100;
      }

      .el-dialog__headerbtn {
        top: 1.3rem;
        right: 2rem;
      }

      .el-dialog__headerbtn .el-dialog__close {
        color: #fff;
      }

      // height: 1rem;
    }

    .el-dialog__body {
      color: #ffffff;
      font-weight: 100;
    }

    .el-dialog__footer {
      padding: 5px 20px !important;
      background: transparent;
      position: absolute;
      box-shadow: none;
      bottom: 2rem;
      right: 1rem;
    }
  }
}

.tabright {
  text-align: right;
  margin-bottom: 10px;
  min-width: 8rem;
}

/deep/ .tipBox .el-dialog:not(.is-fullscreen) {
  margin-top: 20vh !important
}

/deep/ .el-loading-mask {
  background-color: #011439;

  .el-loading-spinner .path {
    stroke: #fff;
  }
}

.btn1 {
  background: transparent;
  color: #fff;
  border: 1px solid #ffffff40;
  padding: 0.4rem 0.8rem;
  font-size: 14px;
}

.btn2 {
  background: #007EFF;
  color: #fff;
  border: 1px solid #007EFF;
  padding: 0.4rem 0.8rem;
  font-size: 14px;
}

.indexIcon {
  width: 1.5rem;
  height: 1.5rem;
  background-image: url("~@/assets/images/ai/indexicon.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  text-align: center;
}

// 往下是复制

.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0px !important;
}

/deep/ .el-table .el-table__header-wrapper th {
  background: linear-gradient(180deg, #041141 0%, #021d48 50%, #002259 100%);
  border-bottom: 1px solid #007eff !important;
  // opacity: 0.2;
}

/deep/ .el-table {
  background-color: transparent !important;
  // max-height: initial !important;
}

/deep/ .el-table__body-wrapper {
  width: calc(100% - 8px);
  // max-height: initial !important;
}

/deep/ .el-table td.el-table__cell {
  border: 0px;
}

/deep/ .el-table tr {
  background-color: transparent !important;
}

/deep/ .el-table th.el-table__cell.is-leaf {
  border: 0;
}

/deep/ .el-table--striped .el-table__body tr.el-table__row--striped td.el-table__cell {
  background: #111f48;
}

/deep/ .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
  background: #111f48;
}

/deep/ .el-dialog__header {
  .el-dialog__headerbtn {
    z-index: 100;

    &:hover .el-dialog__close {
      color: #46a1ff;
    }
  }
}

.showName {
  &>span {
    display: none;
  }
}

.showName:hover {
  &>span {
    display: block;
  }
}

/deep/ .el-scrollbar__wrap {
  overflow-x: inherit;
  // overflow-x: auto;
  height: 100%;
}

/deep/ .el-table {
  background-color: transparent !important;
}

/deep/ .table_scrollbar .el-scrollbar__wrap {
  // margin-bottom: -30px !important;

  .el-scrollbar__view {
    min-width: 100%;
    white-space: nowrap;
    display: inline-block;
  }
}

/deep/ .is-horizontal {
  height: 8px;
  position: relative;
  z-index: 3;
  bottom: 0;
}

/deep/ .el-select-dropdown .el-scrollbar__wrap {
  overflow: inherit;
}

/deep/ .ai_mainLog .el-dialog__body {
  overflow: hidden;

  // min-height: 536px;
  max-height: 76vh;
}

/deep/ .el-upload:focus .el-upload-dragger {
  border-color: #46a1ff;
}

/deep/ .el-upload-dragger {
  background: transparent !important;

  &:hover {
    border-color: #46a1ff;
  }

  .el-upload__text {
    color: #fff;
  }

  .el-upload__text em {
    color: #46a1ff;
  }
}

/deep/ .el-upload-list__item:hover {
  background: transparent !important;
  color: #46a1ff;

  .el-icon-document {
    color: #46a1ff;
  }
}

/deep/ .el-upload-list {
  min-height: 28px;
}

/deep/ .el-input {
  background: transparent !important;
}

/deep/ .el-input__inner {
  background: transparent !important;
  border-color: #ffffff15 !important;
  color: #fff;
}

.flex {
  display: flex;
  align-items: center;
}

.download_btn {
  font-size: 12px;
  padding: 8px 14px;
  margin-left: 10px;
}

.msg_box {
  padding: 10px;
  // background: linear-gradient(270deg, #348cff 0%, #38a2ff 100%);
  box-shadow: inset 0px 0px 13px 0px rgba(64, 211, 255, 1);
  border-radius: 5px;
  border: 1px solid rgba(64, 213, 255, 0.65);
  position: relative;
  color: #fff;
  margin: 16px 0;

  .icon {
    width: 25px;
  }

  .cancel {
    position: absolute;
    right: 20px;
    cursor: pointer;
  }
}

// .background {
//   width: 105%;
//   position: absolute;
//   top: -32px;
//   left: -32px;
//   z-index: -1;
// }

.btn_primary {
  background: linear-gradient(131deg, #46a1ff 0%, #007eff 100%);
  color: #fff;
  border: 0;
  padding: 0.6rem 1rem;
}

.nomal {
  background: transparent;
  color: #fff;

  &:hover {
    border-color: #7ebcfb;
  }
}

.title {
  color: #fff;
  position: relative;
  z-index: 10;
  font-size: 20px;
  font-weight: 100;
}

/deep/ .el-dialog {
  background: transparent;
}

/deep/ .el-dialog__header {
  position: relative;
}

/deep/ .el-dialog__footer {
  background: transparent;
}

.dialog-title {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 100;
  color: #fff;
  background: transparent;
  position: relative;
  margin-bottom: 16px;

  .line {
    background: #fff;
    height: 22px;
  }
}

/deep/ .addbox.ai_mainLog .el-dialog__body {
  overflow: hidden;
  // min-height: 100px;
}

/deep/ .el-input.is-disabled .el-input__inner {
  background: transparent !important;
}

/deep/.el-dialog__headerbtn {
  display: none;
}

.popupback {
  height: 65vh;
  background-image: url("~@/assets/images/ai/BG.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  padding: 2rem 3rem;
}

.pageBox {
  height: 33px;

  .pagination-container {
    padding: 0 !important;
    margin: 0;
    height: 32px;
    margin-top: 12px;

    // /deep/ .number,
    // /deep/ .btn-prev,
    // /deep/ .btn-next {
    //   width: 32px;
    //   height: 32px;
    //   line-height: 31px;
    //   background: #fff;
    //   border-radius: 4px;
    //   font-size: 14px;
    //   font-family: HelveticaNeue;
    //   color: rgba(0, 0, 0, 0.65);
    //   border: 1px solid rgba(23, 35, 61, 0.15);
    //   font-weight: normal;
    // }

    /deep/ .el-input__inner {
      height: 32px;
      line-height: 32px;
    }
  }

  /deep/ .el-pagination__total {
    line-height: 32px;
  }
}

/deep/ .el-table__body {
  width: auto !important;
}


/deep/::-webkit-scrollbar {
  width: 8px;
  height: 20px;
}

/deep/::-webkit-scrollbar-thumb {
  box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  background-color: rgba(144, 147, 153, 0.3);
  border-radius: 12px;
}

/deep/ .busbox {
  .el-form--inline .el-form-item {
    width: 100%;

  }

  .el-form-item__content {
    width: calc(100% - 225px);
  }
}

/deep/.el-autocomplete-suggestion.el-popper {

  background: #041141 !important;
  border-color: #ffffff15;

  li:hover {
    background: linear-gradient(180deg, #0f4ca6 0%, rgba(18, 74, 156, 0.07) 100%);
  }
}

.background {
  width: 105%;
  height: 110%;
  position: absolute;
  top: -32px;
  left: -32px;
  z-index: -1;
}

// /deep/::-webkit-scrollbar-track {
//   box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
//   border-radius: 3px;
//   background: yellow;
// }
</style>
