<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <permission-tabs ref="pt" @ptClick="ptClick"></permission-tabs>
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="目录名称" prop="contentName">
            <el-input v-model="queryParams.contentName" placeholder="请输入目录名称" clearable size="small"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="归档范围" prop="archiveOrgType">
            <el-select v-model="queryParams.archiveOrgType" placeholder="请选择归档范围" clearable size="small">
              <el-option v-for="dict in archiveOrgTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                         :value="dict.dictValue"/>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <page-title></page-title>
    <!-- 列表 -->
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <el-col :span="1.5">
          <el-button type="primary" :disabled="selectRowArr.length == 0" @click="openForm" icon="el-icon-plus"
                     size="mini">设置可见范围
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
                     v-hasPermi="['xunchaReportContent:xunchaReportContent:add']">新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                     v-hasPermi="['xunchaReportContent:xunchaReportContent:edit']">修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                     v-hasPermi="['xunchaReportContent:xunchaReportContent:remove']">删除
          </el-button>
        </el-col>
        <!-- <right-toolbar
          :showSearch.sync="showSearch"
          class="normalBtn"
          @queryTable="getList"
        ></right-toolbar> -->
      </el-row>
      <el-table v-loading="loading" :data="xunchaReportContentList" @selection-change="handleSelectionChange"
                row-key="contentId" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column type="selection" width="55" align="left"/>
        <el-table-column type="index" label="序号" width="55" align="center"/>
        <el-table-column label="目录名称" align="left" prop="contentName"/>
        <el-table-column label="资料移交时间" align="center" prop="archiveDate" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.archiveDate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="归档范围" align="center" prop="archiveOrgType" :formatter="archiveOrgTypeFormat"/>
        <el-table-column label="可见范围" align="center" prop="companyNameStr" width="160"/>
        <el-table-column label="说明" align="left" prop="remark"/>
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
    </div>
    <!-- 新增、修改、查看对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="上级目录：" prop="parentId">
              <!-- <el-select v-model="form.parentId" placeholder="请选择上级目录">
                <el-option label="根目录" value=""/>
                <el-option v-for="item in xunchaReportContentList" :key="item.contentId" :label="item.contentName" :value="item.contentId"/>
              </el-select> -->
              <treeselect :disabled="disabled" v-model="form.parentId" :options="parentSelect" :normalizer="normalizer"
                          :show-count="true" placeholder="选择上级菜单" ref="tree"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报告类型：" prop="reportType">
              <!-- <dict
                v-model="form.reportType"
                :label.sync="form.dataName"
                width="100%"
                :dictType="$dicCode.reportType"
              ></dict> -->
              <el-input :value="reportTypeName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="目录名称：" prop="contentName">
              <el-input show-word-limit maxlength="100" v-model="form.contentName" placeholder="请输入目录名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归档范围：" prop="archiveOrgType">
              <el-select style="width: 100%" v-model="form.archiveOrgType" placeholder="请选择归档范围">
                <el-option v-for="dict in archiveOrgTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                           :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="资料移交时间：" prop="archiveDate">
              <!-- <el-input
                show-word-limit
                maxlength="100"
                v-model="form.archiveDate"
                placeholder="选择资料移交时间"
                clearable
                style="width: 100%"
              /> -->
              <el-select style="width: 100%" v-model="form.archiveDate">
                <el-option v-for="item in archiveDates" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictLabel"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否必传：" prop="requireFlag">
              <el-select style="width: 100%" v-model="form.requireFlag" placeholder="请选择是否必传">
                <el-option label="是" value="1"/>
                <el-option label="否" value="0"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <!--<el-col :span="12">
            <el-form-item label="可视范围：" prop="visibleScope">
              <el-select v-model="form.visibleScope" placeholder="可视范围" style="width: 100%">
                <el-option v-for="dict in archivesVisibleScopeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>-->
          <el-col :span="12">
            <el-form-item label="默认可见范围：" prop="visibleScope">
              <el-select disabled v-model="visibleScope" multiple style="width: 100%">
                <el-option v-for="item in archivesVisibleScopeOptions" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictValue" disabled/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="可见范围：" prop="companyList">
              <el-select v-model="form.companyList" multiple style="width: 100%">
                <el-option v-for="item in companyOptions" :key="item.companyId" :label="item.companyName"
                           :value="item.companyId"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="说明：" prop="remark">
          <el-input v-model="form.remark" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: right">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 可见范围 -->
    <visible-dialog @close="handleClose" :dialogVisible="isOpen" :formData="selectRow" controller="xunchaReportPerm"/>
  </div>
</template>

<script>
  import {getToken} from "@/utils/auth";
  import {
    listXunchaReportContent,
    getXunchaReportContent,
    delXunchaReportContent,
    addXunchaReportContent,
    updateXunchaReportContent,
    treeXunchaReportContent,
  } from "@/api/xuncha/archives/xunchaReportContent";
  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {formatDate} from "@/utils/index";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import PageTitle from "@/components/PageTitle";
  import Dict from "@/components/XunCha/Dict/index";
  import {queryDictList} from "@/api/xuncha/common/base";
  import VisibleDialog from "./visibleDialog";
  import permissionTabs from "@/components/PermissionTabs/index";
  import {queryCompanyList} from "@/api/xuncha/common/base";

  export default {
    name: "XunchaReportContent",
    components: {
      permissionTabs,
      ExpandCollapse,
      Treeselect,
      PageTitle,
      Dict,
      VisibleDialog,
    },
    data() {
      return {
        isOpen: false,
        selectRow: {},
        selectRowArr: [],
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
        // 巡察报告资料库_文件清单 表格数据
        xunchaReportContentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 资料提供方字典
        archiveOrgTypeOptions: [],
        // 报告类型字典
        reportTypeOptions: [],
        // 可视范围字典
        archivesVisibleScopeOptions: [],
        //移交时间字典
        archiveDates: [],
        // 查询参数
        queryParams: {
          parentId: null,
          contentName: null,
          archiveOrgType: null,
          archiveDate: null,
          requireFlag: null,
          remark: null,
        },
        parentSelect: [],
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          archiveOrgType: [
            {required: true, message: "资料提供方不能为空", trigger: "change"},
          ],
          requireFlag: [
            {required: true, message: "是否必传不能为空", trigger: "change"},
          ],
          contentName: [
            {required: true, message: "目录名称不能为空", trigger: "change"},
          ],
          reportType: [
            {required: false, message: "报告类型不能为空", trigger: "change"},
          ],
        },
        activeName: "XunchaReportFile",
        reportTypeName: "巡察报告",
        // 默认可见范围
        visibleScope: [],
        // 可见范围下拉框
        companyOptions: [],
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
      this.getDicts("xuncha_report_content_type").then((response) => {
        this.archiveOrgTypeOptions = response.data || [];
      });
      queryDictList({dictType: "report_type"}).then((res) => {
        this.reportTypeOptions = res.data || [];
      });
      this.getDicts("archives_visible_scope").then((response) => {
        this.archivesVisibleScopeOptions = response.data || [];
      });
      this.getDicts("archives_report_content_time").then((response) => {
        this.archiveDates = response.data || [];
      });

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
        console.log("creat");
      });
    },
    methods: {
      ptClick(e, o) {
        if (o) {
          this.activeName = o.name;
          this.reportTypeName = e;
        }
        this.getList();
      },
      openForm() {
        this.selectRow = this.selectRowArr[0];
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
        let companyId = this.$store.getters.userInfo.companyId;
        let viewAll = false;
        if (
          this.$store.getters.userInfo.department ==
          "巡察工作办公室(党风廉政办公室)"
        ) {
          viewAll = true;
        }
        if (!companyId) {
          viewAll = true;
        }
        treeXunchaReportContent({
          ...this.queryParams,
          companyId: companyId,
          reportType: this.activeName == "XunchaReportFile" ? "A" : "B",
          viewAll: viewAll,
        }).then((response) => {
          this.xunchaReportContentList = this.getTreeData(response.data) || [];
          this.parentSelect = JSON.parse(
            JSON.stringify(this.xunchaReportContentList)
          );
          this.parentSelect.splice(0, 0, {
            contentName: "根目录",
            contentId: "",
            children: [],
          });
          this.loading = false;
        });
      },
      // 资料提供方字典翻译
      archiveOrgTypeFormat(row, column) {
        return this.selectDictLabel(
          this.archiveOrgTypeOptions,
          row.archiveOrgType
        );
      },
      // 可视范围字典翻译
      archivesVisibleScopeFormat(row, column) {
        return this.selectDictLabel(
          this.archivesVisibleScopeOptions,
          row.visibleScope
        );
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          contentId: null,
          parentId: "",
          contentName: null,
          archiveOrgType: null,
          archiveDate: null,
          requireFlag: "0",
          remark: null,
          createdBy: null,
          createdTime: null,
          updatedBy: null,
          updatedTime: null,
          delFlag: null,
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
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map((item) => item.contentId);
        this.selectRowArr = selection;
        this.single = selection.length !== 1;
        this.multiple = !selection.length;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.getList();
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加文件清单";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const contentId = row.contentId || this.ids;
        getXunchaReportContent(contentId).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "修改文件清单";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        this.disabled = true;
        const contentId = row.contentId || this.ids;
        getXunchaReportContent(contentId).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "查看文件清单";
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
            let result = this.getIsLevel(this.form.parentId);
            if (result.flag == 3) {
              this.$message.warning("请勿添加四级目录!");
              return;
            }
            // if (
            //   result.obj.reportType &&
            //   result.obj.reportType != this.form.reportType
            // ) {
            //   this.$message.warning("报告类型必须跟父级一致!");
            //   return;
            // }
            this.form.reportType =
              this.activeName == "XunchaReportFile" ? "A" : "B";
            if (this.form.contentId != null) {
              updateXunchaReportContent(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addXunchaReportContent(this.form).then((response) => {
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
        const contentIds = row.contentId || this.ids;
        this.$confirm("删除后将不能恢复，确定删除？", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return delXunchaReportContent(contentIds);
          })
          .then((res) => {
            if (res.code == 200) {
              this.getList();
              this.msgSuccess("删除成功");
            } else {
              this.msgError(res.msg);
            }
          })
          .catch(function () {
          });
      },
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
