import request from '@/utils/request'


//列表查询
export function yearCheckedList(query) {
  return request({
    url: '/checkedAll/yearChecked/getList',
    method: 'get',
    params: query
  })
}
//年评查看详情
export function getUserExamDetail(query) {
  return request({
    url: '/checkedAll/yearChecked/getUserExamHisOfOther',
    method: 'get',
    params: query
  })
}

//年评分数历史详情
export function getUserExamResult(query) {
  return request({
    url: '/checkedAll/yearChecked/getUserExamHisOfResult',
    method: 'get',
    params: query
  })
}

//生成离库名单
export function createLeaveList(query) {
  return request({
    url: '/checkedAll/yearChecked/createLeaveList',
    method: 'get',
    params: query
  })
}
//列表导出
export function yearCheckedExportList(query) {
  return request({
    url: '/checkedAll/yearChecked/exportList',
    method: 'get',
    params: query
  })
}

//离库推荐导出
export function yearCheckedExportLeaveList(query) {
  return request({
    url: '/checkedAll/yearChecked/exportLeaveList',
    method: 'get',
    params: query
  })
}

//获取评分内容-根据评分类型ID
// export function getExamContentByExamType(query) {
//   return request({
//     url: '/checkedAll/checkInfo/getExamContentByExamType',
//     method: 'get',
//     params: query
//   })
// }
//
// export function saveApplyInfo(data) {
//   return request({
//     url: "/checkedAll/checkInfo/saveApplyInfo",
//     method: "post",
//     data: data,
//   });
// }
