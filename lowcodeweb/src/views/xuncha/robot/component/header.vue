<template>
  <div class="robot_header">
    <img class="logo" src="../../../../assets/images/robot/headerLogo@2x.png" alt="" /><span
      class="robot_header_title"
    >智慧小巡</span>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  created() {},
};
</script>
<style lang="scss" scoped>
.robot_header {
  min-width: 1223px;
  background: #ffffff;
  padding: 15px;
  display: flex;
  align-items: center;
}
.logo{
    width: 26px;
    height: 22px;
}
.robot_header_title {
  font-size: 20px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: rgba(23, 35, 61, 0.85);
  margin-left: 16px;
}
</style>
