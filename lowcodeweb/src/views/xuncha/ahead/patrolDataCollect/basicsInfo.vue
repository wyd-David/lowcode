<template>
  <div>
    <div class="dialog-title">
      <span class="line"></span><span>基础信息</span>
    </div>
    <div class="context-row">
      <el-form disabled label-width="140px" :model="form" ref="formRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="资料类型:" prop="dataType">
              <el-radio
                v-for="item in inventoryType"
                v-model="form.dataType"
                :key="item.dictValue"
                :label="item.dictValue"
                >{{ item.dictLabel }}</el-radio
              >
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="资料说明:" prop="dataRemark">
              <el-input
                v-model="form.dataRemark"
                placeholder="请输入资料说明"
                type="textarea"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="资料提供时限要求:" prop="dataProvideTime">
              <el-date-picker
                style="width: 100%"
                v-model="form.dataProvideTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="查询时段要求:" prop="queryTime">
              <el-date-picker
                style="width: 100%"
                value-format="yyyy-MM-dd"
                v-model="form.queryTime"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="巡察年份:" prop="year">
              <el-date-picker
                style="width: 100%"
                v-model="form.year"
                type="year"
                placeholder="选择年"
                value-format="yyyy"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="巡察批次:" prop="batchId">
              <el-select
                style="width: 100%"
                v-model="form.batchId"
                placeholder="请选择"
              >
                <el-option
                  :label="item.piciName"
                  v-for="item in selectPiciData"
                  :key="item.piciId"
                  :value="item.piciId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="关联巡察任务:">
              <el-select
                multiple
                value-key="taskId"
                style="width: 100%"
                v-model="form.taskList"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in XunchaTaskInfoList"
                  :key="item.taskId"
                  :label="item.taskName"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getDicts } from "@/api/system/dict/data";
import {
  queryPiciByYeay,
  listXunchaTaskInfo,
} from "@/api/xuncha/XunchaTaskInfo";
export default {
  props: {
    form: [Object],
  },
  data() {
    return {
      inventoryType: [],
      selectPiciData: [],
      XunchaTaskInfoList: [],
      loading: false,
    };
  },
  mounted() {
    let si = setInterval(() => {
      if (this.form.year) {
        getDicts("inventory_type").then((res) => {
          this.inventoryType = res.data || [];
        });
        // 获得批次下拉框数据
        queryPiciByYeay(this.form.year).then((res) => {
          this.selectPiciData = res.data;
        });
        listXunchaTaskInfo({
          year: this.form.year,
          piciId: this.form.batchId,
          pageSize: 999,
        }).then((response) => {
          this.XunchaTaskInfoList = response.rows || [];
          this.XunchaTaskInfoList.map((i) => {
            i.remark = "";
            return i;
          });
          this.loading = false;
        });
        clearInterval(si);
      }
    }, 100);
  },
};
</script>

<style></style>
