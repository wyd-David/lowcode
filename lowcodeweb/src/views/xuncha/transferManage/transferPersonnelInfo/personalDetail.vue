<template>
  <div>
    <!-- 查看人员抽调_巡察任务抽调信息 对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="isOpenQuery"
      width="1000px"
      :close-on-click-modal="false"
      :before-close="handleClose"
      @open="handleOpen"
      append-to-body="append-to-body"
    >
      <div class="dialog-context">
        <div class="export">
          <el-button size="mini" @click="handleExport" type="primary"
            >导出巡察人才推荐表</el-button
          >
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>工作信息</span>
        </div>
        <el-form
          :disabled="disabled"
          ref="queryform"
          :rules="rules"
          :model="form"
          label-width="130px"
        >
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="姓名：" prop="personnelName">
                <el-input
                  style="width: 250px"
                  v-model="form.userName"
                  placeholder="请输入姓名"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="6">
              <el-form-item label="所在单位：" prop="unit">
                <el-input style="width: 250px" v-model="form.companyName" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="部门：" prop="department">
                <el-input style="width: 250px" v-model="form.departmentName" />
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="6">
              <el-form-item label="部门备注：" prop="remark">
                <el-input
                  v-model="form.deptRemark"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="职务：" prop="postName">
                <el-input
                  v-model="form.postName"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="6">
              <el-form-item label="职级：" prop="rank">
                <el-input
                  v-model="form.postLevel"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="专业:" prop="major">
                <el-input style="width: 250px" v-model="form.fieldKey" />
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="6">
              <el-form-item label="是否有巡察经历:" prop="experience">
                <el-select
                  style="width: 250px"
                  multiple
                  v-model="form.experience"
                  placeholder=""
                >
                  <el-option label="是" :value="1"></el-option>
                  <el-option label="否" :value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="巡察年份:" multiple prop="date">
            <el-select
              style="width: 250px"
              multiple
              v-model="form.date"
              placeholder="请选择巡察年份"
            >
              <el-option
                v-for="dict in DateList"
                :key="dict.companyId"
                :label="dict.companyName"
                :value="dict.companyId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>

        <div class="dialog-title" style="justify-content: space-between">
          <div><span class="line"></span><span>基础信息</span></div>
        </div>

        <div>
          <el-form
            :disabled="disabled"
            ref="queryform"
            :rules="rules"
            :model="form"
            label-width="130px"
          >
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="出生年月:" prop="birthday">
                  <el-date-picker
                    v-model="form.birthday"
                    type="date"
                    style="width: 250px"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="参加工作时间：" prop="joindate">
                  <el-date-picker
                    v-model="form.joindate"
                    type="date"
                    style="width: 250px"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="入党时间：" prop="rudate">
                  <el-date-picker
                    v-model="form.rudate"
                    type="date"
                    style="width: 250px"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item
                  label-width="155px"
                  label="是否有一线工作经历:"
                  prop="isExperience"
                >
                  <el-radio-group
                    style="width: 150px"
                    v-model="form.isExperience"
                  >
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="OA邮箱:" prop="mail">
                  <el-input
                    v-model="form.mail"
                    placeholder="请输入邮箱"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-form-item label="手机号码:" prop="phone">
                  <el-input
                    v-model="form.phone"
                    placeholder="请输入手机号码"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="毕业院校及专业（全日制及在职）:" prop="major">
              <el-input v-model="form.major" style="width: 100%"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <!-- 工作经历 -->
        <div class="dialog-title">
          <span class="line"></span><span>工作经历</span>
        </div>

        <div class="dialog-title">
          <span class="line"></span><span>职级变更记录</span>
        </div>
        <div class="context-row">
          <el-table :data="rankRecordList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="年份" prop="year"></el-table-column>
            <el-table-column label="所属单位" prop="unit"></el-table-column>
            <el-table-column label="部门" prop="department"></el-table-column>
            <el-table-column label="职级" prop="rank"></el-table-column>
          </el-table>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>巡察经历</span>
        </div>
        <div class="context-row">
          <el-table :data="xunchaExperienceList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="巡察项目" prop="project"></el-table-column>
            <el-table-column
              label="组别名称"
              prop="groupName"
            ></el-table-column>
            <el-table-column label="组内角色" prop="role"></el-table-column>
            <el-table-column label="巡察批次" prop="pici"></el-table-column>
            <el-table-column
              label="巡察时间"
              prop="xunchaTime"
            ></el-table-column>
            <el-table-column label="考核结果" prop="result"></el-table-column>
          </el-table>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>表扬表彰</span>
        </div>
        <div class="context-row">
          <el-table :data="commendRecordList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="年份" prop="initiator"></el-table-column>
            <el-table-column label="所在分组" prop="group"></el-table-column>
            <el-table-column label="组内角色" prop="role"></el-table-column>
            <el-table-column
              label="巡察批次"
              prop="startTime"
            ></el-table-column>
            <el-table-column
              label="表扬表彰记录"
              prop="record"
            ></el-table-column>
          </el-table>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>出入库记录</span>
        </div>
        <div class="context-row">
          <el-table :data="enterRecord">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="年份" prop="year"></el-table-column>
            <el-table-column label="操作" prop="operation"></el-table-column>
            <el-table-column
              label="入库时间"
              prop="enterTime"
            ></el-table-column>
            <el-table-column
              label="出库时间"
              prop="exportTime"
            ></el-table-column>
            <el-table-column label="方式" prop="mode"></el-table-column>
          </el-table>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="handleClose">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getUserDetail } from "@/api/xuncha/transferManage/transferPersonnelInfo";

export default {
  props: {
    isOpenQuery: {
      type: Boolean,
      default: false,
    },
    // 弹出层标题
    title: {
      type: String,
    },
    disabled: {
      type: Boolean,
      default: true,
    },
    id: {
      type: String,
    },
  },
  data() {
    return {
      // 表单参数
      form: {
        unit: "",
        department: "",
        experience: 0,
      },

      rankRecordList: [],
      xunchaExperienceList: [],
      commendRecordList: [],
      enterRecord: [],
      professionList: [],
      // 表单校验
      rules: {
        personnelName: [
          {
            required: true,
            message: "请输入姓名",
            trigger: "blur",
          },
        ],
        unit: [
          { required: true, message: "所在单位不能为空", trigger: "change" },
        ],
        department: [
          { required: true, message: "部门不能为空", trigger: "change" },
        ],
        job: [{ required: true, message: "职务不能为空", trigger: "change" }],
        rank: [{ required: true, message: "职级不能为空", trigger: "change" }],
        major: [{ required: true, message: "专业不能为空", trigger: "change" }],

        experience: [
          { required: true, message: "请选择是否有巡察经历", trigger: "blur" },
        ],
        companyIds: [
          { required: true, message: "被巡察单位不能为空", trigger: "blur" },
        ],
        birthday: [
          { required: true, message: "出生年月不能为空", trigger: "blur" },
        ],
        joindate: [
          { required: true, message: "参加工作时间不能为空", trigger: "blur" },
        ],
        rudate: [
          { required: true, message: "入党时间不能为空", trigger: "blur" },
        ],
        isExperience: [
          {
            required: true,
            message: "请选择是否有一线工作经历",
            trigger: "blur",
          },
        ],
        mail: [
          {
            required: true,
            message: "邮箱不能为空",
            trigger: "blur",
          },
        ],
        phone: [
          {
            required: true,
            message: "手机号码不能为空",
            trigger: "blur",
          },
        ],
      },
      // select选择
      // 专业
      majorList: [],
      // 职级
      rankList: [],
      // 巡察年份
      DateList: [],
      // 部门
      departmentList: [],
      CompanyAll: [
        { companyId: 1, companyName: "深圳市公司" },
        { companyId: 2, companyName: "广州市公司" },
      ],
    };
  },
  computed: {},
  methods: {
    handleOpen() {
      this.userDetailQuery();
    },
    addRule() {
      this.ruleTable.push({
        tab: null,
        profession: null,
        professionNum: null,
        Num: null,
      });
    },
    handleExport() {
      // exportTransferPersonnelInfo().then((res) => {
      //   // this.msgSuccess()
      // });
    },
    init() {
      this.rankRecordListQuery();
      this.xunchaExperienceQuery();
      this.commendRecordQuery();
      this.commendRecordQuery();
      this.enterRecordQuery();
    },
    userDetailQuery() {
      getUserDetail({ userId: this.id }).then((res) => {
        this.form = res.data;
      });
    },
    // 职级Query
    rankRecordListQuery() {
      this.rankRecordList = [
        {
          year: "2022",
          unit: "广州公司",
          department: "党廉办",
          rank: "6级",
        },
      ];
    },
    // 巡察经历Query
    xunchaExperienceQuery() {
      this.xunchaExperienceList = [
        {
          project: "广州公司巡察",
          groupName: "综合巡察一组",
          role: "主笔人",
          pici: "2022年第一批",
          xunchaTime: "2022-02-11至2022-03-20",
          result: "9.40",
        },
      ];
    },
    // 表扬表彰Query
    commendRecordQuery() {
      this.commendRecordList = [
        {
          year: "2022",
          group: "综合巡察一组",
          role: "主笔人",
          startTime: "2022年第一批",
          record: "优秀巡察员",
        },
      ];
    },
    // 出入库
    enterRecordQuery() {
      this.enterRecord = [
        {
          year: "2021",
          operation: "入库",
          enterTime: "2021-03-18",
          exportTime: "",
          mode: "批量推荐",
        },
      ];
    },
    deleteRule(index) {
      this.ruleTable.splice(index, 1);
    },
    react() {
      Object.assign(this.$data, this.$options.data());
    },
    handleClose(e) {
      // this.react()
      this.$emit("close", e);
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-form-item__error {
  width: 120px;
}
.dialog-context {
  .export {
    text-align: right;
    margin-bottom: 10px;
  }
  .radio_group {
    display: flex;
  }
  .dialog-title {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
    color: rgba(23, 35, 61, 0.85);
    background: #fff;

    .btn {
      float: right;
    }

    .line {
      display: inline-block;
      width: 4px;
      height: 17px;
      background: #1590ff;
      border-radius: 2px;
      margin-right: 10px;
    }
  }

  .btn_box {
    display: flex;
    justify-content: center;
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