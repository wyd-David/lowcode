<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="goodsName">
        <el-input
          v-model="queryParams.goodsName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="mobileNum">
        <el-input
          v-model="queryParams.mobileNum"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="返现金额" prop="payMoney">
        <el-input
          v-model="queryParams.payMoney"
          placeholder="请输入返现金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="星级数" prop="starNum">
        <el-input
          v-model="queryParams.starNum"
          placeholder="请输入星级数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="礼品方式" prop="payWay">
        <el-select v-model="queryParams.payWay" placeholder="请选择接受礼品方式,1:银行卡，2：微信，3：支付宝" clearable size="small">
          <el-option
            v-for="dict in payWayOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="已返现" prop="isPay">
        <el-select v-model="queryParams.isPay" placeholder="请选择是否已返现,0:否，1：是" clearable size="small">
          <el-option
            v-for="dict in isPayOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>


      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:customer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:customer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:customer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:customer:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
     <!-- <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="序号" align="center" prop="keyId" />
      <el-table-column label="商品名称" align="center" prop="goodsName" />
      <el-table-column label="手机号" align="center" prop="mobileNum" />
      <el-table-column label="礼品方式" align="center" prop="payWay" :formatter="payWayFormat" />
      <el-table-column label="收款码" align="center" prop="payImg" width="200">
        <template slot-scope="scope">
          <span><img v-if="scope.row.payImg" :src="scope.row.payImg" alt="" style="max-width: 150px; max-height: 200px"></span>
        </template>
      </el-table-column>
      <el-table-column label="五星好评" align="center" prop="goodsImg" width="200">
        <template slot-scope="scope">
          <span><img v-if="scope.row.goodsImg" :src="scope.row.goodsImg" alt="" style="max-width: 150px; max-height: 200px"></span>
        </template>
      </el-table-column>
      <el-table-column label="宝贵意见" align="center" prop="customAdvice" />
      <el-table-column label="已返现" align="center" prop="isPay" :formatter="isPayFormat" />
      <el-table-column label="返现时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="返现金额" align="center" prop="payMoney" />
      <el-table-column label="星级数" align="center" prop="starNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:customer:edit']"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改外卖对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="手机号"   prop="mobileNum">
          <el-input v-model="form.mobileNum" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="礼品方式"  prop="payWay">
          <el-select v-model="form.payWay" placeholder="请选择接受礼品方式,1:银行卡，2：微信，3：支付宝">
            <el-option
              v-for="dict in payWayOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="已返现" prop="isPay">
          <el-select v-model="form.isPay" placeholder="请选择是否已返现,0:否，1：是">
            <el-option
              v-for="dict in isPayOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="星级数" prop="starNum">
          <el-input v-model="form.starNum" placeholder="请输入星级数" />
        </el-form-item>
        <el-form-item label="返现金额" prop="payMoney">
          <el-input v-model="form.payMoney" placeholder="请输入返现金额" />
        </el-form-item>
        <el-form-item label="返现时间" prop="payTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择返现时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="宝贵意见" prop="customAdvice">
          <el-input v-model="form.customAdvice" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容" />
        </el-form-item>
        <el-image
          :src="form.payImg"
          style="max-height: 500px"
          :preview-src-list="[form.payImg]">
        </el-image>
        <el-image
          :src="form.goodsImg"
          style="max-height: 500px"
          :preview-src-list="[form.goodsImg]">
        </el-image>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer, exportCustomer } from "@/api/system/customer";

export default {
  name: "Customer",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 外卖表格数据
      customerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 接受礼品方式,1:银行卡，2：微信，3：支付宝字典
      payWayOptions: [],
      // 是否已返现,0:否，1：是字典
      isPayOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsName: null,
        mobileNum: null,
        payWay: null,
        isPay: null,
        payTime: null,
        payMoney: null,
        starNum: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("FXFS").then(response => {
      this.payWayOptions = response.data;
    });
    this.getDicts("36").then(response => {
      this.isPayOptions = response.data;
    });
  },
  methods: {
    /** 查询外卖列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then(response => {
        this.customerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 接受礼品方式,1:银行卡，2：微信，3：支付宝字典翻译
    payWayFormat(row, column) {
      return this.selectDictLabel(this.payWayOptions, row.payWay);
    },
    // 是否已返现,0:否，1：是字典翻译
    isPayFormat(row, column) {
      return this.selectDictLabel(this.isPayOptions, row.isPay);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        keyId: null,
        goodsName: null,
        mobileNum: null,
        payWay: null,
        payImg: null,
        goodsImg: null,
        customAdvice: null,
        isPay: null,
        payTime: null,
        createTime: null,
        payMoney: null,
        telantId: null,
        starNum: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.keyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加外卖";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const keyId = row.keyId || this.ids
      getCustomer(keyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "订单返现";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.keyId != null) {
            updateCustomer(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addCustomer(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const keyIds = row.keyId || this.ids;
      this.$confirm('是否确认删除外卖编号为"' + keyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCustomer(keyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出外卖数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCustomer(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function() {});
    }
  }
};
</script>
