import request from "@/utils/request";

// 查询巡察案例方法库 列表
export function list(data) {
  return request({
    url: "/archives/content/list",
    method: "post",
    data: data
  });
}

//修改
export function update(data) {
  return request({
    url: "/archives/content/update",
    method: "post",
    data,
  });
}

//新增
export function save(data) {
  return request({
    url: "/archives/content/save",
    method: "post",
    data,
  });
}

//详情
export function getById(query) {
    return request({
        url: "/archives/content/getById",
        method: "get",
        params: query,
    });
}



//单个删除
export function deleteById(query) {
  return request({
    url: "/archives/content/deleteById",
    method: "get",
    params: query,
  });
}


//批量删除
export function deleteByIds(query) {
  return request({
    url: "/archives/content/deleteByIds",
    method: "get",
    params: query,
  });
}