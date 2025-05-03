import request from '@/utils/request'

// 查询待办待阅列表
export function listTodoinfo(query) {
    return request({
        url: '/todoinfo/todoinfo/list',
        method: 'post',
        params: query
    })
}

// 查询待办待阅详细
export function getTodoinfo(todoId) {
    return request({
        url: '/todoinfo/' + todoId,
        method: 'post'
    })
}

// 新增待办待阅
export function addTodoinfo(data) {
    return request({
        url: '/todoinfo',
        method: 'post',
        data: data
    })
}

// 修改待办待阅
export function updateTodoinfo(data) {
    return request({
        url: '/todoinfo',
        method: 'put',
        data: data
    })
}

// 删除待办待阅
export function delTodoinfo(todoId) {
    return request({
        url: '/todoinfo/' + todoId,
        method: 'delete'
    })
}

// 导出待办待阅
export function exportTodoinfo(query) {
    return request({
        url: '/todoinfo/export',
        method: 'get',
        params: query
    })
}
// 下载待办待阅导入模板
export function importTemplate() {
    return request({
        url: '/todoinfo/importTemplate',
        method: 'get'
    })
}


// 流程重推
export function againPush(data) {
    return request({
        url: '/todoinfo/todoinfo/againPush',
        method: 'post',
        data: data
    })
}

// 回收
export function recyclePro(data) {
    return request({
        url: '/todoinfo/todoinfo/recycleProcess',
        method: 'post',
        data: data
    })
}
// 查询员工信息
export function getFitnessWhiteViewEmpInfo(query) {
    return request({
        url: '/ViewEmpInfo/ViewEmpInfo/list',
        method: 'get',
        params: query
    })
}
//流程意外处理
export function accidentDeals(data) {
    return request({
      url: "/activiti/fininshFormTask",
      method: "post",
      data: data,
    });
}

// 查询所有推送数据列表
export function listTodoPushInfo(query) {
    return request({
        url: '/todoinfo/todoPushInfo/list',
        method: 'post',
        params: query
    })
}

// 删除推送数据列表
export function delTodoPushInfo(todoId) {
    return request({
        url: '/todoinfo/todoPushInfo/' + todoId,
        method: 'delete'
    })
}

// 修改待办已办
export function updateTodoPushInfo(data) {
    return request({
      url: '/todoinfo/updateTodoPushInfo',
      method: 'post',
      data: data
    })
  }
  
  
  // 查询待办已办详细
  export function getTodoPushInfo(todoId) {
    return request({
      url: '/todoinfo/getTodoPushInfo/' + todoId,
      method: 'get'
    })
  }
  export function selectMyTodoInfoList(body) {
    return request({
      url: '/todoinfo/todoinfoOrApplyinfo/selectMyTodoInfoList',
      method: 'get',
      params: body
    })
  }
  export function getTodayOrderList(body) {
    return request({
      url: '/food/FoodMealsOrder/getTodayOrderList',
      method: 'get',
      params: body
    })
  }
