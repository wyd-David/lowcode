<template>
  <div class="robot_right">
    <!-- <div class="checked-box">
      <el-checkbox
        :indeterminate="isIndeterminate"
        label="全选"
        v-model="checkAll"
        @change="handleCheckAllChange"
        border
      ></el-checkbox>
      <el-checkbox-group v-model="checkList" @change="handleCheckedChange">
        <el-checkbox
          v-for="item in checkLabes"
          :key="item"
          :label="item"
          border
        ></el-checkbox>
      </el-checkbox-group>
    </div> -->
    <div class="tab-box">
      <!-- <div :class="active == 1 ? 'item active' : 'item'" @click="handleTab(1)">
        猜你想问
      </div> -->
      <!-- <div :class="active == 2 ? 'item active' : 'item'" @click="handleTab(2)">
        热门问题
      </div> -->
      <div :class="'item active'" @click="handleTab(2)">热门问题</div>
    </div>
    <div class="robot_list" v-loading="loading">
      <div
        class="list-item"
        v-for="item in messageList"
        @click="handleClick(item)"
        :key="item.id"
      >
        <div><span class="dot"></span></div>
        <span class="message">{{ item.content }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import { hotQuestionsApi, thinkApi } from "@/api/xuncha/robot";
export default {
  data() {
    return {
      loading: false,
      active: 1,
      messageList: [],
      checkList: [],
      checkAll: false,
      checkLabes: ["过程资料库", "报告报表库", "巡察制度流程库"],
      isIndeterminate: false,
    };
  },
  created() {
    this.getThinkList();
  },
  methods: {
    handleCheckedChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.checkLabes.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.checkLabes.length;
    },
    handleCheckAllChange(val) {
      this.checkList = val ? this.checkLabes : [];
      this.isIndeterminate = false;
    },
    handleTab(index) {
      this.loading = true;
      this.active = index;
      if (index == 1) {
        this.getThinkList();
      } else {
        this.getHotQuestionsList();
      }
    },
    getHotQuestionsList() {
      hotQuestionsApi().then((res) => {
        this.messageList = res.data.list;
        this.loading = false;
      });
    },
    getThinkList() {
      thinkApi().then((res) => {
        this.messageList = res.data.list;
        this.loading = false;
      });
    },
    handleClick(e) {
      this.$parent.$refs.left.send(e.content);
    },
  },
};
</script>
<style lang="scss" scoped>
.robot_right {
  width: 20%;
  height: 100%;
  // min-width: 223px;
  margin-left: 20px;
  .checked-box{
    background: #fff;
    margin-bottom: 15px;
  }
  .tab-box {
    border-bottom: 1px solid #1590ff;
    display: flex;
    .item {
      flex: 1;
      padding: 8px 10px;
      cursor: pointer;
      text-align: center;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: rgba(23, 35, 61, 0.75);
    }
    .active {
      background: #1590ff;
      color: #fff;
    }
  }

  .robot_list {
    background: #fff;
    height: calc(100vh - 220px);
    overflow: auto;
    .list-item {
      display: flex;
      padding: 10px;
      cursor: pointer;
      .dot {
        width: 8px;
        height: 8px;
        margin: 0px 4px 0px 0px;
        border-radius: 50%;
        background: #1590ff;
        display: inline-block;
      }
      .message {
        font-size: 14px;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        line-height: 22px;
        color: rgba(23, 35, 61, 0.75);
      }
      &:hover {
        background: #fbf5f4;
      }
    }
  }
}
/deep/.el-button + .el-button,
.el-checkbox.is-bordered + .el-checkbox.is-bordered {
  margin-left: 0 !important;
}
/deep/.el-checkbox.is-bordered.el-checkbox--medium {
  width: 100% !important;
}
</style>
