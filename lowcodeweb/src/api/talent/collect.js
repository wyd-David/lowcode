import request from "@/utils/request";

export function getRecommendList(query) {
  return request({
    url: "/talent/recommend/getRocemmendList",
    method: "get",
    params: query,
  });
}

//根据公司搜索部门
export function getBaseDeptList(query) {
  return request({
    url: "/xccommon/base/getDeptList",
    method: "get",
    params: query,
  });
}
//搜索公司
export function getBaseCompanyList(query) {
  return request({
    url: "/xccommon/base/getCompanyList",
    method: "get",
    params: query,
  });
}

//导出
export function exportCollect(query) {
  return request({
    url: "/talent/recommend/export",
    method: "get",
    params: query,
  });
}

