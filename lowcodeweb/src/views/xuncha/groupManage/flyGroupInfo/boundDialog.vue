<template>
  <div>
    <!-- 绑定 -->
    <el-dialog
      title="绑定分组"
      :visible.sync="dialogVisible"
      width="400px"
      :close-on-click-modal="false"
      :before-close="closeBound"
      @open="handleOpen"
    >
      <el-form
        :model="boundForm"
        ref="queryForm"
        :inline="true"
        label-width="120px"
      >
        <el-form-item label="绑定分组：" prop="flyGroupId">
          <!-- 飞行小组 -->
          <el-select
            v-if="type == '1'"
            v-model="boundForm.flyGroupId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in flyGroupList"
              :key="item.flyGroupId"
              :label="item.flyGroupName"
              :value="item.flyGroupId"
            >
            </el-option>
          </el-select>
          <!-- 巡察组 -->
          <el-select
            v-else
            v-model="boundForm.checkGroupId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in xunchaGroupList"
              :key="item.groupId"
              :label="item.groupName"
              :value="item.groupId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="closeBound">取 消</el-button>
        <el-button @click="submitForm" type="primary">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getUnboundList } from "@/api/xuncha/groupManage/flyGroupInfo";
import { getUnboundGroupList } from "@/api/xuncha/transferManage/taskGroupInfo.js";
import { addFlyGroupRelation } from "@/api/xuncha/groupManage/flyGroupRelation";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: "",
    },

    boundForm: {
      type: Object,
    },
  },
  data() {
    return {
      flyGroupList: [],
      xunchaGroupList: [],
    };
  },
  created() {},
  methods: {
    closeBound() {
      this.$emit("close");
    },
    handleOpen() {
      getUnboundList().then((res) => {
        this.flyGroupList = res.rows;
      });
      getUnboundGroupList().then((res) => {
        this.xunchaGroupList = res.rows;
      });
    },
    /** 提交按钮 */
    submitForm() {
      addFlyGroupRelation(this.boundForm).then((res) => {
        this.msgSuccess(res.msg);
        this.$emit("close", 200);
      });
    },
  },
};
</script>

<style>
</style>