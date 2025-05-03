<template>
  <div>
    <el-dialog
    modal-append-to-body
    append-to-body
    @open="init"
      title="个人信息详情"
      :visible.sync="dialogVisible"
      width="1000px"
      :close-on-click-modal="false"
      :before-close="close"
    >
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>工作信息</span>
          <div class="operation">
            <el-button type="primary" size="small"
              >导出巡察人才推荐表</el-button
            >
          </div>
        </div>
        <div class="context-row">
          <el-form
            ref="workForm"
            :model="form"
            :rules="rulesForm"
            label-width="130px"
          >
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="姓名:" prop="name">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="所在单位:" required>
                  <el-select v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
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
                <el-form-item label="部门:" required>
                  <el-select v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="部门备注:" required>
                  <el-input></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="职务:" required>
                  <el-select v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="职级:" required>
                  <el-select v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
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
                <el-form-item label="专业:" required>
                  <el-select v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="是否有巡察经历:" required>
                  <el-select v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
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
          <span class="line"></span><span>基本信息</span>
        </div>
        <div class="context-row" style="padding-left: 20px">
          <el-form
            ref="basicForm"
            :model="form"
            :rules="rulesForm"
            label-width="150px"
          >
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="出生年月:">
                  <el-date-picker
                    v-model="form.birth"
                    type="month"
                    placeholder="请选择"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="参加工作时间:">
                  <el-date-picker
                    v-model="form.workDate"
                    type="date"
                    placeholder="请选择"
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
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>工作经历</span>
        </div>
        <div class="context-row">
          <el-form label-width="130px">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="起始时间:">
                  <div style="width: 280px">
                    <el-date-picker
                      style="width: 120px"
                      v-model="value1"
                      type="year"
                      placeholder="开始"
                    >
                    </el-date-picker>
                    -
                    <el-date-picker
                      style="width: 120px"
                      v-model="value1"
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
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getUserInfo } from "@/api/talent/dept";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      yearData:[],
        workItems:[],
      form: {},
      rulesForm: {
        phone: [
          {
            required: true,
            message: "请输入手机号码",
            trigger: "blur",
          },
          {
            pattern: /^1[3456789]\d{9}$/,
            message: "手机号码格式不正确",
            trigger: "blur",
          },
        ],
        name: [
          {
            required: true,
            message: "请输入姓名",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created(){
    this.getYears();
  },
  methods: {
    init() { 
      this.getDetail();
    },
    getDetail() {
      getUserInfo({ userId: this.id }).then((res) => {
        this.form = res.data;
        this.workItems = res.data.workItems;
      });
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
/deep/.el-radio-group {
  display: flex;
  height: 36px;
  align-items: center;
}
/deep/.el-dialog__body{
    max-height: 500px;
    overflow-y: auto;
}
</style>
