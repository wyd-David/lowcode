import request from '@/utils/request'

// 查询 角色授权控制管理列表
export function listRoleControlManager(query) {
    return request({
        url: '/system/roleControlManager/list',
        method: 'get',
        params: query
    })
}

// 查询 角色授权控制管理详细
export function getRoleControlManager(roleControlManagerId) {
    return request({
        url: '/system/roleControlManager/' + roleControlManagerId,
        method: 'get'
    })
}

// 新增 角色授权控制管理
export function addRoleControlManager(data) {
    return request({
        url: '/system/roleControlManager',
        method: 'post',
        data: data
    })
}

// 修改 角色授权控制管理
export function updateRoleControlManager(data) {
    return request({
        url: '/system/roleControlManager',
        method: 'put',
        data: data
    })
}

// 删除 角色授权控制管理
export function delRoleControlManager(roleControlManagerId) {
    return request({
        url: '/system/roleControlManager/' + roleControlManagerId,
        method: 'delete'
    })
}

// 导出 角色授权控制管理
export function exportRoleControlManager(query) {
    return request({
        url: '/system/roleControlManager/export',
        method: 'get',
        params: query
    })
}
// 下载 角色授权控制管理导入模板
export function importTemplate() {
    return request({
        url: '/system/roleControlManager/importTemplate',
        method: 'get'
    })
}
//  角色授权控制管理禁用/启用
export function disableEnable(query) {
    return request({
        url: '/system/roleControlManager/updateByIds',
        method: 'get',
        params: query
    })
}
// 查询员工信息
export function getFitnessWhiteViewEmpInfo(query) {
    return request({
        url: '/ViewEmpInfo/ViewEmpInfo/getListByPermission',
        method: 'get',
        params: query
    })
}