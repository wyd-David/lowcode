<template>
  <span>
    <el-form-item label="常用处理意见:">
      <el-select
        style="width: 100%"
        v-model="option"
        @change="handleChange"
        placeholder="请选择处理意见"
      >
        <el-option
          v-for="item in list"
          :key="item.opinion"
          :label="item.opinion"
          :value="item.opinion"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="处理意见:">
      <el-input @input="handleInput" placeholder="请输入处理意见" v-model="form.option" type="textarea" row="3"></el-input>
      <p>
        <el-button size="small" type="warning" plain @click="add">保存为常用意见</el-button>
      </p>
    </el-form-item>
  </span>
</template>

<script>
import { addOpinion, getOpinions } from "@/api/workflow";
export default {
  data() {
    return {
      option:"",
      form: {
        option: "",
      },
      list: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleChange(e) {
      this.form.option = e;
      this.handleInput();
     },
    getList() {
      getOpinions().then((res) => {
        this.list = res.data;
      });
    },
    add() { 
      addOpinion({ opinion: this.form.option }).then((res) => {
        if (res.code == 200) {
          this.getList();
          this.$message.success("添加成功");
        }
      });
    },
    handleInput() { 
      this.$emit("input", this.form.option);
    }
  },
};
</script>

<style></style>
