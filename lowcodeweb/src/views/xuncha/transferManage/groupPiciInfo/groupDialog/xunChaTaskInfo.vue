<template>
  <div>
    <el-dialog
      title="添加巡察任务管理"
      :visible.sync="dialogVisible"
      width="1000px"
      append-to-body
      :before-close="cancel"
      @open="handleOpen"
      :close-on-click-modal="false"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>巡察批次</span>
        </div>
        <div class="context-row">
          <el-form
            ref="form"
            :disabled="disableForm"
            :model="form"
            :rules="rules"
            label-width="120px"
          >
            <el-form-item label="巡察任务名称" prop="taskName">
              <el-input
                disabled
                v-model="form.taskName"
                maxlength="30"
                placeholder="请输入内容"
              />
            </el-form-item>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="巡察年份" prop="checkYear">
                  <el-date-picker
                    clearable
                    disabled
                    size="small"
                    style="width: 250px"
                    v-model="form.checkYear"
                    type="year"
                    value-format="yyyy"
                    @change="handleChangeCheckYear"
                    placeholder="选择巡察年份"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="巡察批次" prop="piciObj">
                  <div style="width: 350px">
                    <el-select
                      disabled
                      v-model="form.piciObj"
                      @change="handleChangePiciId"
                      value-key="piciId"
                      placeholder="请选择巡察批次"
                    >
                      <el-option
                        :label="item.piciName"
                        v-for="item in pcSelect"
                        :key="item.piciId"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="巡察组" prop="groupName">
                  <!-- <el-input
                    style="width: 250px"
                    v-model="form.groupName"
                    maxlength="30"
                    placeholder="请输入巡察组"
                  ></el-input> -->
                  <el-select
                    style="width: 250px"
                    v-model="form.groupName"
                    placeholder="请选择巡察组"
                  >
                    <el-option
                      v-for="item in groupNameList"
                      :label="item"
                      :key="item"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="巡察方式" prop="checkType">
                  <el-select
                    style="width: 220px"
                    v-model="form.checkType"
                    placeholder="请选择巡察方式"
                  >
                    <el-option
                      v-for="dict in checkTypeOptions"
                      :key="dict.dataId"
                      :label="dict.dataName"
                      :value="dict.dataCode"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="任务时段" prop="dates">
              <el-date-picker
                clearable
                size="small"
                v-model="form.dates"
                type="daterange"
                value-format="yyyy-MM-dd"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                @input="daterangeChange"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="被巡察单位" prop="companyIds">
              <el-select
                style="width: 100%"
                multiple
                v-model="form.companyIds"
                placeholder="请选择当前阶段"
              >
                <el-option
                  v-for="dict in CompanyAll"
                  :key="dict.companyId"
                  :label="dict.companyName"
                  :value="dict.companyId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="说明" prop="remark">
              <el-input
                v-model="form.remark"
                type="textarea"
                maxlength="200"
                show-word-limit
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div slot="footer" v-if="!taskId" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listXunchaTaskInfo,
  getXunchaTaskInfo,
  addXunchaTaskInfo,
  queryPiciByYeay,
  queryGroupByPiciId,
  getGroupNameList
} from "@/api/xuncha/XunchaTaskInfo";
import { getCompanyAll } from "@/api/xuncha/XunchaLunciManage";
import { selectDictData } from "@/api/xuncha/xunchaDictData";

export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    taskId: {
      type: String,
      default: "",
    },
    param: {
      type: Object,
    },
    disableForm: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      // 表单参数
      form: {
        companyIds: [],
      },
      groupNameList:[],
      pcSelect: [],
      tgSelect: [],
      CompanyAll: [],

      // 巡察方式
      checkTypeOptions: [],
      rules: {
        checkYear: [
          { required: true, message: "巡察年份不能为空", trigger: "blur" },
        ],
        checkType: [
          { required: true, message: "巡察方式不能为空", trigger: "blur" },
        ],
        dates: [{ required: true, message: "请选择批次", trigger: "blur" }],
        groupName: [
          { required: true, message: "巡察组不能为空", trigger: "change" },
        ],
        companyIds: [
          { required: true, message: "公司不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const query = {
      dictType: "xuncha_task_type",
    };
    selectDictData(query).then((res) => {
      this.checkTypeOptions = res.data || [];
    });
  },
  methods: {
    // 获得巡察组名称列表数据
    getGroupNameListData() {
      getGroupNameList(this.form.taskId , this.form.piciObj.piciId).then(res => {
        this.groupNameList = res.data;
      })
    },
    handleOpen() {
      if (this.taskId) {
        getXunchaTaskInfo(this.taskId).then((response) => {
          this.form = response.data;
          this.form.piciObj = { piciId: response.data.piciId };
          this.form.dates = [response.data.startTime, response.data.endTime];
          //let tempYear = response.data.checkYear.split("-")[0];
          // this.form.checkYear = tempYear;
          this.getPiciSelect();
          this.getCompanyList();
          this.open = true;
          this.title = "查看巡察任务管理 ";
        });
      } else {
        this.form.checkYear = this.param.xunchaYear;
        queryPiciByYeay(this.form.checkYear).then((res) => {
          this.pcSelect = res.data;
          this.form.piciObj = this.pcSelect.find(
            (i) => i.piciId == this.param.piciId
          );
          this.handleChangePiciId(this.form.piciObj);
          this.$refs["form"].clearValidate("piciObj");
          // let startDate = new Date(parseInt(this.form.piciObj.startTime));
          // let endDate = new Date(parseInt(this.form.piciObj.endTime));
          // this.form.startTime = `${startDate.getFullYear()}-${
          //   startDate.getMonth() + 1
          // }-${startDate.getDate()}`;
          // this.form.endTime = `${endDate.getFullYear()}-${
          //   endDate.getMonth() + 1
          // }-${endDate.getDate()}`;
          // this.form.dates = [
          //   this.form.piciObj.startTime,
          //   this.form.piciObj.endTime,
          // ];
        });
      }
    },
    taskInfoQuery(taskId) {
      getXunchaTaskInfo(taskId).then((response) => {
        this.form = response.data;
        this.form.piciObj = { piciId: response.data.piciId };
        this.form.dates = [response.data.startTime, response.data.endTime];
        // let tempYear = response.data.checkYear.split("-")[0];
        // this.form.checkYear = tempYear;
        this.getPiciSelect();
        this.getCompanyList();
      });
    },
    getPiciSelect() {
      queryPiciByYeay(this.form.checkYear).then((res) => {
        this.pcSelect = res.data;
        // this.form.piciObj=
      });
    },
    // 更新
    daterangeChange(e) {
      console.log(e);
      console.log(this.form.startTime);
      console.log(this.form.endTime);

      // 对计划巡察时段时间校验
      if (!this.form.startTime || !this.form.endTime) {
        this.$message.error("请选择批次！");
        this.form.dates = [];
      } else if (
        this.stringToDate(this.form.startTime) > this.stringToDate(e[0]) ||
        this.stringToDate(this.form.endTime) < this.stringToDate(e[1])
      ) {
        this.$message.error("任务时段不在批次计划时间范围内！");
        console.log("任务时段不在批次计划时间范围内！");
        this.form.dates = [this.form.startTime, this.form.endTime];
      }
      this.$nextTick(() => {
        this.$forceUpdate();
      });
    },
    handleChangePiciId(e) {
      // todo 时间时间
      let startDate = new Date(parseInt(e.startTime));
      let endDate = new Date(parseInt(e.endTime));

      this.form.piciId = e.piciId;
      // 重新获得被巡察单位下拉框数据
      this.getCompanyList();
      this.form.startTime = `${startDate.getFullYear()}-${
        startDate.getMonth() + 1
      }-${startDate.getDate()<10?'0':''}${startDate.getDate()}`;
      this.form.endTime = `${endDate.getFullYear()}-${
        endDate.getMonth() + 1
      }-${endDate.getDate()<10?'0':''}${endDate.getDate()}`;
      this.form.dates = [this.form.startTime, this.form.endTime];
      this.getGroupNameListData();
    },
    getCompanyList() {
      getCompanyAll(this.form.piciId).then((response) => {
        this.CompanyAll = response.data || [];
      });
    },
    handleChangeCheckYear(e) {
      this.getPiciSelect();
    },
    getPiciSelect() {
      queryPiciByYeay(this.form.checkYear).then((res) => {
        this.pcSelect = res.data;
      });
    },
    cancel(e) {
      this.$emit("close", e);

      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        taskId: null,
        piciId: null,
        taskName: null,
        checkYear: null,
        checkType: null,
        startTime: null,
        endTime: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
        dates: [],
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      if (this.disabled) {
        this.open = false;
        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            if (this.form.checkYear.length === 4) {
              this.form.checkYear = this.form.checkYear + "-01-01";
            }
            addXunchaTaskInfo(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.cancel(200);
              }
            });
          });
        }
      });
    },
  },
};
</script>

<style>
</style>