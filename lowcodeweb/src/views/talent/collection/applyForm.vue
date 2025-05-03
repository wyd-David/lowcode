<template>
  <div>
    <div v-if="type == '1'">
      <div class="dialog-title">
        <span class="line"></span><span>人才收集信息</span>
      </div>
      <div class="context-row" style="margin-bottom: 0;">
        <el-form
          :disabled="disabled"
          :model="formData"
          :rules="rules"
          ref="formRef"
          label-width="150px"
        >
          <el-row :gutter="24" v-if="id">
            <el-col :span="12">
              <el-form-item label="业务单号:">
                <el-input disabled v-model="formData.orderNumber"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="标题:" prop="title">
                <el-input
                  placeholder="请输入标题"
                  v-model="formData.title"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收集类型:" prop="collectionType">
                <el-radio-group
                  @change="handleChange"
                  v-model="formData.collectionType"
                >
                  <el-radio
                    :label="item.dictValue"
                    v-for="item in collectDicts"
                    :key="item.dictValue"
                    >{{ item.dictLabel }}</el-radio
                  >
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="参加工作时间(早于):" prop="joinWorkDate">
                <el-date-picker
                  :picker-options="pickerOptions"
                  style="width: 100%"
                  v-model="formData.joinWorkDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="入党时间(早于):" prop="joinPartDate">
                <el-date-picker
                  :picker-options="pickerOptions"
                  style="width: 100%"
                  v-model="formData.joinPartDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="计划巡察时段:" prop="datetime">
                <div v-if="!disabled">
                  <el-date-picker
                    style="width: 100%"
                    v-model="formData.datetime"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                  >
                  </el-date-picker>
                </div>
                <div style="width: 100%; display: flex" v-else>
                  <el-input
                    style="width: 49%"
                    :value="formData.startInspectTime"
                  ></el-input>
                  <span style="margin: 0px 2px">-</span>
                  <el-input
                    style="width: 49%"
                    :value="formData.endInspectTime"
                  ></el-input>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收集截止时间:" prop="collectionEndDate">
                <el-date-picker
                  :picker-options="pickerOptions1"
                  style="width: 100%"
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
        <div class="operation">
          <el-button
            :disabled="disabled"
            type="primary"
            @click="addItem"
            size="small"
            style="margin-right: 10px"
            >添加</el-button
          >
          <el-button
            :disabled="disabled"
            type="primary"
            @click="upload.open = true"
            size="small"
            style="margin-right: 10px"
            >导入</el-button
          >
          <el-button
            :disabled="disabled"
            type="primary"
            size="small"
            @click="dialogRemark = true"
            >设置说明</el-button
          >
        </div>
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
          <el-table-column label="单位" min-width="150" align="center">
            <template slot-scope="scope">
              <el-select
                :disabled="disabled"
                value-key="companyId"
                @change="(res) => handleCompanySelect(res, scope.row)"
                v-model="scope.row.company"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in companyList"
                  :key="item.companyId"
                  :label="item.companyName"
                  :value="item"
                >
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column align="center" width="150" label="接收人">
            <template slot-scope="scope">
              <el-select
                v-if="!disabled"
                v-model="scope.row.receiveUserObj"
                value-key="loginId"
              >
                <el-option
                  v-for="item in scope.row.userList"
                  :key="item.loginId"
                  :label="item.username"
                  :value="item"
                ></el-option>
              </el-select>
              <el-input
                v-else
                :value="scope.row.receiveUserName"
                disabled
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column align="center" width="150" label="说明">
            <template slot-scope="scope">
              <el-input
                :disabled="disabled"
                v-model="scope.row.remark"
                :placeholder="!disabled ? '请输入' : ''"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop="expertTotals"
            width="150px"
            align="center"
            label="推荐专家总人数"
          >
          </el-table-column>
          <el-table-column
            align="center"
            :label="item.dictLabel"
            v-for="item in prefecturalDicts"
            :key="item.dictValue"
            width="100"
            :render-header="renderHeader"
          >
            <template slot-scope="scope">
              <el-input
                :disabled="disabled"
                @input.native="tableInput(scope.row)"
                type="number"
                v-model="filterObj(item.dictValue, scope.row).personNumber"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column
            width="200"
            align="center"
            label="建议继续推荐人员名单"
          >
            <template slot-scope="scope">
              <el-input
                :placeholder="disabled ? '' : '例如：XXX、XXX'"
                style="width: 180px"
                :disabled="disabled"
                v-model="scope.row.memberList"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column
            v-if="!disabled"
            label="操作"
            fixed="right"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="deleteItem(scope.$index)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-table :data="provinceTableData" v-if="activeName == '省公司'">
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column label="单位" width="150" align="center">
            <template slot-scope="scope">
              <el-select
                :disabled="disabled"
                value-key="deptId"
                @change="(res) => handleCompanySelect(res, scope.row)"
                v-model="scope.row.dept"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in companyListPer"
                  :key="item.deptId"
                  :label="item.deptName"
                  :value="item"
                >
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column align="center" width="150" label="接收人">
            <template slot-scope="scope">
              <el-select
                v-if="!disabled"
                v-model="scope.row.receiveUserObj"
                value-key="loginId"
              >
                <el-option
                  v-for="item in scope.row.userList"
                  :key="item.loginId"
                  :label="item.username"
                  :value="item"
                ></el-option>
              </el-select>
              <el-input
                v-else
                :value="scope.row.receiveUserName"
                disabled
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column align="center" width="150" label="说明">
            <template slot-scope="scope">
              <el-input
                :disabled="disabled"
                v-model="scope.row.remark"
                :placeholder="!disabled ? '请输入' : ''"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop="expertTotals"
            align="center"
            label="推荐专家总人数"
          >
          </el-table-column>
          <el-table-column
            align="center"
            :label="item.dictLabel"
            v-for="item in provinceDicts"
            :key="item.dictValue"
          >
            <template slot-scope="scope">
              <el-input
                :disabled="disabled"
                @input.native="tableInput(scope.row)"
                type="number"
                v-model="filterObj(item.dictValue, scope.row).personNumber"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column
            v-if="!disabled"
            label="操作"
            fixed="right"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="deleteItem(scope.$index)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div v-if="type == '2'">
      <div class="dialog-title">
        <span class="line"></span><span>收集信息</span>
      </div>
      <div class="context-row">
        <el-form label-width="100px" disabled>
          <el-form-item label="业务单号:">
            <el-input :value="formData.orderNumber"></el-input>
          </el-form-item>
          <el-form-item label="标题:">
            <el-input :value="formData.title"></el-input>
          </el-form-item>
        </el-form>
        <el-tabs v-model="activeName" @tab-click="handleTabClick">
          <el-tab-pane
            :label="item.dictLabel"
            :name="item.dictLabel"
            v-for="item in tabDicts"
            :key="item.dictLabel"
          ></el-tab-pane>
        </el-tabs>
        <el-table v-if="activeName == '地市公司'" :data="prefecturalTableData">
          <el-table-column label="单位" prop="companyName"></el-table-column>
          <el-table-column label="接收人">
            <template slot-scope="scope">
              <el-input :value="scope.row.receiveUserName" disabled></el-input>
            </template>
          </el-table-column>
          <el-table-column label="说明">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" disabled></el-input>
            </template>
          </el-table-column>
        </el-table>

        <el-table v-else :data="provinceTableData">
          <el-table-column label="单位" prop="deptName"></el-table-column>
          <el-table-column label="接收人">
            <template slot-scope="scope">
              <el-input :value="scope.row.receiveUserName" disabled></el-input>
            </template>
          </el-table-column>
          <el-table-column label="说明">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" disabled></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <all-flow-step
      v-if="!isTodo && id && formData.processStatus != 1"
      :allFlowStep="process.allFlowStep"
    ></all-flow-step>
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

    <!-- 在库管理_人才库 导入对话框 -->
    <el-dialog
      :title="upload.title || '导入'"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <!-- <el-checkbox
            v-model="upload.updateSupport"
          />是否更新已经存在的用户数据 -->
        </div>
        <div class="el-upload__tip" style="text-align: center" slot="tip">
          <el-button
            size="mini"
            style="color: red; border-color: red"
            @click="importTemplate"
            >下载模板</el-button
          ><span style="margin-left: 10px"
            >提示：仅允许导入“xls”或“xlsx”格式文件！</span
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getToken } from "@/utils/auth";
import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
import {
  getCollection,
  getNextDealUser,
  getAllCompany,
  importTemplate,
  selectById,
  getDeptList,
  checkTitle,
  queryLiaisonsList,
  getUserByPostName,
  getUserByRoleKey,
} from "@/api/talent/collection";
import { fetchWorkFlowInfo } from "@/utils/workflow";
import ApplyFooter from "@/components/ApplyFooter";
import { getDicts } from "@/api/system/dict/data";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      default: "0",
    },
    //1提交,2下发,3.待办页
    type: {
      type: String,
      default: "1",
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    isTodo: {
      type: Boolean,
      default: false,
    },
  },
  created() {
    if (this.dialogVisible || this.isTodo) {
      this.handleOpen();
    }
  },
  components: { ApplyFooter, AllFlowStep },
  watch: {
    id(val) {
      this.getDetail();
    },
    disabled(val) {
      if (!val) {
        this.tabDicts = this.tabDictsCopy;
        this.activeName = this.tabDicts[0].dictLabel;
        if (!this.getSelectFlag) {
          this.getSelectById();
          this.getSelectFlag = true;
        }
      } else {
        let tempTabDicts = [];
        if (this.prefecturalTableData.length) {
          tempTabDicts.push(
            this.tabDicts.find((res) => res.dictLabel == "地市公司")
          );
        }
        if (this.provinceTableData.length) {
          tempTabDicts.push(
            this.tabDicts.find((res) => res.dictLabel == "省公司")
          );
        }
        this.tabDicts = tempTabDicts;
        this.activeName = tempTabDicts[0].dictLabel;
      }
    },
  },
  data() {
    return {
      //判断是否调用过selectByid接口
      getSelectFlag: false,
      dialogRemark: false,
      remark: "",
      pickerOptions: {
        //控制时间范围
        disabledDate(time) {
          return time.getTime() > Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      pickerOptions1: {
        //控制时间范围
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      allFlowStep: [],
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url:
          CONFIG.url +
          process.env.VUE_APP_BASE_API +
          "/talent/collection/importTemplateFile",
      },
      title: "发起人才收集",
      companyList: [],
      companyListPer: [],
      nextUsers: [],
      options: [
        {
          value: "",
          label: "请选择",
        },
        {
          value: "1",
          label: "哈哈",
        },
      ],
      prefecturalTableData: [], //地市公司表格数据
      provinceTableData: [], //省公司表格数据
      activeName: "地市公司",
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
      rules: {
        title: [
          {
            required: true,
            message: "请输入标题",
            trigger: "blur",
          },
        ],
        collectionType: [
          {
            required: true,
            message: "请选择收集类型",
            trigger: "blur",
          },
        ],
        joinWorkDate: [
          {
            required: true,
            message: "请选择入职时间",
            trigger: "blur",
          },
        ],
        joinPartDate: [
          {
            required: true,
            message: "请选择入党时间",
            trigger: "blur",
          },
        ],
        datetime: [
          {
            required: false,
            message: "请选择计划巡察时段",
            trigger: "blur",
          },
        ],
        collectionEndDate: [
          {
            required: true,
            message: "请选择收集截止时间",
            trigger: "blur",
          },
        ],
        nextUserId: [
          {
            required: true,
            message: "请选择下一步处理人",
            trigger: "blur",
          },
        ],
      },
      prefecturalDicts: [], //地市表头字典
      provinceDicts: [], //省表头字典
      defaultPD: ["50", "51"], //默认省公司表头字典
      tabDicts: [
        { dictLabel: "地市公司", dictValue: "1" },
        { dictLabel: "省公司", dictValue: "2" },
      ], //tab字典
      tabDictsCopy: [], //tab字典备份
      collectDicts: [], //收集字典
      process: {}, //流程信息
    };
  },
  methods: {
    //修改时通过selectByid接口获取接收人信息
    getSelectById() {
      selectById(this.id).then((st) => {
        st.data.companyList.forEach((fe) => {
          let tempObj = this.prefecturalTableData.find(
            (item) => item.companyId == fe.companyId
          );
          tempObj.userList = fe.userList;
        });
        st.data.deptList.forEach((fe) => {
          let tempObj = this.provinceTableData.find(
            (item) => item.deptId == fe.deptId
          );
          tempObj.userList = fe.userList;
        });
      });
    },
    handleChange() {
      let label = this.collectDicts.find(
        (res) => res.dictValue == this.formData.collectionType
      );
      if (label) {
        this.formData.title =
          new Date().getFullYear() + "年" + label.dictLabel + "人才收集任务";
      }
    },
    setRemarks() {
      if (!this.remark) {
        this.msgError("请输入说明!");
        return;
      }
      if (this.activeName == "地市公司") {
        this.prefecturalTableData.forEach((item) => {
          if (!item.remark) {
            item.remark = this.remark;
          }
        });
      } else {
        this.provinceTableData.forEach((item) => {
          if (!item.remark) {
            item.remark = this.remark;
          }
        });
      }
      this.dialogRemark = false;
    },
    filterObj(item, row) {
      return row.fieldList.filter((res) => res.fieldType == item)[0] || {};
    },
    renderHeader(h, { column }) {
      return h("div", { style: { display: "flex" } }, [
        h(
          "el-tooltip",
          {
            props: {
              effect: "dark",
              content: column.label,
              placement: "top",
            },
          },
          [
            h(
              "span",
              {
                style: {
                  display: "inline-block",
                  width: "80px",
                  textOverflow: "ellipsis",
                  overflow: "hidden",
                  whiteSpace: "nowrap",
                },
              },
              column.label
            ),
          ]
        ),
      ]);
    },
    //检查是否允许提交
    async checkSubmit(t) {
      let flag = false;
      let isCompanyRepetition = false;
      let isDeptRepetition = false;

      let isReceiveUser = false;
      this.prefecturalTableData.forEach((res) => {
        res.companyId = res.company.companyId;
        res.companyName = res.company.companyName;
        res.receiveUserId = res.receiveUserObj.loginId;
        res.receiveUserName = res.receiveUserObj.username;
        if (
          this.prefecturalTableData.filter((p) => p.companyId == res.companyId)
            .length > 1
        ) {
          isCompanyRepetition = true;
        }
        if (!res.companyId) {
          flag = true;
        }
      });
      let tempPrefecturalTableData = this.prefecturalTableData.filter(
        (item) => {
          let count = 0;
          item.fieldList.forEach((res) => {
            count += Number(res.personNumber);
          });
          return count;
        }
      );
      this.provinceTableData.forEach((res) => {
        res.companyId = this.companyListPer[0].companyId;
        res.companyName = this.companyListPer[0].companyName;
        res.receiveUserId = res.receiveUserObj.loginId;
        res.receiveUserName = res.receiveUserObj.username;
        res.deptId = res.dept.deptId;
        res.deptName = res.dept.deptName;
        if (
          this.provinceTableData.filter((p) => p.deptId == res.deptId).length >
          1
        ) {
          isDeptRepetition = true;
        }
        if (!res.deptId) {
          flag = true;
        }
      });
      let tempProvinceTableData = this.provinceTableData.filter((item) => {
        let count = 0;
        item.fieldList.forEach((res) => {
          count += Number(res.personNumber);
        });
        return count;
      });
      if (!tempProvinceTableData.length && !tempPrefecturalTableData.length) {
        this.$message.error("请填写要收集的人数");
        return;
      }
      if (flag) {
        this.$message.error("请选择公司");
        return null;
      }
      if (isCompanyRepetition) {
        this.$message.error("公司重复");
        return null;
      }
      if (isDeptRepetition) {
        this.$message.error("部门重复");
        return null;
      }
      tempPrefecturalTableData.forEach((item) => {
        if (!item.receiveUserId) {
          isReceiveUser = true;
        }
      });
      tempProvinceTableData.forEach((item) => {
        if (!item.receiveUserId) {
          isReceiveUser = true;
        }
      });
      if (isReceiveUser) {
        this.$message.error("请选择接收人");
        return null;
      }
      let tempObj = {
        ...this.formData,
        processStatus: t,
        companyList: tempPrefecturalTableData,
        deptList: tempProvinceTableData,
        variables: JSON.stringify({
          nextDealUser: this.formData.nextUserId,
          option: this.formData.remark,
        }),
      };
      tempObj.startInspectTime = tempObj.datetime[0];
      tempObj.endInspectTime = tempObj.datetime[1];
      delete tempObj.datetime;

      //检查标题是否存在
      let isRepetition = false;
      if (t) {
        await checkTitle({
          title: this.formData.title,
          id: this.formData.id,
        }).then((ct) => {
          if (ct.data) {
            this.msgError("标题重复了，请重新输入!");
            isRepetition = true;
          }
        });
      }
      if (isRepetition) {
        return null;
      }

      return tempObj;
    },
    async handleCompanySelect(e, row) {
      row.companyId = e.companyId;
      row.companyName = e.companyName;
      let id = this.activeName == "地市公司" ? e.companyId : e.deptId;
      let t = this.activeName == "地市公司" ? 1 : 2;
      let tempArr = await this.getQueryLiaisonsList(id, t);
      row.receiveUserObj = {};
      row.userList = tempArr;
    },
    handleOpen() {
      //表头字典
      getDicts("field_type").then((dict) => {
        //地市公司
        this.prefecturalDicts = dict.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) == -1
        );
        //省公司
        this.provinceDicts = dict.data.filter(
          (item) => this.defaultPD.indexOf(item.dictValue) != -1
        );
        getAllCompany().then(async (res) => {
          this.companyList = res.data;
          if (this.id) return;
          let tempArr = await this.getQueryLiaisonsList(
            this.companyList[0].companyId,
            1
          );
          console.log(tempArr);
          this.prefecturalTableData.push({
            companyName: "省公司",
            expertTotals: 0,
            receiveUserObj: {},
            companyType: "1",
            remark: "",
            company: {
              companyId: this.companyList[0].companyId,
              companyName: this.companyList[0].companyName,
            },
            userList: tempArr,
            fieldList: [
              ...this.prefecturalDicts.map((item) => ({
                fieldType: item.dictValue,
                personNumber: 0,
              })),
            ],
          });
        });
        getDeptList({ companyName: "省公司" }).then(async (res) => {
          this.companyListPer = res.data;
          if (this.id) return;
          let tempArr = await this.getQueryLiaisonsList(
            this.companyListPer[0].deptId,
            2
          );
          this.provinceTableData.push({
            companyName: "省公司",
            expertTotals: 0,
            remark: "",
            companyType: "2",
            receiveUserObj: {},
            dept: {
              deptId: this.companyListPer[0].deptId,
              deptName: this.companyListPer[0].deptName,
            },
            userList: tempArr,
            fieldList: [
              ...this.provinceDicts.map((item) => ({
                fieldType: item.dictValue,
                personNumber: 0,
              })),
            ],
          });
        });
      });
      //收集字典
      getDicts("collection_type").then((res) => {
        this.collectDicts = res.data;
        if (!this.id) {
          this.title = "发起人才收集";
          this.addInit();
        } else if (this.id && !this.disabled) {
          this.title = "修改人才收集";
          this.detailInit();
        } else {
          this.title = "查看";
          this.detailInit();
        }
        if (!this.id) {
          this.formData.collectionType = this.collectDicts[0].dictValue;
        }
      });
      //tab字典
      getDicts("company_type").then((res) => {
        this.tabDicts = res.data;
        this.tabDictsCopy = res.data;
        this.activeName = res.data[0].dictLabel;
      });

      if (this.$refs["formRef"]) this.$refs["formRef"].resetFields();
    },
    detailInit() {
      this.getDetail();
    },
    //获取联络员
    async getQueryLiaisonsList(id, t) {
      let tempArr = [];
      // await queryLiaisonsList({
      //   companyId: t == 1 ? id : null,
      //   deptId: t == 2 ? id : null,
      // }).then((res) => {
      //   tempArr = res.data;
      // });
      if (t == 1) {
        await getUserByRoleKey({
          roleKey: "CITY_DBZR",
          companyId: id,
        }).then((res) => {
          tempArr = res.data;
        });
        tempArr.forEach((res) => {
          res.username = res.nickName;
          res.loginId = res.userName;
        });
      } else {
        await getUserByPostName({
          deptId: id,
          postName: "部门领导",
        }).then((res) => {
          tempArr = res.data;
        });
        tempArr.forEach((res) => {
          res.username = res.fullname;
          res.loginId = res.userid;
        });
      }
      return tempArr;
    },
    //获取详情
    async getDetail() {
      if (this.type == "2") {
        await selectById(this.id).then((res) => {
          if (res.code == 200) {
            this.formData = {
              id: res.data.id,
              title: res.data.title,
              createdBy: res.data.createdBy,
              orderNumber: res.data.orderNumber,
              collectionType: res.data.collectionType,
              joinWorkDate: res.data.joinWorkDate,
              joinPartDate: res.data.joinPartDate,
              startInspectTime: res.data.startInspectTime,
              endInspectTime: res.data.endInspectTime,
              datetime: [res.data.startInspectTime, res.data.endInspectTime],
              collectionEndDate: res.data.collectionEndDate,
              processStatus: res.data.processStatus,
            };
            this.getAllFlowStep();
            res.data.companyList.forEach((item) => {
              item.receiveUserObj = {};
              item.collectionType = "1";
              item.company = {
                companyId: item.companyId,
                companyName: item.companyName,
              };
            });
            res.data.deptList.forEach((item) => {
              item.receiveUserObj = {};
              item.collectionType = "2";
              item.dept = {
                deptId: item.deptId,
                deptName: item.deptName,
              };
            });
            this.prefecturalTableData = res.data.companyList;
            this.provinceTableData = res.data.deptList;
          }
        });
      } else {
        await getCollection(this.id).then((res) => {
          if (res.code == 200) {
            this.formData = {
              id: res.data.id,
              title: res.data.title,
              createdBy: res.data.createdBy,
              orderNumber: res.data.orderNumber,
              collectionType: res.data.collectionType,
              joinWorkDate: res.data.joinWorkDate,
              joinPartDate: res.data.joinPartDate,
              startInspectTime: res.data.startInspectTime,
              endInspectTime: res.data.endInspectTime,
              datetime: [res.data.startInspectTime, res.data.endInspectTime],
              collectionEndDate: res.data.collectionEndDate,
              processStatus: res.data.processStatus,
            };
            this.getAllFlowStep();
            res.data.companyList.forEach((item) => {
              item.company = {
                companyId: item.companyId,
                companyName: item.companyName,
              };
              item.receiveUserObj = {
                loginId: item.receiveUserId,
                username: item.receiveUserName,
              };
            });
            res.data.deptList.forEach((item) => {
              item.dept = {
                deptId: item.deptId,
                deptName: item.deptName,
                companyName: "省公司",
              };
              item.receiveUserObj = {
                loginId: item.receiveUserId,
                username: item.receiveUserName,
              };
            });
            this.prefecturalTableData = res.data.companyList;
            this.provinceTableData = res.data.deptList;

            if (!this.disabled && !this.getSelectFlag) {
              this.getSelectById();
              this.getSelectFlag = true;
            }
          }
        });
      }
      if (this.disabled) {
        let tempTabDicts = [];
        if (this.prefecturalTableData.length) {
          tempTabDicts.push(
            this.tabDicts.find((res) => res.dictLabel == "地市公司")
          );
        }
        if (this.provinceTableData.length) {
          tempTabDicts.push(
            this.tabDicts.find((res) => res.dictLabel == "省公司")
          );
        }
        this.tabDicts = tempTabDicts;
        this.activeName = tempTabDicts[0].dictLabel;
      }
    },
    getAllFlowStep() {
      if (!this.isTodo && this.id) {
        fetchWorkFlowInfo(
          this.formData.orderNumber,
          null,
          (res) => {
            this.process = res;
          },
          1
        );
      }
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
      this.formData = {
        title: `${new Date().getFullYear()}${
          this.collectDicts[0].dictLabel
        }人才收集任务`,
        collectionType: "",
        joinWorkDate: "",
        joinPartDate: "",
        startInspectTime: "",
        endInspectTime: "",
        datetime: [],
        collectionEndDate: "",
      };
      this.prefecturalTableData = [];
      this.provinceTableData = [];
      getNextDealUser({
        processKey: "talent_collection:2:2132524",
        userId: "1",
        stepName: "巡察办室经理审核",
      }).then((res) => {
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
    addItem() {
      if (this.activeName == "地市公司") {
        this.prefecturalTableData.push({
          companyName: "",
          expertTotals: 0,
          companyType: "1",
          receiveUserObj: {
            loginId: "",
            username: "",
          },
          userList: [],
          remark: "",
          company: {
            companyId: "",
            companyName: "",
          },
          fieldList: [
            ...this.prefecturalDicts.map((item) => ({
              fieldType: item.dictValue,
              personNumber: 0,
            })),
          ],
        });
      } else {
        this.provinceTableData.push({
          companyName: "",
          expertTotals: 0,
          companyType: "2",
          receiveUserObj: {
            loginId: "",
            username: "",
          },
          userList: [],
          remark: "",
          dept: {
            deptId: "",
            deptName: "",
          },
          fieldList: [
            ...this.provinceDicts.map((item) => ({
              fieldType: item.dictValue,
              personNumber: 0,
            })),
          ],
        });
      }
    },
    //删除行
    deleteItem(i) {
      if (this.activeName == "地市公司") {
        this.prefecturalTableData.splice(i, 1);
      } else {
        this.provinceTableData.splice(i, 1);
      }
    },
    //监听表格输入框计算总人数
    tableInput(e) {
      let count = 0;
      e.fieldList.forEach((res) => {
        count += Number(res.personNumber) || 0;
      });
      e.expertTotals = count;
    },
    //下一步公用组件关闭回调
    close(e) {
      this.$emit("close", e);
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
      if (response.data.deptList) {
        response.data.deptList.forEach((item) => {
          item.dept = {
            deptId: item.deptId,
            deptName: item.deptName,
            companyName: "省公司",
          };
          //expertTotals计算
          let count = 0;
          item.fieldList.forEach((res) => {
            count += Number(res.personNumber) || 0;
          });
          item.expertTotals = count;
        });
        let oldCount = 0;
        this.provinceTableData.forEach((res) => {
          oldCount += Number(res.expertTotals) || 0;
        });
        if (oldCount) {
          this.provinceTableData = this.provinceTableData.concat(
            JSON.parse(JSON.stringify(response.data.deptList))
          );
        } else {
          this.provinceTableData = JSON.parse(
            JSON.stringify(response.data.deptList)
          );
        }
      } else {
        this.provinceTableData = [];
      }
      if (response.data.companyList) {
        response.data.companyList.forEach((item) => {
          item.company = {
            companyId: item.companyId,
            companyName: item.companyName,
          };
          //expertTotals计算
          let count = 0;
          item.fieldList.forEach((res) => {
            count += Number(res.personNumber) || 0;
          });
          item.expertTotals = count;
        });
        let oldCount = 0;
        this.prefecturalTableData.forEach((res) => {
          oldCount += Number(res.expertTotals) || 0;
        });
        if (oldCount) {
          this.prefecturalTableData = this.prefecturalTableData.concat(
            JSON.parse(JSON.stringify(response.data.companyList))
          );
        } else {
          this.prefecturalTableData = JSON.parse(
            JSON.stringify(response.data.companyList)
          );
        }
      } else {
        this.prefecturalTableData = [];
      }
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

/deep/ .el-form-item__error {
  width: 280px;
}
.context-row .operation {
  padding-right: 40px;
}
</style>