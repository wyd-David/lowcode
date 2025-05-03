<template>
  <div class="app-main">
    <todo-bpm-header></todo-bpm-header>
    <div class="dialog-context">
      <div class="dialog-title">
        <span class="line"></span><span>待办信息</span>
      </div>
      <div class="context-row">
        <el-form :model="form" disabled label-width="120px">
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="业务单号:">
                <el-input v-model="form.orderNumber" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="标题:">
                <el-input v-model="form.title" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="收集类型:">
                <el-radio-group v-model="form.collectionType">
                  <el-radio
                    :label="item.dictValue"
                    v-for="item in collectDicts"
                    :key="item.dictValue"
                    >{{ item.dictLabel }}</el-radio
                  >
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="下发机构:">
                <el-input v-model="form.companyName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="参加工作时间:">
                <el-input :value="form.joinWorkDate"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="入党时间:">
                <el-input :value="form.joinPartDate"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="计划巡察时段:">
                <div style="display: flex">
                  <el-input
                    :value="form.startInspectTime"
                    style="width: 49%"
                  ></el-input>
                  <span style="margin: 0px 2px">-</span>
                  <el-input
                    style="width: 49%"
                    :value="form.endInspectTime"
                  ></el-input>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收集截止时间:">
                <el-input :value="form.collectionEndDate"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="说明:">
            <el-input
              type="textarea"
              v-model="form.remark"
              :rows="3"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="dialog-title">
        <span class="line"></span><span>名额指标</span>
      </div>
      <div class="context-row">
        <el-table
          :data="form.fieldList || []"
        >
          <el-table-column
            align="center"
            label="序号"
            type="index"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="fieldTypeName"
            label="专家类型"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="personNumber"
            label="需求人数(人)"
          ></el-table-column>
        </el-table>
        <div style="text-align: right">
          总计:<span style="color: #b1261d"> {{ total }} </span>人
        </div>
      </div>
      <!-- <apply-footer :todoId="id" type="1"></apply-footer> -->
      <all-flow-step :allFlowStep="process.allFlowStep"></all-flow-step>
    </div>
    <div style="height:40px;"></div>
    <div class="dialog-footer-btn" v-if="!Number(form.issuedStatus) && process.authorizeFlag == '2'">
      <!-- <el-button @click="dialogVisible = false">取 消</el-button> -->
      <el-button type="primary" @click="dialogVisible = true">下 发</el-button>
    </div>
    <div class="dialog-footer-btn" v-if="form.processStatus == '20' && process.authorizeFlag == '2'">
      <el-button type="primary" @click="applyOper">审 核</el-button>
    </div>
    <div class="dialog-footer-btn" v-if="form.processStatus == '30' && process.authorizeFlag == '2'">
    <el-button
          type="primary"
          @click="confirmInterview"
          style="margin-right: 10px"
          >确认入库</el-button
        >
    </div>
    <!-- <deep-btn :businessKey="id" type="3">
        <el-button type="primary" @click="dialogVisible = true">下 发</el-button>
    </deep-btn> -->

    <rs-form-apply :obj="applyOpen" @close="handleClose"></rs-form-apply>

    <el-dialog title="任务下发" :visible.sync="dialogVisible" width="1000px">
      <div class="dialog-context">
        <div class="dialog-title">
          <span class="line"></span><span>下一步</span>
        </div>
        <div class="context-row">
          <el-table border :data="nextData" :span-method="objectSpanMethod">
            <el-table-column
              label="序号"
              type="index"
              align="center"
            ></el-table-column>
            <el-table-column
              label="专家类型"
              prop="fieldTypeName"
              align="center"
            ></el-table-column>
            <el-table-column
              label="需求人数(人)"
              prop="personNumber"
              align="center"
            ></el-table-column>
            <el-table-column width="200px" label="下发责任单位" align="center">
              <template slot-scope="scope">
                <el-select
                  value-key="deptId"
                  v-model="scope.row.dept"
                  @change="
                    (item) => {
                      handleSelect(item, scope.row);
                    }
                  "
                  style="width: 170px"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="(item, index) in options"
                    :key="item.deptId + index"
                    :label="item.deptName"
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column width="200px" label="接收人" align="center">
              <template slot-scope="scope">
                <el-select
                  value-key="loginId"
                  style="width: 170px"
                  v-model="scope.row.user"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in scope.row.userList"
                    :key="item.loginId"
                    :label="item.username"
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="指标人数" align="center" width="150px">
              <template slot-scope="scope">
                <el-input-number
                  style="width: 120px"
                  v-model="scope.row.num"
                  :min="1"
                  :max="maxNum(scope.row)"
                ></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="说明" align="center" width="150px">
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.remark"
                  style="width: 120px"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              fixed="right"
              width="150px"
              align="center"
            >
              <template slot-scope="scope">
                <el-button size="small" @click="remove(scope.row)"
                  >删除</el-button
                >
                <el-button
                  v-if="scope.row.children != null"
                  size="small"
                  type="primary"
                  @click="addItem(scope.row)"
                  >添加</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit(2)">暂 存</el-button>
        <el-button type="primary" @click="submit(1)">提 交</el-button>
      </span>
    </el-dialog>

    <rs-form :obj="rsFormObj" @close="handleClose"></rs-form>
  </div>
</template>
<script>
import {
  getCompanyById,
  getTodoInfo,
  getDeptList,
  getDeptContactPerson,
  issued,
  c_temporarySave as temporarySave,
} from "@/api/talent/dept";
import {
  checkWareHousing,
} from "@/api/talent/recommendSchedule";
import rsFormApply from "@/views/talent/recommendSchedule/rsFormApply";
import rsForm from "@/views/talent/recommendSchedule/rsForm";
import { getDicts } from "@/api/system/dict/data";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import { fetchWorkFlowInfo } from "@/utils/workflow";
export default {
  components: {
    AllFlowStep,
    rsFormApply,
    rsForm
  },
  data() {
    return {
      applyOpen: {
        open: false,
        id: "",
      },
      rsFormObj: {
        open: false,
      },
      id: "",
      dialogVisible: false,
      options: [],
      form: {
        companyList: [],
      },
      collectDicts: [], //收集字典
      nextData: [],
      process: { allFlowStep: [] },
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.getDetail();
    //收集字典
    getDicts("collection_type").then((res) => {
      this.collectDicts = res.data;
    });
    getDeptList({ companyName: this.form.companyName }).then((res) => {
      this.options = res.data;
    });
    fetchWorkFlowInfo(this.id, null, (res) => {
      this.process = res;
    });
  },
  computed: {
    //计算指标人数最大
    maxNum() {
      return function (e) {
        let tempArr = this.nextData.filter((item) => item.id == e.id);
        let max = tempArr[0].personNumber + e.num;
        tempArr.forEach((res) => {
          max -= res.num;
        });
        return max;
      };
    },
    total() {
      let count = 0;
      if (this.form.companyList[0]) {
        this.form.fieldList.forEach((res) => {
          count += res.personNumber;
        });
      }
      return count;
    },
  },
  methods: {
    //检查是否可以入库
    confirmInterview() {
      checkWareHousing({
        ids: this.form.newId,
        companyType:  1 ,
      }).then((res) => {
        if (res.code == 200) {
          this.rsFormObj.id = this.form.id;
          this.rsFormObj.collectionType = this.form.collectionType;
          this.rsFormObj.ids = this.form.newId;
          
          this.rsFormObj.companyIds = this.form.companyId;
          this.rsFormObj.companyType =  1;

          this.rsFormObj.title = this.form.title;
          this.rsFormObj.orderNumber = this.form.orderNumber;
          this.rsFormObj.startInspectTime = this.form.startInspectTime;
          this.rsFormObj.endInspectTime = this.form.endInspectTime;
          this.rsFormObj.collectionEndDate = this.form.collectionEndDate;
          this.rsFormObj.open = true;
        }
      });
    },
    applyOper() {
      this.applyOpen.id = this.form.newId;
      this.applyOpen.orderNumber = this.form.newOrderNumber;
      this.applyOpen.title = "审核";
      this.applyOpen.todoTitle = this.form.title;
      this.applyOpen.todoId = this.form.orderNumber;
      this.applyOpen.collectionEndDate = this.form.collectionEndDate;
      this.applyOpen.companyType = this.form.companyType;
      this.applyOpen.type = 1;
      this.applyOpen.open = true;
    },
    handleClose(e) { 
      this.rsFormObj.open = false;
      this.applyOpen.open = false;
      if (typeof (e) == 'number') {
        location.reload();
      }
    },
    submit(t, fun) {
      if (!fun) {
        this.$confirm("确定提交吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.submit(t, 1);
        });
        return;
      }

      let tempArr = [];
      let tempList = JSON.parse(JSON.stringify(this.nextData));
      let tempObj = {};
      let deptFlag = false; //用于判断是否选择部门
      let userFlag = false; //用于判断是否选择用户
      let numFlag = false; //用于判断指标人数大于需求人数
      let deptRepetition = false; //用于判断部门是否重复

      //转换树形结构
      tempList.forEach((res) => {
        if (!res.dept.deptId) {
          deptFlag = true;
        }

        if (!res.user.loginId) {
          userFlag = true;
        }

        if (res.fieldType != tempObj.fieldType) {
          tempObj = res;
          tempArr.push(res);
        } else {
          if (!tempObj.childrens) {
            tempObj.childrens = [];
          }
          tempObj.childrens.push(res);
        }
      });

      //判断是否选择部门
      if (deptFlag) {
        this.$message.warning("请选择部门!");
        return;
      }

      if (userFlag) {
        this.$message.warning("请选择用户!");
        return;
      }

      tempArr.forEach((res) => {
        if (res.childrens) {
          //判断指标人数大于需求人数
          let count = 0;
          res.childrens.forEach((i) => {
            count += Number(i.num);
          });
          count += res.num;
          if (res.personNumber < count) {
            numFlag = true;
          }
          //判断部门重复
          let deptArr = res.childrens.map((item) => item.dept.deptId);
          deptArr.push(res.dept.deptId);
          let nary = deptArr.sort();
          for (let i = 0; i < deptArr.length; i++) {
            if (nary[i] == nary[i + 1]) {
              deptRepetition = true;
            }
          }
        } else {
          if (res.personNumber < res.num) numFlag = true;
        }
      });

      if (deptRepetition) {
        this.$message.warning("每个专家类型的下发责任单位不能重复!");
        return;
      }

      if (numFlag) {
        this.$message.warning("指标人数不能超过需求人数!");
        return;
      }

      //组装数据
      let assemblyData = [];

      tempArr.forEach((res) => {
        res.deptId = res.dept.deptId;
        res.deptName = res.dept.deptName;
        res.receiveUserId = res.user.loginId;
        res.receiveUserName = res.user.username;
        res.personNumber = res.num;

        let tempObj1 = assemblyData.find((af) => af.deptId == res.deptId);

        if (!tempObj1) {
          res.fieldList = [this.cloneObj(res)];
          assemblyData.push(this.cloneObj(res));
        } else {
          tempObj1.fieldList.push(this.cloneObj(res));
        }

        if (res.childrens) {
          res.childrens.forEach((item) => {
            item.deptId = item.dept.deptId;
            item.deptName = item.dept.deptName;
            item.receiveUserId = item.user.loginId;
            item.receiveUserName = item.user.username;
            item.personNumber = item.num;
            if (!item.fieldList) {
              item.fieldList = [];
            }
            let tempObj = assemblyData.find((af) => af.deptId == item.deptId);
            if (!tempObj) {
              item.fieldList.push(this.cloneObj(res));
              assemblyData.push(item);
            } else {
              tempObj.fieldList.push(this.cloneObj(res));
            }
          });
        }
      });
      //统计指标人数
      assemblyData.forEach((res) => {
        let count = 0;
        res.fieldList.forEach((item) => {
          count += item.personNumber;
        });
        res.personNumber = count;
        res.expertTotals = count;
      });

      //提交数据
      const loading = this.openLoading();
      if (t == 1) {
        issued({
          deptList: assemblyData,
          colletionId: this.form.id,
          companyId: this.form.companyList[0].companyId,
        }).then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message.success("下发成功!");
            this.dialogVisible = false;
            loading.close();
            location.reload();
          } else {
            this.$message.error(res.message);
          }
        });
      } else {
        temporarySave({
          deptList: assemblyData,
          colletionId: this.form.id,
          companyId: this.form.companyList[0].companyId,
        }).then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message.success("下发成功!");
            this.dialogVisible = false;
            loading.close();
            location.reload();
          } else {
            this.$message.error(res.message);
          }
        });
      }
    },
    cloneObj(e) {
      return JSON.parse(JSON.stringify(e));
    },
    handleSelect(e, row) {
      row.user = {};
      getDeptContactPerson(e.deptId).then((res) => {
        row.userList = res.data || [];
      });
    },
    //获取详情
    getDetail() {
      getCompanyById(this.id).then((item) => {
        getTodoInfo({id:item.data.colletionId,companyId:item.data.companyId}).then((res) => {
          if (res.code == 200) {
            item.data.fieldList.forEach((i) => {
              i.children = 1;
              i.expertTotals = i.personNumber;
              i.companyId = res.data.companyList[0].companyId;
              i.companyName = res.data.companyList[0].companyName;
              i.num = i.personNumber;
              i.userList = [];
              i.user = {};
              i.dept = {};
            });
            if (item.data.fieldList) { 
              item.data.fieldList.forEach((i,index) => { i.id = index });
            }
            this.nextData = JSON.parse(
              JSON.stringify(
                item.data.fieldList.filter(
                  (item) => item.personNumber
                )
              )
            );

            this.form = res.data;
            this.form.processStatus = item.data.processStatus;
            this.form.newOrderNumber = item.data.orderNumber;
            this.form.newId = item.data.id;
            this.form.companyType = item.data.companyType;
            this.form.fieldList = item.data.fieldList.filter(i => i.personNumber);
            this.form.companyName = item.data.companyName;
            if (res.data.deptList.length) {
              this.resolverList();
            }
          }
        });
      });
    },
    //解析数据,回显用的
    resolverList() {
      let tempArr = [];
      this.form.deptList.forEach((res) => {
        let count = 0;
        let id = 0;
        res.fieldList.forEach((i) => {
          // i.dept = {
          //   deptId: res.deptId,
          //   deptName: res.deptName,
          // };
          count += i.personNumber;
          i.dept = this.options.find((o) => o.deptId == res.deptId);
          i.companyId = res.companyId;
          i.companyName = res.companyName;
          i.user = {
            loginId: res.receiveUserId,
            username: res.receiveUserName,
          };
          i.remark = res.remark;
          i.num = i.personNumber;
          i.userList = [i.user];
          i.children = 1;
          let index = this.getIdByIndex(i.fieldType, tempArr);
          if (index != -1) {
            i.children = null;
            tempArr[index].children++;
            i.id = tempArr[index].id;
            tempArr.splice(index + 1, 0, i);
          } else {
            i.id = id++;
            tempArr.push(i);
          }
        });
      });
      tempArr.forEach((t) => {
        let obj = this.form.companyList[0].fieldList.find(
          (o) => o.fieldType == t.fieldType
        ).personNumber;
        t.personNumber = obj;
      });
      this.nextData = JSON.parse(JSON.stringify(tempArr));

      this.form.fieldList.forEach(res => { 
        if (!this.nextData.find(i => i.fieldType == res.fieldType)) { 
          this.nextData.push(res);
        }
      })
    },
    //根据id获取下标
    getIdByIndex(id, arr) {
      if (!arr) {
        arr = this.nextData;
      }
      let index = -1;
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].fieldType == id) {
          index = i;
        }
      }
      return index;
    },
    //表格合并
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if ([0, 1, 2].indexOf(columnIndex) != -1) {
        if (row.children) {
          return {
            rowspan: row.children,
            colspan: 1,
          };
        } else if (row.children == null) {
          return {
            rowspan: 0,
            colspan: 1,
          };
        } else {
          return {
            rowspan: 1,
            colspan: 1,
          };
        }
      }
    },
    addItem(row) {
      let index = this.getIdByIndex(row.fieldType);
      row.children++;
      this.nextData.splice(index + 1, 0, {
        ...JSON.parse(JSON.stringify(row)),
        num: 0,
        dept: {},
        user: {},
        userList: row.userList || [],
        children: null,
      });
    },
    remove(row) {
      let index = this.getIdByIndex(row.fieldType);
      if (!row.children) {
        this.nextData.filter(
          (res) => res.fieldType == row.fieldType && res.children
        )[0].children--;
        this.nextData.splice(index, 1);
      } else {
        this.nextData = this.nextData.filter(
          (res) => res.fieldType != row.fieldType
        );
      }
    },
    close() {
      this.$emit("close");
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-dialog__body {
  max-height: 500px;
  overflow-y: auto;
}
.dialog-footer-btn {
  text-align: right;
  padding: 5px 50px 5px 0px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #fff;
  z-index: 100;
}
/deep/ input::-webkit-outer-spin-button,
/deep/ input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}
/deep/ input[type="number"] {
  -moz-appearance: textfield !important;
}
.el-form {
  /deep/.el-input {
    width: 100%;
  }
  /deep/.el-select {
    width: 100%;
  }
}
/deep/.el-radio-group {
  display: flex;
  height: 36px;
  align-items: center;
}
.dialog-context {
  background: #fff;
  padding: 15px;
}
</style>
