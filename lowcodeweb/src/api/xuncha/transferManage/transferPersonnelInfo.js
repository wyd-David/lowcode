import request from "@/utils/request";

// 根据批次获取标签
export function getXunchaTransferPersonnelLabel(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/getXunchaTransferPersonnelLabel",
    method: "get",
    params: query,
  });
}
// 根据批次标签获取领域
export function getXunchaTransferPersonnelField(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/getXunchaTransferPersonnelField",
    method: "get",
    params: query,
  });
}
// 查询人员抽调_抽调批次人员信息 列表
export function listTransferPersonnelInfo(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/list",
    method: "get",
    params: query,
  });
}

// 查询人员抽调_抽调批次人员信息 详细
export function getTransferPersonnelInfo(personnelInfoId) {
  return request({
    url: "/transferManage/transferPersonnelInfo/" + personnelInfoId,
    method: "get",
  });
}

export function getPresetsInfolist(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/getPresetsInfolist",
    method: "get",
    params: query,
  });
}

// 查询人员抽调_抽调批次人员信息 详细
export function getUserDetail(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/getUserDetail",
    method: "get",
    params: query,
  });
}

// 新增人员抽调_抽调批次人员信息
export function addTransferPersonnelInfo(data) {
  return request({
    url: "/transferManage/taskTransferInfo/addTaskTransferInfo",
    method: "post",
    data: data,
  });
}
// 获取抽调中各领域人员数量
export function getTransferPersonnelCount(data) {
  return request({
    url:
      "/transferManage/transferPersonnelInfo/getTransferPersonnelCount?piciId=" +
      data.piciId,
    method: "post",
  });
}

// 修改人员抽调_抽调批次人员信息
export function updateTransferPersonnelInfo(data) {
  return request({
    url: "/transferManage/transferPersonnelInfo/edit",
    method: "post",
    data: data,
  });
}

// 删除人员抽调_抽调批次人员信息
export function delTransferPersonnelInfo(personnelInfoId) {
  return request({
    url:
      "/transferManage/transferPersonnelInfo/remove?personnelInfoId=" +
      personnelInfoId,
    method: "get",
  });
}

// 导出人员抽调_抽调批次人员信息
export function exportTransferPersonnelInfo(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/export",
    method: "get",
    params: query,
  });
}
// 下载人员抽调_抽调批次人员信息 导入模板
export function importTemplate() {
  return request({
    url: "/transferManage/transferPersonnelInfo/importTemplate",
    method: "get",
  });
}
// 个人参与信息待办
export function getSendTransferInfo(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/getSendTransferInfo",
    method: "get",
    params: query,
  });
}
export function personnelInfoConfirm(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/personnelInfoConfirm",
    method: "get",
    params: query,
  });
}

// 重抽
export function onAgainTransferPersonnelInfo(data) {
  return request({
    url: "/transferManage/transferPersonnelInfo/onAgain",
    method: "post",
    data: data,
  });
}
// 抽调名单 催办
export function sendTransferList(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/sendTransferList",
    method: "get",
    params: query,
  });
}

//人员确认流程审核
export function fininshPersonnelToExamine(data) {
  return request({
    url: "/transferManage/transferPersonnelInfo/fininshPersonnelToExamine",
    method: "post",
    data: data,
  });
}

//导出抽调人員列表
export function exportPersonInfo(query) {
  return request({
    url: "transferManage/taskTransferInfo/exportPersonInfo",
    method: "get",
    params: query,
  });
}

//新增抽调预设人员
export function addPresetsInfo(data) {
  return request({
    url: "/transferManage/taskTransferInfo/addPresetsInfo",
    method: "post",
    data: data,
  });
}

//删除抽调预设人员
export function removePersonnel(query) {
  return request({
    url: "/transferManage/transferPersonnelInfo/removePersonnel",
    method: "get",
    params: query,
  });
}
