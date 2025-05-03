import request from '@/utils/request'

// 查询巡察任务管理 列表
export function listXunchaTaskInfo(query) {
  return request({
    url: '/xuncha/XunchaTaskInfo/list',
    method: 'get',
    params: query
  })
}
// 查询巡察任务管理 列表-2
export function listXunchaTaskInfo2(query) {
  return request({
    url: '/xccommon/base/queryTaskList',
    method: 'get',
    params: query
  })
}
// 查询未分组 巡察任务管理 列表
export function unListXunchaTaskInfo(query) {
  return request({
    url: '/xuncha/XunchaTaskInfo/unGroupList',
    method: 'get',
    params: query
  })
}

// 查询巡察任务管理 详细
export function getXunchaTaskInfo(taskId) {
  return request({
    url: '/xuncha/XunchaTaskInfo/' + taskId,
    method: 'get'
  })
}

// 新增巡察任务管理
export function addXunchaTaskInfo(data) {
  return request({
    url: '/xuncha/XunchaTaskInfo',
    method: 'post',
    data: data
  })
}

// 修改巡察任务管理
export function updateXunchaTaskInfo(data) {
  return request({
    url: '/xuncha/XunchaTaskInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察任务管理
export function delXunchaTaskInfo(taskId) {
  return request({
    url: '/xuncha/XunchaTaskInfo/remove?taskId=' + taskId,
    method: 'get'
  })
}

// 导出巡察任务管理
export function exportXunchaTaskInfo(query) {
  return request({
    url: '/xuncha/XunchaTaskInfo/export',
    method: 'get',
    params: query
  })
}
// 下载巡察任务管理 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/XunchaTaskInfo/importTemplate',
    method: 'get'
  })
}
// 巡察任务管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/XunchaTaskInfo/updateByIds',
    method: 'get',
    params: query
  })
}

// 【巡察任务管理】 → 【新增或编辑页面】 → 【巡察批次】 传入相应的年份，返回在该年份中的所有批次信息
export function queryPiciByYeay(year) {
  return request({
    url: `/xuncha/XunchaTaskInfo/queryPiciByYeay?year=${year}`,
    method: 'get'
  })
}

// 【巡察任务管理】 → 【新增或编辑页面】 → 【巡察组】传入相应的年份，返回在该年份中的所有批次信息
export function queryGroupByPiciId(piciId) {
  return request({
    url: `/xuncha/XunchaTaskInfo/queryGroupByPiciId?piciId=${piciId}`,
    method: 'get'
  })
}


// 在新增和修改批次时，获得批次名称下拉框数。如：['第一组', '第二组' ...]
export function getGroupNameList(taskId, piciId) {
  return request({
    url: `/xuncha/XunchaTaskInfo/getGroupNameList?taskId=${taskId}&piciId=${piciId}`,
    method: "get",
  });
}
// 获取巡察任务轮次，年份，批次
export function queryInfoByTaskId(id) {
  return request({
    url: `/xuncha/XunchaTaskInfo/queryInfoByTaskId?id=${id}`,
    method: "get",
  });
}
