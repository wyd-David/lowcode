<template>
    <div class="homePage">

        <el-row :gutter="20">
          <el-col :span="7">
            <div class="crad">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                    <span>AI巡察员</span>
                </div>
                <div class="crad-header-content"></div>
                <!-- <div class="crad-header-right">
                    更多 <i class="el-icon-arrow-right"></i>
                </div> -->
                </div>
                <div class="crad-content">
                    <el-row :gutter="10">
                        <el-col :span="12">
                            <div class="actionBtn">作风建设</div>
                        </el-col>
                        <el-col :span="12">
                            <div class="actionBtn">作风建设</div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10" style="margin-top:10px">
                        <el-col :span="24">
                            <el-button class="aiAddBtn" icon="el-icon-plus" type="primary" plain>添加领域</el-button>
                        </el-col>
                    </el-row>
                </div>
            </div>

            <div class="crad mt-20">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <span>巡察案例库</span>
                </div>
                <div class="crad-header-content"></div>
                </div>
                <div class="crad-content">
                    <chart-pie style="height:250px"></chart-pie>
                </div>
            </div>

            <div class="crad mt-20">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>过程资料库</span>
                    </div>
                    <div class="crad-header-content"></div>
                    <div class="crad-header-right">
                      <el-date-picker
                        v-model="year"
                        type="year"
                        size="mini"
                        style="width:120px"
                        placeholder="选择年">
                    </el-date-picker>
                    </div>
                </div>
                <div class="crad-content">
                    
                    <el-row :gutter="10">
                        <el-col :span="8" style="text-align:center">
                            <el-progress type="circle" :width="80" :percentage="25"></el-progress>
                            <p style="font-size:12px;">第一巡察组</p>
                        </el-col>
                        <el-col :span="8" style="text-align:center">
                            <el-progress type="circle" :width="80" :percentage="45"></el-progress>
                            <p style="font-size:12px;">第二巡察组</p>
                        </el-col>
                        <el-col :span="8" style="text-align:center">
                            <el-progress type="circle" :width="80" :percentage="75"></el-progress>
                            <p style="font-size:12px;">第三巡察组</p>
                        </el-col>
                    </el-row>
                </div>
            </div>

            <div class="crad mt-20">
                <div class="crad-header">
                    <div class="crad-title">
                        <span class="line"></span>
                        <span>报告报表库</span>
                    </div>
                    <div class="crad-header-content"></div>
                    <div class="crad-header-right">
                      <el-date-picker
                        v-model="year"
                        type="year"
                        size="mini"
                        style="width:120px"
                        placeholder="选择年">
                    </el-date-picker>
                    </div>
                </div>
                <div class="crad-content">
                    <el-row :gutter="10">
                        <el-col :span="8" style="text-align:center">
                            <el-progress type="circle" :width="80" :percentage="25"></el-progress>
                            <p style="font-size:12px;">第一巡察组</p>
                        </el-col>
                        <el-col :span="8" style="text-align:center">
                            <el-progress type="circle" :width="80" :percentage="45"></el-progress>
                            <p style="font-size:12px;">第二巡察组</p>
                        </el-col>
                        <el-col :span="8" style="text-align:center">
                            <el-progress type="circle" :width="80" :percentage="75"></el-progress>
                            <p style="font-size:12px;">第三巡察组</p>
                        </el-col>
                    </el-row>
                </div>
            </div>
            
            <div class="crad mt-20">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <span>巡察制度流程库</span>
                </div>
                <div class="crad-header-content"></div>
                </div>
                <div class="crad-content">
                    <el-row :gutter="10">
                        <el-col :span="12" style="text-align:center">
                            <chart-pie :option="chartPieOptions" style="height:170px"></chart-pie>
                        </el-col>
                        <el-col :span="12" style="text-align:center">
                            <chart-pie :option="chartPieOptions" style="height:170px"></chart-pie>
                        </el-col>
                    </el-row>
                </div>
            </div>

          </el-col>
          <el-col :span="10">
            <div class="crad">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                    <span>全省巡察情况</span>
                </div>
                <div class="crad-header-content"></div>
                <div class="crad-header-right">
                    <el-date-picker
                        v-model="year"
                        type="year"
                        size="mini"
                        style="width:120px"
                        placeholder="选择年">
                    </el-date-picker>
                </div>
                </div>
                <div class="crad-content">
                    <chart-map style="height:350px;"></chart-map>
                </div>
            </div>
            
            <div class="crad mt-20">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                    <span>年度巡察进度</span>
                </div>
                <div class="crad-header-content"></div>
                <div class="crad-header-right">
                    <el-date-picker
                        v-model="year"
                        type="year"
                        size="mini"
                        style="width:120px"
                        placeholder="选择年">
                    </el-date-picker>
                </div>
                </div>
                <div class="crad-content">
                    <div style="margin-bottom: 10px">
                        <label style="padding: 10px 0;display:block;">
                            第一巡察组
                        </label>
                        <el-steps :active="1" finish-status="success" simple>
                            <el-step title="步骤 1" ></el-step>
                            <el-step title="步骤 2" ></el-step>
                            <el-step title="步骤 3" ></el-step>
                        </el-steps>
                    </div>
                    <div style="margin-bottom: 10px">
                        <label style="padding: 10px 0;display:block;">
                            第一巡察组
                        </label>
                        <el-steps :active="1" finish-status="success" simple>
                            <el-step title="步骤 1" ></el-step>
                            <el-step title="步骤 2" ></el-step>
                            <el-step title="步骤 3" ></el-step>
                        </el-steps>
                    </div>
                    <div  style="margin-bottom: 10px">
                        <label style="padding: 10px 0;display:block;">
                            第一巡察组
                        </label>
                        <el-steps :active="1" finish-status="success" simple>
                            <el-step title="步骤 1" ></el-step>
                            <el-step title="步骤 2" ></el-step>
                            <el-step title="步骤 3" ></el-step>
                        </el-steps>
                    </div>
                </div>
            </div>
            
            <div class="crad mt-20">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                    <span>全省整改情况</span>
                </div>
                <div class="crad-header-content"></div>
                <div class="crad-header-right">
                    <el-date-picker
                        v-model="year"
                        type="year"
                        size="mini"
                        style="width:120px"
                        placeholder="选择年">
                    </el-date-picker>
                </div>
                </div>
                <div class="crad-content">
                    <chart-map style="height:350px;"></chart-map>
                </div>
            </div>
          </el-col>
          <el-col :span="7">
            <div class="crad">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                    <span>人才队伍建设</span>
                </div>
                <div class="crad-header-content"></div>
                <!-- <div class="crad-header-right">
                    更多 <i class="el-icon-arrow-right"></i>
                </div> -->
                </div>
                <div class="crad-content">
                    <el-row :gutter="10">
                        <el-col :span="8">
                            <div class="actionBtn" style="font-size:12px"><span style="font-size:18px">70</span> 人</div>
                            <p class="activeText">人才库</p>
                        </el-col>
                        <el-col :span="8">
                            <div class="actionBtn warringBtn" style="font-size:12px"><span style="font-size:18px">40</span> 人</div>
                            <p class="activeText">支撑人才库</p>
                        </el-col>
                        <el-col :span="8">
                            <div class="actionBtn warringBtnOption" style="font-size:12px"><span style="font-size:18px">10</span> 人</div>
                            <p class="activeText">组长库</p>
                        </el-col>
                    </el-row>
                </div>
            </div>

            
            <div class="crad mt-20">
                <div class="crad-header">
                <div class="crad-title">
                    <span class="line"></span>
                    <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                    <span>人才库结构</span>
                </div>
                <div class="crad-header-content"></div>
                <div class="crad-header-right">
                </div>
                </div>
                <div class="crad-content">
                    <p class="activeText" style="margin:0">年龄分布</p>
                    <el-row :gutter="10">
                        <el-col :span="8" style="text-align:center">
                            <chart-pie :option="{...chartPieOptions, attribute: {...chartPieOptions.attribute, chartTitle: '人才库',titleFontSize: 12,titleY: 100,titleX:25}}" style="height:120px"></chart-pie>
                        </el-col>
                        <el-col :span="8" style="text-align:center">
                            <chart-pie :option="{...chartPieOptions, attribute: {...chartPieOptions.attribute, chartTitle: '支撑人才库',titleFontSize: 12,titleY: 100,titleX:15}}" style="height:120px"></chart-pie>
                        </el-col>
                        <el-col :span="8" style="text-align:center">
                            <chart-pie :option="{...chartPieOptions, attribute: {...chartPieOptions.attribute, chartTitle: '组长库',titleFontSize: 12,titleY: 100}}" style="height:120px"></chart-pie>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10" style="margin-top:20px">
                        <el-col :span="24" style="text-align:center">
                            <chart-column :option="{...chartColumnOptions, attribute: {...chartColumnOptions.attribute, chartTitle: '职级分布'}}" style="height:200px"></chart-column>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10" style="margin-top:20px">
                        <el-col :span="24" style="text-align:center">
                            <chart-column :option="{...chartColumnOptions, attribute: {...chartColumnOptions.attribute, chartTitle: '学历分布'}}" style="height:200px"></chart-column>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10" style="margin-top:20px">
                        <el-col :span="24" style="text-align:center">
                            <chart-line :option="{...chartLineOptions, attribute: {...chartLineOptions.attribute, title: '专家领域分布'}}" style="height:200px"></chart-line>
                        </el-col>
                    </el-row>
                </div>
            </div>


          </el-col>
          
                
        </el-row>

        
        <el-row :gutter="10" style="margin-top:20px">
            <el-col :span="24" style="text-align:center">
                <div class="crad mt-20">
                    <div class="crad-header">
                        <div class="crad-title">
                            <span class="line"></span>
                            <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                            <span>优秀巡察组</span>
                        </div>
                        <div class="crad-header-content"></div>
                        <div class="crad-header-right">
                            更多 <i class="el-icon-arrow-right"></i>
                        </div>
                    </div>
                    <div class="crad-content">
                        <el-table
                            :data="tableData"
                            style="width: 100%">
                            <el-table-column
                                prop="text"
                                label="排名">
                            </el-table-column>
                            <el-table-column
                                prop="text"
                                label="巡察组">
                            </el-table-column>
                            <el-table-column
                                prop="text"
                                label="综合得分">
                            </el-table-column>
                            <el-table-column
                                prop="text"
                                label="被巡察单位">
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </el-col>
        </el-row>

        <el-row :gutter="10" style="margin-top:20px">
            <el-col :span="24" style="text-align:center">
                <div class="crad mt-20">
                    <div class="crad-header">
                        <div class="crad-title">
                            <span class="line"></span>
                            <!-- <img src="~@/assets/images/xc_crad.png" alt=""> -->
                            <span>优秀巡察员</span>
                        </div>
                        <div class="crad-header-content"></div>
                        <div class="crad-header-right">
                            更多 <i class="el-icon-arrow-right"></i>
                        </div>
                    </div>
                    <div class="crad-content">
                        <el-table
                            :data="tableData"
                            style="width: 100%">
                            <el-table-column
                                prop="text"
                                label="姓名">
                            </el-table-column>
                            <el-table-column
                                prop="text"
                                label="公司">
                            </el-table-column>
                            <el-table-column
                                prop="text"
                                label="领域">
                            </el-table-column>
                            <el-table-column
                                prop="text"
                                label="得分">
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </el-col>
        </el-row>
          
    </div>
</template>

<script>
import chartPieOptions from '@/components/echart/chart-pie-options';
import ChartPie from "@/components/echart/chart-pie.vue";
import chartColumnOptions from '@/components/echart/chart-column-options'
import ChartColumn from "@/components/echart/chart-column.vue";
import ChartMap from "@/components/echart/chart-map.vue";
import ChartLine from "@/components/echart/chart-line.vue";
import chartLineOptions from '@/components/echart/chart-line-options'
let chartPieOption = JSON.parse(JSON.stringify(chartPieOptions))
let chartColumnOption = JSON.parse(JSON.stringify(chartColumnOptions))
let chartLineOption = JSON.parse(JSON.stringify(chartLineOptions))
export default {
    components: { ChartPie,ChartMap,ChartColumn, ChartLine },
    data() {
        chartPieOption.attribute.legendShow = false;
        chartPieOption.attribute.labelLineShow = false;
        chartPieOption.attribute.chartTitle = "巡察制度库：128";
        chartPieOption.attribute.titleTextColor = "#1590ff";
        chartPieOption.attribute.titleFontSize = 13;
        chartPieOption.attribute.titleX = 30;
        chartPieOption.attribute.titleY = 150;
        
        return {
            year: '',
            chartPieOptions: chartPieOption,
            chartColumnOptions: chartColumnOption,
            chartLineOptions: chartLineOption,
            tableData: []
        }
    }
}
</script>

<style lang="scss" scoped>
.homePage{
    padding: 16px;
    .actionBtn{
        background: #1590ff;
        color: #fff;
        font-size: 14px;
        font-weight: 550;
        text-align: center;
        height: 48px;
        line-height: 48px;
        border-radius: 6px;
    }
    .activeText{
        color: #1590ff;
        text-align: center;
        font-weight: 550;
    }
    .aiAddBtn{
        width: 100%;
    }
    .warringBtn{
        background: #E6A23C;
    }
    .warringBtnOption{
        background: rgba(#E6A23C, 0.5);
    }
}
</style>