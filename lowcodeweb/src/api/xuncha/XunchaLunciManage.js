import request from '@/utils/request'

// 查询巡察轮次管理 列表
export function listXunchaLunciManage(query) {
  return request({
    url: '/xuncha/XunchaLunciManage/list',
    method: 'get',
    params: query
  })
}

// 查询巡察轮次管理 详细
export function getXunchaLunciManage(lunciId) {
  return request({
    url: '/xuncha/XunchaLunciManage/' + lunciId,
    method: 'get'
  })
}
//获取轮次年份
export function getStartEndYear(id) {
  return request({
    url: "/xuncha/XunchaLunciManage/getStartEndYear?id="+id,
    method: "get",
  });
}
// 新增巡察轮次管理
export function addXunchaLunciManage(data) {
  return request({
    url: '/xuncha/XunchaLunciManage',
    method: 'post',
    data: data
  })
}

// 修改巡察轮次管理
export function updateXunchaLunciManage(data) {
  return request({
    url: '/xuncha/XunchaLunciManage/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察轮次管理
export function delXunchaLunciManage(lunciId) {
  return request({
    url: '/xuncha/XunchaLunciManage/remove?lunciId=' + lunciId,
    method: 'get'
  })
}

// 导出巡察轮次管理
export function exportXunchaLunciManage(query) {
  return request({
    url: '/xuncha/XunchaLunciManage/export',
    method: 'get',
    params: query
  })
}
// 下载巡察轮次管理 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/XunchaLunciManage/importTemplate',
    method: 'get'
  })
}
// 巡察轮次管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/XunchaLunciManage/updateByIds',
    method: 'get',
    params: query
  })
}
// 巡察轮次的下拉框接口，返回 轮次id,轮次名称,轮次开始时间,轮次结束时间
export function selectLunciData() {
  return request({
    url: '/xuncha/XunchaLunciManage/selectLunciData',
    method: 'get'
  })
}
// 查找被巡察公司的公共接口
export function getCompanyAll(id) {
  let url = '/xuncha/XunchaLunciManage/getCompanyAll';
  if (id) { 
    url+= '?piciId=' + id;
  }
  return request({
    url,
    method: 'get'
  })
}



// ai修改
export function AiMeetingEdit(data) {
  return request({
    url: '/ai/AiMeeting/edit',
    method: 'post',
    data: data
  })
}