<template>
  <div>
    <PermissionTabs @ptClick="ptClick"></PermissionTabs>
    <div style="padding:20px;">
      <pageContent></pageContent>
      <pageContent1 v-if="tabShow == '测试2'"></pageContent1>
    </div>
  </div>
</template>

<script>
//三级菜单
import PermissionTabs from "@/components/PermissionTabs/index";
import content from "./content.vue";
import content1 from "./content1.vue";
export default {
  components: { PermissionTabs,pageContent:content,pageContent1:content1 },
  data() {
    return {
      tabShow:0
    };
  },
  methods: {
    ptClick(title){
      this.tabShow = title;
    }
  }
};
</script>

<style lang="scss" scoped>
</style>