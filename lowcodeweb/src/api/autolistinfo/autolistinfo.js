import request from '@/utils/request'


//查询动态数据
export function selectAutoTableData(data) {
  return request({
    url: '/datasource/getTableInfo',
    method: 'post',
    params: data
  })
}
