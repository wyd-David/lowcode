<template>
  <div>
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="dialogVisible"  width="500px"
      :before-close="handleClose" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参数键值" prop="configValue">
          <el-input v-model="form.configValue" placeholder="请输入参数" />
        </el-form-item>
        <el-form-item label="类型" prop="configType">
          <el-radio-group v-model="form.configType">
            <el-radio
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio
            >
          </el-radio-group>
        </el-form-item>     
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">清理缓存</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>
      </div>
</template>

<script>
import {
  clearRedisCacheByType,clearRedisCacheByKey
} from "@/api/system/redis";

export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "清理缓存",
    },
  },
  data() {
    return {
      typeOptions:[
        {
          dictValue:1,
          dictLabel:'通过类型'
        },
        {
          dictValue:2,
          dictLabel:'通过key'
        }
      ],
      form:{
          configValue:''
          ,configType:1
      },
      rules:{
        configValue: [
            { required: true, message: "参数不能为空", trigger: "blur" }
          ],
      }
    };
  },
  methods: {
    handleClose() {
      this.$emit("close");
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          let that=this;
           this.$confirm("确认清理缓存？", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
               if(that.form.configType==1){
                    clearRedisCacheByType({type:that.form.configValue}).then(response => {
                      if (response.code === 200) {
                        this.msgSuccess("操作成功");
                      }
                    });
                }else{
                  clearRedisCacheByKey({key:that.form.configValue}).then(response => {
                      if (response.code === 200) {
                        this.msgSuccess("操作成功");
                      }
                    });
                }
                  
            });
            
          
        }
      })
     

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
