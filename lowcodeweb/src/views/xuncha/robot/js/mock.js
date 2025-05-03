export const friendListApi = () => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({
        code: 0,
        msg: "success",
        data: {
          userid: "admin",
          username: "admin",
        },
      });
    }, 1000);
  });
};

export const chatListApi = (offset = 0, pageSize = 10) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({
        code: 0,
        msg: "success",
        data: {
          list: [
            {
              msgId: 1,
              fromUserId: 1,
              fromUserName: "admin",
              toUserId: 1,
              toUserName: 1,
              msgType: 1,
              content: "巡察工作",
              dialogType: 1,
              status: 1,
              createdBy: 1,
              createdTime: null,
              updatedBy: 1,
              updatedTime: null,
              delFlag: 1,
            },
          ],
        },
      });
    }, 1000);
  });
};
