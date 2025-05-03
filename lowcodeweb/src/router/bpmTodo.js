export const bpmTodo = [
  {
    path: "/todo/mefp_todo_bpm", //名额分配待办
    meta: {
      title: "名额分配",
    },
    component: (resolve) => require(["@/views/bpmTodo/mefp_todo_bpm"], resolve),
    hidden: true,
  },
  {
    path: "/todo/lksq_todo_bpm",
    meta: {
      title: "离库申请",
    },
    component: (resolve) =>
      require(["@/views/talent/apply/applyForm"], resolve),
    hidden: true,
  },
  {
    path: "/todo/rctjgs_todo_bpm", //分公司人才推荐待办
    meta: {
      title: "分公司人才推荐",
    },
    component: (resolve) =>
      require(["@/views/bpmTodo/rctjgs_todo_bpm"], resolve),
    hidden: true,
  },
  {
    path: "/todo/rcbqsz_todo_bpm", //人才标签待办
    meta: {
      title: "人才标签设置",
    },
    component: (resolve) =>
      require(["@/views/bpmTodo/rcbqsz_todo_bpm"], resolve),
    hidden: true,
  },
  {
    path: "/todo/rclksq_todo_bpm", //人才离库待办
    meta: {
      title: "人才离库申请",
    },
    component: (resolve) =>
      require(["@/views/bpmTodo/rclksq_todo_bpm"], resolve),
    hidden: true,
  },
  {
    path: "/todo/patrolCheck_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/patrolCheck_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "巡察组考核",
    },
  },
  {
    path: "/todo/patrolCheckReturn_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/patrolCheckReturn_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "巡察组考核退回",
    },
  },
  {
    path: "/todo/patrolCheckReturnPre_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/patrolCheckReturn_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "巡察员考核退回",
    },
  },
  {
    path: "/checkedAll/leadingRating_db/index",
    component: (resolve) =>
      require(["@/views/checkedAll/leadingRating/index"], resolve),
    hidden: true,
    meta: {
      title: "领导小组评分",
    },
  },
  {
    path: "/checkedAll/patrolOfficeRating_db/index",
    component: (resolve) =>
      require(["@/views/checkedAll/patrolOfficeRating/index"], resolve),
    hidden: true,
    meta: {
      title: "巡察办评分",
    },
  },
  {
    path: "/todo/patrolCompanyCover_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/patrolCompanyCover_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "被巡察单位评分",
    },
  },
  {
    path: "/todo/patrolTeamMember_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/patrolTeamMember_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "组员评分",
    },
  },
  {
    path: "/todo/fineCheck_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/fineCheck_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "优秀优良巡察员申请",
    },
  },
  {
    path: "/todo/fineCheckReturn_todo_bpm",
    component: (resolve) =>
      require(["@/views/bpmTodo/fineCheckReturn_todo_bpm"], resolve),
    hidden: true,
    meta: {
      title: "优秀优良巡察员申请退回",
    },
  },
  {
    path: "/todo/rctjbm_todo_bpm", //人才推荐部门待办
    meta: {
      title: "人才推荐部门",
    },
    component: (resolve) =>
      require(["@/views/bpmTodo/rctjbm_todo_bpm"], resolve),
    hidden: true,
  },
  {
    path: "/todo/archive_todo",
    component: (resolve) => require(["@/views/bpmTodo/archive_todo"], resolve),
    hidden: true,
  },
  {
    path: "/todo/archive_process_todo", //资料申请待办
    component: (resolve) =>
      require(["@/views/bpmTodo/archive_process_todo"], resolve),
    hidden: true,
    meta: {
      title: "巡察过程资料入库",
    },
  },
  {
    path: "/todo/archive_report_process_todo", //报告资料申请待办
    component: (resolve) =>
      require(["@/views/bpmTodo/archive_report_process_todo"], resolve),
    hidden: true,
    meta: {
      title: "报告报表入库",
    },
  },
  {
    path: "/todo/gruop_todo",
    component: (resolve) => require(["@/views/bpmTodo/group_todo"], resolve),
    hidden: true,
    meta: {
      title: "巡察预分组",
    },
  },
  {
    path: "/todo/archive_report_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/archive_report_todo"], resolve),
    hidden: true,
  },

  {
    path: "/todo/person_replace_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/person_replace_todo"], resolve),
    hidden: true,
    meta: {
      title: "人员更替申请",
    },
  },
  {
    path: "/todo/sporadic_talent_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/sporadic_talent_todo"], resolve),
    hidden: true,
    meta: {
      title: "零星入库",
    },
  },

  {
    path: "/todo/archive_case_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/archive_case_todo"], resolve),
    hidden: true,
  },
  {
    path: "/todo/archive_regulation",
    component: (resolve) =>
      require(["@/views/bpmTodo/archive_regulation"], resolve),
    hidden: true,
    meta: {
      title: "制度流程入库",
    },
  },
  {
    path: "/todo/transfer_notice_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/transfer_notice_todo"], resolve),
    hidden: true,
  },
  {
    path: "/todo/course_voucher_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/course_voucher_todo"], resolve),
    hidden: true,
    meta: {
      title: "事实确认单审核",
    },
  },
  {
    path: "/todo/archive_course_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/course_papers_todo"], resolve),
    hidden: true,
    meta: {
      title: "问题底稿审核",
    },
  },
  {
    path: "/todo/ahead_dept_todo",
    component: (resolve) =>
      require(["@/views/bpmTodo/ahead_dept_todo"], resolve),
    hidden: true,
    meta: {
      title: "巡前资料主责部门办理",
    },
  },
];
