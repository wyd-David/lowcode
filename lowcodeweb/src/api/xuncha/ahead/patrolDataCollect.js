import request from "@/utils/request";

// 新增巡前资料_资料收集
export function add(data) {
  return request({
    url: "/ahead/collection",
    method: "post",
    data: data,
  });
}

//查询巡前资料_资料收集列表;
export function list(query) {
  return request({
    url: "/ahead/collection/list",
    method: "get",
    params:query
  });
}
//删除巡前资料_资料收集列表;
export function remove(query) {
  return request({
    url: "/ahead/collection/remove",
    method: "get",
    params: query,
  });
}

// 修改巡前资料_资料收集
export function edit(data) {
  return request({
    url: "/ahead/collection/edit",
    method: "post",
    data: data,
  });
}

// 获取巡前资料_资料收集详细信息
export function getInfo(id) {
  return request({
    url: "/ahead/collection/" + id,
    method: "get",
  });
}

//在库管理_人员标签设置_暂存
export function temporarySave(data) {
  return request({
    url: "/ahead/collection/temporarySave",
    method: "post",
    data: data,
  });
}
//根据部门ids查询资料清单
export function selectBydeptIds(query) { 
  return request({
    url: "/ahead/inventory/selectBydeptIds",
    method: "get",
    params:query
  });
}


