<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="4">
        <div class="tree_tit">巡察分组</div>
        <tree-select
          :isSearch="false"
          :expandall="false"
          @node-click="handleNodeClick"
        ></tree-select>
      </el-col>
      <el-col :span="20">
        <PermissionTabs @ptClick="tabChange"></PermissionTabs>
        <expand-collapse v-if="routeName == '巡察组'">
          <div slot="left">
            <el-form
              :model="queryParams"
              ref="queryForm"
              :inline="true"
              v-show="showSearch"
              label-width="120px"
            >
              <el-form-item label="组长：" prop="groupLeader">
                <el-input
                  v-model="queryParams.groupLeader"
                  placeholder="请输入组长"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="主笔人：" prop="leadWriter">
                <el-input
                  v-model="queryParams.leadWriter"
                  placeholder="请输入主笔人"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="联络人：" prop="liaisonMan">
                <el-input
                  v-model="queryParams.liaisonMan"
                  placeholder="请输入联络人"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="成员：" prop="teamMembers">
                <el-input
                  v-model="queryParams.teamMembers"
                  placeholder="请输入成员"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="小组名称：" prop="groupName">
                <el-input
                  v-model="queryParams.groupName"
                  placeholder="请输入小组名称"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="被巡察单位：" prop="company">
                <company
                  v-model="queryParams.company"
                  :label.sync="queryParams.companyName"
                  size="small"
                  clearable
                  width="100%"
                />
              </el-form-item>
            </el-form>
          </div>
          <div slot="right">
            <el-button type="primary" size="mini" @click="handleQuery"
              >搜索</el-button
            >
            <el-button size="mini" @click="resetQuery">重置</el-button>
          </div>
        </expand-collapse>
        <expand-collapse v-if="routeName == '飞行小组'">
          <div slot="left">
            <el-form
              :model="queryParams"
              ref="queryForm"
              :inline="true"
              v-show="showSearch"
              label-width="120px"
            >
              <el-form-item label="组长：" prop="groupLeader">
                <el-input
                  v-model="queryParams.groupLeader"
                  placeholder="请输入组长"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>

              <el-form-item label="成员：" prop="teamMembers">
                <el-input
                  v-model="queryParams.teamMembers"
                  placeholder="请输入成员"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="小组名称" prop="groupName">
                <el-input
                  v-model="queryParams.groupName"
                  placeholder="请输入小组名称"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
            </el-form>
          </div>
          <div slot="right">
            <el-button type="primary" size="mini" @click="handleQuery"
              >搜索</el-button
            >
            <el-button size="mini" @click="resetQuery">重置</el-button>
          </div>
        </expand-collapse>
        <xuncha-gruop
          ref="xuncha"
          :queryParams="queryParams"
          v-if="routeName == '巡察组'"
        />
        <fly-group
          ref="fly"
          :queryParams="queryParams"
          v-if="routeName == '飞行小组'"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PermissionTabs from "@/components/PermissionTabs/index";
import xunchaGruop from "./xunchaGruop.vue";
import flyGroup from "./flyGroup.vue";
import ExpandCollapse from "@/components/ExpandCollapse/index";
import Company from "@/components/XunCha/Company/index";
import TreeSelect from "@/components/TreeSelect/index";

export default {
  name: "FlyGroupInfo",
  components: {
    xunchaGruop,
    Company,
    PermissionTabs,
    ExpandCollapse,
    flyGroup,
    TreeSelect
  },
  data() {
    return {
      routeName: "巡察组",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        company: null,
        groupLeader: null,
        leadWriter: null,
        liaisonMan: null,
        teamMembers: null,
        groupName: null,
      },
      // 显示搜索条件
      showSearch: true,
    };
  },

  methods: {
    tabChange(e) {
      this.routeName = e;
    },
    handleNodeClick(data, node, agms) {
      console.log("groupData:", data);
      let { batchId, year, lunciId } = data;
      this.queryParams.instanceId = batchId;
      this.queryParams.xunchaYear = year;
      this.queryParams.lunciId = lunciId;

      this.getList();
    },
    getList() {
      if (this.routeName == "巡察组") {
        this.$refs.xuncha.getList();
      } else {
        this.$refs.fly.getList();
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
       this.queryParams.instanceId = null;
      this.queryParams.xunchaYear = null;
      this.queryParams.lunciId = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
  },
};
</script>


<style lang="scss" scoped>
.app-container {
  background: #f5f7f9;
}

/deep/ .el-form-item__label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: rgba(23, 35, 61, 0.75);
}
</style>

