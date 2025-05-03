import Vue from "vue";

import Cookies from "js-cookie";


import Element from "element-ui";
Element.Dialog.props.closeOnClickModal.default = false;
import { Loading } from "element-ui";
import "./assets/styles/element-variables.scss";

import "@/assets/styles/index.scss"; // global css
import "@/assets/styles/ruoyi.scss"; // ruoyi css
import "@/assets/styles/scrollbar.scss"; // ruoyi css
import App from "./App";
import store from "./store";
import router from "./router";
import directive from "./directive"; // directive
import plugins from "./plugins"; // plugins
import { download } from "@/utils/request";

import VForm from 'vform-builds'  //引入VForm库
import 'element-ui/lib/theme-chalk/index.css'  //引入element-ui样式
import 'vform-builds/dist/VFormDesigner.css'  //引入VForm样式
import VueClipboard from 'vue-clipboard2'



import "./assets/icons"; // icon
import "./permission"; // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree,
} from "@/utils/ruoyi";

// import uToast from '@/components/downloadDialog/index'
// Vue.use(uToast);
// 引入全局组件
import components from "@/components/index.js";

import * as echarts from "echarts";
// 头部标签组件
import VueMeta from "vue-meta";
// 字典数据组件
import DictData from "@/components/DictData";
import GDMapData from "@/utils/guangdong.json";
import Code from "@/utils/code.js";
echarts.registerMap("guandong", GDMapData);
// 全局方法挂载
Vue.prototype.getDicts = getDicts;
Vue.prototype.getConfigKey = getConfigKey;
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;
Vue.prototype.selectDictLabels = selectDictLabels;
Vue.prototype.download = download;
Vue.prototype.handleTree = handleTree;
Vue.prototype.$echarts = echarts;
Vue.prototype.$dicCode = Code;
Vue.prototype.$bus = new Vue();

Vue.prototype.openLoading = function (str,time) {
  let tempObj = {
    // 声明一个loading对象
    lock: true, // 是否锁屏
    text: "加载中...",
    fullscreen: true,
    customClass: "loadingclass", // **遮罩层新增类名，如果需要修改loading的样式**
  };
  if (str) {
    tempObj.target = document.querySelector(str);
  }
  const loading = this.$loading(tempObj);
  setTimeout(function () {
    // 设定定时器，超时5S后自动关闭遮罩层，避免请求失败时，遮罩层一直存在的问题
    loading.close(); // 关闭遮罩层
  }, time||20000);
  return loading;
};

Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
};

Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
};

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
};

// 全局组件挂载

Vue.use(components);
Vue.use(VueClipboard);

Vue.use(directive);
Vue.use(plugins);
Vue.use(VueMeta);
DictData.install();

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get("size") || "medium", // set element-ui default size
});
Vue.use(VForm)  //全局注册VForm(同时注册了v-form-designer和v-form-render组件)


Vue.directive("hide", {
  inserted: (el, obj, vnode) => {
    // el.remove()
    if (obj.value != 1) {
      el.parentNode.removeChild(el);
    }
  },
});

Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
