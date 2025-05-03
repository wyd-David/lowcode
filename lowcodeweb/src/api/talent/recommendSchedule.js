import request, { downloadTemplate } from "@/utils/request";

//获取人才推荐进度列表
export function getRecommendProcessRate(query) {
  return request({
    url: "/talent/collection/getRecommendProcessRate",
    method: "get",
    params: query,
  });
}
//获取人才推荐进度详情
export function getRecommendProcessDetail(query) {
  return request({
    url: "/talent/collection/getRecommendProcessDetail",
    method: "get",
    params: query,
  });
}
//部门_模板下载
export function downloadFile(query) {
  return request({
    method: "get",
    url: "/talent/dept/downloadFile",
    responseType: "blob",
  })
    .then((response) => {
      let blob = new Blob([response]);
      let downloadElement = document.createElement("a");
      let href = window.URL.createObjectURL(blob); //创建下载的链接
      downloadElement.href = href;
      downloadElement.download = `广东公司党委巡察人才推荐表.docx`; //下载后文件名
      document.body.appendChild(downloadElement);
      downloadElement.click(); //点击下载
      document.body.removeChild(downloadElement); //下载完成移除元素
      window.URL.revokeObjectURL(href); //释放掉blob对象
      this.$message.success(`导出成功`);
    })
    .catch((err) => {});
}
//子公司_一键提交校验接口
export function checkSubmit(query) {
  return request({
    url: "/talent/company/checkSubmit",
    method: "post",
    params: query,
  });
}
//人才推荐子公司审核
export function approve(data) {
  return request({
    url: "/talent/company/approve",
    method: "post",
    params: data,
  });
}

//人才推荐子公司审核
export function approveSchedule(data) {
  return request({
    url: "/talent/collection/approve",
    method: "post",
    params: data,
  });
}

//入库管理_部门一键提交_获取完整信息;
export function getCompleteDeptInfo(query) {
  return request({
    url: "/talent/dept/getCompleteDeptInfo",
    method: "get",
    params: query,
  });
}

//入库管理_子公司查询待办信息
export function getCompanyTodoInfo(query) {
  return request({
    url: "/talent/company/getCompanyTodoInfo",
    method: "get",
    params: query,
  });
}

//入库管理_人才推荐_子公司_一键提交
export function oneClickSubmit(id, data) {
  return request({
    url: "/talent/company/oneClickSubmit?taskId=" + id,
    method: "post",
    data: data,
  });
}

export function exportCompany(query) {
  return request({
    url: "/talent/company/exportCompany",
    method: "get",
    params: query,
  });
}

export function exportDept(query) {
  return request({
    url: "/talent/company/exportDept",
    method: "get",
    params: query,
  });
}

//入库管理_人才推荐_巡察办_确认入库校验
export function checkWareHousing(query) {
  return request({
    url: "/talent/collection/checkWareHousing",
    method: "get",
    params: query,
  });
}

//人才推荐进度 点击数字查看人员信息接口
export function getRecommendList(query) {
  return request({
    url: "/talent/recommend/list",
    method: "get",
    params: query,
  });
}

//入库管理_确认入库_获取推荐人员信息
export function getRecommendListByCollectionId(query) {
  return request({
    url: "/talent/recommend/selectByColletionId",
    method: "get",
    params: query,
  });
}

//入库管理_人才推荐_巡察办_确认入库
export function comfirmWareHousing(data) {
  return request({
    url: "/talent/collection/comfirmWareHousing",
    method: "post",
    params: data,
  });
}
