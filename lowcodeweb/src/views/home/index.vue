<template>
  <div class="homePage">
    <left ref="left"></left>
    <center ref="center"></center>
    <right ref="right"></right>
  </div>
</template>

<script>
import "./flexible.js";
import left from "./components/left.vue";
import right from "./components/right.vue";
import center from "./components/center.vue";
export default {
  components: { left, right, center },
  data() {
    return {
      removeHandle: null,
    };
  },
  created() {
    var docEl = document.documentElement;
    // 获取当前显示设备的物理像素分辨率与CSS像素分辨率之比;
    var dpr = window.devicePixelRatio || 1;

    //根据分辨率调整全局字体大小
    function setBodyFontSize() {
      // html已完成加载，则立即调整字体大小，否则等待html加载完成再调整字体大小
      if (document.body) {
        document.body.style.fontSize = 12 * dpr + "px";
      } else {
        // 监听DOMContentLoaded 事件——当初始的 HTML 文档被完全加载和解析完成之后触发，无需等待样式表
        document.addEventListener("DOMContentLoaded", setBodyFontSize);
      }
    }
    setBodyFontSize();

    this.removeHandle = function () {
      document.removeEventListener("DOMContentLoaded", setBodyFontSize);
      window.removeEventListener("resize", setRemUnit);
      window.removeEventListener("pageshow", function (e) {
        // 若是浏览器中点击后退时显示页面，则重置rem
        if (e.persisted) {
          setRemUnit();
        }
      });
    };

    // 根据屏幕宽度，重置1rem的长度为当前屏幕宽度的1/10
    function setRemUnit() {
      var rem = docEl.clientWidth / 10;
      // 1rem的值永远为根元素的字体大小，所以此处通过调整全局字体大小来重置rem
      docEl.style.fontSize = rem + "px";
    }

    setRemUnit();

    // 监听resize事件——屏幕大小发生变化时触发
    window.addEventListener("resize", setRemUnit);
    // 监听pageshow事件——显示页面时触发
    window.addEventListener("pageshow", function (e) {
      // 若是浏览器中点击后退时显示页面，则重置rem
      if (e.persisted) {
        setRemUnit();
      }
    });

    // 检测是否支持0.5px
    if (dpr >= 2) {
      var fakeBody = document.createElement("body");
      var testElement = document.createElement("div");
      testElement.style.border = ".5px solid transparent";
      fakeBody.appendChild(testElement);
      docEl.appendChild(fakeBody);
      if (testElement.offsetHeight === 1) {
        docEl.classList.add("hairlines");
      }
      docEl.removeChild(fakeBody);
    }
  },
  mounted() {
    let that = this;
    setTimeout(() => {
      that.handleResize();
    }, 200);
    window.onresize = function () {
      setTimeout(() => {
        that.handleResize();
      }, 200);
    };
  },
  beforeDestroy() {
    window.onresize = null;
    document.querySelector("html").removeAttribute("style");
    document.querySelector("body").removeAttribute("style");
    document.querySelector(".homePage").setAttribute("style","position:fixed;top:999999px;");
    this.removeHandle();
  },
  methods: {
    handleResize() {
      this.$refs.left && this.$refs.left.chartResize();
      this.$refs.center && this.$refs.center.chartResize();
      this.$refs.right && this.$refs.right.chartResize();
    },
  },
};
</script>

<style lang="scss">
.homePage {
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
  padding: 0.111111rem /* 16/144 */;
  display: flex;
  box-sizing: border-box;
  &-item {
    &-header {
      height: 0.347222rem /* 50/144 */;
      line-height: 0.347222rem /* 50/144 */;
      padding-left: 0.104167rem /* 15/144 */;
      background: #ffffff;
      box-shadow: inset 0px -1px 0px 0px rgba(23, 35, 61, 0.1);
      font-size: 0.097222rem /* 14/144 */;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.85);
      position: relative;
      &-date {
        position: absolute;
        right: 0.104167rem /* 15/144 */;
        top: 0px;
      }
      &-more {
        cursor: pointer;
        position: absolute;
        right: 0.104167rem /* 15/144 */;
        top: 0px;
        font-size: 0.097222rem /* 14/144 */;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: rgba(23, 35, 61, 0.45);
      }
    }
    &-context {
      padding: 0.069444rem /* 10/144 */;
      display: flex;
      justify-content: space-between;
      background: #ffffff;
      flex-wrap: wrap;
      margin-bottom: 0.069444rem /* 10/144 */;
    }
  }

  .zflink {
    background: linear-gradient(134deg, #fc5389 0%, #f8a16f 100%);
  }

  .ljlink {
    background: linear-gradient(140deg, #9d66f5 0%, #f26fa1 100%);
  }
  .jclink {
    background: linear-gradient(118deg, #fe7b31 0%, #fca13a 100%, #fca13a 100%);
  }
  .addlink {
    background: #ffffff;
    border-radius: 4px;
    border: 1px dashed #1590ff;
    width: 100%;
    font-size: 0.111111rem /* 16/144 */;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: #ce3933;
    .navlist-item-left > .title {
      color: #ce3933;
    }
  }
  &-left {
    width: 2.597222rem /* 374/144 */;
    margin-right: 0.104167rem /* 15/144 */;
  }
  &-center {
    width: 4.152778rem /* 598/144 */;
  }
  &-right {
    width: 2.597222rem /* 374/144 */;
    margin-left: 0.104167rem /* 15/144 */;
  }

  .navlist {
    &-item {
      margin: 0.034722rem /* 5/144 */ 0;
      display: flex;
      width: 48%;
      align-items: center;
      justify-content: space-between;
      height: 0.458rem /* 68/144 */;
      border-radius: 0.041667rem /* 6/144 */;
      cursor: pointer;
      &-left {
        padding-left: 0.104167rem /* 15/144 */;

        p {
          margin: 0;
        }

        .title {
          font-size: 0.097222rem /* 14/144 */;
          font-family: "PingFangSC-Semibold, PingFang SC";
          font-weight: 600;
          color: #ffffff;
          line-height: 0.194444rem /* 28/144 */;
          margin-bottom: 0.055556rem /* 8/144 */;
          text-align: left;
        }

        .text {
          font-size: 0.097222rem /* 14/144 */;
          font-family: "PingFangSC-Regular, PingFang SC";
          font-weight: 400;
          color: #ffffff;
          line-height: 0.138889rem /* 20/144 */;
        }
      }

      &-right {
        img {
          width: 0.25rem /* 36/144 */;
          height: 0.25rem /* 36/144 */;
          margin: 0;
          margin-right: 0.138889rem /* 20/144 */;
        }
      }
    }
  }
}
</style>
