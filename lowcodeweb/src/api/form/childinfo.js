import request from '@/utils/request'

// 查询基础单信息列表
export function listInfo(query) {
  return request({
    url: '/form/info/list',
    method: 'get',
    params: query
  })
}

// 查询基础单信息详细
export function getInfo(formUuid) {
  return request({
    url: '/form/info/' + formUuid,
    method: 'get'
  })
}


export function getInfoNimin(formUuid) {
  return request({
    url: '/form/info/getInfoNimin/' + formUuid,
    method: 'get'
  })
}
// 新增基础单信息
export function addInfo(data) {
  return request({
    url: '/form/info',
    method: 'post',
    data: data
  })
}

// 修改基础单信息
export function updateInfo(data) {
  return request({
    url: '/form/info/edit',
    method: 'post',
    data: data
  })
}

// 删除基础单信息
export function delInfo(formUuid) {
  return request({
    url: '/form/info/remove?formUuid=' + formUuid,
    method: 'get'
  })
}

// 导出基础单信息
export function exportInfo(query) {
  return request({
    url: '/form/info/export',
    method: 'get',
    params: query
  })
}
// 下载基础单信息导入模板
export function importTemplate() {
  return request({
    url: '/form/info/importTemplate',
    method: 'get'
  })
}
// 基础单信息禁用/启用
export function disableEnable(query) {
  return request({
    url: '/form/info/updateByIds',
    method: 'get',
    params: query
  })
}

// 动态获取表单字段数据信息
export function queryOneAutoSqlInfo(data) {
  return request({
    url: '/form/info/queryOneAutoSqlInfo',
    method: 'post',
    data: data
  })
}

//保存动态表单数据
export function saveChildFormInfo(data) {
  return request({
    url: '/form/info/saveChildFormInfo',
    method: 'post',
    data: data
  })
}

export function saveFormInfoNimin(data) {
  return request({
    url: '/form/info/saveFormInfoNimin',
    method: 'post',
    data: data
  })
}