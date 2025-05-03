import request from '@/utils/request'

//考勤数据
export function getaiAttendanceExceptionList(query) {
  return request({
    url: '/aiAttendanceException/list',
    method: 'get',
    params: query
  })
}
// 考勤删除
export function aiAttendanceExceptionRemove(query) {
  return request({
    url: '/aiAttendanceException/remove',
    method: 'get',
    params: query
  })
}
//出差申请单数据
export function getAiBusinessTripList(query) {
  return request({
    url: 'aiBusinessTrip/list',
    method: 'get',
    params: query
  })
}
export function aiBusinessTripRemove(query) {
  return request({
    url: 'aiBusinessTrip/remove',
    method: 'get',
    params: query
  })
}
//发票数据
export function getAiInvoiceInfoList(query) {
  return request({
    url: 'ai/aiInvoiceInfo/list',
    method: 'get',
    params: query
  })
}
export function aiInvoiceInfoRemove(query) {
  return request({
    url: 'ai/aiInvoiceInfo/remove',
    method: 'get',
    params: query
  })
}
// 伙食包干
export function getAiMealsInfoList(query) {
  return request({
    url: 'ai/aiMealsInfo/list',
    method: 'get',
    params: query
  })
}
export function aiMealsInfoRemove(query) {
  return request({
    url: 'ai/aiMealsInfo/remove',
    method: 'get',
    params: query
  })
}
//交通包干
export function getAiTrafficInfoList(query) {
  return request({
    url: 'ai/aiTrafficInfo/list',
    method: 'get',
    params: query
  })
}
export function aiTrafficInfoRemove(query) {
  return request({
    url: 'ai/aiTrafficInfo/remove',
    method: 'get',
    params: query
  })
}
// 结算信息
export function getAiSettlementInfoList(query) {
  return request({
    url: 'ai/aiSettlementInfo/list',
    method: 'get',
    params: query
  })
}
export function aiSettlementInfoRemove(query) {
  return request({
    url: 'ai/aiSettlementInfo/remove',
    method: 'get',
    params: query
  })
}
// 申请单行程
export function getAiBusinessTripDetailList(query) {
  return request({
    url: 'ai/aiBusinessTripDetail/list',
    method: 'get',
    params: query
  })
}
export function aiBusinessTripDetailRemove(query) {
  return request({
    url: 'ai/aiBusinessTripDetail/remove',
    method: 'get',
    params: query
  })
}
// 招待
export function getAiServelList(query) {
  return request({
    url: 'ai/aiServe/list',
    method: 'get',
    params: query
  })
}
export function aiServeRemove(query) {
  return request({
    url: 'ai/aiServe/remove',
    method: 'get',
    params: query
  })
}
// 用车
export function getAiCarNetworkingList(query) {
  return request({
    url: 'ai/aiCarNetworking/list',
    method: 'get',
    params: query
  })
}
export function aiCarNetworkingRemove(query) {
  return request({
    url: 'ai/aiCarNetworking/remove',
    method: 'get',
    params: query
  })
}

// 议题与关键字
export function getMeeting(query) {
  return request({
    url: 'ai/AiMeeting/list',
    method: 'get',
    params: query
  })
}
export function getMeetingRemove(query) {
  return request({
    url: 'ai/AiMeeting/remove',
    method: 'get',
    params: query
  })
}

// 被巡察单位学习记录
export function getMeetingStudy(query) {
  return request({
    url: 'ai/AiMeetingStudy/list',
    method: 'get',
    params: query
  })
}
export function getMeetingStudyRemove(query) {
  return request({
    url: 'ai/AiMeetingStudy/remove',
    method: 'get',
    params: query
  })
}