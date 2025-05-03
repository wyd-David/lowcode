import request from '@/utils/request'

// 查询作业开发列表
export function listEtlDev(query) {
  return request({
    url: '/datasource/etlDev/list',
    method: 'get',
    params: query
  })
}

// 查询作业开发详细
export function getEtlDev(collectId) {
  return request({
    url: '/datasource/etlDev/' + collectId,
    method: 'get'
  })
}

// 新增作业开发
export function addEtlDev(data) {
  return request({
    url: '/datasource/etlDev',
    method: 'post',
    data: data
  })
}

// 修改作业开发
export function updateEtlDev(data) {
  return request({
    url: '/datasource/etlDev/edit',
    method: 'post',
    data: data
  })
}

// 删除作业开发
export function delEtlDev(collectId) {
  return request({
    url: '/datasource/etlDev/remove?collectId=' + collectId,
    method: 'get'
  })
}

//立即运行
export function etlRun(collectId){
  return request({
    url: '/datasource/etlDev/dataFlowRun?ids=' + collectId,
    method: 'get'
  })
}
// 导出作业开发
export function exportEtlDev(query) {
  return request({
    url: '/datasource/etlDev/export',
    method: 'get',
    params: query
  })
}
// 下载作业开发导入模板
export function importTemplate() {
  return request({
    url: '/datasource/etlDev/importTemplate',
    method: 'get'
  })
}
// 作业开发禁用/启用
export function disableEnable(query) {
  return request({
    url: '/datasource/etlDev/updateByIds',
    method: 'get',
    params: query
  })
}
