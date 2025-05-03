<template>
  <div>
    <div class="app-main">
      <div class="dialog-title">
        <span class="line"></span><span>归档信心</span>
      </div>
      <div class="context-row">
        <el-form
          :disabled="disabled"
          :model="formData"
          ref="formRef"
          label-width="150px"
        >
          <el-form-item label="标题">
            <el-input
              placeholder="请输入标题"
              v-model="formData.title"
            ></el-input>
          </el-form-item>
          <el-form-item label="收集类型">
            <el-radio-group v-model="formData.collectionType">
              <el-radio
                :label="item.dictValue"
                v-for="item in collectDicts"
                :key="item.dictValue"
                >{{ item.dictLabel }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="参加工作时间(早于):">
                <el-date-picker
                  style="width: 280px"
                  v-model="formData.joinWorkDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="6">
              <el-form-item label="入党时间(早于):">
                <el-date-picker
                  style="width: 280px"
                  v-model="formData.joinPartDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="计划巡察时段:">
                <div style="width: 280px">
                  <el-input
                    style="width: 130px"
                    :value="formData.startInspectTime"
                  ></el-input>
                  -
                  <el-input
                    style="width: 130px"
                    :value="formData.endInspectTime"
                  ></el-input>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="6">
              <el-form-item
                label="收集截止时间(早于):"
                prop="collectionEndDate"
              >
                <el-date-picker
                  style="width: 280px"
                  v-model="formData.collectionEndDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="context-row">
        <el-tabs v-model="activeName" @tab-click="handleTabClick">
          <el-tab-pane
            :label="item.dictLabel"
            :name="item.dictLabel"
            v-for="item in tabDicts"
            :key="item.dictLabel"
          ></el-tab-pane>
        </el-tabs>
        <el-table :data="prefecturalTableData" v-if="activeName == '地市公司'">
          <el-table-column
            label="序号"
            align="center"
            type="index"
          ></el-table-column>
          <el-table-column label="单位" width="150" align="center">
            <template slot-scope="scope">
              {{ scope.row.companyName }}
            </template>
          </el-table-column>
          <el-table-column width="200px" align="center" label="推荐专家总人数">
            <template slot-scope="scope">
              <span>{{ scope.row.expertTotals }}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            :label="item.dictLabel"
            v-for="item in prefecturalDicts"
            :key="item.dictValue"
          >
            <template slot-scope="scope">
              {{
                scope.row.fieldList?scope.row.fieldList.filter(
                  (res) => res.fieldType == item.dictValue
                )[0].personNumber:0
              }}
            </template>
          </el-table-column>
        </el-table>

        <el-table :data="provinceTableData" v-if="activeName == '省公司'">
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column label="单位" width="150" align="center">
            <template slot-scope="scope">
              {{ scope.row.companyName }}
            </template>
          </el-table-column>
          <el-table-column width="200px" align="center" label="推荐专家总人数">
            <template slot-scope="scope"></template>
          </el-table-column>
          <el-table-column
            align="center"
            :label="item.dictLabel"
            v-for="item in provinceDicts"
            :key="item.dictValue"
          >
            <template slot-scope="scope">
              {{
                scope.row.fieldList.filter(
                  (res) => res.fieldType == item.dictValue
                )[0].personNumber
              }}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <apply-footer :todoId="id" type="3"></apply-footer>
    </div>
  </div>
</template>
<script>
import {
  getCollection,
  getNextDealUser,
  getAllCompany,
} from "@/api/talent/collection";
import { getDicts } from "@/api/system/dict/data";
import ApplyFooter from "@/components/ApplyFooter";
export default {
  components:{ApplyFooter},
  created() {
    this.handleOpen();
  },
  data() {
    return {
        activeName: "1",
      id: "073f96092421424094632acc0b387ff7",
      disabled: true,
      applyUser: "",
      btnList: [],
      allFlowStep: [],
      companyList: [],
      nextUsers: [],
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
      formData: {
        title: "",
        collectionType: "",
        joinWorkDate: "",
        joinPartDate: "",
        startInspectTime: "",
        endInspectTime: "",
        datetime: [],
        collectionEndDate: "",
      },
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [], //tab字典
      collectDicts: [], //收集字典
    };
  },
  methods: {
    handleClick(e) {},
    handleCompanySelect(e, row) {
      row.companyId = e.companyId;
      row.companyName = e.companyName;
    },
    handleOpen() {
      //表头字典
      getDicts("field_type").then((res) => {
        //地市公司
        this.prefecturalDicts = res.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) == -1
        );
        //省公司
        this.provinceDicts = res.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) != -1
        );
      });
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
      });
      //tab字典
      getDicts("company_type").then((res) => {
        this.tabDicts = res.data;
        this.activeName = res.data[0].dictLabel;
      });
      getAllCompany().then((res) => {
        this.companyList = res.data;
      });
      if (!this.id) {
        this.title = "发起人才收集";
        this.addInit();
      } else {
        this.title = "查看";
        this.detailInit();
      }
    },
    detailInit() {
      this.getDetail();
    },
    //获取详情
    getDetail() {
      getCollection(this.id).then((res) => {
        if (res.code == 200) {
          this.formData = res.data;
          res.data.companyList.forEach((item) => {
            item.company = {
              companyId: item.companyId,
              companyName: item.companyName,
            };
          });
          this.prefecturalTableData = res.data.companyList.filter(item => item.companyType == '1');
          this.provinceTableData = res.data.companyList.filter(item => item.companyType == '2');
        }
        console.log(res);
      });
    },
    /** 导入按钮操作 */
    openImportTable() {
      this.upload.title = "入库管理_人才收集管理 数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    addInit() {
      getNextDealUser().then((res) => {
        let tempArr = [];
        let ids = res.data.nextUserId.split(",");
        let names = res.data.nextUserName.split(",");
        for (let i = 0; i < ids.length; i++) {
          tempArr.push({
            id: ids[i],
            name: names[i],
          });
        }
        this.nextUsers = tempArr;
      });
    },
    handleClose() {
      this.$emit("close");
    },
    handleTabClick(tab) {},
    add(t) {},
    tableInput(e) {
      let count = 0;
      this.prefecturalTableData.forEach((item) => {
        item.fieldList.forEach((item1) => {
          count += Number(item1.personNumber) || 0;
        });
      });
      e.expertTotals = count;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ input::-webkit-outer-spin-button,
/deep/ input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}
/deep/ input[type="number"] {
  -moz-appearance: textfield !important;
}
/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
/deep/ .el-dialog__footer {
  text-align: center;
}
/deep/ .el-form-item__error {
  width: 280px;
}
.app-main {
  padding: 15px;
  background: #fff;
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
.app-footer {
  text-align: center;
  background: #fff;
  padding-bottom: 20px;
}
</style>
