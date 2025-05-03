<template>
  <div>
    <xc-select v-on="$listeners" v-bind="$attrs" :keys="queryUnbound?keys2:keys" :datas="options">
    </xc-select>
  </div>
</template>
<script>
import XcSelect from "@/components/formComponents/XcSelect/index";
import { queryTaskList } from "@/api/xuncha/common/base";
import { getUnboundGroupList } from "@/api/xuncha/transferManage/taskGroupInfo.js";
export default {
  props: {
    isLink: {
      type: Boolean,
      default: false,
    },
    queryUnbound: {
      type: Boolean,
      default: false,
    },
  },
  components: { XcSelect },
  data() {
    return {
      keys: { value: "taskId", label: "groupName" },
      keys2: { value: "groupId", label: "groupName" },
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
    queryList(piciId,groupId) {
      let params = {
        piciId,
      };
      console.log(this.queryUnbound);
      if (this.queryUnbound) {
        getUnboundGroupList({groupId}).then((res) => {
          this.options = res.rows;
        });
      } else {
        queryTaskList(params).then((res) => {
          this.options = res.data;
        });
      }
    },
  },
};
</script>
