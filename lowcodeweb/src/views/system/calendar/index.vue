<template>
    <div class="calendarPage">
        <el-calendar v-model="date">
            <template slot="dateCell" slot-scope="{date,data}">
                <div class="dateCellBox">
                    <div class="iconBox">
                        <i class="iconfont icon-xiu" style="color:#67C23A;font-size:22px"></i>
                        <i class="iconfont icon-shangban" style="color:#f00"></i>
                    </div>
                    <p class="title">{{data.day | dateFtt}}</p>
                    <p class="desc">{{solarDate2lunar(data.day)}}</p>
                </div>
            </template>
        </el-calendar>

    </div>
</template>

<script>
    import calendar from '../../../utils/calendar_tool.js'
    export default {
        data() {
            return {
                date: new Date()
            }
        },
        filters: {
            dateFtt(val) {
                return new Date(val).getDate();
            },
        },
        methods: {
            solarDate2lunar(solarDate) {
                var solar = solarDate.split('-');
                var lunar = calendar.solar2lunar(solar[0], solar[1], solar[2])
                // return solar[1] + '-' + solar[2] + '\n' + lunar.IMonthCn + lunar.IDayCn
                return lunar.IMonthCn + lunar.IDayCn
            }
        }
    }
</script>

<style lang="scss" scoped>
// @import url('./icomfont.css');
@import './iconfont.css';
.calendarPage{
    padding: 16px;
    text-align: right;
    p{
        margin: 0;
    }
    .dateCellBox{
        // display: flex;
        height: 100%;
        // flex-direction: column;
        // justify-content: space-between;
        position: relative;
    }
    .title{
        font-size: 28px;
    }
    .desc{
        margin-top: 10px;
        color: #666;
    }
    .iconBox{
        position: absolute;
        top: 0px;
        left: 0px;
        i{
            font-size: 24px;
        }
    }
    
}
</style>