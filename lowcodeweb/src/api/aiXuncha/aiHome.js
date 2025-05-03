import request from '@/utils/request'

/**
 * 首页相关的接口
 */

// 全省差旅异常情况
export function getXunchaCitiesYear(xunchaLunciId) {
  return request({
    url: `/ai/home/getXunchaCitiesYear?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 稽核无文件出差集中度模型
export function notEvectionFile(xunchaLunciId) {
  return request({
    url: `/ai/home/notEvectionFile?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 未出差报销差旅费（与考勤比对）
export function travelExpenseAttendance(xunchaLunciId) {
  return request({
    url: `/ai/home/travelExpenseAttendance?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 未出差报销差旅费（与招待比对）
export function travelExpenseServe(xunchaLunciId) {
  return request({
    url: `/ai/home/travelExpenseServe?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 分析酒店发票集中度模型
export function hotelConcentrationRatio(xunchaLunciId) {
  return request({
    url: `/ai/home/hotelConcentrationRatio?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 重复报账
export function repetitionSubmit(xunchaLunciId) {
  return request({
    url: `/ai/home/repetitionSubmit?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 出差半天却报销全天伙食补贴
export function foodUnreasonable(xunchaLunciId) {
  return request({
    url: `/ai/home/foodUnreasonable?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 即派车又报销差旅补贴
export function tripUnreasonable(xunchaLunciId) {
  return request({
    url: `/ai/home/tripUnreasonable?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}

// 出差期间申请用车
export function evectionUseCar(xunchaLunciId) {
  return request({
    url: `/ai/home/evectionUseCar?xunchaLunciId=${xunchaLunciId}`,
    method: 'get',
  })
}
// 出差期间申请用车
export function getDateStr() {
  return request({
    url: `/ai/home/getDateStr`,
    method: 'get',
  })
}
