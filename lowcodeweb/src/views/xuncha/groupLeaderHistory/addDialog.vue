<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="700px"
    :close-on-click-modal="false"
    :before-close="handleClose"
    @open="handleOpen"
    append-to-body="append-to-body"
  >
    <div class="dialog-context">
      <el-form
        ref="queryform"
        :model="form"
        :disabled="id"
        :rules="formRules"
        label-width="120px"
      >
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="姓名：" prop="userName">
              <el-input v-model="form.userNameAll" disabled v-if="id" />
              <el-select
                v-else
                style="width: 100%"
                v-model="form.userNameObj"
                filterable
                remote
                value-key="userId"
                reserve-keyword
                placeholder="请输入用户名"
                :remote-method="remoteMethod"
                :loading="loading"
                clearable
                @change="handleSelect"
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
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="专业：" prop="fieldKey">
              <el-select
                v-model="form.fieldKey"
                placeholder="请选择状态"
                clearable
                size="small"
                @change="handleField"
              >
                <el-option
                  v-for="dict in fieldList"
                  :key="dict.dataCode"
                  :label="dict.dictLabel"
                  :value="dict.dictLabel"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="所属单位：" prop="company">
              <el-input disabled v-model="form.company"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="部门：" prop="dpname">
              <el-input disabled v-model="form.dpname"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="职位：" prop="postName">
              <el-input disabled v-model="form.postName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="职级：" prop="postLevel">
              <el-input disabled v-model="form.postLevel"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div v-if="!id" slot="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="submitForm">提 交</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {
  getGroupLeader,
  addGroupLeader,
  queryUnUserList,
} from "@/api/xuncha/groupLeader/groupLeader";
import { listData } from "@/api/system/dict/data";

export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: true,
    },
    title: {
      type: String,
      default: "新增",
    },
    id: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      selectedPerson: null,
      form: {
        userNameObj: null,
        userName: null,
        userId: null,
        fieldKey: null,
        fieldId: null,
        company: null,
        dpname: null,
        postName: null,
        postLevel: null,
      },
      userList: [],
      fieldList: [],
      formRules: {
        userName: [
          {
            required: true,
            message: "请选择用户",
            trigger: "change",
          },
        ],
        fieldKey: [
          {
            required: true,
            message: "请输入专业",
            trigger: "bulr",
          },
        ],
      },
    };
  },
  created() {
    let param = {
      dictType: "field_type",
      pageNum: 1,
      pageSize: 100,
    };
    listData(param).then((res) => {
      this.fieldList = res.rows;
    });
  },
  methods: {
    handleOpen() {
      if (this.id) {
        getGroupLeader(this.id).then((res) => {
          this.form = res.data;
          this.form.userNameAll = `${res.data.userName}-${res.data.userId}`;
        });
      }
    },
    handleField(val) {
      this.form.fieldId = this.fieldList.find(
        (i) => i.dictLabel == val
      ).dictValue;
      console.log(this.form.fieldId);
    },
    handleClose() {
      this.userList = [];
      this.$refs.queryform.resetFields();
      this.$emit("close");
    },
    handleSelect(value) {
      let params = {
        userName: value.username,
        userId: value.userId,
        fieldKey: value.fieldKey,
        company: value.company,
        dpname: value.department,
        postName: value.postName,
        postLevel: value.postLevel,
      };
      console.log(value);
      this.form = params;
      this.form.userNameObj = value;
      // this.form = value;
    },
    //搜索用户下拉框
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          queryUnUserList(query).then((res) => {
            this.userList = res.data;
          });
        }, 200);
      } else {
        this.userList = [];
      }
    },
    submitForm() {
      this.$refs.queryform.validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            const loading = this.openLoading();
            addGroupLeader(this.form).then((res) => {
              if (res.code == 200) {
                loading.close();
                this.msgSuccess(res.msg);
                this.handleClose();
              }
            });
          });
        }
      });
    },
  },
};
</script>

<style>
</style>