import request from '@/utils/request'

// 查询我的工单列表
export function list(query) {
  return request({
    url: '/procinst/list',
    method: 'post',
    data: query,
    params: query
  })
}

// 删除我的工单
export function deleteDraftOrder(query) {
  return request({
    url: "/procinst/deleteDraftOrder",
    method: "post",
    data: query,
  });
}

