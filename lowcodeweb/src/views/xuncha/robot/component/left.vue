<template>
  <div class="robot_left">
    <div class="checkBox">
      <div
        @click="handleCheckAll"
        :class="checkAll ? 'checkBox-item active' : 'checkBox-item'"
      >
        全选
      </div>
      <div
        @click="handleCheck(item)"
        :class="
          !checkList.find((res) => res.id == item.id)
            ? 'checkBox-item'
            : 'checkBox-item active'
        "
        v-for="item in checkLabes"
        :key="item.id"
      >
        {{ item.name }}
      </div>
    </div>
    <div class="load-more" v-if="loading">
      <i class="el-icon-loading"></i>
    </div>
    <div class="no-next-page" v-if="!isNextPage && isShowTip">没有更多了</div>
    <div style="height: 20px"></div>
    <div class="chat-list" id="chatList" @scroll="handleScroll($event)">
      <message v-for="item in chatList" :key="item.id" :obj="item"></message>
      <div style="height: 101px"></div>
    </div>
    <div class="msg-input-container">
      <div
        ref="msgInput"
        @keydown="textareaKeydown($event)"
        v-html="context"
        @input="textareaInput"
        contenteditable="true"
        @blur="context = $event.target.innerText"
        class="input-wrapper"
      ></div>
      <div class="limit-tip">字数:{{ divCount }}</div>
      <div class="sendBtn">
        <el-button
          :disabled="disabled"
          @click="send"
          style="width: 76px; height: 32px"
          type="primary"
          size="mini"
          ><img
            src="../../../../assets/images/robot/send@2x.png"
            width="14px"
          />
          发送</el-button
        >
      </div>
    </div>
  </div>
</template>
<script>
import message from "./message.vue";
// import { friendListApi, chatListApi } from "../js/mock.js";
import { friendListApi, chatListApi } from "@/api/xuncha/robot";
import { parseTime } from "@/utils/ruoyi";
export default {
  components: { message },
  data() {
    return {
      friendList: [{}],
      loading: false,
      chatList: [],
      disabled: true,
      context: "",
      st: null,
      curScollTop: 0,
      offset: 0, //偏移量
      isNextPage: true,
      isShowTip: true,
      userInfo: this.$store.getters.userInfo || { sex: 0 },
      searchKeyword: "",
      checkList: [],
      checkAll: true,
      checkLabes: [
        { name: "过程资料库", id: 3 },
        { name: "报告报表库", id: 4 },
        { name: "巡察制度流程库", id: 2 },
        { name: "案例库", id: 1 },
      ],
      divCount: "0/50",
    };
  },
  created() {
    this.getFriendList();
  },
  mounted() {
    this.setScrollTop();
    let that = this;
    window.weatherEvent.on("onmessageWS", function (e) {
      let tempObj = JSON.parse(e.detail.data);
      if (tempObj.data && tempObj.data.contentJson) {
        that.chatList.push(tempObj.data);
        that.setScrollTop();
      }
    });

    const editDiv = document.querySelector(".input-wrapper");

    editDiv.addEventListener("keydown", (e) => {
      limitTextLength(e);
    });

    editDiv.addEventListener("click", () => {
      editDiv.setAttribute("contenteditable", "true");
      editDiv.focus();
    });
    function limitTextLength(e) {
      let textLength = editDiv.innerText.length;
      let allowKey = [8, 13, 37, 38, 39, 40]; // 上下左右 回车 删除
      let maxLength = 50;
      if (textLength >= maxLength && allowKey.indexOf(e.keyCode) === -1) {
        editDiv.setAttribute("contenteditable", "false");
      }
    }

    this.$refs.msgInput.addEventListener(
      "DOMNodeInserted",
      this.textareaInput,
      false
    );
    this.$refs.msgInput.addEventListener(
      "DOMNodeRemoved",
      this.textareaInput,
      false
    );
    this.$refs.msgInput.addEventListener(
      "DOMCharacterDataModified",
      this.textareaInput,
      false
    );
  },
  methods: {
    handleCheckAll() {
      this.checkAll = !this.checkAll;
    },
    handleCheck(e) {
      let i = this.checkList.findIndex((res) => res.id == e.id);
      if (i != -1) {
        this.checkList.splice(i, 1);
      } else {
        this.checkList.push(e);
      }
    },
    //获取好友列表
    getFriendList() {
      friendListApi(this.$store.getters.userInfo.userName).then((res) => {
        this.friendList = res.data;
        this.getChatList();
      });
    },
    getChatList() {
      chatListApi({
        reviceuserid: this.friendList[0].userid,
        offset: this.offset,
        pageSize: 10,
      }).then((res) => {
        res.data.list.reverse();
        this.chatList = res.data.list.concat(this.chatList);
        if (this.chatList.length == 0) {
          // this.chatList.push({
          //   id: new Date().getTime(),
          //   msgType: "MSG_TEXT",
          //   createdTime: parseTime(new Date(),"{y}-{m}-{d} {h}:{i}:{s}"),
          //   content: "亲爱的巡察员，我是小维，随时为您提供巡察业务支撑服务",
          // });
        } else {
          // this.chatList = this.chatList.filter(item => item.fromUserId == this.friendList[0].userid && item.contentJson.length);
          // let tempArr = [];
          // this.chatList.forEach((i) => {
          // console.log(i.contentJson);
          // if (
          //   i.fromUserId == this.friendList[0].userid && i.contentJson && i.contentJson.length
          // ) {
          //   tempArr.push(i);
          // }
          // if (i.fromUserId != this.friendList[0].userid) {
          //   tempArr.push(i);
          // }
          // tempArr.push(i);
          // });
          // this.chatList = tempArr;
        }

        if (res.data.list[0]) {
          this.offset = res.data.list[0].msgId;
        }
        this.loading = false;
        if (res.data.total == this.chatList.length || !res.data.list[0]) {
          this.isNextPage = false;
        }
        this.setScrollTop(this.curScollTop);
      });
    },
    //滚动事件监听
    handleScroll(e) {
      if (e.target.scrollTop != 0) {
        this.isShowTip = false;
      } else {
        this.isShowTip = true;
      }
      if (!this.isNextPage) {
        return;
      }
      if (e.target.scrollTop == 0 && this.offset != 0) {
        clearTimeout(this.st);
        this.loading = true;
        this.curScollTop = e.target.scrollHeight;
        this.st = setTimeout(() => {
          this.getChatList();
        }, 1000);
      } else {
        if (this.st) clearTimeout(this.st);
        this.loading = false;
      }
    },
    //设置滚动条的位置
    setScrollTop(top) {
      this.$nextTick(() => {
        var container = this.$el.querySelector("#chatList");
        container.scrollTop = container.scrollHeight - (top || 0);
      });
    },
    //发送信息
    send(msg) {
      if (typeof msg === "string") {
        this.context = msg;
      }
      if (this.context) {
        let sendObj = {
          fromUserId: this.userInfo.userName,
          fromUserName: this.userInfo.nickName,
          toUserId: this.friendList[0].userid,
          toUserName: this.friendList[0].username,
          msgType: "MSG_TEXT",
          content: this.context,
          pageNum: 0,
          pageSize: 10,
          repoType: this.checkAll
            ? "0"
            : this.checkList.map((item) => item.id).join(","),
        };
        this.$parent.sendWSPush({
          command: "SEND_MSG",
          param: sendObj,
        });
        this.chatList.push({
          id: new Date().getTime(),
          createdTime: parseTime(
            new Date().getTime(),
            "{y}-{m}-{d} {h}:{i}:{s}"
          ),
          ...sendObj,
        });
        this.searchKeyword = this.$refs.msgInput.innerText;
        this.$refs.msgInput.innerText = "";
        this.context = "";
        this.divCount = "0/50";
        this.setScrollTop();
      } else {
        this.$message.warning("请输入内容!");
      }
    },
    textareaRange() {
      var el = this.$refs.textarea;
      var range = document.createRange();
      //返回用户当前的选区
      var sel = document.getSelection();
      //获取当前光标位置
      var offset = sel.focusOffset;
      //div当前内容
      var content = el.innerHTML; //添加换行符
      el.innerHTML = content.slice(0, offset) + "" + content.slice(offset); //设置光标为当前位置
      range.setStart(el.childNodes[0], offset + 1);
      //使得选区(光标)开始与结束位置重叠
      range.collapse(true);
      //移除现有其他的选区
      sel.removeAllRanges();
      //加入光标的选区
      sel.addRange(range);
    },
    textareaInput(e) {
      var text = e.newValue || e.target.innerText || e.target.data;
      if (text) {
        this.divCount = text.length + "/" + 50;
      } else {
        this.divCount = "0/50";
      }
      if (text && text.replace(/\s+/g, "")) {
        this.disabled = false;
      } else {
        this.disabled = true;
      }
    },
    //监听按键操作
    textareaKeydown(event) {
      if (event.ctrlKey && event.keyCode === 13) {
        //ctrl+enter
        this.textareaRange();
      } else if (event.keyCode === 13) {
        //enter
        if (!this.disabled) {
          this.context = this.$refs.msgInput.innerText;
          this.send();
        } else {
          this.$message.warning("请输入内容!");
        }
        event.preventDefault(); // 阻止浏览器默认换行操作
        return false;
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.robot_left {
  height: 100%;
  background: #ffffff;
  width: 80%;
  // min-width: 959px;
  position: relative;

  .checkBox {
    padding: 10px 0px 10px 10px;
    border-bottom: 5px solid #f5f7f9;
    &-item {
      margin-right: 10px;
      display: inline-block;
      padding: 2px 20px;
      font-size: 14px;
      color: #495874;
      border-radius: 20px;
      background-color: #f4f5f6;
      cursor: pointer;
    }
    &-item:hover {
      background-color: #1590ff;
      color: #fff;
    }
    .active {
      background-color: #1590ff;
      color: #fff;
    }
  }
  .load-more {
    text-align: center;
    margin-top: 10px;
    position: absolute;
    top: 50px;
    left: 0px;
    right: 0px;
  }
  .no-next-page {
    text-align: center;
    margin-top: 10px;
    color: #de1f14;
    position: absolute;
    top: 50px;
    left: 0px;
    right: 0px;
    z-index: 1;
  }
}

.chat-list {
  height: calc(100vh - 250px);
  overflow: auto;
  padding-bottom: 16px;
}
.msg-input-container {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 20px;
  z-index: 2;
  padding: 11px;
  background: #ffffff;
  border-top: 1px solid #eee;
  .input-wrapper {
    min-height: 41px;
    overflow: auto;
  }
  .limit-tip {
    position: absolute;
    bottom: 10px;
    font-size: 14px;
    color: #aba5a5;
  }
  .sendBtn {
    margin-top: 5px;
    text-align: right;
  }
  .input-wrapper:empty:before {
    content: "请输入您关心的问题";
    color: rgba(23, 35, 61, 0.45);
    line-height: 24px;
    font-size: 16px;
  }
}
</style>