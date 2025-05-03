<template>
  <div>
    <div class="todo_header" ref="todoHeaderRef">{{ pageTitle || title }}</div>
    <div :style="'height:'+height+'px;'"></div>
  </div>
</template>

<script>
export default {
  props: {
    pageTitle: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      title: "",
      height:""
    };
  },
  created() {
    this.title = this.$route.meta.title;
  },
  mounted() { 
    this.height= this.$refs.todoHeaderRef.offsetHeight;
  }
};
</script>

<style>
.todo_header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: #fff;
  z-index: 999;
  text-align: center;
  font-size: 18px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: rgba(23, 35, 61, 0.85);
  padding: 10px 0;
}
</style>
