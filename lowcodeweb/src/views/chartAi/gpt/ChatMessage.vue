<!-- ChatMessage.vue -->
<template>
  <div class="chat-message" :class="{ 'chat-message-right': isUser }">
    <div class="message-content" v-html="message"></div>
  </div>
</template>

<script>
export default {
  props: {
    message: {
      type: String,
      required: true
    },
    isUser: {
      type: Boolean,
      default: false
    }
  }
};
</script>

<style scoped>
.chat-message {
  padding: 10px;
  margin: 10px;
  background-color: #f2f2f2;
  border-radius: 10px;
}

.chat-message-right {
  background-color: #d4e6f1;
  text-align: right;
}

.message-content {
  word-wrap: break-word;
}
</style>
