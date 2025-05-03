import request from '@/utils/request'

// 查询入库管理_人才库历史记录 列表
export function listXunchaTalentPersonInfoHistory(query) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/list',
    method: 'get',
    params: query
  })
}

// 查询入库管理_人才库历史记录 详细
export function getXunchaTalentPersonInfoHistory(id) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/' + id,
    method: 'get'
  })
}

// 新增入库管理_人才库历史记录
export function addXunchaTalentPersonInfoHistory(data) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory',
    method: 'post',
    data: data
  })
}

// 修改入库管理_人才库历史记录
export function updateXunchaTalentPersonInfoHistory(data) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/edit',
    method: 'post',
    data: data
  })
}

// 删除入库管理_人才库历史记录
export function delXunchaTalentPersonInfoHistory(id) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/remove?id=' + id,
    method: 'get'
  })
}

// 导出入库管理_人才库历史记录
export function exportXunchaTalentPersonInfoHistory(query) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/export',
    method: 'get',
    params: query
  })
}
// 下载入库管理_人才库历史记录 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/importTemplate',
    method: 'get'
  })
}
// 入库管理_人才库历史记录 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/xunchaTalentPersonInfoHistory/updateByIds',
    method: 'get',
    params: query
  })
}
