<template>
  <div>
    <el-dialog
      title="下一步"
      modal-append-to-body
      append-to-body
      :visible.sync="dialogVisible"
      width="600px"
      :before-close="handleClose"
    >
      <div class="dialog-context">
        <el-form label-width="100px">
          <el-form-item label="转办人:" :model="todoForm" :rules="tfRules" ref="tfRules">
            <el-select
              style="width: 100%"
              v-model="todoForm.nextUserId"
              filterable
              remote
              value-key="userId"
              reserve-keyword
              placeholder="请输入联络人"
              :remote-method="remoteMethod"
              :loading="loading"
              @change="handleSelect"
            >
              <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.username + '-' + item.userId + '-' +item.company+'/'+ item.dpname"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="todoShow = false">取 消</el-button>
        <el-button type="primary" @click="todo">确 定</el-button>
      </span>

    </el-dialog>
  </div>
</template>
<script>


export default {
  components:{},
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
      loading: false,
      todoShow: false,
      applyShow: false,
      userList: [], //转办人
      nextUsers: [], //下一步处理人
      allFlowStep: [], //审核记录
      taskId: "", //任务id
      todoInfoId:"",
      todoForm: {},
      applyForm: {
        nextUserObj: {},
        nextStepObj:{}
      },
      afRules: {
        nextUserObj: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "change",
          },
        ],
        nextStepObj: [
          {
            required: true,
            message: "请选择下一步处理节点",
            trigger: "change",
          },
        ],
      },
      tfRules: {
        nextUserId: [
          {
            required: true,
            message: "请选择转办人",
            trigger: "change",
          },
        ],
      },
    };
  },
  mounted() {

  },
  methods: {
    handleClose() {
      this.dialogVisible=false;
      // this.$emit("close");
    },
    handleAF(e){
      let fm = new FormData();
      fm.append("taskId",e.taskId);
      fm.append("id",this.id);
      fm.append("variables",JSON.stringify({
          message:e.nextStepObj.nextStep,
          nextDealUser:e.nextUserObj.id,
          option:e.option
        }));
      console.log(e);
      fininshFormTask(fm).then(res => {
        if(res.code == 200){
          this.$message.success("提交成功");
        }
      });
    },
     
  },
};
</script>



<style lang="scss">
.dialog-context {
}
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
    top: 10px;
    margin: 10px 0px;
    text-align: right;
    padding-right: 30px;
    z-index: 9;
  }
}

.dialog-footer {
  text-align: center;
}
</style>