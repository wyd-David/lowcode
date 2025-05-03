<template>
  <div>
    <!-- 添加或修改巡察组管理 对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
      @close="close"
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="巡察组名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="巡察任务" prop="taskId">
          <el-select
            style="width: 100%"
            v-model="form.taskId"
            placeholder="请选择巡察任务"
          >
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="巡察轮次:">
              <el-select style="width: 350px" v-model="form.taskId">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="6">
            <el-form-item label="巡察年份:">
              <el-input style="width: 350px"> </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="巡察批次:">
              <el-select style="width: 350px" v-model="form.taskId">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  getXunchaTaskGroupManage,
  addXunchaTaskGroupManage,
  updateXunchaTaskGroupManage,
} from "@/api/xuncha/XunchaTaskGroupManage";
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
  },
  data() {
    return {
      title: "新增巡察组",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        groupName: [
          { required: true, message: "巡察组名称不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    close() {
      this.$emit("close");
    },
    init() {
      if (this.id) {
        this.title = "修改巡察批次";
        this.getDetail();
      } else {
        this.reset();
        this.title = "新增巡察批次";
      }
    },
    getDetail() {
      getXunchaTaskGroupManage({
        taskId: this.id,
      }).then((res) => {
        this.form = res.data;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.id) {
            updateXunchaTaskGroupManage(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addXunchaTaskGroupManage(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog-context {
  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;
    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }

  .context-row {
    padding: 20px 40px;
    margin-bottom: 15px;
    position: relative;
    .operation {
      position: absolute;
      right: 0;
      left: 0;
      top: -30px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 40px;
    }
  }
}
</style>
