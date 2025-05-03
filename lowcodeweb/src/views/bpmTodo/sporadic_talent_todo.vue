<template>
  <div class="app-main">
    <sporadic-talent-dialog
      isTodo
      v-if="dialogVisible"
      :id="id"
    ></sporadic-talent-dialog>
  </div>
</template>

<script>
import SporadicTalentDialog from "@/views/xuncha/replaceManage/sporadicTalentUserInfo/sporadicTalentDialog.vue";
export default {
  components: { SporadicTalentDialog },
  data() {
    return {
      id: null,
      dialogVisible: false,
    };
  },
  computed: {},
  watch: {},
  created() {
    this.id = this.$route.query.id;
    this.dialogVisible = true;
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
.app-main{
  padding:15px;
}
</style>