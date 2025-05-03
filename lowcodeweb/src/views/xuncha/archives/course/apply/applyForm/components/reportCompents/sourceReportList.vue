<template>
  <div>
    <el-table
      :data="renderDynamic"
      @select="selectFun"
      @select-all="selectAllFun"
      ref="product"
      row-key="contentId"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column type="selection" width="55" align="left" />
      <el-table-column type="index" label="序号" width="55" align="center" />
      <el-table-column label="目录名称" prop="contentName" />
      <el-table-column
        label="资料移交时间"
        align="center"
        prop="archiveDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.archiveDate }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="资料提供方"
        prop="archiveOrgType"
        :formatter="archiveOrgTypeFormat"
      />
      <el-table-column label="说明" prop="remark" />
    </el-table>
    <!-- <div class="pageBox">
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div> -->
  </div>
</template>

<script>
import { getSourceList } from "@/api/xuncha/archives/xunchaProcessContent.js";
import { treeXunchaReportContent } from "@/api/xuncha/archives/xunchaReportContent";

export default {
  props: {
    // tableData: {
    //   type: Array,
    //   default: [],
    // },
  },
  data() {
    return {
      archiveOrgTypeOptions: [],
      multipleSelection: [],
      renderDynamic: [],
    };
  },
  mounted() {
    this.handleOpen();
    this.getDicts("process_content_archive").then((response) => {
      this.archiveOrgTypeOptions = response.data || [];
    });
  },
  methods: {
    handleOpen() {
      treeXunchaReportContent({
        reportType: "A",
      }).then((res) => {
        let data = res.data.map((item) => {
          item.isSelect = false;
          return item;
        });
        this.renderDynamic = this.getTreeData(data);
      });
    },
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
    archiveOrgTypeFormat(row, column) {
      return this.selectDictLabel(
        this.archiveOrgTypeOptions,
        row.archiveOrgType
      );
    },
    selectFun(selection, row) {
      this.setRowIsSelect(row);
    },
    setRowIsSelect(row) {
      if (row.isSelect === "") {
        row.isSelect = false;
        this.$refs.product.toggleRowSelection(row, true);
      }
      row.isSelect = !row.isSelect;
      let that = this;

      function selectAllChildrens(data) {
        data.forEach((item) => {
          item.isSelect = row.isSelect;
          that.$refs.product.toggleRowSelection(item, row.isSelect);
          if (item.children && item.children.length) {
            selectAllChildrens(item.children);
          }
        });
      }

      function getSelectStatus(selectStatuaArr, data) {
        data.forEach((childrenItem) => {
          selectStatuaArr.push(childrenItem.isSelect);
          if (childrenItem.children && childrenItem.children.length) {
            getSelectStatus(selectStatuaArr, childrenItem.children);
          }
        });
        return selectStatuaArr;
      }
      function getLevelStatus(row) {
        //如果当前节点的parantId =0 并且有子节点，则为1
        //如果当前节点的parantId !=0 并且子节点没有子节点 则为3
        if (row.parentId == 0) {
          if (row.children && row.children.length) {
            return 1;
          } else {
            return 4;
          }
        } else {
          if (!row.children || !row.children.length) {
            return 3;
          } else {
            return 2;
          }
        }
      }
      let result = {};
      //获取明确的节点
      function getExplicitNode(data, parentId) {
        data.forEach((item) => {
          if (item.contentId == parentId) {
            result = item;
          }
          if (item.children && item.children.length) {
            getExplicitNode(item.children, parentId);
          }
        });
        return result;
      }
      function operateLastLeve(row) {
        //操作的是子节点  1、获取父节点  2、判断子节点选中个数，如果全部选中则父节点设为选中状态，如果都不选中，则为不选中状态，如果部分选择，则设为不明确状态
        let selectStatuaArr = [];
        let item = getExplicitNode(that.renderDynamic, row.parentId);
        console.log(item);
        selectStatuaArr = getSelectStatus(selectStatuaArr, item.children);
        console.log(selectStatuaArr);
        if (
          selectStatuaArr.some((selectItem) => {
            return true == selectItem;
          })
        ) {
          item.isSelect = true;
          that.$refs.product.toggleRowSelection(item, true);
        } else if (
          selectStatuaArr.every((selectItem) => {
            return false == selectItem;
          })
        ) {
          item.isSelect = false;
          that.$refs.product.toggleRowSelection(item, false);
        } else {
          item.isSelect = "";
        }
        //则还有父级
        if (item.parentId != "") {
          operateLastLeve(item);
        }
      }
      //判断操作的是子级点复选框还是父级点复选框，如果是父级点，则控制子级点的全选和不全选

      //1、只是父级 2、既是子集，又是父级 3、只是子级
      let levelSataus = getLevelStatus(row);
      if (levelSataus == 1) {
        selectAllChildrens(row.children);
      } else if (levelSataus == 2) {
        selectAllChildrens(row.children);
        operateLastLeve(row);
      } else if (levelSataus == 3) {
        operateLastLeve(row);
      }
    },
    checkIsAllSelect() {
      this.oneProductIsSelect = [];
      this.renderDynamic.forEach((item) => {
        this.oneProductIsSelect.push(item.isSelect);
      });
      //判断一级产品是否是全选.如果一级产品全为true，则设置为取消全选，否则全选
      let isAllSelect = this.oneProductIsSelect.every((selectStatusItem) => {
        return true == selectStatusItem;
      });
      return isAllSelect;
    },

    selectAllFun(selection) {
      let isAllSelect = this.checkIsAllSelect();
      this.renderDynamic.forEach((item) => {
        item.isSelect = isAllSelect;
        this.$refs.product.toggleRowSelection(item, !isAllSelect);
        this.selectFun(selection, item);
      });
    },
  },
};
</script>

<style></style>