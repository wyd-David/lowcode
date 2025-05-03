import request from '@/utils/request'

// 查询员工奖惩子集列表
export function listWin(query) {
  return request({
    url: '/system/win/list',
    method: 'get',
    params: query
  })
}

// 查询员工奖惩子集详细
export function getWin(recId) {
  return request({
    url: '/system/win/' + recId,
    method: 'get'
  })
}

// 新增员工奖惩子集
export function addWin(data) {
  return request({
    url: '/system/win',
    method: 'post',
    data: data
  })
}

// 修改员工奖惩子集
export function updateWin(data) {
  return request({
    url: '/system/win',
    method: 'put',
    data: data
  })
}

// 删除员工奖惩子集
export function delWin(recId) {
  return request({
    url: '/system/win/' + recId,
    method: 'delete'
  })
}

// 导出员工奖惩子集
export function exportWin(query) {
  return request({
    url: '/system/win/export',
    method: 'get',
    params: query
  })
}