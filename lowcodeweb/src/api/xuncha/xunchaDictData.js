import request from '@/utils/request'

// 查询字典数据 列表
export function listXunchaDictData(query) {
  return request({
    url: '/xuncha/xunchaDictData/list',
    method: 'get',
    params: query
  })
}

// 查询字典数据 详细
export function getXunchaDictData(dataId) {
  return request({
    url: '/xuncha/xunchaDictData/' + dataId,
    method: 'get'
  })
}

// 新增字典数据
export function addXunchaDictData(data) {
  return request({
    url: '/xuncha/xunchaDictData',
    method: 'post',
    data: data
  })
}

// 修改字典数据
export function updateXunchaDictData(data) {
  return request({
    url: '/xuncha/xunchaDictData/edit',
    method: 'post',
    data: data
  })
}

// 删除字典数据
export function delXunchaDictData(dataId) {
  return request({
    url: '/xuncha/xunchaDictData/remove?dataId=' + dataId,
    method: 'get'
  })
}

// 导出字典数据
export function exportXunchaDictData(query) {
  return request({
    url: '/xuncha/xunchaDictData/export',
    method: 'get',
    params: query
  })
}
// 下载字典数据 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/xunchaDictData/importTemplate',
    method: 'get'
  })
}
// 字典数据 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/xunchaDictData/updateByIds',
    method: 'get',
    params: query
  })
}

// 获得业务字典下拉框数据
export function selectDictData(query) {
  return request({
    url: '/xuncha/xunchaDictData/selectDictData',
    method: 'get',
    params: query
  })
}
