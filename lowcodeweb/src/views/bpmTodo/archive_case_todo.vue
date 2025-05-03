<template>
  <div id="page" class="app-main">
    <div class="form-module-title">
      <span class="line"></span><span>申请信息</span>
    </div>
    <case-method-form :isTodo="true" :caseType="caseType" :caseId="caseId"/>
  </div>
</template>

<script>
  import caseMethodForm from '@/views/xuncha/archives/case/caseMethodForm'

  export default {
    components: { caseMethodForm },
    data() {
      return {
        caseId: '',
        // 1 申报；2 修订；3废止；4暂存、审核
        caseType: 4,
        open: ''
      }
    },
    mounted() {
      this.caseId = this.$route.query.bussinessKey
    }
  }
</script>

<style lang="scss">
  .app-main {
    padding: 15px;
    background: #fff;
  }

  .form-module-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;
    margin-bottom: 15px;

  .line {
    display: inline-block;
    width: 4px;
    height: 17px;
    background: #1590ff;
    border-radius: 2px;
    margin-right: 10px;
  }
  }
</style>
