import request from '@/utils/request'

// 查询日历配置列表
export function listSysConfigCalendar(query) {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar/list',
    method: 'get',
    params: query
  })
}

// 查询日历配置详细
export function getSysConfigCalendar(id) {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar/' + id,
    method: 'get'
  })
}

// 新增日历配置
export function addSysConfigCalendar(data) {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar',
    method: 'post',
    data: data
  })
}

// 修改日历配置
export function updateSysConfigCalendar(data) {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar',
    method: 'put',
    data: data
  })
}

// 删除日历配置
export function delSysConfigCalendar(id) {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar/' + id,
    method: 'delete'
  })
}

// 导出日历配置
export function exportSysConfigCalendar(query) {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar/export',
    method: 'get',
    params: query
  })
}
// 下载日历配置导入模板
export function importTemplate() {
  return request({
    url: '/SysConfigCalendar/SysConfigCalendar/importTemplate',
    method: 'get'
  })
}