import request from '@/utils/request'

// 查询文字转声音列表
export function listTextvoid(query) {
  return request({
    url: '/capacity/textvoid/list',
    method: 'get',
    params: query
  })
}

// 查询文字转声音详细
export function getTextvoid(textId) {
  return request({
    url: '/capacity/textvoid/' + textId,
    method: 'get'
  })
}

// 新增文字转声音
export function addTextvoid(data) {
  return request({
    url: '/capacity/textvoid',
    method: 'post',
    data: data
  })
}

// 修改文字转声音
export function updateTextvoid(data) {
  return request({
    url: '/capacity/textvoid/edit',
    method: 'post',
    data: data
  })
}

// 删除文字转声音
export function delTextvoid(textId) {
  return request({
    url: '/capacity/textvoid/remove?textId=' + textId,
    method: 'get'
  })
}

// 导出文字转声音
export function exportTextvoid(query) {
  return request({
    url: '/capacity/textvoid/export',
    method: 'get',
    params: query
  })
}
// 下载文字转声音导入模板
export function importTemplate() {
  return request({
    url: '/capacity/textvoid/importTemplate',
    method: 'get'
  })
}
// 文字转声音禁用/启用
export function disableEnable(query) {
  return request({
    url: '/capacity/textvoid/updateByIds',
    method: 'get',
    params: query
  })
}




export function downloadVoid(textId) {
  return request({
    url: 'msgCode/textToVoid?text=' + textId,
    method: 'get'
  })
}