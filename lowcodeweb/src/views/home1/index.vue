<template>
  <div class="homePage">
    <left ref="left"></left>
    <center ref="center"></center>
    <right ref="right"></right>
  </div>
</template>

<script>
import left from "./components/left.vue";
import right from "./components/right.vue";
import center from "./components/center.vue";
export default {
  components: { left, right, center },
  data() {
    return {};
  },
  created() {},
  mounted() {
    let that = this;
    window.onresize = function () {
      that.$refs.left.chartResize();
      that.$refs.center.chartResize();
      that.$refs.right.chartResize();
    };
  },
  methods: {},
};
</script>

<style lang="scss">
.homePage {
  padding: 16px;
  display: flex;
  box-sizing: border-box;
  // height: calc(100vh - 84px);
  &-item {
    &-header {
      height: 50px;
      line-height: 50px;
      padding-left: 15px;
      background: #ffffff;
      box-shadow: inset 0px -1px 0px 0px rgba(23, 35, 61, 0.1);
      font-size: 14px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: rgba(23, 35, 61, 0.85);
      position: relative;
      &-date {
        position: absolute;
        right: 15px;
        top: 0px;
      }
      &-more {
        cursor: pointer;
        position: absolute;
        right: 15px;
        top: 0px;
        font-size: 14px;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: rgba(23, 35, 61, 0.45);
      }
    }
    &-context {
      padding: 15px;
      display: flex;
      justify-content: space-between;
      background: #ffffff;
      flex-wrap: wrap;
      margin-bottom: 15px;
    }
  }

  .zflink {
    background: linear-gradient(134deg, #fc5389 0%, #f8a16f 100%);
  }

  .ljlink {
    background: linear-gradient(140deg, #9d66f5 0%, #f26fa1 100%);
  }
  .addlink {
    background: #ffffff;
    border-radius: 4px;
    border: 1px dashed #1590ff;
    width: 100%;
    height: 1px;
    background-size: 8px 1px;
    background-repeat: repeat-x;

    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: #ce3933;
    .navlist-item-left > .title {
      color: #ce3933;
    }
  }
  &-left {
    width: 25%;
    margin-right: 10px;
  }
  &-center {
    width: 50%;
  }
  &-right {
    width: 25%;
    margin-left: 10px;
  }

  .navlist {
    &-item {
      margin: 5px 0;
      display: flex;
      width: 48%;
      align-items: center;
      justify-content: space-between;
      height: 68px;
      border-radius: 6px;
      cursor: pointer;
      &-left {
        padding-left: 15px;

        p {
          margin: 0;
        }

        .title {
          font-size: 14px;
          font-family: "PingFangSC-Semibold, PingFang SC";
          font-weight: 600;
          color: #ffffff;
          line-height: 28px;
          margin-bottom: 8px;
        }

        .text {
          font-size: 14px;
          font-family: "PingFangSC-Regular, PingFang SC";
          font-weight: 400;
          color: #ffffff;
          line-height: 20px;
        }
      }

      &-right {
        img {
          width: 36px;
          height: 36px;
          margin: 0;
          margin-right: 20px;
        }
      }
    }
  }
}
</style>
