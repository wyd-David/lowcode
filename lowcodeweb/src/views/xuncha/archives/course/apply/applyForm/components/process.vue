<template>
  <div>
    <el-dialog
      title="资料入库申请"
      :visible.sync="dialogVisible"
      width="1000px"
      :before-close="handleClose"
    >
      <div class="dialog-context">
        <div class="form-module-title">
          <span class="line"></span><span>申请信息</span>
        </div>
        <div class="module-context-row">
          <el-form
            :model="formData"
            :rules="formRules"
            ref="formRef"
            label-width="100px"
            class="searchFormClass"
            :disabled="type == '3' ? true : false"
          >
            <el-form-item v-if="type == '3'" label="业务单号">
              <el-input></el-input>
            </el-form-item>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="巡察轮次:" required>
                  <el-select
                    style="width: 320px"
                    v-model="formData.batch"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in batchs"
                      :key="item.value"
                      :label="item.name"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="巡察年份:" required>
                  <el-date-picker
                    style="width: 320px"
                    v-model="formData.year"
                    type="year"
                    placeholder="选择年"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="巡察批次:" required>
                  <el-select
                    style="width: 320px"
                    v-model="formData.batch"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in batchs"
                      :key="item.value"
                      :label="item.name"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="巡察任务:" required>
                  <el-select
                    style="width: 320px"
                    v-model="formData.batch"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in batchs"
                      :key="item.value"
                      :label="item.name"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="巡察组:" required>
                  <el-select
                    style="width: 320px"
                    v-model="formData.batch"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in batchs"
                      :key="item.value"
                      :label="item.name"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="被巡察单位:" required>
                  <el-select
                    style="width: 320px"
                    v-model="formData.batch"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in batchs"
                      :key="item.value"
                      :label="item.name"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="module-context-row">

        </div>
        <div class="form-module-title">
          <span class="line"></span><span>入库资料信息2</span>
        </div>
        <div class="module-context-row">
          <div class="operation">
            <el-button
              v-if="type != '3'"
              type="primary"
              size="small"
              @click="upload"
              >资料上传</el-button
            >
            <el-button v-else type="primary" size="small" @click="downloads"
              >批量下载</el-button
            >
          </div>

          <el-table :data="tableData" @selection-change="handleSelectionChange">
             <el-table-column v-if="type == '3'" type="selection" width="55"> </el-table-column>
            <el-table-column type="index" label="序号"></el-table-column>
            <el-table-column prop="fileName" align="center" label="资料名称">
              <template slot-scope="scope">
                <el-input v-model="scope.row.fileName"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="filePath" align="center" label="附件"></el-table-column>
            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="deleteData(scope)">删除</el-button>
                <el-button type="text">重传</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-if="type != '3'">
          <div class="form-module-title">
            <span class="line"></span><span>下一步</span>
          </div>
          <div class="module-context-row">
            <el-form :disabled="type == '3' ? true : false" label-width="150px">
              <el-form-item label="下一步处理人:" required>
                <el-select
                  style="width: 715px"
                  v-model="formData.batch"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in batchs"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="备注:">
                <el-input
                  type="textarea"
                  :rows="2"
                  style="width: 715px"
                  placeholder="请输入内容"
                  v-model="nextObj.remark"
                >
                </el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div v-else>
          <div class="form-module-title">
            <span class="line"></span><span>流程信息</span>
          </div>
          <div class="module-context-row">
            <el-table
              :data="taskInfo"
            >
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="步骤"></el-table-column>
              <el-table-column label="处理人"></el-table-column>
              <el-table-column label="处理时间"></el-table-column>
              <el-table-column label="处理意见"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >暂 存</el-button
        >
        <el-button type="primary" @click="dialogVisible = false"
          >提 交</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>


export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    //1->新建 2->编辑 3->查看
    type: {
      type: String,
      default: "1",
    },
  },
  data() {
    return {
      multipleSelection: [], //多选
      formData: {
        year: "",
      },
      formRules: {},
      batchs: [],
      tableData: [{}], //入库资料信息
      taskInfo: [{}], //流程信息
      nextObj: {
        //下一步信息
        remark: "",
      },
    };
  },
  mounted() {
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
  },
  methods: {
    complete() {
      console.log("complete", arguments);
    },
    //成功回调
    fileComplete(e) {
      console.log(e);
    },
    handleClose() {
      this.$emit("close");
    },
    upload() {
      this.tableData.push({
        fileName:"test"+new Date().getTime(),
        filePath: "path" + new Date().getTime(),
      });
    },
    deleteData(scope){
      this.tableData.splice(scope.$index,1);
    },
    downloads() {},
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  height: 500px;
  overflow: auto;
}

.dialog-context {
  .form-module-title {
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

  .module-context-row {
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
