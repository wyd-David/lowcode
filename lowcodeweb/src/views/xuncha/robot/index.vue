<template>
  <div class="container">
    <rheader></rheader>
    <div class="main">
      <left ref="left"></left>
      <right></right>
    </div>
  </div>
</template>
<script>
import left from "./component/left.vue";
import right from "./component/right.vue";
import header from "./component/header.vue";
import { createSocket, sendWSPush } from "./js/websocket.js";
import { getToken } from "@/utils/auth";

export default {
  name:"robot",
  components: {
    left,
    right,
    rheader: header,
  },
  data() {
    return {
      ws:null
    };
  },
  created() {
    let url = CONFIG.url.replace('http','ws') + process.env.VUE_APP_BASE_API + "/websocket/" + this.$store.getters.userInfo.userId+"?token="+getToken();
    this.ws = createSocket(url);
  },
  methods: {
    sendWSPush
  }
};
</script>
<style lang="scss" scoped>
.container {
  /*css主要部分的样式*/
  /*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 6px; /*对垂直流动条有效*/
    height: 6px; /*对水平流动条有效*/
  }

  /*定义滚动条的轨道颜色、内阴影及圆角*/
  ::-webkit-scrollbar-track {
    border-radius: 4px;
    /* -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3); */
    /* background-color: rosybrown; */
  }

  /*定义滑块颜色、内阴影及圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 8px;
    background-color: #dddee0;
    /* -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3); */
  }

  /*定义滑块悬停变化颜色、内阴影及圆角*/
  ::-webkit-scrollbar-thumb:hover {
    background-color: #c7c9cc;
  }

  /*定义两端按钮的样式*/
  ::-webkit-scrollbar-button {
    /* background-color: cyan; */
  }

  /*定义右下角汇合处的样式*/
  ::-webkit-scrollbar-corner {
    /* background: khaki; */
  }
}

.main {
  padding: 15px;
  display: flex;
  justify-content: space-between;
}
</style>
