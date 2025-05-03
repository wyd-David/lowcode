import request from '@/utils/request'


//批次查询
export function getBatchList(query) {
  return request({
    url: '/checkedAll/checkInfo/getBatchList',
    method: 'get',
    params: query
  })
}
//查询优秀员工申报名单 列表
export function checkedFineList(query) {
  return request({
    url: '/checkedAll/checkedFine/list',
    method: 'get',
    params: query
  })
}
//获取未申报优良记录列表
export function getToApplyFineList(query) {
  return request({
    url: '/checkedAll/checkedFine/getToApplyFineList',
    method: 'get',
    params: query
  })
}
//获取择优添加列表
export function getOtherFinePerList(query) {
  return request({
    url: '/checkedAll/checkedFine/getOtherFinePerList',
    method: 'get',
    params: query
  })
}
//删除优秀员工申报名单
export function delCheckedFine(query) {
  return request({
    url: '/checkedAll/checkedFine/remove',
    method: 'get',
    params: query
  })
}
//暂存优秀员工申报
export function saveFineApply(data) {
  return request({
    url: "/checkedAll/checkedFine/saveFineApply",
    method: "post",
    data: data,
  });
}
//提交优秀员工申报
export function submitFineApply(data) {
  return request({
    url: "/checkedAll/checkedFine/submitFineApply",
    method: "post",
    data: data,
  });
}
//获取附件
export function getFileListOfApply(query) {
  return request({
    url: "/checkedAll/checkedFine/getFileListOfApply",
    method: "get",
    params: query
  });
}
//审批申报记录
export function approveApplyInfo(data) {
  return request({
    url: "/checkedAll/checkedFine/approveApplyInfo",
    method: "post",
    data: data,
  });
}
//根据id反查年份
export function queryTaskUnit(query) {
  return request({
    url: '/xuncha/xunchaTaskUnitInfo/list',
    method: 'post',
    params: query,
  })
}
