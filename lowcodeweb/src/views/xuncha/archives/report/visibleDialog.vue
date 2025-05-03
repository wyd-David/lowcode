<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      title="设置可见范围"
      :visible.sync="dialogVisible"
      width="70%"
      @open="handleOpen"
      :before-close="close"
      append-to-body="append-to-body"
    >
      <!-- <div class="dialog-content">
        <div class="list-title">
          <span class="line"></span><span>设置可见范围</span>
        </div>
      </div> -->
      <div class="context-row" style="padding-left: 10px">
        <el-form
          :model="formData"
          ref="rulesForm"
          :rules="rulesForm"
          label-width="170px"
        >
          <el-row :gutter="24">
            <el-col :span="24">
              <el-form-item label="默认可见范围：" prop="orgId">
                <el-select
                  disabled
                  v-model="form.disOrgId"
                  multiple
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.companyId"
                    :label="item.companyName"
                    :value="item.companyId"
                    :disabled="item.disabled"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="24">
              <el-form-item label="可见范围：" prop="orgId">
                <el-select
                  v-model="orgId"
                  multiple
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in optionsQuery"
                    :key="item.companyId"
                    :label="item.companyName"
                    :value="item.companyId"
                    :disabled="item.disabled"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="24">
              <el-form-item label="备注：" prop="remark">
                <el-input
                  type="textarea"
                  v-model="formData.remark"
                  maxlength="500"
                  show-word-limit
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="handleClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryCompanyList } from "@/api/xuncha/common/base";
import { xunchaProcessPerm,getXunchaList,xunchaDelete } from "@/api/xuncha/archives/xunchaProcessContent";

export default {
  props: {
    dialogVisible: { type: Boolean, default: true },
    formData: {
      type: Object,
    },
    orgId:[],
    controller: {
      type: String,
      default: "xunchaProcessPerm",
    },
  },

  data() {
    return {
      isShowUpload: false,
      dialogFileVisible: false,
      form: {
        org: [],
        orgName: [],
        disOrgId: [0, 1, 2],
      },
      rulesForm: {},
      options: [
        { companyName: "巡察组", companyId: 0, disabled: true },
        { companyName: "巡察办", companyId: 1, disabled: true },
        { companyName: "地市公司分管党办领导", companyId: 2, disabled: true },
      ],
      optionsQuery: [],
    };
  },
  created() {
    queryCompanyList().then((res) => {
      this.optionsQuery.push(...res.data);
      console.log("creat");
    });
  },
  methods: {
    getList() {
      getXunchaList({ applyId: this.formData.contentId }, this.controller).then(res => {
        this.orgId = res.rows.map(item => item.orgId);
      })
    },
    handleOpen() {
      this.getList();
      // console.log(111111);
      // if (this.formData.premList.length != 0 && this.formData) {
      //   console.log(this.formData.premList[0].orgId);
      //   let orgId = [];
      //   let orgName = [];
      //   this.formData.premList.forEach((i) => {
      //     orgId.push(i.orgId);
      //     orgName.push(i.orgName);
      //   });
      //   this.$set(this.formData,'orgId',[0, 1, 2, ...orgId])
      // } else {
      //   this.$set(this.formData,'orgId',[0, 1, 2])
      // }
      // this.$forceUpdate();
      // getById({permId:this.id}).then(res=>{
      // })
    },

    handleClick() {
      let params = this.orgId.map((i) => {
        return {
          orgName: this.optionsQuery.find((op) => op.companyId == i)
            .companyName,
          orgId: i,
          applyId: this.formData.contentId,
        };
      });
      if (!params) {
        params = [{
          applyId: this.formData.contentId,
        }]
      }
      this.$confirm("是否确定提交?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        xunchaDelete({ appId: this.formData.contentId },this.controller).then(r => {
          if (r.code == 200) {
            xunchaProcessPerm(params,this.controller).then((res) => {
              if (res.code == 200) {
                this.msgSuccess(res.msg);
                this.close(200);
              }
            });
          }
        })
      });
    },

    handleClose() {
      this.dialogVisible = false;
      this.isShowUpload = false;
    },
    close(e) {
      this.$emit("close", e);
    },
  },
};
</script>

<style scoped lang="scss">
</style>
