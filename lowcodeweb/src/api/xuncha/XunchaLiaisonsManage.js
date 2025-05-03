import request from '@/utils/request'

// 查询联络员维护 列表
export function listXunchaLiaisonsManage(query) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/list',
    method: 'get',
    params: query
  })
}

// 查询联络员维护 详细
export function getXunchaLiaisonsManage(liaisonsId) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/' + liaisonsId,
    method: 'get'
  })
}

// 新增联络员维护 
export function addXunchaLiaisonsManage(data) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage',
    method: 'post',
    data: data
  })
}

// 修改联络员维护 
export function updateXunchaLiaisonsManage(data) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/edit',
    method: 'post',
    data: data
  })
}

// 删除联络员维护 
export function delXunchaLiaisonsManage(liaisonsId) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/remove?liaisonsId=' + liaisonsId,
    method: 'get'
  })
}

// 导出联络员维护 
export function exportXunchaLiaisonsManage(query) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/export',
    method: 'get',
    params: query
  })
}
// 下载联络员维护 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/importTemplate',
    method: 'get'
  })
}
// 联络员维护 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/XunchaLiaisonsManage/updateByIds',
    method: 'get',
    params: query
  })
}
//获取用户列表
export function selectLiaisonsData(name) {
  return request({
    url: 'xuncha/XunchaLiaisonsManage/selectLiaisonsData?username=' + name,
    method: 'get'
  })
}
