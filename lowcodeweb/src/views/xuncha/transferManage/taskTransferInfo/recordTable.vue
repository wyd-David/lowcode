<template>
  <div>
    <div class="dialog-title">
      <span class="line"></span><span>抽取记录</span>
    </div>
    <el-form label-width="80px" :disabled="true">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="抽调人:">
            <el-input style="width: 250px" v-model="form.updateName" />
          </el-form-item>
        </el-col>
        <el-col :span="6" :offset="6">
          <el-form-item label="抽调时间:" prop="instanceId">
            <el-input style="width: 250px" v-model="form.transferTime" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  props:{
    form:{
      type:Object,
      default:{}
    }
  }
};
</script>

<style scoped lang="scss">
.dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;

    .btn {
      float: right;
    }

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }
</style>