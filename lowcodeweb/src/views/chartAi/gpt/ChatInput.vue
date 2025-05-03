<!-- ChatInput.vue -->
<template>
  <div class="chat-input">
    <el-select v-model="value" placeholder="请选择" style="margin: 10px;">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <div style="margin-left: 10px;width: 98%;"> <input v-model="message" @keyup.enter="sendMessage" type="text" class="form-control" placeholder="Type a message..." /></div>

  </div>
</template>

<script>
export default {
  data() {
    return {
     options: [{
        value: '1',
        label: '搜一搜'
      }, {
        value: '2',
        label: '记一记'
      }],
      value: '1',
      message: '',
      params: {}
    };
  },
  methods: {
    sendMessage() {
      if (!this.message.trim()) return;
      this.$emit('send', this.value+"@@@#"+ this.message);
      this.message = '';
    }
  }
};
</script>

<style scoped>
.chat-input {
  margin: 20px;

}
.form-control {
    width: 80%;
    height: 50px;
}
</style>
