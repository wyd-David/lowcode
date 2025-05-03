import request from '@/utils/request'

// 查询工作流模型列表
export function listModels(query) {
  return request({
    url: '/workflow/models/list',
    method: 'get',
    params: query
  })
}

// 查询工作流模型详细
export function getModels(id) {
  return request({
    url: '/workflow/models/' + id,
    method: 'get'
  })
}

// 新增工作流模型
export function addModels(data) {
  return request({
    url: '/workflow/models',
    method: 'post',
    data: data
  })
}

// 修改工作流模型
export function updateModels(data) {
  return request({
    url: '/workflow/models',
    method: 'put',
    data: data
  })
}

// 删除工作流模型
export function delModels(id) {
  return request({
   // url: '/workflow/models/' + id,
    url: 'http://10.51.167.134:8081/models/' + id+'/deployment',
    method: 'get'
  })
}

// 导出工作流模型
export function exportModels(query) {
  return request({
    url: '/workflow/models/export',
    method: 'get',
    params: query
  })
}
