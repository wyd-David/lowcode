import request from '@/utils/request'

//批次查询
export function getBatchList(query) {
  return request({
    url: '/checkedAll/checkInfo/getBatchList',
    method: 'get',
    params: query
  })
}
//列表数据
export function getList(query){
	return request({
	  url: '/checkedAll/checkedFine/getApplyFineList',
	  method: 'get',
	  params: query
	})
}

//优秀员工推荐导出
export function exportApplyFineList(query) {
  return request({
    url: "/checkedAll/checkedFine/exportApplyFineList",
    method: "get",
    params: query
  });
}
// //
// export function saveApplyInfo(data) {
//   return request({
//     url: "/checkedAll/checkInfo/saveApplyInfo",
//     method: "post",
//     data: data,
//   });
// }