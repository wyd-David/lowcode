import request from '@/utils/request'

// 查询巡察过程资料库_文件信息  列表
export function listXunchaProcessFile(query) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/list',
    method: 'get',
    params: query
  })
}

// 查询巡察过程资料库_文件信息  详细
export function getXunchaProcessFile(id) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/' + id,
    method: 'get'
  })
}

// 新增巡察过程资料库_文件信息
export function addXunchaProcessFile(data) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/',
    method: 'post',
    data: data
  })
}
// 共享人&主责人保存数据
export function addFiles(data, applyId) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/addFiles?applyId=' + applyId,
    method: 'post',
    data: data
  })
}

// 共享人完成
export function endTodo(data) {
  return request({
    url: '/archives/xunchaProcessParticipant/end_todo',
    method: 'post',
    data: data
  })
}


// 修改巡察过程资料库_文件信息
export function updateXunchaProcessFile(data) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察过程资料库_文件信息
export function delXunchaProcessFile(id) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/remove?id=' + id,
    method: 'get'
  })
}

// 导出巡察过程资料库_文件信息
export function exportXunchaProcessFile(query) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/export',
    method: 'get',
    params: query
  })
}
// 下载巡察过程资料库_文件信息  导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/importTemplate',
    method: 'get'
  })
}
// 巡察过程资料库_文件信息  禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/archives/xunchaProcessFile/updateByIds',
    method: 'get',
    params: query
  })
}

// 文件预览量和下载量 +1 操作，type 区分是预览量（preview）还是下载量（download）,默认为下载量
export function downloadAndPreviewPlusMuch(ids, type) {
  return request({
    url: `/xuncha/archives/xunchaProcessFile/downloadAndPreviewPlusMuch?ids=${ids}&type=${type}`,
    method: 'get'
  })
}
