<template>
  <div class="dialog-context">
    <div v-if="type != '3'" class="dialog-title">
      <span class="line"></span><span>搜索人才</span>
    </div>
    <div v-if="type != '3'" class="context-row">
      <el-form
        :model="queryForm"
        :rules="rulesForm"
        ref="rulesForm"
        label-width="100px"
      >
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="姓名:" prop="name">
              <el-input
                style="width: 100%"
                v-model="queryForm.name"
                placeholder="请输入姓名"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在单位:" prop="companyName">
              <el-input
                style="width: 100%"
                v-model="queryForm.companyName"
                placeholder="请输入部门"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="部门:" prop="deptName">
              <el-input
                style="width: 100%"
                v-model="queryForm.deptName"
                placeholder="请输入部门"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专家类型:" prop="fieldType">
              <el-select
                style="width: 100%"
                v-model="queryForm.fieldType"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in filedSelect"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" :offset="12">
            <el-form-item style="    text-align: right;">
              <el-button size="small" @click="reset">重置</el-button>
              <el-button size="small" type="primary" @click="handleQuery"
                >查询</el-button
              >
            </el-form-item></el-col
          >
        </el-row>
      </el-form>
    </div>
    <div v-if="type != '3'" class="dialog-title">
      <span class="line"></span><span>搜索结果</span>
      <div class="operation">
        <el-button type="primary" @click="labelSet" size="small"
          >设置标签</el-button
        >
      </div>
    </div>
    <div v-if="type != '3'" class="context-row">
      <el-table
        v-loading="loading"
        :data="searchData"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column align="center" type="selection" width="55">
        </el-table-column>
        <el-table-column
          align="center"
          label="序号"
          type="index"
          width="55"
        ></el-table-column>
        <el-table-column
          align="center"
          label="巡察员"
          prop="name"
        ></el-table-column>
        <el-table-column
          align="center"
          label="所在单位"
          prop="companyName"
        ></el-table-column>
        <el-table-column
          align="center"
          label="部门"
          prop="deptName"
        ></el-table-column>
        <el-table-column
          align="center"
          label="专家类型"
          prop="fieldTypeName"
        ></el-table-column>
      </el-table>
    </div>
    <div class="dialog-title">
      <span class="line"></span><span>标签设置详情</span>
    </div>
    <div class="context-row">
      <el-form v-if="id" disabled label-width="80px">
        <el-row :gutter="24"> 
          <el-col :span="12">
            <el-form-item label="业务单号:">
              <el-input :value="form.orderNumber"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column
          align="center"
          label="序号"
          type="index"
        ></el-table-column>
        <el-table-column
          align="center"
          label="巡察员"
          prop="name"
        ></el-table-column>
        <el-table-column
          align="center"
          label="所在单位"
          prop="companyName"
        ></el-table-column>
        <el-table-column
          align="center"
          label="部门"
          prop="deptName"
        ></el-table-column>
        <el-table-column
          label="专家类型"
          prop="fieldTypeName"
          align="center"
        ></el-table-column>
        <el-table-column align="center" label="已设标签" width="300">
          <template slot-scope="scope">
            <div class="label-box">
              <span
                @click="deleteLabel(scope.row, index)"
                class="label-item-t"
                v-for="(item, index) in scope.row.labels"
                :key="index"
                >{{ item.dataName }}
                <i v-if="type != '3'" class="el-icon-circle-close"></i
              ></span>
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" v-if="type != '3'">
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="labelSet(scope.row)"
              >添加标签</el-button
            >
            <el-button type="text" size="small" @click="deleteItem(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <all-flow-step v-if="!isTodo && id && form.processStatus != 1" :allFlowStep="process.allFlowStep"></all-flow-step>

    <el-dialog
      append-to-body
      title="设置标签"
      :visible.sync="open"
      width="600px"
      :before-close="handleClose"
      @closed="single = false"
    >
      <div class="dialog-context">
        请选择需要设置的标签
        <div class="label-box" v-if="single">
          <span
            :class="
              echoLables.findIndex((res) => res.dataCode === item.dataCode) !=
              -1
                ? 'label-item-active label-item'
                : 'label-item'
            "
            @click="addLabel(item)"
            v-for="item in singleLabels"
            :key="item.dataCode"
            >{{ item.dataName }}</span
          >
        </div>
        <div class="label-box" v-else>
          <span
            :class="
              echoLables.findIndex((res) => res.dataCode === item.dataCode) !=
              -1
                ? 'label-item-active label-item'
                : 'label-item'
            "
            @click="addLabel(item)"
            v-for="item in labels"
            :key="item.dataCode"
            >{{ item.dataName }}</span
          >
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="addItem">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getPersonList, getConfig, queryDictList } from "@/api/talent/config";
import { getDicts } from "@/api/system/dict/data";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
export default {
  components: {
    AllFlowStep
  },
  props: {
    id: {
      type: String,
      default: "",
    },
    //弹窗状态(1,新增,2,修改,3,查看,4,待办页)
    type: {
      type: String,
      default: "",
    },
    isTodo: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      open: false,
      single: false,
      multipleSelection: [],
      form: {},
      searchData: [], //搜索到的巡察员
      queryForm: {
        name: "",
        deptName: "",
        companyName: "",
        fieldType: "",
      },
      rulesForm: {
        deptName: [
          { required: true, message: "请输入所在单位", trigger: "change" },
        ],
        companyName: [
          { required: true, message: "请输入所在单位", trigger: "change" },
        ],
        fieldType: [
          { required: true, message: "请选择专家类型", trigger: "change" },
        ],
      },
      labels: [],
      process:{},
      singleLabels: [], //用于单个巡察员设置标签
      singleObj: {}, //记录当前操作的巡察员
      echoLables: [], //记录选中的标签
      tableData: [], //勾选的巡察员
      filedSelect: [], //专家类型下拉框数据
    };
  },
  created() {
    this.handleOpen();
  },
  methods: {
    //重置
    resetForm() {
      this.queryForm = {
        name: "",
        deptName: "",
        companyName:"",
        fieldType: "",
      };
      this.rulesForm = {
        deptName: [
          { required: false, message: "请输入所在单位", trigger: "change" },
        ],
        companyName: [
          { required: false, message: "请输入所在单位", trigger: "change" },
        ],
        fieldType: [
          { required: false, message: "请选择专家类型", trigger: "change" },
        ],
      };
      this.labels = [];
      this.searchData = [];
      this.singleLabels = [];
      this.echoLables = [];
      this.tableData = [];
      this.singleObj = {};
    },
    getDetail() {
      this.loading = true;
      getConfig(this.id).then((res) => {
        if (res.code == 200) {
          this.form = res.data;
          fetchWorkFlowInfo(res.data.orderNumber, null, (res) => {
            this.process = res;
          },1);
          let resultArr = res.data.labelResultList;
          let tempArr = JSON.parse(JSON.stringify(res.data.labelResultList));
          let obj = {};
          //去重
          tempArr = tempArr.reduce(function (item, next) {
            obj[next.userId]
              ? ""
              : (obj[next.userId] = true && item.push(next));
            return item;
          }, []);
          tempArr.forEach((item) => {
            item.labelIds = "";
            resultArr.forEach((j) => {
              if (item.userId == j.userId) {
                item.labelIds += j.labelId + ",";
              }
            });
            item.labelIds = item.labelIds.substring(
              0,
              item.labelIds.length - 1
            );
            item.labels = this.parseLabel(item.labelIds);
            item.name = item.userName;
          });
          this.tableData = tempArr;
          this.loading = false;
        }
      });
    },
    deleteItem(i) { 
      this.tableData.splice(i, 1);
    },
    //表单打开初始化
    handleOpen() {
      this.resetForm();
      //表头字典
      getDicts("field_type").then((res) => {
        this.filedSelect = res.data;
      });
      //查询标签列表
      queryDictList({
        dictType: "xuncha_label",
      })
        .then((res) => {
          this.labels = res.data.filter((item) => ["0","1","7"].indexOf(item.dataCode)  == -1);
          if (this.id) {
            this.getDetail();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //搜索巡察员
    handleQuery() {
      //校验表单
      // this.$refs.rulesForm.validate((valid) => {
      //   if (valid) {

      //   }
      // });
      //判断查询条件有一个
      if (
        this.queryForm.name == "" &&
        this.queryForm.deptName == "" &&
        this.queryForm.companyName == "" &&
        this.queryForm.fieldType == ""
      ) {
        this.$message.error("请至少输入一个查询条件");
        return;
      }
      getPersonList(this.queryForm).then((res) => {
        res.data.forEach((item) => {
          item.labels = [];
        });
        this.searchData = res.data;
      });
    },
    //解析以逗号隔开的标签
    parseLabel(label) {
      let tempArr = [];
      if (label) {
        label.split(",").map((item) => {
          let temp = this.labels.find((res) => res.dataCode == item);
          if (temp) { 
            tempArr.push({
              dataCode: item,
              dataName: temp.dataName,
            });
          }
        });
      }
      return tempArr;
    },
    reset() {
      this.queryForm = {
        name: "",
        deptName: "",
        companyName: "",
        fieldType: "",
      };
      this.$refs.rulesForm.resetFields();
    },
    //勾选标签
    labelSet(e) {
      if (e.labels) {
        this.single = true;
        this.singleObj = e;
        this.open = true;
        this.labels.forEach((res) => {
          if (
            e.labels.findIndex((item) => item.dataCode === res.dataCode) == -1
          ) {
            this.singleLabels.push(res);
          }
        });
        return;
      }
      if (this.multipleSelection.length) {
        this.open = true;
      } else {
        this.$message({
          message: "请选择需要设置标签的巡察员",
          type: "warning",
        });
      }
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    //激活勾选标签
    addLabel(e) {
      let index = this.echoLables.findIndex(
        (item) => item.dataCode == e.dataCode
      );
      if (index == -1) {
        this.echoLables.push(e);
      } else {
        this.echoLables.splice(index, 1);
      }
    },
    //已选标签列表,删除标签
    deleteLabel(e, i) {
      if (this.type != "3") {
        e.labels.splice(i, 1);
      }
    },
    addItem() {
      if (!this.echoLables.length) { 
        this.msgError("请选择标签!");
        return;
      }
      //判断是否修改
      if (this.single) {
        this.echoLables.forEach((res) => {
          this.singleObj.labels.push(res);
        });
      } else {
        //判断是否有重复的巡察员
        let oldArr = JSON.parse(JSON.stringify(this.multipleSelection));
        let tempArr = [];
        
        oldArr.forEach((item) => {
          let tempObj = this.tableData.find((res) => res.userId == item.userId);
          if (!tempObj) {
            tempArr.push(item);
          } else {
            //重复就不插入行,只合并标签
            this.echoLables.forEach((i) => {
              let temp = tempObj.labels.find(j => j.dataCode == i.dataCode);
              if (!temp) { 
                tempObj.labels.push(i);
              }
            });
          }
        });
        //判断是否有重复的标签
        tempArr.forEach((res) => {
          this.echoLables.forEach((item) => {
              res.labels.push(item);
          });
        });
        this.tableData = this.tableData.concat(tempArr);
      }
      this.toggleSelection();
      this.echoLables = [];
      this.singleLabels = [];
      this.multipleSelection = [];
      this.open = false;
      this.$forceUpdate();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleClose() {
      this.open = false;
      this.singleLabels = [];
    },
    close(e) {
      this.$emit("close", e);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
.dialog-title .operation {
  padding-right: 40px;
}
</style>
