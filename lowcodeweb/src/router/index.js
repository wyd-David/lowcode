import Vue from 'vue'
import Router from 'vue-router'

//待办路由配置
import { bpmTodo } from "./bpmTodo";

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/redirect"),
      },
    ],
  },
  {
    path: "/login",
    component: () => import("@/views/login"),
    hidden: true,
  },
  {
    path: "/register",
    component: () => import("@/views/register"),
    hidden: true,
  },
  {
    path: "/404",
    component: () => import("@/views/error/404"),
    hidden: true,
  },
  {
    path: "/401",
    component: () => import("@/views/error/401"),
    hidden: true,
  },
  {
    path: "/noPermission",
    component: () => import("@/views/error/noPermission"),
    hidden: true,
  },
  {
    path: "/lowcodeFormDesign",
    component: () => import("@/views/lowcodeForm/design"),
    hidden: true,
  },
  {
    path: "/lowcodeForm",
    component: () => import("@/views/lowcodeForm/form"),
    hidden: true,
  },
  {
    path: "/lowcodeFormChild",
    component: () => import("@/views/lowcodeForm/formchild"),
    hidden: true,
  },
  {
    path: "/lowcodeFormChildView",
    component: () => import("@/views/lowcodeForm/formchildview"),
    hidden: true,
  },
  {
    path: "/exam",
    component: () => import("@/views/lowcodeForm/exam"),
    hidden: true,
  },
  {
    path: "/lowcodeFormFlow",
    component: () => import("@/views/lowcodeForm/formFlow"),
    hidden: true,
  },
  {
    path: "/lowcodeResult",
    component: () => import("@/views/lowcodeForm/result"),
    hidden: true,
  },
  {
    path: "/lowcodeExam",
    component: () => import("@/views/lowcodeForm/exam"),
    hidden: true,
  },
  {
    path: "/autolistinfo",
    component: () => import("@/views/autoListInfo/autoListInfo"),
    hidden: true,
  },
  {
    path: "/autoResource",
    component: () => import("@/views/autoListInfo/autoResource"),
    hidden: true,
  },
  {
    path: "/ThreeDform",
    component: () => import("@/views/ThreeD/form"),
    hidden: true,
  },
  {
    path: "/ThreeDformDemo",
    component: () => import("@/views/ThreeD/form"),
    hidden: true,
  },

  {
    path: "/test",
    component: () => import("@/views/demo/test"),
    hidden: true,
  },
  {
    path: "/test/groupPiciInfo",
    component: () =>
      import("@/views/xuncha/transferManage/groupPiciInfo/index.vue"),
    hidden: true,
  },
  {
    path: "/ai/chart",
    component: () =>
      import("@/views/chartAi/gpt/index.vue"),
    hidden: true,
  },
  {
    path: "/ai/chartNow",
    component: () =>
      import("@/views/chartAi/gpt/nologin.vue"),
    hidden: true,
  },
  {
    path: "/ai/personchartNow",
    component: () =>
      import("@/views/chartAi/gpt/person.vue"),
    hidden: true,
  },
  {
    path: "",
    component: Layout,
    redirect: "index",
    children: [
      {
        path: "index",
        component: () => import("@/views/index"),
        name: "Index",
        meta: { title: "AI工作台", icon: "dashboard", affix: true },
      },{
        path: "index1",
        component: () => import("@/views/indextodo"),
        name: "Index1",
        meta: { title: "待办工作台", icon: "dashboard", affix: true },
      },
    ],
  },
  /* {
    component: Layout,
    redirect: "index",
    children: [
      {
        path: "home",
        component: () => import("@/views/home/index"),
        name: "Index",
        meta: { title: "首页", icon: "dashboard", affix: true },
      },
    ],
  }, */

  {
    path: "/user",
    component: Layout,
    hidden: true,
    redirect: "noredirect",
    children: [
      {
        path: "profile",
        component: () => import("@/views/system/user/profile/index"),
        name: "Profile",
        meta: { title: "个人中心", icon: "user" },
      },
    ],
  },

  {
    path: "/user",
    component: Layout,
    hidden: true,
    redirect: "noredirect",
    children: [
      {
        path: "profile",
        component: (resolve) =>
          require(["@/views/system/user/profile/index"], resolve),
        name: "Profile",
        meta: { title: "个人中心", icon: "user" },
      },
    ],
  },

  {
    path: "/dict",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "type/data/:dictId",
        component: (resolve) => require(["@/views/system/dict/data"], resolve),
        name: "sjzd",
        meta: { title: "字典数据", icon: "" },
      },
    ],
  },
  {
    path: "/role",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "type/data/:roleId",
        component: (resolve) => require(["@/views/system/role/data"], resolve),
        meta: { title: "角色用户信息", icon: "" },
      },
    ],
  },
  {
    path: "/dict",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "type/data/:dictId",
        component: (resolve) => require(["@/views/system/dict/data"], resolve),
        name: "zd",
        meta: { title: "字典数据", icon: "" },
      },
    ],
  },

  {
    path: "/roleControlManager",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "control/data/:id",
        component: (resolve) =>
          require(["@/views/system/roleControlManager/data"], resolve),
        name: "sq",
        meta: { title: "角色授权信息", icon: "" },
      },
    ],
  },
  {
    name: "AIModel",
    path: "/AIModel",
    component: () => import("@/views/aiModel/home"),
    hidden: true,
  },
  ...bpmTodo,
];

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  // {
  //   path: '/system/user-auth',
  //   component: Layout,
  //   hidden: true,
  //   permissions: ['system:user:edit'],
  //   children: [
  //     {
  //       path: 'role/:userId(/d+)',
  //       component: () => import('@/views/system/user/authRole'),
  //       name: 'AuthRole',
  //       meta: { title: '分配角色', activeMenu: '/system/user' }
  //     }
  //   ]
  // },
  // {
  //   path: '/system/role-auth',
  //   component: Layout,
  //   hidden: true,
  //   permissions: ['system:role:edit'],
  //   children: [
  //     {
  //       path: 'user/:roleId(/d+)',
  //       component: () => import('@/views/system/role/authUser'),
  //       name: 'AuthUser',
  //       meta: { title: '分配用户', activeMenu: '/system/role' }
  //     }
  //   ]
  // },
  {
    path: "/system/dict-data",
    component: Layout,
    hidden: true,
    permissions: ["system:dict:list"],
    children: [
      {
        path: "index/:dictId(/d+)",
        component: () => import("@/views/system/dict/data"),
        name: "Data",
        meta: { title: "字典数据", activeMenu: "/system/dict" },
      },
    ],
  },
  {
    path: "/monitor/job-log",
    component: Layout,
    hidden: true,
    permissions: ["monitor:job:list"],
    children: [
      {
        path: "index",
        component: () => import("@/views/monitor/job/log"),
        name: "JobLog",
        meta: { title: "调度日志", activeMenu: "/monitor/job" },
      },
    ],
  },
  {
    path: "/tool",
    component: Layout,
    hidden: true,
    permissions: ["tool:gen:edit"],
    children: [
      {
        path: "gen-edit/:tableId",
        component: () => import("@/views/tool/gen/editTable"),
        name: "GenEdit",
        meta: { title: "修改生成配置", activeMenu: "/tool/gen" },
      },
    ],
  },
];

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'hash', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
