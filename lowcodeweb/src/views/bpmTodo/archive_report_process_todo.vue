<template>
  <!-- 资料申请待办 -->
  <div>
    <div class="app-main">
      <todo-bpm-header></todo-bpm-header>
      <applyForm isTodo></applyForm>
    </div>
  </div>
</template>
<script>
import applyForm from "@/views/xuncha/archives/course/apply/applyForm/reportSubmit";
export default {
  components: { applyForm },
  data() {
    return {
      activeName: "1",
      applyId: "1",
      todoId: "",
    };
  },
  mounted() {
    this.applyId = this.$route.query.bussinessKey;
    // this.todoId = this.$route.query.todoId;
    console.log(this.$route.query);
  },
  methods: {
    complete() {
      console.log("complete", arguments);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .form-module-footer {
  text-align: center;
}

/deep/ .form-module-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: rgba(23, 35, 61, 0.85);
  background: #fff;
  .line {
    display: inline-block;
    width: 4px;
    height: 17px;
    background: #1590ff;
    border-radius: 2px;
    margin-right: 10px;
  }
}
/deep/ .module-context-row {
  padding: 20px 40px;
  margin-bottom: 15px;
  position: relative;
  .operation {
    position: absolute;
    right: 0;
    left: 0;
    top: -30px;
    margin: 10px 0px;
    text-align: right;
    padding-right: 40px;
  }
}

.app-main {
  padding: 15px;
  background: #fff;
}
</style>
