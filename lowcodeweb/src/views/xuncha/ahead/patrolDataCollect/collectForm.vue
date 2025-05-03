<template>
  <div>
    <el-dialog
      :visible.sync="obj.dialogVisible"
      :fullscreen="isFullscreen"
      width="70%"
      @open="handleOpen"
      title="发起收集"
    >
      <div slot="title" style="position: relative">
        <span>{{ obj.title }}</span>
        <el-button
          type="text"
          style="position: absolute; right: 40px"
          size="small"
          @click="isFullscreen = !isFullscreen"
          >{{ isFullscreen ? "关闭全屏" : "打开全屏" }}</el-button
        >
      </div>
      <div class="dialog-context" v-if="obj.dialogVisible">
        <div class="dialog-title">
          <span class="line"></span><span>基础信息</span>
        </div>
        <div class="context-row">
          <el-form
            :disabled="obj.disabled"
            label-width="140px"
            :model="form"
            :rules="formRules"
            ref="formRef"
          >
            <el-row :gutter="24" v-if="obj.title != '修改巡前资料收集'">
              <el-col :span="24">
                <el-form-item label="资料类型:" prop="dataType">
                  <el-radio
                    @change="radioChange"
                    v-for="item in inventoryType"
                    v-model="form.dataType"
                    :key="item.dictValue"
                    :label="item.dictValue"
                    >{{ item.dictLabel }}</el-radio
                  >
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="资料说明:" prop="dataRemark">
                  <el-input
                    v-model="form.dataRemark"
                    placeholder="请输入资料说明"
                    type="textarea"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="资料提供时限要求:" prop="dataProvideTime">
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.dataProvideTime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetime"
                    placeholder="选择日期时间"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="查询时段要求:" prop="queryTime">
                  <el-date-picker
                    style="width: 100%"
                    value-format="yyyy-MM-dd"
                    v-model="form.queryTime"
                    type="date"
                    placeholder="选择日期"
                  >
                  </el-date-picker> </el-form-item
              ></el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="巡察年份:" prop="year">
                  <el-date-picker
                    style="width: 100%"
                    v-model="form.year"
                    type="year"
                    placeholder="选择年"
                    @change="changeYear"
                    value-format="yyyy"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="巡察批次:" prop="batchId">
                  <el-select
                    style="width: 100%"
                    v-model="form.batchId"
                    placeholder="请选择"
                    @change="changePici"
                  >
                    <el-option
                      :label="item.piciName"
                      v-for="item in selectPiciData"
                      :key="item.piciId"
                      :value="item.piciId"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="关联巡察任务:" v-if="form.dataType == 1">
                  <el-select
                    multiple
                    value-key="taskId"
                    style="width: 100%"
                    v-model="form.taskList"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in XunchaTaskInfoList"
                      :key="item.taskId"
                      :label="item.taskName"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item
                  prop="taskList"
                  label="关联巡察任务:"
                  key="taskList"
                  v-else
                >
                  <el-select
                    multiple
                    @change="taskChange"
                    @focus="companyFocus"
                    value-key="taskId"
                    style="width: 100%"
                    v-model="form.taskList"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in XunchaTaskInfoList"
                      :key="item.taskId"
                      :label="item.taskName"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="dialog-title">
          <span class="line"></span><span>资料清单</span>
        </div>
        <div class="context-row">
          <el-form
            v-if="form.dataType == 1"
            :disabled="obj.disabled"
            label-width="140px"
            :model="form"
            :rules="formRules"
            ref="formRef1"
          >
            <el-form-item label="提供部门:" prop="deptList" key="deptList">
              <el-select
                style="width: 100%"
                v-model="form.deptList"
                multiple
                placeholder="请选择"
                value-key="deptId"
                @change="deptChange"
                @focus="deptFocus"
              >
                <el-option
                  v-for="item in deptList"
                  :label="item.deptName"
                  :key="item.deptId"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div class="operation">
            <div>
              注：带 <span style="color: #f56c6c">*</span> 号的字段为必填字段
            </div>
            <div v-if="!obj.disabled">
              <el-button type="primary" size="small" @click="addInventoryList"
                >添加</el-button
              >
              <el-button
                @click="
                  dialogRemarkType = 1;
                  dialogRemark = true;
                "
                type="primary"
                size="small"
                >批量填写说明</el-button
              >
              <el-button type="primary" size="small">导入清单</el-button>
            </div>
          </div>
          <el-table :data="inventoryList">
            <el-table-column
              label="序号"
              align="center"
              type="index"
            ></el-table-column>
            <el-table-column
              v-if="form.dataType == 1"
              align="center"
              label="提供部门"
            >
              <template slot-scope="scope">
                <el-select
                  :disabled="obj.disabled || !!scope.row.id"
                  style="width: 100%"
                  v-model="scope.row.deptObj"
                  placeholder="请选择"
                  value-key="deptId"
                >
                  <el-option
                    v-for="item in form.deptList"
                    :label="item.deptName"
                    :key="item.deptId"
                    :value="item"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
              :render-header="addRedStar"
              align="center"
              label="需求资料清单"
            >
              <template slot-scope="scope">
                <el-input
                  :disabled="obj.disabled || !!scope.row.id"
                  style="width: 100%"
                  v-model="scope.row.inventoryName"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              :render-header="addRedStar"
              align="center"
              label="加入巡前资料基础清单"
            >
              <template slot-scope="scope">
                <el-radio
                  :disabled="obj.disabled || !!scope.row.id"
                  v-model="scope.row.joinBaseInventory"
                  label="1"
                  >是</el-radio
                >
                <el-radio
                  :disabled="obj.disabled || !!scope.row.id"
                  v-model="scope.row.joinBaseInventory"
                  label="0"
                  >否</el-radio
                >
              </template>
            </el-table-column>
            <el-table-column align="center" label="说明">
              <template slot-scope="scope">
                <el-input
                  :disabled="obj.disabled || !!scope.row.id"
                  style="width: 100%"
                  v-model="scope.row.remark"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column v-if="!obj.disabled" align="center" label="操作">
              <template slot-scope="scope">
                <el-button
                  :disabled="!!scope.row.id"
                  type="text"
                  size="small"
                  @click="delInventory(scope.$index)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div class="dialog-title">
          <span class="line"></span><span>下发</span>
          <div class="operation" v-if="!obj.disabled">
            <el-button
              @click="
                dialogRemarkType = 2;
                dialogRemark = true;
              "
              type="primary"
              size="small"
              >批量填写意见</el-button
            >
          </div>
        </div>
        <div class="context-row">
          <el-table :data="issueList">
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column
              v-if="form.dataType == 1"
              align="center"
              :render-header="addRedStar"
              label="部门"
            >
              <template slot-scope="scope">
                <el-select
                  disabled
                  style="width: 100%"
                  v-model="scope.row.deptObj"
                  placeholder="请选择"
                  value-key="deptId"
                  @change="issDeptSelect(scope.row)"
                >
                  <el-option
                    v-for="item in form.deptList"
                    :label="item.deptName"
                    :key="item.deptId"
                    :value="item"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
              v-else
              label="被巡察单位"
              align="center"
              :render-header="addRedStar"
            >
              <template slot-scope="scope">
                <el-select
                  :disabled="obj.disabled"
                  style="width: 100%"
                  v-model="scope.row.companyObj"
                  placeholder="请选择"
                  value-key="companyId"
                  @change="issCompanySelect(scope.row)"
                >
                  <el-option
                    v-for="(item, index) in issueList"
                    :label="item.companyNames"
                    :key="index"
                    :value="item"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              :render-header="addRedStar"
              label="处理人"
            >
              <template slot-scope="scope">
                <el-select
                  :disabled="obj.disabled"
                  value-key="userid"
                  v-model="scope.row.dealUser"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in scope.row.dealUserList"
                    :key="item.userid"
                    :label="item.fullname"
                    :value="item"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column v-if="this.obj.title != '查看巡前资料收集'" align="center" label="备注">
              <template slot-scope="scope">
                <el-input
                  :disabled="obj.disabled"
                  v-model="scope.row.remark"
                  placeholder="请输入备注"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column v-if="this.obj.title == '查看巡前资料收集'" label="下发业务单号" prop="orderNumber"></el-table-column>
            <el-table-column v-if="this.obj.title == '查看巡前资料收集'" label="当前状态" :formatter="aheadProcessStatusFormat"></el-table-column>
            <el-table-column v-if="this.obj.title == '查看巡前资料收集'" label="处理时间" prop="createdTime"></el-table-column>
          </el-table>
        </div>
        <div style="height: 40px"></div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button v-if="!obj.disabled" type="primary" @click="temporarySave"
          >暂 存</el-button
        >
        <el-button v-if="!obj.disabled" type="primary" @click="submit"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 批量设置说明弹窗 -->
    <el-dialog
      append-to-body
      modal-append-to-body
      title="设置说明"
      :visible.sync="dialogRemark"
    >
      <div class="dialog-content">
        <el-form label-width="80px">
          <el-form-item label="说明:">
            <el-input v-model="remark" placeholder="请输入说明"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="setRemarks">确 定</el-button>
        <el-button @click="dialogRemark = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  queryPiciByYeay,
  listXunchaTaskInfo,
} from "@/api/xuncha/XunchaTaskInfo";
import {
  add,
  edit,
  temporarySave,
  getInfo,
  selectBydeptIds,
} from "@/api/xuncha/ahead/patrolDataCollect";
import {
  getDeptList,
  getUserByPostName,
  getUserByRoleKey,
} from "@/api/talent/collection";
import { getDicts } from "@/api/system/dict/data";
export default {
  props: {
    obj: [Object],
  },
  data() {
    return {
      isFullscreen: false,
      form: {
        dataType: "1",
        dataRemark: "",
        dataProvideTime: "",
        queryTime: "",
        year: "",
        batchId: "",
        taskList: [],
        deptList: [],
        companyList: [],
      },
      formRules: {
        dataType: {
          required: true,
          message: "请选择资料类型",
          trigger: "blur",
        },
        dataRemark: {
          required: true,
          message: "请输入资料说明",
          trigger: "blur",
        },
        dataProvideTime: {
          required: true,
          message: "请选择资料提供时限要求",
          trigger: ["blur", "change"],
        },
        queryTime: {
          required: true,
          message: "请选择查询时段要求",
          trigger: ["blur", "change"],
        },
        year: {
          required: true,
          message: "请选择巡察年份",
          trigger: ["blur", "change"],
        },
        batchId: {
          required: true,
          message: "请选择巡察批次",
          trigger: ["blur", "change"],
        },
        taskList: {
          required: true,
          message: "请选择关联巡察任务",
          trigger: ["blur", "change"],
        },
        deptList: {
          required: true,
          message: "请选择提供部门",
          trigger: ["blur", "change"],
        },
      },
      inventoryType: [],
      selectPiciData: [],
      XunchaTaskInfoList: [],
      deptList: [],
      inventoryList: [],
      issueList: [],
      remark: "", //批量备注
      dialogRemark: false,
      dialogRemarkType: 1, //资料清单->1,下发->2
      aheadProcessStatus: [], //待办状态字段
    };
  },
  methods: {
    aheadProcessStatusFormat(row, column) {
      return this.selectDictLabel(this.aheadProcessStatus, row.processStatus);
    },
    radioChange(e) {
      if (e == 2) {
        this.selectInventory({});
      }
    },
    addInventoryList() {
      //判断主责部门资料
      if (this.form.dataType == 1) {
        if (!this.form.deptList[0]) {
          this.msgError("请选择提供部门");
          return;
        }
        let obj = JSON.parse(JSON.stringify(this.form.deptList[0]));
        this.inventoryList.push({
          ...obj,
          deptObj: obj,
          joinBaseInventory: "1",
          inventoryName: "",
          remark: "",
        });
        return;
      }

      this.inventoryList.push({
        joinBaseInventory: "1",
        inventoryName: "",
        remark: "",
      });
    },
    //被巡察单位的关联巡察任务修改事件
    taskChange(e) {
      if (!e.length) {
        this.issueList = [];
        return;
      }
      getUserByRoleKey({
        roleKey: "CITY_DBZR",
        companyId: e[e.length - 1].companyId,
      }).then((ubrk) => {
        if (ubrk.code == 200) {
          e[e.length - 1].dealUser = ubrk.data[0];
          ubrk.data.map((i) => {
            i.fullname = i.nickName;
            i.userid = i.userName;
          });
          e[e.length - 1].dealUserList = ubrk.data;

          //去重组装数据
          let obj = {};
          this.issueList = e
            .reduce(function (item, next) {
              obj[next.companyId]
                ? ""
                : (obj[next.companyId] = true && item.push(next));
              return item;
            }, [])
            .map((res) => {
              return {
                companyObj: res,
                dealUserId: "",
                dealUserName: "",
                remark: "",
                companyNames: res.companyName,
                ...res,
              };
            });
        }
      });
    },
    issDeptSelect(row) {
      getUserByPostName({
        deptId: row.deptObj.deptId,
        postName: "部门领导",
      }).then((ubp) => {
        row.dealUserList = ubp.data;
        row.dealUser = row.dealUserList[0];
      });
    },
    issCompanySelect(row) {
      getUserByRoleKey({
        roleKey: "CITY_DBZR",
        companyId: row.companyObj.companyId,
      }).then((res) => {
        res.data.map((i) => {
          i.fullname = i.nickName;
          i.userid = i.userName;
        });
        this.$set(row, "dealUserList", res.data);
        this.$set(row, "dealUser", res.data[0]);
      });
    },
    delInventory(i) {
      this.inventoryList.splice(i, 1);
    },
    //数据同步，防止修改时数据丢失,先不用
    companyFocus() {
      // this.form.taskList = JSON.parse(JSON.stringify(this.issueList));
    },
    //数据同步，防止修改时数据丢失,先不用
    deptFocus() {
      // this.form.deptList = JSON.parse(JSON.stringify(this.issueList));
    },
    deptChange(e) {
      if (!e.length) {
        this.inventoryList = [];
        this.form.deptList = [];
        return;
      }
      getUserByPostName({
        deptId: e[e.length - 1].deptId,
        postName: "部门领导",
      }).then((ubp) => {
        if (ubp.code == 200) {
          e[e.length - 1].dealUser = ubp.data[0];
          e[e.length - 1].dealUserList = ubp.data;

          this.issueList = e.map((res) => {
            return {
              deptObj: res,
              dealUserId: "",
              dealUserName: "",
              remark: "",
              ...res,
            };
          });
          this.selectInventory({
            deptIds: this.issueList
              .map((i) => {
                return i.deptId;
              })
              .join(","),
          });
        }
      });
    },
    //获取基础资料清单
    selectInventory(data) {
      selectBydeptIds(data).then((sbi) => {
        if (sbi.code == 200) {
          let tempArr = sbi.data.map((item) => {
            item.deptObj = {
              deptId: item.deptId,
              deptName: item.deptName,
            };
            if (!item.joinBaseInventory) {
              item.joinBaseInventory = "0";
            }
            return item;
          });
          let arr = JSON.parse(JSON.stringify(this.inventoryList));
          this.inventoryList = arr.filter((i) => !i.id).concat(tempArr);
        }
      });
    },
    changeYear() {
      // 获得批次下拉框数据
      queryPiciByYeay(this.form.year).then((res) => {
        this.selectPiciData = res.data;
      });
    },
    changePici(fun) {
      if (typeof fun != "function") {
        this.form.taskList = [];
        this.issueList = [];
      }
      listXunchaTaskInfo({
        year: this.form.year,
        piciId: this.form.batchId,
        pageSize: 999,
      }).then((response) => {
        this.XunchaTaskInfoList = response.rows || [];
        this.XunchaTaskInfoList.map((i) => {
          i.remark = "";
          return i;
        });
        this.loading = false;
        if (typeof fun == "function") {
          fun();
        }
      });
    },
    getDeptList() {
      getDeptList({ companyName: "省公司" }).then((res) => {
        this.deptList = res.data;
      });
    },
    handleClose(t) {
      this.$emit("handleClose", t);
    },
    //封装暂存提交修改的数据
    filterData() {
      let data = {};
      this.inventoryList.forEach(res => {
        if (res.deptObj && res.deptObj.deptId) { 
          res.deptId = res.deptObj.deptId;
          res.deptName = res.deptObj.deptName;
          res.companyId = res.deptObj.companyId;
          res.companyName = res.deptObj.companyName;
          res.ouOrder = res.deptObj.ouOrder;
        } 
      })
      if (this.form.dataType == 1) {
        this.issueList.forEach((i) => {
          i.dealUserId = i.dealUser.userid;
          i.dealUserName = i.dealUser.fullname;
          i.deptId = i.deptObj.deptId;
          i.deptName = i.deptObj.deptName;
        });
        data = {
          ...this.form,
          deptInventoryList: this.inventoryList,
          taskList: this.form.taskList.length
            ? this.form.taskList
            : this.XunchaTaskInfoList,
          deptList: this.issueList,
        };
      } else {
        this.form.taskList.forEach((i) => {
          i.companyName = i.companyNames;
        });
        this.issueList.forEach((i) => {
          i.dealUserId = i.dealUser.userid;
          i.dealUserName = i.dealUser.fullname;
          i.companyId = i.companyObj.companyId;
          i.companyName = i.companyObj.companyNames;
        });
        data = {
          ...this.form,
          companyInventoryList: this.inventoryList,
          companyList: this.issueList,
        };
      }
      return data;
    },
    //暂存
    temporarySave() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          let data = this.filterData();
          const loading = this.openLoading();
          temporarySave(data).then((res) => {
            if (res.code == 200) {
              this.msgSuccess("提交成功");
              this.handleClose(res.code);
              return;
            }
            loading.close();
            this.msgError(res.msg);
          });
        }
      });
    },
    verifySubmit() {
      if (!this.inventoryList.length) {
        this.msgError("请添加资料清单");
        return false;
      }
      let issueFlag = false;
      let inventoryFlag = false;
      let inventoryMsg = "";
      this.inventoryList.forEach((res) => {
        let result = this.inventoryList.filter(
          (i) =>
            i.deptObj.deptId == res.deptObj.deptId &&
            i.inventoryName == res.inventoryName
        );
        if (result && result.length > 1) {
          inventoryFlag = true;
          inventoryMsg = "同一个部门下资料清单名称不能重复";
        }
        if (res.inventoryName == "" || !res.inventoryName) {
          inventoryFlag = true;
          inventoryMsg = "请填写需求资料清单";
        }
      });
      this.issueList.forEach((res) => {
        if (!res.dealUser || !res.dealUser.userid) {
          issueFlag = true;
        }
      });
      if (inventoryFlag) {
        this.msgError(inventoryMsg);
        return false;
      }
      if (issueFlag) {
        this.msgError("请选择下发处理人");
        return false;
      }
      return true;
    },
    //提交,修改
    submit() {
      if (!this.XunchaTaskInfoList.length) {
        this.msgError("没有关联巡察任务");
        return;
      }
      this.$refs["formRef"].validate(async (valid) => {
        if (valid) {
          let flag = false;
          if (this.$refs["formRef1"]) {
            await this.$refs["formRef1"].validate((valid) => {
              if (valid) {
                flag = true;
              }
            });
          } else {
            flag = true;
          }
          if (flag) {
            if (!this.verifySubmit()) {
              return;
            }
            this.$confirm("确定提交吗", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              const loading = this.openLoading();
              let data = this.filterData();
              if (!this.form.orderNumber) {
                add(data).then((res) => {
                  if (res.code == 200) {
                    this.msgSuccess("提交成功");
                    this.handleClose(res.code);
                  } else {
                    this.msgError(res.msg);
                  }
                  loading.close();
                });
                return;
              }
              edit(data).then((res) => {
                if (res.code == 200) {
                  this.msgSuccess("提交成功");
                  this.handleClose(res.code);
                } else {
                  this.msgError(res.msg);
                }
                loading.close();
              });
            });
          }
        }
      });
    },
    handleOpen() {
      Object.assign(this.$data, this.$options.data());
      getDicts("ahead_process_status").then((res) => {
        this.aheadProcessStatus = res.data || [];
      });
      getDicts("inventory_type").then((res) => {
        this.inventoryType = res.data || [];
      });
      this.getDeptList();
      if (this.obj.id) {
        getInfo(this.obj.id).then((res) => {
          if (res.code == 200) {
            if (res.data.dataType == 1) {
              res.data.deptInventoryList.forEach((item) => {
                item.deptObj = {
                  deptId: item.deptId,
                  deptName: item.deptName,
                };
              });
              res.data.deptList.forEach((item) => {
                item.deptObj = {
                  deptId: item.deptId,
                  deptName: item.deptName,
                };
                item.dealUser = {
                  fullname: item.dealUserName,
                  userid: item.dealUserId,
                };
                item.dealUserList = [item.dealUser];
              });
              this.inventoryList = res.data.deptInventoryList;
              this.issueList = res.data.deptList;
            } else {
              res.data.companyList.forEach((item) => {
                item.companyObj = {
                  companyId: item.companyId,
                  companyNames: item.companyName,
                };
                item.companyNames = item.companyName;
                item.dealUser = {
                  fullname: item.dealUserName,
                  userid: item.dealUserId,
                };
                item.dealUserList = [item.dealUser];
              });
              this.inventoryList = res.data.companyInventoryList;
              this.issueList = res.data.companyList;
            }

            this.form = res.data;
            this.changeYear();
            this.changePici(() => {
              if (!res.data.taskList.length) {
                this.form.taskList = this.XunchaTaskInfoList;
              }
            });
          }
        });
      }
    },
    addRedStar(h, { column }) {
      return [
        h("span", { style: "color: red" }, "*"),
        h("span", " " + column.label),
      ];
    },
    //批量设置备注
    setRemarks() {
      if (this.dialogRemarkType == 1) {
        this.inventoryList.forEach((res) => {
          if (!res.id) {
            this.$set(res, "remark", this.remark);
          }
        });
      } else {
        this.issueList.forEach((res) => {
          res.remark = this.remark;
        });
      }
      this.remark = "";
      this.dialogRemark = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.context-row .operation {
  top: -30px;
  display: flex;
  justify-content: space-between;
  position: unset;
  padding-right: 0;
}
.context-row {
  padding: 20px 30px;
}
/deep/ .el-dialog__body {
  max-height: 500px;
  overflow: auto;
}
/deep/.is-fullscreen > .el-dialog__body {
  max-height: initial;
  padding-bottom: 40px;
}
/deep/.is-fullscreen > .el-dialog__footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 100;
}
</style>
