import request from '@/utils/request'

/**
 * 获取轮次接口
 * @param query lunciName-轮次名称，模糊查询；year-查询年份
 */
export function queryLunciList(query) {
  console.log("dayin=================")
  // debugger
  return request({
    url: '/xccommon/base/queryLunciList',
    method: 'get',
    params: query
  })
}

/**
 * 获取批次及年份接口
 * @param query piciName-批次名称，模糊查询；year-查询年份；lunciId-轮次ID
 */
export function queryPiciList(query) {
  return request({
    url: '/xccommon/base/queryPiciList',
    method: 'get',
    params: query
  })
}

/**
 * 获取巡察任务和巡察组的接口
 * @param query piciId-批次ID；year-查询年份；taskName-任务名称，模糊查询；checkType-巡察类型，字典；groupName-巡察组名称，模糊查询
 */
export function queryTaskList(query) {
  return request({
    url: '/xccommon/base/queryTaskList',
    method: 'get',
    params: query
  })
}

/**
 * 获取巡察单位接口
 * @param query piciId-批次ID；taskId-任务ID
 */
export function queryCompanyList(query) {
  return request({
    url: '/xccommon/base/queryCompanyList',
    method: 'get',
    params: query
  })
}

/**
 * 获取业务数据字典接口
 * @param query dictType-字典数据类型
 */
export function queryDictList(query) {
  return request({
    url: '/xccommon/base/queryDictList',
    method: 'get',
    params: query
  })
}

/**
 * 获取单位联络人接口
 * @param query iaisonsCode-联络员工号；liaisonsAccount-联络人账号；liaisonsName-联络人姓名，模糊查询；companyId-公司编号；
 *    companyName-公司名称，模糊查询；deptId-部门编号；deptName-部门名称，模糊查询
 */
export function queryLiaisonsList(query) {
  return request({
    url: '/xccommon/base/queryLiaisonsList',
    method: 'get',
    params: query
  })
}

/**
 * 获取组联络人接口
 * @param query taskId-巡察任务编号；taskName-巡察任务名称；
 */
export function queryLiaisonsUserList(query) {
  return request({
    url: '/xccommon/base/queryLiaisonsUserList',
    method: 'get',
    params: query
  })
}

/**
 * 条件选择用户接口
 * @param query userId-用户ID；username-用户名称，模糊查询；companyId-公司ID；company-公司名称，模糊查询；departmentId 部门ID；department-部门名称，模糊查询
 */
export function queryUserList(name) {
  return request({
    url: '/xccommon/base/queryUserList?username=' + name,
    method: 'get',
  })
}

/**
 * 条件选择用户接口
 * @param query userId-用户ID；username-用户名称，模糊查询；companyId-公司ID；company-公司名称，模糊查询；departmentId 部门ID；department-部门名称，模糊查询
 */
export function queryUumUserList(query) {
  return request({
    url: '/xccommon/base/queryUumUserList',
    method: 'get',
    params: query
  })
}
/**
 * 树形控件数据接口
 * 
 */
export function getBaseLunciTree() {
  return request({
    url: '/xccommon/base/getBaseLunciTree',
    method: 'post',
  })
}

