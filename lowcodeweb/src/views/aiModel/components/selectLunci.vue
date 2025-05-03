<template>
  <div id="aimodelSelect">
    <el-select v-model="lunci" :popper-append-to-body='false' :style="{ width: width }">
      <el-option
        v-for="item in options"
        :key="item.lunciId"
        :label="item.lunciName"
        :value="item.lunciId"
      >
      </el-option>
    </el-select>
  </div>
</template>
<script>
import XcSelect from "@/components/formComponents/XcSelect/index";
import { queryLunciList } from "@/api/xuncha/common/base";
export default {
  props: {
    isLink: {
      type: Boolean,
      default: false,
    },
    width:{
      default: '250px'
    }
  },
  components: { XcSelect },
  data() {
    return {
      keys: { value: "lunciId", label: "lunciName" },
      options: [],
      lunci:''
    };
  },
  created() {
    // 加载下拉框数据
    if (!this.isLink) {
      queryLunciList().then((res) => {
        this.options = res.data;
        this.lunci = this.options[0].lunciId
      });
    }
    this.queryList("");
  },
  methods: {
    queryList(year) {
      console.log(year);
      let params = {
        // year
      };
      queryLunciList(params).then((res) => {
        this.options = res.data;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
    .el-input{
        background-color: transparent !important;
    }
</style>