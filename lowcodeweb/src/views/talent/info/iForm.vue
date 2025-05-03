<template>
  <div>
    <el-dialog
      title="个人信息详情"
      :visible.sync="dialogVisible"
      width="1000px"
      @open="init"
      :before-close="close"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>工作信息</span>
        </div>
        <div class="context-row">
          <el-form label-width="120px" :disabled="disabled">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="姓名:">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="所在单位:">
                  <el-select v-model="form.company" placeholder="请选择">
                    <el-option
                      v-for="item in companys"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="部门:">
                  <el-select v-model="form.dept" placeholder="请选择">
                    <el-option
                      v-for="item in companys"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="部门备注:">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="职务:">
                  <el-select v-model="form.job" placeholder="请选择">
                    <el-option
                      v-for="item in companys"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="职级:">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="专业:">
                  <el-select v-model="form.major" placeholder="请选择">
                    <el-option
                      v-for="item in companys"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="是否有巡察经历:">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="巡察年份:">
              <el-select multiple v-model="form.years" placeholder="请选择">
                <el-option
                  v-for="item in yearData"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>基础信息</span>
        </div>
        <div class="context-row" style="padding-left: 10px">
          <el-form label-width="150px" :disabled="disabled">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="出生年月:">
                  <el-date-picker
                    v-model="form.birthDate"
                    type="month"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="参加工作时间:">
                    <el-date-picker
                    v-model="form.workDate"
                    type="date"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="入党时间:">
                  <el-date-picker
                    v-model="form.birth"
                    type="month"
                    placeholder="请选择"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="是否有一线工作经历:">
                  <el-radio-group v-model="form.radio">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="2">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="OA邮箱:">
                  <el-input v-model="form.oa"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="手机号码:" prop="phone">
                  <el-input v-model="form.phone"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="毕业院校及专业(全日制及在职):">
                <el-input type="textarea" :rows="3"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>工作经历</span>
        </div>
        <div class="context-row">
          <el-form label-width="130px" :disabled="disabled">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="起始时间:">
                  <div style="width: 280px">
                    <el-date-picker
                      style="width: 120px"
                      v-model="form.startTime"
                      type="year"
                      placeholder="开始"
                    >
                    </el-date-picker>
                    -
                    <el-date-picker
                      style="width: 120px"
                      v-model="form.endTime"
                      type="year"
                      placeholder="结束"
                    >
                    </el-date-picker>
                    &nbsp;&nbsp;<i class="el-icon-circle-plus-outline" @click="addItem" style="font-size:22px;cursor: pointer;"></i>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="工作单位及任职:">
                  <el-input></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-for="(item,index) in workItems" :key="index">
              <el-col :span="6">
                <el-form-item label="起始时间:">
                  <div style="width: 280px">
                    <el-date-picker
                      style="width: 120px"
                      v-model="item.startTime"
                      type="year"
                      placeholder="开始"
                    >
                    </el-date-picker>
                    -
                    <el-date-picker
                      style="width: 120px"
                      v-model="item.endTime"
                      type="year"
                      placeholder="结束"
                    >
                    </el-date-picker>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="工作单位及任职:">
                  <el-input></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="从事专业工作内容:">
              <el-input type="textarea" :rows="3"></el-input>
            </el-form-item>
            <el-form-item
            label-width="340px"
              label="近5年参加过的综合性调研、检查活动(列1-3项名称):"
            >
            </el-form-item>
            <el-form-item>
              <el-input type="textarea" :rows="3"></el-input>
            </el-form-item>
            <el-form-item
            label-width="340px"
              label="近5年撰写过的综合性文字材料(列2-3篇材料名称):"
            >
            </el-form-item>
            <el-form-item>
              <el-input type="textarea" :rows="3"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>职级变更记录</span>
        </div>
        <div class="context-row">
            <el-table>
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="年份"></el-table-column>
              <el-table-column label="所属单位"></el-table-column>
              <el-table-column label="部门"></el-table-column>
              <el-table-column label="职级"></el-table-column>
            </el-table>
        </div>

        <div class="dialog-title">
          <span class="line"></span><span>出入库记录</span>
        </div>
        <div class="context-row">
            <el-table>
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="入库时间"></el-table-column>
              <el-table-column label="出库时间"></el-table-column>
              <el-table-column label="入库方式"></el-table-column>
              <el-table-column label="出库方式"></el-table-column>
              <el-table-column label="出库原因"></el-table-column>
            </el-table>
        </div>

        <div class="dialog-title">
          <span class="line"></span><span>巡察经历</span>
        </div>
        <div class="context-row">
            <el-table>
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="巡察项目"></el-table-column>
              <el-table-column label="组别名称"></el-table-column>
              <el-table-column label="组内角色"></el-table-column>
              <el-table-column label="巡察批次"></el-table-column>
              <el-table-column label="巡察时间"></el-table-column>
              <el-table-column label="考核结果"></el-table-column>
            </el-table>
        </div>

        <div class="dialog-title">
          <span class="line"></span><span>表扬表彰</span>
        </div>
        <div class="context-row">
            <el-table>
              <el-table-column label="序号" type="index"></el-table-column>
              <el-table-column label="年份"></el-table-column>
              <el-table-column label="所在分组"></el-table-column>
              <el-table-column label="组内角色"></el-table-column>
              <el-table-column label="巡察批次"></el-table-column>
              <el-table-column label="表扬表彰记录"></el-table-column>
            </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getAllCompany } from "@/api/talent/collection";
import ApplyFooter from "@/components/ApplyFooter";
export default {
  components: { ApplyFooter },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "",
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
        workItems:[],
      yearData: [], //年份列表
      companys: [], //公司列表
      form: {},
      rulesForm: {},
    };
  },
  methods: {
    init() {
      getAllCompany().then((res) => {
        if (res.status == 200) {
          this.companys = res.rows;
        }
      });
      this.getYears();
    },
    //遍历年份数组
    getYears() {
      let arr = [];
      let lastYear = Number(String(new Date().getFullYear()).substring(2, 4));
      for (let i = 0; i <= lastYear; i++) {
        arr.push({
          label: `${i + 2000}年`,
          value: `${i + 2000}`,
        });
      }
      this.yearData = arr;
    },
    getDetail() {},
    close() {
      this.$emit("close");
    },
    submit() {
      let flag = false;
      ["workForm", "basicForm"].forEach((item) => {
        this.$refs[item].validate((valid) => {
          if (valid) {
            flag = true;
          } else {
            flag = false;
          }
        });
      });
      console.log(flag);
    },
    addItem(){
        this.workItems.push({
            startTime:'',
            endTime:''
        });
    }
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-input {
  width: 260px;
}

/deep/.el-dialog__body{
    max-height: 500px;
    overflow-y: auto;
}
/deep/.el-radio-group {
  display: flex;
  height: 36px;
  align-items: center;
}
.app-main {
}
</style>
