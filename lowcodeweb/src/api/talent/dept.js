import request from "@/utils/request";

// 查询入库管理_人才库收集管理各部门指标 列表
export function listDept(query) {
  return request({
    url: "/talent/dept/list",
    method: "get",
    params: query,
  });
}

// 查询入库管理_人才库收集管理各部门指标 详细
export function getDept(id) {
  return request({
    url: "/talent/dept/" + id,
    method: "get",
  });
}

// 新增入库管理_人才库收集管理各部门指标
export function addDept(data) {
  return request({
    url: "/talent/dept",
    method: "post",
    data: data,
  });
}

// 修改入库管理_人才库收集管理各部门指标
export function updateDept(data) {
  return request({
    url: "/talent/dept/edit",
    method: "post",
    data: data,
  });
}

// 删除入库管理_人才库收集管理各部门指标
export function delDept(id) {
  return request({
    url: "/talent/dept/remove?id=" + id,
    method: "get",
  });
}

// 导出入库管理_人才库收集管理各部门指标
export function exportDept(query) {
  return request({
    url: "/talent/dept/export",
    method: "get",
    params: query,
  });
}
// 下载入库管理_人才库收集管理各部门指标 导入模板
export function importTemplate() {
  return request({
    url: "/talent/dept/importTemplate",
    method: "get",
  });
}
// 入库管理_人才库收集管理各部门指标 禁用/启用
export function disableEnable(query) {
  return request({
    url: "/talent/dept/updateByIds",
    method: "get",
    params: query,
  });
}

//获取待办详情
export function getTodoInfo(data) {
  return request({
    url: "/talent/company/getTodoInfo",
    method: "get",
    params:data
  });
}

//获取部门列表
export function getDeptList(query) {
  return request({
    url: "/talent/company/getDeptList",
    method: "get",
    params: query,
  });
}
//获取部门联络人
export function getDeptContactPerson(id) {
  return request({
    url: "/talent/company/getDeptContactPerson?deptId=" + id,
    method: "get",
  });
}

//下发
export function issued(data) {
  return request({
    url: "/talent/company/issued",
    method: "post",
    data: data,
  });
}

//暂存
export function c_temporarySave(data) {
  return request({
    url: "/talent/company/temporarySave",
    method: "post",
    data: data,
  });
}

//根据id获取人才推荐各子公司列表
export function getByColletionId(data) {
  return request({
    url: "/talent/dept/getByColletionId",
    method: "get",
    params: data,
  });
}
//根据id获取部门
export function getDeptById(id) {
  return request({
    url: "/talent/dept/" + id,
    method: "get",
  });
}

//根据id获取公司
export function getCompanyById(id) {
  return request({
    url: "/talent/company/" + id,
    method: "get",
  });
}

//根据名字查用户
export function getUserInfo(query) {
  return request({
    url: "/xccommon/base/getUserInfo",
    method: "get",
    params: query,
  });
}

//部门推荐，搜索可推荐人员
export function getCanRecommendList(query) {
  return request({
    url: "/talent/dept/getCanRecommendList",
    method: "get",
    params: query,
  });
}

//人才推荐部门待办流程推动
export function finishFormTask(id, data) {
  return request({
    url: "/talent/dept/fininshFormTask?taskId=" + id,
    method: "post",
    data: data,
  });
}
//人才推荐催办
export function urge(id) {
  let fm = new FormData();
  fm.append("ids", id);
  return request({
    url: "/talent/dept/urge",
    method: "post",
    data: fm,
  });
}

//人才推荐部门待办,暂存
export function temporarySave(data) {
  return request({
    url: "/talent/dept/temporarySave",
    method: "post",
    data: data,
  });
}

//名单下载
export function downRecommendPeople(query) {
  return request({
    url: "/talent/recommend/downRecommendPeople",
    method: "get",
    params: query,
  });
}

//名单下载——下载推荐的人信息
export function downloadZip(query) {
  return request({
    url: "/talent/recommend/downloadZip",
    method: "get",
    params: query,
    responseType: "blob",
  })
    .then((response) => {
      let blob = new Blob([response]);
      let downloadElement = document.createElement("a");
      let href = window.URL.createObjectURL(blob); //创建下载的链接
      downloadElement.href = href;
      downloadElement.download = `推荐人信息.zip`; //下载后文件名
      document.body.appendChild(downloadElement);
      downloadElement.click(); //点击下载
      document.body.removeChild(downloadElement); //下载完成移除元素
      window.URL.revokeObjectURL(href); //释放掉blob对象
      this.$message.success(`导出成功`);
    })
    .catch((err) => {});;
}
