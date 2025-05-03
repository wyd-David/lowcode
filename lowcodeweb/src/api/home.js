import request from "@/utils/request";

//获取人才队伍建设数据
export function getPersonNumberCountApi() {
  return request({
    url: "/homePage/getPersonNumberCount",
    method: "get",
  });
}

//获取年龄分布数据
export function getPersonAgeCountApi() {
  return request({
    url: "/homePage/getPersonAgeCount",
    method: "get",
  });
}

//获取职级分布数据
export function getPersonPostLevelCountApi() {
  return request({
    url: "/homePage/getPersonPostLevelCount",
    method: "get",
  });
}

//获取专家领域分布数据
export function getPersonFieldCountApi(query) {
  return request({
    url: "/homePage/getPersonFieldCount",
    method: "get",
    params: query,
  });
}

//获取优秀巡察员数据
export function getExcellentPeopleApi(query) {
  return request({
    url: "/homePage/getExcellentPeople",
    method: "get",
    params: query,
  });
}

//获取优秀巡察组数据
export function getExcellentGroupApi(query) {
  return request({
    url: "/homePage/getExcellentGroup",
    method: "get",
    params: query,
  });
}

//获取巡察案例库
export function getCaseCountApi() {
  return request({
    url: "/homePage/getCaseCount",
    method: "get",
  });
}

//获取过程资料库接口
export function getProcessFileCountApi(query) {
  return request({
    url: "/homePage/getProcessFileCount",
    method: "get",
    params: query,
  });
}

//获取报告报表库接口
export function getReportContentCountApi(query) {
  return request({
    url: "/homePage/getReportContentCount",
    method: "get",
    params: query,
  });
}

// 获取巡察制度流程库接口
export function getRegulationCountApi(query) {
  return request({
    url: "/homePage/getRegulationCount",
    method: "get",
    params: query,
  });
}

//获取所有地市公司信息
export function getAllProvinceApi(query) {
  return request({
    url: "/homePage/getAllProvince",
    method: "get",
    params: query,
  });
}

//获取所有轮次
export function getLunciDataApi() {
  return request({
    url: "/xuncha/XunchaLunciManage/selectLunciData",
    method: "get",
  });
}


//获取全省巡察情况接口
export function getProvinceConditionApi(query) {
  return request({
    url: "/homePage/getProvinceCondition",
    method: "get",
    params: query,
  });
}

//获取年度巡察进度
export function getYearProgressRateApi(params) {
  return request({
    url: "/homePage/getYearProgressRate",
    method: "get",
    params: params,
  });
}
