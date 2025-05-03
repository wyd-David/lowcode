<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <expand-collapse v-show="showSearch">
      <div slot="left">
        <el-form ref="queryForm" :inline="true" label-width="120px">
          <el-form-item label="目录名称">
            <el-input v-model="queryParams.contentName" placeholder="请输入目录名称" clearable size="small"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>

    <div class="list-title">
      <span class="line"></span><span>制度库目录维护</span>
    </div>
    <!-- 列表 -->
    <div class="list">
      <div class="operation">
        <el-button type="primary" :disabled="multipleSelection.length !== 1" @click="openForm" icon="el-icon-plus"
                   size="mini">设置可见范围
        </el-button>
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['xunchaReportContent:xunchaReportContent:add']">
          新增
        </el-button>
        <!-- <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['xunchaReportContent:xunchaReportContent:edit']">修改</el-button> -->
        <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['xunchaReportContent:xunchaReportContent:remove']">
          删除
        </el-button>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </div>

      <el-table :header-cell-style="{ 'text-align': 'center' }" v-loading="loading" :data="tableData"
                @selection-change="handleSelectionChange" row-key="contentId"
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column type="index" width="50" label="序号" align="center"></el-table-column>
        <el-table-column label="目录名称" align="left" prop="contentName"/>
        <el-table-column label="目录全名称" prop="fullName"/>
        <el-table-column label="目录类型" align="center" prop="fullName" :formatter="catalogueTypeFormat"/>
        <!--<el-table-column label="可见范围" align="center" prop="visibleScope" width="160" :formatter="archivesVisibleScopeFormat"/>-->
        <el-table-column label="可见范围" align="center" prop="companyNameStr" width="160"/>
        <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
          <template slot-scope="scope">
            <span>{{parseTime(scope.row.createdTime, "{y}-{m}-{d} {h}:{m}:{s}")}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
                       v-hasPermi="['xunchaReportContent:xunchaReportContent:edit']">修改
            </el-button>
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
                       v-hasPermi="['xunchaReportContent:xunchaReportContent:remove']">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      /> -->
    </div>
    <!-- 新增、修改、查看对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="上级目录：" prop="parentId">
          <treeselect :disabled="disabled" v-model="form.parentId" :options="parentSelect" :normalizer="normalizer"
                      :show-count="true" placeholder="选择上级菜单" @input="parentIdInput" ref="tree"/>
        </el-form-item>
        <el-form-item label="目录名称：" prop="contentName">
          <el-input v-model="form.contentName" placeholder="请输入目录名称" maxlength="100"/>
        </el-form-item>
        <el-form-item label="目录类型：" prop="contentType">
          <el-select style="width: 100%" v-model="form.contentType">
            <el-option v-for="item in contentTypes" :label="item.name" :key="item.id" :value="item.value"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="默认可见范围：" prop="visibleScope">
          <el-select disabled v-model="visibleScope" multiple style="width: 100%">
            <el-option v-for="item in archivesVisibleScopeOptions" :key="item.dictValue" :label="item.dictLabel"
                       :value="item.dictValue" disabled/>
          </el-select>
        </el-form-item>

        <el-form-item label="可见范围：" prop="companyList">
          <el-select v-model="form.companyList" multiple style="width: 100%">
            <el-option v-for="item in companyOptions" :key="item.companyId" :label="item.companyName"
                       :value="item.companyId"/>
          </el-select>
        </el-form-item>

        <!--<el-form-item label="可视范围：" prop="visibleScope">
          <el-select v-model="form.visibleScope" placeholder="可视范围" style="width: 100%">
            <el-option v-for="dict in archivesVisibleScopeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                       :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="目录完整名称" prop="fullName">
          <el-input v-model="form.fullName" type="textarea" placeholder="请输入内容" maxlength="500" show-word-limit/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 可见范围 -->
    <visible-dialog @close="handleClose" :dialogVisible="isOpen" :formData="selectRow"/>
  </div>
</template>

<script>
  import {
    list,
    update,
    save,
    getById,
    deleteByIds,
    deleteById,
  } from "@/api/xuncha/archives/catalogue";
  import {queryCompanyList} from "@/api/xuncha/common/base";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from "@/utils/index";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import VisibleDialog from "../regulationBase/visibleDialog";

  export default {
    components: {ExpandCollapse, Treeselect, VisibleDialog},
    data: function () {
      const validName = (rule, value, callback) => {
        // 添加时的校验：目录名称不能重复
        let existCodeList = this.tableData;
        if (this.isEdit) {
          // 编辑时的校验: 目录名称能取自己
          existCodeList = this.tableData.filter(
            (item) => item.contentId !== this.form.contentId
          );
        }
        existCodeList.map((it) => it.contentName).includes(value)
          ? callback(new Error("同级目录中已存在【" + value + "】目录"))
          : callback();
      };
      return {
        isOpen: false,
        selectRow: {},
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
        // 表格数据
        tableData: [],
        isEdit: false,
        //上级目录下拉数据
        parentSelect: [
          {
            contentName: "根目录",
            contentId: "0",
            children: [],
          },
        ],
        contentTypes: [
          {
            value: 1,
            name: "中央库",
          },
          {
            value: 2,
            name: "集团",
          },
          {
            value: 3,
            name: "省公司",
          },
        ],
        levelTypes: [
          {
            value: 1,
            name: "一级目录",
          },
          {
            value: 2,
            name: "二级目录",
          },
          {
            value: 3,
            name: "三级目录",
          },
        ],
        // 可视范围字典
        archivesVisibleScopeOptions: [],
        // 默认可见范围
        visibleScope: [],
        // 可见范围下拉框
        companyOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //总条数
        total: 0,
        // 查询参数
        queryParams: {},
        multipleSelection: [],
        // 表单参数
        form: {
          contentId: "",
          parentId: "0",
          contentName: "",
          fullName: "",
          contentType: "",
          level: "",
          companyList: []
        },
        // 表单校验
        rules: {
          contentName: [
            {required: true, message: "目录名称不能为空", trigger: "change"},
            /*{validator: validName, trigger: "blur"},*/
          ],
          contentType: [
            {required: true, message: "目录类型不能为空", trigger: "change"},
          ]
        },
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
      xuncha_report_content_typeFieldFilter(val, optionArr) {
        var arr = optionArr.filter((item) => item.dictValue === val);
        if (!arr.length) return "";
        return arr[0].dictLabel;
      },
    },
    created() {
      this.getList();
      this.getDicts('archives_visible_scope').then(response => {
        this.archivesVisibleScopeOptions = response.data || []
        this.archivesVisibleScopeOptions.forEach(item => {
          // 默认可见范围全选，巡察组、巡察办、地市公司分管领导
          this.visibleScope.push(item.dictValue)
        })
      })
      // 获得可见范围下拉框数据
      queryCompanyList().then((res) => {
        this.companyOptions.push(...res.data);
      });
    },
    methods: {
      openForm() {
        this.selectRow = this.multipleSelection[0];
        this.selectRow.applyId = this.selectRow.contentId;
        // this.$refs.tableRef.multipleSelection
        this.isOpen = true;
      },
      handleClose(e) {
        if (e == 200) {
          this.getList();
        }
        this.isOpen = false;
      },
      //获取级别
      getIsLevel(id) {
        let flag = 1;
        let obj = {};
        this.parentSelect.forEach((res) => {
          if (res.contentId == id) {
            flag = 1;
            obj = res;
          }
          if (res.children) {
            res.children.forEach((i) => {
              if (i.contentId == id) {
                flag = 2;
                obj = i;
              }
              if (i.children) {
                i.children.forEach((k) => {
                  if (k.contentId == id) {
                    flag = 3;
                    obj = k;
                  }
                });
              }
            });
          }
        });

        return {flag, obj};
      },
      //表格目录类型翻译
      catalogueTypeFormat(row, column) {
        let temp = this.contentTypes.filter(
          (item) => item.value == row.contentType
        )[0];
        if (temp) {
          return temp.name;
        }
        return "";
      },
      /** 转换菜单数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.contentId,
          label: node.contentName,
          children: node.children,
        };
      },
      /** 查询巡察报告资料库_文件清单 列表 */
      getList() {
        this.loading = true;
        list(this.queryParams).then((response) => {
          this.tableData = this.getTreeData(response.rows || []);
          this.parentSelect = JSON.parse(JSON.stringify(this.tableData));
          this.parentSelect.splice(0, 0, {
            contentName: "根目录",
            contentId: "0",
            children: [],
          });
          this.total = Number(response.total);
          this.loading = false;
        });
      },
      //转换数据
      getTreeData(data) {
        let temp = [];
        data.forEach((item) => {
          if (item.parentId == 0) {
            temp.push(item);
          } else {
            let parent = data.filter(
              (item2) => item2.contentId == item.parentId
            )[0];
            if (parent) {
              if (!parent.children) {
                parent.children = [];
              }
              parent.children.push(item);
            }
          }
        });
        return temp;
      },
      // 可视范围字典翻译
      archivesVisibleScopeFormat(row, column) {
        return this.selectDictLabel(this.archivesVisibleScopeOptions, row.visibleScope)
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          contentId: "",
          parentId: "0",
          contentName: "",
          fullName: "",
          contentType: "",
          level: "",
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.contentName = ''
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.multipleSelection = selection;
        this.ids = selection.map((item) => item.contentId);
        this.single = selection.length !== 1;
        this.multiple = !selection.length;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        if (this.ids.length != 0) {
          // 修改目录类型
          this.$set(this.form, "parentId", this.ids[0]);
          // 添加目录类型
          getById({contentId: this.ids[0]}).then(res => {
            if (res.code === 200) {
              this.form.contentType = res.data.contentType
            }
          })
        }
        this.open = true;
        this.title = "添加目录";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.isEdit = true;
        this.disabled = false;
        this.reset();
        const contentId = row.contentId || this.ids;
        getById({contentId}).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "修改目录";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        this.disabled = true;
        const contentId = row.contentId || this.ids;
        getById({contentId}).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "查看目录";
        });
      },
      /** 提交按钮 */
      submitForm() {
        if (this.form.contentId == this.form.parentId) {
          this.$message.warning("不能选择自己为上级目录!");
          return;
        }
        if (this.disabled) {
          this.open = false;
          return;
        }
        this.$refs["form"].validate((valid) => {
          if (valid) {
            let result = this.getIsLevel(this.form.parentId).flag;
            if (result == 3) {
              this.$message.warning("请勿添加四级目录!");
              return;
            }
            this.form.level = result + 1;
            this.$confirm("是否确定提交?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(() => {
              if (this.form.contentId) {
                update(this.form).then((response) => {
                  if (response.code === 200) {
                    this.msgSuccess("修改成功");
                    this.open = false;
                    this.getList();
                  }
                });
              } else {
                save(this.form).then((response) => {
                  if (response.code === 200) {
                    this.msgSuccess("新增成功");
                    this.open = false;
                    this.getList();
                  }
                });
              }
            });
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        let that = this;
        let flag = false;
        const contentIds = row.contentId || this.ids;
        that.multipleSelection.forEach((item) => {
          if (that.getIsLevel(item.contentId).obj.children) {
            flag = true;
          }
        });
        this.$confirm("删除后将不能恢复，确定删除？", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            if (!row.contentId) {
              if (flag) {
                that.$message.warning("有子目录不能删除,请先删除子目录!");
                return;
              }
              return deleteByIds({ids: contentIds.join(",")});
            }
            if (row.children) {
              that.$message.warning("有子目录不能删除,请先删除子目录!");
              return;
            }
            return deleteById({contentId: contentIds});
          })
          .then((res) => {
            if (res.code == 200) {
              this.getList();
              this.msgSuccess("删除成功");
            }
          })
          .catch(function (err) {
            console.log(err);
          });
      },
      // 修改上级目录
      parentIdInput(value, instanceId) {
        // 修改目录类型
        if (value) {
          getById({contentId: value}).then(res => {
            if (res.code === 200) {
              this.form.contentType = res.data.contentType
            }
          })
        }
      }
    },
  };
</script>

<style lang="scss" scoped>
  .app-container {
    background: #f5f7f9;
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
