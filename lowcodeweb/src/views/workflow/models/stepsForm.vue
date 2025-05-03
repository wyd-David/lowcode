<template>
  <div>
    <el-dialog
      title="节点信息"
      :visible.sync="dialogVisible"
      width="1200px"
      :before-close="handleClose"
    >
    <div>


      <el-table v-loading="loading" :data="tableList" >
        <el-table-column type="selection" width="55" align="center" />
        <!--<el-table-column label="序列号" align="center" prop="rev" />-->
        <el-table-column label="节点编码" width="200px" align="center" prop="stepCode" />
        <el-table-column label="节点名称" align="center" prop="stepName" />

        <el-table-column label="操作" width="100px" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
                <el-button size="mini"
                          type="text"
                          @click="handleBtn(scope.row)"
                        >按钮信息</el-button>
          </template>
        </el-table-column>
      </el-table>

           <btnForm ref="btnFormRef" :dialogVisible="btnFormShow" @close="btnFormShow = false"></btnForm>

    </div>
    </el-dialog>
  </div>
</template>
<script>

  import btnForm from './btnForm';
  import {getAllFlowNode} from '@/api/workflow';


export default {
  components:{btnForm},
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
    // 是否显示表单
     btnFormShow: false,
    // 遮罩层
     loading: false,
     processDefId: '',

      tableList: [],
      activeName:'1',
      // 流程处理弹窗
      applyShow: false,
      turnbackProcessShow: false,
      transferProcessShow: false,
      //业务模块
      participantSelectShow: false,
      auditProcessShow: false,
      participantSelectShow: false
    };
  },
  mounted() {
    // this.$nextTick(() => {
    //   window.uploader = this.$refs.uploader.uploader;
    // });
  },
  methods: {
    getList(processDefId){
      this.processDefId=processDefId
      let fm = new FormData();
      fm.append("processDefId",processDefId);
      getAllFlowNode(fm).then((e)=>{
        this.tableList=e.data
      })
    },
    handleBtn(e) {
      debugger
      this.btnFormShow=true;
      this.$refs.btnFormRef.tableData=[]
      this.$nextTick(() => {
          this.$refs.btnFormRef.getList(this.processDefId,e);
        });
    },
    handleClose() {
      this.$emit("close");
    },

  },
};
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  height: 500px;
  overflow: auto;
}

/deep/ .form-module-footer {
  text-align: center;
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
