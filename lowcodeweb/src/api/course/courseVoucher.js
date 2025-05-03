import request from '@/utils/request'

// 查询巡察过程_事实确认单列表
export function listCourseVoucher(query) {
  return request({
    url: '/course/courseVoucher/list',
    method: 'get',
    params: query
  })
}

// 查询巡察过程_事实确认单详细
export function getCourseVoucher(id) {
  return request({
    url: '/course/courseVoucher/' + id,
    method: 'get'
  })
}

// 新增巡察过程_事实确认单
export function addCourseVoucher(data) {
  return request({
    url: '/course/courseVoucher',
    method: 'post',
    data: data
  })
}

// 修改巡察过程_事实确认单
export function updateCourseVoucher(data) {
  return request({
    url: '/course/courseVoucher/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察过程_事实确认单
export function delCourseVoucher(id) {
  return request({
    url: '/course/courseVoucher/remove?id=' + id,
    method: 'get'
  })
}

// 导出巡察过程_事实确认单
export function exportCourseVoucher(query) {
  return request({
    url: '/course/courseVoucher/export',
    method: 'get',
    params: query
  })
}
// 下载巡察过程_事实确认单导入模板
export function importTemplate() {
  return request({
    url: '/course/courseVoucher/importTemplate',
    method: 'get'
  })
}
// 巡察过程_事实确认单禁用/启用
export function disableEnable(query) {
  return request({
    url: '/course/courseVoucher/updateByIds',
    method: 'get',
    params: query
  })
}

// 巡察过程_巡察组详情
export function getXunchaGroupInfoList(query) {
  return request({
    url: '/course/courseVoucher/getXunchaGroupInfoList',
    method: 'get',
    params: query
  })
}

// 新增事实确认单
export function getStartFlow(data) {
  return request({
    url: '/course/courseVoucher/startFlow',
    method: 'post',
    data: data
  })
}

// 获取承办人
export function getUndertaker(query) {
  return request({
    url: '/course/courseVoucher/getUndertaker',
    method: 'get',
    params: query
  })
}

// 事实确认单流程推动
export function finishFormTask(data) {
  return request({
    url: '/course/courseVoucher/finishFormTask',
    method: 'post',
    data: data
  })
}

// 获得组长和副组长
export function getGroupLeader(query) {
  return request({
    url: '/course/courseVoucher/getGroupLeader',
    method: 'get',
    params: query
  })
}

// 通过巡察批次获得巡察组下拉框数据
export function getPiciSelectData(query) {
  return request({
    url: '/course/courseVoucher/getPiciSelectData',
    method: 'get',
    params: query
  })
}

// 通过巡察批次获得巡察组下拉框数据
export function getGroupSelectData(query) {
  return request({
    url: '/course/courseVoucher/getGroupSelectData',
    method: 'get',
    params: query
  })
}

// 生成确认单页面中，通过巡察组（taskId）获得问题底稿下拉框数据
export function getPapersSelectData(query) {
  return request({
    url: '/course/courseVoucher/getPapersSelectData',
    method: 'get',
    params: query
  })
}

// 根据问题底稿ID获得事实概述和具体事实（ID 数组）
export function getPapersData(data) {
  return request({
    url: '/course/courseVoucher/getPapersData',
    method: 'post',
    data: data
  })
}

// 导出 Word 文件
export function exportWord(data) {
  return request({
    url: '/course/courseVoucher/exportWord',
    method: 'post',
    data: data
  })
}

// 资料退回接口（包括事实确认单和问题底稿）
export function sendBackFlow(data) {
  return request({
    url: '/course/courseVoucher/sendBackFlow',
    method: 'post',
    data: data
  })
}

// 资料入库接口（包括事实确认单和问题底稿）
export function sendFinishFlow(data) {
  return request({
    url: '/course/courseVoucher/sendFinishFlow',
    method: 'post',
    data: data
  })
}
// 导入
export function getReadWorldFile(query) {
  return request({
    url: '/course/courseVoucher/readWorldFile2',
    method: 'get',
    params: query
  })
}

// 下载模板
export function getDownloadWordTemplate() {
  return request({
    url: '/course/courseVoucher/downloadWordTemplate',
    method: 'get',
  })
}
