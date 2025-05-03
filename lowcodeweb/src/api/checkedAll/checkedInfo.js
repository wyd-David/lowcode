import request from '@/utils/request'

// 查询巡察考核发起记录列表
export function listCheckedInfo(query) {
  return request({
    url: '/checkedAll/checkInfo/list',
    method: 'get',
    params: query
  })
}
//批次查询
export function getBatchList(query) {
  return request({
    url: '/checkedAll/checkInfo/getBatchList',
    method: 'get',
    params: query
  })
}

//发起审核-查询批次小组
export function GroupList(query) {
  return request({
    url: '/checkedAll/checkInfo/getBeforeGroupList',
    method: 'get',
    params: query
  })
}
//获取各分组各考核类型评分人列表列表
export function getExamLeaderList(query) {
  return request({
    url: '/checkedAll/checkInfo/getExamLeaderList',
    method: 'get',
    params: query
  })
}


// 查询巡察考核发起记录详细
export function getApplyInfo(params) {
  return request({
    url: '/checkedAll/checkInfo/getApplyInfo',
    method: 'get',
    params: params,
  })
}
//暂存巡察考核发起记录

export function saveApplyInfo(data) {
  return request({
    url: "/checkedAll/checkInfo/saveApplyInfo",
    method: "post",
    data: data,
  });
}
// 新增巡察考核发起记录
export function addCheckedInfo(data) {
  return request({
    url: '/checkedAll/checkedInfo',
    method: 'post',
    data: data
  })
}
//提交发起审核
export function submitApplyInfo(data) {
  return request({
    url: '/checkedAll/checkInfo/submitApplyInfo',
    method: 'post',
    data: data
  })
}
// 修改巡察考核发起记录
export function updateCheckedInfo(data) {
  return request({
    url: '/checkedAll/checkedInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察考核发起记录
export function delCheckedInfo(params) {
  return request({
    url: '/checkedAll/checkInfo/remove',
    method: 'get',
    params: params,
  })
}

// 导出巡察考核发起记录
export function exportCheckedInfo(query) {
  return request({
    url: '/checkedAll/checkedInfo/export',
    method: 'get',
    params: query
  })
}
// 下载巡察考核发起记录导入模板
export function importTemplate() {
  return request({
    url: '/checkedAll/checkedInfo/importTemplate',
    method: 'get'
  })
}
// 巡察考核发起记录禁用/启用
export function disableEnable(query) {
  return request({
    url: '/checkedAll/checkedInfo/updateByIds',
    method: 'get',
    params: query
  })
}
//发起考核室经理审批
export function approveApplyInfo(data) {
  return request({
    url: '/checkedAll/checkInfo/approveApplyInfo',
    method: 'post',
    data: data
  })
}
/*领导小组评分
  */
//领导小组评分列表
export function getLeaderListToExam(query) {
  return request({
    url: '/checkedAll/checkInfo/getLeaderListToExam',
    method: 'get',
    params: query
  })
}
//领导小组评分导出
export function exportLeaderListToExam(query) {
  return request({
    url: '/checkedAll/checkInfo/exportLeaderListToExam',
    method: 'get',
    params: query
  })
}
//获取评分内容-根据评分类型ID
export function getExamContentByExamType(query) {
  return request({
    url: '/checkedAll/checkInfo/getExamContentByExamType',
    method: 'get',
    params: query
  })
}
//领导小组暂存
export function saveLeaderListToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/saveLeaderListToExam',
    method: 'post',
    data: data
  })
}
//领导小组提交
export function submitLeaderListToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/submitLeaderListToExam',
    method: 'post',
    data: data
  })
}
//考核进度领导小组提交

export function batchSubmitLeaderListToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/batchSubmitLeaderListToExam',
    method: 'post',
    data: data
  })
}
//巡察办评分列表

export function getPatrolToExam(query) {
  return request({
    url: '/checkedAll/checkInfo/getPatrolToExam',
    method: 'get',
    params: query
  })
}
//巡察办评分导出
export function exportPatrolToExam(query) {
  return request({
    url: '/checkedAll/checkInfo/exportPatrolToExam',
    method: 'get',
    params: query
  })
}
//巡察办暂存
export function savePatrolToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/savePatrolToExam',
    method: 'post',
    data: data
  })
}
//巡察办提交
export function submitPatrolToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/submitPatrolToExam',
    method: 'post',
    data: data
  })
}

//被巡察单位详情
export function getToExamOfficeToExam(query) {
  return request({
    url: '/checkedAll/checkInfo/getToExamOfficeToExam',
    method: 'get',
    params: query
  })
}
//被巡察单位导出
export function exportToExamOfficeToExam(query) {
  return request({
    url: '/checkedAll/checkInfo/exportToExamOfficeToExam',
    method: 'get',
    params: query
  })
}
//被巡察单位暂存
export function saveToExamOfficeToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/saveToExamOfficeToExam',
    method: 'post',
    data: data
  })
}
//被巡察单位提交
export function submitToExamOfficeToExam(data) {
  return request({
    url: '/checkedAll/checkInfo/submitToExamOfficeToExam',
    method: 'post',
    data: data
  })
}
//组员评分详情
export function getExamOfGroupPre(query) {
  return request({
    url: '/checkedAll/checkInfo/getExamOfGroupPre',
    method: 'get',
    params: query
  })
}
//组员评分导出
export function exportExamOfGroupPre(query) {
  return request({
    url: '/checkedAll/checkInfo/exportExamOfGroupPre',
    method: 'get',
    params: query
  })
}
///组员评分暂存
export function saveExamOfGroupPre(data) {
  return request({
    url: '/checkedAll/checkInfo/saveExamOfGroupPre',
    method: 'post',
    data: data
  })
}
//组员评分提交前获取调配人数
export function getPersonNumber(data) {
  return request({
    url: '/checkedAll/checkInfo/getPersonNumber',
    method: 'post',
    data: data
  })
}
//组员评分提交
export function submitExamOfGroupPre(data) {
  return request({
    url: '/checkedAll/checkInfo/submitExamOfGroupPre',
    method: 'post',
    data: data
  })
}
//发起巡察员审核-查询批次小组
export function GroupListPer(query) {
  return request({
    url: '/checkedAll/checkInfo/getBeforeGroupListPer',
    method: 'get',
    params: query
  })
}
//发起巡察员审核-暂存巡察考核发起记录

export function saveApplyInfoPer(data) {
  return request({
    url: "/checkedAll/checkInfo/saveApplyInfoPer",
    method: "post",
    data: data,
  });
}
//发起巡察员审核-提交发起审核
export function submitApplyInfoPer(data) {
  return request({
    url: '/checkedAll/checkInfo/submitApplyInfoPer',
    method: 'post',
    data: data
  })
}
//考核进度
export function getProgressList(query) {
  return request({
    url: '/checkedAll/checkInfo/getProgressList',
    method: 'get',
    params: query
  })
}
//考核进度-领导小组详情
export function getLeaderListProgress(query){
  return request({
    url: '/checkedAll/checkInfo/getLeaderListProgress',
    method: 'get',
    params: query
  })
}
//考核进度-巡察办详情
export function getPatrolProgressInfo(query){
  return request({
    url: '/checkedAll/checkInfo/getPatrolProgressInfo',
    method: 'get',
    params: query
  })
}
//考核进度-被巡察小组详情
export function getToexamProgressInfo(query){
  return request({
    url: '/checkedAll/checkInfo/getToexamProgressInfo',
    method: 'get',
    params: query
  })
}
//考核进度-组员评分详情

export function getGroupMemberProgress(query){
  return request({
    url: '/checkedAll/checkInfo/getGroupMemberProgress',
    method: 'get',
    params: query
  })
}
//考核进度-组员评分详情-2

export function getPerDetail(query){
  return request({
    url: '/checkedAll/checkedCollect/getPerDetail',
    method: 'get',
    params: query
  })
}
//考核汇总-详情-领导小组

export function getGroupDetail(query){
  return request({
    url: '/checkedAll/checkedCollect/getGroupDetail',
    method: 'get',
    params: query
  })
}
//考核汇总-详情-领导小组

export function getPatrolDetail(query){
  return request({
    url: '/checkedAll/checkedCollect/getPatrolDetail',
    method: 'get',
    params: query
  })
}

//考核进度-领导小组评分导出
export function exportLeaderListProgress(query) {
  return request({
    url: '/checkedAll/checkInfo/exportLeaderListProgress',
    method: 'get',
    params: query
  })
}

//考核进度-巡察办评分导出
export function exportPatrolProgressInfo(query) {
  return request({
    url: '/checkedAll/checkInfo/exportPatrolProgressInfo',
    method: 'get',
    params: query
  })
}

//考核进度- 被考核单位评分导出
export function exportToexamProgressInfo(query) {
  return request({
    url: '/checkedAll/checkInfo/exportToexamProgressInfo',
    method: 'get',
    params: query
  })
}

//考核进度- 被考核单位评分导出
export function exportGroupMemberProgress(query) {
  return request({
    url: '/checkedAll/checkInfo/exportGroupMemberProgress',
    method: 'get',
    params: query
  })
}