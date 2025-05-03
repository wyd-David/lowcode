<template>
  <div>
       <xc-select
       v-on="$listeners"
       v-bind="$attrs"
        :keys="keys"
        :datas="options"> </xc-select>
  </div>
</template>
<script>
  import XcSelect from '@/components/formComponents/XcSelect/index'
  import { queryCompanyList } from '@/api/xuncha/common/base'
  export default {
    components:{XcSelect},
    data() {
      return {
        keys:{value:'companyId',label:'companyName'},
        options: []
      }
    },
    created() {
      // 加载下拉框数据
      // console.log($attrs);
      // this.keys = 
      queryCompanyList().then(res => {this.options = res.data})
    }
  }
</script>
