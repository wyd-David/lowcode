<template>
  <div class="list-title">
    <span class="line"></span><span>{{ pageTitle||title }}</span>
    
    <!-- <span style="color: #ec832f">
      {{tipsText}}
    </span> -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  props: {
    pageTitle: {
      type: String,
      default: "",
    },
  },
  computed: {
    ...mapState({
      // 取菜单栏名做默认值
      title: (state) => state.settings.title,
    }),
  },
};
</script>

<style lang="scss">
</style>

