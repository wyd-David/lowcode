<template>
  <div>
    <xc-select v-on="$listeners" v-bind="$attrs" :keys="keys" :datas="options">
    </xc-select>
  </div>
</template>
<script>
import XcSelect from "@/components/formComponents/XcSelect/index";
import { queryTaskList } from "@/api/xuncha/common/base";
export default {
  props: {
    isLink: {
      type: Boolean,
      default: false,
    },
  },
  components: { XcSelect },
  data() {
    return {
      keys: { value: "taskId", label: "taskName" },
      options: [],
    };
  },
  created() {
    if (!this.isLink) {
      // 加载下拉框数据
      queryTaskList().then((res) => {
        this.options = res.data;
      });
    }
  },
  methods: {
    queryList(piciId) {
      let params = {
        piciId,
      };
      queryTaskList(params).then((res) => {
        this.options = res.data;
      });
    },
  },
};
</script>
