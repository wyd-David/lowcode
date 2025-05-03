<template>
  <div class="app-main">
    <todo-bpm-header></todo-bpm-header>
    <Form ref="formRef" isTodo :groupId="groupId" :businessKey="groupId"/>
  </div>
</template>

<script>
import Form from "@/views/xuncha/transferManage/taskGroupInfo/formDialog.vue";
export default {
  components: {
    Form,
  },
  data() {
    return {
      businessKey: "",
      groupId:''
    };
  },
  mounted() {
    
    this.groupId = this.$route.query.id;
        // this.groupId = this.$route.query.groupDetailId;
        // this.groupId = '4d3c6e112f8a472698d35b953dcb161e';
    this.$nextTick(function () {
      this.$refs.formRef.handleOpen()
    });
  },
  updated() {
  },
};
</script>

<style scoped lang="scss">
  .app-main{
    padding: 15px;
  }
</style>
