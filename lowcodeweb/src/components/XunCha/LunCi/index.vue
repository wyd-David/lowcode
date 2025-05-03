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
  import { queryLunciList } from '@/api/xuncha/common/base'
  export default {
    props:{
      isLink:{
        type:Boolean,
        default:false
      }
    },
    components:{XcSelect},
    data() {
      return {
        keys:{value:'lunciId',label:'lunciName'},
        options: []
      }
    },
    created() {
      // 加载下拉框数据
      if (!this.isLink) {
        queryLunciList().then(res => {this.options = res.data})
      }
      this.queryList('');
    },
    methods:{
      queryList(year){
        console.log(year);
        let params = {
          // year
        }
        queryLunciList(params).then(res => {this.options = res.data})
      }
    }
  }
</script>
