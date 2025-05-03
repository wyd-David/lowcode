import request from '@/utils/request'

// 查询step列表
export function listConf(query) {
  return request({
    url: '/workflow/conf/list',
    method: 'get',
    params: query
  })
}

// 查询step详细
export function getConf(keyId) {
  return request({
    url: '/workflow/conf/' + keyId,
    method: 'get'
  })
}

// 新增step
export function addConf(data) {
  return request({
    url: '/workflow/conf',
    method: 'post',
    data: data
  })
}

// 修改step
export function updateConf(data) {
  return request({
    url: '/workflow/conf',
    method: 'put',
    data: data
  })
}

// 删除step
export function delConf(keyId) {
  return request({
    url: '/workflow/conf/' + keyId,
    method: 'delete'
  })
}

// 导出step
export function exportConf(query) {
  return request({
    url: '/workflow/conf/export',
    method: 'get',
    params: query
  })
}