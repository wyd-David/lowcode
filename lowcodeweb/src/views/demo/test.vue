<template>
    <div>
        <component-test v-model="val"></component-test>{{val}}
    </div>
</template>
<script>
import componentTest from './componentTest'
    export default{
        components:{
            componentTest
        },
        data(){
            return{
                val:0
            }
        }
    }
</script>