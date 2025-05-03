import {
  getaiAttendanceExceptionList,
  aiAttendanceExceptionRemove,
  getAiBusinessTripList,
  aiBusinessTripRemove,
  getAiInvoiceInfoList,
  aiInvoiceInfoRemove,
  getAiMealsInfoList,
  aiMealsInfoRemove,
  getAiTrafficInfoList,
  aiTrafficInfoRemove,
  getAiSettlementInfoList,
  aiSettlementInfoRemove,
  getAiBusinessTripDetailList,
  aiBusinessTripDetailRemove,
  getAiServelList,
  aiServeRemove,
  getAiCarNetworkingList,
  aiCarNetworkingRemove,
  getMeeting,
  getMeetingRemove,
  getMeetingStudy,
  getMeetingStudyRemove,
} from "@/api/aiXuncha/aiModelData";

export default [
  {
    id: 8,
    comparisonName: "考勤系统（考勤异常数据）",
    dataKey: "attendanceLength",
    idLabel: 'exceptionId',
    dataNumber: "数据未上传！",
    list: getaiAttendanceExceptionList,
    option: aiAttendanceExceptionRemove,
    url: "aiAttendanceException",
    desc: "Excel文件说明1",
    tableItems: [
      {
        label: "编号",
        prop: "code",
      },
      {
        label: "旧员工编码",
        prop: "empCode",
      },
      {
        label: "员工姓名",
        prop: "empName",
        width: '80'
      },
      {
        label: "考勤异常日期",
        prop: "exceptionDate",
        isDate: true,
        width: '180'
      },
      {
        label: "考勤异常类型",
        prop: "exceptionType",
      },
      {
        label: "核对说明",
        prop: "exceptionExplain",
      },
    ],
  },
  {
    id: 2,
    comparisonName: "差旅系统（出差申请单）",
    dataKey: "businessTripLength",
    idLabel: 'id',
    dataNumber: "数据未上传！",
    list: getAiBusinessTripList,
    option: aiBusinessTripRemove,
    url: "aiBusinessTrip",
    desc: "Excel文件说明2",
    tableItems: [
      {
        label: "工单编号",
        prop: "tripCode",
      },
      {
        label: "出差类型",
        prop: "evectionType",
      },
      {
        label: "公司",
        prop: "companyName",
      },
      {
        label: "部门",
        prop: "deptName",
      },
      {
        label: "填报人",
        prop: "filledByName",
      },
      {
        label: "填报人编号",
        prop: "filledBy",
      },
      {
        label: "出差人数",
        prop: "peopleSum",
      },
      {
        label: "出差开始日期",
        prop: "tripBeginDate",
        isDate: true
      },
      {
        label: "出差结束日期",
        prop: "tripEndDate",
        isDate: true
      },
      {
        label: "出差人员",
        prop: "tripUserNames",
      },
      {
        label: "审批人",
        prop: "approvalBy",
      },
    ],
  },
  {
    id: 3,
    comparisonName: "差旅系统（发票信息）",
    dataKey: "invoiceLength",
    idLabel: 'invoiceId',
    dataNumber: "数据未上传！",
    list: getAiInvoiceInfoList,
    option: aiInvoiceInfoRemove,
    url: "/ai/aiInvoiceInfo",
    desc: "Excel文件说明3",
    tableItems: [
      {
        label: "单据编号",
        prop: "billCode",
      },

      {
        label: "城市类型",
        prop: "cityType",
      },
      {
        label: "发票代码",
        prop: "invoiceCode",
      },
      {
        label: "发票号码",
        prop: "invoiceNumber",
      },
      {
        label: "发票日期",
        prop: "invoiceDate",
        isDate: true
      },
      {
        label: "销方税号",
        prop: "marketTfn",
      },
      {
        label: "销方名称",
        prop: "marketName",
      },
      {
        label: "销方地址电话",
        prop: "marketAddress",
      },
      {
        label: "销方开户行账号",
        prop: "marketBankAccount",
      },
    ],
  },
  {
    id: 4,
    comparisonName: "差旅系统（伙食包干信息）",
    dataKey: "mealsLength",
    idLabel: 'mealsId',
    dataNumber: "数据未上传！",
    list: getAiMealsInfoList,
    option: aiMealsInfoRemove,
    url: "/ai/aiMealsInfo",
    desc: "Excel文件说明4",
    tableItems: [
      {
        label: "单号",
        prop: "billCode",
      },
      {
        label: "员工名称",
        prop: "empName",
      },
      {
        label: "员工号码",
        prop: "empId",
      },
      {
        label: "伙食标准",
        prop: "mealsStandard",
      },
      {
        label: "出差伙食天数",
        prop: "tripMealsDays",
      },
      {
        label: "包干伙食补贴",
        prop: "mealsSubsidy",
      },
      {
        label: "地区类别",
        prop: "areaType",
      },
      {
        label: "自理情况",
        prop: "selfCare",
      },
    ],
  },
  {
    id: 5,
    comparisonName: "差旅系统（交通包干信息）",
    dataKey: "trafficLength",
    idLabel: 'trafficId',
    dataNumber: "数据未上传！",
    list: getAiTrafficInfoList,
    option: aiTrafficInfoRemove,
    url: "/ai/aiTrafficInfo",
    desc: "Excel文件说明5",
    tableItems: [
      {
        label: "工单编号",
        prop: "billCode",
      },
      {
        label: "员工姓名",
        prop: "empName",
      },
      {
        label: "员工编码",
        prop: "empId",
      },
      {
        label: "交通包干标准",
        prop: "trafficStandard",
      },
      {
        label: "出差交通天数",
        prop: "tripTrafficDays",
      },
      {
        label: "包干交通补贴",
        prop: "trafficSubsidy",
      },
      {
        label: "地区类别",
        prop: "areaType",
      },
      {
        label: "自理情况",
        prop: "selfCare",
      },
    ],
  },
  {
    id: 6,
    comparisonName: "差旅系统（结算单信息）",
    dataKey: "settlementLength",
    idLabel: 'settlementId',
    dataNumber: "数据未上传！",
    list: getAiSettlementInfoList,
    option: aiSettlementInfoRemove,
    url: "/ai/aiSettlementInfo",
    desc: "Excel文件说明6",
    tableItems: [
      {
        label: "工单编号",
        prop: "billCode",
      },
      {
        label: "填报日期",
        prop: "fillInDate",
        isDate: true
      },
      {
        label: "出差类型",
        prop: "tripType",
      },
      {
        label: "填报人",
        prop: "fillName",
      },
      {
        label: "填报人ID",
        prop: "fillBy",
      },
      {
        label: "出差人数",
        prop: "peopleSum",
      },
      {
        label: "费用总计",
        prop: "totalAmount",
      },
      {
        label: "提交时间",
        prop: "submitDate",


      },
      {
        label: "报账状态",
        prop: "paymentStatus",
      },
      {
        label: "摘要",
        prop: "summary",
      },
      {
        label: "只报机票",
        prop: "justAirTicket",
      },
      {
        label: "关联影像",
        prop: "relevanceId",
      },
      {
        label: "填报人账号",
        prop: "fillByAccount",
      },
      {
        label: "报账平台ID",
        prop: "submitId",
      },
      {
        label: "审批人",
        prop: "approveName",
      },
      {
        label: "审批人账号",
        prop: "approveBy",
      },
      {
        label: "备注",
        prop: "remarks",
      },
      {
        label: "业务小类",
        prop: "businessCategory",
      },
      {
        label: "业务活动代码",
        prop: "businessActivitiesCode",
      },
      {
        label: "业务场景编码",
        prop: "businessScenarioCode",
      },
      {
        label: "是否推送影像",
        prop: "isPushRelevance",
      },
      {
        label: "预算多维KEY",
        prop: "budgetKey",
      },
      {
        label: "预算项目编码",
        prop: "budgetProjectCode",
      },
      {
        label: "预算项目名称",
        prop: "budgetProjectName",
      },
    ],
  },
  {
    id: 7,
    comparisonName: "差旅系统（申请单行程信息）",
    dataKey: "businessTripDetailLength",
    idLabel: 'detailId',
    dataNumber: "数据未上传！",
    list: getAiBusinessTripDetailList,
    option: aiBusinessTripDetailRemove,
    url: "/ai/aiBusinessTripDetail",
    desc: "Excel文件说明7",
    tableItems: [
      {
        label: "工单编号",
        prop: "tripCode",
      },
      {
        label: "员工姓名",
        prop: "empName",
      },
      {
        label: "员工编码",
        prop: "empCode",
      },
      {
        label: "出行方式",
        prop: "tripType",
      },
      {
        label: "行程类型",
        prop: "journeyType",
      },
      {
        label: "出发城市",
        prop: "departureCity",
      },
      {
        label: "出发城市KEY",
        prop: "cityKey",
      },
      {
        label: "到达城市",
        prop: "targetCity",
      },
      {
        label: "到达城市KEY",
        prop: "targetCityKey",
      },
      {
        label: "出差开始日期",
        prop: "tripBeginDate",
        isDate: true
      },
      {
        label: "出差结束日期",
        prop: "tripEndDate",
        isDate: true
      },
      {
        label: "出差文件",
        prop: "hasTripFile",
      },
      {
        label: "是否属于异地任职领导探亲事项",
        prop: "isVisitFamily",
      },
      {
        label: "其他说明",
        prop: "remarks",
      },
    ],
  },
  {
    id: 1,
    comparisonName: "招待系统（业务招待费台账）",
    dataKey: "serveLength",
    idLabel: 'id',
    dataNumber: "数据未上传！",
    list: getAiServelList,
    option: aiServeRemove,
    url: "/ai/aiServe",
    desc: "Excel文件说明8",
    tableItems: [
      {
        label: "申请单编号",
        prop: "code",
      },
      {
        label: "经办人",
        prop: "responsibleBy",
      },
      {
        label: "申请部门",
        prop: "deptName",
      },
      {
        label: "业务招待时间",
        prop: "happenDate",
      },
      {
        label: "业务招待类型",
        prop: "serveType",
      },
      {
        label: "业务招待对象",
        prop: "serveObject",
      },
      {
        label: "关联ERP报账单号",
        prop: "erpNumber",
      },
      {
        label: "发票付款方名称（餐厅名称）",
        prop: "paymentName",
      },
      {
        label: "发票号码（8位）",
        prop: "invoiceNumber",
      },
      {
        label: "发票开票日期",
        prop: "invoiceDate",
      },
      {
        label: "发票金额",
        prop: "invoiceMoney",
      },
      {
        label: "实际报账金额",
        prop: "reimburseMoney",
      },
      {
        label: "备注",
        prop: "remark",
      },
    ],
  },
  {
    id: 9,
    comparisonName: "车联网系统（用车数据信息）",
    dataKey: "networkingLength",
    idLabel: 'id',
    dataNumber: "数据未上传！",
    list: getAiCarNetworkingList,
    option: aiCarNetworkingRemove,
    url: "/ai/aiCarNetworking",
    desc: "Excel文件说明9",
    tableItems: [
      {
        label: "申请人",
        prop: "userBy",
      },
      {
        label: "申请人id",
        prop: "userId",
      },
      {
        label: "用车人",
        prop: "empName",
      },
      {
        label: "用车人id",
        prop: "empCode",
      },
      {
        label: "上车地市",
        prop: "startCity",
      },
      {
        label: "上车地点",
        prop: "startPalce",
      },
      {
        label: "下车地市",
        prop: "endCity",
      },
      {
        label: "下车地点",
        prop: "endPalce",
      },
      {
        label: "实际开始",
        prop: "startDate",
      },
      {
        label: "实际结束",
        prop: "endDate",
      },
      {
        label: "用车时长",
        prop: "duration",
      },
    ],
  },
  {
    id: 10,
    comparisonName: "议题及关键字",
    dataKey: "meetingLength",
    idLabel: 'id',
    dataNumber: "数据未上传！",
    list: getMeeting,
    option: getMeetingRemove,
    url: "/ai/AiMeeting",
    desc: "Excel文件说明10",
    tableItems: [
      {
        label: "唯一标识",
        prop: "excelId",
      },
      {
        label: "省公司公文",
        prop: "companyDispatch",
      },
      {
        label: "下发部门",
        prop: "issueDept",
      },
      {
        label: "下发时间",
        prop: "issueDate",
      },
      {
        label: "省公司联系人",
        prop: "companyLinkman",
      },
      {
        label: "学习要求和内容",
        prop: "studyContent",
      },
      {
        label: "关键字1",
        prop: "keyword1",
      },
      {
        label: "关键字2",
        prop: "keyword2",
      },
      {
        label: "关键字3",
        prop: "keyword3",
      },
      {
        label: "关键字4",
        prop: "keyword4",
      },
      {
        label: "关键字5",
        prop: "keyword5",
      },
    ],
  },
  {
    id: 11,
    comparisonName: "被巡察单位学习记录",
    dataKey: "meetingStudyLength",
    idLabel: 'id',
    dataNumber: "数据未上传！",
    list: getMeetingStudy,
    option: getMeetingStudyRemove,
    url: "/ai/AiMeetingStudy",
    desc: "Excel文件说明11",
    tableItems: [
      {
        label: "唯一标识",
        prop: "excelId",
      },
      {
        label: "支部名称",
        prop: "branchName",
      },
      {
        label: "会议名称",
        prop: "meetingName",
      },
      {
        label: "会议时间",
        prop: "meetingDate",
      },
      {
        label: "会议时长",
        prop: "meetingDuration",
      },
      {
        label: "应到人数",
        prop: "shouldPopulation",
      },
      {
        label: "实到人数",
        prop: "practicalPopulation",
      },
      {
        label: "出席人数",
        prop: "presentMembers",
      },
      {
        label: "缺席人员及原因",
        prop: "absentee	",
      }, 
      {
        label: "会议议题",
        prop: "meetingAgenda",
      },
    ],
  },
]
