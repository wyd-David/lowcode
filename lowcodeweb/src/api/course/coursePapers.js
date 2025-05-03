import request from '@/utils/request'

// 查询巡察过程_问题底稿列表
export function listPapers(query) {
  return request({
    url: '/system/papers/list',
    method: 'get',
    params: query
  })
}

// 查询巡察过程_问题底稿详细
export function getPapers(id) {
  return request({
    url: '/system/papers/' + id,
    method: 'get'
  })
}

// 新增巡察过程_问题底稿
export function addPapers(data) {
  return request({
    url: '/system/papers',
    method: 'post',
    data: data
  })
}

// 修改巡察过程_问题底稿
export function updatePapers(data) {
  return request({
    url: '/system/papers/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察过程_问题底稿
export function delPapers(id) {
  return request({
    url: '/system/papers/remove?id=' + id,
    method: 'get'
  })
}

// 导出巡察过程_问题底稿
export function exportPapers(query) {
  return request({
    url: '/system/papers/export',
    method: 'get',
    params: query
  })
}
// 下载巡察过程_问题底稿导入模板
export function importTemplate() {
  return request({
    url: '/system/papers/importTemplate',
    method: 'get'
  })
}
// 巡察过程_问题底稿禁用/启用
export function disableEnable(query) {
  return request({
    url: '/system/papers/updateByIds',
    method: 'get',
    params: query
  })
}

// 新增问题底稿
export function getStartFlow(data) {
  return request({
    url: '/system/papers/startCoursePapers',
    method: 'post',
    data: data
  })
}

// 问题来源
export function getPapersSource(query) {
  return request({
    url: '/system/papers/course_papers_source',
    method: 'get',
    params: query
  })
}

// 获取承办人
export function getUndertaker(query) {
  return request({
    url: '/system/papers/getUndertaker',
    method: 'get',
    params: query
  })
}

// 导入
export function getImportWord(query) {
  return request({
    url: '/system/papers/importWord',
    method: 'get',
    params: query
  })
}


// 导出 Word 文件
export function exportWord(data) {
  return request({
    url: '/system/papers/exportWord',
    method: 'post',
    data: data
  })
}


// // 通过巡察批次获得巡察组下拉框数据
// export function getGroupSelectData(query) {
//   return request({
//     url: '/system/papers/getGroupSelectData',
//     method: 'get',
//     params: query
//   })
// }

// 生成确认单页面中，通过巡察组（taskId）获得问题底稿下拉框数据
export function getPapersSelectData(query) {
  return request({
    url: '/system/papers/getPapersSelectData',
    method: 'get',
    params: query
  })
}

// 生成事实确认单
export function getPapersData(data) {
  return request({
    url: '/system/papers/getPapersData',
    method: 'post',
    data: data
  })
}


// 下载模板
export function getDownloadWordTemplate() {
  return request({
    url: '/system/papers/downloadWordTemplate',
    method: 'get',
  })
}