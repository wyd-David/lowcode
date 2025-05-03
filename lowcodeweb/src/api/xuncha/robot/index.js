import request from '@/utils/request'

//查询好友列表
export function friendListApi(query) {
  return request({
    url: "/chat/http/chat/friendList/" + query,
    method: "get",
  });
}

//查询聊天记录
export function chatListApi(query) {
  return request({
    url: "/chat/http/chat/msglist/" + query.reviceuserid,
    method: "get",
    params: query,
  });
}

//查询热门问题
export function hotQuestionsApi(query) {
  return request({
    url: "/chat/http/hot/questions",
    method: "get",
    params: query,
  });
}

//猜你所想
export function thinkApi() {
  return request({
    url: "/chat/http/guest/think",
    method: "get",
  });
}

//获取卡片的内容
export function getCardListApi(query) {
  return request({
    url: "/chat/http/chat/getCardList",
    method: "get",
    params: query,
  });
}

