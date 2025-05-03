import request from '@/utils/request'

// 通过类型清理缓存
export function clearRedisCacheByType(query) {
  return request({
    url: '/cache/clearRedisCacheByType',
    method: 'get',
    params: query
  })
}
 
// 通过key清理缓存
export function clearRedisCacheByKey(query) {
  return request({
    url: '/cache/clearRedisCacheByKey',
    method: 'get',
    params: query
  })
}