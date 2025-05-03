<template>
  <div>
    <h1 style="color: green;">{{msg}}</h1>
    <h2 >{{id}}</h2>
  </div>

</template>

<script>

  export default {
    data() {
      return {
        /* 注意：formJson是指表单设计器导出的json，此处演示的formJson只是一个空白表单json！！ */

        id: null,
        msg: ''


      }
    },
    mounted() {
      let id =this.$route.query.id;
      if(id=="修改成功！"){
        this.id="";
        this.msg="修改成功！"

      }else{
        this.msg="保存成功";
        this.id="序列号："+id;
      }


    },

    methods: {
    }
  }
</script>

<style>
</style>
