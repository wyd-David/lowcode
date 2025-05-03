<template>
    <div>
        <div class="form-module-title">
          <span class="line"></span><span>共享信息</span>
        </div>

        <div class="module-context-row">

          <el-table :data="tableData" @selection-change="handleSelectionChange">
             <el-table-column v-if="type == '3'" type="selection" width="55"> </el-table-column>
             <el-table-column type="index" label="序号"></el-table-column>
             
            <el-table-column prop="userName" align="center" label="姓名"></el-table-column>
            <el-table-column prop="companyName" align="center" label="所在单位"></el-table-column>
            <el-table-column prop="deptName" align="center" label="部门"></el-table-column>
            <el-table-column prop="status" align="center" label="完成状态">
              <template slot-scope="scope">
                <span>{{ scope.row.status | statusStr }}</span>
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
      tableData: [],
      formRules: {},
      batchs: [],
      taskInfo: [{}], //流程信息
      nextObj: {
        //下一步信息
        remark: "",
      },
    };
  },
  filters: {
    statusStr(status) {
      return {
        0: "未完成",
        1: "已完成",
        
      }[status];
    },
  },
  mounted() {
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
    this.$bus.$on('addParticipant',(arr)=>{
      console.log(arr);
      arr.forEach(i=>{
        i.status=0
        this.tableData.push(i)
      })
    })
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
        applyId:'' 
        ,userId:'' 
        ,userAccount:'' 
        ,userName:'张三' 
        ,companyId:'' 
        ,companyName:'广州公司' 
        ,deptId:'' 
        ,deptName:'信息服务部' 
        ,status:'已完成' 
        ,remark:'' 
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
