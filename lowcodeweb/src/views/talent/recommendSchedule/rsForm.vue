<template>
  <div>
    <el-dialog
      title="提示"
      :visible.sync="obj.open"
      @open="init"
      :close-on-click-modal="false"
      width="80%"
      :before-close="close"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>待办信息</span>
        </div>
        <div class="context-row">
          <el-form disabled label-width="120px">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="业务单号:">
                  <div style="display: flex">
                    <el-input :value="obj.orderNumber"></el-input>
                  </div>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="标题:">
                  <el-input :value="obj.title"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="计划巡察时段:">
                  <el-date-picker
                    style="width: 100%"
                    :value="[obj.startInspectTime, obj.endInspectTime]"
                    type="monthrange"
                    range-separator="至"
                    start-placeholder="开始月份"
                    end-placeholder="结束月份"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="收集截止时间:">
                  <el-date-picker
                    style="width: 100%"
                    :value="obj.collectionEndDate"
                    type="date"
                    placeholder="选择日期时间"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div style="text-align: right">
            <!-- <el-button style="margin-left: 20px" type="primary" size="small"
              >待办详情</el-button
            > -->
          </div>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>推荐人才</span>
        </div>
        <div class="context-row">
          <el-table
            v-loading="loading"
            :header-cell-style="{ 'text-align': 'center' }"
            :cell-style="{ 'text-align': 'center' }"
            :data="tableData"
          >
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column prop="userName" label="姓名"></el-table-column>
            <el-table-column prop="companyName" label="单位"></el-table-column>
            <el-table-column prop="deptName" label="部门"></el-table-column>
            <el-table-column
              prop="filedTypeName"
              label="专家领域"
            ></el-table-column>
            <el-table-column prop="postName" label="职务"></el-table-column>
            <el-table-column prop="postLevel" label="职级"></el-table-column>
          </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="add">确认入库</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  getRecommendListByCollectionId,
  comfirmWareHousing,
} from "@/api/talent/recommendSchedule";
export default {
  props: {
    obj: [Object],
  },
  data() {
    return {
      form: {},
      tableData: [],
      loading: false,
    };
  },
  methods: {
    close() {
      this.$emit("close");
    },
    add() {
      this.$confirm("是否确定提交?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        const loading = this.openLoading();
        comfirmWareHousing({
          ids: this.obj.ids,
          companyType: this.obj.companyType,
          collectionType: this.obj.collectionType,
        }).then((res) => {
          if (res.code == 200) {
            this.$message.success("提交成功");
            this.$emit("close", 200);
          }
          loading.close();
        });
      });
    },
    init() {
      this.tableData = [];
      this.loading = true;
      let temp = {
        colletionId: this.obj.id,
        companyIds: this.obj.companyIds,
        deptIds: this.obj.deptIds,
      };
      if (this.companyType == 2) {
        temp.deptIds = this.obj.deptIds;
        temp.companyIds = null;
      }
      getRecommendListByCollectionId(temp).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data;
        }
        this.loading = false;
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
      top: 10px;
      margin: 10px 0px;
      text-align: right;
      padding-right: 30px;
      z-index: 9;
    }
  }
}
</style>
