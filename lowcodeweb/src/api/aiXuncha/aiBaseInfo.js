import request from '@/utils/request'


//获得模型文件上传的数据
export function getModelData(query) {
  return request({
    url: '/aiBaseInfo/getModelData',
    method: 'get',
    params: query
  })
}
//插入数据分析
export function startOneAnalyse(data) {
  return request({
    url: '/aiBaseInfo/startOneAnalyse',
    method: 'post',
    data: data
  })
}
//检查是否已存在未分析的定时任务
export function validateAnalyse(data) {
  return request({
    url: '/aiBaseInfo/validateAnalyse',
    method: 'post',
    data: data
  })
}

//动态get接口
export function getJSON(url,query) {
  return request({
    url: url,
    method: 'get',
    params: query||{}
  })
}

//动态post接口
export function postJSON(url,data) {
  return request({
    url: url,
    method: 'post',
    data: data||{}
  })
}

//历史分析记录
export function getHistoryAnalyse(query) {
  return request({
    url: 'aiBaseInfo/historyAnalyse',
    method: 'get',
    params: query
  })
}

//发票标记列表
export function aiResultHotelFapiaoRiskInfo(query) {
  return request({
    url: 'ai/aiResultHotelFapiaoRiskInfo/list',
    method: 'get',
    params: query
  })
}
//标记添加
export function RiskInfoAdd(data) {
  return request({
    url: "ai/aiResultHotelFapiaoRiskInfo",
    method: 'post',
    data: data||{}
  })
}
//标记修改
export function RiskInfoEdit(data) {
  return request({
    url: "ai/aiResultHotelFapiaoRiskInfo/edit",
    method: 'post',
    data: data||{}
  })
}
// 获取左侧菜单
export function getMenuJsonData() {
  return request({
    url: "/bingoAiMenu/getMenuJsonData",
    method: 'get',
  })
}

//查询分析结构
export function analysisDataList(data) {
  return request({
    url: '/aiBaseInfo/getLastModelData',
    method: 'get',
    params: data
  })
}

// 上传文件
export function fileConfirmSave(recordId){
  return request({
    url: `/aiBaseInfo/fileConfirmSave?recordId=${recordId}`,
    method: 'get'
  })
}

// 一键删除
export function batchDelete(data){
  return request({
    url:`/aiBaseInfo/batchDelete`,
    method:'post',
    data: data
  })
}
//协议酒店列表
export function aiAgreementHotelInfo(data){
  return request({
    url: `/ai/aiAgreementHotelInfo/list`,
    method: 'get',
    params: data
  })
}

//协议酒店新增
export function aiAgreementHotelAdd(data){
  return request({
    url: `/ai/aiAgreementHotelInfo`,
    method:'post',
    data: data
  })
}
//协议输入酒店名字获取信息

export function getAiInvoiceInfoList(data){
  return request({
    url: `/ai/aiInvoiceInfo/getAiInvoiceInfoList`,
    method:'get',
    params: data
  })
}

//协议酒店修改
export function aiAgreementHotelaEdit(data){
  return request({
    url: `/ai/aiAgreementHotelInfo/edit`,
    method:'post',
    data: data
  })
}
//协议酒店删除
export function aiAgreementHotelaDele(data){
  return request({
    url: `/ai/aiAgreementHotelInfo/remove`,
    method:'get',
    params: data
  })
}
//协议酒店导出
export function aiAgreementHotelaExport(data){
  return request({
    url: `/ai/aiAgreementHotelInfo/export`,
    method:'get',
    params: data
  })
}