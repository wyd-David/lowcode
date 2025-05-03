<template>
  <div>
    <!-- 预分组——设置规则 对话框 -->
    <el-dialog
      title="设置分组规则"
      :visible.sync="dialogVisible"
      width="800px"
      @open="handleOpen"
      :close-on-click-modal="false"
      :before-close="handleClose"
      append-to-body="append-to-body"
    >
      <div class="context-row">
        <el-table
          ref="tableList"
          :data="tableList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column :selectable="selected" type="selection" width="55">
          </el-table-column>
          <el-table-column label="组内标签/职责" prop="labelName">
            <template slot-scope="scope">
              <el-select
                v-if="scope.row.isAnother"
                v-model="scope.row.labelId"
                placeholder="请选择"
                clearable
                @change="
                  (val) => handleLabelChang(val, scope.row, scope.$index)
                "
              >
                <el-option
                  v-for="item in labelsList"
                  :key="item.labelKey"
                  :label="item.labelName"
                  :value="item.labelKey"
                />
              </el-select>
              <span v-else>{{ scope.row.labelName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="人数" align="center" prop="transferTotal">
            <template slot-scope="scope">
              <el-input
                oninput="value=value.replace(/[^\d]/g,'')"
                style="width: 80px"
                v-model="scope.row.transferTotal"
              />
            </template>
          </el-table-column>
          <el-table-column label="专家领域" align="center" prop="fieldKey">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.fieldKey"
                placeholder="请选择"
                clearable
                @change="handleChang"
              >
                <el-option
                  v-for="item in scope.row.list"
                  :key="scope.row.labelId + item.fieldKey"
                  :label="item.fieldKey"
                  :value="item.fieldKey"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                @click="onAddMember(scope)"
                v-show="scope.$index == tableList.length - 1"
                size="mini"
                type="text"
                >继续添加
              </el-button>
              <el-button
                @click="onDeleteMember(scope.$index)"
                v-show="scope.row.labelName == '其他成员'"
                size="mini"
                type="text"
                >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="react">重 置</el-button>
        <el-button @click="submitForm" type="primary">设 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getXunchaTransferPersonnelLabel,
  getXunchaTransferPersonnelField,
} from "@/api/xuncha/transferManage/transferPersonnelInfo.js";
import { getTransferGroupLeaderFieldKey } from "@/api/xuncha/groupLeader/groupLeader";
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: true,
    },
    instanceId: {
      type: String,
      default: "",
    },
    groupId: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      tableList: [
        {
          labelName: "副组长",
          labelId: "1",
          transferTotal: "",
          fieldKey: "",
          list: [],
          isSelected: false,
        },
        {
          labelName: "主笔人",
          labelId: "2",
          transferTotal: "",
          list: [],
          fieldKey: "",
          isSelected: false,
        },
        {
          labelName: "联络员",
          labelId: "3",
          transferTotal: "",
          list: [],
          fieldKey: "",
          isSelected: false,
        },
        {
          labelName: "其他成员",
          labelId: "",
          transferTotal: "",
          list: [],
          isAnother: true,
          isSelected: false,
        },
      ],
      labelsList: [],
      multipleSelection: [],
    };
  },

  methods: {
    handleClose() {
      // let arr = JSON.parse(JSON.stringify(this.multipleSelection));
      this.$emit("close");
      this.react();
    },
    selected(row, index) {
      if (row.labelId) {
        return true; //可勾选
      } else {
        return false; //不可勾选
      }
    },
    handleOpen() {
      this.labelDictDataQuery();
      // 获取标签
      getXunchaTransferPersonnelLabel({
        instanceId: this.instanceId,
        groupId: this.groupId,
      }).then((res) => {
        this.labelsList = res.data;
        console.log(this.labelsList);
      });
    },
    handleLabelChang(val, row, index) {
      if (val) {
        let params = {
          instanceId: this.instanceId,
          labelKey: val,
        };
        row.labelName = this.labelsList.find(
          (i) => i.labelKey == val
        ).labelName;
        getXunchaTransferPersonnelField(params).then((res) => {
          row.list = res.data;
          this.$set(this.tableList, index, {
            ...this.tableList[index],
            fieldKey: "",
          });

          this.$forceUpdate();
        });
      }
      // this.labelsList.forEach((i) => {
      //   if (i.labelId == val) {
      //     row.labelName = i.labelName;
      //   }
      // });
    },
    handleChang(val) {
      console.log(val);
    },
    // 根据label查询专家类型
    labelDictDataQuery() {
      this.tableList.forEach((item) => {
        if (item.labelId) {
          let params = {
            instanceId: this.instanceId,
            labelKey: item.labelId,
          };
          if (item.labelName == "副组长") {
            getTransferGroupLeaderFieldKey({
              instanceId: this.instanceId,
            }).then((res) => {
              item.list = res.data.filter((i) => i);
            });
          } else {
            getXunchaTransferPersonnelField(params).then((res) => {
              item.list = res.data.filter((i) => i);
            });
          }
        }
      });
    },

    onAddMember(scope) {
      this.tableList.push({
        labelName: "其他成员",
        transferTotal: "",
        list: [],
        isAnother: "true",
        fieldKey: "",
      });
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    // 规则回显
    mySelectResign(arr) {
      // this.multipleSelection = arr;
      console.log(arr);
      this.tableList.splice(0, 4); // 删除默认规则
      this.tableList.push(...arr);
      this.tableList.forEach((i) => {
        this.$refs.tableList.toggleRowSelection(i, true); //列表选中
      });

      // console.log(arr);
    },
    onDeleteMember(index) {
      this.tableList.splice(index, 1);
    },
    submitForm() {
      let status = true;
      this.multipleSelection.forEach((i) => {
        if (!i.labelId || !i.fieldKey || !i.transferTotal) {
          status = false;
          return;
        }
      });
      if (status) {
        let arr = JSON.parse(JSON.stringify(this.multipleSelection));
        console.log(this.multipleSelection);
        this.$emit("submit", arr);
        this.react();
      } else {
        this.msgError("请完善勾选中的规则");
      }
    },
    // 初始化
    react() {
      this.multipleSelection = [];
      this.$data.tableList = this.$options.data().tableList;
    },
  },
};
</script>

<style lang="scss" scoped>
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
</style>