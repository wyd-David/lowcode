<template>
  <div>
    <!-- <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="120px"
    > -->
      <el-form-item label="一级目录:">
        <el-select value-key="contentId" v-model="queryParams.firstContent" placeholder="请选择" size="small" style="width: 200px">
          <el-option v-for="item in firstContentSelect" :label="item.contentName" :key="'a' + item.contentId" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级目录:">
        <el-select value-key="contentId" v-model="queryParams.secondContent" placeholder="请选择" size="small" style="width: 200px">
          <el-option v-for="item in queryParams.firstContent.children" :label="item.contentName" :key="'b' + item.contentId" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="三级目录:">
        <el-select value-key="contentId" v-model="queryParams.thirdContent" placeholder="请选择" size="small" style="width: 200px">
          <el-option v-for="item in queryParams.secondContent.children" :label="item.contentName" :key="'c' + item.contentId" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="制度名称:">
        <el-input v-model="queryParams.regulationName" placeholder="请输入制度名称" size="small"></el-input>
      </el-form-item>
      <el-form-item label="文号(历史文号序列):">
        <el-input v-model="queryParams.docCode" placeholder="请输入文号" size="small"></el-input>
      </el-form-item>
      <el-form-item label="执行有效性评价:">
        <el-select v-model="queryParams.execEffeEvaluation" placeholder="请选择" size="small" style="width: 200px">
          <el-option label="无效" value="无效"></el-option>
          <el-option label="有效" value="有效"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="集团废立计划:">
        <el-select v-model="queryParams.groupAbolitionPlan" placeholder="请选择" size="small" style="width: 200px">
          <el-option label="保留" value="保留"></el-option>
          <el-option label="不保留" value="不保留"></el-option>
        </el-select>
      </el-form-item>
    <!-- </el-form> -->
  </div>
</template>

<script>
import { list as catalogueList } from "@/api/xuncha/archives/catalogue";

export default {
    props:{
        queryParams:{
            type:Object,
        }
    },
  data() {
    return {
    //   queryParams: {
    //     firstContent: {
    //       children: [],
    //     },
    //     secondContent: {
    //       children: [],
    //     },
    //   },
      firstContentSelect: [],
    };
  },
  created() {
    this.catalogueSelect(1);
  },
  methods: {
    //转换数据
    getTreeData(data) {
      let temp = [];
      data.forEach((item) => {
        if (item.parentId == 0) {
          temp.push(item);
        } else {
          let parent = data.filter(
            (item2) => item2.contentId == item.parentId
          )[0];
          if (parent) {
            if (!parent.children) {
              parent.children = [];
            }
            parent.children.push(item);
          }
        }
      });
      return temp;
    },
    catalogueSelect(t) {
      catalogueList({ pageNo: 1, pageSize: 10 }).then((res) => {
        if (res.code == 200) {
          this.firstContentSelect = this.getTreeData(res.rows);
          console.log(this.firstContentSelect);
        }
      });
    },
  },
};
</script>

<style>
</style>
