import request from '@/utils/request'


//批次查询
export function getBatchList(query) {
  return request({
    url: '/checkedAll/checkInfo/getBatchList',
    method: 'get',
    params: query
  })
}

//获取评分内容-根据评分类型ID
export function getExamContentByExamType(query) {
  return request({
    url: '/checkedAll/checkInfo/getExamContentByExamType',
    method: 'get',
    params: query
  })
}

export function getList(query){
	return request({
	  url: '/checkedAll/checkedCollect/' + query.getMethod,
	  method: 'get',
	  params: query
	})
}
//
// export function saveApplyInfo(data) {
//   return request({
//     url: "/checkedAll/checkInfo/saveApplyInfo",
//     method: "post",
//     data: data,
//   });
// }
//考核汇总- 巡察组导出
export function exportGroupList(query) {
  return request({
    url: '/checkedAll/checkedCollect/exportGroupList',
    method: 'get',
    params: query
  })
}
//考核汇总- 巡察员导出
export function exportPerList(query) {
  return request({
    url: '/checkedAll/checkedCollect/exportPerList',
    method: 'get',
    params: query
  })
}


export function exportGroupDetail(query) {
  return request({
    url: '/checkedAll/checkedCollect/exportGroupDetail',
    method: 'get',
    params: query
  })
}

export function exportPatrolDetail(query) {
  return request({
    url: '/checkedAll/checkedCollect/exportPatrolDetail',
    method: 'get',
    params: query
  })
}

export function exportToExamOfficeDetail(query) {
  return request({
    url: '/checkedAll/checkedCollect/exportToExamOfficeDetail',
    method: 'get',
    params: query
  })
}

export function exportPerDetail(query) {
  return request({
    url: '/checkedAll/checkedCollect/exportPerDetail',
    method: 'get',
    params: query
  })
}
