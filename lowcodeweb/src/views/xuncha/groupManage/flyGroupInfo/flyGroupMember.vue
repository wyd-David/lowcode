<template>
  <div>
    <!-- 查看&新增分组 对话框 -->

    <div class="dialog-context">
      <div class="context-row">
        <el-form
          :disabled="type != 3"
          ref="queryform"
          :rules="rules"
          :model="formQuery"
          label-width="120px"
        >
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="年份：" prop="xunchaYear">
                <el-input
                  v-if="flyGroupId && type == 2"
                  style="width: 100%"
                  v-model="formQuery.xunchaYear"
                />
                <el-date-picker
                  v-else
                  clearable
                  style="width: 100%"
                  v-model="formQuery.xunchaYear"
                  type="year"
                  value-format="yyyy"
                  @change="
                    (val) =>
                      handleSelectChange(
                        val,
                        'piciRef',
                        ['instanceId', 'xunchaGroup'],
                        'year'
                      )
                  "
                  placeholder="选择巡察年份"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :offset="1" :span="11">
              <el-form-item
                label="巡察批次："
                v-if="flyGroupId && type == 2"
                prop="instanceId"
              >
                <el-input style="width: 100%" v-model="formQuery.xunchaPici" />
              </el-form-item>
              <el-form-item
                label="巡察批次："
                v-show="type != 2"
                prop="instanceId"
              >
                <pici
                  ref="piciRef"
                  isLink
                  v-model="formQuery.instanceId"
                  queryXunchaPici
                  :label.sync="formQuery.xunchaPiciName"
                  :width="width"
                  @change="
                    (val) =>
                      handleSelectChange(
                        val,
                        'groupRef',
                        ['xunchaGroup'],
                        'pici'
                      )
                  "
                >
                </pici>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="分组类型：" prop="type">
                <el-input
                  disabled
                  style="width: 100%"
                  v-model="formQuery.type"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="组名：" prop="flyGroupName">
                <el-input
                  v-model="formQuery.flyGroupName"
                  placeholder="请输入组名"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="组长：" prop="groupLeader">
                <el-input
                  v-if="flyGroupId && type == 2"
                  style="width: 100%"
                  v-model="formQuery.groupLeaderName"
                />
                <Person-search
                  v-else
                  ref="LeaderRef"
                  placeholder="请输入组长名"
                  @change="handleLeaderChange"
                  @clear="handleClear"
                />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="绑定分组：">
                <el-input
                  v-if="flyGroupId && type == 2"
                  style="width: 100%"
                  disabled
                  v-model="formQuery.groupName1"
                />
                <group
                  v-show="type != 2"
                  ref="groupRef"
                  :queryUnbound="true"
                  v-model="formQuery.groupId"
                  :label.sync="formQuery.groupName"
                  :width="width"
                  isLink
                >
                </group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>

      <div class="btn_box" v-if="type != 2">
        <el-button class="btn" size="mini" type="primary" @click="addMember"
          >添加组员
        </el-button>
      </div>
      <div class="context-row">
        <el-table
          v-loading="tableLoading"
          :data="formQuery.xunchaFlyPersonInfoList"
          align="center"
        >
          <el-table-column
            label="序号"
            align="center"
            type="index"
          ></el-table-column>
          <el-table-column
            label="人员"
            align="center"
            width="140"
            prop="personnelName"
          >
            <template slot-scope="scope">
              <span v-if="type && scope.row.flyGroupId">{{
                scope.row.personnelName
              }}</span>

              <el-select
                v-else
                style="width: 100%"
                v-model="scope.row.personnelObj"
                filterable
                remote
                value-key="userId"
                reserve-keyword
                placeholder="请输入姓名关键字"
                :remote-method="remoteMethod"
                :loading="loading"
                @change="handleMemberChange($event, scope.row)"
              >
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="
                    item.username +
                    '-' +
                    item.userId +
                    '-' +
                    item.company +
                    '/' +
                    item.department
                  "
                  :value="item"
                >
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="所在单位"
            align="center"
            prop="company"
          ></el-table-column>
          <el-table-column
            label="部门"
            align="center"
            prop="ouname"
          ></el-table-column>
          <el-table-column
            label="职位"
            align="center"
            min-width="120"
            prop="postName"
          ></el-table-column>

          <el-table-column width="80px" label="操作" v-if="type != 2">
            <template slot-scope="scope">
              <el-button
                @click="deleteRow(scope.row, scope.$index)"
                size="mini"
                type="text"
                >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import Pici from "@/components/XunCha/Pici/index";
import Lunci from "@/components/XunCha/LunCi/index";
import Group from "@/components/XunCha/Group/index";
import PersonSearch from "@/components/XunCha/PersonSearch/index";

import { getXunchaFlyGroupInfoList } from "@/api/xuncha/groupManage/flyGroupInfo.js";
import { listXunchaPiciManage } from "@/api/xuncha/XunchaPiciManage";
import {
  addFlyGroupInfo,
  editFlyGroupInfo,
} from "@/api/xuncha/groupManage/flyGroupInfo.js";
import { delFlyPersonInfo } from "@/api/xuncha/groupManage/flyPersonInfo.js";
import { selectLunciData } from "@/api/xuncha/XunchaLunciManage";
import { queryUserList } from "@/api/xuncha/common/base";

export default {
  props: {
    isOpenQuery: {
      type: Boolean,
      default: true,
    },
    // 弹出层标题
    title: {
      type: String,
    },
    // 1:添加组员  2:查看 3:编辑
    type: {
      type: Number,
      default: 0,
    },
    flyGroupId: {
      type: String,
      default: "",
    },
  },
  components: { Lunci, Pici, Group, PersonSearch },
  data() {
    return {
      groupRules: [],
      members: [],
      informTable: [],
      professionList: [],
      userList: [],
      loading: false,
      tableLoading: false,
      // 表单参数
      formQuery: {
        xunchaYear: null,
        instanceId: null,
        type: "飞行小组",
        flyGroupName: "",
        groupLeader: null,
        groupLeaderName: null,
        groupName: null,
        xunchaGroup: null,
        groupId: null,
        xunchaFlyPersonInfoList: [],
      },
      // 表单校验
      rules: {
        xunchaYear: [
          {
            required: true,
            message: "巡察年份不能为空",
            trigger: "change",
          },
        ],
        instanceId: [
          { required: true, message: "巡察批次名称不能为空", trigger: "blur" },
        ],
        type: [{ required: true, message: "", trigger: "blur" }],
        flyGroupName: [
          { required: true, message: "组名不能为空", trigger: "blur" },
        ],
      },
      CompanyAll: [],
      xunchaYears: [
        {
          value: "1",
          label: "22年",
        },
      ],
      width: "100%",
    };
  },
  computed: {},
  watch: {},
  created() {
    this.yearsQuery();
    this.handleOpne();
  },
  methods: {
    handleOpne() {
      if (this.flyGroupId) {
        let params = {
          flyGroupId: this.flyGroupId,
        };
        getXunchaFlyGroupInfoList(params).then((res) => {
          this.formQuery = res.data;
          this.formQuery.type = "飞行小组";
          this.$set(this.formQuery, "flyGroupName", res.data.flyName);
          this.$set(this.formQuery, "groupName1", res.data.groupName);
          this.$refs["groupRef"].queryList(
            res.data.instanceId,
            res.data.groupId
          );
          this.$refs["piciRef"].queryList(res.data.xunchaYear);
          this.$refs["LeaderRef"].remoteMethod(res.data.groupLeaderName);
          this.$refs["LeaderRef"].innerValue = res.data.groupLeader;

          console.log(res.data);
        });
      }
    },
    /**
     *
     */
    handleSelectChange(val, ref, formKeyArr, condition) {
      // 清空选中值
      formKeyArr.forEach((i) => {
        this.formQuery[i] = "";
      });
      // 请求下一个select数据
      this.$refs[ref].queryList(val);
      this.cleanOptions(condition);
    },
    cleanOptions(condition) {
      if (condition == "year") {
        this.$refs.piciRef.options = [];
        this.$refs.groupRef.options = [];
      } else if (condition == "pici") {
        this.$refs.groupRef.options = [];
      }
    },

    handleLeaderChange(val) {
      this.$refs.LeaderRef.userList.forEach((i) => {
        if (i.userId == val) {
          this.formQuery.groupLeaderName = i.username;
          this.formQuery.groupLeader = i.userId;
        }
      });
    },
    handleClear() {
      console.log("handleClear");
      this.formQuery.groupLeaderName = null;
      this.formQuery.groupLeader = null;
    },
    handleMemberChange(event, row) {
      row.personnelId = event.userId;
      row.ouname = event.department;
      row.company = event.company;
      row.postName = event.postName;
      row.personnelName = event.username;

      this.$forceUpdate();
    },
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          queryUserList(query).then((res) => {
            this.userList = res.data;
            this.loading = false;
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    // 巡察年份query
    yearsQuery() {
      selectLunciData().then((res) => {
        this.xunchaYears = res.data;
      });
    },
    //巡察批次Query
    piciQuery(params) {
      listXunchaPiciManage(params).then((res) => {
        this.instanceId = res.rows;
      });
    },

    addMember() {
      this.formQuery.xunchaFlyPersonInfoList.push({
        ouname: "",
        company: "",
        postName: "",
        personnelName: "",
        personnelId: "",
        indexNum: this.formQuery.xunchaFlyPersonInfoList.length + 1,
      });
    },
    submitForm() {
      this.$refs["queryform"].validate((valid) => {
        console.log(this.formQuery);
        if (valid) {
          console.log(this.formQuery);
          if (this.type == 3 && this.flyGroupId) {
            
            editFlyGroupInfo(this.formQuery).then((res) => {
              this.msgSuccess(res.msg);
              this.handleClose(200);
            });
          } else {
            addFlyGroupInfo(this.formQuery).then((res) => {
              this.msgSuccess(res.msg);
              this.handleClose(200);
            });
          }
        }
      });
    },

    handleClose(e) {
      this.$emit("close", e);
    },
    deleteRow(row, index) {
      this.formQuery.xunchaFlyPersonInfoList.splice(index, 1);
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog-context {
  .btn_box {
    display: flex;
    flex-direction: row-reverse;
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
  .result_box {
    margin-left: 28px;
    display: flex;
    align-items: center;
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
