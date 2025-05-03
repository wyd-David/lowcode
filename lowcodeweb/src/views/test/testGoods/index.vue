<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="商品名称" prop="goodsName">
            <el-input
              v-model="queryParams.goodsName"
              placeholder="请输入商品名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="商品描述" prop="goodsDesc">
            <el-input
              v-model="queryParams.goodsDesc"
              placeholder="请输入商品描述"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="商品价格" prop="price">
            <el-input
              v-model="queryParams.price"
              placeholder="请输入商品价格"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="是否上架：1：上架，0：下架；参照字典 test_goods_sale" prop="isSale">
            <el-select v-model="queryParams.isSale" placeholder="请选择是否上架：1：上架，0：下架；参照字典 test_goods_sale" clearable
                       size="small">
              <el-option
                v-for="dict in isSaleOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete" prop="isDelete">
            <el-select v-model="queryParams.isDelete" placeholder="请选择是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete"
                       clearable size="small">
              <el-option
                v-for="dict in isDeleteOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['test:testGoods:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['test:testGoods:edit']"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['test:testGoods:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-lock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(1)"
            v-hasPermi="['test:testGoods:remove']"
          >禁用
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-unlock"
            size="mini"
            :disabled="multiple"
            @click="disabledBtnClick(0)"
            v-hasPermi="['test:testGoods:remove']"
          >启用
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-upload"
            size="mini"
            @click="openImportTable"
            v-hasPermi="['test:testGoods:importData']"
          >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['test:testGoods:importData']"
          >导出
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="testGoodsList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column label="主键" align="center" prop="id"/>
        <el-table-column label="商品名称" align="center" prop="goodsName"/>
        <el-table-column label="商品描述" align="center" prop="goodsDesc"/>
        <el-table-column label="商品价格" align="center" prop="price"/>
        <el-table-column label="是否上架：1：上架，0：下架；参照字典 test_goods_sale" align="center" prop="isSale"
                         :formatter="isSaleFormat"/>
        <el-table-column label="是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete" align="center" prop="isDelete"
                         :formatter="isDeleteFormat"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
            >查看
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['test:testGoods:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['test:testGoods:remove']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 添加或修改商品功能（测试）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入商品名称"/>
        </el-form-item>
        <el-form-item label="商品描述" prop="goodsDesc">
          <el-input v-model="form.goodsDesc" placeholder="请输入商品描述"/>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入商品价格"/>
        </el-form-item>
        <el-form-item label="是否上架：1：上架，0：下架；参照字典 test_goods_sale" prop="isSale">
          <el-select v-model="form.isSale" placeholder="请选择是否上架：1：上架，0：下架；参照字典 test_goods_sale">
            <el-option
              v-for="dict in isSaleOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete" prop="isDelete">
          <el-select v-model="form.isDelete" placeholder="请选择是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete">
            <el-option
              v-for="dict in isDeleteOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看商品功能（测试）对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="商品名称:" prop="goodsName">
          {{form.goodsName}}
        </el-form-item>
        <el-form-item label="商品描述:" prop="goodsDesc">
          {{form.goodsDesc}}
        </el-form-item>
        <el-form-item label="商品价格:" prop="price">
          {{form.price}}
        </el-form-item>
        <el-form-item label="是否上架：1：上架，0：下架；参照字典 test_goods_sale:" prop="isSale">
          {{form.isSale | test_goods_saleFieldFilter(isSaleOptions)}}
        </el-form-item>
        <el-form-item label="是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete:" prop="isDelete">
          {{form.isDelete | test_goods_deleteFieldFilter(isDeleteOptions)}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 商品功能（测试）导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport"/>
          是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getToken} from "@/utils/auth";
  import {
    listTestGoods,
    getTestGoods,
    delTestGoods,
    addTestGoods,
    updateTestGoods,
    exportTestGoods,
    importTemplate,
    disableEnable
  } from "@/api/test/testGoods";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from '@/utils/index';

  export default {
    name: "TestGoods",
    components: {ExpandCollapse},
    data() {
      return {
        disabled: false,
        // 遮罩层
        loading: true,
        // 是否显示查看弹出层
        isOpenQuery: false,
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
        // 商品功能（测试）表格数据
        testGoodsList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
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
          headers: {Authorization: "Bearer " + getToken()},
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/test/testGoods/importData",
        },
        // 是否上架：1：上架，0：下架；参照字典 test_goods_sale字典
        isSaleOptions: [],
        // 是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete字典
        isDeleteOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          goodsName: null,
          goodsDesc: null,
          price: null,
          isSale: null,
          isDelete: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          goodsName: [
            {required: true, message: "商品名称不能为空", trigger: "blur"}
          ],
          price: [
            {required: true, message: "商品价格不能为空", trigger: "blur"}
          ],
          isSale: [
            {required: true, message: "是否上架：1：上架，0：下架；参照字典 test_goods_sale不能为空", trigger: "change"}
          ],
          isDelete: [
            {required: true, message: "是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete不能为空", trigger: "change"}
          ],
        }
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
      test_goods_saleFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
      test_goods_deleteFieldFilter(val, optionArr) {
        var arr = optionArr.filter(item => item.dictValue === val);
        if (!arr.length) return '';
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts("test_goods_sale").then(response => {
        this.isSaleOptions = response.data || [];
      });
      this.getDicts("test_goods_delete").then(response => {
        this.isDeleteOptions = response.data || [];
      });
    },
    methods: {
      /** 查询商品功能（测试）列表 */
      getList() {
        this.loading = true;
        listTestGoods(this.queryParams).then(response => {
          this.testGoodsList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // 是否上架：1：上架，0：下架；参照字典 test_goods_sale字典翻译
      isSaleFormat(row, column) {
        return this.selectDictLabel(this.isSaleOptions, row.isSale);
      },
      // 是否已经删除，1：已经删除 0：未删除；参照字典 test_goods_delete字典翻译
      isDeleteFormat(row, column) {
        return this.selectDictLabel(this.isDeleteOptions, row.isDelete);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          goodsName: null,
          goodsDesc: null,
          price: null,
          isSale: null,
          isDelete: null,
          createTime: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加商品功能（测试）";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids
        getTestGoods(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改商品功能（测试）";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const id = row.id || this.ids
        getTestGoods(id).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看商品功能（测试）";
        });
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateTestGoods(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addTestGoods(this.form).then(response => {
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
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除商品功能（测试）编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delTestGoods(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || [];
        this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return disableEnable({ids: ids.toString(), disableEnableState: type});
        }).then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有商品功能（测试）数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportTestGoods(queryParams);
        }).then(response => {
          console.log(response)
          // this.download(response.msg);
        }).catch(function () {
        });
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "商品功能（测试）数据导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg);
        });
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
        this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
    }
  };
</script>


<style lang="scss" scoped>
  .app-container {
    background: #F5F7F9;
  }

  .search-folder {
    // margin: 0 -16px;
    // padding: 0 16px;
    padding: 15px;
    position: relative;
    height: 60px;
    overflow-y: hidden;
    display: flex;
    background: #fff;
    border-radius: 2px;

    .ivu-icon {
      // position: absolute;
      left: 2px;
      top: 6px;
      color: #3a89da;
      z-index: 10;
      font-size: 18px;
      cursor: pointer;

      &:hover {
        opacity: 0.7;
      }
    }

    &.folder-open {
      height: auto;
    }

    &-utils {
      display: flex;
      height: 32px;
      align-items: center;
      margin-left: 10px;

      p {
        cursor: pointer;
        width: 32px;
        margin-left: 10px;
        font-size: 14px;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #3a89da;
      }
    }
  }

  .utlsBtn {
    /deep/ .el-button--mini {
      padding: 9px 15px;
    }

    .normalBtn {
      /deep/ .el-button--mini {
        padding: 8px;
      }
    }
  }

  .container {
    padding: 12px;
    background: #fff;
    border-radius: 2px;

    

    .pageBox {
      height: 32px;

      .pagination-container {
        padding: 0 !important;
        margin: 0;
        height: 32px;
        margin-top: 12px;

        /deep/ .number,
        /deep/ .btn-prev,
        /deep/ .btn-next {
          width: 32px;
          height: 32px;
          line-height: 31px;
          background: #fff;
          border-radius: 4px;
          font-size: 14px;
          font-family: HelveticaNeue;
          color: rgba(0, 0, 0, 0.65);
          border: 1px solid rgba(23, 35, 61, 0.15);
          font-weight: normal;
        }

        /deep/ .el-input__inner {
          height: 32px;
          line-height: 32px;
        }
      }

      /deep/ .el-pagination__total {
        line-height: 32px;
      }
    }
  }

  /deep/ .el-form-item__label {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
  }
</style>

