<template>
  <div v-if="!item.hidden">
    <template v-if="
      hasOneShowingChild(item.children, item) &&
      (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
      !item.alwaysShow
    ">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path, onlyOneChild.query)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{
          'submenu-title-noDropdown': !isNest,
          oneMenu: onlyOneChild.parentId == '0' || !onlyOneChild.parentId,
        }">
          <item :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)" :title="onlyOneChild.meta.title" />
        </el-menu-item>
      </app-link>
    </template>
    <el-dropdown placement="right" v-else-if="hasOneShowingChild(item.children, item) == '1'">
      <span :class="
        curDropdown.catalogueIndex == resolvePath(item.path)
          ? 'el-dropdown-link el-dropdown-link-active'
          : 'el-dropdown-link'
      ">
        <span :title="item.meta.title" class="el-dropdown-link-title">{{
            item.meta.title
        }}</span><i class="el-icon-arrow-right"></i>
      </span>
      <el-dropdown-menu :append-to-body="false" slot="dropdown">
        <el-dropdown-item :class="{
          'el-dropdown-menu__item_active':
            resolvePath(child.path) == curDropdown.index,
        }" @click.native="onDropdown(child, item)" v-for="child in item.children" :key="child.path">{{
    child.meta.title
}}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template slot="title">
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
      </template>
      <sidebar-item v-for="child in item.children" :key="child.path" :is-nest="true" :item="child"
        :base-path="resolvePath(child.path)" class="nest-menu" :parentIndex="resolvePath(item.path)" />
    </el-submenu>
  </div>
</template>

<script>
import path from "path";
import { isExternal } from "@/utils/validate";
import Item from "./Item";
import AppLink from "./Link";
import FixiOSBug from "./FixiOSBug";

export default {
  name: "SidebarItem",
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    parentIndex: [String],
    // route object
    item: {
      type: Object,
      required: true,
    },
    isNest: {
      type: Boolean,
      default: false,
    },
    basePath: {
      type: String,
      default: "",
    },
  },
  data () {
    this.onlyOneChild = null;
    return {
      curDropdown: localStorage.getItem("curDropdown")
        ? JSON.parse(localStorage.getItem("curDropdown"))
        : {},
    };
  },
  mounted () {
    this.$bus.$on("curDropdownUpdate", () => {
      setTimeout(() => {
        this.initStore();
      }, 0);
    });
  },
  methods: {
    initStore () {
      let curDropdown = localStorage.getItem("curDropdown");
      if (curDropdown) {
        curDropdown = JSON.parse(curDropdown);
        this.curDropdown = curDropdown;
      }
    },
    onDropdown (e, item) {
      let pathT = e.path;
      let query = {}
      if (pathT.indexOf("?") > -1) {
        pathT = e.path.split("?")[0]
        let type = e.path.split("?")[1].split("&");
        type.forEach(itm => {
          query[itm.split("=")[0]] = itm.split("=")[1]
        })
      }
      // debugger
      localStorage["curDropdown"] = JSON.stringify({
        parentIndex: this.parentIndex,
        catalogueIndex: this.resolvePath(item.path),
        index: this.resolvePath(pathT),
      });
      this.initStore();
      this.$bus.$emit("curDropdownUpdate");
      
      this.$router.push({
        path: this.resolvePath(pathT),
        query,
      });
    },
    hasOneShowingChild (children = [], parent) {
      if (!children) {
        children = [];
      }
      const showingChildren = children.filter((item) => {
        if (item.hidden) {
          return false;
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item;
          return true;
        }
      });
      //判断二级目录右侧悬浮弹窗
      if (parent.parentId != "0" && showingChildren.length != 0) {
        return "1";
      }

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true;
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: "", noShowingChildren: true };
        return true;
      }

      return false;
    },
    resolvePath (routePath, routeQuery) {
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(this.basePath)) {
        return this.basePath;
      }
      if (routeQuery) {
        let query = JSON.parse(routeQuery);
        return { path: path.resolve(this.basePath, routePath), query: query };
      }
      return path.resolve(this.basePath, routePath);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-dropdown-link {
  color: #fff;
  font-size: 13px;
  height: 50px;
  line-height: 50px;
  padding-left: 60px;
  min-width: 200px;
  display: flex;
  align-items: center;

  &:hover {
    cursor: pointer;
    background-color: #c34b4c !important;
  }

  &-active {
    background-color: #de1f14 !important;
  }
}

.el-dropdown-menu__item_active {
  background-color: #fbe8e8;
  color: #e04943;
}

.el-dropdown-link-title {
  width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
}

/deep/.el-dropdown-menu--medium .el-dropdown-menu__item {
  padding: 5px 17px;
}

.oneMenu {
  background-image: url(~@/assets/images/bg-menu-item.png);
  background-size: cover;
  background-repeat: no-repeat;
}

/deep/.el-dropdown-menu {
  min-width: 150px !important;
}

/deep/.el-icon-arrow-right:before {
  font-size: 13px;
}
</style>
