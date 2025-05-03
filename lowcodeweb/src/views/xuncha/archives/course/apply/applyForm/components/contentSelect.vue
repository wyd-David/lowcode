<template>
    <div>
        <div class="form-module-title">
          <span class="line"></span><span>归档清单</span>
        </div>

        <div class="module-context-row">
          <div class="operation">
            <el-button
              v-if="type != '3'"
              type="primary"
              size="small"
              @click="upload"
              >添加清单</el-button
            >
          </div>
 

          <el-table :data="tableData" @selection-change="handleSelectionChange">
             <el-table-column v-if="type == '3'" type="selection" width="55"> </el-table-column>
             <el-table-column type="index" label="序号"></el-table-column>
            <el-table-column prop="fileName" align="center" label="归档范围">
              <template slot-scope="scope">
                <el-input v-model="scope.row.fileName"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="indexNo" align="center" label="序号"></el-table-column>
            <el-table-column prop="tyupe" align="center" label="类别"></el-table-column>
            <el-table-column prop="stage" align="center" label="对应巡察阶段"></el-table-column>
            <el-table-column prop="oder" align="center" label="资料提供方"></el-table-column>
            <el-table-column prop="isNedc" align="center" label="必传"></el-table-column>
            <el-table-column prop="createTim" align="center" label="资料移交时间"></el-table-column> 
   


            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="deleteData(scope)">删除</el-button>
                <el-button type="text">上传附件</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
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
     // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
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
