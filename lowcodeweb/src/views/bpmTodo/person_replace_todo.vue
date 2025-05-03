<template>
  <div class="app-main">
    <todo-bpm-header></todo-bpm-header>
    <div class="dialog-context">
      <apply-form isTodo :id="replaceNo" />
    </div>
  </div>
</template>

<script>
import applyForm from "@/views/xuncha/replaceManage/taskGroupPersonChangeApply/applyForm";
export default {
  components: {
    applyForm,
  },
  data() {
    return {
      replaceNo: null,
    };
  },
  created() {
    this.replaceNo = this.$route.query.id;
  },
};
</script>

<style lang="scss" scoped>
  .app-main{
    padding: 15px;
  }
</style>