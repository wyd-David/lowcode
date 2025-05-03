<template>
<div class="app-main">
  <todo-bpm-header></todo-bpm-header>
  <apply-form ref="applyForm" isTodo :obj="obj" v-if="obj.open"></apply-form>
</div>
  
</template>

<script>
import applyForm from "@/views/xuncha/archives/regulation/inStorage/applyForm.vue";
export default {
  data() {
    return {
        obj:{
            id:'',
            open:false,
            operationType:4
        }
    };
  },
  components: {
    applyForm,
  },
  mounted() {
    this.obj.id = this.$route.query.bussinessKey;
    this.obj.open = true
    // this.todoId = this.$route.query.todoId;
    console.log(this.$route.query);
  },
  methods: {},
};
</script>

<style lang="scss" scoped>

.app-main{
  padding: 15px;
}
</style>