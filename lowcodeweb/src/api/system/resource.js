import request from '@/utils/request'

// 查询数据资源列表
export function listResource(query) {
  return request({
    url: '/system/resource/list',
    method: 'get',
    params: query
  })
}

// 查询数据资源详细
export function getResource(resourceId) {
  return request({
    url: '/system/resource/' + resourceId,
    method: 'get'
  })
}

// 抓取数据资源
export function addResource(resourceId) {
  console.log("test111"+resourceId);
  return request({
    url: '/system/resource',
    method: 'post',
    data:resourceId
  })
}

// 修改数据资源
export function updateResource(data) {
  return request({
    url: '/system/resource',
    method: 'put',
    data: data
  })
}

// 删除数据资源
export function delResource(resourceId) {
  return request({
    url: '/system/resource/' + resourceId,
    method: 'delete'
  })
}

// 构建数据资源代码
export function handleBuild(resourceId) {
  return request({
    url: '/system/resource/build/' + resourceId,
    method: 'post'
  })
}

// 导出数据资源
export function exportResource(query) {
  return request({
    url: '/system/resource/export',
    method: 'get',
    params: query
  })
}