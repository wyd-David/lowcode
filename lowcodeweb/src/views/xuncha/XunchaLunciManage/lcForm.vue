<template>
  <div>
    <el-dialog
      :title="title"
      :visible="open"
      width="600px"
      append-to-body
      @open="init"
      @close="close"
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        :disabled="disabled"
      >
        <el-form-item label="巡察轮次：" prop="lunciName">
          <el-input
            maxlength="100"
            show-word-limit
            v-model="form.lunciName"
            placeholder="请输入巡察轮次"
          />
        </el-form-item>
        <el-form-item label="巡察开始时间：" prop="startTime">
          <el-date-picker
            clearable
            style="width: 100%"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择巡察开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="巡察结束时间：" prop="endTime">
          <el-date-picker
            clearable
            style="width: 100%"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择巡察结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="说明：" prop="remark">
          <el-input
            maxlength="500"
            show-word-limit
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="submitForm" v-if="!disabled"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  getXunchaLunciManage,
  addXunchaLunciManage,
  updateXunchaLunciManage,
} from "@/api/xuncha/XunchaLunciManage";

export default {
  props: {
    open: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "",
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      title: "新增巡察轮次",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        lunciName: [
          { required: true, message: "巡察轮次不能为空", trigger: "blur" },
        ],
        startTime: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (!value) {
                return callback(new Error("请选择开始时间"));
              }
              if (this.form.endTime) {
                setTimeout(() => {
                  if (
                    new Date(this.form.startTime) > new Date(this.form.endTime)
                  ) {
                    callback(new Error("开始时间不能大于结束时间"));
                  } else {
                    callback();
                  }
                }, 1000);
              }
            },
            trigger: "blur",
          },
        ],
        endTime: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (!value) {
                return callback(new Error("请选择开始时间"));
              }
              if (this.form.startTime) {
                setTimeout(() => {
                  if (
                    new Date(this.form.startTime) > new Date(this.form.endTime)
                  ) {
                    callback(new Error("结束时间不能小于开始时间"));
                  } else {
                    callback();
                  }
                }, 1000);
              }
            },
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    init() {
      if (this.id) {
        if (this.disabled) {
          this.title = "查看巡察轮次";
        } else {
          this.title = "修改巡察轮次";
        }
        this.getDetail();
      } else {
        this.reset();
        this.title = "新增巡察轮次";
      }
    },
    // 表单重置
    reset() {
      this.form = {
        lunciId: null,
        lunciName: null,
        startTime: null,
        endTime: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        delFlag: null,
      };
      this.resetForm("form");
    },
    getDetail() {
      getXunchaLunciManage(this.id).then((res) => {
        this.form = res.data;
      });
    },
    close(e) {
      this.$emit("close", e);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            if (this.form.lunciId != null) {
              updateXunchaLunciManage(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.close();
                }
              });
            } else {
              addXunchaLunciManage(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.close(response.data);
                }
              });
            }
          });
        }
      });
    },
  },
};
</script>
