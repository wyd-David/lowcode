<template>
  <div>
    <p>此页面只用于测试组件</p>
    <div>
        <expand-collapse>
            <div slot="left">
                 <el-form class="searchFormClass" ref="queryForm" :inline="true" label-width="60px">
                    <el-form-item label="姓名" prop="foodName">
                        <el-input
                        clearable
                        size="small"
                        />
                    </el-form-item>
                    <el-form-item label="公司" prop="foodName">
                        <el-input
                        clearable
                        size="small"
                        />
                    </el-form-item>
                    <el-form-item label="部门" prop="foodName">
                        <el-input
                        clearable
                        size="small"
                        />
                    </el-form-item>
                    <el-form-item label="姓名" prop="foodName">
                        <el-input
                        clearable
                        size="small"
                        />
                    </el-form-item>
                    <el-form-item label="姓名" prop="foodName">
                        <el-input
                        clearable
                        size="small"
                        />
                    </el-form-item>
                    <el-form-item label="姓名" prop="foodName">
                        <el-input
                        clearable
                        size="small"
                        />
                    </el-form-item>
                </el-form>
            </div>
            <div slot="right">
                <el-button type="primary" size="mini" >搜索</el-button>
                <el-button size="mini">重置</el-button>
            </div>
        </expand-collapse>
    </div>
    <div style="width: 300px; border: 1px solid #000; padding: 16px;display:inline-block">
      <tree-select :treeData="treeData"></tree-select>
    </div>
    <div style="width: 450px; height:400px; border: 1px solid #000; padding: 16px;display:inline-block;margin-left: 20px">
      <chart-pie></chart-pie>
    </div>
    <div style="width: 450px; height:400px; border: 1px solid #000; padding: 16px;display:inline-block;margin-left: 20px">
      <chart-column></chart-column>
    </div>
    <div style="width: 100%; height:400px; border: 1px solid #000; padding: 16px;display:inline-block;margin-left: 20px;margin-top:20px">
      <chart-line></chart-line>
    </div>
    <div style="width: 100%; height:400px; border: 1px solid #000; padding: 16px;display:inline-block;margin-left: 20px;margin-top:20px">
      <chart-map></chart-map>
    </div>
    <p>待办测试</p>
     <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="text"
        label="待办名称">
      </el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">办理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import TreeSelect from "@/components/TreeSelect/index";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import ChartPie from "@/components/echart/chart-pie.vue";
import ChartColumn from "@/components/echart/chart-column.vue";
import ChartLine from "@/components/echart/chart-line.vue";
import ChartMap from "@/components/echart/chart-map.vue";
export default {
  components: { TreeSelect, ExpandCollapse, ChartPie, ChartColumn, ChartLine, ChartMap },
  data() {
    return {
      treeData: [
        {
          label: "一级 1",
          children: [
            {
              label: "二级 1-1",
              children: [
                {
                  label: "三级 1-1-1",
                },
              ],
            },
          ],
        },
        {
          label: "一级 2",
          children: [
            {
              label: "二级 2-1",
              children: [
                {
                  label: "三级 2-1-1",
                },
              ],
            },
            {
              label: "二级 2-2",
              children: [
                {
                  label: "三级 2-2-1",
                },
              ],
            },
          ],
        },
        {
          label: "一级 3",
          children: [
            {
              label: "二级 3-1",
              children: [
                {
                  label: "三级 3-1-1",
                },
              ],
            },
            {
              label: "二级 3-2",
              children: [
                {
                  label: "三级 3-2-1",
                },
              ],
            },
          ],
        },
      ],
      tableData: [{
        text: '待办测试跳转模式',
        url: 'http://localhost:81/test/demo?hideNavbar=1'
      }]
    };
  },
  methods: {
    handleClick(row) {
      let winObj = window.open(row.url);
      var loop = setInterval(function() {
        if(winObj.closed) {
          console.log('待办关闭了')
          clearInterval(loop);
        }
      }, 1000);
    },
  }
};
</script>

<style lang="scss" scoped>
</style>