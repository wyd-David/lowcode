<template>
  <div>
    <xc-select
      v-on="$listeners"
      v-bind="$attrs"
      :keys="keys"
      :datas="options"></xc-select>
  </div>
</template>
<script>
  import XcSelect from '@/components/formComponents/XcSelect/index'
  import { queryDictList } from '@/api/xuncha/common/base'

  export default {
    props:{
      dictType: {
        type: String,
        default: ""
      }
    },
    components: { XcSelect },
    data() {
      return {
        keys: { value: 'dataCode', label: 'dataName' },
        options: []
      }
    },
    created() {
      // 加载下拉框数据
      queryDictList({dictType: this.dictType}).then(res => {
        console.log(res.data);
        this.options = res.data
      })
    }
  }
</script>
