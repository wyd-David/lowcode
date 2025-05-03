<template>
    <div>
        <div>{{id}}</div>
        <el-button type="" @click="test">传参</el-button>
    </div>
</template>
<script>
export default{
    props:{
        id:{
            type:String,
            default:""
        },
        val:{
            type:Number,
            default:0
        }
    },
    data(){
        return{}
    },
    methods:{
test(){
    this.$emit("input",new Date().getTime())
}
    }
}
</script>