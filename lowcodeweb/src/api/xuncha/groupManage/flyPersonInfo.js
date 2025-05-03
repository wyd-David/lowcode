import request from '@/utils/request'

// 查询分组管理_飞行小组R人员信息 列表
export function listFlyPersonInfo(query) {
  return request({
    url: '/groupManage/flyPersonInfo/list',
    method: 'get',
    params: query
  })
}

// 根据条件获取飞行小组列表
export function getXunchaFlyGroupInfoData(query) {
  return request({
    url: '/groupManage/flyPersonInfo/getXunchaFlyGroupInfoData',
    method: 'get',
    params: query
  })
}

// 查询分组管理_飞行小组R人员信息 详细
export function getFlyPersonInfo(flyPersonId) {
  return request({
    url: '/groupManage/flyPersonInfo/' + flyPersonId,
    method: 'get'
  })
}

// 新增分组管理_飞行小组R人员信息 
export function addFlyPersonInfo(data) {
  return request({
    url: '/groupManage/flyPersonInfo',
    method: 'post',
    data: data
  })
}

// 修改分组管理_飞行小组R人员信息 
export function updateFlyPersonInfo(data) {
  return request({
    url: '/groupManage/flyPersonInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除分组管理_飞行小组R人员信息 
export function delFlyPersonInfo(flyPersonId) {
  return request({
    url: '/groupManage/flyPersonInfo/remove?flyPersonId=' + flyPersonId,
    method: 'get'
  })
}

// 导出分组管理_飞行小组R人员信息 
export function exportFlyPersonInfo(query) {
  return request({
    url: '/groupManage/flyPersonInfo/export',
    method: 'get',
    params: query
  })
}
// 下载分组管理_飞行小组R人员信息 导入模板
export function importTemplate() {
  return request({
    url: '/groupManage/flyPersonInfo/importTemplate',
    method: 'get'
  })
}
// 分组管理_飞行小组R人员信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/groupManage/flyPersonInfo/updateByIds',
    method: 'get',
    params: query
  })
}
