<template>
  <el-select
    style="width: 100%"
    v-model="innerValue"
    filterable
    remote
    value-key="userId"
    reserve-keyword
    :placeholder="placeholder"
    :remote-method="remoteMethod"
    :loading="loading"
    clearable
    @change="onChange"
    @clear="onClear"
  >
    <el-option
      v-for="item in userList"
      :key="item.userId"
      :label="item.username + '-' + item.userId + '-' +item.company+'/'+ item.department"
      :value="item.userId"
    >
    </el-option>
  </el-select>
</template>

<script>
import { selectLiaisonsData } from "@/api/xuncha/XunchaLiaisonsManage";
import { queryUserList } from "@/api/xuncha/common/base";
import { getUserInfo } from "@/api/talent/dept";
export default {
  props: {
    placeholder: {
      type: String,
    },
    value: [String, Number],
    //1,搜索公司里的人,2.搜索部门里的人,默认搜全部
    type: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      innerValue: null,
      userList: [],
    };
  },
  watch: {
    value(val) {
      //将value同步到innerValue
      this.innerValue = val;
    },
  },
  methods: {
    onChange(val) {
      this.$emit("input", val);
      this.$emit("change", val);
    },
    onClear() {
      this.$emit("clear");
      
    },
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          if (this.type) {
            getUserInfo({
              userName: query,
              type: this.type,
            }).then((res) => {
              this.userList = res.data.map((item) => {
                item.company = item.companyName;
                item.dpname = item.departmentName;
                item.username = item.userName;
                return item;
              });
              this.loading = false;
            });
          } else {
            queryUserList(query).then((res) => {
              this.userList = res.data;
              this.loading = false;
            });
          }
        }, 200);
      } else {
        this.userList = [];
      }
    },
  },
};
</script>

<style>
</style>