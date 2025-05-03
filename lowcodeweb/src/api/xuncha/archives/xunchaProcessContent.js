import request from '@/utils/request'

// 查询巡察过程资料库_文件清单 列表
export function listContent(query) {
  return request({
    url: '/xuncha/system/content/list',
    method: 'get',
    params: query
  })
}

// 查询巡察过程资料库_文件清单 详细
export function getContent(contentId) {
  return request({
    url: '/xuncha/system/content/' + contentId,
    method: 'get'
  })
}

// 新增巡察过程资料库_文件清单
export function addContent(data) {
  return request({
    url: '/xuncha/system/content',
    method: 'post',
    data: data
  })
}

// 修改巡察过程资料库_文件清单
export function updateContent(data) {
  return request({
    url: '/xuncha/system/content/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察过程资料库_文件清单
export function delContent(contentId) {
  return request({
    url: '/xuncha/system/content/remove?contentId=' + contentId,
    method: 'get'
  })
}

// 导出巡察过程资料库_文件清单
export function exportContent(query) {
  return request({
    url: '/xuncha/system/content/export',
    method: 'get',
    params: query
  })
}
// 下载巡察过程资料库_文件清单 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/system/content/importTemplate',
    method: 'get'
  })
}
// 巡察过程资料库_文件清单 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/system/content/updateByIds',
    method: 'get',
    params: query
  })
}
// 巡察过程资料库_文件清单 列表查询
export function getSourceList(query) {
  return request({
    url: '/xuncha/system/content/sourceList',
    method: 'get',
    params: query
  })
}

//设置可见范围
export function xunchaProcessPerm(data,controller) {
  return request({
    url: "/archives/"+controller,
    method: "post",
    data: data,
  });
}

//获取可见范围
export function getXunchaList(query,controller) { 
  return request({
    url: "/archives/"+controller+"/list",
    method: "get",
    params: query,
  });
}

//可见范围同步删除
export function xunchaDelete(query,controller) { 
  return request({
    url: "/archives/" + controller + "/delete",
    method: "get",
    params: query,
  });
}
