<!-- App.vue -->
<template>
  <div id="app">
    <div class="my_title" style="color: blue ;font-size: 2em;">{{titleName}}</div>
    <p style="text-align: right; color: aquamarine;font-size: 1em;">当前时间：{{ currentTime }}</p>
    <div class="container">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <div class="chat-window">
            <ChatMessage v-for="(message, index) in messages" :key="index" :message="message.text" :isUser="message.isUser" />
          </div>
          <ChatInput @send="sendMessage" />
        </div>
      </div>
    </div>
    <footer>

          <p style=" color: red;"><el-button type="danger" @click="clearLocalStorage">清空内容&刷新</el-button>
          <h3>{{links}}</h3>
        </footer>
  </div>
</template>

<script>
import ChatMessage from './ChatMessage.vue';
import ChatInput from './ChatInput.vue';
import { getToken } from "@/utils/auth";
import { v4 as uuidv4 } from 'uuid';

export default {
  components: {
    ChatMessage,
    ChatInput
  },
  data() {
    return {
      messages: [],
      ws: null,
      inputMessage: '',
      currentTime: '',
      userId: '',
      uuid: '',
      titleName: '您问我答',
      links: '联系我们微信：yabushan'
    };
  },
  mounted() {
    document.title = 'AI搜一搜';
    const arrayString = localStorage.getItem('aichart1');

     if(arrayString){
        this.messages=JSON.parse(arrayString);
     }
     this.updateTime();
         // 每秒更新一次时间
         this.timer = setInterval(() => {
           this.updateTime();
         }, 1000);
  },
  created() {

    let geturl = window.location.href;
    let getqyinfo = geturl.split('?')[1]   //qycode=1001&qyname=%E4%BC%81%E4%B8%9A%E5%BF%99
    let getqys = getqyinfo.split('&')  //['qycode=1001', 'qyname=%E4%BC%81%E4%B8%9A%E5%BF%99%E5%95%8A%E5%95%8A%E5%95%8A']
    this.userId =  getqys[0].split('=')[1]
    this.links =   decodeURIComponent(getqys[2].split('=')[1])
    this.titleName = decodeURIComponent(getqys[1].split('=')[1])
    this.generateUUID();
    //连接WebSocket服务端，然后初始化监听事件
    this.ws = new WebSocket("ws://n127.0.0.1:8087/prod-api/myWs/"+this.userId+"_ngs_"+this.uuid);
    this.wsInit();


  },
  methods: {
     wsInit() {
         this.ws.onopen = () => {
             this.ws.send("服务已连接");
             console.log(this.ws.readyState)
         }
         this.ws.onclose = () => {
             console.log("服务器关闭")
             console.log(this.ws.readyState)
             // this.messages.push("服务器关闭");
         }
         this.ws.onmessage = (message) => {
           console.log(message)
             console.log("收到服务器消息")
             this.messages.push({ text: message.data, isUser: false });
             //设置本地缓存消息
             localStorage.setItem('aichart1', JSON.stringify(this.messages));
             //滚动到最后
            this.scrollToBottom();

         }
         this.ws.onerror = (error) => {
             console.log("报错了")
             console.log(error)
            this.messages.push("报错了");
         }
     },
      sendMessage(inputMessage) {
        //if (inputMessage.trim() === '') return;
        let word =  inputMessage.replace("1@@@#","").replace("2@@@#","");
        this.messages.push({ text: word, isUser: true });
        //设置本地缓存消息
       localStorage.setItem('aichart1', JSON.stringify(this.messages));
        this.ws.send(inputMessage);

    },
    scrollToBottom() {
          this.$nextTick(() => {
            const container = this.$el.querySelector('.chat-window');
            container.scrollTop = container.scrollHeight;
          });
        },
        updateTime() {
              const date = new Date();
              const hours = date.getHours();
              const minutes = date.getMinutes();
              const seconds = date.getSeconds();
              this.currentTime = `${this.formatTime(hours)}:${this.formatTime(minutes)}:${this.formatTime(seconds)}`;
            },
            formatTime(time) {
              return time < 10 ? `0${time}` : time;
            },
          clearLocalStorage() {
            localStorage.removeItem('aichart1');

            alert('已清空！');
           window.location.reload();

        },
        generateUUID() {
              this.uuid = uuidv4(); // 调用uuid库生成一个UUID并赋值给uuid变量
            }
  },
  beforeDestroy() {
    clearInterval(this.timer); // 清除定时器
  }
};
</script>

<style>
.my_title{
    text-align: center;
    margin-top: 10px;
    margin-bottom: -1px;
    font-size: 10px;

  }
.chat-window {
  height: 450px;
  overflow-y: scroll;
  border: 1px solid #ccc;
  border-radius: 5px;

  padding: 10px;
}

.container {
  margin-top: 8px;
}
footer {
  background-color: #f2f2f2;
  padding: 10px;
  text-align: center;
}
</style>
