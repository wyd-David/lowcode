<template>
  <div class="robot_message">
    <div class="send-date">{{obj.createdTime}}</div>
    <!-- 机器人 -->
    <div class="send-context" v-if="obj.fromUserId != userInfo.userName">
      <div class="send-context-left">
        <img
          class="photo"
          src="../../../../assets/images/robot/serviceUser@2x.png"
          alt=""
        />
      </div>
      <div class="send-context-right-service" v-if="obj.msgType == 'MSG_TEXT'">
        {{ obj.content }}
      </div>
      <knowledge-base :obj="obj" v-else></knowledge-base>
    </div>

    <!-- 用户 -->
    <div class="send-context userRight" v-else>
    <div class="send-context-left" style="height:48px;">
        <img
        v-if="userInfo.sex == 0"
          class="photo"
          src="../../../../assets/images/robot/user1@2x.png"
          alt=""
        />
        <img
        v-if="userInfo.sex == 1"
          class="photo"
          src="../../../../assets/images/robot/user2@2x.png"
          alt=""
        />
      </div>
      <div :class="'send-context-right-user'+userInfo.sex">
        {{obj.content}}
      </div>
    </div>
  </div>
</template>
<script>
import knowledgeBase from './knowledgeBase';
export default {
    components:{knowledgeBase},
  props: {
    obj: {
      type: Object,
      default: () => {
        return {
          type: "1",
          content: "",
          date: "",
        };
      },
    },
  },
  data() {
    return {
      userInfo: this.$parent.userInfo || {sex:0}
    };
  },
  created() {},
};
</script>
<style lang="scss" scoped>
.robot_message {
  padding: 16px 16px 0px 16px;
  .send-date {
    height: 20px;
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
    text-align: center;
  }

  .userRight{
        flex-direction: row-reverse;
    }
  .send-context {
    margin-top: 10px;
    display: flex;
    .send-context-left {
      height: 48px;
      .photo {
        width: 48px;
        height: 48px;
        border-radius: 50%;
      }
    }
    .send-context-right-service {
      border-radius: 15px;
      padding: 12px;
      word-break: break-all;
      max-width: 807px;
      background: #ffffff;
      border: 1px solid rgba(133, 133, 133, 0.18);
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: rgba(23, 35, 61, 0.75);
      position: relative;
      margin-left: 10px;
      &::before {
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 8px 8px 8px 0;
        border-color: transparent rgba(133, 133, 133, 0.18) transparent
          transparent;
        content: "\A";
        position: absolute;
        left: -8px;
        top: 8px;
        border-right-color: rgba(133, 133, 133, 0.18);
      }
      &::after {
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 8px 8px 8px 0;
        border-color: transparent #fff transparent transparent;
        content: "\A";
        position: absolute;
        left: -7px;
        top: 8px;
        border-right-color: #fff;
      }
    }

    .send-context-right-user0{
      word-break: break-all;
      max-width: 807px;
      border-radius: 15px;
      padding: 12px;
      background: #ffffff;
      border: 1px solid #c7e5ff;
      background: #c7e5ff;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: rgba(23, 35, 61, 0.75);
      position: relative;
      margin-right: 20px;
      &::before {
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 8px 0px 8px 8px;
        border-color: transparent transparent transparent #c7e5ff;
        content: "\A";
        position: absolute;
        right: -8px;
        top: 8px;
        border-right-color: #c7e5ff;
      }
    }

    .send-context-right-user1{
      border-radius: 15px;
      padding: 12px;
      word-break: break-all;
      max-width: 807px;
      background: #ffffff;
      border: 1px solid #f9e5e2;
      background: #f9e5e2;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: rgba(23, 35, 61, 0.75);
      position: relative;
      margin-right: 20px;
      &::before {
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 8px 0px 8px 8px;
        border-color: transparent  transparent transparent #f9e5e2;
        content: "\A";
        position: absolute;
        right: -8px;
        top: 8px;
        border-right-color: #f9e5e2;
      }
    }
  }
}
</style>
