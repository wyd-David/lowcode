import request from '@/utils/request'

// 查询入库管理_人才收集管理 列表
export function listCollection(query) {
  return request({
    url: '/talent/collection/list',
    method: 'get',
    params: query
  })
}

// 查询入库管理_人才收集管理 详细
export function getCollection(id) {
  return request({
    url: '/talent/collection/' + id,
    method: 'get'
  })
}

// 新增入库管理_人才收集管理 
export function addCollection(data) {
  return request({
    url: '/talent/collection',
    method: 'post',
    data: data
  })
}

// 修改入库管理_人才收集管理 
export function updateCollection(data) {
  return request({
    url: '/talent/collection/edit',
    method: 'post',
    data: data
  })
}

// 删除入库管理_人才收集管理 
export function delCollection(id) {
  return request({
    url: '/talent/collection/remove?orderNumber=' + id,
    method: 'get'
  })
}

// 导出入库管理_人才收集管理 
export function exportCollection(query) {
  return request({
    url: '/talent/collection/export',
    method: 'get',
    params: query
  })
}
// 下载入库管理_人才收集管理 导入模板
export function importTemplate() {
  return request({
    url: '/talent/collection/importTemplate',
    method: 'get'
  })
}
// 入库管理_人才收集管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/talent/collection/updateByIds',
    method: 'get',
    params: query
  })
}
//获取下一步处理人
export function getNextDealUser(e) {
  return request({
    url: '/talent/collection/getNextDealUser',
    method: 'get',
  })
}
//获取公司列表
export function getAllCompany() {
  return request({
    url: "/ViewEmpInfo/ViewEmpInfo/getAllCompany",
    method: "get",
  });
}
//获取审核记录
export function getAllFlowStep(data) {
  return request({
    url: "/activiti/getAllFlowStep",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//获取流程按钮
export function getOneTaskInfo(data) {
  return request({
    url: "/activiti/getOneTaskInfo",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//流程推动
export function fininshFormTask(data) { 
  return request({
    url: "/talent/collection/fininshFormTask?taskId="+data.taskId,
    method: "post",
    data: data,
  });
}
//转办
export function accidentDeal(data) { 
  return request({
    url: "/todoinfo/todoinfo/accidentDeal",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}
//转办-特殊处理
export function transfer(data) { 
  return request({
    url: "/checkedAll/checkInfo/transfer",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}
export function getUserInfo(e) { 
  return request({
    url: "/xccommon/base/getUserInfo?userName="+e,
    method: "get",
  });
} 
//下发详情
export function selectById(id) {
  return request({
    url: "/talent/collection/selectById?id="+id,
    method: "get",
  });
}

//下发
export function issued(data) { 
  return request({
    url: "/talent/collection/issued",
    method: "post",
    data: data,
  });
}

//获取部门列表
export function getDeptList(data) { 
  return request({
    url: "/talent/company/getDeptList",
    method: "get",
    params:data
  });
}

//检查重复标题
export function checkTitle(data) {
  return request({
    url: "/talent/collection/checkTitle",
    method: "get",
    params: data,
  });
}

//获取接收人列表
export function queryLiaisonsList(data) {
  return request({
    url: "/xccommon/base/queryLiaisonsList",
    method: "get",
    params: data,
  });
}

//发起人才收集通过公司获取接收人
export function getUserByRoleKey(query) {
  return request({
    url: "/xccommon/base/getUserByRoleKey",
    method: "get",
    params: query,
  });
}
 
//发起人才收集通过部门获取接收人
export function getUserByPostName(query) {
  return request({
    url: "/xccommon/base/getUserByPostName",
    method: "get",
    params: query,
  });
}