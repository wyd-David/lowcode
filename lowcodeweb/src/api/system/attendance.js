import request from '@/utils/request'

// 查询考勤记录列表
export function listAttendance(query) {
  return request({
    url: '/system/attendance/list',
    method: 'get',
    params: query
  })
}

// 查询考勤记录详细
export function getAttendance(atdId) {
  return request({
    url: '/system/attendance/' + atdId,
    method: 'get'
  })
}

// 新增考勤记录
export function addAttendance(data) {
  return request({
    url: '/system/attendance',
    method: 'post',
    data: data
  })
}

// 修改考勤记录
export function updateAttendance(data) {
  return request({
    url: '/system/attendance',
    method: 'put',
    data: data
  })
}

// 删除考勤记录
export function delAttendance(atdId) {
  return request({
    url: '/system/attendance/' + atdId,
    method: 'delete'
  })
}

// 导出考勤记录
export function exportAttendance(query) {
  return request({
    url: '/system/attendance/export',
    method: 'get',
    params: query
  })
}