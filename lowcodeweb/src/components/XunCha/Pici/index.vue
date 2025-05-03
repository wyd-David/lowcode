<template>
  <div>
    <xc-select v-on="$listeners" v-bind="$attrs" :keys="keys" :datas="options">
    </xc-select>
  </div>
</template>
<script>
import XcSelect from "@/components/formComponents/XcSelect/index";
import { queryPiciList } from "@/api/xuncha/common/base";
import { listXunchaPiciManage } from "@/api/xuncha/XunchaPiciManage";

export default {
  props: {
    isLink: {
      type: Boolean,
      default: false,
    },
    queryXunchaPici: {
      type: Boolean,
      default: false,
    },
  },
  components: { XcSelect },
  data() {
    return {
      keys: { value: "piciId", label: "piciName" },
      options: [],
    };
  },
  created() {
    // 加载下拉框数据
    if (!this.isLink) {
      queryPiciList().then((res) => {
        this.options = res.data;
      });
    }
  },
  methods: {
    queryList(value) {
      if (this.queryXunchaPici) {
        let param = {
          year: value,
        };
        listXunchaPiciManage(param).then((res) => {
          this.options = res.rows;
        });
      } else {
        let params = {
          year: value,
        };
        queryPiciList(params).then((res) => {
          this.options = res.data;
        });
      }
    },
  },
};
</script>
