import request from '@/utils/request'

// 查询流程按钮信息
export function getProcessBnts(query,data) {
  return request({
    url: '/todoinfo/todoinfo/getProcessBnts',
    method: 'post',
    params: query,
    data: data,
  })
}

// 保存流程按钮信息
export function saveProcessBnts(query,data) {
  return request({
    url: '/workflow/models/saveProcessBnts',
    method: 'post',
    params: query,
    data: data,
  })
}
